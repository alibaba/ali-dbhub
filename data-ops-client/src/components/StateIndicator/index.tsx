import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Loading from '../Loading/Loading';

interface IProps {
  className?: string;
  state: 'loading' | 'empty' | 'error';
  text?: string;
}

export const enum State {
  LOADING = 'loading',
  EMPTY = 'empty',
  ERROR = 'error',
}

export default memo<IProps>(function StateIndicator({ className, state, text }) {

  const renderState = () => {
    switch (state) {
      case 'loading':
        return <Loading></Loading>;
      case 'error':
        return <div className={styles.errorBox}>
          <div className={classnames(className, styles[state])}></div >
          <div className={styles.errorText}>{text}</div>
        </div>
      default:
        return <div className={classnames(className, styles[state])}></div >
    }
  }
  return <div className={classnames(className, styles.box)}>
    {renderState()}
  </div>

})
