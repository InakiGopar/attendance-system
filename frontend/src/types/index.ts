// ─── Domain models ─────────────────────────────────────────────────────────────

export interface Teacher {
  id: string
  name: string
  initials: string
  institution: string
}

export interface CourseClass {
  id: string
  name: string
  room: string
  startTime: string
  endTime: string
  enrolledCount: number
  /** Hex avatar colors for the first few students */
  sampleAvatars: { initials: string; color: string }[]
}

export interface QuickNote {
  id: string
  text: string
  severity: 'warning' | 'info'
  addedAt: string
}

export interface DaySummary {
  classesHeld: number
  totalClasses: number
  presentCount: number | null
  absentCount: number | null
}

export interface NextClass {
  id: string
  name: string
  startTime: string
  endTime: string
  room: string
}

export interface ProfessorPanelData {
  teacher: Teacher
  semesterWeek: number
  activeClass: CourseClass
  sideClasses: CourseClass[]
  notes: QuickNote[]
  daySummary: DaySummary
  nextClass: NextClass
}

// ─── Students ──────────────────────────────────────────────────────────────────

export interface Student {
  id: string
  /** Display ID shown in the UI, e.g. "48921" */
  displayId: string
  firstName: string
  lastName: string
  /** initials derived from name */
  initials: string
  /** Hex background color for the avatar */
  avatarColor: string
  /** ID of the class this student belongs to */
  classId: string
  className: string
}

export interface StudentManagementData {
  teacher: Teacher
  students: Student[]
  classes: Pick<CourseClass, 'id' | 'name'>[]
}

// ─── Nav ───────────────────────────────────────────────────────────────────────

export type NavTab = 'classes' | 'students' | 'reports' | 'settings'

// ─── App-level screen routing ──────────────────────────────────────────────────

export type Screen = 'professor-panel' | 'student-management'

// ─── Attendance ────────────────────────────────────────────────────────────────

/** null = not yet marked */
export type AttendanceStatus = 'present' | 'absent' | 'tardy' | null

export interface AttendanceRecord {
  studentId: string
  status: AttendanceStatus
}

export interface AttendanceSession {
  /** Matches CourseClass.id */
  classId: string
  className: string
  group: string
  date: string
  /** e.g. "Lunes, 24 Oct" */
  displayDate: string
  students: Student[]
}

/** Derived — computed from record Map, never stored as state */
export interface AttendanceSummary {
  present: number
  absent: number
  tardy: number
  pending: number
  total: number
}
