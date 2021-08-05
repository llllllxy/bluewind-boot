package com.liuxingyu.meco.websocket.service;


import com.liuxingyu.meco.websocket.vo.WebsocketVO;

/**
 * @author liuxingyu01
 * @date 2021-03-24-23:18
 **/
public interface WebsocketService {
    /**
     * 发送消息
     */
    void saveWebsocketVO(WebsocketVO websocketVO);
}
