
var datagrid;
var queryParameters;

$(function () {
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            {title: "序号", field: "id", hidden: true},
            {title: "补货单号", field: "replNo", sortable: true},
            {title: "货品ID", field: "scItemId",sortable: true},
            {title: "货品编码", field: "scItemCode", sortable: true},
            {title: "货品名称", field: "scItemName", sortable: false},
            {title: "处理状态", field: "state", sortable: false},
            {title: "计划补货数量", field: "planReplQty",sortable: true},
            {title: "建议补货数量", field: "suggestQty",sortable: true},
            {title: "确认补货数量", field: "confirmReplQty",sortable: true},
            {title: "基地仓编码", field: "fromStoreCode", sortable: false},
            {title: "菜鸟仓编码", field: "storeCode", sortable: false},
            {title: "菜鸟仓名称", field: "storeName", sortable: false},
            {title: "创建时间", field: "gmtCreate", sortable: false},
            {title: "修改时间", field: "gmtModified", sortable: false},
            {title: "最晚入库时间", field: "deadLine", sortable: false},
            {title: "确认入库时间", field: "confirmDeadLine", sortable: false},
            {title: "85单号", field: "entryOrderCode", sortable: false},
            {title: "LBX号", field: "entryOrderId", sortable: false},
            {title: "联系人", field: "contactName", sortable: true},
            {title: "联系电话", field: "contactPhone", sortable: true},
            {title: "错误信息", field: "errorMsg", sortable: false}
        ]],
        pageSize: 50,
        pageList: [50,100,200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);
});

