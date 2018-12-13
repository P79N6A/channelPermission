
var datagrid;
var queryParameters;
$(function () {
    showPro();
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        striped: true,
        singleSelect: true,
        pagination: true,
        columns: [[
            // {title: "操作", field: "field0", sortable: false, formatter: go_detail},
            { title: "序号", field: "id", hidden: true },
            { title: "库位名称", field: "name", sortable: false },
            { title: "库位码", field: "code", sortable: false },
            { title: "货票同行", field: "isFreightInvoice", sortable: false },
            { title: "货到付款", field: "isSupportCod", sortable: false },
            { title: "库位类型", field: "type", sortable: false },
            { title: "所属中心", field: "centerCity", sortable: false },
            { title: "中心代码", field: "centerCode", sortable: false },
            { title: "所属区域", field: "area", sortable: false },
            { title: "添加时间", field: "addTime", sortable: false }
        ]],
        pageSize: 50,
        pageList: [50, 100, 200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);


});

//三级联动省市区
function showPro() {
    $.ajax({
        url: '/netpoint/getRegions',
        type:'POST',
        dataType: 'json',
        success: function (data) {
            var tempCombox = [{"code": '', "name": "please", "childs": [{}]}];
            $.each(data.rows, function (i, val) {
                if (val.regionType == 1) {
                    if (val.parentId == 0) {
                        tempCombox.push({"value": val.id, "text": val.regionName, "childs": [{}]});
                        $("#province").combobox("loadData", tempCombox);
                    }
                }
            });
        }
    });
}
$('#province').combobox({
    onChange: function (newValue, oldValue) {
        if (newValue) {
            showCity(newValue)
            $("#citys").combobox("clear")
            $("#county").combobox("clear")
        }
    }
});
function showCity(newValue) {
    var parentId=$('#province').combobox('getValue');
    $.ajax({
        url: '/netpoint/getRegions',
        type:'POST',
        dataType: 'json',
        data: {
            "parentId":parentId
        },
        success: function (data) {
            var tempCombox = [{"code": '', "name": "please", "childs": [{}]}];
            $.each(data.rows, function (i, val) {
                if (val.parentId == newValue) {
                    tempCombox.push({"value": val.id, "text": val.regionName, "childs": [{}]});
                    $("#citys").combobox("loadData", tempCombox);
                }
            });
        }
    });
}
$('#citys').combobox({
    onChange: function (newValue, oldValue) {
        if (newValue) {
            showCounty(String(newValue))
            $("#county").combobox("clear")
        }
    }
});
function showCounty(newValue) {
    var parentId=$('#citys').combobox('getValue');
    $.ajax({
        url: '/netpoint/getRegions',
        type:'POST',
        dataType: 'json',
        data: {
            "parentId":parentId
        },
        success: function (data) {
            var tempCombox = [{"code": '', "name": "please", "childs": [{}]}];
            $.each(data.rows, function (i, val) {
                if (val.parentId == newValue) {
                    tempCombox.push({"value": val.code, "text": val.regionName, "childs": [{}]});
                    $("#county").combobox("loadData", tempCombox);
                }
            });
        }
    });
}

