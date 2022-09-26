import React, { memo } from 'react';
import classnames from 'classnames';
import AppHeader from '@/components/AppHeader';
import Tabs, { ITab } from '@/components/Tabs';
import styles from './index.less';


interface IProps {
  className?: string;
}

const tabs: ITab<string>[] = [
  {
    label: '我的保存',
    key: 'save'
  },
  {
    label: '执行记录',
    key: 'record'
  }
]

export default memo<IProps>(function SQLHistory({ className }) {

  const onChange = (key: string) => {
    console.log(key);
  };

  return <div className={classnames(className, styles.box)}>
    <AppHeader></AppHeader>
    <Tabs tabs={tabs}></Tabs>
  </div>
})
