import { useCallback,  useRef, useState } from 'react';

export function useDebounce<A extends any[]>(callback: (...args: A) => void, timeout: number) {
  const timer = useRef<any>();
  return useCallback<(...args: A) => void>((...args) => {
    if (timer.current) {
      clearTimeout(timer.current);
      timer.current = undefined;
    }
    timer.current = setTimeout(() => {
      callback(...args);
      timer.current = undefined;
    }, timeout);
  }, [callback, timeout]);
}