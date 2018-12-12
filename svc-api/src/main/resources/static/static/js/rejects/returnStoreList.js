var datagridData_orderForecastGoal = {
    'data': {
        'records': [
            {
                'field0': '<a href="../rejects/returnStoreDetail.html">查看详情</a>', sortable: true,
                'field1': 'WD161116577291TC2', sortable: true,
                'field2': '', sortable: true,
                'field3': '非良品', sortable: true,
                'field4': '', sortable: true,
                'field5': '', sortable: true,
                'field6': 'GA0SL201C', sortable: true,
                'field7': 'EC6002-R5', sortable: true,
                'field8': '箱损包装箱可换', sortable: true,
                'field9': '', sortable: true,
                'field10': '1', sortable: true,
            },
            {
                'field0': '<a href="../rejects/returnStoreDetail.html">查看详情</a>', sortable: true,
                'field1': 'WD154111027291TC2', sortable: true,
                'field2': '', sortable: true,
                'field3': '非良品', sortable: true,
                'field4': '', sortable: true,
                'field5': '', sortable: true,
                'field6': 'GA0SL201C', sortable: true,
                'field7': 'EC6002-R5', sortable: true,
                'field8': '箱损包装箱可换', sortable: true,
                'field9': '', sortable: true,
                'field10': '1', sortable: true,
            },
            {
                'field0': '<a href="../rejects/returnStoreDetail.html">查看详情</a>', sortable: true,
                'field1': 'WD161111027691TC2', sortable: true,
                'field2': '', sortable: true,
                'field3': '非良品', sortable: true,
                'field4': '', sortable: true,
                'field5': '', sortable: true,
                'field6': 'GA0SL201C', sortable: true,
                'field7': 'EC6002-R5', sortable: true,
                'field8': '箱损包装箱可换', sortable: true,
                'field9': '', sortable: true,
                'field10': '1', sortable: true,
            },
            {
                'field0': '<a href="../rejects/returnStoreDetail.html">查看详情</a>', sortable: true,
                'field1': 'WD161111027291UT2', sortable: true,
                'field2': '', sortable: true,
                'field3': '非良品', sortable: true,
                'field4': '', sortable: true,
                'field5': '', sortable: true,
                'field6': 'GA0SL201C', sortable: true,
                'field7': 'EC6002-R5', sortable: true,
                'field8': '七天无理由退货', sortable: true,
                'field9': '', sortable: true,
                'field10': '1', sortable: true,
            },
            {
                'field0': '<a href="../rejects/returnStoreDetail.html">查看详情</a>', sortable: true,
                'field1': 'WD161111027291TY2', sortable: true,
                'field2': '', sortable: true,
                'field3': '非良品', sortable: true,
                'field4': '', sortable: true,
                'field5': '', sortable: true,
                'field6': 'GA0SL201C', sortable: true,
                'field7': 'EC6002-R5', sortable: true,
                'field8': '箱损包装箱可换', sortable: true,
                'field9': '', sortable: true,
                'field10': '1', sortable: true,
            },
            {
                'field0': '<a href="../rejects/returnStoreDetail.html">查看详情</a>', sortable: true,
                'field1': 'WD161111027291RC2', sortable: true,
                'field2': '', sortable: true,
                'field3': '非良品', sortable: true,
                'field4': '', sortable: true,
                'field5': '', sortable: true,
                'field6': 'GA0SL201C', sortable: true,
                'field7': 'EC6002-R5', sortable: true,
                'field8': '物流送货延迟一周', sortable: true,
                'field9': '', sortable: true,
                'field10': '1', sortable: true,
            },
        ], 'totalCount': 1
    }
};

var datagridOptions_orderForecastGoal = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/auth/orderForecastGoal/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '单据ID ', field: 'field0', sortable: true},
        {title: 'vom退仓单号', field: 'field1', sortable: true},
        {title: '退出仓库', field: 'field2', sortable: true},
        {title: '退仓类型', field: 'field3', sortable: true},
        {title: '出库单', field: 'field4', sortable: true},
        {title: '出库单状态', field: 'field5', sortable: true},
        {title: '货品编码', field: 'field6', sortable: true},
        {title: '货品名称', field: 'field7', sortable: true},
        {title: 'hp质检结果', field: 'field8', sortable: true},
        {title: '库存类型', field: 'field9', sortable: true},
        {title: '退仓计划数量', field: 'field10', sortable: true},
    ]],
    toolbar: '#datagridToolbar_areapeoplemanage',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_areapeoplemanage').datagrid(datagridOptions_orderForecastGoal);
    datagrid.datagrid('loadData', datagridData_orderForecastGoal);

    //创建表头的菜单
    //CustomConfig.load(datagrid,"OrderForecastGoal");
    //$("#searchPanel_areapeoplemanage").panel('resize');

    /*$("#searchBtn_orderForecastGoal").on('click', function (event) {
     var param = $('#paramForm_areapeoplemanagel').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/
    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_orderForecastGoal').form('reset');
        $('#addForm_orderForecastGoal').find('[__actType]').val('add');
        $('#addDlg_areapeoplemanage').dialog({'title': '新增'});
        $('#addDlg_areapeoplemanage').dialog('open');
    });
    $("#addDlgSaveBtn_orderForecastGoal").on('click', function () {
        if (!$('#addForm_orderForecastGoal').form('validate')) {
            return;
        }
        var actType = $('#addForm_orderForecastGoal').find('[__actType]').val();
        if (actType === 'add') {
            alert('新增成功');
        } else {
            alert('编辑成功');
        }
        $('#addDlg_areapeoplemanage').dialog('close');
    });
    $("#editBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_areapeoplemanage').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_orderForecastGoal').form('load', selected);
            $('#addForm_orderForecastGoal').find('[__actType]').val('edit');
            $('#addDlg_areapeoplemanage').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
            confirm('确定删除？', function (r) {
                if (r) {
                    alert('删除成功');
                }
            })

        } else {
            alert('请选择一条数据');
        }
    });
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_areapeoplemanagel').form('reset');
    });


});
$("#importSave").on('click', function () {
    if (!$('#importForm').form('validate')) {
        return;
    }
    $('#importDialog').dialog('close');
    alert("导入成功！")

})

$("#import").on('click', function (event) {
    $('#importDialog').dialog({'title': '导入'});
    $('#importDialog').dialog('open');
});$("#import1").on('click', function (event) {
    $('#importDialog').dialog({'title': '导入'});
    $('#importDialog').dialog('open');
});