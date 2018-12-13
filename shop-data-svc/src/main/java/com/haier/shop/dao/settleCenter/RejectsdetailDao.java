package com.haier.shop.dao.settleCenter;

import com.haier.common.PagerInfo;
import com.haier.shop.model.Outstoragelist;
import com.haier.shop.model.Rejectsdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/14.
 */
@Mapper
public interface RejectsdetailDao {
    /**
     * 不良品查询
     * @param param
     * @param
     * @return
     */
    List<Rejectsdetail> paging(Rejectsdetail param);
    long count(@Param("param") Rejectsdetail param);
    int insert(Rejectsdetail param);
    int update(Rejectsdetail param);

    /**
     * 查询是否重复
     * @param rejectsdetail
     * @return
     */
    List<Rejectsdetail> findallbyattachmentBarCode(Rejectsdetail rejectsdetail);

    /**
     * 不良品导入
     * @param list
     */
    void bulkImport(List<Rejectsdetail> list);

    List<Rejectsdetail> findyear(Rejectsdetail param);

    List<Rejectsdetail> importbad(Rejectsdetail param);

    int responsibilityjd(Rejectsdetail param);
    int responsibilityhr(Rejectsdetail param);

    int Rejection(Rejectsdetail param);
    int Takepartjd(Rejectsdetail param);
    int afterwhere(Rejectsdetail param);

}
