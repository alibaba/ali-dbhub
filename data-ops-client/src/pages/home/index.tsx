import React, { memo, useState } from 'react';
import styles from './index.less';
import BrandLogo from '@/components/BrandLogo';
import Iconfont from '@/components/Iconfont';
import i18n from '@/i18n';
import classnames from 'classnames';
import { history } from 'umi';

export default function HomePage({ children }) {

  const [activeNav, setActiveNav] = useState<string>('connectionAdmin');

  const navConfig = [
    {
      title: i18n('home.nav.database'),
      icon: '\ue759',
      code: 'connectionAdmin',
      path: '/connection',
    },
    {
      title: '我的',
      icon: '\ue759',
      code: 'SQLHistory',
      path: '/SQLHistory', // TODO
    },
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