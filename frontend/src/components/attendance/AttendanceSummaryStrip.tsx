import type { AttendanceSummary } from '../../types'

interface AttendanceSummaryStripProps {
  summary: AttendanceSummary
}

interface PillProps {
  icon: string
  color: string
  label: string
  count: number
}

/**
 * Color pills are static JSX hoisted outside the component (rendering-hoist-jsx rule).
 * The actual count value is the only dynamic part.
 */
function SummaryPill({ icon, color, label, count }: PillProps) {
  return (
    <div className={`flex items-center gap-1.5 px-3 py-2 rounded-full bg-white border ${color}`}>
      <span className={`material-symbols-outlined text-base ${color.replace('border-', 'text-')}`} aria-hidden="true">
        {icon}
      </span>
      <span className={`text-xs font-semibold ${color.replace('border-', 'text-')}`}>
        {label}: {count}
      </span>
    </div>
  )
}

/**
 * Live summary strip — re-renders only when summary values change.
 * Shows present (green), absent (red) and tardy (amber) counts.
 */
export function AttendanceSummaryStrip({ summary }: AttendanceSummaryStripProps) {
  return (
    <div
      className="flex gap-2 overflow-x-auto no-scrollbar px-4 py-3 bg-surface-variant"
      role="status"
      aria-live="polite"
      aria-label={`Resumen: ${summary.present} presentes, ${summary.absent} ausentes, ${summary.tardy} tardíos`}
    >
      <SummaryPill icon="check_circle" color="border-success text-success"  label="Presente" count={summary.present} />
      <SummaryPill icon="cancel"       color="border-error text-error"      label="Ausente"  count={summary.absent}  />
      <SummaryPill icon="schedule"     color="border-warning text-warning"  label="Tarde"    count={summary.tardy}   />
      {summary.pending > 0 && (
        <SummaryPill icon="pending" color="border-muted text-muted" label="Pendiente" count={summary.pending} />
      )}
    </div>
  )
}
