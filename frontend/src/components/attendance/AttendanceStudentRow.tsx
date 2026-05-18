import { memo } from 'react'
import { Avatar } from '../ui/Avatar'
import type { AttendanceStatus, Student } from '../../types'

interface AttendanceStudentRowProps {
  student: Student
  status: AttendanceStatus
  onSetStatus: (studentId: string, status: AttendanceStatus) => void
}

// ─── Status button config (hoisted — never recreated on re-render) ─────────────

interface StatusConfig {
  value: NonNullable<AttendanceStatus>
  label: string
  shortLabel: string
  activeClass: string
  inactiveClass: string
}

const STATUS_CONFIGS: StatusConfig[] = [
  {
    value:         'present',
    label:         'Presente',
    shortLabel:    'P',
    activeClass:   'bg-success text-white',
    inactiveClass: 'bg-white text-success border border-success hover:bg-green-50',
  },
  {
    value:         'absent',
    label:         'Ausente',
    shortLabel:    'A',
    activeClass:   'bg-error text-white',
    inactiveClass: 'bg-white text-error border border-error hover:bg-red-50',
  },
  {
    value:         'tardy',
    label:         'Tarde',
    shortLabel:    'T',
    activeClass:   'bg-warning text-white',
    inactiveClass: 'bg-white text-warning border border-warning hover:bg-amber-50',
  },
]

/**
 * A single student row with togglable P / A / T buttons.
 *
 * Wrapped in React.memo — the parent list only re-renders the rows whose
 * status actually changed (rerender-memo rule).
 * onSetStatus is stable (useCallback in hook) so memo comparisons work correctly.
 */
export const AttendanceStudentRow = memo(function AttendanceStudentRow({
  student,
  status,
  onSetStatus,
}: AttendanceStudentRowProps) {
  const fullName = `${student.firstName} ${student.lastName}`

  return (
    <li className="flex items-center gap-3 bg-white rounded-xl border border-outline px-4 py-3">
      {/* Avatar */}
      <Avatar initials={student.initials} color={student.avatarColor} size="md" />

      {/* Name + ID */}
      <div className="flex-1 min-w-0">
        <p className="text-[15px] font-semibold text-on-surface truncate">{fullName}</p>
        <p className="text-xs text-muted">ID: {student.displayId}</p>
      </div>

      {/* P / A / T toggles */}
      <div className="flex items-center gap-1.5 shrink-0" role="group" aria-label={`Estado de asistencia de ${fullName}`}>
        {STATUS_CONFIGS.map((cfg) => {
          const isActive = status === cfg.value
          return (
            <button
              key={cfg.value}
              type="button"
              onClick={() =>
                // Toggle off if already active (sets to null = pending)
                onSetStatus(student.id, isActive ? null : cfg.value)
              }
              aria-pressed={isActive}
              aria-label={cfg.label}
              className={`w-9 h-9 rounded-lg text-sm font-bold transition-all active:scale-95 ${
                isActive ? cfg.activeClass : cfg.inactiveClass
              }`}
            >
              {cfg.shortLabel}
            </button>
          )
        })}
      </div>
    </li>
  )
})
