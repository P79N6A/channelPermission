//package com.haier.svc.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//
///**
// * 事务处理代理类
// *                       
// * @Filename: TransactionProxy.java
// * @Version: 1.0
// * @Author: yaoyu
// * @Email: yaoyu@ehaier.com
// *
// */
//@Service("transactionProxy")
//public class TransactionProxy {
//	@Autowired
//    private DataSourceTransactionManager transactionManager;
//
//    public DataSourceTransactionManager getTransactionManager() {
//        return transactionManager;
//    }
//
//    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
//        this.transactionManager = transactionManager;
//    }
//
//    private TransactionDefinition transactionDefinition;
//
//    public TransactionDefinition getTransactionDefinition() {
//        return transactionDefinition;
//    }
//
//    public void doProxy(IProxy proxyInterface) throws Exception {
//        TransactionStatus transactionStatus = null;
//        try {
//            transactionStatus = this.getTransactionManager().getTransaction(this.getTransactionDefinition());
//            proxyInterface.doBusiness();
//            //提交事务
//            this.getTransactionManager().commit(transactionStatus);
//        } catch (Exception e) {
//            //回滚事务
//            if (null != transactionStatus) {
//                this.getTransactionManager().rollback(transactionStatus);
//            }
//            throw e;
//        }
//    }
//}
