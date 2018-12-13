var datagrid;
var queryParameters;
var LEVEL1_QJ={};
var dateweek_start;
var dateweek_end;

var date_start;
var date_end;

function compareDate(startdate, enddate, replaceblag, message) {
    if(replaceblag){
        if(startdate){
            startdate = startdate.replace("年", "");
            startdate = startdate.replace("周", "");
        }
        if(enddate){
            enddate = enddate.replace("年", "");
            enddate = enddate.replace("周", "");
        }
    }
    if(startdate && enddate){
        if(startdate > enddate){
            //alert(message);
            $.messager.alert('错误',message,'error');
            return false;
        }
    }
    return true;
}

$(function () {
    // var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    // buttons.splice(1, 0, {
    //     text: '清除',
    //     handler: function(target){
    //         $(target).datebox('setValue', '');
    //         $('#'+$(target).attr('id')+'_txt').val('');console.log(111);
    //         $(target).combo("hidePanel");
    //     }
    // });
    // var buttonsStart = $.extend([], $.fn.datebox.defaults.buttons);
    // buttonsStart.splice(1, 0, {
    //     text: '清除',
    //     handler: function(target){
    //         $(target).datebox('setValue', '');
    //         $('#'+$(target).attr('id')+'_txt').val('');
    //         dateweek_start = '';console.log(222);
    //         date_start = '';
    //         $(target).combo("hidePanel");
    //     }
    // });
    // var buttonsEnd = $.extend([], $.fn.datebox.defaults.buttons);
    // buttonsEnd.splice(1, 0, {
    //     text: '清除',
    //     handler: function(target){
    //         $(target).datebox('setValue', '');
    //         $('#'+$(target).attr('id')+'_txt').val('');console.log(333);
    //         dateweek_end = '';
    //         date_end = '';
    //         $(target).combo("hidePanel");
    //     }
    // });
    // $('#report_year_week_start').datebox({
    //     buttons: buttonsStart
    // });
    // $('#report_year_week_end').datebox({
    //     buttons: buttonsEnd
    // });
    // $('#bill_time_start').datebox({
    //     buttons: buttons
    // });
    // $('#bill_time_end').datebox({
    //     buttons: buttons
    // });
    // $('#datestorge_start').datebox({
    //     buttons: buttons
    // });
    // $('#datestorge_end').datebox({
    //     buttons: buttons
    // });
    //接收当前周
    $("#report_year_week_start").datebox("setValue", "$!lastweek");
    $("#report_year_week_end").datebox("setValue", "$!currentweek");

    $("#report_start_week_hidden").val("$!lastweek");
    $("#report_end_week_hidden").val("$!currentweek");

    //取得流程状态列表
    var valueSetId = "flow_flag";
    jQuery.getJSON("/purchase/getByValueSetId"+ "?valueSetId=" + valueSetId, function(result){
        var count = result.totalCount;
        var dataList = result.data;
        //添加全部
        dataList.unshift( {value:'ALL',value_meaning:'全部'});
        $("#flow_flag").combobox({
            data:dataList,
            valueField:'value',
            textField:'value_meaning',
            panelHeight:'300',
            editable:false,
            value:'ALL'
        });
    });
    jQuery.getJSON("/purchase/getCbsCategoryByAuth", function(result){
        var JosnList=[];
        var cbsCategoryJson = {id: "全部", text: "全部"};
        JosnList.push(cbsCategoryJson)
        console.info(result);
        jQuery.each(result.data,function(i, v){
            var item={id:v,text:v};
            JosnList.push(item);
        });
        $("#cbsCategory").combobox({
            data:JosnList,
            valueField:'text',
            textField:'text',
            panelHeight:'auto',
            editable:false,
            value:'全部',
            loadFilter: function(data){
                var datas = [];
                $.each(data, function(k, v){
                    if($.inArray(v.text, authMap.cbsCategory) > -1 || v.text == '全部'){
                        datas.push(v);
                    }
                });
                return datas;
            }
        });
    });

    jQuery.getJSON("/T2OrderQuery/getProductBrand", function(result){
        var brand = result.data;
        brand.unshift({value:'ALL',valueMeaning:'全部'});
        $("#brand").combobox({
            data:result.data,
            valueField:'value',
            textField:'valueMeaning',
            editable:false,
            value:'ALL'
        });
    });

    jQuery.getJSON("/purchase/getProductGroupByAuth", function(result){
        var productgroup = result.data;
        productgroup.unshift({value:'ALL',valueMeaning:'全部'});
        $("#product_group").combobox({
            data:result.data,
            valueField:'value',
            textField:'valueMeaning',
            panelHeight:'300',
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
//点击导出
$('#export').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#dataGrid').datagrid('getChecked');
    var exportData = new Array();
    //将订单号存入Array
    $.each(checkedItems, function(index, item){
        exportData.push(item.bill_order_id);
    });
    $("#exportData").val(JSON.stringify(exportData));
    //判断是否都没选中
    if(exportData==null||exportData.length==0){
        $.messager.alert('错误','请至少选择一行要导出的行！','error');
        return;
    }
    $.messager.confirm('确认', '确定要导出吗？', function(r){
        if (r){
            $('#filterForm').attr("action", '/T2OrderQuery/exportPoList.export');
            $('#filterForm').submit();
        }
    });
});
//点击全部导出
$('#exportall').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    if(window.total && window.total > 20000){
        $.messager.alert('提示', '最多导出20000条记录，请缩小查询范围！', 'info');
        return;
    }
    $.messager.confirm('确认','确定要全部导出吗？', function(r){
        if (r){
            $('#filterForm').attr("action", '/T2OrderQuery/exportAllPoList.export');
            $('#filterForm').submit();
        }
    });
});
$(function () {
    datagrid = {
        fit: true,
        fitColumns: false,
        toolbar: '#datagridToolbar',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        pageSize: 100,
        pageList: [100,200,300],
        loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        rownumbers: true,
        columns: [[
            {
                field: 'check',
                title: '全选',
                width: 100,
                align: 'center',
                checkbox:true
            },
            {
                field: 'order_id',
                title: 'WP订单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'po_id',
                title: 'PO编号',
                width: 100,
                align: 'center'
            },
            {
                field: 'bill_order_id',
                title: '销售单号',
                width: 100,
                align: 'center'
            },
            {
                field: 'bill_time_display',
                title: '提单时间',
                width: 150,
                align: 'center'
            },
            {
                field: 'so_id',
                title: 'SO',
                width: 100,
                align: 'center'
            },
            {
                field: 'dn_id',
                title: 'DN',
                width: 100,
                align: 'center'
            },
            {
                field: 'ed_channel_name',
                title: '渠道',
                width: 100,
                align: 'center'
            },
            {
                field:'category_id',
                title:'品类',
                width:100,
                align:'center'
            },
            {
                field: 'product_group_name',
                title: '产品组',
                width: 100,
                align: 'center'
            },
            {
                field: 'materials_id',
                title: '物料编号',
                width: 100,
                align: 'center'
            },
            {
                field: 'materials_desc',
                title: '型号',
                width: 200,
                align: 'center'
            },
            {
                field: 'qty',
                title: '数量',
                width: 100,
                align: 'center'
            },
            {
                field: 'price',
                title: '样表单价',
                width: 100,
                align: 'center'
            },
            {
                field: 't2_amount',
                title: '样表金额',
                width: 100,
                align: 'center'
            },
            {
                field: 'amount',
                title: '采购价格',
                width: 100,
                align: 'center'
            },
            {
                field: 'total',
                title: '采购金额',
                width: 100,
                align: 'center'
            },
            {
                field: 'storage_id',
                title: 'WA库位码',
                width: 100,
                align: 'center'
            },
            {
                field: 'storage_name',
                title: 'WA库位名称',
                width: 100,
                align: 'center'
            },
            {
                field: 'flow_flag_name',
                title: '状态',
                width: 100,
                align: 'center'
            },
            {
                field: 'rrs_out_time_display',
                title: '出日日顺库时间',
                width: 150,
                align: 'center'
            },
            {
                field: 'wa_in_time_display',
                title: '入库时间',
                width: 150,
                align: 'center'
            },
            {
                field: 'WAqty',
                title: '已入WA库数量',
                width: 100,
                align: 'center'
            },
            {
                field: 'sapStatus',
                title: '推送sap状态',
                width: 100,
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
                field: 'sapMessage',
                title: '推送sap信息',
                width: 200,
                align: 'center'
            },
            {
                field: 'sapProcessTime',
                title: '推送sap时间',
                width: 200,
                align: 'center'
            }]]
        
    }

    $('#datagrid').datagrid(datagrid);

});


function myformatter_report_start(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    date_start = date;
    jQuery.getJSON("/T2OrderQuery/findateWeek?date="+ date, function(result){
        dateweek_start = result.data;
        $('#report_year_week_start_txt').val(dateweek_start);
    });
    return date;
}

function myformatter_report_end(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    date_end = date;
    jQuery.getJSON("/T2OrderQuery/findateWeek?date="+ date, function(result){
        dateweek_end = result.data;
        $('#report_year_week_end_txt').val(dateweek_end);
    });
    return date;
}

function myformatter(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    return date;
}

function myparser(s) {
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0], 10);
    var m = parseInt(ss[1], 10);
    var d = parseInt(ss[2], 10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
        return new Date(y, m - 1, d);
    } else {
        return new Date();
    }
}

