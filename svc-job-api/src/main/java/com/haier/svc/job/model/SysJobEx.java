package com.haier.svc.job.model;

public class SysJobEx extends SysJob{
    private static final long serialVersionUID = 8177742371347539394L;

    private String cfgDataStr;

    public String getCfgDataStr() {
        return cfgDataStr;
    }

    public void setCfgDataStr(String cfgDataStr) {
        this.cfgDataStr = cfgDataStr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SysJobEx) {
            SysJobEx old = (SysJobEx) obj;
            if (!this.getJobName().equals(old.getJobName())) {
                return false;
            }
            if (!this.getJobType().equals(old.getJobType())) {
                return false;
            }
            if (!this.getJobStatus().equals(old.getJobStatus())) {
                return false;
            }
            if (!this.getCfgDataStr().equals(old.getCfgDataStr())) {
                return false;
            }
            if (!this.getCron().equals(old.getCron())) {
                return false;
            }
            return true;
        }
        return false;
    }
    
}