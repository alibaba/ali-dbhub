import React, { memo, useCallback, useEffect, useRef, useState } from 'react';
import styles from './index.less'
import Iconfont from '@/components/Iconfont'
import Tabs from '@/components/Tabs';

function DemoPage() {
  const list = new Array(10).fill('123');
  const [data, setData] = useState(1);
  const dataRef = useRef();
  function task() {
    console.log(1)
  }

  return <div className={styles.page}>
    {
      (new Array(100)).fill(1).map((i, index) => {
        return <div key={index}>{index}</div>
      })
    }
  </div>
}

export default DemoPage