import React, { useState } from "react";
import { Layout, Input, Button, List, Avatar } from "antd";

const { Header, Content, Footer } = Layout;

const ChatGPT = () => {
  const [messages, setMessages] = useState([]);
  const [inputValue, setInputValue] = useState("");

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleSendMessage = () => {
    if (inputValue.trim() !== "") {
      setMessages([...messages, inputValue]);
      setInputValue("");
    }
  };

  return (
    <Layout style={{ height: "100vh" }}>
      <Header>
        <h1 style={{ color: "#fff" }}>ChatGPT</h1>
      </Header>
      <Content style={{ padding: "50px" }}>
        <List
          itemLayout="horizontal"
          dataSource={messages}
          renderItem={(item) => (
            <List.Item>
              <List.Item.Meta
                avatar={
                  // <Avatar style={{ backgroundColor: "#87d068" }} icon={<UserOutlined />} />
                }
                title="ChatGPT"
                description={item}
              />
            </List.Item>
          )}
        />
        <Input
          placeholder="Type your message here"
          value={inputValue}
          onChange={handleInputChange}
          onPressEnter={handleSendMessage}
          suffix={
            <Button type="primary" onClick={handleSendMessage}>
              Send
            </Button>
          }
        />
      </Content>
      <Footer style={{ textAlign: "center" }}>ChatGPT ©2023 Created by OpenAI</Footer>
    </Layout>
  );
};

export default ChatGPT;
