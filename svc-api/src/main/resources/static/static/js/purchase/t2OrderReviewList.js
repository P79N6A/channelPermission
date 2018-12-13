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
                field: 'order_category_name',
                title: '订单类别',
                width: 100,
                align: 'center'
            },
            {
                field: 'ed_channel_name',
                title: '渠道',
                width: 80,
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
                width: 80,
                align: 'center'
            },
            {
                field: 'order_id',
                title: '订单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'materials_id',
                title: '物料号',
                width: 100,
                align: 'center'
            },
            {
                field: 'storage_id',
                title: '库位',
                width: 50,
                align: 'center'
            },
            {
                field: 't2_delivery_prediction',
                title: '数量',
                width: 50,
                align: 'center'
            },
            {
                field:'price',
                title:'样表价格',
                width:80,
                align:'center'
            },
            {
                field:'amount',
                title:'金额',
                width:70,
                align:'center'
            },
            {
                field: 'materials_desc',
                title: '型号',
                width: 200,
                align: 'center'
            },
            {
                field:'order_num_73',
                title:'参考单号(73单号)',
                width:200,
                align:'center'
            },
            {
                field:'send_flag',
                title:'更改发货方向标识(Y)',
                width:200,
                align:'center'
            },
            {
                field: 'flow_flag_name',
                title: '状态',
                width: 100,
                align: 'center'
            },
            {
                field: 'order_type_name',
                title: '订单类型',
                width: 70,
                align: 'center'
            },
            {
                field: 'channel_commit_user',
                title: '渠道提交人',
                width: 100,
                align: 'center'
            },
            {
                field: 'channel_commit_time_display',
                title: '渠道提交时间',
                width: 150,
                align: 'center'
            },
            {
                field: 'audit_depart_user',
                title: '产品部审核人',
                width: 100,
                align: 'center'
            },
            {
                field: 'audit_depart_time_display',
                title: '产品部审核时间',
                width: 150,
                align: 'center'
            },
            {
                field: 'audit_user',
                title: '内部审核人',
                width: 150,
                align: 'center'
            },
            {
                field: 'audit_time_display',
                title: '内部审核时间',
                width: 150,
                align: 'center'
            },
            {
                field: 'error_msg',
                title: '备注',
                width: 260,
                align: 'left'
            },
            {
                field: 'operation',
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


//点击查询
$('#search').click(function () {
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
    var cbsCategoryValue = $("#cbsCategory").combobox("getValue");
    //如果是ALL，品类设为空
    if(cbsCategoryValue=="全部"){
        cbsCategoryValue="";
    }
    //产品组保存
    $("#product_group_id_save").val(product_group);
    //渠道保存
    $("#ed_channel_id_save").val(channel);
    //品类保存
    $("#cbsCategory_save").val(cbsCategoryValue);
    //订单号保存
    $("#order_id_save").val($("#order_id").val());
    //物料号保存
    $("#materials_id_save").val($("#materials_id").val());
    //库位保存
    $("#storage_id_save").val($("#storage_id").val());
    //状态保存
    // var flow_flag = 5;
    var flow_flag = $("#flow_flag").val();
    $("#flow_flag_save").val(flow_flag);

    if(flow_flag.indexOf("ALL")>=0){
        flow_flag="";
    }
    //订单类别保存
    var order_category = $("#order_category").combobox("getValues").join(",");
    //如果包含ALL，设置为空
    if(order_category.indexOf("ALL")>=0){
        order_category="";
    }
    $("#order_category_save").val(order_category);

    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/purchase/findT2OrderReviewList",
        fit: true,
        pagination: true,
        singleSelect: true,
        pageSize: 100,
        pageList: [100,200,300],
        nowrap: false,
        rownumbers: true,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        queryParams: {
            report_year_week:$("#report_year_week").val(),
            channel:channel,
            product_group:product_group,
            cbsCategory:$("#cbsCategory_save").val(),
            order_id:$("#order_id").val(),
            materials_id:$("#materials_id").val(),
            storage_id:$("#storage_id").val(),
            flow_flag:flow_flag,
            order_category:order_category
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
                    field: 'order_category_name',
                    title: '订单类别',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'ed_channel_name',
                    title: '渠道',
                    width: 80,
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
                    width: 80,
                    align: 'center'
                },
                {
                    field: 'order_id',
                    title: '订单号',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'materials_id',
                    title: '物料号',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'storage_id',
                    title: '库位',
                    width: 50,
                    align: 'center'
                },
                {
                    field: 't2_delivery_prediction',
                    title: '数量',
                    width: 50,
                    align: 'center'
                },
                {
                    field:'price',
                    title:'样表价格',
                    width:80,
                    align:'center'
                },
                {
                    field:'amount',
                    title:'金额',
                    width:70,
                    align:'center'
                },
                {
                    field: 'materials_desc',
                    title: '型号',
                    width: 200,
                    align: 'center'
                },
                {
                    field:'order_num_73',
                    title:'参考单号(73单号)',
                    width:200,
                    align:'center'
                },
                {
                    field:'send_flag',
                    title:'更改发货方向标识(Y)',
                    width:200,
                    align:'center'
                },
                {
                    field: 'flow_flag_name',
                    title: '状态',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'order_type_name',
                    title: '订单类型',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 'channel_commit_user',
                    title: '渠道提交人',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'channel_commit_time_display',
                    title: '渠道提交时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'audit_depart_user',
                    title: '产品部审核人',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'audit_depart_time_display',
                    title: '产品部审核时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'audit_user',
                    title: '内部审核人',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'audit_time_display',
                    title: '内部审核时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'error_msg',
                    title: '备注',
                    width: 260,
                    align: 'left'
                },
                {
                    field: 'operation',
                    title: '操作',
                    width: 100,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
                        if(rowData.flow_flag==5){
                            //只有状态为待内部审核的行可以修改数量
                            return "<a href='javascript:void(0);' onclick='onModifycount("+'"'+rowData.order_id+'"'+','+'"'+rowData.t2_delivery_prediction+'"'+");return false;'>修改数量</a>";
                        }else{
                            return "";
                        }
                    }
                }
            ]
        ]
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);

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
        exportData.push(item.order_id);
    });
    $("#exportData").val(JSON.stringify(exportData));
    //判断是否都没选中
    if(exportData==null||exportData.length==0){
        $.messager.alert('错误','请至少选择一行要导出的行！','error');
        return;
    }
    $.messager.confirm('确认', '确定要导出吗？', function(r){
        if (r){
            $('#filterForm').attr("action", '/purchase/exportT2OrderList.export');
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
// 		var checkedItems = $('#datagrid').datagrid('getRows');
// 		var exportData = new Array();
// 		$.each(checkedItems, function(index, item){
// 				exportData.push(item.order_id);
// 		});
// 		$("#exportData").val(JSON.stringify(exportData));
    $.messager.confirm('确认','确定要全部导出吗？', function(r){
        if (r){
            $('#filterForm').attr("action", '/purchase/exportAllT2OrderList.export');
            $('#filterForm').form('submit');
        }
    });
});

//点击撤销
$('#revoke').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    var revokeData = new Array();
    $.each(checkedItems, function(index, item){
        if(item.flow_flag==10||item.flow_flag==15||item.flow_flag==20){
            //将状态为内部审核通过，OMS已审核和待OES评审的订单号推入数组
            revokeData.push(item.order_id);
        }
    });
    //判断是否选择了内部审核通过OMS已审核和待OES评审的订单
    if(revokeData==null||revokeData.length==0){
        $.messager.alert('错误','请至少选择一份状态为内部审核通过或OMS已审核或待OES评审的订单！','error');
        return;
    }
    $.messager.confirm('确认','确定要撤销吗？（只撤销状态为内部审核通过或OMS已审核或待OES评审的订单）', function(r){
        if (r){
            jQuery.ajax({
                url: "/purchase/revokeOrderList",   // 撤销的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"revokeData": JSON.stringify(revokeData)},
                success: function (data) {
                    if(data.success == true){
                        $('#datagrid').datagrid('reload',
                            {report_year_week:$("#report_year_week").val(),
                                channel:$("#ed_channel_id_save").val(),
                                product_group:$("#product_group_id_save").val(),
                                cbsCategory:$("#cbsCategory_save").val(),
                                order_id:$("#order_id_save").val(),
                                materials_id:$("#materials_id_save").val(),
                                storage_id:$("#storage_id_save").val(),
                                flow_flag:$("#flow_flag_save").val(),
                                order_category:$("#order_category_save").val()
                            });
                        $.messager.alert('成功', '撤销成功');

                    }else{
                        $.messager.alert('失败', data.message);
                    }
                }
            });
        }
    });
});

