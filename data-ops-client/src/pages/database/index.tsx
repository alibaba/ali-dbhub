import React, { memo, useEffect, useState, useRef } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { history, useParams } from 'umi';
import { Button, DatePicker, Input, Table, Modal, Tabs, Dropdown } from 'antd';
import i18n from '@/i18n';
import AppHeader from '@/components/AppHeader';
import Iconfont from '@/components/Iconfont';
import Tree from '@/components/Tree';
import Loading from '@/components/Loading/Loading';
import MonacoEditor from '@/components/MonacoEditor';
import DraggableDivider from '@/components/DraggableDivider';
import ConnectionPage from '@/pages/connection';
import SearchResult from '@/components/SearchResult';
import Menu, { IMenu, MenuItem } from '@/components/Menu';
import connectionServer from '@/service/connection';
import mysqlServer from '@/service/mysql';
import SearchInput from '@/components/SearchInput';
import { IConnectionBase, ITreeNode } from '@/types'
import { TreeNodeType } from '@/utils/constants'
import { toTreeList, createRandom } from '@/utils/index'
import { databaseType, DatabaseTypeCode } from '@/utils/constants'

interface IProps {
  className?: any;
}
interface ITabItem {
  label: string;
  key: string;
  sql?: string;
}
interface IDB {
  id: string;
  name: string;
  type: DatabaseTypeCode
}

const initialItems: ITabItem[] = [
  {
    label: 'Default Tab',
    key: createRandom(1000000000000000, 9999999999999999) + '',
    sql: 'SELECT * FROM 1'
  },
];

const basicsTree: ITreeNode[] = [
  {
    key: '1-1',
    name: '表',
    type: TreeNodeType.TABLE,
  },
  {
    key: '1-2',
    name: '查询',
    type: TreeNodeType.SAVE,
  }
]

export function DatabaseQuery({ activeKey, details }: { activeKey: string, details: any }) {

  const [searchResultDataList, setSearchResultDataList] = useState<any>()

  useEffect(() => {
    let p = {
      consoleId: '1',
      dataSourceId: details.dataSourceId,
      databaseName: details.databaseName,
    }
    mysqlServer.connectConsole(p).then(res => {

    })
  }, [])
  const monacoEditorBox = useRef<HTMLDivElement | null>(null);
  let editor: any = ''

  function getEditor(e: any) {
    editor = e
  }
  const callback = () => {
    editor && editor.layout()
  }

  function getMonacoValue(value: string) {
    console.log(value)
  }

  const executeSql = () => {
    mysqlServer.executeSql({
      sql: details.sql,
      consoleId: details.consoleId || 1,
      dataSourceId: details?.dataSourceId,
      databaseName: details?.databaseName
    }).then(res => {
      setSearchResultDataList(res)
    })
  }

  return <>
    <div className={classnames(styles.databaseQuery, { [styles.databaseQueryConceal]: details.id !== activeKey })}>
      <div className={styles.operatingArea}>
        <Button type="primary" onClick={executeSql}>执行</Button>
        <Button>保存</Button>
        <Button>我的SQL</Button>
      </div>
      <div ref={monacoEditorBox} className={styles.monacoEditor}>
        <MonacoEditor getMonacoValue={getMonacoValue} id={details.id} defaultValue={details.sql} getEditor={getEditor}></MonacoEditor>
      </div>
      <DraggableDivider callback={callback} direction='row' min={200} volatileRef={monacoEditorBox} />
      <div className={styles.searchResult}>
        <SearchResult dataList={searchResultDataList}></SearchResult>
      </div>
    </div>
  </>
}

const windowListMock = [
  {
    sql: "select * from test;",
    dataSourceId: 16,
    databaseName: "PUBLIC",
    consoleId: "1",
    id: 2
  },
  {
    sql: "select * from test;",
    dataSourceId: 16,
    databaseName: "PUBLIC",
    consoleId: "1",
    id: 1
  },

]

