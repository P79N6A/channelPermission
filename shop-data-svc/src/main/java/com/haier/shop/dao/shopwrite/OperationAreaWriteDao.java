package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 网单
 * 吴坤洋 2017-10-25
 * @author wukunyang
 *
 */
@Mapper
public interface OperationAreaWriteDao {

	
	public void update_WwwHpRecordsCount(List<String> list);////更新WwwHpRecords表中的匹配次数
	public void updateFlagBatch(List<String> list);//批量更新hp拒收表的flag字段
	public void insertHPlogs(Map<String, Object> map);//hp推送信息插入到hp拒收日志表
	public void insertWwwHpRecords(Map<String, Object> map);

	public void update_WwwHpRecordsInfo(List<Map<String, Object>> list);//更新HP拒收信息
}
