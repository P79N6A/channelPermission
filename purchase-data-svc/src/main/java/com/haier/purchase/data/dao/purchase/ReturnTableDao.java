package com.haier.purchase.data.dao.purchase;

import java.util.List;

import com.haier.purchase.data.model.CnDataDirectPush;
import com.haier.purchase.data.model.ReturnTable;

public interface ReturnTableDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table returnTable
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table returnTable
     *
     * @mbggenerated
     */
    int insert(ReturnTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table returnTable
     *
     * @mbggenerated
     */
    int insertSelective(ReturnTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table returnTable
     *
     * @mbggenerated
     */
    ReturnTable selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table returnTable
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ReturnTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table returnTable
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(ReturnTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table returnTable
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ReturnTable record);

	String findReturnMax();

	int insertRt(List<ReturnTable> list);

	List<ReturnTable> find3WInStock();

	int updateRtExpection(ReturnTable rt);

    int updateReturn(ReturnTable rt);

    List<ReturnTable> findReturnGoods(ReturnTable params);

    int findReturnGoodsCount(ReturnTable params);
}