//点击查询
$('#search').click(function () {
    if($('#gmtCreateMin').datetimebox('getValue') && $('#gmtCreateMax').datetimebox('getValue')){
        if($('#gmtCreateMin').datetimebox('getValue')>$('#gmtCreateMax').datetimebox('getValue')){
            $.messager.alert('错误','创建时间区间不正确','error');
            return false;
        }
    }
    if($('#gmtModifiedMin').datetimebox('getValue') && $('#gmtModifiedMax').datetimebox('getValue')){
        if($('#gmtModifiedMin').datetimebox('getValue')>$('#gmtModifiedMax').datetimebox('getValue')){
            $.messager.alert('错误','修改时间区间不正确','error');
            return false;
        }
    }
    if($('#deadLineMin').datetimebox('getValue') && $('#deadLineMax').datetimebox('getValue')){
        if($('#deadLineMin').datetimebox('getValue')>$('#deadLineMax').datetimebox('getValue')){
            $.messager.alert('错误','最晚入库时间区间不正确','error');
            return false;
        }
    }
    if($('#confirmDeadLineMin').datetimebox('getValue') && $('#confirmDeadLineMax').datetimebox('getValue')){
        if($('#confirmDeadLineMin').datetimebox('getValue')>$('#confirmDeadLineMax').datetimebox('getValue')){
            $.messager.alert('错误','确认入库时间区间不正确','error');
            return false;
        }
    }
    queryParameters ={
        replNo:$("#replNo").val(),
        scItemId:$("#scItemId").val(),
        scItemCode:$('#scItemCode').val(),
        scItemName:$("#scItemName").val(),
        entryOrderCode:$("#entryOrderCode").val(),
        entryOrderId:$("#entryOrderId").val(),
        gmtCreateMin:$('#gmtCreateMin').datetimebox('getValue'),
        gmtCreateMax:$('#gmtCreateMax').datetimebox('getValue'),
        gmtModifiedMin:$('#gmtModifiedMin').datetimebox('getValue'),
        gmtModifiedMax:$('#gmtModifiedMax').datetimebox('getValue'),
        deadLineMin:$('#deadLineMin').datetimebox('getValue'),
        deadLineMax:$('#deadLineMax').datetimebox('getValue'),
        confirmDeadLineMin:$('#confirmDeadLineMin').datetimebox('getValue'),
        confirmDeadLineMax:$('#confirmDeadLineMax').datetimebox('getValue'),
        fromStoreCode:$("#fromStoreCode").val(),
        storeCode:$("#storeCode").val(),
        storeName:$("#storeName").val(),
        state:$("#state").combobox("getValue"),
    };
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/cnreplenishment/findOrderList",
        fit: true,
        fitColumns: true,
        pagination: true,
        singleSelect: false,
        checkOnSelect:true,
        pageSize: 50,
        pageList: [50,100,200],
        nowrap: true,
        rownumbers: true,
        queryParams: queryParameters,
        columns: [
            [
                {
                    field: 'id',
                    title: '序号',
                    align: 'center',
                    hidden:true
                },
                {
                    field: 'replNo',
                    title: '补货单号',
                    sortable: true,
                    align: 'center'
                },
                {
                    field: 'scItemId',
                    title: '货品ID',
                    sortable: true,
                    align: 'center'
                },
                {
                    field: 'scItemCode',
                    title: '货品编码',
                    sortable: true,
                    align: 'center'
                },
                {
                    field: 'scItemName',
                    title: '货品名称',
                    sortable: false,
                    align: 'center'
                },
                {
                    field:'state',
                    title:'处理状态',
                    sortable: false,
                    align:'left',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='1') return '初始状态';
                        if(value=='2') return '生成85单号';
                        if(value=='3') return '已推送85单号';
                        if(value=='4') return '已获取lbx号';
                        if(value=='10') return '正在补货';
                        if(value=='50') return '完成补货';
                        if(value=='11') return '取消补货';
                        if(value=='12') return '补货异常';
                        return value;
                    }
                },
                {
                    field: 'planReplQty',
                    title: '计划补货数量',
                    sortable: true,
                    align: 'center'
                },
                {
                    field:'suggestQty',
                    title:'建议补货数量',
                    sortable: true,
                    align:'center'
                },
                {
                    field:'confirmReplQty',
                    title:'确认补货数量',
                    sortable: true,
                    align:'center'
                },
                {
                    field:'fromStoreCode',
                    title:'基地仓编码',
                    sortable: true,
                    align:'center'
                },
                {
                    field:'storeCode',
                    title:'菜鸟仓编码',
                    sortable: true,
                    align:'center'
                },
                {
                    field:'storeName',
                    title:'菜鸟仓名称',
                    sortable: false,
                    align:'center'
                },
                {
                    field: 'gmtCreate',
                    title: '创建时间',
                    sortable: false,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return value;
                        }
                    }
                },
                {
                    field: 'gmtModified',
                    title: '修改时间',
                    sortable: false,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return value;
                        }
                    }
                },
                {
                    field: 'deadLine',
                    title: '最晚入库时间',
                    sortable: false,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return value;
                        }
                    }
                },
                {
                    field: 'confirmDeadLine',
                    title: '确认入库时间',
                    sortable: false,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return value;
                        }
                    }
                },
                {
                    field:'entryOrderCode',
                    title:'85单号',
                    sortable: false,
                    align:'center'
                },
                {
                    field:'entryOrderId',
                    title:'LBX号',
                    sortable: false,
                    align:'center'
                },
                {
                    field:'contactName',
                    title:'联系人',
                    sortable: true,
                    align:'center'
                },
                {
                    field:'contactPhone',
                    title:'联系电话',
                    sortable: true,
                    align:'center'
                },
                {
                    field: 'errorMsg',
                    title: '错误信息',
                    sortable: false,
                    align: 'left'
                }
            ]
        ],
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
});

function confirmOrder(id, replNo){
    $("#replOrderConfirmHtml").attr("style", "display:none;padding:5px;width:800px;height:400px;");
    if (replNo == undefined){
        replNo = "";
    }
    jQuery.ajax({
        url: "/cnreplenishment/replOrderDetail",
        data:{
            "id": id,
            "replNo": replNo
        },
        type: "GET",
        success: function (data) {
            $("#replOrderConfirmHtml").html(data);
            $("#replOrderConfirmHtml").show();
            $("#replOrderConfirmHtml").dialog({
                collapsible: true,
                minimizable: false,
                maximizable: false
            });
        }
    });
}