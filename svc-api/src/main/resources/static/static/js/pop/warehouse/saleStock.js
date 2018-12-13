var datagridOptions_saleStockListGoal = {
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
        {title: '渠道名称 ', field: 'channelName', sortable: false},
        {title: '商品编码', field: 'productCode', sortable: false},
        {title: '配送仓库', field: 'warehouseName', sortable: false},
        {title: '商品数量', field: 'quantity', sortable: false,align:'right'},
        {title: '锁库存数量', field: 'lockQuantity', sortable: false,align:'right'},
        {title: '创建者', field: 'createBy', sortable: false},
        {title: '创建时间', field: 'cTime', sortable: false},
//        {title: '更新者', field: 'updateby', sortable: false},
//        {title: '更新时间', field: 'uTime', sortable: false},
        {title: '备注', field: 'remark', sortable: false}
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    striped: true,
    autoRowHeight:true,
    nowrap:true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
};
$(function () {
    var datagrid = $('#datagrid_saleStockListGoal').datagrid(datagridOptions_saleStockListGoal);    

    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_saleStock').form('reset');
        $('#addForm_saleStock').find('[__actType]').val('add');
        $('#addDlg_saleStock').dialog({'title': '新增'});
        $('#addDlg_saleStock').dialog('open');
//        $('#channelcode').textbox('textbox').attr('disabled',false); 
        $("#productCode").textbox('textbox').attr('maxlength',60);
        $("#quantity").textbox('textbox').attr('maxlength',10);
        $("#lockQuantity").textbox('textbox').attr('maxlength',10);
		Test();//-----------------------------------------------------------仓库类型改变时
    });
    
    $("#addDlgSaveBtn_warehouse").on('click', function () {
        var channelId = $("#channelId").combobox("getValue");
        var warehouseCode = $("#warehouseCode").val();

        if (channelId == "" || channelId == null) {
            $.messager.alert('提示', '渠道编码不能为空');
            return;
        }

        if (warehouseCode == "" || warehouseCode == null) {
            $.messager.alert('提示', '配送仓库不能为空');
            return;
        }

    	if (!$('#addForm_saleStock').form('validate')) {
    		return;
    	}
    	$('#addForm_saleStock').form('submit', {
    		url: '/warehouse/addSaleStock',
    		onSubmit: function(){
    			if($("#addForm_saleStock").form("validate"))  
                    return true  
                else  
                    return false;   			
    		},
    		success: function(data){
    			var actType = $('#addForm_saleStock').find('[__actType]').val();
    			var obj = data;
    			 if(obj=="codeIsSame"){
    				 $.messager.alert('提示', '商品编码已存在');
                 }
    			if(obj=="success"){   
                    $('#datagrid_saleStockListGoal').datagrid('reload');  
                    $("#addForm_saleStock").form("clear");
        			if (actType === 'add') {
//        				alert('新增成功');
        				$.messager.alert('提示', '新增成功');
        			} else {
        				$.messager.alert('提示','修改成功');
        			}
        			$('#addDlg_saleStock').dialog('close'); 
                }    			    			   			
    		}
    	});    	    	    	   	
    });
    $("#editBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_saleStock').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_saleStock').form('load', selected);
            $('#addForm_saleStock').find('[__actType]').val('edit');
            $('#addDlg_saleStock').dialog('open');
        } else {
        	$.messager.alert('提示','请选择一条数据');
        }
        var actType = $('#addForm_saleStock').find('[__actType]').val();
//        alert(actType);
        if (actType === 'edit') {
//        	$('#channelcode').textbox('textbox').attr('disabled',true); 
        }
        $("#productCode").textbox('textbox').attr('maxlength',60);
        $("#quantity").textbox('textbox').attr('maxlength',10);
        $("#lockQuantity").textbox('textbox').attr('maxlength',10); 
    });
    
    $("#addDlgSaveBtn_warehouse").on('click', function () {
    	
    });
    
    /**
     * 删除渠道
     */    
    $("#deleteBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
        	var id = selected.id;
        	console.log("2222"+id);
            confirm('确定删除？', function (r) {
            	if(r==true){
            		$.post("/warehouse/removeSaleStock",{id:id},function(data){
            			if(data.text="success"){
            				$.messager.alert('提示',"删除成功");
            				$('#datagrid_saleStockListGoal').datagrid('reload');             			
            			}
            		});           	             
            	}
            });
        } else {
        	$.messager.alert('提示','请选择一条数据');
        }
    });
    
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $("#warehouseid").empty();
        $.post("/warehouse/autoLoadPid", {channelId: "", id: ""}, function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#warehouseid").append("<option value=" + data[i].id + ">" + data[i].warehouseName + "</option>");
            }
        });
        $('#paramForm_orderForecastGoal').form('reset');
    });
});

//点击查询
$("#searchBtn").on('click', function (event) {
    var sourceData = $("#source").val();
    var productCodeData = $("#productcode").textbox("getValue");
    var warehouseCodeData = $("#warehousecode").val();

    datagrid = $('#datagrid_saleStockListGoal').datagrid({
        url: "/warehouse/findSaleStockList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50,100,200],
        queryParams: {
            channelId: sourceData,
            productCode: productCodeData,
            warehouseCode: warehouseCodeData
        },
        frozenColumns: [[{
        	 field: 'id',
             checkbox: false,
             hidden:true
        }]],
        columns: [[
            {title: '渠道名称 ', field: 'channelName', sortable: false},
            {title: '商品编码', field: 'productCode', sortable: false},
            {title: '配送仓库', field: 'warehouseName', sortable: false},
            {title: '商品数量', field: 'quantity', sortable: false,align:'right'},
            {title: '锁库存数量', field: 'lockQuantity', sortable: false,align:'right'},
            {title: '创建者', field: 'createBy', sortable: false},
            {title: '创建时间', field: 'cTime', sortable: false},
//        {title: '更新者', field: 'updateby', sortable: false},
//        {title: '更新时间', field: 'uTime', sortable: false},
            {title: '备注', field: 'remark', sortable: false}
        ]],
    });
});

$("#channelId").combobox({
	onChange: function (n,o) {
		Test();//-----------------------------------------------------------仓库类型改变时
	}
});
function Test() {
	$("#warehouseCode").empty();
	var channel_code = $("#channelId").combobox("getValue");
	var id = $("#id").val();
	console.info("id="+channel_code);
	$.post("/warehouse/autoLoadPid",{channelId:channel_code,id:""},function(data){
		for (var i = 0; i < data.length; i++) {
			$("#warehouseCode").append("<option value="+data[i].warehouseCode+">" + data[i].warehouseName + "</option>");
		}
	})
};
function auto1() {
    Test1();
};
function Test1() {
    $("#warehousecode").empty();
    var channel_code = $("#source").val();
    if (channel_code == null || channel_code == "") {
        $.post("/warehouse/autoLoadPid", {channelId: "", id: ""}, function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#warehousecode").append("<option value=" + data[i].warehouseCode + ">" + data[i].warehouseName + "</option>");
            }
        });
    } else {
        var id = $("#id").val();
        console.info("id=" + id);
        $.post("/warehouse/autoLoadPid", {channelId: channel_code, id: ""}, function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#warehousecode").append("<option value=" + data[i].warehouseCode + ">" + data[i].warehouseName + "</option>");
            }
        });
    }
};