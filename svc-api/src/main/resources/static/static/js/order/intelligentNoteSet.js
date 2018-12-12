var datagridData_orderForecastGoal = {
    'data': {
        'records': [
            {
                'sendObj':'长沙工贸', sortable: true,
                'name': '刘玉玲', sortable: true,
                'mobileNo': '186612654321', sortable: true,
                'telephoneNo': '0532-87678956', sortable: true,
                'appealTimes': '1', sortable: true,
                'isSend': '是', sortable: true,

            },
            {
                'sendObj':'银川工贸', sortable: true,
                'name': '王晓理', sortable: true,
                'mobileNo': '18661234467', sortable: true,
                'telephoneNo': '0532-87678956', sortable: true,
                'appealTimes': '1', sortable: true,
                'isSend': '是', sortable: true,

            },
            {
                'sendObj':'襄樊工贸', sortable: true,
                'name': '刘玉玲', sortable: true,
                'mobileNo': '186612344321', sortable: true,
                'telephoneNo': '0532-87678956', sortable: true,
                'appealTimes': '1', sortable: true,
                'isSend': '是', sortable: true,

            },
            {
                'sendObj':'深圳工贸', sortable: true,
                'name': '刘彩凤', sortable: true,
                'mobileNo': '185612344111', sortable: true,
                'telephoneNo': '0532-87678956', sortable: true,
                'appealTimes': '1', sortable: true,
                'isSend': '是', sortable: true,

            },
            {
                'sendObj':'成都工贸', sortable: true,
                'name': '王传英', sortable: true,
                'mobileNo': '186613334321', sortable: true,
                'telephoneNo': '0532-87678956', sortable: true,
                'appealTimes': '1', sortable: true,
                'isSend': '是', sortable: true,

            },
            {
                'sendObj':'锦州工贸', sortable: true,
                'name': '王川玉', sortable: true,
                'mobileNo': '15023483456', sortable: true,
                'telephoneNo': '0532-87678234', sortable: true,
                'appealTimes': '1', sortable: true,
                'isSend': '是', sortable: true,

            },
            {
                'sendObj':'合肥工贸', sortable: true,
                'name': '戈瑞沙', sortable: true,
                'mobileNo': '132956758734', sortable: true,
                'telephoneNo': '0532-87674556', sortable: true,
                'appealTimes': '1', sortable: true,
                'isSend': '是', sortable: true,

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
        {title: '发送对象 ', field: 'sendObj', sortable: true},
        {title: '姓名', field: 'name', sortable: true},
        {title: '手机号码', field: 'mobileNo', sortable: true},
        {title: '座机号码', field: 'telephoneNo', sortable: true},
        {title: '申诉次数', field: 'appealTimes', sortable: true},
        {title: '是否发送', field: 'isSend', sortable: true},

    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    striped: true,
    autoRowHeight:true,
    nowrap:true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);
    datagrid.datagrid('loadData', datagridData_orderForecastGoal);

    //创建表头的菜单
    //CustomConfig.load(datagrid,"OrderForecastGoal");
    //$("#searchPanel_orderForecastGoal").panel('resize');

    /*$("#searchBtn_orderForecastGoal").on('click', function (event) {
     var param = $('#paramForm_orderForecastGoal').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/
    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_orderForecastGoal').form('reset');
        $('#addForm_orderForecastGoal').find('[__actType]').val('add');
        $('#addDlg_orderForecastGoal').dialog({'title': '新增'});
        $('#addDlg_orderForecastGoal').dialog('open');
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
        $('#addDlg_orderForecastGoal').dialog('close');
    });
    $("#editBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_orderForecastGoal').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_orderForecastGoal').form('load', selected);
            $('#addForm_orderForecastGoal').find('[__actType]').val('edit');
            $('#addDlg_orderForecastGoal').dialog('open');
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
        $('#paramForm_orderForecastGoal').form('reset');
    });


});
