package com.haier.svc.job;

/**
 * Job执行结果。
 * @version 1.0
 * @author 刘志斌 yudi@sina.com
 */
public class JobResult {
    private int    result;
    private String message;

    public int getResult() {
        return this.result;
    }

    public void setResult(int value) {
        this.result = value;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
    }
}