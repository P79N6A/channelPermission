package com.haier.stock.model;


public class InvStockLockEx extends InvStockLock {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String startLockTime;

    private String endLockTime;

    private String secName;

    private String channelCode;

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getStartLockTime() {
        return startLockTime;
    }

    public void setStartLockTime(String startLockTime) {
        this.startLockTime = startLockTime;
    }

    public String getEndLockTime() {
        return endLockTime;
    }

    public void setEndLockTime(String endLockTime) {
        this.endLockTime = endLockTime;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public boolean isWa() {
        return InvSection.CHANNEL_CODE_WA.equals(this.getChannelCode());
    }

}
