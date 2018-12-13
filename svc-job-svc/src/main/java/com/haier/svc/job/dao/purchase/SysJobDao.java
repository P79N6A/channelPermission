package com.haier.svc.job.dao.purchase;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.svc.job.model.JobLog;
import com.haier.svc.job.model.SysJob;
import com.haier.svc.job.model.SysJobEx;

public interface SysJobDao {
    /**
     * 查job配置。
     * @param name
     * @param status
     * @return Job列表
     */
    List<SysJob> find(@Param("name") String name, @Param("status") Integer status);
    
    SysJob findById(@Param("jobId") Integer jobId);

    /**
     * 查job配置记录数。
     * @param name
     * @param status
     * @return
     */
    Integer getCount(@Param("name") String name, @Param("status") Integer status);

    /**
     * 插入job配置
     * @param sysJob
     */
    void insert(SysJobEx sysJob);

    /**
     * 更新job配置
     * @param sysJob
     */
    void update(SysJobEx sysJob);

    /**
     * 根据job名称更新job配置
     * @param sysJob
     */
    Integer updateByName(SysJobEx sysJob);

	List<SysJobEx> getJobList(Map<String, Object> map);
	
	int getRowCnts(Map<String, Object> map);
    
	Integer addJob(SysJobEx sysJob);
    
    List<JobLog> findJobLog(Map<String,Object> map);
    
    int getJobLogRow(Map<String, Object> map);
}
