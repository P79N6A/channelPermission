package com.haier.order.util;

public interface IFetchCallback {
	/**
	 * 抓取线程完成后调用此函数通知调用线程任务已完成
	 * 
	 * @param id
	 */
	public void threadFinish(int id);

	/**
	 * 数据抓取完毕后调用此函数通知线程准备结束
	 */
	public void dataFetchFinish();
}
