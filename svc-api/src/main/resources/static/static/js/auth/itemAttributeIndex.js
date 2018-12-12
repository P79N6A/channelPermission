var datagridData_itemAttribute = {
    'data': {
        'records': [
            {
                'valueSetId': 'AbcMark', sortable: true,
                'description': 'ABC标识', sortable: true,
                'value': 'C', sortable: true,
                'valueMeaning': '物料 - 不重要', sortable: true,
            },
            {
                'valueSetId': 'QmControlCode', sortable: true,
                'description': 'QM控制码', sortable: true,
                'value': '0001', sortable: true,
                'valueMeaning': '交货下达', sortable: true,
            },
            {
                'valueSetId': 'QmControlCode', sortable: true,
                'description': 'QM控制码', sortable: true,
                'value': '0000', sortable: true,
                'valueMeaning': '无活动功能，信息消息', sortable: true,
            },
            {
                'valueSetId': 'QmControlCode', sortable: true,
                'description': 'QM控制码', sortable: true,
                'value': '0002', sortable: true,
                'valueMeaning': '交货下达，技术交货条款', sortable: true,
            },
            {
                'valueSetId': 'QmControlCode', sortable: true,
                'description': 'QM控制码', sortable: true,
                'value': '0003', sortable: true,
                'valueMeaning': '交货下达，QA 协议', sortable: true,
            }
        ], 'totalCount': 5
    }
};

var datagridOptions_itemAttribute = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/auth/itemAttribute/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[

        {title: '属性组编码', field: 'valueSetId', sortable: true},
        {title: '属性组名称', field: 'description', sortable: true},
        {title: '属性编码', field: 'value', sortable: true},
        {title: '属性名称', field: 'valueMeaning', sortable: true},

    ]],
    toolbar: '#datagridToolbar_itemAttribute',
    striped: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_itemAttribute').datagrid(datagridOptions_itemAttribute);
    datagrid.datagrid('loadData', datagridData_itemAttribute);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"ItemAttribute");
    //$("#searchPanel_itemAttribute").panel('resize');

    /*$("#searchBtn_itemAttribute").on('click', function (event) {
     var param = $('#paramForm_itemAttribute').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/

    $("#resetBtn_itemAttribute").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_itemAttribute').form('reset');
    });
});
