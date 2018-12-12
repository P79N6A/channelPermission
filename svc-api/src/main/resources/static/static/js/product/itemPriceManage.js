
var datagridData_productLimitGateT={'data':{'records':[
    {
        'channelCode':'大客户渠道',sortable:true,
        'classifyType':'冰箱',sortable:true,
        'type':'上单',sortable:true,
        'orderNum':'50',sortable:true,
        'startEndTime':'2015-11-15~2020-11-11',sortable:true,
        'createUser':'张凌霄',sortable:true,
        'createTime':'2015-11-17 17:42:26',sortable:true,
        'updateUser':'',sortable:true,
        'updateTime':'',sortable:true
    },
    {
        'channelCode':'商城渠道',sortable:true,
        'classifyType':'B70TQ509P',sortable:true,
        'type':'下市',sortable:true,
        'orderNum':'',sortable:true,
        'startEndTime':'2015-11-15~2020-11-11',sortable:true,
        'createUser':'张凌霄',sortable:true,
        'createTime':'2015-11-17 17:42:26',sortable:true,
        'updateUser':'',sortable:true,
        'updateTime':'',sortable:true
    },

],'totalCount':2}};

var datagridOptions_productLimitGateT = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/auth/productLimitGateT/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '渠道', field: 'channelCode',sortable:true},
        {title: '产品组/品类/物料号', field: 'classifyType',sortable:true},
        {title: '类别', field: 'type',sortable:true},
        {title: '上单数', field: 'orderNum',sortable:true},
        {title: '起止时间', field: 'startEndTime',sortable:true},
        {title: '创建人', field: 'createUser',sortable:true},
        {title: '创建时间', field: 'createTime',sortable:true},
        {title: '更新人', field: 'updateUser',sortable:true},
        {title: '更新时间', field: 'updateTime',sortable:true},
    ]],
    toolbar: '#datagridToolbar_productLimitGateT',
    striped: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_productLimitGateT').datagrid(datagridOptions_productLimitGateT);
    datagrid.datagrid('loadData',datagridData_productLimitGateT);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"ProductLimitGateT");
    //$("#searchPanel_productLimitGateT").panel('resize');

    /*$("#searchBtn_productLimitGateT").on('click', function (event) {
     var param = $('#paramForm_productLimitGateT').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/
    $("#addBtn_productLimitGateT").on('click', function (event) {
        $('#addForm_productLimitGateT').form('reset');
        $('#addForm_productLimitGateT').find('[__actType]').val('add');
        $('#addDlg_productLimitGateT').dialog({'title':'新增'});
        $('#addDlg_productLimitGateT').dialog('open');
    });
    $("#addDlgSaveBtn_productLimitGateT").on('click', function () {
        if(!$('#addForm_productLimitGateT').form('validate')){return;}
        var actType = $('#addForm_productLimitGateT').find('[__actType]').val();
        if(actType === 'add'){
            alert( '新增成功');
        }else{
            alert('编辑成功');
        }
        $('#addDlg_productLimitGateT').dialog('close');
    });
    $("#editBtn_productLimitGateT").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_productLimitGateT').dialog({'title':'修改'});
        if (selected !== null) {
            $('#addForm_productLimitGateT').form('load', selected);
            $('#addForm_productLimitGateT').find('[__actType]').val('edit');
            $('#addDlg_productLimitGateT').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_productLimitGateT").on('click',function (event) {
        event.preventDefault();
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
            confirm('确定删除？',function(r){
                if(r){
                    alert('删除成功');
                }
            })

        }else{
            alert('请选择一条数据');
        }
    });
    $("#resetBtn_productLimitGateT").on('click',function (event) {
        event.preventDefault();
        $('#paramForm_productLimitGateT').form('reset');
    });
});
