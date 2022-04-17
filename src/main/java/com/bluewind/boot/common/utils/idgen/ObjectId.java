package com.bluewind.boot.common.utils.idgen;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuxingyu01
 * @date 2021-02-02-18:41
 * @description MongoDb ObjectId java版实现.
 **/
public final class ObjectId {
    private final static int MAX_RANDOM_NUMBER = 16777215;
    private static AtomicInteger numCount = new AtomicInteger(ThreadLocalRandom.current().nextInt(MAX_RANDOM_NUMBER));
    private final static String OBJECT_LOCK = "object_lock";

    private static byte[] getBytes() {
        //定义一个12位的字节数组
        ByteBuffer buffer = ByteBuffer.allocate(12);
        final int currentTimeSecond = (int) (System.currentTimeMillis() / 1000);
        //255->11111111->显示 补码变反码->11111111 - 1 = 11111110 反码变原码显示10000001
        //4个位置，分别存储原码，但是java存取的是补码，所以显示就不一样了
        buffer.putInt(currentTimeSecond);
        buffer.put(hostNameBytes());
        buffer.put(getPid());
        buffer.put(number());
        return buffer.array();
    }

    public static String next() {
        StringBuilder sb = new StringBuilder();
        final byte[] array = getBytes();
        for (byte b : array) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    // 获取3个字节的int值，作为每一秒同一台电脑同一个进程的随机数，最大值为16777215
    private static byte[] number() {
        //计数器
        int num = numCount.get();
        //需要判断是否超过值，双重检查
        if (num >= MAX_RANDOM_NUMBER) {
            synchronized (OBJECT_LOCK) {
                if (num >= MAX_RANDOM_NUMBER) {
                    numCount = new AtomicInteger(ThreadLocalRandom.current().nextInt(16777215));
                }
            }
        }
        num = numCount.incrementAndGet();
        return intBitToByte(num, 8, 0, 3);
    }

    //获取进程pid
    private static byte[] getPid() {
        String pidStr = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        int pid = Integer.parseInt(pidStr);
        return intBitToByte(pid, 8, 0, 2);
    }

    //主机名3个字节
    private static byte[] hostNameBytes() {
        final String hostName = getHostName();
        //左移，使低位的数据保留，一般来说高位可能多数是0
        //至于8位，是因为，int是32位，而我只需要24位，所以保留低位的24位
        int hashCode = hostName.hashCode() << 8;
        //把int的hashCode的高位24位转成3个8位的byte
        return intBitToByte(hashCode, 8, 8, 3);
    }

    //获取主机名
    private static String getHostName() {
        try {
            return new BufferedReader(
                    new InputStreamReader(Runtime.getRuntime().exec("hostname").getInputStream()))
                    .readLine();
        } catch (IOException ioException) {
            if (System.getProperty("os.name").startsWith("Windows")) {
                return System.getenv("COMPUTERNAME");
            } else {
                String hostname = System.getenv("HOSTNAME");
                if (hostname == null) {
                    //找不到就随机一个
                    hostname = String.valueOf((ThreadLocalRandom.current().nextInt()) << 16);
                }
                return hostname;
            }
        }
    }

    /**
     * @param val    操作值
     * @param shift  取的位数
     * @param offset 偏移位数
     * @param len    取多少个
     * @return int位上指定数量的byte
     */
    private static byte[] intBitToByte(int val, int shift, int offset, int len) {
        byte[] buf = new byte[len];
        int mask = 1 << shift;
        mask = mask - 1;
        val = val >>> offset;
        for (int i = 0; i < len; i++) {
            buf[len - 1 - i] = (byte) (val & mask);
            val >>>= shift;
        }
        return buf;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(next());
    }
}

