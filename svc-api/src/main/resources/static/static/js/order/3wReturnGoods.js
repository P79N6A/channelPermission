var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
    // fitColumns: true,//fitColumns: true,
    singleSelect: true,//多选
    url: '',
    columns: [[
        {title: 'LBX单号 ', field: 'returnOrderId', sortable: false},
        {title: '姓名', field: 'name', sortable: false},
        {title: '手机号', field: 'mobile', sortable: false},
        {title: '订单确认时间', field: 'orderConfirmTime', sortable: false},
        {title: '子交易单号', field: 'subSourceOrderCode', sortable: false, align: 'right'},
        {title: '物料编码', field: 'itemCode', sortable: false,},
        {title: '数量', field: 'actualQty', sortable: false, },
        {title: '详细地址', field: 'detailAddress', sortable: false},
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
        },
        {
            title: '操作', field: 'exceptionTimes', sortable: false, formatter: function (val, row) {
                if (val > 7) {
                    return "<a>手动匹配</a>";
                }
                else {
                    return "";
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
        url: "/exception/findReturnGoods",
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
            subSourceOrderCode: $("#subSourceOrderCode").val()
        },
        columns: [[
            {title: 'LBX单号 ', field: 'returnOrderId', sortable: false},
            {title: '姓名', field: 'name', sortable: false},
            {title: '手机号', field: 'mobile', sortable: false},
            {title: '订单确认时间', field: 'orderConfirmTime', sortable: false,formatter : formatDatebox},
            {title: '子交易单号', field: 'subSourceOrderCode', sortable: false, align: 'right'},
            {title: '物料编码', field: 'itemCode', sortable: false,},
            {title: '数量', field: 'actualQty', sortable: false, },
            {title: '详细地址', field: 'detailAddress', sortable: false},
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
            },
            {
                title: '操作', field: 'exceptionTimes', sortable: false, formatter: function (val, row) {
                    if (val > 7&&row.status != 2) {
                        return '<a href="javascript:void(0);" onclick="returnGoodsPushSAP('+row.id+')">手动匹配</a>';
                    }
                    else {
                        return "";
                    }
                }
            }

        ]],
    });
}

function returnGoodsPushSAP(id) {
    $.ajax({
        url: "returnGoodsPushSAP?id="+id,
        success: function(data) {
               $.messager.alert("操作提示",data);
                SearchUnit();

        }
    });


}

