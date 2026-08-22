package pomodorocritter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PomodoroServer {

    private static final int PORT = 8080;
    private static final int DEFAULT_SESSION_SECONDS = 1500; // 25 minutes
    private static final int QUALIFYING_SECONDS = 900; // 15 minutes: minimum length that counts toward stats/mood
    private static final Path WEB_ROOT = Path.of("web");

    private final Object lock = new Object();
    private final Critter critter = new Critter();
    private final SessionLog sessionLog = new SessionLog();
    private final PomodoroTimer timer = new PomodoroTimer(DEFAULT_SESSION_SECONDS);
    private int currentSessionLength = DEFAULT_SESSION_SECONDS;

    public static void main(String[] args) throws IOException {
        new PomodoroServer().start();
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/state", this::handleState);
        server.createContext("/start", this::handleStart);
        server.createContext("/pause", this::handlePause);
        server.createContext("/reset", this::handleReset);
        server.createContext("/", new StaticFileHandler(WEB_ROOT));

        server.setExecutor(Executors.newCachedThreadPool());
        server.start();

        ScheduledExecutorService ticker = Executors.newSingleThreadScheduledExecutor();
        ticker.scheduleAtFixedRate(this::tick, 1, 1, TimeUnit.SECONDS);

        System.out.println("PomodoroCritter server running at http://localhost:" + PORT);
    }

    // Server-side clock: advances the timer once a second and, on completion,
    // logs the session and moves remainingSeconds off zero (resetTo) before
    // pausing. Skipping that resetTo is what let isComplete() stay true
    // forever and re-log a session on every subsequent tick.
    private void tick() {
        synchronized (lock) {
            timer.tick();
            if (timer.isComplete()) {
                if (currentSessionLength >= QUALIFYING_SECONDS) {
                    sessionLog.logSession(currentSessionLength / 60);
                    critter.react(sessionLog.getCount(), sessionLog.getTotalMinutes());
                }
                timer.resetTo(currentSessionLength);
                timer.pause();
            }
        }
    }

    private void handleState(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        String json;
        synchronized (lock) {
            json = toStateJson();
        }
        sendJson(exchange, 200, json);
    }

    private void handleStart(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Integer seconds = extractSeconds(body);

        String json;
        synchronized (lock) {
            if (seconds != null && seconds > 0) {
                currentSessionLength = seconds;
                timer.resetTo(currentSessionLength);
            }
            timer.start();
            json = toStateJson();
        }
        sendJson(exchange, 200, json);
    }

    private void handlePause(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        String json;
        synchronized (lock) {
            timer.pause();
            json = toStateJson();
        }
        sendJson(exchange, 200, json);
    }

    private void handleReset(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        String json;
        synchronized (lock) {
            timer.pause();
            timer.resetTo(currentSessionLength);
            json = toStateJson();
        }
        sendJson(exchange, 200, json);
    }

    // Caller must hold `lock`.
    private String toStateJson() {
        return "{"
                + "\"remainingSeconds\":" + timer.getRemainingSeconds() + ","
                + "\"isRunning\":" + timer.isRunning() + ","
                + "\"mood\":\"" + critter.getMood() + "\","
                + "\"sessionCount\":" + sessionLog.getCount() + ","
                + "\"qualifyingMinutes\":" + sessionLog.getTotalMinutes() + ","
                + "\"sessionLength\":" + currentSessionLength + ","
                + "\"qualifyingThresholdSeconds\":" + QUALIFYING_SECONDS
                + "}";
    }

    private static final Pattern SECONDS_PATTERN = Pattern.compile("\"seconds\"\\s*:\\s*(\\d+)");

    private static Integer extractSeconds(String jsonBody) {
        Matcher m = SECONDS_PATTERN.matcher(jsonBody);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return null;
    }

    private static void sendJson(HttpExchange exchange, int status, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static final Map<String, String> CONTENT_TYPES = Map.of(
            "html", "text/html; charset=utf-8",
            "css", "text/css; charset=utf-8",
            "js", "application/javascript; charset=utf-8",
            "svg", "image/svg+xml",
            "png", "image/png",
            "ico", "image/x-icon"
    );

    private static class StaticFileHandler implements HttpHandler {
        private final Path root;

        StaticFileHandler(Path root) {
            this.root = root.toAbsolutePath().normalize();
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String requestPath = exchange.getRequestURI().getPath();
            if (requestPath.equals("/")) {
                requestPath = "/index.html";
            }

            Path resolved = root.resolve("." + requestPath).normalize();
            if (!resolved.startsWith(root) || !Files.isRegularFile(resolved)) {
                byte[] notFound = "404 Not Found".getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(404, notFound.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(notFound);
                }
                return;
            }

            String extension = fileExtension(resolved.getFileName().toString());
            String contentType = CONTENT_TYPES.getOrDefault(extension, "application/octet-stream");
            byte[] bytes = Files.readAllBytes(resolved);

            exchange.getResponseHeaders().add("Content-Type", contentType);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }

        private static String fileExtension(String filename) {
            int dot = filename.lastIndexOf('.');
            return dot == -1 ? "" : filename.substring(dot + 1).toLowerCase();
        }
    }
}
