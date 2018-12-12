package com.haier.stock.util;

import com.haier.common.ServiceResult;
import com.haier.eis.model.IExecute;
import com.haier.stock.services.WriteLogProxy;

/**
 * HTTPS加密专用调用工具类
 * 
 * @Filename: HttpSecServiceUtil.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class HttpSecServiceUtil {
    public static ServiceResult<String> executeService(String foreignKey, String intefaceCode,
                                                       final String content,
                                                       final String url) throws Exception {
        return executeService(foreignKey, intefaceCode, content, url,
            HttpSecInvokeUtil.METHOD_POST);
    }

    public static ServiceResult<String> executeService(String foreignKey, String intefaceCode,
                                                       final String content, final String url,
                                                       final String requestType) throws Exception {
        return executeService(foreignKey, intefaceCode, content, url, requestType,
            HttpSecInvokeUtil.CONTENT_TYPE_TEXT);
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
                    if (HttpSecInvokeUtil.METHOD_GET.equalsIgnoreCase(requestType)) {
                        resultStr = HttpSecInvokeUtil.invokeGet(content, url, contentType);
                    } else {
                        resultStr = HttpSecInvokeUtil.invokePost(content, url, contentType);
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
                                                                  final String requestType) throws Exception {
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
                        if (HttpSecInvokeUtil.METHOD_GET.equalsIgnoreCase(requestType)) {
                            resultStr = HttpSecInvokeUtil.invokeGet(content, url);
                        } else {
                            resultStr = HttpSecInvokeUtil.invokePost(content, url);
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
