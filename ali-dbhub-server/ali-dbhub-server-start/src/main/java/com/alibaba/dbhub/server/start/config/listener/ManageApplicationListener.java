package com.alibaba.dbhub.server.start.config.listener;

import com.alibaba.dbhub.server.start.config.listener.manage.ManageMessage;
import com.alibaba.dbhub.server.start.config.listener.manage.MessageTypeEnum;
import com.alibaba.dbhub.server.tools.base.excption.CommonErrorEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 用来管理启动
 * 防止启动多个
 *
 * @author zhuangjiaju
 * @date 2023/05/08
 */
@Slf4j
public class ManageApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {


    private ServerSocket serverSocket;

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Integer managePort = event.getEnvironment().getProperty("manage.port", Integer.class);
        Assert.notNull(managePort, "无法获取manage.port配置信息");
        log.info("启动管理服务：{}", managePort);

        try {
            serverSocket = new ServerSocket(managePort);
            // 启动一个线程来监听其他请求事件
            new Thread(() -> {
                while (true) {
                    try {
                        accept();
                    } catch (Exception e) {
                        log.error("处理管理消息异常", e);
                    }
                }
            }).start();
            log.info("端口:{}未被占用，继续启动", managePort);
        } catch (BindException e) {
            log.info("发现端口:{}已经被绑定，尝试去通讯.", managePort);
            // 校验是否是已经有其他应用启动
            try {
                checkRunning(managePort);
            } catch (Exception ex) {
                // 要么服务器挂了 要么是其他人占用端口了
                log.error("端口:{}被其他占用，请尝试使用-Dmanage.port= 修改端口", managePort, ex);
                System.exit(1);
            }
        } catch (Exception e) {
            log.error("启动服务失败", e);
            System.exit(0);
        }

    }

    private void checkRunning(Integer managePort) throws Exception {
        try (Socket socket = new Socket("127.0.0.1", managePort)) {
            socket.setSoTimeout(1000*10);
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
                objectOutputStream.writeObject(ManageMessage.builder()
                        .messageTypeEnum(MessageTypeEnum.HEARTBEAT)
                        .build());
                objectOutputStream.flush();
                try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
                    ActionResult result = (ActionResult) objectInputStream.readObject();
                    // 代表已经有其他应用启动了
                    if (result.success()) {
                        log.info("当前接口已经存在启动的应用了，本应用不在启动");
                        System.exit(0);
                    }
                    log.error("启动异常{}", JSON.toJSONString(result));
                    System.exit(1);
                }
            }
        }
    }


    private void accept() throws Exception {
        Socket socket = serverSocket.accept();
        socket.setSoTimeout(1000*10);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            ManageMessage message = (ManageMessage) objectInputStream.readObject();
            switch (message.getMessageTypeEnum()) {
                case HEARTBEAT:
                    output(socket, ActionResult.isSuccess());
                    break;
                default:
                    output(socket, ActionResult.fail(CommonErrorEnum.COMMON_SYSTEM_ERROR, "找不到指定消息类型：" + message.getMessageTypeEnum().name()));
                    break;
            }
        }
        log.info("启动管理服务成功");
    }

    private void output(Socket socket, ActionResult result) throws IOException {
        log.info("回复消息:{}", JSON.toJSONString(result));
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        }
    }
}