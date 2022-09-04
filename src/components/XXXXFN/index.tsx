import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';

interface IProps {
  className?: any;
}

export default memo<IProps>(function XXXXX(props) {
  const { className } = props;
  return <div className={classnames(className, styles.page)}>我是组件</div>;
});
