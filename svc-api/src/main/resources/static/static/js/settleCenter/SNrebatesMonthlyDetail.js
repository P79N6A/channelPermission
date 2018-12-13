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

        dataGrid.datagrid("load");

    });

});





function exp() {
    var data = $('#dg').datagrid('getData');
    if (data.rows.length <= 0) {
        $.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
        return;
    }
    //var selectedvalue=$("input[name='tab']:checked").val();
    var param = $("#paramForm").serialize();
    window.location.href = "/SNrebatesMonthlyDetail/export?" +param;

}





