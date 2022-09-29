import React, { memo, PropsWithChildren, useRef, useCallback, useState, useLayoutEffect, useEffect } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Loading from '@/components/Loading/Loading'

interface IProps {
  className?: string;
  children?: React.ReactChild;
  onReachBottom: () => Promise<unknown>;
  threshold: number;
  scrollerElement: HTMLElement;
  finished: boolean;
}

export default memo<IProps>(function ScrollLoading({ className, children, scrollerElement, threshold, onReachBottom, finished }) {
  const scroller = scrollerElement;
  const scrollerRef = useRef(scroller);
  const pendingRef = useRef(false);
  const finishedRef = useRef(false);
  const onReachBottomRef = useRef(onReachBottom);
  const [isPending, setIsPadding] = useState(false);

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
        setIsPadding(true)
        onReachBottomRef.current().then(() => {
          pendingRef.current = false;
          setIsPadding(false)
        });
      }
    }
  }, []);

  useEffect(() => {
    if (scrollerRef.current) {
      scrollerRef.current.addEventListener('scroll', onScroll);
      return () => {
        scrollerRef.current.removeEventListener('scroll', onScroll);
      }
    }
  }, [onScroll]);

  return <div className={classnames(className, styles.box)}>
    {children}
    {
      isPending && <div className={styles.tips}>
        <Loading className={styles.loading}></Loading>
      </div>
    }
    {
      finishedRef.current && <div className={styles.tips}>----列表是有底线的----</div>
    }
  </div>
})
