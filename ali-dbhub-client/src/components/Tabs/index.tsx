import React, { memo, ReactNode, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { Tabs as AntdTabs, Button } from 'antd';

export interface ITab {
  label: ReactNode;
  key: string;
}

interface IProps {
  className?: string;
  tabs: ITab[];
  currentTab?: string;
  onChange: (key: string, index: number) => void;
  extra?: React.ReactNode
}

export default memo(function Tabs({ className, tabs, currentTab, onChange, extra }: IProps) {
  const [hiddenTabs, setHiddenTabs] = useState<string[]>([]);

  function myChange(key: string) {
    const index = tabs.findIndex(t => t.key === key);
    onChange(key, index);
  }

  function handleClose(key: string) {
    const updatedHiddenTabs = [...hiddenTabs, key];
    setHiddenTabs(updatedHiddenTabs);
  }

  const visibleTabs = tabs.filter(tab => !hiddenTabs.includes(tab.key));

  return (
    <div className={classnames(className, styles.box)}>
      <AntdTabs defaultActiveKey={currentTab} onChange={myChange}>
        {visibleTabs.map(tab => (
          <AntdTabs.TabPane tab={
            <span>
              <span style={{float:'left'}}> {tab.label} &nbsp; </span> 
              <a onClick={() => handleClose(tab.key)}>X</a>
            </span>
          } key={tab.key} />
        ))}
      </AntdTabs>
      <div className={styles.extra}>
        
        {extra}
      </div>
    </div>
  );
});


