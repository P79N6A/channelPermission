$(function () {

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
    $("#ccategoryId").combobox({
        url: 'product/departmentproducttypeList',
        valueField: "id",
        textField: "typeName",
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
// /pop/findOrderCommissionInfoList
$("#searchBtn").on('click', function (event) {
    $("#searchBtn").attr("disabled", "disabled");
    var new_code = $("#e_code").val("yes");
    var p = $('#datagrid_orderForecastGoal').datagrid('getPager').data("pagination").options;
    // var cOrderNoData = $("#cOrderNo").textbox("getValue");//网单号
    var sureYearMonthData = $("#sureYearMonth").textbox("getValue");//确认月份
    var sourceData = $("#source").combobox("getValue");
    var productTypeData = $("#ccategoryId").combobox("getValue");
    var orderNoData = $("#orderNo").textbox("getValue");//订单号
    var sourceOrderNoData = $("#sourceOrderNo").textbox("getValue");//来源订单号
    var itemNameData = $("#itemName").textbox("getValue");//商品名称
    var itemSkuData = $("#itemSku").textbox("getValue");//商品SKU
    var firstSureTimeSData = $("#payTime").textbox("getValue");//付款时间
    var firstSureTimeEData = $("#payTimeEnd").textbox("getValue");//付款时间

    $("#sureYearMonth_e").val(sureYearMonthData);
    $("#source_e").val(sourceData);
    $("#ccategoryId_e").val(productTypeData);
    $("#orderNo_e").val(orderNoData);
    $("#sourceOrderNo_e").val(sourceOrderNoData);
    $("#itemName_e").val(itemNameData);
    $("#itemSku_e").val(itemSkuData);
    $("#payTime_e").val(firstSureTimeSData);
    $("#payTimeEnd_e").val(firstSureTimeEData);
    $("#code").val("yes");

    var data = {
        sureYearMonth: sureYearMonthData,
        // cOrderSn: cOrderNoData,
        orderSn: orderNoData,
        source: sourceData,
        sourceOrderSn: sourceOrderNoData,
        productName: itemNameData,
        sku: itemSkuData,
        productType: productTypeData,
        firstSureTimeS: firstSureTimeSData,
        firstSureTimeE: firstSureTimeEData,
        page: p.pageNumber,
        rows: p.pageSize
    }
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/findOrderCommissionInfoList',
        data: data,
        success: function (data, textStatus) {
            $('#datagrid_orderForecastGoal').datagrid('loadData', data);
        },
//        beforeSend: function () {
//            ajaxLoading();
//        }
    });

    //加载分页插件
    $('#datagrid_orderForecastGoal').datagrid('getPager').pagination({
        total: 0,
        pageSize: 50,
        pageList: [50, 100, 200],
        showRefresh: false,
        displayMsg: '显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNo, pageSize) {
            var start = (pageNo - 1) * pageSize;
            var end = start + pageSize;
            var p = $('#datagrid_orderForecastGoal').datagrid('getPager').data("pagination").options;
            var sureYearMonthData = $("#sureYearMonth").textbox("getValue");//确认月份
            var sourceData = $("#source").combobox("getValue");
            var productTypeData = $("#ccategoryId").combobox("getValue");
            var orderNoData = $("#orderNo").textbox("getValue");//订单号
            var sourceOrderNoData = $("#sourceOrderNo").textbox("getValue");//来源订单号
            var itemNameData = $("#itemName").textbox("getValue");//商品名称
            var itemSkuData = $("#itemSku").textbox("getValue");//商品SKU
            var firstSureTimeSData = $("#payTime").textbox("getValue");//付款时间
            var firstSureTimeEData = $("#payTimeEnd").textbox("getValue");//付款时间

            var data = {
                sureYearMonth: sureYearMonthData,
                // cOrderSn: cOrderNoData,
                orderSn: orderNoData,
                source: sourceData,
                sourceOrderSn: sourceOrderNoData,
                productName: itemNameData,
                sku: itemSkuData,
                productType: productTypeData,
                firstSureTimeS: firstSureTimeSData,
                firstSureTimeE: firstSureTimeEData,
                page: p.pageNumber,
                rows: p.pageSize
            }
            $.ajax({
                type: 'POST',
                async: true,
                url: '/pop/findOrderCommissionInfoList',
                data: data,
                success: function (data, textStatus) {

                    $('#datagrid_orderForecastGoal').datagrid('loadData', data);
                    var rowNumbers = $('.datagrid-cell-rownumber');
                    $(rowNumbers).each(function (index) {
                        var row = parseInt($(rowNumbers[index]).html()) + parseInt(start);
                        $(rowNumbers[index]).html("");
                        $(rowNumbers[index]).html(row);
                    });

                },
                error: function (e) {
                    $.messager.alert(e);
                }
            });
        }
    });

})

//加载表格
$('#datagrid_orderForecastGoal').datagrid({
    striped: true, // 隔行变色
    rownumbers: true,
    fit: true,
    pagination: true,
    singleSelect: true,
    fitColunms: true,
    idField: 'id',
    remoteSort: false,
    showFooter: true,
    frozenColumns: [[
        // {field: 'id', checkbox: true},
        {title: '订单号 ', field: 'orderSn', sortable: true},
        {title: '订单来源', field: 'source', sortable: true},
        {title: '商品名称', field: 'productName', sortable: true},
        {title: '商品编号', field: 'sku', sortable: true}
    ]],
    columns: [[
        // {title: '网单编号 ', field: 'cOrderSn', sortable: true},
        // {title: '来源订单号', field: 'sourceOrderSn', sortable: true},
        {title: '产品组', field: 'productType', sortable: true},
        {title: '商品分类', field: 'typeName', sortable: true},
        {title: '确认时间', field: 'firstConfirmTimeStr', sortable: true},
        {title: '价格', field: 'price', sortable: true, align: 'right'},
        {title: '数量', field: 'number', sortable: true, align: 'right'},
        {title: '订单金额', field: 'pAmount', sortable: true, align: 'right'},
        {title: '结算价格', field: 'supplyPrice', sortable: true},
        {title: '返点率(%)', field: 'monthPolicy', sortable: true},
        {title: '结算金额(元)', field: 'supplyAmount', sortable: true},
        {title: '佣金(元)', field: 'commission', sortable: true}
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    pageSize: 50,
    pageList: [50,100,200],
});

$(function () {

    $('#sureYearMonth').datebox({
        //显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
        onShowPanel: function () {
            //触发click事件弹出月份层
            span.trigger('click');
            if (!tds)
            //延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                setTimeout(function () {
                    tds = p.find('div.calendar-menu-month-inner td');
                    tds.click(function (e) {
                        //禁止冒泡执行easyui给月份绑定的事件
                        e.stopPropagation();
                        //得到年份
                        var year = /\d{4}/.exec(span.html())[0],
                            //月份
                            //之前是这样的month = parseInt($(this).attr('abbr'), 10) + 1;
                            month = parseInt($(this).attr('abbr'), 10);

                        //隐藏日期对象
                        $('#sureYearMonth').datebox('hidePanel')
                        //设置日期的值
                            .datebox('setValue', year + '-' + month);
                    });
                }, 0);
        },
        //配置parser，返回选择的日期
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        //配置formatter，只返回年月 之前是这样的d.getFullYear() + '-' +(d.getMonth());
        formatter: function (d) {
            var currentMonth = (d.getMonth() + 1);
            var currentMonthStr = currentMonth < 10 ? ('0' + currentMonth) : (currentMonth + '');
            return d.getFullYear() + '-' + currentMonthStr;
        }
    });

    //日期选择对象
    var p = $('#sureYearMonth').datebox('panel'),
        //日期选择对象中月份
        tds = false,
        //显示月份层的触发控件
        span = p.find('span.calendar-text');
    var curr_time = new Date();

    //设置前当月
    // $("#sureYearMonth").datebox("setValue", myformatter(curr_time));
});
//格式化日期
function myformatter(date) {
    //获取年份
    var y = date.getFullYear();
    //获取月份
    var m = date.getMonth() + 1;
    return y + '-' + m;
};
$("#importBtn_productList").click(function () {
    $("#exportForm").attr("action", "/pop/exportOrderCommissionList");
    $("#exportForm").submit();
});