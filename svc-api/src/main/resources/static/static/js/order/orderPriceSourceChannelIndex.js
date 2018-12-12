var datagridData_orderPriceSourceChannel = {
    'data': {
        'records': [
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '淘宝海尔官方旗舰店', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '海尔洗衣机官方旗舰店', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '海尔冰冷官方旗舰店', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '模卡官方旗舰店', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '淘宝海尔热水器专卖店', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '统帅日日顺乐家专卖店', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '淘宝空调旗舰店', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '天猫模卡分销店铺', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '淘宝海尔官方旗舰店', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
            {
                'channelCodeName': '天猫', sortable: true,
                'orderSourceName': '淘宝海尔洗衣机官方旗舰店', sortable: true,
                'restrictStatus': '生效', sortable: true,
                'restrictType': '保本价', sortable: true,
                'responsePerson': '高开机', sortable: true,
                'mobile': '', sortable: true,
                'email': 'tm@haier.com.yy@ehaier.com', sortable: true,
                'sendType': '邮件', sortable: true,
            },
        ], 'totalCount': 10
    }
};

var datagridOptions_orderPriceSourceChannel = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/order/orderPriceSourceChannel/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '渠道名称', field: 'channelCodeName', sortable: true},
        {title: '订单来源名称', field: 'orderSourceName', sortable: true},
        {title: '闸口状态', field: 'restrictStatus', sortable: true},
        {title: '闸口类型', field: 'restrictType', sortable: true},
        {title: '负责人', field: 'responsePerson', sortable: true},
        {title: '手机号码', field: 'mobile', sortable: true},
        {title: '电子邮件', field: 'email', sortable: true},
        {title: '发送类型', field: 'sendType', sortable: true},
    ]],
    toolbar: '#datagridToolbar_orderPriceSourceChannel',
    striped: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_orderPriceSourceChannel').datagrid(datagridOptions_orderPriceSourceChannel);
    datagrid.datagrid('loadData', datagridData_orderPriceSourceChannel);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"OrderPriceSourceChannel");
    //$("#searchPanel_orderPriceSourceChannel").panel('resize');

    /*$("#searchBtn_orderPriceSourceChannel").on('click', function (event) {
     var param = $('#paramForm_orderPriceSourceChannel').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/
    $("#addBtn_orderPriceSourceChannel").on('click', function (event) {
        $('#addForm_orderPriceSourceChannel').form('reset');
        $('#addForm_orderPriceSourceChannel').find('[__actType]').val('add');
        $('#addDlg_orderPriceSourceChannel').dialog({'title': '新增'});
        $('#addDlg_orderPriceSourceChannel').dialog('open');
    });
    $("#addDlgSaveBtn_orderPriceSourceChannel").on('click', function () {
        if (!$('#addForm_orderPriceSourceChannel').form('validate')) {
            return;
        }
        var actType = $('#addForm_orderPriceSourceChannel').find('[__actType]').val();
        if (actType === 'add') {
            alert('新增成功');
        } else {
            alert('编辑成功');
        }
        $('#addDlg_orderPriceSourceChannel').dialog('close');
    });
    $("#editBtn_orderPriceSourceChannel").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_orderPriceSourceChannel').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_orderPriceSourceChannel').form('load', selected);
            $('#addForm_orderPriceSourceChannel').find('[__actType]').val('edit');
            $('#addDlg_orderPriceSourceChannel').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_orderPriceSourceChannel").on('click', function (event) {
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
    $("#resetBtn_orderPriceSourceChannel").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderPriceSourceChannel').form('reset');
    });
});
