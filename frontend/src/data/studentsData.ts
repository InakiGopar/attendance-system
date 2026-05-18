import type { StudentManagementData } from '../types'
import { professorPanelData } from './professorPanel'

/** Seed data matching the "Gestión de Alumnos" Stitch design. */
export const studentManagementData: StudentManagementData = {
  teacher: professorPanelData.teacher,

  classes: [
    { id: 'math-4a',    name: 'Matemáticas 4ºA' },
    { id: 'physics-5b', name: 'Física 5ºB'       },
  ],

  students: [
    {
      id:          's1',
      displayId:   '48921',
      firstName:   'Ana',
      lastName:    'García López',
      initials:    'AG',
      avatarColor: '#22c55e',
      classId:     'math-4a',
      className:   'Matemáticas 4ºA',
    },
    {
      id:          's2',
      displayId:   '48922',
      firstName:   'Carlos',
      lastName:    'Martínez',
      initials:    'CM',
      avatarColor: '#f59e0b',
      classId:     'physics-5b',
      className:   'Física 5ºB',
    },
    {
      id:          's3',
      displayId:   '48925',
      firstName:   'Laura',
      lastName:    'Rodríguez',
      initials:    'LR',
      avatarColor: '#3557c7',
      classId:     'math-4a',
      className:   'Matemáticas 4ºA',
    },
  ],
}
