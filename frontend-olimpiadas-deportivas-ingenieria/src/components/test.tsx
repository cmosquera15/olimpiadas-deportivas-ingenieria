import React from 'react';

export function TestComponent() {
  const [count, setCount] = React.useState(0);
  return React.createElement(
    'div',
    null,
    React.createElement(
      'button',
      { onClick: () => setCount((c: number) => c + 1) },
      `Count: ${count}`
    )
  );
}