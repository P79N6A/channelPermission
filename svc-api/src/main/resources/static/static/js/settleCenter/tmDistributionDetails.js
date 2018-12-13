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

    $("#addBtn").on('click', function (event) {
        $('#fm').form('reset');
        $('#fm').find('[__actType]').val('add');
        $('#userEdit').dialog({'title': i18n['message.act.add']});

        $("#fm").form("clear");
        $('#ym').combobox('enable');
        $('#industry').combobox('enable');
        $('#ecologyShop').textbox('enable');
        $('#brand').textbox('enable');
        $('#type').combobox('enable');
        $('#year').combobox('enable');
        $('#beginMonth').combobox('enable');
        $('#endMonth').combobox('enable');
        $('#sku').textbox('enable');
        $('#modelDes').textbox('enable');
        $('#isRemainingModel').combobox('enable');
        $('#beginTarget').combobox('enable');
        $('#endTarget').combobox('enable');
        return $('#userEdit').dialog('open').dialog('setTitle', '新增返利政策点位');
    });
    $('#searchBtn').click(function () {
        var queryParameter = dataGrid.datagrid("options").queryParams;
        var param = $("#paramForm").serializeArray();
        for (var o in param) {
            queryParameter[param[o].name] = param[o].value;
        }
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

    $("#addDlgSaveBtn").on('click', function () {
        var data = $("#fm").serializeArray();

        if (!$("#fm").find("[name='year']").val()) {
            $.messager.alert('提示信息', '请选择年份');
            return;
        }
        if (!$("#fm").find("[name='beginMonth']").val()) {
            $.messager.alert('提示信息', '请选择期间起');
            return;
        }
        if (!$("#fm").find("[name='endMonth']").val()) {
            $.messager.alert('提示信息', '请选择期间止');
            return;
        }
        if (!$("#fm").find("[name='industry']").val()) {
            $.messager.alert('提示信息', '请选择产业');
            return;
        }
        if (!$("#fm").find("[name='brand']").val()) {
            $.messager.alert('提示信息', '请填写品牌');
            return;
        }
        if (!$("#fm").find("[name='type']").val()) {
            $.messager.alert('提示信息', '请选择类型');
            return;
        }
        if (!$("#fm").find("[name='rewardType']").val()) {
            $.messager.alert('提示信息', '请选择奖励类型');
            return;
        }
        if ($("#fm").find("[name='isStep']").val() === 'Y' && (!$("#fm").find("[name='beginTarget']").val() || !$("#fm").find("[name='endTarget']").val())) {
            $.messager.alert('提示信息', '请填写目标范围');
            return;
        }
        if ($("#fm").find("[name='industry']").val() === "彩电" && (isNaN($("#fm").find("[name='beginMonth']").val()) || isNaN($("#fm").find("[name='endMonth']").val()))) {
            $.messager.alert('提示信息', '彩电年度季度都不参与!');
            return;
        }
        if ($("[name='type']").val() === "m" && (isNaN($("[name='beginMonth']").val()) || isNaN($("[name='endMonth']").val()))) {
            $.messager.alert('提示信息', '期间与类型不匹配!');
            return;
        }
        if ($("#fm").find("[name='type']").val() === "q" && (!isNaN($("#fm").find("[name='beginMonth']").val()) || $("#fm").find("[name='beginMonth']").val() !== $("#fm").find("[name='endMonth']").val() || $("#fm").find("[name='beginMonth']").val() === "N")) {
            $.messager.alert('提示信息', '期间与类型不匹配!');
            return;
        }
        if ($("#fm").find("[name='type']").val() === "y" && ($("#fm").find("[name='beginMonth']").val() !== "N" || $("#fm").find("[name='beginMonth']").val() !== $("#fm").find("[name='endMonth']").val())) {
            $.messager.alert('提示信息', '期间与类型不匹配!');
            return;
        }


        var url = "/pointMaintain/insertOrUpdate";
        $.ajax({
            url: url,
            data: data,
            dataType: 'json',
            type: "post",
            success: function (res) {
                //$("#addDlgSaveBtn").removeClass("disabled");
                if (res.success) {
                    alert("操作成功")
                } else {
                    alert("数据异常")
                }
                dataGrid.datagrid("reload")
                $('#userEdit').dialog('close')
            }
        })
    })

    $("#editBtn").on('click', function () {
        var row, rows;
        rows = $('#dg').datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert('提示信息', '请选择单条数据进行操作!');
            return;
        }
        row = $('#dg').datagrid('getSelected');
        if (row) {
            $("#fm").form("clear");
            $('#ym').combobox('disable');
            $('#industry').combobox('disable');
            $('#ecologyShop').textbox('disable');
            $('#brand').textbox('disable');
            $('#type').combobox('disable');
            $('#year').combobox('disable');
            $('#beginMonth').combobox('disable');
            $('#endMonth').combobox('disable');
            $('#sku').textbox('disable');
            $('#modelDes').textbox('disable');
            $('#rewardType').combobox('disable');
            $('#beginTarget').textbox('disable');
            $('#endTarget').textbox('disable');
            row = $('#dg').datagrid('getSelected');
            if (row) {
                $('#fm').find('[__actType]').val('add');
                $('#userEdit').dialog({'title': i18n['message.act.add']});
                $('#userEdit').dialog('open').dialog('setTitle', '修改信息');
                return $('#userEdit').form('load', row);
            }
        } else {
            return $.messager.alert('提示信息', '请选择一条数据进行操作');
        }
    }).error(function (errorObj, statusText) {
        alert(statusText);
    });

    $("#delBtn").click(function () {
        var selected = dataGrid.datagrid('getSelections');
        var ids = []
        if (selected.length < 1) {
            alert("请至少选择一条数据")
            return;
        }
        for (var i in selected) {
            ids.push(selected[i].id);
        }

        $.messager.confirm("确认", "您确定要删除这些数据吗？", function (yes) {
            if (yes) {
                $.ajax({
                    url: "/pointMaintain/delBatch",
                    data: {ids: JSON.stringify(ids)},
                    dataType: 'json',
                    type: 'post',
                    success: function (res) {
                        if (res.success) {
                            alert("操作成功")
                        } else {
                            alert("数据异常")
                        }
                        dataGrid.datagrid("reload")
                    }
                })
            }
        })

    })

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
    window.location.href = "/pointMaintain/export?" +param;

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
        url: '/pointMaintain/importTMFXPointMaintainExcel',
        secureuri: false,
        fileElementId: 'file',
        dataType: 'json',
        callback: function(data) {
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
                $.messager.alert('提示信息', '上传成功!' );
                $('#importExecl').dialog('close');
                $('#dg').datagrid('load');
            }else {
                $.messager.alert('提示信息', '上传失败!' + data.message);
            }

        }
    });
});

