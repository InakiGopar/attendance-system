import type { ProfessorPanelData } from '../types'

/** Seed data matching the Stitch "Panel del Profesor" design */
export const professorPanelData: ProfessorPanelData = {
  teacher: {
    id: 'smith',
    name: 'Dr. Smith',
    initials: 'DS',
    institution: 'Instituto San Martín',
  },
  semesterWeek: 14,
  activeClass: {
    id: 'math-4a',
    name: 'Matemáticas 4ºA',
    room: 'Aula 102',
    startTime: '08:00',
    endTime: '09:30',
    enrolledCount: 31,
    sampleAvatars: [
      { initials: 'A', color: '#22c55e' },
      { initials: 'MI', color: '#ef4444' },
      { initials: 'SF', color: '#3557c7' },
    ],
  },
  sideClasses: [
    {
      id: 'physics-5b',
      name: 'Física 5ºB',
      room: 'Aula Laboratorio 2',
      startTime: '10:00',
      endTime: '11:30',
      enrolledCount: 28,
      sampleAvatars: [],
    },
  ],
  notes: [
    {
      id: 'n1',
      text: 'Recordar justificación médica de Sofía Pérez.',
      severity: 'warning',
      addedAt: 'Añadido ayer, 16:45',
    },
    {
      id: 'n2',
      text: 'Hoy toca revisión del Capítulo 4.',
      severity: 'info',
      addedAt: 'Añadido hoy, 07:10',
    },
  ],
  daySummary: {
    classesHeld: 0,
    totalClasses: 3,
    presentCount: null,
    absentCount: null,
  },
  nextClass: {
    id: 'physics-5b',
    name: 'Física 5ºB',
    startTime: '10:00',
    endTime: '11:30',
    room: 'Aula Laboratorio 2',
  },
}
