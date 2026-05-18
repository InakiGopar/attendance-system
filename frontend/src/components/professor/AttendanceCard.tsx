import { Avatar } from '../ui/Avatar'
import { Badge } from '../ui/Badge'
import { Button } from '../ui/Button'
import { Card } from '../ui/Card'
import type { CourseClass } from '../../types'

interface AttendanceCardProps {
  courseClass: CourseClass
  onStart: () => void
}

/**
 * The prominent "pending attendance" card on the Professor Panel.
 * Shows class details, a sample avatar row, and the CTA to start attendance.
 */
export function AttendanceCard({ courseClass, onStart }: AttendanceCardProps) {
  const OVERFLOW_COUNT = courseClass.enrolledCount - courseClass.sampleAvatars.length

  return (
    <Card className="flex flex-col gap-3">
      {/* Card header: status + folder icon */}
      <div className="flex items-center justify-between">
        <Badge variant="pending">Pendiente</Badge>
        <span className="material-symbols-outlined text-muted text-xl" aria-hidden="true">
          folder_open
        </span>
      </div>

      {/* Class info */}
      <h2 className="text-base font-bold text-on-surface">
        Asistencia: {courseClass.name}
      </h2>
      <p className="text-sm text-muted">
        Horario: {courseClass.startTime} - {courseClass.endTime} • {courseClass.room}
      </p>

      {/* Avatar row + enrolled count */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-1">
          {courseClass.sampleAvatars.map((av) => (
            <Avatar key={av.initials} initials={av.initials} color={av.color} size="sm" />
          ))}
          {OVERFLOW_COUNT > 0 && (
            <div className="w-7 h-7 rounded-full bg-surface-variant flex items-center justify-center text-[9px] font-bold text-muted">
              +{OVERFLOW_COUNT}
            </div>
          )}
        </div>
        <span className="text-xs text-muted">{courseClass.enrolledCount} alumnos inscritos</span>
      </div>

      {/* CTA */}
      <Button fullWidth onClick={onStart}>
        <span className="material-symbols-outlined text-lg" aria-hidden="true">how_to_reg</span>
        Iniciar Toma de Asistencia
      </Button>
    </Card>
  )
}
