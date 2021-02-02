package com.etl.ds.core.utils;

import com.etl.ds.BaseTest;
import com.etl.ds.core.Dto.TaskParamDto;
import com.etl.ds.core.entity.Task;
import com.etl.ds.core.mapper.TaskMapper;
import com.etl.ds.core.service.TaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskUtilsTest  extends BaseTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;


    @Test
    public  void getAllTask(){
        List<Task> activeByWorkflowId = taskMapper.findActiveByWorkflowId(1);
        //activeByWorkflowId.stream().map(x->x.getId()).sorted().forEach(System.out::println);
        System.out.println(activeByWorkflowId);
    }

    @Test
    public  void testPattern() {
    }
}
