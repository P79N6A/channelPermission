/**
 * Created by Hanhaoyang on 2017/11/6.
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
            // {title: "操作", field: "field0", sortable: false, formatter: go_detail},
            {title: "订单来源", field: "source", sortable: false},
            {title: "外部订单号", field: "sourceOrderSn", sortable: false},
            {title: "商品名称", field: "productName", sortable: false},
            {title: "商品编码", field: "productCode", sortable: false},
            {title: "商品评分", field: "custStar", sortable: false},
            {title: "服务评分", field: "attitudeStar", sortable: false},
            {title: "物流评分", field: "deliverySpeedStar", sortable: false},
            {title: "买家名称", field: "userName", sortable: false},
            {title: "顾客评价内容", field: "custEvaluateContent", sortable: false, width: 150, formatter: be_pointer},
            {title: "顾客评价时间", field: "custEvaluateTime", sortable: false},
            {title: "顾客追评", field: "custAddEvaluateContent", sortable: false, width: 150, formatter: be_pointer},
            {title: "顾客追评时间", field: "custAddEvaluateTime", sortable: false},
            {title: "商家回复", field: "shopReplyContent", sortable: false, width: 150, formatter: be_pointer},
            {title: "商家回复时间", field: "shopReplyTime", sortable: false}]],
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
    var productName = $("#productName").val();
    var markResult = $("#markResult").val();
    var productCode = $("#productCode").val();
    var options = $('#datagrid2').datagrid().datagrid('getPager').data("pagination").options;
    queryParameters = {
        source: source,
        sourceOrderSn: sourceOrderSn,
        productName: productName,
        commentCreateTime: commentCreateTime,
        commentEndTime: commentEndTime,
        markResult: markResult,
        productCode: productCode,
        page: options.pageNumber,
        rows: options.pageSize
    };

    datagrid = $('#datagrid2').datagrid({
        url: "/evaluation/search",
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        striped: true,
        singleSelect: true,
        pagination: true,
        queryParams: queryParameters,
        columns: [[
            {
                title: "订单来源", field: "source", sortable: false, formatter: function (val, rowData, rowIndex) {
                if (val == 'SNYG') {
                    return '海尔统帅苏宁旗舰店';
                } else if (val == 'GMZX') {
                    return '国美海尔官方旗舰店';
                } else if (val == 'GMZXTS') {
                  return '国美统帅官方旗舰店';
                } else if (val == 'SNQDZX') {
                    return '苏宁渠道中心';
                }else if (val == 'SNYG') {
                    return '苏宁统帅官方旗舰店';
                } else if (val == 'SNHEGQ') {
                   return '苏宁海尔官方旗舰店';
                } else if (val == 'DDW') {
                    return '当当';
                }
                else if (val == 'JDHEBXGQ') {
                    return '京东海尔集团冰箱官方旗舰店';
                }
                else if (val == 'JDHEGQ') {
                    return '京东海尔官方旗舰店';
                }
                else if (val == 'DSPTJDST') {
                    return '电商平台-京东生态';
                }
            }
            },
            {title: "外部订单号", field: "sourceOrderSn", sortable: false},
            {title: "商品名称", field: "productName", sortable: false},
            {title: "商品编码", field: "productCode", sortable: false},
            {title: "商品评分", field: "custStar", sortable: false},
            {title: "服务评分", field: "attitudeStar", sortable: false},
            {title: "物流评分", field: "deliverySpeedStar", sortable: false},
            {title: "买家名称", field: "userName", sortable: false},
            {title: "顾客评价内容", field: "custEvaluateContent", sortable: false, width: 150, formatter: be_pointer},
            {title: "顾客评价时间", field: "custEvaluateTime", sortable: false},
            {title: "顾客追评", field: "custAddEvaluateContent", sortable: false, width: 150, formatter: be_pointer},
            {title: "顾客追评时间", field: "custAddEvaluateTime", sortable: false},
            {title: "顾客回复", field: "shopReplyContent", sortable: false, width: 150, formatter: be_pointer},
            {title: "顾客回复时间", field: "shopReplyTime", sortable: false}]],
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