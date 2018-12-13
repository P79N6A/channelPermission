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
        $('#rewardType').combobox('enable');
        return $('#userEdit').dialog('open').dialog('setTitle', '添加信息');
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


    var flag='[{"value":"Y","valueMeaning":"有效"},{"value":"N","valueMeaning":"无效"}]';
    var data=$.parseJSON(flag);
    $('#flag').combobox("loadData", data);
    $('#flag').combobox('setValue', "").combobox('setText', '-请选择-');


    $('#cateName').combobox(
        {
            valueField : 'cateName',
            textField : 'cateName',
            onLoadSuccess : function(data) {

                $('#cateName').combobox('setValue', "").combobox(
                    'setText', '-请选择-');

            }
        });
    $.ajax({
        type : "get",
        url : "/GateGrossprofit/getCateName",
        data : {
        },
        dataType : "json",
        success : function(data) {
            a = [];

            data.unshift(a);
            tmp = data.slice(0);
            LEVEL1_QJ=tmp;
            $('#cateName').combobox("loadData", data);
        }
    });


    $('#cateGory').combobox(
        {
            valueField : 'cateName',
            textField : 'cateName',
            onLoadSuccess : function(data) {

            }
        });
    $.ajax({
        type : "get",
        url : "/GateGrossprofit/getCateName",
        data : {
        },
        dataType : "json",
        success : function(data) {
            a = [];

            data.unshift(a);
            tmp = data.slice(0);
            LEVEL1_QJ=tmp;
            $('#cateGory').combobox("loadData", data);
        }
    });


    $('#search-brand').combobox(
        {
            valueField : 'brandName',
            textField : 'brandName',
            onLoadSuccess : function(data) {

                $('#search-brand').combobox('setValue', "").combobox(
                    'setText', '-请选择-');

            }
        });
    $.ajax({
        type : "get",
        url : "/GateGrossprofit/getBrands",
        data : {
        },
        dataType : "json",
        success : function(data) {
            a = [];

            data.unshift(a);
            tmp = data.slice(0);
            LEVEL1_QJ=tmp;
            $('#search-brand').combobox("loadData", data);
        }
    });

    $('#brand').combobox(
        {
            valueField : 'brandName',
            textField : 'brandName',
            onLoadSuccess : function(data) {

            }
        });
    $.ajax({
        type : "get",
        url : "/GateGrossprofit/getBrands",
        data : {
        },
        dataType : "json",
        success : function(data) {
            a = [];

            data.unshift(a);
            tmp = data.slice(0);
            LEVEL1_QJ=tmp;
            $('#brand').combobox("loadData", data);
        }
    });





    $("#addDlgSaveBtn").on('click', function () {
        var data = $("#fm").serializeArray();
        var begin=$("#beginTime").textbox('getValue');
        var end=$("#endTime").textbox('getValue');

        if (!$("#fm").find("[name='brand']").val()) {
            $.messager.alert('提示信息', '请填写品牌');
            return;
        }
        if (!$("#fm").find("[name='cateGory']").val()) {
            $.messager.alert('提示信息', '请填写品类');
            return;
        }
        if (!$("#fm").find("[name='grossProfit']").val()) {
            $.messager.alert('提示信息', '请填写毛利率');
            return;
        }
        if (!$("#fm").find("[name='beginTime']").val()) {
            $.messager.alert('提示信息', '请选择开始时间');
            return;
        }
        if (!$("#fm").find("[name='endTime']").val()) {
            $.messager.alert('提示信息', '请选择结束时间');
            return;
        }
        if(begin>end){
            $.messager.alert('提示信息','开始时间不能大于结束时间');
            return;
        }
        var url = "/GateGrossprofit/insertOrUpdate";
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

    /*$("#delBtn").click(function () {
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

    })*/

});

var WORK_ORDER_EXCEL = {
    'Q_username_search': null,
    'Q_phone_search': null,
    'Q_email_search': null,
    'Q_type_search': null,
    'Q_workStatus': null,
    'Q_issend_search': null
};

/*function exp() {
    var data = $('#dg').datagrid('getData');
    if (data.rows.length <= 0) {
        $.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
        return;
    }
    //var selectedvalue=$("input[name='tab']:checked").val();
    var param = $("#paramForm").serialize();
    window.location.href = "/pointMaintain/export?" +param;

}*/

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
        url: '/GateGrossprofit/importTMFXPointMaintainExcel',
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

/*$("#bAudit").click(function() {
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
/!*        return $.messager.confirm("确认", "确认要审核吗?", function(r) {
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
        });*!/
    }
});*/
/*$("#fAudit").click(function() {
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
/!*        return $.messager.confirm("确认", "确认要审核吗?", function(r) {
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
        });*!/
    }
});*/
/*$("#action").click(function() {
    $('#toAction').linkbutton('enable');
    return $('#actionEdit').dialog('open').dialog('setTitle', '请选择年度,期间,类型');
});*/

/*$("#toAction").click(function() {
    if ($(this).linkbutton('options').disabled == true) {
        return;
    }
    var month, type, year;
    year = $("[name='year2']").val();
    month = $("[name='month2']").val();
    type = $("[name='type2']").val();
    return $.messager.confirm("确认", "确认要执行" + year + "-" + month + "的数据吗", function(r) {
        if (r) {
            $('#toAction').linkbutton('disable');
            $.messager.show({
                title: '提示',
                msg: '后台处理中请不要重复点击执行!',
                timeout: 0
            });

            var url = "/rebatesMonthlyDetail/actionToCreateData";
            $.ajax({
                url: url,
                data: {
                    year: year,
                    month: month,
                    type: type,
                    flag: "1"
                },
                dataType: 'json',
                type: "post",
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        $(".messager-body").window('close');
                        $('#actionEdit').dialog('close');
                        return $.messager.alert('提示信息', data.msg);
                    } else {
                        $(".messager-body").window('close');
                        return $.messager.alert('提示信息', data.msg);
                    }
                }
            })
        }
    });
});*/
