$(function () {
  Date.prototype.format = function (format) {
    var o = {
      "M+": this.getMonth() + 1, // month
      "d+": this.getDate(), // day
      "h+": this.getHours(), // hour
      "m+": this.getMinutes(), // minute
      "s+": this.getSeconds(), // second
      "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
      "S": this.getMilliseconds()
      // millisecond
    }
    if (/(y+)/.test(format)) {
      format = format.replace(RegExp.$1, (this.getFullYear() + "")
      .substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
      if (new RegExp("(" + k + ")").test(format)) {
        format = format.replace(RegExp.$1,
            RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(
                ("" + o[k]).length));
      }
    }
    return format;
  }
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
      $("#month").combobox('readonly',false);
      $("#costDes").combobox('readonly',false);

      return $('#addDlg').dialog('open').dialog('setTitle', '添加');
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
	    if(data[0].value==null||data[0].value==""){
	    	data[0].value=0;
	    }
      if (!$("#ecologyShop").textbox('getValue')){
          $.messager.alert("提示信息", "请填写生态店！");
          return
      }if (!$("#year").textbox('getValue')){
          $.messager.alert("提示信息", "请选择年度！");
          return
      }if (!$("#month").textbox('getValue')){
          $.messager.alert("提示信息", "请选择期间！");
          return
      }if (!$("#brand").textbox('getValue')){
          $.messager.alert("提示信息", "请填写品牌！");
          return
      }if (!$("#costDes").textbox('getValue')){
          $.messager.alert("提示信息", "请填写费用描述！");
          return
      }if (!$("#industryS").textbox('getValue')==''&&$("#qtCostAmount").textbox('getValue')==''){
              $.messager.alert("提示信息", "其他费用要维护到产业！");
              return;
      }


    var url = "/SNOtherCost/editRecord"
    $.ajax({
      url: url,
      data: data,
      dataType: 'json',
      type: "post",
      success: function (res) {
        if (res.success) {
          alert("操作成功");
        } else {
          alert("数据异常");
        }
        dataGrid.datagrid("load")
        $('#addDlg').dialog('close');
      }
    })
  })

  $("#editBtn").on('click', function () {

    var selected = dataGrid.datagrid('getSelections');
    $('#addDlg').dialog({'title': i18n['message.act.edit']});
    if (selected.length == 1) {
        $('#ecologyShop').textbox('readonly',true);
        $('#brand').textbox('readonly',true);
        $("#type").combobox('readonly',true);
        $("#industryS").combobox('readonly',true);
        $("#year").combobox('readonly',true);
        $("#month").combobox('readonly',true);
        $("#costDes").combobox('readonly',true)

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
    var ids = ''
    for (var i in selected) {
      ids += selected[i].id + ','
    }
    $.messager.confirm("确认", "您确定要删除这些数据吗？", function (yes) {
      if (yes) {
        $.ajax({
          url: "/SNOtherCost/delete",
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
              url: '/SNOtherCost/audit',
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
              url: '/SNOtherCost/audit',
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
                        'flag':"2"},
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
  $("#importBtn").click(function () {
    $('#importExcel').dialog('open');
  });

    $("#calculate").click(function () {
        $('#actionEdit').dialog('open');
    });

  $("#upload").click(function () {
    if (!$("#file").val()) {
      $.messager.alert('提示信息', '请选择文件');
      return;
    }
    $("#upload").addClass("l-btn-disabled");
    $.ajaxFileUpload({
      url: '/SNOtherCost/import',
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

$("select[name=year2]").combobox({
    valueField: 'value',
    textField: 'label',
    data: yearData2
})

$("select[name=year]").combobox({
  valueField: 'value',
  textField: 'label',
  data: yearData
});


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
  window.location.href = "/SNOtherCost/export?" +
      'year=' + param.year
      + '&month=' + param.month
      + '&ecologyShop=' + param.ecologyShop
      + '&brand=' + param.brand
      + '&industry=' + param.industry
}
