import React, { memo, ReactNode, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { Tabs as AntdTabs } from 'antd';

export interface ITab {
  label: ReactNode;
  key: string;
}

interface IProps {
  className?: string;
  tabs: ITab[];
  currentTab?: string;
  onChange: (index: string) => void;
  extra?: React.ReactNode;

}

export default memo(function Tabs({ className, tabs, currentTab, onChange, extra }: IProps) {

  return <div className={classnames(className, styles.box)}>
    <AntdTabs
      defaultActiveKey={currentTab}
      onChange={onChange}
      items={tabs}
    />
    <div className={styles.extra}>
      {extra}
    </div>
  </div>
})
