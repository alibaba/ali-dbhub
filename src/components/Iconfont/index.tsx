import classnames from 'classnames';
import React, { memo } from 'react';
import styles from './index.less';

export type Iprops = {
  className?: any;
  code?: string;
};

function Iconfont({ code, className }: Iprops) {
  return <i className={classnames(className, styles.iconfont)}>{code}</i>;
}

export default memo(Iconfont);
