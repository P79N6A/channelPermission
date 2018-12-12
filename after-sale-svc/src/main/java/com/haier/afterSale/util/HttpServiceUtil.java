package com.haier.afterSale.util;

import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.IExecute;

/**
 * HTTP调用工具类
 * 
 * @Filename: HttpServiceUtil.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class HttpServiceUtil {
    public static ServiceResult<String> executeService(String foreignKey, String intefaceCode,
                                                       final String content, final String url)
                                                                                              throws Exception {
        return executeService(foreignKey, intefaceCode, content, url, HttpInvokeUtil.METHOD_POST);
    }

    public static ServiceResult<String> executeService(String foreignKey, String intefaceCode,
                                                       final String content, final String url,
                                                       final String requestType) throws Exception {
        return executeService(foreignKey, intefaceCode, content, url, requestType,
            HttpInvokeUtil.CONTENT_TYPE_TEXT);
    }

    /**
     * 调用http post方式提交的服务
     * 
     * @param foreignKey
     *            为日志索引用的,比如订单id或网单id
     * @param intefaceCode
     *            接口编码
     * @param content
     *            http post提交的内容
     * @param url
     *            http服务地址
     * @return 结果字符串,未经解析的原始串,有可能是xml等
     * @throws Exception
     */
    public static ServiceResult<String> executeService(String foreignKey, String intefaceCode,
                                                       final String content, final String url,
                                                       final String requestType,
                                                       final String contentType) throws Exception {
        final ServiceResult<String> result = new ServiceResult<String>();
        String tempContent;
        if (content == null) {
            tempContent = url;
        } else {
            tempContent = content;
            if (tempContent.length() > 30000) {
                tempContent = tempContent.substring(0, 30000);
            }
        }
        WriteLogProxy.writeLog(foreignKey, intefaceCode, tempContent, new IExecute() {

            @Override
            public String execute() throws Exception {
                String resultStr = null;
                try {
                    if (HttpInvokeUtil.METHOD_GET.equalsIgnoreCase(requestType)) {
                        resultStr = HttpInvokeUtil.invokeGet(content, url, contentType);
                    } else {
                        resultStr = HttpInvokeUtil.invokePost(content, url, contentType);
                    }
                    result.setResult(resultStr);
                } catch (Exception e) {
                    result.setMessage(e.getMessage());
                    result.setSuccess(false);
                    result.setResult(null);
                    throw e;
                }
                return resultStr;
            }
        });
        return result;
    }

    public static ServiceResult<Boolean> executeService(String foreignKey, String intefaceCode,
                                                        final Map<String, Object> paramMap,
                                                        final String url) throws Exception {
        final ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        WriteLogProxy.writeLog(foreignKey, intefaceCode, paramMap.toString(), new IExecute() {

            @Override
            public String execute() throws Exception {
                String resultStr = null;
                try {
                    String json = HttpInvokeUtil.paramInvoke(paramMap, url);
                    Map<String, String> map = JsonUtil.fromJson(json);
                    if (map != null && "200".equals(map.get("code"))) {
                        result.setSuccess(true);
                    } else {
                        result.setSuccess(false);
                    }
                    result.setMessage(json);
                } catch (Exception e) {
                    result.setMessage(e.getMessage());
                    result.setSuccess(false);
                    result.setResult(null);
                    throw e;
                }
                return resultStr;
            }
        });
        return result;
    }

    public static ServiceResult<String> vomwwwExecuteService(String intefaceCode,
                                                             final String content,
                                                             final String url,
                                                             final String requestType)
                                                                                      throws Exception {
        final ServiceResult<String> result = new ServiceResult<String>();
        String tempContent;
        if (content == null) {
            tempContent = url;
        } else {
            tempContent = content;
        }
        WriteLogProxy.vomwwwWriteLog(intefaceCode, tempContent, new IExecute() {

            @Override
            public String execute() throws Exception {
                String resultStr = null;
                try {
                    if (HttpInvokeUtil.METHOD_GET.equalsIgnoreCase(requestType)) {
                        resultStr = HttpInvokeUtil.invokeGet(content, url);
                    } else {
                        resultStr = HttpInvokeUtil.invokePost(content, url);
                    }
                    result.setResult(resultStr);
                } catch (Exception e) {
                    result.setMessage(e.getMessage());
                    result.setSuccess(false);
                    result.setResult(null);
                    throw e;
                }
                return resultStr;
            }
        });
        return result;
    }

    public static ServiceResult<String> executeServiceAndReturnId(String foreignKey,
                                                                  String intefaceCode,
                                                                  final String content,
                                                                  final String url,
                                                                  final String requestType)
                                                                                           throws Exception {
        final ServiceResult<String> result = new ServiceResult<String>();
        String tempContent;
        if (content == null) {
            tempContent = url;
        } else {
            tempContent = content;
        }
        Integer backId = WriteLogProxy.writeLogAndReturnId(foreignKey, intefaceCode, tempContent,
            new IExecute() {

                @Override
                public String execute() throws Exception {
                    String resultStr = null;
                    try {
                        if (HttpInvokeUtil.METHOD_GET.equalsIgnoreCase(requestType)) {
                            resultStr = HttpInvokeUtil.invokeGet(content, url);
                        } else {
                            //                            String json = URLDecoder.decode(content, "UTF-8");
                            //                            String[] arry1 = json.split("&");
                            //                            String[] cOrderSnArr = null;
                            //                            for (String a1 : arry1) {
                            //                                if (a1.contains("apply_id")) {
                            //                                    cOrderSnArr = a1.replace("apply_id=", "").split(",");
                            //                                }
                            //                            }
                            //                            StringBuffer sb = new StringBuffer();
                            //                            sb.append(
                            //                                "{\"status\": \"ok\",\"code\": \"200\",\"msg\": \"操作成功\",\"data\": [");
                            //                            for (String str : cOrderSnArr) {
                            //                                sb.append("{\"apply_id\": \"").append(str)
                            //                                    .append(
                            //                                        "\",\"wo_id\": \"TMA090IU7Y0T00O8\",\"wo_status\": null,\"success_flag\": \"1\",\"error_type\": null,\"wo_desc\": null,\"source_code\": \"8\"}")
                            //                                    .append(",");
                            //                            }
                            //                            sb.deleteCharAt(sb.toString().length() - 1);
                            //                            sb.append(
                            //                                "],\"total_results\": 20,\"page_now\": 0,\"page_size\": 0,\"total_pages\": 0}");
                            //                            resultStr = sb.toString();
                            resultStr = HttpInvokeUtil.invokePost(content, url);
                        }
                        result.setResult(resultStr);
                    } catch (Exception e) {
                        result.setMessage(e.getMessage());
                        result.setSuccess(false);
                        result.setResult(null);
                        throw e;
                    }
                    return resultStr;
                }
            });
        result.setMessage(backId == null ? "" : backId.toString());
        return result;
    }
}
