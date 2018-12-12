/*var datagridData_itemAttribute = {
    'data': {
        'records':  filed
            {
                'centerName': '不良品量',sortable: true, 
                'fridge': 6300, sortable: true,
                'washingMach': 6300, sortable: true,
                'freezer': 6300, sortable: true,
                'airCondition': 6300, sortable: true,
                'waterHeater': 6300, sortable: true,
                'kichenMach': 6300, sortable: true,
                'colorTV': 6300, sortable: true
            },
            {
                'centerName': '北京', sortable: true,
                'fridge': '35%', sortable: true,
                'washingMach': '35%', sortable: true,
                'freezer': '35%', sortable: true,
                'airCondition': '35%', sortable: true,
                'waterHeater': '35%', sortable: true,
                'kichenMach': '35%', sortable: true,
                'colorTV': '35%', sortable: true
            },
            {
                'centerName': '上海', sortable: true,
                'fridge': '35%', sortable: true,
                'washingMach': '35%', sortable: true,
                'freezer': '35%', sortable: true,
                'airCondition': '35%', sortable: true,
                'waterHeater': '35%', sortable: true,
                'kichenMach': '35%', sortable: true,
                'colorTV': '35%', sortable: true
            },
            {
                'centerName': '深圳', sortable: true,
                'fridge': '35%', sortable: true,
                'washingMach': '35%', sortable: true,
                'freezer': '35%', sortable: true,
                'airCondition': '35%', sortable: true,
                'waterHeater': '35%', sortable: true,
                'kichenMach': '35%', sortable: true,
                'colorTV': '35%', sortable: true
            },
            {
                'centerName': '重庆', sortable: true,
                'fridge': '35%', sortable: true,
                'washingMach': '35%', sortable: true,
                'freezer': '35%', sortable: true,
                'airCondition': '35%', sortable: true,
                'waterHeater': '35%', sortable: true,
                'kichenMach': '35%', sortable: true,
                'colorTV': '35%', sortable: true
            },
            {
                'centerName': '青岛', sortable: true,
                'fridge': '35%', sortable: true,
                 'washingMach': '35%', sortable: true,
                'freezer': '35%', sortable: true,
                'airCondition': '35%', sortable: true,
                'waterHeater': '35%', sortable: true,
                'kichenMach': '35%', sortable: true,
                'colorTV': '35%', sortable: true
            }
        , 'totalCount': 10
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
        {title: '冰箱', field: 'fridge', sortable: false},
        {title: '洗衣机', field: 'washingMach', sortable: false},
        {title: '冷柜', field: 'freezer', sortable: false},
        {title: '空调', field: 'airCondition', sortable: false},
        {title: '热水器', field: 'waterHeater', sortable: false},
        {title: '厨电', field: 'kichenMach', sortable: false},
        {title: '彩电', field: 'colorTV', sortable: false}

    ]],
    toolbar: '#datagridToolbar_itemAttribute',
    striped: true,
    pagination: true,
    rownumbers: true,
};
var datagrid = $('#datagrid_itemAttribute').datagrid(datagridOptions_itemAttribute);
$(function () {
	
	
	$.ajax({
	        url:'findIndustry',
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
    
    /*datagrid.datagrid('loadData', datagridData_itemAttribute);*/
    //创建表头的菜单
    //CustomConfig.load(datagrid,"ItemAttribute");
    //$("#searchPanel_itemAttribute").panel('resize');

    /*$("#searchBtn_itemAttribute").on('click', function (event) {
     var param = $('#paramForm_itemAttribute').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefauslt();
     });*/

    
});
