package com.etl.ds.core.enums;


import com.etl.ds.core.exception.BizException;

import java.util.Objects;

/**
 * Author: huyisen@cvte.com
 * Date: 2020-03-06
 * Copyright © 2020 CVTE. All Rights Reserved.
 */
public enum ExecuteType {
    TIMING(0, "定时调度生成"),
    MANUAL(1, "手动启动"),
    RERUN(2, "重跑"),
    INIT(3, "初始化");

    public Integer val;
    public String description;

    ExecuteType(Integer val, String description) {
        this.val = val;
        this.description = description;
    }

    public static ExecuteType valOf(Integer val) {
        if (Objects.equals(TIMING.val, val)) {
            return TIMING;
        } else if (Objects.equals(MANUAL.val, val)) {
            return MANUAL;
        } else if (Objects.equals(RERUN.val, val)) {
            return RERUN;
        } else if (Objects.equals(INIT.val, val)) {
            return INIT;
        } else {
            throw new BizException("执行枚举类型不存在：" + val);
        }
    }
}
