var dataGrid;
var gloid;
$(function () {
    //------ 初始化列表
     datagrid = {
         fit: true,
         toolbar: '#datagridToolbar_syncOrder',
         striped: true,
         singleSelect: false,
         pagination: true,
         pagePosition: 'bottom',
         columns: [[
             { title: "网店名称", field: "shopName", sortable: false },
             { title: "外部网店类型", field: "configType", sortable: false },
             { title: "自动同步", field: "autoSync", sortable: false }
         ]],
         pageSize: 50,
         pageList: [50, 100, 200],
         rownumbers: true
     }
     $('#datagrid').datagrid(datagrid);

    var searchselect = function () {
        dataGrid = $('#datagrid').datagrid({
            url: "/syncOrder/searchSyncOrder",
            striped: true, // 隔行变色
            rownumbers: true,
            fit: true,
            pagination: true,
            singleSelect: true,
            nowrap: false,
            pageSize: 50,
            pageList: [50, 100, 200],
            idField: 'id',
            columns: [[
                { title: 'id', field: 'id', sortable: false, width: 0, align: 'left', hidden: true},
                { title: '网店名称', field: 'shopName', sortable: false, width: 300, align: 'center'},
                { title: '外部网店类型', field: 'configType', sortable: false, width: 300, align: 'center'},
                {
                    title: '是否自动同步', field: 'autoSync', sortable: false, width: 100, align: 'center',
                    formatter: function (value, rowData, rowIndex) {
                     if (value == '1') {
                         return "是";
                     }
                     return "否";
                    }
                }
            ]],
            toolbar: '#datagridToolbar_syncOrder',
            onClickRow: function (rowIndex, row) {
                gloid = row.id;
            }
        });
        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
    }

    $('#searchBtn_syncOrder').on('click', function (event) {
        searchselect();
    });

    $("#addBtn_syncOrder").on('click', function (event) {
        // window.location.href = "/costPool/toAddCostPool";
        window.top.addTab('添加同步配置信息','/syncOrder/toAddSyncOrder',true);
    });


    $("#deleteBtn_syncOrder").on('click', function (event) {
        event.preventDefault();
        var selected = dataGrid.datagrid('getSelected');
        if (selected !== null) {
            confirm('确定删除？', function (r) {
                if (r) {
                    $.ajax({
                        url: "/syncOrder/deleteSyncOrder",
                        type: 'GET',
                        dataType: 'json',
                        data: { "id": selected.id },
                        success: function (data) {
                            if (data == 1) {
                                alert('删除成功');
                                searchselect();
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




})


function list() {
    if (gloid == undefined) {
        $.messager.alert("提示：", "请选择要修改的同步配置信息");
        return;
    } else {
        window.top.addTab('修改同步配置信息','/syncOrder/toUpdateSyncOrder?id='+ gloid,true);
    }
}
