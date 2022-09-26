import React, { memo, ReactNode, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';

export interface ITab<T> {
  label: ReactNode;
  key: T;
  extra?: React.ReactNode;
}

interface IProps<T> {
  className?: string;
  tabs: ITab<T>[];
}

export default memo(function Tabs<T extends number | string>({ className, tabs }: IProps<T>) {
  const [currentTab, setCurrentTab] = useState<T>(tabs[0].key);
  const change = (item: ITab<T>) => {
    setCurrentTab(item.key)
  }
  return <div className={classnames(className, styles.box)}>
    {
      tabs.map(item => {
        return <div onClick={change.bind(null, item)} className={classnames({ [styles.currentTab]: currentTab == item.key }, styles.tab)} key={item.key}>
          <div>
            {item.label}
          </div>
        </div>
      })
    }
  </div>
})
