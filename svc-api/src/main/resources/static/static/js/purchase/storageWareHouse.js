var datagrid;
var queryParameters;
var LEVEL1_QJ={};
$(function () {
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[{
            field: 'whCode',
            title: '库位编码',
            width:90,
            align: 'center'
        },
            {
                field: 'whName',
                title: '库位名称',
                width:120,
                align: 'center'
            },
            {
                field: 'status',
                title: '状态',
                width:120,
                align: 'center',
                formatter: function (value) {
                    if (value == '0') {
                        return "停用";
                    }else{
                        return "在用";
                    }
                }
            },
            {
                field: 'centerCode',
                title: '网单中心代码',
                width:120,
                align: 'center'
            },
            {
                field: 'supportCod',
                title: '是否支持货到付款',
                width:80,
                align: 'center',
                formatter: function (value) {
                    if (value == '0') {
                        return "否";
                    } else{
                        return "是";
                    }
                }
            },
            {
                field: 'supportedDeliveryMode',
                title: '该TC支持的物流模式',
                width:300,
                align: 'center'
            },
            {
                field: 'lesFiveYard',
                title: '送达方代码',
                width:300,
                align: 'center'
            },
            {
                field: 'lesWhCode',
                title: 'LES库位编码',
                width:300,
                align: 'center'
            },
            {
                field: 'updateTime',
                title: '最后更新时间',
                width:300,
                align: 'center',
                formatter: function (value) {
                    return new Date(value).format('yyyy-MM-dd hh:mm:ss')
                }
            }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);

});

//点击查询
$('#searchBtn').click(function () {

    var queryParameter = {};
    var param = $("#paramForm").serializeArray();
    for (var o in param) {
        queryParameter[param[o].name] = param[o].value;
    }
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/storageLocMgtModel/queryPage",
        method: "get",
        fit: true,
        fitColumns: false,
        pagination: true,
        pageSize: 100,
        pageList: [100, 200, 300],
        pageNumber: 1,
        autoRowHeight: true,
        striped: true,
        toolbar: '#datagridToolbar',
        rownumbers: true,
        nowrap: false,
        queryParams: queryParameter,
        loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        columns: [
            [
                {
                    field: 'whCode',
                    title: '库位编码',
                    width:90,
                    align: 'center'
                },
                {
                    field: 'whName',
                    title: '库位名称',
                    width:120,
                    align: 'center'
                },
                {
                    field: 'status',
                    title: '状态',
                    width:120,
                    align: 'center',
                    formatter: function (value) {
                        if (value == '0') {
                            return "停用";
                        }else{
                            return "在用";
                        }
                    }
                },
                {
                    field: 'centerCode',
                    title: '网单中心代码',
                    width:120,
                    align: 'center'
                },
                {
                    field: 'supportCod',
                    title: '是否支持货到付款',
                    width:80,
                    align: 'center',
                    formatter: function (value) {
                        if (value == '0') {
                            return "否";
                        } else{
                            return "是";
                        }
                    }
                },
                {
                    field: 'supportedDeliveryMode',
                    title: '该TC支持的物流模式',
                    width:300,
                    align: 'center'
                },
                {
                    field: 'lesFiveYard',
                    title: '送达方代码',
                    width:300,
                    align: 'center'
                },
                {
                    field: 'lesWhCode',
                    title: 'LES库位编码',
                    width:300,
                    align: 'center'
                },
                {
                    field: 'updateTime',
                    title: '最后更新时间',
                    width:300,
                    align: 'center',
                    formatter: function (value) {
                        return new Date(value).format('yyyy-MM-dd hh:mm:ss')
                    }
                }
            ]
        ]
    });
});


function exp() {
    if(window.total && window.total > 20000){
        $.messager.alert('提示', '最多导出20000条记录，请缩小查询范围！', 'info');
        return;
    }
    var data = $('#datagrid').datagrid('getData');
    if (data.rows.length <= 0) {
        $.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
        return;
    }
    var temp = $("#paramForm").serializeArray();
    var param = {}
    for (var o in temp) {
        param[temp[o].name] = temp[o].value;
    }
    window.location.href = "/storageLocMgtModel/export?" +
        'whCode=' + param.whCode
        + '&whName=' + param.whName
}
