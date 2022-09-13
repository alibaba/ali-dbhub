import React, { memo,useState } from 'react';
import styles from './index.less';
import { ConfigProvider, Modal, Button } from 'antd';
import Avatar from '@/components/Avatar';
import Iconfont from '@/components/Iconfont';
import classnames from 'classnames';

interface IProps {
  className?: any;
  place?: 'header' | 'left';
}

function GlobalNav(props: IProps) {
  const { className, place = 'left' } = props;
  const [isModalVisible, setIsModalVisible] = useState(false);
  const colorList = [
    {
      color: 'polar-green',
      name: '极光绿'
    },
    {
      color: 'polar-blue',
      name: '蓝蓬釉'
    },
    {
      color: 'golden-purple',
      name: '酱紫'
    },
    {
      color: 'sunset-orange ',
      name: '日暮'
    },
  ]
  const backGroundList = [
    {
      color: 'default',
      name: '亮色'
    },
    {
      color: 'dark',
      name: '暗色'
    },
  ]

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  function handleColorSchemeClick(item:any) {
    const html = document.documentElement;
    html.setAttribute('theme', item.color);
    localStorage.setItem('theme', item.color);
    // const theme =  html.getAttribute('theme');
    // if (theme == 'dark') {
    //   // html.setAttribute('theme', 'default');
    //   localStorage.setItem('theme', 'default');
    //   location.reload();
    // } else {
    //   // html.setAttribute('theme', 'dark');
    //   localStorage.setItem('theme', 'dark');
    //   location.reload();
    // }
  }

  const changeTheme = (item:any)=>{
    const html = document.documentElement;
    html.setAttribute('primary-color', item.color);
    localStorage.setItem('primary-color', item.color);
  }

  return (
    <>
    <div
      className={classnames(styles.globalNav, className, {
        [styles.topNav]: place === 'header',
      })}
    >
      <div className={classnames(styles.avatarBox, styles.vessel)}>
        <Avatar size={26} classNames={styles.userLogo}></Avatar>
      </div>
      <div className={styles.bottom}>
        <div
          className={classnames(styles.message, styles.itemBox, styles.vessel)}
          onClick={showModal}
        >
          <Iconfont code="&#xe795;"></Iconfont>
        </div>
        <div className={classnames(styles.set, styles.itemBox, styles.vessel)}>
          <Iconfont code="&#xe60c;"></Iconfont>
        </div>
      </div>
      <div className={styles.borderSplitBottom}></div>
    </div>
    <Modal title="Basic Modal" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
      <h1>背景色</h1>
      {
        backGroundList.map(item=>{
          return <Button onClick={handleColorSchemeClick.bind(null,item)}>
          {item.name}
        </Button>
        })
      }
      <h1>主题色</h1>
      {
        colorList.map(item=>{
          return <Button onClick={changeTheme.bind(null,item)}>
            {item.name}
          </Button>
        })
      }
      </Modal>
    </>
  );
}

export default memo(GlobalNav);
