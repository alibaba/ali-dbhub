import React, { memo, useCallback, useEffect, useRef, useState } from 'react';
import styles from './index.less'
import Iconfont from '@/components/Iconfont'
import Tabs from '@/components/Tabs';
import ModifyTablePage from '@/pages/modify-table';

function DemoPage() {
  const list = new Array(10).fill('123');
  const [data, setData] = useState(1);
  const dataRef = useRef();
  function task() {
    console.log(1)
  }

  return <div className={styles.page}>
    {
      list.map(item => {
        <div onClick={task}>
          {item}
        </div>
      })
    }
  </div>
}

export default DemoPage