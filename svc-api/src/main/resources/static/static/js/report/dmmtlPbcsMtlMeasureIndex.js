var dateweek_start;
var dateweek_end;

var date_start;
var date_end;

var report_dateweek_start;
var report_dateweek_end;

var datagridOptions_dmmtlPbcsMtlMeasure = {
    fit: true,
    fitColumns: true,
    singleSelect: false,
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: covertColumns(tableHead),
    toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 20, 30, 40, 50],
    rownumbers: true
};

var datagrid1 = {
    fit: true,
    fitColumns: true,
    toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
    striped: true,
    singleSelect: true,
    pagination: true,
    columns: covertColumns(tableHead),
    pageSize: 10,
    pageList: [10, 20, 30, 40, 50],
    rownumbers: true
}

var dgt = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
    nowrap: true,
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 20, 30, 40, 50],
    rownumbers: true
}
var dg = {
    fit: true,
    fitColumns: true,
    toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
    striped: true,
    singleSelect: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 20, 30, 40, 50],
    rownumbers: true
}

function compareDate(startdate, enddate, replaceblag, message) {
    if (replaceblag) {
        if (startdate) {
            startdate = startdate.replace("年", "");
            startdate = startdate.replace("周", "");
        }
        if (enddate) {
            enddate = enddate.replace("年", "");
            enddate = enddate.replace("周", "");
        }
    }
    if (startdate && enddate) {
        if (startdate > enddate) {
            //alert(message);
            $.messager.alert('错误', message, 'error');
            return false;
        }
    }
    return true;
}

