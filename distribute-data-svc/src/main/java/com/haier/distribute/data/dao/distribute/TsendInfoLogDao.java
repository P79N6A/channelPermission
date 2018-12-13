package com.haier.distribute.data.dao.distribute;

import java.util.List;
import java.util.Map;

import com.haier.distribute.data.model.TsendInfoLog;
import com.haier.distribute.data.model.TsendInfoLogWithBLOBs;

public interface TsendInfoLogDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_send_info_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_send_info_log
     *
     * @mbggenerated
     */
    int insert(TsendInfoLogWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_send_info_log
     *
     * @mbggenerated
     */
    int insertSelective(TsendInfoLogWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_send_info_log
     *
     * @mbggenerated
     */
    TsendInfoLogWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_send_info_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TsendInfoLogWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_send_info_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TsendInfoLogWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_send_info_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TsendInfoLog record);

	List<TsendInfoLog> channelCodeSelect(Map<String, Object> params);
}