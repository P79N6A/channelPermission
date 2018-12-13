var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
    fitColumns: true,//fitColumns: true,
    singleSelect: true,//多选
    url: '',
//    idField: 'id',
    frozenColumns: [[{
        field: 'id', 
        checkbox: false,
        hidden:true
    }]],
    columns: [[
        {title: '信息类型 ', field: 'infoType', sortable: true},
        {title: '推送时间', field: 'sendTime', sortable: true},
        {title: '推送人', field: 'sender', sortable: true, align: 'right'},
        {title: '推送类型', field: 'sendType', sortable: true, align: 'right'},
        {title: '推送状态', field: 'sendStatus', sortable: true, align: 'right'},
        {title: '推送内容', field: 'sendInfo', sortable: true, align: 'right'},
        {title: '结果信息', field: 'responseInfo', sortable: true }
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
};
$(function () {
	var datagrid = $('#datagrid_channelForecastGoal').datagrid(datagridOptions_orderForecastGoal);
/*	//信息类型
	$("#channelId").combobox({
	    url: '/push/channelsList',
	    valueField: "id",
	    textField: "channelcode",
	    required: true,
	    editable: false,
	    hasDownArrow: true,
	    filter: function (q, row) {
	        var opts = $(this).combobox('options');
	        return row[opts.textField].indexOf(q) >= 0;
	    },
	    onSelect: function (reord) {
	    },
	    onUnselect: function () {
	    }
	});
	//渠道
	$("#channelId").combobox({
	    url: '/push/channelsList',
	    valueField: "id",
	    textField: "channelcode",
	    required: true,
	    editable: false,
	    hasDownArrow: true,
	    filter: function (q, row) {
	        var opts = $(this).combobox('options');
	        return row[opts.textField].indexOf(q) >= 0;
	    },
	    onSelect: function (reord) {
	    },
	    onUnselect: function () {
	    }
	});
	//渠道
	$("#channelId").combobox({
	    url: '/push/channelsList',
	    valueField: "id",
	    textField: "channelcode",
	    required: true,
	    editable: false,
	    hasDownArrow: true,
	    filter: function (q, row) {
	        var opts = $(this).combobox('options');
	        return row[opts.textField].indexOf(q) >= 0;
	    },
	    onSelect: function (reord) {
	    },
	    onUnselect: function () {
	    }
	});*/
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
    });
   
 });  

//点击查询
$("#searchBtn").on('click', function (event) {
	var channelCode = $("#channelCode").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
    var infoType = $("#infoType").combobox("getValue");
    var sendStatus = $("#sendStatus").combobox("getValue");
    var sendType = $("#sendType").combobox("getValue");
    if(startDate>endDate){
    	alert("开始时间应小于结束时间");
    	return;
    }
    datagrid = $('#datagrid_channelForecastGoal').datagrid({
        url: "/push/findPushDataLog",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        queryParams: {
        	channelCode: channelCode,
        	infoType: infoType,
        	sendStatus: sendStatus,
        	sendType: sendType,
            startDate: startDate,
            endDate: endDate
        },
        frozenColumns: [[{
            field: 'id', 
            checkbox: false,
            hidden:true
        
        }]],
        columns: [[
            {title: '信息类型 ', field: 'infotype', sortable: true},
            {title: '推送时间', field: 'sendtime', sortable: true},
            {title: '推送人', field: 'sender', sortable: true, align: 'right'},
            {title: '推送类型', field: 'sendtype', sortable: true, align: 'right',
              	formatter : function(value) {
                	
            		if(value == 0){
            			value = "系统";
            		}
            		if(value == 1){
            			value = "人工";
            		}
            	return value;
        	}
            
            },
            {title: '推送状态', field: 'sendstatus', sortable: true, align: 'right',
              	formatter : function(value) {
                	
            		if(value == 0){
            			value = "失败";
            		}
            		if(value == 1){
            			value = "成功";
            		}
            	return value;
        	}
            },
            {title: '推送内容', field: 'sendinfo', sortable: true, align: 'right'},
            {title: '结果信息', field: 'responseinfo', sortable: true }
        ]],
    });

});