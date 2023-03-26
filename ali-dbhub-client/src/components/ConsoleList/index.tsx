import React, { memo, useEffect, useState, useContext } from 'react';
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

import { DatabaseContext } from '@/context/database'

interface Iprops {
  className?: string;
}

export default memo<Iprops>(function ConsoleList(props) {
  const { setcreateConsoleDialog } = useContext(DatabaseContext);
  const [windowList, setWindowList] = useState<IConsole[]>([]);
  const [activeKey, setActiveKey] = useState<string>();

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
        // <DatabaseQuery
        //   // treeNodeClickMessage={treeNodeClickMessage}
        //   // setTreeNodeClickMessage={setTreeNodeClickMessage}
        //   windowTab={i as ISQLQueryConsole}
        //   key={i.key}
        //   activeTabKey={activeKey!}
        // />
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
    // if (action === 'add') {
    //   setcreateConsoleDialog(true)
    // } else {
    //   closeWindowTab(targetKey);
    // }
    if (action === 'remove') {
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
  }

  return <div className={styles.box}>
    <AppHeader className={styles.appHeader} showRight={false}>
      <div className={styles.tabsBox}>
        {
          !!windowList.length &&
          <Tabs
            hideAdd
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
        }
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
    <CreateConsoleModal createdCallback={createdCallback} />
  </div>

});

export function CreateConsoleModal({ createdCallback }: { createdCallback: (newConsole: IConsole) => void }) {
  const { model, setcreateConsoleDialog } = useContext(DatabaseContext);
  const { createConsoleDialog } = model
  const [consoleName, setConsoleName] = useState<string>();

  function handleOk() {
    createConsole();
  }

  function handleCancel() {
    setcreateConsoleDialog(false)
  }

  function createConsole() {
    if (!consoleName) {
      return
    }

    if (!createConsoleDialog) {
      return
    }

    let p = {
      name: 'windowName',
      type: DatabaseTypeCode.MYSQL,
      dataSourceId: createConsoleDialog.dataSourceId,
      databaseName: createConsoleDialog?.databaseName,
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
        databaseName: createConsoleDialog.databaseName,
        dataSourceId: createConsoleDialog.dataSourceId,
        consoleId: res,
        ddl: 'SELECT * FROM',
      };
      createdCallback(newConsole);
      setcreateConsoleDialog(false);
      setConsoleName('');
    });
  }

  return <Modal
    title="新建查询控制台"
    open={!!createConsoleDialog}
    onOk={handleOk}
    onCancel={handleCancel}
    width={400}
    style={{ top: '20%' }}
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
