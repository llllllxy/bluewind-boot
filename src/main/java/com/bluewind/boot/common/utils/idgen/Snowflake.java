package com.bluewind.boot.common.utils.idgen;

import java.util.Random;

/**
 * @author liuxingyu01
 * @date 2021-02-02-18:39
 * @description 来自于twitter项目snowflake的id产生方案，全局唯一，时间有序。
 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 * https://github.com/twitter/snowflake/blob/scala_28/src/main/scala/com/twitter/service/snowflake/IdWorker.scala
 */
public class Snowflake {
    private final static long twepoch = 1288834974657L;
    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 数据中心标识位数
    private final static long datacenterIdBits = 5L;
    // 机器ID最大值 31
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心ID最大值 31
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // 毫秒内自增位
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits
            + datacenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    private static long lastTimestamp = -1L;

    private long sequence = 0L;
    private final long workerId;
    private final long datacenterId;

    // 私有化示例要加上volatile，防止jvm重排序，导致空指针
    private static volatile Snowflake snowflake = null;
    private static final Object lock = new Object();


    /**
     * 单例禁止new实例化
     *
     * @param workerId
     * @param datacenterId
     */
    private Snowflake(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            if (workerId == -1) {
                this.workerId = getRandom(maxWorkerId);
            } else {
                throw new IllegalArgumentException(String.format("%s 机器中心ID最大值 必须是 %d 到 %d 之间", workerId, 0, maxWorkerId));
            }
        } else {
            this.workerId = workerId;
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            if (datacenterId == -1) {
                this.datacenterId = getRandom(maxDatacenterId);
            } else {
                throw new IllegalArgumentException(String.format("%s 数据中心ID最大值 必须是 %d 到 %d 之间", datacenterId, 0, maxDatacenterId));
            }
        } else {
            this.datacenterId = datacenterId;
        }
    }


    /**
     * 获取单例（懒汉式单例，有线程安全问题，所以加锁）
     *
     * @return
     */
    public static Snowflake getInstance() {
        if (snowflake == null) {
            synchronized (lock) {
                if (snowflake == null) {
                    long workerId = getRandom(maxWorkerId);
                    long dataCenterId = getRandom(maxDatacenterId);

                    snowflake = new Snowflake(workerId, dataCenterId);
                }
            }
        }
        return snowflake;
    }


    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("时钟向后移动，拒绝生成id " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }


    /**
     * 生成1-31之间的随机数
     *
     * @return
     */
    private static long getRandom(long maxId) {
        int max = (int) (maxId);
        int min = 1;
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

//	////////////  test  ////////////

//	public static void main(String[] args) throws Exception {
//        // CopyOnWriteArraySet是线程安全的
//		final CopyOnWriteArraySet set = new CopyOnWriteArraySet();
//
//		final IdWorker w1 = getInstanceIdWorker();
//		final IdWorker w2 = getInstanceIdWorker();
//		final CyclicBarrier cdl = new CyclicBarrier(100);
//
//		for (int i = 0; i < 1000; i++) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						cdl.await();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					} catch (BrokenBarrierException e) {
//						e.printStackTrace();
//					}
//
//					// id
//					Long id = w1.nextId();
//					if (set.contains(id)){
//						System.out.println(id + " exists");
//					}
//					set.add(id);
//					System.out.println(id);
//
//					// id2
//					Long id2 = w2.nextId();
//					if (set.contains(id2)){
//						System.out.println(id2 + " exists");
//					}
//					set.add(id2);
//					System.out.println(id2);
//				}
//			}).start();
//		}
//		try {
//			TimeUnit.SECONDS.sleep(5);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
}
