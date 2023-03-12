import React, { memo } from 'react';
import classnames from 'classnames';
import styles from './index.less';
import Iconfont from '../Iconfont'


export type Iprops = {
  classNames?: any,
  img?: string,
  size?: number,
}

function Avatar({ img, size, classNames }: Iprops) {
  const sizePx = size + 'px'
  const defaultAvatar = 'https://cdn.apifox.cn/app/avatar/builtin/3.png'
  return <div className={classnames(styles.avatar, classNames)} style={{ width: sizePx, height: sizePx }}>
    <img src={img || defaultAvatar} alt="用户头像" />
  </div>
}

export default memo(Avatar)