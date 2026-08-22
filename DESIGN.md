---
name: Pomodoro Critter
description: A cream-and-blush Pomodoro timer with a mood-reactive ninja critter companion
colors:
  cream-ground: "#fbf1e3"
  cream-ground-deep: "#f3e3cd"
  card-surface: "#fffcf6"
  ink: "#5a4a44"
  ink-soft: "#7a6960"
  blush-accent: "#f5c7d3"
  blush-accent-deep: "#e79cae"
  blush-ink: "#7a4152"
  label-outline: "#3b2a55"
  mood-sleepy: "#c9c8e8"
  mood-sleepy-deep: "#a6a4d4"
  mood-neutral: "#bfe3ec"
  mood-neutral-deep: "#8fcbda"
  mood-happy: "#bfedc9"
  mood-happy-deep: "#8fdba4"
  mood-glowing: "#ffdca0"
  mood-glowing-deep: "#ffc670"
  reset-shadow-deep: "#d8c4a8"
  sky-lavender: "#e6def5"
  sky-blush: "#f4e2dc"
  sky-peach: "#fbe6cc"
  critter-skin: "#ffe0c4"
  shadow-ambient: "rgba(122, 90, 70, 0.14)"
  shadow-ambient-highlight: "rgba(255, 255, 255, 0.6)"
  shadow-disabled: "rgba(0, 0, 0, 0.08)"
  ground-shadow: "rgba(59, 42, 85, 0.2)"
  divider-line: "rgba(59, 42, 85, 0.12)"
  card-texture-dot: "rgba(59, 42, 85, 0.035)"
  stage-dot: "rgba(255, 255, 255, 0.4)"
typography:
  scale:
    title: "1.6rem"
    title-compact: "1.4rem"
    body: "1rem"
    button: "0.95rem"
    caption: "0.9rem"
    label-sm: "0.85rem"
    fine-print: "0.78rem"
    zzz-md: "1.15rem"
    zzz-lg: "1.5rem"
    readout: "3.4rem"
    readout-compact: "2.8rem"
    stepper-value: "1.25rem"
  label:
    fontFamily: "'Titan One', ui-rounded, 'SF Pro Rounded', 'Trebuchet MS', sans-serif"
    fontWeight: 400
    letterSpacing: "0.01em"
    outline: "0.8px solid {colors.label-outline} (title: 1.6px)"
    shadow: "1.5px 1.5px 0 {colors.label-outline} (title: 3px 3px 0; hard offset, no blur, same color as outline)"
    appliesTo: "title, mood label, buttons only — the session-length label uses the bubble font with no outline/shadow"
  readout:
    fontFamily: "'Nunito', -apple-system, BlinkMacSystemFont, 'Segoe UI', Helvetica, Arial, sans-serif"
    fontWeight: 700
    fontSize: "3.4rem"
    lineHeight: 1
rounded:
  md: "16px"
  lg: "28px"
  pill: "999px"
spacing:
  sm: "8px"
  md: "16px"
  lg: "24px"
breakpoints:
  sm: "480px"
  md: "768px"
components:
  button-start:
    backgroundColor: "{colors.mood-happy}"
    textColor: "{colors.blush-ink}"
    rounded: "{rounded.pill}"
    padding: "12px 20px"
  button-pause:
    backgroundColor: "{colors.mood-neutral}"
    textColor: "{colors.blush-ink}"
    rounded: "{rounded.pill}"
    padding: "12px 20px"
  button-reset:
    backgroundColor: "{colors.cream-ground-deep}"
    textColor: "{colors.blush-ink}"
    rounded: "{rounded.pill}"
    padding: "12px 20px"
  session-length-stepper:
    backgroundColor: "{colors.cream-ground-deep}"
    textColor: "{colors.ink}"
    rounded: "{rounded.pill}"
    padding: "5px"
  session-length-stepper-button:
    backgroundColor: "{colors.mood-sleepy}"
    textColor: "{colors.label-outline}"
    rounded: "{rounded.pill}"
    size: "42px (38px under sm breakpoint)"
---

# Design System: Pomodoro Critter

## Overview

**Creative North Star: "The Napping Dojo"**

A single quiet card on a warm cream ground, like a paper training mat: a small ninja critter naps or glows on it depending on how much focused work has actually happened. Nothing about the surface performs urgency — no red countdowns, no progress bars, no streak pressure. The reward for finishing a session is one step up a fixed four-stage mood ladder (SLEEPY → NEUTRAL → HAPPY → GLOWING), rendered in locked pastel colors straight from `Mood.java`, never invented per-render.