export default memo<IProps>(function DatabasePage({ className }) {
  const params: { id: string } = useParams()
  const letfRef = useRef<HTMLDivElement | null>(null);
  const [connectionDetaile, setConnectionDetaile] = useState<IConnectionBase>()
  const [currentDB, setCurrentDB] = useState<IDB>()
  const [activeKey, setActiveKey] = useState(initialItems[0].key);
  const [items, setItems] = useState<ITabItem[]>(initialItems);
  const [windowList, setWindowList] = useState<any[]>(windowListMock);
  const [currentWindow, setCurrentWindow] = useState(windowListMock[0]);
  const newTabIndex = useRef(0);
  const [treeData, setTreeData] = useState<ITreeNode[]>(basicsTree);
  const [DBList, setDBList] = useState<IDB[]>();
  let editor: any = ''

  useEffect(() => {
    if (params.id) {
      getDetaile()
      getDBList()
    }
  }, [params.id])

  useEffect(() => {
    console.log('数据库切换')
    if (currentDB) {
      getTableList()
    }
  }, [currentDB])

  useEffect(() => {
    setCurrentDB(DBList?.[0])
  }, [DBList])

  const getDBList = () => {
    connectionServer.getDBList({
      id: params.id
    }).then(res => {
      const list = res?.map(item => {
        return {
          name: item.name,
          id: item.name,
          type: DatabaseTypeCode.H2
        }
      })
      setDBList(list)
    })
  }

  const getTableList = () => {
    let p = {
      dataSourceId: params.id,
      databaseName: currentDB?.name,
      pageNo: 1,
      pageSize: 10,
    }
    return mysqlServer.getList(p).then(res => {
      const tableList: ITreeNode[] = res.data?.map(item => {
        return {
          name: item.name,
          type: TreeNodeType.TABLE,
          key: item.name,
          children: [
            {
              key: '1',
              name: '列',
              type: TreeNodeType.LINE,
              children: toTreeList(item.columnList, 'name', 'type', TreeNodeType.LINE)
            },
            {
              key: '1',
              name: '索引',
              type: TreeNodeType.INDEXES,
              children: toTreeList(item.indexList, 'name', 'type', TreeNodeType.INDEXES)
            }
          ]
        }
      })
      console.log(tableList)
      setTreeData(tableList)
      return tableList
    })

  }

  const getDetaile = () => {
    let p = {
      id: params.id
    }
    connectionServer.getDetaile(p).then(res => {
      setConnectionDetaile(res)
    })
  }

  const callback = () => {
    editor && editor.layout()
  }

  const add = () => {
    const newActiveKey = 'newTab';
    const newPanes = [...items];
    newPanes.push({ label: 'New Tab', key: createRandom(1000000000000000, 9999999999999999) + '' });
    console.log(newPanes)
    setItems(newPanes);
    setActiveKey(newActiveKey);
  };

  const remove = (targetKey: string) => {
    let newActiveKey = activeKey;
    let lastIndex = -1;
    items.forEach((item, i) => {
      if (item.key === targetKey) {
        lastIndex = i - 1;
      }
    });
    const newPanes = items.filter(item => item.key !== targetKey);
    if (newPanes.length && newActiveKey === targetKey) {
      if (lastIndex >= 0) {
        newActiveKey = newPanes[lastIndex].key;
      } else {
        newActiveKey = newPanes[0].key;
      }
    }
    setItems(newPanes);
    setActiveKey(newActiveKey);
  };

  const onEdit = (targetKey: any, action: 'add' | 'remove') => {
    if (action === 'add') {
      add();
    } else {
      remove(targetKey);
    }
  };

  const onChangeTab = (newActiveKey: string) => {
    setActiveKey(newActiveKey);
  };

  const loadData = (data: ITreeNode) => {
    if (!data.children && data.type === TreeNodeType.TABLE) {
      return getTableList()
    } else {
      return new Promise((r) => {
        r(null)
      })
    }
  }

  const searchTable = (value: string) => {
  }

  const DBListMenu = () => {
    const myDBList = DBList?.map(item => {
      return {
        title: item.name,
        key: item.id,
        type: item.type
      }
    })
    const switchDB = (item: IMenu<DatabaseTypeCode>) => {
      if (item.key !== currentDB?.id) {
        setCurrentDB({
          name: item.title,
          id: item.key,
          type: item.type!
        })
      }
    }

    return <Menu>
      {
        myDBList?.map(item => {
          return <MenuItem key={item.key} onClick={switchDB.bind(null, item)}>
            <div className={styles.switchDBItem}>
              <div className={styles.DBLogo} style={{ backgroundImage: `url(${databaseType[item?.type].img})` }}></div>
              <div className={styles.DBName}>{item.title}</div>
            </div>
          </MenuItem>
        })
      }
    </Menu>
  }

  return <>
    <div className={classnames(className, styles.box)}>
      <div ref={letfRef} className={styles.aside}>
        <div className={styles.header}>
          <Dropdown overlay={DBListMenu} trigger={['click']}>
            <div className={styles.currentNameBox}>
              {
                currentDB &&
                <div className={styles.DBLogo} style={{ backgroundImage: `url(${databaseType[currentDB?.type].img})` }}></div>
              }
              <div className={styles.databaseName}>
                {currentDB?.name}
              </div>
              {DBList?.[1] && <Iconfont code="&#xe7b1;"></Iconfont>}
            </div>
          </Dropdown>

          <div className={styles.searchBox}>
            <SearchInput onChange={searchTable} placeholder='搜索'></SearchInput>
            <div className={classnames(styles.refresh, styles.button)}>
              <Iconfont code="&#xec08;"></Iconfont>
            </div>
            <div className={classnames(styles.create, styles.button)}>
              <Iconfont code="&#xe631;"></Iconfont>
            </div>
          </div>
        </div>
        <div className={styles.overview}>
          <Iconfont code="&#xe63d;"></Iconfont>
          <span>{i18n('database.button.overview')}</span>
        </div>
        <Tree className={styles.tree} loadData={loadData} treeData={treeData}></Tree>
      </div>
      <DraggableDivider callback={callback} volatileRef={letfRef} />
      <div className={styles.main}>
        <AppHeader>
          <div className={styles.tabsBox}>
            <Tabs
              type="editable-card"
              onChange={onChangeTab}
              activeKey={activeKey}
              onEdit={onEdit}
              items={items}
            />
          </div>
        </AppHeader>
        <div className={styles.databaseQueryBox}>
          {
            windowList?.map((i: any) => {
              return <DatabaseQuery details={i} key={i.id} activeKey={currentWindow.id}></DatabaseQuery>
            })
          }
        </div>
      </div>
    </div>
  </>
});
