interface ClassFilter {
  id: string
  name: string
}

interface ClassFilterChipsProps {
  classes: ClassFilter[]
  /** null = all classes */
  activeId: string | null
  onSelect: (id: string | null) => void
}

/**
 * Horizontally scrollable class filter chips.
 * Pressing the active chip again resets to "all classes" (null).
 */
export function ClassFilterChips({ classes, activeId, onSelect }: ClassFilterChipsProps) {
  return (
    <div
      className="flex gap-2 overflow-x-auto no-scrollbar"
      role="group"
      aria-label="Filtrar por clase"
    >
      {classes.map((cls) => {
        const isActive = cls.id === activeId
        return (
          <button
            key={cls.id}
            type="button"
            onClick={() => onSelect(isActive ? null : cls.id)}
            className={`shrink-0 px-4 py-2 rounded-full text-sm font-semibold transition-colors
              ${isActive
                ? 'bg-primary-container text-white'
                : 'bg-[#e1e2ed] text-on-surface hover:bg-[#d4d5e6]'}`}
            aria-pressed={isActive}
          >
            {cls.name}
          </button>
        )
      })}

      {/* Filters trigger */}
      <button
        type="button"
        className="shrink-0 flex items-center gap-1.5 px-3 py-2 rounded-full bg-[#e1e2ed] text-sm text-on-surface hover:bg-[#d4d5e6] transition-colors"
        aria-label="Abrir filtros avanzados"
      >
        <span className="material-symbols-outlined text-base" aria-hidden="true">
          filter_list
        </span>
        Filtros
      </button>
    </div>
  )
}
