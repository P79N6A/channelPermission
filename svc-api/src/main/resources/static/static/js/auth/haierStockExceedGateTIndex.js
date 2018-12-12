var datagridData_haierStockExceedGateT = {
    'data': {
        'records': [
            {
                'judgeEdChannelId': '商城渠道', sortable: true,
                'exceedAge': '45', sortable: true,
                'limitEdChannelId': '商城渠道', sortable: true,
                'brandType': '本品牌', sortable: true,
                'categoryType': '本品类', sortable: true,
                'modelType': '本型号', sortable: true,
                'storageType': '本库位', sortable: true,
                'isEnabled': '启用', sortable: true,
                'summary': '零售超45天到型号到库位到本渠道闸口', sortable: true
            },
            {
                'judgeEdChannelId': '大客户渠道', sortable: true,
                'exceedAge': '45', sortable: true,
                'limitEdChannelId': '大客户渠道', sortable: true,
                'brandType': '本品牌', sortable: true,
                'categoryType': '本品类', sortable: true,
                'modelType': '本型号', sortable: true,
                'storageType': '本库位', sortable: true,
                'isEnabled': '启用', sortable: true,
                'summary': '零售超30天到型号到库位到本渠道闸口', sortable: true
            },
            {
                'judgeEdChannelId': '天猫渠道', sortable: true,
                'exceedAge': '45', sortable: true,
                'limitEdChannelId': '天猫渠道', sortable: true,
                'brandType': '本品牌', sortable: true,
                'categoryType': '本品类', sortable: true,
                'modelType': '本型号', sortable: true,
                'storageType': '本库位', sortable: true,
                'isEnabled': '启用', sortable: true,
                'summary': '零售超60天到型号到库位到本渠道闸口', sortable: true
            },
            {
                'judgeEdChannelId': '微店渠道', sortable: true,
                'exceedAge': '45', sortable: true,
                'limitEdChannelId': '微店渠道', sortable: true,
                'brandType': '本品牌', sortable: true,
                'categoryType': '本品类', sortable: true,
                'modelType': '本型号', sortable: true,
                'storageType': '本库位', sortable: true,
                'isEnabled': '启用', sortable: true,
                'summary': '零售超75天到型号到库位到本渠道闸口', sortable: true
            },
            {
                'judgeEdChannelId': '商城', sortable: true,
                'exceedAge': '45', sortable: true,
                'limitEdChannelId': '商城', sortable: true,
                'brandType': '本品牌', sortable: true,
                'categoryType': '本品类', sortable: true,
                'modelType': '本型号', sortable: true,
                'storageType': '本库位', sortable: true,
                'isEnabled': '启用', sortable: true,
                'summary': '零售超90天到型号到库位到本渠道闸口', sortable: true
            },
            {
                'judgeEdChannelId': '模卡渠道', sortable: true,
                'exceedAge': '45', sortable: true,
                'limitEdChannelId': '模卡渠道', sortable: true,
                'brandType': '本品牌', sortable: true,
                'categoryType': '本品类', sortable: true,
                'modelType': '本型号', sortable: true,
                'storageType': '本库位', sortable: true,
                'isEnabled': '启用', sortable: true,
                'summary': '零售超15天到型号到库位到本渠道闸口', sortable: true
            },
            {
                'judgeEdChannelId': '微店渠道', sortable: true,
                'exceedAge': '45', sortable: true,
                'limitEdChannelId': '微店渠道', sortable: true,
                'brandType': '本品牌', sortable: true,
                'categoryType': '本品类', sortable: true,
                'modelType': '本型号', sortable: true,
                'storageType': '本库位', sortable: true,
                'isEnabled': '作废', sortable: true,
                'summary': '零售超75天到型号到库位到本渠道闸口', sortable: true
            },
            {
                'judgeEdChannelId': '商城', sortable: true,
                'exceedAge': '45', sortable: true,
                'limitEdChannelId': '商城', sortable: true,
                'brandType': '本品牌', sortable: true,
                'categoryType': '本品类', sortable: true,
                'modelType': '本型号', sortable: true,
                'storageType': '本库位', sortable: true,
                'isEnabled': '作废', sortable: true,
                'summary': '零售超90天到型号到库位到本渠道闸口', sortable: true
            },
            {
                'judgeEdChannelId': '模卡渠道', sortable: true,
                'exceedAge': '45', sortable: true,
                'limitEdChannelId': '模卡渠道', sortable: true,
                'brandType': '本品牌', sortable: true,
                'categoryType': '本品类', sortable: true,
                'modelType': '本型号', sortable: true,
                'storageType': '本库位', sortable: true,
                'isEnabled': '作废', sortable: true,
                'summary': '零售超15天到型号到库位到本渠道闸口', sortable: true
            }
        ], 'totalCount': 10
    }
};

var datagridOptions_haierStockExceedGateT = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/auth/haierStockExceedGateT/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '状态', field: 'isEnabled', sortable: true, rowspan: 2},
        {title: '判断方式', field: 'judgeEdId', sortable: false, colspan: 2, align: "center"},
        {title: '关闸方式', field: 'limitEdId', sortable: false, colspan: 5, align: "center"},
        {title: '说明', field: 'summary', sortable: true, rowspan: 2},

    ],
        [{title: '渠道', field: 'judgeEdChannelId', sortable: true, align: 'center'},
            {title: '超期库龄', field: 'exceedAge', sortable: true},
            {title: '渠道', field: 'limitEdChannelId', sortable: true, align: 'center'},
            {title: '品牌', field: 'brandType', sortable: true},
            {title: '品类', field: 'categoryType', sortable: true},
            {title: '型号', field: 'modelType', sortable: true},
            {title: '库位', field: 'storageType', sortable: true}]
    ],
    toolbar: '#datagridToolbar_haierStockExceedGateT',
    striped: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_haierStockExceedGateT').datagrid(datagridOptions_haierStockExceedGateT);
    datagrid.datagrid('loadData', datagridData_haierStockExceedGateT);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"HaierStockExceedGateT");
    //$("#searchPanel_haierStockExceedGateT").panel('resize');

    /*$("#searchBtn_haierStockExceedGateT").on('click', function (event) {
     var param = $('#paramForm_haierStockExceedGateT').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/
    $("#addBtn_haierStockExceedGateT").on('click', function (event) {
        $('#addForm_haierStockExceedGateT').form('reset');
        $('#addForm_haierStockExceedGateT').find('[__actType]').val('add');
        $('#addDlg_haierStockExceedGateT').dialog({'title': '新增'});
        $('#addDlg_haierStockExceedGateT').dialog('open');
    });
    $("#addDlgSaveBtn_haierStockExceedGateT").on('click', function () {
        if (!$('#addForm_haierStockExceedGateT').form('validate')) {
            return;
        }
        var actType = $('#addForm_haierStockExceedGateT').find('[__actType]').val();
        if (actType === 'add') {
            alert('新增成功');
        } else {
            alert('编辑成功');
        }
        $('#addDlg_haierStockExceedGateT').dialog('close');
    });
    $("#editBtn_haierStockExceedGateT").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_haierStockExceedGateT').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_haierStockExceedGateT').form('load', selected);
            $('#addForm_haierStockExceedGateT').find('[__actType]').val('edit');
            $('#addDlg_haierStockExceedGateT').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_haierStockExceedGateT").on('click', function (event) {
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
    $("#resetBtn_haierStockExceedGateT").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_haierStockExceedGateT').form('reset');
    });
});
