var datagridData_orderForecastGoal = {
    'data': {
        'records': [
            {
                'field0': 'TH00274513345533102', sortable: true,
                'field1': 'LBX00274513345533', sortable: true,
                'field2': '冰箱', sortable: true,
                'field3': 'BH02W10AE', sortable: true,
                'field4': 'BCD-572WDENU1', sortable: true,
                'field5': '1', sortable: true,
                'field6': '22转10', sortable: true,
                'field7': '正品换箱', sortable: true,
                'field8': 'Mary', sortable: true,
                'field9': '2017-04-1814:25:37', sortable: true,
                'field10': '即墨仓库', sortable: true,
                'field11': '', sortable: true
            }

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
        {title: '退仓单号 ', field: 'field0', sortable: true},
        {title: 'LBX单号', field: 'field1', sortable: true},
        {title: '品类', field: 'field2', sortable: true},
        {title: '物料号', field: 'field3', sortable: true},
        {title: '物料描述', field: 'field4', sortable: true},
        {title: '数量', field: 'field5', sortable: true},
        {title: '操作类型', field: 'field6', sortable: true},
        {title: '操作描述', field: 'field7', sortable: true},
        {title: '操作人', field: 'field8', sortable: true},
        {title: '操作时间', field: 'field9', sortable: true},
        {title: '操作仓库', field: 'field10', sortable: true},
        {title: '备注', field: 'field11', sortable: true}

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
});