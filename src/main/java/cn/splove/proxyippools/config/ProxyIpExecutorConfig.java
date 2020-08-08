/*
 * 文件名：ExecutorConfig.java 版权：Copyright by www.sdhuijin.cn 描述： 修改人：sunp@sdhuijin.cn 修改时间：2019年7月23日
 * 修改内容：
 */

package cn.splove.proxyippools.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@EnableAsync
public class ProxyIpExecutorConfig {
    private static final Logger logger = LoggerFactory.getLogger(ProxyIpExecutorConfig.class);

    @Value("${proxy_ip_async.executor.thread.core_pool_size}")
    private int corePoolSize;

    @Value("${proxy_ip_async.executor.thread.max_pool_size}")
    private int maxPoolSize;

    @Value("${proxy_ip_async.executor.thread.queue_capacity}")
    private int queueCapacity;

    @Value("${proxy_ip_async.executor.thread.name.prefix}")
    private String namePrefix;

    @Bean(name = "proxyIpAsyncServiceExecutor")
    public Executor proxyIpAsyncServiceExecutor() {
        logger.info("start proxyIpAsyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 配置队列大小
        executor.setQueueCapacity(queueCapacity);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(namePrefix);

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor;
    }
}
