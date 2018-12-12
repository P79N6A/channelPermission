/**
 * 用户管理.
 * Created by Magp on 2017/4/26.
 */
var datagridOptions = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  url: '/user/list',
  frozenColumns: [[{
    field: 'id', checkbox: true
  },
    {title: '用户名', field: 'userName'}]],
  columns: [[
    {title: '登录帐号', field: 'loginAccount'},
    {title: 'email', field: 'email'},
    {title: '座机', field: 'phone'},
    {title: '创建时间', field: 'createDate'},
    {title: '修改时间', field: 'lastUpdDate'},
    {title: '修改人', field: 'lastUpdBy'},
    {title: '上次登录时间', field: 'lastLoginDate'},
    {title: '手机', field: 'mobile'}
  ]],
  toolbar: '#datagridToolbar',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid').datagrid(datagridOptions);
  $("#searchPanel").panel('resize');
  $("#searchBtn").on('click', function (event) {
    var param = $('#paramForm').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });
  $("#addBtn").on('click', function (event) {
    $('#addForm').form('clear');
    $('#addForm').find('[__actType]').val('add');
    $('#addDlg').dialog('open');
  });
  $("#addDlgSaveBtn").on('click', function () {
    $.post('/user/save', $('#addForm').serializeObject()).success(
        function (res) {
          var actType = $('#addForm').find('[__actType]').val();
          var actText = actType === 'add' ? '添加' : '修改';
          if (res.success) {
            alert(actText + '成功');
            $('#addDlg').dialog('close');
          } else {
            alert(actText + '失败');
          }
        }).error(function (errorObj, statusText) {
      alert(statusText);
    });
  });
  $("#editBtn").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    if (selected !== null) {
      $.post('/user/getone', {id: selected.id}).success(function (res) {
        $('#addForm').form('load', res);
        $('#addForm').find('[__actType]').val('edit');
        $('#addDlg').dialog('open');
      });
    } else {
      alert('请选择一条数据');
    }
  }).error(function (errorObj, statusText) {
    alert(statusText);
  });
});
