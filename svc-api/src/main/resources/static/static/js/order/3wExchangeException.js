var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
    // fitColumns: true,//fitColumns: true,
    singleSelect: true,//多选
    url: '',
    columns: [[
        {title: 'BMS单号 ', field: 'ordercode', sortable: false},
        {title: '仓库编码', field: 'storecode', sortable: false},
        {title: '仓库作业单号', field: 'storeordercode', sortable: false},
        {title: '出库时间', field: 'consigntime', sortable: false},
        {title: '主交易单号', field: 'tradeid', sortable: false},
        {title: '子交易单号', field: 'tradeitemid', sortable: false, align: 'right'},
        {title: '物料编码', field: 'itemcode', sortable: false,},
        {title: '数量', field: 'itemquantity', sortable: false, },
        {title: '商品金额', field: 'itemamount', sortable: false},
        {title: '旧网单号', field: 'orderProductsCn', sortable: false},
        {title: '新网单号', field: 'orderProductsCnNew', sortable: false},
        {
            title: '状态', field: 'status', sortable: false, formatter: function (val,row) {
                if (val==0) {
                    return "未匹配";
                }
                if (val==1) {
                    return "匹配异常";
                }
                if (val==2) {
                    return "匹配成功";
                }
            }
        }
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
};

$(function () {
    var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);

    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
    });
});

$("#searchBtn_orderForecastGoal").click(function(){
    SearchUnit();
});
// 日期格式化
function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }

    return dt.format("yyyy-MM-dd hh:mm:ss"); // 扩展的Date的format方法(上述插件实现)
};
function SearchUnit() {
    var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);

    datagrid = $('#datagrid_orderForecastGoal').datagrid({
        url: "/exception/search",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50,100,200],
        queryParams:{
            storeordercode: $("#storeordercode").val()
        },
        columns: [[
            {title: 'BMS单号 ', field: 'ordercode', sortable: false},
            {title: '仓库编码', field: 'storecode', sortable: false},
            {title: '仓库作业单号', field: 'storeordercode', sortable: false},
            {title: '出库时间', field: 'consigntime', sortable: false,formatter : formatDatebox},
            {title: '主交易单号', field: 'tradeid', sortable: false},
            {title: '子交易单号', field: 'tradeitemid', sortable: false, align: 'right'},
            {title: '物料编码', field: 'itemcode', sortable: false,},
            {title: '数量', field: 'itemquantity', sortable: false, },
            {title: '商品金额', field: 'itemamount', sortable: false},
            {title: '旧网单号', field: 'orderProductsCn', sortable: false},
            {title: '新网单号', field: 'orderProductsCnNew', sortable: false},
            {
                title: '状态', field: 'status', sortable: false, formatter: function (val,row) {
                    if (val==0) {
                        return "未匹配";
                    }
                    if (val==1) {
                        return "匹配异常";
                    }
                    if (val==2) {
                        return "匹配成功";
                    }
                }
            }
        ]],
    });
}

