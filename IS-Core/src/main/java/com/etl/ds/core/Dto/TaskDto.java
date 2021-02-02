package com.etl.ds.core.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Integer id;
    private String name;
    private Integer type;
    private String content;
    private Integer level;
    private Integer version;
    private Integer isDelete;
    private Integer workflowId;
    private Integer priority = 0;
}