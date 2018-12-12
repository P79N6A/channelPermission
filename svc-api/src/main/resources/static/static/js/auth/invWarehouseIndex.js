
var datagridData_invWarehouse={'data':{'records':[
    {
        'whCode':'AK',sortable:true,
        'whName':'安康网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'C12709',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

    },
    {
        'whCode':'NN',sortable:true,
        'whName':'南宁网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'NN34OP3',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

    },
    {
        'whCode':'CD',sortable:true,
        'whName':'成都网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'CD234P0ER',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

    },
    {
        'whCode':'WLMQ',sortable:true,
        'whName':'乌鲁木齐网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'WLMQ678904',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

    },
    {
        'whCode':'JN',sortable:true,
        'whName':'济南网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'JN342R',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

    },
    {
        'whCode':'SJZ',sortable:true,
        'whName':'石家庄网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'SJZW43R',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

    },
    {
        'whCode':'LZ',sortable:true,
        'whName':'柳州网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'LZ3423',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

    },
    {
        'whCode':'QQHE',sortable:true,
        'whName':'齐齐哈尔网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'QQHE4542709',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

    },
    {
        'whCode':'WX',sortable:true,
        'whName':'无锡网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'WX78E52709',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

    },
    {
        'whCode':'WUHA',sortable:true,
        'whName':'武汉网单库',sortable:true,
        'status':'在用',sortable:true,
        'centerCode':'WH89789',sortable:true,
        'supportCod':'否',sortable:true,
        'supportedDeliveryMode':'H3',sortable:true,


        'lesFiveYard':'',sortable:true,
        'lesWhCode':'BDWA',sortable:true,
        'updateTime':'2015-05-18 17:11:15',sortable:true,

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
        {title: '网单中心代码', field: 'centerCode',sortable:true},
        {title: '是否支持货到付款', field: 'supportCod',sortable:true},
        {title: '该TC支持的物流模式', field: 'supportedDeliveryMode',sortable:true},
        /*{title: '创建者', field: 'createUser',sortable:true},
        {title: '创建时间', field: 'createTime',sortable:true},
        {title: '最后更新人', field: 'updateUser',sortable:true},
        */
        {title: '送达方代码', field: 'lesFiveYard',sortable:true},
        {title: 'LES库位编码', field: 'lesWhCode',sortable:true},
        {title: '最后更新时间', field: 'updateTime',sortable:true},
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
