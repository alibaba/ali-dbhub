import React, { memo, useContext, useState } from 'react';
import classnames from 'classnames';
import styles from './index.less';
import Iconfont from '../../Iconfont';
import { MenuProps, message } from 'antd';
import { Modal, Input } from 'antd';
// import { Menu } from 'antd';
import Menu, { IMenu, MenuItem } from '@/components/Menu';
import { IOperationData } from '@/components/OperationTableModal';
import { TreeNodeType } from '@/utils/constants';
import { ITreeConfigItem, ITreeConfig, treeConfig } from '@/components/Tree/treeConfig';
import { ITreeNode } from '@/types';
import { DatabaseContext } from '@/context/database';
import connectionServer from '@/service/connection';
import mysqlServer from '@/service/mysql';
import { getDataSource } from '@/components/Tree';

type MenuItem = Required<MenuProps>['items'][number];

export type Iprops = {
  className?: string;
  setIsLoading: (value: boolean) => void;
  data: ITreeNode;
  setTreeData: Function;
  nodeConfig: ITreeConfigItem | undefined;
}

function TreeNodeRightClick(props: Iprops) {
  const { className, setTreeData, data, nodeConfig, setIsLoading } = props;
  const { setcreateConsoleDialog, setOperationDataDialog, setNeedRefreshNodeTree } = useContext(DatabaseContext);
  const [verifyDialog, setVerifyDialog] = useState<boolean>();
  const [verifyTableName, setVerifyTableName] = useState<string>('');

  function refresh() {
    data.children = []
    setIsLoading(true)
    getNodeData(data).then(res => {
      setTimeout(() => {
        data.children = res;
        setIsLoading(false)
      }, 200);
    })
  }

  if (!nodeConfig) {
    return <></>
  }

  const { getNodeData } = nodeConfig;

  const tableMenu: IMenu<string>[] = [
    // {
    //   title: '设计表结构',
    //   key: 'edit',
    //   icon: '\ue60f'
    // },
    {
      title: '导出建表语句',
      key: 'export',
      icon: '\ue613'
    },
    {
      title: '删除表',
      key: 'delete',
      icon: '\ue6a7'
    }
  ]

  const dataBaseMenu: IMenu<string>[] = [
    {
      title: '新建控制台',
      key: 'newConsole',
      icon: '\ue631'
    },
    {
      title: '新建Table',
      key: 'createTable',
      icon: '\ue6b6'
    },
    {
      title: '刷新',
      key: 'refresh',
      icon: '\uec08'
    },
  ]
  const tablesMenu: IMenu<string>[] = [
    {
      title: '新建Table',
      key: 'createTable',
      icon: '\ue6b6'
    },
    {
      title: '刷新',
      key: 'refresh',
      icon: '\uec08'
    },
  ]

  const dataSourseMenu: IMenu<string>[] = [
    {
      title: '刷新',
      key: 'refresh',
      icon: '\uec08'
    },
    {
      title: '移除',
      key: 'remove',
      icon: '\ue6a7'
    },
  ]

  function closeMenu() {
    // TODO: 关闭下拉弹窗 有木有更好的方法
    const customDropdown: any = document.getElementsByClassName('custom-dropdown');
    for (let i = 0; i < customDropdown.length; i++) {
      customDropdown[i].classList.add('custom-dropdown-hidden')
    }
  }

  function tableClick(item: IMenu<string>) {
    if (item.key === 'export') {
      const operationData: IOperationData = {
        type: item.key,
        nodeData: data
      }
      if (operationData.type === 'export') {
        setOperationDataDialog(operationData);
      }
    } else if (item.key === 'delete') {
      setVerifyDialog(true)
    }
    closeMenu();
  }

  function dataBaseClick(item: IMenu<string>) {
    if (item.key === 'newConsole') {
      setcreateConsoleDialog({
        dataSourceId: data.dataSourceId!,
        databaseName: data.databaseName!,
      })
    } else if (item.key === 'refresh') {
      refresh()
    } else if (item.key === 'createTable') {
      const operationData: IOperationData = {
        type: 'new',
        nodeData: data
      }
      setOperationDataDialog(operationData)
    }
    closeMenu();
  }

  function handleOk() {
    let p = {
      tableName: verifyTableName,
      dataSourceId: data.dataSourceId!,
      databaseName: data.databaseName!
    }
    if (verifyTableName === data.tableName) {
      mysqlServer.deleteTable(p).then(res => {
        setVerifyDialog(false);
        setNeedRefreshNodeTree({
          databaseName: data.databaseName,
          dataSourceId: data.dataSourceId,
          nodeType: TreeNodeType.TABLES
        })
      })
    } else {
      message.error('输入的表名与要删除的表名不一致，请再次确认')
    }
  }

  function dataSourseClick(item: IMenu<string>) {
    if (item.key === 'refresh') {
      refresh()
    } else if (item.key === 'remove') {
      connectionServer.remove({ id: +data.key }).then(res => {
        getDataSource(setTreeData)
      })
    }
    closeMenu();
  }

  if (data.nodeType == TreeNodeType.TABLE) {
    return <div className={styles.menuBox}>
      <Modal
        title="删除确认"
        open={verifyDialog}
        onOk={handleOk}
        width={400}
        onCancel={(() => { setVerifyDialog(false) })}>
        <Input placeholder='请输入你要删除的表名' value={verifyTableName} onChange={(e) => { setVerifyTableName(e.target.value) }}></Input>
      </Modal>
      <Menu>
        {
          tableMenu.map(item => {
            return <MenuItem key={item.key} onClick={tableClick.bind(null, item)}>
              <Iconfont code={item.icon!}></Iconfont>
              {item.title}
            </MenuItem>
          })
        }
      </Menu>
    </div>
  } else if (data.nodeType == TreeNodeType.DATABASE) {
    return <div className={styles.menuBox}>
      <Menu>
        {
          dataBaseMenu.map(item => {
            return <MenuItem key={item.key} onClick={dataBaseClick.bind(null, item)}>
              <Iconfont code={item.icon!}></Iconfont>
              {item.title}
            </MenuItem>
          })
        }
      </Menu>
    </div>
  } else if (data.nodeType == TreeNodeType.DATASOURCE) {
    return <div className={styles.menuBox}>
      <Menu>
        {
          dataSourseMenu.map(item => {
            return <MenuItem key={item.key} onClick={dataSourseClick.bind(null, item)}>
              <Iconfont code={item.icon!}></Iconfont>
              {item.title}
            </MenuItem>
          })
        }
      </Menu>
    </div>
  } else if (data.nodeType == TreeNodeType.TABLES) {
    return <div className={styles.menuBox}>
      <Menu>
        {
          tablesMenu.map(item => {
            return <MenuItem key={item.key} onClick={dataBaseClick.bind(null, item)}>
              <Iconfont code={item.icon!}></Iconfont>
              {item.title}
            </MenuItem>
          })
        }
      </Menu>
    </div>

  } else {
    return <span></span>
  }
}

export default memo(TreeNodeRightClick)