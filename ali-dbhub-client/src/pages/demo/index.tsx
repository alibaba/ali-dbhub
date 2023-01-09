import React, { memo, useCallback, useState } from 'react';
import styles from './index.less'
import Iconfont from '@/components/Iconfont'
import Tabs from '@/components/Tabs';
import ModifyTablePage from '@/pages/modify-table';

function DemoPage() {
  return <div className={styles.page}>
    <ModifyTablePage></ModifyTablePage>
  </div>
}

export default DemoPage