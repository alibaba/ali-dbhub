import React, { memo, useEffect, useState, useRef } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { history, useParams } from 'umi';
import { Button, DatePicker, Input, Table, Modal, Tabs, Dropdown, message, Tooltip } from 'antd';
import i18n from '@/i18n';
import AppHeader from '@/components/AppHeader';
import Iconfont from '@/components/Iconfont';
import Tree from '@/components/Tree';
import MonacoEditor, { setEditorHint, IHintData } from '@/components/MonacoEditor';
import DraggableDivider from '@/components/DraggableDivider';
import DatabaseQuery from '@/components/DatabaseQuery';
import SearchResult from '@/components/SearchResult';
import LoadingContent from '@/components/Loading/LoadingContent';
import OperationTableModal, { IOperationData } from '@/components/OperationTableModal';
import ModifyTable from '@/components/ModifyTable/ModifyTable';
import Menu, { IMenu, MenuItem } from '@/components/Menu';
import connectionServer from '@/service/connection';
import historyServer from '@/service/history';
import mysqlServer from '@/service/mysql';
import SearchInput from '@/components/SearchInput';
import { IConnectionBase, ITreeNode, IWindowTab, IDB, IConsole, ISQLQueryConsole, IEditTableConsole } from '@/types'
import { toTreeList, createRandom, approximateTreeNode, setCurrentPosition, OSnow } from '@/utils'
import { databaseType, DatabaseTypeCode, TreeNodeType, ConsoleStatus, OSType, ConsoleType } from '@/utils/constants'
const monaco = require('monaco-editor/esm/vs/editor/editor.api');
import { language } from 'monaco-editor/esm/vs/basic-languages/sql/sql';
const { keywords } = language;

interface IProps {
  className?: any;
}
type ITabType = 'sql' | 'editTable'

interface IEditTableConslo {
  label: string;
  key: string;
  tabType: ITabType;
  id: number;
  operationData: any;
}

const basicsTree: ITreeNode[] = []
let monacoEditorExternalList: any = {}

type IParams = {
  databaseName: string;
  id: string;
}

function getCurrentPageInfo(): IParams {
  const rightHash = location.hash.split('?')[1]
  const params: any = {}
  if (rightHash) {
    const arr = rightHash.split('&')
    arr.map(item => {
      const splitRes = item.split('=')
      params[splitRes[0]] = splitRes[1]
    })
  }
  return params as IParams
}

