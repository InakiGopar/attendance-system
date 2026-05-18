import { Card } from '../ui/Card'
import type { NextClass } from '../../types'

interface NextClassCardProps {
  nextClass: NextClass
}

/**
 * Next class preview card shown at the bottom of the Professor Panel.
 */
export function NextClassCard({ nextClass }: NextClassCardProps) {
  return (
    <Card className="flex flex-col gap-3">
      <h3 className="text-sm font-bold text-on-surface">Próxima Clase</h3>

      <div className="flex items-center gap-3">
        <div className="w-10 h-10 rounded-lg bg-surface-variant flex items-center justify-center shrink-0">
          <span className="material-symbols-outlined text-primary-container text-xl" aria-hidden="true">
            science
          </span>
        </div>
        <div className="flex flex-col gap-0.5">
          <span className="text-sm font-semibold text-on-surface">{nextClass.name}</span>
          <span className="text-xs text-muted">{nextClass.startTime} - {nextClass.endTime}</span>
        </div>
      </div>

      <div className="inline-flex items-center gap-1.5 bg-surface-variant rounded-lg px-3 py-2 self-start">
        <span className="text-xs text-on-surface">{nextClass.room}</span>
      </div>
    </Card>
  )
}
