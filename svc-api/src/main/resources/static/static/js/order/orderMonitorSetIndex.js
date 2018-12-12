
var datagridData_orderMonitorSet={'data':{'records':[
      {
          'monitorName':'待同步VOM网单数',sortable:true,
          'monitorDesc':'推送订单到VOM未完成网单统计',sortable:true,
          'dataSource':'shop',sortable:true,
          'sql':'select count(1) from LesQueues ',sortable:true,
          'gateValue':'100.0',sortable:true,
          'monitorType':'订单',sortable:true,
          'warnLevel':'严重',sortable:true,
          'isSendEmail':'是',sortable:true,
          'isSendMsg':'是',sortable:true,
          'emailAddress':'xinmeng@ehaier.com,weiyunjun@ehaier.com',sortable:true,
          'moblieNo':'18512344321,13687654321',sortable:true,
          'satus':'启用',sortable:true,
      },
      {
          'monitorName':'VOM报文解析',sortable:true,
          'monitorDesc':'解析VOM报文',sortable:true,
          'dataSource':'cbs',sortable:true,
          'sql':'select count(1) from dbs_eis.vom_recieved_queque where status = 0 and TIME_TO_SEC(TIMEDIFF(now(),add_time))>600 ',sortable:true,
          'gateValue':'100.0',sortable:true,
          'monitorType':'库存',sortable:true,
          'warnLevel':'严重',sortable:true,
          'isSendEmail':'是',sortable:true,
          'isSendMsg':'是',sortable:true,
          'emailAddress':'xinmeng@ehaier.com,weiyunjun@ehaier.com',sortable:true,
          'moblieNo':'18512344321,13687654321',sortable:true,
          'satus':'启用',sortable:true,
      },
      {
          'monitorName':'确认订单正常专有库存监控',sortable:true,
          'monitorDesc':'确认订单正常专有库存监控',sortable:true,
          'dataSource':'shop',sortable:true,
          'sql':'select count(1) from orderproducts op inner join orders os on op.orderId = os.id where op.stockType = "WA" AND op.status = 0 ',sortable:true,
          'gateValue':'100.0',sortable:true,
          'monitorType':'订单',sortable:true,
          'warnLevel':'严重',sortable:true,
          'isSendEmail':'是',sortable:true,
          'isSendMsg':'是',sortable:true,
          'emailAddress':'xinmeng@ehaier.com,weiyunjun@ehaier.com',sortable:true,
          'moblieNo':'18512344321,13687654321',sortable:true,
          'satus':'启用',sortable:true,
      },

],'totalCount':3}};

var datagridOptions_orderMonitorSet = {
  fit: true,
  fitColumns: false,
  singleSelect: true,
  //url: '/order/orderMonitorSet/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
        {title: '监控名称', field: 'monitorName',sortable:true},
        {title: '监控项描述', field: 'monitorDesc',sortable:true},
        {title: '数据源', field: 'dataSource',sortable:true},
        {title: 'sql语句', field: 'sql',sortable:true},
        {title: '阈值', field: 'gateValue',sortable:true},
        {title: '监控类型', field: 'monitorType',sortable:true},
        {title: '预警级别', field: 'warnLevel',sortable:true},
        {title: '是否发送邮件', field: 'isSendEmail',sortable:true},
        {title: '是否发送短信', field: 'isSendMsg',sortable:true},
        {title: '邮件地址', field: 'emailAddress',sortable:true},
        {title: '手机号码', field: 'moblieNo',sortable:true},
        {title: '状态', field: 'satus',sortable:true},
  ]],
  toolbar: '#datagridToolbar_orderMonitorSet',
  striped: true,
  pagination: true,
  rownumbers: true,
    autoRowHeight: true,
    nowrap: false,
};
$(function () {
  var datagrid = $('#datagrid_orderMonitorSet').datagrid(datagridOptions_orderMonitorSet);
  datagrid.datagrid('loadData',datagridData_orderMonitorSet);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"OrderMonitorSet");
  //$("#searchPanel_orderMonitorSet").panel('resize');

  /*$("#searchBtn_orderMonitorSet").on('click', function (event) {
    var param = $('#paramForm_orderMonitorSet').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_orderMonitorSet").on('click', function (event) {
    $('#addForm_orderMonitorSet').form('reset');
    $('#addForm_orderMonitorSet').find('[__actType]').val('add');
    $('#addDlg_orderMonitorSet').dialog({'title':'新增'});
    $('#addDlg_orderMonitorSet').dialog('open');
  });
  $("#addDlgSaveBtn_orderMonitorSet").on('click', function () {
    if(!$('#addForm_orderMonitorSet').form('validate')){return;}
    var actType = $('#addForm_orderMonitorSet').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_orderMonitorSet').dialog('close');
  });
  $("#editBtn_orderMonitorSet").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_orderMonitorSet').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_orderMonitorSet').form('load', selected);
      $('#addForm_orderMonitorSet').find('[__actType]').val('edit');
      $('#addDlg_orderMonitorSet').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_orderMonitorSet").on('click',function (event) {
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
  $("#resetBtn_orderMonitorSet").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_orderMonitorSet').form('reset');
  });
});