//点击查询
$("#search").on('click', function createTbody(){
    if (!compareDate($('#arrival_year_week_start_txt').val(), $('#arrival_year_week_end_txt').val(), 1, "到货开始周大于结束周，请重新选择!")) {
        return false;
    }
    if (!compareDate($('#datestart_txt').val(), $('#dateend_txt').val(), 1, "提报开始周大于结束周，请重新选择!")) {
        return false;
    }
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
    var brand = $("#brand").combobox("getValue");
    //如果是ALL，品牌设为空
    if (brand == "ALL") {
        brand = "";
    }
    //状态保存
    var flow_flag = $("#flow_flag").combobox("getValues").join(",");
    //如果包含ALL，设置为空
    if (flow_flag.indexOf("ALL") >= 0) {
        flow_flag = "";
    }
    var customization = $("#customization").combobox("getValue");
    //如果是ALL，定制设为空
    if (customization == "ALL") {
        customization = "";
    }
    var order_type = $("#order_type").combobox("getValue");
    //如果是ALL，订单类型设为空
    if (order_type == "ALL") {
        order_type = "";
    }
    var cbs_catgory = $("#cbs_catgory").combobox("getValue");
    //如果是ALL，品类设为空
    if (cbs_catgory == "ALL") {
        cbs_catgory = "";
    }
	//订单类别保存
	var order_category = $("#order_category").combobox("getValues").join(",");
	//如果包含ALL，设置为空
	if(order_category.indexOf("ALL")>=0){
		 order_category="";
	}
    //到货开始周保存
    $("#arrival_year_week_start_save").val(date_start);
    //到货结束周保存
    $("#arrival_year_week_end_save").val(date_end);
    //提报开始周保存
    $("#datestart_save").val(report_dateweek_start);
    //提报结束周保存
    $("#dateend_save").val(report_dateweek_end);
    //产品组保存
    $("#product_group_id_save").val(product_group);
    //渠道保存
    $("#ed_channel_id_save").val(channel);
    //订单号保存
    $("#wp_order_id_save").val($("#wp_order_id").val());
    //OMS订单号保存
    $("#oms_order_id_save").val($("#oms_order_id").val());
    //品牌保存
    $("#brand_save").val(brand);
    //物料号保存
    $("#materials_id_save").val($("#materials_id").val());
    //型号保存
    $("#materials_description_save").val($("#materials_description").val());
    //库位保存
    $("#storage_id_save").val($("#storage_id").val());
    //状态保存
    $("#flow_flag_save").val(flow_flag);
    //一次发运组合号保存
    $("#shipment_combination_id_save").val($("#shipment_combination_id").val());
    //GVS订单号保存
    $("#gvs_order_id_save").val($("#gvs_order_id").val());
    //客户订单号保存
    $("#custom_order_id_save").val($("#custom_order_id").val());
    //定制保存
    $("#customization_save").val(customization);
    //订单类型保存
    $("#order_type_save").val(order_type);
    //品类保存
    $("#cbs_catgory_save").val(cbs_catgory);
	//订单类别
	$("#order_category_save").val(order_category);
    //grid加载
    if (datagrid) {
        $('#dataGrid').datagrid('load',
                {
                    arrival_start_week: $("#arrival_year_week_start_save").val(),
                    arrival_end_week: $("#arrival_year_week_end_save").val(),
                    report_start_week: $("#datestart_save").val(),
                    report_end_week: $("#dateend_save").val(),
                    ed_channel_id: channel,
                    product_group_id: product_group,
                    order_id: $("#wp_order_id").val(),
                    oms_order_id: $("#oms_order_id").val(),
                    brand_id: brand,
                    materials_id: $("#materials_id").val(),
                    materials_desc: $("#materials_description").val(),
                    storage_id: $("#storage_id").val(),
                    flow_flag: flow_flag,
                    shipment_combination_id: $("#shipment_combination_id").val(),
                    gvs_order_id: $("#gvs_order_id").val(),
                    custom_order_id: $("#custom_order_id").val(),
                    customization: customization,
                    order_type: order_type,
                    category_id: cbs_catgory,
                    order_category: order_category
                });
    } else {
        datagrid = $('#dataGrid').datagrid({
            url: "findMultipleOrder",
            type:"post",
            fit: true,
            pagination: true,
            singleSelect: true,
            pageSize: 100,
            pageList: [1, 2, 100, 200, 300],
            nowrap: false,
            rownumbers: true,
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            collapsible: true,
            striped: true,
            queryParams: {
                arrival_start_week: $("#arrival_year_week_start_save").val(),
                arrival_end_week: $("#arrival_year_week_end_save").val(),
                report_start_week: $("#datestart_save").val(),
                report_end_week: $("#dateend_save").val(),
                ed_channel_id: channel,
                product_group_id: product_group,
                order_id: $("#wp_order_id").val(),
                oms_order_id: $("#oms_order_id").val(),
                brand_id: brand,
                materials_id: $("#materials_id").val(),
                materials_desc: $("#materials_description").val(),
                storage_id: $("#storage_id").val(),
                flow_flag: flow_flag,
                shipment_combination_id: $("#shipment_combination_id").val(),
                gvs_order_id: $("#gvs_order_id").val(),
                custom_order_id: $("#custom_order_id").val(),
                customization: customization,
                order_type: order_type,
                category_id: cbs_catgory,
                order_category: order_category
            },
            columns: [
                [

                    {
                        field: 'check',
                        title: '全选',
                        width: 20,
                        align: 'center',
                        checkbox: true
                    },
					{
                        field: 'order_category_name',
                        title: '订单类别',
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
                        field: 'order_id',
                        title: 'WP单号',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'channel_commit_time_display',
                        title: '渠道提交时间',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'channel_commit_user',
                        title: '渠道提交人',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'audit_time_display',
                        title: '审核时间',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'audit_user',
                        title: '审核人',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'order_close_time_display',
                        title: '手工关单时间',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'order_close_user',
                        title: '手工关单人',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'oms_order_id',
                        title: 'OMS订单号',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'custom_desc',
                        title: '客户名称',
                        width: 200,
                        align: 'center'
                    },
                    {
                        field: 'ggg',
                        title: '售达方',
                        width: 200,
                        align: 'center',
                        formatter: function (value, rowData, rowIndex) {
                            return '重庆新日日顺家电销售有限公司';
                        }

                    },
                    {
                        field: 'transmit_desc',
                        title: '送达方',
                        width: 200,
                        align: 'center'
                    },
                    {
                        field: 'report_year_week',
                        title: '提报年',
                        width: 90,
                        align: 'center'
                    },
                    {
                        field: 'report_year_week_display',
                        title: '提报周',
                        width: 90,
                        align: 'center'
                    },
                    {
                        field: 'arrive_year_week',
                        title: '到货年',
                        width: 90,
                        align: 'center',
                        formatter: function (value, rowData, rowIndex) {
                            return value.substr(0, 5);
                        }
                    },
                    {
                        field: 'arrive_year_week_display',
                        title: '到货周',
                        width: 90,
                        align: 'center',
                        formatter: function (value, rowData, rowIndex) {
                            return value.substr(5);
                        }
                    },

                    {
                        field: 'industry_trade_desc',
                        title: '工贸',
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
                        field: 'category_id',
                        title: '品类',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'product_group_name',
                        title: '产品组',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'brand_id',
                        title: '品牌',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'materials_id',
                        title: '物料编码',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'materials_desc',
                        title: '型号',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'order_type_name',
                        title: '订单类型',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 't2_delivery_prediction',
                        title: '数量',
                        width: 50,
                        align: 'center'
                    },
                    {
                        field: 'price',
                        title: '样表单价',
                        width: 60,
                        align: 'center'
                    },
                    {
                        field: 'amount',
                        title: '样表金额',
                        width: 60,
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
                        field: 'arrival_storage_desc',
                        title: '日日顺库位名称',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'series_id',
                        title: '系列',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'status',
                        title: 'OMS状态',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'latest_arrive_date_display',
                        title: '最晚到货日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'plan_deliver_date_display',
                        title: '计划发货日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'promise_arrive_date_display',
                        title: '承诺到货日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'actual_deliver_date_display',
                        title: '实际发货日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'predict_arrive_date_display',
                        title: '预计到货日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'industry_trade_take_date_display',
                        title: '工贸收货日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'custom_sign_date_display',
                        title: '工贸签收日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'return_order_date_display',
                        title: '工贸返单日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'latest_leave_base_date_display',
                        title: '最晚离基地日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'factory_id',
                        title: '发运工厂编码',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'factory_name',
                        title: '发运工厂名称',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'shipment_combination_id',
                        title: '一次发运单号',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'sign_num',
                        title: '工贸签收数量',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'no_pass_reason',
                        title: 'OMS拒单原因',
                        width: 200,
                        align: 'left'
                    },
                    {
                        field: 'gvs_order_id',
                        title: 'GVS订单号',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'dn_id',
                        title: '日日顺DN号',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'custpodetailcode',
                        title: '客户订单号',
                        width: 110,
                        align: 'center'
                    },
                    {
                        field: 'commit_time_display',
                        title: '提交日期',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'customization_name',
                        title: '定制',
                        width: 50,
                        align: 'center'
                    },
                    {
                        field: 'satisfy_type_name',
                        title: '满足方式',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'WAqty',
                        title: '已入WA库数量',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'error_msg',
                        title: '备注',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'pass_reason',
                        title: '冻结推送意见',
                        width: 200,
                        align: 'center'
                    }
                ]
            ]
        });
    }
    $('#dataGrid').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
});
//PO查询
/*$("#posearch").click(function () {
    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
    
    //获得选中行
    var checkedItems = $('#dataGrid').datagrid('getChecked');
    var poDetailData = new Array();
    //判断是否存在选择行
    $.each(checkedItems, function (index, item) {
        //flow_flag=30 - 80item.flow_flag==30||item.flow_flag==40||item.flow_flag==50||
        //item.flow_flag==60||item.flow_flag==70||item.flow_flag==80
        //if(){
        poDetailData.push(item.order_id);
        //}
    });
    if (poDetailData == null || poDetailData.length == 0) {
        $.messager.alert('错误', '请至少选择一行！', 'error');
        return;
    }
    var url = '/T2OrderQuery/purchaseOrderQueryList' + '?poDetailData=' + JSON.stringify(poDetailData);
    var win = window.open(url, '_blank');
    return false;

});*/
//手工关单
$("#manualCloseOrder").click(function () {
    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
    //获得选中行
    var checkedItems = $('#dataGrid').datagrid('getChecked');
    var manualCloseData = new Array();
    //判断是否存在已入WA库（WA入库数量小于采购数量）的行
    $.each(checkedItems, function (index, item) {
        //flow_flag=70 已开入WA库提单(并且WA入库数量小于采购数量)
        //if(item.flow_flag==70&&item.WAqty<item.t2_delivery_prediction){
        //flow_flag=60 已入日日顺库
        if (item.flow_flag == 60) {
            manualCloseData.push(item.order_id);
        }
    });
    //判断是否存在已开入WA提单的行
    if (manualCloseData == null || manualCloseData.length == 0) {
        $.messager.alert('错误', '请至少选择一行已入日日顺库的行！', 'error');
        return;
    }
    $.messager.confirm('确认', '确定要手动关单吗？（只关闭已入日日顺库）的行）', function (r) {
        if (r) {
            jQuery.ajax({
                url: "manualCloseOrderList",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data: {"manualCloseData": JSON.stringify(manualCloseData)},
                success: function (data) {
                    $.messager.alert('提示', '手动关单完成', 'info');
                    $('#dataGrid').datagrid('reload',
                            {
                                arrival_start_week: $("#arrival_year_week_start_save").val(),
                                arrival_end_week: $("#arrival_year_week_end_save").val(),
                                report_start_week: $("#datestart_save").val(),
                                report_end_week: $("#dateend_save").val(),
                                ed_channel_id: $("#ed_channel_id_save").val(),
                                product_group_id: $("#product_group_id_save").val(),
                                order_id: $("#wp_order_id_save").val(),
                                oms_order_id: $("#oms_order_id_save").val(),
                                brand_id: $("#brand_save").val(),
                                materials_id: $("#materials_id_save").val(),
                                materials_desc: $("materials_description_save").val(),
                                storage_id: $("#storage_id_save").val(),
                                flow_flag: $("#flow_flag_save").val(),
                                shipment_combination_id: $("#shipment_combination_id_save").val(),
                                gvs_order_id: $("#gvs_order_id_save").val(),
                                custom_order_id: $("#custom_order_id_save").val(),
                                customization: $("#customization_save").val(),
                                order_type: $("#order_type_save").val(),
                                category_id: $("#cbs_catgory_save").val(),
                                order_category: $("#order_category_save").val()
                            });
                    $('#dataGrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                }
            });
        }
    });
});

//撤消手工关单
$("#cancelCloseOrder").click(function () {
    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
    //获得选中行
    var checkedItems = $('#dataGrid').datagrid('getChecked');
    var cancelCloseData = new Array();
    //判断是否已经关闭状态
    $.each(checkedItems, function (index, item) {
        //flow_flag=-70 完成关闭
        if (item.flow_flag == '-70') {
            cancelCloseData.push(item.order_id);
        }
    });
    //判断是否存在已开入WA提单或者已入日日顺库的行
    if (cancelCloseData == null || cancelCloseData.length == 0) {
        $.messager.alert('错误', '请至少选择一行已完成关闭的行！', 'error');
        return;
    }
    $.messager.confirm('确认', '确定要撤消手动关单吗？（只撤消已完成关闭的行）', function (r) {
        if (r) {
            jQuery.ajax({
                url: "cancelCloseOrderList",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data: {"cancelCloseData": JSON.stringify(cancelCloseData)},
                success: function (data) {
                    $.messager.alert('提示', '撤消手动关单完成', 'info');
                    $('#dataGrid').datagrid('reload',
                            {
                                arrival_start_week: $("#arrival_year_week_start_save").val(),
                                arrival_end_week: $("#arrival_year_week_end_save").val(),
                                report_start_week: $("#datestart_save").val(),
                                report_end_week: $("#dateend_save").val(),
                                ed_channel_id: $("#ed_channel_id_save").val(),
                                product_group_id: $("#product_group_id_save").val(),
                                order_id: $("#wp_order_id_save").val(),
                                oms_order_id: $("#oms_order_id_save").val(),
                                brand_id: $("#brand_save").val(),
                                materials_id: $("#materials_id_save").val(),
                                materials_desc: $("materials_description_save").val(),
                                storage_id: $("#storage_id_save").val(),
                                flow_flag: $("#flow_flag_save").val(),
                                shipment_combination_id: $("#shipment_combination_id_save").val(),
                                gvs_order_id: $("#gvs_order_id_save").val(),
                                custom_order_id: $("#custom_order_id_save").val(),
                                customization: $("#customization_save").val(),
                                order_type: $("#order_type_save").val(),
                                category_id: $("#cbs_catgory_save").val(),
                                order_category: $("#order_category_save").val()
                            });
                    $('#dataGrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                }
            });
        }
    });
});

function myformatter_arrival_start(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    date_start = date;
    jQuery.getJSON("findateWeek?date="+ date, function (result) {
        dateweek_start = result.data;
        $('#arrival_year_week_start_txt').val(dateweek_start);
    });
    return date;
}

function myformatter_arrival_end(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    date_end = date;
    jQuery.getJSON("findateWeek?date=" + date, function (result) {
        dateweek_end = result.data;
        $('#arrival_year_week_end_txt').val(dateweek_end);
    });
    return date;
}

function myformatter_start(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    jQuery.getJSON("findateWeek?date=" + date + "&type=0", function (result) {
        report_dateweek_start = result.data;
        $('#datestart_txt').val(report_dateweek_start);
    });
    return date;
}

function myformatter_end(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    jQuery.getJSON("findateWeek?date=" + date + "&type=0", function (result) {
        report_dateweek_end = result.data;
        $('#dateend_txt').val(report_dateweek_end);
    });
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

var datagrid = null;
$(function () {
    datagrid = $('#datagrid').datagrid(datagridOptions_dmmtlPbcsMtlMeasure);
    var buttonsStart = $.extend([], $.fn.datebox.defaults.buttons);

    buttonsStart.splice(1, 0, {
        text: '清除',
        handler: function (target) {
            $(target).datebox('setValue', '');
            $('#' + $(target).attr('id') + '_txt').val('');
            dateweek_start = '';
            date_start = '';
            $(target).combo("hidePanel");
        }
    });
    var buttonsEnd = $.extend([], $.fn.datebox.defaults.buttons);
    buttonsEnd.splice(1, 0, {
        text: '清除',
        handler: function (target) {
            $(target).datebox('setValue', '');
            $('#' + $(target).attr('id') + '_txt').val('');
            dateweek_end = '';
            date_end = '';
            $(target).combo("hidePanel");
        }
    });
    $('#arrival_year_week_start').datebox({
        buttons: buttonsStart
    });
    $('#arrival_year_week_end').datebox({
        buttons: buttonsEnd
    });

    //接收当前周
    $("#arrival_year_week_start").datebox("setValue", "$!lastweek");
    $("#arrival_year_week_end").datebox("setValue", "$!currentweek");

    $("#arrival_year_week_start_save").val("$!lastweek");
    $("#arrival_year_week_end_save").val("$!currentweek");

    var buttonsStart1 = $.extend([], $.fn.datebox.defaults.buttons);
    buttonsStart1.splice(1, 0, {
        text: '清除',
        handler: function (target) {
            $(target).datebox('setValue', '');
            $('#' + $(target).attr('id') + '_txt').val('');
            report_dateweek_start = '';
            $(target).combo("hidePanel");
        }
    });
    var buttonsEnd1 = $.extend([], $.fn.datebox.defaults.buttons);
    buttonsEnd1.splice(1, 0, {
        text: '清除',
        handler: function (target) {
            $(target).datebox('setValue', '');
            $('#' + $(target).attr('id') + '_txt').val('');
            report_dateweek_end = '';
            $(target).combo("hidePanel");
        }
    });
    $('#datestart').datebox({
        buttons: buttonsStart1
    });
    $('#dateend').datebox({
        buttons: buttonsEnd1
    });

    //取得流程状态列表
    var valueSetId = "flow_flag";
    jQuery.getJSON("/purchase/getByValueSetId" + "?valueSetId=" + valueSetId, function (result) {
        var count = result.totalCount;
        var dataList = result.data;
        //添加全部
        dataList.unshift({value: 'ALL', value_meaning: '全部'});
        $("#flow_flag").combobox({
            data: dataList,
            valueField: 'value',
            textField: 'value_meaning',
            panelHeight: '300',
            editable: false,
            value: 'ALL'
        });
    });
    jQuery.getJSON("/purchase/getProductGroupByAuth", function (result) {
        var productgroup = result.data;
        productgroup.unshift({value: 'ALL', valueMeaning: '全部'});
        $("#product_group").combobox({
            data: result.data,
            valueField: 'value',
            textField: 'valueMeaning',
            panelHeight: '300',
            editable: false,
            value: 'ALL'

        });
    });
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
            value: 'ALL'
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
        $("#cbs_catgory").combobox({
            data: JosnList,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            editable: false,
            value: 'ALL'
        });
    });

    jQuery.getJSON("/T2OrderQuery/getProductBrand", function (result) {
        var brand = result.data;
        brand.unshift({value: 'ALL', valueMeaning: '全部'});
        $("#brand").combobox({
            data: result.data,
            valueField: 'value',
            textField: 'valueMeaning',
            editable: false,
            value: 'ALL'
        });
    });

    //取得是否定制列表
    var valueSetId = "true_or_false";
    jQuery.getJSON("/purchase/getByValueSetId" + "?valueSetId=" + valueSetId, function (result) {
        var count = result.totalCount;
        var dataList = result.data;
        //添加全部
        dataList.unshift({value: 'ALL', value_meaning: '全部'});
        $("#customization").combobox({
            data: dataList,
            valueField: 'value',
            textField: 'value_meaning',
            panelHeight: 'auto',
            editable: false,
            value: 'ALL'
        });
    });

    //取得订单类型列表
    var valueSetId = "order_type";
    jQuery.getJSON("/purchase/getByValueSetId" + "?valueSetId=" + valueSetId, function (result) {
        var count = result.totalCount;
        var dataList = result.data;
        //添加全部
        dataList.unshift({value: 'ALL', value_meaning: '全部'});
        $("#order_type").combobox({
            data: dataList,
            valueField: 'value',
            textField: 'value_meaning',
            panelHeight: 'auto',
            editable: false,
            value: 'ALL'
        });
    });
    //取得订单类别列表
    var valueSetId = "order_category";
    jQuery.getJSON("/purchase/getByValueSetId" + "?valueSetId=" + valueSetId, function (result) {
        var count = result.totalCount;
        var dataList = result.data;
        //添加全部
        dataList.unshift({value: 'ALL', value_meaning: '全部'});
        $("#order_category").combobox({
            data: dataList,
            valueField: 'value',
            textField: 'value_meaning',
            panelHeight: 'auto',
			width:'154',
            editable: false,
            value: 'ALL'
        });
    });

//构筑批量手工关单的dialog
/*    $("#manualDiv").dialog({
    	title:"批量手工关单",
        //autoOpen: false,
        modal: true,
        //closeOnEscape: false,
        width: 400,
        buttons: {
            "确定": function () {
                var manualClose = $("#manualOrderId").val();
                if (manualClose == '') {
                    $.messager.alert('提示', '请输入WA单号', 'info');
                    //return;
                }
                $.messager.confirm('确认', '确定要手动关单吗？（只关闭已入日日顺库的行）', function (r) {
                    if (r) {
                        jQuery.ajax({
                            url: "batchManualCloseOrderList",   // 提交的页面
                            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                            data: {"manualCloseData": $("#manualOrderId").val()},
                            success: function (data) {
                                $.messager.alert('提示', '手动关单完成', 'info');
                                $("#manualOrderId").val("");
                                $("#manualDiv").dialog('close');
                                $('#dataGrid').datagrid('reload',
                                        {
                                            arrival_start_week: $("#arrival_year_week_start_save").val(),
                                            arrival_end_week: $("#arrival_year_week_end_save").val(),
                                            report_start_week: $("#datestart_save").val(),
                                            report_end_week: $("#dateend_save").val(),
                                            ed_channel_id: $("#ed_channel_id_save").val(),
                                            product_group_id: $("#product_group_id_save").val(),
                                            order_id: $("#wp_order_id_save").val(),
                                            oms_order_id: $("#oms_order_id_save").val(),
                                            brand_id: $("#brand_save").val(),
                                            materials_id: $("#materials_id_save").val(),
                                            materials_desc: $("materials_description_save").val(),
                                            storage_id: $("#storage_id_save").val(),
                                            flow_flag: $("#flow_flag_save").val(),
                                            shipment_combination_id: $("#shipment_combination_id_save").val(),
                                            gvs_order_id: $("#gvs_order_id_save").val(),
                                            custom_order_id: $("#custom_order_id_save").val(),
                                            customization: $("#customization_save").val(),
                                            order_type: $("#order_type_save").val(),
                                            category_id: $("#cbs_catgory_save").val(),
                                            order_category: $("#order_category_save").val()
                                        });
                                $('#dataGrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                            }
                        });
                    }
                });

            },
            "取消": function () {
                $(this).dialog('close');
            }
        }
    });*/


        if($('#grid').length>0){
            var gridtable = $('#grid').datagrid(grid)
        }
        if (!datagrid.length) {
            datagrid = $('#dg').datagrid(dg);
        }
        if (!datagrid.length) {
            datagrid = $('#datagrid1').datagrid(datagrid1);
        }
        if (!datagrid.length) {
            datagrid = $('#dgt').datagrid(dgt);
        }
        $("#searchBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
            /*var param = $('#paramForm_dmmtlPbcsMtlMeasure').serializeObject();
            datagrid.datagrid({queryParams: param});*/
            event.preventDefault();
            var options = datagrid.datagrid('getPager').data("pagination").options;
            var InvoicequeryParams = {	cOrderSn:$("#cOrderSn").val(),
                      invoiceTitle:$("#invoiceTitle").val(),
                      taxPayerNumber:$("#taxPayerNumber").val(),
                      registerAddressAndPhone:$("#registerAddressAndPhone").val(),
                      bankNameAndAccount:$("#bankNameAndAccount").val(),
                      isEnergySaving:$("#isEnergySaving").combobox('getValue'),
                      isReInvoice:$("#isReInvoice").combobox('getValue'),
                      type:$("#type").combobox('getValue'),
                      state:$("#state").combobox('getValue'),
                      electricFlag:$("#electricFlag").combobox('getValue'),
                      tryNum:$("#tryNum").val(),
                      startFirstPushTime:$("#startFirstPushTime").datebox('getValue'),
                      endFirstPushTime:$("#endFirstPushTime").datebox('getValue'),
                      startInvalidTime:$("#startInvalidTime").datebox('getValue'),
                      endInvalidTime:$("#endInvalidTime").datebox('getValue'),
                      success:$("#success").combobox('getValue'),
                      startCOrderAddTime:$("#startCOrderAddTime").datebox('getValue'),
                      endCOrderAddTime:$("#endCOrderAddTime").datebox('getValue'),
                      isTogether:$("#isTogether").combobox('getValue'),
                      cOrderType:$("#cOrderType").combobox('getValue'),
                      invoiceNumber:$("#invoiceNumber").val(),
                      page: options.pageNumber,
                      rows: options.pageSize
            };
            datagrid.datagrid({
                    url: "/invoiceOperation/search",
                    queryParams: InvoicequeryParams,
                    fit: true,
                    pagination: true,
                    pageSize: 20,
                    pageList: [10, 20, 30],
                    nowrap: true,
                    rownumbers: true,
                    singleSelect: false,
                    selectOnCheck: true,
                    checkOnSelect: true,
                    columns: [[
                        {title: "网单号", field: "cOrderSn", sortable: true},
                        {
                            title: "网单类型", field: "cOrderType", sortable: true, formatter: function (val, rowData, rowIndex) {
                            if (val == '1') {
                                return '普通网单';
                            } else if (val == '2') {
                                return '差异网单';
                            } else if (val == '3'){
                                return '专项开票';
                            } else{
                                return '';
                            }
                        }
                        },
                        {title: "物料编号", field: "sku", sortable: true},
                        {title: "商品名称", field: "productName", sortable: true},
                        {title: "商品分类", field: "productCateName", sortable: true},
                        {title: "数量", field: "number", sortable: true},
                        {title: "含税单价", field: "price", sortable: true},
                        {title: "含税金额", field: "amount", sortable: true},
                        {title: "纳税人识别号", field: "taxPayerNumber", sortable: true},
                        {title: "发票号", field: "invoiceNumber", sortable: true},
                        {title: "税控码", field: "fiscalCode", sortable: true},
                        {
                            title: "发票类型", field: "type", sortable: true, formatter: function (val, rowData, rowIndex) {
                            if (val == '1') {
                                return '增值税发票';
                            } else if (val == '2') {
                                return '普通发票';
                            } else{
                                return '';
                            }
                        }
                        },
                        {
                            title: "电子发票标志", field: "electricFlag", sortable: true, formatter: function (val, rowData, rowIndex) {
                            if (val == '1') {
                                return '电子发票';
                            } else if (val == '2') {
                                return '纸制发票';
                            } else{
                                return '';
                            }
                        }
                        },
                        {
                            title: "发票状态", field: "state", sortable: true, formatter: function (val, rowData, rowIndex) {
                            if (val == '0') {
                                return '待开票';
                            } else if (val == '1') {
                                return '开票中';
                            } else if (val == '4') {
                                return '已开票';
                            } else if (val == '5'){
                                return '已取消开票';
                            } else{
                                return '';
                            }
                        }
                        },
                        {
                            title: "货票同行", field: "isTogether", sortable: true, formatter: function (val, rowData, rowIndex) {
                            if (val == '1') {
                                return '货票同行';
                            } else if (val == '2') {
                                return '非货票同行';
                            } else{
                                return '';
                            }
                        }
                        },
                        {title: "首次推送开票时间", field: "firstPushTime", sortable: true},
                        {title: "开票时间", field: "billingTime", sortable: true},
                        {title: "作废时间", field: "invalidTime", sortable: true},
                        {
                            title: "推送状态", field: "success", sortable: true, formatter: function (val, rowData, rowIndex) {
                            if (val == '0') {
                                return '未提交或提交失败';
                            } else if (val == '1') {
                                return '已提交';
                            } else{
                                return '';
                            }
                        }
                        },
                        {
                            title: "是否成功", field: "success", sortable: true, formatter: function (val, rowData, rowIndex) {
                            if (val == '0') {
                                return '待推送';
                            } else if (val == '1') {
                                return '成功';
                            } else{
                                return '';
                            }
                        }
                        },
                        {title: "推送次数", field: "tryNum", sortable: true},
                        {
                            title: "实时开票信息查询", field: "success", sortable: true, formatter: function (val, rowData, rowIndex) {
                            return '<a>实时开票信息查询</a>';
                        }
                        }
                    ]],
                    /*toolbar: '#sale_road_grid_bar',*/
                    striped: true,
                    autoRowHeight: true,
                    nowrap: true,
                    pagination: true,
                    rownumbers: true,
                });
        });
        $("#addBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
            $('#addForm_dmmtlPbcsMtlMeasure').form('reset');
            $('#addForm_dmmtlPbcsMtlMeasure').find('[__actType]').val('add');
            $('#addDlg_dmmtlPbcsMtlMeasure').dialog({'title': i18n['message.act.add']});
            $('#addDlg_dmmtlPbcsMtlMeasure').dialog('open');
        });

        $("#importSave").on('click', function () {
            if (!$('#importForm').form('validate')) {
                return;
            }
            $('#importDialog').dialog('close');
            alert("导入成功！")

        })

        $("#import").on('click', function (event) {
            var options = datagrid.datagrid('getPager').data("pagination").options;
            var InvoicequeryParams = {	cOrderSn:$("#cOrderSn").val(),
                      invoiceTitle:$("#invoiceTitle").val(),
                      taxPayerNumber:$("#taxPayerNumber").val(),
                      registerAddressAndPhone:$("#registerAddressAndPhone").val(),
                      bankNameAndAccount:$("#bankNameAndAccount").val(),
                      isEnergySaving:$("#isEnergySaving").combobox('getValue'),
                      isReInvoice:$("#isReInvoice").combobox('getValue'),
                      type:$("#type").combobox('getValue'),
                      state:$("#state").combobox('getValue'),
                      electricFlag:$("#electricFlag").combobox('getValue'),
                      tryNum:$("#tryNum").val(),
                      startFirstPushTime:$("#startFirstPushTime").datebox('getValue'),
                      endFirstPushTime:$("#endFirstPushTime").datebox('getValue'),
                      startInvalidTime:$("#startInvalidTime").datebox('getValue'),
                      endInvalidTime:$("#endInvalidTime").datebox('getValue'),
                      success:$("#success").combobox('getValue'),
                      startCOrderAddTime:$("#startCOrderAddTime").datebox('getValue'),
                      endCOrderAddTime:$("#endCOrderAddTime").datebox('getValue'),
                      isTogether:$("#isTogether").combobox('getValue'),
                      cOrderType:$("#cOrderType").combobox('getValue'),
                      invoiceNumber:$("#invoiceNumber").val(),
                      page: options.pageNumber,
                      rows: options.pageSize
            };
            $.ajax({
                url      : "/invoiceOperation/export_Excel",
                type     : 'POST',
                data     : InvoicequeryParams,
                success  : function(data) {
                    alert(data.msg);
                }
            })
        });

        $("#addDlgSaveBtn_dmmtlPbcsMtlMeasure").on('click', function () {
            if (!$('#addForm_dmmtlPbcsMtlMeasure').form('validate')) {
                return;
            }
            var actType = $('#addForm_dmmtlPbcsMtlMeasure').find('[__actType]').val();
            var url;
            if (actType === 'add') {
                url = '/report/dmmtlPbcsMtlMeasure/c';
            } else {
                url = '/report/dmmtlPbcsMtlMeasure/u';
            }
            $.post(url, $('#addForm_dmmtlPbcsMtlMeasure').serializeObject()).success(
                function (res) {
                    if (res.success) {
                        if (actType === 'add') {
                            alert(i18n['message.act.add.success']);
                        } else {
                            alert(i18n['message.act.edit.success']);
                        }
                        $('#addDlg_dmmtlPbcsMtlMeasure').dialog('close');
                    } else {
                        if (actType === 'add') {
                            alert(i18n['message.act.add.fail']);
                        } else {
                            alert(i18n['message.act.edit.fail']);
                        }
                    }
                }).error(function (errorObj, statusText) {
                alert(statusText);
            }).done(function () {
                datagrid.datagrid('reload');
            });
        });

        $("[detail]").on('click', function (event) {
            if (this.className != 'easyui-linkbutton') {
                var selected = true;
            } else {
                datagrid.datagrid('getSelected');
            }
            if (selected) {
                $('#detailgrid').datagrid(detail);
                $('#detailDialog').dialog({'title': '详情'});
                $('#detailDialog').dialog('open');
            } else {
                alert(i18n['message.select-one']);
            }
        });


        $("#editBtn_dmmtlPbcsMtlMeasure").on('click', function () {
            var selected = datagrid.datagrid('getSelections');
            $('#addDlg_dmmtlPbcsMtlMeasure').dialog({'title': i18n['message.act.edit']});
            if (selected.length == 1) {
                $('#addForm_dmmtlPbcsMtlMeasure').form('load', selected[0]);
                $('#addForm_dmmtlPbcsMtlMeasure').find('[__actType]').val('edit');
                $('#addDlg_dmmtlPbcsMtlMeasure').dialog('open');
                datagrid.datagrid('reload');
            } else {
                alert(i18n['message.select-one']);
            }
        }).error(function (errorObj, statusText) {
            alert(statusText);
        });

        $("#deleteBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
            event.preventDefault();
            var selected = datagrid.datagrid('getSelected');
            if (selected !== null) {
                confirm(i18n['message.delete.confirm'], function (r) {
                    if (r) {
                        $.post('/report/dmmtlPbcsMtlMeasure/d', {id: selected.id}).success(function (res) {
                            alert(i18n['message.act.delete.success']);
                        }).error(function () {
                            alert(i18n['message.act.delete.fail']);
                        }).done(function () {
                            datagrid.datagrid('reload');
                        });
                    }
                })

            } else {
                alert(i18n['message.select-one']);
            }
        });
        $("#resetBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
            event.preventDefault();
            $('#paramForm_dmmtlPbcsMtlMeasure').form('reset');
        });

});

