package com.haier.shop.service;

import com.haier.shop.model.HpSignTimeInterface;

/*
* 作者:张波
* 2017/12/26
*/
public interface HpSignTimeInterfaceService {
    HpSignTimeInterface selectByTbNoAndLbx(HpSignTimeInterface record);
    int addCountBySkuAndLbx(HpSignTimeInterface record);
    int insert(HpSignTimeInterface record);
}
