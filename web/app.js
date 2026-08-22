const POLL_INTERVAL_MS = 500;
const MIN_SESSION_MINUTES = 1;
const MAX_SESSION_MINUTES = 180;

const critterEl = document.querySelector('.critter');
const moodLabelEl = document.getElementById('mood-label');
const timerReadoutEl = document.getElementById('timer-readout');
const sessionCountEl = document.getElementById('session-count');
const qualifyingMinutesEl = document.getElementById('qualifying-minutes');
const startBtn = document.getElementById('start-btn');
const pauseBtn = document.getElementById('pause-btn');
const resetBtn = document.getElementById('reset-btn');
const lengthValueEl = document.getElementById('length-value');
const lengthDecBtn = document.getElementById('length-dec');
const lengthIncBtn = document.getElementById('length-inc');

let latestState = null;
let sessionLengthInitialized = false;
let customMinutes = 25;

function formatTime(totalSeconds) {
  const minutes = Math.floor(totalSeconds / 60);
  const seconds = totalSeconds % 60;
  return String(minutes).padStart(2, '0') + ':' + String(seconds).padStart(2, '0');
}

function moodDisplayName(mood) {
  return mood.charAt(0) + mood.slice(1).toLowerCase();
}

// Retriggers the element's gentle "tick" easing animation, used whenever a
// piece of displayed state actually changes so updates feel eased in
// rather than cutting instantly.
function pulse(el) {
  el.classList.remove('tick');
  void el.offsetWidth;
  el.classList.add('tick');
}

function render(state) {
  const previousState = latestState;
  latestState = state;

  const mood = state.mood.toLowerCase();
  critterEl.setAttribute('data-mood', mood);
  critterEl.setAttribute('aria-label', 'Critter is ' + mood);

  moodLabelEl.textContent = 'Mood: ' + moodDisplayName(state.mood);
  timerReadoutEl.textContent = formatTime(state.remainingSeconds);
  sessionCountEl.textContent = 'Sessions completed: ' + state.sessionCount;
  qualifyingMinutesEl.textContent = 'Focus minutes: ' + state.qualifyingMinutes;

  if (previousState) {
    if (previousState.remainingSeconds !== state.remainingSeconds) pulse(timerReadoutEl);
    if (previousState.mood !== state.mood) pulse(moodLabelEl);
    if (previousState.sessionCount !== state.sessionCount) pulse(sessionCountEl);
    if (previousState.qualifyingMinutes !== state.qualifyingMinutes) pulse(qualifyingMinutesEl);
  }

  startBtn.disabled = state.isRunning;
  pauseBtn.disabled = !state.isRunning;
  lengthDecBtn.disabled = state.isRunning;
  lengthIncBtn.disabled = state.isRunning;

  // Reflect the server's current session length in the stepper exactly once,
  // on the very first render (page load), so a reload shows the length
  // actually in effect. Every later poll must leave it alone, otherwise a
  // background poll can silently overwrite a choice the user just made but
  // hasn't submitted yet via Start.
  if (!sessionLengthInitialized) {
    sessionLengthInitialized = true;
    customMinutes = Math.round(state.sessionLength / 60);
    lengthValueEl.textContent = customMinutes;
  }
}

async function fetchState() {
  const response = await fetch('/state');
  const state = await response.json();
  render(state);
}

async function callAction(path, body) {
  const response = await fetch(path, {
    method: 'POST',
    headers: body ? { 'Content-Type': 'application/json' } : undefined,
    body: body ? JSON.stringify(body) : undefined,
  });
  const state = await response.json();
  render(state);
}

function resolveSessionSeconds() {
  return Math.round(customMinutes * 60);
}

startBtn.addEventListener('click', () => {
  const isFreshSession = !latestState || latestState.remainingSeconds === latestState.sessionLength;
  if (isFreshSession) {
    callAction('/start', { seconds: resolveSessionSeconds() });
  } else {
    callAction('/start');
  }
});

pauseBtn.addEventListener('click', () => {
  callAction('/pause');
});

resetBtn.addEventListener('click', () => {
  callAction('/reset');
});

lengthDecBtn.addEventListener('click', () => {
  customMinutes = Math.max(MIN_SESSION_MINUTES, customMinutes - 1);
  lengthValueEl.textContent = customMinutes;
});

lengthIncBtn.addEventListener('click', () => {
  customMinutes = Math.min(MAX_SESSION_MINUTES, customMinutes + 1);
  lengthValueEl.textContent = customMinutes;
});

fetchState();
setInterval(fetchState, POLL_INTERVAL_MS);
