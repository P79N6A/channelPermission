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
        {title: '渠道编码 ', field: 'channelCode', sortable: true},
        {title: '渠道名称', field: 'channelName', sortable: true},
        {title: '最后推送时间', field: 'moneyLimit', sortable: true, align: 'right'},
        {title: '最后推送信息', field: 'moneyAlert', sortable: true, align: 'right'},
        {title: '推送状态', field: 'moneyLock', sortable: true, align: 'right'},
        {title: '操作', field: 'remark', sortable: true,        
        	formatter: function(value,row,index){
			
			return "<a id='oper' href='#' onclick=onRelease("+JSON.stringify(row)+")>编辑</a>";
        	}
        }
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
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
    });
   
 });  

//点击查询
$("#searchBtn").on('click', function (event) {
	 
    var channelNameData = $("#channelId").combobox("getText");
    datagrid = $('#datagrid_channelForecastGoal').datagrid({
        url: "/push/findPushData",
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
            channelName: channelNameData
        },
        frozenColumns: [[{
            field: 'id', 
            checkbox: false,
            hidden:true
        
        }]],
        columns: [[
            {title: '渠道编码 ', field: 'channelCode', sortable: true,
    			formatter: function(value,row,index){
					return '<a href="../push/channelCodeSelect?channelCode='+row.channelCode+'">'+row.channelCode+'</a>';
    			}	
            },
            {title: '渠道名称', field: 'channelName', sortable: true},
            {title: '最后推送时间', field: 'sendTime', sortable: true, align: 'right'},
            {title: '最后推送信息', field: 'sendInfo', sortable: true, align: 'right'},
            {title: '推送状态', field: 'sendStatus', sortable: true, align: 'right',
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
           {
				title : '操作',
				width : 110,
				field : 'update',
				align : 'center',
				sortable: true,
		    	formatter: function(value,row,index){
					return '<a href="../push/pushProduct">商品信息</a>&nbsp;&nbsp;<a href="../push/pushPrice">价格信息</a>&nbsp;&nbsp;<a href="../push/pushAvailable">可售区域</a>';
    			}
		            
            }
        ]],
    });

});