import React, { memo, useState, useRef, useEffect, useContext } from 'react';
import classnames from 'classnames';
import { useParams } from 'umi';
import { ISQLQueryConsole } from '@/types';
import { Divider, message, Tooltip } from 'antd';
import { TreeNodeType, WindowTabStatus, OSType } from '@/utils/constants';
import IconFont from '@/components/IconFont';
import MonacoEditor, { setEditorHint } from '@/components/MonacoEditor';
import DraggableDivider from '@/components/DraggableDivider';
import SearchResult from '@/components/SearchResult';
import LoadingContent from '@/components/Loading/LoadingContent';
import mysqlServer from '@/service/mysql';
import historyServer from '@/service/history';
import { format } from 'sql-formatter';
import { OSnow } from '@/utils';
import { DatabaseContext } from '@/context/database';
import styles from './index.less';

const monaco = require('monaco-editor/esm/vs/editor/editor.api');

export interface IDatabaseQueryProps {
  activeTabKey: string;
  windowTab: ISQLQueryConsole;
}

interface IProps extends IDatabaseQueryProps {
  className?: string;
}

let monacoEditorExternalList: any = {};

export default memo<IProps>(function DatabaseQuery(props) {
  const { model, setDblclickNodeData, setAiImportSql } =
    useContext(DatabaseContext);
  const { activeTabKey, windowTab } = props;
  const params: { id: string; type: string } = useParams();
  const [manageResultDataList, setManageResultDataList] = useState<any>([]);

  const consoleRef = useRef<HTMLDivElement>(null);
  const monacoEditor = useRef<any>(null);
  const monacoHint = useRef<any>(null);
  const { dblclickNodeData, aiImportSql } = model;

  useEffect(() => {
    if (windowTab.consoleId !== +activeTabKey) {
      return;
    }
    connectConsole();
    getTableList();
  }, [activeTabKey]);

  useEffect(() => {
    if (aiImportSql) {
      const model = monacoEditor.current.getModel(monacoEditor.current);
      const value = model.getValue();
      model.setValue(`${value}\n${aiImportSql}`);
      setAiImportSql('');
    }
  }, [aiImportSql]);

  useEffect(() => {
    if (!dblclickNodeData) {
      return;
    }
    if (
      dblclickNodeData.databaseName !== windowTab.databaseName ||
      dblclickNodeData.dataSourceId !== windowTab.dataSourceId
    ) {
      return;
    }
    const nodeData = dblclickNodeData;
    if (nodeData && windowTab.consoleId === +activeTabKey) {
      const model = monacoEditor.current.getModel(monacoEditor.current);
      const value = model.getValue();
      if (nodeData.nodeType == TreeNodeType.TABLE) {
        if (value == 'SELECT * FROM' || value == 'SELECT * FROM ') {
          model.setValue(`SELECT * FROM ${nodeData.name};`);
        } else {
          model.setValue(`${value}\nSELECT * FROM ${nodeData.name};`);
        }
      } else if (nodeData.nodeType == TreeNodeType.COLUMN) {
        if (value == 'SELECT * FROM' || value == 'SELECT * FROM ') {
          model.setValue(
            `SELECT * FROM ${nodeData?.parent?.name} WHERE ${nodeData.name} = ''`,
          );
        } else {
          model.setValue(
            `${value}\nSELECT * FROM ${nodeData?.parent?.name} WHERE ${nodeData.name} = ''`,
          );
        }
      }
      setDblclickNodeData(null);
    }
  }, [dblclickNodeData]);

  const connectConsole = () => {
    const { consoleId, dataSourceId, databaseName } = windowTab || {};
    mysqlServer.connectConsole({
      consoleId,
      dataSourceId,
      databaseName,
    });
  };

  const getTableList = () => {
    const p = {
      dataSourceId: windowTab.dataSourceId!,
      databaseName: windowTab.databaseName!,
      pageNo: 1,
      pageSize: 999,
    };

    mysqlServer.getList(p).then((res) => {
      const tableList = res.data?.map((item) => {
        return {
          name: item.name,
          key: item.name,
        };
      });
      disposalEditorHintData(tableList);
    });
  };

  const disposalEditorHintData = (tableList: any) => {
    try {
      monacoHint.current?.dispose();
      const myEditorHintData: any = {};
      tableList?.map((item: any) => {
        myEditorHintData[item.name] = [];
      });
      monacoHint.current = setEditorHint(myEditorHintData);
    } catch {}
  };

  const getEditor = (editor: any) => {
    monacoEditor.current = editor;
    monacoEditorExternalList[activeTabKey] = editor;
    const model = editor.getModel(editor);
    model.setValue(
      localStorage.getItem(
        `window-sql-${windowTab.dataSourceId}-${windowTab.databaseName}-${windowTab.consoleId}`,
      ) ||
        windowTab.ddl ||
        '',
    );
  };

  const callback = () => {
    monacoEditor.current && monacoEditor.current.layout();
  };

  /** 获取编辑器整体值 */
  const getMonacoEditorValue = () => {
    if (monacoEditor?.current?.getModel) {
      const model = monacoEditor?.current.getModel(monacoEditor?.current);
      const value = model.getValue();
      return value;
    }
  };

  /** 获取选中区域的值 */
  const getSelectionVal = () => {
    const selection = monacoEditor.current.getSelection(); // 获取光标选中的值
    const { startLineNumber, endLineNumber, startColumn, endColumn } =
      selection;
    const model = monacoEditor.current.getModel(monacoEditor.current);
    const value = model.getValueInRange({
      startLineNumber,
      startColumn,
      endLineNumber,
      endColumn,
    });
    return value;
  };

  const executeSql = () => {
    const sql = getSelectionVal() || getMonacoEditorValue();
    if (!sql) {
      message.warning('请输入SQL语句');
      return;
    }
    let p = {
      sql,
      type: windowTab.DBType,
      consoleId: +windowTab.consoleId,
      dataSourceId: windowTab?.dataSourceId as number,
      databaseName: windowTab?.databaseName,
    };
    setManageResultDataList(null);
    mysqlServer
      .executeSql(p)
      .then((res) => {
        let p = {
          dataSourceId: windowTab?.dataSourceId,
          databaseName: windowTab?.databaseName,
          name: windowTab?.name,
          ddl: sql,
          type: windowTab.DBType,
        };
        historyServer.createHistory(p);
        setManageResultDataList(res);
      })
      .catch((error) => {
        setManageResultDataList([]);
      });
  };

  const saveWindowTabTab = () => {
    let p = {
      id: windowTab.consoleId,
      name: windowTab?.name,
      type: windowTab.DBType,
      dataSourceId: +params.id,
      databaseName: windowTab.databaseName,
      status: WindowTabStatus.RELEASE,
      ddl: getMonacoEditorValue(),
    };
    historyServer.updateWindowTab(p).then((res) => {
      message.success('保存成功');
    });
  };

  const formatValue = () => {
    const model = monacoEditor.current.getModel(monacoEditor.current);
    const value = model.getValue();
    model.setValue(format(value, {}));
  };

  const monacoEditorChange = () => {
    localStorage.setItem(
      `window-sql-${windowTab.dataSourceId}-${windowTab.databaseName}-${windowTab.consoleId}`,
      getMonacoEditorValue(),
    );
  };

  const lang2SQL = () => {
    // TODO: 自然语言转化SQL
  };
  const analysisSQL = () => {
    // TODO: 解析SQL
  };

  const optimizeSQL = () => {
    // TODO: 优化SQL
  };

  const optBtn = useRef([
    /** 基础SQL命令 */
    [
      { name: '执行', icon: '\ue626', onClick: executeSql },
      {
        name: OSnow() === OSType.WIN ? '保存 Ctrl + S' : '保存 CMD + S',
        icon: '\ue645',
        onClick: saveWindowTabTab,
      },
      { name: '格式化', icon: '\ue7f8', onClick: formatValue },
    ],
    /** 自然语言转化SQL */
    // [
    //   { name: '语言转化SQL', icon: '\ue626', onClick: lang2SQL },
    //   { name: '语言转化SQL带参数', icon: '\ue626', onClick: lang2SQL },
    // ],
    // /** 解释SQL */
    // [{ name: '解析SQL', icon: '\ue626', onClick: analysisSQL }],
    // /** 优化SQL */
    // [{ name: '优化SQL', icon: '\ue626', onClick: optimizeSQL }],
  ]);

  const renderOptBtn = () => {
    let dom = [];
    for (let i = 0; i < optBtn.current.length; i++) {
      const optList = optBtn.current[i];
      const tmpDom = (optList || []).map((item: any) => (
        <Tooltip placement="bottom" title={item.name}>
          <IconFont
            code={item.icon}
            className={styles.icon}
            onClick={item.onClick}
          />
        </Tooltip>
      ));
      tmpDom.push(<Divider type="vertical" />);
      dom.push([...tmpDom]);
    }
    return dom;
  };

  return (
    <div className={classnames(styles.databaseQuery)}>
      <div ref={consoleRef} className={styles.console}>
        <div className={styles.operatingArea}>
          <div className={styles.left}>{renderOptBtn()}</div>
          <div className={styles.right}>
            <span>dataSourceName: {windowTab.dataSourceName}</span>
            <span>database: {windowTab.databaseName}</span>
          </div>
        </div>
        <div className={styles.monacoEditor}>
          <MonacoEditor
            onSave={saveWindowTabTab}
            onChange={monacoEditorChange}
            id={windowTab.consoleId!}
            getEditor={getEditor}
          />
        </div>
      </div>
      <DraggableDivider
        callback={callback}
        direction="row"
        min={200}
        volatileRef={consoleRef}
      />
      <div className={styles.searchResult}>
        <LoadingContent data={manageResultDataList} handleEmpty>
          <SearchResult manageResultDataList={manageResultDataList} />
        </LoadingContent>
      </div>
    </div>
  );
});
