package com.etl.ds.core.utils;

import com.alibaba.fastjson.JSON;
import com.etl.ds.core.Dto.TaskDto;
import com.etl.ds.core.Dto.TaskParamDto;
import com.etl.ds.core.enums.TaskType;
import parquet.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: huyisen@cvte.com
 * Date: 2020-03-06
 * Copyright © 2020 CVTE. All Rights Reserved.
 */
public class TaskUtils {

    /*public static HqlDto parseHqlDto(Task task) {
        return JSON.parseObject(task.getContent(), HqlDto.class);
    }

    public static HqlDto parseHqlDto(TaskDto task) {
        return JSON.parseObject(task.getContent(), HqlDto.class);
    }

    public static List<String> parseHqlSourceTables(TaskDto task) {
        List<String> tables = new ArrayList<>();
        HqlDto hql = JSON.parseObject(task.getContent(), HqlDto.class);
        String sources = hql.getSources();
        if (!Strings.isNullOrEmpty(sources)) {
            tables.addAll(Arrays.asList(sources.split(",")));
        }
        return tables;
    }

    public static String getHqlSinkTable(TaskDto task) {
        HqlDto hql = JSON.parseObject(task.getContent(), HqlDto.class);
        return hql.getSink();
    }

    public static RefluxDto parseRefluxDto(Task task) {
        return JSON.parseObject(task.getContent(), RefluxDto.class);
    }

    public static RefluxDto parseRefluxDto(TaskDto task) {
        return JSON.parseObject(task.getContent(), RefluxDto.class);
    }

    public static String parseRefluxSourceTable(TaskDto task) {
        RefluxDto reflux = JSON.parseObject(task.getContent(), RefluxDto.class);
        return reflux.getSourceDb() + "." + reflux.getSourceTb();
    }

    public static String parseRefluxSinkTable(TaskDto task) {
        RefluxDto reflux = JSON.parseObject(task.getContent(), RefluxDto.class);
        return reflux.getSinkDb() + "." + reflux.getSinkTb();
    }*/

    public static List<String> parseParam(TaskDto task) {
        List<String> params = new ArrayList<>();
        if (TaskType.HQL.val.equals(task.getType())) {
            Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
            Matcher matcher = pattern.matcher(task.getContent());
            while (matcher.find()) {
                String var = matcher.group(1).trim();
                if (!TaskParamDto.VAR_DATE.equals(var)) {
                    params.add(var);
                }
            }
        }
        params.add(TaskParamDto.BIZ_DATE);
        return params;
    }
}
