package com.haier.stock.model;

import com.haier.stock.util.excel.Excel;
import com.haier.stock.util.excel.ExcelTitle;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Excel(filename = "虚拟库位")
public class InvSection implements Serializable{

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;
    private String id;

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    private String updateTimeStr;
    /**
     * 正品库
     */
    public static String W10 = "10";
    /**
     * 样品机库
     */
    public static String W40 = "40";
	/**
	 * 库位编码
	 */
    @ExcelTitle(titleName = "库位编码")
    private String secCode;
	/**
	 * LES库位
	 */
    private String lesSecCode;
    /**
     * 库位名称
     */
    @ExcelTitle(titleName = "库位名称")
    private String secName;
    /**
     * 状态
     */
    private Integer status;

    public String getStatusExcel() {
        return statusExcel;
    }

    public void setStatusExcel(String statusExcel) {
        this.statusExcel = statusExcel;
    }

    @ExcelTitle(titleName = "状态")

    private String statusExcel;
    /**
     * 仓库（TC）代码
     */
    @ExcelTitle(titleName = "仓库编码")
    private String whCode;
    /**
     * 库位属性
     */
    private String sectionProperty;
    /**
     * 渠道编码
     */
    @ExcelTitle(titleName = "渠道编码")
    private String channelCode;
    /**
     * 批次
     */
    @ExcelTitle(titleName = "产品属性")
    private String itemProperty;
    /**
     * 
     */
    private String createUser;

    private Date createTime;

    private String updateUser;
    @ExcelTitle(titleName = "最后更新时间")
    private Date updateTime;
    /**
     * 销售组织编码
     */
    private String corpCode;
    /**
     * 付款方编码
     */
    @ExcelTitle(titleName = "付款方编码")
    private String custCode;
    /**
     * 地区编码
     */
    @ExcelTitle(titleName = "地区编码")
    private String regionCode;
    /**
     * 分渠道送达方
     */
    private String ehaierDeliverCode;
    /**
     * 物流OE码
     */
    
    
    /**
     * 状态 - 在用
     */
    public static Integer STATUS_IS_VALID   = 1;
    /**
     * 库存渠道 - 海朋渠道
     */
    public static String CHANNEL_CODE_HAIP     = "HAIP";
    private String les0eCode;    
    /**
     * 库存渠道 - 基地库
     */
    public static String CHANNEL_CODE_GD  = "GD";
    
    /**
     * 库存渠道 - 日日顺库位
     */
    public static String CHANNEL_CODE_RRS = "RRS";
    /**
     * 库位属性 - 协同仓库位
     */
    public static String SECTION_PROPERTY_XT  = "XT";
    /**
     * 库存渠道 - 净水渠道
     */
    public static String CHANNEL_CODE_JINGSHUI = "JINS";
    /**
     * 库存渠道 - 网单库位
     */
    public static String CHANNEL_CODE_WA  = "WA";
    
    /**
     * 不良品库
     */
    public static String W21 = "21";
    /**
     * 库存渠道 - 净水之后通用特殊渠道
     */
    @SuppressWarnings("serial")
    public static Set<String> NEW_CHANNEL_CODE = new HashSet<String>() {
        {
            add(CHANNEL_CODE_JINGSHUI);
        }
    };
    
    @SuppressWarnings("serial")
    public static Set<String> gdCodes      = new HashSet<String>() {
        {
            add("FA02");
            add("FA01");
            add("0088");
            add("QDCP");
            add("CDCP");
            add("CX11");
            add("EB72");
        }
    };
    

    public static String getW21() {
		return W21;
	}

	public static void setW21(String w21) {
		W21 = w21;
	}

	public static Integer getSTATUS_IS_VALID() {
		return STATUS_IS_VALID;
	}

	public static void setSTATUS_IS_VALID(Integer sTATUS_IS_VALID) {
		STATUS_IS_VALID = sTATUS_IS_VALID;
	}

	@SuppressWarnings("serial")
    public static Map<String, String> NEW_CHANNEL_CODE_MAP = new HashMap<String, String>() {
        {
            put(CHANNEL_CODE_JINGSHUI, "净水");
        }
    };
    @SuppressWarnings("serial")
    public static Set<String> JINGSHUICodes = new HashSet<String>() {
        {
            add("JS01");
        }
    };
    
    @SuppressWarnings("serial")
    public static Set<String> HAIPENGCodes = new HashSet<String>() {
        {
            add("CT01");
        }
    };
    

