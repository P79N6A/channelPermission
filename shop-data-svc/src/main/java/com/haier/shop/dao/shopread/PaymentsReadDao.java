package com.haier.shop.dao.shopread;


import com.haier.shop.model.Payments;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentsReadDao {
    Payments get(Integer id);

    Payments getByCode(String paymentCode);
}
