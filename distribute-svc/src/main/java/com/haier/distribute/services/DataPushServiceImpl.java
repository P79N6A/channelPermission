package com.haier.distribute.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.distribute.data.service.PopOrderService;
import com.haier.distribute.service.DataPushService;
import com.haier.distribute.service.PushProductInfoService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataPushServiceImpl implements DataPushService {
	private static org.slf4j.Logger log = LoggerFactory.getLogger(DataPushServiceImpl.class);
	@Autowired
	private PushProductInfoService pushProductInfoService;
	@Autowired
	private PopOrderService popOrderService;
	private String suffixUrl = "/api/productInfo/getProductInfo";
	private String url;

	// @Scheduled(cron = "0 05 10 * * ?")
	public JSONObject pushData() {
		JSONObject result=new JSONObject();
		List<Map<String, Object>> list = popOrderService.selectIdAndUrl();
		for (Map map : list) {
			Object prefixUrl = map.get("url");
			Integer id = Integer.parseInt(map.get("id").toString());
			if (prefixUrl == null) {
				log.info("此channelId:" + id + ",没有对应的url,不做任何处理!");
				continue;
			}
			url = prefixUrl.toString() + suffixUrl;
			JSONObject jsonObject = pushProductInfoService.pushData(url, id);
			result.put(id.toString(), jsonObject);
			String msg = jsonObject.get("msg").toString();
			log.info(msg);
		}
		return result;
	}
}
