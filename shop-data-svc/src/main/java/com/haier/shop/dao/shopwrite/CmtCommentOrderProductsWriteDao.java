package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.CmtCommentOrderProducts;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 张波 on 2017/12/26.
 */
@Mapper
public interface CmtCommentOrderProductsWriteDao {
    Integer insert(CmtCommentOrderProducts cmtCommentOrderProducts);
}
