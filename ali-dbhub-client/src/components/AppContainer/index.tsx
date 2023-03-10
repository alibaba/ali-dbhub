import React, { memo, useEffect, useLayoutEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { ConfigProvider } from 'antd';
import { history } from 'umi';
import { useLogin } from '@/utils/hooks';
import { getLastPosition, setCurrentPosition } from '@/utils';
import miscService from '@/service/misc';
import LoadingLiquid from '@/components/Loading/LoadingLiquid';
import i18n from '@/i18n';

interface IProps {
  className?: any;
}

export default memo<IProps>(function AppContainer({ className, children }) {
  const [serviceStart, setServiceStart] = useState(false);
  const [serviceFail, setServiceFail] = useState(false);

  function hashchange() {
    console.log(location.hash);
    setCurrentPosition();
  }

  useLayoutEffect(() => {
    window.addEventListener('hashchange', hashchange);
    // const hash = getLastPosition();
    // if (hash) {
    //   location.hash = hash;
    // }
    return () => {
      window.removeEventListener('hashchange', hashchange);
    };
  }, []);

  useEffect(() => {
    settings();
    detectionService();
  }, []);

  function detectionService() {
    setServiceFail(false);
    let flag = 1;
    const time = setInterval(() => {
      flag++;
      miscService.testService().then(() => {
        clearInterval(time);
        setServiceStart(true);
      });
      if (flag > 30) {
        setServiceFail(true);
        clearInterval(time);
        // setServiceStart(true)
      }
    }, 300);
  }

  function settings() {
    const theme = localStorage.getItem('theme') || 'dark';
    document.documentElement.setAttribute('theme', theme);
    localStorage.setItem('theme', theme);
    document.documentElement.setAttribute(
      'primary-color',
      localStorage.getItem('primary-color') || 'polar-blue',
    );
    if (!localStorage.getItem('lang')) {
      localStorage.setItem('lang', 'zh-cn');
    }
    //禁止右键
    document.oncontextmenu = (e) => {
      e.preventDefault();
    };
  }

  return (
    <ConfigProvider prefixCls="custom">
      {serviceStart ? (
        <div className={classnames(className, styles.app)}>{children}</div>
      ) : (
        <div className={styles.starting}>
          <div>
            {!serviceFail && <LoadingLiquid />}
            <div className={styles.hint}>
              {serviceFail
                ? i18n('common.text.serviceFail')
                : i18n('common.text.serviceStarting')}
            </div>
            {serviceFail && (
              <div className={styles.restart}>联系我们-钉钉群：9135032392</div>
            )}
            {serviceFail && (
              <div className={styles.restart} onClick={detectionService}>
                尝试重新启动
              </div>
            )}
          </div>
        </div>
      )}
    </ConfigProvider>
  );
});
