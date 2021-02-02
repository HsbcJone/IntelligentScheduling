package com.etl.ds.core.exception;

/**
 * Author: huyisen@cvte.com
 * Date: 2019-12-21
 * Copyright © 2019 CVTE. All Rights Reserved.
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
