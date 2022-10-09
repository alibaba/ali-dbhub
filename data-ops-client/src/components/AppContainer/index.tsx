import React, { memo, useEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { ConfigProvider } from 'antd';
import { history } from 'umi'
import { useLogin } from '@/utils/hooks'

interface IProps {
  className?: any;
}

export default memo<IProps>(function AppContainer({ className, children }) {
  // const [isLogin] = useLogin();
  // // 路由守卫
  // const unlisten = history.listen((location, action) => {
  //   console.log(location)
  //   if (!isLogin && location.pathname !== '/login') {
  //     history.push('/login')
  //   }
  //   if (isLogin && location.pathname === '/login') {
  //     history.push('/')
  //   }
  // })
  // unlisten();

  useEffect(() => {
    settings();
  }, [])

  function settings() {
    document.documentElement.setAttribute('theme', localStorage.getItem('theme') || 'default');
    document.documentElement.setAttribute('primary-color', localStorage.getItem('primary-color') || 'polar-blue');
    if (!localStorage.getItem('lang')) {
      localStorage.setItem('lang', 'zh-cn')
    }

    // document.oncontextmenu = (e) => {
    //   e.preventDefault()
    // }
  }

  return <ConfigProvider prefixCls='custom'>
    <div className={classnames(className, styles.app)}>
      {children}
    </div>
  </ConfigProvider>
});
