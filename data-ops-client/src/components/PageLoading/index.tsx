import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';

interface IProps {
  className?: any;
}

function PageLoadinng(props: IProps) {
  const { className } = props;
  return <div className={classnames(className, styles.page)}>
    loadding 动画
  </div>;
}

export default memo(PageLoadinng);
