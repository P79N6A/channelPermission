
var datagridData_dataDictionary={'data':{'records':[
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
      {
          'valueSetId':'类别'+'100',sortable:true,
          'valueSetMeaning':'对应中文名称'+'100',sortable:true,
          'value':'常量代码'+'100',sortable:true,
          'valueMeaning':'常量值-中文'+'100',sortable:true,
          'orderFlag':'排序标志'+'100',sortable:true,
          'deleteFlag':'是否删除'+'100',sortable:true,
      },
],'total':10}};

var datagridOptions_dataDictionary = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  //url: '/auth/dataDictionary/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
        {title: '类别', field: 'valueSetId',sortable:true},
        {title: '对应中文名称', field: 'valueSetMeaning',sortable:true},
        {title: '常量代码', field: 'value',sortable:true},
        {title: '常量值-中文', field: 'valueMeaning',sortable:true},
        {title: '排序标志', field: 'orderFlag',sortable:true},
        {title: '是否删除', field: 'deleteFlag',sortable:true},
  ]],
  toolbar: '#datagridToolbar_dataDictionary',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid_dataDictionary').datagrid(datagridOptions_dataDictionary);
  datagrid.datagrid('loadData',datagridData_dataDictionary);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"DataDictionary");
  //$("#searchPanel_dataDictionary").panel('resize');

  /*$("#searchBtn_dataDictionary").on('click', function (event) {
    var param = $('#paramForm_dataDictionary').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_dataDictionary").on('click', function (event) {
    $('#addForm_dataDictionary').form('reset');
    $('#addForm_dataDictionary').find('[__actType]').val('add');
    $('#addDlg_dataDictionary').dialog({'title':'新增'});
    $('#addDlg_dataDictionary').dialog('open');
  });
  $("#addDlgSaveBtn_dataDictionary").on('click', function () {
    if(!$('#addForm_dataDictionary').form('validate')){return;}
    var actType = $('#addForm_dataDictionary').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_dataDictionary').dialog('close');
  });
  $("#editBtn_dataDictionary").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_dataDictionary').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_dataDictionary').form('load', selected);
      $('#addForm_dataDictionary').find('[__actType]').val('edit');
      $('#addDlg_dataDictionary').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_dataDictionary").on('click',function (event) {
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
  $("#resetBtn_dataDictionary").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_dataDictionary').form('reset');
  });
});
