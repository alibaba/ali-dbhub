import React, { memo, useLayoutEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '@/components/Iconfont';
import Button from '@/components/Button';
import { Modal, Radio, Input, message } from 'antd';
import i18n from '@/i18n';
import configService from '@/service/config';
import miscService from '@/service/misc';

interface IProps {
  className?: string;
  text: string;
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

let colorSchemeListeners: ((theme: string) => void)[] = [];

export default memo<IProps>(function Setting({ className, text }) {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [chatgptKey, setChatgptKey] = useState('');
  const [apiPrefix, setApiPrefix] = useState(window._BaseURL);
  const [lang, setLang] = useState(localStorage.getItem('lang'));
  const [currentTheme, setCurrentTheme] = useState(localStorage.getItem('theme'));
  const [currentPrimaryColor, setCurrentPrimaryColor] = useState(localStorage.getItem('primary-color'));

  useLayoutEffect(() => {

  }, [])

  const showModal = () => {
    configService.getSystemConfig({ code: 'chatgpt.apiKey' }).then(res => {
      setChatgptKey(res.content)
      setIsModalVisible(true);
    }).catch(() => {
      setIsModalVisible(true);
    })
  };

  function changeChatgptApiKey() {
    if (!chatgptKey) {
      message.error('请输入ChatGPT-apiKey')
      return
    }
    configService.setSystemConfig({ code: 'chatgpt.apiKey', content: chatgptKey }).then(res => {
      message.success('配置成功')
    })
  }

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
    colorSchemeListeners.forEach(t => t(item.code));

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

  function updateApi(e: any) {
    setApiPrefix(e.target.value)
  }

  function affirmUpdateApi() {
    if (!apiPrefix) {
      return
    }
    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    xhr.open('GET', `${apiPrefix}/api/system/get-version-a`);
    xhr.onload = function () {
      if (xhr.status === 200) {
        localStorage.setItem('_BaseURL', apiPrefix);
        location.reload();
      } else {
        message.error('接口测试不通过')
      }
    };
    xhr.send();
  }

  return (
    <>
      <div className={classnames(className, styles.box)} onClick={showModal}>
        {
          text ?
            <span className={styles.setText}>{text}</span>
            :
            <Iconfont code="&#xe795;"></Iconfont>
        }
      </div>
      <Modal
        open={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        footer={false}
      >
        <div className={styles.title}>
          {i18n('common.text.background')}
        </div>
        <ul className={styles.backgroundList}>
          {backgroundList.map((item) => {
            return (
              <li key={item.code} className={classnames({ [styles.current]: currentTheme == item.code })} onClick={changeTheme.bind(null, item)} style={{ backgroundImage: `url(${item.img})` }} />
            );
          })}
        </ul>
        <div className={styles.title}>
          OpenAI Api Key
        </div>
        <div className={classnames(styles.content, styles.chatGPTKey)}>
          <Input value={chatgptKey} onChange={(e) => { setChatgptKey(e.target.value) }} />
          <Button theme='default' onClick={changeChatgptApiKey}>更新</Button>
        </div>
        <div className={styles.title}>
          后台服务地址
        </div>
        <div className={classnames(styles.content, styles.chatGPTKey)}>
          <Input value={apiPrefix} onChange={updateApi} />
          <Button theme='default' onClick={affirmUpdateApi}>更新</Button>
        </div>
        {/* <div className={styles.title}>
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
        </ul> */}
        <div className={styles.title}>
          {i18n('common.text.language')}
        </div>
        <div>
          <Radio.Group onChange={changeLang} value={lang}>
            <Radio value='zh-cn'>{i18n('common.text.zh-cn')}</Radio>
            <Radio value='en'>{i18n('common.text.en')}</Radio>
          </Radio.Group>
        </div>
      </Modal>
    </>
  );
});

export function addColorSchemeListener(callback: (theme: string) => void) {
  colorSchemeListeners.push(callback);
}
