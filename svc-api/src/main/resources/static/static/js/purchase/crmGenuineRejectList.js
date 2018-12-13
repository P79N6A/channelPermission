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
                title: '退货单号',
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
                field: 'so_id',
                title: 'SO单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'dn_id',
                title: 'DN单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'vom_reverse_in_wa_no',
                title: '终止退货单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'commit_time_display',
                title: '提交时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'delivery_time_display',
                title: '提单时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'warehouse_out_time_display',
                title: '出WA库时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'warehouse_in_time_display',
                title: '入日日顺库时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'reverse_syn_vom_time_display',
                title: '终止退货提单时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'wa_in_time_displays',
                title: '终止退货入WA库时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'request_user',
                title: '申请人',
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
                field:'brand_name',
                title:'品牌',
                width:100,
                align:'center'
            },
            {
                field: 'materials_id',
                title: '物料号',
                width: 120,
                align: 'center'
            },
            {
                field: 'model_id',
                title: '型号',
                width: 300,
                align: 'center'
            },
            {
                field:'storage_id',
                title:'库位',
                width:80,
                align:'center'
            },
            {
                field: 'quantity',
                title: '数量',
                width: 100,
                align: 'center'
            },
            {
                field: 'tax_in_price',
                title: '含税价格',
                width: 100,
                align: 'center'
            },
            {
                field:'address',
                title:'地址',
                width:300,
                align:'left'
            },
            {
                field:'concat_person',
                title:'联系人',
                width:200,
                align:'center'
            },
            {
                field:'concat_phone',
                title:'联系电话',
                width:140,
                align:'center'
            },
            {
                field:'error_msg',
                title:'备注',
                width:300,
                align:'left'
            },
            {
                field: 'operation',
                title: '操作',
                width: 200,
                align: 'center'
            }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);



    //取得流程状态列表
    var valueSetId = "genuine_reject_flow_flag";
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
            value:'全部'
        });
    });
    //品牌
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
    //产品组
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
    //将退货单号存入Array
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
            $('#filterForm').attr("action", '/crmgenuinereject/exportCrmGenuineReject.export');
            $('#filterForm').submit();
        }
    });
});

//点击下载模板
$("#downloadtmp").click(function(){
    $('#filterForm').attr("action", '/crmgenuinereject/downloadCRMGenuineRejectTemplate');
    $('#filterForm').submit();
});

//点击提交
$('#commit').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    var commitData = new Array();
    //判断是否存在未提交的行
    $.each(checkedItems, function(index, item){
        //flow_flag=0 未提交
        if(item.flow_flag==0){
            commitData.push(item.wp_order_id);
        }
    });
    //判断是否存在未提交的行
    if(commitData==null||commitData.length==0){
        //alert("请至少选择一行状态为未提交的行！");
        $.messager.alert('错误','请至少选择一行状态为未提交的行！','error');
        return;
    }
    $.messager.confirm('确认','确定要提交吗？（只提交未提交的行）', function(r){
        if (r){
            jQuery.ajax({
                url: "/crmgenuinereject/commitCrmGenuineRejectList",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"commitData": JSON.stringify(commitData)},
                beforeSend:function(){
                    $('#msg').show();
                    $('#msgMask').show();
                },
                success: function (data) {
                    if(data.success){

                        $('#msg').hide();
                        $('#msgMask').hide();
                        $.messager.alert('提示','提交完成','info');
                        $('#datagrid').datagrid('reload',
                            {
                                wp_order_id:$("#wp_order_id_save").val(),
                                request_user:$("#request_user_save").val(),
                                flow_flag:$("#flow_flag_save").val(),
                                commit_time_start:$("#commit_time_start_save").val(),
                                commit_time_end:$("#commit_time_end_save").val(),
                                brand:$("#brand_save").val(),
                                cbsCategory:$("#cbsCategory_save").val(),
                                product_group:$("#product_group_save").val(),
                                materials_id:$("#materials_id_save").val(),
                                model_id:$("#model_id_save").val(),
                                channel:$("#channel_save").val(),
                                storage_id:$("#storage_id_save").val(),
                                so_id:$("#so_id_save").val()
                            });
                        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }else{
                        $.messager.alert('提示',data.message,'info');

                    }

                }
            });
        }
    });
});

//点击导入
$('#import').click(function(){
    var url = '/crmgenuinereject/gotoImportCrmReturnInfo';
    window.location.href=url;
    return false;
});

