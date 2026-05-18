import type { ReactNode } from 'react'

interface BadgeProps {
  children: ReactNode
  variant?: 'pending' | 'success' | 'error' | 'warning' | 'neutral'
}

const variantMap: Record<NonNullable<BadgeProps['variant']>, string> = {
  pending: 'bg-yellow-100 text-yellow-800',
  success: 'bg-green-100 text-green-800',
  error:   'bg-red-100 text-red-800',
  warning: 'bg-orange-100 text-orange-800',
  neutral: 'bg-surface-variant text-muted',
}

/** Small pill badge used for status labels like "Pendiente". */
export function Badge({ children, variant = 'neutral' }: BadgeProps) {
  return (
    <span className={`inline-flex items-center px-2.5 py-1 rounded text-xs font-semibold ${variantMap[variant]}`}>
      {children}
    </span>
  )
}
