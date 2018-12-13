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
                field: 'id',
                title: '序号',
                width: 10,
                align: 'center',
                hidden: true
            },
            {
                field: 'report_year_week',
                title: '提报周yyyyww',
                width: 80,
                align: 'center',
                hidden: true
            },
            {
                field: 'report_year_week_display',
                title: '提报周',
                width: 80,
                align: 'center'
            },
            {
                field: 'product_group_id',
                title: '产品组id',
                width: 10,
                align: 'center',
                hidden: true
            },
            {
                field: 'product_group_name',
                title: '产品组',
                width: 160,
                align: 'center'
            },
            {
                field: 'ed_channel_id',
                title: '渠道id',
                width: 10,
                align: 'center',
                hidden: true
            },
            {
                field: 'ed_channel_name',
                title: '渠道',
                width: 160,
                align: 'center'
            },
            {
                field: 'not_commit_count',
                title: '未提交',
                width: 100,
                align: 'center'
            },
            {
                field: 'commit_count',
                title: '已提交',
                width: 100,
                align: 'center'
            },
            {
                field: 'failure_count',
                title: '提交失败',
                width: 100,
                align: 'center'
            },
            {
                field: 'operate',
                title: '操作',
                width: 100,
                align: 'center'
            }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);

});


//点击下载全部
$('#downloadall').click(function () {
    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
    if(window.total && window.total > 20000){
        $.messager.alert('提示', '最多导出20000条记录，请缩小查询范围！', 'info');
        return;
    }
    //提报周
    var report_year_week = $("#report_year_week").val().replace("年", "").replace("周", "");
    //产品组
    var product_group_id_save = $("#product_group_id_save").val();
    //渠道
    var ed_channel_id_save = $("#ed_channel_id_save").val();
    //调用下载全部
    onExport(report_year_week, ed_channel_id_save, product_group_id_save);
});
//点击导入
$('#import').click(function () {
    var report_year_week = $("#report_year_week").val().replace("年", "").replace("周", "");
    var url = '/purchase/importPredictingStock.html' + '?report_year_week=' + report_year_week;
    window.location.href = url;
    return false;
})
//点击下载模板
$("#downloadtmp").click(function () {
    $('#filterForm').attr("action", '/purchase/exportT3PredictModel.html');
    $('#filterForm').submit();
});
//列表内点击查看
function onShowDetail(param_report_year_week, param_channel, param_product_group) {
    var url = '/purchase/gotoQueryDetail.html' + '?report_year_week=' + param_report_year_week
        + '&ed_channel_id=' + param_channel + '&product_group_id=' + param_product_group
        + '&create_user=' + $("#create_user_save").val();
    location.href = url;
    return false;
}
//列表内点击下载
function onExport(param_report_year_week, param_channel, param_product_group) {
    $.messager.confirm('确认', '确定要下载吗？', function (r) {
        if (r) {
            //提报周数据设置
            $("#report_year_week_hidden").val(param_report_year_week);
            //产品组数据设置
            $("#product_group_id_hidden").val(param_product_group);
            //渠道数据设置
            $("#ed_channel_id_hidden").val(param_channel);
            //提报人数据设置
            $("#create_user_hidden").val($("#create_user_save").val());
            $('#filterForm').attr("action", '/purchase/exportPurchasePredictStockDetailList');
            $('#filterForm').submit();
        }
    });
}

$(function () {

    //取得渠道列表
    jQuery.getJSON("/purchase/getChannelsByAuth", function (result) {
        var channel = result.data;
        //添加全部
        channel.unshift({channelCode: 'ALL', name: '全部'});
        $("#channel").combobox({
            data: channel,
            valueField: 'channelCode',
            textField: 'name',
            panelHeight: 'auto',
            editable: false,
            value: 'ALL',
            loadFilter: function(data){
                var datas = [];
                $.each(data, function(k, v){
                    if($.inArray(v.channelCode, authMap.channel) > -1 || v.channelCode == 'ALL'){
                        datas.push(v);
                    }
                });
                return datas;
            }
        });
    });
    //取得产品组列表
    jQuery.getJSON("/purchase/getProductGroupByAuth", function (result) {
        var productgroup = result.data;
        //添加全部
        productgroup.unshift({value: 'ALL', valueMeaning: '全部'});
        $("#product_group").combobox({
            data: productgroup,
            valueField: 'value',
            textField: 'valueMeaning',
            panelHeight: 'auto',
            editable: false,
            value: 'ALL',
            loadFilter: function(data){
                var datas = [];
                $.each(data, function(k, v){
                    if($.inArray(v.value, authMap.productGroup) > -1 || v.value == 'ALL'){
                        datas.push(v);
                    }
                });
                return datas;
            }
        });
    });
});

//点击查询
$('#search').click(function () {

    var product_group = $("#product_group").combobox("getValue");
    //如果是ALL，产品组设为空
    if (product_group == "ALL") {
        product_group = "";
    }
    var channel = $("#channel").combobox("getValue");
    //如果是ALL，渠道设为空
    if (channel == "ALL") {
        channel = "";
    }
    //产品组保存
    $("#product_group_id_save").val(product_group);
    //渠道保存
    $("#ed_channel_id_save").val(channel);
    //填报人保存
    $("#create_user_save").val($("#create_user").val());

    //生成grid
    datagrid = $('#dataGrid').datagrid({
        url: "/purchase/findPredictingStockList",
        fit: true,
        fitColumns: true,
        pagination: true,
        singleSelect: true,
        pageSize: 100,
        pageList: [100, 200, 300],
        nowrap: true,
        loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        rownumbers: true,
        queryParams: {
            report_year_week: $("#report_year_week").val(),
            channel: channel,
            product_group: product_group,
            create_user: $("#create_user").val()
        },
        columns: [
            [
                {
                    field: 'id',
                    title: '序号',
                    width: 10,
                    align: 'center',
                    hidden: true
                },
                {
                    field: 'report_year_week',
                    title: '提报周yyyyww',
                    width: 80,
                    align: 'center',
                    hidden: true
                },
                {
                    field: 'report_year_week_display',
                    title: '提报周',
                    width: 160,
                    align: 'center'
                },
                {
                    field: 'product_group_id',
                    title: '产品组id',
                    width: 10,
                    align: 'center',
                    hidden: true
                },
                {
                    field: 'product_group_name',
                    title: '产品组',
                    width: 160,
                    align: 'center'
                },
                {
                    field: 'ed_channel_id',
                    title: '渠道id',
                    width: 10,
                    align: 'center',
                    hidden: true
                },
                {
                    field: 'ed_channel_name',
                    title: '渠道',
                    width: 160,
                    align: 'center'
                },
                {
                    field: 'not_commit_count',
                    title: '未提交',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'commit_count',
                    title: '已提交',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'failure_count',
                    title: '提交失败',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'operate',
                    title: '操作',
                    width: 100,
                    align: 'center',
                    formatter: function (value, rowData, rowIndex) {
                        //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
                        return "<a href='javascript:void(0);' onclick='onExport(" + '"'
                            + rowData.report_year_week + '","' + rowData.ed_channel_id + '","'
                            + rowData.product_group_id + '"'
                            + ");return false;'>下载</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='onShowDetail("
                            + '"' + rowData.report_year_week + '","' + rowData.ed_channel_id + '","'
                            + rowData.product_group_id + '"' + ");return false;'>查看</a>";
                    }
                }
            ]
        ]
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);

});

