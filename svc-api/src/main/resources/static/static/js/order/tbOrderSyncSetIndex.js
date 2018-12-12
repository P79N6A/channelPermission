67
var datagridData_tbOrderSyncSet={'data':{'records':[
      {
          'shopName':'海尔厨房大电旗舰店',sortable:true,
          'externalShopType':'	淘宝海尔厨房大电旗舰店',sortable:true,
          'autoSync':'<img src="../static/third/easyui/themes/insdep/icons/ok.png"/>',sortable:true,
      },
      {
          'shopName':'海尔电视旗舰店',sortable:true,
          'externalShopType':'天猫海尔电视',sortable:true,
          'autoSync':'<img src="../static/third/easyui/themes/insdep/icons/ok.png"/>',sortable:true,
      },
],'totalCount':2}};

var datagridOptions_tbOrderSyncSet = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  //url: '/order/tbOrderSyncSet/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
        {title: '网店名称', field: 'shopName',sortable:true},
        {title: '外部网店类型', field: 'externalShopType',sortable:true},
        {title: '自动同步', field: 'autoSync',sortable:true},
  ]],
  toolbar: '#datagridToolbar_tbOrderSyncSet',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid_tbOrderSyncSet').datagrid(datagridOptions_tbOrderSyncSet);
  datagrid.datagrid('loadData',datagridData_tbOrderSyncSet);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"TbOrderSyncSet");
  //$("#searchPanel_tbOrderSyncSet").panel('resize');

  /*$("#searchBtn_tbOrderSyncSet").on('click', function (event) {
    var param = $('#paramForm_tbOrderSyncSet').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_tbOrderSyncSet").on('click', function (event) {
    $('#addForm_tbOrderSyncSet').form('reset');
    $('#addForm_tbOrderSyncSet').find('[__actType]').val('add');
    $('#addDlg_tbOrderSyncSet').dialog({'title':'新增'});
    $('#addDlg_tbOrderSyncSet').dialog('open');
  });
  $("#addDlgSaveBtn_tbOrderSyncSet").on('click', function () {
    if(!$('#addForm_tbOrderSyncSet').form('validate')){return;}
    var actType = $('#addForm_tbOrderSyncSet').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_tbOrderSyncSet').dialog('close');
  });
  $("#editBtn_tbOrderSyncSet").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_tbOrderSyncSet').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_tbOrderSyncSet').form('load', selected);
      $('#addForm_tbOrderSyncSet').find('[__actType]').val('edit');
      $('#addDlg_tbOrderSyncSet').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_tbOrderSyncSet").on('click',function (event) {
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
  $("#resetBtn_tbOrderSyncSet").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_tbOrderSyncSet').form('reset');
  });
});
