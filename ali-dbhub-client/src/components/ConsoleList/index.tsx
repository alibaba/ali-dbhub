import React, { memo, useEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import ModifyTable from '@/components/ModifyTable/ModifyTable';
import DatabaseQuery from '@/components/DatabaseQuery';
import AppHeader from '@/components/AppHeader';

import historyService from '@/service/history';

import { Tabs, Modal, Button, Input } from 'antd';
import {
  ISavedConsole,
  IConsole,
  IEditTableConsole,
  ISQLQueryConsole,
} from '@/types';
import { TabOpened, ConsoleType, ConsoleStatus, DatabaseTypeCode } from '@/utils/constants';

interface Iprops {
  className?: string;
}

export default memo<Iprops>(function ConsoleList(props) {
  const [windowList, setWindowList] = useState<IConsole[]>([]);
  const [activeKey, setActiveKey] = useState<string>();
  const [open, setOpen] = useState<boolean>(false);

  useEffect(()=>{
     // setOpen(boolean)
  },['usereducer'])

  useEffect(() => {
    getConsoleList();
  }, []);

  function getConsoleList() {
    let p = {
      pageNo: 1,
      pageSize: 999,
      // tabOpened: TabOpened.IS_OPEN,  
    };

    historyService.getSaveList(p).then((res) => {
      setWindowList(
        res.data?.map((item, index) => {
          if (index === 0) {
            setActiveKey(item.id + '');
          }
          return {
            consoleId: item.id,
            ddl: '111',
            name: item.name,
            key: item.id + '',
            type: ConsoleType.SQLQ,
            DBType: item.type,
            databaseName: item.databaseName,
            dataSourceId: item.dataSourceId,
          };
        }),
      );
    });
  }

  function renderCurrentTab(i: IConsole) {
    if (i.type === 'editTable') {
      return <ModifyTable data={i as IEditTableConsole}></ModifyTable>;
    } else {
      return (
        <DatabaseQuery
          // treeNodeClickMessage={treeNodeClickMessage}
          // setTreeNodeClickMessage={setTreeNodeClickMessage}
          windowTab={i as ISQLQueryConsole}
          key={i.key}
          activeTabKey={activeKey!}
        />
      );
    }
  }

  const onChangeTab = (newActiveKey: string) => {
    setActiveKey(newActiveKey);
  };

  const onEdit = (targetKey: any, action: 'add' | 'remove') => {
    if (action === 'add') {
      setOpen(true);
    } else {
      closeWindowTab(targetKey);
    }
  };

  const closeWindowTab = (targetKey: string) => {
    let newActiveKey = activeKey;
    let lastIndex = -1;
    windowList.forEach((item, i) => {
      if (item.key === targetKey) {
        lastIndex = i - 1;
      }
    });
    const newPanes = windowList.filter((item) => item.key !== targetKey);
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
      tabOpened: 'n',
    };
    historyService.updateWindowTab(p);
  };

  function createdCallback(newConsole: IConsole) {
    setWindowList([...windowList, newConsole]);
    setActiveKey(newConsole.key);
    setOpen(false);
    // usereducer触发搜索列表
  }

  return <div className={styles.box}>
    <AppHeader className={styles.appHeader} showRight={false}>
      <div className={styles.tabsBox}>
        <Tabs
          type="editable-card"
          onChange={onChangeTab}
          activeKey={activeKey}
          onEdit={onEdit}
          items={windowList.map(t => {
            return {
              key: t.key,
              label: t.name
            }
          })}
        ></Tabs>
      </div>
    </AppHeader>
    <div className={styles.databaseQueryBox}>
      {windowList?.map((i: IConsole, index: number) => {
        return (
          <div
            key={index}
            className={classnames(styles.windowContent, {
              [styles.concealTab]: activeKey !== i.key,
            })}
          >
            {renderCurrentTab(i)}
          </div>
        );
      })}
    </div>
    <CreateConsoleModal
      open={open}
      setOpen={setOpen}
      createdCallback={createdCallback}
    />
  </div>

});

export interface ICreateConsoleModal {
  createdCallback: (newConsole: IConsole) => void;
  setOpen: (status: boolean) => void;
  open: boolean;
}

export function CreateConsoleModal({ createdCallback, open, setOpen }: ICreateConsoleModal) {
  const [consoleName, setConsoleName] = useState<string>();

  function handleOk() {
    createConsole();
  }

  function handleCancel() {
    setOpen(false);
  }

  function createConsole() {
    if (!consoleName) {
      return
    }

    let p = {
      name: 'windowName',
      type: DatabaseTypeCode.MYSQL,
      dataSourceId: 1,
      databaseName: '01_01',
      status: ConsoleStatus.DRAFT,
      ddl: 'SELECT * FROM',
      tabOpened: TabOpened.IS_OPEN,
    };

    historyService.saveWindowTab(p).then((res) => {
      const newConsole: IConsole = {
        name: consoleName!,
        key: res.toString(),
        type: ConsoleType.SQLQ,
        DBType: DatabaseTypeCode.MYSQL,
        databaseName: '01_01',
        dataSourceId: 1,
        consoleId: res,
        ddl: 'SELECT * FROM',
      };
      createdCallback(newConsole);
      setConsoleName('');
    });
  }

  return <Modal
    title="新建查询控制台"
    open={open}
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
    <Input value={consoleName} onChange={(e) => { setConsoleName(e.target.value) }} />
  </Modal>
}
