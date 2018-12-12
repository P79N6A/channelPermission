package com.haier.eis.model;

/**
 * 需要写日志的代理接口
 *                       
 * @Filename: IExecute.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public interface IExecute {
    /**
     * 执行调用
     * @return 执行结果json字符串
     */
    String execute() throws Exception;
}
