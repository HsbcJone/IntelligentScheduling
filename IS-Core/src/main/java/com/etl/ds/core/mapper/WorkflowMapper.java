package com.etl.ds.core.mapper;

import com.etl.ds.core.entity.Workflow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Workflow record);

    int insertSelective(Workflow record);

    Workflow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Workflow record);

    int updateByPrimaryKey(Workflow record);

    List<Workflow> findByEnv(Integer env);

    Workflow getByCronId(Integer cronId);

    Workflow findByEnvAndMark(@Param("env") Integer env, @Param("mark") String mark);
}