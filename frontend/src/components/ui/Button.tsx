import type { ButtonHTMLAttributes, ReactNode } from 'react'

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'ghost'
  children: ReactNode
  fullWidth?: boolean
}

/** Primary action button matching the design's filled blue CTA style. */
export function Button({
  variant = 'primary',
  fullWidth = false,
  className = '',
  children,
  ...rest
}: ButtonProps) {
  const base = 'inline-flex items-center justify-center gap-2 rounded-md text-sm font-semibold h-11 px-4 transition-opacity active:opacity-80 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-container'
  const variants = {
    primary: 'bg-primary-container text-white',
    ghost:   'bg-transparent text-primary-container',
  }

  return (
    <button
      type="button"
      className={`${base} ${variants[variant]} ${fullWidth ? 'w-full' : ''} ${className}`}
      {...rest}
    >
      {children}
    </button>
  )
}
