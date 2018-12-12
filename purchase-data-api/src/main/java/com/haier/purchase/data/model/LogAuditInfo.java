package com.haier.purchase.data.model;

import java.io.Serializable;

public class LogAuditInfo implements Serializable {
    /**
	 *Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private String order_id;
    private int    type;
    private String content;
    private String log_time;
    private String oper_user_name;
    private String oper_user_id;
    //xuelin.zhao start
    private String typeName;
    private String jude_way_channel;
    private String gate_way_channel;
    private String channel;
    private String category;
    //xuelin.zhao end

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLog_time() {
        return log_time;
    }

    public void setLog_time(String log_time) {
        this.log_time = log_time;
    }

    public String getOper_user_name() {
        return oper_user_name;
    }

    public void setOper_user_name(String oper_user_name) {
        this.oper_user_name = oper_user_name;
    }

    public String getOper_user_id() {
        return oper_user_id;
    }

    public void setOper_user_id(String oper_user_id) {
        this.oper_user_id = oper_user_id;
    }

	public String getJude_way_channel() {
		return jude_way_channel;
	}

	public void setJude_way_channel(String jude_way_channel) {
		this.jude_way_channel = jude_way_channel;
	}

	public String getGate_way_channel() {
		return gate_way_channel;
	}

	public void setGate_way_channel(String gate_way_channel) {
		this.gate_way_channel = gate_way_channel;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
    
}
