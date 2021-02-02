package com.etl.ds.server.runner;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextProvider implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
         applicationContext=app;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
