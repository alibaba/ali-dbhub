import React, { memo, useState } from 'react';
import styles from './index.less';
import BrandLogo from '@/components/BrandLogo';
import Iconfont from '@/components/Iconfont';
import i18n from '@/i18n';
import classnames from 'classnames';
import { history } from 'umi';

export default function HomePage({children}){

  const [activeNav, setActiveNav] = useState<string>('databaseAdmin');

  const navConfig = [
    {
      title: i18n('home.nav.database'),
      icon: '\ue759',
      code: 'databaseAdmin',
      path: '/databaseList',
    },
    {
      title: '我的',
      icon: '\ue759',
      code: 'SQLHistory',
      path: '/SQLHistory', // TODO
    },
    {
      title: i18n('home.nav.database'),
      icon: '\ue759',
      code: 'databaseAdmin1',
      path: '/databaseList',
    },
    {
      title: '我的保存',
      icon: '\ue759',
      code: 'mySQL1',
      path: '/', // TODO
    },
    {
      title: i18n('home.nav.database'),
      icon: '\ue759',
      code: 'databaseAdmin2',
      path: '/databaseList',
    }
  ];

  function switchingNav(item) {
    history.push({
      pathname: item.path,
    });
    setActiveNav(item.code);
  }

  return <div className={styles.page}>
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
    </div>
    <div className={styles.layoutRight}>
      {children}
    </div>
  </div>
}