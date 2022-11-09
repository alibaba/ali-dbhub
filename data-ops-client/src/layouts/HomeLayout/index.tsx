import React, { memo, useState, PropsWithChildren } from 'react';
import BrandLogo from '@/components/BrandLogo';
import Iconfont from '@/components/Iconfont';
import AppHeader from '@/components/AppHeader';
import Setting from '@/components/Setting';
import i18n from '@/i18n';
import classnames from 'classnames';
import { history, useLocation } from 'umi';
import styles from './index.less';
interface Iprops {

}

interface INavItem {
  title: string;
  icon: string,
  path: string,
}

const navConfig: INavItem[] = [
  {
    title: i18n('home.nav.database'),
    icon: '\uec57',
    path: '/connection'
  },

  {
    title: i18n('home.nav.myHistory'),
    icon: '\ue610',
    path: '/sql-history'
  }
];

export default function HomeLayout({ children }: PropsWithChildren<Iprops>) {
  const location = useLocation();
  const defaultNav = location.pathname == '/' ? navConfig[0].path : location.pathname
  const [currentNav, setCurrentNav] = useState<string>(defaultNav);

  function switchingNav(item: INavItem) {
    history.push(item.path);
    setCurrentNav(item.path);
  }

  return (
    <div className={styles.page}>
      <div className={styles.layoutLeft}>
        <BrandLogo size={70} className={styles.brandLogo} />
        <ul className={styles.navList}>
          {navConfig.map((item) => {
            return (
              <li
                key={item.path}
                className={classnames({
                  [styles.currentNav]: item.path == currentNav,
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
        <AppHeader>
          <Setting className={styles.setting}></Setting>
        </AppHeader>
        {children}
      </div>
    </div>
  );
}
