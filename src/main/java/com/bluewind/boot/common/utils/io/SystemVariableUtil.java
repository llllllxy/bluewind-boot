package com.bluewind.boot.common.utils.io;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuxingyu01
 * @date 2020-09-12-21:21
 * @description 使用系统环境变量替换配置文件中的占位符
 **/
public class SystemVariableUtil {

    /**
     * 匹配${AAA_BBB}或者${AAA_BBB:xxx}形式字符串
     */
    private static final Pattern SYSTEM_VARIABLE_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");


    /**
     * 解析含有${}格式的propertyValue值,将其以环境变量替代
     * @param variableStr
     * @return
     */
    public static String convertSystemVariable(String variableStr) {
        Matcher matcher = SYSTEM_VARIABLE_PATTERN.matcher(variableStr);
        HashMap<String, String> temp = new HashMap<>();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String matchStr = variableStr.substring(start, end);
            String systemVariableKey = "";
            String defaultValue = "";
            if (matchStr.indexOf(":") > -1) {
                systemVariableKey = matchStr.split(":")[0].replace("${", "");
                defaultValue = matchStr.replace("${" + systemVariableKey + ":", "").replace("}", "");
            } else {
                systemVariableKey = matchStr.substring(2, matchStr.length() - 1);
            }
            // 读系统属性Property
            String systemProperties = System.getProperty(systemVariableKey);
            // 读环境变量Env
            String systemVariableValue = System.getenv().get(systemVariableKey);
            if (null != systemProperties) {
                temp.put(matchStr, systemProperties);
            } else if (null != systemVariableValue) {
                temp.put(matchStr, systemVariableValue);
            } else {
                temp.put(matchStr, defaultValue);
            }
        }
        for (String key : temp.keySet()) {
            variableStr = variableStr.replace(key, temp.get(key));
        }

        return variableStr;
    }


    public static void main(String[] args) {
        System.setProperty("app.name", "plm-server");
        System.setProperty("DATASOURCE_MASTER_URL", "127.0.0.1:3306/");

        String result1 = convertSystemVariable("jdbc:mysql://${DATASOURCE_MASTER_URL:10.10.250.149:3306/lambo}${app.name:1123}");
        System.out.println(result1);
        String result2 = convertSystemVariable("${app.name:1123}"); // 取不到环境变量的话，就取:后面的默认值
        System.out.println(result2);
        String result3 = convertSystemVariable("${app.name}");
        System.out.println(result3);
        String result4 = convertSystemVariable("${app.mongodb.credentials:root:Lms2020@admin}");
        System.out.println(result4);
    }
}
