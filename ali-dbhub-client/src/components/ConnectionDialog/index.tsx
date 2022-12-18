import React, { memo, useEffect } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import connectionServer from '@/service/connection'
import { IConnectionBase } from '@/types'
import { databaseTypeList } from '@/utils/constants'
import {
  Select,
  Button,
  Modal,
  Form,
  Input,
  message,
  // Menu,
} from 'antd';

const { Option } = Select;

interface IProps {
  className?: string;
  isModalVisible: boolean;
  setIsModalVisible: Function;
  rowData: any;
  getConnectionList: Function;
  closeModal: Function;
}

enum submitType {
  UPDATE = 'update',
  SAVE = 'save',
  TEST = 'test'
}

export default memo<IProps>(function ConnectionDialog(props) {
  const { isModalVisible, className, setIsModalVisible, rowData, getConnectionList, closeModal } = props

  const [form] = Form.useForm();

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const handleOk = () => {
    setIsModalVisible(true);
  };

  // 测试、保存、修改连接
  const saveConnection = (values: IConnectionBase, type: submitType) => {
    let p = values
    if (type === submitType.UPDATE) {
      p.id = rowData?.id
    }
    connectionServer[type](p).then(res => {
      if (type === submitType.TEST) {
        message.success(res === false ? '测试连接失败' : '测试连接成功')
      } else {
        getConnectionList()
        closeModal();
      }
    })
  };

  const submitConnection = (type: submitType) => {
    form.validateFields().then(res => {
      saveConnection(res, type)
    }).catch(error => {
    })
  }

  return <Modal
    title="连接数据库"
    open={isModalVisible}
    onOk={handleOk}
    onCancel={handleCancel}
    footer={false}
  >
    <Form
      form={form}
      labelCol={{ span: 5 }}
      // wrapperCol={{ span: 16 }}
      initialValues={{ remember: true }}
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
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="用户名"
        name="user"
      // rules={[{ required: true, message: '用户名不可为空！' }]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="密码"
        name="password"
      // rules={[{ required: true, message: '密码不可为空！' }]}
      >
        <Input.Password />
      </Form.Item>
      {/* <Form.Item
      wrapperCol={{ offset: 5 }}
      label={false}
      name="savePassword"
    >
      <Checkbox onChange={onChange}>保存密码</Checkbox>
    </Form.Item> */}
      <Form.Item wrapperCol={{ offset: 0 }}>
        <div className={styles.formFooter}>
          <div className={styles.test}>
            {
              !rowData &&
              <Button
                size='small'
                onClick={submitConnection.bind(null, submitType.TEST)}
                className={styles.test}>
                测试连接
              </Button>
            }
          </div>
          <div className={styles.rightButton}>
            <Button size='small' onClick={() => { closeModal() }} className={styles.cancel}>
              取消
            </Button>
            <Button className={styles.save} size='small' type="primary" onClick={submitConnection.bind(null, rowData ? submitType.UPDATE : submitType.SAVE)}>
              {
                rowData ? '修改' : '连接'
              }
            </Button>
          </div>
        </div>
      </Form.Item>
    </Form>
  </Modal>
})
