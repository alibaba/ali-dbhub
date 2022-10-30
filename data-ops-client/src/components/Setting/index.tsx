import React, { memo, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '@/components/Iconfont';
import { Modal, Button, Radio } from 'antd';

interface IProps {
  className?: any;
}

const colorList = [
  {
    code: 'polar-green',
    name: '极光绿',
    color: '#1d3712'

  },
  {
    code: 'polar-blue',
    name: '蓝蓬釉',
    color: '#1a90ff'
  },
  {
    code: 'golden-purple',
    name: '酱紫',
    color: '#301c4d'
  },
  {
    code: 'sunset-orange',
    name: '日暮',
    color: "#593815"
  },
];

const backgroundList = [
  {
    code: 'dark',
    name: '暗色',
    img: 'https://img.alicdn.com/imgextra/i1/O1CN01Oj4G6k22ln0NU3A1X_!!6000000007161-2-tps-171-130.png'
  },
  {
    code: 'default',
    name: '亮色',
    img: 'https://img.alicdn.com/imgextra/i2/O1CN01HCTGD11PF1erPJvXa_!!6000000001810-2-tps-182-137.png'
  },
  // {
  //   code: 'eyeshield',
  //   name: '护眼',
  //   img: 'https://img.alicdn.com/imgextra/i1/O1CN01KGCqY21uJpuFjEQW2_!!6000000006017-2-tps-181-135.png'
  // },
];

export default memo<IProps>(function Setting({ className }) {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [lang, setLang] = useState(localStorage.getItem('lang'));
  const [currentTheme, setCurrentTheme] = useState(localStorage.getItem('theme'));
  const [currentPrimaryColor, setCurrentPrimaryColor] = useState(localStorage.getItem('primary-color'));

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  function changeTheme(item: any) {
    const html = document.documentElement;
    html.setAttribute('theme', item.code);
    localStorage.setItem('theme', item.code);
    setCurrentTheme(item.code)
  }

  const changePrimaryColor = (item: any) => {
    const html = document.documentElement;
    html.setAttribute('primary-color', item.code);
    localStorage.setItem('primary-color', item.code);
    setCurrentPrimaryColor(item.code)
  };

  function changeLang() {
    const lang = localStorage.getItem('lang') === 'en' ? 'zh-cn' : 'en'
    localStorage.setItem('lang', lang);
    location.reload();
  }

  return (
    <>
      <div className={classnames(className, styles.box)} onClick={showModal}>
        <Iconfont code="&#xe795;"></Iconfont>
      </div>
      <Modal
        open={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        footer={false}
      >
        {/* <div className={styles.nav}>

        </div>
        <div className={styles.main}>

        </div> */}
        <div className={styles.title}>
          背景
        </div>
        <ul className={styles.backgroundList}>
          {backgroundList.map((item) => {
            return (
              <li key={item.code} className={classnames({ [styles.current]: currentTheme == item.code })} onClick={changeTheme.bind(null, item)} style={{ backgroundImage: `url(${item.img})` }} />
            );
          })}
        </ul>
        <div className={styles.title}>
          主题色
        </div>
        <ul className={styles.primaryColorList}>
          {colorList.map((item) => {
            return (
              <li key={item.code} onClick={changePrimaryColor.bind(null, item)} style={{ backgroundColor: item.color }}>
                {currentPrimaryColor == item.code && <Iconfont code="&#xe617;"></Iconfont>}
              </li>
            );
          })}
        </ul>
        <div className={styles.title}>
          语言
        </div>
        <div>
          <Radio.Group onChange={changeLang} value={lang}>
            <Radio value='zh-cn'>中文</Radio>
            <Radio value='en'>英文</Radio>
          </Radio.Group>
        </div>
      </Modal>
    </>
  );
});
