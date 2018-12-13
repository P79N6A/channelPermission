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
            },
            {
                field: 'wp_order_id',
                title: '订单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'source_order_id',
                title: '来源单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'po_id',
                title: 'PO号',
                width: 100,
                align: 'center'
            },
            {
                field: 'so_id',
                title: 'SO号',
                width: 100,
                align: 'center'
            },
            {
                field: 'dn_id',
                title: 'DN号',
                width: 100,
                align: 'center'
            },
            {
                field: 'ed_channel_id',
                title: '渠道',
                width: 150,
                align: 'center'
            },
            {
                field: 'flow_flag_name',
                title: '状态',
                width: 100,
                align: 'center'
            },
            {
                field: 'corpName',
                title: '分公司',
                width: 300,
                align: 'center'
            },
            {
                field: 'industry_trade_desc',
                title: '工贸',
                width: 200,
                align: 'center'
            },
            {
                field: 'billType',
                title: '订单类型',
                width: 100,
                align: 'center',
                hidden:true
            },
            {
                field: 'billTypeName',
                title: '订单类型',
                width: 100,
                align: 'center'
            },
            {
                field: 'sap_flow_num',
                title: '配送方式',
                width: 100,
                align: 'center',
                hidden:true
            },
            {
                field: 'sap_flow_num_name',
                title: '配送方式',
                width: 100,
                align: 'center'
            },
            {
                field: 'payment_name',
                title: '付款方',
                width: 200,
                align: 'center'
            },
            {
                field: 'esale_name',
                title: '送达方',
                width: 200,
                align: 'center'
            },
            {
                field: 'estorge_id',
                title: '仓库编码',
                width: 100,
                align: 'center'
            },
            {
                field: 'estorge_name',
                title: '仓库名称',
                width: 200,
                align: 'center'
            },
            {
                field: 'whCode',
                title: '日日顺库位码',
                width: 100,
                align: 'center'
            },
            {
                field: 'custMgr',
                title: '客户经理',
                width: 100,
                align: 'center'
            },
            {
                field: 'porMgr',
                title: '产品经理',
                width: 100,
                align: 'center'
            },
            {
                field: 'proDputy',
                title: '产品代表',
                width: 100,
                align: 'center'
            },
            {
                field: 'isKPOName',
                title: '是否商用空调',
                width: 100,
                align: 'center'
            },
            {
                field: 'planindate_display',
                title: '预计到货日期',
                width: 100,
                align: 'center'
            },
            {
                field: 'budgetOrgName',
                title: '预算体',
                width: 250,
                align: 'center'
            },
            {
                field: 'ggg',
                title: '预算种类',
                width: 200,
                align: 'center'
            },
            {
                field: 'corpName',
                title: '销售组织',
                width: 200,
                align: 'center'
            },
            {
                field: 'maker',
                title: '开单人',
                width: 100,
                align: 'center'
            },
            {
                field: 'releBillCode',
                title: '关联单号',
                width: 100,
                align: 'center'
            },
            {
                field: 'rrs_distribution_name',
                title: '配送中心',
                width: 200,
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
                field: 'brand_name',
                title: '品牌',
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
                field: 'invState',
                title: '批次',
                width: 100,
                align: 'center'
            },
            {
                field: 'sumMoney',
                title: '价税合计',
                width: 100,
                align: 'center'
            },
            {
                field: 'qty',
                title: '数量',
                width: 100,
                align: 'center'
            },
            {
                field: 'unitPrice',
                title: '开票价格',
                width: 100,
                align: 'center'
            },
            {
                field: 'retailPrice',
                title: '供价',
                width: 100,
                align: 'center'
            },
            {
                field: 'actPrice',
                title: '执行价',
                width: 100,
                align: 'center'
            },
            {
                field: 'bateRate',
                title: '直扣',
                width: 100,
                align: 'center'
            },
            {
                field: 'bateMoney',
                title: '台返金额',
                width: 100,
                align: 'center'
            },
            {
                field: 'lossRate',
                title: '折扣',
                width: 100,
                align: 'center'
            },
            {
                field: 'isFLName',
                title: '是否返利',
                width: 100,
                align: 'center'
            },
            {
                field: 'bill_time_display',
                title: '提交时间',
                width: 150,
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
                title: '入WA库时间',
                width: 150,
                align: 'center'
            },{
                field: 'push_status',
                title: '推送SAP状态',
                width: 150,
                align: 'center'
            },
            {
                field: 'push_message',
                title: '推送SAP返回消息',
                width: 150,
                align: 'center'
            },
            {
                field: 'push_process_time',
                title: '推送SAP时间',
                width: 150,
                align: 'center'
            },
            {
                field: 'error_msg',
                title: '备注',
                width: 300,
                align: 'left'
            }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);


});

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

