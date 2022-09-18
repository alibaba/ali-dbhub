import React, { memo, useEffect } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { ConfigProvider } from 'antd';

interface IProps {
  className?: any;
}

export default memo<IProps>(function AppContainer({className, children}) {

  useEffect(()=>{
    settings();
    
  },[])

  function settings(){
    
    document.documentElement.setAttribute('theme', localStorage.getItem('theme') || 'default');
    document.documentElement.setAttribute('primary-color', localStorage.getItem('primary-color') || 'polar-blue');
    if(!localStorage.getItem('lang')){
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
