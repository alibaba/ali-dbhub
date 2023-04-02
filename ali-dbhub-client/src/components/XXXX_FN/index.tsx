import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';

interface Iprops {
  className?: string;
}

export default memo<Iprops>(function XXXX_FN(props) {
  const { className } = props
  return <div className={classnames(styles.box, className)}>

  </div>
})