    /* 设置 地区编码。
    *
    * @param value 属性值
    */

   public boolean isChannelWA() {
       return CHANNEL_CODE_WA.equals(this.channelCode);
   }

   public boolean isChannelRRS() {
       return CHANNEL_CODE_RRS.equals(this.channelCode);
   }

   public boolean isChannelGD() {
       return CHANNEL_CODE_GD.equals(this.channelCode);
   }


    
    public static String getCHANNEL_CODE_WA() {
		return CHANNEL_CODE_WA;
	}

	public static void setCHANNEL_CODE_WA(String cHANNEL_CODE_WA) {
		CHANNEL_CODE_WA = cHANNEL_CODE_WA;
	}

	public static Set<String> getJINGSHUICodes() {
		return JINGSHUICodes;
	}

	public static void setJINGSHUICodes(Set<String> jINGSHUICodes) {
		JINGSHUICodes = jINGSHUICodes;
	}

	public static Set<String> getHAIPENGCodes() {
		return HAIPENGCodes;
	}

	public static void setHAIPENGCodes(Set<String> hAIPENGCodes) {
		HAIPENGCodes = hAIPENGCodes;
	}

	public static String getCHANNEL_CODE_HAIP() {
		return CHANNEL_CODE_HAIP;
	}

	public static void setCHANNEL_CODE_HAIP(String cHANNEL_CODE_HAIP) {
		CHANNEL_CODE_HAIP = cHANNEL_CODE_HAIP;
	}

	public static String getCHANNEL_CODE_GD() {
		return CHANNEL_CODE_GD;
	}

	public static String getW10() {
		return W10;
	}

	public static void setW10(String w10) {
		W10 = w10;
	}

	public static String getCHANNEL_CODE_RRS() {
		return CHANNEL_CODE_RRS;
	}

	public static void setCHANNEL_CODE_RRS(String cHANNEL_CODE_RRS) {
		CHANNEL_CODE_RRS = cHANNEL_CODE_RRS;
	}

	public static void setCHANNEL_CODE_GD(String cHANNEL_CODE_GD) {
		CHANNEL_CODE_GD = cHANNEL_CODE_GD;
	}

	public static String getCHANNEL_CODE_JINGSHUI() {
		return CHANNEL_CODE_JINGSHUI;
	}

	public static void setCHANNEL_CODE_JINGSHUI(String cHANNEL_CODE_JINGSHUI) {
		CHANNEL_CODE_JINGSHUI = cHANNEL_CODE_JINGSHUI;
	}

	public static Set<String> getNEW_CHANNEL_CODE() {
		return NEW_CHANNEL_CODE;
	}

	public static void setNEW_CHANNEL_CODE(Set<String> nEW_CHANNEL_CODE) {
		NEW_CHANNEL_CODE = nEW_CHANNEL_CODE;
	}

	public static Map<String, String> getNEW_CHANNEL_CODE_MAP() {
		return NEW_CHANNEL_CODE_MAP;
	}

	public static void setNEW_CHANNEL_CODE_MAP(Map<String, String> nEW_CHANNEL_CODE_MAP) {
		NEW_CHANNEL_CODE_MAP = nEW_CHANNEL_CODE_MAP;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode == null ? null : secCode.trim();
    }

    public String getLesSecCode() {
        return lesSecCode;
    }

    public void setLesSecCode(String lesSecCode) {
        this.lesSecCode = lesSecCode == null ? null : lesSecCode.trim();
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName == null ? null : secName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode == null ? null : whCode.trim();
    }

    public String getSectionProperty() {
        return sectionProperty;
    }

    public void setSectionProperty(String sectionProperty) {
        this.sectionProperty = sectionProperty == null ? null : sectionProperty.trim();
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getItemProperty() {
        return itemProperty;
    }

    public void setItemProperty(String itemProperty) {
        this.itemProperty = itemProperty == null ? null : itemProperty.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode == null ? null : corpCode.trim();
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode == null ? null : custCode.trim();
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public String getEhaierDeliverCode() {
        return ehaierDeliverCode;
    }

    public void setEhaierDeliverCode(String ehaierDeliverCode) {
        this.ehaierDeliverCode = ehaierDeliverCode == null ? null : ehaierDeliverCode.trim();
    }

    public String getLes0eCode() {
        return les0eCode;
    }

    public void setLes0eCode(String les0eCode) {
        this.les0eCode = les0eCode == null ? null : les0eCode.trim();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	private Integer page;
    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    private Integer start;
    private Integer size;
}