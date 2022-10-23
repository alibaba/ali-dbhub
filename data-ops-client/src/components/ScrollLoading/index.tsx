import React, { memo, PropsWithChildren, useRef, useCallback, useState, useLayoutEffect, useEffect } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Loading from '@/components/Loading/Loading'

interface IProps {
  className?: string;
  children?: React.ReactChild; // 滚动的内容
  onReachBottom: () => Promise<unknown>; // 触底的数据请求
  threshold: number; // 触底阈值
  scrollerElement: HTMLElement; // overfollow：scroll 的盒子
  finished: boolean; // 是否结束
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
    finishedRef.current = finished
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
          setIsPadding(false);
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

  // 当数据没有出现滚动条时处理
  const replenishData = (a: HTMLElement, b: HTMLElement) => {
    if (a.clientHeight <= b.clientHeight && !finishedRef.current) {
      pendingRef.current = true;
      setIsPadding(true);
      onReachBottomRef.current().then(() => {
        pendingRef.current = false;
        setIsPadding(false);
        replenishData(a, b);
      });
    }
  }

  const onBoxMounted = useCallback((element: HTMLElement | null) => {
    if (element) {
      replenishData(element, scroller)
    }
  }, []);

  return <div ref={onBoxMounted} className={classnames(className, styles.box)}>
    {children}
    <>
      {isPending && <div className={styles.tips}>
        <Loading className={styles.loading}></Loading>
      </div>}
      {finishedRef.current && <div className={styles.tips}>----列表是有底线的----</div>}
    </>
  </div>
})
