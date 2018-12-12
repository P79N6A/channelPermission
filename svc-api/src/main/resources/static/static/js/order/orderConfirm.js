var datagridData_orderForecastLogs = {
    'data': {
        'records': [
            {
                'secCode': 'CCWA', sortable: true,
                'secName': '长春库位', sortable: true,
                'productType': '洗衣机', sortable: true,
                'sku': 'CB013K00M', sortable: true,
                'skuName': 'XQB55-M1268 关爱', sortable: true,

                'stock2MonthOverdue': '6', sortable: true,

                'shopTwoWeeksSales': '0', sortable: true,
                'shopWeekGoal': '0', sortable: true,
                'shopInTransit': '0', sortable: true,
                'shopSurplusStock': '0', sortable: true,
                'shopForecast': '0', sortable: true,

                'tbTwoWeeksSales': '0', sortable: true,
                'tbWeekGoal': '0', sortable: true,
                'tbInTransit': '0', sortable: true,
                'tbSurplusStock': '0', sortable: true,
                'tbForecast': '0', sortable: true,

                'keyaccountTwoWeeksSales': '0', sortable: true,
                'keyaccountWeekGoal': '0', sortable: true,
                'keyaccountInTransit': '0', sortable: true,
                'keyaccountSurplusStock': '0', sortable: true,
                'keyaccountForecast': '0', sortable: true
            }
        ], 'totalCount': 1
    }
};
var v1_datagridData_orderForecastLogs = {
    'data': {
        'records': [
            {
                'secCode': 'CCWA', sortable: true,
                'secName': '长春库位', sortable: true,
                'productType': '洗衣机', sortable: true,
                'sku': 'CB013K00M', sortable: true,
                'skuName': 'XQB55-M1268 关爱', sortable: true,

                'channel_sc': '商城渠道', sortable: true,
                'keyaccountForecast': '0', sortable: true,
                'seasonPer': '100.0%', sortable: true,
            }
        ], 'totalCount': 1
    }
};
var v2_datagridData_orderForecastLogs = {
    'data': {
        'records': [
            {
                'secCode': 'CCWA', sortable: true,
                'secName': '长春库位', sortable: true,
                'productType': '洗衣机', sortable: true,
                'sku': 'CB013K00M', sortable: true,
                'skuName': 'XQB55-M1268 关爱', sortable: true,

                'stock2MonthOverdue': '6', sortable: true,
                'channelCode': '天猫商城', sortable: true,
                'forecastCount': '5', sortable: true,
                'seasonPer': '100.0%', sortable: true
            }
        ], 'totalCount': 1
    }
};

var datagridOptions_orderForecastLogs = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/predictive/orderForecastLogs/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '网单编号', field: 'secCode', sortable: true,},
        {title: '网单状态', field: 'secName', sortable: true},
        {title: '超时类型', field: 'overTimeType', sortable: true},
        {title: '应完成时间', field: 'finishTime', sortable: true},
        {title: '收货人', field: 'receivePerson', sortable: true},
        {title: '电话', field: 'telephone', align: "center"},
        {title: '地址', field: 'location', sortable: true},
        {title: '配送时效', field: 'deliverRight', sortable: true},
        {title: '是否超时免单', field: 'seasonPer', sortable: true},
        {title: '订单来源', field: 'seasonPer', sortable: true}


    ]],
    autoRowHeight: true,
    nowrap: false,
    toolbar: '#datagridToolbar_orderForecastLogs',
    striped: true,
    pagination: true,
    rownumbers: true,
};
var v1_datagridOptions_orderForecastLogs = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/predictive/orderForecastLogs/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '网单编号', field: 'secCode', sortable: true,},
        {title: '网单状态', field: 'secName', sortable: true},
        {title: '预警类型', field: 'predictiveType', sortable: true},
        {title: '预警时间', field: 'predictiveTime', sortable: true},
        {title: '应完成时间', field: 'finishTime', sortable: true},
        {title: '收货人', field: 'receivePerson', align: "center"},
        {title: '电话', field: 'telephone', sortable: true},
        {title: '地址', field: 'location', sortable: true},
        {title: '配送时效', field: 'deliverRight', sortable: true},
        {title: '是否超时免单', field: 'isOverTimeFree', sortable: true}
    ]],
    autoRowHeight: true,
    nowrap: false,
    toolbar: '#datagridToolbar_orderForecastLogs',
    striped: true,
    pagination: true,
    rownumbers: true,
};
var v2_datagridOptions_orderForecastLogs = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/predictive/orderForecastLogs/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '网单编号', field: 'secCode', sortable: true,},
        {title: '网单状态', field: 'secName', sortable: true},
        {title: '超时类型', field: 'overTimeType', sortable: true},
        {title: '应完成时间', field: 'finishTime', sortable: true},
        {title: '收货人', field: 'receivePerson', align: "center"},
        {title: '电话', field: 'telephone', sortable: true},
        {title: '地址', field: 'location', sortable: true},
        {title: '配送时效', field: 'deliverRight', sortable: true},
        {title: '是否超时免单', field: 'isOverTimeFree', sortable: true},
        {title: '订单来源', field: 'orderSource', sortable: true}

    ]],
    autoRowHeight: true,
    nowrap: false,
    toolbar: '#datagridToolbar_orderForecastLogs',
    striped: true,
    pagination: true,
    rownumbers: true,
};

$(function () {
    var datagrid = $('#datagrid_orderForecastLogs').datagrid(datagridOptions_orderForecastLogs);
    datagrid.datagrid('loadData', datagridData_orderForecastLogs);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"OrderForecastLogs");
    //$("#searchPanel_orderForecastLogs").panel('resize');

    /*$("#searchBtn_orderForecastLogs").on('click', function (event) {
     var param = $('#paramForm_orderForecastLogs').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/

    $("#resetBtn_orderForecastLogs").on('click', function (event) {
        event.preventDefault();
        $('#1paramForm_orderForecastLogs').form('reset');
    });

    //$('.datagrid-header .datagrid-cell span ').css('font-size', '12px'); //datagrid中的列名称字体大小调整
    $("#mainsheet").css("background-color", 'rgba(0, 0, 255, 0.22)');
});
var on_tab = function (e) {
    if (e == "main") {
        $("#mainsheet").css("background-color", 'rgba(0, 0, 255, 0.22)');
        $("#secsheet").css("background-color", '#efefef');
        $("#skusheet").css("background-color", '#efefef');
        var datagrid = $('#datagrid_orderForecastLogs').datagrid(datagridOptions_orderForecastLogs);
        datagrid.datagrid('loadData', datagridData_orderForecastLogs);
    }
    if (e == "sec") {
        $("#secsheet").css("background-color", 'rgba(0, 0, 255, 0.22)');
        $("#mainsheet").css("background-color", '#efefef');
        $("#skusheet").css("background-color", '#efefef');
        var v1_datagrid = $('#datagrid_orderForecastLogs').datagrid(v1_datagridOptions_orderForecastLogs);
        v1_datagrid.datagrid('loadData', v1_datagridData_orderForecastLogs);

    }
    if (e == "sku") {
        $("#skusheet").css("background-color", 'rgba(0, 0, 255, 0.22)');
        $("#secsheet").css("background-color", '#efefef');
        $("#mainsheet").css("background-color", '#efefef');
        var v2_datagrid = $('#datagrid_orderForecastLogs').datagrid(v2_datagridOptions_orderForecastLogs);
        v2_datagrid.datagrid('loadData', v2_datagridData_orderForecastLogs);
    }

};
