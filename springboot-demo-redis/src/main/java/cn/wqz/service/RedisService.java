package cn.wqz.service;

import java.util.List;

/**
 * redis 服务接口
 * 自定义对redis服务的封装
 */
public interface RedisService {

    /**
     * hash添加操做
     * @param redisKey hash redis键值，相当于标记唯一的map
     * @param key hash本身的键值
     * @param obj hash值
     * @param expire 过期时间
     */
    void hashPut(String redisKey, String key, Object obj, long expire);

    /**
     * hash删除
     * @param redisKey
     * @param key
     */
    void hashDelete(String redisKey, String key);

    /**
     * hash获取
     * @param redisKey
     * @param key
     * @return
     */
    Object hashGet(String redisKey, String key);

    /**
     * hash获取
     * @param redisKey
     * @return
     */
    List<Object> hashGetAll(String redisKey);

    /**
     * hash 判断键值是否存在
     * @param redisKey
     * @param key
     * @return
     */
    boolean hashKeyExists(String redisKey, String key);

    /**
     * hsah统计数量
     * @param redisKey
     * @return
     */
    long hashCount(String redisKey);

    /**
     * hash清空
     * @param redisKey
     */
    void hashDeleteAll(String redisKey);

}
