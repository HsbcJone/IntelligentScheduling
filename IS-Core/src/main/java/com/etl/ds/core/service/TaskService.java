package com.etl.ds.core.service;

import com.etl.ds.core.Dto.TaskDto;
import com.etl.ds.core.entity.Task;
import com.etl.ds.core.mapper.TaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 1.关于数据库task级别的service
 */
@Slf4j
@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskDto> findActiveByWorkflowId(int workflowId) {
        List<Task> taskList = taskMapper.findActiveByWorkflowId(workflowId);
        return taskList.stream().map(task -> {
            TaskDto dto = new TaskDto();
            BeanUtils.copyProperties(task, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}
