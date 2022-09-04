import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { ConfigProvider } from 'antd';

interface IProps {
  className?: any;
}

export default memo<IProps>(function AppContainer(props) {
  const { className } = props;
  return <ConfigProvider prefixCls='custom'>
    <div className={classnames(className, styles.app)}>
      {props.children}
    </div>;
  </ConfigProvider>
});
