package com.haier.logistics.Util;

import java.io.Serializable;

public class HttpResult<T> implements Serializable {
    private static final long serialVersionUID = -8637111820477625638L;

    public HttpResult() {

    }

    public HttpResult(T data) {
        this.data = data;
    }

    public HttpResult(Boolean success, String errorMessage) {
        this.success = success;
        this.message = errorMessage;
    }

    private Boolean success;

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
