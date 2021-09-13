package com.bluewind.boot.common.utils.idgen;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;

/**
 * @author liuxingyu01
 * @date 2021-02-02-18:44
 * @description 一种自定义的id生成算法
 **/
public class UUIDGenerator {

    private String sep = "";
    private int length = 32;
    private static final int IP;
    private static short counter;
    private static final int JVM;
    private static int mycount;
    private static Random random;

    public UUIDGenerator() {
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getNextSeqId() {
        this.setLength(32);
        return 36 + this.format(this.getIP()) + this.sep + this.format(this.getJVM()) + this.sep + this.format(this.getHiTime()) + this.sep + this.format(this.getLoTime()) + this.sep + this.format(this.getCount());
    }

    public Serializable getNextSeqId(int length) {
        this.setLength(length);
        ++mycount;
        StringBuffer sb = new StringBuffer(36);
        sb.append(this.format(this.getIP())).append(this.sep).append(this.format(this.getJVM())).append(this.sep).append(this.format(this.getHiTime())).append(this.sep).append(this.format(this.getLoTime())).append(this.sep).append(this.format(this.getCount())).toString();
        return this.processLength(sb);
    }

    public int getNextIntSeqId() {
        return random.nextInt();
    }

    protected int getJVM() {
        return JVM;
    }

    protected short getCount() {
        Class var1 = UUIDGenerator.class;
        synchronized(UUIDGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }

            short tmp19_16 = counter;
            counter = (short)(tmp19_16 + 1);
            return tmp19_16;
        }
    }

    protected int getIP() {
        return IP;
    }

    protected short getHiTime() {
        return (short)((int)(System.currentTimeMillis() >>> 32));
    }

    protected int getLoTime() {
        return (int)System.currentTimeMillis();
    }

    protected String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    private String processLength(StringBuffer sb) {
        if (this.getLength() != 32) {
            int offset = 32 - this.getLength();
            if (offset > 0) {
                sb.delete(0, offset);
            } else {
                sb.insert(0, this.sep);
                sb.insert(0, this.random(offset));
            }
        }

        return sb.toString();
    }

    private String random(int offset) {
        long curr = System.currentTimeMillis();
        String formatted = Long.toHexString((long)mycount + curr);
        StringBuffer buf = new StringBuffer();

        for(int i = offset; i < 0; ++i) {
            buf.append("0");
        }

        if (-offset < 8) {
            buf.replace(0, -offset, formatted.substring(formatted.length() + offset));
        } else if (-offset > 16) {
            buf.replace(0, 8, formatted.substring(formatted.length() - 8));
            formatted = Long.toHexString(curr + (long)mycount + (long)this.getCount());
            buf.replace(8, 16, formatted.substring(formatted.length() - 8));
        } else {
            buf.replace(0, 8, formatted.substring(formatted.length() - 8));
            formatted = Long.toHexString(curr + (long)mycount + (long)this.getCount() + curr / 10000L);
            buf.replace(8, -offset, formatted.substring(formatted.length() + offset + 8));
        }

        return buf.toString();
    }

    public static int toInt(byte[] bytes) {
        int result = 0;

        for(int i = 0; i < 4; ++i) {
            result = (result << 8) - -128 + bytes[i];
        }

        return result;
    }

    static {
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception var2) {
            ipadd = 0;
        }

        IP = ipadd;
        counter = 0;
        JVM = (int)(System.currentTimeMillis() >>> 8);
        mycount = 0;
        random = new Random();
    }
}
