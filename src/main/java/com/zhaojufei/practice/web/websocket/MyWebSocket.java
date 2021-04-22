package com.zhaojufei.practice.web.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket类
 * 
 * @author Administrator
 *
 */
@ServerEndpoint(value = "/ws/setting-check")
@Component
@Slf4j
public class MyWebSocket {

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 在客户初次连接时触发，
     * 这里会为客户端创建一个session，这个session并不是我们所熟悉的httpsession
     * 
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this); // 加入set中
        addOnlineCount(); // 在线数加1
        log.info("有新连接加入！当前在线人数为 = {}，session = {}", getOnlineCount(), session.getId());
        try {
            sendMessage("当前人数为：" + getOnlineCount());
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 在客户端与服务器端断开连接时触发。
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        subOnlineCount(); // 在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * 
     * @param message 消息内容
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息 = {}，sessionid = {}", message, session.getId());

        // 群发消息
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用此方法
     * 
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息到页面
     * 
     * @param message 消息内容
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        // this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发消息，给所有人
     * 
     * @param message 消息内容
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException {
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 得到当前联接人数
     * 
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 增加联接人数
     */
    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    /**
     * 减少联接人数
     */
    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }

}
