import { Table } from 'antd';
import React from 'react';
const dataSource = [
  {
    key: '1',
    name: '胡彦斌',
    age: 32,
    address: '西湖区湖底公园1号',
  },
  {
    key: '2',
    name: '胡彦祖',
    age: 42,
    address: '西湖区湖底公园1号',
  },
];

const columns = [
  {
    title: '用户名',
    dataIndex: 'user_name',
    key: 'user_name',
  },
  {
    title: '昵称',
    dataIndex: 'nick_name',
    key: 'nick_name',
  },
  {
    title: '邮箱',
    dataIndex: 'email',
  },
  {
    title: '角色',
    dataIndex: 'role'
  },
  {
    title: '操作',
  },
];

export default function Manage() {
  return <Table dataSource={dataSource} columns={columns} />;
}
