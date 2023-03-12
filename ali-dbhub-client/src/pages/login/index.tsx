import React, { useCallback, useEffect } from 'react';
import { Button, Form, Input } from 'antd';
import { getLocationHash } from '@/utils';
import './index.less';
import { getUser, userLogin } from '@/service/user';

interface IFormData {
  userName: string;
  password: string;
}

const App: React.FC = () => {
  const handleLogin = useCallback(async (values: IFormData) => {
    let res = await userLogin(values);
    if (res) {
      const params = getLocationHash();
      const href = '#/' + (params?.callback ?? '');
      window.location.href = href;
    }
  }, []);

  return (
    <div className="login">
      <Form
        size="large"
        name="login"
        className="login-form"
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 16 }}
        onFinish={(values: IFormData) => {
          handleLogin(values);
        }}
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
