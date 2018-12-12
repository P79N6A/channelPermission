package com.haier.shop.dao.shopread;

import com.haier.shop.model.CustomerCodes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-05
 **/
@Mapper
public interface CustomerCodesReadDao {

    List<String> getCustomerCode(@Param("title") String title);

    CustomerCodes getByTitle(@Param("title") String title);
}
