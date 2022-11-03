import React, { memo, useEffect, useRef, useState } from 'react';
import classnames from 'classnames';
import i18n from '@/i18n';
import AppHeader from '@/components/AppHeader';
import Tabs, { ITab } from '@/components/Tabs';
import Iconfont from '@/components/Iconfont';
import SearchInput from '@/components/SearchInput';
import LoadingContent from '@/components/Loading/LoadingContent';
import ScrollLoading from '@/components/ScrollLoading';
import sqlServer, { IGetHistoryListParams } from '@/service/history';
import { IPageParams, IHistoryRecord } from '@/types';
import { DatabaseTypeCode, databaseType } from '@/utils/constants';
import { useDebounce, useUpdateEffect } from '@/utils/hooks'
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

export default memo<IProps>(function SQLHistoryPage({ className }) {

  const [currentTab, setCurrentTab] = useState(tabs[0].key);
  const [dataList, setDataList] = useState<IHistoryRecord[] | null>();
  const [searchKey, setSearchKey] = useState<string>();
  const [finished, setFinished] = useState(false);
  const scrollerRef = useRef(null);
  const initialListParams: IGetHistoryListParams = {
    searchKey: '',
    pageNo: 1,
    pageSize: 1000,
    dataSourceId: '10',
    databaseName: 'PUBLIC'
  }
  const listParams = useRef(initialListParams)

  useUpdateEffect(() => {
    getList();
  }, [currentTab])

  const getList = () => {
    const api = currentTab == TabsKey.SAVE ? sqlServer.getSaveList : sqlServer.getHistoryList;
    return api(listParams.current).then(res => {
      if (!res.hasNextPage) {
        setFinished(true)
      }
      if (listParams.current.pageNo === 1) {
        setDataList(res.data)
      } else {
        setDataList([...dataList!, ...res.data])
      }
      listParams.current.pageNo++
    })
  }

  const onChangeTab = (key: string) => {
    listParams.current.pageNo = 1;
    setCurrentTab(key);
  };

  const searchInputChange = (value: string) => {
    listParams.current.pageNo = 1;
    listParams.current.searchKey = value;
    getList();
  }
  const searchChange = useDebounce(searchInputChange, 500)

  const jumpToDatabasePage = (item: IHistoryRecord) => {
    if (currentTab == TabsKey.SAVE) {
      location.href = `/#/database/${item.type}/${item.dataSourceId}?databaseName=${item.databaseName}?windowTabName=${item.name}`
    }
  }

  return <div className={classnames(className, styles.box)}>
    <div className={styles.header}>
      <div className={styles.title}>我的SQL</div>
    </div>
    <Tabs
      className={styles.tabs}
      onChange={onChangeTab}
      currentTab={currentTab}
      tabs={tabs}
    ></Tabs>
    <div className={styles.searchInputBox}>
      <SearchInput onChange={searchChange} className={styles.searchInput} placeholder='搜索'></SearchInput>
    </div>
    <div className={styles.sqlListBox} ref={scrollerRef}>
      <ScrollLoading
        finished={finished}
        scrollerElement={scrollerRef}
        onReachBottom={getList}
        threshold={300}
      >
        <div className={styles.sqlList}>
          {
            dataList?.map(item => {
              return <div onClick={jumpToDatabasePage.bind(null, item)} key={item.id} className={styles.cardItem}>
                <div className={styles.ddlType}>
                  <img src={databaseType[item.type].img} alt="" />
                </div>
                <div className={styles.name}>
                  {item.name}
                </div>
                <div className={styles.databaseName}>
                  {item.databaseName}
                </div>
                {
                  currentTab == TabsKey.SAVE &&
                  < div className={styles.arrows}>
                    <Iconfont code='&#xe685;'></Iconfont>
                  </div>
                }
              </div>
            })
          }
        </div>
      </ScrollLoading>
    </div >
  </div >
})
