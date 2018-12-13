$(function () {
  $("#addBtn").on('click', function (event) {
    $('#addForm').form('reset');
    $('#addForm').find('[__actType]').val('add');
    $('#addDlg').dialog({'title': i18n['message.act.add']});
    $('#addDlg').dialog('open');
  });
  $('#searchBtn').click(function () {
    var queryParameter = dataGrid.datagrid("options").queryParams;
    var param = $("#paramForm").serializeArray();
    for (var o in param) {
      queryParameter[param[o].name] = param[o].value;
    }
    // param.push({name: "pageIndex", value: options.pageNumber},
    //     {name: "pageSize", value: options.pageSize})
    dataGrid.datagrid("load");
    // $.ajax({
    //   url: '/woUser/getList',
    //   data: param,
    //   type:'get',
    //   dataType: 'json',
    //   success: function (res) {
    //     dataGrid.datagrid("loadData", res);
    //   }
    // })
  });

  $("#addDlgSaveBtn").on('click', function () {
    var data = $("#addForm").serializeArray();
      if(data[2].value.replace(/\s+/g,"")==''){
          alert("姓名不能为空");
          return;
      }
      if(data[3].value.replace(/\s+/g,"")==''){
          alert("电话不能为空");
          return;
      }
      if(data[4].value.replace(/\s+/g,"")==''){
          alert("邮件不能为空");
          return;
      }
    var url = "/woUser/" + $('#addForm').find('[__actType]').val() + "User";
    $.ajax({
      url: url,
      data: data,
      dataType: 'json',
      type: "post",
      success: function (res) {
        if (res.success) {
          alert("操作成功")
        } else {
          alert("数据异常")
        }
        dataGrid.datagrid("reload")
        $('#addDlg').dialog('close')
      }
    })
  })

  $("#editBtn").on('click', function () {
    var selected = dataGrid.datagrid('getSelections');
    $('#addDlg').dialog({'title': i18n['message.act.edit']});
    if (selected.length == 1) {
      $('#addForm').form('load', selected[0]);
      $('#addForm').find('[__actType]').val('edit');
      $('#addDlg').dialog('open');
    } else {
      alert("请选择一条数据");
    }
  }).error(function (errorObj, statusText) {
    alert(statusText);
  });

    $("#resetBtn_orderOp").on('click', function () {
        $('#search-userName').textbox('setValue','');
        $('#search-mobile').textbox('setValue','');
        $('#search-email').textbox('setValue','');
        $('#company_search').textbox('setValue','');
        $('#userType').combobox('setValue', "").combobox('setText', '-请选择-');
        $('#sendEmail').combobox('setValue', "").combobox('setText', '-请选择-');

    });

  $("#delBtn").click(function () {
    var selected = dataGrid.datagrid('getSelections');
    var userIds = []
    if (selected.length < 1) {
      alert("请至少选择一条数据")
      return;
    }
    for (var i in selected) {
      userIds.push(selected[i].userId);
    }

    $.messager.confirm("确认", "您确定要删除这些数据吗？", function (yes) {
      if (yes) {
        $.ajax({
          url: "/woUser/delUser",
          data: {userIds: JSON.stringify(userIds)},
          dataType: 'json',
          type: 'post',
          success: function (res) {
            if (res.success) {
              alert("操作成功")
            } else {
              alert("数据异常")
            }
            dataGrid.datagrid("reload")
          }
        })
      }
    })

  })

});

var WORK_ORDER_EXCEL = {
  'Q_username_search': null,
  'Q_phone_search': null,
  'Q_email_search': null,
  'Q_type_search': null,
  'Q_workStatus': null,
  'Q_issend_search': null
};

function exp() {
  var data = $('#dg').datagrid('getData');
  if (data.rows.length <= 0) {
    $.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
    return;
  }
  //var selectedvalue=$("input[name='tab']:checked").val();
  var temp = $("#paramForm").serializeArray();
  var param = {}
  for (var o in temp) {
    param[temp[o].name] = temp[o].value;
  }
  window.location.href = "/export/export4.html?" +
      'username_search=' + param.userName
      + '&phone_search=' + param.mobile
      + '&email_search=' + param.email
      + '&type_search=' + param.userType
      + '&issend_search=' + param.sendEmail

}
