package com.haier.shop.service;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/6 18:03
 */
public interface TBOrderSnExportService {

    /**
     * 导出功能根据sourceOrderSn获取tbOrderSn
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> getTBOrderSnBySourceOrderSn(Map<String,Object> paramMap);
}
