import React, { memo, useEffect, useLayoutEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { ConfigProvider } from 'antd';
import { history } from 'umi';
import { useLogin } from '@/utils/hooks';
import { getLastPosition, setCurrentPosition } from '@/utils';
import miscService from '@/service/misc'
import LoadindLiquid from '@/components/Loading/LoadingLiquid';
import i18n from '@/i18n';

interface IProps {
  className?: any;
}


export default memo<IProps>(function AppContainer({ className, children }) {
  const [startSchedule, setStartSchedule] = useState(0); // 0 初始状态 1 服务启动报错 2 启动成功
  const [serviceFail, setServiceFail] = useState(false);

  function hashchange() {
    console.log(location.hash)
    setCurrentPosition()
  }

  useLayoutEffect(() => {
    window.addEventListener('hashchange', hashchange)
    const hash = getLastPosition();
    if (hash && !location.hash) {
      location.hash = hash
    }
    return () => {
      window.removeEventListener('hashchange', hashchange);
    }
  }, [])

  useEffect(() => {
    settings();
    detectionService();
  }, [])

  function detectionService() {
    setServiceFail(false)
    let flag = 0
    const time = setInterval(() => {
      flag++
      miscService.testService().then(() => {
        clearInterval(time)
        setStartSchedule(2)
      }).catch(error => {
        setStartSchedule(1)
      })
      if (flag > 100) {
        setServiceFail(true)
        clearInterval(time)
        // setStartSchedule(true) 
      }
    }, 500)
  }

  function settings() {
    const theme = localStorage.getItem('theme') || 'dark'
    document.documentElement.setAttribute('theme', theme);
    localStorage.setItem('theme', theme);
    document.documentElement.setAttribute('primary-color', localStorage.getItem('primary-color') || 'polar-blue');
    if (!localStorage.getItem('lang')) {
      localStorage.setItem('lang', 'zh-cn');
    }
    //禁止右键
    // document.oncontextmenu = (e) => {
    //   e.preventDefault()
    // }
  }

  return <ConfigProvider prefixCls='custom'>
    {
      startSchedule === 2 &&
      <div className={classnames(className, styles.app)}>
        {children}
      </div>
    }
    {
      startSchedule === 1 &&
      <div className={styles.starting}>
        {
          !serviceFail ?
            <div>
              <LoadindLiquid></LoadindLiquid>
              <div className={styles.hint} >
                {i18n('common.text.serviceStarting')}
              </div>
            </div>
            :
            <div>
              <div className={styles.hint} >
                {i18n('common.text.serviceFail')}
              </div>
              <div className={styles.dingGroup}>联系我们-钉钉群：9135032392</div>
              <div className={styles.restart} onClick={detectionService}>尝试重新启动</div>
            </div>
        }
      </div>
    }

  </ConfigProvider>
});