$(function(){
    $("#reviewDiv").panel('close');
});

//部分审核
var reviewData = new Array();
$("#review").click(function(){
    if(datagrid){
        var checkedItems = $('#datagrid').datagrid('getChecked');
        $.each(checkedItems, function(index, item){
            //flow_flag=5 待内部审核
            if(item.flow_flag==5){
                reviewData.push(item.order_id);
            }
        });
        if(reviewData==null||reviewData.length==0){
            $.messager.alert('错误','请至少选择一行状态为待内部审核的行！','error');
        }
        else{
            $("#reviewDiv").show();
            $("#reviewDiv").panel('open');
        }
    }else{
        $.messager.alert('提示', "请先查询！", 'info');
        return;
    }
    $("#reviewData").val(JSON.stringify(reviewData));
});

//点击全部审核
$("#reviewall").click(function(){
    if(datagrid){
        var checkedItems = $('#datagrid').datagrid('getRows');
        $.each(checkedItems, function(index, item){
            //flow_flag=5 待内部审核
            if(item.flow_flag==5){
                reviewData.push(item.order_id);
            }
        });
        if(reviewData==null||reviewData.length==0){
            $.messager.alert('错误','不存在状态为待内部审核的行！','error');
        }
        else{
            $("#reviewDiv").show();
            $("#reviewDiv").panel('open');
        }
    }else{
        $.messager.alert('提示', "请先查询！", 'info');
        return;
    }
    $("#reviewData").val(JSON.stringify(reviewData));
});

