package com.haier.shop.dto;

import java.io.Serializable;

/**
 * Created by liumeng on 2017/4/26.
 */
public class ThirdPartyLiabilityCondition implements Serializable
{
    private         Integer           id;                                      //id
    private         String            question1Level1;                       //责任位1
    private         String            question1Level2;                      //责任位2
    private         String            question1Level3;                     //责任位3
    private         String            question1Level4;                     //责任位4
    private         String            adduser;                             //添加人
    private         String            addtime;                             //添加时间
    private         String            ThirdPartyType;                     //第三方类型
    private         String            isEnable;                           //是否禁用
    private         String            serviceType;                        //服务类型
    private         String            canal;                               //渠道


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion1Level1() {
        return question1Level1;
    }

    public void setQuestion1Level1(String question1Level1) {
        this.question1Level1 = question1Level1;
    }

    public String getQuestion1Level2() {
        return question1Level2;
    }

    public void setQuestion1Level2(String question1Level2) {
        this.question1Level2 = question1Level2;
    }

    public String getQuestion1Level3() {
        return question1Level3;
    }

    public void setQuestion1Level3(String question1Level3) {
        this.question1Level3 = question1Level3;
    }

    public String getQuestion1Level4() {
        return question1Level4;
    }

    public void setQuestion1Level4(String question1Level4) {
        this.question1Level4 = question1Level4;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getThirdPartyType() {
        return ThirdPartyType;
    }

    public void setThirdPartyType(String thirdPartyType) {
        ThirdPartyType = thirdPartyType;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }
}
