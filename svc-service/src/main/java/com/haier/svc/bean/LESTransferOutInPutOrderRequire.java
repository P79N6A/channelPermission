/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 中转出入库,入日日顺时间(LES)
 * @author xuelt
 *
 */
public class LESTransferOutInPutOrderRequire implements Serializable {
private static final long serialVersionUID = 1L;
	
	private String ZFLAG;  //1为查询退货 2查询转运
    private List<String> BSTKD;	//提单号
	public String getZFLAG() {
		return ZFLAG;
	}
	public void setZFLAG(String zFLAG) {
		ZFLAG = zFLAG;
	}
	public List<String> getBSTKD() {
		return BSTKD;
	}
	public void setBSTKD(List<String> bSTKD) {
		BSTKD = bSTKD;
	}


  public String toXMLMessage() {
	  String head = "<PARAMETER>" + "<!--Zero or more repetitions:-->" + "<DETAIL>" + "<ZFLAG>"
              + ZFLAG + "</ZFLAG>";
	  String content = "";
	  for(int i = 0; i < BSTKD.size(); i++){
		  content = content + "<BSTKD>" + BSTKD.get(i) + "</BSTKD>";
	  }
	  String tail = " </DETAIL>" + "</PARAMETER>";
	  //System.out.println(head+content+tail);
        return head+content+tail;
    }
}
