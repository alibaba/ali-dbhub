import React, { memo, useRef, useEffect, useState, Fragment } from 'react';
import styles from './index.less';
import classnames from 'classnames';

interface IProps {
  className?: string;
  children: React.ReactNode[];
  volatileRef: any; // TODO：如何直接返回一个ref 这个ref的类型是什么
  min?: number;
  direction?: 'row' | 'line';
  callback?: Function;
}

export default memo<IProps>(function DraggableContainer({ children, callback, min, className, direction = 'line', volatileRef }) {

  const DividerRef = useRef<HTMLDivElement | null>(null);
  const DividerLine = useRef<HTMLDivElement | null>(null);
  const beforeTheLine = useRef<HTMLDivElement | null>(null);
  const afterTheLine = useRef<HTMLDivElement | null>(null);
  const [dragging, setDragging] = useState(false)
  useEffect(() => {
    if (DividerRef.current) {
      DividerRef.current.onmouseover = e => {
        setDragging(true);
      }
      DividerRef.current.onmouseout = e => {
        setDragging(false);
      }
      DividerRef.current.onmousedown = e => {
        setDragging(true);
        const clientStart = direction == 'line' ? e.clientX : e.clientY
        if (!volatileRef.current) return
        const volatileBoxXY = direction == 'line' ? volatileRef.current.offsetWidth : volatileRef.current.offsetHeight;
        e.preventDefault();
        document.onmousemove = e => {
          moveHandle(
            direction == 'line' ? e.clientX : e.clientY,
            volatileRef.current,
            clientStart,
            volatileBoxXY
          );
        };
        document.onmouseup = e => {
          setDragging(false)
          document.onmouseup = null;
          document.onmousemove = null;
        };
      };
    }
  }, [])

  const moveHandle = (nowClientXY: any, leftDom: any, clientStart: any, volatileBoxXY: any) => {
    let computedXY = nowClientXY - clientStart;
    let changeLength = volatileBoxXY + computedXY;
    if (min && changeLength < min) {
      return
    }
    if (direction == 'line') {
      leftDom.style.width = changeLength + "px";
    } else {
      leftDom.style.height = changeLength + "px";
    }
    callback && callback(changeLength);
  }

  return <div className={classnames(styles.box, className)}>
    {children[0]}
    <div
      ref={DividerLine}
      className={classnames(direction == 'line' ? styles.divider : styles.rowDivider, { [styles.displayDivider]: !children[1] })}
    >
      <div ref={DividerRef} className={classnames(
        styles.dividerCenter,
        { [styles.dragging]: dragging },
        { [styles.rowDragging]: (dragging && direction == 'row') }
      )} />
    </div>
    {children[1]}
  </div>
})
