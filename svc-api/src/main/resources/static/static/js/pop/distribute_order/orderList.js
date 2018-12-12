var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
    // fitColumns: true,//fitColumns: true,
    singleSelect: true,//多选
    url: '',
    // frozenColumns: [[{
    //     field: 'id', checkbox: true
    // }]],
    columns: [[
        {title: '订单号 ', field: 'orderSn', sortable: true},
//        {title: '货到付款', field: 'isCodDisplay', sortable: true},
//        {title: '预定', field: 'book', sortable: true},
        {title: '订单来源', field: 'source', sortable: true},
        {title: '来源订单号', field: 'sourceOrderSn', sortable: true},
//        {title: '发票类型', field: 'invoiceType', sortable: true},
        {title: '下单时间', field: 'addTimeStart', sortable: true},
//        {title: '同步到商城时间', field: 'syncShopTime', sortable: true},
        {title: '首次确认时间', field: 'firstTureTime', sortable: true},
        {title: '确认时间', field: 'sureTime', sortable: true},
//        {title: '首次确认时间', field: 'firstConfirmTime', sortable: true,align: "center"},
//        {title: '会员等级', field: 'memberLevel', sortable: true},
        {title: '订单金额', field: 'orderAmount', sortable: true, align: 'right'},
//        {title: '订单类型', field: 'orderType', sortable: true},
        {
            title: '订单状态', field: 'orderStatus', sortable: true,
            formatter: function (val, rec) {
                if (val == "200" || val == "0") {
                    return "未确认";
                } else if (val == "204") {
                    return "缺货";
                } else if (val == "201") {
                    return "已确认";
                } else if (val == "203") {
                    return "已完成";
                } else if (val == "202") {
                    return "已取消";
                } else if (val == "205") {
                    return "配送中";
                }
            }
        },
        {title: '订单操作', field: 'operation', sortable: true, width: 130},
        {title: '关联订单号', field: 'oid', sortable: true},
        {title: '物流编号', field: 'expressNo', sortable: true},
        {
            title: '订单取消状态', field: 'cancelStatus', sortable: true,
//        	101申请中，102已确认，103拒绝，104退货完成
            formatter: function (val, rec) {
                if (val == "206") {
                    return "订单取消申请中";
                } else if (val == "207") {
                    return "订单取消成功";
                } else if (val == "208") {
                    return "订单取消失败";
                }
            }
        },
        {title: '订单取消操作', field: 'cancelOperation', sortable: true, width: 130}  //订单状态的更新更网单状态的更新在业务上有些关联，暂不确定，故先注释掉
//        {title: '支付状态', field: 'payStatus', sortable: true},
//        {title: '已确认次数', field: 'haveConfirmTime', sortable: true}
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
    $('#dlg').dialog('close');
    $('#logistics_dlg').dialog('close');
    var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);

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
            $.messager.alert('提示', '新增成功');
        } else {
            $.messager.alert('提示', '编辑成功');
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
            $.messager.alert('提示', '请选择一条数据');
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
            $.messager.alert('提示', '请选择一条数据');
        }
    });
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
    });

    $("#source").combobox({
        prompt: '请输入订单来源',
        url: '/pop/getSource',
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

    $("#orderStatus").combobox({
        prompt: '请输入订单状态',
        url: '/pop/getOrderStatus',
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

    // $("#orderPayStatus").combobox({
    //     prompt: '请输入支付状态',
    //     url: '/pop/getOrderPayStatus',
    //     valueField: "value",
    //     textField: "text",
    //     required: false,
    //     editable: true,
    //     hasDownArrow: true,
    //     filter: function (q, row) {
    //         var opts = $(this).combobox('options');
    //         return row[opts.textField].indexOf(q) >= 0;
    //     },
    //     onSelect: function (record) {
    //     },
    //     onUnselect: function () {
    //     }
    // });

    $("#invoiceType").combobox({
        prompt: '输入关键字自动检索',
        url: '/pop/getInvoiceType',
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

function SearchUnit() {
    var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);
    var new_code = $("#code").val("yes");
    var p = datagrid.datagrid("getPager").data("pagination").options;
    var orderNoData = $("#orderNo").textbox("getValue");
    var sourceOrderNoData = $("#sourceOrderNo").textbox("getValue");
    var sourceData = $("#source").combobox("getValue");
    var orderStatusData = $("#orderStatus").combobox("getValue");
    // var orderPayStatusData = $("#orderPayStatus").combobox("getValue");
    //var base = $("#isCod").combobox("getValue");
    var oidData = $("#selectOid").textbox("getValue");
    var expressNoData = $("#selectExpressNo").textbox("getValue");
    var addTimeStartData = $("#addTimeStart").textbox("getValue");
    var addTimeEndData = $("#addTimeEnd").textbox("getValue");
    if (addTimeStartData > addTimeEndData) {
        alert("开始时间应该小于结束时间");
        return ;
    }
    $("#order_sn").val(orderNoData);
    $("#source_order_sn").val(sourceOrderNoData);
    $("#order_Status").val(orderStatusData);
    $("#source_e").val(sourceData);
    $("#o_id").val(oidData);
    $("#express_no").val(expressNoData);
    $("#addTime_start").val(addTimeStartData);
    $("#addTime_end").val(addTimeEndData);
    // var data = {
    //     orderSn: orderNoData,
    //     sourceOrderSn: sourceOrderNoData,
    //     orderStatus: orderStatusData,
    //     source: sourceData,
    //     oid: oidData,
    //     expressNo: expressNoData,
    //     // paymentStatus: orderPayStatusData,
    //     addTimeStart: addTimeStartData,
    //     addTimeEnd: addTimeEndData,
    //     page: p.pageNumber,
    //     rows: p.pageSize
    // }
    datagrid = $('#datagrid_orderForecastGoal').datagrid({
        url: "/pop/findDistributeOrderList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50,100,200],
        queryParams: {
            orderSn: orderNoData,
            sourceOrderSn: sourceOrderNoData,
            orderStatus: orderStatusData,
            source: sourceData,
            oid: oidData,
            expressNo: expressNoData,
            // paymentStatus: orderPayStatusData,
            addTimeStart: addTimeStartData,
            addTimeEnd: addTimeEndData
        },
        columns: [[
            {title: '订单号 ', field: 'orderSn', sortable: true},
//        {title: '货到付款', field: 'isCodDisplay', sortable: true},
//        {title: '预定', field: 'book', sortable: true},
            {title: '订单来源', field: 'source', sortable: true},
            {title: '来源订单号', field: 'sourceOrderSn', sortable: true},
//        {title: '发票类型', field: 'invoiceType', sortable: true},
            {title: '下单时间', field: 'addTimeStart', sortable: true},
//        {title: '同步到商城时间', field: 'syncShopTime', sortable: true},
            {title: '首次确认时间', field: 'firstTureTime', sortable: true},
            {title: '确认时间', field: 'sureTime', sortable: true},
//        {title: '首次确认时间', field: 'firstConfirmTime', sortable: true,align: "center"},
//        {title: '会员等级', field: 'memberLevel', sortable: true},
            {title: '订单金额', field: 'orderAmount', sortable: true, align: 'right'},
//        {title: '订单类型', field: 'orderType', sortable: true},
            {
                title: '订单状态', field: 'orderStatus', sortable: true,
                formatter: function (val, rec) {
                    if (val == "200" || val == "0") {
                        return "未确认";
                    } else if (val == "204") {
                        return "缺货";
                    } else if (val == "201") {
                        return "已确认";
                    } else if (val == "203") {
                        return "已完成";
                    } else if (val == "202") {
                        return "已取消";
                    } else if (val == "205") {
                        return "配送中";
                    }
                }
            },
            {title: '订单操作', field: 'operation', sortable: true, width: 130},
            {title: '关联订单号', field: 'oid', sortable: true},
            {title: '物流编号', field: 'expressNo', sortable: true},
            {
                title: '订单取消状态', field: 'cancelStatus', sortable: true,
//        	101申请中，102已确认，103拒绝，104退货完成
                formatter: function (val, rec) {
                    if (val == "206") {
                        return "订单取消申请中";
                    } else if (val == "207") {
                        return "订单取消成功";
                    } else if (val == "208") {
                        return "订单取消失败";
                    }
                }
            },
            {title: '订单取消操作', field: 'cancelOperation', sortable: true, width: 130}  //订单状态的更新更网单状态的更新在业务上有些关联，暂不确定，故先注释掉
//        {title: '支付状态', field: 'payStatus', sortable: true},
//        {title: '已确认次数', field: 'haveConfirmTime', sortable: true}
        ]],
    });
}

//取消
function cancel(id) {
    if (id.length > 11) {
        var price = id.slice(11);
        orderId = id.substring(0, 8)
        thisStatus = id.substring(8, 11);
        var params = {
            but: "cancel",
            orderId: orderId,
            price: price,
            thisStatus: thisStatus
        };
    } else {
        orderId = id.substring(0, 8)
        thisStatus = id.substring(8, 11);
        var params = {
            but: "cancel",
            orderId: orderId,
            price: null,
            thisStatus: thisStatus
        };
    }
    console.log("orderId=" + orderId);
    $.messager.confirm('提示', '您确定取消吗?', function (b) {
        if (b) {
            $.get("/pop/orderOperation", params, function () {
                $("#searchBtn").click();
            });
        }
    });
}
//获取确认数据
var price
var orderId
var thisStatus;
function show(id) {
    $("#oId").val("");
    price = id.slice(11);
    orderId = id.substring(0, 8);
    thisStatus = id.substring(8, 11);
    console.info("PRICE=" + thisStatus + price);
    $("#dlg").dialog();
};
//保存关联订单号
$("#sure").click(function () {
    var oId = $("#oId").val();
    var but = "sure";
    var params = {
        orderId: orderId,
        but: but,
        oId: oId,
        price: price,
        thisStatus: thisStatus
    };
    if (oId == "" || oId == null) {
        $.messager.alert('提示', "关联订单号不能为空");
        return;
    }
    $.get("/pop/orderOperation", params, function (data) {
        if (data == "changed") {
            $('#dlg').dialog('close');
            $.messager.alert('提示', "该订单状态已改变，请重新操作！");
            $("#searchBtn").click();
            return;
        }
        if (data == "oidIsSame") {
            $.messager.alert('提示', "该关联订单号已存在，请重新输入！");
            $("#oId").val("");
            return;
        }

        if (data == "lock") {
            $('#dlg').dialog('close');
            $.messager.alert('提示', "金额额度已经临近锁定金额额度，不足以完成此操作！");
            $("#searchBtn").click();
            return;
        }
        if (data == "alert") {
            $('#dlg').dialog('close');
            $.messager.alert('提示', "金额额度已经达到报警金额额度！");
            $("#searchBtn").click();
            return;
        }
        if (data == null || data == "") {
            $('#dlg').dialog('close');
            $("#searchBtn").click();
            return;
        }
//			 mmoneyLimit(orderId)
    });
});
//完成
function finish(id) {
    orderId = id.substring(0, 8);
    thisStatus = id.substring(8, 11);
    var params = {
        orderId: orderId,
        but: "finish",
        thisStatus: thisStatus
    };
    $.get("/pop/orderOperation", params, function () {
        $("#searchBtn").click();
    });
}
//缺货
function stockout(id) {
    orderId = id.substring(0, 8);
    thisStatus = id.substring(8, 11);
    var params = {
        orderId: orderId,
        but: "stockout",
        thisStatus: thisStatus
    };
    $.get("/pop/orderOperation", params, function () {
        $("#searchBtn").click();
    });
}
//配送
var sendId
function send(id) {
    $("#expressNo").val("");
    $("#logistics_dlg").dialog();
    sendId = id.substring(0, 8);
    thisStatus = id.substring(8, 11);
}

//	logistics_sure
$("#logistics_sure").click(function () {
    var expressNo = $("#expressNo").val();
    if (expressNo == "" || expressNo == null) {
        $.messager.alert('提示', "物流编号不能为空！");
        return;
    }
    console.log(expressNo + "www");
    var params = {
        orderId: sendId,
        but: "send",
        expressNo: expressNo,
        thisStatus: thisStatus
    };
    $.get("/pop/orderOperation", params, function (data) {
        if (data == "isSame") {
            $("#expressNo").val("");
            $.messager.alert('提示', "该物流编号已经存在，请重新输入！");
            return;
        }
        if (null == data || "" == data) {
            $('#logistics_dlg').dialog('close');
            $("#searchBtn").click();
        }
    });
})
function mmoneyLimit(orderId) {
    $.post("/pop/moneyLimit", {orderId: orderId}, function (data) {
        console.log(data);
        if (data == "lock") {
            $.messager.alert('提示', "金额已达锁定");
        }
        if (data == "alert") {
            $.messager.alert('提示', "金额已达报警");
        }
    });
};
$("#importBtn_orderList").click(function () {
        $("#exportForm").attr("action","/pop/exportOrderList");
        $("#exportForm").submit();
})

