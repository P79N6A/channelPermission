package com.haier.purchase.data.dao.purchase;


import com.haier.purchase.data.model.WwwStockSaveRequire;
import java.util.List;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年03月29日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface WwwStockDao {
    /**
     * @return 记录日志
     */
    public int saveLog(String message, String createdDate);

    /**
     * @param
     * @return zzb
     * 保存
     */
    public int save(List<WwwStockSaveRequire> list);

    /**
     * @return zzb
     * 删除重新insert
     */
    public int delete();

    public int update(List<WwwStockSaveRequire> list);

    public List<WwwStockSaveRequire> select(String productTypeName, String enChannleId);
}
