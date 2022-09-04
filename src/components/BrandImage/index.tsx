import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';

interface IProps {
  className?: any;
}

function BrandImage(props: IProps) {
  const { className } = props;
  return (
    <div className={classnames(className, styles.page)}>
      <img src="https://cdn.apifox.cn/logo/apifox-logo-512.png" alt="" />
      <span>DataOps</span>
    </div>
  );
}

export default memo(BrandImage);