$("#bAudit").click(function() {
    var ids, result, row, rows;
    row = $('#dg').datagrid('getSelected');
    rows = $('#dg').datagrid('getSelections');
    if (rows.length === 0) {
        $.messager.alert('提示信息', '请选择数据!');
        return;
    }
    ids = "";
    result = "";
    $.each(rows, function(i) {
        if (this.deleteTab !== 'N') {
            result += "不能通过审核:第" + (i + 1) + "条数据已删除<br>";
        }
        if (this.auditState === 'F') {
            result += "不能通过审核:第" + (i + 1) + "条数据待财务审核<br>";
        }
        if (this.auditState === 'A') {
            result += "不能通过审核:第" + (i + 1) + "条数据审核已通过<br>";
        }
        return ids += this.id + ",";
    });
    if (result !== '') {
        $.messager.alert('提示信息', result);
        return;
    }
    if (row) {
        var url = "/pointMaintain/audit";
        $.ajax({
            url: url,
            data: {
                ids: ids,
                audit: 'B'
            },
            dataType: 'json',
            type: "post",
            success: function (res) {
                //$("#addDlgSaveBtn").removeClass("disabled");
                if (res.success) {
                    alert("操作成功")
                } else {
                    alert("数据异常")
                }
                dataGrid.datagrid("reload")

            }
        })
/*        return $.messager.confirm("确认", "确认要审核吗?", function(r) {
            if (r) {
                return Core.randomPost("/api/tmfx/auditPointMaintain", {
                    ids: ids,
                    audit: 'B'
                }, function(data) {
                    if (data.success) {
                        $.messager.alert('提示信息', data.result);
                        return $('#userGrid').datagrid('load');
                    } else {
                        return $.messager.alert('提示信息', data.error);
                    }
                });
            }
        });*/
    }
});
$("#fAudit").click(function() {
    var ids, result, row, rows;
    row = $('#dg').datagrid('getSelected');
    rows = $('#dg').datagrid('getSelections');
    if (rows.length === 0) {
        $.messager.alert('提示信息', '请选择数据!');
        return;
    }
    ids = "";
    result = "";
    $.each(rows, function(i) {
        if (this.deleteTab !== 'N') {
            result += "不能通过审核:第" + (i + 1) + "条数据已删除<br>";
        }
        if (this.auditState === 'B') {
            result += "不能通过审核:第" + (i + 1) + "条数据待业务审核<br>";
        }
        if (this.auditState === 'A') {
            result += "不能通过审核:第" + (i + 1) + "条数据审核已通过<br>";
        }
        return ids += this.id + ",";
    });
    if (result !== '') {
        $.messager.alert('提示信息', result);
        return;
    }
    if (row) {
        var url = "/pointMaintain/audit";
        $.ajax({
            url: url,
            data: {
                ids: ids,
                audit: 'F'
            },
            dataType: 'json',
            type: "post",
            success: function (res) {
                //$("#addDlgSaveBtn").removeClass("disabled");
                if (res.success) {
                    alert("操作成功")
                } else {
                    alert("数据异常")
                }
                dataGrid.datagrid("reload")

            }
        })
/*        return $.messager.confirm("确认", "确认要审核吗?", function(r) {
            if (r) {
                return Core.randomPost("/api/tmfx/auditPointMaintain", {
                    ids: ids,
                    audit: 'F'
                }, function(data) {
                    if (data.success) {
                        $.messager.alert('提示信息', data.result);
                        return $('#userGrid').datagrid('load');
                    } else {
                        return $.messager.alert('提示信息', data.error);
                    }
                });
            }
        });*/
    }
});
$("#action").click(function() {
    $('#toAction').linkbutton('enable');
    return $('#actionEdit').dialog('open').dialog('setTitle', '请选择年度,期间,类型');
});

