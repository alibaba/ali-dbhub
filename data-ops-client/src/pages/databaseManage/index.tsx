import React, { Children, useEffect, useState } from 'react';
import styles from './index.less';
import { history } from 'umi';
import Iconfont from '@/components/Iconfont';
import classnames from 'classnames';
import { Button, Menu } from 'antd';
import Setting from '@/components/Setting';
import BrandLogo from '@/components/BrandLogo';
import { INavItem } from '@/type.js';
import i18n from '@/i18n';

const navConfig: INavItem[] = [
  {
    title: i18n('home.nav.database'),
    icon: '\ue759',
    code: 'databaseAdmin',
    path: '/databaseList',
  },
  {
    title: i18n('home.nav.mySql'),
    icon: '\ue759',
    code: 'mySQL',
    path: '/', // TODO
  },
];

export default function HomePage(props: any) {
  const [activeNav, setActiveNav] = useState<string>('databaseAdmin');

  useEffect(() => {}, []);

  function switchingNav(item: INavItem) {
    history.push({
      pathname: item.path,
    });
    setActiveNav(item.code);
  }

  return (
    <div className={styles.page}>
      <div className={styles.layoutLeft}>
        <BrandLogo className={styles.brandLogo} />
        <ul className={styles.navList}>
          {navConfig.map((item) => {
            return (
              <li
                key={item.code}
                className={classnames({
                  [styles.activeNav]: item.code == activeNav,
                })}
                onClick={switchingNav.bind(null, item)}
              > 
                <Iconfont className={styles.icon} code={item.icon} />
                <div>{item.title}</div>
              </li>
            );
          })}
        </ul>
        <div className={styles.footer}>
          <Setting className={styles.setIcon}></Setting>
        </div>
      </div>
      <div className={styles.layoutRight}>
        <div className={styles.main}>{props.children}</div>
      </div>
    </div>
  );
}
