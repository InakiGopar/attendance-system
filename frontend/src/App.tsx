import { useState } from 'react'
import { ProfessorPanelScreen }    from './components/screens/ProfessorPanelScreen'
import { StudentManagementScreen } from './components/screens/StudentManagementScreen'
import { AttendanceScreen }        from './components/screens/AttendanceScreen'
import type { NavTab }             from './types'

type ActiveScreen = 'professor-panel' | 'student-management' | 'attendance'

/**
 * Root app component — lightweight screen switcher.
 * A proper router (React Router / TanStack Router) can replace this later
 * without touching any of the screen components.
 */
function App() {
  const [screen, setScreen]       = useState<ActiveScreen>('professor-panel')
  const [activeTab, setActiveTab] = useState<NavTab>('classes')

  function handleNavigate(tab: NavTab) {
    setActiveTab(tab)
    if (tab === 'students') setScreen('student-management')
    else setScreen('professor-panel')
  }

  function handleStartAttendance() {
    setScreen('attendance')
  }

  function handleBack() {
    setScreen('professor-panel')
    setActiveTab('classes')
  }

  if (screen === 'attendance') {
    return (
      <AttendanceScreen
        onBack={handleBack}
        onNavigate={handleNavigate}
      />
    )
  }

  if (screen === 'student-management') {
    return <StudentManagementScreen onNavigate={handleNavigate} />
  }

  return (
    <ProfessorPanelScreen
      onNavigate={handleNavigate}
      onStartAttendance={handleStartAttendance}
    />
  )
}

export default App
