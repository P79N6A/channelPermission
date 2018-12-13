package com.haier.shop.dao.shopread;

import com.haier.shop.model.Storages;
import java.util.List;
import java.util.Map;

import com.haier.shop.model.StoragesVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface StoragesReadDao {
    /**
     * 根据编码获取库位信息
     * @param code 库位编码
     * @return
     */
    Storages getByCode(String code);

    /**
     * 根据id获取库位信息
     * @param id 库位id
     * @return
     */
    Storages get(Integer id);
    /**
     * 查询库位编码
     * @param netPointN8
     * @return
     */
    String queryCenterCode(String netPointN8);
    /**
     * 根据c码查询出库位
     * @param centerCode
     * @return
     */
    String queryCode(String centerCode);

    String getBywwwCode(String wwwCode);

    /**
     * 根据类型获取库位信息
     * @param type 库位类型
     * @return
     */
    List<Map<String, String>> getInfoByType(Integer type);

    /**
<<<<<<< HEAD
     * 根据编码批量获取名称
     * @param code
     * @return
     */
    List<Map<String, String>> getInfoByCode(@Param("codeList")List<String> code);

     /** 查询总条数
     * @return
     */
    int getRowCnts();

    /**
     * 查询库位列表
     * @param params
     * @return
     */
    List<StoragesVo> queryStoragesList(Map<String,Object> params);

    /**
     * 导出库位列表查询
     * @param params
     * @return
     */
    List<Map<String,Object>> getExportStoragesListByParams(Map<String,Object> params);

}
