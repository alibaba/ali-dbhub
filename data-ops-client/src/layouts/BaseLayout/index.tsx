import React, { Children, useEffect, useState, PropsWithChildren } from 'react';
import styles from './index.less';
import { history } from 'umi';
import Iconfont from '@/components/Iconfont';
import classnames from 'classnames';
import { Button, Menu } from 'antd';
import Setting from '@/components/Setting';
import BrandLogo from '@/components/BrandLogo';
import i18n from '@/i18n';

interface Iprops {

}

interface INavItem {
  title: string,
  icon: string,
  path: string,
}

const LNKConfig: INavItem[] = [
  {
    title: i18n('home.nav.database'),
    icon: '\ue759',
    path: '/connection',
  },
  {
    title: i18n('home.nav.mySql'),
    icon: '\ue759',
    path: '/sql-history',
  },
];

export default function BaseLayout({ children }: PropsWithChildren<Iprops>) {
  const [activeNav, setActiveNav] = useState<string>(LNKConfig[0].path);

  useEffect(() => { }, []);

  function switchingNav(item: INavItem) {
    history.push(item.path);
    setActiveNav(item.path);
  }

  return (
    <div className={styles.page}>
      <div className={styles.layoutLeft}>
        <a href="/">
          <BrandLogo className={styles.brandLogo} />
        </a>
        <ul className={styles.navList}>
          {LNKConfig.map((item) => {
            return (
              <li
                key={item.path}
                className={classnames({
                  [styles.activeNav]: item.path == activeNav,
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
        <div className={styles.main}>
          {children}
        </div>
      </div>
    </div>
  );
}
