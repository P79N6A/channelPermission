
var datagrid;
var queryParameters;
$(function () {
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
            { title: "淘宝店铺", field: "shopName", sortable: false },
            { title: "订单号", field: "orderSn", sortable: false },
            { title: "来源订单号", field: "sourceOrderSn", sortable: false },
            { title: "LES是否已发货", field: "hasShipped", sortable: false },
            { title: "同步状态", field: "orderState", sortable: false },
            { title: "错误日志", field: "errorLog", sortable: false },
            { title: "最后同步时间", field: "syncTime", sortable: false },
            { title: "同步的次数", field: "syncCount", sortable: false },
            { title: "写入时间", field: "addTime", sortable: false },
            { title: "写入人", field: "creator", sortable: false },
            { title: "交易类型", field: "type", sortable: false },
            { title: "状态", field: "orderStatus", sortable: false },
        ]],
        pageSize: 50,
        pageList: [50, 100, 200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);


});
//日期控件
var buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
buttons.splice(1, 0, {
    text: '清除',
    handler: function (target) {
        $(target).datetimebox('setValue', '');
        $(target).combo("hidePanel");
    }
});
$('#addTimeMin').datetimebox({ buttons: buttons });
$('#addTimeMax').datetimebox({ buttons: buttons });

var searchselect = function () {
        //写入时间
        if ($('#addTimeMin').datetimebox('getValue') && $('#addTimeMax').datetimebox('getValue')) {
            if ($('#addTimeMin').datetimebox('getValue') > $('#addTimeMax').datetimebox('getValue')) {
                $.messager.alert('错误', '时间区间不正确,请重新选择', 'error');
                return false;
            }
        }

        //店铺
        var taoBaoShop = $("#taoBaoShop").combobox("getValue");
        if (taoBaoShop == "-1") {
            taoBaoShop = "";
        }
        $('#taoBaoShop').combobox('setValue', taoBaoShop);


        //提交参数
        queryParameters = {
            id: $("#id").val(),
            taoBaoShop: $("#taoBaoShop").combobox("getValue"),//淘宝店铺,
            orderState: $("#orderState").combobox("getValue"),//淘宝店铺,
            sourceOrderSn: $("#sourceOrderSn").val().trim(),//来源订单号
            addTimeMin:  $('#addTimeMin').datetimebox('getValue' ),//写入时间
            addTimeMax: $('#addTimeMax').datetimebox('getValue' ),
        };

        datagrid = $('#datagrid').datagrid({
            url: "/order/getExternalOrderList.html",
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
                        title: '淘宝店铺 ',
                        field: 'shopName',
                        align: 'center',
                    },
                    {
                        title: '订单号 ',
                        field: 'orderSn',
                        align: 'center',
                    }, {
                    title: '来源订单号 ',
                    field: 'sourceOrderSn',
                    align: 'center'
                }, {
                    title: 'LES是否已发货 ',
                    field: 'hasShipped',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var isShipped = "";
                        if (row.hasShipped == 0) {
                            isShipped = "否";
                        } else if (row.hasShipped == 1) {
                            isShipped = '是';
                        }else {
                            isShipped = "";
                        }
                        return isShipped;
                    }
                }, {
                    title: '同步状态 ',
                    field: 'orderState',
                    align: 'center'
                }, {
                    title: '错误日志 ',
                    field: 'errorLog',
                    align: 'center'
                }, {
                    title: '最后同步时间 ',
                    field: 'syncTimeStr',
                    align: 'center'
                }, {
                    title: '同步的次数 ',
                    field: 'syncCount',
                    align: 'center'
                }, {
                    title: '写入时间 ',
                    field: 'addTime',
                    align: 'center'
                } ,{
                    title: '写入人 ',
                    field: 'creator',
                    align: 'center'
                },{
                    title: '交易类型 ',
                    field: 'type',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var typeStr = "";
                        if (row.type=="fixed") {
                            typeStr = "一口价";
                        } else if (row.type =="fx") {
                            typeStr = '分销';
                        }else if (row.type =="step") {
                            typeStr = '万人团';
                        }else {
                            typeStr = "";
                        }
                        return typeStr;
                    }
                },{
                    title: '状态',
                    field: 'orderStatus',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var orderS = "";
                        if (row.orderStatus == 1) {
                            orderS = "待开票";
                        } else if (row.orderStatus == 2) {
                            orderS = '已开票';
                        }else {
                            orderS = "";
                        }
                        return orderS;
                    }
                }]
            ],
        });



}
$('#search').click(function () {
    searchselect();
});



$("#add_Externalorders").on('click', function (event) {
    window.location.href="/order/externalOrdersAdd.html";
});

$("#update_Externalorders").on('click', function (event) {
    var row = $("#datagrid").datagrid('getSelected');
    $('#update_externalOrders_info_form').form('reset');
    if (row !== null) {
        $('#update_externalOrders_info').dialog({'title': '修改'});
        $('#update_externalOrders_info').dialog('open');
        $("#up_sourceOrderSn").val(row.sourceOrderSn);
        $("#up_errorLog").val(row.errorLog);
        $('#up_orderState').combobox('setValue', row.orderState);


    }else {
        $.messager.alert('警告', '请选择一条数据！', 'warning');
        return;
    }

});

$("#externalOrders_info_update").on('click', function (event) {
    //价格
    var baseInfo=$('#update_externalOrders_info_form').serializeObject();//基本信息
    var up_orderState = baseInfo.up_orderState;
    if(up_orderState ==""||up_orderState ==null){
        alert("同步状态不能为空,请选择!");
        return false;
    }
    var up_sourceOrderSn = baseInfo.up_sourceOrderSn;
    var up_errorLog = baseInfo.up_errorLog;

    $.ajax({
        url      : "/order/updateExternalOrdersInfo.html",
        type     : 'POST',
        dataType : 'json',
        async : false,
        data     : {
            sourceOrderSn: up_sourceOrderSn,
            orderState:up_orderState,
            errorLog: up_errorLog
        },
        success  : function(data) {
            $('#update_externalOrders_info').dialog('close');

            if (data.flag == 1) {
                $.messager.alert('提示','修改成功！','info');
                searchselect();
            } else if (data.flag == 2) {
                $.messager.alert('提示','修改失败！','info');
                return false;
            }else {
                $.messager.alert('提示','操作异常,添加失败！','info');
                return false;
            }
        }
    });
});