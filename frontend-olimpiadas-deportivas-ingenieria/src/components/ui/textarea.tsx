import * as React from "react";
import { cn } from "@/lib/utils";

// Minimal fallback props because custom react.d.ts removed detailed attribute typings.
interface TextareaProps {
  className?: string;
  value?: string;
  placeholder?: string;
  rows?: number;
  disabled?: boolean;
  onChange?: (e: any) => void; // narrowed manually in callers
}

const Textarea = React.forwardRef(function Textarea(
  { className, value, onChange, placeholder, rows, disabled }: TextareaProps,
  ref: any
) {
  return (
    <textarea
      className={cn(
        "flex min-h-[80px] w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50",
        className
      )}
      ref={ref}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      rows={rows}
      disabled={disabled}
    />
  );
});

export { Textarea };
