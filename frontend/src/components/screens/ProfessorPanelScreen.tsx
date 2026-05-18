import { useProfessorPanel } from '../../hooks/useProfessorPanel'
import { TopBar }         from '../layout/TopBar'
import { BottomNav }      from '../layout/BottomNav'
import { AttendanceCard } from '../professor/AttendanceCard'
import { QuickNotesCard } from '../professor/QuickNotesCard'
import { DaySummaryCard } from '../professor/DaySummaryCard'
import { NextClassCard }  from '../professor/NextClassCard'
import type { NavTab }   from '../../types'

interface ProfessorPanelScreenProps {
  onNavigate: (tab: NavTab) => void
  onStartAttendance: () => void
}

/**
 * Professor Panel screen — top-level screen component.
 * Layout: TopBar → scrollable content → BottomNav (sticky).
 * All state lives in useProfessorPanel to keep this file declarative.
 */
export function ProfessorPanelScreen({ onNavigate, onStartAttendance }: ProfessorPanelScreenProps) {
  const {
    data,
    activeClassId,
    activeTab,
    setActiveClassId,
    setActiveTab,
    handleAddNote,
  } = useProfessorPanel()

  const allClasses = [data.activeClass, ...data.sideClasses]
  const displayedClass = allClasses.find((c) => c.id === activeClassId) ?? data.activeClass

  function handleTabChange(tab: NavTab) {
    setActiveTab(tab)
    onNavigate(tab)
  }

  return (
    <div className="flex flex-col h-dvh bg-surface-variant font-sans">
      {/* ── Top bar ──────────────────────────────────────────────── */}
      <TopBar teacher={data.teacher} />

      {/* ── Scrollable body ──────────────────────────────────────── */}
      <main className="flex-1 overflow-y-auto">
        {/* Greeting + semester chip */}
        <section className="px-4 pt-4 pb-3 flex flex-col gap-1">
          <p className="text-sm text-muted">¡Buenos días, {data.teacher.name.split(' ').pop()}!</p>
          <h1 className="text-xl font-bold text-on-surface">Hoy, 24 de Mayo</h1>
          <div className="flex items-center gap-1.5 bg-[#ededf9] rounded-full px-3 py-1.5 w-fit mt-1">
            <span className="material-symbols-outlined text-primary-container text-base" aria-hidden="true">
              calendar_month
            </span>
            <span className="text-xs text-primary-container font-medium">
              Semana {data.semesterWeek} del Semestre
            </span>
          </div>
        </section>

        {/* Class selector tabs */}
        <nav
          className="flex gap-2 px-4 pb-3 overflow-x-auto no-scrollbar"
          aria-label="Selector de clase"
        >
          {allClasses.map((c) => {
            const isActive = c.id === activeClassId
            return (
              <button
                key={c.id}
                type="button"
                onClick={() => setActiveClassId(c.id)}
                className={`shrink-0 px-4 py-2 rounded-full text-sm font-semibold transition-colors
                  ${isActive
                    ? 'bg-primary-container text-white'
                    : 'bg-[#e1e2ed] text-on-surface'}`}
                aria-current={isActive ? 'true' : undefined}
              >
                {c.name}
              </button>
            )
          })}
        </nav>

        {/* Cards */}
        <div className="flex flex-col gap-3 px-4 pb-6">
          <AttendanceCard
            courseClass={displayedClass}
            onStart={onStartAttendance}
          />
          <QuickNotesCard
            courseClassName={displayedClass.name}
            notes={data.notes}
            onAddNote={handleAddNote}
          />
          <DaySummaryCard summary={data.daySummary} />
          <NextClassCard nextClass={data.nextClass} />
        </div>
      </main>

      {/* ── Bottom nav ───────────────────────────────────────────── */}
      <BottomNav active={activeTab} onChange={handleTabChange} />
    </div>
  )
}
