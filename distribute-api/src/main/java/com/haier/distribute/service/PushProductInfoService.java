package com.haier.distribute.service;

import com.alibaba.fastjson.JSONObject;

public interface PushProductInfoService {
    public JSONObject pushData(String url, Integer channelId);
}
