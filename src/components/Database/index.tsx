import React, { memo, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Input from '@/components/Input';
import { Button, DatePicker } from 'antd';
import i18n from '../../i18n';



interface IProps {
  className?: any;
}



export default memo<IProps>(function Database({ className }) {
  const tabConfig = [
    {
      title: 'xx',
      code: 'dataBase',
      component: record,
    },
    {
      title: 'xx',
      code: 'member',
      component: record,
    },
    {
      title: 'xxx',
      code: 'setting',
      component: record,
    },
  ];
  const [activeTab, setActiveTab] = useState(tabConfig[0]);

  function switchTab(item: any) {
    setActiveTab(item);
  }

  
  function changeLang(){
    localStorage.setItem('lang', localStorage.getItem('lang') === 'en' ? 'zh-cn' : 'en')
    location.reload();
    console.log(localStorage.getItem('lang'))
  }

  function record() {
    return (
      <div className={styles.record}>
        <div className={styles.scan}>
        </div>
        <div className={styles.recordHeader}>
          <Button type='primary' onClick={changeLang}>{i18n('database.buttonn.colorSwitch')}</Button>
          <DatePicker />
        </div>
      </div>
    );
  }

  return (
    <div className={classnames(className, styles.page)}>
      <div className={styles.ABookBasic}>
        <div className={styles.logo}>
          <img src="" alt="" />
        </div>
        <div className={styles.descBox}>
          <div className={styles.name}>数据库名称</div>
          <div className={styles.desc}>
            数据库简介数据库简介数据库简介数据库简介数据库简介数据库简介数据库简介
          </div>
        </div>
      </div>
      <div className={styles.tab}>
        {tabConfig.map((item) => {
          return (
            <li
              key={item.code}
              className={classnames({
                [styles.activeTab]: activeTab.code === item.code,
              })}
              onClick={switchTab.bind(null, item)}
            >
              <div className={styles.activeLine}></div>
              {item.title}
            </li>
          );
        })}
      </div>
      <div className={styles.main}>{activeTab.component()}</div>
    </div>
  );
});
