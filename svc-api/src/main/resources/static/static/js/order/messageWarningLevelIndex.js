
var datagridData_messageWarningLevel={'data':{'records':[
      {
          'level':'1',sortable:true,
          'day':'0',sortable:true,
      },
      {
          'level':'2',sortable:true,
          'day':'1',sortable:true,
      },
      {
          'level':'3',sortable:true,
          'day':'2',sortable:true,
      },
      {
          'level':'4',sortable:true,
          'day':'3',sortable:true,
      },
      {
          'level':'5',sortable:true,
          'day':'4',sortable:true,
      },
],'totalCount':5}};

var datagridOptions_messageWarningLevel = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  //url: '/order/messageWarningLevel/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
        {title: '级别', field: 'level',sortable:true},
        {title: '超期天数', field: 'day',sortable:true},
  ]],
  toolbar: '#datagridToolbar_messageWarningLevel',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid_messageWarningLevel').datagrid(datagridOptions_messageWarningLevel);
  datagrid.datagrid('loadData',datagridData_messageWarningLevel);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"MessageWarningLevel");
  //$("#searchPanel_messageWarningLevel").panel('resize');

  /*$("#searchBtn_messageWarningLevel").on('click', function (event) {
    var param = $('#paramForm_messageWarningLevel').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_messageWarningLevel").on('click', function (event) {
    $('#addForm_messageWarningLevel').form('reset');
    $('#addForm_messageWarningLevel').find('[__actType]').val('add');
    $('#addDlg_messageWarningLevel').dialog({'title':'新增'});
    $('#addDlg_messageWarningLevel').dialog('open');
  });
  $("#addDlgSaveBtn_messageWarningLevel").on('click', function () {
    if(!$('#addForm_messageWarningLevel').form('validate')){return;}
    var actType = $('#addForm_messageWarningLevel').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_messageWarningLevel').dialog('close');
  });
  $("#editBtn_messageWarningLevel").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_messageWarningLevel').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_messageWarningLevel').form('load', selected);
      $('#addForm_messageWarningLevel').find('[__actType]').val('edit');
      $('#addDlg_messageWarningLevel').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_messageWarningLevel").on('click',function (event) {
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
  $("#resetBtn_messageWarningLevel").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_messageWarningLevel').form('reset');
  });
});
