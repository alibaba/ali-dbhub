import React, { useEffect, useState, PropsWithChildren } from 'react';
import i18n from '@/i18n';
import { Button } from 'antd';
import { history, connect } from 'umi';
import classnames from 'classnames';
import Setting from '@/blocks/Setting';
import Iconfont from '@/components/Iconfont';
import BrandLogo from '@/components/BrandLogo';
import { IMainPageType } from '@/models/mainPage';
import { IWorkspaceModelType } from '@/models/workspace';
import { IConnectionModelType } from '@/models/connection';
import { findObjListValue } from '@/utils'

import DataSource from './connection';
import Workspace from './workspace';
import Dashboard from './dashboard';
import Chat from './chat';

import styles from './index.less';
import { INavItem } from '@/typings/main';
const navConfig: INavItem[] = [
  {
    key: 'workspace',
    icon: '\ue616',
    iconFontSize: 16,
    component: <Workspace />,
  },
  // {
  //   key: 'dashboard',
  //   icon: '\ue629',
  //   iconFontSize: 24,
  //   component: <Dashboard />,
  // },
  // {
  //   key: 'connections',
  //   icon: '\ue622',
  //   iconFontSize: 20,
  //   component: <DataSource />,
  // },
  // {
  //   key: 'github',
  //   icon: '\ue885',
  //   openBrowser: 'https://github.com/alibaba/Chat2DB',
  // },
];

const initPageIndex = navConfig.findIndex(t => `/${t.key}` === window.location.pathname);

interface IProps {
  mainModel: IMainPageType['state'];
  workspaceModel: IWorkspaceModelType['state'];
  connectionModel: IConnectionModelType['state'];
  dispatch: any;
}

function MainPage(props: IProps) {
  const { mainModel, workspaceModel, connectionModel, dispatch } = props;
  const { curPage } = mainModel;
  const { curConnection } = connectionModel;
  const [activeNav, setActiveNav] = useState<INavItem>(navConfig[initPageIndex > 0 ? initPageIndex : 0]);

  useEffect(() => {
    // activeNav 发生变化，同步到全局状态管理
    dispatch({
      type: 'mainPage/updateCurPage',
      payload: activeNav.key
    })
    // activeNav 发生变化 如果没有选中连接并且不在connections 那么需要跳转到 连接页面
    // if (!curConnection?.id && activeNav.key !== 'connections') {
    //   setActiveNav(navConfig[2]);
    // }
    // activeNav 变化 同步地址栏变化
    // change url，but no page refresh
    window.history.pushState({}, "", activeNav.key);
  }, [activeNav])

  useEffect(() => {
    // 全局状态curPage发生变化，activeNav 需要同步变化
    if (curPage && curPage !== activeNav.key) {
      const newActiveNav = navConfig[findObjListValue(navConfig, 'key', curPage)]
      setActiveNav(newActiveNav)
    }
  }, [curPage])

  useEffect(() => {
    if (curConnection?.id) {
      dispatch({
        type: 'workspace/fetchdatabaseAndSchema',
      })
    }
  }, [curConnection])

  function switchingNav(item: INavItem) {
    if (item.openBrowser) {
      window.open(item.openBrowser);
    } else {
      setActiveNav(item);
    }
  }


  return (
    <div className={styles.page}>
      <div className={styles.layoutLeft}>
        <BrandLogo size={40} onClick={() => { }} className={styles.brandLogo} />
        <ul className={styles.navList}>
          {navConfig.map((item, index) => {
            return (
              <li
                key={item.key}
                className={classnames({
                  [styles.activeNav]: item.key == activeNav.key,
                })}
                onClick={switchingNav.bind(null, item)}
              >
                <Iconfont style={{ fontSize: `${item.iconFontSize}px` }} className={styles.icon} code={item.icon} />
                {/* <div>{item.title}</div> */}
              </li>
            );
          })}
        </ul>
        <div className={styles.footer}>
          <Setting className={styles.setIcon}></Setting>
        </div>
      </div>
      <div className={styles.layoutRight}>
        {navConfig.map((item) => {
          return (
            <div
              key={item.key}
              className={styles.componentBox}
              hidden={activeNav.key !== item.key}
            >
              {item.component}
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default connect(({ mainPage, workspace, connection }: { mainPage: IMainPageType, workspace: IWorkspaceModelType, connection: IConnectionModelType }) => ({
  mainModel: mainPage,
  workspaceModel: workspace,
  connectionModel: connection,
}))(MainPage);
