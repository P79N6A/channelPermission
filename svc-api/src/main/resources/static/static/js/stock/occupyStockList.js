var datagrid_occupyStockFail = {
    fit: true,//自适应
    fitColumns: false,//fitColumns: true,
    singleSelect: true,//多选
    url: '',
    frozenColumns: [[{
        field: 'id',
        checkbox: false,
        hidden:true
    }]],
    columns: [[
        {title: '订单编号', field: 'orderSn', sortable: true},
        {title: '网单编号', field: 'corderSn', sortable: true},
        {title: '订单ID', field: 'orderId', sortable: true},
        {title: '状态', field: 'status', sortable: true,hidden: true},
        {title: '货号', field: 'sku', sortable: true},
        {title: '数量', field: 'number', sortable: true},
        {title: '曾经锁定的库存数量', field: 'lockedNumber', sortable: true},
        {title: '库位编码', field: 'sCode', sortable: true, hidden: true},
        {title: '转运库房编码', field: 'tsCode', sortable: true, hidden: true},
        {title: '转运时效（小时）', field: 'tsShippingTime', sortable: true},
        {title: '物流模式值', field: 'shippingMode', sortable: true},
        {title: '商品类型', field: 'typeName', sortable: true},
        {title: '系统备注', field: 'systemRemark', sortable: true, hidden: true},
        {title: '开票类型', field: 'makeReceiptType', sortable: true},
        {title: '网点id', field: 'netPointId', sortable: true, hidden: true},
        {title: 'HP注册时间', field: 'hpReservationDate', sortable: true, hidden: true},
        {title: '活动订单发货时机', field: 'shippingOpporunity', sortable: true},
        {title: '库存类型', field: 'stockType', sortable: true},
        {title: 'o2o网单类型', field: 'o2oType', sortable: true},
        {title: '店铺id', field: 'storeId', sortable: true, hidden: true},
        {title: '店铺类型', field: 'storeType', sortable: true,hidden: true},
        {title: '佣金类别', field: 'brokerageType', sortable: true, hidden: true}
    ]],
    toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 10,
    pageList: [10, 20, 50],
};
$(function () {
    var datagrid = $('#occupyStockFailTable').datagrid(datagrid_occupyStockFail);

    $("#resetBtn_occupyStock").on('click', function (event) {
        event.preventDefault();
        $('#filterForm').form('reset');
    });

    $("#occupyStock").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
            var postParam = {
                'orderSn' : selected.orderSn,
                'corderSn' : selected.corderSn,
                'id': selected.id
            }
            $.ajax({
                type: "post",
                url: '/occupyStockFailController/againOccupyStock',
                data: postParam,
                dataType: 'json',
                success: function (data) {
                    alert(data.message);
                    $('#occupyStockFailTable').datagrid('reload');
                },
                error: function () {
                    alert("重新占用库存失败");
                    $('#occupyStockFailTable').datagrid('reload');
                }
            });
        } else {
            $.messager.alert('提示', '请选择一条数据');
        }
    });
});

//点击查询
$("#searchBtn").on('click', function (event) {

    //加载分页
    datagrid = $('#occupyStockFailTable').datagrid({
        url: "/occupyStockFailController/getOccupyStockFail",
        fit: true,
        fitColumns: false,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 10,
        pageList: [10, 20, 50],
        queryParams: {
            orderSn: $("#orderSn").val(),
            corderSn: $("#corderSn").val()
        },
        frozenColumns: [[{
            field: 'id',
            checkbox: false,
            hidden:true
        }]],
        columns: [[
            {title: '订单编号', field: 'orderSn', sortable: true},
            {title: '网单编号', field: 'corderSn', sortable: true},
            {title: '订单ID', field: 'orderId', sortable: true},
            {title: '状态', field: 'status', sortable: true,hidden: true},
            {title: '货号', field: 'sku', sortable: true},
            {title: '数量', field: 'number', sortable: true},
            {title: '曾经锁定的库存数量', field: 'lockedNumber', sortable: true},
            {title: '库位编码', field: 'sCode', sortable: true, hidden: true},
            {title: '转运库房编码', field: 'tsCode', sortable: true, hidden: true},
            {title: '转运时效（小时）', field: 'tsShippingTime', sortable: true},
            {title: '物流模式值', field: 'shippingMode', sortable: true},
            {title: '商品类型', field: 'typeName', sortable: true},
            {title: '系统备注', field: 'systemRemark', sortable: true, hidden: true},
            {title: '开票类型', field: 'makeReceiptType', sortable: true,
                formatter: function (val) {
                    if (val == "0") {
                        return "初始值";
                    } else if (val == "1") {
                        return "库房开票";
                    } else if (val == "2") {
                        return "共享开票";
                    }
                }
            },
            {title: '网点id', field: 'netPointId', sortable: true, hidden: true},
            {title: 'HP注册时间', field: 'hpReservationDate', sortable: true, hidden: true},
            {title: '活动订单发货时机', field: 'shippingOpporunity', sortable: true,
                formatter: function(val) {
                    if(val == '0') {
                        return "定金发货";
                    }else if(val == '1') {
                        return "尾款发货";
                    }
                }
            },
            {title: '库存类型', field: 'stockType', sortable: true,
                formatter: function(val) {
                    if(val == 'WA') {
                        return "自有库存";
                    }else if(val == 'STORE') {
                        return "店铺";
                    }else if(val == '3W') {
                        return "3W库存";
                    }
                }
            },
            {title: 'o2o网单类型', field: 'o2oType', sortable: true,
                formatter: function(val) {
                    if(val == '1') {
                        return "非O2O网单";
                    }else if(val == '2') {
                        return "线下用户分销商城";
                    }else if(val == '3') {
                        return "商城分销旗舰店";
                    } else if(val == '4') {
                        return "创客";
                    }
                }
            },
            {title: '店铺id', field: 'storeId', sortable: true, hidden: true},
            {title: '店铺类型', field: 'storeType', sortable: true,hidden: true},
            {title: '佣金类别', field: 'brokerageType', sortable: true, hidden: true}

        ]],
    });
});

