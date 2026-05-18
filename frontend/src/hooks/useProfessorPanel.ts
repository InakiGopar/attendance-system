import { useState } from 'react'
import { professorPanelData } from '../data/professorPanel'
import type { NavTab, ProfessorPanelData } from '../types'

interface UseProfessorPanelReturn {
  data: ProfessorPanelData
  activeClassId: string
  activeTab: NavTab
  setActiveClassId: (id: string) => void
  setActiveTab: (tab: NavTab) => void
  handleStartAttendance: () => void
  handleAddNote: () => void
}

/**
 * Encapsulates all UI state for the Professor Panel screen.
 * Data fetching / mutations can be added here without touching the view layer.
 */
export function useProfessorPanel(): UseProfessorPanelReturn {
  const [activeClassId, setActiveClassId] = useState(professorPanelData.activeClass.id)
  const [activeTab, setActiveTab] = useState<NavTab>('classes')

  function handleStartAttendance() {
    // TODO: navigate to attendance-taking screen
    console.info('[ProfessorPanel] Starting attendance for', activeClassId)
  }

  function handleAddNote() {
    // TODO: open add-note modal
    console.info('[ProfessorPanel] Opening add-note sheet')
  }

  return {
    data: professorPanelData,
    activeClassId,
    activeTab,
    setActiveClassId,
    setActiveTab,
    handleStartAttendance,
    handleAddNote,
  }
}
