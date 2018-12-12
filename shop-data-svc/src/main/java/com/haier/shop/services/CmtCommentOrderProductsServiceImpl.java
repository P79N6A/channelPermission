package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.CmtCommentOrderProductsWriteDao;
import com.haier.shop.model.CmtCommentOrderProducts;
import com.haier.shop.service.CmtCommentOrderProductsService;

/**
 * Created by 张波 on 2017/12/26.
 */
@Service
public class CmtCommentOrderProductsServiceImpl implements CmtCommentOrderProductsService {
    @Autowired
    CmtCommentOrderProductsWriteDao cmtCommentOrderProductsWriteDao;

    @Override
    public Integer insert(CmtCommentOrderProducts cmtCommentOrderProducts) {
        return cmtCommentOrderProductsWriteDao.insert(cmtCommentOrderProducts);
    }
}
