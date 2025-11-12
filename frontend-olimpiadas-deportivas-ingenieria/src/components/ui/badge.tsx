// Minimal badge component without relying on full React typings
// Avoid React.createElement since custom react.d.ts does not expose it
import { type VariantProps } from "class-variance-authority";
import { cn } from "@/lib/utils";
import { badgeVariants } from "./badge-variants";

export interface BadgeProps extends VariantProps<typeof badgeVariants> {
  className?: string;
  // children kept broad: accept string or number or JSX-like nodes
  children?: string | number | (string | number | null | undefined)[];
  title?: string;
}

export function Badge(props: BadgeProps) {
  const { className, variant, children, title } = props;
  return <div className={cn(badgeVariants({ variant }), className)} title={title}>{children}</div>;
}
