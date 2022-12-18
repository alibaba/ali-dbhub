import React, { memo, useEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import connectionServer from '@/service/connection'
import { IConnectionBase } from '@/types'
import { databaseTypeList, databaseType, DatabaseTypeCode } from '@/utils/constants'
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

const authenticationConfig = [
  {
    label: '用户与密码',
    value: 1,
  },
  {
    label: '无身份验证',
    value: 2,
  },
]

export default memo<IProps>(function ConnectionDialog(props) {
  const { isModalVisible, className, setIsModalVisible, rowData, getConnectionList, closeModal } = props
  const [authentication, setAuthentication] = useState(1);

  useEffect(() => {
    if (!rowData) {
      form.setFieldsValue({
        type: DatabaseTypeCode.MYSQL,
        hostComputer: 'localhost',
        port: databaseType[DatabaseTypeCode.MYSQL].port,
        authentication: 1,
        url: 'jdbc:mysql://localhost:3306',
        alias: `localhost[1]`
      });
    } else {
      const arr = rowData.url.split(':')
      const type: DatabaseTypeCode = arr[1].toUpperCase()
      form.setFieldsValue({
        ...rowData,
        port: arr[3],
        hostComputer: arr[2].split('//')[0] || arr[2].split('//')[1],
        authentication: (rowData.user && rowData.password) ? 1 : 0,
      });
    }
  }, [])

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
    })
  }

  function onChangeForm(type: string) {
    const newForm: any = form
    const formData = newForm.getFieldValue()
    console.log(formData)
    if (type === 'port' || type === 'hostComputer') {
      form.setFieldsValue({
        ...formData,
        hostComputer: formData.hostComputer,
        url: `jdbc:${formData.type.toLowerCase()}://${formData.hostComputer}:${formData.port}`,
      });
    }
    if (type === 'type') {
      form.setFieldsValue({
        ...formData,
        port: databaseType[formData.type].port,
        hostComputer: 'localhost',
        url: `jdbc:${formData.type.toLowerCase()}://${formData.hostComputer}:${databaseType[formData.type].port}`,
      });
    }
    if (type === 'url') {
      const arr = formData.url.split(':')
      const type: DatabaseTypeCode = arr[1].toUpperCase()
      form.setFieldsValue({
        ...formData,
        port: arr[3],
        hostComputer: arr[2].split('//')[0] || arr[2].split('//')[1],
        type: DatabaseTypeCode[type]
      });
    }
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
      initialValues={{ remember: true }}
      autoComplete="off"
      className={styles.form}
    >
      <Form.Item
        label="连接类型"
        name="type"
      >
        <Select onChange={(value) => { onChangeForm('type') }}>
          {
            databaseTypeList.map(item => {
              return <Option key={item.code} value={item.code}>{item.name}</Option>
            })
          }
        </Select>
      </Form.Item>
      <Form.Item
        label="名称"
        name="alias"
      >
        <Input />
      </Form.Item>
      <div className={styles.moreLine}>
        <Form.Item
          label="主机"
          name="hostComputer"
          className={styles.hostComputer}
        >
          <Input onChange={(value) => { onChangeForm('hostComputer') }} />
        </Form.Item>
        <Form.Item
          label="端口"
          name="port"
          className={styles.port}
        >
          <Input onChange={(value) => { onChangeForm('port') }} />
        </Form.Item>
      </div>
      <Form.Item
        label="身份验证"
        name="authentication"
        className={styles.authentication}
      >
        <Select onChange={setAuthentication}>
          {authenticationConfig.map(t => <Option key={t.value} value={t.value}>{t.label}</Option>)}
        </Select>
      </Form.Item>
      {
        authentication === 1 &&
        <div>
          <Form.Item
            label="用户名"
            name="user"
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
        </div>
      }

      <Form.Item
        label="URL"
        name="url"
      >
        <Input onChange={() => { onChangeForm('url') }} />
      </Form.Item>
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
  </Modal >
})
