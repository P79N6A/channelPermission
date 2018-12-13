var datagridOptions_invMachineSet = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    url: '',
    columns: [[
        {title: '主物料编码', field: 'mainSku', sortable: false, align: 'center'},
        {title: '子物料编码', field: 'subSku', sortable: false, align: 'center'},
        {title: '更新时间', field: 'updateTime', sortable: false, align: 'center'},
        {title: '创建时间', field: 'createTime', sortable: false, align: 'center'},
        {title: '套机启用状态', field: 'status', sortable: false, align: 'center'},
        {title: '支持子件销售状态', field: 'isSaleSub', sortable: false, align: 'center'},
        {title: 'BOM项目编号', field: 'posnr', sortable: false, align: 'center'},
        {title: '操作', field: 'operation', sortable: false, align: 'center'},

    ]],
    toolbar: '#datagridToolbar_invMachineSet',
    striped: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_invMachineSet').datagrid(datagridOptions_invMachineSet);

    $("#resetBtn_invMachineSet").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_invMachineSet').form('reset');
    });
});


//查询
$("#searchBtn_invMachineSet").on('click', function (event) {

    var mainSkuData = $("#mainSku").textbox("getValue");
    var subSkuData = $("#subSku").textbox("getValue");
    var statusData = $("#status").textbox("getValue");

    datagrid = $('#datagrid_invMachineSet').datagrid({
        url: "/stock/findMachineSetMgtList",
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
            mainSku: mainSkuData,
            subSku: subSkuData,
            status: statusData
        },
        columns: [[
            {title: '主物料编码', field: 'mainSku', sortable: false, align: 'center'},
            {title: '子物料编码', field: 'subSku', sortable: false, align: 'center'},
            {title: '更新时间', field: 'updateTime', sortable: false, align: 'center', formatter: formatDatebox},
            {title: '创建时间', field: 'createTime', sortable: false, align: 'center', formatter: formatDatebox},
            {
                title: '套机启用状态', field: 'status', sortable: false, align: 'center',
                formatter: function (val, rec) {
                    if (val == "0") {
                        return "启用";
                    } else {
                        return "未启用";
                    }
                }
            },
            {
                title: '支持子件销售状态', field: 'isSaleSub', sortable: false, align: 'center',
                formatter: function (val, rec) {
                    if (val == "0") {
                        return "启用";
                    } else {
                        return "未启用";
                    }
                }
            },
            {title: 'BOM项目编号', field: 'posnr', sortable: false, align: 'center'},
            {title: '操作', field: 'operation', sortable: false, align: 'center'},
        ]],
    });
});

function enableSubSku(ss) {
    var s = ss.split(",");
    var status = s[0];
    var sku = s[1];
// debugger;
    // console.log("LOG" + status + sku)
    $.messager.confirm('提示', '确定要做此操作吗?', function (b) {
        if (b) {
            jQuery.ajax({
                url: "/stock/enableSubSku",
                data: {
                    status: status,
                    sku: sku
                },
                type: "GET",
                success: function (data) {
                    if (data.message == "" || data.message == 'null' || data.message == null) {
                        alert("操作成功!");
                    } else {
                        alert(data.message);
                        return;
                    }
                    $("#searchBtn_invMachineSet").click();
                }
            });
        }
    });
};

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