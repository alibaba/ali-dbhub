import React, { memo, useCallback, useState } from 'react';
import styles from './index.less'
import Iconfont from '@/components/Iconfont'
import Tabs from '@/components/Tabs';

function DemoPage() {
  return <div className={styles.page}>
    <div className={styles.container}>
      <div className={styles.wenzi}>happy everyday~</div>
    </div>
  </div>
}

export default DemoPage