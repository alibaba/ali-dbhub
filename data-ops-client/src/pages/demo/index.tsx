import React, { memo, useCallback, useState } from 'react';
import styles from './index.less'
import Iconfont from '@/components/Iconfont'

function DemoPage() {
  const [userName, setUserName] = useState('');
  const a = 1
  // const debounceGetList = useCallback(debounce(getList,2000),[a])
  const debounceGetList = debounce(getList, 2000)

  function debounce(fn: Function, wait: number) {
    let timer: any = '';
    return function () {
      if (timer) {
        clearInterval(timer)
      }
      timer = setTimeout(() => {
        fn(arguments)
      }, wait);
    }
  }

  function getList() {
  }

  function changeValue(e: any) {
    setUserName(e.target.value)
    debounceGetList()
  }

  function onChange(e: any, b) {

  }

  return <div className={styles.page}>
  </div>
}

export default DemoPage