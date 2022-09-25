import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Loading from '../Loading';

interface IProps {
  className?: string;
  state: 'loading' | 'empty' | 'error'
}

export const enum State {
  LOADING = 'loading',
  EMPTY = 'empty',
  ERROR = 'error',
}

export default memo<IProps>(function StateIndicator({ className, state }) {

  const renderState = () => {
    switch (state) {
      case 'loading':
        return <Loading></Loading>;
      default:
        return <div className={classnames(className, styles[state])}></div >
    }
  }
  return <div className={classnames(className, styles.box)}>
    {renderState()}
  </div>

})
