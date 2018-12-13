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
        columns: [[{
            field: 'check',
            title: '全选',
            width: 100,
            align: 'center',
            checkbox:true
        }, {
            field : 'whCode',
            title : '仓库（TC）代码',
            width : 140,
            align : 'center'
        },{
            field : 'whName',
            title : '仓库名称',
            width : 100,
            align : 'center'
        },{
            field : 'estorge_id',
            title : '电商库位码',
            width : 100,
            align : 'center'
        },{
            field : 'estorge_name',
            title : '电商库位名称',
            width : 100,
            align : 'center'
        },{
            field : 'transmit_id',
            title : '送达方代码',
            width : 100,
            align : 'center'
        },{
            field : 'transmit_desc',
            title : '送达方名称',
            width : 300,
            align : 'center'
        },{
            field : 'custom_id',
            title : '管理客户编码',
            width : 100,
            align : 'center'
        },{
            field : 'custom_desc',
            title : '管理客户名称',
            width : 300,
            align : 'center'
        },{
            field : 'rrs_distribution_id',
            title : '日日顺配送编码',
            width : 100,
            align : 'center'
        },{
            field : 'rrs_distribution_name',
            title : '日日顺配送名称',
            width : 150,
            align : 'center'
        },{
            field : 'centerCode',
            title : '网单中心代码',
            width : 100,
            align : 'center'
        },{
            field : 'les_OE_id',
            title : '0E码（LES）',
            width : 100,
            align : 'center'
        },{
            field : 'industry_trade_id',
            title : '工贸代码',
            width : 100,
            align : 'center'
        },{
            field : 'industry_trade_desc',
            title : '工贸描述',
            width : 100,
            align : 'center'
        },{
            field : 'graininess_id',
            title : '颗粒度编码',
            width : 200,
            align : 'center'
        },{
            field : 'net_management_id',
            title : '网单经营体编码',
            width : 200,
            align : 'center'
        },{
            field : 'esale_id',
            title : '电商售达方编码',
            width : 200,
            align : 'center'
        },{
            field : 'esale_name',
            title : '电商售达方名称',
            width : 300,
            align : 'center'
        },{
            field : 'sale_org_id',
            title : '销售组织编码',
            width : 100,
            align : 'center'
        },{
            field : 'branch',
            title : '分公司地址',
            width : 300,
            align : 'center'
        },{
            field : 'payment_id',
            title : '电商付款方编码',
            width : 100,
            align : 'center'
        },{
            field : 'payment_name',
            title : '电商付款方名称',
            width : 300,
            align : 'center'
        },{
            field : 'area_id',
            title : '地区编码（CRM专用）',
            width : 150,
            align : 'center'
        },{
            field : 'rrs_sale_id',
            title : '日日顺售达方',
            width : 150,
            align : 'center'
        },{
            field : 'rrs_sale_name',
            title : '日日顺售达方名称',
            width : 300,
            align : 'center'
        },{
            field : 'oms_rrs_payment_id',
            title : 'OMS重庆新日日顺付款方',
            width : 300,
            align : 'center'
        },{
            field : 'oms_rrs_payment_name',
            title : 'OMS重庆新日日顺付款方名称',
            width : 300,
            align : 'center'
        },{
            field : 'city_code',
            title : '城市编码',
            width : 300,
            align : 'center'
        },{
            field : 'city_desc',
            title : '城市描述',
            width : 300,
            align : 'center'
        },{
            field : 'createUser',
            title : '创建者',
            width : 100,
            align : 'center'
        },{
            field : 'createTime',
            title : '创建时间',
            width : 200,
            align : 'center'
        },{
            field : 'updateUser',
            title : '最后更新人',
            width : 100,
            align : 'center'
        },{
            field : 'updateTime',
            title : '最后更新时间',
            width : 200,
            align : 'center'
        },{
            field : 'is_enabled_name',
            title : '启用/禁用状态',
            width : 100,
            align : 'center'
        }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }


    //城市下拉框
    jQuery.getJSON("/invWarehouse/getinvBaseCityCodeCombobox", function(result){
        $("#city_desc_update,#city_desc_create").combobox({
            data:result.rows,
            valueField:'city_code',
            textField:'city_desc',
            panelHeight:'300',
            editable:true,
            novalidate:true
        });
    });

    $('#datagrid').datagrid(datagrid);


    $("#update").on('click', function () {
        if(datagrid){
            var selected = $('#datagrid').datagrid('getSelections');
            $('#updateDiv').dialog({'title': '修改'});
            if (selected.length == 1) {
                $("#updateForm").form("clear");
                $('#updateForm').form('load', selected[0]);
                $('#updateForm').find('[__actType]').val('edit');
                $('#updateDiv').dialog('open');
            } else {
                alert("请选择一条数据");
            }
        }else{
            $.messager.alert('提示', "请先查询！", 'info');
            return;
        }
    }).error(function (errorObj, statusText) {
        alert(statusText);
    });

    //确定修改按钮
    $("#updateConfirm").click(function() {
        var updateFlag = true;
        $("#updateForm").form("enableValidation");
        updateFlag=$("#updateForm").form('validate');
        if ($(this).linkbutton('options').disabled == false) {
            if(updateFlag){
                $('#updateConfirm').linkbutton('disable');
                var updateData = new Array();
                updateData.push($("#wh_code_update").val());
                updateData.push($("#wh_name_update").val());
                updateData.push(0);
                updateData.push($("#center_code_update").val());
                updateData.push(0);
                updateData.push("noaccepter");
                updateData.push($("#estorge_id_update").val());
                updateData.push($("#estorge_name_update").val());
                updateData.push($("#transmit_id_update").val());
                updateData.push($("#transmit_desc_update").val());
                updateData.push($("#les_OE_id_update").val());
                updateData.push($("#custom_id_update").val());
                updateData.push($("#custom_desc_update").val());
                updateData.push($("#industry_trade_id_update").val());
                updateData.push($("#industry_trade_desc_update").val());
                updateData.push($("#graininess_id_update").val());
                updateData.push($("#net_management_id_update").val());
                updateData.push($("#esale_id_update").val());
                updateData.push($("#esale_name_update").val());
                updateData.push($("#sale_org_id_update").val());
                updateData.push($("#branch_update").val());
                updateData.push($("#payment_id_update").val());
                updateData.push($("#payment_name_update").val());
                updateData.push($("#area_id_update").val());
                updateData.push($("#rrs_distribution_id_update").val());
                updateData.push($("#rrs_distribution_name_update").val());
                updateData.push($("#rrs_sale_id_update").val());
                updateData.push($("#rrs_sale_name_update").val());
                updateData.push($("#oms_rrs_payment_id_update").val());
                updateData.push($("#oms_rrs_payment_name_update").val());
                updateData.push($("#city_desc_update").combobox("getText"));
                updateData.push($("#city_desc_update").combobox("getValue"));
                $.messager.confirm('确认','确定要修改吗？', function(r){
                    if (r){
                        jQuery.ajax({
                            url: "/invWarehouse/updateInvWarehouse",
                            type: "POST",
                            data:{"updateData": JSON.stringify(updateData)},
                            success: function (data) {
                                $.messager.alert('提示','修改完成','info');
                                $('#updateConfirm').linkbutton('enable');
                                // $("#updateForm").form("disableValidation");
                                $('#datagrid').datagrid('load');
                                $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                                $(':input','#updataForm')
                                    .not(':button, :submit, :reset, :hidden')
                                    .val('')
                                    .removeAttr('checked')
                                    .removeAttr('selected');
                                $("#wh_code_update").val("");
                                $("#updateDiv").window('close');
                            }
                        });
                    }else{
                        $('#updateConfirm').linkbutton('enable');
                    }
                });
                $('.panel-tool-close').hide();
            }
        }
    });

    $("#create").on('click', function () {
        $('#createForm').form('reset');
        $('#createForm').find('[__actType]').val('add');
        $('#createDiv').dialog({'title': i18n['message.act.add']});


        $("#createForm").form("clear");

        return $('#createDiv').dialog('open').dialog('setTitle', '创建信息');
    });


//确认创建按钮
    $("#createConfirm").click( function() {
        var createFlag= true;
        $("#createForm").form("enableValidation");
        createFlag=$("#createForm").form('validate');
        if ($(this).linkbutton('options').disabled == false) {
            if(createFlag){
                $('#createConfirm').linkbutton('disable');
                var createData = new Array();
                createData.push($("#wh_code_create").val());
                createData.push($("#wh_name_create").val());
                createData.push(0);
                createData.push($("#center_code_create").val());
                createData.push(0);
                createData.push("noaccepter");
                createData.push($("#estorge_id_create").val());
                createData.push($("#estorge_name_create").val());
                createData.push($("#transmit_id_create").val());
                createData.push($("#transmit_desc_create").val());
                createData.push($("#les_OE_id_create").val());
                createData.push($("#custom_id_create").val());
                createData.push($("#custom_desc_create").val());
                createData.push($("#industry_trade_id_create").val());
                createData.push($("#industry_trade_desc_create").val());
                createData.push($("#graininess_id_create").val());
                createData.push($("#net_management_id_create").val());
                createData.push($("#esale_id_create").val());
                createData.push($("#esale_name_create").val());
                createData.push($("#sale_org_id_create").val());
                createData.push($("#branch_create").val());
                createData.push($("#payment_id_create").val());
                createData.push($("#payment_name_create").val());
                createData.push($("#area_id_create").val());
                createData.push($("#rrs_distribution_id_create").val());
                createData.push($("#rrs_distribution_name_create").val());
                createData.push($("#rrs_sale_id_create").val());
                createData.push($("#rrs_sale_name_create").val());
                createData.push($("#oms_rrs_payment_id_create").val());
                createData.push($("#oms_rrs_payment_name_create").val());
                createData.push($("#city_desc_create").combobox("getText"));
                createData.push($("#city_desc_create").combobox("getValue"));
                $.messager.confirm('确认','确定要创建吗？', function(r){
                    if (r){
                        jQuery.ajax({
                            url: "/invWarehouse/createInvWarehouse",
                            type: "POST",
                            data:{"createData": JSON.stringify(createData)},
                            dataType:"JSON",
                            success: function (data) {
                                if(data.resultStatus==0){
                                    $.messager.alert('提示','创建失败,仓库（TC）代码已存在','info');
                                }else{
                                    $.messager.alert('提示','创建完成','info');
                                }
                                $('#datagrid').datagrid('reload');
                                $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                                $(':input','#createForm')
                                    .not(':button, :submit, :reset, :hidden')
                                    .val('')
                                    .removeAttr('checked')
                                    .removeAttr('selected');
                                $("#createDiv").window('close');
                                $('#createConfirm').linkbutton('enable');
                                // $("#createForm").form("disableValidation");
                            }
                        });
                    }else{
                        $('#createConfirm').linkbutton('enable');
                    }
                });
                $('.panel-tool-close').hide();
            }
        }
    });
//删除
    $("#delete").click(function(){
        if(!datagrid){
            $.messager.alert('提示', "请先查询！", 'info');
            return;
        }
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var deleteData = new Array();
        $.each(checkedItems, function(index, item){
            deleteData.push(item.whCode);
        });
        if(deleteData==null||deleteData.length==0){
            $.messager.alert('错误','请选择需要删除的行！','error');
            return;
        }
        $.messager.confirm('确认','确定要删除吗？', function(r){
            if(r){
                jQuery.ajax({
                    url: "/invWarehouse/deleteInvWarehouse",
                    type: "POST",
                    data:{"deleteData": JSON.stringify(deleteData)},
                    success: function (data) {
                        $.messager.alert('提示','删除完成','info');
                        $('#datagrid').datagrid('load');
                        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                });
            }
        });
    });

//启用
    $("#openStatus").click(function(){
        if(!datagrid){
            $.messager.alert('提示', "请先查询！", 'info');
            return;
        }
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var openData = new Array();
        $.each(checkedItems, function(index, item){
            openData.push(item.whCode);
        });
        if(openData==null||openData.length==0){
            $.messager.alert('错误','请选择需要启用的行！','error');
            return;
        }
        $.messager.confirm('确认','确定要启用吗？', function(r){
            if(r){
                jQuery.ajax({
                    url: "/invWarehouse/openStatusInvWarehouse",
                    type: "POST",
                    data:{"openData": JSON.stringify(openData)},
                    success: function (data) {
                        $.messager.alert('提示','启用完成','info');
                        $('#datagrid').datagrid('load');
                        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                });
            }
        });
    });

//禁用
    $("#closeStatus").click(function(){
        if(!datagrid){
            $.messager.alert('提示', "请先查询！", 'info');
            return;
        }
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var closeData = new Array();
        $.each(checkedItems, function(index, item){
            closeData.push(item.whCode);
        });
        if(closeData==null||closeData.length==0){
            $.messager.alert('错误','请选择需要禁用的行！','error');
            return;
        }
        $.messager.confirm('确认','确定要禁用吗？', function(r){
            if(r){
                jQuery.ajax({
                    url: "/invWarehouse/closeStatusInvWarehouse",
                    type: "POST",
                    data:{"closeData": JSON.stringify(closeData)},
                    success: function (data) {
                        $.messager.alert('提示','禁用完成','info');
                        $('#datagrid').datagrid('load');
                        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                });
            }
        });
    });

//导出
    $('#export').click(function(){

        if(!datagrid){
            $.messager.alert('提示','请查询！','info');
            return;
        }
        if(window.total && window.total > 20000){
            $.messager.alert('提示', '最多导出20000条记录，请缩小查询范围！', 'info');
            return;
        }
        //获得选中行
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var exportData = new Array();
        //将订单号存入Array
        $.each(checkedItems, function(index, item){
            exportData.push(item.whCode);
        });
        $("#exportData").val(JSON.stringify(exportData));
        //判断是否都没选中
        if(exportData==null||exportData.length==0){
            $.messager.alert('错误','请至少选择一行要导出的行！','error');
            return;
        }
        $.messager.confirm('确认', '确定要导出吗？', function(r){
            if (r){
                $('#invWarehouse').attr("action", '/invWarehouse/invWarehouseExport.export');
                $('#invWarehouse').submit();
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
                $('#invWarehouse').attr("action", '/invWarehouse/invWarehouseAllExport.export');
                $('#invWarehouse').submit();
            }
        });
    });
});


//点击查询
$('#search').click(function () {
    $("#wh_code_hidden").val($("#wh_code").val());
    $("#estorge_name_hidden").val($("#estorge_name").val());
    $("#transmit_id_hidden").val($("#transmit_id").val());
    $("#transmit_desc_hidden").val($("#transmit_desc").val());
    $("#custom_id_hidden").val($("#custom_id").val());
    $("#rrs_distribution_id_hidden").val($("#rrs_distribution_id").val());
    $("#rrs_distribution_name_hidden").val($("#rrs_distribution_name").val());
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url : "/invWarehouse/searchInvWarehouse",
        fit:true,
        pagination: true,
        pageSize:100,
        pageList:[100,200,300],
        nowrap: false,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        fitColumns:false,
        rownumbers: true,
        queryParams : {
            wh_code : $("#wh_code_hidden").val(),
            estorge_name : $("#estorge_name_hidden").val(),
            transmit_id : $("#transmit_id_hidden").val(),
            transmit_desc : $("#transmit_desc_hidden").val(),
            custom_id : $("#custom_id_hidden").val(),
            rrs_distribution_id : $("#rrs_distribution_id_hidden").val(),
            rrs_distribution_name : $("#rrs_distribution_name_hidden").val(),
        },loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        columns : [ [ {
            field: 'check',
            title: '全选',
            width: 100,
            align: 'center',
            checkbox:true
        }, {
            field : 'whCode',
            title : '仓库（TC）代码',
            width : 140,
            align : 'center'
        },{
            field : 'whName',
            title : '仓库名称',
            width : 100,
            align : 'center'
        },{
            field : 'estorge_id',
            title : '电商库位码',
            width : 100,
            align : 'center'
        },{
            field : 'estorge_name',
            title : '电商库位名称',
            width : 100,
            align : 'center'
        },{
            field : 'transmit_id',
            title : '送达方代码',
            width : 100,
            align : 'center'
        },{
            field : 'transmit_desc',
            title : '送达方名称',
            width : 300,
            align : 'center'
        },{
            field : 'custom_id',
            title : '管理客户编码',
            width : 100,
            align : 'center'
        },{
            field : 'custom_desc',
            title : '管理客户名称',
            width : 300,
            align : 'center'
        },{
            field : 'rrs_distribution_id',
            title : '日日顺配送编码',
            width : 100,
            align : 'center'
        },{
            field : 'rrs_distribution_name',
            title : '日日顺配送名称',
            width : 150,
            align : 'center'
        },{
            field : 'centerCode',
            title : '网单中心代码',
            width : 100,
            align : 'center'
        },{
            field : 'les_OE_id',
            title : '0E码（LES）',
            width : 100,
            align : 'center'
        },{
            field : 'industry_trade_id',
            title : '工贸代码',
            width : 100,
            align : 'center'
        },{
            field : 'industry_trade_desc',
            title : '工贸描述',
            width : 100,
            align : 'center'
        },{
            field : 'graininess_id',
            title : '颗粒度编码',
            width : 200,
            align : 'center'
        },{
            field : 'net_management_id',
            title : '网单经营体编码',
            width : 200,
            align : 'center'
        },{
            field : 'esale_id',
            title : '电商售达方编码',
            width : 200,
            align : 'center'
        },{
            field : 'esale_name',
            title : '电商售达方名称',
            width : 300,
            align : 'center'
        },{
            field : 'sale_org_id',
            title : '销售组织编码',
            width : 100,
            align : 'center'
        },{
            field : 'branch',
            title : '分公司地址',
            width : 300,
            align : 'center'
        },{
            field : 'payment_id',
            title : '电商付款方编码',
            width : 100,
            align : 'center'
        },{
            field : 'payment_name',
            title : '电商付款方名称',
            width : 300,
            align : 'center'
        },{
            field : 'area_id',
            title : '地区编码（CRM专用）',
            width : 150,
            align : 'center'
        },{
            field : 'rrs_sale_id',
            title : '日日顺售达方',
            width : 150,
            align : 'center'
        },{
            field : 'rrs_sale_name',
            title : '日日顺售达方名称',
            width : 300,
            align : 'center'
        },{
            field : 'oms_rrs_payment_id',
            title : 'OMS重庆新日日顺付款方',
            width : 300,
            align : 'center'
        },{
            field : 'oms_rrs_payment_name',
            title : 'OMS重庆新日日顺付款方名称',
            width : 300,
            align : 'center'
        },{
            field : 'city_code',
            title : '城市编码',
            width : 300,
            align : 'center'
        },{
            field : 'city_desc',
            title : '城市描述',
            width : 300,
            align : 'center'
        },{
            field : 'createUser',
            title : '创建者',
            width : 100,
            align : 'center'
        },{
            field : 'createTime',
            title : '创建时间',
            width : 200,
            align : 'center'
        },{
            field : 'updateUser',
            title : '最后更新人',
            width : 100,
            align : 'center'
        },{
            field : 'updateTime',
            title : '最后更新时间',
            width : 200,
            align : 'center'
        },{
            field : 'is_enabled_name',
            title : '启用/禁用状态',
            width : 100,
            align : 'center'
        }
        ] ],
        toolbar: '#datagridToolbar'
    });
});