//审核通过
$('#passReview').click(function(){
    //审核通过FLAG（10:审核通过 -10：作废）
    var reviewFlag = '0';
    $("#reviewFlag").val(reviewFlag);
    $("#reviewData").val(JSON.stringify(reviewData));
    var audit_remark  = $('#audit_remark').val();
    $("#audit_remark").val(audit_remark);



    var flag = true;
    $('#reviewForm input').each(function () {
        if ($(this).attr('validType')) {
            if (!$(this).validatebox('isValid')) {
                flag = false;
                return;
            }
        }
    });

    if(flag){
        jQuery.ajax({
            url: "/purchase/reviewT2OrderReviewDetailList",   // 提交的页面
            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
            data:{
                "reviewFlag":$("#reviewFlag").val(),
                "reviewData": $("#reviewData").val(),
                "audit_remark": $("#audit_remark").val()
            },
            beforeSend: function(){
                var win = $.messager.progress({
                    title:'请等待',
                    msg:'数据保存中...',
                    text:'处理中',
                    interval:700
                });
            },
            complete: function(){
                //AJAX请求完成时隐藏loading提示
                $.messager.progress('close');
            },
            success: function (data) {
                if(data.data != null) {$.messager.alert('错误',data.data,'error');}
                else {
                    var message = data.message;
                    if(data.message == "success"){
                        $.messager.alert('提示',"审核通过状态更新成功！",'info',function(r){
                            $('#datagrid').datagrid('reload');
                            $('#reviewDiv').dialog("close");
                            // onGoBack();
                        });
                    }else{
                        $.messager.alert('提示',"审核通过状态更新失败！",'info');
                    }
                }}
        });
    }else{
        $.messager.alert('提示',"请输入正确数据",'error');
    }
});

//返回调用画面
function onGoBack(){
    var url = 't2OrderReviewList';
    window.location.href=url;
}

//返回查询页面
$("#close").click(function() {
    $("#reviewForm").form("clear");
    $("#reviewDiv").window('close');
    reviewData=[];
});

