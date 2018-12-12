package com.haier.shop.service;

import com.haier.shop.model.LesShippingInfos;

import java.util.List;

public interface LesShippingInfosService {
    public List<LesShippingInfos> getByCorderSn(String corderSn);

    public Integer insert(LesShippingInfos lesShippingInfos);
}
