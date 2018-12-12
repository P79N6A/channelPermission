package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.HpSignTimeInterface;
import org.apache.ibatis.annotations.Mapper;

/*
* 作者:张波
* 2017/12/26
*/
@Mapper
public interface HpSignTimeInterfaceWriteDao {

    int addCountBySkuAndLbx(HpSignTimeInterface record);

    int insert(HpSignTimeInterface record);
}