//点击取消
$('#cancel').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    if(checkedItems.length!=1){
        $.messager.alert('错误','请选择一行数据！','error');
        return;
    }
    var cancelData = new Array();
    $.each(checkedItems, function(index, item){
        if(item.flow_flag==20 || item.flow_flag==10 || item.flow_flag==15){
            //将状态为'20:待出WA库'的订单号推入数组
            cancelData.push(item.wp_order_id);
        }
    });
    //判断是否选择了'20:待出WA库'的订单
    if(cancelData==null||cancelData.length==0){
        $.messager.alert('错误','请选择一份状态为【已提交】【同步到VOM】【待出WA库】的订单！','error');
        return;
    }
    $.messager.confirm('确认','确定要取消吗？（只有状态为【已提交】【同步到VOM】【待出WA库】的退货单才可以取消）', function(r){
        if (r){
            jQuery.ajax({
                url: "/crmgenuinereject/cancelCrmGenuineRejectList",   // 取消的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"cancelData": JSON.stringify(cancelData)},
                success: function (data) {
                    if(data.message) {$.messager.alert('提示',data.message,'info');}
                    $('#datagrid').datagrid('reload',
                        {wp_order_id:$("#wp_order_id_save").val(),
                            request_user:$("#request_user_save").val(),
                            flow_flag:$("#flow_flag_save").val(),
                            commit_time_start:$("#commit_time_start_save").val(),
                            commit_time_end:$("#commit_time_end_save").val(),
                            brand:$("#brand_save").val(),
                            cbsCategory:$("#cbsCategory_save").val(),
                            product_group:$("#product_group_save").val(),
                            materials_id:$("#materials_id_save").val(),
                            model_id:$("#model_id_save").val(),
                            channel:$("#channel_save").val(),
                            storage_id:$("#storage_id_save").val(),
                            so_id:$("#so_id_save").val()
                        });
                }
            });
        }
    });
});


//点击取消入WA提单
$('#cancelInWa').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    //已终止退货的订单
    var stopData = new Array();
    //其他状态的订单
    var otherData = new Array();

    $.each(checkedItems, function(index, item){
        //flow_flag=35或50
        if(item.flow_flag==35 || item.flow_flag==50){
            stopData.push(item.wp_order_id);
        }else{
            otherData.push(item.wp_order_id);
        }
    });

    if((stopData==null||stopData.length==0)&&(otherData==null||otherData.length==0)){
        $.messager.alert('错误','请至少选择一行！','error');
        return;
    }else if(otherData!=null&&otherData.length>0){
        $.messager.alert('错误','请选择状态为【逆向同步到VOM】【待入WA库】的退货单！','error');
        return;
    }


    $.messager.confirm('确认','确定取消入WA提单吗？（只取消状态为【逆向同步到VOM】【待入WA库】的行）', function(r){
        if (r){
            jQuery.ajax({
                url: "/crmgenuinereject/cancelInWaCrmGenuineRejectList",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"stopData": JSON.stringify(stopData)},
                beforeSend:function(){
                    $('#msg').show();
                    $('#msgMask').show();
                },
                success: function (data) {
                    $('#msg').hide();
                    $('#msgMask').hide();
                    $.messager.alert('提示','取消入WA提单完成','info');
                    $('#datagrid').datagrid('reload',
                        {
                            wp_order_id:$("#wp_order_id_save").val(),
                            request_user:$("#request_user_save").val(),
                            flow_flag:$("#flow_flag_save").val(),
                            commit_time_start:$("#commit_time_start_save").val(),
                            commit_time_end:$("#commit_time_end_save").val(),
                            brand:$("#brand_save").val(),
                            cbsCategory:$("#cbsCategory_save").val(),
                            product_group:$("#product_group_save").val(),
                            materials_id:$("#materials_id_save").val(),
                            model_id:$("#model_id_save").val(),
                            channel:$("#channel_save").val(),
                            storage_id:$("#storage_id_save").val(),
                            so_id:$("#so_id_save").val()
                        });
                    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                }
            });
        }
    });
});

