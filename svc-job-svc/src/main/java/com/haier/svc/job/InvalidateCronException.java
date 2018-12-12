package com.haier.svc.job;

/**
 * 
 * @version 1.0
 * @author 刘志斌 yudi@sina.com
 */
public class InvalidateCronException extends RuntimeException {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5908430862321514808L;

    public InvalidateCronException(String message) {
        super(message);
    }
}