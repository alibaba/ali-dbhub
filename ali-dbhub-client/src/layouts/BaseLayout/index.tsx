import React, { Children, useEffect, useState, PropsWithChildren } from 'react';
import styles from './index.less';
import { history } from 'umi';
import Iconfont from '@/components/Iconfont';
import classnames from 'classnames';
import { Button, Menu } from 'antd';
import Setting from '@/components/Setting';
import BrandLogo from '@/components/BrandLogo';
import i18n from '@/i18n';

interface Iprops { }

interface INavItem {
  title: string;
  icon: string;
  path: string;
  openBrowser?: boolean;
}

const LNKConfig: INavItem[] = [
  {
    title: i18n('home.nav.database'),
    icon: '\uec57',
    path: '/connection',
  },
  {
    title: i18n('home.nav.myHistory'),
    icon: '\ue610',
    path: '/sql-history',
  },
  {
    title: i18n('home.nav.github'),
    icon: '\ue885',
    path: 'https://github.com/alibaba/ali-dbhub',
    openBrowser: true,
  },
  {
    title: '后台管理',
    icon: "\ue66d",
    path: '/manage',
  },
];

export default function BaseLayout({ children }: PropsWithChildren<Iprops>) {
  const [activeNav, setActiveNav] = useState<string>(LNKConfig[0].path);

  useEffect(() => { }, []);

  function switchingNav(item: INavItem) {
    if (item.openBrowser) {
      window.open(item.path);
    } else {
      history.push(item.path);
      setActiveNav(item.path);
    }
  }

  function jumpHome() {
    history.push('/');
  }

  return (
    <div className={styles.page}>
      <div className={styles.layoutLeft}>
        {/* <BrandLogo onClick={jumpHome} className={styles.brandLogo} /> */}
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
        <div className={styles.main}>{children}</div>
      </div>
    </div>
  );
}