//作废
$('#nopassReview').click(function(){
    //审核通过FLAG
    var reviewFlag = '-3';
    var audit_remark  = $('#audit_remark').val();
    $("#audit_remark").val(audit_remark);
    var flag = true;
    $('#reviewForm input').each(function () {
        if ($(this).attr('validType')) {
            if (!$(this).validatebox('isValid')) {
                flag = false;
                return;
            }
        }
    });
    if(flag){
        jQuery.ajax({
            url: "/purchase/reviewT2OrderReviewDetailList",   // 提交的页面
            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
            data:{
                "reviewFlag":reviewFlag,
                //作废意见
                "audit_remark": $("#audit_remark").val(),
                //部分作废的数据
                "reviewData": JSON.stringify(reviewData),
                //作废全部的数据
                report_year_week:$("#report_year_week").val(),
                channel:$("#ed_channel_id_save").val(),
                product_group:$("#product_group_id_save").val(),
                cbsCategory:$("#cbsCategory_save").val(),
                order_id:$("#order_id_save").val(),
                materials_id:$("#materials_id_save").val(),
                storage_id:$("#storage_id_save").val(),
                flow_flag:$("#flow_flag_save").val(),
                order_category:$("#order_category_save").val()
            },
            beforeSend: function(){
                var win = $.messager.progress({
                    title:'请等待',
                    msg:'数据保存中...',
                    text:'处理中',
                    interval:700
                });
            },
            complete: function(){
                //AJAX请求完成时隐藏loading提示
                $.messager.progress('close');
            },
            success: function (data) {
                var message = data.message;
                if(data.data != null) {
                    $.messager.alert('错误',data.data,'error');
                    $("#reviewForm").form("clear");
                    reviewData=[];
                }
                else {
                    $.messager.alert('提示','作废完成','info');
                    $("#reviewForm").form("clear");
                    reviewData=[];
                }
                $("#reviewDiv").window('close');
                $('#datagrid').datagrid('reload');
                $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
            }
        });
    }else{
        $.messager.alert('提示',"请输入正确数据",'error');
    }
});

$.extend($.fn.validatebox.defaults.rules, {
    maxLength: {
        validator: function(value, param){
            return value.length <= param[0];
        },
        message: 'Please enter no more than {0} characters.'
    }
});

//修改数量
function onModifycount(order_id,t2_delivery_prediction){
    $("#modifycount").val(t2_delivery_prediction);
    $("#modifycountDiv").show();
    $("#modifycountDiv").dialog({
        collapsible: true,
        minimizable: false,
        maximizable: false,
        buttons: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler: function() {
                if($("#modifycount").val()>0&&$("#modifycount").val()<=200){
                    jQuery.ajax({
                        url: "/purchase/updateCount",   // 提交的页面
                        type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                        data:{"order_id": order_id,"modifycount":$("#modifycount").val(),"t2_delivery_prediction":t2_delivery_prediction},
                        success: function (data) {
                            if(data.totalCount==1){
                                $.messager.alert('成功','数量修改成功','info');
                            }else{
                                $.messager.alert('错误','数量修改失败,该行状态已被改变','error');
                            }

                            $('#datagrid').datagrid('reload',
                                {report_year_week:$("#report_year_week").val(),
                                    channel:$("#ed_channel_id_save").val(),
                                    product_group:$("#product_group_id_save").val(),
                                    cbsCategory:$("#cbsCategory_save").val(),
                                    order_id:$("#order_id_save").val(),
                                    materials_id:$("#materials_id_save").val(),
                                    storage_id:$("#storage_id_save").val(),
                                    flow_flag:$("#flow_flag_save").val(),
                                    order_category:$("#order_category_save").val()
                                });
                        }
                    });
                    $('#modifycountDiv').dialog('close');
                }else{
                    $.messager.alert('错误','数量必须大于0，小于等于200','error');
                }
            }
        }, {
            text: '取消',
            handler: function() {
                $('#modifycountDiv').dialog('close');
            }
        }]
    });
}

$(function () {

    jQuery.getJSON("/purchase/getProductGroupByAuth", function(result){
        var productgroup = result.data;
        productgroup.unshift({value:'ALL',valueMeaning:'全部'});
        $("#product_group").combobox({
            data:result.data,
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

    //取得流程状态列表--------
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
            panelHeight:'auto',
            editable:false,
            value:'ALL'
        });
    });

    //品类
    jQuery.getJSON("/purchase/getCbsCategoryByAuth", function(result){
        var JosnList=[];
        var cbsCategoryJson = {id: "全部", text: "全部"};
        JosnList.push(cbsCategoryJson)
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
    //取得订单类别列表
    var valueSetId = "order_category";
    jQuery.getJSON("/purchase/getByValueSetId"+ "?valueSetId=" + valueSetId, function(result){
        var count = result.totalCount;
        var dataList = result.data;
        //添加全部
        dataList.unshift( {value:'ALL',value_meaning:'全部'});
        $("#order_category").combobox({
            data:dataList,
            valueField:'value',
            textField:'value_meaning',
            panelHeight:'auto',
            editable:false,
            value:'ALL'
        });
    });
});