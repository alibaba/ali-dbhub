import React, { memo } from 'react';
import classnames from 'classnames';
import styles from './index.less';
import Iconfont from '../../Iconfont';
import { AppstoreOutlined, MailOutlined, SettingOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
// import { Menu } from 'antd';
import Menu, { IMenu, MenuItem } from '@/components/Menu';
import { IOperationData } from '@/components/OperationTableModal';
import { TreeNodeType } from '@/utils/constants'

type MenuItem = Required<MenuProps>['items'][number];

export type Iprops = {
  className?: string;
  data: any;
  openOperationTableModal: any;
  loadDataObj: any;
}

function getItem(
  label: React.ReactNode,
  key?: React.Key | null,
  icon?: React.ReactNode,
  children?: MenuItem[],
  type?: 'group',
): MenuItem {
  return {
    key,
    icon,
    children,
    label,
    type,
  } as MenuItem;
}

const items: MenuItem[] = [
  getItem('Navigation One', 'sub1', <MailOutlined />, [
    getItem('Item 1', null, null, [getItem('Option 1', '1'), getItem('Option 2', '2')], 'group'),
    getItem('Item 2', null, null, [getItem('Option 3', '3'), getItem('Option 4', '4')], 'group'),
  ]),

  getItem('Navigation Two', 'sub2', <AppstoreOutlined />, [
    getItem('Option 5', '5'),
    getItem('Option 6', '6'),
    getItem('Submenu', 'sub3', null, [getItem('Option 7', '7'), getItem('Option 8', '8')]),
  ]),

  getItem('Navigation Three', 'sub4', <SettingOutlined />, [
    getItem('Option 9', '9'),
    getItem('Option 10', '10'),
    getItem('Option 11', '11'),
    getItem('Option 12', '12'),
  ]),
];



function TreeNodeRightClick(props: Iprops) {
  const { className, data, openOperationTableModal, loadDataObj } = props;

  const tableMenu: IMenu<string>[] = [
    {
      title: '设计表结构',
      key: 'edit',
    },
    {
      title: '导出建表语句',
      key: 'export',
    },
    {
      title: '删除表',
      key: 'delete',
    }
  ]

  const dataBaseMenu: IMenu<string>[] = [
    {
      title: '新建控制台',
      key: 'newConsole',
    }
  ]

  function refresh() {
    return loadDataObj[data.nodeType]?.getNodeData(data);
  }

  function tableClick(item: IMenu<string>) {
    const operationData: IOperationData = {
      type: item.key,
      nodeData: data
    }
    openOperationTableModal?.(operationData)
    // TODO: 关闭下拉弹窗 有木有更好的方法
    const customDropdown: any = document.getElementsByClassName('custom-dropdown');
    for (let i = 0; i < customDropdown.length; i++) {
      customDropdown[i].classList.add('custom-dropdown-hidden')
    }
  }

  function dataBaseClick(item: IMenu<string>) {
    if (item.key === 'newConsole') {

    }

    // TODO: 关闭下拉弹窗 有木有更好的方法
    const customDropdown: any = document.getElementsByClassName('custom-dropdown');
    for (let i = 0; i < customDropdown.length; i++) {
      customDropdown[i].classList.add('custom-dropdown-hidden')
    }
  }

  if (data.nodeType == TreeNodeType.TABLE) {
    return <div className={styles.menuBox}>
      <Menu>
        {
          tableMenu.map(item => {
            return <MenuItem key={item.key} onClick={tableClick.bind(null, item)}>{item.title}</MenuItem>
          })
        }
      </Menu>
    </div>
  } else if (data.nodeType == TreeNodeType.DATABASE) {
    return <div className={styles.menuBox}>
      <Menu>
        {
          dataBaseMenu.map(item => {
            return <MenuItem key={item.key} onClick={dataBaseClick.bind(null, item)}>{item.title}</MenuItem>
          })
        }
      </Menu>
    </div>
  } else {
    return <span></span>
  }

  // return <div className={classnames(className, styles.box)}>
  //   <Menu onClick={onClick} style={{ width: 256 }} mode="vertical" items={items} />
  // </div>
}

export default memo(TreeNodeRightClick)