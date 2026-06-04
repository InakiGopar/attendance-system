import { useEffect, useState } from 'react'
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
  const [isLoading, setIsLoading] = useState<boolean>(true);
  (async () => {
    fetch('http://localhost:8080/users', {credentials: 'include'}) // credentials: 'include' es vital para enviar la cookie
      .then(response => {
        if (response.status === 401) {
            // El usuario no está autenticado. Redirigimos la ventana completa al flujo OAuth2
            window.location.href = "http://localhost:8080/oauth2/authorization/google";
            console.log("401")
        } else {
            return response.json();
        }
      })
      .then(data => {
        console.log(data)
        data === undefined ? null : setIsLoading(false);
      })
      .catch(error => console.error(error))
  })();

  console.log(isLoading)

  if (isLoading) return <h1>Loading...</h1>

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
