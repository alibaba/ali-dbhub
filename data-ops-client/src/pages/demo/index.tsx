import React, { memo, useCallback, useState } from 'react';
import styles from './index.less'

function DemoPage(){
  const [userName,setUserName] = useState('');
  const a = 1
  // const debounceGetList = useCallback(debounce(getList,2000),[a])
  const debounceGetList = debounce(getList,2000)

  function debounce(fn: Function,wait: number){
    let timer:any =  '';
    return function (){
      if(timer){
        clearInterval(timer)
      }
      timer = setTimeout(() => {
        fn(arguments)
      }, wait);
    }
  }

  function getList(){
    console.log('请求接口了')
  }

  function changeValue(e:any){
    setUserName(e.target.value)
    debounceGetList()
  }

  return <div className={styles.page}>
    我是Demo
    <input type="text" value={userName} onChange={(e)=> {changeValue(e)}}/>
  </div>
}

export default DemoPage