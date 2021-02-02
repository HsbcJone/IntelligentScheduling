package com.etl.ds.core.service;

import com.etl.ds.core.Dto.TaskParamDto;
import com.etl.ds.core.engine.CoreEngine;
import com.etl.ds.core.utils.TaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用来提交Job
 */
@Slf4j
@Service
public class EtlService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private CoreEngine coreEngine;



    public void submit(final int workflowId, TaskParamDto param) {
        //对于没有传入的参数，都置为昨天
        List<String> params = findAllParamByWorkflowId(workflowId);
        params.forEach(it -> {
            if (!param.getVars().containsKey(it)) {
                param.getVars().put(it, LocalDate.now().minusDays(1).toString());
            }
        });

        //按照level对task进行排序->
        //1.0 去数据库检查状态是否就绪
        //2.0 执行
           //2.1 将文件写入hdfs->(hql->xml) 已经存在了 /user/jwth/etl_ds/etl/
           //2.2 oozie-client执行
        List<Integer> xmlNames = taskService.findActiveByWorkflowId(workflowId).stream().filter(x->x.getType()==0).map(x -> x.getId())
                .sorted().collect(Collectors.toList());
        xmlNames.forEach(name->{
            coreEngine.runJob(workflowId+"",name.toString());
        });
        //如果是回流肯定直接
        //3.0 更新task实列的状态  暂时不处理
        //4.0 更新工作流从状态     暂时不处理
    }

    @PreDestroy
    public void preDestroy() {
      /*  workflowExecutors.shutdown();
        while (true) {
            try {
                if (workflowExecutors.awaitTermination(1, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                log.error(ExceptionUtils.getMessage(e));
            }
        }
        coreV1.preDestroy();
        coreV2.preDestroy();*/
    }


    public List<String> findAllParamByWorkflowId(int id) {
        return taskService.findActiveByWorkflowId(id)
                .stream()
                .map(TaskUtils::parseParam)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
