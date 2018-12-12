package com.haier.svc.api.controller.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.novell.ldap.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class LdapUserAuth{

	@Value(value="${idm.ldapHost}")
	String ldapHost;
	@Value(value="${idm.loginDN}")
    String loginDN;
	@Value(value="${idm.loginPassword}")
    String loginPassword;

    /**
     * 获得ldap链接
     * @return
     */
    private static synchronized LDAPConnection getLDAPConnection(String ldapHost, String loginDN, String password){
    	LDAPConnection lc = null;
    	try {
	    	int ldapVersion = LDAPConnection.LDAP_V3;
	        int ldapPort = 389;
	        lc = new LDAPConnection();
			lc.connect( ldapHost, ldapPort );
			lc.bind( ldapVersion, loginDN, password );
			return lc;
		} catch (LDAPException e) {
			e.printStackTrace();
			if(lc != null){
				try {
					lc.disconnect();
					lc = null;
				} catch (LDAPException e1) {
					e1.printStackTrace();
				}
			}
			return null;
		}
    }
    /**
     * 释放ldap链接
     * @param lc
     */
    private static void releaseLDAPConnection(LDAPConnection lc){
    	try {
    		if(lc != null){
    			lc.disconnect();
    			lc = null;
    		}
		} catch (LDAPException e) {
			e.printStackTrace();
		}
    }
    /**
     * 获得dn
     * @param lc
     * @param cn : 工号cn
     * @return
     */
    private static synchronized String getDN(LDAPConnection lc,String cn){
    	 try {
    		 if((cn == null || "".equals(cn))){
	   			 return null;
	   		 }
    		 //haier 下面只查cn
             String searchBase = "o=haier";
             String searchFilter = "(&(cn="+cn+")(objectClass=user))";
             int searchScope = LDAPConnection.SCOPE_SUB;
             LDAPSearchResults searchResults = lc.search(searchBase,searchScope, searchFilter.toString(), null, false);

             while (searchResults.hasMore()) {
                 LDAPEntry nextEntry = null;
                 nextEntry = searchResults.next();
                 String str = nextEntry.getDN();
                 return str;
             }
             return null;
         } catch (LDAPException t) {
         	t.printStackTrace();
         	return null;
         }
    }
    /**
     * 校验用户是否合法
     */
    public IDMLoginResult checkInfo(String ldapHost,String loginDN,String loginPassword,String dn,String pwd){
    	IDMLoginResult r = new IDMLoginResult();
    	int ldapVersion   = LDAPConnection.LDAP_V3;
        int ldapPort      = 389;
        LDAPConnection lc = new LDAPConnection();
        try {
            lc.connect( ldapHost, ldapPort );
            lc.bind( ldapVersion, loginDN, loginPassword.getBytes("UTF8") );
            Map<String,String> map  = getOperationalAttrs( lc, dn );
            //开始判断
			String logindisabled = map.get("logindisabled");
			if(logindisabled.equalsIgnoreCase("TRUE")){
				r.setMsg("用户已被锁定！");
				r.setResult(IDMLoginResult.LOGIN_DISABLED);
				return r;
			}
			String loginIntruderAttempts = map.get("loginIntruderAttempts");
			if(loginIntruderAttempts!=null && !"".equals(loginIntruderAttempts)){
				int loginIntruderAttemptsInt = Integer.parseInt(loginIntruderAttempts);
				if(loginIntruderAttemptsInt>=5){
					r.setMsg("账号锁定，输入错误密码次数"+loginIntruderAttemptsInt+"次！");
					r.setResult(IDMLoginResult.LOGIN_INTRUDER_ATTEMPTS);
					return r;
				}
			}
			//超期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssZ");
			String now = sdf.format(new Date());
			String loginExpirationTime = map.get("loginExpirationTime");
			if(loginExpirationTime!=null && !"".equals(loginExpirationTime)){
				if(now.compareTo(loginExpirationTime)>0){
					r.setMsg("用户密码已经过期！");
					r.setResult(IDMLoginResult.LOGIN_EXPIRATION_TIME);
					return r;
				}
			}
			//强制修改密码
			String passwordExpirationTime = map.get("passwordExpirationTime");
			if(passwordExpirationTime!=null && !"".equals(passwordExpirationTime)){
				if(now.compareTo(passwordExpirationTime)>0){
					r.setMsg("2个月内未修改密码，请修改后再登录");
					r.setResult(IDMLoginResult.PASSWORD_EXPIRATION_TIME);
					return r;
				}
			}
			//判断密码
			LDAPAttribute pwdAttr = new LDAPAttribute("userPassword", pwd );
			boolean pwdCorrect = lc.compare( dn, pwdAttr );
			System.out.println( pwdCorrect ? "密码正确":"密码错误.\n");
			if(pwdCorrect){
				r.setMsg("密码正确");
				r.setResult(IDMLoginResult.SUCCESS);
			}else{
				r.setMsg("密码错误");
				r.setResult(IDMLoginResult.PASSWORD_WRONG);
			}
            lc.disconnect();
            return r;
        }catch(Exception e ) {
        	e.printStackTrace();
            System.out.println( "Error: " + e.toString() );
            r.setMsg("登录失败。");
            r.setResult(IDMLoginResult.EXCEPTION);
            return r;
        }
    }
    /**
     * 获取用户属性信息
     * @param lc
     * @param dn
     */
    public Map<String,String>  getOperationalAttrs( LDAPConnection lc, String dn)throws LDAPException{
    	String returnAttrs[] = {"logindisabled","loginExpirationTime","loginIntruderAttempts","passwordExpirationTime"};
    	Map<String,String> map = new HashMap<String,String>();
        String attributeName, attrValue;
        Iterator allAttributes;
        Enumeration allValues;
        LDAPAttribute attribute;
        LDAPAttributeSet attributeSet;
        for(String str:returnAttrs){
        	map.put(str, "");
        }
        try {
            LDAPEntry entry = lc.read( dn, returnAttrs );
            attributeSet = entry.getAttributeSet();
            allAttributes = attributeSet.iterator();
            while(allAttributes.hasNext()) {
                attribute = (LDAPAttribute)allAttributes.next();
                attributeName = attribute.getName();
                if ( (allValues = attribute.getStringValues()) != null
                  && (attrValue = (String) allValues.nextElement()) != null) {
                	map.put(attributeName, attrValue);
                }
            }
            return map;
        }catch( LDAPException e ) {
            System.err.println( "\nOperationalAttrs() failed.");
            System.err.println( "\nError: " + e.toString() );
            System.exit(1);
            return map;
        }
    }

    public IDMLoginResult login(String userName, String password){
    	LdapUserAuth info = new LdapUserAuth();
    	//下面信息需要自己填写
        String cn = userName;
        String pwd = password;
//		ldapHost = "10.135.7.92";
//		loginDN = "cn=httestuser,ou=user,o=services";
//		loginPassword = "httestuser@0423";
        //获取链接
        LDAPConnection connection = getLDAPConnection(ldapHost,loginDN,loginPassword);
        //获取用户dn
        String dn = info.getDN(connection, cn);
        IDMLoginResult result =null;
        if(dn!=null){
        	 //用户认证
        	result = info.checkInfo( ldapHost, loginDN, loginPassword, dn, pwd);
        	System.out.println("=======result="+result.getMsg());
        }else{
        	result=new IDMLoginResult();
        	result.setMsg("登录失败。");
        	result.setResult(IDMLoginResult.EXCEPTION);
        }
    	//释放链接
    	releaseLDAPConnection(connection);

    	return result;
    }
}
