package com.haier.shop.dto;


import com.haier.shop.model.WOUser;

/**
 * 向页面返回的数据
 *                       
 * @Filename: ReviewUserDto.java
 * @Version: 1.0
 * @Author: tianshuai.zhang 张天帅
 * @Email: tianshuai.zhang@dhc.com.cn
 *
 */
public class ReviewUserDto extends WOUser {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8287914180738301453L;
    private String            id1;
    private String            username1;                               //一级领导
    private String            phone1;
    private String            email1;

    private String            id2;
    private String            username2;                               //二级领导
    private String            phone2;
    private String            email2;

    @Override
    public String toString() {
        return "ReviewUserDto [id1=" + id1 + ", username1=" + username1 + ", phone1=" + phone1
               + ", email1=" + email1 + ", id2=" + id2 + ", username2=" + username2 + ", phone2="
               + phone2 + ", email2=" + email2 + "]";
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

}
