import React, { memo, useEffect, useState, useRef } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { history, useParams } from 'umi';
import { Button, DatePicker, Input, Table, Modal, Tabs } from 'antd';
import i18n from '@/i18n';
import AppHeader from '@/components/AppHeader';
import Iconfont from '@/components/Iconfont';
import Tree from '@/components/Tree';
import Loading from '@/components/Loading/Loading';
import MonacoEditor from '@/components/MonacoEditor';
import DraggableDivider from '@/components/DraggableDivider';
import ConnectionPage from '@/pages/connection';
import SearchResult from '@/components/SearchResult';
import connectionServer from '@/service/connection';
import mysqlServer from '@/service/mysql';
import SearchInput from '@/components/SearchInput';
import { IConnectionBase, ITreeNode } from '@/types'
import { TreeNodeType } from '@/utils/constants'
import { toTreeList } from '@/utils/index'

interface IProps {
  className?: any;
}

interface ITabItem {
  label: string;
  key: string;
  sql?: string;
}

const initialItems: ITabItem[] = [
  {
    label: 'Tab 1',
    key: '1',
    sql: 'SELECT * FROM 1'
  },
  {
    label: 'Tab 2',
    key: '2',
    sql: 'SELECT * FROM 2'
  },
  {
    label: 'Tab 3',
    key: '3',
    sql: 'SELECT * FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE'
  },
];

export default memo<IProps>(function DatabasePage({ className }) {
  const params: { id: string } = useParams()
  const letfRef = useRef<HTMLDivElement | null>(null);
  const [databaseDetaile, setDatabaseDetaile] = useState<IConnectionBase>()
  const monacoEditorBox = useRef<HTMLDivElement | null>(null);
  const [activeKey, setActiveKey] = useState(initialItems[0].key);
  const [items, setItems] = useState<ITabItem[]>(initialItems);
  const newTabIndex = useRef(0);
  const [treeData, setTreeData] = useState<ITreeNode[] | null>(null);
  const [connectionModalVisible, setConnectionModalVisible] = useState(false);
  let editor: any = ''

  useEffect(() => {
    if (params.id) {
      getDetaile()
      getDBList()
    }
  }, [params.id])

  const getDBList = () => {
    const DBList: ITreeNode[] = [
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

      // {
      //   key: '1',
      //   name: 'data-ops',
      //   type: TreeNodeType.DATABASE,
      //   children: [

      //   ]
      // },
      // {
      //   key: '2',
      //   name: 'ata',
      //   type: TreeNodeType.DATABASE,
      //   children: [
      //     {
      //       key: '2-1',
      //       name: '表',
      //       type: TreeNodeType.TABLE,
      //     },
      //     {
      //       key: '2-2',
      //       name: '查询',
      //       type: TreeNodeType.SAVE,
      //     },
      //   ]
      // },
      // {
      //   key: '3',
      //   name: 'grow',
      //   type: TreeNodeType.DATABASE,
      //   children: [
      //     {
      //       key: '3-1',
      //       name: '表',
      //       type: TreeNodeType.TABLE,
      //     },
      //     {
      //       key: '3-2',
      //       name: '查询',
      //       type: TreeNodeType.SAVE,
      //     },
      //   ]
      // },
    ]
    setTreeData(DBList)
  }

  const getTableList = () => {
    let p = {
      dataSourceId: '2',
      databaseName: 'TEST',
      pageNo: 1,
      pageSize: 10,
    }
    return mysqlServer.getList(p).then(res => {
      const tableList: ITreeNode[] = res.data.map(item => {
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
      return tableList
    })

  }

  const getDetaile = () => {
    let p = {
      id: params.id
    }
    connectionServer.getDetaile(p).then(res => {
      setDatabaseDetaile(res)
      // setTableDate([])
    })
  }

  const callback = () => {
    editor && editor.layout()
  }

  function getEditor(e: any) {
    editor = e
  }

  const add = () => {
    const newActiveKey = `newTab${newTabIndex.current++}`;
    const newPanes = [...items];
    newPanes.push({ label: 'New Tab', key: newActiveKey });
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
    setConnectionModalVisible
  }

  return <>
    <div className={classnames(className, styles.box)}>
      <div ref={letfRef} className={styles.aside}>
        <div className={styles.header}>
          <div onClick={() => { setConnectionModalVisible(true) }} className={styles.currentNameBox}>
            <div className={styles.databaseName}>
              {databaseDetaile?.alias}
            </div>
            {databaseDetaile?.alias && <Iconfont code="&#xe7b1;"></Iconfont>}
          </div>
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
        <div className={styles.operatingArea}>
          <Button type="primary">执行</Button>
          <Button>保存</Button>
          <Button>我的SQL</Button>
        </div>
        <div className={styles.databaseQueryBox}>
          {
            items.map((i: ITabItem) => {
              return <div className={classnames(styles.databaseQuery, { [styles.databaseQueryConceal]: i.key !== activeKey })}>
                <div ref={monacoEditorBox} className={styles.monacoEditor}>
                  <MonacoEditor id={i.key} defaultValue={i.sql} getEditor={getEditor}></MonacoEditor>
                </div>
                <DraggableDivider callback={callback} direction='row' min={200} volatileRef={monacoEditorBox} />
                <div className={styles.searchResult}>
                  <SearchResult></SearchResult>
                </div>
              </div>
            })
          }
        </div>
      </div>
    </div>
    <Modal
      title="切换连接"
      visible={connectionModalVisible}
      onCancel={() => { setConnectionModalVisible(false) }}
      footer={false}
    >
      <div className={styles.connectionList}>
        <ConnectionPage onlyList={true}></ConnectionPage>
      </div>
    </Modal>
  </>
});
