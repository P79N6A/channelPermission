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
                field : 'sCode',
                title : '库位码',
                width : 120,
                align : 'center'
            },{
                field : 'centerName',
                title : '所属中心',
                width : 120,
                align : 'center'
            },{
                field : 'cCode',
                title : '中心代码',
                width : 140,
                align : 'center'
            },{
                field : 'province',
                title : '所属省级',
                width : 120,
                align : 'center'
            },{
                field : 'city',
                title : '市',
                width : 100,
                align : 'center'
            },{
                field : 'county',
                title : '县区',
                width : 100,
                align : 'center'
            },{
                field : 'lesCode',
                title : 'LES省级代码',
                width : 150,
                align : 'center'
            },{
                field : 'address',
                title : '地址',
                width : 500,
                align : 'center'
            },{
                field : 'zipCode',
                title : '邮编',
                width : 100,
                align : 'center'
            },{
                field : 'contact_cgodbm',
                title : 'CGODBM正品退货联系人',
                width : 400,
                align : 'center'
            },{
                field : 'contact_crm',
                title : 'CRM正品退货联系人',
                width : 400,
                align : 'center'
            },{
                field : 'mobilePhone',
                title : '联系电话(手机)',
                width : 200,
                align : 'center'
            },{
                field : 'phone',
                title : '固定电话',
                width : 200,
                align : 'center'
            },{
                field : 'is_enabled_name',
                title : '状态',
                width : 100,
                align : 'center'
            },{field:'createUser', title:'创建人', width:'100', align:'center'},
            {field:'updateUser', title:'更新人', width:'100', align:'center'},
            {field:'createTime', title:'创建时间', width:'220', align:'center'},
            {field:'updateTime', title:'更新时间', width:'220', align:'center'}
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

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
    //确定更新按钮
    $("#updateConfirm").click(function(){
        var updateFlag = true;
        $("#updateForm").form("enableValidation");
        updateFlag = $("#updateForm").form("validate");
        if ($(this).linkbutton('options').disabled == false) {
            if (updateFlag) {
                $('#updateConfirm').linkbutton('disable');
                $.messager.confirm('确认','确定要修改吗？', function(r){
                    if (r){
                        jQuery.ajax({
                            url: "/waaddress/updateWaAddress",
                            type: "POST",
                            data: $("#updateForm").serializeJson(),
                            success: function (data) {
                                $('#datagrid').datagrid("load")
                                $.messager.alert('提示','修改完成','info');
                                $("#updateForm").form("clear");
                                $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                                $('#updateConfirm').linkbutton('enable');
                                // $("#updateForm").form("disableValidation");
                                $("#updateDiv").window('close');
                            }
                        });
                    }else {
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




    //确定创建按钮
    $("#createConfirm").click(function(){
        var createFlag= true;
        $("#createForm").form("enableValidation");
        createFlag = $("#createForm").form("validate");
        if ($(this).linkbutton('options').disabled == false) {
            if(createFlag){
                $('#createConfirm').linkbutton('disable');
                $.messager.confirm('确认','确定要创建吗？', function(r){
                    if (r){
                        jQuery.ajax({
                            url: "/waaddress/createWaAddress",
                            type: "POST",
                            data: $("#createForm").serializeJson(),
                            dataType:"JSON",
                            success: function (data) {
                                if(data.resultStatus==0){
                                    $.messager.alert('提示','创建失败,库位码已存在','info');
                                }
                                else{ $.messager.alert('提示','创建完成','info');
                                }
                                $("#createDiv").window('close');
                                $('#createConfirm').linkbutton('enable');
                                // $("#createForm").form("disableValidation");
                                $("#createForm").form("clear");
                                $('#datagrid').datagrid("load")
                                $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
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

    //禁用
    $("#closeStatus").click(function(){
        if(!datagrid){
            $.messager.alert('提示', "请先查询！", 'info');
            return;
        }
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var closeData = new Array();
        $.each(checkedItems, function(index, item){
            closeData.push(item.sCode);
        });
        if(closeData==null||closeData.length==0){
            $.messager.alert('错误','请选择需要禁用的行！','error');
            return;
        }
        $.messager.confirm('确认','确定要禁用吗？', function(r){
            if(r){
                jQuery.ajax({
                    url: "/waaddress/closeStatusWaAddress.html",
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

//启用
    $("#openStatus").click(function(){
        if(!datagrid){
            $.messager.alert('提示', "请先查询！", 'info');
            return;
        }
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var openData = new Array();
        $.each(checkedItems, function(index, item){
            openData.push(item.sCode);
        });
        if(openData==null||openData.length==0){
            $.messager.alert('错误','请选择需要启用的行！','error');
            return;
        }
        $.messager.confirm('确认','确定要启用吗？', function(r){
            if(r){
                jQuery.ajax({
                    url: "/waaddress/openStatusWaAddress",
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
            exportData.push(item.sCode);
        });
        $("#exportData").val(JSON.stringify(exportData));
        //判断是否都没选中
        if(exportData==null||exportData.length==0){
            $.messager.alert('错误','请至少选择一行要导出的行！','error');
            return;
        }
        $.messager.confirm('确认', '确定要导出吗？', function(r){
            if (r){
                $('#waAddress').attr("action", '/waaddress/waAddressExport.export');
                $('#waAddress').submit();
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
                $('#waAddress').attr("action", '/waaddress/waAddressAllExport.export');
                $('#waAddress').submit();
            }
        });
    });
//删除
    $("#delete").click(function(){
        if(!datagrid){
            $.messager.alert('提示', "请先查询！", 'info');
            return;
        }
        var checkedItems = $('#datagrid').datagrid('getSelections');

        var deleteData = new Array();
        $.each(checkedItems, function(index, item){
            deleteData.push(item.sCode);
        });
        if(deleteData==null||deleteData.length==0){
            $.messager.alert('错误','请选择需要删除的行！','error');
            return;
        }
        $.messager.confirm('确认','确定要删除吗？', function(r){
            if(r){
                jQuery.ajax({
                    url: "/waaddress/deleteWaAddress.html",
                    type: "POST",
                    data:{"deleteData": JSON.stringify(deleteData)},
                    success: function (data) {
                        $.messager.alert('提示','删除完成','info');
                        $('#datagrid').datagrid("load")
                        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                });
            }
        });
    });

});


//点击查询
$('#search').click(function () {

    //生成grid
    datagrid = $('#datagrid').datagrid({
        url : "/waaddress/searchWaAddress",
        fit:true,
        pagination: true,
        pageSize:100,
        pageList:[100,200,300],
        nowrap: false,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        rownumbers: true,
        queryParams: $("#waAddress").serializeJson(),
        loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        columns : [ [ {
            field: 'check',
            title: '全选',
            width: 100,
            align: 'center',
            checkbox:true
        },{
            field : 'sCode',
            title : '库位码',
            width : 120,
            align : 'center'
        },{
            field : 'centerName',
            title : '所属中心',
            width : 120,
            align : 'center'
        },{
            field : 'cCode',
            title : '中心代码',
            width : 140,
            align : 'center'
        },{
            field : 'province',
            title : '所属省级',
            width : 120,
            align : 'center'
        },{
            field : 'city',
            title : '市',
            width : 100,
            align : 'center'
        },{
            field : 'county',
            title : '县区',
            width : 100,
            align : 'center'
        },{
            field : 'lesCode',
            title : 'LES省级代码',
            width : 150,
            align : 'center'
        },{
            field : 'address',
            title : '地址',
            width : 500,
            align : 'center'
        },{
            field : 'zipCode',
            title : '邮编',
            width : 100,
            align : 'center'
        },{
            field : 'contact_cgodbm',
            title : 'CGODBM正品退货联系人',
            width : 400,
            align : 'center'
        },{
            field : 'contact_crm',
            title : 'CRM正品退货联系人',
            width : 400,
            align : 'center'
        },{
            field : 'mobilePhone',
            title : '联系电话(手机)',
            width : 200,
            align : 'center'
        },{
            field : 'phone',
            title : '固定电话',
            width : 200,
            align : 'center'
        },{
            field : 'is_enabled_name',
            title : '状态',
            width : 100,
            align : 'center'
        },{field:'createUser', title:'创建人', width:'100', align:'center'},
            {field:'updateUser', title:'更新人', width:'100', align:'center'},
            {field:'createTime', title:'创建时间', width:'220', align:'center',
                formatter : function(value) {
                    if(value == '0000-00-00'){
                        return "";
                    }else {
                        return value;
                    }
                }},
            {field:'updateTime', title:'更新时间', width:'220', align:'center',
                formatter : function(value) {
                    if(value == '0000-00-00'){
                        return "";
                    }else{
                        return value;
                    }
                }}
        ] ],
        toolbar: '#datagridToolbar'
    });
});

