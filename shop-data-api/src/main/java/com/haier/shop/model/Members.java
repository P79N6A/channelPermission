package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Table: <strong>members</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>avatarImageFileId</td><td>{@link String}</td><td>avatarImageFileId</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>email</td><td>{@link String}</td><td>email</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>username</td><td>{@link String}</td><td>username</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>password</td><td>{@link String}</td><td>password</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>salt</td><td>{@link String}</td><td>salt</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>registerTime</td><td>{@link Integer}</td><td>registerTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lastLoginTime</td><td>{@link Integer}</td><td>lastLoginTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lastLoginIp</td><td>{@link String}</td><td>lastLoginIp</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>memberRankId</td><td>{@link Integer}</td><td>memberRankId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>bigCustomerId</td><td>{@link Integer}</td><td>bigCustomerId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lastAddressId</td><td>{@link Integer}</td><td>lastAddressId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lastPaymentCode</td><td>{@link String}</td><td>lastPaymentCode</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>gender</td><td>{@link Integer}</td><td>gender</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>birthday</td><td>{@link Date}</td><td>birthday</td><td>date</td><td>&nbsp;</td></tr>
 *   <tr><td>qq</td><td>{@link String}</td><td>qq</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>msn</td><td>{@link String}</td><td>msn</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>mobile</td><td>{@link String}</td><td>mobile</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>canReceiveSms</td><td>{@link Integer}</td><td>canReceiveSms</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>phone</td><td>{@link String}</td><td>phone</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>validateCode</td><td>{@link String}</td><td>validateCode</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>pwdErrCount</td><td>{@link Integer}</td><td>pwdErrCount</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>source</td><td>{@link String}</td><td>source</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>sign</td><td>{@link String}</td><td>sign</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>money</td><td>{@link BigDecimal}</td><td>money</td><td>decimal</td><td>&nbsp;</td></tr>
 *   <tr><td>moneyPwd</td><td>{@link String}</td><td>moneyPwd</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>isEmailVerify</td><td>{@link Integer}</td><td>isEmailVerify</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>isSmsVerify</td><td>{@link Integer}</td><td>isSmsVerify</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>smsVerifyCode</td><td>{@link String}</td><td>smsVerifyCode</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>emailVerifyCode</td><td>{@link String}</td><td>emailVerifyCode</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>verifySendCoupon</td><td>{@link Integer}</td><td>verifySendCoupon</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>canReceiveEmail</td><td>{@link Integer}</td><td>canReceiveEmail</td><td>tinyint</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-5-9
 * @email yudi@sina.com
 */
public class Members implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3574641930340284755L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private String avatarImageFileId;

    public String getAvatarImageFileId() {
        return this.avatarImageFileId;
    }

    public void setAvatarImageFileId(String value) {
        this.avatarImageFileId = value;
    }

    private String email;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    private String salt;

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String value) {
        this.salt = value;
    }

    private Integer registerTime;

    public Integer getRegisterTime() {
        return this.registerTime;
    }

    public void setRegisterTime(Integer value) {
        this.registerTime = value;
    }

    private Integer lastLoginTime;

    public Integer getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Integer value) {
        this.lastLoginTime = value;
    }

    private String lastLoginIp;

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }

    public void setLastLoginIp(String value) {
        this.lastLoginIp = value;
    }

    private Integer memberRankId;

    public Integer getMemberRankId() {
        return this.memberRankId;
    }

    public void setMemberRankId(Integer value) {
        this.memberRankId = value;
    }

    private Integer bigCustomerId;

    public Integer getBigCustomerId() {
        return this.bigCustomerId;
    }

    public void setBigCustomerId(Integer value) {
        this.bigCustomerId = value;
    }

    private Integer lastAddressId;

    public Integer getLastAddressId() {
        return this.lastAddressId;
    }

    public void setLastAddressId(Integer value) {
        this.lastAddressId = value;
    }

    private String lastPaymentCode;

    public String getLastPaymentCode() {
        return this.lastPaymentCode;
    }

    public void setLastPaymentCode(String value) {
        this.lastPaymentCode = value;
    }

    private Integer gender;

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer value) {
        this.gender = value;
    }

    private Date birthday;

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date value) {
        this.birthday = value;
    }

    private String qq;

    public String getQq() {
        return this.qq;
    }

    public void setQq(String value) {
        this.qq = value;
    }

    private String msn;

    public String getMsn() {
        return this.msn;
    }

    public void setMsn(String value) {
        this.msn = value;
    }

    private String mobile;

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }

    private Integer canReceiveSms;

    public Integer getCanReceiveSms() {
        return this.canReceiveSms;
    }

    public void setCanReceiveSms(Integer value) {
        this.canReceiveSms = value;
    }

    private String phone;

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String value) {
        this.phone = value;
    }

    private String validateCode;

    public String getValidateCode() {
        return this.validateCode;
    }

    public void setValidateCode(String value) {
        this.validateCode = value;
    }

    private Integer pwdErrCount;

    public Integer getPwdErrCount() {
        return this.pwdErrCount;
    }

    public void setPwdErrCount(Integer value) {
        this.pwdErrCount = value;
    }

    private String source;

    public String getSource() {
        return this.source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    private String sign;

    public String getSign() {
        return this.sign;
    }

    public void setSign(String value) {
        this.sign = value;
    }

    private BigDecimal money;

    public BigDecimal getMoney() {
        return this.money;
    }

    public void setMoney(BigDecimal value) {
        this.money = value;
    }

    private String moneyPwd;

    public String getMoneyPwd() {
        return this.moneyPwd;
    }

    public void setMoneyPwd(String value) {
        this.moneyPwd = value;
    }

    private Integer isEmailVerify;

    public Integer getIsEmailVerify() {
        return this.isEmailVerify;
    }

    public void setIsEmailVerify(Integer value) {
        this.isEmailVerify = value;
    }

    private Integer isSmsVerify;

    public Integer getIsSmsVerify() {
        return this.isSmsVerify;
    }

    public void setIsSmsVerify(Integer value) {
        this.isSmsVerify = value;
    }

    private String smsVerifyCode;

    public String getSmsVerifyCode() {
        return this.smsVerifyCode;
    }

    public void setSmsVerifyCode(String value) {
        this.smsVerifyCode = value;
    }

    private String emailVerifyCode;

    public String getEmailVerifyCode() {
        return this.emailVerifyCode;
    }

    public void setEmailVerifyCode(String value) {
        this.emailVerifyCode = value;
    }

    private Integer verifySendCoupon;

    public Integer getVerifySendCoupon() {
        return this.verifySendCoupon;
    }

    public void setVerifySendCoupon(Integer value) {
        this.verifySendCoupon = value;
    }

    private Integer canReceiveEmail;

    public Integer getCanReceiveEmail() {
        return this.canReceiveEmail;
    }

    public void setCanReceiveEmail(Integer value) {
        this.canReceiveEmail = value;
    }

    private String nickName;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}