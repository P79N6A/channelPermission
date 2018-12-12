var datagridData_orderForecastGoal = {
    'data': {
        'records': [
            {
                'field0': 'AK', sortable: true,
                'field1': '安康', sortable: true,
                'field2': '陕西', sortable: true,
                'field3': '安康', sortable: true,
                'field4': '汉滨区', sortable: true,
                'field5': '安康市汉滨区五里镇李家坝村正英朝阳产业城', sortable: true,
                'field6': '010097', sortable: true,
                'field7': '18866921011', sortable: true,
                'field8': '', sortable: true,
                'field9': '', sortable: true,
                'field10': '', sortable: true,
            },
            {
                'field0': 'AKS', sortable: true,
                'field1': '阿克苏', sortable: true,
                'field2': '新疆', sortable: true,
                'field3': '新疆', sortable: true,
                'field4': '阿克苏地区', sortable: true,
                'field5': '阿克苏市纺织工业园', sortable: true,
                'field6': '843000', sortable: true,
                'field7': '18866921011', sortable: true,
                'field8': '', sortable: true,
                'field9': '', sortable: true,
                'field10': '', sortable: true,
            },
            {
                'field0': 'BB', sortable: true,
                'field1': '蚌埠', sortable: true,
                'field2': '安徽', sortable: true,
                'field3': '蚌埠', sortable: true,
                'field4': '怀远县', sortable: true,
                'field5': '怀远开发区迎宾路', sortable: true,
                'field6': '010097', sortable: true,
                'field7': '18866921011', sortable: true,
                'field8': '', sortable: true,
                'field9': '', sortable: true,
                'field10': '', sortable: true,
            },
            {
                'field0': 'BD', sortable: true,
                'field1': '保定', sortable: true,
                'field2': '河北', sortable: true,
                'field3': '保定', sortable: true,
                'field4': '新市区', sortable: true,
                'field5': '天威西路', sortable: true,
                'field6': '010097', sortable: true,
                'field7': '18866921011', sortable: true,
                'field8': '', sortable: true,
                'field9': '', sortable: true,
                'field10': '', sortable: true,
            },
            {
                'field0': 'BJ', sortable: true,
                'field1': '北京', sortable: true,
                'field2': '北京', sortable: true,
                'field3': '北京', sortable: true,
                'field4': '朝阳区', sortable: true,
                'field5': '朝阳区黑庄户大鲁店甲1号物流', sortable: true,
                'field6': '010097', sortable: true,
                'field7': '18866921011', sortable: true,
                'field8': '', sortable: true,
                'field9': '', sortable: true,
                'field10': '', sortable: true,
            },
            {
                'field0': 'AK', sortable: true,
                'field1': '包头', sortable: true,
                'field2': '内蒙', sortable: true,
                'field3': '包头', sortable: true,
                'field4': '青山区', sortable: true,
                'field5': '包头市青山区装备制造产业园110国道', sortable: true,
                'field6': '010097', sortable: true,
                'field7': '18866921011', sortable: true,
                'field8': '', sortable: true,
                'field9': '', sortable: true,
                'field10': '', sortable: true,
            },
            {
                'field0': 'CC', sortable: true,
                'field1': '长春', sortable: true,
                'field2': '吉林', sortable: true,
                'field3': '长春', sortable: true,
                'field4': '绿园区', sortable: true,
                'field5': '绿园区核心经济开发区金鹏路111号', sortable: true,
                'field6': '010097', sortable: true,
                'field7': '18866921011', sortable: true,
                'field8': '', sortable: true,
                'field9': '', sortable: true,
                'field10': '', sortable: true,
            },
            {
                'field0': 'CD', sortable: true,
                'field1': '成都', sortable: true,
                'field2': '四川', sortable: true,
                'field3': '成都', sortable: true,
                'field4': '双流县', sortable: true,
                'field5': '双流县腾飞十二路兴发铝业海尔办公室', sortable: true,
                'field6': '010097', sortable: true,
                'field7': '18866921011', sortable: true,
                'field8': '', sortable: true,
                'field9': '', sortable: true,
                'field10': '', sortable: true,
            },
            {
                'field0': 'NJ', sortable: true,
                'field1': '内江', sortable: true,
                'field2': '四川', sortable: true,
                'field3': '内江', sortable: true,
                'field4': '市中区', sortable: true,
                'field5': '市中区汉渝路842号金鑫公司内', sortable: true,
                'field6': '010097', sortable: true,
                'field7': '18866921011', sortable: true,
                'field8': '', sortable: true,
                'field9': '', sortable: true,
                'field10': '', sortable: true,
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
        {title: '仓库编码 ', field: 'field0', sortable: true},
        {title: '仓库名称', field: 'field1', sortable: true},
        {title: '省', field: 'field2', sortable: true},
        {title: '市', field: 'field3', sortable: true},
        {title: '区', field: 'field4', sortable: true},
        {title: '详细地址', field: 'field5', sortable: true},
        {title: '联系人', field: 'field6', sortable: true},
        {title: '手机号 ', field: 'field7', sortable: true},
        {title: '固定电话', field: 'field8', sortable: true},
        {title: '邮箱', field: 'field9', sortable: true},
        {title: '备注', field: 'field10', sortable: true}
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