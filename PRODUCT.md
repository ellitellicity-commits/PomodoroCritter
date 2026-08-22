# Product

<!-- impeccable:product-schema 1 -->

## Platform

web

## Stack

Static HTML/CSS/JS frontend (no framework, no build/bundler step), served as static files by a Java backend using `com.sun.net.httpserver.HttpServer`. The frontend polls JSON endpoints (`/state`) and calls action endpoints (`/start`, `/pause`, `/reset`) via `fetch()`. No new dependencies beyond the JDK.

## Users

Primary user is the developer themself, using it personally as a focus/productivity tool. Built for personal convenience first; may be shared with other people in the future, but is not currently designed for multi-user or account-based use.

## Product Purpose

A Pomodoro-style focus timer that pairs work sessions with a virtual critter companion. Completing timed sessions advances the critter's mood, giving the user a lightweight, delightful sense of progress alongside the timer itself.

## Positioning

Most Pomodoro timers are purely functional (a countdown and a bell). PomodoroCritter adds a mood-reactive companion tied directly to completed session count, without turning into a full gamification/rewards system.

## Operating Context

Run locally as a single Java process (backend) with a browser tab open against it (frontend). Originally a Swing desktop app; being rewritten so the UI is HTML/CSS/JS while all logic stays in Java. This is also a personal learning project for practicing OOP in Java (the backend domain classes — Critter, Mood, SessionLog, PomodoroTimer — are hand-written by the user for that purpose and are treated as stable/authoritative).

## Capabilities and Constraints

- Session/mood/critter state is in-memory only; restarting the Java process resets session count and mood back to SLEEPY. No database or file persistence.
- Single shared timer state on the backend (no multi-session/multi-user concurrency to design for).
- Frontend is plain static HTML/CSS/JS: no React/Vue/bundler/npm build pipeline. Matches the "Java is the only backend language" constraint by keeping the frontend dependency-free too.
- Session length is user-selectable (e.g. a short test duration and standard 25/30-minute options) and must be set before starting a session.
- Four mood states drive the critter's appearance: SLEEPY, NEUTRAL, HAPPY, GLOWING, determined by cumulative completed session count.

## Brand Commitments

None yet — no existing name/logo/voice commitments beyond the working title "Pomodoro Critter" and the critter concept itself (an original Wii Mii-style ninja character, not based on existing IP).

## Evidence on Hand

None. No existing screenshots, copy, or brand assets beyond the Swing prototype's placeholder UI (which this rewrite replaces).

## Product Principles

- Keep the timer itself the primary, trustworthy mechanism — the critter is a reward layer, not a distraction or a guilt mechanic.
- Favor simplicity and directness (few dependencies, in-memory state, no accounts) over building for hypothetical future multi-user needs.
- Treat the hand-written Java domain classes as durable product logic; UI rewrites should adapt to them, not the other way around.

## Accessibility & Inclusion

No specific requirement established yet.
