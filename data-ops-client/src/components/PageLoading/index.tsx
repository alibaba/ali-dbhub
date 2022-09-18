import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { Spin } from 'antd';

interface IProps {
  className?: any;
}

// TODO： 首屏以及懒加载Lodding效果
export default memo(function PageLoadinng(props: IProps) {
  const { className } = props;
  return <div className={styles.box}>
    <div className={styles.loading}>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </div>
  </div>;
});
