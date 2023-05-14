package com.alibaba.dbhub.server.start.config.listener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.alibaba.dbhub.server.start.config.listener.manage.ManageMessage;
import com.alibaba.dbhub.server.start.config.listener.manage.MessageTypeEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 用来管理启动
 * 防止启动多个
 *
 * @author zhuangjiaju
 * @date 2023/05/08
 */
@Slf4j
public class ManageApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        //Integer serverPort = event.getEnvironment().getProperty("server.port", Integer.class);
        //Assert.notNull(serverPort, "server.port配置信息");
        //log.info("启动端口为：{}", serverPort);
        //// 尝试访问确认应用是否已经启动
        //DataResult<String> dataResult;
        //try {
        //    dataResult = Forest.get("http://127.0.0.1:" + serverPort + "/api/system/get-version-a")
        //        .connectTimeout(Duration.ofMillis(80))
        //        .readTimeout(Duration.ofSeconds(1))
        //        .execute(new TypeReference<>() {});
        //} catch (Exception e) {
        //    // 抛出异常 代表没有旧的启动 或者旧的不靠谱
        //    log.info("尝试访问旧的应用失败", e);
        //
        //    // 尝试杀死旧的进程
        //    killOldIfNecessary();
        //    return;
        //}
        //
        //if (dataResult == null || BooleanUtils.isNotTrue(dataResult.getSuccess())) {
        //    // 尝试杀死旧的进程
        //    killOldIfNecessary();
        //    return;
        //}
        //
        // 代表旧的进程是可以用的


        //
        //Integer managePort = event.getEnvironment().getProperty("manage.port", Integer.class);
        //Assert.notNull(managePort, "无法获取manage.port配置信息");
        //log.info("启动管理服务：{}", managePort);
        //forest
        //try {
        //    serverSocket = new ServerSocket(managePort);
        //    // 启动一个线程来监听其他请求事件
        //    new Thread(() -> {
        //        while (true) {
        //            try {
        //                accept();
        //            } catch (Exception e) {
        //                log.error("处理管理消息异常", e);
        //            }
        //        }
        //    }).start();
        //    log.info("端口:{}未被占用，继续启动", managePort);
        //} catch (BindException e) {
        //    log.info("发现端口:{}已经被绑定，尝试去通讯.", managePort);
        //    // 校验是否是已经有其他应用启动
        //    try {
        //        checkRunning(managePort);
        //    } catch (Exception ex) {
        //        // 要么服务器挂了 要么是其他人占用端口了
        //        log.error("端口:{}被其他占用，请尝试使用-Dmanage.port= 修改端口", managePort, ex);
        //        System.exit(1);
        //    }
        //} catch (Exception e) {
        //    log.error("启动服务失败", e);
        //    System.exit(0);
        //}

    }

    private void killOldIfNecessary() {
        ProcessHandle.allProcesses().forEach(process -> {
            // 判断进程的命令行参数中是否包含指定的应用程序名称
            if (process.info().command().isPresent() && process.info().command().get().contains("ali-dbhub-server-start.jar")) {
                // 终止指定进程
                //process.destroy();
                log.info("xx");
            }
        });
    }

    private void checkRunning(Integer managePort) throws Exception {
        try (Socket socket = new Socket("127.0.0.1", managePort)) {
            socket.setSoTimeout(1000);
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
                objectOutputStream.writeObject(ManageMessage.builder()
                    .messageTypeEnum(MessageTypeEnum.HEARTBEAT)
                    .build());
                objectOutputStream.flush();
                try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
                    ActionResult result = (ActionResult)objectInputStream.readObject();
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

    //private void accept() throws Exception {
    //    //Socket socket = serverSocket.accept();
    //    socket.setSoTimeout(1000);
    //    try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
    //        ManageMessage message = (ManageMessage)objectInputStream.readObject();
    //        switch (message.getMessageTypeEnum()) {
    //            case HEARTBEAT:
    //                output(socket, ActionResult.isSuccess());
    //                break;
    //            default:
    //                output(socket, ActionResult.fail(CommonErrorEnum.COMMON_SYSTEM_ERROR,
    //                    "找不到指定消息类型：" + message.getMessageTypeEnum().name()));
    //                break;
    //        }
    //    }
    //    log.info("启动管理服务成功");
    //}

    private void output(Socket socket, ActionResult result) throws IOException {
        log.info("回复消息:{}", JSON.toJSONString(result));
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        }
    }
}