import React from 'react';

interface DisabledOverlayProps {
  message?: string;
}

export function DisabledOverlay({ message = 'Endpoint no disponible. Funcionalidad deshabilitada.' }: DisabledOverlayProps) {
  return React.createElement(
    'div',
    { className: 'absolute inset-0 z-50 flex items-center justify-center pointer-events-auto' },
    [
      React.createElement('div', {
        key: 'backdrop',
        className: 'absolute inset-0 bg-white/70 backdrop-blur-sm'
      }),
      React.createElement('div', {
        key: 'content',
        className: 'relative z-10 max-w-md w-full p-4 rounded shadow bg-base-100 border'
      }, [
        React.createElement('p', {
          key: 'message',
          className: 'text-center text-sm text-muted-foreground'
        }, message)
      ])
    ]
  );
}
