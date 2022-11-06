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
import connectionServer from '@/service/connection'
import { IPageParams, IHistoryRecord } from '@/types';
import { DatabaseTypeCode, databaseType } from '@/utils/constants';
import { useDebounce, useUpdateEffect } from '@/utils/hooks'
import { Input, Select } from 'antd';
import styles from './index.less';
import type { SelectProps } from 'antd';

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
  const [finished, setFinished] = useState(false);
  const [connectionOptions, setConnectionOptions] = useState<SelectProps['options']>();
  const [currentConnection, setCurrentConnection] = useState<string>();
  const [databaseOptions, setDatabaseOptions] = useState<SelectProps['options']>();
  const [currentDatabase, setCurrentDatabase] = useState<string>();
  const scrollerRef = useRef(null);
  const initialListParams: IGetHistoryListParams = {
    searchKey: '',
    pageNo: 1,
    pageSize: 1000,
    dataSourceId: '',
    databaseName: ''
  }
  const listParams = useRef(initialListParams)

  useUpdateEffect(() => {
    getList();
  }, [currentTab])

  useEffect(() => {
    let p = {
      pageNo: 1,
      pageSize: 100
    }
    connectionServer.getList(p).then(res => {
      let list: SelectProps['options'] = []
      list = res.data.map(item => {
        return {
          label: item.alias,
          value: item.id,
        }
      })
      setConnectionOptions(list)
    })
  }, [])


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
      location.href = `/#/database/${item.type}/${item.dataSourceId}?databaseName=${item.databaseName}&windowTabName=${item.name}`
    }
  }

  const handleChangeConnection = (value: string) => {
    console.log(value)
    listParams.current.dataSourceId = value;
    let p = {
      id: value
    }
    connectionServer.getDBList(p).then(res => {
      let list: SelectProps['options'] = []
      list = res.map(item => {
        return {
          label: item.name,
          value: item.name,
        }
      })
      setDatabaseOptions(list)
    })
  };
  const handleChangeDatabase = (value: string) => {
    console.log(value)
    listParams.current.databaseName = value;
    listParams.current.pageNo = 1;
    getList();
  };


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
      <Select
        className={styles.select}
        // allowClear
        placeholder="请选择链接"
        onChange={handleChangeConnection}
        options={connectionOptions}
      />
      <Select
        // allowClear
        className={styles.select}
        placeholder="请选择数据库"
        onChange={handleChangeDatabase}
        options={databaseOptions}
      />
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
