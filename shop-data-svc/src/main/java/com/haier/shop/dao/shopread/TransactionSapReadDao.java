package com.haier.shop.dao.shopread;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TransactionSap;
@Mapper
public interface TransactionSapReadDao {

    TransactionSap selectByPrimaryKey(Long id);
    
    List<TransactionSap> getTransactionSapList(QueryZFBOrderParameter queryOrder);

	Integer getTransactionSapCount(QueryZFBOrderParameter queryOrder);

	List<TransactionSap> getSummarySapList(QueryZFBOrderParameter queryOrder);

	Integer getSummarySapCount(QueryZFBOrderParameter queryOrder);
}