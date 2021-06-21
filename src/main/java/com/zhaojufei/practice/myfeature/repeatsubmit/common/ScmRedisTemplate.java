package com.zhaojufei.practice.myfeature.repeatsubmit.common;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScmRedisTemplate {

    private RedisTemplate redisTemplate;

    private RedissonClient redissonClient;

    private static TimeUnit SECONDS_UNIT = TimeUnit.SECONDS;
    /**
     * 分布式锁的前缀
     */
    private static String LOCK_KEY_PREFIX = "credit:locker:";
    /**
     * 获取分布式锁最大等待时间（秒）
     */
    private static long LOCK_MAX_WAIT_SECTOND = 30;
    /**
     * 获取锁后多久自动释放锁（秒）
     */
    private static long LOCK_MAX_LEASE_SECTOND = 60;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, SECONDS_UNIT);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(Collection keys) {
        redisTemplate.delete(keys);
    }

    public Boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public void multiSet(Map<? extends String, ?> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    public Boolean multiSetIfAbsent(Map<? extends String, ?> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 判断是否存在某key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置key超时秒数
     */
    public void expireSeconds(String key, long timeout) {
        redisTemplate.expire(key, timeout, SECONDS_UNIT);
    }

    /**
     * 获取key的超时时间（秒）
     */
    public long getExpireSeconds(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 新增Map操作
     */
    public void mapOpsPutAll(String key, Map<String, ?> map) {
        this.redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 设置map某个域的值
     */
    public void mapOpsPut(String key, String hashKey, Object value) {

        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取Map下域的值
     */
    public Object mapOpsGet(String key, String hashKey) {

        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取Map的size
     */
    public long mapOpsSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 判断map中是否有给定hashkey
     * 
     * @param key
     * @param hashKey
     * @return
     */
    public Boolean mapOpsHashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 删除map下的域
     */
    public long mapOpsDel(String key, String hashKey) {
        return redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 获取分布式锁
     * 
     * @param key 分布式锁key
     * @param callback 获取锁后的回调
     * @return
     */
    public Object lock(String key, LockCallback callback) {
        return lock(key, LOCK_MAX_WAIT_SECTOND, LOCK_MAX_LEASE_SECTOND, callback);
    }

    /**
     * 获取分布式锁 -- 多个key
     * 
     * @param keys 分布式锁的key
     * @param callback 获取锁之后的回调
     * @return
     */
    public Object mlock(List<String> keys, LockCallback callback) {
        return mlock(keys, LOCK_MAX_WAIT_SECTOND, LOCK_MAX_LEASE_SECTOND, callback);
    }

    /**
     * 获取分布式锁
     * 
     * @param key 分布式锁key
     * @param waitTime 获取锁时最长等待时间
     * @param leaseTime 获取到锁后，多久后自动释放锁
     * @param callback 回调操作
     * @return
     */
    public Object lock(String key, long waitTime, long leaseTime, LockCallback callback) {
        String locKey = LOCK_KEY_PREFIX + key;
        RLock rLock = redissonClient.getLock(locKey);
        try {
            boolean locked = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            // 如果获取到了锁
            if (locked) {
                return callback.success();
            } else {
                return callback.fail();
            }

        } catch (InterruptedException e) {
            log.error("获取分布式锁时出现异常，lockKey = {},errorMsg = {}, error stack = {}", locKey, e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }
    }

    /**
     * 获取分布式锁
     * 
     * @param keys 分布式锁key数组
     * @param waitTime 获取锁时最长等待时间
     * @param leaseTime 获取到锁后，多久后自动释放锁
     * @param callback 回调操作
     * @return
     */
    public Object mlock(List<String> keys, long waitTime, long leaseTime, LockCallback callback) {
        List<RLock> rLockList = new ArrayList<>();
        for (String key : keys) {
            key = LOCK_KEY_PREFIX + key;
            RLock rLock = redissonClient.getLock(key);
            rLockList.add(rLock);
        }
        try {
            boolean locked = true;
            for (RLock rLock : rLockList) {
                boolean islocked = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
                if (!islocked) {
                    locked = islocked;
                    break;
                }
            }
            if (locked) {
                // 所有的key都获取到了锁
                return callback.success();
            } else {
                // 有些key 没有获取的锁
                return callback.fail();
            }

        } catch (InterruptedException e) {
            log.error("获取分布式锁时出现异常，lockKey = {},errorMsg = {}, error stack = {}", keys, e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            for (RLock rLock : rLockList) {
                if (rLock.isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }
        }
    }

    /**
     * 获取锁成功+失败，执行响应的方法
     *
     * @param lockey 锁key
     * @param lockExecutor 锁获取成功执行方法
     * @param unLockExecutor 锁获取失败执行方法
     * @param <T> 反回对象类型
     * @return 返回执行结果，类型为T，如果业务方法报错，返回null
     */
    public <T> T lock(String lockey, Executor<T> lockExecutor, Executor<T> unLockExecutor) {
        return this.lock(lockey, LOCK_MAX_LEASE_SECTOND, lockExecutor, unLockExecutor);
    }

    /**
     * 获取锁成功+失败，执行响应的方法
     *
     * @param lockey 锁key
     * @param leaseTime key存活时间
     * @param lockExecutor 锁获取成功执行方法
     * @param unLockExecutor 锁获取失败执行方法
     * @param <T> 反回对象类型
     * @return 返回执行结果，类型为T，如果业务方法报错，返回null
     */
    public <T> T lock(String lockey, long leaseTime, Executor<T> lockExecutor, Executor<T> unLockExecutor) {

        RLock lock = redissonClient.getLock(lockey);

        // 使用基于Redis的分布式锁获取锁
        lock.lock(leaseTime, TimeUnit.SECONDS);

        boolean locked = Thread.currentThread().isInterrupted() ? false : true;

        // 定义返回值
        T t = null;

        try {
            log.info("lock获取锁结果：{}", locked);
            if (locked) {
                if (lockExecutor == null) {
                    log.error("lockey:{}, 未配置获取到锁执行方法！", lockey);
                    throw new RuntimeException("lockey:" + lockey + ", 未配置获取到锁执行方法！");
                }
                t = lockExecutor.exec();
            } else {
                if (unLockExecutor == null) {
                    log.error("lockey:{}, 未配置未获取到锁执行方法！", lockey);
                    throw new RuntimeException("lockey:" + lockey + ", 未配置未获取到锁执行方法！");
                }
                t = unLockExecutor.exec();
            }
        } catch (Exception e) {
            log.error("执行业务方法出错，errorMsg = {}, error stack = {}", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (locked) {
                lock.unlock();
            }
            return t;
        }
    }

    /**
     * 获取等多个锁方法，所有锁获取成功，执行lockExecutor，否则执行unLockExecutor，最后释放获取到的锁。
     * 获取多个锁，需要预估业务处理时间，设置正确的leaseTime
     *
     * @param lockeys 锁key
     * @param lockExecutor 锁获取成功执行方法
     * @param unLockExecutor 锁获取失败执行方法
     * @param <T> 反回对象类型
     * @return 返回执行结果，类型为T，如果业务方法报错，返回null
     */
    public <T> T multiLock(List<String> lockeys, Executor<T> lockExecutor, Executor<T> unLockExecutor) {
        return this.multiLock(lockeys, LOCK_MAX_LEASE_SECTOND, lockExecutor, unLockExecutor);
    }

    /**
     * 获取等多个锁方法，所有锁获取成功，执行lockExecutor，否则执行unLockExecutor，最后释放获取到的锁。
     * 获取多个锁，需要预估业务处理时间，设置正确的leaseTime
     *
     * @param lockeys 锁key
     * @param leaseTime 锁存活时间
     * @param lockExecutor 锁获取成功执行方法
     * @param unLockExecutor 锁获取失败执行方法
     * @param <T> 反回对象类型
     * @return 返回执行结果，类型为T，如果业务方法报错，返回null
     */
    public <T> T multiLock(List<String> lockeys, long leaseTime, Executor<T> lockExecutor, Executor<T> unLockExecutor) {

        List<RLock> lockList = Lists.newLinkedList();

        boolean locked = true;

        for (String key : lockeys) {
            // 只要有一个失败，即为获取锁失败
            if (!Thread.currentThread().isInterrupted()) {
                RLock lock = redissonClient.getLock(key);
                // 使用基于Redis的分布式锁获取锁
                lock.lock(leaseTime, TimeUnit.SECONDS);
                lockList.add(lock);
            } else {
                locked = false;
                break;
            }
        }

        // 定义返回值
        T t = null;

        try {
            log.info("multiLock获取锁结果：{}", locked);
            if (locked) {
                if (lockExecutor == null) {
                    log.error("lockey:{}, 未配置获取到锁执行方法！", lockeys);
                    throw new RuntimeException("lockey:" + lockeys + ", 未配置获取到锁执行方法！");
                }
                t = lockExecutor.exec();
            } else {
                if (unLockExecutor == null) {
                    log.error("lockey:{}, 未配置未获取到锁执行方法！", lockeys);
                    throw new RuntimeException("lockey:" + lockeys + ", 未配置未获取到锁执行方法！");
                }
                t = unLockExecutor.exec();
            }
        } catch (Exception e) {
            log.error("执行业务方法出错，errorMsg = {}, error stack = {}", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if ( !CollectionUtils.isEmpty(lockList)) {
                lockList.forEach(lock -> lock.unlock());
            }
            return t;
        }
    }

    /**
     * 获取锁成功+失败，执行响应的方法
     *
     * @param lockey 锁key
     * @param lockExecutor 锁获取成功执行方法
     * @param unLockExecutor 锁获取失败执行方法
     * @param <T> 反回对象类型
     * @return 返回执行结果，类型为T，如果业务方法报错，返回null
     */
    public <T> T tryLock(String lockey, Executor<T> lockExecutor, Executor<T> unLockExecutor) {
        return tryLock(lockey, LOCK_MAX_WAIT_SECTOND, LOCK_MAX_LEASE_SECTOND, lockExecutor, unLockExecutor);
    }

    /**
     * 获取锁成功+失败，执行响应的方法
     *
     * @param lockey 锁key
     * @param lockExecutor 锁获取成功执行方法
     * @param unLockExecutor 锁获取失败执行方法
     * @param <T> 反回对象类型
     * @return 返回执行结果，类型为T，如果业务方法报错，返回null
     */
    public <T> T tryLock(String lockey, long waitTime, long leaseTime, Executor<T> lockExecutor,
            Executor<T> unLockExecutor) {

        RLock lock = redissonClient.getLock(lockey);

        boolean locked;

        try {
            // 使用基于Redis的分布式锁获取锁
            locked = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            locked = false;
        }

        // 定义返回值
        T t = null;

        try {
            log.info("trylock获取锁结果：{}", locked);
            if (locked) {
                if (lockExecutor == null) {
                    log.error("lockey:{}, 未配置获取到锁执行方法！", lockey);
                    throw new RuntimeException("lockey:" + lockey + ", 未配置获取到锁执行方法！");
                }
                t = lockExecutor.exec();
            } else {
                if (unLockExecutor == null) {
                    log.error("lockey:{}, 未配置未获取到锁执行方法！", lockey);
                    throw new RuntimeException("lockey:" + lockey + ", 未配置未获取到锁执行方法！");
                }
                t = unLockExecutor.exec();
            }
        } catch (Exception e) {
            log.error("执行业务方法出错，errorMsg = {}, error stack = {}", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (locked) {
                lock.unlock();
            }
            return t;
        }
    }

    /**
     * 获取等多个锁方法，所有锁获取成功，执行lockExecutor，否则执行unLockExecutor，最后释放获取到的锁。
     * 获取多个锁，需要预估业务处理时间，设置正确的leaseTime
     *
     * @param lockeys 锁key
     * @param lockExecutor 锁获取成功执行方法
     * @param unLockExecutor 锁获取失败执行方法
     * @param <T> 反回对象类型
     * @return 返回执行结果，类型为T，如果业务方法报错，返回null
     */
    public <T> T multiTryLock(List<String> lockeys, Executor<T> lockExecutor, Executor<T> unLockExecutor) {
        return multiTryLock(lockeys, LOCK_MAX_WAIT_SECTOND, LOCK_MAX_LEASE_SECTOND, lockExecutor, unLockExecutor);
    }

    /**
     * 获取等多个锁方法，所有锁获取成功，执行lockExecutor，否则执行unLockExecutor，最后释放获取到的锁。
     * 获取多个锁，需要预估业务处理时间，设置正确的leaseTime
     *
     * @param lockeys 锁key
     * @param waitTime 获取锁等待时间
     * @param leaseTime 锁存活时间
     * @param lockExecutor 锁获取成功执行方法
     * @param unLockExecutor 锁获取失败执行方法
     * @param <T> 反回对象类型
     * @return 返回执行结果，类型为T，如果业务方法报错，返回null
     */
    public <T> T multiTryLock(List<String> lockeys, long waitTime, long leaseTime, Executor<T> lockExecutor,
            Executor<T> unLockExecutor) {

        List<RLock> lockList = Lists.newLinkedList();

        boolean locked = true;

        for (String key : lockeys) {
            // 只要有一个失败，即为获取锁失败
            try {
                RLock lock = redissonClient.getLock(key);
                // 使用基于Redis的分布式锁获取锁
                lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
                lockList.add(lock);
            } catch (InterruptedException e) {
                e.printStackTrace();
                locked = false;
                break;
            }
        }

        // 定义返回值
        T t = null;

        try {
            log.info("multiTryLock获取锁结果：{}", locked);
            if (locked) {
                if (lockExecutor == null) {
                    log.error("lockey:{}, 未配置获取到锁执行方法！", lockeys);
                    throw new RuntimeException("lockey:" + lockeys + ", 未配置获取到锁执行方法！");
                }
                t = lockExecutor.exec();
            } else {
                if (unLockExecutor == null) {
                    log.error("lockey:{}, 未配置未获取到锁执行方法！", lockeys);
                    throw new RuntimeException("lockey:" + lockeys + ", 未配置未获取到锁执行方法！");
                }
                t = unLockExecutor.exec();
            }
        } catch (Exception e) {
            log.error("执行业务方法出错，errorMsg = {}, error stack = {}", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (!CollectionUtils.isEmpty(lockList)) {
                lockList.forEach(lock -> lock.unlock());
            }
            return t;
        }
    }

    public RedisTemplate getTemplate() {
        return redisTemplate;
    }

    public void setTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public ScmRedisTemplate() {
        super();
    }

    public ScmRedisTemplate(RedisTemplate redisTemplate, RedissonClient redissonClient) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
    }
}
