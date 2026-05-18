import { Avatar } from '../ui/Avatar'
import type { Teacher } from '../../types'

interface TopBarProps {
  teacher: Teacher
  /** Override background colour (defaults to primary-container blue) */
  className?: string
}

/**
 * Top application bar with branding on the left and teacher avatar on the right.
 * Shared by Panel del Profesor and Gestión de Alumnos screens.
 */
export function TopBar({ teacher, className = '' }: TopBarProps) {
  return (
    <header
      className={`flex items-center justify-between px-4 h-16 bg-primary-container ${className}`}
    >
      <div className="flex flex-col gap-0.5">
        <span className="text-base font-bold text-white">EduTrack Pro</span>
        <span className="text-[11px] text-blue-200">{teacher.institution}</span>
      </div>

      <div className="flex items-center gap-2">
        <Avatar initials={teacher.initials} color="#1a3c8f" size="sm" />
        <span className="text-sm text-white">{teacher.name}</span>
      </div>
    </header>
  )
}
