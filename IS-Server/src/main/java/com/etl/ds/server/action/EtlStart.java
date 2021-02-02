package com.etl.ds.server.action;

import com.etl.ds.core.Dto.TaskParamDto;
import com.etl.ds.core.Dto.WorkflowDto;
import com.etl.ds.core.enums.ExecuteType;
import com.etl.ds.core.enums.RunningType;
import com.etl.ds.core.exception.OperateException;
import com.etl.ds.core.service.EtlService;
import com.etl.ds.core.service.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component("ETL_START")
public class EtlStart implements OozieWorkflow {

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private  EtlService etlService;

   /*
    @Autowired
    public EtlStart(EtlService etlService) {
        this.etlService = etlService;
    }*/

    @Override
    public void run(String... args) {
        if (args.length != 2) {
            String msg = "Usage: START_WORKFLOW ${cronId} ${date}";
            throw new OperateException(msg);
        }
        Integer cronId = 0;
        try {
            log.info("args = {}", Arrays.asList(args));
            cronId = Integer.valueOf(args[0]);
            String date = args[1];

            TaskParamDto paramDto = new TaskParamDto(ExecuteType.TIMING.val, date, RunningType.INCR);
            log.info("cronId={}, date={}, bizDate={}, paramDto={}", cronId, date, date, paramDto);

            // 工作流可以绑定，自己特有的参数？？，
            WorkflowDto workflowDto = workflowService.getByCronId(cronId);
            System.out.println("heihhhhhhh:"+workflowDto);
            List<String> params = etlService.findAllParamByWorkflowId(workflowDto.getId());

            System.out.println(params);
            System.out.println("hello");
            etlService.submit(workflowDto.getId(), paramDto);
            //目前定时调度不支持其他参数，所以先置为空
         /*   params.forEach(it -> {
                if (!TaskParamDto.isInherentParam(it)) {
                    paramDto.getVars().put(it, date);
                }
            });

            etlService.submit(workflowDto.getId(), paramDto);

            etlService.preDestroy();*/
        } catch (Exception e) {
    /*        CronDto cron = cronService.getById(cronId);
            alertService.alert2Admin(AlertLevel.ERROR, "定时器启动工作流错误, 名字=" + cron.getName()
                    + "，错误信息：" + ExceptionUtils.getMessage(e));*/
            throw e;
        }
    }
}