The critter now naps inside its own scene rather than a flat panel: a lavender-to-peach sky gradient, a faint dot-texture wash, a few gently twinkling stars, and a soft ground shadow under its feet. The card itself carries a matching (much subtler) dot texture, so the "paper training mat" reads as slightly textured stock, not a flat color swatch.

Two type registers do two different jobs on purpose: bubble-letter type (Titan One) on every label and control that is *about* the app's personality — the title, mood label, buttons — and plain legible type (Nunito, tabular numerals) on the one piece of information that has to be read at a glance under time pressure: the countdown itself. The countdown is never bubbly; the chrome around it always is. Titan One is set with a heavy stroke and a hard offset shadow, both in the same ink color (`label-outline`), for a solid bubble-outline / sticker look — see Typography below.

**Key Characteristics:**
- Cream ground (`cream-ground`), single elevated card (`card-surface`) with a subtle dot texture, no secondary panels.
- The critter stage is its own lavender-to-peach sky scene, not a flat cream panel.
- Mood color is the only thing that changes on the critter; body shape, ninja band, and cheek blush stay constant across all four states.
- Buttons read as soft pressable pills (flat color fill + a solid offset "drop" shadow, not blur) — a tactile, slightly toy-like press, not a flat web-app button.
- Only `GLOWING` gets a soft radial halo behind the critter; the other three states are flat.
- A thin divider line and three small dots mark section breaks (after the stats row, and between the button row and the session-length control), giving the single-column stack some visual breathing room without adding panels.

## Colors

Restrained pastel strategy: one neutral cream field, one accent (blush), and four *locked* mood colors that are product logic, not decoration — they must always map 1:1 to `Mood.SLEEPY/NEUTRAL/HAPPY/GLOWING` and never get reassigned for a redesign without updating the Java enum's meaning too.

### Primary
- **Cream Ground** (`#fbf1e3` / `cream-ground`): page background, radial-blended into `#f3e3cd` toward the top.
- **Card Surface** (`#fffcf6` / `card-surface`): the single content card, barely lighter than the ground so it reads as paper laid on the mat, not a hard panel.
- **Ink** (`#5a4a44` / `ink`): primary text — warm charcoal-brown, never pure black.
- **Ink Soft** (`#7a6960` / `ink-soft`): secondary text (session count, length label). Contrast-checked at 5.1:1 on `card-surface`.

### Secondary
- **Blush Accent** (`#f5c7d3` / `blush-accent`): decorative accent — ninja knot, focal touches.
- **Blush Ink** (`#7a4152` / `blush-ink`): text color on light pastel fills (title, mood label, button labels).
- **Label Outline** (`#3b2a55` / `label-outline`): sampled directly from the critter PNGs' own baked-in line art (eyes, mouth — identical across all four mood frames). Used as the single stroke+shadow color for bubble type, and as the sleepy mouth-pulse overlay's fill, so hand-drawn linework and CSS-drawn linework read as the same ink.
- **Critter Skin** (`#ffe0c4` / `critter-skin`): sampled from the critter PNGs' own face fill. Used only by the sleepy mouth-pulse overlay's cover patch, which masks the baked-in closed-mouth line so it doesn't peek out from behind the smaller animated circle.
- **Sky Lavender / Blush / Peach** (`#e6def5` / `#f4e2dc` / `#fbe6cc`): the three-stop gradient behind the critter stage.

### Mood Colors (locked to `Mood.java`, never used for anything else)
- **Sleepy** `#c9c8e8` / deep `#a6a4d4` — muted periwinkle.
- **Neutral** `#bfe3ec` / deep `#8fcbda` — soft sky blue.
- **Happy** `#bfedc9` / deep `#8fdba4` — soft mint.
- **Glowing** `#ffdca0` / deep `#ffc670`, halo `rgba(255,214,130,0.5)` — warm apricot with a radial glow ring.

## Typography

Two roles, deliberately not shared:

- **`label`** (Titan One): the app title, the mood label, and all three buttons — the "bubble-letter" register. Rendered with `-webkit-text-stroke` plus a hard-offset `text-shadow`, both in `label-outline`, giving a solid bubble-outline/sticker look (title: 1.6px stroke / 3px offset; mood label and buttons: 0.8px stroke / 1.5px offset). The session-length label also uses Titan One for its playful character but carries no stroke/shadow, so it reads as a quieter sub-label rather than a fourth outlined element competing with the title.
- **`readout`** (Nunito, 700 weight, `font-variant-numeric: tabular-nums`): the countdown, and the session-length stepper's numeric value. Plain, legible, digits don't shift width as they change.

Both fall back gracefully to system rounded/system-ui stacks when the Google Fonts request is unavailable (offline); no functionality depends on the webfont loading.

## Layout

