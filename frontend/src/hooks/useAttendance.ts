import { useCallback, useMemo, useReducer, useState } from 'react'
import { attendanceSession } from '../data/attendanceData'
import type {
  AttendanceSession,
  AttendanceStatus,
  AttendanceSummary,
  NavTab,
} from '../types'

// ─── State shape ───────────────────────────────────────────────────────────────

/** Keyed by studentId for O(1) lookup (js-set-map-lookups rule) */
type RecordsMap = Map<string, AttendanceStatus>

interface AttendanceState {
  records: RecordsMap
  isSaving: boolean
  savedAt: string | null
}

// ─── Reducer ───────────────────────────────────────────────────────────────────

type Action =
  | { type: 'SET_STATUS'; studentId: string; status: AttendanceStatus }
  | { type: 'MARK_ALL'; status: AttendanceStatus }
  | { type: 'SAVING' }
  | { type: 'SAVED'; savedAt: string }

function buildInitialRecords(session: AttendanceSession): RecordsMap {
  // Pre-populate from the design: Juan=present, Maria=absent, Carlos=present, Lucia=pending
  const preset: Record<string, AttendanceStatus> = {
    'att-s1': 'present',
    'att-s2': 'absent',
    'att-s3': 'present',
    'att-s4': null,
  }
  return new Map(session.students.map((s) => [s.id, preset[s.id] ?? null]))
}

function reducer(state: AttendanceState, action: Action): AttendanceState {
  switch (action.type) {
    case 'SET_STATUS': {
      // Immutably update the Map by creating a new one (rerender-functional-setstate pattern)
      const next = new Map(state.records)
      next.set(action.studentId, action.status)
      return { ...state, records: next, savedAt: null }
    }
    case 'MARK_ALL': {
      const next = new Map(state.records)
      for (const key of next.keys()) next.set(key, action.status)
      return { ...state, records: next, savedAt: null }
    }
    case 'SAVING':
      return { ...state, isSaving: true }
    case 'SAVED':
      return { ...state, isSaving: false, savedAt: action.savedAt }
    default:
      return state
  }
}

// ─── Hook ──────────────────────────────────────────────────────────────────────

interface UseAttendanceReturn {
  session: AttendanceSession
  records: RecordsMap
  summary: AttendanceSummary
  isSaving: boolean
  savedAt: string | null
  activeTab: NavTab
  setStatus: (studentId: string, status: AttendanceStatus) => void
  markAll: (status: AttendanceStatus) => void
  saveAttendance: () => Promise<void>
  setActiveTab: (tab: NavTab) => void
}

/**
 * Owns all attendance-taking state and logic.
 *
 * Key rules applied:
 * - useReducer instead of multiple useStates (rerender-split-combined-hooks)
 * - summary is derived with useMemo, never stored (rerender-derived-state)
 * - setStatus and markAll wrapped in useCallback for stable references (rerender-memo rule)
 */
export function useAttendance(): UseAttendanceReturn {
  const [state, dispatch] = useReducer(reducer, undefined, () => ({
    records:  buildInitialRecords(attendanceSession),
    isSaving: false,
    savedAt:  null,
  }))

  const [activeTab, setActiveTab] = useState<NavTab>('classes')

  // Derive summary from records — O(n) single pass (js-combine-iterations rule)
  const summary = useMemo<AttendanceSummary>(() => {
    let present = 0, absent = 0, tardy = 0, pending = 0
    for (const status of state.records.values()) {
      if (status === 'present') present++
      else if (status === 'absent') absent++
      else if (status === 'tardy') tardy++
      else pending++
    }
    return { present, absent, tardy, pending, total: state.records.size }
  }, [state.records])

  const setStatus = useCallback((studentId: string, status: AttendanceStatus) => {
    dispatch({ type: 'SET_STATUS', studentId, status })
  }, [])

  const markAll = useCallback((status: AttendanceStatus) => {
    dispatch({ type: 'MARK_ALL', status })
  }, [])

  const saveAttendance = useCallback(async () => {
    dispatch({ type: 'SAVING' })

    // Serialize for the API
    const payload = Object.fromEntries(state.records)
    console.info('[Attendance] Saving payload', payload)

    // TODO: replace with real API call → await api.post('/attendance', payload)
    await new Promise((r) => setTimeout(r, 800))

    dispatch({ type: 'SAVED', savedAt: new Date().toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' }) })
  }, [state.records])

  return {
    session:         attendanceSession,
    records:         state.records,
    summary,
    isSaving:        state.isSaving,
    savedAt:         state.savedAt,
    activeTab,
    setStatus,
    markAll,
    saveAttendance,
    setActiveTab,
  }
}
