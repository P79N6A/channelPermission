
var dataGrid = null;

 
//查询
 var searchselect = function () {
	 var options = dataGrid.datagrid('getPager').data("pagination").options;
	 var addTimeMin = $("#addTimeMin").datebox("getValue");//下单时间 开始
	 var addTimeMix = $("#addTimeMix").datebox("getValue");//下单时间 截止
	 if(addTimeMin>addTimeMix){
		 alert("开始时间应小于结束时间");
		 return;
	 }
      $.ajax({
         url      : "/orderOperation/search",
         type     : 'POST',
         dataType : 'json',
          data     : {
        	    orderSn :$("#orderSn").val().trim(),
      		  	addTimeMin:addTimeMin,
      		  	addTimeMix :addTimeMix,
      		  	source :$("#source").datebox("getValue"),//订单来源
      		  	page  : options.pageNumber,
      		  	rows : options.pageSize
         }, 
         success  : function(data) {
        	  var datagridData_orderForecastGoal = {
          		    'data': {
          		        'records':
          		        	data.rows,'totalCount':data.total
          		            }
        	  }
        	  dataGrid.datagrid('loadData', datagridData_orderForecastGoal);
         }
     });
 }
    
      $('#search').click(function() {
    	  var options = dataGrid.datagrid('getPager').data("pagination").options;
			options.pageNumber = 1;
			dataGrid.datagrid('getPager').pagination('refresh');
    	  searchselect()
    	  }
      )

$(function () {
    var moreOptions = $(".morehide");
    var osn = $("#orderSn").val().trim();
    for (var i = 0; i < moreOptions.length; i++) {
        $(moreOptions[i]).css("display", "none");
    } ;
    dataGrid = $('#datagrid_orderForecastGoal').datagrid({
		striped : true, // 隔行变色
		rownumbers : true,
		fit: true,
		pagination : true,
		singleSelect : true,
		fitColunms : true,
		idField : 'id',
		columns : [ [  
			{
			title : '订单号 ',
			width : 150,
			field : 'orderSn',
			align : 'center',
			formatter: function(value,row,index){
					return '<a href="../operationArea/orderNumberSelect?orderSn='+row.orderSn+'">'+row.orderSn+'</a>';
			}
		}, {
			title : '下单时间',
			width : 160,
			field : 'addTimeMin',
			align : 'center'
		}, {
			
			title : '付款时间',
			width : 160,
			field : 'payTimeMin',
			align : 'center'
		} ,   {
			title : '首次确认时间 ',
			width : 160,
			field : 'firstConfirmTime',
			align : 'center'
		}, {
			title : '首次确认人 ',
			width : 100,
			field : 'firstConfirmPerson',
			align : 'center'
		}, {
			title : '已确认次数 ',
			width : 100,
			field : 'autoConfirmNumMin',
			align : 'center'
		}, {
			title : '订单来源 ',
			width : 170,
			field : 'source',
			align : 'center'
		},{
			title : '是否货到付款 ',
		 	width : 120,
			field : 'isCod',
			formatter : function(value) {
            	
        		if(value == 0){
        			value = "否";
        		}
        		if(value == 1){
        			value = "否";
        		}
        	return value;
    	},
			align : 'center'
		} ] ],
		 toolbar: '#datagridToolbar_orderForecastGoal'
	});
    dataGrid.datagrid('getPager').pagination({
    	 pageSize: 50,
	     pageList: [50,100,200,300],
		onSelectPage : function(pageNumber, pageSize) {
			searchselect();
		}
	});

    
    
    
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
    });

	if(osn!=null && osn !=""){
		searchselect();
	}
	//取得订单来源列表
    jQuery.getJSON("/orderOperation/getSource", function (result) {
        //添加全部
    	 result.unshift({source: '全部'});
        $("#source").combobox({
            data: result,
            valueField: 'orderSource',
            textField: 'note',
            panelHeight: '300',
            editable: false,
            value: '全部'
        });
    });
});

