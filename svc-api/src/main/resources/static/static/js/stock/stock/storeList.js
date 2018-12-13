var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
    singleSelect: true,//多选
    url: '',
    columns: [[
        {title: '店铺码 ', field: 'storeCode', sortable: false},
        {title: '店铺名称 ', field: 'storeName', sortable: false},
        {title: '物料编号', field: 'storeSku', sortable: false},
        {title: '品类', field: 'cbsCategory', sortable: false},
        {title: '产品型号', field: 'productName', sortable: false},
        {title: '总库存量', field: 'stockqty', sortable: false},
        {title: 'EC库存更新时间', field: 'storeTs', sortable: false, formatter: formatDatebox},
        {
            title: '创建时间', field: 'updateTime', sortable: false, formatter: formatDatebox
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
    var datagrid = $('#storeTable').datagrid(datagridOptions_orderForecastGoal);
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
	 
    var storeCodeData = $("#storeCode").textbox("getValue");
    var skuData = $("#sku").textbox("getValue");
    var stockQtyData = $("#stockQty").textbox("getValue");
    var productTypeNameData = $("#product_type_name").textbox("getValue");
    var productNameData = $("#productName").textbox("getValue");
    var reg = /^\d+$/;
    
    if (isNaN(stockQtyData)) {
    	 
        $.messager.alert('提示', '可用库存请输入数字');
        return;
    }
    
    if (reg.test(stockQtyData)!= true && stockQtyData != null && stockQtyData != "") {
        $.messager.alert('提示', '可用库存请输入非负整数');
        return;
    }

    $("#code").val("yes");
    $("#storeCodeData").val(storeCodeData);
    $("#skuData").val(skuData);
    $("#stockQtyData").val(stockQtyData);
    $("#productTypeNameData").val(productTypeNameData);
    $("#productNameData").val(productNameData);

    datagrid = $('#storeTable').datagrid({
        url: "/stock/findStoreList",
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
            storeCode: storeCodeData,
            storeSku: skuData,
            stockqty: stockQtyData,
            productTypeName: productTypeNameData,
            productName: productNameData
        },
        columns: [[
            {title: '店铺码 ', field: 'storeCode', sortable: false},
            {title: '店铺名称 ', field: 'storeName', sortable: false},
            {title: '物料编号', field: 'storeSku', sortable: false},
            {title: '品类', field: 'cbsCategory', sortable: false},
            {title: '产品型号', field: 'productName', sortable: false},
            {title: '总库存量', field: 'stockqty', sortable: false},
            {title: 'EC库存更新时间', field: 'storeTs', sortable: false, formatter: formatDatebox},
            {
                title: '创建时间', field: 'updateTime', sortable: false, formatter: formatDatebox
            },
        ]],
    });
});

$("#importBtn_productList").click(function () {
    $("#exportForm").attr("action", "/stock/exportStoreList");
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