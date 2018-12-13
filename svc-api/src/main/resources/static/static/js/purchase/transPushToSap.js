var datagrid;
var queryParameters;
var LEVEL1_QJ={};
$(function () {
    datagrid = {
        fit: true,
        fitColumns: false,
        toolbar: '#datagridToolbar',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            {
                field: 'check',
                title: '全选',
                width: 100,
                align: 'center',
                checkbox:true
            },{
                field: 'id',
                hidden:'true'
            },
            {
                field: 'foreignKey',
                title: '订单号',
                width: 150,
                align: 'center'
            },
            {
                field: 'requestData',
                title: '推送数据',
                width: 300,
                align: 'center'
            },
            {
                field: 'responseDatas',
                title: '返回数据',
                width: 300,
                align: 'center'
            },
            {
                field: 'status',
                title: '状态',
                width: 300,
                align: 'center'
            },
            {
                field: 'requestTime',
                title: '推送时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'zlsin',
                title: 'LBX',
                width: 200,
                align: 'center'
            },
            {
                field: 'matnr',
                title: '物料编码',
                width: 200,
                align: 'center'
            },
            {
                field: 'menge',
                title: '数量',
                width: 200,
                align: 'center'
            },
            {
                field: 'zlgorto',
                title: '调出库位',
                width: 200,
                align: 'center'
            },
            {
                field: 'zlgorti',
                title: '调入库位',
                width: 200,
                align: 'center'
            }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);

});

function formatLongText(text){
    return '<div style="height:15px;padding:0px;text-align:left;" onmouseover="$(this).height(\'auto\');" onmouseleave="$(this).height(\'15px\');"><code>' + text + '</code></div>';
}



//点击导出
$('#export').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    var exportData = new Array();
    //将退货单号存入Array
    $.each(checkedItems, function(index, item){
        exportData.push(item.id);
    });
    $("#exportData").val(JSON.stringify(exportData));
    //判断是否都没选中
    if(exportData==null||exportData.length==0){
        $.messager.alert('错误','请至少选择一行要导出的行！','error');
        return;
    }
    $.messager.confirm('确认', '确定要导出吗？', function(r){
        if (r){
            $('#filterForm').attr("action", '/transPushToSap/exportPushToSap');
            $('#filterForm').submit();
        }
    });
});

//点击全部导出export
$('#exportall').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    if(window.total && window.total > 20000){
        $.messager.alert('提示', '最多导出20000条记录，请缩小查询范围！', 'info');
        return;
    }
    //提交日开始保存
    $("#startDate_save").val($("#startDate").datebox('getValue'));
    //提交日结束保存
    $("#endDate_save").val($("#endDate").datebox('getValue'));
    $("#foreignKey_save").val($("#foreignKey").val());
    var status = $("#status").combobox("getValue");

    $("#status_save").val(status);

    $.messager.confirm('确认','确定要全部导出吗？', function(r){
        if (r){
            $('#filterForm').attr("action", '/transPushToSap/exportPushToSapList');
            $('#filterForm').submit();
        }
    });
});

//点击查询
$('#search').click(function () {
    //提交日开始保存
    $("#startDate_save").val($("#startDate").datebox('getValue'));
    //提交日结束保存
    $("#endDate_save").val($("#endDate").datebox('getValue'));
    $("#foreignKey_save").val($("#foreignKey").val());
    var status = $("#status").combobox("getValue");

    $("#status_save").val(status);

    //生成grid
    datagrid = $('#datagrid').datagrid({
        url : "/transPushToSap/findTransPushToSapList",
        fit:true,
        pagination: true,
        pageSize:100,
        pageList:[100,200,300],
        nowrap: false,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        rownumbers: true,
        queryParams: {
            foreignKey:$("#foreignKey_save").val(),
            status:status,
            startDate:$("#startDate_save").val(),
            endDate:$("#endDate_save").val(),

        },
        columns : [ [
            {
                field: 'check',
                title: '全选',
                width: 100,
                align: 'center',
                checkbox:true
            },
            {
                field: 'id',
                hidden:'true'
            },
            {
                field: 'foreignKey',
                title: '订单号',
                width: 150,
                align: 'center'
            },
            {
                field: 'requestData',
                title: '推送数据',
                width: 300,
                align: 'center',
                formatter: formatLongText
            },
            {
                field: 'responseDatas',
                title: '返回数据',
                width: 300,
                align: 'center',
                formatter: formatLongText
            },
            {
                field: 'status',
                title: '状态',
                width: 120,
                align: 'center',
                formatter : function(value) {
                    if(value == 1){
                        return "成功";
                    }
                    if(value == 0){
                        return "未推送";
                    }
                    if(value == 2){
                        return "推送失败";
                    }
                }
            },
            {
                field: 'requestTime',
                title: '推送时间',
                width: 200,
                align: 'center',
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


                }
            },
            {
                field: 'zlsin',
                title: 'LBX',
                width: 200,
                align: 'center'
            },
            {
                field: 'matnr',
                title: '物料编码',
                width: 200,
                align: 'center'
            },
            {
                field: 'menge',
                title: '数量',
                width: 200,
                align: 'center'
            },
            {
                field: 'zlgorto',
                title: '调出库位',
                width: 200,
                align: 'center'
            },
            {
                field: 'zlgorti',
                title: '调入库位',
                width: 200,
                align: 'center'
            }
        ] ],
        toolbar: '#datagridToolbar'
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);

});

