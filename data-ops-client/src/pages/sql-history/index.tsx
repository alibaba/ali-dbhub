import React, { memo } from 'react';
import classnames from 'classnames';
import AppHeader from '@/components/AppHeader';
import { Tabs } from 'antd';
import styles from './index.less';

interface IProps {
  className?: string;
}

export default memo<IProps>(function SQLHistory({className}) {

  const onChange = (key: string) => {
    console.log(key);
  };

  const items = [
    { label: '项目 1', key: '1', children: '内容 1' }, // 务必填写 key
    { label: '项目 2', key: '2', children: '内容 2' },
    { label: '项目 2', key: '2', children: '内容 2' },
    { label: '项目 2', key: '2', children: '内容 2' },
    { label: '项目 2', key: '2', children: '内容 2' },
    { label: '项目 2', key: '2', children: '内容 2' },
    { label: '项目 2', key: '2', children: '内容 2' },
  ];
  
  return <div className={classnames(className, styles.box)}>
    <AppHeader></AppHeader>
    
  </div>
})
