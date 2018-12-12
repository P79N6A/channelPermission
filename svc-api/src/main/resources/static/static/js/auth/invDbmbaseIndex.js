
var datagridData_invDbmbase={'data':{'records':[
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      },
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      },
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      },
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      },
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      },
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      },
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      },
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      },
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      },
      {
          'estorgeId':'AKWA',sortable:true,
          'baseCode':'AK',sortable:true,
          'baseName':'日日顺安康N库',sortable:true,
          'transportPrescription':'2',sortable:true,
          'createUser':'管理员',sortable:true,
          'createTime':'1900-01-01 00:00:00.0',sortable:true,
          'updateUser':'管理员',sortable:true,
          'updateTime':'2014-12-19 10:53:57.0',sortable:true,
          'isEnabled':'启用',sortable:true
      }

],'totalCount':10}};

var datagridOptions_invDbmbase = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  //url: '/auth/invDbmbase/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
        {title: '电商库位码', field: 'estorgeId',sortable:true},
        {title: '发运基地码', field: 'baseCode',sortable:true},
        {title: '发运基地名称', field: 'baseName',sortable:true},
        {title: '运输周期', field: 'transportPrescription',sortable:true},
        {title: '创建者', field: 'createUser',sortable:true},
        {title: '创建时间', field: 'createTime',sortable:true},
        {title: '最后更新人', field: 'updateUser',sortable:true},
        {title: '最后更新时间', field: 'updateTime',sortable:true},
        {title: '状态', field: 'isEnabled',sortable:true}

  ]],
  toolbar: '#datagridToolbar_invDbmbase',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid_invDbmbase').datagrid(datagridOptions_invDbmbase);
  datagrid.datagrid('loadData',datagridData_invDbmbase);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"InvDbmbase");
  //$("#searchPanel_invDbmbase").panel('resize');

  /*$("#searchBtn_invDbmbase").on('click', function (event) {
    var param = $('#paramForm_invDbmbase').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_invDbmbase").on('click', function (event) {
    $('#addForm_invDbmbase').form('reset');
    $('#addForm_invDbmbase').find('[__actType]').val('add');
    $('#addDlg_invDbmbase').dialog({'title':'新增'});
    $('#addDlg_invDbmbase').dialog('open');
  });
  $("#addDlgSaveBtn_invDbmbase").on('click', function () {
    if(!$('#addForm_invDbmbase').form('validate')){return;}
    var actType = $('#addForm_invDbmbase').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_invDbmbase').dialog('close');
  });
  $("#editBtn_invDbmbase").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_invDbmbase').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_invDbmbase').form('load', selected);
      $('#addForm_invDbmbase').find('[__actType]').val('edit');
      $('#addDlg_invDbmbase').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_invDbmbase").on('click',function (event) {
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
  $("#resetBtn_invDbmbase").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_invDbmbase').form('reset');
  });
});
