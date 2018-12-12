

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
        {title: '品类', field: 'category', sortable: false},
        {title: '物料号', field: 'materielNum', sortable: false},
        {title: '机编', field: 'machineNum', sortable: false},
        {title: '负提单号', field: 'negativeLadingListNum', sortable: false},
        {title: '负提单产生日期', field: 'negativeProduceDate', sortable: false},
        {title: '入库单', field: 'pushWarehouseNum', sortable: false},
        {title: '入库单产生日期', field: 'pushWarehouseProduceDate', sortable: false},
        {title: '入库完成时间', field: 'pushCompleteDate', sortable: false},
        {title: '周期（天）', field: 'period', sortable: false}
        /*{title: '备注', field: 'remark', sortable: false}*/
    ]],
    toolbar: '#datagridToolbar_itemAttribute',
    striped: true,
    pagination: true,
    rownumbers: true,
};

var datagrid = $('#datagrid_itemAttribute').datagrid(datagridOptions_itemAttribute);
$(function () {
	
	
	$.ajax({
	        url:'findmach',
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
