
var datagridData_productLimitGateT={'data':{'records':[
      {
          'channelCode':'渠道编码'+'100',sortable:true,
          'groupType':'产品组'+'100',sortable:true,
          'productType':'品类'+'100',sortable:true,
          'modelType':'型号'+'100',sortable:true,
          'type':'类别  0 下市 1上单'+'100',sortable:true,
          'orderNum':'上单数'+'100',sortable:true,
          'startTime':'起始时间'+'100',sortable:true,
          'endTime':'截止时间'+'100',sortable:true,
          'createUser':'创建人'+'100',sortable:true,
          'createTime':'创建时间'+'100',sortable:true,
          'updateUser':'更新人'+'100',sortable:true,
          'updateTime':'更新时间'+'100',sortable:true,
      },
      {
          'channelCode':'渠道编码'+'100',sortable:true,
          'groupType':'产品组'+'100',sortable:true,
          'productType':'品类'+'100',sortable:true,
          'modelType':'型号'+'100',sortable:true,
          'type':'类别  0 下市 1上单'+'100',sortable:true,
          'orderNum':'上单数'+'100',sortable:true,
          'startTime':'起始时间'+'100',sortable:true,
          'endTime':'截止时间'+'100',sortable:true,
          'createUser':'创建人'+'100',sortable:true,
          'createTime':'创建时间'+'100',sortable:true,
          'updateUser':'更新人'+'100',sortable:true,
          'updateTime':'更新时间'+'100',sortable:true,
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
        {title: '渠道编码', field: 'channelCode',sortable:true},
        {title: '产品组', field: 'groupType',sortable:true},
        {title: '品类', field: 'productType',sortable:true},
        {title: '型号', field: 'modelType',sortable:true},
        {title: '类别  0 下市 1上单', field: 'type',sortable:true},
        {title: '上单数', field: 'orderNum',sortable:true},
        {title: '起始时间', field: 'startTime',sortable:true},
        {title: '截止时间', field: 'endTime',sortable:true},
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
