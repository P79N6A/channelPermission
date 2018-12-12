
var datagridData_orderForecastGoal={'data':{'records':[
      {
          'sku':'ASF0898',sortable:true,
          'materialdescription':'459435un',sortable:true,
          'channelcode':'天猫商城',sortable:true,
          'orderweek':'3',sortable:true,
          'orderyear':'2017',sortable:true,
          'goalqty':'35634',sortable:true,
          'operate':'',sortable:true
      },
      {
          'sku':'ASF0898',sortable:true,
          'materialdescription':'459435un',sortable:true,
          'channelcode':'天猫商城',sortable:true,
          'orderweek':'3',sortable:true,
          'orderyear':'2017',sortable:true,
          'goalqty':'35634',sortable:true,
          'operate':'',sortable:true
      },
      {
          'sku':'ASF0898',sortable:true,
          'materialdescription':'459435un',sortable:true,
          'channelcode':'天猫商城',sortable:true,
          'orderweek':'3',sortable:true,
          'orderyear':'2017',sortable:true,
          'goalqty':'35634',sortable:true,
          'operate':'',sortable:true
      }
],'totalCount':3}};

var datagridOptions_orderForecastGoal = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  //url: '/auth/orderForecastGoal/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
        {title: '物料编码', field: 'sku',sortable:true},
        {title: '型号', field: 'materialdescription',sortable:true},
        {title: '渠道', field: 'channelcode',sortable:true},
        {title: '上单周', field: 'orderweek',sortable:true},
        {title: '上单年', field: 'orderyear',sortable:true},
        {title: '目标销售', field: 'goalqty',sortable:true},
        // {title: '操作', field: 'operate',sortable:true}
  ]],
  toolbar: '#datagridToolbar_orderForecastGoal',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);
  // datagrid.datagrid('loadData',datagridData_orderForecastGoal);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"OrderForecastGoal");
  //$("#searchPanel_orderForecastGoal").panel('resize');

  /*$("#searchBtn_orderForecastGoal").on('click', function (event) {
    var param = $('#paramForm_orderForecastGoal').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_orderForecastGoal").on('click', function (event) {
    $('#addForm_orderForecastGoal').form('reset');
    $('#addForm_orderForecastGoal').find('[__actType]').val('add');
    $('#addDlg_orderForecastGoal').dialog({'title':'新增'});
    $('#addDlg_orderForecastGoal').dialog('open');
  });
  $("#addDlgSaveBtn_orderForecastGoal").on('click', function () {
    if(!$('#addForm_orderForecastGoal').form('validate')){return;}
    var actType = $('#addForm_orderForecastGoal').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_orderForecastGoal').dialog('close');
  });
  $("#editBtn_orderForecastGoal").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_orderForecastGoal').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_orderForecastGoal').form('load', selected);
      $('#addForm_orderForecastGoal').find('[__actType]').val('edit');
      $('#addDlg_orderForecastGoal').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_orderForecastGoal").on('click',function (event) {
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
  $("#resetBtn_orderForecastGoal").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_orderForecastGoal').form('reset');
  });
});
