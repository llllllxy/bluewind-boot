package com.liuxingyu.meco.websocket.config;

import com.liuxingyu.meco.websocket.service.WebsocketService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author liuxingyu01
 * @date 2021-07-28-20:07
 * @description WebSocket服务端
 **/
@Component
@ServerEndpoint(value = "/websocket/{sid}")
public class WebSocketServer {
    final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 业务处理层
     */
    public static WebsocketService websocketService;

    /**
     * 接收sid
     */
    private Integer sid = 0;

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") Integer sid) {
        if (null == sid || 0 == sid) {
            log.info("该用户没有登录,不能连接 Websocket！");
            return;
        }
        this.session = session;
        // 加入websocket
        webSocketSet.add(this);
        // 赋予用户id
        this.sid = sid;
        // 在线数加1
        addOnlineCount();
        // 给当前用户发消息
        sendMessage("连接 Websocket 成功", sid);
        log.info("该用户sid为：{} 连接 Websocket 成功！当前在线人数为：{}", sid, getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        // 在线数减1
        subOnlineCount();
        log.info("有一连接关闭，sid为{}！当前在线人数为{}", this.sid, getOnlineCount());
    }

    /**
     * 接收到消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("接收到来自：sid为{}，用户的消息:{}", this.sid, message);
        if (this.session.isOpen()) {
            this.session.getAsyncRemote().sendText(message);
        } else {
            log.info("当前用户session已关闭!");
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端给客户端发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message, @PathParam("sid") Integer sid) {
        if (StringUtils.isBlank(message)) {
            log.info("不能发送空的消息");
            return;
        }
        try {
            for (WebSocketServer item : webSocketSet) {
                // 没有传入sid表示群发
                if (null == sid || 0 == sid) {
                    item.sendMessage(message);
                }
                // 给指定用户发
                else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
                log.info("服务端推送消息到客户端成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("服务端推送消息到客户端失败");
        }
    }

    /**
     * 实现服务器主动推送消息
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 获取在线人数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 增加在线人数
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 减少在线人数
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    /**
     * 获取 WebSocket 对象
     */
    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }

}
