package com.liuxingyu.meco.websocket.config;

import com.liuxingyu.meco.websocket.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author liuxingyu01
 * @date 2021-07-28-20:06
 * @description 开启WebSocket支持
 **/
@Configuration
public class WebsocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 注入业务层对象
     *
     * @param websocketService
     */
    @Autowired
    private void setChatLogService(WebsocketService websocketService) {
        WebSocketServer.websocketService = websocketService;
    }
}
