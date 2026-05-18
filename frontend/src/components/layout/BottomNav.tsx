import type { NavTab } from '../../types'

interface NavItem {
  id: NavTab
  label: string
  /** Material Symbols codepoint name */
  icon: string
}

const NAV_ITEMS: NavItem[] = [
  { id: 'classes',  label: 'Clases',   icon: 'menu_book'  },
  { id: 'students', label: 'Alumnos',  icon: 'group'      },
  { id: 'reports',  label: 'Reportes', icon: 'bar_chart'  },
  { id: 'settings', label: 'Ajustes',  icon: 'settings'   },
]

interface BottomNavProps {
  active: NavTab
  onChange: (tab: NavTab) => void
}

/**
 * Sticky bottom navigation bar shared by all main screens.
 * Uses Material Symbols Outlined via Google Fonts ligature trick.
 */
export function BottomNav({ active, onChange }: BottomNavProps) {
  return (
    <nav
      className="flex items-center justify-around border-t border-outline bg-white h-16 px-2 shrink-0"
      aria-label="Navegación principal"
    >
      {NAV_ITEMS.map((item) => {
        const isActive = item.id === active
        return (
          <button
            key={item.id}
            type="button"
            onClick={() => onChange(item.id)}
            className={`flex flex-col items-center gap-1 px-4 py-2 rounded-lg transition-colors
              ${isActive ? 'text-primary-container' : 'text-muted hover:text-on-surface'}`}
            aria-current={isActive ? 'page' : undefined}
          >
            <span
              className="material-symbols-outlined text-2xl leading-none"
              aria-hidden="true"
            >
              {item.icon}
            </span>
            <span className="text-[10px] font-medium">{item.label}</span>
          </button>
        )
      })}
    </nav>
  )
}
