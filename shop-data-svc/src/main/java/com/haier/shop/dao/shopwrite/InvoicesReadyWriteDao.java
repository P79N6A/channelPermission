package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InvoicesReady;
import org.apache.ibatis.annotations.Mapper;

/*
* 作者:张波
* 2017/12/20
* */
@Mapper
public interface InvoicesReadyWriteDao {

    Integer insert(InvoicesReady invoicesReady);
}