//点击查询
$('#search').click(function () {
    if(!compareDate($('#PlanInDate_start').datebox('getValue'), $('#PlanInDate_end').datebox('getValue'), 0, "预计到货开始日期大于结束日期，请重新选择!")){
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

    var billType = $("#BillType").combobox("getValue");
    //如果是ALL，品类设为空
    if(billType=="ALL"){
        billType="";
    }

    //状态保存
    var flow_flag = $("#flow_flag").combobox("getValues");

    //产品组保存
    $("#product_group_id_hidden").val(product_group);
    //渠道保存
    $("#ed_channel_id_hidden").val(channel);

    $("#flow_flag_hidden").val(flow_flag);

    var planInDatestart = $('#PlanInDate_start').datebox('getValue');
    var planInDateend = $('#PlanInDate_end').datebox('getValue');
    //if (planInDatestart && planInDateend) {
    //
    //} else {
    //    if (planInDatestart) {
    //        planInDateend = planInDatestart;
    //    } else {
    //       planInDatestart = planInDateend;
    //   }
    //}

    $("#PlanInDate_start_hidden").val(planInDatestart);
    $("#PlanInDate_end_hidden").val(planInDateend);


    $("#order_id_hidden").val($("#order_id").val());
    $("#CorpCode_hidden").val($("#CorpCode").val());
    $("#WhCode_hidden").val($("#WhCode").val());
    $("#materials_desc_hidden").val($("#materials_desc").val());
    $("#materials_id_hidden").val($("#materials_id").val());
    $("#brand_hidden").val(brandValue);
    $("#cbsCategory_hidden").val(cbsCategoryValue);
    $("#BillType_hidden").val(billType);
    $("#so_id_hidden").val($("#so_id").val());
    $("#dn_id_hidden").val($("#dn_id").val());
    $("#rrsCode_hidden").val($("#rrsCode").val());
    $("#source_order_id_hidden").val($("#source_order_id").val());
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/CrmOrderManual/findCrmOrderManualList",
        fit: true,
        pagination: true,
        singleSelect: true,
        pageSize: 100,
        pageList: [100,200,300],
        nowrap: false,
        rownumbers: true,
        singleSelect: false,
        selectOnCheck: true,
        toolbar: '#datagridToolbar',
        checkOnSelect: true,
        queryParams: {
            order_id:$("#order_id_hidden").val(),
            corpCode:$("#CorpCode_hidden").val(),
            whCode:$("#WhCode_hidden").val(),
            materials_id:$("#materials_id_hidden").val(),
            materials_desc:$("#materials_desc_hidden").val(),
            brand_id:$("#brand_hidden").val(),
            category_id:$("#cbsCategory_hidden").val(),
            bill_type:$("#BillType_hidden").val(),
            channel:$("#ed_channel_id_hidden").val(),
            product_group:$("#product_group_id_hidden").val(),
            flow_flag:$("#flow_flag_hidden").val(),
            planInDate_start:$("#PlanInDate_start_hidden").val(),
            planInDate_end:$("#PlanInDate_end_hidden").val(),
            so_id:$("#so_id_hidden").val(),
            dn_id:$("#dn_id_hidden").val(),
            rrsCode:$("#rrsCode_hidden").val(),
            source_order_id:$("#source_order_id_hidden").val()
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
                    field: 'wp_order_id',
                    title: '订单号',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'source_order_id',
                    title: '来源单号',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'po_id',
                    title: 'PO号',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'so_id',
                    title: 'SO号',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'dn_id',
                    title: 'DN号',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'ed_channel_id',
                    title: '渠道',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'flow_flag_name',
                    title: '状态',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'corpName',
                    title: '分公司',
                    width: 300,
                    align: 'center'
                },
                {
                    field: 'industry_trade_desc',
                    title: '工贸',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'billType',
                    title: '订单类型',
                    width: 100,
                    align: 'center',
                    hidden:true
                },
                {
                    field: 'billTypeName',
                    title: '订单类型',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'sap_flow_num',
                    title: '配送方式',
                    width: 100,
                    align: 'center',
                    hidden:true
                },
                {
                    field: 'sap_flow_num_name',
                    title: '配送方式',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'payment_name',
                    title: '付款方',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'esale_name',
                    title: '送达方',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'estorge_id',
                    title: '仓库编码',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'estorge_name',
                    title: '仓库名称',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'whCode',
                    title: '日日顺库位码',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'custMgr',
                    title: '客户经理',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'porMgr',
                    title: '产品经理',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'proDputy',
                    title: '产品代表',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'isKPOName',
                    title: '是否商用空调',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'planindate_display',
                    title: '预计到货日期',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'budgetOrgName',
                    title: '预算体',
                    width: 250,
                    align: 'center'
                },
                {
                    field: 'ggg',
                    title: '预算种类',
                    width: 200,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        return '月度直扣(占PL市场费用)';
                    }
                },
                {
                    field: 'corpName',
                    title: '销售组织',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'maker',
                    title: '开单人',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'releBillCode',
                    title: '关联单号',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'rrs_distribution_name',
                    title: '配送中心',
                    width: 200,
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
                    field: 'brand_name',
                    title: '品牌',
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
                    field: 'invState',
                    title: '批次',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'sumMoney',
                    title: '价税合计',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'qty',
                    title: '数量',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'unitPrice',
                    title: '开票价格',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'retailPrice',
                    title: '供价',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'actPrice',
                    title: '执行价',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'bateRate',
                    title: '直扣',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'bateMoney',
                    title: '台返金额',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'lossRate',
                    title: '折扣',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'isFLName',
                    title: '是否返利',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'bill_time_display',
                    title: '提交时间',
                    width: 150,
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
                    title: '入WA库时间',
                    width: 150,
                    align: 'center'
                },{
                field: 'push_status',
                title: '推送SAP状态',
                width: 150,
                align: 'center',
                formatter:function(value,rowData,rowIndex){
                    if(value == 1){
                        return '成功';
                    }else{
                        return '失败';
                    }
                }
            },
                {
                    field: 'push_message',
                    title: '推送SAP返回消息',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'push_process_time',
                    title: '推送SAP时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'error_msg',
                    title: '备注',
                    width: 300,
                    align: 'left'
                }
            ]
        ]
    });
});

