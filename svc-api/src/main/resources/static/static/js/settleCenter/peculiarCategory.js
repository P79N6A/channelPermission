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
        return $('#userEdit').dialog('open').dialog('setTitle', '新增母婴产品');
    });
    $('#searchBtn').click(function () {
        var queryParameter = dataGrid.datagrid("options").queryParams;
        var param = {
            flag:$("#flag").val(),

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



    $("#addDlgSaveBtn").on('click', function () {
        var data = $("#fm").serializeArray();
        if (!$("[name='sku']").val()) {
            $.messager.alert('提示信息', '请填写sku');
            return;
        }
        if (!$("[name='category']").val()) {
            $.messager.alert('提示信息', '请填写品类');
            return;
        }


        var url = "/peculiarCategory/insert";
        $.ajax({
            url: url,
            data: data,
            dataType: 'json',
            type: "post",
            success: function (res) {
                //$("#addDlgSaveBtn").removeClass("disabled");
                if (res.success) {
                    $.messager.alert('系统提示', '操作成功', 'info');
                } else {
                    $.messager.alert('系统提示', res.message, 'error');
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
                    url: "/peculiarCategory/delBatch",
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
    window.location.href = "/peculiarCategory/export?" +param;

}

$("#action").click(function() {
    $('#toAction').linkbutton('enable');
    return $('#actionEdit').dialog('open').dialog('setTitle', '请选择年度,期间,类型');
});

