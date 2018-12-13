$(function () {
	// combobox
    year = new Date().getFullYear();
    data = [];
    data.push({
        "text": (year - 1) + '年度',
        "id": year - 1
    });
    data.push({
        "text": year + '年度',
        "id": year
    });
    data.push({
        "text": (year + 1) + '年度',
        "id": year + 1
    });
    $("#year1").combobox({
        valueField: "id",
        textField: "text",
        loadData: data
    });
    $("#year").combobox({
        valueField: "id",
        textField: "text",
        loadData: data
    });
    $("#year1").combobox("loadData", data);
    $("#year").combobox("loadData", data);

    $('#searchBtn').click(function () {
        var queryParameter = dataGrid.datagrid("options").queryParams;
        var param = {
            yearmonth:$("#yearmonth").datetimebox('getValue'),
            sellPeople:$("#sellPeople").val(),
            settlementType:$("#settlementType").combobox('getValue'),
            state:$("#state").combobox('getValue'),
            statusType:$("#statusType").combobox('getValue'),
            payTimeMin:$("#payTimeMin").datetimebox('getValue'),
            payTimeMax:$("#payTimeMax").datetimebox('getValue'),
            invoiceTimeMin:$("#invoiceTimeMin").datetimebox('getValue'),
            invoiceTimeMax:$("#invoiceTimeMax").datetimebox('getValue'),
            source:$("#source").datetimebox('getValue'),
        };
        for (var o in param) {
            queryParameter[o] = param[o];
        }
        dataGrid.datagrid("options").queryParams = queryParameter;
        console.log(queryParameter);
        // param.push({name: "pageIndex", value: options.pageNumber},
        //     {name: "pageSize", value: options.pageSize})
        dataGrid.datagrid("load");
        // $.ajax({
        //   url: '/woUser/getList',
        //   data: param,
        //   type:'get',
        //   dataType: 'json',
        //   success: function (res) {
        //     dataGrid.datagrid("loadData", res);
        //   }
        // })
    });


    $("#resetBtn_orderOp").on('click', function () {
        var currTime=new Date();
        var strDate=currTime.getFullYear()+"-"+(currTime.getMonth()+1)+"-01";
        $('#yearmonth').datebox('setValue',strDate);//默认加载当前月份
        $("#sellPeople").val('');
        $('#settlementType').combobox('setValue', "").combobox('setText', '全部');
        $('#statusType').combobox('setValue', "").combobox('setText', '全部');
        $('#payTimeMin').datebox('setValue','');
        $('#payTimeMax').datebox('setValue','');
        $('#invoiceTimeMin').datebox('setValue','');
        $('#invoiceTimeMax').datebox('setValue','');
        $('#state').combobox('setValue', "").combobox('setText', '全部');
    });
    
    var url = "/settlementInvoice/getSelecttion";
    $.ajax({
        url: url,
        dataType: 'json',
        type: "post",
        success: function (data) {
            var optionstring = "";
            for (var j = 0; j < data.length;j++) {              
            	optionstring += "<option value=\"" + data[j].source + "\" >"+data[j].source +"</option>";  
            }  
            $("#source").append(optionstring);
            $("#source").combobox({});
         },  
         error: function (msg) {  
               alert("订单来源选项获取失败！");  
         }  
	});
    
});

var WORK_ORDER_EXCEL = {
    'Q_username_search': null,
    'Q_phone_search': null,
    'Q_email_search': null,
    'Q_type_search': null,
    'Q_workStatus': null,
    'Q_issend_search': null
};

function exp() {
    var data = $('#dg').datagrid('getData');
    if (data.rows.length <= 0) {
        $.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
        return;
    }
    //var selectedvalue=$("input[name='tab']:checked").val();
    var param = $("#paramForm").serialize();
    window.location.href = "/settlementInvoice/export?" +param;

}

$("#import").click(function() {
    return $('#importExecl').dialog('open').dialog({
        title: '报表导入',
        closed: false,
        width: 500,
        height: 300,
        cache: false,
        modal: true
    });
});
$("#upload").click(function() {
    if (!$("#file").val()) {
        $.messager.alert('提示信息', '请选择文件');
        return;
        $("#upload").addClass("l-btn-disabled");
    }
    return $.ajaxFileUpload({
        url: '/settlementInvoice/importTMFXPointMaintainExcel',
        secureuri: false,
        fileElementId: 'file',
        dataType: 'json',
        success: function(data) {
            $("#upload").removeClass("l-btn-disabled");
            var data = $.parseJSON(data);

            if(data.success === true){
                $.messager.alert('提示信息', '上传成功!' );
                $('#importExecl').dialog('close');
                $('#dg').datagrid('load');
            }else {
                $.messager.alert('提示信息', '上传失败!' + data.message);
            }
        },
        error: function(a, b, c, d) {
            $("#upload").removeClass("l-btn-disabled");
            var data = $.parseJSON(a.responseText);

            if(data.success === true){
                $.messager.alert('提示信息', data.message);
                $('#importExecl').dialog('close');
                $('#dg').datagrid('load');
            }else {
                $.messager.alert('提示信息', '上传失败!' + data.message);
            }

        }
    });
});

