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
                checkbox: true
            },
            {
                field: 'rrs_wh_code',
                title: '日日顺库位码',
                width: 100,
                align: 'center'
            },
            {
                field: 'rrs_wh_name',
                title: '日日顺仓库名称',
                width: 200,
                align: 'center'
            },
            {
                field: 'estorge_id',
                title: '电商库位码',
                width: 100,
                align: 'center'
            },
            {
                field: 't2_default',
                title: 'T+2上单默认',
                width: 100,
                align: 'center',
                formatter: function(value,row,index){
                    if(value==1){
                        return "是";
                    }else{
                        return  "";
                    }
                }
            },
            {
                field: 'transport_prescription',
                title: '运输时效(天)',
                width: 100,
                align: 'center'
            },
            {
                field: 'create_user',
                title: '创建人',
                width: 100,
                align: 'center'
            },
            {
                field: 'create_time',
                title: '创建时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'update_user',
                title: '最后更新人',
                width: 100,
                align: 'center'
            },
            {
                field: 'update_time',
                title: '最后更新时间',
                width: 200,
                align: 'center'
            },
            {
                field: 'is_enabled_name',
                title: '状态',
                width: 100,
                align: 'center'
            }

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
    //更新确认
    $("#updateConfirm").click(
        function(){
            var flag = true;
            $("#updateForm").form("enableValidation");
            flag = $("#updateForm").form("validate");
            if ($(this).linkbutton('options').disabled == false) {
                if (flag) {
                    $('#updateConfirm').linkbutton('disable');
                    $.messager.confirm('确认', '确定要修改吗？', function (r) {
                        if (r) {
                            jQuery.ajax({
                                url: "/invRrsWarehouse/updateInvRrsWarehouse",   // 提交的页面
                                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                                data: $("#updateForm").serialize(),
                                dataType:"JSON",
                                success: function (data) {
                                    if(data.resultStatus=="yes"){
                                        $.messager.alert('提示', '修改完成', 'info');
                                    }else{
                                        $.messager.alert('提示', '更新失败,一个WA库位只允许勾选一个T+2默认库位', 'error');
                                    }

                                    $("#updateDiv").window('close');
                                    // $("#updateForm").form("disableValidation");
                                    $("#updateConfirm").linkbutton('enable');
                                    $('#datagrid').datagrid('reload',
                                        {
                                            rrs_wh_code: $("#rrs_wh_code_hidden").val(),
                                            rrs_wh_name: $("#rrs_wh_name_hidden").val(),
                                            estorge_id: $("#estorge_id_hidden").val(),
                                            t2_default: $("#t2_default_hidden").val(),
                                            transport_prescription: $("#transport_prescription_hidden").val()
                                        });
                                    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                                    $("#rrs_wh_code_update").val("");
                                    $("#rrs_wh_name_update").val("");
                                    $("#estorge_id_update").val("");
                                    $("#t2_default_update").val("");
                                    $("#transport_prescription_update").numberbox("clear");
                                }
                            });
                        }else{
                            $('#updateConfirm').linkbutton('enable');
                        }
                    });
                    $('.panel-tool-close').hide();}}
        }
    );
    $("#create").on('click', function () {
        $('#createForm').form('reset');
        $('#createForm').find('[__actType]').val('add');
        $('#createDiv').dialog({'title': i18n['message.act.add']});


        $("#createForm").form("clear");

        return $('#createDiv').dialog('open').dialog('setTitle', '创建信息');
    });


    //创建确认按钮
    $("#createConfirm").click(
        function(){
            var flag = true;
            $("#createForm").form("enableValidation");
            flag = $("#createForm").form("validate");
            if ($(this).linkbutton('options').disabled == false) {
                if (flag) {
                    $("#createConfirm").linkbutton('disable');
                    var createData = new Array();
                    createData.push($("#rrs_wh_code_create").val());
                    createData.push($("#rrs_wh_name_create").val());
                    createData.push($("#estorge_id_create").val());
                    createData.push($("#t2_default_create").combobox("getValue"));
                    createData.push($("#transport_prescription_create").val());
                    $.messager.confirm('确认', '确定要创建吗？', function (r) {
                        if (r) {
                            jQuery.ajax({
                                url: "/invRrsWarehouse/insertInvRrsWarehouse",   // 提交的页面
                                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                                data: $("#createForm").serialize(),
                                dataType:"JSON",
                                success: function (data) {
                                    if(data.resultStatus=="yes"){
                                        $.messager.alert('提示', '创建完成', 'info');
                                    }else {
                                        if(data.resultStatus=="no"){
                                            $.messager.alert('提示', '同一个电商库位码只能有一个上单默认', 'error');
                                        }else{
                                            $.messager.alert('提示', '日日顺库位码不能重复', 'error');
                                        }
                                    }

                                    $("#createDiv").window('close');
                                    // $("#createForm").form("disableValidation");
                                    $("#createConfirm").linkbutton('enable');
                                    $('#datagrid').datagrid('reload',
                                        {
                                            user_id: $("#rrs_wh_code_hidden").val(),
                                            user_name: $("#rrs_wh_name_hidden").val(),
                                            estorge_id: $("#estorge_id_hidden").val(),
                                            t2_default: $("#t2_default_hidden").val(),
                                            transport_prescription: $("#transport_prescription_hidden").val()
                                        });
                                    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                                    $("#rrs_wh_code_create").val("");
                                    $("#rrs_wh_name_create").val("");
                                    $("#estorge_id_create").val("");
                                    $("#t2_default_create").val("");
                                    $("#transport_prescription_create").numberbox("clear");

                                }


                            });

                        }else{
                            $("#createConfirm").linkbutton('enable');
                        }

                    });
                    $('.panel-tool-close').hide();}
            }
        }

    );

    $('#delete').click(function () {
        $("#rrs_wh_code_hidden").val($("#rrs_wh_code").val());
        if (!datagrid) {
            $.messager.alert('提示', '请查询！', 'info');
            return;
        }
        //获得选中行
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var deleteData = new Array();
        $.each(checkedItems, function (index, item) {

            deleteData.push(item.rrs_wh_code);
        });
        //判断是否存在未提交的行
        if (deleteData == null || deleteData.length == 0) {
            $.messager.alert('错误', '请至少选择一行！', 'error');
            return;
        }
        $.messager.confirm('确认', '确定要删除吗？', function (r) {
            if (r) {
                jQuery.ajax({
                    url: "/invRrsWarehouse/deleteInvRrsWarehouse",   // 提交的页面
                    type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                    data: {"deleteData": JSON.stringify(deleteData)},
                    success: function (data) {
                        $.messager.alert('提示', '删除完成', 'info');
                        $('#datagrid').datagrid('reload',
                            {
                                rrs_wh_code: $("#rrs_wh_code_hidden").val()

                            });
                        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                });
            }
        });
    });
//启用按钮

    $('#statusOpen').click(function () {
        $("#rrs_wh_code_hidden").val($("#rrs_wh_code").val());
        if (!datagrid) {
            $.messager.alert('提示', '请查询！', 'info');
            return;
        }
        //获得选中行
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var deleteData = new Array();
        $.each(checkedItems, function (index, item) {

            deleteData.push(item.rrs_wh_code);
        });
        //判断是否存在未提交的行
        if (deleteData == null || deleteData.length == 0) {
            $.messager.alert('错误', '请至少选择一行！', 'error');
            return;
        }
        $.messager.confirm('确认', '确定要变更为开启状态么', function (r) {
            if (r) {
                jQuery.ajax({
                    url: "/invRrsWarehouse/statusOpenInvRrsWarehouse",   // 提交的页面
                    type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                    data: {"deleteData": JSON.stringify(deleteData)},
                    success: function (data) {
                        $.messager.alert('提示', '开启完成', 'info');
                        $('#datagrid').datagrid('reload',
                            {
                                rrs_wh_code: $("#rrs_wh_code_hidden").val()

                            });
                        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                });
            }
        });
    });
//禁用按钮

    $('#statusClose').click(function () {
        $("#rrs_wh_code_hidden").val($("#rrs_wh_code").val());
        if (!datagrid) {
            $.messager.alert('提示', '请查询！', 'info');
            return;
        }
        //获得选中行
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var deleteData = new Array();
        $.each(checkedItems, function (index, item) {

            deleteData.push(item.rrs_wh_code);
        });
        //判断是否存在未提交的行
        if (deleteData == null || deleteData.length == 0) {
            $.messager.alert('错误', '请至少选择一行！', 'error');
            return;
        }
        $.messager.confirm('确认', '确定要变更为禁用状态么', function (r) {
            if (r) {
                jQuery.ajax({
                    url: "/invRrsWarehouse/statusCloseInvRrsWarehouse",   // 提交的页面
                    type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                    data: {"deleteData": JSON.stringify(deleteData)},
                    success: function (data) {
                        $.messager.alert('提示', '禁用完成', 'info');
                        $('#datagrid').datagrid('reload',
                            {
                                rrs_wh_code: $("#rrs_wh_code_hidden").val()

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
        if(window.total && window.total > 20000){
            $.messager.alert('提示', '最多导出20000条记录，请缩小查询范围！', 'info');
            return;
        }
        //获得选中行
        var checkedItems = $('#datagrid').datagrid('getChecked');
        var exportData = new Array();
        //将订单号存入Array
        $.each(checkedItems, function(index, item){
            exportData.push(item.rrs_wh_code);
        });
        $("#exportData").val(JSON.stringify(exportData));
        //判断是否都没选中
        if(exportData==null||exportData.length==0){
            $.messager.alert('错误','请至少选择一行要导出的行！','error');
            return;
        }
        $.messager.confirm('确认', '确定要导出吗？', function(r){
            if (r){
                $('#filterForm').attr("action", '/invRrsWarehouse/exportInvRrsWarehouse.export');
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
                $('#filterForm').attr("action", '/invRrsWarehouse/exportInvRrsWarehouseAll.export');
                $('#filterForm').submit();
            }
        });
    });
    
    $("#t2_default_create").combobox({
        editable:false,
        valueField:'value',//数据库值
        textField:'status',
        panelHeight:50,

        data : [{
            status : '是',
            value : '1',
        }, {
            status : '否',
            value : '0',
        }],
        value:0,

    });
    $("#t2_default_update").combobox({
        editable:false,
        valueField:'value',//数据库值
        textField:'status',
        panelHeight:50,
        data : [{
            status : '是',
            value : '1',
        }, {
            status : '否',
            value : '0',
        }],
        value:0,
    });

});


//点击查询
$('#search').click(function () {
    $("#rrs_wh_code_hidden").val($("#rrs_wh_code").val());
    $("#rrs_wh_name_hidden").val($("#rrs_wh_name").val());
    $("#estorge_id_hidden").val($("#estorge_id").val());
    if(document.getElementById("t2_default").checked==true){
        document.getElementById("t2_default").value="1"
        $("#t2_default_hidden").val($("#t2_default").val());
    }else{
        document.getElementById("t2_default").value="0"
        $("#t2_default_hidden").val($("#t2_default").val());
    }

    $("#transport_prescription_hidden").val($("#transport_prescription").val());
    //生成grid
    datagrid = $('#datagrid').datagrid({ url: "/invRrsWarehouse/getRrsWhByEstorgeId",
        fit: true,
        pagination: true,
        singleSelect: true,
        pageSize: 100,
        pageList: [100, 200, 300],
        nowrap: false,
        rownumbers: true,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        queryParams: {
            rrs_wh_code: $("#rrs_wh_code_hidden").val(),
            rrs_wh_name: $("#rrs_wh_name_hidden").val(),
            estorge_id: $("#estorge_id_hidden").val(),
            t2_default: $("#t2_default_hidden").val(),
            transport_prescription: $("#transport_prescription_hidden").val()
        },loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        columns: [
            [
                {
                    field: 'check',
                    title: '全选',
                    width: 100,
                    align: 'center',
                    checkbox: true
                },
                {
                    field: 'rrs_wh_code',
                    title: '日日顺库位码',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'rrs_wh_name',
                    title: '日日顺仓库名称',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'estorge_id',
                    title: '电商库位码',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 't2_default',
                    title: 'T+2上单默认',
                    width: 100,
                    align: 'center',
                    formatter: function(value,row,index){
                        if(value==1){
                            return "是";
                        }else{
                            return  "";
                        }
                    }
                },
                {
                    field: 'transport_prescription',
                    title: '运输时效(天)',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'create_user',
                    title: '创建人',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'create_time',
                    title: '创建时间',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'update_user',
                    title: '最后更新人',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'update_time',
                    title: '最后更新时间',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'is_enabled_name',
                    title: '状态',
                    width: 100,
                    align: 'center'
                }

            ]
        ],
        toolbar: '#datagridToolbar'
    });
});