//点击终止退货
$('#stopRefund').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    var stopData = new Array();
    $.each(checkedItems, function(index, item){
        if(item.flow_flag==30){
            //将状态为'30:已出库'的订单号推入数组
            stopData.push(item.wp_order_id);
        }
    });
    //判断是否选择了'150:已出WA库'的订单
    if(stopData==null||stopData.length==0){
        $.messager.alert('错误','请至少选择一份状态为【已出WA库】的订单！','error');
        return;
    }
    $.messager.confirm('确认','确定要终止退货吗？（只有状态为【已出WA库】的退货单才可以终止退货）', function(r){
        if (r){
            jQuery.ajax({
                url: "/crmgenuinereject/stopCrmGenuineRejectList",
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"stopData": JSON.stringify(stopData)},
                success: function (data) {
                    if(data.message) {$.messager.alert('提示',data.message,'info');}
                    $('#datagrid').datagrid('reload',
                        {wp_order_id:$("#wp_order_id_save").val(),
                            request_user:$("#request_user_save").val(),
                            flow_flag:$("#flow_flag_save").val(),
                            commit_time_start:$("#commit_time_start_save").val(),
                            commit_time_end:$("#commit_time_end_save").val(),
                            brand:$("#brand_save").val(),
                            cbsCategory:$("#cbsCategory_save").val(),
                            product_group:$("#product_group_save").val(),
                            materials_id:$("#materials_id_save").val(),
                            model_id:$("#model_id_save").val(),
                            channel:$("#channel_save").val(),
                            storage_id:$("#storage_id_save").val(),
                            so_id:$("#so_id_save").val()
                        });
                }
            });
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
            $('#filterForm').attr("action", '/crmgenuinereject/exportAllCrmGenuineReject.export');
            $('#filterForm').submit();
        }
    });
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
    //判断是否存在未提交的行
    $.each(checkedItems, function(index, item){
        //flow_flag=0 未提交
        if(item.flow_flag==0){
            deleteData.push(item.wp_order_id);
        }
    });
    //判断是否存在未提交的行
    if(deleteData==null||deleteData.length==0){
        //alert("请至少选择一行状态为未提交的行！");
        $.messager.alert('错误','请至少选择一行状态为未提交的行！','error');
        return;
    }
    $.messager.confirm('确认','确定要删除吗？（只能删除未提交的行）', function(r){
        if(r){
            jQuery.ajax({
                url: "/crmgenuinereject/deleteCrmGenuineRejectList",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"deleteData": JSON.stringify(deleteData)},
                success: function (data) {
                    $.messager.alert('提示','删除完成','info');
                    $('#datagrid').datagrid('load',
                        {
                            wp_order_id:$("#wp_order_id_save").val(),
                            request_user:$("#request_user_save").val(),
                            flow_flag:$("#flow_flag_save").val(),
                            commit_time_start:$("#commit_time_start_save").val(),
                            commit_time_end:$("#commit_time_end_save").val(),
                            brand:$("#brand_save").val(),
                            cbsCategory:$("#cbsCategory_save").val(),
                            product_group:$("#product_group_save").val(),
                            materials_id:$("#materials_id_save").val(),
                            model_id:$("#model_id_save").val(),
                            channel:$("#channel_save").val(),
                            storage_id:$("#storage_id_save").val(),
                            so_id:$("#so_id_save").val()
                        });
                    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                }
            });
        }
    });
});

//订单履历
function opration(wp_order_id){
    $("#oprationWindow").show();
    $("#oprationWindow").window('open');
    $('#oprationDataGrid').datagrid({
        url: "/crmgenuinereject/crmOrderOperation",
        fit: true,
        singleSelect: true,
        nowrap: false,
        rownumbers: true,
        queryParams: {
            "order_id": wp_order_id
        },
        columns: [
            [
                {
                    field: 'Id',
                    title: '编号',
                    width: 50,
                    align: 'center'
                },
                {
                    field: 'order_id',
                    title: '订单号',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'type_name',
                    title: '原状态',
                    width: 100,
                    align: 'center',
                },
                {
                    field: 'is_sucess',
                    title: '是否成功',
                    width: 100,
                    align: 'center',
                    formatter:function(value){
                        if(value==1)
                            return '成功';
                        if(value==0)
                            return '失败';
                    }
                },
                {
                    field: 'content',
                    title: '更改内容',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'remark',
                    title: '备注',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'log_time',
                    title: '操作时间',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'operate_user',
                    title: '操作人',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'system',
                    title: '操作系统',
                    width:100,
                    align: 'center'
                }

            ]
        ]
    });
}
//构筑订单履历的dialog
$('#oprationWindow').window({
    shadow:false,
    resizable:false,
    autoOpen: false,
    closed:true,
    width:1100,
    height:400,
    collapsible:false,
    minimizable:false,
    maximizable:false,
    modal:true,
    closeOnEscape:true,
});

