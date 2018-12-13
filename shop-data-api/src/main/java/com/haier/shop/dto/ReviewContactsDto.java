package com.haier.shop.dto;


/**
 * 向页面返回的数据
 *                       
 * @Filename: ReviewContactsDto.java
 * @Version: 1.0
 * @Author: tianshuai.zhang 张天帅
 * @Email: tianshuai.zhang@dhc.com.cn
 *
 */
public class ReviewContactsDto extends ReviewContacts {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3978989865090770119L;

    private String            username;                               //人员
    private String            phone;
    private String            email;

    private String            username1;                              //一级领导
    private String            phone1;
    private String            email1;

    private String            username2;                              //二级领导
    private String            phone2;
    private String            email2;

    private String            qtd;
    private Integer           rowNum;                                 //页面传来的当行下标

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "ReviewContactsDto [username=" + username + ", phone=" + phone + ", email=" + email
               + ", username1=" + username1 + ", phone1=" + phone1 + ", email1=" + email1
               + ", username2=" + username2 + ", phone2=" + phone2 + ", email2=" + email2
               + ", qtd=" + qtd + "]";
    }

}