Single centered card, `max-width: 400px`, generous internal padding (28px, tightening at the `sm`/`md` breakpoints — see below). Everything stacks vertically in one column — critter stage, mood label, countdown, session count, dot accents, button row (3 across), divider, session-length stepper. No sidebar, no multi-column state at any width.

**Breakpoints** (`sm: 480px`, `md: 768px`): under `md`, card padding tightens slightly. Under `sm`, the title and countdown fonts step down, button padding/gap tighten, and the stepper narrows — keeping the three-button row and the stepper pill from crowding or wrapping on phone-width screens. Above `md` the layout is unchanged from desktop; the card never grows past its 400px max-width, so laptop/desktop windows just add more cream margin around it.

## Elevation & Depth

Flat, not layered. The card lifts off the page with one soft ambient shadow (`0 18px 40px rgba(122,90,70,0.14)`) plus a 1px inset highlight, not a shadow scale, plus a barely-there dot texture (`card-texture-dot`, 22px grid) so the surface reads as textured stock rather than a flat fill. Buttons use a solid 4px "offset" shadow in a darker shade of their own fill (not a blur) to read as a pressable pill; pressing a button drops it 2px and halves the offset. This is structural (communicates "pressable"), not ambient.

## Shapes

Everything rounds toward a pill: buttons and the session-length stepper (and its two circular +/- controls) are fully pill/circle-shaped (`rounded.pill`, 999px), the card and critter stage use a large soft radius (`rounded.lg` 28px / `rounded.md` 16px). No sharp corners anywhere — matches the critter's own rounded-body language.

## Components

- **Buttons** (`button-start`, `button-pause`, `button-reset`): pill shape, bubble type, solid offset shadow. Each carries its own tint (Start = happy mint, Pause = neutral blue, Reset = neutral cream) rather than one generic "primary" color, so the three actions stay visually distinct without needing icons. Disabled state drops opacity to 0.45 and flattens the shadow instead of graying the fill.
- **Critter** (four raster frames, `exports/critter-{mood}.png`): finalized hand-painted sprites, one per mood, stacked and crossfaded by opacity, driven purely by `[data-mood]` on a wrapper element, staged inside the sky-gradient critter scene. Adds a continuous idle bob, a periodic all-mood blink, a glowing-only pulsing halo layered over the art's own baked-in glow, and — sleepy only — a `critter-skin`-colored patch that masks the baked-in closed-mouth line, topped with a mouth-pulse overlay that cycles between a small and larger circle, alternating with the drifting Zzz text like a slow snore (see `.impeccable/design.json`'s `critter.motion`). Adding a fifth mood means adding one more frame and one more `[data-mood="x"]` opacity rule — never branching in JS.
- **Session length stepper** (`session-length-stepper`, `session-length-stepper-button`): a pill containing a circular "−" button, the current minute value (Nunito, tabular), and a circular "+" button. Replaces the old bare number input — same underlying value (1–180 minutes, whole-minute steps), same disabled-while-running behavior, but the affordance is now tap/click-driven rather than typed. The circle buttons reuse `mood-sleepy` as their fill, tying the control visually back to the critter's own palette.

## Motion

All four moods' keyframe animations and their trigger rules live in `web/animations.css`, separate from `web/style.css`'s structural/visual rules — see that file's own section comments for the per-mood breakdown (shared "general" animations, SLEEPY, the shared awake blink, HAPPY, GLOWING). `web/style.css` keeps only `transition`s (crossfades, hover/press states) and the static "which frame is showing" rules.

## Do's and Don'ts

- **Do** keep the four mood colors locked 1:1 to `Mood.java`'s enum order. **Don't** repurpose them decoratively elsewhere in the UI — a mood color appearing outside the critter/mood context would break the signal.
- **Do** keep bubble type off the countdown, always. **Don't** let "make it cuter" pressure creep bubble styling onto the one number that has to be read under time pressure.
- **Do** use the four finalized `critter-{mood}.png` sprites as the only critter art — one hand-painted illustration per locked mood, crossfaded, never recolored or filtered per-render. **Don't** introduce photographic rendering or arbitrary hue shifts for the critter — the illustration style and the four locked mood palettes are the whole language.
- **Don't** add a shadow/blur scale to the buttons; the solid offset "drop" is the whole tactile idea, and a soft blur shadow would make them read like generic web buttons instead of pressable pills.
- **Do** keep `label-outline` as the single stroke+shadow color for bubble type — it's sampled from the critter art itself, not an arbitrary pick. **Don't** reintroduce a two-tone stroke/shadow (separate outline and highlight colors); the current look is a deliberate monochrome bubble-outline, not the earlier sticker-highlight style.
