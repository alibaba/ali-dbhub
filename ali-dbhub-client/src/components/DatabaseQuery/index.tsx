import React, { memo, useState, useRef, useEffect } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { history, useParams } from 'umi';
import { IConnectionBase, ITreeNode, IWindowTab, IDB, ISQLQueryConsole } from '@/types'
import { message, Tooltip } from 'antd';
import { DatabaseTypeCode, TreeNodeType, WindowTabStatus, OSType } from '@/utils/constants';
import Iconfont from '@/components/Iconfont';
import MonacoEditor, { setEditorHint, IHintData } from '@/components/MonacoEditor';
import DraggableDivider from '@/components/DraggableDivider';
import SearchResult from '@/components/SearchResult';
import LoadingContent from '@/components/Loading/LoadingContent';
import mysqlServer from '@/service/mysql';
const monaco = require('monaco-editor/esm/vs/editor/editor.api');
import { format } from 'sql-formatter';
import historyServer from '@/service/history';
import { OSnow } from '@/utils'

export interface IDatabaseQueryProps {
  activeTabKey: string;
  windowTab: ISQLQueryConsole;
  treeNodeClickMessage?: ITreeNode | null;
  setTreeNodeClickMessage?: Function;
}

interface IProps extends IDatabaseQueryProps {
  className?: string;
}

let monacoEditorExternalList: any = {}

