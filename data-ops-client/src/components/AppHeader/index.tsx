import React, { memo, PropsWithChildren } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../Iconfont';

interface IProps {
  className?: any;
}

export default memo<PropsWithChildren<IProps>>(function AppHeader(props) {
  const { className, children } = props;

  const refreshPage = () => {
    location.reload();
  };

  return (
    <div className={classnames(className, styles.box)}>
      <div className={styles.headerBox}>
        <div className={styles.header}>
          <div className={styles.headerLeft}>
            {children}
          </div>
          <div className={styles.headerRight}>
            <div className={styles.refreshBox} onClick={refreshPage}>
              <Iconfont code="&#xe62d;" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
});
