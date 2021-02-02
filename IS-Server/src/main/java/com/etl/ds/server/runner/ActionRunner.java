package com.etl.ds.server.runner;

import com.etl.ds.server.action.OozieWorkflow;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ActionRunner implements CommandLineRunner {


    @Autowired
    private ContextProvider contextProvider;

    @Override
    public void run(String... args) throws Exception {
        //args=new String[]{"ETL_START","68","2021-01-22"};
        if (args == null || args.length == 0) {
            throw new RuntimeException("Oozie Action 参数不能为空");
        }
        String actionName = args[0];
        String[] parameters = new String[args.length - 1];
        System.arraycopy(args, 1, parameters, 0, parameters.length);
        AbstractApplicationContext context = (AbstractApplicationContext) contextProvider.getApplicationContext();
        try {
            Object bean = context.getBean(actionName);
            if (bean instanceof OozieWorkflow) {
                OozieWorkflow oozieAction = (OozieWorkflow) bean;
                oozieAction.run(parameters);
            } else {
                throw new RuntimeException("没有这个Oozie Action = " + actionName);
            }
        } catch (Exception e) {
            throw new RuntimeException(ExceptionUtils.getStackTrace(e));
        }
    }

}
