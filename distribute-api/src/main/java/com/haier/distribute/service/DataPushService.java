package com.haier.distribute.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

public interface DataPushService {
        public JSONObject pushData();
}
