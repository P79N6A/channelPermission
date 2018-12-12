var datagridData_orderForecastGoal = {
    'data': {
        'records': [
            {
                'cOrderSn': '<a href="../order/norderDetail.html">WD170817233445</a>', sortable: true,
                'orderSn': 'rsq_47026570109147374', sortable: true,
                'receipt': '需要', sortable: true,
                'diliveryTime': '6', sortable: true,
                'book': 'NO', sortable: true,
                'isEs': 'NO', sortable: true,
                'productName': 'EC6003-MT3(U1)淘宝海尔热水器专卖店', sortable: true,
                'price': '￥1889.00元', sortable: true,
                'number': '1', sortable: true,
                'money': '￥1889.00元', sortable: true,
                'secCode': 'DBWA', sortable: true,
                'makeOrderTime': '2017-08-17 00:00:03', sortable: true,
                'remark': 'y413110780=', sortable: true,
                'isNotice': 'N', sortable: true,
                'status': '【已确认】【已付款】【待出库】【待开票】', sortable: true,
                'firstComfirmTime': '2017-08-17 00:02:00', sortable: true,
                'haveConfirmTime': '0', sortable: true,
                'personDealTime': '', sortable: true
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
        {title: '网单编号', field: 'cOrderSn', sortable: true},
        {title: '订单编号', field: 'orderSn', sortable: true},
        {title: '发票', field: 'receipt', sortable: true},
        {title: '配送时效(天)', field: 'diliveryTime', sortable: true},
        {title: '预定', field: 'book', sortable: true},
        {title: '节能补贴', field: 'isEs', sortable: true},
        {title: '商品名称', field: 'productName', sortable: true},
        {title: '价格', field: 'price', sortable: true},
        {title: '数量', field: 'number', sortable: true},
        {title: '金额', field: 'money', sortable: true},
        {title: '库位', field: 'secCode', sortable: true},
        {title: '下单时间', field: 'makeOrderTime', sortable: true},
        {title: '备注', field: 'remark', sortable: true},
        {title: '预警', field: 'isNotice', sortable: true},
        {title: '状态', field: 'status', sortable: true},
        {title: '首次确认时间', field: 'firstComfirmTime', sortable: true},
        {title: '已确认次数', field: 'haveConfirmTime', sortable: true},
        {title: '转人工时间', field: 'personDealTime', sortable: true}
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    autoRowHeight:true,
    nowrap:true,
    striped: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var moreOptions = $(".morehide");
    for (var i = 0; i < moreOptions.length; i++) {
        $(moreOptions[i]).css("display", "none");
    }
    ;
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
var showhideoptions = function (t) {
    var $this = $(t);
    if ($this.hasClass('shouqi')) {

        $this.removeClass('shouqi');
        setTimeout(function () {
            $this.addClass('zhankai');
            var moreOptions = $(".morehide");
            for (var i = 0; i < moreOptions.length; i++) {
                $(moreOptions[i]).css("display", "");
            }
            ;
            $("#moreSpan").text("收起");
            $("#moreImag").attr("src", "../static/img/up_icon.png");
        }, 0);

    } else {
        $this.removeClass('zhankai');
        setTimeout(function () {
            $this.addClass('shouqi');
            var moreOptions = $(".morehide");
            for (var i = 0; i < moreOptions.length; i++) {
                $(moreOptions[i]).css("display", "none");
            }
            ;
            $("#moreSpan").text("更多");
            $("#moreImag").attr("src", "../static/img/down_icon.png");
        }, 0);
    }
    setTimeout(function () {
        $('#datagrid_orderForecastGoal').datagrid('resize');
    }, 500);

};
/*
 $('#datagrid_orderForecastGoal').datagrid({

 //双击事件
 onDblClickRow :function(rowIndex,rowData){
 addTab("网单详情[WD170817233445]","order/norderDetail.html",false);
 }
 });*/
