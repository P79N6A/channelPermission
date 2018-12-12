var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
    singleSelect: true,//多选
    url: '',
    columns: [[
        {title: '城市 ', field: 'cityName', sortable: true},
        {title: '区县 ', field: 'regionName', sortable: true},
        {title: '库存类型', field: 'stockType', sortable: true},
        {title: '库位/店铺', field: 'storeCode', sortable: true},
        {title: '物料编码', field: 'sku', sortable: true},
        {title: '存量', field: 'avaibleQty', sortable: true},
        {title: '更新时间', field: 'updateTime', sortable: true, formatter: formatDatebox},
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
    var datagrid = $('#stockAreaTable').datagrid(datagridOptions_orderForecastGoal);
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
    });

    $("#province_code").combobox({
        prompt: '--请选择--',
        url: '/stock/getAllProvince',
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
    $("#channel_code").combobox({
        prompt: '--请选择--',
        url: '/stock/getChannels',
        textField: "text",
        valueField: "value",
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
    if (!validate()) {
        $.messager.alert("提示", '区县、渠道、物料编码都不能为空');
        return;
    }
    var skuData = $("#sku").val();
    var provinceCodeData = $("#province_code").combobox("getValue");
    var cityCodeData = $("#city_code").val();
    var regionCodeData = $("#regionCode").val();
    var channelCodeData = $("#channel_code").combobox("getValue");

    datagrid = $('#stockAreaTable').datagrid({
        url: "/stock/getStockAreaRowList",
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
            provinceCode: provinceCodeData,
            sku: skuData,
            cityCode: cityCodeData,
            regionCode: regionCodeData,
            channelCode: channelCodeData
        },
        columns: [[
            {title: '城市 ', field: 'cityName', sortable: true},
            {title: '区县 ', field: 'regionName', sortable: true},
            {title: '库存类型', field: 'stockType', sortable: true},
            {title: '库位/店铺', field: 'storeCode', sortable: true},
            {title: '物料编码', field: 'sku', sortable: true},
            {title: '存量', field: 'avaibleQty', sortable: true},
            {title: '更新时间', field: 'updateTime', sortable: true, formatter: formatDatebox},
        ]],
    });
});
$("#province_code").combobox({
    onChange: function (n, o) {
        var key = $("#province_code").combobox("getValue");
        // console.log("ID+" + key);
        $("#city_code").html("<option value=\"0\">--请选择--</option>");
        $("#regionCode").html("<option value=\"0\">--请选择--</option>");
        $.post("/stock/getAllCityByProvId", {provinceCode: key}, function (data) {
            // console.log("result=" + data);
            for (var key in data) {
                $("#city_code").append(
                    "<option value=" + key + ">" + data[key]
                    + "</option>");
            }
        });
    }
});

function getAllRegionByCityId(key) {
    $("#regionCode").html("<option value=\"0\">--请选择--</option>");
    $.post("/stock/getAllRegionByCityId", {city_code: key}, function (data) {
        // console.log("result=" + data);
        for (var key in data) {
            $("#regionCode").append(
                "<option value=" + key + ">" + data[key]
                + "</option>");
        }
    });
}
function validate() {
    var sku = $("#sku").val();
    var province = $("#province_code").combobox("getValue");
    var city = $("#city_code").val();
    var regionCode = $("#regionCode").val();
    var channel = $("#channel_code").combobox("getValue");
    // alert(sku+","+province+","+city+","+regionCode+","+channel);
    if (sku != '' && province != null && city != 0 && regionCode != 0 && channel != null) {
        return true;
    }
}

// $("#importBtn_productList").click(function () {
//     $("#exportForm").attr("action", "/stock/exportStoreList");
//     $("#exportForm").submit();
// });
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