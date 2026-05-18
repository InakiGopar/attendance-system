interface AddStudentFabProps {
  onClick: () => void
}

/**
 * Floating Action Button — fixed to the bottom-right of the scroll area.
 * Positioned with `absolute` inside a `relative` wrapper on the screen.
 */
export function AddStudentFab({ onClick }: AddStudentFabProps) {
  return (
    <button
      type="button"
      onClick={onClick}
      className="absolute bottom-20 right-4 w-14 h-14 rounded-2xl bg-primary-container text-white shadow-lg flex items-center justify-center hover:opacity-90 active:scale-95 transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-container"
      aria-label="Añadir alumno"
    >
      <span className="material-symbols-outlined text-3xl" aria-hidden="true">add</span>
    </button>
  )
}
