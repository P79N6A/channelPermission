var dataGrid;
var queryParameters;
var gloid;
$(function () {
    var ym = new Date();
    Y = ym.getFullYear();
    M = (ym.getMonth() + 1 < 10 ? '0' + (ym.getMonth() + 1) : ym.getMonth() + 1)
    $('#yearMonth').textbox({ prompt: Y + M });
    //------ 初始化列表
    datagrid = {
        fit: true,
        // fitColumns: true,
        toolbar: '#datagridToolbar_costPools',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            { title: "渠道", field: "channel", sortable: false },
            { title: "产业", field: "chanYe", sortable: false },
            { title: "费用类型", field: "type", sortable: false },
            { title: '年月', field: 'yearMonth', sortable: false },
            { title: '批次', field: 'batch', sortable: false },
            { title: '总费用', field: 'amount', sortable: false },
            { title: '已使用', field: 'balanceAmount', sortable: false },
            { title: '添加时间', field: 'addTime', sortable: false },
            { title: '修改时间', field: 'editTime', sortable: false },
            { title: '操作人', field: 'masterName', sortable: false },
            { title: '备注', field: 'remark', sortable: false }

        ]],
        pageSize: 50,
        pageList: [50, 100, 200],
        rownumbers: true
    }
    $('#datagrid').datagrid(datagrid);

    //-----查询方法
    var searchselect = function () {
        queryParameters = {
            yearMonth: $("#yearMonth").val(),
            batch: $("#batch").val(),
            type: $("#type").combobox('getValue'),
            channel: $("#channel").combobox('getValue'),
            chanYe: $("#chanYe").combobox('getValue')
        };

        dataGrid = $('#datagrid').datagrid({
            url: "/costPool/search",
            striped: true, // 隔行变色
            rownumbers: true,
            fit: true,
            pagination: true,
            singleSelect: true,
            nowrap: false,
            pageSize: 50,
            pageList: [50, 100, 200],
            queryParams: queryParameters,
            idField: 'id',
            columns: [[
                { title: 'id', field: 'id', sortable: false, width: 0, align: 'left', hidden: true },
                {
                    title: '渠道 ', field: 'channel', sortable: false, width: 120,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == '1') return '商城';
                        if (value == '2') return '天猫';
                        if (value == '3') return '电商平台';
                        if (value == '4') return '微店';
                        return value;
                    }
                },
                {
                    title: '产业', field: 'chanYe', sortable: false, width: 150,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == 'C200076226') return '冰箱户';
                        if (value == 'C200076227') return '冷柜户';
                        if (value == 'C200076228') return '洗衣机户';
                        if (value == 'C200076229') return '空调户';
                        if (value == 'C200076230') return '热水器户';
                        if (value == 'C200076231') return '厨电户';
                        if (value == 'C200076232') return '彩电户';
                        if (value == 'C200076233') return '商用空调及其他';
                        if (value == 'C20170825') return '海尔集团电子商务有限公司(赠品)';
                        return value;
                    }
                },
                {
                    title: '费用类型', field: 'type', sortable: false, width: 80,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == '0') return '赠品机';
                        if (value == '1') return '优惠券';
                        return value;
                    }
                },
                { title: '年月', field: 'yearMonth', sortable: false, width: 125 },
                { title: '批次', field: 'batch', sortable: false, width: 80 },
                { title: '总费用', field: 'amount', sortable: false, width: 135 },
                { title: '已使用', field: 'balanceAmount', sortable: false, width: 135 },
                {
                    title: '添加时间', field: 'addTime', sortable: false, width: 200,
                    formatter: function (value, rowData, rowIndex) {
                        if (value != '0') {
                            var date = new Date(value * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                            Y = date.getFullYear() + '-';
                            M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
                            D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' '
                            h = (date.getHours() < 10 ? '0' + (date.getHours()) : date.getHours()) + ':'
                            m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes()) + ':'
                            s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds())
                            return Y + M + D + h + m + s;
                        }
                        return value;
                    }
                },
                {
                    title: '修改时间', field: 'editTime', sortable: false, width: 200,
                    formatter: function (value, rowData, rowIndex) {
                        if (value != '0') {
                            var date = new Date(value * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                            Y = date.getFullYear() + '-';
                            M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
                            D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' '
                            h = (date.getHours() < 10 ? '0' + (date.getHours()) : date.getHours()) + ':'
                            m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes()) + ':'
                            s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds())
                            return Y + M + D + h + m + s;
                        }
                        return value;
                    }
                },
                { title: '操作人', field: 'masterName', sortable: false, width: 165 },
                { title: '备注', field: 'remark', sortable: false, width: 165 }
            ]],
            toolbar: '#datagridToolbar_costPools',
            onClickRow: function (rowIndex, row) {
                gloid = row.id;
            }
        });
        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
    }


    $("#deleteBtn_costPools").on('click', function (event) {
        event.preventDefault();
        var selected = dataGrid.datagrid('getSelected');
        if (selected !== null) {
            confirm('确定删除？', function (r) {
                if (r) {
                    $.ajax({
                        url: "/costPool/deleteCostPool",
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

    $('#searchBtn_costPools').on('click', function (event) {
        searchselect();
    });



    // $("#addBtn_costPools").on('click', function (event) {
    //     $('#addForm_costPools').form('reset');
    //     $('#addForm_costPools').find('[__actType]').val('add');
    //     $('#addDlg_costPools').dialog({ 'title': '新增' });
    //     $('#addDlg_costPools').dialog('open');
    //     var date = new Date;
    //     var year = date.getFullYear();
    //     var month = date.getMonth() + 1;
    //     month = (month < 10 ? "0" + month : month);
    //     var mydate = (year.toString() + month.toString());
    //     $('#add_yearMonth').textbox('setValue', mydate);
    //     $('#add_yearMonth').textbox('textbox').attr('readonly', true);
    // });

    // $("#resetBtn_costPools").on('click', function (event) {
    //     event.preventDefault();
    //     $('#paramForm_costPools').form('reset');
    //     var startDate = $('#startDate').val();
    //     $('#addTimeBegine').datebox('setValue', startDate);
    // });

    // $("#addDlgSaveBtn_costPools").on('click', function () {
    //     if (!$('#addForm_costPools').form('validate')) {
    //         return;
    //     }
    //     var actType = $('#addForm_costPools').find('[__actType]').val();
    //     if (actType === 'add') {
    //         alert('新增成功');
    //     } else {
    //         alert('编辑成功');
    //     }
    //     $('#addDlg_costPools').dialog('close');
    // });

});
$("#searchBtn_costPoolsUsedLogs").on('click', function (event) {
    window.top.addTab("费用池使用日志", "/costPool/loadCostPoolsUsedLogs", true);
});

$("#addBtn_costPools").on('click', function (event) {
    // window.location.href = "/costPool/toAddCostPool";
    window.top.addTab('添加新费用','/costPool/toAddCostPool',true);
});
//    window.top.addTab("网单详情和订单详情", "/operationArea/ProductView?cOrderSn="+corderSn, true);
function addTab(title, url) {
    if ($('#costPoolsUsedLogsList').tabs('exists', title)) {
        $('#costPoolsUsedLogsList').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
        $('#costPoolsUsedLogsList').tabs('costPoolsUsedLogsList', {
            title: title,
            content: content,
            closable: true
        });
    }
}



setTimeout(function () {
    $('#datagrid').datagrid('resize');
}, 500);

$('#importBtn_costPools').click(function () {
    $.messager.confirm('确认', '确定要导出吗？', function (r) {
        if (r) {
            queryParameters = {
                yearMonth: $("#yearMonth").val(),
                batch: $("#batch").val(),
                type: $("#type").combobox('getValue'),
                channel: $("#channel").combobox('getValue'),
                chanYe: $("#chanYe").combobox('getValue')
            };
            $('#exportData').val(JSON.stringify(queryParameters));
            $('#paramForm_costPools').attr("action", '/costPool/exportCostPools');
            $('#paramForm_costPools').submit();
        }
    });
});

function list() {
    if (gloid == undefined) {
        $.messager.alert("提示：", "请选择要修改的费用池渠道");
        return;
    } else {
        // self.location.href = "/costPool/toUpdateCostPool?id=" + gloid + "";
        window.top.addTab('修改费用信息','/costPool/toUpdateCostPool?id='+ gloid,true);
    }
}
