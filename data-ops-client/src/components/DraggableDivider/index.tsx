import React, { memo, useRef, useEffect, useState} from 'react';
import styles from './index.less';
import classnames from 'classnames';

interface IProps {
  className?: string;
  volatileRef: any; // TODO：如何直接返回一个ref 这个ref的类型是什么
  min?: number;
  direction?: 'row' | 'line';
  callback?: Function;
}

export default memo<IProps>(function DraggableDivider({className, volatileRef, min,direction='line',callback}) {

  const DividerRef = useRef<HTMLDivElement | null>(null);
  const DividerLine = useRef<HTMLDivElement | null>(null);
  const [dragging, setDragging] = useState(false)

  useEffect(()=>{
    if(DividerRef.current){

      DividerRef.current.onmouseover = e => {
        if(DividerRef.current && DividerLine.current){
          DividerLine.current.style.backgroundColor =  'var(--custom-primary-color)';
          DividerLine.current.style.cursor =  direction == 'line' ?  'col-resize' : 'row-resize';
        }
      }
      
      DividerRef.current.onmouseout = e => {
        if(DividerRef.current && DividerLine.current){
          DividerLine.current.style.backgroundColor =  'transparent';
          DividerLine.current.style.cursor =  'default';
        }
      }

      DividerRef.current.onmousedown = e => {
        setDragging(true)
        const clientStart = direction == 'line' ? e.clientX : e.clientY
        if(!volatileRef.current) return
        const volatileBoxXY = direction == 'line' ? volatileRef.current.offsetWidth : volatileRef.current.offsetHeight;
        e.preventDefault();
        document.onmousemove = e => {
          moveHandle(
            direction == 'line' ? e.clientX : e.clientY,
            volatileRef.current,
            clientStart,
            volatileBoxXY);
        };
        document.onmouseup = e => {
          setDragging(false)
          document.onmouseup = null;
          document.onmousemove = null;
        };
      };
    }
  },[])

  const moveHandle = (nowClientXY, letfDom, clientStart, volatileBoxXY) =>{
    let computedXY = nowClientXY - clientStart;
    let changeLength = volatileBoxXY + computedXY;
    if(min && changeLength < min){
      return
    }
    if(direction == 'line'){
      letfDom.style.width = changeLength + "px";
    }else{
      letfDom.style.height = changeLength + "px";
    }
    callback && callback(changeLength)
  }

  return <div ref={DividerLine}  className={
    classnames(
      className, 
      (direction == 'line' ? styles.divider : styles.rowDivider),
      {[styles.dragging]: dragging},
      {[styles.rowDragging]: (dragging && direction == 'row')},
    )} >
    <div ref={DividerRef} className={styles.dividerCenter}></div>
    </div>
})
