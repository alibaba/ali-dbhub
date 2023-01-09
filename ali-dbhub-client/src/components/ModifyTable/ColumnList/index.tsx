import React, { memo, useDebugValue, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { Button, DatePicker, Input, Table, Modal, message } from 'antd';
import { createTableRows } from '@/components/TableColumns'

interface IProps {
  className?: string;
}

const dataSourceMock = {
  index: 111111,
  state: '新增',
  columnName: 'id',
  type: '123',
  length: 100,
  unNull: true,
  comment: '我是注释'
}


export default memo<IProps>(function ColumnList({ className }) {
  const columns = [
    {
      title: '序号',
      dataIndex: 'index',
      key: 'index',
      width: 100,
      render: (value: any) => {
        return <div>{value}</div>
      }
    },
    {
      title: '状态',
      dataIndex: 'state',
      key: 'state',
      width: 100
    },
    {
      title: '列名',
      dataIndex: 'columnName',
      key: 'columnName',
      width: 100
    },
    {
      title: '类型',
      dataIndex: 'type',
      key: 'type',
      width: 100
    },
    {
      title: '长度',
      dataIndex: 'length',
      key: 'length',
      width: 100
    },
    {
      title: '非null',
      dataIndex: 'unNull',
      key: 'unNull',
      width: 100
    },
    {
      title: '注释',
      dataIndex: 'comment',
      key: 'comment',
    },
  ]
  const [dataSource, setDataSource] = useState(dataSourceMock);
  const { Header: TableHeader, Row: TableRow } = createTableRows<{
    index: number;
    state: string;
    columnName: string,
    type: string,
    length: number,
    unNull: boolean,
    comment: string
  }>([
    { name: '序号', baseWidth: 200, flex: 1, renderCell: t => <div>{t.index}</div> },
    // { name: '12', baseWidth: 200, flex: 1, renderCell: t => <div>{t}</div> },
    // { name: '13', baseWidth: 200, flex: 1, renderCell: t => <div>{t}</div> },
    // { name: '14', baseWidth: 200, flex: 1, renderCell: t => <div>{t}</div> },
    // { name: '15', baseWidth: 200, flex: 1, renderCell: t => <div>{t}</div> },
  ]);

  return <div className={classnames(className, styles.box)}>
    <TableHeader></TableHeader>
    <TableRow data={dataSource}></TableRow>
  </div>
})