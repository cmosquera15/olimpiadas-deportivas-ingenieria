import { NavLink as RouterNavLink, NavLinkProps } from "react-router-dom";
import { forwardRef } from 'react';
import { cn } from "@/lib/utils";

interface NavLinkCompatProps extends Omit<NavLinkProps, "className"> {
  className?: string;
  activeClassName?: string;
  pendingClassName?: string;
}

const NavLink = forwardRef<HTMLAnchorElement, NavLinkCompatProps>((props, ref) => {
  const { className, activeClassName, pendingClassName, to, ...rest } = props;
  return (
    <RouterNavLink
      ref={ref}
      to={to}
      className={({ isActive, isPending }) =>
        cn(className, isActive && activeClassName, isPending && pendingClassName)
      }
      {...rest}
    />
  );
});

const NavLinkWithDisplay = NavLink as typeof NavLink & { displayName?: string };
NavLinkWithDisplay.displayName = "NavLink";

export { NavLinkWithDisplay as NavLink };
