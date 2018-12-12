
var datagridData_ordersynclogs={'data':{'records':[
      {
          'orderSn':'',sortable:true,
          'lesDeliveryStatus':'',sortable:true,
          'shopName':'',sortable:true,
          'shopType':'',sortable:true,
          'failReason':'',sortable:true,
          'lastSyncTime':'',sortable:true,

      },

],'totalCount':1}};

var datagridOptions_ordersynclogs = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  //url: '/order/ordersynclogs/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
        {title: '订单号', field: 'orderSn',sortable:true},
        {title: 'LES发货状态', field: 'lesDeliveryStatus',sortable:true},
        {title: '所属网店', field: 'shopName',sortable:true},
        {title: '网店类型', field: 'shopType',sortable:true},
        {title: '失败原因', field: 'failReason',sortable:true},
        {title: '最后同步时间', field: 'lastSyncTime',sortable:true}
  ]],
  toolbar: '#datagridToolbar_ordersynclogs',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid_ordersynclogs').datagrid(datagridOptions_ordersynclogs);
  datagrid.datagrid('loadData',datagridData_ordersynclogs);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"Ordersynclogs");
  //$("#searchPanel_ordersynclogs").panel('resize');

  /*$("#searchBtn_ordersynclogs").on('click', function (event) {
    var param = $('#paramForm_ordersynclogs').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_ordersynclogs").on('click', function (event) {
    $('#addForm_ordersynclogs').form('reset');
    $('#addForm_ordersynclogs').find('[__actType]').val('add');
    $('#addDlg_ordersynclogs').dialog({'title':'新增'});
    $('#addDlg_ordersynclogs').dialog('open');
  });
  $("#addDlgSaveBtn_ordersynclogs").on('click', function () {
    if(!$('#addForm_ordersynclogs').form('validate')){return;}
    var actType = $('#addForm_ordersynclogs').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_ordersynclogs').dialog('close');
  });
  $("#editBtn_ordersynclogs").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_ordersynclogs').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_ordersynclogs').form('load', selected);
      $('#addForm_ordersynclogs').find('[__actType]').val('edit');
      $('#addDlg_ordersynclogs').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_ordersynclogs").on('click',function (event) {
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
  $("#resetBtn_ordersynclogs").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_ordersynclogs').form('reset');
  });
});
