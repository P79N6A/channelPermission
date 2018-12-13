$(function () {
  $("#addBtn").on('click', function (event) {
    $('#addForm').form('reset');
    $('#addForm').find('[__actType]').val('add');
    $('#addDlg').dialog({'title': i18n['message.act.add']});
    $('#addDlg').dialog('open');
  });

});

