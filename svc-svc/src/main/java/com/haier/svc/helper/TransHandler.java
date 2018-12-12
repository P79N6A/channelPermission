//package com.haier.svc.helper;
//
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//
//public class TransHandler {
//    private DataSourceTransactionManager transactionManager;
//
//    public TransHandler(DataSourceTransactionManager transactionManager) {
//        this.transactionManager = transactionManager;
//    }
//
//    public DefaultTransactionDefinition getDefinition() {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        return def;
//    }
//
//    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
//        this.transactionManager = transactionManager;
//    }
//
//    public void handler(Handler handler) throws Exception {
//        handler.beforeProcess();
//        TransactionStatus status = null;
//        try {
//            status = transactionManager.getTransaction(getDefinition());
//            handler.process();
//            transactionManager.commit(status);
//        } catch (Exception e) {
//            transactionManager.rollback(status);
//            throw e;
//        }
//        handler.afterProcess();
//    }
//}
