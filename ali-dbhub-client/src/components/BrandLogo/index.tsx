import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import logo from '@/assets/logo.png'

interface IProps {
  className?: any;
  size?: number;
}

export default memo<IProps>(function BrandLogo({ className, size = 48 }) {
  return (
    <div className={classnames(className, styles.box)} style={{ height: `${size}px`, width: `${size}px` }}>
      <img src={logo} alt="" />
    </div>
  );
});
