package com.haier.shop.service;

import com.haier.shop.model.Members;

/**
 * Created by 李超 on 2018/1/9.
 */
public interface ShopMembersService {

    Members get(Integer id);

    String getMemberMobile(Integer id);

    int update(Members members);
}
