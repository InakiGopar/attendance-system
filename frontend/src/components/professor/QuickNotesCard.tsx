import { Card } from '../ui/Card'
import type { QuickNote } from '../../types'

interface QuickNotesCardProps {
  courseClassName: string
  notes: QuickNote[]
  onAddNote: () => void
}

const SEVERITY_ICON: Record<QuickNote['severity'], { icon: string; color: string }> = {
  warning: { icon: 'warning', color: 'text-error' },
  info:    { icon: 'info',    color: 'text-primary-container' },
}

/**
 * Quick notes card — lists teacher notes with severity icons and timestamps.
 */
export function QuickNotesCard({ courseClassName, notes, onAddNote }: QuickNotesCardProps) {
  return (
    <Card className="flex flex-col gap-3">
      {/* Section header */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-1.5">
          <span className="material-symbols-outlined text-on-surface text-lg" aria-hidden="true">
            edit_note
          </span>
          <h3 className="text-sm font-semibold text-on-surface">
            Notas Rápidas ({courseClassName})
          </h3>
        </div>
      </div>

      {/* Notes list */}
      <ul className="flex flex-col gap-3">
        {notes.map((note) => {
          const { icon, color } = SEVERITY_ICON[note.severity]
          return (
            <li key={note.id} className="flex items-start gap-2">
              <span
                className={`material-symbols-outlined text-base mt-0.5 shrink-0 ${color}`}
                aria-hidden="true"
              >
                {icon}
              </span>
              <div className="flex flex-col gap-0.5">
                <p className="text-sm text-on-surface">{note.text}</p>
                <span className="text-xs text-muted">{note.addedAt}</span>
              </div>
            </li>
          )
        })}
      </ul>

      <button
        type="button"
        onClick={onAddNote}
        className="text-sm text-primary-container text-left font-medium hover:underline"
      >
        + Añadir nota
      </button>
    </Card>
  )
}
