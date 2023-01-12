import React, { memo, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import IndexList from '@/components/ModifyTable/IndexList';
import ColumnList from '@/components/ModifyTable/ColumnList';
import BaseInfo from '@/components/ModifyTable/BaseInfo';

interface ITabItem {
  title: string;
  key: string;
  component: any; // TODO: 组件的Ts是什么
}

export default function ModifyTablePage() {

  const tabList: ITabItem[] = [
    {
      title: '基本信息',
      key: 'basic',
      component: <BaseInfo></BaseInfo>
    },
    {
      title: '列信息',
      key: 'column',
      component: <ColumnList></ColumnList>
    },
    {
      title: '索引信息',
      key: 'index',
      component: <IndexList></IndexList>
    },
  ]

  const [currentTab, setCurrentTab] = useState<ITabItem>(tabList[1])

  function changeTab(item: ITabItem) {
    setCurrentTab(item)
  }

  function renderTabList() {
    return <div className={styles.tabList}>
      {
        tabList.map((item, index) => {
          return <div
            key={item.key}
            onClick={changeTab.bind(null, item)}
            className={classnames(styles.tabItem, currentTab.key == item.key ? styles.currentTab : '')}
          >
            {item.title}
          </div>
        })
      }
    </div>
  }

  return <div className={styles.page}>
    {renderTabList()}
    <div className={styles.main}>
      {currentTab.component}
    </div>
  </div>
}