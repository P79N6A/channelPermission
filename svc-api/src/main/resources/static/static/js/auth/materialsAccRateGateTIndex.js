
var datagridData_materialsAccRateGateT={'data':{'records':[
      {
          'edChannelId':'大客户渠道',sortable:true,
          'categoryId':'冰箱',sortable:true,
          'accRate':'0.90',sortable:true,
          'remark':'',sortable:true,
          'isEnabled':'禁用',sortable:true
      },
      {
          'edChannelId':'京东渠道',sortable:true,
          'categoryId':'空调',sortable:true,
          'accRate':'0.60',sortable:true,
          'remark':'',sortable:true,
          'isEnabled':'禁用',sortable:true
      },
      {
          'edChannelId':'模卡渠道',sortable:true,
          'categoryId':'洗衣机',sortable:true,
          'accRate':'0.80',sortable:true,
          'remark':'',sortable:true,
          'isEnabled':'禁用',sortable:true
      },
      {
          'edChannelId':'微店渠道',sortable:true,
          'categoryId':'热水器',sortable:true,
          'accRate':'0.60',sortable:true,
          'remark':'',sortable:true,
          'isEnabled':'禁用',sortable:true
      },
      {
          'edChannelId':'商城渠道',sortable:true,
          'categoryId':'厨电',sortable:true,
          'accRate':'0.60',sortable:true,
          'remark':'',sortable:true,
          'isEnabled':'禁用',sortable:true
      },
      {
          'edChannelId':'天猫渠道',sortable:true,
          'categoryId':'热水器',sortable:true,
          'accRate':'0.70',sortable:true,
          'remark':'',sortable:true,
          'isEnabled':'禁用',sortable:true
      },
      {
          'edChannelId':'统帅渠道',sortable:true,
          'categoryId':'洗衣机',sortable:true,
          'accRate':'0.60',sortable:true,
          'remark':'',sortable:true,
          'isEnabled':'禁用',sortable:true
      },
      {
          'edChannelId':'模卡渠道',sortable:true,
          'categoryId':'家电',sortable:true,
          'accRate':'0.60',sortable:true,
          'remark':'',sortable:true,
          'isEnabled':'禁用',sortable:true
      },
      {
          'edChannelId':'天猫渠道',sortable:true,
          'categoryId':'彩电',sortable:true,
          'accRate':'0.50',sortable:true,
          'remark':'',sortable:true,
          'isEnabled':'禁用',sortable:true
      },
    {
        'edChannelId':'大客户渠道',sortable:true,
        'categoryId':'空调',sortable:true,
        'accRate':'0.90',sortable:true,
        'remark':'',sortable:true,
        'isEnabled':'禁用',sortable:true
    },
    {
        'edChannelId':'京东渠道',sortable:true,
        'categoryId':'洗衣机',sortable:true,
        'accRate':'0.60',sortable:true,
        'remark':'',sortable:true,
        'isEnabled':'禁用',sortable:true
    },
    {
        'edChannelId':'模卡渠道',sortable:true,
        'categoryId':'热水器',sortable:true,
        'accRate':'0.80',sortable:true,
        'remark':'',sortable:true,
        'isEnabled':'禁用',sortable:true
    },

],'totalCount':12}};

var datagridOptions_materialsAccRateGateT = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  //url: '/auth/materialsAccRateGateT/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
      {title: '状态', field: 'isEnabled',sortable:true,
          editor:{
              type:'combobox',
              options:{
                  valueField:'isEnabled',
                  textField:'name',
                  data:'[{"isEnabled":"是"},{"isEnabled":"是"}]',
                  required:true
              }
          }
      },
      {title: '渠道', field: 'edChannelId',sortable:true},
      {title: '品类', field: 'categoryId',sortable:true},
      {title: '备料准确率闸口', field: 'accRate',sortable:true},
      {title: '备注', field: 'remark',sortable:true}
  ]],
    onBeforeEdit:function(index,row){
        row.editing = true;
        updateActions(index);
    },
    onAfterEdit:function(index,row){
        row.editing = false;
        updateActions(index);
    },
    onCancelEdit:function(index,row){
        row.editing = false;
        updateActions(index);
    },
  toolbar: '#datagridToolbar_materialsAccRateGateT',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid_materialsAccRateGateT').datagrid(datagridOptions_materialsAccRateGateT);
  datagrid.datagrid('loadData',datagridData_materialsAccRateGateT);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"MaterialsAccRateGateT");
  //$("#searchPanel_materialsAccRateGateT").panel('resize');

  /*$("#searchBtn_materialsAccRateGateT").on('click', function (event) {
    var param = $('#paramForm_materialsAccRateGateT').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_materialsAccRateGateT").on('click', function (event) {
    $('#addForm_materialsAccRateGateT').form('reset');
    $('#addForm_materialsAccRateGateT').find('[__actType]').val('add');
    $('#addDlg_materialsAccRateGateT').dialog({'title':'新增'});
    $('#addDlg_materialsAccRateGateT').dialog('open');
  });
  $("#addDlgSaveBtn_materialsAccRateGateT").on('click', function () {
    if(!$('#addForm_materialsAccRateGateT').form('validate')){return;}
    var actType = $('#addForm_materialsAccRateGateT').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_materialsAccRateGateT').dialog('close');
  });
  $("#editBtn_materialsAccRateGateT").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_materialsAccRateGateT').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_materialsAccRateGateT').form('load', selected);
      $('#addForm_materialsAccRateGateT').find('[__actType]').val('edit');
      $('#addDlg_materialsAccRateGateT').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_materialsAccRateGateT").on('click',function (event) {
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
  $("#resetBtn_materialsAccRateGateT").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_materialsAccRateGateT').form('reset');
  });
});