$('#search').click(function () {

    //库位类型
    var storageType = $("#storageType").combobox("getValue");
    if (storageType == "-1") {
        storageType = "";
    }
    $('#storageType').combobox('setValue', storageType);
    //货票同行
    var isTogether = $("#isTogether").combobox("getValue");
    if (isTogether == "-1") {
        isTogether = "";
    }
    $('#isTogether').combobox('setValue', isTogether);
    //支持货到付款
    var isSupportCod = $("#isSupportCod").combobox("getValue");
    if (isSupportCod == "-1") {
        isSupportCod = "";
    }
    $('#isSupportCod').combobox('setValue', isSupportCod);


    //提交参数
    queryParameters = {
        id: $("#id").val(),
        storageName: $("#storageName").val().trim(),//库位名称
        storageCode: $("#storageCode").val().trim(),//库位码
        storageType: $("#storageType").combobox("getValue"),//库位类型
        isTogether: $("#isTogether").combobox("getValue"),//货票同行
        isSupportCod: $("#isSupportCod").combobox("getValue"),//支持货到付款
        rand: Math.random(),
    };

    datagrid = $('#datagrid').datagrid({
        url: "/storage/getStorageList.html",
        fit: true,
        fitColumns: false,
        pagination: true,
        singleSelect: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        nowrap: true,
        rownumbers: true,
        queryParams: queryParameters,
        columns: [
            [{
                field: 'id',
                title: '序号',
                align: 'center',
                hidden: true
            },
                {
                    title: '库位名称 ',
                    field: 'name',
                    align: 'center',
                },
                {
                    title: '库位码 ',
                    field: 'code',
                    align: 'center',
                }, {
                title: '货票同行 ',
                field: 'isFreightInvoice',
                color:'green',
                align: 'center',
                formatter: function (value, row, index) {
                    var freightInvoice = "";
                    if (row.isFreightInvoice == '1') {
                        freightInvoice = "是";
                    } else if (row.isFreightInvoice == '0') {
                        freightInvoice = '否';
                    } else {
                        freightInvoice = '';
                    }
                    return freightInvoice;
                }
            }, {
                title: '货到付款 ',
                field: 'isSupportCod',
                align: 'center',
                formatter: function (value, row, index) {
                    var supportCod = "";
                    if (row.isSupportCod == '0') {
                        supportCod = "否";
                    } else if (row.isSupportCod == '1') {
                        supportCod = '是';
                    }else {
                        supportCod = '';
                    }
                    return supportCod;
                }
            }, {
                title: '库位类型 ',
                field: 'type',
                align: 'center',
                formatter: function (value, row, index) {
                    var storageType = "";
                    if (row.type == '1') {
                        storageType = "大库";
                    } else if (row.type == '2') {
                        storageType = '小库';
                    } else {
                        storageType = '';
                    }
                    return storageType;
                }
            },
                {
                    field: 'cityName',
                    title: '所属中心',
                    sortable: false,
                    align: 'center'
                },
                {
                    title: '中心代码',
                    field: 'centerCode',
                    align: 'center'
                }, {
                title: '所属区域',
                field: 'area',
                align: 'center',
                formatter: function (value, row, index) {
                    var belongArea = "";
                    if (row.area == '1') {
                        belongArea = "东区";
                    } else if (row.area == '2') {
                        belongArea = '西区';
                    } else if (row.area == '3') {
                        belongArea = '南区';
                    } else if (row.area == '4') {
                        belongArea = '北区';
                    } else {
                        belongArea = '';
                    }
                    return belongArea;
                }
            },
                {
                    title: '添加时间',
                    field: 'addTime',
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value!='0' && value != null){
                            var date = new Date(value)//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                            Y = date.getFullYear() + '-';
                            M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'
                            D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' '
                            h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':'
                            m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':'
                            s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds())
                            return Y+M+D+h+m+s;
                        }else {
                            return "";
                        }

                    }
                }]
        ],
    });


});

//点击导出
$('#export').click(function () {

    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }

    $.messager.confirm('确认', '确定要导出吗？', function (r) {
        if (r) {
            $('#exportData').val(JSON.stringify(queryParameters));
            $('#paramForm_dmmtlPbcsMtlMeasure').attr("action", '/storage/exportStoragesList');
            $('#paramForm_dmmtlPbcsMtlMeasure').submit();
        }
    });
});
//跳转到添加页面
$("#add_Storage").on('click', function (event) {
    window.location.href="/storage/storagesAdd";
});

//删除
$("#delete_Storage").on('click', function (event) {
    event.preventDefault();
    var selected = $('#datagrid').datagrid('getSelected');
    if (selected !== null) {
        confirm('确定删除？', function (r) {
            if (r) {
                $.ajax({
                    url      : "/storage/deleteStorage",
                    type     : 'GET',
                    dataType : 'json',
                    data     : {"id":selected.id},
                    success  : function(data) {
                        if (data.flag == '1') {
                            alert('删除成功');
                            $('#datagrid').datagrid('reload');
                        } else {
                            alert('删除失败');
                        }
                    }
                });
            }
        })
    } else {
        alert('请选择一条数据');
    }
});