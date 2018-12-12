/**
 * Created by LuJun on 2017/11/6.
 */
var queryParameters;
var datagrid = null;
$(function () {
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        striped: true,
        singleSelect: true,
        pagination: true,
        columns: [[
            // {title: "操作", field: "field0", sortable: true, formatter: go_detail},
            {title: "订单来源", field: "source", sortable: true},
            {title: "外部订单号", field: "sourceOrderSn", sortable: true},
            {title: "LBX单号", field: "lbxNo", sortable: true},
            {title: "网单号", field: "cOrderSn", sortable: true},
            {title: "宝贝信息", field: "productInfos", sortable: true},
            {title: "型号", field: "productType", sortable: true},
            {title: "数量", field: "number", sortable: true},
            {title: "物料编码", field: "sku", sortable: true},
            {title: "商品类型名称", field: "productName", sortable: true},
            {title: "旺旺ID", field: "nicker", sortable: true},
            {title: "收货人", field: "consignee", sortable: true},
            {title: "电话", field: "phone", sortable: true},
            {title: "手机", field: "mobile", sortable: true},
            {title: "所属区域", field: "region", sortable: true},
            {title: "所属库位", field: "sCode", sortable: true},
            {title: "下单时间", field: "createTime", sortable: true},
            {title: "付款时间", field: "payTime", sortable: true},
            {title: "评价创建时间", field: "commentCreateTime", sortable: true},
            {title: "是否评分", field: "isGiveMark", sortable: true},
            {title: "评价结果", field: "markResult", sortable: true},
            {title: "评价信息", field: "commentInfos", sortable: true, width: 150, formatter: be_pointer}]],
        pageSize: 20,
        pageList: [10, 20, 30, 40, 50],
        rownumbers: true
    }

    function be_pointer(val, row) {

        return '<a style="color: black;" href="#" title="' + val + '">' + val + '</a>';
    };
    function go_detail() {
        return '<a href="acceptBadReview.html">详情</a>'
    }

    $("#resetBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_dmmtlPbcsMtlMeasure').form('reset');
    });

    $('#datagrid2').datagrid(datagrid);
});


//增加查询参数，在页面加载时运行
function reloadgrid() {
    //查询参数直接添加在queryParams中
    var source = $("#source").val();
    var sourceOrderSn = $("#sourceOrderSn").val();
    var commentCreateTime = $("#commentCreateTime").datetimebox('getValue');
    var commentEndTime = $("#commentEndTime").datetimebox('getValue');
    var isGiveMark = $("#isGiveMark").val();
    var markResult = $("#markResult").val();
    var productType = $("#productType").val();
    var options = $('#datagrid2').datagrid().datagrid('getPager').data("pagination").options;
    queryParameters = {
        source: source,
        sourceOrderSn: sourceOrderSn,
        commentCreateTime: commentCreateTime,
        commentEndTime: commentEndTime,
        isGiveMark: isGiveMark,
        markResult: markResult,
        productType: productType,
        page: options.pageNumber,
        rows: options.pageSize
    };

    datagrid = $('#datagrid2').datagrid({
        url: "/toptraderate/search",
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        striped: true,
        singleSelect: true,
        pagination: true,
        queryParams: queryParameters,
        columns: [[
            // {title: "操作", field: "field0", sortable: true, formatter: go_detail},
            {
                title: "订单来源", field: "source", sortable: true, formatter: function (val, rowData, rowIndex) {
                if (val == 'TOPFENXIAO') {
                    return '官旗供应商';
                } else if (val == 'TBSC') {
                    return '海尔官方淘宝旗舰店';
                } else if (val == 'TONGSHUAI') {
                    return '淘宝网统帅日日顺乐家专卖店';
                }
                else if (val == 'FRIDGE') {
                    return '海尔冰冷旗舰店';
                }
                else if (val == 'TOPBOILER') {
                    return '海尔热水器专卖店';
                }
                else if (val == 'WASHER') {
                    return '海尔洗衣机旗舰店';
                }
                else if (val == 'MOOKA') {
                    return '淘宝模卡旗舰店';
                }
                else if (val == 'AIR') {
                    return '海尔空调旗舰店';
                }
                else if (val == 'TMTV') {
                    return '海尔电视旗舰店';
                }
                else if (val == 'TMKSD') {
                    return '天猫卡萨帝旗舰店';
                }
                else {
                    return '未知类型';
                }
            }
            },
            {title: "外部订单号", field: "sourceOrderSn", sortable: true},
            {title: "LBX单号", field: "lbxNo", sortable: true},
            {title: "网单号", field: "cOrderSn", sortable: true},
            {title: "宝贝信息", field: "productInfos", sortable: true},
            {title: "型号", field: "productName", sortable: true},
            {title: "数量", field: "number", sortable: true},
            {title: "物料编码", field: "sku", sortable: true},
            {title: "商品类型名称", field: "productType", sortable: true},
            {title: "旺旺ID", field: "nicker", sortable: true},
            {title: "收货人", field: "consignee", sortable: true},
            {title: "电话", field: "phone", sortable: true},
            {title: "手机", field: "mobile", sortable: true},
            {title: "所属区域", field: "region", sortable: true},
            {title: "所属库位", field: "sCode", sortable: true},
            {title: "下单时间", field: "createTime", sortable: true},
            {title: "付款时间", field: "payTime", sortable: true},
            {title: "评价创建时间", field: "commentCreateTime", sortable: true},
            {
                title: "是否评分", field: "isGiveMark", sortable: true, formatter: function (val, rowData, rowIndex) {
                return "";
            }
            },
            {title: "评价结果", field: "markResult", sortable: true},
            {title: "评价信息", field: "commentInfos", sortable: true, width: 150, formatter: be_pointer}]],
        pageSize: options.pageSize,
        pageList: [10, 20, 30, 40, 50],
        rownumbers: true
    });

    function go_detail() {
        return '<a href="acceptBadReview.html">详情</a>'
    }

    function be_pointer(val, row) {
        return '<a style="color: black;" href="#" title="' + val + '">' + val + '</a>';
    }
}