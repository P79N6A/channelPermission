var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
//    fitColumns: true,//fitColumns: true,
    singleSelect: true,//多选
    url: '',
    // frozenColumns: [[{
    //     field: 'id', checkbox: true
    // }]],
    columns: [[
        {title: '网单编号 ', field: 'cOrderSn', sortable: false},
        {title: '订单编号 ', field: 'orderSn', sortable: false},
        {title: '商品名称', field: 'productName', sortable: false},
        {title: '商品编号', field: 'sku', sortable: false},
        {title: '商品分类', field: 'typeName', sortable: false},
        {title: '价格', field: 'price', sortable: false, align: 'right'},
//      {title: '配送费', field: 'secName1', sortable: false},
        {title: '数量', field: 'number', sortable: false, align: 'right'},
        {title: '网单金额', field: 'pAmount', sortable: false, align: 'right'},
        {title: '订单来源', field: 'source', sortable: false},
        {title: '来源订单号', field: 'sourceOrderSn', sortable: false},
        {title: '付款时间', field: 'cPayTime', sortable: false},
        {
            title: '订单状态', field: 'orderStatus', sortable: false,
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
        {title: '收货人', field: 'consignee', sortable: false},
        {title: '联系电话', field: 'mobile', sortable: false},
        {title: '所在地', field: 'originRegionName', sortable: false},
        {title: '详细信息', field: 'originAddress', sortable: false},
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
    $('#datagrid_orderForecastGoal').datagrid({
        showFooter: true
    });

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

    $("#source").combobox({
        prompt: '输入关键字自动检索',
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

    // $("#orderStatus").combobox({
    // 	prompt: '输入关键字自动检索',
    // 	url: '/pop/getOrderStatus',
    // 	valueField: "value",
    // 	textField: "text",
    // 	required: false,
    // 	editable: true,
    // 	hasDownArrow: true,
    // 	filter: function (q, row) {
    // 		var opts = $(this).combobox('options');
    // 		return row[opts.textField].indexOf(q) >= 0;
    // 	},
    // 	onSelect: function (record) {
    // 	},
    // 	onUnselect: function () {
    // 	}
    // });

    $("#orderPayStatus").combobox({
        prompt: '输入关键字自动检索',
        url: '/pop/getOrderPayStatus',
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
// /pop/findOrderProductList
$("#searchBtn").on('click', function (event) {
    // var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);
    //
    // $("#searchBtn").attr("disabled", "disabled");
    // var p = datagrid.datagrid("getPager").data("pagination").options;
    var cOrderNoData = $("#cOrderNo").textbox("getValue");//网单号
    var orderNoData = $("#orderNo").textbox("getValue");//订单号
    var sourceOrderNoData = $("#sourceOrderNo").textbox("getValue");//来源订单号
    var itemNameData = $("#itemName").textbox("getValue");//商品名称
    var itemSkuData = $("#itemSku").textbox("getValue");//商品SKU
    var payTimeData = $("#payTime").textbox("getValue");//付款时间
    var payTimeEndData = $("#payTimeEnd").textbox("getValue");//付款时间
    if (payTimeData > payTimeEndData) {
        alert("开始时间应该小于结束时间");
        return ;
    }
    var orderStatusData = $("#orderStatus").combobox("getValue");
    $("#code").val("yes");
    $("#c_order_sn").val(cOrderNoData);
    $("#order_sn").val(orderNoData);
    $("#source_order_sn").val(sourceOrderNoData);
    $("#product_name").val(itemNameData);
    $("#sku_p").val(itemSkuData);
    $("#c_pay_time").val(payTimeData);
    $("#pay_time_end").val(payTimeEndData);
    $("#order_status").val(orderStatusData);
    // var data = {
    //     cOrderSn: cOrderNoData,
    //     orderSn: orderNoData,
    //     sourceOrderSn: sourceOrderNoData,
    //     productName: itemNameData,
    //     sku: itemSkuData,
    //     cPayTime: payTimeData,
    //     payTimeEnd: payTimeEndData,
    //     orderStatus: orderStatusData,
    //     page: p.pageNumber,
    //     rows: p.pageSize
    // }
    datagrid = $('#datagrid_orderForecastGoal').datagrid({
        url: "/pop/findOrderProductList",
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
            cOrderSn: cOrderNoData,
            orderSn: orderNoData,
            sourceOrderSn: sourceOrderNoData,
            productName: itemNameData,
            sku: itemSkuData,
            cPayTime: payTimeData,
            payTimeEnd: payTimeEndData,
            orderStatus: orderStatusData
        },
        // frozenColumns: [[{
        //     field: 'id', checkbox: true
        // }]],
        columns: [[
            {title: '网单编号 ', field: 'cOrderSn', sortable: false},
            {title: '订单编号 ', field: 'orderSn', sortable: false},
            {title: '商品名称', field: 'productName', sortable: false},
            {title: '商品编号', field: 'sku', sortable: false},
            {title: '商品分类', field: 'typeName', sortable: false},
            {title: '价格', field: 'price', sortable: false, align: 'right'},
//      {title: '配送费', field: 'secName1', sortable: false},
            {title: '数量', field: 'number', sortable: false, align: 'right'},
            {title: '网单金额', field: 'pAmount', sortable: false, align: 'right'},
            {title: '订单来源', field: 'source', sortable: false},
            {title: '来源订单号', field: 'sourceOrderSn', sortable: false},
            {title: '付款时间', field: 'cPayTime', sortable: false},
            {
                title: '订单状态', field: 'orderStatus', sortable: false,
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
            {title: '收货人', field: 'consignee', sortable: false},
            {title: '联系电话', field: 'mobile', sortable: false},
            {title: '所在地', field: 'originRegionName', sortable: false},
            {title: '详细信息', field: 'originAddress', sortable: false},
        ]],
    });

})

$("#importBtn_productList").click(function () {
	$("#exportForm").attr("action","/pop/exportProductList");
    $("#exportForm").submit();
});