package com.etl.ds.core.mapper;

import com.etl.ds.core.entity.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    List<Task> findByWorkflowId(Integer workflowId);

    List<Task> findActiveByWorkflowIdList(@Param("workflowIdList") List<Integer> workflowIdList);

    List<Task> findActiveByWorkflowId(Integer workflowId);

    List<Task> findRefluxByWorkflowId(Integer workflowId);

    List<Task> findByIds(@Param("ids") List<Integer> ids);

    List<Task> findAllActive();

    List<Task> selectLastByName(@Param("names") List<String> names);

    List<Task> findActiveByEnv(Integer env);
}