$(function () {
    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    buttons.splice(1, 0, {
        text: '清除',
        handler: function(target){
            $(target).datebox('setValue', '');
            $(target).combo("hidePanel");
        }
    });
    $('#PlanInDate_start').datebox({
        buttons: buttons
    });
    $('#PlanInDate_end').datebox({
        buttons: buttons
    });
    //取得流程状态列表
    var valueSetId = "manual_order_flow_flag";
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
    //取得订单类型列表
    var valueSetId = "bill_type";
    jQuery.getJSON("/purchase/getByValueSetId"+ "?valueSetId=" + valueSetId, function(result){
        var count = result.totalCount;
        var dataList = result.data;
        //添加全部
        dataList.unshift( {value:'ALL',value_meaning:'全部'});
        $("#BillType").combobox({
            data:dataList,
            valueField:'value',
            textField:'value_meaning',
            panelHeight:'auto',
            editable:false,
            value:'ALL'
        });
    });

    jQuery.getJSON("/purchase/getCbsCategoryByAuth", function(result){
        var JosnList=[];
        var cbsCategoryJson = {id: "ALL", text: "全部"};
        JosnList.push(cbsCategoryJson)
        jQuery.each(result.data,function(i, v){
            var item={id:v,text:v};
            JosnList.push(item);
        });
        $("#cbsCategory").combobox({
            data:JosnList,
            valueField:'id',
            textField:'text',
            panelHeight:'auto',
            editable:false,
            value:'ALL'
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
            value:'ALL'
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
    var checkedItems = $('#datagrid').datagrid('getChecked');
    var exportData = new Array();
    //将订单号存入Array
    $.each(checkedItems, function(index, item){
        exportData.push(item.wp_order_id);
    });
    $("#exportData").val(JSON.stringify(exportData));
    //判断是否都没选中
    if(exportData==null||exportData.length==0){
        $.messager.alert('错误','请至少选择一行要导出的行！','error');
        return;
    }
    $.messager.confirm('确认', '确定要导出吗？', function(r){
        if (r){
            $('#filterForm').attr("action", '/CrmOrderManual/exportCrmList.export');
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
    if ($("#datagrid").datagrid("getData").rows.length == 0) {
        $.messager.alert('提示', '无有效数据！', 'info');
        return;
    }
    $.messager.confirm('确认','确定要全部导出吗？', function(r){
        if (r){
            $("#exportData").val('');
            $('#filterForm').attr("action", '/CrmOrderManual/exportCrmList.export');
            $('#filterForm').submit();
        }
    });
});
//点击新增
$('#add').click(function(){
    $("#modifyData").val("add");
    $('#filterForm').attr("action", '/CrmOrderManual/gotoAddCRMOrderManualDetail');
    $('#filterForm').submit();
});
//点击修改
$('#modify').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    //判断选中的数据是否符合条件
    if(checkedItems.length==0){
        $.messager.alert('错误','请至少选择一张要修改的订单！','error');
        return;
    }else if(checkedItems.length > 1){
        $.messager.alert('错误','每次只能修改一张订单！','error');
        return;
    }else if(checkedItems[0].flow_flag != 0){
        $.messager.alert('错误','只能修改未提交的订单！','error');
        return;
    }else{
        $("#modifyData").val(checkedItems[0].wp_order_id);
        $('#filterForm').attr("action", '/CrmOrderManual/gotoAddCRMOrderManualDetail');
        $('#filterForm').submit();
    }
});
//点击删除
$('#delete').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    var deleteData = new Array();
    //将订单号存入Array
    $.each(checkedItems, function(index, item){
        if(item.flow_flag == 0){
            deleteData.push(item.wp_order_id);
        }
    });
    $("#deleteData").val(JSON.stringify(deleteData));
    //判断是否都没选中
    if(deleteData==null||deleteData.length==0){
        $.messager.alert('错误','请至少选择一张要删除的未提交订单！','error');
        return;
    }
    $.messager.confirm('确认', '确定要删除吗？', function(r){
        if (r){
            jQuery.ajax({
                url: "/CrmOrderManual/deleteOrderManual",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"deleteData": JSON.stringify(deleteData)},
                success: function (data) {
                    if(data.message) {$.messager.alert('提示',data.message,'info');}
                    $('#datagrid').datagrid('load',
                        {order_id:$("#order_id_hidden").val(),
                            corpCode:$("#CorpCode_hidden").val(),
                            whCode:$("#WhCode_hidden").val(),
                            materials_id:$("#materials_id_hidden").val(),
                            materials_desc:$("#materials_desc_hidden").val(),
                            brand_id:$("#brand_hidden").val(),
                            category_id:$("#cbsCategory_hidden").val(),
                            bill_type:$("#BillType_hidden").val(),
                            channel:$("#ed_channel_id_hidden").val(),
                            product_group:$("#product_group_id_hidden").val(),
                            flow_flag:$("#flow_flag_hidden").val(),
                            planInDate_start:$("#PlanInDate_start_hidden").val(),
                            planInDate_end:$("#PlanInDate_end_hidden").val(),
                            so_id:$("#so_id_hidden").val(),
                            dn_id:$("#dn_id_hidden").val()});
                    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                }
            });
        }
    });
});