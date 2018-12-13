package com.haier.shop.dao.workorder;

import com.haier.shop.model.Members;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
public interface ReviewMembersDao {

        Members getMemberMobile(@Param("storeId") String storeId);


}
