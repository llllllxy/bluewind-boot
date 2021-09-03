package com.liuxingyu.meco.timertask;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuxingyu01
 * @date 2021-08-27-20:46
 **/
@Component("mecoTask")
public class MecoTask {
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println("执行多参方法： 字符串类型=" + s +"，布尔类型=" + b + "，长整型="+ l + "，浮点型=" + d + "，整形=" + i);
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法，参数为：" + params + " 当前时间为:" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
    }

    public void ryNoParams() {
        System.out.println("执行无参方法，当前时间为:" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
    }
}
