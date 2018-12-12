var datagridData_waAddressT = {
    'data': {
        'records': [
            {
                'channelCode': '天猫渠道', sortable: true,
                'productGroup': '家用空调', sortable: true,
                'secCode': 'XNWA', sortable: true,
                'coefficient': '0.00010000', sortable: true
            },
            {
                'channelCode': '商城渠道', sortable: true,
                'productGroup': '智饮机', sortable: true,
                'secCode': 'FSWA', sortable: true,
                'coefficient': '0.04470000', sortable: true
            },
            {
                'channelCode': '大客户渠道', sortable: true,
                'productGroup': '社会化彩电', sortable: true,
                'secCode': 'SHWA', sortable: true,
                'coefficient': '0.21210000', sortable: true
            }
        ], 'totalCount': 3
    }
};

var datagridOptions_waAddressT = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/auth/waAddressT/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '渠道', field: 'channelCode', sortable: true},
        {title: '产品组', field: 'productGroup', sortable: true},
        {title: '库存编码', field: 'secCode', sortable: true},
        {title: '库位系数', field: 'coefficient', sortable: true}

    ]],
    toolbar: '#datagridToolbar_waAddressT',
    striped: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_waAddressT').datagrid(datagridOptions_waAddressT);
    // datagrid.datagrid('loadData', datagridData_waAddressT);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"WaAddressT");
    //$("#searchPanel_waAddressT").panel('resize');

    /*$("#searchBtn_waAddressT").on('click', function (event) {
     var param = $('#paramForm_waAddressT').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/
    $("#addBtn_waAddressT").on('click', function (event) {
        $('#addForm_waAddressT').form('reset');
        $('#addForm_waAddressT').find('[__actType]').val('add');
        $('#addDlg_waAddressT').dialog({'title': '新增'});
        $('#addDlg_waAddressT').dialog('open');
    });
    $("#addDlgSaveBtn_waAddressT").on('click', function () {
        if (!$('#addForm_waAddressT').form('validate')) {
            return;
        }
        var actType = $('#addForm_waAddressT').find('[__actType]').val();
        if (actType === 'add') {
            alert('新增成功');
        } else {
            alert('编辑成功');
        }
        $('#addDlg_waAddressT').dialog('close');
    });
    $("#editBtn_waAddressT").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_waAddressT').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_waAddressT').form('load', selected);
            $('#addForm_waAddressT').find('[__actType]').val('edit');
            $('#addDlg_waAddressT').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_waAddressT").on('click', function (event) {
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
    $("#resetBtn_waAddressT").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_waAddressT').form('reset');
    });
});
