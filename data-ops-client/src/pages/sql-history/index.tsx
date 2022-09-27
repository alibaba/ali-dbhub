import React, { memo, useEffect, useState } from 'react';
import classnames from 'classnames';
import i18n from '@/i18n';
import AppHeader from '@/components/AppHeader';
import Tabs, { ITab } from '@/components/Tabs';
import Iconfont from '@/components/Iconfont';
import SearchInput from '@/components/SearchInput';
import LoadingContent from '@/components/LoadingContent';
import sqlServer, { IGetSaveListParams } from '@/service/sql';
import { IPageParams, IHistoryRecord } from '@/types';
import { DatabaseTypeCode, databaseType } from '@/utils/constants';
import { useDebounce } from '@/utils/hooks'
import { Input } from 'antd';
import styles from './index.less';
import request from 'umi-request';

interface IProps {
  className?: string;
}

enum TabsKey {
  SAVE = 'save',
  HISTPRY = 'history'
}

const tabs: ITab[] = [
  {
    label: '我的保存',
    key: TabsKey.SAVE
  },
  {
    label: '执行记录',
    key: TabsKey.HISTPRY
  }
]

export default memo<IProps>(function SQLHistory({ className }) {

  const [currentTab, setCurrentTab] = useState(tabs[0].key);
  const [dataList, setDataList] = useState<IHistoryRecord[] | null>();
  const [searchKey, setSearchKey] = useState<string>();

  useEffect(() => {
    getList()
  }, [currentTab, searchKey])

  const getList = () => {
    setDataList(null)
    const api = currentTab == TabsKey.SAVE ? sqlServer.getSaveList : sqlServer.getHistoryList
    let p: IPageParams | IGetSaveListParams = {
      searchKey,
      pageNo: 1,
      pageSize: 10,
      dataSourceId: 1,
      databaseName: '1'
    }

    api(p).then(res => {
      setDataList(res.data)
    })

  }

  const onChange = (key: string) => {
    setCurrentTab(key)
  };

  const searchInputChange = (value: string) => {
    setSearchKey(value)
  }
  const searchChange = useDebounce(searchInputChange, 500)


  return <div className={classnames(className, styles.box)}>
    <div className={styles.header}>
      <div className={styles.title}>我的SQL</div>
    </div>
    <Tabs
      className={styles.tabs}
      onChange={onChange}
      currentTab={currentTab}
      tabs={tabs}
    ></Tabs>
    <div className={styles.searchInputBox}>
      <SearchInput onChange={searchChange} className={styles.searchInput} placeholder='搜索'></SearchInput>
    </div>
    <div className={styles.sqlList}>
      <LoadingContent data={dataList} handleEmpty>
        {
          dataList?.map(item => {
            return <div key={item.id} className={styles.cardItem}>
              <div className={styles.ddlType}>
                <img src={databaseType[item.type].img} alt="" />
              </div>
              <div className={styles.name}>
                {item.name}
              </div>
              <div className={styles.databaseName}>
                {item.databaseName}
              </div>
              <div className={styles.arrows}>
                <Iconfont code='&#xe685;'></Iconfont>
              </div>
            </div>
          })
        }
      </LoadingContent>
    </div>
  </div>
})
