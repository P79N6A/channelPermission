package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.CorderStatusToLes;


public interface CorderStatusToLesService {

    int updateByPrimaryKey(CorderStatusToLes record);

    List<CorderStatusToLes> findNeedSendToLes(Integer limit);

    int insert(CorderStatusToLes corderStatusToLes);
}