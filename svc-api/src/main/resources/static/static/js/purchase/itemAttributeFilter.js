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
            field: 'id',
            title: '序号',
            width:90,
            align: 'center'
        },
            {
                field: 'valueSetId',
                title: '属性组编码',
                width:150,
                align: 'center'
            },
            {
                field: 'description',
                title: '属性组名称',
                width:120,
                align: 'center'
            },
            {
                field: 'value',
                title: '属性编码',
                width:70,
                align: 'center'
            },
            {
                field: 'valueMeaning',
                title: '属性名称',
                width:300,
                align: 'center'
            }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);

});
$('#reset').click(function () {
    $("#valueSetId").textbox("setValue", "");
    $("#valueMeaning").textbox("setValue", "");
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
        url: "/item/queryPage",
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
        queryParams: queryParameter,
        rownumbers: true,
        nowrap: false,
        columns: [
            [
                {
                    field: 'id',
                    title: '序号',
                    width:90,
                    align: 'center'
                },
                {
                    field: 'valueSetId',
                    title: '属性组编码',
                    width:150,
                    align: 'center'
                },
                {
                    field: 'description',
                    title: '属性组名称',
                    width:120,
                    align: 'center'
                },
                {
                    field: 'value',
                    title: '属性编码',
                    width:70,
                    align: 'center'
                },
                {
                    field: 'valueMeaning',
                    title: '属性名称',
                    width:300,
                    align: 'center'
                }
            ]
        ]
    });
});