//点击查询
$('#search').click(function () {
    if($('#commit_time_start').datebox('getValue') && $('#commit_time_end').datebox('getValue')){
        if($('#commit_time_start').datebox('getValue')>$('#commit_time_end').datebox('getValue')){
            $.messager.alert('错误','提交日开始不能大于提交日结束','error');
            return false;
        }
    }
    var request_user = $("#request_user").val();
    //如果是ALL，申请人设为空
    if(request_user=="ALL"){
        request_user="";
    }
    //状态保存
    var flow_flag = $("#flow_flag").combobox("getValues").join(",");
    //如果包含ALL，设置为空
    if(flow_flag.indexOf("ALL")>=0){
        flow_flag="";
    }
    var brandValue = $("#brand").combobox("getValue");
    //如果是ALL，品牌设为空
    if(brandValue=="ALL"){
        brandValue="";
    }
    var cbsCategoryValue = $("#cbsCategory").combobox("getValue");
    //如果是ALL，品类设为空
    if(cbsCategoryValue=="全部"){
        cbsCategoryValue="";
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
    //退货单号(WP)保存
    $("#wp_order_id_save").val($("#wp_order_id").val());
    //申请人保存
    $("#request_user_save").val(request_user);
    //状态保存
    $("#flow_flag_save").val(flow_flag);
    //提交日开始保存
    $("#commit_time_start_save").val($("#commit_time_start").datebox('getValue'));
    //提交日结束保存
    $("#commit_time_end_save").val($("#commit_time_end").datebox('getValue'));
    //品牌保存
    $("#brand_save").val(brandValue);
    //品类保存
    $("#cbsCategory_save").val(cbsCategoryValue);
    //产品组保存
    $("#product_group_save").val(product_group);
    //物料号保存
    $("#materials_id_save").val($("#materials_id").val());
    //型号保存
    $("#model_id_save").val($("#model_id").val());
    //渠道保存
    $("#channel_save").val(channel);
    //库位保存
    $("#storage_id_save").val($("#storage_id").val());
    //so单号
    $("#so_id_save").val($("#so_id").val());
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url : "/crmgenuinereject/findCrmGenuineRejectList",
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
            wp_order_id:$("#wp_order_id_save").val(),
            request_user:$("#request_user_save").val(),
            flow_flag:$("#flow_flag_save").val(),
            commit_time_start:$("#commit_time_start_save").val(),
            commit_time_end:$("#commit_time_end_save").val(),
            brand:$("#brand_save").val(),
            cbsCategory:$("#cbsCategory_save").val(),
            product_group:$("#product_group_save").val(),
            materials_id:$("#materials_id_save").val(),
            model_id:$("#model_id_save").val(),
            channel:$("#channel_save").val(),
            storage_id:$("#storage_id_save").val(),
            so_id:$("#so_id_save").val()
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
                field: 'wp_order_id',
                title: '退货单号',
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
                field: 'so_id',
                title: 'SO单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'dn_id',
                title: 'DN单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'vom_reverse_in_wa_no',
                title: '终止退货单号',
                width: 120,
                align: 'center'
            },
            {
                field: 'commit_time_display',
                title: '提交时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'delivery_time_display',
                title: '提单时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'warehouse_out_time_display',
                title: '出WA库时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'warehouse_in_time_display',
                title: '入日日顺库时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'reverse_syn_vom_time_display',
                title: '终止退货提单时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'wa_in_time_displays',
                title: '终止退货入WA库时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'request_user',
                title: '申请人',
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
                field:'brand_name',
                title:'品牌',
                width:100,
                align:'center'
            },
            {
                field: 'materials_id',
                title: '物料号',
                width: 120,
                align: 'center'
            },
            {
                field: 'model_id',
                title: '型号',
                width: 300,
                align: 'center'
            },
            {
                field:'storage_id',
                title:'库位',
                width:80,
                align:'center'
            },
            {
                field: 'quantity',
                title: '数量',
                width: 100,
                align: 'center'
            },
            {
                field: 'tax_in_price',
                title: '含税价格',
                width: 100,
                align: 'center'
            },
            {
                field:'address',
                title:'地址',
                width:300,
                align:'left'
            },
            {
                field:'concat_person',
                title:'联系人',
                width:200,
                align:'center'
            },
            {
                field:'concat_phone',
                title:'联系电话',
                width:140,
                align:'center'
            },
            {
                field:'error_msg',
                title:'备注',
                width:300,
                align:'left'
            },
            {
                field: 'operation',
                title: '操作',
                width: 200,
                align: 'center',
                formatter:function(value,rowData,rowIndex){
                    //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
                    return "<a class='opration' href='javascript:void(0);' onclick='opration("+'"'+rowData.wp_order_id+'"'+");return false;'>订单履历</a>";
                }
            }
        ] ],
        toolbar: '#datagridToolbar',
        onLoadSuccess:function(data){
            $('.opration').linkbutton({text:'订单履历',plain:true,iconCls:'icon-search'});
        }
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);

});

