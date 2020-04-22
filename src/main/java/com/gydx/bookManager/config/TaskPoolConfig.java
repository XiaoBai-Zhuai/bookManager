package com.gydx.bookManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskPoolConfig {

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数10：线程池创建时初始化的线程数
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //缓冲队列
        executor.setQueueCapacity(15);
        //允许线程的空闲时间，当超过了核心线程数之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        //线程池名的前缀，方便定位处理任务所在的线程池
        executor.setThreadNamePrefix("taskExecutor-");
        /*
        线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，
        当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；
        如果执行程序已关闭，则会丢弃该任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程池中任务的等待时间，如果超过这个时候还没被销毁就强制销毁，以确保应用最后能够呗关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(600);
        return executor;
    }

}
