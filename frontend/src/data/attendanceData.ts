import type { AttendanceSession } from '../types'

/**
 * Seed session matching the "Toma de Asistencia" Stitch design.
 * In production this would be fetched from the API keyed by classId + date.
 */
export const attendanceSession: AttendanceSession = {
  classId:     'math-101',
  className:   'Matemáticas 101',
  group:       'Grupo A',
  date:        '2024-10-24',
  displayDate: 'Lunes, 24 Oct',
  students: [
    {
      id:          'att-s1',
      displayId:   '1002234',
      firstName:   'Juan',
      lastName:    'Pérez',
      initials:    'JP',
      avatarColor: '#94a3b8',
      classId:     'math-101',
      className:   'Matemáticas 101',
    },
    {
      id:          'att-s2',
      displayId:   '1002235',
      firstName:   'Maria',
      lastName:    'García',
      initials:    'MG',
      avatarColor: '#f59e0b',
      classId:     'math-101',
      className:   'Matemáticas 101',
    },
    {
      id:          'att-s3',
      displayId:   '1002236',
      firstName:   'Carlos',
      lastName:    'Ruiz',
      initials:    'CR',
      avatarColor: '#ef4444',
      classId:     'math-101',
      className:   'Matemáticas 101',
    },
    {
      id:          'att-s4',
      displayId:   '1002237',
      firstName:   'Lucia',
      lastName:    'Martín',
      initials:    'LM',
      avatarColor: '#8b5cf6',
      classId:     'math-101',
      className:   'Matemáticas 101',
    },
  ],
}
