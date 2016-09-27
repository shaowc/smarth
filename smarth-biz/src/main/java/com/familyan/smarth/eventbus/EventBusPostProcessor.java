package com.familyan.smarth.eventbus;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by xifeng on 2016/4/19.
 */
public class EventBusPostProcessor implements BeanPostProcessor {

    @Autowired
    EventBus eventBus;

    /**
     * 在Bean初始化完成之后，在注册到eventbus ， 防止循环引用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof EventListener){
            eventBus.register(bean);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
