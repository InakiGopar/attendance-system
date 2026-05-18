import { Card } from '../ui/Card'
import type { DaySummary } from '../../types'

interface DaySummaryCardProps {
  summary: DaySummary
}

interface CounterBoxProps {
  value: number | null
  label: string
}

function CounterBox({ value, label }: CounterBoxProps) {
  return (
    <div className="flex-1 flex flex-col items-center gap-1 bg-surface-variant rounded-lg py-4">
      <span className="text-2xl font-bold text-on-surface">
        {value ?? '--'}
      </span>
      <span className="text-[10px] font-semibold text-muted tracking-wide">{label}</span>
    </div>
  )
}

/**
 * Day summary card — shows classes held and present/absent counters.
 * CounterBox is kept co-located because it's not used anywhere else.
 */
export function DaySummaryCard({ summary }: DaySummaryCardProps) {
  return (
    <Card className="flex flex-col gap-3">
      <h3 className="text-sm font-bold text-on-surface">Resumen del Día</h3>

      <div className="flex items-center justify-between text-sm">
        <span className="text-muted">Clases Impartidas</span>
        <span className="font-semibold text-on-surface">
          {summary.classesHeld} de {summary.totalClasses}
        </span>
      </div>

      <div className="flex gap-3">
        <CounterBox value={summary.presentCount} label="PRESENTES" />
        <CounterBox value={summary.absentCount}  label="AUSENTES"  />
      </div>
    </Card>
  )
}
