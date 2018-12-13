package com.haier.svc.api.controller.util.http.suning;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SuningApiClient {

   private static final String APIURL = "http://open.suning.com/api/http/sopRequest"; 

   // 服务地址
   private String url;

   // 访问令牌
   private String appKey;

   // 访问密钥
   private String appSecret;

   // api方法
   private String appMethod;

   // 返回格式
   private String format = "json";

   // 版本
   private String versionNo = "v1.2";

   // 请求时间
   private String appRequestTime;

   // 应用级参数(业务参数)
   private String applicationParams; 
   
   public SuningApiClient(String url, String appKey, String appSecret, String appMethod, String applicationParams) {
       this.url = url;
       this.appKey = appKey;
       this.appSecret = appSecret;
       this.appMethod = appMethod; 
       this.appRequestTime = getNowTime();
       this.applicationParams = applicationParams;
   }
  
   public String postApiRequest() {
       String responseContent = "";
       try {
           HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
           ApiRequest request = new ApiRequest();
           request.doRequest(con);
           ApiResponse response = new ApiResponse();
           responseContent = response.resovlerResponse(con);
           con.disconnect();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return responseContent;
   }

   private static String getNowTime() {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date now = new Date();
       Date afterDate = new Date(now.getTime() + 300000);
       return sdf.format(afterDate);


   }

   class ApiRequest {
       public void preRequest(HttpURLConnection con) {
           validateParams();
           setApiSystemParams(con);
           setApiApplicationParams(con);
       }

       /**
        * 
        * 功能描述: <br>
        * 〈设置系统级参数〉
        * @param con
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
       public void setApiSystemParams(HttpURLConnection con) {
           try { 
               // 应用参数请求格式为json，指定
               con.setRequestProperty("Content-type", "application/json;charset=UTF-8");
               con.setRequestMethod("POST");
               con.setReadTimeout(30000);
               con.setConnectTimeout(30000);
               con.setDoInput(true);
               con.setDoOutput(true);
               con.setUseCaches(false);

               con.setRequestProperty("appKey", appKey);
               con.setRequestProperty("appMethod", appMethod);
               con.setRequestProperty("format", format);
               con.setRequestProperty("appRequestTime", appRequestTime);
               con.setRequestProperty("signInfo", doDataSign());
               con.setRequestProperty("versionNo", versionNo); 
           } catch (ProtocolException e) {
               e.printStackTrace();
           }
       }

       /**
        * 
        * 功能描述: <br>
        * 〈校验参数〉
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
       public void validateParams() {
           if (null == appKey || appKey == "") {
               throw new IllegalArgumentException("访问令牌不能为空");
           } else if (null == appMethod || appMethod == "") {
               throw new IllegalArgumentException("Api方法名不能为空");
           } else if (null == applicationParams || applicationParams == "") {
               throw new IllegalArgumentException("应用参数不能为空");
           } else if (!(format.equals("xml") || format.equals("json"))) {
               throw new IllegalArgumentException("返回格式错误");
           }
       }

       /**
        * 
        * 功能描述: <br>
        * 〈设置应用级参数,应用级参数编码为UTF-8〉
        * @param con
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
       public void setApiApplicationParams(URLConnection con) {
           try {
               OutputStream out = con.getOutputStream();
               // 写入应用级参数
               out.write(applicationParams.getBytes());
               out.flush();
               out.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

       public void doRequest(HttpURLConnection con) {
           preRequest(con);
       }

       /**
        * 
        * 功能描述: <br>
        * 〈数据签名〉
        * @return
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
       public String doDataSign() {
           ApiSecurity security = new ApiSecurity();
           String baseString = security.base64(applicationParams);
           String signInfo = security.md5(baseString);
           return signInfo;
       }
   }

   class ApiResponse {
       /**
        * 功能描述: <br>
        * 〈解析响应〉
        * @param con
        * @return
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
       public String resovlerResponse(HttpURLConnection con) {
           StringBuffer responseContent = new StringBuffer();
           try {
               int responseCode = con.getResponseCode();
               if (responseCode == HttpURLConnection.HTTP_OK) {
                   InputStream in = con.getInputStream();
                   BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                   String lineStr = "";
                   while ((lineStr = br.readLine()) != null) {
                       responseContent.append(lineStr);
                   }
                   br.close();
                   in.close();
               } else {
                   System.out.println("返回状态为：" + responseCode);
               }

           } catch (IOException e) {
               e.printStackTrace();
           }
           return responseContent.toString();
       }
   }

   class ApiSecurity {
       private final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
               "f" };

       public String base64(String content) {
           if (content == null) {
               throw new IllegalArgumentException("编码内容不能为空");
           }
           BASE64Encoder encoder = new BASE64Encoder();
           return encoder.encode(content.getBytes()).replaceAll("\r|\n", "");

       }

       private String byteArrayToHexString(byte[] byteArr) {
           StringBuffer stringBuffer = new StringBuffer();
           for (byte b : byteArr) {
               stringBuffer.append(byteToHexString(b));
           }
           return stringBuffer.toString();
       }

       private String byteToHexString(byte b) {
           int n = b;
           if (n < 0)
               n = 256 + n;
           return hexDigits[n / 16] + hexDigits[n % 16];
       }

       /**
        * 
        * 功能描述: <br>
        * 〈md5〉
        * 
        * @param content
        * @return
        * @see [相关类/方法](可选)
        * @since [产品/模块版本](可选)
        */
       public String md5(String content) {
           StringBuffer signStr = new StringBuffer();
           // 签名规则
           signStr.append(appSecret).append(appMethod).append(appRequestTime).append(appKey).append(versionNo)
                   .append(content);
           MessageDigest md;
           String encode = null;
           try {
               md = MessageDigest.getInstance("MD5");
               encode = byteArrayToHexString(md.digest(signStr.toString().getBytes()));
           } catch (NoSuchAlgorithmException e) {
               e.printStackTrace();
           }
           return encode;
       }

   }

   public static void main(String[] args) { 
       String appMethod = "suning.custom.order.query";
		 String appKey = "458e39b7a6c3d198ca1483a9405fb33c";
		 String appSecret = "7c805c40af42c49ac908b71052a608a0";
		 String applicationParams="{\"sn_request\":{\"sn_body\":{\"orderQuery\":{\"endTime\":\"2015-03-23 10:10:10\",\"pageNo\":\"1\",\"pageSize\":\"10\",\"startTime\":\"2015-03-20 10:10:10\"}}}}";

       String format = "json";
     //  String applicationParams = "<sn_request><sn_body><logisticCompany><pageNo>1</pageNo><pageSize>20</pageSize></logisticCompany></sn_body></sn_request>";
       SuningApiClient client = new SuningApiClient(appKey, appSecret, appMethod, format, applicationParams);
       String content = client.postApiRequest();
       System.out.println(content);
   }


}

