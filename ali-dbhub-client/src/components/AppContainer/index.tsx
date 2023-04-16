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
import { ThemeType } from '@/utils/constants';

interface IProps {
  className?: any;
}

/** 重启次数 */
const restartCount = 30;

export default memo<IProps>(function AppContainer({ className, children }) {
  const [startSchedule, setStartSchedule] = useState(0); // 0 初始状态 1 服务启动报错 2 启动成功
  const [serviceFail, setServiceFail] = useState(false);
  const [serviceStart, setServiceStart] = useState(true);

  function hashchange() {
    console.log(location.hash);
    setCurrentPosition();
  }

  useLayoutEffect(() => {
    settings();
    window.addEventListener('hashchange', hashchange);
    return () => {
      window.removeEventListener('hashchange', hashchange);
    };
  }, []);

  useEffect(() => {
    detectionService();
  }, []);

  function detectionService() {
    setServiceFail(false);
    let flag = 0;
    const time = setInterval(() => {
      if(flag === 2){
        setServiceStart(false)
      }
      miscService.testService().then(() => {
        clearInterval(time);
        setServiceStart(true);
      });
      if (flag > restartCount) {
        setServiceFail(true);
        clearInterval(time);
      }
      flag++;
    }, 300);
  }

  function settings() {
    const theme = localStorage.getItem('theme') || ThemeType.dark;
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
    // document.oncontextmenu = (e) => {
    //   e.preventDefault();
    // };
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
              <>
                <div className={styles.restart}>
                  联系我们-钉钉群：<a href="dingtalk://dingtalkclient/action/sendmsg?dingtalk_id=9135032392">9135032392</a>
                </div>
                <div className={styles.restart} onClick={detectionService}>
                  尝试重新启动
                </div>
              </>
            )}
          </div>
        </div>
      )}
    </ConfigProvider>
  );
});
