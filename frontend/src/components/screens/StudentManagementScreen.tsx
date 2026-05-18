import { useStudentManagement }  from '../../hooks/useStudentManagement'
import { TopBar }               from '../layout/TopBar'
import { BottomNav }            from '../layout/BottomNav'
import { StudentSearchBar }     from '../students/StudentSearchBar'
import { ClassFilterChips }     from '../students/ClassFilterChips'
import { StudentListItem }      from '../students/StudentListItem'
import { AddStudentFab }        from '../students/AddStudentFab'
import type { NavTab }          from '../../types'

interface StudentManagementScreenProps {
  /** Called when the user navigates away via the bottom nav */
  onNavigate: (tab: NavTab) => void
}

/**
 * Student Management screen — lists, searches and filters students per class.
 * Layout: TopBar → scrollable content (search + chips + list) → sticky BottomNav.
 * FAB sits absolutely above the nav.
 */
export function StudentManagementScreen({ onNavigate }: StudentManagementScreenProps) {
  const {
    data,
    activeClassId,
    searchQuery,
    activeTab,
    filteredStudents,
    setActiveClassId,
    setSearchQuery,
    setActiveTab,
    handleEdit,
    handleDelete,
    handleAddStudent,
  } = useStudentManagement()

  function handleTabChange(tab: NavTab) {
    setActiveTab(tab)
    onNavigate(tab)
  }

  return (
    // `relative` creates the positioning context for the FAB
    <div className="relative flex flex-col h-dvh bg-surface-variant font-sans">
      {/* ── Top bar ────────────────────────────────────────────── */}
      <TopBar teacher={data.teacher} />

      {/* ── Scrollable body ────────────────────────────────────── */}
      <main className="flex-1 overflow-y-auto">
        {/* Sticky search + filter toolbar */}
        <div className="sticky top-0 z-10 bg-surface-variant px-4 pt-4 pb-3 flex flex-col gap-3">
          <StudentSearchBar value={searchQuery} onChange={setSearchQuery} />
          <ClassFilterChips
            classes={data.classes}
            activeId={activeClassId}
            onSelect={setActiveClassId}
          />
        </div>

        {/* Student list */}
        {filteredStudents.length > 0 ? (
          <ul className="flex flex-col gap-2 px-4 pb-24" aria-label="Lista de alumnos">
            {filteredStudents.map((student) => (
              <StudentListItem
                key={student.id}
                student={student}
                onEdit={handleEdit}
                onDelete={handleDelete}
              />
            ))}
          </ul>
        ) : (
          <div className="flex flex-col items-center justify-center gap-2 py-16 text-muted">
            <span className="material-symbols-outlined text-5xl" aria-hidden="true">
              person_search
            </span>
            <p className="text-sm">No se encontraron alumnos</p>
          </div>
        )}
      </main>

      {/* ── FAB (floats above nav) ──────────────────────────────── */}
      <AddStudentFab onClick={handleAddStudent} />

      {/* ── Bottom nav ─────────────────────────────────────────── */}
      <BottomNav active={activeTab} onChange={handleTabChange} />
    </div>
  )
}
