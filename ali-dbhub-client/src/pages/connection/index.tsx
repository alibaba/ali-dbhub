import React, { memo, useEffect, useLayoutEffect, useRef, useState } from 'react';
import classnames from 'classnames';
import { formatNaturalDate } from '@/utils/index';
import Iconfont from '@/components/Iconfont';
import ScrollLoading from '@/components/ScrollLoading';
import StateIndicator from '@/components/StateIndicator';
import i18n from '@/i18n';
import { history } from 'umi';
import connectionServer from '@/service/connection'
import { IConnectionBase } from '@/types'
import { databaseTypeList, DatabaseTypeCode, databaseType } from '@/utils/constants'
import {
  Dropdown,
  Space,
  Select,
  Button,
  Modal,
  Form,
  Input,
  Checkbox,
  message,
  // Menu,
  Pagination
} from 'antd';

import styles from './index.less';
import globalStyle from '@/global.less';
import Menu, { IMenu, MenuItem } from '@/components/Menu';

const { Option } = Select;

interface IProps {
  className?: any;
  onlyList?: boolean;
}

enum submitType {
  UPDATE = 'update',
  SAVE = 'save',
  TEST = 'test'
}

enum handleType {
  EDIT = 'edit',
  DELETE = 'delete',
  CLONE = 'clone'
}

const menuList = [
  {
    key: handleType.EDIT,
    icon: '\ue60f',
    title: i18n('connection.button.edit'),
  },
  {
    key: handleType.CLONE,
    icon: '\ue6ca',
    title: i18n('connection.button.clone'),
  },
  {
    key: handleType.DELETE,
    icon: '\ue604',
    title: i18n('connection.button.delete'),
  }
];

export default memo<IProps>(function ConnectionPage(props) {

  const { className, onlyList } = props;
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [connectionList, setConnectionList] = useState<IConnectionBase[]>();
  const [finished, setFinished] = useState(false);
  const [rowData, setRowData] = useState<IConnectionBase | null>();
  const [form] = Form.useForm();
  const scrollerRef = useRef(null);
  const [pageNo, setPageNo] = useState(0);

  useEffect(() => {
  }, [])

  type IParams = {
    superposition: boolean
  }

  const resetGetList = () => {
    setPageNo(0);
  }

  const getConnectionList = (params?: IParams) => {
    const { superposition } = params || {}
    let p = {
      pageNo: pageNo + 1,
      pageSize: 10
    }
    return connectionServer.getList(p).then(res => {
      if (connectionList?.length && superposition) {
        setConnectionList([...connectionList, ...res.data])
      } else {
        setConnectionList(res.data)
      }

      if (!res.hasNextPage) {
        setFinished(true)
      }
    })
  }

  const jumpPage = (item: IConnectionBase) => {
    history.push({
      pathname: `/database/${item.type}/${item.id}`,
    });
  };



  const showLinkModal = () => {
    setIsModalVisible(true);
    setRowData(null);
    form.resetFields();
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const handleOk = () => {
    setIsModalVisible(true);
  };

  // 测试、保存、修改连接
  const saveConnection = (values: IConnectionBase, type: submitType) => {
    let p = values
    connectionServer[type](p).then(res => {
      if (type === submitType.TEST) {
        message.success(res === false ? '测试连接失败' : '测试连接成功')
      } else {
        getConnectionList()
        closeModal();
      }
    })
  };

  const onChange = () => { };

  const closeModal = () => {
    setRowData(null);
    form.resetFields();
    setIsModalVisible(false);
  }

  const submitConnection = (type: submitType) => {
    form.validateFields().then(res => {
      saveConnection(res, type)
    }).catch(error => {
    })
  }

  const RenderCard = ({ item }: { item: IConnectionBase }) => {
    const [openDropdown, setOpenDropdown] = useState(false);
    const closeDropdownFn = () => {
      setOpenDropdown(false);
    }
    useEffect(() => {
      if (openDropdown) {
        document.documentElement.addEventListener('click', closeDropdownFn);
      }
      return () => {
        document.documentElement.removeEventListener('click', closeDropdownFn);
      }
    }, [openDropdown])

    const renderMenu = (rowData: IConnectionBase) => {
      const editConnection = () => {
        setRowData(rowData);
        setIsModalVisible(true);
        form.setFieldsValue(rowData);
      }

      const deleteConnection = () => {
        resetGetList()
        connectionServer.remove({ id: rowData.id! }).then(res => {
          message.success('删除成功');
          getConnectionList();
        })
      }

      const cloneConnection = () => {
        resetGetList()
        connectionServer.clone({ id: rowData.id! }).then(res => {
          message.success('克隆成功');
          getConnectionList();
        })
      }

      const clickMenuList = (item: any) => {
        switch (item.key) {
          case handleType.EDIT:
            return editConnection();
          case handleType.DELETE:
            return deleteConnection();
          case handleType.CLONE:
            return cloneConnection();
        }
      }
      return <Menu>
        {
          menuList.map((item, index) => {
            return <MenuItem key={index}>
              <span onClick={clickMenuList.bind(null, item)}>
                <Iconfont code={item.icon!}></Iconfont>
                {item.title}
              </span>
            </MenuItem>
          })
        }
      </Menu>
    };

    return <div key={item.id} className={styles.connectionItem}>
      <div className={styles.left} onClick={jumpPage.bind(null, item)}>
        <div
          className={styles.logo}
          style={{
            backgroundImage: `url(${databaseType[item.type].img})`,
          }}
        ></div>
        <div className={styles.name}>{item.alias}</div>
      </div>
      {
        !onlyList &&
        <div className={styles.right}>
          <Dropdown open={openDropdown} overlay={renderMenu(item)} trigger={['hover']}>
            <a onClick={(event) => { event.stopPropagation(); setOpenDropdown(true) }}>
              <div className={styles.moreActions}>
                <Iconfont code="&#xe601;" />
              </div>
            </a>
          </Dropdown>
        </div>
      }
    </div>
  }

  return (
    <div className={classnames(className, styles.box)}>
      {
        !onlyList &&
        <div className={styles.header}>
          <div className={styles.title}>{i18n('home.nav.database')}</div>
          <Button
            className={styles.linkButton}
            type="primary"
            onClick={showLinkModal}
          >
            <Iconfont code="&#xe631;"></Iconfont>
            {i18n('connection.input.newLink')}
          </Button>
        </div>
      }
      <div className={styles.scrollBox} ref={scrollerRef}>
        <ScrollLoading
          finished={finished}
          scrollerElement={scrollerRef}
          onReachBottom={getConnectionList.bind(null, { superposition: true })}
          threshold={200}
        >
          {
            !!connectionList?.length &&
            <div className={styles.connectionList}>
              {connectionList?.map(item => <RenderCard key={item.id} item={item}></RenderCard>)}
            </div>
          }
        </ScrollLoading>
        {!connectionList?.length && <StateIndicator state='empty'></StateIndicator>}
      </div>
      <Modal
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
          {/* <Form.Item
            label="端口"
            name="linkName"
          // rules={[{ required: true, message: '端口不可为空！' }]}
          >
            <Input />
          </Form.Item> */}
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
                  !rowData && <Button onClick={submitConnection.bind(null, submitType.TEST)} className={styles.test}>测试连接</Button>
                }
              </div>
              <div>
                <Button onClick={closeModal} className={styles.cancel}>
                  取消
                </Button>
                <Button type="primary" onClick={submitConnection.bind(null, rowData ? submitType.UPDATE : submitType.SAVE)}>
                  {
                    rowData ? '修改' : '连接'
                  }
                </Button>
              </div>
            </div>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
});
