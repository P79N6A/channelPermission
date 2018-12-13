package com.haier.distribute.services;

import com.haier.distribute.data.service.SaleProductStockDataService;
import com.haier.distribute.service.SaleProductStockService;
import com.haier.distribute.util.Request;
import com.thoughtworks.xstream.XStream;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class SaleProductStockServiceImpl implements SaleProductStockService{
    private Logger logger = LoggerFactory.getLogger(SaleProductStockServiceImpl.class);
    @Autowired
    private SaleProductStockDataService saleProductStockDataService;
    //定时查询可售商品表里面的数据推送到distribute系统
    //@Scheduled(cron = "*/10 * * * * ?")
    public void saleProductStock(){
        List<Map<String,Object>> maps=saleProductStockDataService.selectBystatus();
        if (maps.size()==0){
            logger.info("此次没有推送的数据,等待下一次任务执行");
            return;
        }
        List<Request> requestList=new ArrayList<>();
        XStream xstream = new XStream();
        xstream.alias("request", Request.class);
        for (Map map:maps){
            Request request=new Request();
            request.setLockQuantity(map.get("lockQuantity")==null?0:Integer.parseInt(map.get("lockQuantity").toString()));
            request.setQuantity(map.get("quantity")==null?0:Integer.parseInt(map.get("quantity").toString()));
            request.setProductCode(map.get("productCode")==null?"":map.get("productCode").toString());
            request.setWarehouseCode(map.get("warehouseCode")==null?"":map.get("warehouseCode").toString());
            requestList.add(request);
        }
        //将查询出来的数据转成xml报文
        String xml = xstream.toXML(requestList);
        String url="http://127.0.0.1:8082/api/distribute/receiveData";
        HttpPost httpPost = new HttpPost(url);
        HttpEntity rqentity = new StringEntity(xml, "utf-8");
        httpPost.setEntity(rqentity);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setSocketTimeout(60000).build();
        httpPost.setConfig(requestConfig);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity rpEntity = response.getEntity();
            String responseResult=EntityUtils.toString(rpEntity, "UTF-8");
            if (responseResult.equals("S")){
                logger.info("推送数据成功");
            }
            if (responseResult.equals("F")){
                logger.info("推送数据失败");
            }
            System.out.println(responseResult);
        }catch (Exception e){
            logger.error("调用远程服务出现错误");
            return;
        }

    }
}
