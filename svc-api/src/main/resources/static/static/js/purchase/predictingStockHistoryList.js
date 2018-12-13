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
                field: 'report_year_week',
                title: '提报周',
                width: 80,
                align: 'center'
            },
            {
                field: 'ed_channel_name',
                title: '渠道',
                width: 80,
                align: 'center'
            },
            {
                field: 'product_group_name',
                title: '产品组',
                width: 80,
                align: 'center'
            },
            {
                field: 'materials_id',
                title: '物料编号',
                width: 100,
                align: 'center'
            },
            {
                field: 'brand_name',
                title: '品牌',
                width: 100,
                align: 'center'
            },
            {
                field: 'material_description',
                title: '型号',
                width: 300,
                align: 'center'
            },
            {
                field: 't3_require_prediction',
                title: 'T+3周',
                width: 70,
                align: 'center'
            },
            {
                field: 't4_require_prediction',
                title: 'T+4周',
                width: 70,
                align: 'center'
            },
            {
                field: 't5_require_prediction',
                title: 'T+5周',
                width: 70,
                align: 'center'
            },
            {
                field: 't6_require_prediction',
                title: 'T+6周',
                width: 70,
                align: 'center'
            },
            {
                field: 't7_require_prediction',
                title: 'T+7周',
                width: 70,
                align: 'center'
            },
            {
                field: 't8_require_prediction',
                title: 'T+8周',
                width: 70,
                align: 'center'
            },
            {
                field: 't9_require_prediction',
                title: 'T+9周',
                width: 70,
                align: 'center'
            },
            {
                field: 't10_require_prediction',
                title: 'T+10周',
                width: 70,
                align: 'center'
            },
            {
                field: 't11_require_prediction',
                title: 'T+11周',
                width: 70,
                align: 'center'
            },
            {
                field: 't12_require_prediction',
                title: 'T+12周',
                width: 70,
                align: 'center'
            },
            {
                field: 't13_require_prediction',
                title: 'T+13周',
                width: 70,
                align: 'center'
            },
            {
                field: 'flow_flag_name',
                title: '状态',
                width: 80,
                align: 'center'
            },
            {
                field: 'create_time_display',
                title: '填报时间',
                width: 150,
                align: 'center'
            },
            {
                field: 'create_user',
                title: '填报人',
                width: 100,
                align: 'center'
            },
            {
                field: 'audit_time_display',
                title: '提交时间',
                width: 150,
                align: 'center'
            },
            {
                field: 'audit_user',
                title: '提交人',
                width: 100,
                align: 'center'
            },
            {
                field: 'error_msg',
                title: '失败信息',
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


$(function () {


    //取得产品组列表
    jQuery.getJSON("/purchase/getProductGroupByAuth", function(result){
        var productgroup = result.data;
        //添加全部
        productgroup.unshift( {value:'ALL',valueMeaning:'全部'});
        $("#product_group").combobox({
            data:productgroup,
            valueField:'value',
            textField:'valueMeaning',
            panelHeight:'auto',
            editable:false,
            value:'ALL',
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

    //取得流程状态列表
    var valueSetId = "predict_stock_flow_flag";
    jQuery.getJSON("/purchase/getByValueSetId"+ "?valueSetId=" + valueSetId, function(result){
        var count = result.totalCount;
        var dataList = result.data;
        //添加全部
        dataList.unshift( {value:'ALL',value_meaning:'全部'});
        $("#flow_flag").combobox({
            data:dataList,
            valueField:'value',
            textField:'value_meaning',
            panelHeight:'auto',
            editable:false,
            value:'ALL'
        });
    });

//取得渠道列表
    jQuery.getJSON("/purchase/getChannelsByAuth", function(result){
        var channel = result.data;
        //添加全部
        channel.unshift( {channelCode:'ALL',name:'全部'});
        $("#channel").combobox({
            data:channel,
            valueField:'channelCode',
            textField:'name',
            panelHeight:'auto',
            editable:false,
            value:'ALL',
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

});

var dateweek_start;
var dateweek_end;

//点击查询
$('#search').click(function () {
    $('#product_group_id_save').val($('#product_group').combobox("getValue"));
    $('#ed_channel_id_save').val($('#channel').combobox("getValue"));
    $('#flow_flag_save').val($("#flow_flag").combobox("getValues").join(","));

    if(dateweek_start && dateweek_end){
        dateweek_start = dateweek_start.replace("年", "");
        dateweek_start = dateweek_start.replace("周", "");
        dateweek_end = dateweek_end.replace("年", "");
        dateweek_end = dateweek_end.replace("周", "");
        if(dateweek_start > dateweek_end){
            $.messager.alert('错误','开始周大于结束周，请重新选择!','error');
            return false;
        }
    }
    $('#start_week_save').val(dateweek_start);
    $('#end_week_save').val(dateweek_end);
    $("#start_week_hidden").val($("#start_week_save").val().replace("年", "").replace("周", ""));
    $("#end_week_hidden").val($("#end_week_save").val().replace("年", "").replace("周", ""));
    $("#product_group_id_hidden").val($("#product_group_id_save").val());
    $("#ed_channel_id_hidden").val($("#ed_channel_id_save").val());
    $("#flow_flag_hidden").val($("#flow_flag_save").val());

    if($("#product_group_id_hidden").val()=="ALL"){
        $("#product_group_id_hidden").val("");
    }
    if($("#ed_channel_id_hidden").val()=="ALL"){
        $("#ed_channel_id_hidden").val("");
    }

    if ($("#start_week_hidden").val() && $("#end_week_hidden").val()) {
    }
    else {
        if ($("#start_week_hidden").val()) {
            $("#end_week_hidden").val($("#start_week_hidden").val());
        } else {
            $("#start_week_hidden").val($("#end_week_hidden").val());
        }
    }
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/purchase/findPredictingStockDetailList",
        fit: true,
        fitColumns: false,
        pagination: true,
        singleSelect: true,
        pageSize: 100,
        pageList: [100,200,300],
        nowrap: false,
        rownumbers: true,
        singleSelect: false,
        loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        selectOnCheck: true,
        checkOnSelect: true,
        queryParams: {
            start_week: $("#start_week_hidden").val(),
            end_week: $("#end_week_hidden").val(),
            product_group_id: $("#product_group_id_hidden").val(),
            ed_channel_id: $("#ed_channel_id_hidden").val(),
            flow_flag: $("#flow_flag_hidden").val()
        },
        columns: [
            [
                {
                    field: 'report_year_week',
                    title: '提报周',
                    width: 80,
                    align: 'center'
                },
                {
                    field: 'ed_channel_name',
                    title: '渠道',
                    width: 80,
                    align: 'center'
                },
                {
                    field: 'product_group_name',
                    title: '产品组',
                    width: 80,
                    align: 'center'
                },
                {
                    field: 'materials_id',
                    title: '物料编号',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'brand_name',
                    title: '品牌',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'material_description',
                    title: '型号',
                    width: 300,
                    align: 'center'
                },
                {
                    field: 't3_require_prediction',
                    title: 'T+3周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't4_require_prediction',
                    title: 'T+4周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't5_require_prediction',
                    title: 'T+5周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't6_require_prediction',
                    title: 'T+6周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't7_require_prediction',
                    title: 'T+7周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't8_require_prediction',
                    title: 'T+8周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't9_require_prediction',
                    title: 'T+9周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't10_require_prediction',
                    title: 'T+10周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't11_require_prediction',
                    title: 'T+11周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't12_require_prediction',
                    title: 'T+12周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 't13_require_prediction',
                    title: 'T+13周',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 'flow_flag_name',
                    title: '状态',
                    width: 80,
                    align: 'center'
                },
                {
                    field: 'create_time_display',
                    title: '填报时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'create_user',
                    title: '填报人',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'audit_time_display',
                    title: '提交时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'audit_user',
                    title: '提交人',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'error_msg',
                    title: '失败信息',
                    width: 200,
                    align: 'center'
                }
            ]
        ]
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);

});

//点击全部导出export
$('#exportall').click(function(){
    if(window.total && window.total > 20000){
        $.messager.alert('提示', '最多导出20000条记录，请缩小查询范围！', 'info');
        return;
    }
    $.messager.confirm('确认','确定要全部导出吗？', function(r){
        if (r){
            $('#filterForm').attr("action", '/purchase/exportPredictStockDetailList');
            $('#filterForm').submit();
        }
    });
});
