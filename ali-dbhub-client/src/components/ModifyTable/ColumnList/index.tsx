import React, { memo, useDebugValue, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { Button, DatePicker, Input, Table, Modal, message } from 'antd';
import { createTableRows } from '@/components/TableColumns'
import Iconfont from '@/components/Iconfont';
import DraggingRow from '@/components/DraggingRow';

interface IProps {
  className?: string;
}
interface IRow {
  index: number;
  state: string;
  columnName: string,
  type: string,
  length: number,
  unNull: boolean,
  comment: string
}

const dataSourceMock = [
  {
    index: 1,
    state: '新增1',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: 100,
    unNull: true,
    comment: '我是注释'
  },
  {
    index: 2,
    state: '新增2',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: 100,
    unNull: true,
    comment: '我是注释'
  },
  {
    index: 3,
    state: '新增3',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: 100,
    unNull: true,
    comment: '我是注释'
  },
  {
    index: 4,
    state: '新增4',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: 100,
    unNull: true,
    comment: '我是注释'
  },
  {
    index: 5,
    state: '新增5',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: 100,
    unNull: true,
    comment: '我是注释'
  },
  {
    index: 6,
    state: '新增6',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: 100,
    unNull: true,
    comment: '我是注释'
  },
  {
    index: 7,
    state: '新增7',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: 100,
    unNull: true,
    comment: '我是注释'
  },
]
const CategoryLineHeight = 46;

export default memo<IProps>(function ColumnList({ className }) {
  const [dataSource, setDataSource] = useState<IRow[]>(dataSourceMock);
  const [dragIndex, setDragIndex] = useState<number>();
  const [currentDragMovePx, setCurrentDragMovePx] = useState<string>();
  const [dragedIndex, setDragedIndex] = useState<number>();

  function renderDrag(t: IRow, i: number) {
    return <div>{
      <Iconfont code="&#xe611;" onMouseDown={onMouseDown(t, i)} />
    }</div>
  }

  function onMouseDown(t: IRow, i: number) {
    return (e: React.MouseEvent) => {
      setDragIndex(i)
      const start = e.clientY
      const mask = document.createElement('div');
      const { style } = mask;
      style.position = 'fixed';
      style.left = style.right = style.top = style.bottom = '0';
      style.zIndex = '9999999';
      style.cursor = 'move';
      function check(e: MouseEvent) {
        const { clientY } = e;
        const px = start - clientY;
        // 移动边界判断
        if (i === 0 && (start - clientY) > 0) return false
        if (i === dataSource.length && (start - clientY) < 0) return false
        setCurrentDragMovePx(`${-(px)}`);
        const absoluteValue = px > 0 ? px : -px
        if (absoluteValue / CategoryLineHeight > 0.5) {
          const target = px > 0 ? i - Math.round(absoluteValue / CategoryLineHeight) : i + Math.round(absoluteValue / CategoryLineHeight)
          setDragedIndex(target)
          return target
        } else {
          setDragedIndex(dragIndex)
          return dragIndex!
        }
      }

      mask.addEventListener('mousemove', e => {
        check(e);
      });

      mask.addEventListener('mouseup', e => {
        const target = check(e);
        if (target !== false) {
          const newList = [...dataSource!];
          const item = newList.splice(i, 1)[0];
          newList.splice(target, 0, item);
          setDataSource(newList)
        }
        document.body.removeChild(mask);
        setCurrentDragMovePx('0px')
        setDragIndex(undefined)
        setDragedIndex(undefined)
      });

      document.body.appendChild(mask);
    }
  }

  const { Header: TableHeader, Row: TableRow } = createTableRows<IRow>([
    {
      name: '拖动', baseWidth: 50, renderCell: (t: IRow, i) => renderDrag(t, i)
    },
    { name: '序号', baseWidth: 80, renderCell: t => <div>{t.index}</div> },
    { name: '状态', baseWidth: 150, renderCell: t => <div>{t.state}</div> },
    { name: '列名', baseWidth: 180, renderCell: t => <div>{t.columnName}</div> },
    { name: '类型', baseWidth: 120, renderCell: t => <div>{t.type}</div> },
    { name: '长度', baseWidth: 120, renderCell: t => <div>{t.length}</div> },
    { name: '非null', baseWidth: 80, renderCell: t => <div>{t.unNull}</div> },
    { name: '注释', baseWidth: 200, flex: 1, renderCell: t => <div>{t.comment}</div> },
  ]);

  function moveStyle(index: number) {
    if (index === dragIndex) {
      return { transform: `translateY(${currentDragMovePx}px)` }
    } else if ((index > dragIndex!) && (dragedIndex! >= index)) {
      return { transform: `translateY(-${CategoryLineHeight}px)`, }
    } else if ((index < dragIndex!) && (dragedIndex! <= index)) {
      return { transform: `translateY(${CategoryLineHeight}px)` }
    }
  }

  return <div className={classnames(className, styles.box)}>
    <TableHeader></TableHeader>
    <div className={styles.tableMain}>
      {
        dataSource.map((t, index) => {
          return <TableRow
            className={classnames(styles.tableRow, { [styles.draggingRow]: index === dragIndex })}
            style={moveStyle(index)}
            index={index}
            key={t.index}
            data={t}
          ></TableRow>
        })
      }
    </div>
  </div >
})