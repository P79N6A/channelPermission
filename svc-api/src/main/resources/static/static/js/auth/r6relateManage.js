
var datagridData_r6relateManage={'data':{'records':[
    {
        'shopSku': 'DC16Q0M01',sortable:true,
        'externalSku':'R00009732',sortable:true,
        'status':'启用 <a href="#">弃用</a>',sortable:true,
        'externalSystem':'卡萨帝',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    },
    {
        'shopSku': 'SFLALSDFDFS',sortable:true,
        'externalSku':'SAFDSFGHKLP',sortable:true,
        'status':'启用 <a href="#">弃用</a>',sortable:true,
        'externalSystem':'统帅',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    },
    {
        'shopSku': 'ADFADFS089',sortable:true,
        'externalSku':'SDFAS2890347',sortable:true,
        'status':'<a href="#">启用</a> 弃用',sortable:true,
        'externalSystem':'统帅',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    },
    {
        'shopSku': 'DFJPR45343W4',sortable:true,
        'externalSku':'ASF]OASL452',sortable:true,
        'status':'启用 <a href="#">弃用</a>',sortable:true,
        'externalSystem':'卡萨帝',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    },
    {
        'shopSku': '43530RFTGL',sortable:true,
        'externalSku':'23RLDFSSFLS',sortable:true,
        'status':'启用 <a href="#">弃用</a>',sortable:true,
        'externalSystem':'统帅',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    },
    {
        'shopSku': '2432DFKSD',sortable:true,
        'externalSku':'34W3SDFLL',sortable:true,
        'status':'启用 <a href="#">弃用</a>',sortable:true,
        'externalSystem':'卡萨帝',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    },
    {
        'shopSku': '342K89098S',sortable:true,
        'externalSku':'KLLDFS00890',sortable:true,
        'status':'<a href="#">启用</a> 弃用',sortable:true,
        'externalSystem':'统帅',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    },
    {
        'shopSku': '349394KLDF',sortable:true,
        'externalSku':'3423LLSDD',sortable:true,
        'status':'启用 <a href="#">弃用</a>',sortable:true,
        'externalSystem':'卡萨帝',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    },
    {
        'shopSku': '342IOIDFK',sortable:true,
        'externalSku':'R00009732',sortable:true,
        'status':'启用 <a href="#">弃用</a>',sortable:true,
        'externalSystem':'统帅',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    },
    {
        'shopSku': 'DF34523423',sortable:true,
        'externalSku':'DFSFSFSD435',sortable:true,
        'status':'<a href="#">启用</a> 弃用',sortable:true,
        'externalSystem':'卡萨帝',sortable:true,
        'createTime':'2013-10-09 16:31:40.0',sortable:true,
        'createPerson':'yanzhao',sortable:true
    }

],'totalCount':10}};

var datagridOptions_r6relateManage = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/auth/dataDictionary/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '商城SKU', field: 'shopSku',sortable:true},
        {title: '外部SKU', field: 'externalSku',sortable:true},
        {title: '状态', field: 'status',sortable:true},
        {title: '外部系统', field: 'externalSystem',sortable:true},
        {title: '创建时间', field: 'createTime',sortable:true},
        {title: '创建人', field: 'createPerson',sortable:true},
    ]],
    toolbar: '#datagridToolbar_r6relateManage',
    striped: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_r6relateManage').datagrid(datagridOptions_r6relateManage);
    datagrid.datagrid('loadData',datagridData_r6relateManage);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"DataDictionary");
    //$("#searchPanel_r6relateManage").panel('resize');

    /*$("#searchBtn_r6relateManage").on('click', function (event) {
     var param = $('#paramForm_r6relateManage').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/
    $("#addBtn_r6relateManage").on('click', function (event) {
        /*$('#addForm_r6relateManage').form('reset');
         $('#addForm_r6relateManage').find('[__actType]').val('add');*/
        $('#addDlg_r6relateManage').dialog({'title':'新增'});
        $('#addDlg_r6relateManage').dialog('open');
    });
    $("#addDlgSaveBtn_r6relateManage").on('click', function () {
        if(!$('#addForm_r6relateManage').form('validate')){return;}
        var actType = $('#addForm_r6relateManage').find('[__actType]').val();
        if(actType === 'add'){
            alert( '新增成功');
        }else{
            alert('编辑成功');
        }
        $('#addDlg_r6relateManage').dialog('close');
    });
    $("#editBtn_r6relateManage").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_r6relateManage').dialog({'title':'修改'});
        if (selected !== null) {
            $('#addForm_r6relateManage').form('load', selected);
            $('#addForm_r6relateManage').find('[__actType]').val('edit');
            $('#addDlg_r6relateManage').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_r6relateManage").on('click',function (event) {
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
    $("#resetBtn_r6relateManage").on('click',function (event) {
        event.preventDefault();
        $('#paramForm_r6relateManage').form('reset');
    });
});
