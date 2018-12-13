var dataGrid = null;
//查询
 var searchselect = function () {
	 var options = dataGrid.datagrid('getPager').data("pagination").options;
      $.ajax({
         url      : "/zfbOrder/tradingFlowList",
         type     : 'POST',
         dataType : 'json',
          data     : {		
              orderSn:$("#orderSn").val(),
              addTimeBegine:$("#addTimeBegine").datebox('getValue'),
              addTimeEnd:$("#addTimeEnd").datebox('getValue'),
              shopName:$("#shopName").combobox('getValue'),
              page: options.pageNumber,
              rows: options.pageSize
         }, 
         success  : function(data) {
        	  var datagridData_orderForecastGoal = {
          		    'data': {
          		        'records':
          		        	data.rows,'totalCount':data.total
          		            }
        	  }
        	  dataGrid.datagrid('loadData', datagridData_orderForecastGoal);
        	  options.total = data.total;
         }
     });
 }
    
      $('#searchBtn_orderForecastGoal').click(function() {
    	  var options = dataGrid.datagrid('getPager').data("pagination").options;
			options.pageNumber = 1;
			dataGrid.datagrid('getPager').pagination('refresh');
    	  searchselect()
    	  }
      )

$(function () {
	 var queryParameters={		
		 addTimeBegine:$("#addTimeBegine").datebox('getValue'),
		 addTimeEnd:$("#addTimeEnd").datebox('getValue'),
		 shopName:$("#shopName").combobox('getValue'),
	         };
    var moreOptions = $(".morehide");
    for (var i = 0; i < moreOptions.length; i++) {
        $(moreOptions[i]).css("display", "none");
    } ;
    dataGrid = $('#datagrid_orderForecastGoal').datagrid({
        url: "/zfbOrder/tradingFlowList",
		striped : true, // 隔行变色
		rownumbers : true,
		fit: true,
		pagination : true,
		singleSelect : true,
		fitColunms : true,
		idField : 'id',
		columns : [ [ 
					{title: '创建时间', field: 'time', sortable: false},
					{title: '店铺名称', field: 'shopName', sortable: false},
					{title: '期初额(收入)', field: 'startMoney', sortable: false},
					{title: '期末额(收入)', field: 'endMoney', sortable: false},
					{title: '收入金额', field: 'incomeMoney', sortable: false},
					{title: '支出金额', field: 'expenditureMoney', sortable: false}
		 ] ],
		 toolbar: '#datagridToolbar_orderForecastGoal',
         pageSize: 50,
         pageList: [50, 100, 200],
	});
    dataGrid.datagrid('getPager').pagination({
		onSelectPage : function(pageNumber, pageSize) {
			searchselect();
		}
	});
});
