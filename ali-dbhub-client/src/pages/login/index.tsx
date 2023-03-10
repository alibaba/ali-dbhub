import React from 'react';
import { Button, Form, Input } from 'antd';
import { getLocationHash } from '@/utils';
import './index.less';

const App: React.FC = () => {
  const onFinish = (values: any) => {
    console.log('Received values of form: ', values);
    // 1. 请求接口
    // 2. 成功后跳转
    const params = getLocationHash();
    const href = '#/' + (params?.callback ?? '');
    window.location.href = href;
  };

  return (
    <div className="login">
      <Form
        name="login"
        className="login-form"
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 16 }}
        onFinish={onFinish}
        size="large"
      >
        <div className="logo">ALi-DBHub</div>
        <Form.Item
          label="用户名"
          name="username"
          rules={[{ required: true, message: '请输入用户名' }]}
        >
          <Input placeholder="默认用户名: db_hub" />
        </Form.Item>
        <Form.Item
          label="密码"
          name="password"
          rules={[{ required: true, message: '请输入密码' }]}
        >
          <Input type="password" placeholder="默认密码: db_hub" />
        </Form.Item>

        <Button type="primary" htmlType="submit" className="login-form-button">
          登 录
        </Button>
      </Form>
    </div>
  );
};

export default App;