$("#y").click(function () {
	var manualClose = $("#manualOrderId").val();
    if (manualClose == '') {
        $.messager.alert('提示', '请输入WA单号', 'info');
        //return;
    }
    $.messager.confirm('确认', '确定要手动关单吗？（只关闭已入日日顺库的行）', function (r) {
        if (r) {
            jQuery.ajax({
                url: "batchManualCloseOrderList",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data: {"manualCloseData": $("#manualOrderId").val()},
                success: function (data) {
                    $.messager.alert('提示', '手动关单完成', 'info');
                    $("#manualOrderId").val("");
                    $("#manualDiv").dialog('close');
                    $('#dataGrid').datagrid('reload',
                            {
                                arrival_start_week: $("#arrival_year_week_start_save").val(),
                                arrival_end_week: $("#arrival_year_week_end_save").val(),
                                report_start_week: $("#datestart_save").val(),
                                report_end_week: $("#dateend_save").val(),
                                ed_channel_id: $("#ed_channel_id_save").val(),
                                product_group_id: $("#product_group_id_save").val(),
                                order_id: $("#wp_order_id_save").val(),
                                oms_order_id: $("#oms_order_id_save").val(),
                                brand_id: $("#brand_save").val(),
                                materials_id: $("#materials_id_save").val(),
                                materials_desc: $("materials_description_save").val(),
                                storage_id: $("#storage_id_save").val(),
                                flow_flag: $("#flow_flag_save").val(),
                                shipment_combination_id: $("#shipment_combination_id_save").val(),
                                gvs_order_id: $("#gvs_order_id_save").val(),
                                custom_order_id: $("#custom_order_id_save").val(),
                                customization: $("#customization_save").val(),
                                order_type: $("#order_type_save").val(),
                                category_id: $("#cbs_catgory_save").val(),
                                order_category: $("#order_category_save").val()
                            });
                    $('#dataGrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                }
            });
        }
    });
});
$("#n").click(function () {
/*	$(this).dialog('close');*/
	$("#manualDiv").window('close');
});

$(function(){
	$("#manualDiv").panel('close');
});

//批量手工关单
$("#manualClose").click(function () {
    if (datagrid) {
        $("#manualDiv").show();
        $("#manualDiv").dialog('open');
    } else {
        $.messager.alert('提示', "请先查询！", 'info');
        return;
    }
});
//点击导出
$('#export').click(function () {
    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
    //获得选中行

    var checkedItems = $('#dataGrid').datagrid('getChecked');
    var exportData = new Array();
    //将订单号存入Array
    $.each(checkedItems, function (index, item) {
        exportData.push(item.order_id);
    });
    $("#exportData").val(JSON.stringify(exportData));
    //判断是否都没选中
    if (exportData == null || exportData.length == 0) {
        alert('错误', '请至少选择一行要导出的行！', 'error');
        return;
    }
    $.messager.confirm('确认', '确定要导出吗？', function (r) {
        if (r) {
            $('#filterForm').attr("action", 'exportT2OrderList.export');
            $('#filterForm').submit();
        }
    });
});
//点击全部导出
$('#exportall').click(function () {
    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
    $.messager.confirm('确认', '确定要全部导出吗？', function (r) {
        if (r) {
            $("#exportData").val('');
            $('#filterForm').attr("action", 'exportAllT2OrderList.export');
            $('#filterForm').submit();
        }
    });
});

//打开已冻结推送意见框
$('#commitAgain').click(function () {
    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
    //获得选中行
    var checkedItems = $('#dataGrid').datagrid('getChecked');
    $('#oms_order_id_save').val('');
    //判断xuanzhong
    if (checkedItems.length != 1) {
        $.messager.alert('提示', "请选择一行状态为[OMS冻结]的行！", 'info');
        return;
    } else {
        $.each(checkedItems, function (index, item) {
            //flow_flag=25 OMS冻结
            if (item.flow_flag == 25) {
                $('#oms_order_id_save').val(item.oms_order_id);
            }
        });
    }
    if ($('#oms_order_id_save').val() == null || $('#oms_order_id_save').val() == '') {
        $.messager.alert('提示', '请至少选择一行状态为[OMS冻结]的行！', 'info');
        return;
    }else{
	    $("#passDiv").show();
	    $("#passDiv").window('open');
	}
});

//已冻结推送
$('#passReview').click(function () {
  var flag=true;
  $('#passForm input').each(function () {
	    if ($(this).attr('validType')) {
		    if (!$(this).validatebox('isValid')) {
		        flag = false;
		        return;
		    }
	    }
	});
  if(flag){
  $("#passReview").linkbutton('disable');
     jQuery.ajax({
                url: "commitAgainOrderMultipleList",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data: {"oms_order_id": $('#oms_order_id_save').val(),
				       "pass_reason":$("#pass_reason").val(),
				     },
		        dataType: "JSON",
			    beforeSend: function(){
		              var win = $.messager.progress({  
		                   title:'请等待',  
		                   msg:'数据处理中...',
		                   text:'处理中',
		                   interval:700
		                   });  
		        },  
			    complete: function(){  
		                //AJAX请求完成时隐藏loading提示  
		                $.messager.progress('close'); 
		        }, 
                success: function (data) {
                    if (data == true) {
                        $.messager.alert('提示', '推送完成', 'info');
                    }else {
				        $.messager.alert('提示','推送失败','info');
					 }
				    $('#oms_order_id_save').val("");
					$("#passDiv").window('close');
					$("#passForm").form("clear");
                    $('#dataGrid').datagrid('reload');
					$("#passReview").linkbutton('enable');
                    $('#dataGrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                }
			});
		 }else{
		     $.messager.alert('提示',"请输入正确数据",'error');
	     }
});

//返回查询页面
$("#close").click(function() {
					 $("#passForm").form("clear");
					 $("#passDiv").window('close');
					 $('#oms_order_id_save').val("");
					 $("#passForm").form("clear");
				});	

//冻结推送原因windows框
$('#passDiv').window({
    shadow:false,
    resizable:false,
    autoOpen: false,
    closed:true,
    width:360,
	height:260,
    collapsible:false,
    minimizable:false,
    maximizable:false,
    modal:true,
    closeOnEscape: true
    });
	
//推送意见框长度校验	
$.extend($.fn.validatebox.defaults.rules, {
    maxLength: {
        validator: function(value, param){
            return value.length <= param[0];
        },
        message: 'Please enter no more than {0} characters.'
    }
});
