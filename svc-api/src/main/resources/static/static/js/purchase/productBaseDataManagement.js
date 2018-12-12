
//点击查询
$("#search").on('click', function createTbody(){

    var product_group_id = $("#product_group_id").combobox("getValue");
    //如果是ALL，产品组设为空
    if (product_group_id == "ALL") {
        product_group_id = "";
    }
    var type = $("#type").combobox("getValue");
    //如果是ALL，渠道设为空
    if (type == "全部") {
        type = "";
    }
 
    var status = $("#status").combobox("getValue");
    //如果是ALL，订单类型设为空
    if (status == "3") {
    	status = "";
    }
    var update = $("#update").combobox("getValue");
    //如果是ALL，订单类型设为空
    if (update == "2") {
    	update = "";
    }
    var category_id = $("#category_id").combobox("getValue");
    //如果是ALL，品类设为空
    if (category_id == "ALL") {
        category_id = "";
    }
	
 
    //grid加载
    if (datagrid) {
        $('#dataGrid').datagrid('load',
                {
                    type: type,
                    product_group_id: product_group_id,
                    materials_id: $("#materials_id").val(),
                    materials_desc: $("#materials_desc").val(),
                    status: status,
                    update:update,
                    category_id: category_id
                });
    } else {
        datagrid = $('#dataGrid').datagrid({
            url: "findProductBaseData",
            type:"post",
            fit: true,
            pagination: true,
            singleSelect: true,
            pageSize: 50,
            pageList: [50, 100, 200],
            nowrap: false,
            rownumbers: true,
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            collapsible: true,
            striped: true,
            queryParams: {
            	  type: type,
                  product_group_id: product_group_id,
                  materials_id: $("#materials_id").val(),
                  materials_desc: $("#materials_desc").val(),
                  status: status,
                  update:update,
                  category_id: category_id
            },
            columns: [
                [

                    {
                        field: 'materialCode',
                        title: '物料编码',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'materialDescription',
                        title: '型号',
                        width: 220,
                        align: 'center'
                    },
                    {
                        field: 'materialType',
                        title: '类型',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'departmentName',
                        title: '产品组',
                        width: 100,
                        align: 'center'
                    },                    {
                        field: 'cbsCategory',
                        title: '品类',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 60,
                        formatter : function(value){
                            if(value==0){  
                                return "初始化";  
                            }else if(row.isu==1){  
                                return "使用中";  
                            }else{
                            	return "停用中";
                            }
                        },
                        align: 'center'
                    },
                    {
                        field: 'modifier',
                        title: '最后更新人',
                        width: 65,
                        align: 'center'
                    },
                    {
                        field: 'productType',
                        title: '产品类型',
                        width: 60,
                        align: 'center'
                    },
                    {
                        field: 'lengthNumber',
                        title: '长度',
                        width: 60,
                        align: 'center'
                    },
                    {
                        field: 'widthNumber',
                        title: '宽度',
                        width: 60,
                        align: 'center'
                    },
                    {
                        field: 'highNumber',
                        title: '高度',
                        width: 60,
                        align: 'center'
                    },
                    {
                        field: 'grossWeightNumber',
                        title: '总重量',
                        width: 60,
                        align: 'center'
                    },
                    {
                        field: 'weightUnit',
                        title: '单位',
                        width: 60,
                        align: 'center'
                    },
                    {
                        field: 'deleteFlag',
                        title: '删除标志',
                        width: 60,
                        align: 'center'
                    },
                    {
                        field: 'created',
                        title: '创建时间',
                        width: 100,
                        formatter : function(value) {
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
                        },
                        align: 'center'
                    },
                    {
                        field: 'lastUpd',
                        title: '最后更新时间',
                        width: 100,
                        formatter : function(value) {
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
                        },
                        align: 'center'
                    },
                    {
                        field: 'proBand',
                        title: '品牌',
                        width: 60,
                        align: 'center'
                    },
                    {
                        field: 'isAutoUpdate',
                        title: '是否同步',
                        width: 60,                        
                        formatter : function(value){
                            if(value==0){  
                                return "否";  
                            }else{
                            	return "是";
                            }
                        },
                        align: 'center'
                    }
                ]
            ]
        });
    }
    $('#dataGrid').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
});

var datagrid;
$(function () {
    
	//取得产品组
	jQuery.getJSON("/purchase/getProductGroupByAuth", function(result){
		var productgroup = result.data;
		productgroup.unshift({value:'ALL',valueMeaning:'全部'});
		$("#product_group_id").combobox({
			 data:result.data,
		     valueField:'value',
			 textField:'valueMeaning',
			 panelHeight:'300',
			 editable:false,
			 value:'ALL'
		});
	});
    //取得类型列表
    jQuery.getJSON("/purchaseAllocation/getType", function (result) {
        //添加全部
    	 result.unshift({materialType: '全部'});
        $("#type").combobox({
            data: result,
            valueField: 'materialType',
            textField: 'materialType',
            panelHeight: 'auto',
            editable: false,
            value: '全部'
        });
    });
    jQuery.getJSON("/purchase/getCbsCategoryByAuth", function (result) {
        var JosnList = [];
        var cbsCategoryJson = {id: "ALL", text: "全部"};
        JosnList.push(cbsCategoryJson)
        jQuery.each(result.data, function (i, v) {
            var item = {id: v, text: v}
            JosnList.push(item)
        });
        $("#category_id").combobox({
            data: JosnList,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            editable: false,
            value: 'ALL'
        });
    });
    


});
$("#n").click(function () {
/*	$(this).dialog('close');*/
	$("#manualDiv").window('close');
});

$(function(){
	$("#manualDiv").panel('close');
});


$('#export').click(function () {
   
    var category_id = $("#category_id").combobox("getValue");
    var product_group_id = $("#product_group_id").combobox("getValue");
    var materials_id = $("#materials_id").val();
    var materials_desc = $("#materials_desc").val();

    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
/*    if (datagrid) {
        //获得所有行
        var getItems = $('#dataGrid').datagrid('getRows');
        var exportData = new Array();
        //将订单号存入Array
        $.each(getItems, function (index, item) {
            exportData.push(item.order_id);
        });
        $("#exportData").val(JSON.stringify(exportData));
    }*/
    $.messager.confirm('确认', '确定要全部导出吗？', function (r) {
        if (r) {
            $("#exportData").val('');
            $('#filterForm').attr("action", 'exportProductBaseList.export');
            $('#filterForm').submit();
        }
    });
});


 



//返回查询页面
$("#close").click(function() {
					 $("#passForm").form("clear");
					 $("#passDiv").window('close');
					 $('#oms_order_id_save').val("");
					 $("#passForm").form("clear");
				});	


	

