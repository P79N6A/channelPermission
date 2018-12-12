var datagridData_orderForecastGoal = {
    'data': {
        'records': [
            {
                'field0': '西区', sortable: true,
                'field1': '重庆工贸', sortable: true,
                'field2': '马秀军', sortable: true,
                'field3': '王川英', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '银川工贸', sortable: true,
                'field2': '康佳', sortable: true,
                'field3': '王川英', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '西安工贸', sortable: true,
                'field2': '郑文杰', sortable: true,
                'field3': '王青霞', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '兰州工贸', sortable: true,
                'field2': '高攀', sortable: true,
                'field3': '王川英', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '昆明工贸', sortable: true,
                'field2': '王晓丽', sortable: true,
                'field3': '王青霞', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '贵阳工贸', sortable: true,
                'field2': '孙导航', sortable: true,
                'field3': '李珍菊', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '成都工贸', sortable: true,
                'field2': '高攀', sortable: true,
                'field3': '尹李斌', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '厦门工贸', sortable: true,
                'field2': '马秀军', sortable: true,
                'field3': '王川英', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '武汉工贸', sortable: true,
                'field2': '张宪文', sortable: true,
                'field3': '李东华', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '深圳工贸', sortable: true,
                'field2': '刘玉玲', sortable: true,
                'field3': '汤秀', sortable: true,
                'field4': '2', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
            },
            {
                'field0': '西区', sortable: true,
                'field1': '贵州工贸', sortable: true,
                'field2': '高飞', sortable: true,
                'field3': '刘海', sortable: true,
                'field4': '3', sortable: true,
                'field5': '巨斌@邹亚东@张霞', sortable: true
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
        {title: '区域 ', field: 'field0', sortable: true},
        {title: '工贸', field: 'field1', sortable: true},
        {title: '商城专员', field: 'field2', sortable: true},
        {title: '天猫专员', field: 'field3', sortable: true},
        {title: '区域主管', field: 'field4', sortable: true},
        {title: '42工贸专员', field: 'field5', sortable: true}
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
