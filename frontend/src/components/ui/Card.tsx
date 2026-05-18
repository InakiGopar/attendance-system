import type { ReactNode } from 'react'

interface CardProps {
  children: ReactNode
  className?: string
}

/** White surface card with subtle border and rounded corners. */
export function Card({ children, className = '' }: CardProps) {
  return (
    <div className={`bg-white rounded-xl border border-outline p-4 ${className}`}>
      {children}
    </div>
  )
}
