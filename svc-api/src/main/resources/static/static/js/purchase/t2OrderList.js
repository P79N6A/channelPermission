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
        columns: [[ {
            field: 'id',
            title: '序号',
            width: 10,
            align: 'center',
            hidden:true
        },
            {
                field: 'checked',
                width: 10,
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
                field:'order_id',
                title:'订单号',
                width:120,
                align:'center'
            },{
                field:'materials_id',
                title:'物料号',
                width:100,
                align:'center'
            },
            {
                field:'storage_id',
                title:'库位',
                width:50,
                align:'center'
            },
            {
                field:'t2_delivery_prediction',
                title:'数量',
                width:50,
                align:'center'
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
                field:'materials_desc',
                title:'型号',
                width:200,
                align:'center'
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
                width: 80,
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
                width: 100,
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
            }]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);

    //点击导入
    $('#import').click(function(){
        var report_year_week = $("#report_year_week").val().replace("年","").replace("周","");
        var url = '/purchase/importT2Order' + '?report_year_week=' + report_year_week;
        window.location.href=url;
        return false;
    })

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
                commitData.push(item.order_id);
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
                    url: "/purchase/commitOrderList",   // 提交的页面
                    type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                    data:{"commitData": JSON.stringify(commitData)},

                    success: function (data) {
                        $('#msg').hide();
                        $('#msgMask').hide();
                        if(data.data != null) {$.messager.alert('错误',data.data,'error');}
                        else {
                            $.messager.alert('提示','提交完成','info');
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
                            $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                        }}
                });
            }
        });
    });


//点击全部提交
    $('#commitall').click(function(){
        if(!datagrid){
            $.messager.alert('提示','请查询！','info');
            return;
        }
        $.messager.confirm('确认','确定要提交吗？（只提交未提交的行）', function(r){
            if(r){
                var params = {report_year_week:$("#report_year_week").val(),
                    channel:$("#ed_channel_id_save").val(),
                    cbsCategory:$("#cbsCategory_save").val(),
                    product_group:$("#product_group_id_save").val(),
                    order_id:$("#order_id_save").val(),
                    materials_id:$("#materials_id_save").val(),
                    storage_id:$("#storage_id_save").val(),
                    flow_flag:$("#flow_flag_save").val(),
                    order_category:$("#order_category_save").val()
                };
                jQuery.ajax({
                    url: "/purchase/commitAllOrderList",   // 提交的页面
                    type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                    data:params,
                    beforeSend:function(){
                        $('#msg').show();
                        $('#msgMask').show();
                    },
                    success: function (data) {
                        $('#msg').hide();
                        $('#msgMask').hide();
                        if(data.data != null) {$.messager.alert('错误',data.data,'error');}
                        else {
                            $.messager.alert('提示','全部提交完成','info');
                            $('#datagrid').datagrid('reload', params);
                            $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                        }}
                });
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
        //判断是否存在未提交或者待内部审核的行
        $.each(checkedItems, function(index, item){
            //flow_flag=0 未提交或者flow_flag=5 待内部审核或者flow_flag==3待产品部审核
            if(item.flow_flag==0 ||item.flow_flag==3|| item.flow_flag==5){
                deleteData.push(item.order_id);
            }
        });
        //判断是否存在未提交或者待内部审核的行
        if(deleteData==null||deleteData.length==0){
            //alert("请至少选择一行状态为未提交或者待内部审核的行！");
            $.messager.alert('错误','请至少选择一行状态为未提交或者待产品部审核或者待内部审核的行！','error');
            return;
        }
        $.messager.confirm('确认','确定要删除吗？（只能删除未提交或者待产品部审核或者待内部审核的行）', function(r){
            if(r){
                jQuery.ajax({
                    url: "/purchase/deleteOrderList",   // 提交的页面
                    type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                    data:{"deleteData": JSON.stringify(deleteData)},
                    success: function (data) {
                        $.messager.alert('提示','删除完成','info');
                        $('#datagrid').datagrid('load',
                            {report_year_week:$("#report_year_week").val(),
                                channel:$("#ed_channel_id_save").val(),
                                cbsCategory:$("#cbsCategory_save").val(),
                                product_group:$("#product_group_id_save").val(),
                                order_id:$("#order_id_save").val(),
                                materials_id:$("#materials_id_save").val(),
                                storage_id:$("#storage_id_save").val(),
                                flow_flag:$("#flow_flag_save").val(),
                                order_category:$("#order_category_save").val()
                            });
                        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                });
            }
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
            exportData.push(item.order_id);
        });
        $("#exportData").val(JSON.stringify(exportData));
        //判断是否都没选中
        if(exportData==null||exportData.length==0){
            //alert("请至少选择一行要导出的行！");
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
        $.messager.confirm('确认','确定要全部导出吗？<br><em style="color: red;">提示：先查询，再导出！</em>', function(r){
            if (r){
                $('#filterForm').attr("action", '/purchase/exportAllT2OrderList.export');
                $('#filterForm').form('submit');
            }
        });
    });
//点击下载模板
    $("#downloadtmp").click(function(){
        $('#filterForm').attr("action", '/purchase/exportT2OrderModel');
        $('#filterForm').submit();
    });

    
    
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


    //品类---------------
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
    var flow_flag = $("#flow_flag").combobox("getValues").join(",");
    //如果包含ALL，设置为空
    if(flow_flag.indexOf("ALL")>=0){
        flow_flag="";
    }
    $("#flow_flag_save").val(flow_flag);
    //订单类别保存
    var order_category = $("#order_category").combobox("getValues").join(",");
    //如果包含ALL，设置为空
    if(order_category.indexOf("ALL")>=0){
        order_category="";
    }
    $("#order_category_save").val(order_category);

    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/purchase/findT2QueryCommitOrderList",
        fit: true,
        pagination: true,
        singleSelect: false,
        checkOnSelect:true,
        pageSize: 100,
        pageList: [100,200,300],
        nowrap: false,
        rownumbers: true,
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
                    field: 'id',
                    title: '序号',
                    width: 10,
                    align: 'center',
                    hidden:true
                },
                {
                    field: 'checked',
                    width: 10,
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
                    field:'order_id',
                    title:'订单号',
                    width:120,
                    align:'center'
                },{
                field:'materials_id',
                title:'物料号',
                width:100,
                align:'center'
            },
                {
                    field:'storage_id',
                    title:'库位',
                    width:50,
                    align:'center'
                },
                {
                    field:'t2_delivery_prediction',
                    title:'数量',
                    width:50,
                    align:'center'
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
                    field:'materials_desc',
                    title:'型号',
                    width:200,
                    align:'center'
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
                    width: 80,
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
                    width: 100,
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
                }
            ]
        ]
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);

});

