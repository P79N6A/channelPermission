
var datagridData_invWarehouse={'data':{'records':[
    {
        'whCode':'0088',sortable:true,
        'whName':'顺德成品库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'GD',sortable:true,
        'supportCod':'GD',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    },
    {
        'whCode':'3BJ4',sortable:true,
        'whName':'京东北京协同仓',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'BJ',sortable:true,
        'supportCod':'JDXT',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    },
    {
        'whCode':'3BT4',sortable:true,
        'whName':'京东包头协同仓',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'BT',sortable:true,
        'supportCod':'JDXT',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    },
    {
        'whCode':'3CC4',sortable:true,
        'whName':'京东长春协同仓',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'CC',sortable:true,
        'supportCod':'JDXT',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    },
    {
        'whCode':'3CD4',sortable:true,
        'whName':'京东成都协同仓',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'CD',sortable:true,
        'supportCod':'JDXT',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    },
    {
        'whCode':'3CN4',sortable:true,
        'whName':'京东内江协同仓',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'CN',sortable:true,
        'supportCod':'JDXT',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    },
    {
        'whCode':'3DB4',sortable:true,
        'whName':'京东哈尔滨协同仓',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'DB',sortable:true,
        'supportCod':'JDXT',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    },
    {
        'whCode':'3GX4',sortable:true,
        'whName':'京东贵阳协同仓',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'GX',sortable:true,
        'supportCod':'JDXT',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    },
    {
        'whCode':'3GY4',sortable:true,
        'whName':'京东柳州协同仓',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'GY',sortable:true,
        'supportCod':'JDXT',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    },
    {
        'whCode':'3GX4',sortable:true,
        'whName':'京东贵阳协同仓',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'GX',sortable:true,
        'supportCod':'JDXT',sortable:true,
        'supportedDeliveryMode':'22',sortable:true,
        'updateTime':'2014-01-22 11:24:45',sortable:true,
        'lesFiveYard':'',sortable:true,
        'lesWhCode':'',sortable:true,
    }
],'totalCount':10}};

var datagridOptions_invWarehouse = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/auth/invWarehouse/p',
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
        {title: '库位编码', field: 'whCode',sortable:true},
        {title: '库位名称', field: 'whName',sortable:true},
        {title: '状态', field: 'status',sortable:true},
        {title: '仓库编码', field: 'centerCode',sortable:true},
        {title: '渠道编码', field: 'supportCod',sortable:true},
        {title: '产品属性', field: 'supportedDeliveryMode',sortable:true},
        /*{title: '创建者', field: 'createUser',sortable:true},
         {title: '创建时间', field: 'createTime',sortable:true},
         {title: '最后更新人', field: 'updateUser',sortable:true},
         */
        {title: '最后更新时间', field: 'updateTime',sortable:true},
        {title: '付款方编码', field: 'lesFiveYard',sortable:true},
        {title: '地区编码', field: 'lesWhCode',sortable:true},

        /*{title: 'les接收人', field: 'lesAccepter',sortable:true},
         {title: '日日顺配送中心编码', field: 'rrsDeliverCode',sortable:true},
         {title: 'CRM地区编码', field: 'crmAreaCode',sortable:true},
         {title: '电商付款方', field: 'ehaierPayer',sortable:true},
         {title: '工贸代码', field: 'itcCode',sortable:true},
         {title: '销售组织编码', field: 'moCode',sortable:true},
         {title: '电商库位码', field: 'estorgeId',sortable:true},
         {title: '电商库位名称', field: 'estorgeName',sortable:true},
         {title: '送达方代码', field: 'transmitId',sortable:true},
         {title: '送达方名称', field: 'transmitDesc',sortable:true},
         {title: '0E码（LES）', field: 'lesOeId',sortable:true},
         {title: '管理客户编码', field: 'customId',sortable:true},
         {title: '管理客户名称', field: 'customDesc',sortable:true},
         {title: '工贸代码', field: 'industryTradeId',sortable:true},
         {title: '工贸描述', field: 'industryTradeDesc',sortable:true},
         {title: '颗粒度编码', field: 'graininessId',sortable:true},
         {title: '网单经营体编码', field: 'netManagementId',sortable:true},
         {title: '电商售达方编码', field: 'esaleId',sortable:true},
         {title: '电商售达方名称', field: 'esaleName',sortable:true},
         {title: '销售组织编码', field: 'saleOrgId',sortable:true},
         {title: '', field: 'branch',sortable:true},
         {title: '电商付款方编码', field: 'paymentId',sortable:true},
         {title: '电商付款方名称', field: 'paymentName',sortable:true},
         {title: '地区编码（CRM专用）', field: 'areaId',sortable:true},
         {title: '日日顺配送编码', field: 'rrsDistributionId',sortable:true},
         {title: '日日顺配送名称', field: 'rrsDistributionName',sortable:true},
         {title: '日日顺售达方', field: 'rrsSaleId',sortable:true},
         {title: '日日顺售达方名称', field: 'rrsSaleName',sortable:true},
         {title: 'OMS重庆新日日顺付款方', field: 'omsRrsPaymentId',sortable:true},
         {title: 'OMS重庆新日日顺付款方名称', field: 'omsRrsPaymentName',sortable:true},
         {title: '客户编码', field: 'sapCustomerCode',sortable:true},
         {title: '客户编码名称', field: 'sapCustomerName',sortable:true},*/
    ]],
    toolbar: '#datagridToolbar_invWarehouse',
    striped: true,
    pagination: true,
    rownumbers: true,
};
$(function () {
    var datagrid = $('#datagrid_invWarehouse').datagrid(datagridOptions_invWarehouse);
    datagrid.datagrid('loadData',datagridData_invWarehouse);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"InvWarehouse");
    //$("#searchPanel_invWarehouse").panel('resize');

    /*$("#searchBtn_invWarehouse").on('click', function (event) {
     var param = $('#paramForm_invWarehouse').serializeObject();
     datagrid.datagrid({queryParams: param});
     event.preventDefault();
     });*/
    $("#addBtn_invWarehouse").on('click', function (event) {
        $('#addForm_invWarehouse').form('reset');
        $('#addForm_invWarehouse').find('[__actType]').val('add');
        $('#addDlg_invWarehouse').dialog({'title':'新增'});
        $('#addDlg_invWarehouse').dialog('open');
    });
    $("#addDlgSaveBtn_invWarehouse").on('click', function () {
        if(!$('#addForm_invWarehouse').form('validate')){return;}
        var actType = $('#addForm_invWarehouse').find('[__actType]').val();
        if(actType === 'add'){
            alert( '新增成功');
        }else{
            alert('编辑成功');
        }
        $('#addDlg_invWarehouse').dialog('close');
    });
    $("#editBtn_invWarehouse").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_invWarehouse').dialog({'title':'修改'});
        if (selected !== null) {
            $('#addForm_invWarehouse').form('load', selected);
            $('#addForm_invWarehouse').find('[__actType]').val('edit');
            $('#addDlg_invWarehouse').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_invWarehouse").on('click',function (event) {
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
    $("#resetBtn_invWarehouse").on('click',function (event) {
        event.preventDefault();
        $('#paramForm_invWarehouse').form('reset');
    });
});
