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
    dataGrid.datagrid("load");
  });

  $("#addDlgSaveBtn").on('click', function () {
    var data = $("#addForm").serializeArray();
    var url = "/scTargetMaintain/editRecord"
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
        dataGrid.datagrid("load")
        $('#addDlg').dialog('close')
      }
    })
  })

  $("#editBtn").on('click', function () {
    var selected = dataGrid.datagrid('getSelections');
    $('#addDlg').dialog({'title': i18n['message.act.edit']});
    if (selected.length == 1) {
      var row = selected[0]
      if (row.firstAudit == 'A') {
        $.messager.alert('提示信息', '该数据审核已通过不能修改!')
        return
      }
      $('#addForm').form('load', selected[0]);
      $('#addForm').find('[__actType]').val('edit');
      $('#addDlg').dialog('open');
    } else {
      alert("请选择一条数据");
    }
  }).error(function (errorObj, statusText) {
    alert(statusText);
  });

  $("#delBtn").click(function () {
    var selected = dataGrid.datagrid('getSelections');
    var ids = []
    if (selected.length < 1) {
      alert("请至少选择一条数据")
      return;
    }
    var ids = ''
    for (var i in selected) {
      ids += selected[i].id + ','
    }
    $.messager.confirm("确认", "您确定要删除这些数据吗？", function (yes) {
      if (yes) {
        $.ajax({
          url: "/scTargetMaintain/delete",
          data: {ids: ids},
          dataType: 'json',
          type: 'post',
          success: function (res) {
            if (res.success) {
              alert("操作成功")
            } else {
              alert("数据异常")
            }
            dataGrid.datagrid("load")
          }
        })
      }
    })
  })

  $("#businessAudit").click(function () {
    var rows = dataGrid.datagrid('getSelections')
    if (rows.length > 0) {
      var ids = '';
      var result = '';
      for (var k in rows) {
        if (rows[k].firstAudit == 'A') {
          result += "不能通过审核:第" + (parseInt(k) + 1) + "条数据初审已通过<br>";
          continue
        }
        ids += rows[k].id + ",";
      }
      if (result) {
        $.messager.alert('提示信息', result)
        return
      } else if (ids) {
        $.messager.confirm("确认", "确认要审核吗?", function (r) {
          if (r) {
            $.ajax({
              url: '/scTargetMaintain/audit',
              data: {ids: ids, audit: 'B'},
              type: 'post',
              dataType: 'json',
              success: function (res) {
                if (res.success) {
                  $.messager.alert('提示信息', res.result)
                  dataGrid.datagrid('load')
                } else {
                  $.messager.alert('提示信息', res.message)
                }
              }
            })
          }
        })
      }
    } else {
      $.messager.alert('提示信息', '请选择数据!')
      return
    }
  })

  $("#financeAudit").click(function () {
    var rows = dataGrid.datagrid('getSelections')
    if (rows.length > 0) {
      var ids = '';
      var result = '';
      for (var k in rows) {
        if (rows[k].secondAudit == 'A') {
          result += "不能通过审核:第" + (parseInt(k) + 1) + "条数据复审已通过<br>";
          continue
        }
        if (rows[k].firstAudit == 'S') {
          result += "不能通过审核:第" + (parseInt(k) + 1) + "条数据待业务初审<br>";
          continue
        }
        ids += rows[k].id + ",";
      }
      if (result) {
        $.messager.alert('提示信息', result)
        return
      } else if (ids) {
        $.messager.confirm("确认", "确认要审核吗?", function (r) {
          if (r) {
            $.ajax({
              url: '/scTargetMaintain/audit',
              data: {ids: ids, audit: 'F'},
              type: 'post',
              dataType: 'json',
              success: function (res) {
                if (res.success) {
                  $.messager.alert('提示信息', res.result)
                  dataGrid.datagrid('load')
                } else {
                  $.messager.alert('提示信息', res.message)
                }
              }
            })
          }
        })
      }
    } else {
      $.messager.alert('提示信息', '请选择数据!')
      return
    }
  })

  $("#importBtn").click(function () {
    $('#importExcel').dialog('open')
  })
  $("#upload").click(function () {
    if (!$("#file").val()) {
      $.messager.alert('提示信息', '请选择文件');
      return;
    }
    $("#upload").addClass("l-btn-disabled");
    $.ajaxFileUpload({
      url: '/scTargetMaintain/import',
      secureuri: false,
      fileElementId: 'file',
      dataType: 'json',
      success: function (data) {
        $("#upload").removeClass(
            "l-btn-disabled")
        if (data.success) {
          $.messager.alert('提示信息', '上传成功!')
          $('#importExcel').dialog('close');
          dataGrid.datagrid('load')
        } else {
          var msg = "";
          var msgs = JSON.parse(data.message)
          for (var k in msgs) {
            msg += msgs[k] + '<br/>';
          }
          $.messager.alert('提示信息', '上传失败!<br/>' + msg)
        }
      },
      error: function (a, b, c, d) {
        $("#upload").removeClass("l-btn-disabled")
        $.messager.alert('提示信息', '上传失败!' + a.responseText)
      }
    })
  })
})

var yearData = (function () {
  var cd = new Date();
  var now = cd.getFullYear()
  var prev = now - 1
  var next = now + 1
  return [{
    label: prev + "年",
    value: prev
  }, {
    label: now + "年",
    value: now
  }, {
    label: next + "年",
    value: next
  }]
})()

$("select[name=year]").combobox({
  valueField: 'value',
  textField: 'label',
  data: yearData
})

function exp() {
  var data = $('#dg').datagrid('getData');
  if (data.rows.length <= 0) {
    $.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
    return;
  }
  var temp = $("#paramForm").serializeArray();
  var param = {}
  for (var o in temp) {
    param[temp[o].name] = temp[o].value;
  }
  window.location.href = "/scRebatesSummary/industrySummaryExport?" +
      'year=' + param.year
      + '&month=' + param.month
      + '&industry=' + param.industry
}
