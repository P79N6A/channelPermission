package com.haier.stock.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 事务处理代理类
 *                       
 * @Filename: TransactionProxy.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@EnableTransactionManagement
@SpringBootApplication
public class TransactionProxy {
	@Autowired
    private DataSourceTransactionManager transactionManager;
	@Autowired
    private TransactionDefinition transactionDefinition;

    public TransactionDefinition getTransactionDefinition() {
        return transactionDefinition;
    }

    public void doProxy(IProxy proxyInterface) throws Exception {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = transactionManager.getTransaction(this.getTransactionDefinition());
            proxyInterface.doBusiness();
            //提交事务
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            //回滚事务
            if (null != transactionStatus) {
            	transactionManager.rollback(transactionStatus);
            }
            throw e;
        }
    }
}
