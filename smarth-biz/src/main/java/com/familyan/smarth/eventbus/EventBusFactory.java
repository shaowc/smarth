package com.familyan.smarth.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by xifeng on 2016/4/19.
 */
public class EventBusFactory implements FactoryBean<EventBus> {

    boolean async = false;

    ThreadPoolTaskExecutor executor;

    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Class<?> getObjectType() {
        return EventBus.class;
    }

    @Override
    public EventBus getObject() throws Exception {
        if(async){
            return new AsyncEventBus(executor);
        }
        return new EventBus();
    }
}
