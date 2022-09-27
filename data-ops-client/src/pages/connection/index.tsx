import React, { memo, useEffect, useState } from 'react';
import classnames from 'classnames';
import { formatNaturalDate } from '@/utils/index';
import Iconfont from '@/components/Iconfont';
import ScrollLoading from '@/components/ScrollLoading';
import i18n from '@/i18n';
import { history } from 'umi';
import connectionServer from '@/service/connection'
import { IConnectionBase } from '@/types'
import { databaseTypeList, DatabaseTypeCode } from '@/utils/constants'
import {
  Dropdown,
  Menu,
  Space,
  Select,
  Button,
  Modal,
  Form,
  Input,
  Checkbox,
  Pagination
} from 'antd';

import styles from './index.less';

const { Option } = Select;

interface IProps {
  className?: any;
}

const menuList = [
  {
    code: 'edit',
    icon: '\ue60f',
    text: '修改名称',
  },
  {
    code: 'delete',
    icon: '\ue604',
    text: '断开链接',
  },
];

export default memo<IProps>(function Connection(props) {
  const { className } = props;
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [connectionList, setConnectionList] = useState<IConnectionBase[]>();
  const [createForm, setCreateForm] = useState<IConnectionBase>()

  useEffect(() => {
    getconnectionList()
  }, [])

  const getconnectionList = () => {
    let p = {
      pageNo: 1,
      pageSize: 10
    }
    connectionServer.getList(p).then(res => {
      setConnectionList(res.data)
    })
  }

  const jumpPage = (item: IConnectionBase) => {
    history.push({
      pathname: `/database/${item.id}`,
    });
  };

  const renderMenu = () => {
    return (
      <ul className={styles.menu}>
        {menuList.map((item) => {
          return (
            <li key={item.code} className={styles.menuItem}>
              <Iconfont code={item.icon}></Iconfont>
              {item.text}
            </li>
          );
        })}
      </ul>
    );
  };

  const linkDatabase = () => { };

  const showLinkModal = () => {
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const handleOk = () => {
    setIsModalVisible(true);
  };

  const onFinish = (values: any) => {
    let p = values
    connectionServer.save(p).then(res => {
      setIsModalVisible(false)
    })

  };

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
  };

  const onChange = () => { };

  const changeForm = (e: any) => {
    console.log(e)
  }

  return (
    <div className={classnames(className, styles.box)}>
      <div className={styles.header}>
        <div className={styles.title}>{i18n('home.nav.database')}</div>
        <Button
          className={styles.linkButton}
          type="primary"
          onClick={showLinkModal}
        >
          <Iconfont code="&#xe631;"></Iconfont>
          {i18n('database.input.newLink')}
        </Button>
      </div>
      <div className={styles.scrollBox}>
        <ScrollLoading onReachBottom={1} threshold={300}>
          <div className={styles.connectionList}>
            {connectionList?.map((item) => {
              return (
                <div key={item.id} className={styles.connectionItem}>
                  <div className={styles.left} onClick={jumpPage.bind(null, item)}>
                    <div
                      className={styles.logo}
                      style={{
                        backgroundImage: `url(https://cdn.apifox.cn/app/project-icon/builtin/9.jpg)`,
                      }}
                    ></div>
                    <div className={styles.name}>{item.alias}</div>
                  </div>
                  <div className={styles.right}>
                    <Dropdown overlay={renderMenu()} trigger={['hover']}>
                      <a onClick={(e) => e.preventDefault()}>
                        <div className={styles.moreActions}>
                          <Iconfont code="&#xe601;" />
                        </div>
                      </a>
                    </Dropdown>
                  </div>
                </div>
              );
            })}
          </div>
        </ScrollLoading>
      </div>
      <Modal
        title="连接数据库"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        footer={false}
      >
        <Form
          name="basic"
          labelCol={{ span: 5 }}
          // wrapperCol={{ span: 16 }}
          initialValues={{ remember: true }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
          autoComplete="off"
        >
          <Form.Item
            label="连接类型"
            name="type"
            rules={[{ required: true, message: '连接类型不可为空！' }]}
          >
            <Select>
              {
                databaseTypeList.map(item => {
                  return <Option key={item.code} value={item.code}>{item.name}</Option>
                })
              }
            </Select>
          </Form.Item>
          <Form.Item
            label="连接名"
            name="alias"
            rules={[{ required: true, message: '连接名不可为空！' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="连接地址"
            name="url"
            rules={[{ required: true, message: '连接地址不可为空！' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="端口"
            name="linkName"
            rules={[{ required: true, message: '端口不可为空！' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="用户名"
            name="user"
            rules={[{ required: true, message: '用户名不可为空！' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="密码"
            name="password"
            rules={[{ required: true, message: '密码不可为空！' }]}
          >
            <Input.Password />
          </Form.Item>
          <Form.Item
            wrapperCol={{ offset: 5 }}
            label={false}
            name="savePassword"
          >
            <Checkbox onChange={onChange}>保存密码</Checkbox>
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 0 }}>
            <div className={styles.formFooter}>
              <div className={styles.ceshi}>
                <Button className={styles.test}>测试连接</Button>
              </div>
              <div>
                <Button className={styles.cancel}>
                  取消
                </Button>
                <Button type="primary" htmlType="submit">
                  连接
                </Button>
              </div>
            </div>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
});
