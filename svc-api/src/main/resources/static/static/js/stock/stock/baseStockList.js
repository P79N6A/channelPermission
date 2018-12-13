var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
    singleSelect: true,//多选
    url: '',
    columns: [[
        {title: '物料编号 ', field: 'stockSku', sortable: false},
        {title: '品类 ', field: 'cbsCategory', sortable: false},
        {title: '产品型号', field: 'productName', sortable: false},
        {title: '库位编码', field: 'secCode', sortable: false},
        {title: '库位名称', field: 'secName', sortable: false},
        {title: '实际库存', field: 'stockStockQty', sortable: false},
        {title: '占用库存', field: 'stockFrozenQty', sortable: false},
        {title: '商品属性', field: 'stockItemProperty', sortable: false},
        {title: '创建时间', field: 'createTime', sortable: false},
        {title: '更新时间', field: 'updateTime', sortable: false},

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
    var datagrid = $('#invBaseStockTable').datagrid(datagridOptions_orderForecastGoal);

    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
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

    var secCodeData = $("#secCode").textbox("getValue");//库位编码
    var skuData = $("#sku").textbox("getValue");//物料编码
    var stockQtyData = $("#stockQty").textbox("getValue");//实际库存
    var avaiableQtyData = $("#avaiableQty").textbox("getValue");//可用库存
    var itemPropertyData = $("#itemProperty").textbox("getValue");//商品属性
    var product_type_nameData = $("#product_type_name").textbox("getValue");//品类
    var productNameData = $("#productName").textbox("getValue");//产品型号

    var stockQtyCode = $("#stockQtyCode").textbox("getValue");//比较大小的标识
    var avaiableQtyCode = $("#avaiableQtyCode").textbox("getValue");//

    if (isNaN(stockQtyData)) {
        $.messager.alert('提示', '实际库存请输入数字');
        return;
    }
    if (isNaN(avaiableQtyData)) {
        $.messager.alert('提示', '可用库存请输入数字');
        return;
    }

    $("#code").val("yes");
    $("#secCodeData").val(secCodeData);
    $("#skuData").val(skuData);
    $("#stockQtyData").val(stockQtyData);
    $("#avaiableQtyData").val(avaiableQtyData);
    $("#itemPropertyData").val(itemPropertyData);
    $("#productTypeNameData").val(product_type_nameData);
    $("#productNameData").val(productNameData);
    $("#stockQtyCodeData").val(stockQtyCode);
    $("#avaiableQtyCodeData").val(avaiableQtyCode);

    datagrid = $('#invBaseStockTable').datagrid({
        url: "/stock/findBaseStockList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        queryParams: {
            secCode: secCodeData,
            stockSku: skuData,
            stockStockQty: stockQtyData,
            avaiableQty: avaiableQtyData,
            stockItemProperty: itemPropertyData,
            productTypeName: product_type_nameData,
            productName: productNameData,
            stockQtyCode: stockQtyCode,
            avaiableQtyCode: avaiableQtyCode

        },
        columns: [[
            {title: '物料编号 ', field: 'stockSku', sortable: false},
            {title: '品类 ', field: 'cbsCategory', sortable: false},
            {title: '产品型号', field: 'productName', sortable: false},
            {title: '库位编码', field: 'secCode', sortable: false},
            {title: '库位名称', field: 'secName', sortable: false},
            {title: '实际库存', field: 'stockStockQty', sortable: false},
            {title: '占用库存', field: 'stockFrozenQty', sortable: false},
            {title: '商品属性', field: 'stockItemProperty', sortable: false, formatter: changeCode},
            {
                title: '创建时间', field: 'createTime', sortable: false, formatter: formatDatebox
            },
            {
                title: '更新时间', field: 'updateTime', sortable: false, formatter: formatDatebox
            },
        ]],
    });
});

//查询套机
$("#searchMachineBtn").on('click', function (event) {

    var secCodeData = $("#secCode").textbox("getValue");//库位编码
    var skuData = $("#sku").textbox("getValue");//物料编码
    var stockQtyData = $("#stockQty").textbox("getValue");//实际库存
    var avaiableQtyData = $("#avaiableQty").textbox("getValue");//可用库存
    var itemPropertyData = $("#itemProperty").textbox("getValue");//商品属性
    var product_type_nameData = $("#product_type_name").textbox("getValue");//品类
    var productNameData = $("#productName").textbox("getValue");//产品型号

    var stockQtyCode = $("#stockQtyCode").textbox("getValue");//比较大小的标识
    var avaiableQtyCode = $("#avaiableQtyCode").textbox("getValue");//

    if (isNaN(stockQtyData)) {
        $.messager.alert('提示', '实际库存请输入数字');
        return;
    }
    if (isNaN(avaiableQtyData)) {
        $.messager.alert('提示', '可用库存请输入数字');
        return;
    }

    $("#code").val("mYes");
    $("#secCodeData").val(secCodeData);
    $("#skuData").val(skuData);
    $("#stockQtyData").val(stockQtyData);
    $("#avaiableQtyData").val(avaiableQtyData);
    $("#itemPropertyData").val(itemPropertyData);
    $("#productTypeNameData").val(product_type_nameData);
    $("#productNameData").val(productNameData);
    $("#stockQtyCodeData").val(stockQtyCode);
    $("#avaiableQtyCodeData").val(avaiableQtyCode);

    datagrid = $('#invBaseStockTable').datagrid({
        url: "/stock/findMachineBaseStockList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        queryParams: {
            secCode: secCodeData,
            stockSku: skuData,
            stockStockQty: stockQtyData,
            avaiableQty: avaiableQtyData,
            stockItemProperty: itemPropertyData,
            productTypeName: product_type_nameData,
            productName: productNameData,
            stockQtyCode: stockQtyCode,
            avaiableQtyCode: avaiableQtyCode

        },
        columns: [[
            {title: '物料编号 ', field: 'stockSku', sortable: false},
            {title: '品类 ', field: 'cbsCategory', sortable: false},
            {title: '产品型号', field: 'productName', sortable: false},
            {title: '库位编码', field: 'secCode', sortable: false},
            {title: '库位名称', field: 'secName', sortable: false},
            {title: '可用库存', field: 'avaiableQty', sortable: false},
            {title: '商品属性', field: 'stockItemProperty', sortable: false, formatter: changeCode},
            {
                title: '创建时间', field: 'createTime', sortable: false, formatter: formatDatebox
            },
            {
                title: '更新时间', field: 'updateTime', sortable: false, formatter: formatDatebox
            },
        ]],
    });

});

$("#importBtn_productList").click(function () {
    $("#exportForm").attr("action", "/stock/exportBaseStockList");
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

//商品属性编码转名称
function changeCode(value) {
    if (value == 10) {
        return "正品";
    }else if (value == 21) {
      return "不良品";
    } else if (value == 22) {
      return "开箱正品";
    } else if (value == 40) {
      return "样品";
    } else if (value == 41) {
      return "夺宝机";
    }
}