//点击查询
$('#search').click(function () {
    if(!compareDate($('#report_year_week_start_txt').val(), $('#report_year_week_end_txt').val(), 1, "到货开始周大于结束周，请重新选择!")){
        return false;
    }
    if(!compareDate($('#bill_time_start').datebox('getValue'), $('#bill_time_end').datebox('getValue'), 0, "提单开始日期大于结束日期，请重新选择!")){
        return false;
    }
    if(!compareDate($('#datestorge_start').datebox('getValue'), $('#datestorge_end').datebox('getValue'), 0, "入库开始日期大于结束日期，请重新选择!")){
        return false;
    }

    var product_group = $("#product_group").combobox("getValue");
    //如果是ALL，产品组设为空
    if(product_group=="ALL"){
        product_group="";
    }
    var channel = $("#channel").combobox("getValue");
    //如果是ALL，渠道设为空
    if(channel=="ALL"){
        channel="";
    }

    var brandValue = $("#brand").combobox("getValue");
    //如果是ALL，品牌设为空
    if(brandValue=="ALL"){
        brandValue="";
    }

    var cbsCategoryValue = $("#cbsCategory").combobox("getValue");
    //如果是ALL，品类设为空
    if(cbsCategoryValue=="ALL"){
        cbsCategoryValue="";
    }

    //状态保存
    var flow_flag = $("#flow_flag").combobox("getValues");


    var reportWeekStart = $("#report_year_week_start").datebox('getValue');
    var reportWeekEnd = $("#report_year_week_end").datebox('getValue');
    $("#report_start_week_hidden").val(reportWeekStart);
    $("#report_end_week_hidden").val(reportWeekEnd);
    // $("#report_start_week_hidden").val(date_start);
    // $("#report_end_week_hidden").val(date_end);

    //产品组保存
    $("#product_group_id_hidden").val(product_group);
    //渠道保存
    $("#ed_channel_id_hidden").val(channel);
    //订单号保存
    $("#wp_order_id_hidden").val($("#order_id").val());
    //物料号保存
    $("#materials_id_hidden").val($("#materials_id").val());
    //库位保存
    $("#storage_id_hidden").val($("#storage_id").val());

    $("#flow_flag_hidden").val(flow_flag);

    var billtimestart = $('#bill_time_start').datebox('getValue');
    var billtimeend = $('#bill_time_end').datebox('getValue');

    var storgestart = $('#datestorge_start').datebox('getValue');
    var storgeend = $('#datestorge_end').datebox('getValue');

    $("#bill_time_start_hidden").val(billtimestart);
    $("#bill_time_end_hidden").val(billtimeend);
    $("#bill_order_id_hidden").val($("#bill_order_id").val());
    $("#so_id_hidden").val($("#so_id").val());
    $("#dn_id_hidden").val($("#dn_id").val());
    $("#datestorge_start_hidden").val(storgestart);
    $("#datestorge_end_hidden").val(storgeend);
    $("#brand_id_hidden").val(brandValue);
    $("#category_id_hidden").val(cbsCategoryValue);
    $("#materials_desc_hidden").val($("#materials_desc").val());
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/T2OrderQuery/findPOList",
        fit: true,
        pagination: true,
        singleSelect: true,
        pageSize: 100,
        pageList: [100,200,300],
        nowrap: true,
        rownumbers: true,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        queryParams: {
            report_start_week:$("#report_start_week_hidden").val(),
            report_end_week:$("#report_end_week_hidden").val(),
            channel:$("#ed_channel_id_hidden").val(),
            product_group:$("#product_group_id_hidden").val(),
            wp_order_id:$("#wp_order_id_hidden").val(),
            storage_id:$("#storage_id_hidden").val(),
            flow_flag:$("#flow_flag_hidden").val(),
            materials_id:$("#materials_id_hidden").val(),
            bill_order_id:$("#bill_order_id_hidden").val(),
            so_id:$("#so_id_hidden").val(),
            dn_id:$("#dn_id_hidden").val(),
            bill_time_start:$("#bill_time_start_hidden").val(),
            bill_time_end:$("#bill_time_end_hidden").val(),
            datestorge_start:$("#datestorge_start_hidden").val(),
            datestorge_end:$("#datestorge_end_hidden").val(),
            brand_id:$("#brand_id_hidden").val(),
            category_id:$("#category_id_hidden").val(),
            materials_desc:$("#materials_desc_hidden").val(),
            order_id:$("#order_id").val(),
            source_order_id:$("#source_order_id").val()
        },
        columns: [
            [
                {
                    field: 'check',
                    title: '全选',
                    width: 100,
                    align: 'center',
                    checkbox:true
                },
                {
                    field: 'order_id',
                    title: 'WP订单号',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'po_id',
                    title: 'PO编号',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'bill_order_id',
                    title: '销售单号',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'bill_time_display',
                    title: '提单时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'so_id',
                    title: 'SO',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'dn_id',
                    title: 'DN',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'ed_channel_name',
                    title: '渠道',
                    width: 100,
                    align: 'center'
                },
                {
                    field:'category_id',
                    title:'品类',
                    width:100,
                    align:'center'
                },
                {
                    field: 'product_group_name',
                    title: '产品组',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'materials_id',
                    title: '物料编号',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'materials_desc',
                    title: '型号',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'qty',
                    title: '数量',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'price',
                    title: '样表单价',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 't2_amount',
                    title: '样表金额',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'amount',
                    title: '采购价格',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'total',
                    title: '采购金额',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'storage_id',
                    title: 'WA库位码',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'storage_name',
                    title: 'WA库位名称',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'flow_flag_name',
                    title: '状态',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'rrs_out_time_display',
                    title: '出日日顺库时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'wa_in_time_display',
                    title: '入库时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'WAqty',
                    title: '已入WA库数量',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'sapStatus',
                    title: '推送sap状态',
                    width: 100,
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
                    field: 'sapMessage',
                    title: '推送sap信息',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'sapProcessTime',
                    title: '推送sap时间',
                    width: 200,
                    align: 'center'
                }
            ]
        ],
        toolbar: '#datagridToolbar'
    });
});

