package com.shuzheng.holmes.common.utils;


import java.util.concurrent.*;

/**
 * 线程池创建工厂
 * <p>
 * Created by zhongjunkai on 2019/5/14.
 */
public class ThreadPoolFactory {

    private static final ThreadPoolFactory factory = new ThreadPoolFactory();

    //线程空闲时间，超过该时间的空闲线程将被回收
    private static final long DEFAULT_KEEP_ALIVE_TIME = 0L;

    private static ConcurrentHashMap<String, ThreadPoolExecutor> hashMap = new ConcurrentHashMap<>();

    public static ThreadPoolFactory getInstance() {
        return factory;
    }

    private ThreadPoolFactory() {

    }


    /**
     * 自动创建IO密集型线程池
     *
     * @param identifier
     * @return
     */
    public ThreadPoolExecutor autoCreate(String identifier,Integer capacity) {
        ThreadPoolExecutor threadPoolExecutor = hashMap.get(identifier);
        if (threadPoolExecutor == null) {
            int size = Runtime.getRuntime().availableProcessors();
            return this.create(identifier, size, size * 2 + 1, 0
                    , new ArrayBlockingQueue<Runnable>(capacity));
        }
        return threadPoolExecutor;
    }

    /**
     * 创建线程池
     *
     * @param identifier
     * @param corePoolSize
     * @param maxPoolSize
     * @return
     */
    public ThreadPoolExecutor create(String identifier, int corePoolSize, int maxPoolSize) {
        return this.create(identifier, corePoolSize, maxPoolSize, DEFAULT_KEEP_ALIVE_TIME);
    }

    /**
     * 创建线程池
     *
     * @param identifier
     * @param corePoolSize
     * @param maxPoolSize
     * @param keepAliveTime
     * @return
     */
    public ThreadPoolExecutor create(String identifier, int corePoolSize, int maxPoolSize, long keepAliveTime) {
        return this.create(identifier, corePoolSize, maxPoolSize, keepAliveTime, new LinkedBlockingQueue());
    }

    /**
     * 创建线程池
     *
     * @param identifier
     * @param corePoolSize
     * @param maxPoolSize
     * @param keepAliveTime
     * @param queue
     * @return
     */
    public ThreadPoolExecutor create(String identifier, int corePoolSize, int maxPoolSize, long keepAliveTime, BlockingQueue queue) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, queue, new DefaultRejectedExecutionHandler(identifier));
        hashMap.putIfAbsent(identifier, executor);
        return executor;
    }

    /**
     * 获取线程池
     *
     * @param identifier
     * @return
     */
    public ThreadPoolExecutor getExecutor(String identifier) {
        return hashMap.get(identifier);
    }

    /**
     * 删除线程池
     *
     * @param identifier
     */
    public void removeExecutor(String identifier) {
        hashMap.remove(identifier);
    }

    /**
     * 拒绝机制
     */
    private class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {

        private String identifier;

        public DefaultRejectedExecutionHandler(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new RejectedExecutionException("线程池" + this.identifier + "目前正忙，无法提供服务");
        }
    }

}
