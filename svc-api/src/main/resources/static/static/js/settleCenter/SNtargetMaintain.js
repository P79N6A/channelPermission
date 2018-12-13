$(function () {

  $("#addBtn").on('click', function (event) {
      $('#addForm').form('reset');
      $('#addForm').find('[__actType]').val('add');
      $('#addDlg').dialog({'title': i18n['message.act.add']});

      $("#addForm").form("clear");
    $('#ecologyShop').textbox('readonly',false);
    $('#brand').textbox('readonly',false);
    $("#type").combobox('readonly',false);
    $("#industryS").combobox('readonly',false)
    $("#year").combobox('readonly',false)
    $("#month").combobox('readonly',false)

    return $('#addDlg').dialog('open').dialog('setTitle', '添加');
  });
  $('#searchBtn').click(function () {
    var queryParameter = dataGrid.datagrid("options").queryParams;
    var param = $("#paramForm").serializeArray();
    for (var o in param) {
      queryParameter[param[o].name] = param[o].value;
    }
    dataGrid.datagrid("load");
  });
    //弹出计算dialog
    $("#calculate").on('click', function () {
        $('#actionEdit').dialog({'title': '请选择年度,期间,类型'});
        $('#actionEdit').dialog('open');
        $('#toAction').linkbutton('enable');
    })

    //点击执行按钮
    $("#toAction").on('click', function () {

        if ($(this).linkbutton('options').disabled == true) {
            return;
        }
        var year = $("[name='year2']").val();
        var month = $("[name='month2']").val();
        var type = $("[name='type2']").val();
        var url = '/SNrebatesMonthlyDetail/actionToCreateData';

        $.messager.confirm('确认', "确认要执行"+year+"-"+month+"的数据吗？", function(r){
            if (r){
                $('#toAction').linkbutton('disable');
                $.messager.show({
                    title: '提示',
                    msg: '后台处理中请不要重复点击执行!',
                    timeout:0
                })
                $.ajax({
                    url: url,
                    data: {
                      'year': year,
                      'month': month,
                      'type': type,
                      'flag':"1"},
                    dataType: 'json',
                    type: "post",
                    success: function (res) {
                        if (res.success) {
                            $.messager.alert('提示信息', res.msg);
                            $('#actionEdit').dialog('close');
                        } else {
                            $.messager.alert('提示信息', res.msg);
                        }
                    }
                })
            }
        });
    })

  $("#addDlgSaveBtn").on('click', function () {
    var data = $("#addForm").serializeArray();
	    if(data[0].value==null||data[0].value==""){
	    	data[0].value=0;
	    }
      if (!$("#ecologyShop").textbox('getValue')){
          $.messager.alert("提示信息", "请填写生态店！");
          return
      }if (!$("#year").textbox('getValue')){
          $.messager.alert("提示信息", "请选择年度！");
          return
      }if (!$("#industryS").textbox('getValue')){
          $.messager.alert("提示信息", "请选择产业！");
          return
      }if (!$("#month").textbox('getValue')){
          $.messager.alert("提示信息", "请选择期间！");
          return
      }if (!$("#brand").textbox('getValue')){
          $.messager.alert("提示信息", "请填写品牌！");
          return
      }if (!$("#type").textbox('getValue')){
          $.messager.alert("提示信息", "请选择类型！");
          return
      }if (!$("#target").textbox('getValue')){
          $.messager.alert("提示信息", "请填写目标！");
          return
      }
    var url = "/SNTargetMaintain/editRecord"
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
        $("#addForm").form("clear");
        $('#ecologyShop').textbox('readonly',true);
        $('#brand').textbox('readonly',true);
        $("#type").combobox('readonly',true);
        $("#industryS").combobox('readonly',true)
        $("#year").combobox('readonly',true)
        $("#month").combobox('readonly',true)
      var row = selected[0]
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
    if (selected.length>20){
      $.messager.alert("提示信息", "删除条数最多不要超过20条！");
      return;
    }

    var ids = ''
    var result=''
    for (var i in selected) {
        if(selected[i].deleteTab=='Y'){
            result+="第"+(parseInt(i)+1)+"行已删除不能重复删除<br>"
        }
      ids += selected[i].id + ','
    }
      if (!result == ''){
          $.messager.alert("提示信息", result);
          return;
        }
    $.messager.confirm("确认", "您确定要删除这些数据吗？", function (yes) {
      if (yes) {
        $.ajax({
          url: "/SNTargetMaintain/delete",
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
          if(rows[k].deleteTab=='Y'){
              result += "不能通过审核:第" + (parseInt(k) + 1) + "条数据已被删除<br>";
              continue;
        }
        if (rows[k].auditState == 'F') {
            result += "不能通过审核:第" + (parseInt(k) + 1) + "条数据待财务审核<br>";
            continue;
        }
        if (rows[k].auditState == 'A') {
          result += "不能通过审核:第" + (parseInt(k) + 1) + "条数据审核已通过<br>";
          continue;
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
              url: '/SNTargetMaintain/audit',
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
          if(rows[k].deleteTab=='Y'){
              result += "不能通过审核:第" + (parseInt(k) + 1) + "条数据已被删除<br>";
              continue;
          }
        if (rows[k].auditState == 'A') {
          result += "不能通过审核:第" + (parseInt(k) + 1) + "条数据审核已通过<br>";
          continue
        }
        if (rows[k].auditState == 'B') {
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
              url: '/SNTargetMaintain/audit',
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
      url: '/SNTargetMaintain/import',
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

var yearData2 = (function () {
    var cd = new Date();
    var now = cd.getFullYear()
    var prev = now - 1
    var next = now + 1
    return [{
        label: prev + "年",
        value: prev
    }, {
        label: now + "年",
        value: now,
        selected:true
    }, {
        label: next + "年",
        value: next
    }]
})()

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

$("select[name=year2]").combobox({
    valueField: 'value',
    textField: 'label',
    data: yearData2
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
  window.location.href = "/SNTargetMaintain/export?" +
      'year=' + param.year
      + '&month=' + param.month
      + '&ecologyShop=' + param.ecologyShop
      + '&industry=' + param.industry
      + '&deleteTab=' + param.deleteTab
      + '&auditState=' + param.auditState
      + '&type=' + param.type
}
