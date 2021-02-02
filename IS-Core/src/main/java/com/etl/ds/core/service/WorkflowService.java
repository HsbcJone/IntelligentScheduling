package com.etl.ds.core.service;

import com.etl.ds.core.Dto.WorkflowDto;
import com.etl.ds.core.entity.Workflow;
import com.etl.ds.core.mapper.WorkflowMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 工作流workflow的service
 */
@Service
public class WorkflowService {

    private final WorkflowMapper workflowMapper;


    @Autowired
    public WorkflowService(WorkflowMapper workflowMapper) {
        this.workflowMapper = workflowMapper;
    }



    public WorkflowDto getByCronId(Integer cronId) {
        Workflow wf = workflowMapper.getByCronId(cronId);
        WorkflowDto dto = new WorkflowDto();
        BeanUtils.copyProperties(wf, dto);
        return dto;
    }
}
