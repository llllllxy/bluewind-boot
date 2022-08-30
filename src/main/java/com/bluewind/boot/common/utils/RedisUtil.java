package com.bluewind.boot.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxingyu01
 * @date 2020-12-26-0:46
 * @description Redis操作工具类（基于RedisTemplate）
 **/
@Component
public class RedisUtil {
    final static Logger log = LoggerFactory.getLogger(RedisUtil.class);

    // 构造函数注入
    private final RedisTemplate<String, Object> redisTemplate;
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }



    /*============================Common Start=============================*/
    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public void expire(final String key, final long time) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("RedisUtil - expire - 设置缓存时间失败，Exception：{e}", e);
        }
    }


    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间
     * @param unit 时间类型
     */
    public void expire(final String key, final long time, final TimeUnit unit) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, unit);
            }
        } catch (Exception e) {
            log.error("RedisUtil - expire - 设置缓存时间失败，Exception：{e}", e);
        }
    }


    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0则代表为永久有效
     */
    public long getExpire(final String key) {
        try {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("RedisUtil - getExpire - 获取过期时间失败，Exception：{e}", e);
            return 0;
        }
    }


    /**
     * 判断key是否存在
     *
     * @param key  键
     * @return true存在，false不存在
     */
    public boolean hasKey(final String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("RedisUtil - hasKey - 判断key是否存在失败，Exception：{e}", e);
            return false;
        }
    }


    /**
     * 修改 key 的名称
     *
     * @param oldKey 旧名字
     * @param newKey 新名字
     */
    public void rename(final String oldKey, final String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }


    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    /**
     * 模糊查询获取key值
     *
     * @param pattern 传入""查询所有key， 传入"shiro:test:" 查询所有这个开头的key
     * @return
     */
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern.concat("*"));
    }

    /*============================Common End=============================*/


    /*============================String Start=============================*/
    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值 Object
     */
    public Object get(final String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    /**
     * 批量获取缓存
     * @param keys Collection<key>
     * @return List<Object>
     */
    public List<Object> multiGet(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        } else {
            List<Object> result = redisTemplate.opsForValue().multiGet(keys);
            return result;
        }
    }


    /**
     * 普通缓存放入（永不失效）
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(final String key, final Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - set - 存入redis失败，Exception：{e}", e);
            return false;
        }
    }


    /**
     * 普通缓存放入并设置失效时间（默认单位：秒）
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功，false失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            log.info("RedisUtil - set - 存入redis成功，key：{}，value：{}", key, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - set - 存入redis失败，Exception：{e}", e);
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间
     *
     * @param key      键
     * @param value    值
     * @param time     时间 time要大于0 如果time小于等于0 将为无限期
     * @param timeUnit 时间单位TimeUnit
     *                 TimeUnit.DAYS          天
     *                 TimeUnit.HOURS         小时
     *                 TimeUnit.MINUTES       分钟
     *                 TimeUnit.SECONDS       秒
     *                 TimeUnit.MILLISECONDS  毫秒
     * @return true成功，false失败
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - set - 存入redis失败，Exception：{e}", e);
            return false;
        }
    }


    /**
     * 批量写入数据
     * @param map 键值对数据
     * @return true成功，false失败
     */
    public boolean multiSet(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().multiSet(map);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - multiSet - 批量存入redis失败，Exception：{e}", e);
        }
        return false;
    }


    /**
     * 根据key更新数据
     * @param key 键
     * @param value 值
     * @return true成功，false失败
     */
    public boolean update(final String key, Object value) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - update - 更新redis失败，Exception：{e}", e);
        }
        return false;
    }


    /**
     * 递增（将key所储存的值加上增量 increment；如果key不存在，那么key的值会先被初始化为0）
     *
     * @param key  键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }


    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    /*============================String end=============================*/


    /*================================Map==============================*/
    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true成功，false失败
     */
    public boolean hmSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - hmSet - Exception：{e}", e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - hmSet - Exception：{e}", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - hset - Exception：{e}", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - hset - Exception：{e}", e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以是多个 不能为null
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hDecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    /*================================Map  end====================*/


    /*============================set=============================*/
    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return Set<Object>
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("RedisUtil - sGet - Exception：{e}", e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("RedisUtil - sHasKey - Exception：{e}", e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("RedisUtil - sSet - Exception：{e}", e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("RedisUtil - sSetAndTime - Exception：{e}", e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("RedisUtil - sGetSetSize - Exception：{e}", e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("RedisUtil - setRemove - Exception：{e}", e);
            return 0;
        }
    }
    /*============================set end=============================*/


    /*===============================list=============================*/
    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("RedisUtil - lGet - Exception：{e}", e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("RedisUtil - lGetListSize - Exception：{e}", e);
            return 0;
        }
    }

    /**
     * 通过索引获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("RedisUtil - lGetIndex - Exception：{e}", e);
            return null;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil - lUpdateIndex - Exception：{e}", e);
            return false;
        }
    }

    /**
     * 将指定的值插入存储在键的列表的尾部。
     * 如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
     * @param key   键
     * @param value 值
     * @return Long 返回的结果为推送操作后的列表的长度
     */
    public Long lRightPush(String key, Object value) {
        try {
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.error("RedisUtil - lRightPush - Exception：{e}", e);
            return 0L;
        }
    }

    /**
     * 将指定的值插入存储在键的列表的尾部。
     * 如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
     * @param key   键
     * @param value 值
     * @param time  过期时间(秒)
     * @return Long 返回的结果为推送操作后的列表的长度
     */
    public Long lRightPush(String key, Object value, long time) {
        try {
            Long num = redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return num;
        } catch (Exception e) {
            log.error("RedisUtil - lRightPush - Exception：{e}", e);
            return 0L;
        }
    }

    /**
     * 将所有指定的值插入存储在键的列表的尾部。
     * 如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
     *
     * @param key   键
     * @param value 值
     */
    public void lRightPushAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
        } catch (Exception e) {
            log.error("RedisUtil - lRightPushAll - Exception：{e}", e);
        }
    }

    /**
     * 将所有指定的值插入存储在键的列表的尾部。
     * 如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
     * @param key   键
     * @param value 值
     * @param time  过期时间(秒)
     * @return Long 返回的结果为推送操作后的列表的长度
     */
    public Long lRightPushAll(String key, List<Object> value, long time) {
        try {
            Long num = redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return num;
        } catch (Exception e) {
            log.error("RedisUtil - lRightPushAll - Exception：{e}", e);
            return 0L;
        }
    }


    /**
     * 将指定的值插入存储在键的列表的头部。
     * 如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
     * @param key   键
     * @param value 值
     * @return Long 返回的结果为推送操作后的列表的长度
     */
    public Long lLeftPush(String key, Object value) {
        try {
            return redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            log.error("RedisUtil - lLeftPush - Exception：{e}", e);
            return 0L;
        }
    }

    /**
     * 将指定的值插入存储在键的列表的头部。
     * 如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
     * @param key   键
     * @param value 值
     * @param time  过期时间(秒)
     * @return Long 返回的结果为推送操作后的列表的长度
     */
    public Long lLeftPush(String key, Object value, long time) {
        try {
            Long num = redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return num;
        } catch (Exception e) {
            log.error("RedisUtil - lLeftPush - Exception：{e}", e);
            return 0L;
        }
    }


    /**
     * 将所有指定的值插入存储在键的列表的头部。
     * 如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
     * @param key   键
     * @param value 值
     * @return Long 返回的结果为推送操作后的列表的长度
     */
    public Long lLeftPushAll(String key, List<Object> value) {
        try {
            return redisTemplate.opsForList().leftPushAll(key, value);
        } catch (Exception e) {
            log.error("RedisUtil - lLeftPushAll - Exception：{e}", e);
            return 0L;
        }
    }

    /**
     * 将所有指定的值插入存储在键的列表的头部。
     * 如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
     * @param key   键
     * @param value 值
     * @param time  过期时间(秒)
     * @return Long 返回的结果为推送操作后的列表的长度
     */
    public Long lLeftPushAll(String key, List<Object> value, long time) {
        try {
            Long num = redisTemplate.opsForList().leftPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return num;
        } catch (Exception e) {
            log.error("RedisUtil - lLeftPushAll - Exception：{e}", e);
            return 0L;
        }
    }

    /**
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件。
     * 计数参数以下列方式影响操作：
     * count> 0：删除等于从头到尾移动的值的元素。
     * count <0：删除等于从尾到头移动的值的元素。
     * count = 0：删除等于value的所有元素。
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("RedisUtil - lRemove - Exception：{e}", e);
            return 0;
        }
    }

    /**
     * 弹出最左边的元素并返回，弹出之后该值在列表中将不复存在
     *
     * @param key 键
     * @return Object 弹出的元素
     */
    public Object lLeftPop(String key) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.error("RedisUtil - lLeftPop - Exception：{e}", e);
            return null;
        }
    }

    /**
     * 弹出最右边的元素并返回，弹出之后该值在列表中将不复存在
     *
     * @param key 键
     * @return Object 弹出的元素
     */
    public Object lRightPop(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            log.error("RedisUtil - lRightPop - Exception：{e}", e);
            return null;
        }
    }
    
    /*===============================list  end=============================*/


    /**
     * 使用Redis的消息队列
     *
     * @param channel 主题
     * @param message 消息内容
     */
    public void convertAndSend(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }


    //=========BoundListOperations 用法 start============

    /**
     * 将数据添加到Redis的list中（从右边添加）
     *
     * @param listKey  键
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @param values   待添加的数据
     */
    public void addToListRight(String listKey, Long time, TimeUnit timeUnit, Object... values) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //插入数据
        boundValueOperations.rightPushAll(values);
        //设置过期时间
        boundValueOperations.expire(time, timeUnit);
    }

    /**
     * 根据起始结束序号遍历Redis中的list
     *
     * @param listKey 键
     * @param start   起始序号
     * @param end     结束序号
     * @return
     */
    public List<Object> rangeList(String listKey, long start, long end) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //查询数据
        return boundValueOperations.range(start, end);
    }

    /**
     * 弹出右边的值 --- 并且移除这个值
     *
     * @param listKey
     */
    public Object rifhtPop(String listKey) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        return boundValueOperations.rightPop();
    }

    //=========BoundListOperations 用法 End============

}
