import React, { memo, useEffect, useState, useRef } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { history, useParams } from 'umi';
import { Button, DatePicker, Input, Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import i18n from '@/i18n';
import AppHeader from '@/components/AppHeader';
import Iconfont from '@/components/Iconfont';
import Tree from '@/components/Tree';
import Loading from '@/components/Loading/Loading';
import MonacoEditor from '@/components/MonacoEditor';
import DraggableDivider from '@/components/DraggableDivider';
import LoadingContent from '@/components/Loading/LoadingContent';
import connectionServer from '@/service/connection';
import mysqlServer from '@/service/mysql';
import SearchInput from '@/components/SearchInput';
import Tabs from '@/components/Tabs';
import { IConnectionBase, ITreeNode } from '@/types'
import { TreeNodeType } from '@/utils/constants'
import { toTreeList } from '@/utils/index'

interface IProps {
  className?: any;
}
interface DataType {
  key: React.Key;
  name: string;
  age: number;
  address: string;
}

const columns: ColumnsType<DataType> = [
  {
    title: 'Name',
    dataIndex: 'name',
  },
  {
    title: 'Age',
    dataIndex: 'age',
  },
  {
    title: 'Address',
    dataIndex: 'address',
  },
];

const tableDataMock: DataType[] = [
  {
    key: '1',
    name: 'John Brown',
    age: 32,
    address: 'New York No. 1 Lake Park',
  },
  {
    key: '2',
    name: 'Jim Green',
    age: 42,
    address: 'London No. 1 Lake Park',
  },
  {
    key: '3',
    name: 'Joe Black',
    age: 32,
    address: 'Sidney No. 1 Lake Park',
  },
];

const initialItems = [
  { label: 'Tab 1', key: '1' },
  { label: 'Tab 2', key: '2' },
  {
    label: 'Tab 3',
    key: '3',
  },
];

export default memo<IProps>(function DatabasePage({ className }) {
  const params: { id: string } = useParams()
  const letfRef = useRef<HTMLDivElement | null>(null);
  const [databaseDetaile, setDatabaseDetaile] = useState<IConnectionBase>()
  const monacoEditorBox = useRef<HTMLDivElement | null>(null);
  const [activeKey, setActiveKey] = useState(initialItems[0].key);
  const [items, setItems] = useState(initialItems);
  const newTabIndex = useRef(0);
  const [tableData, setTableDate] = useState(tableDataMock);
  const [treeData, setTreeData] = useState<ITreeNode[] | null>(null);
  let editor: any = ''

  useEffect(() => {
    if (params.id) {
      getDetaile()
      getDBList()
    }
  }, [])

  const getDBList = () => {
    const DBList: ITreeNode[] = [
      {
        key: '1',
        name: 'data-ops',
        type: TreeNodeType.DATABASE,
        children: [
          {
            key: '1-1',
            name: '表',
            type: TreeNodeType.TABLE,
          },
          {
            key: '1-2',
            name: '查询',
            type: TreeNodeType.SAVE,
          },
        ]
      },
      {
        key: '2',
        name: 'ata',
        type: TreeNodeType.DATABASE,
        children: [
          {
            key: '2-1',
            name: '表',
            type: TreeNodeType.TABLE,
          },
          {
            key: '2-2',
            name: '查询',
            type: TreeNodeType.SAVE,
          },
        ]
      },
      {
        key: '3',
        name: 'grow',
        type: TreeNodeType.DATABASE,
        children: [
          {
            key: '3-1',
            name: '表',
            type: TreeNodeType.TABLE,
          },
          {
            key: '3-2',
            name: '查询',
            type: TreeNodeType.SAVE,
          },
        ]
      },
    ]
    setTreeData(DBList)
  }

  const getTableList = () => {
    let p = {
      dataSourceId: '11',
      databaseName: '11',
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

  const makerResultHeaderList = () => {
    const list = [
      {
        label: <div>执行记录</div>,
        key: '10',
      }
    ]
    const sqlRes = [
      {
        status: 'success',
        id: '0'
      },
      {
        status: 'fail',
        id: '1'
      },
      {
        status: 'fail',
        id: '2'
      },
      {
        status: 'success',
        id: '3'
      }, {
        status: 'success',
        id: '4'
      }, {
        status: 'success',
        id: '5'
      },
    ]

    sqlRes.map((item, index) => {
      list.push({
        label: <div>
          <Iconfont className={classnames(
            styles[item.status == 'success' ? 'successIcon' : 'failIcon'],
            styles.statusIcon
          )}
            code={item.status == 'success' ? '\ue605' : '\ue87c'} />
          执行结果{index}
        </div>,
        key: item.id
      })
    })
    return list
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

  function onChange() {
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

  const onEdit = (targetKey: string, action: 'add' | 'remove') => {
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

  return (
    <div className={classnames(className, styles.box)}>
      <div ref={letfRef} className={styles.aside}>
        <div className={styles.header}>
          <div className={styles.currentNameBox}>
            <div className={styles.databaseName}>
              {databaseDetaile?.alias}
            </div>
            {databaseDetaile?.alias && <Iconfont code="&#xe7b1;"></Iconfont>}
          </div>
          <div className={styles.searchBox}>
            <SearchInput placeholder='搜索'></SearchInput>
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
        <div className={styles.databaseQuery}>
          <div ref={monacoEditorBox} className={styles.monacoEditor}>
            <MonacoEditor getEditor={getEditor}></MonacoEditor>
          </div>
          <DraggableDivider callback={callback} direction='row' min={200} volatileRef={monacoEditorBox} />
          <div className={styles.searchResult}>
            <div className={styles.resultHeader}>
              <Tabs
                onChange={onChange}
                tabs={makerResultHeaderList()}
              />
            </div>
            <div className={styles.resultContent}>
              <LoadingContent data={tableData} handleEmpty>
                <div className={styles.tableBox}>
                  <Table columns={columns} dataSource={tableData} size="middle" />
                </div>
              </LoadingContent>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
});
