import { useAttendance }            from '../../hooks/useAttendance'
import { BottomNav }                 from '../layout/BottomNav'
import { AttendanceSummaryStrip }    from '../attendance/AttendanceSummaryStrip'
import { AttendanceStudentRow }      from '../attendance/AttendanceStudentRow'
import type { NavTab }               from '../../types'

interface AttendanceScreenProps {
  onBack: () => void
  onNavigate: (tab: NavTab) => void
}

/**
 * Taking Attendance screen.
 *
 * Layout:
 *   ┌──────────────────────────────┐
 *   │  Back header + class info    │
 *   │  Student count badge         │
 *   ├──────────────────────────────┤
 *   │  Summary strip (sticky)      │
 *   ├──────────────────────────────┤
 *   │  Scrollable student list     │
 *   ├──────────────────────────────┤
 *   │  Save CTA                    │
 *   │  Bottom nav                  │
 *   └──────────────────────────────┘
 */
export function AttendanceScreen({ onBack, onNavigate }: AttendanceScreenProps) {
  const {
    session,
    records,
    summary,
    isSaving,
    savedAt,
    activeTab,
    setStatus,
    markAll,
    saveAttendance,
    setActiveTab,
  } = useAttendance()

  function handleTabChange(tab: NavTab) {
    setActiveTab(tab)
    onNavigate(tab)
  }

  const allMarked = summary.pending === 0

  return (
    <div className="flex flex-col h-dvh bg-surface-variant font-sans">

      {/* ── Header ─────────────────────────────────────────────────── */}
      <header className="flex items-center gap-3 px-4 h-16 bg-white border-b border-outline shrink-0">
        <button
          type="button"
          onClick={onBack}
          className="text-on-surface hover:text-primary-container transition-colors"
          aria-label="Volver al panel"
        >
          <span className="material-symbols-outlined text-2xl" aria-hidden="true">arrow_back</span>
        </button>

        <div className="flex-1 min-w-0">
          <h1 className="text-base font-bold text-on-surface truncate">{session.className}</h1>
          <p className="text-xs text-muted">{session.displayDate} • {session.group}</p>
        </div>

        {/* Student count badge */}
        <div className="w-9 h-9 rounded-full bg-primary-container flex items-center justify-center shrink-0">
          <span className="text-sm font-bold text-white">{summary.total}</span>
        </div>
      </header>

      {/* ── Sticky summary strip ────────────────────────────────────── */}
      <div className="shrink-0 border-b border-outline">
        <AttendanceSummaryStrip summary={summary} />
      </div>

      {/* ── Quick actions ───────────────────────────────────────────── */}
      <div className="flex gap-2 px-4 py-2 bg-white border-b border-outline shrink-0">
        <button
          type="button"
          onClick={() => markAll('present')}
          className="flex-1 text-xs font-semibold py-2 rounded-lg bg-green-50 text-success border border-success hover:bg-green-100 transition-colors"
        >
          Todos presentes
        </button>
        <button
          type="button"
          onClick={() => markAll(null)}
          className="flex-1 text-xs font-semibold py-2 rounded-lg bg-surface-variant text-muted border border-outline hover:bg-gray-100 transition-colors"
        >
          Limpiar todo
        </button>
      </div>

      {/* ── Scrollable student list ─────────────────────────────────── */}
      <main className="flex-1 overflow-y-auto">
        <ul className="flex flex-col gap-2 p-4" aria-label="Lista de asistencia">
          {session.students.map((student) => (
            <AttendanceStudentRow
              key={student.id}
              student={student}
              status={records.get(student.id) ?? null}
              onSetStatus={setStatus}
            />
          ))}
        </ul>
      </main>

      {/* ── Save CTA ────────────────────────────────────────────────── */}
      <div className="px-4 pt-3 pb-2 bg-white border-t border-outline shrink-0">
        {savedAt && (
          <p className="text-center text-xs text-success mb-2 flex items-center justify-center gap-1">
            <span className="material-symbols-outlined text-sm" aria-hidden="true">check_circle</span>
            Guardado a las {savedAt}
          </p>
        )}
        {!allMarked && (
          <p className="text-center text-xs text-warning mb-2">
            {summary.pending} alumno{summary.pending !== 1 ? 's' : ''} sin marcar
          </p>
        )}
        <button
          type="button"
          onClick={saveAttendance}
          disabled={isSaving}
          className="w-full h-11 rounded-xl bg-primary-container text-white text-sm font-semibold flex items-center justify-center gap-2 transition-opacity disabled:opacity-60 active:scale-[0.98]"
          aria-label="Guardar asistencia"
        >
          {isSaving ? (
            <>
              <span className="material-symbols-outlined text-lg animate-spin" aria-hidden="true">progress_activity</span>
              Guardando…
            </>
          ) : (
            <>
              <span className="material-symbols-outlined text-lg" aria-hidden="true">save</span>
              Guardar Asistencia
            </>
          )}
        </button>
      </div>

      {/* ── Bottom nav ──────────────────────────────────────────────── */}
      <BottomNav active={activeTab} onChange={handleTabChange} />
    </div>
  )
}
