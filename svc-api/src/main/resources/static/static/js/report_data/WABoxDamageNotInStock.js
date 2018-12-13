/*var datagridData_itemAttribute = {
    'data': {
        'records': [
            {
                'centerName': '北京', sortable: true,
                'warehouseLocation': '35%', sortable: true,
                'netListNum': '35%', sortable: true,
                'category': '35%', sortable: true,
                'materielNum': '35%', sortable: true,
                'machineNum': '35%', sortable: true,
                'storageDate': '35%', sortable: true,
                'pushTime': '35%', sortable: true,
                'returnExchangeBoxResult': '35%', sortable: true,
                'returnResultTime': '35%', sortable: true,
                'secondPushTime': '35%', sortable: true,
                'negativeLadingListNum': '35%', sortable: true,
                'negativeProduceDate': '35%', sortable: true,
                'pushWarehouseNum': '35%', sortable: true,
                'pushWarehouseProduceDate': '35%', sortable: true,
                'pushCompleteDate': '35%', sortable: true
            },
            {
                'centerName': '上海', sortable: true,
                'warehouseLocation': '35%', sortable: true,
                'netListNum': '35%', sortable: true,
                'category': '35%', sortable: true,
                'materielNum': '35%', sortable: true,
                'machineNum': '35%', sortable: true,
                'storageDate': '35%', sortable: true,
                'pushTime': '35%', sortable: true,
                'returnExchangeBoxResult': '35%', sortable: true,
                'returnResultTime': '35%', sortable: true,
                'secondPushTime': '35%', sortable: true,
                'negativeLadingListNum': '35%', sortable: true,
                'negativeProduceDate': '35%', sortable: true,
                'pushWarehouseNum': '35%', sortable: true,
                'pushWarehouseProduceDate': '35%', sortable: true,
                'pushCompleteDate': '35%', sortable: true
            },
            {
                'centerName': '深圳', sortable: true,
                'warehouseLocation': '35%', sortable: true,
                'netListNum': '35%', sortable: true,
                'category': '35%', sortable: true,
                'materielNum': '35%', sortable: true,
                'machineNum': '35%', sortable: true,
                'storageDate': '35%', sortable: true,
                'pushTime': '35%', sortable: true,
                'returnExchangeBoxResult': '35%', sortable: true,
                'returnResultTime': '35%', sortable: true,
                'secondPushTime': '35%', sortable: true,
                'negativeLadingListNum': '35%', sortable: true,
                'negativeProduceDate': '35%', sortable: true,
                'pushWarehouseNum': '35%', sortable: true,
                'pushWarehouseProduceDate': '35%', sortable: true,
                'pushCompleteDate': '35%', sortable: true
            },
            {
                'centerName': '重庆', sortable: true,
                'warehouseLocation': '35%', sortable: true,
                'netListNum': '35%', sortable: true,
                'category': '35%', sortable: true,
                'materielNum': '35%', sortable: true,
                'machineNum': '35%', sortable: true,
                'storageDate': '35%', sortable: true,
                'pushTime': '35%', sortable: true,
                'returnExchangeBoxResult': '35%', sortable: true,
                'returnResultTime': '35%', sortable: true,
                'secondPushTime': '35%', sortable: true,
                'negativeLadingListNum': '35%', sortable: true,
                'negativeProduceDate': '35%', sortable: true,
                'pushWarehouseNum': '35%', sortable: true,
                'pushWarehouseProduceDate': '35%', sortable: true,
                'pushCompleteDate': '35%', sortable: true
            },
            {
                'centerName': '青岛', sortable: true,
                'warehouseLocation': '35%', sortable: true,
                'netListNum': '35%', sortable: true,
                'category': '35%', sortable: true,
                'materielNum': '35%', sortable: true,
                'machineNum': '35%', sortable: true,
                'storageDate': '35%', sortable: true,
                'pushTime': '35%', sortable: true,
                'returnExchangeBoxResult': '35%', sortable: true,
                'returnResultTime': '35%', sortable: true,
                'secondPushTime': '35%', sortable: true,
                'negativeLadingListNum': '35%', sortable: true,
                'negativeProduceDate': '35%', sortable: true,
                'pushWarehouseNum': '35%', sortable: true,
                'pushWarehouseProduceDate': '35%', sortable: true,
                'pushCompleteDate': '35%', sortable: true
            }
        ], 'totalCount': 10
    }
};*/

var datagridOptions_itemAttribute = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/auth/itemAttribute/p',
    // frozenColumns: [[{
    //     field: 'id', checkbox: true
    // }]],
    columns: [[
        {title: '中心', field: 'centerName', sortable: false},
        {title: '库位', field: 'warehouseLocation', sortable: false},
        {title: '网单号', field: 'netListNum', sortable: false},
        {title: '产业', field: 'industry', sortable: false},
        {title: '品类', field: 'category', sortable: false},
        {title: '物料号', field: 'materielNum', sortable: false},
        {title: '机编', field: 'machineNum', sortable: false},
/*        {title: '是否套机', field: 'istz', sortable: false,
        	formatter : function(value) {
            	
            		if(value == 0){
            			value = "否";
            		}
            		if(value == 1){
            			value = "否";
            		}
            	return value;
        	}
            	
        },*/
        {title: '回传换箱结果', field: 'returnExchangeBoxResult', sortable: false},
        {title: '22出库单', field: 'outNum', sortable: false},
        {title: '正品入库单', field: 'GenuineWarehouseNum', sortable: false},
        {title: '不良品入库单', field: 'UndesirableWarehouseNum', sortable: false},
        {title: '正品或不良品入库单产生日期', field: 'pushWarehouseProduceDate', sortable: false,
        	formatter : function(value) {
            	if(value){
                var date = new Date(value);
                var year = date.getFullYear().toString();
                var month = (date.getMonth() + 1);
                var day = date.getDate().toString();
                var hour = date.getHours().toString();
                var minutes = date.getMinutes().toString();
                var seconds = date.getSeconds().toString();
                if (month < 10) {
                    month = "0" + month;
                }
                if (day < 10) {
                    day = "0" + day;
                }
                if (hour < 10) {
                    hour = "0" + hour;
                }
                if (minutes < 10) {
                    minutes = "0" + minutes;
                }
                if (seconds < 10) {
                    seconds = "0" + seconds;
                }
                return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
            	}
            	}},
        {title: '未完成周期（天）', field: 'period', sortable: false}
    ]],
    toolbar: '#datagridToolbar_itemAttribute',
    striped: true,
    pagination: true,
    rownumbers: true,
};

var datagrid = $('#datagrid_itemAttribute').datagrid(datagridOptions_itemAttribute);
$(function () {
	
	
	$.ajax({
	        url:'findNotInStock',
	        type:'POST',
	        /*data:'',*/
	        dataType : "json", //返回数据形式为json
	        success : function(data) {
	        	  var datagridData_itemAttribute = {
	            		    'data': {
	            		        'records':
	            		        	data.rows,'totalCount':data.total
	            		            }
	          	  }
	        	  datagrid.datagrid('loadData', datagridData_itemAttribute);
	                                
	       },
	           error : function(errorMsg) {
	               alert("不好意思，图表请求数据失败啦!");
	                myChart.hideLoading();
	              }
	 });
});
