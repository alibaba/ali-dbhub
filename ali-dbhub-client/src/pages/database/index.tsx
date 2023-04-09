import React, { memo, useEffect, useState, useRef } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { history, useParams } from 'umi';
import {
  Button,
  DatePicker,
  Input,
  Table,
  Modal,
  Tabs,
  Dropdown,
  message,
  Tooltip,
} from 'antd';
import i18n from '@/i18n';
import Iconfont from '@/components/Iconfont';
import Tree from '@/components/Tree';
import MonacoEditor, {
  setEditorHint,
  IHintData,
} from '@/components/MonacoEditor';
import DraggableDivider from '@/components/DraggableDivider';
import OperationTableModal, {
  IOperationData,
} from '@/components/OperationTableModal';
import GlobalAddMenu from '@/components/GlobalAddMenu';
import ConsoleList from '@/components/ConsoleList';
import SearchInput from '@/components/SearchInput';
import { IConnectionBase, ITreeNode, IWindowTab, IDB, IConsole } from '@/types';
const monaco = require('monaco-editor/esm/vs/editor/editor.api');
import { language } from 'monaco-editor/esm/vs/basic-languages/sql/sql';
const { keywords } = language;
import DatabaseContextProvider from '@/context/database';

interface IProps {
  className?: any;
}
type ITabType = 'sql' | 'editTable';

interface IEditTableConsole {
  label: string;
  key: string;
  tabType: ITabType;
  id: number;
  operationData: any;
}

let monacoEditorExternalList: any = {};

export default memo<IProps>(function DatabasePage({ className }) {
  const leftRef = useRef<HTMLDivElement | null>(null);
  const [activeKey, setActiveKey] = useState<string>();
  const [openDropdown, setOpenDropdown] = useState(false);
  const [treeNodeClickMessage, setTreeNodeClickMessage] =
    useState<ITreeNode | null>(null);
  const [isUnfold, setIsUnfold] = useState(true);
  const [addTreeNode, setAddTreeNode] = useState<ITreeNode[]>();
  const treeRef = useRef<any>();

  const closeDropdownFn = () => {
    setOpenDropdown(false);
  };

  useEffect(() => {
    if (openDropdown) {
      document.documentElement.addEventListener('click', closeDropdownFn);
    }
    return () => {
      document.documentElement.removeEventListener('click', closeDropdownFn);
    };
  }, [openDropdown]);

  const moveLeftAside = () => {
    const databaseLeftAside = document.getElementById('database-left-aside');
    if (databaseLeftAside) {
      if (databaseLeftAside.offsetWidth === 0) {
        databaseLeftAside.style.width = '250px';
        setIsUnfold(true);
      } else {
        databaseLeftAside.style.width = '0px';
        setIsUnfold(false);
      }
    }
  };

  const callback = () => {
    monacoEditorExternalList[activeKey!] &&
      monacoEditorExternalList[activeKey!].layout();
  };

  const searchTable = (value: string) => {
    treeRef.current?.filtrationDataTree(value);
  };

  function nodeDoubleClick(data: ITreeNode) {
    setTreeNodeClickMessage(data);
  }

  function refresh() {
    treeRef.current?.getDataSource();
  }

  function getAddTreeNode(data: ITreeNode) {
    setAddTreeNode([data]);
  }

  return (
    <DatabaseContextProvider>
      <div className={classnames(className, styles.box)}>
        <div ref={leftRef} className={styles.asideBox} id="database-left-aside">
          <div className={styles.aside}>
            <div className={styles.header}>
              <div className={styles.searchBox}>
                <SearchInput onChange={searchTable} placeholder="搜索数据源" />
                <div
                  className={classnames(styles.refresh, styles.button)}
                  onClick={refresh}
                >
                  <Iconfont code="&#xec08;" />
                </div>
                <Dropdown
                  overlay={<GlobalAddMenu getAddTreeNode={getAddTreeNode} />}
                  trigger={['click']}
                >
                  <div
                    onClick={() => setOpenDropdown(true)}
                    className={classnames(styles.create, styles.button)}
                  >
                    <Iconfont code="&#xe631;" />
                  </div>
                </Dropdown>
              </div>
            </div>
            <div className={styles.overview}>
              <Iconfont code="&#xe63d;" />
              <span>{i18n('connection.button.overview')}</span>
            </div>
            <Tree
              cRef={treeRef}
              className={styles.tree}
              addTreeData={addTreeNode}
            />
          </div>
        </div>
        <DraggableDivider callback={callback} volatileRef={leftRef} />
        <div className={styles.main}>
          <ConsoleList />
        </div>
      </div>
      <OperationTableModal />
    </DatabaseContextProvider>
  );
});
