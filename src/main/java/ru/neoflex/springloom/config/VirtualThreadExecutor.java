package ru.neoflex.springloom.config;

import org.apache.catalina.Executor;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.util.LifecycleMBeanBase;
import org.apache.tomcat.util.threads.ResizableExecutor;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


public class VirtualThreadExecutor extends LifecycleMBeanBase
        implements Executor, ResizableExecutor {

    private ThreadFactory threadFactory = Thread.ofVirtual().factory();
    private ExecutorService executorService = Executors.newFixedThreadPool(10000, threadFactory);

    @Override
    public String getName() {
        return "virtual";
    }

    @Override
    public void execute(Runnable command, long timeout, TimeUnit unit) {
        executorService.execute(command);
    }


    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }

    @Override
    protected String getDomainInternal() {
        return null;
    }

    @Override
    protected String getObjectNameKeyProperties() {
        return "type=Executor,name=" + getName();
    }

    @Override
    protected void startInternal() throws LifecycleException {
         //NOOP
    }

    @Override
    protected void stopInternal() throws LifecycleException {
        executorService.shutdown();
    }

    @Override
    public int getPoolSize() {
        return 0;
    }

    @Override
    public int getMaxThreads() {
        return 0;
    }

    @Override
    public int getActiveCount() {
        return 0;
    }

    @Override
    public boolean resizePool(int corePoolSize, int maximumPoolSize) {
        return false;
    }

    @Override
    public boolean resizeQueue(int capacity) {
        return false;
    }
}