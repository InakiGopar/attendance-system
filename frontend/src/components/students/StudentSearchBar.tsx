interface StudentSearchBarProps {
  value: string
  onChange: (value: string) => void
}

/**
 * Search input with a leading search icon.
 * Uses a controlled pattern — parent owns the value via the hook.
 */
export function StudentSearchBar({ value, onChange }: StudentSearchBarProps) {
  return (
    <div className="flex items-center gap-2 bg-white border border-outline rounded-lg px-3 h-11">
      <span
        className="material-symbols-outlined text-muted text-xl shrink-0"
        aria-hidden="true"
      >
        search
      </span>
      <input
        id="student-search"
        type="search"
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder="Buscar por nombre o ID..."
        className="flex-1 bg-transparent text-sm text-on-surface placeholder:text-muted outline-none"
        aria-label="Buscar alumno"
      />
    </div>
  )
}
