import React from 'react';
import styles from './index.less';
import { history } from 'umi';

export default function IndexPage() {
  function login() {
    history.push('/');
  }

  return (
    <div className={styles.loginPage}>
      <div className={styles.signupSpace}>
        <div className={styles.signupStars}></div>
        <div className={styles.signupStars}></div>
        <div className={styles.signupStars}></div>
        <div className={styles.signupStars}></div>
        <div className={styles.signupStars}></div>
      </div>
      <div className={styles.brand}>DataOps</div>
      <div className={styles.loginBox}>
        <div>Welcome to DataOps!</div>
        <div>Let's record the details of life</div>
        <div className={styles.account}>
          <input type="text" />
        </div>
        <div className={styles.password}>
          <input type="password" />
        </div>
        <div className={styles.loginButton} onClick={login}>
          登录
        </div>
      </div>
    </div>
  );
}
