package com.haier.distribute.service;



/**
 * Created by 付振兴 on 2018/3/23 1117.
 */
public interface DistributeCenterProductDataService {
    String ProductTiming(int channelId);

	String pushPrice(int channelId);

	String pushAvailable();
	void callHttpURL(String appKey,String suffixUrl, String param);
}
