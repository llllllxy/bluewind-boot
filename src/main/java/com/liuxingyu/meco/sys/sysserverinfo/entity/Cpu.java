package com.liuxingyu.meco.sys.sysserverinfo.entity;

import com.liuxingyu.meco.common.utils.ArithmeticUtils;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-02-21-14:41
 **/
public class Cpu implements Serializable {
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public double getTotal() {
        return ArithmeticUtils.round(ArithmeticUtils.mul(total, 100), 2);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSys() {
        return ArithmeticUtils.round(ArithmeticUtils.mul(sys / total, 100), 2);
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public double getUsed() {
        return ArithmeticUtils.round(ArithmeticUtils.mul(used / total, 100), 2);
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getWait() {
        return ArithmeticUtils.round(ArithmeticUtils.mul(wait / total, 100), 2);
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getFree() {
        return ArithmeticUtils.round(ArithmeticUtils.mul(free / total, 100), 2);
    }

    public void setFree(double free) {
        this.free = free;
    }
}
