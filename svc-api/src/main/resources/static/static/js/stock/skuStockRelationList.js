var datagridData_orderForecastLogs;
var datagridOptions_waAddressT = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    columns: [[
        /*{title: '渠道', field: 'channelCode', sortable: true},
        {title: '产品组', field: 'productGroup', sortable: true},
        {title: '库存编码', field: 'secCode', sortable: true},
        {title: '库位系数', field: 'coefficient', sortable: true}*/
    ]],
    toolbar: '#datagridToolbar_waAddressT',
    striped: true,
    pagination: true,
    rownumbers: true,
};

var columns;
var datagrid;
$(function () {
    datagrid = $('#datagrid_waAddressT').datagrid(datagridOptions_waAddressT);
    var options = datagrid.datagrid('getPager').data("pagination").options;
    $("#searchBtn_waAddressT").on('click', function(event){
        $.ajax({
            type: "POST",
            url: "/skustockrelation/sku_stock_relation_list.html",
            data: {sku:$("#sku").val(),scode:$("#scode").val(),addTimeStart:$("#addTimeStart").val(),page: options.pageNumber,rows: options.pageSize},
            dataType: "json",
            async:false,//同步请求
            success: function(data){
                if(data.scodeSet.length > 0){
                    var columns = '[[{"title":"&库存|锁定库存","field":"sku","sortable":"true"},';
                    for(var i = 0; i < data.scodeSet.length; i++){
                       columns = columns + '{"title":"' + data.scodeSet[i] + '","field":"' + data.scodeSet[i] + '","sortable":"true"},';
                    }
                    columns = columns.substring(0,columns.length-1) + ']]';
                    var columnsJstr = JSON.parse(columns);
                    datagridOptions_waAddressT = {
                        fit: true,
                        fitColumns: true,
                        singleSelect: true,
                        columns: columnsJstr,
                        toolbar: '#datagridToolbar_waAddressT',
                        striped: true,
                        pagination: true,
                        rownumbers: true,
                    };
                    datagrid = $('#datagrid_waAddressT').datagrid(datagridOptions_waAddressT);

                    var temp = '{"total":'+ data.total + ',"rows":[';
                    for(var i = 0; i < data.showList.length; i++){
                        temp = temp + '{';
                        for(var j in data.showList[i]){
                            temp = temp + '"' + j + '":"' +data.showList[i][j] + '","sortable": "true",';
                        }
                        temp = temp.substring(0,temp.length-1) + '},';
                    }
                    datagridData_orderForecastLogs = temp.substring(0,temp.length-1) + ']}';
                    var data = JSON.parse(datagridData_orderForecastLogs);
                    datagrid.datagrid('loadData', data);
                }
            }
        });
    });

    $("#addBtn_waAddressT").on('click', function (event) {
        $('#addForm_waAddressT').form('reset');
        $('#addForm_waAddressT').find('[__actType]').val('add');
        $('#addDlg_waAddressT').dialog({'title': '新增'});
        $('#addDlg_waAddressT').dialog('open');
    });
    $("#addDlgSaveBtn_waAddressT").on('click', function () {
        if (!$('#addForm_waAddressT').form('validate')) {
            return;
        }
        var actType = $('#addForm_waAddressT').find('[__actType]').val();
        if (actType === 'add') {
            alert('新增成功');
        } else {
            alert('编辑成功');
        }
        $('#addDlg_waAddressT').dialog('close');
    });
    $("#editBtn_waAddressT").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_waAddressT').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_waAddressT').form('load', selected);
            $('#addForm_waAddressT').find('[__actType]').val('edit');
            $('#addDlg_waAddressT').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_waAddressT").on('click', function (event) {
        event.preventDefault();
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
            confirm('确定删除？', function (r) {
                if (r) {
                    alert('删除成功');
                }
            })

        } else {
            alert('请选择一条数据');
        }
    });
    $("#resetBtn_waAddressT").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_waAddressT').form('reset');
    });
});
