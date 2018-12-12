var datagridData_lesqueues = {
    'data': {
        'records': [
            {
                'ids': '159059885', sortable: true,
                'sorderSn': 'WD170818307295', sortable: true,
                'sorderStatus': '取消关闭', sortable: true,
                'deliveryStatus': '推送失败', sortable: true,
                'deliveryTimes': '1', sortable: true,
                'addQuequeTime': '2017-08-18 15:32:02', sortable: true,
                'lastPushTime': '2017-08-18 15:33:09', sortable: true,
                'pushSuccessTime': '', sortable: true,
                'lockStatus': '未锁定', sortable: true,
                'remarkMsg': '订单取消，关闭推送', sortable: true,
            },
            {
                'ids': '1453059995', sortable: true,
                'sorderSn': 'WD170818307999', sortable: true,
                'sorderStatus': '待出库', sortable: true,
                'deliveryStatus': '推送成功', sortable: true,
                'deliveryTimes': '1', sortable: true,
                'addQuequeTime': '2017-08-18 15:32:02', sortable: true,
                'lastPushTime': '2017-08-18 15:33:09', sortable: true,
                'pushSuccessTime': '', sortable: true,
                'lockStatus': '已锁定', sortable: true,
                'remarkMsg': '下单成功推送', sortable: true,
            },
            {
                'ids': '133059577', sortable: true,
                'sorderSn': 'WD170818307888', sortable: true,
                'sorderStatus': '待出库', sortable: true,
                'deliveryStatus': '推送成功', sortable: true,
                'deliveryTimes': '1', sortable: true,
                'addQuequeTime': '2017-08-18 15:32:02', sortable: true,
                'lastPushTime': '2017-08-18 15:33:09', sortable: true,
                'pushSuccessTime': '', sortable: true,
                'lockStatus': '已锁定', sortable: true,
                'remarkMsg': '下单成功推送', sortable: true,
            },
            {
                'ids': '144059775', sortable: true,
                'sorderSn': 'WD170818307777', sortable: true,
                'sorderStatus': '已出库', sortable: true,
                'deliveryStatus': '推送成功', sortable: true,
                'deliveryTimes': '1', sortable: true,
                'addQuequeTime': '2017-08-18 15:32:02', sortable: true,
                'lastPushTime': '2017-08-18 15:33:09', sortable: true,
                'pushSuccessTime': '', sortable: true,
                'lockStatus': '已锁定', sortable: true,
                'remarkMsg': '出库推送', sortable: true,
            },
            {
                'ids': '176605987', sortable: true,
                'sorderSn': 'WD170818366666', sortable: true,
                'sorderStatus': '已出库', sortable: true,
                'deliveryStatus': '推送成功', sortable: true,
                'deliveryTimes': '1', sortable: true,
                'addQuequeTime': '2017-08-18 15:32:02', sortable: true,
                'lastPushTime': '2017-08-18 15:33:09', sortable: true,
                'pushSuccessTime': '', sortable: true,
                'lockStatus': '已锁定', sortable: true,
                'remarkMsg': '出库推送', sortable: true,
            },
        ], 'totalCount': 5
    }
};

var datagridOptions_lesqueues = {
    fit: true,
    fitColumns: false,
    singleSelect: true,
    //url: '/order/lesqueues/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: 'ID', field: 'ids', sortable: true},
        {title: '网单编号', field: 'sorderSn', sortable: true},
        {title: '网单状态', field: 'sorderStatus', sortable: true},
        {title: '推送状态', field: 'deliveryStatus', sortable: true},
        {title: '推送次数', field: 'deliveryTimes', sortable: true},
        {title: '添加队列时间', field: 'addQuequeTime', sortable: true},
        {title: '上次推送时间', field: 'lastPushTime', sortable: true},
        {title: '推送成功时间', field: 'pushSuccessTime', sortable: true},
        {title: '锁定状态', field: 'lockStatus', sortable: true},
        {title: '备注信息', field: 'remarkMsg', sortable: true}
    ]],
    toolbar: '#datagridToolbar_lesqueues',
    striped: true,
    pagination: true,
    rownumbers: true,
    autoRowHeight:true,
};
$(function () {
    var datagrid = $('#datagrid_lesqueues').datagrid(datagridOptions_lesqueues);
    datagrid.datagrid('loadData', datagridData_lesqueues);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"Lesqueues");
    //$("#searchPanel_lesqueues").panel('resize');

    /*$("#searchBtn_lesqueues").on('click', function (event) {
     var param = $('#paramForm_lesqueues').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/
    $("#addBtn_lesqueues").on('click', function (event) {
        $('#addForm_lesqueues').form('reset');
        $('#addForm_lesqueues').find('[__actType]').val('add');
        $('#addDlg_lesqueues').dialog({'title': '新增'});
        $('#addDlg_lesqueues').dialog('open');
    });
    $("#addDlgSaveBtn_lesqueues").on('click', function () {
        if (!$('#addForm_lesqueues').form('validate')) {
            return;
        }
        var actType = $('#addForm_lesqueues').find('[__actType]').val();
        if (actType === 'add') {
            alert('新增成功');
        } else {
            alert('编辑成功');
        }
        $('#addDlg_lesqueues').dialog('close');
    });
    $("#editBtn_lesqueues").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_lesqueues').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_lesqueues').form('load', selected);
            $('#addForm_lesqueues').find('[__actType]').val('edit');
            $('#addDlg_lesqueues').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_lesqueues").on('click', function (event) {
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
    $("#resetBtn_lesqueues").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_lesqueues').form('reset');
    });
});
