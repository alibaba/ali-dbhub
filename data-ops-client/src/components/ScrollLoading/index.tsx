import React, { memo, PropsWithChildren } from 'react';
import styles from './index.less';
import classnames from 'classnames';

interface IProps {
  className?: string;
  children?: React.ReactChild;
  onReachBottom: any;
  threshold: number;
}

export default memo<IProps>(function ScrollLoading({ className, children }) {
  return <div className={classnames(className, styles.box)}>
    {children}
  </div>
})
