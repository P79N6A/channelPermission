
var datagrid;
var queryParameters;

$(function () {
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_invoiceMakeOutList',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            {title: "序号", field: "id", hidden: true},
            {title: "网单号", field: "corderSn", sortable: true},
            {title: "税控码", field: "fiscalCode", sortable: true}
        ]],
        pageSize: 50,
        pageList: [50,100,200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);
});

//点击查询
$('#search').click(function () {
    var sourceOrderSn = $("#sourceOrderSn").val();

    if(sourceOrderSn == null || sourceOrderSn.trim() == ""){
        $.messager.alert('错误','请先输入:来源订单号','error');
        return false;
    }

    queryParameters ={
        sourceOrderSn:$("#sourceOrderSn").val(),
    };
        //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/invoice/getFiscalCodeList.html",
        fit: true,
        fitColumns: true,
        pagination: true,
        singleSelect: false,
        checkOnSelect:true,
        pageSize: 50,
        pageList: [50,100,200],
        nowrap: false,
        rownumbers: true,
        queryParams: queryParameters,
        columns: [
            [
                {
                    field: 'id',
                    title: '序号',
                    align: 'center',
                    hidden:true
                },
                {
                    field: 'corderSn',
                    title: '网单号',
                    sortable: true,
                    align: 'left'
                },
                {
                    field: 'fiscalCode',
                    title: '税控码',
                    sortable: true,
                    align: 'center'
                }
            ]
        ],
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
});
