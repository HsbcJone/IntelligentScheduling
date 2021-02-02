package com.etl.ds.core.enums;


/**
 * Author: huyisen@cvte.com
 * Date: 2020-03-06
 * Copyright © 2020 CVTE. All Rights Reserved.
 */
public enum TaskType {

    HQL(0, "HQL"),
    REFLUX(1, "回流");

    public Integer val;
    public String description;

    TaskType(Integer val, String description) {
        this.val = val;
        this.description = description;
    }
}
