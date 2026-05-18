import { Avatar } from '../ui/Avatar'
import type { Student } from '../../types'

interface StudentListItemProps {
  student: Student
  onEdit: (student: Student) => void
  onDelete: (student: Student) => void
}

/**
 * A single student row: avatar + name/id/class + action icons.
 * Extracted as a pure presentational component so the parent list
 * can be virtualised later without changing this component.
 */
export function StudentListItem({ student, onEdit, onDelete }: StudentListItemProps) {
  const fullName = `${student.firstName} ${student.lastName}`

  return (
    <li className="flex items-center gap-3 bg-white rounded-xl border border-outline px-4 py-3">
      {/* Avatar */}
      <Avatar initials={student.initials} color={student.avatarColor} size="md" />

      {/* Info */}
      <div className="flex-1 min-w-0">
        <p className="text-[15px] font-semibold text-on-surface truncate">{fullName}</p>
        <p className="text-xs text-muted truncate">
          ID: {student.displayId} • {student.className}
        </p>
      </div>

      {/* Actions */}
      <div className="flex items-center gap-3 shrink-0">
        <button
          type="button"
          onClick={() => onEdit(student)}
          className="text-muted hover:text-on-surface transition-colors"
          aria-label={`Editar a ${fullName}`}
        >
          <span className="material-symbols-outlined text-xl" aria-hidden="true">edit</span>
        </button>
        <button
          type="button"
          onClick={() => onDelete(student)}
          className="text-error hover:opacity-75 transition-opacity"
          aria-label={`Eliminar a ${fullName}`}
        >
          <span className="material-symbols-outlined text-xl" aria-hidden="true">delete</span>
        </button>
      </div>
    </li>
  )
}
