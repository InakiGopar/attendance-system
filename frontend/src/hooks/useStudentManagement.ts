import { useMemo, useState } from 'react'
import { studentManagementData } from '../data/studentsData'
import type { NavTab, Student, StudentManagementData } from '../types'

interface UseStudentManagementReturn {
  data: StudentManagementData
  /** null = "all classes" */
  activeClassId: string | null
  searchQuery: string
  activeTab: NavTab
  filteredStudents: Student[]
  setActiveClassId: (id: string | null) => void
  setSearchQuery: (q: string) => void
  setActiveTab: (tab: NavTab) => void
  handleEdit: (student: Student) => void
  handleDelete: (student: Student) => void
  handleAddStudent: () => void
}

/**
 * Encapsulates all state for the Student Management screen.
 * Filtering is derived in useMemo — no redundant state (rerender-derived-state rule).
 */
export function useStudentManagement(): UseStudentManagementReturn {
  const [activeClassId, setActiveClassId] = useState<string | null>('math-4a')
  const [searchQuery, setSearchQuery]     = useState('')
  const [activeTab, setActiveTab]         = useState<NavTab>('students')

  // Derive filtered list from search + class filter — never store duplicated state
  const filteredStudents = useMemo(() => {
    const q = searchQuery.toLowerCase().trim()

    return studentManagementData.students.filter((s) => {
      const matchesClass = activeClassId === null || s.classId === activeClassId
      const fullName = `${s.firstName} ${s.lastName}`.toLowerCase()
      const matchesSearch = q === '' || fullName.includes(q) || s.displayId.includes(q)
      return matchesClass && matchesSearch
    })
  }, [activeClassId, searchQuery])

  function handleEdit(student: Student) {
    // TODO: open edit-student modal
    console.info('[StudentManagement] Edit', student.id)
  }

  function handleDelete(student: Student) {
    // TODO: confirm-delete dialog
    console.info('[StudentManagement] Delete', student.id)
  }

  function handleAddStudent() {
    // TODO: open add-student modal
    console.info('[StudentManagement] Add student')
  }

  return {
    data: studentManagementData,
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
  }
}
