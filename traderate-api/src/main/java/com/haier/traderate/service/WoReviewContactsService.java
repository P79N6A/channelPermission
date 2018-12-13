package com.haier.traderate.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dto.ReviewContacts;
import com.haier.shop.dto.ReviewContactsDto;
import java.util.List;

/**
 * 通讯录表操作
 *                       
 * @Filename: ReviewContactsService.java
 * @Version: 1.0
 * @Author: tianshuai.zhang 张天帅
 * @Email: tianshuai.zhang@dhc.com.cn
 *
 */
public interface WoReviewContactsService {
    /**
     * 分页查询
     * @param pool
     * @param pager
     * @return
     */
    JSONObject page(ReviewContactsDto pool, PagerInfo pager);

    /**
     * 
     * @param reviewContactsDto
     * @return
     */
    List<ReviewContactsDto> findListLG(ReviewContactsDto reviewContactsDto);

    /**
     * 添加数据
     * @param reviewContacts
     * @return
     */
     Boolean addReviewContacts(ReviewContacts reviewContacts);

    /**
     * 修改
     * @param record
     * @return
     */
     Boolean updateReviewContacts(ReviewContacts record);

    /**
     * 删除
     * @param record
     * @return
     */
     Boolean deleteReviewContacts(List<ReviewContacts> record, String qf);

     Boolean findinsert(ReviewContactsDto record);

     Boolean updatedel(ReviewContacts record);

     Integer updatemanagerUserId(ReviewContactsDto record);

    /**
     * 通过ID修改责任人
     * @param reviewContacts
     * @return
     */
     Integer updateById(ReviewContactsDto reviewContacts);
     List<ReviewContactsDto> findListBySearch(ReviewContactsDto rcd);
}
