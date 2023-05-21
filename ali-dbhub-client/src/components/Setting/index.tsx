import React, { memo, useEffect, useLayoutEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '@/components/Iconfont';
import Button from '@/components/Button';
import { Modal, Radio, Input, message } from 'antd';
import i18n from '@/i18n';
import { imghub } from '@/utils/imghub';
import configService, { IChatgptConfig } from '@/service/config';
import miscService from '@/service/misc';
import BrandLogo from '@/components/BrandLogo';
import themeDarkImg from '@/assets/theme-dark.webp';
import themeDefaultImg from '@/assets/theme-default.webp';

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
    img: themeDarkImg
  },
  {
    code: 'default',
    name: '亮色',
    img: themeDefaultImg
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

  const menusList = [
    {
      label: '基础设置',
      icon: '\ue795',
      body: <BaseBody />
    },
    {
      label: 'OpenAI',
      icon: '\ue646',
      body: <OpenAiBody />,
    },
    {
      label: '代理设置',
      icon: '\ue63f',
      body: <ProxyBody />
    },
    {
      label: '关于Chat2DB',
      icon: '\ue60c',
      body: <>
        <div className={styles.aboutUs}>
          <div className={styles.versions}>
            <BrandLogo className={styles.brandLogo} />
            <div>
              <div>Chat2DB</div>
              <div>
                当前环境：{window._ENV}
              </div>
            </div>
          </div>
        </div>
      </>
    },
  ]

  const [currentMenu, setCurrentMenu] = useState(menusList[0]);

  useLayoutEffect(() => {

  }, [])

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  function changeMenu(t: any) {
    setCurrentMenu(t);
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
        width={600}
        maskClosable={false}
      >
        <div className={styles.modalBox}>
          <div className={styles.menus}>
            <div className={classnames(styles.menusTitle)}>
              设置
            </div>
            {
              menusList.map((t, index) => {
                return <div key={index} onClick={changeMenu.bind(null, t)} className={classnames(styles.menuItem, { [styles.activeMenu]: t.label === currentMenu.label })}>
                  <Iconfont code={t.icon} />
                  {t.label}
                </div>
              })
            }
          </div>
          <div className={styles.menuContent}>
            <div className={classnames(styles.menuContentTitle)}>
              {currentMenu.label}
            </div>
            {currentMenu.body}
          </div>
        </div>
      </Modal>
    </>
  );
});

export function addColorSchemeListener(callback: (theme: string) => void) {
  colorSchemeListeners.push(callback);
}

// openAI 的设置项
export function OpenAiBody() {
  const [chatgptConfig, setChatgptConfig] = useState<IChatgptConfig>({
    apiKey: '',
    httpProxyHost: '',
    httpProxyPort: ''
  });

  useEffect(() => {
    configService.getChatGptSystemConfig().then(res => {
      setChatgptConfig(res || {
        apiKey: '',
        httpProxyHost: '',
        httpProxyPort: ''
      })
    })
  }, [])

  function changeChatgptApiKey() {
    // if (!chatgptKey) {
    //   message.error('请输入ChatGPT-apiKey')
    //   return
    // }
    configService.setChatGptSystemConfig(chatgptConfig).then(res => {
      message.success('配置成功')
    })
  }

  return <>
    <div className={styles.title}>
      Api Key
    </div>
    <div className={classnames(styles.content, styles.chatGPTKey)}>
      <Input value={chatgptConfig.apiKey} onChange={(e) => { setChatgptConfig({ ...chatgptConfig, apiKey: e.target.value }) }} />
    </div>
    <div className={styles.title}>
      HTTP Proxy Host
    </div>
    <div className={classnames(styles.content, styles.chatGPTKey)}>
      <Input value={chatgptConfig.httpProxyHost} onChange={(e) => { setChatgptConfig({ ...chatgptConfig, httpProxyHost: e.target.value }) }} />
    </div>
    <div className={styles.title}>
      HTTP Proxy Prot
    </div>
    <div className={classnames(styles.content, styles.chatGPTKey)}>
      <Input value={chatgptConfig.httpProxyPort} onChange={(e) => { setChatgptConfig({ ...chatgptConfig, httpProxyPort: e.target.value }) }} />
    </div>
    <div className={styles.bottomButton}>
      <Button theme='default' onClick={changeChatgptApiKey}>更新</Button>
    </div>
  </>
}

// baseBody 基础设置
export function BaseBody() {
  const [lang, setLang] = useState(localStorage.getItem('lang'));
  const [currentTheme, setCurrentTheme] = useState(localStorage.getItem('theme'));
  const [currentPrimaryColor, setCurrentPrimaryColor] = useState(localStorage.getItem('primary-color'));

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

  return <>
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
      {i18n('common.text.language')}
    </div>
    <div>
      <Radio.Group onChange={changeLang} value={lang}>
        <Radio value='zh-cn'>{i18n('common.text.zh-cn')}</Radio>
        <Radio value='en'>{i18n('common.text.en')}</Radio>
      </Radio.Group>
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
  </>
}

// 代理设置
export function ProxyBody() {
  const [apiPrefix, setApiPrefix] = useState(window._BaseURL)

  function updateApi(e: any) {
    console.log(e.target.value)
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
  return <>
    <div className={styles.title}>
      后台服务地址
    </div>
    <div className={classnames(styles.content, styles.chatGPTKey)}>
      <Input value={apiPrefix} onChange={updateApi} />
    </div>
    <div className={styles.bottomButton}>
      <Button theme='default' onClick={affirmUpdateApi}>更新</Button>
    </div>
  </>
}
