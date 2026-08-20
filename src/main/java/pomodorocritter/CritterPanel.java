// Critter Outline (Vibecoded Frontend)
package pomodorocritter;

import javax.swing.*;
import java.awt.*;

public class CritterPanel extends JPanel {

    private Mood currentMood;

    public CritterPanel() {
        this.currentMood = Mood.SLEEPY;
        setOpaque(true);
    }

    public void setMood(Mood mood) {
        this.currentMood = mood;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Background
        g2.setColor(new Color(255, 245, 250));
        g2.fillRect(0, 0, w, h);

        // Body sizing, centered
        int bodySize = Math.min(w, h) - 60;
        int bodyX = (w - bodySize) / 2;
        int bodyY = (h - bodySize) / 2 + 10;

        // Glow ring for GLOWING mood, drawn behind the body
        if (currentMood == Mood.GLOWING) {
            g2.setColor(new Color(255, 235, 150, 120));
            int glowPad = 25;
            g2.fillOval(bodyX - glowPad, bodyY - glowPad, bodySize + glowPad * 2, bodySize + glowPad * 2);
        }

        // Ears
        Color bodyColor = moodBodyColor();
        g2.setColor(bodyColor);
        int earSize = bodySize / 4;
        g2.fillOval(bodyX + bodySize / 8, bodyY - earSize / 2, earSize, earSize);
        g2.fillOval(bodyX + bodySize - bodySize / 8 - earSize, bodyY - earSize / 2, earSize, earSize);

        // Body
        g2.setColor(bodyColor);
        g2.fillOval(bodyX, bodyY, bodySize, bodySize);

        // Cheeks
        g2.setColor(new Color(255, 200, 210, 150));
        int cheekSize = bodySize / 6;
        g2.fillOval(bodyX + bodySize / 8, bodyY + bodySize / 2, cheekSize, cheekSize);
        g2.fillOval(bodyX + bodySize - bodySize / 8 - cheekSize, bodyY + bodySize / 2, cheekSize, cheekSize);

        // Face
        drawFace(g2, bodyX, bodyY, bodySize);
    }

    private Color moodBodyColor() {
        switch (currentMood) {
            case SLEEPY:  return new Color(200, 200, 230);
            case NEUTRAL: return new Color(190, 225, 235);
            case HAPPY:   return new Color(190, 235, 200);
            case GLOWING: return new Color(255, 220, 160);
            default:      return new Color(210, 210, 220);
        }
    }

    private void drawFace(Graphics2D g2, int bodyX, int bodyY, int bodySize) {
        int eyeY = bodyY + bodySize * 2 / 5;
        int leftEyeX = bodyX + bodySize * 3 / 8;
        int rightEyeX = bodyX + bodySize * 5 / 8;
        int eyeSize = bodySize / 10;

        g2.setColor(new Color(60, 60, 70));
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        switch (currentMood) {
            case SLEEPY:
                // Closed, drooping eyes: simple downward arcs
                g2.drawArc(leftEyeX - eyeSize / 2, eyeY, eyeSize, eyeSize, 0, -180);
                g2.drawArc(rightEyeX - eyeSize / 2, eyeY, eyeSize, eyeSize, 0, -180);
                // Small flat mouth
                g2.drawLine(bodyX + bodySize / 2 - 8, eyeY + bodySize / 6, bodyX + bodySize / 2 + 8, eyeY + bodySize / 6);
                break;

            case NEUTRAL:
                // Open round eyes
                g2.fillOval(leftEyeX - eyeSize / 2, eyeY, eyeSize, eyeSize);
                g2.fillOval(rightEyeX - eyeSize / 2, eyeY, eyeSize, eyeSize);
                // Straight mouth
                g2.drawLine(bodyX + bodySize / 2 - 10, eyeY + bodySize / 5, bodyX + bodySize / 2 + 10, eyeY + bodySize / 5);
                break;

            case HAPPY:
                // Open round eyes
                g2.fillOval(leftEyeX - eyeSize / 2, eyeY, eyeSize, eyeSize);
                g2.fillOval(rightEyeX - eyeSize / 2, eyeY, eyeSize, eyeSize);
                // Upward smile arc
                g2.drawArc(bodyX + bodySize / 2 - 20, eyeY + bodySize / 8, 40, 24, 180, 180);
                break;

            case GLOWING:
                // Sparkle eyes: two crossed short lines each (star-ish)
                drawSparkle(g2, leftEyeX, eyeY + eyeSize / 2, eyeSize);
                drawSparkle(g2, rightEyeX, eyeY + eyeSize / 2, eyeSize);
                // Big open smile
                g2.setStroke(new BasicStroke(3f));
                g2.drawArc(bodyX + bodySize / 2 - 26, eyeY + bodySize / 10, 52, 30, 180, 180);
                break;
        }
    }

    private void drawSparkle(Graphics2D g2, int cx, int cy, int size) {
        int r = size / 2 + 2;
        g2.drawLine(cx - r, cy, cx + r, cy);
        g2.drawLine(cx, cy - r, cx, cy + r);
    }
}