export default memo<IProps>(function DatabasePage({ className }) {
  const params: { id: string, type: DatabaseTypeCode } = useParams();
  const dataBaseType = params.type.toUpperCase() as DatabaseTypeCode;
  const letfRef = useRef<HTMLDivElement | null>(null);
  const [connectionDetaile, setConnectionDetaile] = useState<IConnectionBase>()
  const [currentDB, setCurrentDB] = useState<IDB>()
  const [activeKey, setActiveKey] = useState<string>();
  const [windowList, setWindowList] = useState<IConsole[]>([]);
  const [treeData, setTreeData] = useState<ITreeNode[]>();
  const fixedTreeData = useRef<ITreeNode[]>();
  const [DBList, setDBList] = useState<IDB[]>();
  const [openDropdown, setOpenDropdown] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(false);
  const [windowName, setWindowName] = useState<string>('console_1');
  const [operationData, setOperationData] = useState<IOperationData | null>();
  const [treeNodeClickMessage, setTreeNodeClickMessage] = useState<ITreeNode | null>(null);
  const monacoHint = useRef<any>(null);
  const [isUnfold, setIsUnfold] = useState(true);

  const closeDropdownFn = () => {
    setOpenDropdown(false)
  }

  const disposalEditorHintData = (tableList: any) => {
    try {
      monacoHint.current?.dispose();
      const myEditorHintData: any = {};
      tableList?.map((item: any) => {
        myEditorHintData[item.name] = item.children[0].children.map((item: any) => {
          return item.name
        })
      })
      monacoHint.current = setEditorHint(myEditorHintData);
    }
    catch {

    }
  }

  useEffect(() => {
    setWindowName('console_1');
  }, [isModalVisible])

  useEffect(() => {
    if (openDropdown) {
      document.documentElement.addEventListener('click', closeDropdownFn)
    }
    return () => {
      document.documentElement.removeEventListener('click', closeDropdownFn)
    }
  }, [openDropdown])

  useEffect(() => {
    if (!params.id) return
    getDetaile();
    getDBList();
  }, [params.id])

  useEffect(() => {
    if (!currentDB) return
    getWindowList()
    getTableList(currentDB)
  }, [currentDB])

  useEffect(() => {
    if (!DBList?.length) return
    const locationHash = getCurrentPageInfo()
    let flag = false;
    DBList.map(item => {
      if (locationHash.databaseName && item.name == locationHash.databaseName) {
        flag = true
        setCurrentDB(item)
      }
    })
    if (!flag) {
      setCurrentDB(DBList?.[0])
    }
  }, [DBList])

  useEffect(() => {
    if (activeKey) {
      setPageHash(currentDB?.name!, activeKey)
    }
  }, [activeKey])

  const moveLeftAside = () => {
    const databaseLeftAside = document.getElementById('database-left-aside');
    if (databaseLeftAside) {
      if (databaseLeftAside.offsetWidth === 0) {
        databaseLeftAside.style.width = '250px'
        setIsUnfold(true)
      } else {
        databaseLeftAside.style.width = '0px'
        setIsUnfold(false)
      }
    }
  }

  function setPageHash(databaseName: string, windowId: string | number) {
    // TODO:这里如果用正则替换应该会优雅一些
    if (location.hash.split('?')[1]) {
      location.hash = location.hash.split('?')[0] + `?databaseName=${databaseName}&id=${windowId}`
    } else {
      location.hash = location.hash + `?databaseName=${databaseName}&id=${windowId}`
    }
    setCurrentPosition()
  }

  const getWindowList = () => {
    let p = {
      pageNo: 1,
      pageSize: 999, // TODO: 查全部保存
      dataSourceId: params.id,
      databaseName: currentDB!.name,
      tabOpened: 'y',
    }
    historyServer.getSaveList(p).then(res => {
      const locationHash = getCurrentPageInfo();
      if (res?.data?.length) {
        const list: IConsole[] = res.data.map(item => {
          return {
            dataSourceId: +params.id,
            label: item.name,
            key: item.id.toString(),
            consoleId: item.id,
            DBType: item.type,
            ddl: item.ddl,
            type: ConsoleType.SQLQ,
            databaseName: currentDB!.name,
          }
        })
        let flag = false;
        list?.map(item => {
          if (locationHash.id && item.key == locationHash.id) {
            setActiveKey(item.key)
            flag = true
          }
        })
        if (!flag) {
          setActiveKey(list?.[0]?.key)
        }
        setWindowList(list)
      } else {
        addWindowTab([])
      }
    })
  }

  const getDBList = () => {
    connectionServer.getDBList({
      id: params.id
    }).then(res => {
      setDBList(res.map(item => {
        return {
          ...item,
          databaseType: params.type
        }
      }))
    })
  }

  const getTableList = (currentDB: IDB) => {
    setTreeData(undefined)
    let p = {
      dataSourceId: +params.id,
      databaseName: currentDB?.name,
      pageNo: 1,
      pageSize: 10000,
    }

    return mysqlServer.getList(p).then(res => {
      const tableList: ITreeNode[] = res.data?.map(item => {
        return {
          name: item.name,
          nodeType: TreeNodeType.TABLE,
          key: item.name,
          children: [
            {
              key: '1',
              name: i18n('common.text.column'),
              nodeType: TreeNodeType.LINETOTAL,
              children: toTreeList({
                data: item.columnList, nodeType: TreeNodeType.LINE, parent: {
                  name: item.name,
                  nodeType: TreeNodeType.TABLE,
                  key: item.name
                }
              })
            },
            {
              key: '2',
              name: i18n('common.text.indexes'),
              nodeType: TreeNodeType.INDEXESTOTAL,
              children: toTreeList({
                data: item.indexList, nodeType: TreeNodeType.INDEXES, parent: {
                  name: item.name,
                  nodeType: TreeNodeType.TABLE,
                  key: item.name
                }
              })
            }
          ]
        }
      })

      fixedTreeData.current = tableList
      setTreeData(tableList)
      disposalEditorHintData(tableList)
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
    monacoEditorExternalList[activeKey!] && monacoEditorExternalList[activeKey!].layout()
  }

  const addWindowTab = (windowList: IConsole[]) => {
    let p = {
      name: windowName,
      type: dataBaseType,
      dataSourceId: +params.id,
      databaseName: currentDB?.name!,
      status: ConsoleStatus.DRAFT,
      ddl: 'SELECT * FROM',
      tabOpened: 'y' as 'y' | 'n'
    }
    historyServer.saveWindowTab(p).then(res => {
      const newWindow: IConsole = {
        label: windowName,
        key: res.toString(),
        type: ConsoleType.EDITTABLE,
        DBType: dataBaseType,
        databaseName: currentDB?.name!,
        dataSourceId: +params.id,
        consoleId: res,
        ddl: 'SELECT * FROM',
      }
      setWindowList([
        ...windowList,
        newWindow
      ])
      setActiveKey(res.toString());
      setIsModalVisible(false);
    })
  };

  const closeWindowTab = (targetKey: string) => {
    let newActiveKey = activeKey;
    let lastIndex = -1;
    windowList.forEach((item, i) => {
      if (item.key === targetKey) {
        lastIndex = i - 1;
      }
    });
    const newPanes = windowList.filter(item => item.key !== targetKey);
    if (newPanes.length && newActiveKey === targetKey) {
      if (lastIndex >= 0) {
        newActiveKey = newPanes[lastIndex].key;
      } else {
        newActiveKey = newPanes[0].key;
      }
    }
    setWindowList(newPanes);
    setActiveKey(newActiveKey);
    let p: any = {
      id: targetKey,
      tabOpened: 'n'
    };
    historyServer.updateWindowTab(p);
    // historyServer.deleteWindowTab(p);
  };

  const onEdit = (targetKey: any, action: 'add' | 'remove') => {
    if (action === 'add') {
      setIsModalVisible(true)
    } else {
      closeWindowTab(targetKey);
    }
  };

  const onChangeTab = (newActiveKey: string) => {
    setActiveKey(newActiveKey);
  };

  const searchTable = (value: string) => {
    if (fixedTreeData.current?.length) {
      setTreeData(approximateTreeNode(fixedTreeData.current, value));
    }
  }

  const DBListMenu = () => {
    const switchDB = (item: IDB) => {
      setOpenDropdown(false)
      if (item.name !== currentDB?.name) {
        setCurrentDB(item)
        getTableList(item)
      }
    }

    return <Menu>
      {
        DBList?.map(item => {
          return <MenuItem key={item.name} onClick={switchDB.bind(null, item)}>
            <div className={styles.switchDBItem}>
              <div className={styles.DBName}>{item.name}</div>
            </div>
          </MenuItem>
        })
      }
    </Menu>
  }

  function handleOk() {
    addWindowTab(windowList);
  }

  function handleCancel() {
    setIsModalVisible(false);
  }

  function nodeDoubleClick(data: ITreeNode) {
    setTreeNodeClickMessage(data)
  }

  function openOperationTableModal(value: IOperationData) {
    let data = {
      ...value,
      database: currentDB,
      connectionDetaile: connectionDetaile
    }
    setOperationData(data)
    if (value.type === 'edit') {
      let flag = false
      windowList?.map(item => {
        if (item.key === `editTable-${value.nodeData?.name}`) {
          flag = true
        }
      })
      const { databaseName, id } = getCurrentPageInfo();
      if (!flag) {
        const newData: IEditTableConsole = {
          label: `编辑表-${value.nodeData?.name}`,
          key: `editTable-${value.nodeData?.name}`,
          type: ConsoleType.EDITTABLE,
          DBType: params.type,
          databaseName: databaseName,
          dataSourceId: +params.id,
          tableData: value.nodeData!,
        }
        setWindowList([...windowList, newData])
        setActiveKey(`editTable-${value.nodeData?.name}`)
      } else {
        setActiveKey(`editTable-${value.nodeData?.name}`)
      }
    }
    if (value.type === 'delete') {
      Modal.confirm({
        title: '你确定要删除该表吗',
        onOk: () => {
          let p = {
            tableName: value?.nodeData?.name!,
            dataSourceId: connectionDetaile?.id!,
            databaseName: currentDB?.name!
          }
          mysqlServer.deleteTable(p).then(res => {
            getTableList(currentDB!);
            message.success('删除成功');
          })
        },
        cancelText: '取消',
        okText: '确认'
      });
    }
  }

  function createTable() {
    let flag = false
    windowList?.map(item => {
      if (item.key === `newTable-${currentDB?.name}`) {
        flag = true
      }
    })
    if (!flag) {
      setWindowList([...windowList, {
        label: `新建表-${currentDB?.name}`,
        key: `newTable-${currentDB?.name}`,
        tabType: 'editTable',
        id: `newTable-${currentDB?.name}`,
      } as any])
      setActiveKey(`newTable-${currentDB?.name}`)
    } else {
      setActiveKey(`newTable-${currentDB?.name}`)
    }
    // setOperationData({
    //   type: 'new',
    //   database: currentDB,
    //   connectionDetaile: connectionDetaile,
    //   callback: getTableList
    // })
  }

  function renderCurrentTab(i: IConsole) {
    if (i.type === 'editTable') {
      return <ModifyTable data={i as IEditTableConsole}></ModifyTable>
    } else {
      return <DatabaseQuery
        treeNodeClickMessage={treeNodeClickMessage}
        setTreeNodeClickMessage={setTreeNodeClickMessage}
        windowTab={i as ISQLQueryConsole}
        key={i.key}
        activeTabKey={activeKey!}
      />
    }
  }

  return <>
    <div className={classnames(className, styles.box)}>
      <div ref={letfRef} className={styles.asideBox} id="database-left-aside">
        <div className={styles.aside}>
          <div className={styles.header}>
            <Dropdown open={openDropdown} overlay={DBListMenu} trigger={['click']}>
              <div className={styles.currentNameBox} onClick={(event) => { event.stopPropagation(); setOpenDropdown(true) }}>
                {
                  currentDB &&
                  <div className={styles.DBLogo} style={{ backgroundImage: `url(${databaseType[params.type.toUpperCase()]?.img})` }}></div>
                }
                <div className={styles.databaseName}>
                  {currentDB?.name}
                </div>
                {(DBList?.length || 0) > 1 && <Iconfont code="&#xe7b1;"></Iconfont>}
              </div>
            </Dropdown>

            <div className={styles.searchBox}>
              <SearchInput onChange={searchTable} placeholder={i18n('common.text.search')}></SearchInput>
              <div className={classnames(styles.refresh, styles.button)} onClick={() => { currentDB && getTableList(currentDB) }}>
                <Iconfont code="&#xec08;"></Iconfont>
              </div>
              <div onClick={createTable} className={classnames(styles.create, styles.button)}>
                <Iconfont code="&#xe631;"></Iconfont>
              </div>
            </div>
          </div>
          <div className={styles.overview}>
            <Iconfont code="&#xe63d;"></Iconfont>
            <span>{i18n('connection.button.overview')}</span>
          </div>
          {/* <Tree
            openOperationTableModal={openOperationTableModal}
            nodeDoubleClick={nodeDoubleClick}
            treeData={treeData}
          /> */}
          <Tree className={styles.tree}></Tree>
        </div>
      </div>
      <DraggableDivider callback={callback} volatileRef={letfRef} />
      <div className={styles.main}>
        <AppHeader className={styles.appHeader} showRight={false}>
          <div className={styles.tabsBox}>
            <Tabs
              type="editable-card"
              onChange={onChangeTab}
              activeKey={activeKey}
              onEdit={onEdit}
              items={windowList}
            >
            </Tabs>
          </div>
        </AppHeader>
        <div className={styles.databaseQueryBox}>
          {
            currentDB &&
            windowList?.map((i: IConsole, index: number) => {
              return <div key={index} className={classnames(styles.windowContent, { [styles.concealTab]: activeKey !== i.key })}>
                {renderCurrentTab(i)}
              </div>
            })
          }
        </div>
        <div className={styles.footer}>
          <div className={classnames({ [styles.reversalIconBox]: !isUnfold }, styles.iconBox)} onClick={moveLeftAside}>
            <Iconfont code='&#xeb93;'></Iconfont>
          </div>
          <div></div>
        </div>
      </div>

    </div >
    <Modal
      title="新窗口名称"
      open={isModalVisible}
      onOk={handleOk}
      onCancel={handleCancel}
      footer={
        <>
          <Button onClick={handleCancel} className={styles.cancel}>
            取消
          </Button>
          <Button type="primary" onClick={handleOk} className={styles.cancel}>
            添加
          </Button>
        </>
      }
    >
      <Input value={windowName} onChange={(e) => { setWindowName(e.target.value) }} />
    </Modal>
    {
      (operationData?.type === 'new' || operationData?.type === 'export') &&
      <OperationTableModal
        setOperationData={setOperationData}
        operationData={operationData!}
      />
    }
  </>
});
