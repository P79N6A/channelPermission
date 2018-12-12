package com.haier.svc.api.webService.WwwHpRecords.client;

/**
 * Created by zhangbo on 2017/11/20.
 */
public class TestClient {
    public static void main(String[] args) {
        CommonServiceImplService  cs=new CommonServiceImplService();
        CommonServiceImpl commonService=cs.getCommonServiceImplPort();
        String xmlString="<?xml version='1.0' encoding='UTF-8' standalone='yes'?><root><orderJs><item><rowid>1000000000112509</rowid><orderno>TB17111505009742</orderno><sourceno>null</sourceno><prodtype>CE0JKD017</prodtype><status>6</status><remark>null</remark><types>180</types><netcode>null</netcode><add10>null</add10><add1>null</add1><add2>LBX0327541298068126</add2><add3>null</add3></item></orderJs></root>";
        String result=commonService.xmlString(xmlString);
        System.out.println(result+"~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
