package com.haier.shop.service;

import com.haier.shop.model.HPFaileds;

public interface HPFailedsService {

    public HPFaileds getByOrderProductId(Integer orderProductId);

    public Integer insert(HPFaileds hpFailed);

    public Integer updateHpFailed(HPFaileds hpFailed);

}
