package com.etl.ds.core.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowDto {
    private Integer id;

    private String name;

    private String mark;

    private Integer env;

    private Integer cronId;

    private String description;

    private Date createTime;

    private Date updateTime;

    private Integer version;

    private Integer isDelete;

    private String priority;

    private String readyTime;
}