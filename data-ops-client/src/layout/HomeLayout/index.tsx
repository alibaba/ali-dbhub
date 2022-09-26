import React, { memo, useState, PropsWithChildren } from 'react';
import BrandLogo from '@/components/BrandLogo';
import Iconfont from '@/components/Iconfont';
import i18n from '@/i18n';
import classnames from 'classnames';
import { history } from 'umi';
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
    icon: '\ue759',
    path: '/connection'
  },

  {
    title: '我的SQL',
    icon: '\ue759',
    path: '/sql-history'
  }
];

export default function HomeLayout({ children }: PropsWithChildren<Iprops>) {
  const [activeNav, setActiveNav] = useState<string>(navConfig[0].path);

  function switchingNav(item: INavItem) {
    history.push(item.path);
    setActiveNav(item.path);
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
      </div>
      <div className={styles.layoutRight}>{children}</div>
    </div>
  );
}
