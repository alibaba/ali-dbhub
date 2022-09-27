import React, { memo, PropsWithChildren, useRef, useCallback, useState, useLayoutEffect, useEffect } from 'react';
import styles from './index.less';
import classnames from 'classnames';

interface IProps {
  className?: string;
  children?: React.ReactChild;
  onReachBottom: () => Promise<unknown>;
  threshold: number;
  scrollerElement?: HTMLElement;
  finished: boolean;
}

export default memo<IProps>(function ScrollLoading({ className, children, scrollerElement, threshold, onReachBottom, finished }) {
  const [autoScroller, setAutoScroller] = useState<HTMLElement | null>(null);
  const scroller = (scrollerElement || autoScroller);
  const scrollerRef = useRef(scroller);
  const pendingRef = useRef(false);
  const finishedRef = useRef(false);
  const onReachBottomRef = useRef(onReachBottom);

  useLayoutEffect(() => {
    scrollerRef.current = scroller;
  }, [scroller]);

  useLayoutEffect(() => {
    scrollerRef.current = scroller;
  }, [finished]);

  useLayoutEffect(() => {
    onReachBottomRef.current = onReachBottom;
  }, [onReachBottom]);

  const onScroll = useCallback(() => {
    if (finishedRef.current || pendingRef.current) {
      return
    }
    const scroller = scrollerRef.current;
    if (scroller) {
      if (scroller.scrollTop >= scroller.scrollHeight - scroller.clientHeight - threshold) {
        pendingRef.current = true;
        onReachBottomRef.current().then(() => {
          pendingRef.current = false;
        });
      }
    }
  }, []);

  useEffect(() => {
    window.addEventListener('scroll', onScroll, { capture: true });
    return () => {
      window.removeEventListener('scroll', onScroll, { capture: true });
    }
  }, [onScroll]);

  return <div className={classnames(className, styles.box)}>
    {children}
  </div>
})
