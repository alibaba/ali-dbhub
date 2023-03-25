import React, { memo, useEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import connectionServer from '@/service/connection'
import { IConnectionBase, ITreeNode } from '@/types'
import { databaseTypeList, databaseType, DatabaseTypeCode, EnvType, TreeNodeType } from '@/utils/constants'
import {
  Select,
  Button,
  Modal,
  Form,
  Input,
  message,
  Radio,
  // Menu,
} from 'antd';

const { Option } = Select;

export enum submitType {
  UPDATE = 'update',
  SAVE = 'save',
  TEST = 'test'
}

interface IProps {
  className?: string;
  rowData?: any;
  openModal: boolean;
  dataSourceType?: DatabaseTypeCode;
  submitCallback?: (data: ITreeNode) => void;
  onCancel?: () => void;
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

const oracelDriven = [
  {
    label: 'Thin',
    value: 'thin',
  },
  {
    label: 'OCI',
    value: 'oci',
  },
  {
    label: 'OCI8',
    value: 'oci8',
  },
]

const h2Driven = [
  {
    label: 'tcp',
    value: 'tcp',
  },
  {
    label: 'mem',
    value: 'mem',
  },
  {
    label: 'file',
    value: 'file',
  },
]

const envOptions = [
  { label: '日常', value: EnvType.DAILY },
  { label: '线上', value: EnvType.PRODUCT },
];

export default memo<IProps>(function ConnectionDialog(props) {
  const { className, rowData, onCancel, submitCallback, openModal, dataSourceType } = props;
  const [authentication, setAuthentication] = useState(1);
  const [currentDBType, setCurrentDBType] = useState(DatabaseTypeCode.MYSQL);

  useEffect(() => {
    if (!rowData) {
      form.setFieldsValue({
        type: DatabaseTypeCode.MYSQL,
        hostComputer: 'localhost',
        port: databaseType[DatabaseTypeCode.MYSQL].port,
        authentication: 1,
        url: 'jdbc:mysql://localhost:3306',
        alias: `localhost[1]`,
        EnvType: EnvType.DAILY

      });
    } else {
      const arr = rowData.url.split(':')
      const type: DatabaseTypeCode = arr[1]?.toUpperCase()
      form.setFieldsValue({
        ...rowData,
        port: arr[3],
        hostComputer: arr[2].split('//')[0] || arr[2].split('//')[1],
        authentication: (rowData.user && rowData.password) ? 1 : 0,
      });
    }
  }, [])

  useEffect(() => {
    const newForm: any = form
    const formData = newForm.getFieldValue()
    if (currentDBType === DatabaseTypeCode.ORACLE) {
      form.setFieldsValue({
        ...formData,
        sid: 'XE',
        driven: 'thin',
        port: databaseType[formData.type].port,
        hostComputer: 'localhost',
        database: '',
        url: `jdbc:${formData.type.toLowerCase()}:thin://${formData.hostComputer}:${formData.port}:XE`,
      });
    }
    if (currentDBType === DatabaseTypeCode.H2) {
      form.setFieldsValue({
        ...formData,
        port: databaseType[formData.type].port,
        hostComputer: 'localhost',
        database: '',
        driven: 'tcp',
        url: `jdbc:${formData.type.toLowerCase()}:tcp://${formData.hostComputer}:${formData.port}`,
      });
    }
    if (currentDBType === DatabaseTypeCode.MYSQL || currentDBType === DatabaseTypeCode.POSTGRESQL) {
      const p = {
        ...formData,
        port: databaseType[formData.type].port,
        hostComputer: 'localhost',
        database: '',
        url: `jdbc:${formData.type.toLowerCase()}://${formData.hostComputer}:${databaseType[formData.type].port}`,
      }
      form.setFieldsValue(p);
    }
  }, [currentDBType])

  useEffect(() => {
    if (dataSourceType) {
      form.setFieldValue('type', dataSourceType);
    }
  }, [dataSourceType])

  const [form] = Form.useForm();

  // 测试、保存、修改连接
  const saveConnection = (values: IConnectionBase, type: submitType) => {
    let p = values;

    if (type === submitType.UPDATE) {
      p.id = rowData?.id;
    }

    connectionServer[type](p).then(res => {
      if (type === submitType.TEST) {
        message.success(res === false ? '测试连接失败' : '测试连接成功');
      } else {
        const dataSource: ITreeNode = {
          name: p.alias,
          key: p.alias.toString(),
          nodeType: TreeNodeType.DATASOURCE,
          dataSourceId: p.id,
        }
        submitCallback?.(dataSource);
      }
    })
  }

  const submitConnection = (type: submitType) => {
    form.validateFields().then(res => {
      saveConnection(res, type);
    })
  }

  function onChangeForm(type: string) {
    const newForm: any = form;
    const formData = newForm.getFieldValue();

    if (type === 'port' || type === 'hostComputer') {
      form.setFieldsValue({
        ...formData,
        hostComputer: formData.hostComputer,
        url: `jdbc:${formData.type.toLowerCase()}://${formData.hostComputer}:${formData.port}`,
      });
    }

    if (type === 'database') {
      if (formData.database) {
        form.setFieldsValue({
          ...formData,
          url: `jdbc:${formData.type.toLowerCase()}://${formData.hostComputer}:${formData.port}/${formData.database}`,
        });
      } else {
        form.setFieldsValue({
          ...formData,
          url: `jdbc:${formData.type.toLowerCase()}://${formData.hostComputer}:${formData.port}`,
        });
      }
    }

    if (type === 'url') {
      try {
        const arr = formData.url.split(':');
        const type: DatabaseTypeCode = arr[1]?.toUpperCase();
        if (currentDBType === DatabaseTypeCode.ORACLE) {
          form.setFieldsValue({
            ...formData,
            port: arr[4],
            hostComputer: arr[3].split('//')[0] || arr[3].split('//')[1],
            type: DatabaseTypeCode[type],
            sid: arr[5],
            driven: arr[2],
          });
        } else if (currentDBType === DatabaseTypeCode.H2) {
          form.setFieldsValue({
            ...formData,
            port: arr[4].split('/')[0],
            hostComputer: arr[3].split('//')[0] || arr[3].split('//')[1],
            type: DatabaseTypeCode[type],
            driven: arr[2],
            database: arr[4].split('/')[1],
          });
        } else {
          form.setFieldsValue({
            ...formData,
            hostComputer: arr[2].split('//')[0] || arr[2].split('//')[1],
            type: DatabaseTypeCode[type],
            port: arr[3].split('/')[0],
            database: arr[3].split('/')[1],
          });
        }
      }
      catch {

      }
    }

    if (type === 'type') {
      setCurrentDBType(formData.type);
    }

    if (type == 'driven') {
      form.setFieldsValue({
        ...formData,
        url: `jdbc:${formData.type.toLowerCase()}:${formData.driven}://${formData.hostComputer}:${formData.port}` + (formData.database && `/${formData.database}`),
      });
    }

    if (currentDBType === DatabaseTypeCode.ORACLE && type !== 'url') {
      form.setFieldsValue({
        ...formData,
        url: `jdbc:${formData.type.toLowerCase()}:${formData.driven}://${formData.hostComputer}:${formData.port}:${formData.sid}`,
      });
    }
  }

  return <Modal
    title="连接数据库"
    open={openModal}
    onCancel={onCancel}
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
        <Select value={currentDBType} onChange={(value) => { onChangeForm('type') }}>
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
      {
        currentDBType === DatabaseTypeCode.H2 &&
        <div className={styles.moreLine}>
          <Form.Item
            label="数据库"
            name="database"
            className={styles.hostComputer}
          >
            <Input onChange={(value) => { onChangeForm('database') }} />
          </Form.Item>
          <Form.Item
            label="连接方式"
            name="driven"
            className={styles.port}
          >
            <Select onChange={() => { onChangeForm('driven') }}>
              {h2Driven.map(t => <Option key={t.value} value={t.value}>{t.label}</Option>)}
            </Select>
          </Form.Item>
        </div>
      }
      {
        currentDBType === DatabaseTypeCode.ORACLE &&
        < div className={styles.moreLine}>
          <Form.Item
            label="SID"
            name="sid"
            className={styles.hostComputer}
          >
            <Input onChange={() => { onChangeForm('sid') }} />
          </Form.Item>
          <Form.Item
            label="驱动"
            name="driven"
            className={styles.port}
          >
            <Select onChange={() => { onChangeForm('driven') }}>
              {oracelDriven.map(t => <Option key={t.value} value={t.value}>{t.label}</Option>)}
            </Select>
          </Form.Item>
        </div>
      }
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
            <Input id='user' />
          </Form.Item>
          <Form.Item
            id='password'
            label="密码"
            name="password"
          // rules={[{ required: true, message: '密码不可为空！' }]}
          >
            <Input.Password id='password' />
          </Form.Item>
        </div>
      }
      {
        currentDBType !== DatabaseTypeCode.ORACLE && currentDBType !== DatabaseTypeCode.H2 &&
        < Form.Item
          label="数据库"
          name="database"
        >
          <Input onChange={() => { onChangeForm('database') }} />
        </Form.Item>
      }
      <Form.Item
        label="URL"
        name="url"
      >
        <Input onChange={() => { onChangeForm('url') }} />
      </Form.Item>
      <Form.Item
        label="环境"
        name="EnvType"
      >
        <Radio.Group options={envOptions} />
      </Form.Item>
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
          <Button size='small' onClick={onCancel} className={styles.cancel}>
            取消
          </Button>
          <Button className={styles.save} size='small' type="primary" onClick={submitConnection.bind(null, rowData ? submitType.UPDATE : submitType.SAVE)}>
            {
              rowData ? '修改' : '连接'
            }
          </Button>
        </div>
      </div>
    </Form>
  </Modal >
})
