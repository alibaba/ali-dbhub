import React, { memo, useEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { ConfigProvider } from 'antd';
import { history } from 'umi';
import { useLogin } from '@/utils/hooks';
import miscService from '@/service/misc'
import i18n from '@/i18n';

interface IProps {
  className?: any;
}


export default memo<IProps>(function AppContainer({ className, children }) {
  const [serviceStart, setServiceStart] = useState(false);
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
    detectionService()
  }, [])

  function detectionService() {
    let flag = 1
    const time = setInterval(() => {
      flag++
      miscService.testService().then(() => {
        clearInterval(time)
        setServiceStart(true)
      })
      if (flag > 20) {
        clearInterval(time)
      }
    }, 300)
  }

  function settings() {
    const theme = localStorage.getItem('theme') || 'default'
    document.documentElement.setAttribute('theme', theme);
    document.documentElement.setAttribute('primary-color', localStorage.getItem('primary-color') || 'polar-blue');
    if (!localStorage.getItem('lang')) {
      localStorage.setItem('lang', 'zh-cn');
    }

    // document.oncontextmenu = (e) => {
    //   e.preventDefault()
    // }
  }



  return <ConfigProvider prefixCls='custom'>
    {
      serviceStart ?
        <div className={classnames(className, styles.app)}>
          {children}
        </div>
        :
        <div className={styles.starting}>
          {i18n('common.text.serviceStarting')}
        </div>
    }
  </ConfigProvider>
});



