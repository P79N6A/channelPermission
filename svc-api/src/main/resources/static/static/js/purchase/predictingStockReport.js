var datagrid;
var queryParameters;
var LEVEL1_QJ={};
$(function () {
    datagrid = {
        fit: true,
        fitColumns: false,
        toolbar: '#datagridToolbar',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            {
                field: 'id',
                title: '序号',
                width: 10,
                align: 'center',
                hidden:true
            },
            {
                field: 'report_year_week_display',
                title: '提报周',
                width: 100,
                align: 'center'
            },
            {
                field: 'report_year_week',
                title: '提报周值',
                width: 10,
                align: 'center',
                hidden:true
            },
            {
                field: 'product_group_id',
                title: '产品线id',
                width: 10,
                align: 'center',
                hidden:true
            },
            {
                field: 'product_group_name',
                title: '产品组',
                width: 160,
                align: 'center'
            },
            {
                field: 'not_commit_count',
                title: '未提交',
                width: 100,
                align: 'center'
            },
            {
                field: 'commit_count',
                title: '已提交',
                width: 100,
                align: 'center'
            },
            {
                field: 'failure_count',
                title: '提交失败',
                width: 100,
                align: 'center'
            },
            {
                field: 'look',
                title: '操作',
                width: 100,
                align: 'center'
            }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);


});

function onReport(reportYearWeek,product_group) {
    $.messager.confirm('确定','确定要提交吗', function(r){
        if (r) {
            jQuery.ajax({
                url: "/purchase/reportPredictingStock?report_year_week=" + reportYearWeek + "&product_group_id=" + product_group,   // 提交的页面
                type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
                success: function (data) {
                    if(data.data != null) {$.messager.alert('错误',data.data,'error');}
                    else {
                        if(data.success){
                            //alert(data.data);
                            $.messager.alert('提示',data.data,'info',function(){
                                $('#dataGrid').datagrid('load', {reportYearWeek: $('#report_year_week').val(),productGroupId: $("#product_group_hidden").val()});
                            });
                        }}
                }
            })}
    });
}
function onShowDetail(param_report_year_week, param_product_group) {
    //var win = window.open('about:blank');
    var url = '/purchase/gotoQueryDetail.html' + '?report_year_week=' + param_report_year_week + '&product_group_id=' + param_product_group;
    //var win = window.open(url,'_blank');
    location.href = url;

    return false;
}
$(function () {
    jQuery.getJSON("/purchase/getProductGroupByAuth", function(result){
        var productgroup = result.data;
        productgroup.unshift({value:'ALL',valueMeaning:'全部'});
        $("#product_group").combobox({
            data:result.data,
            valueField:'value',
            textField:'valueMeaning',
            panelHeight:'auto',
            multiple:false,
            editable:false,
            value:'ALL',
            loadFilter: function(data){
                var datas = [];
                $.each(data, function(k, v){
                    if($.inArray(v.value, authMap.productGroup) > -1 || v.value == 'ALL'){
                        datas.push(v);
                    }
                });
                return datas;
            }
        });
    });
});
//点击查询
$('#search').click(function () {

    var product_group = $("#product_group").combobox("getValue");
    //如果是ALL，产品组设为空
    if(product_group=="ALL"){
        product_group="";
    };
    $("#product_group_hidden").val(product_group);
    
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/purchase/findPredictingStockReport",
        fit: true,
        fitColumns: false,
        pagination: true,
        singleSelect: true,
        pageSize: 100,
        pageList: [100,200,300],
        nowrap: true,
        rownumbers: true,
        queryParams: {
            reportYearWeek: $('#report_year_week').val(),
            productGroupId: product_group
        },
        onLoadSuccess : function (data) {
            /*if (data.rows.length == 0) {
                //var body = $(this).data().datagrid.dc.body2;
                //body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">您查询的预测信息不存在，请修改查询条件！</td></tr>');
                $.messager.alert('提示','您查询的预测信息不存在，请修改查询条件！','info');
            }*/
        },

        columns: [
            [
                {
                    field: 'id',
                    title: '序号',
                    width: 10,
                    align: 'center',
                    hidden:true
                },
                {
                    field: 'report_year_week_display',
                    title: '提报周',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'report_year_week',
                    title: '提报周值',
                    width: 10,
                    align: 'center',
                    hidden:true
                },
                {
                    field: 'product_group_id',
                    title: '产品线id',
                    width: 10,
                    align: 'center',
                    hidden:true
                },
                {
                    field: 'product_group_name',
                    title: '产品组',
                    width: 160,
                    align: 'center'
                },
                {
                    field: 'not_commit_count',
                    title: '未提交',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'commit_count',
                    title: '已提交',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'failure_count',
                    title: '提交失败',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'look',
                    title: '操作',
                    width: 100,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
                        return "<a href='javascript:void(0);' onclick=onReport('"+rowData.report_year_week + "','" + rowData.product_group_id+"');return false;>提交</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick=onShowDetail('"+rowData.report_year_week+ "','" + rowData.product_group_id+"');return false;>查看</a>";
                    }

                }
            ]
        ]
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);

});

