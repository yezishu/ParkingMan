package com.yzs.net.model;

import com.google.gson.annotations.Expose;

/**
 * Des：
 * creat by Yiming.Gan on 2016/4/1  11:42
 */
public class TestModel {
    @Expose
    private boolean success;
    @Expose
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
