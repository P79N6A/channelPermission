package com.haier.svc.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haier.common.util.StringUtil;
import com.haier.svc.job.dao.purchase.SysJobDao;
import com.haier.svc.job.dao.purchase.SysJobLogDao;
import com.haier.svc.job.model.SysJob;

/**
 * Job仓库。
 * @version 1.0
 * @author 刘志斌 yudi@sina.com
 */
@Component
public class JobRepository {
	@Autowired
    private SysJobDao    sysJobDao;
	@Autowired
    private SysJobLogDao sysJobLogDao;

    public List<SysJob> findSysJobDao(String name, Integer status) {
        Assert.notNull(this.sysJobDao, "Property 'sysJobDao' is required!");
        if (StringUtil.isEmpty(name))
            name = null;
        return this.sysJobDao.find(name, status);
    }

    public void setSysJobDao(SysJobDao value) {
        this.sysJobDao = value;
    }

    public void setSysJobLogDao(SysJobLogDao value) {
        this.sysJobLogDao = value;
    }

    public SysJobLogDao getSysJobLogDao() {
        return this.sysJobLogDao;
    }
}