function busAuditor(type) {


    //获得选中行
    var checkedItems = $('#dg').datagrid('getChecked');
    var auditorData = new Array();
    var flag = true;
    var ids = "";
    //判断是否存在满足条件的行
    $.each(checkedItems, function(index, item){

        //审核状态为'待业务审核'
        if(item.state== '1'){
            ids += this.id + ",";
        }else{
            flag = false;
        }
    });

    if(!flag){
        $.messager.alert('错误','请选择待业务审核的数据！','error');
        return false;
    }

    //判断是否存在适合处理的数据
    if(ids==""){
        $.messager.alert('错误','请选择待业务审核的数据！','error');
        return;
    }

    if(type=='ok'){
        $.messager.confirm('确认','确定审核<font color=\'red\'>通过</font>吗？', function(r){
            if (r){

                $.messager.progress({
                    title:'请稍后',
                    msg:'正在处理，请稍后...'
                });
                var url = "/settlementInvoice/audit";
                $.ajax({
                    url: url,
                    data: {
                        ids: ids,
                        audit: 'B',
                        state:2
                    },
                    dataType: 'json',
                    type: "post",
                    success: function (res) {
                        $.messager.progress('close');
                        //$("#addDlgSaveBtn").removeClass("disabled");
                        if (res.success) {
                            alert("操作成功")
                        } else {
                            alert("数据异常")
                        }
                        dataGrid.datagrid("reload")
                        $('#dg').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                })


            }
        });
    }else if(type=='no'){
        $.messager.confirm('确认','确定审核<font color=\'red\'>拒绝</font>吗？', function(r){
            if (r){
                $.messager.progress({
                    title:'请稍后',
                    msg:'正在处理，请稍后...'
                });
                var url = "/settlementInvoice/audit";
                $.ajax({
                    url: url,
                    data: {
                        ids: ids,
                        audit: 'B',
                        state: -1
                    },
                    dataType: 'json',
                    type: "post",
                    success: function (res) {
                        $.messager.progress('close');
                        //$("#addDlgSaveBtn").removeClass("disabled");
                        if (res.success) {
                            alert("操作成功")
                        } else {
                            alert("数据异常")
                        }
                        dataGrid.datagrid("reload")
                        $('#dg').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                })
            }
        });
    }
};
function finAuditor(type) {


    //获得选中行
    var checkedItems = $('#dg').datagrid('getChecked');
    var auditorData = new Array();
    var flag = true;
    var ids = "";
    //判断是否存在满足条件的行
    $.each(checkedItems, function(index, item){

        //审核状态为'待财务审核'
        if(item.state== '2'){
            ids += this.id + ",";
        }else{
            flag = false;
        }
    });

    if(!flag){
        $.messager.alert('错误','请选择待财务审核的数据！','error');
        return false;
    }

    //判断是否存在适合处理的数据
    if(ids==""){
        $.messager.alert('错误','请选择需要处理的数据！','error');
        return;
    }

    if(type=='ok'){
        $.messager.confirm('确认','确定审核<font color=\'red\'>通过</font>吗？', function(r){
            if (r){

                $.messager.progress({
                    title:'请稍后',
                    msg:'正在处理，请稍后...'
                });
                var url = "/settlementInvoice/audit";
                $.ajax({
                    url: url,
                    data: {
                        ids: ids,
                        audit: 'F',
                        state:3
                    },
                    dataType: 'json',
                    type: "post",
                    success: function (res) {
                        $.messager.progress('close');
                        //$("#addDlgSaveBtn").removeClass("disabled");
                        if (res.success) {
                            alert("操作成功")
                        } else {
                            alert("数据异常")
                        }
                        dataGrid.datagrid("reload")
                        $('#dg').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                })


            }
        });
    }else if(type=='no'){
        $.messager.confirm('确认','确定审核<font color=\'red\'>拒绝</font>吗？', function(r){
            if (r){
                $.messager.progress({
                    title:'请稍后',
                    msg:'正在处理，请稍后...'
                });
                var url = "/settlementInvoice/audit";
                $.ajax({
                    url: url,
                    data: {
                        ids: ids,
                        audit: 'F',
                        state: -2
                    },
                    dataType: 'json',
                    type: "post",
                    success: function (res) {
                        $.messager.progress('close');
                        //$("#addDlgSaveBtn").removeClass("disabled");
                        if (res.success) {
                            alert("操作成功")
                        } else {
                            alert("数据异常")
                        }
                        dataGrid.datagrid("reload")
                        $('#dg').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                })
            }
        });
    }



};
$("#action").click(function() {
    $('#toAction').linkbutton('enable');
    return $('#actionEdit').dialog('open').dialog('setTitle', '请选择年度,期间,类型');
});

