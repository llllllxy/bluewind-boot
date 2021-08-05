package com.liuxingyu.meco.common.utils.web;


import com.liuxingyu.meco.common.utils.JsonTool;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liuxingyu01
 * @date 2021-01-30-11:21
 **/
public class ResponseUtil {

    public static void sendJson(ServletResponse response, Object responseObject) {
        //将实体对象转换为JSON Object转换
        String jsonStr = JsonTool.objectToJsonString(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
