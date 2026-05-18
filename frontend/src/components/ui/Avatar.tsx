interface AvatarProps {
  initials: string
  /** Hex or Tailwind-compatible color string */
  color?: string
  size?: 'sm' | 'md' | 'lg'
}

const sizeMap = {
  sm: 'w-7 h-7 text-[11px]',
  md: 'w-10 h-10 text-sm',
  lg: 'w-9 h-9 text-sm',
}

/** Round avatar with initials, used for teachers and student previews. */
export function Avatar({ initials, color = '#3557c7', size = 'md' }: AvatarProps) {
  return (
    <div
      className={`${sizeMap[size]} rounded-full flex items-center justify-center font-bold text-white shrink-0`}
      style={{ backgroundColor: color }}
      aria-label={initials}
    >
      {initials}
    </div>
  )
}
