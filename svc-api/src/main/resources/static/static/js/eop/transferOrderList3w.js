
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
            {title: "单据号", field: "transferOrderCode"},
            {title: "物料编码", field: "scItemCode"},
            {title: "出仓库编码", field: "fromStoreCode"},
            {title: "入仓库编码", field: "toStoreCode"},
            {title: "实际出库数量", field: "outCount"},
            {title: "入库正品数量", field: "inCount"},
            {title: "入库残品数量", field: "remnantNum"},
            {title: "状态", field: "orderStatus"},
            {title: "出库单LBX号", field: "transferOutOrderCode"},
            {title: "入库单LBX号", field: "transferInOrderCode"},
            {title: "外部ERP单号", field: "orderCode"},
            {title: "出库确认时间", field: "confirmOutTime"},
            {title: "入库确认时间", field: "confirmInTime"},
            {title: "创建人", field: "creater"},
            {title: "创建时间", field: "createTime"},
            {title: "SAP错误信息", field: "sapErrorMessage"},
            {title: "操作", field: "operation"}
        ]],
        pageSize: 50,
        pageList: [50,100,200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);
});

//点击查询
$('#search').click(function () {
    if($('#startTime').datetimebox('getValue') && $('#endTime').datetimebox('getValue')){
        if($('#startTime').datetimebox('getValue')>$('#endTime').datetimebox('getValue')){
            $.messager.alert('错误','创建时间区间不正确','error');
            return false;
        }
    }
    queryParameters ={
        transferOrderCode:$("#transferOrderCode").val(),
        transferOutOrderCode:$("#transferOutOrderCode").val(),
        transferInOrderCode:$('#transferInOrderCode').val(),
        scItemCode:$("#scItemCode").val(),
        startTime:$('#startTime').datetimebox('getValue'),
        endTime:$('#endTime').datetimebox('getValue'),
        orderStatus:$("#orderStatus").combobox("getValue"),
        difference:$("#difference").combobox("getValue"),
        fromStoreCode:$("#fromStoreCode").val(),
        toStoreCode:$("#toStoreCode").val()
    };
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/transferorder3w/transfer_order_query",
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
                    field: 'transferOrderCode',
                    title: '单据号',
                    align: 'center'
                },
                {
                    field: 'scItemCode',
                    title: '物料编码',
                    align: 'center'
                },
                {
                    field: 'fromStoreCode',
                    title: '出仓库编码',
                    align: 'center'
                },
                {
                    field: 'toStoreCode',
                    title: '入仓库编码',
                    align: 'center'
                },
                {
                    field: 'outCount',
                    title: '实际出库数量',
                    align: 'center'
                },
                {
                    field: 'inCount',
                    title: '入库正品数量',
                    align: 'center'
                },
                {
                    field: 'remnantNum',
                    title: '入库残品数量',
                    align: 'center'
                },
                {
                    field:'orderStatus',
                    title:'状态',
                    align:'left',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='0') return '调拨单申请中';
                        if(value=='30') return '全部出库';
                        if(value=='140') return '全部入库';
                        if(value=='150') return '调拨完成,出库推送SAP';
                        if(value=='100') return '已推送SAP,关闭';
                        if(value=='101') return '同仓调拨,关闭';
                        if(value=='110') return '需人工介入';
                        if(value=='120') return '创建未请求菜鸟接口';
                        if(value=='-100') return '已取消';
                        return value;
                    }
                },
                {
                    field: 'transferOutOrderCode',
                    title: '出库单LBX号',
                    align: 'center'
                },
                {
                    field: 'transferInOrderCode',
                    title: '入库单LBX号',
                    align: 'center'
                },
                {
                    field: 'confirmOutTime',
                    title: '出库确认时间',
                    align: 'center'
                },
                {
                    field:'confirmInTime',
                    title:'入库确认时间',
                    align:'center'
                },
                {
                    field:'creater',
                    title:'创建人',
                    align:'center'
                },
                {
                    field:'createTime',
                    title:'创建时间',
                    align:'center'
                },
                {
                    field:'sapErrorMessage',
                    title:'SAP错误信息',
                    align:'center'
                },
                {
                    field: 'operation',
                    title: ' 操作',
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(rowData.orderStatus==110 || rowData.orderStatus == 30){
                            return "<a href='javascript:void(0);' onclick='syncOrder("+'"'+rowData.id+'","'+rowData.transferOrderCode+'"'+");return false;'>同步</a>";
                        }else{
                            return "";
                        }
                    }
                }
            ]
        ],
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
});

$('#syncAll').click(function(){
    jQuery.ajax({
        url: "/transferorder3w/transfer_order_syncall",
        data:{
        },
        type: "POST",
        success: function (data) {
            $.messager.alert('信息','开始同步调拨单信息','info');
        },
        error: function (data){
            $.messager.alert('错误','同步调拨单信息发生异常，请稍后再试！','error');
        }
    });

});

function syncOrder(id, transferOrderCode){
    jQuery.ajax({
        url: "/transferorder3w/transfer_order_sync",
        data:{
            id: id,
            transferOrderCode: transferOrderCode
        },
        type: "POST",
        success: function (data) {
            $.messager.alert('信息','开始同步调拨单信息','info');
        },
        error: function (data){
            $.messager.alert('错误','同步调拨单信息发生异常，请稍后再试！','error');
        }
    });
}
    //日期控件
var buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
    buttons.splice(1, 0, {
        text: '清除',
        handler: function(target){
            $(target).datetimebox('setValue', '');
            $(target).combo("hidePanel");
        }
    });
    $('#startTime').datetimebox({ buttons: buttons});
    $('#endTime').datetimebox({ buttons: buttons});


$('#export').click(function(){

    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    var syncData = new Array();
    //判断是否存在满足条件的行
    $.each(checkedItems, function(index, item){
        syncData.push(item.id);
    });
    $.messager.confirm('确认','确定要导出吗？', function(r){
        if (r){
            $('#exportData').val(JSON.stringify(queryParameters));
            $('#paramForm_dmmtlPbcsMtlMeasure').attr("action", '/transferorder3w/export_transfer_order');
            $('#paramForm_dmmtlPbcsMtlMeasure').submit();
        }
    });
});