export default memo<IProps>(function DatabaseQuery(props) {
  const { activeTabKey, windowTab, treeNodeClickMessage, setTreeNodeClickMessage } = props
  const params: { id: string, type: string } = useParams();
  const [manageResultDataList, setManageResultDataList] = useState<any>([]);
  const monacoEditorBox = useRef<HTMLDivElement | null>(null);
  const monacoEditor = useRef<any>(null);
  const monacoHint = useRef<any>(null);

  useEffect(() => {
    if (windowTab.consoleId !== +activeTabKey) {
      return
    }
    connectConsole();
    getTableList();
  }, [activeTabKey])

  useEffect(() => {
    const nodeData = treeNodeClickMessage
    if (nodeData && windowTab.consoleId === +activeTabKey) {
      const model = monacoEditor.current.getModel(monacoEditor.current)
      const value = model.getValue()
      if (nodeData.nodeType == TreeNodeType.TABLE) {
        if (value == 'SELECT * FROM') {
          model.setValue(`SELECT * FROM ${nodeData.name};`)
        } else {
          model.setValue(`${value}\nSELECT * FROM ${nodeData.name};`)
        }
      } else if (nodeData.nodeType == TreeNodeType.LINE) {
        if (value == 'SELECT * FROM') {
          model.setValue(`SELECT * FROM ${nodeData?.parent?.name} WHERE ${nodeData.name} = ''`)
        } else {
          model.setValue(`${value}\nSELECT * FROM ${nodeData?.parent?.name} WHERE ${nodeData.name} = ''`)
        }
      }
      setTreeNodeClickMessage?.(null)
    }
  }, [treeNodeClickMessage])

  const connectConsole = () => {
    let p = {
      consoleId: windowTab.consoleId,
      dataSourceId: windowTab.dataSourceId,
      databaseName: windowTab.databaseName,
    }
    mysqlServer.connectConsole(p);
  }

  const getTableList = () => {

    let p = {
      dataSourceId: windowTab.dataSourceId!,
      databaseName: windowTab.databaseName!,
      pageNo: 1,
      pageSize: 999,
    }

    mysqlServer.getList(p).then(res => {
      const tableList = res.data?.map(item => {
        return {
          name: item.name,
          key: item.name,
        }
      })
      disposalEditorHintData(tableList)
    })
  }

  const disposalEditorHintData = (tableList: any) => {
    try {
      monacoHint.current?.dispose();
      const myEditorHintData: any = {};
      tableList?.map((item: any) => {
        myEditorHintData[item.name] = []
      })
      monacoHint.current = setEditorHint(myEditorHintData);
    }
    catch {

    }
  }

  const getEditor = (editor: any) => {
    monacoEditor.current = editor
    monacoEditorExternalList[activeTabKey] = editor
    const model = editor.getModel(editor)
    model.setValue(localStorage.getItem(`window-sql-${windowTab.dataSourceId}-${windowTab.databaseName}-${windowTab.consoleId}`) || windowTab.ddl || '')
  }

  const callback = () => {
    monacoEditor.current && monacoEditor.current.layout()
  }

  const getMonacoEditorValue = () => {
    if (monacoEditor?.current?.getModel) {
      const model = monacoEditor?.current.getModel(monacoEditor?.current)
      const value = model.getValue()
      return value
    }
  }

  // 获取选中区域的值
  const getSelectionVal = () => {
    const selection = monacoEditor.current.getSelection() // 获取光标选中的值
    const { startLineNumber, endLineNumber, startColumn, endColumn } = selection
    const model = monacoEditor.current.getModel(monacoEditor.current)
    const value = model.getValueInRange({
      startLineNumber,
      startColumn,
      endLineNumber,
      endColumn,
    })
    return value
  }

  const executeSql = () => {
    const sql = getSelectionVal() || getMonacoEditorValue()
    if (!sql) {
      message.warning('请输入sql');
      return
    }
    let p = {
      sql,
      type: windowTab.DBType,
      consoleId: +windowTab.consoleId!,
      dataSourceId: windowTab?.dataSourceId as number,
      databaseName: windowTab?.databaseName
    }
    setManageResultDataList(null);
    mysqlServer.executeSql(p).then(res => {
      let p = {
        dataSourceId: windowTab?.dataSourceId,
        databaseName: windowTab?.databaseName,
        name: windowTab?.name,
        ddl: sql,
        type: windowTab.DBType,
      }
      historyServer.createHistory(p)
      setManageResultDataList(res)
    }).catch(error => {
      setManageResultDataList([])
    })
  }

  const saveWindowTabTab = () => {
    let p = {
      id: windowTab.consoleId,
      name: windowTab?.name,
      type: windowTab.DBType,
      dataSourceId: +params.id,
      databaseName: windowTab.databaseName,
      status: WindowTabStatus.RELEASE,
      ddl: getMonacoEditorValue()
    }
    historyServer.updateWindowTab(p).then(res => {
      message.success('保存成功');
    })
  }

  function formatValue() {
    const model = monacoEditor.current.getModel(monacoEditor.current)
    const value = model.getValue()
    model.setValue(format(value, {}))
  }

  function monacoEditorChange() {
    localStorage.setItem(`window-sql-${windowTab.dataSourceId}-${windowTab.databaseName}-${windowTab.consoleId}`, getMonacoEditorValue())
  }

  return <>
    <div className={classnames(styles.databaseQuery)}>
      <div className={styles.operatingArea}>
        <div className={styles.left}>
          <div>
            <Tooltip placement="bottom" title="执行">
              <Iconfont code="&#xe626;" className={styles.icon} onClick={executeSql} />
            </Tooltip>
          </div>
          <div>
            <Tooltip placement="bottom" title={OSnow() === OSType.WIN ? "保存 Ctrl + S" : "保存 CMD + S"} >
              <Iconfont code="&#xe645;" className={styles.icon} onClick={saveWindowTabTab} />
            </Tooltip>
          </div>
          <div>
            <Tooltip placement="bottom" title="格式化">
              <Iconfont code="&#xe7f8;" className={styles.icon} onClick={formatValue} />
            </Tooltip>
          </div>
        </div>
        <div className={styles.right}>
          <span>dataSourseId: {windowTab.dataSourceId}</span>
          <span>database: {windowTab.databaseName}</span>
        </div>
      </div>
      <div ref={monacoEditorBox} className={styles.monacoEditor}>
        {
          <MonacoEditor onSave={saveWindowTabTab} onChange={monacoEditorChange} id={windowTab.consoleId!} getEditor={getEditor}></MonacoEditor>
        }
      </div>
      <DraggableDivider callback={callback} direction='row' min={200} volatileRef={monacoEditorBox} />
      <div className={styles.searchResult}>
        <LoadingContent data={manageResultDataList} handleEmpty>
          <SearchResult manageResultDataList={manageResultDataList}></SearchResult>
        </LoadingContent>
      </div>
    </div>
  </>
})
