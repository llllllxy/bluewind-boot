package com.liuxingyu.meco.sys.sysserverinfo.entity;

import com.liuxingyu.meco.common.utils.ArithmeticUtils;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-02-21-14:47
 **/
public class Mem implements Serializable {
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return ArithmeticUtils.div(total, (1024 * 1024 * 1024), 2);
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public double getUsed() {
        return ArithmeticUtils.div(used, (1024 * 1024 * 1024), 2);
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public double getFree() {
        return ArithmeticUtils.div(free, (1024 * 1024 * 1024), 2);
    }

    public void setFree(long free) {
        this.free = free;
    }

    public double getUsage() {
        return ArithmeticUtils.mul(ArithmeticUtils.div(used, total, 4), 100);
    }
}
