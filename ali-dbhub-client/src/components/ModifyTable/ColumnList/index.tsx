import React, { memo, useCallback, useDebugValue, useEffect, useLayoutEffect, useRef, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { Button, DatePicker, Input, Table, Modal, message, Checkbox, Select } from 'antd';
import { createTableRows } from '@/components/TableColumns'
import Iconfont from '@/components/Iconfont';
import DraggingRow from '@/components/DraggingRow';
import { useOnlyOnceTask } from '@/utils/hooks';

interface IProps {
  className?: string;
}
interface IRow {
  index: number;
  state: string;
  columnName: string;
  type: string;
  length: string;
  unNull: boolean;
  comment: string;
  isEdit: boolean;
}

const dataSourceMock = [
  {
    index: 1,
    state: '新增1',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: '100',
    unNull: true,
    comment: '我是注释',
    isEdit: true,
  },
  {
    index: 2,
    state: '新增2',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: '100',
    unNull: true,
    comment: '我是注释',
    isEdit: false,
  },
  {
    index: 3,
    state: '新增3',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: '100',
    unNull: true,
    comment: '我是注释',
    isEdit: false,
  },
  {
    index: 4,
    state: '新增4',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: '100',
    unNull: true,
    comment: '我是注释',
    isEdit: false,
  },
  {
    index: 5,
    state: '新增5',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: '100',
    unNull: true,
    comment: '我是注释',
    isEdit: false,
  },
  {
    index: 6,
    state: '新增6',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: '100',
    unNull: true,
    comment: '我是注释',
    isEdit: false,
  },
  {
    index: 7,
    state: '新增7',
    columnName: 'SERVER_VERSION',
    type: 'bigint unsigned',
    length: '100',
    unNull: true,
    comment: '我是注释',
    isEdit: false,
  },
]

const CategoryLineHeight = 46;

export default memo<IProps>(function ColumnList({ className }) {
  const dataSourceRef = useRef<IRow[]>(dataSourceMock);
  const [, setRefresh] = useState(0);
  const [dragIndex, setDragIndex] = useState<number>();
  const [currentDragMovePx, setCurrentDragMovePx] = useState<string>();
  const [dragedIndex, setDragedIndex] = useState<number>();

  function renderDrag(t: IRow, i: number) {
    return <div onMouseDown={(e) => { onMouseDown(e, t, i) }}>{
      <Iconfont code="&#xe611;" />
    }</div>
  }

  function onMouseDown(e: React.MouseEvent, t: IRow, i: number) {
    setDragIndex(i);
    const start = e.clientY;
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
      if (i === dataSourceRef.current.length && (start - clientY) < 0) return false
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
        const newList = [...dataSourceRef.current!];
        const item = newList.splice(i, 1)[0];
        newList.splice(target, 0, item);
        dataSourceRef.current = newList
      }
      document.body.removeChild(mask);
      setCurrentDragMovePx('0px')
      setDragIndex(undefined)
      setDragedIndex(undefined)
    });

    document.body.appendChild(mask);
  }

  function onChangeIsNull() {

  }
  console.log('dataSourceRef.current-变了', dataSourceRef.current)

  function enterEdit(t: IRow) {
    const newList = [...dataSourceRef.current]
    newList.map(item => {
      if (item.index === t.index) {
        item.isEdit = true
      } else {
        item.isEdit = false
      }
    })
    dataSourceRef.current = [...newList]
    setRefresh(new Date().getTime())
  }

  function onChangeDataSource<Key extends keyof IRow>(type: Key, value: IRow[Key], rowData: IRow) {
    const newList = [...dataSourceRef.current]
    newList.map((t: IRow, i) => {
      if (t.index === rowData.index) {
        t[type] = value
      }
    })
    dataSourceRef.current = newList
    setRefresh(new Date().getTime())
  }

  function renderSelete(rowData: IRow) {

    const onChange = (value: string) => {
      console.log(`selected ${value}`);
    };

    const onSearch = (value: string) => {
      console.log('search:', value);
    };

    return <Select
      className={styles.select}
      showSearch
      placeholder="请选择"
      onChange={onChange}
      onSearch={onSearch}
      options={[
        {
          value: 'jack',
          label: 'Jack',
        },
        {
          value: 'lucy',
          label: 'Lucy',
        },
        {
          value: 'tom',
          label: 'Tom',
        },
      ]}
    />
  }

  const { Header: TableHeader, Row: TableRow } = useOnlyOnceTask(() => {
    return createTableRows<IRow>([
      {
        name: '拖动', baseWidth: 50, renderCell: (t: IRow, i) => renderDrag(t, i)
      },
      {
        name: '序号', baseWidth: 50, renderCell: t => <div>{t.index}</div>
      },
      {
        name: '状态', baseWidth: 50, renderCell: t => <div>{t.state}</div>
      },
      {
        name: '列名', baseWidth: 180, renderCell: t => {
          return t.isEdit ?
            <Input onChange={(value) => { onChangeDataSource('columnName', value.target.value, t) }} value={t.columnName}></Input>
            :
            <div onClick={enterEdit.bind(null, t)} className={styles.cellContent}>{t.columnName}</div>
        }
      },
      {
        name: '类型', baseWidth: 180, renderCell: t => {
          return t.isEdit ?
            renderSelete(t)
            :
            <div onClick={enterEdit.bind(null, t)} className={styles.cellContent}>{t.type}</div>
        }
      },
      {
        name: '长度', baseWidth: 80, renderCell: t => {
          return t.isEdit ?
            <Input onChange={(value) => { onChangeDataSource('length', value.target.value, t) }} value={t.length}></Input>
            :
            <div onClick={enterEdit.bind(null, t)} className={styles.cellContent}>{t.length}</div>
        }
      },
      {
        name: '可空', baseWidth: 50, renderCell: t => <div><Checkbox value={t.unNull} onChange={(value) => { onChangeDataSource('unNull', value.target.value, t) }}></Checkbox></div>
      },
      {
        name: '注释', flex: 1, renderCell: t => {
          return t.isEdit ?
            <Input onChange={(value) => { onChangeDataSource('comment', value.target.value, t) }} value={t.comment}></Input>
            :
            <div onClick={enterEdit.bind(null, t)} className={styles.cellContent}>{t.comment}</div>
        }
      },
    ])
  });

  function moveStyle(index: number) {
    if (index === dragIndex) {
      return { transform: `translateY(${currentDragMovePx}px)` }
    } else if ((index > dragIndex!) && (dragedIndex! >= index)) {
      return { transform: `translateY(-${CategoryLineHeight}px)`, }
    } else if ((index < dragIndex!) && (dragedIndex! <= index)) {
      return { transform: `translateY(${CategoryLineHeight}px)` }
    } else {
      return { transform: `translateY(0px)` }
    }
  }

  return <div className={classnames(className, styles.box)}>
    <TableHeader></TableHeader>
    <div className={classnames(styles.tableMain, { [styles.dragging]: dragIndex !== undefined })}>
      {
        dataSourceRef.current.map((t, index) => {
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