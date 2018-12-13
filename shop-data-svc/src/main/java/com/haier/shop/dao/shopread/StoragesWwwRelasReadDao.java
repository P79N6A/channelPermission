package com.haier.shop.dao.shopread;


import com.haier.shop.model.StoragesWwwRelas;

public interface StoragesWwwRelasReadDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storageswwwrelas
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storageswwwrelas
     *
     * @mbggenerated
     */
    int insert(StoragesWwwRelas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storageswwwrelas
     *
     * @mbggenerated
     */
    int insertSelective(StoragesWwwRelas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storageswwwrelas
     *
     * @mbggenerated
     */
    StoragesWwwRelas selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storageswwwrelas
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StoragesWwwRelas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storageswwwrelas
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StoragesWwwRelas record);

    StoragesWwwRelas findForGbCode(String wwwCode);

    StoragesWwwRelas findByCainiaoStoreCode(String caiNiaoCode);
}