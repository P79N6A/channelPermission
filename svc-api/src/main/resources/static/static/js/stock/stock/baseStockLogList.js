var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
    singleSelect: true,//多选
    url: '',
    columns: [[
        {title: '单据号 ', field: 'refNo', sortable: false},
        {title: '物料编号 ', field: 'sku', sortable: false},
        {title: '物料型号', field: 'productName', sortable: false},
        {title: '库位编码', field: 'secCode', sortable: false},
        {title: '库位名称', field: 'secName', sortable: false},
        { title:'借贷标志', field: "mark",sortable: false},
        {title: '日志内容', field: 'content', sortable: false},
        {
            title: '创建时间', field: 'createTime', sortable: false, formatter: formatDatebox
        },
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
    var datagrid = $('#invBaseStockLogTable').datagrid(datagridOptions_orderForecastGoal);
    onBillType();
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
        onBillType();
    });

    $("#product_type_name").combobox({
        prompt: '输入关键字自动检索',
        url: '/stock/getAllProductTypes',
        valueField: "value",
        textField: "text",
        required: false,
        editable: true,
        hasDownArrow: true,
        filter: function (q, row) {
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;
        },
        onSelect: function (record) {
        },
        onUnselect: function () {
        }
    });
});

//查询
$("#searchBtn").on('click', function (event) {

    var refNoData = $("#refNo").textbox("getValue");
    var secCodeData = $("#secCode").textbox("getValue");
    var skuData = $("#sku").textbox("getValue");
    var startCreateTimeData = $("#startCreateTime").textbox("getValue");
    var endCreateTimeData = $("#endCreateTime").textbox("getValue");
        if (startCreateTimeData > endCreateTimeData) {
            alert("开始时间应该小于结束时间");
            return ;
        }
    
    var markData = $("#mark").textbox("getValue");
    var billTypeData = $("#bill_type").val();

    $("#code").val("yes");
    $("#secCodeData").val(secCodeData);
    $("#skuData").val(skuData);
    $("#refNoData").val(refNoData);
    $("#startCreateTimeData").val(startCreateTimeData);
    $("#endCreateTimeData").val(endCreateTimeData);
    $("#markData").val(markData);
    $("#billTypeData").val(billTypeData);


   
    datagrid = $('#invBaseStockLogTable').datagrid({
        url: "/stock/findBaseStockLogList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: false,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        queryParams: {
            secCode: secCodeData,
            sku: skuData,
            refNo: refNoData,
            startCreateTime: startCreateTimeData,
            endCreateTime: endCreateTimeData,
            mark: markData,
            billType: billTypeData
        },
        columns: [[
            {title: '单据号 ', field: 'refNo', sortable: false},
            {title: '物料编号 ', field: 'sku', sortable: false},
            {title: '物料型号', field: 'productName', sortable: false},
            {title: '库位编码', field: 'secCode', sortable: false},
            {title: '库位名称', field: 'secName', sortable: false},
            {
                field: "mark",
                title: '借贷标志',
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == 'S') {
                        return '入库';
                    } else {
                        return '出库';
                    }
                }
            },
            {
                title: '日志内容', field: 'content', sortable: false, width: 400
            },
            {
                title: '创建时间', field: 'createTime', sortable: false, formatter: formatDatebox,
                width: 170
            },
        ]],
    });
});


$("#mark").combobox({
    onChange: function (n, o) {
        var value = $("#mark").val();
        if (value == 'H') {
            $("#bill_type").empty();
            $("#bill_type").append("<option value=''>全部</option>");
            for (var key in hBillType) {
                $("#bill_type").append("<option value='" + key + "'>" + hBillType[key] + "</option>");
            }
        } else if (value == 'S') {
            $("#bill_type").empty();
            $("#bill_type").append("<option value=''>全部</option>");

            for (var key in sBillType) {
                $("#bill_type").append("<option value='" + key + "'>" + sBillType[key] + "</option>");
            }
        } else {
            $("#bill_type").empty();
            $("#bill_type").append("<option value=''>全部</option>");
            for (var key in hBillType) {
                $("#bill_type").append("<option value='" + key + "'>" + hBillType[key] + "</option>");
            }

            for (var key in sBillType) {
                $("#bill_type").append("<option value='" + key + "'>" + sBillType[key] + "</option>");
            }
        }
    }
});
function onBillType() {
    $("#bill_type").empty();
    $("#bill_type").append("<option value=''>全部</option>");
    for (var key in hBillType) {
        $("#bill_type").append("<option value='" + key + "'>" + hBillType[key] + "</option>");
    }

    for (var key in sBillType) {
        $("#bill_type").append("<option value='" + key + "'>" + sBillType[key] + "</option>");
    }
}

var hBillType = {
    ZBCC: '销售出库',
    YTIB: '存性变更出库',
    ZGI6: '调拨出库',
    BRSI: '转运出库',
    ZBJT: '京东虚拟退货出库',
    DBFF: '调拨冻结',
    XSFF: '销售冻结',
    SDFF: '手动冻结'
};
var sBillType = {
    ZBCR: '采购入库',
    YTRB: '存性变更入库',
    ZBCT: '退货入库',
    ZBCJ: '拒收入库',
    ZGR6: '调拨入库',
    ZRSR: '转运入库',
    QYRK: '迁移入库',
    DBRR: '取消调拨释放',
    XSRR: '取消销售释放',
    SDRR: '取消手动释放',
    RARR: '重新分配库位释放'
};

$("#importBtn_productList").click(function () {
    $("#exportForm").attr("action", "/stock/exportBaseStockLogList");
    $("#exportForm").submit();
});
// 日期格式化方法
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
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
}