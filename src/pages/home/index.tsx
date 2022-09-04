import React, { Children, useEffect, useState } from 'react';
import styles from './index.less';
import { history } from 'umi';
import Iconfont from '@/components/Iconfont';
import classnames from 'classnames';
import { Button, Menu } from 'antd';
import { AppstoreOutlined, MailOutlined, SettingOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import GlobalNav from '@/components/GlobalNav';
import BrandImage from '@/components/BrandImage';
import { INavItem } from '@/type.js';
import i18n from '@/i18n';
type MenuItem = Required<MenuProps>['items'][number];

const navConfig = [
  {
    name: '数据库管理',
    icon: '',
    id: 1,
  },
  {
    name: '数据库管理',
    icon: '',
    id: 2,
  },
  {
    name: '数据库管理',
    icon: '',
    id: 3,
  },
  {
    name: '数据库管理',
    icon: '',
    id: 4,
  },
];

export default function HomePage(props: any) {
  const [activeDatabase, setActiveDatabase] = useState<number>();

  useEffect(()=>{
    document.documentElement.setAttribute('theme', localStorage.getItem('theme') || 'default');
    document.documentElement.setAttribute('primary-color', localStorage.getItem('primary-color') || 'polar-blue');
  },[])

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

  function switchingNav(item: INavItem) {
    history.push({
      pathname: '/database/111',
    });
    setActiveDatabase(item.id);
  }

  const onClick: MenuProps['onClick'] = e => {
    console.log('click', e);
  };


  return (
    <div className={styles.page}>
      <div className={styles.layoutLeft}>
        <div className={styles.brandImageBox}>
          <BrandImage></BrandImage>
        </div>
        <div className={styles.database}>
          {navConfig.map((item) => {
            return (
              <li
                key={item.id}
                className={classnames({
                  [styles.activeDatabase]: item.id == activeDatabase,
                })}
                onClick={switchingNav.bind(null, item)}
              >
                <span>{item.name}</span>
              </li>
            );
          })}
        </div>
        <div className={styles.createDatabase}>
          <Iconfont className={styles.createIcon} code="&#xe727;" />
          <span>{i18n('home.title.link')}</span>
        </div>
        <div className={styles.createDatabase}>
          <Iconfont className={styles.createIcon} code="&#xe93f;" />
          <span>{i18n('home.title.query')}</span>
        </div>
      </div>
      <div className={styles.treeBox}>
      <Menu onClick={onClick}  mode="inline" items={items} />
      </div>
      <div className={styles.layoutRight}>
        <GlobalNav place="header"></GlobalNav>
        <div className={styles.main}>{props.children}</div>
      </div>
    </div>
  );
}
