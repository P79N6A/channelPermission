
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
            { title: "库位码", field: "a", sortable: false },
            { title: "商品SKU", field: "b", sortable: false },
            { title: "实际库存", field: "c", sortable: false },
            { title: "自有库存", field: "d", sortable: false },
            { title: "占用库存", field: "e", sortable: false },
            { title: "可用库存", field: "f", sortable: false },
            { title: "淘宝预留", field: "g", sortable: false },
            { title: "商城预留", field: "h", sortable: false },
            { title: "企业预留", field: "i", sortable: false },
            { title: "共享预留", field: "j", sortable: false },
            { title: "更新时间", field: "k", sortable: false }
        ]],
        pageSize: 50,
        pageList: [50, 100, 200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);


});



$('#search').click(function () {


    //提交参数
    queryParameters = {
        id: $("#id").val(),
        secCode: $("#secCode").val().trim(),//库位码
        sku: $("#sku").val().trim(),//商品SKU
        rand: Math.random(),
    };

    datagrid = $('#datagrid').datagrid({
        url: "/stock/getStockList.html",
        fit: true,
        fitColumns: false,
        pagination: true,
        singleSelect: false,
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
                    title: '库位码 ',
                    field: 'a',
                    align: 'center',
                },
                {
                    title: '商品SKU ',
                    field: 'b',
                    align: 'center',
                }, {
                title: '实际库存 ',
                field: 'c',
                align: 'center',
            }, {
                title: '自有库存 ',
                field: 'd',
                align: 'center',
            }, {
                title: '占用库存 ',
                field: 'e',
                align: 'center',
            },
                {
                    title: '可用库存',
                    field: 'f',
                    sortable: false,
                    align: 'center'
                }, {
                    title: '淘宝预留',
                    field: 'g',
                    align: 'center'
                }, {
                    title: '商城预留',
                    field: 'h',
                    align: 'center'
                }, {
                title: '企业预留',
                field: 'i',
                align: 'center'
            }, {
                title: '共享预留',
                field: 'j',
                align: 'center'
            }, {
                title: '更新时间',
                field: 'k',
                align: 'center'
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
            $('#paramForm_dmmtlPbcsMtlMeasure').attr("action", '//');
            $('#paramForm_dmmtlPbcsMtlMeasure').submit();
        }
    });
});