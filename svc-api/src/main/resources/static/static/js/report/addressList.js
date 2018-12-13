columns1 = [];
columns1 = [[{
  'field': "ordercome",
  'title': "订单来源",
  'width': "250"
}, {
  'field': "question1level1",
  'title': "责任位一",
  'width': "100",
  "formatter": "productFormatter",
  "type": "combobox"
}, {
  'field': "question1level2",
  'title': "责任位二",
  'width': "130"
}, {
  'field': "company",
  'title': "工贸",
  'width': "180"
},

  {
    'field': "id",
    "hidden": "true",
    'title': "id",
    'width': "100"
  }

// <th field="question1level1" width="100" formatter="productFormatter"
// editor="{type:'combobox',options:{valueField:'value',textField:'valueMeaning',data:products,required:true}}">责任位一</th>
// <th field="question1level2" width="100" formatter="productFormatter"
// editor="{type:'combobox',options:{valueField:'value',textField:'valueMeaning',data:products2}}">责任位二</th>
// <th field="company" width="100" formatter="productFormatter"
// editor="{type:'combobox',options:{valueField:'value',textField:'valueMeaning',data:products3}}">工贸</th>
// <th field="ordercome" width="100" formatter="productFormatter"
// editor="{type:'combobox',options:{valueField:'value',textField:'valueMeaning',data:products4}}">订单来源</th>
// <th field="attr1" width="250" editor="text">其他</th>
// <th field="status" width="60" align="center"
// editor="{type:'checkbox',options:{on:'P',off:''}}">Status</th>
]];
var ordersource = [];
// 导出工单EXCEL参数
var WORK_ORDER_EXCEL = {
  'Q_question1level1_search': null,
  'Q_question1level2_search': null,
  'Q_ordercome_search': null,
  'Q_username_search': null,
  'Q_manageruserId1_search': null,
  'Q_manageruserId2_search': null,
  'Q_company_search': null
};
// 获取工贸
$(function () {
  $.ajax({
    type: "get",
    url: "/dictionary/getdictionary.ajax",
    data: {
      "value_set_id": "company"
    },
    dataType: "json",
    success: function (data) {
      a = [];
      a.value = "";
      a.valueMeaning = "-请选择-";
      data.unshift(a);
      $('#company').combobox('loadData', data);
    }
  });
});
// 窗口工贸
$('#company').combobox({
  valueField: 'value',
  textField: 'valueMeaning',
  onLoadSuccess: function (data) {
    $('#company').combobox('setValue', "").combobox('setText', '-请选择-');
  },
});
// 获取责任位
$(function () {
  $.ajax({
    type: "get",
    url: "/dictionary/getdictionary.ajax",
    data: {
      "value_set_id": "duty_1"
    },
    dataType: "json",
    success: function (data) {
      // 复制对象
      var tmp = clone(data);
      for (var i = 0; i < tmp.length; i++) {
        tmp[i].value = tmp[i].valueMeaning;
      }
      a = [];
      a.value = "";
      a.valueMeaning = "-请选择-";
      data.unshift(a);
      $('#question1level1_search').combobox('loadData', data);
      products = data;
      $('#question1level1').combobox('loadData', data);
      b = [];
      b.value = "";
      b.valueMeaning = "-请选择-";
      tmp.unshift(b);
      products5 = tmp;
    }
  });
});
// 添加/修改责任位1
$('#question1level1').combobox(
    {
      valueField: 'value',
      textField: 'valueMeaning',
      onLoadSuccess: function (param) {
        $('#question1level1').combobox('setValue', "").combobox(
            'setText', '-请选择-');

      },
      onSelect: function (record) {

        if ($('#question1level1').combobox('getText') == '物流类') {
          $("#zrwz").hide();
          $("#question1level2 + .combo").hide();
          $("#ddly").hide();
          $("#ordercome + .combo").hide();
          $("#company + .combo").show();
          $("#gm").show();
          $("#qt").hide();
          $("#qtd").hide();
        } else if ($('#question1level1').combobox('getText') == '发票类') {
          $("#zrwz").hide();
          $("#question1level2 + .combo").hide();
          $("#ddly").hide();
          $("#ordercome + .combo").hide();
          $("#company + .combo").hide();
          $("#gm").hide();
          $("#qt").hide();
          $("#qtd").hide();
        } else {
          $("#zrwz").show();
          $("#question1level2 + .combo").show();
          $("#ddly").show();
          $("#ordercome + .combo").show();
          $("#company + .combo").hide();
          $("#gm").hide();
          // 如果非物料与发票,并且订单来源是其他
          if ($('#ordercome').combobox('getText') == '其他') {
            $("#qt").show();
            $("#qtd").show();
          }
        }

        var n = record.value;
        $.ajax({
          type: "GET",
          url: "/dictionary/gettwomenu.ajax",
          data: {
            "value_set_id": "duty_2",
            "parent_value": n
          },
          dataType: "json",
          success: function (data) {
            a = [];
            a.value = "";
            a.valueMeaning = "-请选择-";
            data.unshift(a);
            $('#question1level2').combobox('loadData', data);

          }
        });
        $('#question1level2').combobox({
          valueField: 'value',
          textField: 'valueMeaning',
          onLoadSuccess: function (data) {
            $('#question1level2').combobox('setText', '-请选择-');
          }
        });
      }
    });
// 界面责任位1
$('#question1level1_search').combobox(
    {
      valueField: 'value',
      textField: 'valueMeaning',
      onLoadSuccess: function (data) {
        $('#question1level1_search').combobox('setValue', "")
        .combobox('setText', '-请选择-');
        $('#question1level1_search').combobox('setValue', "")
        .combobox('setText', '-请选择-');
      },
      onSelect: function (record) {
        if (record.valueMeaning == '-请选择-') {
          $('#question1level2_search').combobox('setValue', '');
          $('#question1level2_search').combobox('setText', '');
          $('#question1level2_search').combobox('loadData',{});
          $('#question1level2_search').combobox('setText', '-请选择-');
          return;
        }
        var n = record.value;
        $.ajax({
          type: "GET",
          url: "/dictionary/gettwomenu.ajax",
          data: {
            "value_set_id": "duty_2",
            "parent_value": n
          },
          dataType: "json",
          success: function (data) {
            a = [];
            a.value = "";
            a.valueMeaning = "-请选择-";
            data.unshift(a);
            $('#question1level2_search').combobox('loadData', data);
          }
        });
        $('#question1level2_search').combobox(
            {
              valueField: 'value',
              textField: 'valueMeaning',
              onLoadSuccess: function (data) {
                if (data.length==1) {
                  $('#question1level2_search').combobox('setText', '');
                } else {
                  $('#question1level2_search').combobox('setText', '-请选择-');
                }

              }
            });
      }
    });

// 获取订单来源
$(function () {
  $.ajax({
    type: "GET",
    url: "/dictionary/getdictionary.ajax",
    data: {
      "value_set_id": "order_source"
    },
    dataType: "json",
    success: function (data) {
      a = [];
      a.value = "";
      a.valueMeaning = "-请选择-";
      data.unshift(a);
      // b=[];
      // b.value = "qt";
      // b.valueMeaning = "其他";
      // data.push(b);
      $('#ordercome_search').combobox('loadData', data);
      $('#ordercome').combobox('loadData', data);
      ordersource = data;
    }
  });
});
// 订单来源（一览的页
$('#ordercome_search').combobox(
    {
      valueField: 'value',
      textField: 'valueMeaning',
      onLoadSuccess: function (data) {
        $('#ordercome_search').combobox('setValue', "").combobox(
            'setText', '-请选择-');
      },
    });
// 订单来源（弹出页
$('#ordercome').combobox({
  valueField: 'value',
  textField: 'valueMeaning',
  onLoadSuccess: function (data) {
    $('#ordercome').combobox('setValue', "").combobox('setText', '-请选择-');
  },
  onSelect: function (data) {
    if (data.valueMeaning == '其他') {
      $("#qt").show();
      $("#qtd").show();
    } else {
      $("#qt").hide();
      $("#qtd").hide();
    }
  },
});
// 获取人员
$(function () {
  $.ajax({
    type: "get",
    url: "/woUser/getuser.ajax",
    data: {
      "type": "0"
      // "value_set_id" : "1"
    },
    dataType: "json",
    success: function (data) {
      $('#username').combobox('loadData', data);
    }
  });

});
userText = "";
userValue = "";
// 设置人员
$('#username').combobox(
    {
      valueField: 'userId',
      textField: 'userName',
      onSelect: function (rec) {
        if (obj.editRow != undefined) {
          $('#username').combobox("setText", userText).combobox(
              "setValue", userValue);
          $.messager.alert('系统提示', "请先保存当前处于编辑状态下的数据!", 'warning');
          return;
        }
        userText = rec.userName;
        userValue = rec.userId;
        $("#phone").val(rec.mobile);// 填充内容
        $("#email").val(rec.email);// 填充内容
        var id = rec.userId;
        findDg3(id);
        $.ajax({
          type: "get",
          url: "/woUser/getuser.ajax",
          data: {
            "value_set_id": id
          },
          dataType: "json",
          success: function (data) {
            if (data[0].phone1) {
              $("#phone1").val(data[0].phone1);// 填充内容
            } else {
              $("#phone1").val("");// 填充内容
            }
            if (data[0].email1) {
              $("#email1").val(data[0].email1);// 填充内容
            } else {
              $("#email1").val("");// 填充内容
            }
            if (data[0].phone2) {
              $("#phone2").val(data[0].phone2);// 填充内容
            } else {
              $("#phone2").val("");// 填充内容
            }
            if (data[0].email2) {
              $("#email2").val(data[0].email2);// 填充内容
            } else {
              $("#email2").val("");// 填充内容
            }
            $('#manageruserid1').combobox('setText',
                data[0].username1).combobox('setValue',
                data[0].id1);
            $('#manageruserid2').combobox('setText',
                data[0].username2).combobox('setValue',
                data[0].id2);
          }
        });

      }
    });
// 获取一级领导
$(function () {
  // 获取一级领导
  $.ajax({
    type: "get",
    url: "/woUser/getuser.ajax",
    data: {
      "type": "1"
    },
    dataType: "json",
    success: function (data) {
      // 复制对象
      // var a = [];
      // a.id = "";
      // a.username = "-请选择-";
      // data.unshift(a);
      $('#manageruserid1').combobox('loadData', data);
    }
  });
  // 获取二级领导
  $.ajax({
    type: "get",
    url: "/woUser/getuser.ajax",
    data: {
      "type": "2"
    },
    dataType: "json",
    success: function (data) {
      // 复制对象
      // var a = [];
      // a.id = "";
      // a.username = "-请选择-";
      // data.unshift(a);
      $('#manageruserid2').combobox('loadData', data);
    },
  });
});
// 设置一级领导
$('#manageruserid1').combobox({
  valueField: 'userId',
  textField: 'userName',
  onSelect: function (rec) {
    $("#phone1").val(rec.mobile);// 填充内容
    $("#email1").val(rec.email);// 填充内容
  }
});
// 设置2级领导
$('#manageruserid2').combobox({
  valueField: 'userId',
  textField: 'userName',
  onSelect: function (rec) {
    $("#phone2").val(rec.mobile);// 填充内容
    $("#email2").val(rec.email);// 填充内容
  }
});

function status_format(val, row, index) {
  var value = row.workStatus;
  if (value == 0) {
    value = '未处理';
  } else if (value == 1) {
    value = '已确认';
  } else if (value == 2) {
    value = '中间结果';
  } else if (value == 3) {
    value = '最终结果';
  } else {
    value = '';
  }
  // return signformatter(value, row);
}

// 工单分页查询
function searchReview() {
  //
  setFirstPage("#dg");
  WORK_ORDER_EXCEL.Q_question1level1_search = $('#question1level1_search')
  .combobox('getText') != '-请选择-' ? $('#question1level1_search')
  .combobox('getText') : "";
  WORK_ORDER_EXCEL.Q_question1level2_search = $('#question1level2_search')
  .combobox('getText') != '-请选择-' ? $('#question1level2_search')
  .combobox('getText') : "";
  WORK_ORDER_EXCEL.Q_ordercome_search = $('#ordercome_search').combobox(
      'getText') != '-请选择-' ? $('#ordercome_search').combobox('getText')
      : "";
  WORK_ORDER_EXCEL.Q_username_search = $('#username_search').val();
  WORK_ORDER_EXCEL.Q_manageruserId1_search = $('#manageruserId1_search')
  .val();
  WORK_ORDER_EXCEL.Q_manageruserId2_search = $('#manageruserId2_search')
  .val();
  WORK_ORDER_EXCEL.Q_company_search = $('#company_search').val();
  $('#dg')
  .datagrid(
      {
        url: '/contacts/reviewcontactspage.ajax',
        queryParams: {
          'question1level1_search': $(
              '#question1level1_search').combobox(
              'getText') != '-请选择-' ? $(
              '#question1level1_search').combobox(
              'getText') : "",
          'question1level2_search': $(
              '#question1level2_search').combobox(
              'getText') != '-请选择-' ? $(
              '#question1level2_search').combobox(
              'getText') : "",
          'ordercome_search': $('#ordercome_search')
          .combobox('getText') != '-请选择-' ? $(
              '#ordercome_search').combobox('getText')
              : "",
          'username_search': $('#username_search').val(),
          'manageruserId1_search': $(
              '#manageruserId1_search').val(),
          'manageruserId2_search': $(
              '#manageruserId2_search').val(),
          'company_search': $('#company_search').val(),
        },
        loadMsg: '数据装载中......',
        onLoadSuccess: function (data) {

          MergeCells('dg', 'username,username1,username2');
        }

      });
}

// 用来临时存放添加或修改工单时的地址
// 添加人员
function newBackList() {
  $('#fm').form('clear');
  // 初始化
  // 清空列表
  $('#dg3').datagrid('loadData', {
    total: 0,
    rows: []
  });
  $('#dg3').datagrid({
    url: '',
  });
  $('#dlg').dialog('open').dialog('setTitle', '人员责任配置');
  init();
}

var QJ_DATAVALUE;

// 查看
function viewPool() {
  var rows = $('#dg').datagrid('getSelections');
  if (rows.length == 1) {
    var row = $('#dg').datagrid('getSelected');
    $('#dlg').dialog('open').dialog('setTitle', '人员责任配置');
    $('#id').val(row.id);
    row.company = null;
    row.ordercome = null;
    row.question1level1 = null;
    row.question1level2 = null;
    $('#fm').form('load', row);
    $('#username').combobox('select',row.userid);
    $('#manageruserid1').combobox('select',row.manageruserid1);
    $('#manageruserid2').combobox('select',row.manageruserid2);
    init();
    findDg3(row.userid);
  } else if (rows.length == 0) {
    $.messager.alert('系统提示', '请选择要操作的数据', 'warning');
  } else if (rows.length > 1) {
    $.messager.alert('系统提示', '请选择一条数据', 'warning');
  }
}

// 添加/删除工单信息
function saveBackList() {
  if (!$('#fm').form('validate')) {
    return;
  }
  if (obj.editRow != undefined) {
    $.messager.alert('系统提示', "请先保存当前处于编辑状态下的数据!", 'warning');
    return;
  }
  dataV = serializeObject($('#fm'));
  if (dataV.userid != undefined) {
    dataV.userid = $("#username").combobox('getValue');
  }
  dataV.id = $("#id").val();
  if (isObjectValueEqual(QJ_DATAVALUE, dataV)) {
    $('#dlg').dialog('close');
    return;
  }
  $.ajax({
    type: "post",
    url: '/contacts/updatemanageruserid.ajax',
    data: dataV,
    dataType: "json",
    async: false,
    success: function (data) {
      if (data) {
        $('#dlg').dialog('close');
        $('#dg').datagrid('reload');
      } else {
        $.messager.alert('系统提示', data.message, 'warning');
      }
    }
  });
}

// 表单选中后事件
function onClickCell(index, field) {
  return;
}

// 将form表单内的所有提交项转成对象
function serializeObject(form) {
  var o = {};
  $.each(form.serializeArray(), function (index) {
    if (o[this['name']]) {
      o[this['name']] = o[this['name']] + "," + this['value'];
    } else {
      o[this['name']] = this['value'];
    }
  });
  return o;
};

// 责任位
function addTr(tab, row, trHtml) {
  // 获取table最后一行 $("#tab tr:last")
  // 获取table第一行 $("#tab tr").eq(0)
  // 获取table倒数第二行 $("#tab tr").eq(-2)
  var $p = $("#" + tab + " p").eq(row);
  if ($p.size() == 0) {
    alert("指定的table id或行数不存在！");
    return;
  }
  $p.after(trHtml);
}

// 增加责任位
function addTr2(dataValue) {

  // if (!$('#fm').form('validate')) {
  // return;
  // }
  // $('#ordercome').combobox('getText') != '-请选择-' ?
  // $('#ordercome').combobox('getText') : "";
  dataV = serializeObject($('#fm'));
  var selRows = $("#box").datagrid("getData");
  dataV.id = selRows.rows[obj.editRow].id;
  // 责任位1
  dataV.question1level1 = dataValue.question1level1 != "-请选择-"
      ? dataValue.question1level1
      : "";

  // 责任位2
  dataV.question1level2 = dataValue.question1level2;
  dataV.userid = $("#username").combobox('getValue');
  // 订单来源
  dataV.ordercome = dataValue.ordercome;
  // 工贸
  dataV.company = dataValue.company;
  // 其他
  dataV.qtd = dataValue.qtd;
  if (!dataV.ordercome || dataV.ordercome == "") {
    $.messager.alert('系统提示', "订单来源不能为空!", 'warning');
    return;
  }
  $.ajax({
    type: "post",
    url: '/contacts/findinsert.ajax',
    data: dataV,
    dataType: "json",
    async: false,
    success: function (data) {
      if (data) {
        findDg3(null);
        // init();
        $('#save,#redo').hide();
        $('#box').datagrid('loaded');
        // $('#box').datagrid('load');
        $('#box').datagrid('unselectAll');
        $('#box').datagrid('selectRow', obj.editRow);
        obj.editRow = undefined;
        $('#box').datagrid('hideColumn', 'qtd');
      } else {
        $('#box').datagrid('beginEdit', obj.editRow);
        sysLy(getEditRow(obj.editRow, 'ordercome'), obj.editRow);
        $.messager.alert('系统提示', data.message);
      }
    }
  });

}

// 窗口通过人员查询对应责任位
function findDg3(userid) {
  if (userid != null) {
    queryParams = {
      'userId': userid
    };
  } else {
    queryParams = {
      'userId': $('#username').combobox('getValue')
    };
  }
  $("#id").val("");
  $('#box').datagrid({
    url: '/contacts/reviewcontactspage.ajax',
    queryParams: queryParams,
    loadMsg: '数据装载中......',
    onLoadSuccess: function (data) {
      var rows = data.rows;
      if (rows.length > 0) {
        // 将返回的ID存入隐藏的ID中 方便修改责任人
        $("#id").val(rows[0].id);

        $("#updateDutyPeople").show();
        if (rows[0].issend1) {
          rows.issend1 = rows[0].issend1;
        } else {
          rows.issend1 = 0;
        }
        if (rows[0].issend2) {
          rows.issend2 = rows[0].issend2;
        } else {
          rows.issend1 = 0;
        }
        $('#fm').form('load', rows);
        QJ_DATAVALUE = serializeObject($('#fm'));
        if (QJ_DATAVALUE.userid != undefined) {
          QJ_DATAVALUE.userid = $("#username").combobox('getValue');
        }
        QJ_DATAVALUE.id = $("#id").val();
      } else {
        $("#updateDutyPeople").hide();
      }
    }
  });
}

function deltr(clickTd) {
  $.ajax({
    type: "post",
    url: '/contacts/updatedel.ajax',
    data: {
      "id": clickTd
    },
    dataType: "json",
    async: false,
    success: function (data) {
      if (data) {
        $.messager.alert('系统提示', data.result, 'warning');
        $('#dlg').dialog('close');
        $('#dg').datagrid('reload');
      } else {
        $.messager.alert('系统提示', data.message, 'warning');
      }
    }
  });
}

// 删除信息
function deletecontacts() {
  dataValue = $('#dg').datagrid('getSelections');
  // dataValue=serializeObject($('#fm'));
  if (dataValue.length == 0) {
    $.messager.alert('系统提示', '请选择要操作的数据', 'warning');
    return;
  }
  $.messager.confirm('系统提示', '</br>是否确认删除</br></br>', function (r) {
    if (r) {
      $.ajax({
        type: "post",
        url: '/contacts/deletecontacts.ajax',
        data: {
          values: JSON.stringify(dataValue)
        },
        dataType: "json",
        async: false,
        success: function (data) {
          if (data) {
            $.messager.alert('系统提示', data.result, 'warning');
            $('#dlg').dialog('close');
            $('#dg').datagrid('reload');
          } else {
            $.messager.alert('系统提示', data.message, 'warning');
          }
        }
      });
    }
  });
}

var obj

// 初始化责任添加
function init() {
  $('#save,#redo').hide();
  obj.editRow;
  $('#box').datagrid('rejectChanges');
  $('#box').datagrid('loadData', {
    total: 0,
    rows: []
  });
  // 清空ID 隐藏修改责任人按钮
  $("#id").val("");
  $("#updateDutyPeople").hide();
  // 初始化定义在全局用来回滚选中前的责任人
  userText = $('#username').combobox("getText");
  userValue = $('#username').combobox("getValue");
}

// 获取需要编辑的控件
function getEditRow(lastIndex, field) {
  return category = $('#box').datagrid('getEditor', {
    index: lastIndex,
    field: field
  });
}

// 异步加载责任位1，并在选中后，级联加载责任位2
function synchCategory(editRow, index) {

  // 责任位
  $.ajax({
    type: "get",
    url: "/dictionary/getdictionary.ajax",
    data: {
      "value_set_id": "duty_1"
    },
    dataType: "json",
    success: function (data) {
      // 复制对象

      a = [];
      a.value = "";
      a.valueMeaning = "-请选择-";
      data.unshift(a);
      $(editRow.target).combobox('loadData', data);

    }
  });
}

// 责任位1将文字转换为id
function textOrId(text) {

  for (var i = 0; i < products.length; i++) {
    if (products[i].valueMeaning == text) {
      return products[i].value;
    }
  }
  return text;
}

// 异步加载责任位2
function synchBrand(editRow, index, categoryId) {

  if (!categoryId) {
    return;
  }
  // 如果传来的不是责任位1的id则转换
  var pattern = /\D/ig;
  category = "";
  if (pattern.test(categoryId)) {
    category = textOrId(categoryId);

  } else {
    category = categoryId;

  }
  var rowIndex = obj.editRow;
  if (rowIndex == "-1") {
    rowIndex = 0;
  }
  $.ajax({
    type: "GET",
    url: "/dictionary/gettwomenu.ajax",
    data: {
      "value_set_id": "duty_2",
      "parent_value": category
    },
    dataType: "json",
    success: function (data) {

      a = [];
      a.value = "";
      a.valueMeaning = "-请选择-";
      data.unshift(a);

      products2 = data;

      jQuery(editRow.target).combobox('loadData', data);

      jQuery(getEditRow(rowIndex, 'question1level2').target)
      .combobox('setText', "-请选择-").combobox('setValue', "");

    }
  });
}

//异步加载责任位1，并在选中后，级联加载责任位2
function synchCategory2(editRow, index) {
  // 责任位
  $.ajax({
    type: "get",
    url: "/dictionary/getdictionary.ajax",
    data: {
      "value_set_id": "duty_1"
    },
    dataType: "json",
    success: function (data) {
      for (var i = 0; i < data.length; i++) {
        data[i].value = data[i].valueMeaning;
      }
      a = [];
      a.value = "";
      a.valueMeaning = "-请选择-";
      data.unshift(a);
      $(editRow.target).combobox('loadData', data);
    }
  });
}

//异步加载责任位2
function synchBrand2(editRow, index, categoryId) {
  if (!categoryId) {
    return;
  }
  // 如果传来的不是责任位1的id则转换
  var pattern = /\D/ig;
  category = "";
  if (pattern.test(categoryId)) {
    category = textOrId(categoryId);
  } else {
    category = categoryId;
  }
  var rowIndex = obj3.editRow;
  if (rowIndex == "-1") {
    rowIndex = 0;
  }
  $.ajax({
    type: "GET",
    url: "/dictionary/gettwomenu.ajax",
    data: {
      "value_set_id": "duty_2",
      "parent_value": category
    },
    dataType: "json",
    success: function (data) {
      for (var i = 0; i < data.length; i++) {
        data[i].value = data[i].valueMeaning;

      }
      a = [];
      a.value = "";
      a.valueMeaning = "-请选择-";
      data.unshift(a);
      products2 = data;
      jQuery(editRow.target).combobox('loadData', data);
      if (!pattern.test(categoryId)) {
        jQuery(getEditRow3(rowIndex, 'question1level2').target)
        .combobox('setText', "-请选择-").combobox('setValue', "");
      }

    }
  });
}

// 异步加载工贸
function sysGm(editRow, index) {

  jQuery(editRow.target).combobox('loadData', products3);
}

// 异步加载订单来源
function sysLy(editRow, index) {
  $.ajax({
    type: "GET",
    url: "/dictionary/getdictionary.ajax",
    data: {
      "value_set_id": "order_source"
    },
    dataType: "json",
    success: function (data) {
      // a = [];
      // a.value = "";
      // a.valueMeaning = "-请选择-";
      // data.unshift(a);
      // b=[];
      // b.value = "qt";
      // b.valueMeaning = "其他";
      // data.push(b);
      window.products4 = data;
      jQuery(editRow.target).combobox('loadData', data);
    }
  });

}

// 责任位1格式化代码
function productFormatter(value) {
  for (var i = 0; i < products.length; i++) {
    if (products[i].value == value) {
      return products[i].valueMeaning;
    }
  }
  return value;
}

// 责任位2格式化代码
function productFormatter2(value) {

  for (var i = 0; i < products2.length; i++) {
    if (value == "") {
      return "";
    }
    if (products2[i].value == value) {
      return products2[i].valueMeaning;
    }
  }
  return value;
}

// 工贸格式化代码
function productFormatter3(value) {
  for (var i = 0; i < products3.length; i++) {
    if (value == "") {
      return "";
    }
    if (products3[i].value == value) {
      return products3[i].valueMeaning;
    }
  }
  return value;
}

// 订单来源格式化代码
function productFormatter4(value) {
  for (var i = 0; i < products4.length; i++) {
    if (value == "") {
      return "";
    }
    if (products4[i].value == value) {
      return products4[i].valueMeaning;
    }
  }
  return value;
}

// 合并同行
function MergeCells(tableID, fldList) {
  var Arr = fldList.split(",");
  var dg = $('#' + tableID);
  var fldName;
  var RowCount = dg.datagrid("getRows").length;
  var span;
  var PerValue = "";
  var CurValue = "";
  var PerId = "";
  var CurId = "";
  var length = Arr.length - 1;
  for (i = length; i >= 0; i--) {
    fldName = Arr[i];
    PerValue = "";
    PerId = "";
    span = 1;
    for (row = 0; row <= RowCount; row++) {
      if (row == RowCount) {
        CurValue = "";
      } else {
        CurValue = dg.datagrid("getRows")[row][fldName];
        CurId = dg.datagrid("getRows")[row].userid;
      }
      // if(userid==id){
      if (PerValue == CurValue && PerId == CurId) {
        span += 1;
      }
      // }

      else {
        var index = row - span;
        dg.datagrid('mergeCells', {
          index: index,// 列号
          field: fldName,// 行号
          rowspan: span,
          colspan: null
        });
        span = 1;
        PerValue = CurValue;
        PerId = CurId;

      }
    }
  }
}

var lastIndex;
$(document)
.ready(
    function () {
      // 责任位
      $
      .ajax({
        type: "get",
        url: "/dictionary/getdictionary.ajax",
        data: {
          "value_set_id": "duty_1"
        },
        dataType: "json",
        success: function (data) {
          // 复制对象
          var tmp = clone(data);
          for (var i = 0; i < tmp.length; i++) {
            tmp[i].value = tmp[i].valueMeaning;
          }
          a = [];
          a.value = "";
          a.valueMeaning = "-请选择-";
          data.unshift(a);
          window.products = data;
          // 责任位2
          b = [];
          b.value = "";
          b.valueMeaning = "-请选择-";
          tmp.unshift(b);
          window.products2 = {};
          window.products5 = tmp;
          // 工贸
          $
          .ajax({
            type: "get",
            url: "/dictionary/getdictionary.ajax",
            data: {
              "value_set_id": "company"
            },
            dataType: "json",
            success: function (data) {
              // a = [];
              // a.value = "";
              // a.valueMeaning = "-请选择-";
              // data.unshift(a);
              window.products3 = data;
              // 订单来源
              $
              .ajax({
                type: "GET",
                url: "/dictionary/getdictionary.ajax",
                data: {
                  "value_set_id": "order_source"
                },
                dataType: "json",
                success: function (
                    data) {
                  // a = [];
                  // a.value =
                  // "";
                  // a.valueMeaning
                  // =
                  // "-请选择-";
                  // data.unshift(a);
                  // b=[];
                  // b.value =
                  // "qt";
                  // b.valueMeaning
                  // = "其他";
                  // data.push(b);
                  window.products4 = data;

                  obj = {
                    editRow: undefined,
                    search: function () {
                      $('#box').datagrid('load', {
                        user: $.trim($('input[name="user"]').val()),
                        date_from: $(
                            'input[name="date_from"]')
                        .val(),
                        date_to: $(
                            'input[name="date_to"]')
                        .val(),
                      });
                    },
                    add: function () {
                      $('#save,#redo').show();
                      if (this.editRow == undefined) {
                        // 添加一行
                        $('#box').datagrid('insertRow', {
                          index: 0,
                          row: {}
                        });
                        this.editRow = 0;
                        // 将第一行设置为可编辑状态
                        $('#box').datagrid('beginEdit', 0);
                        jQuery(getEditRow(0, 'company').target).combobox(
                            'disable');
                        synchCategory(getEditRow(0, 'question1level1'), 0);
                        sysGm(getEditRow(0, 'company'),
                            0);
                        sysLy(getEditRow(0, 'ordercome'), 0);
                      }
                      $('#box').datagrid('selectRow', this.editRow);
                    },
                    save: function () {
                      // 这两句不应该放这里，应该是保存成功后，再执行
                      $('#box').datagrid('endEdit', this.editRow);
                    },
                    redo: function () {
                      $(
                          '#save,#redo')
                      .hide();
                      this.editRow = undefined;
                      $(
                          '#box')
                      .datagrid(
                          'rejectChanges');
                      $(
                          '#box')
                      .datagrid(
                          'hideColumn',
                          'qtd');
                    },
                    edit: function () {
                      if (obj.editRow != undefined) {
                        $.messager
                        .alert(
                            '系统提示',
                            "请先保存当前处于编辑状态下的数据!",
                            'warning');
                        return;
                      }
                      var rows = $(
                          '#box')
                      .datagrid(
                          'getSelections');
                      if (rows.length == 1) {
                        if (this.editRow != undefined) {
                          $(
                              '#box')
                          .datagrid(
                              'endEdit',
                              this.editRow);
                        }

                        if (this.editRow == undefined) {
                          var index = $(
                              '#box')
                          .datagrid(
                              'getRowIndex',
                              rows[0]);
                          $(
                              '#save,#redo')
                          .show();
                          this.editRow = index;
                          $(
                              '#box')
                          .datagrid(
                              'beginEdit',
                              index);
                          $(
                              '#box')
                          .datagrid(
                              'unselectRow',
                              index);
                          sysLy(
                              getEditRow(
                                  index,
                                  'ordercome'),
                              index);

                        }
                      } else {
                        $.messager
                        .alert(
                            '提示',
                            '修改必须或只能选择一行！',
                            'warning');
                      }
                      $(
                          '#box')
                      .datagrid(
                          'selectRow',
                          this.editRow);
                    },
                    remove: function () {
                      if (obj.editRow != undefined) {
                        $.messager
                        .alert(
                            '系统提示',
                            "请先保存当前处于编辑状态下的数据!",
                            'warning');
                        return;
                      }
                      var rows = $(
                          '#box')
                      .datagrid(
                          'getSelections');
                      if (rows.length > 0) {
                        $.messager
                        .confirm(
                            '确定操作',
                            '您确定要删除所选的记录吗？',
                            function (
                                flag) {
                              if (flag) {
                                var ids = [];
                                for (var i = 0; i < rows.length; i++) {
                                  ids
                                  .push(rows[i].id);
                                }
                                console
                                .log(ids
                                .join(','));
                                dataValue = [];

                                for (i = 0; i < ids.length; i++) {
                                  dataV = {};
                                  dataV.id = ids[i]
                                  dataValue
                                  .push(dataV)
                                }

                                $
                                .ajax({
                                  type: "post",
                                  url: '/contacts/deletecontacts.ajax',
                                  data: {
                                    values: JSON
                                    .stringify(dataValue),
                                    qf: "tcy"
                                  },
                                  dataType: "json",
                                  async: false,
                                  success: function (
                                      data) {
                                    if (data) {
                                      $(
                                          '#box')
                                      .datagrid(
                                          'loaded');
                                      $(
                                          '#box')
                                      .datagrid(
                                          'load');
                                      $(
                                          '#box')
                                      .datagrid(
                                          'unselectAll');
                                    } else {
                                      $.messager
                                      .alert(
                                          '系统提示',
                                          data.message,
                                          'warning');
                                    }
                                  }
                                });

                              }
                            });
                      } else {
                        $.messager
                        .alert(
                            '提示',
                            '请选择要删除的记录！',
                            'info');
                      }
                    },
                  };

                  $('#box')
                  .datagrid(
                      {
                        width: 750,
                        height: 380,
                        // url
                        // :
                        // 'content.json',
                        // url
                        // :
                        // 'user.php',
                        // title
                        // :
                        // '责任配置',
                        // iconCls
                        // :
                        // 'icon-search',
                        striped: true,
                        nowrap: true,
                        rownumbers: true,
                        // singleSelect
                        // :
                        // true,
                        // fitColumns
                        // :
                        // true,
                        columns: [[
                          {
                            field: 'id',
                            title: '编号',
                            // sortable
                            // :
                            // true,
                            width: 100,
                            checkbox: true,
                          },
                          {
                            field: 'ordercome',
                            title: '订单来源',
                            // sortable
                            // :
                            // true,
                            width: 180,
                            editor: {
                              type: 'combobox',
                              options: {
                                multiple: true,
                                editable: false,
                                required: true,
                                valueField: 'valueMeaning',
                                textField: 'valueMeaning',
                                data: products4,
                                onChange: function (
                                    oldVal,
                                    newVal) {
                                  var rowIndex = obj.editRow
                                  if (rowIndex == "-1") {
                                    rowIndex = 0;
                                  }
                                  if (oldVal
                                  .indexOf('其他') != -1) {
                                    $(
                                        '#box')
                                    .datagrid(
                                        'showColumn',
                                        'qtd');
                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'qtd').target)
                                    .val(
                                        '');
                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'qtd').target)
                                    .attr(
                                        'disabled',
                                        false);
                                  } else {
                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'qtd').target)
                                    .val(
                                        '');
                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'qtd').target)
                                    .attr(
                                        'disabled',
                                        true);
                                    $(
                                        '#box')
                                    .datagrid(
                                        'hideColumn',
                                        'qtd');

                                  }
                                },
                                onSelect: function (
                                    oldVal) {
                                  var rowIndex = obj.editRow;
                                  if (rowIndex == "-1") {
                                    rowIndex = 0;
                                  }
                                  if (oldVal.valueMeaning == '其他') {
                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'ordercome').target)
                                    .combobox(
                                        'setValue',
                                        '其他')
                                    .combobox(
                                        'setText',
                                        '其他');
                                  } else {
                                    if (jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'ordercome').target)
                                    .combobox(
                                        'getValues')[0] == '其他') {
                                      jQuery(
                                          getEditRow(
                                              rowIndex,
                                              'ordercome').target)
                                      .combobox(
                                          'setValue',
                                          oldVal.valueMeaning)
                                      .combobox(
                                          'setText',
                                          oldVal.valueMeaning);
                                    }
                                  }
                                },
                                onUnselect: function (
                                    oldVal,
                                    newVal) {

                                },

                              },
                            },
                          },
                          {
                            field: 'qtd',
                            title: '其他',
                            // sortable
                            // :
                            // true,
                            hidden: true,
                            width: 150,
                            editor: {
                              type: 'validatebox',
                              options: {
                                // required
                                // :
                                // true,
                                validType: 'length[0,100]'
                              },
                            },
                          },
                          {
                            field: 'question1level1',
                            title: '责任位1',
                            // sortable
                            // :
                            // true,
                            width: 100,
                            editor: {
                              type: 'combobox',
                              options: {
                                editable: false,
                                // required
                                // :
                                // true,
                                valueField: 'value',
                                textField: 'valueMeaning',
                                data: products,
                                onChange: function (
                                    oldVal,
                                    newVal) {
                                  var rowIndex = obj.editRow
                                  if (rowIndex == "-1") {
                                    rowIndex = 0;
                                  }
                                  // 如果是物流类
                                  // if(oldVal==1||oldVal=='物流类'){
                                  // $('#question1Level2').combobox('disable');
                                  // $('#question1Level1').combobox('enable');
                                  // jQuery(getEditRow(rowIndex,'company').target).combobox('setText',"-请选择-").combobox('setValue',"");
                                  // jQuery(getEditRow(rowIndex,'ordercome').target).combobox('setText',"-请选择-");
                                  // jQuery(getEditRow(rowIndex,'question1level2').target).combobox('disable');
                                  // jQuery(getEditRow(rowIndex,'company').target).combobox('enable');
                                  // jQuery(getEditRow(rowIndex,'ordercome').target).combobox('disable');
                                  // jQuery(getEditRow(rowIndex,'qtd').target).val('');
                                  // jQuery(getEditRow(rowIndex,'qtd').target).attr('disabled',
                                  // true);
                                  // 如果是发票类
                                  // }else
                                  // if(oldVal==2||oldVal=='发票类'){
                                  // jQuery(getEditRow(rowIndex,'company').target).combobox('setText',"-请选择-").combobox('setValue',"");
                                  // jQuery(getEditRow(rowIndex,'ordercome').target).combobox('setText',"-请选择-");
                                  // jQuery(getEditRow(rowIndex,'question1level2').target).combobox('disable');
                                  // jQuery(getEditRow(rowIndex,'company').target).combobox('disable');
                                  // jQuery(getEditRow(rowIndex,'ordercome').target).combobox('disable');
                                  // jQuery(getEditRow(rowIndex,'qtd').target).val('');
                                  // jQuery(getEditRow(rowIndex,'qtd').target).attr('disabled',
                                  // true);
                                  // }else{

                                  // jQuery(getEditRow(rowIndex,'company').target).combobox('setText',"-请选择-").combobox('setValue',"");
                                  // jQuery(getEditRow(rowIndex,'ordercome').target).combobox('setText',"");
                                  // jQuery(getEditRow(rowIndex,'question1level2').target).combobox('enable');
                                  // jQuery(getEditRow(rowIndex,'company').target).combobox('disable');
                                  // jQuery(getEditRow(rowIndex,'ordercome').target).combobox('enable');
                                  // if(jQuery(getEditRow(rowIndex,'ordercome').target).combobox('getText')=='其他'){
                                  // jQuery(getEditRow(rowIndex,'qtd').target).val('');
                                  // jQuery(getEditRow(rowIndex,'qtd').target).attr('disabled',
                                  // false);
                                  // }else{
                                  // jQuery(getEditRow(rowIndex,'qtd').target).val('');
                                  // jQuery(getEditRow(rowIndex,'qtd').target).attr('disabled',
                                  // true);
                                  // }

                                  // }

                                  if (oldVal == ''
                                      || oldVal == '-请选择-') {

                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'company').target)
                                    .combobox(
                                        'setText',
                                        "-请选择-")
                                    .combobox(
                                        'setValue',
                                        "");
                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'company').target)
                                    .combobox(
                                        'disable');
                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'question1level2').target)
                                    .combobox(
                                        'loadData',
                                        {});
                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'question1level2').target)
                                    .combobox(
                                        'setText',
                                        "")
                                    .combobox(
                                        'setValue',
                                        "");

                                  } else {

                                    jQuery(
                                        getEditRow(
                                            rowIndex,
                                            'company').target)
                                    .combobox(
                                        'enable');
                                    synchBrand(
                                        getEditRow(
                                            rowIndex,
                                            'question1level2'),
                                        null,
                                        oldVal);

                                  }

                                },
                              },

                            },
                          },

                          {
                            field: 'question1level2',
                            title: '责任位2',
                            // sortable
                            // :
                            // true,
                            width: 100,
                            editor: {
                              type: 'combobox',
                              options: {
                                editable: false,
                                valueField: 'value',
                                textField: 'valueMeaning',
                                data: products2
                              },
                            },
                          },
                          {
                            field: 'company',
                            title: '工贸',
                            disabled: true,
                            // sortable
                            // :
                            // true,
                            width: 100,
                            editor: {
                              type: 'combobox',
                              options: {
                                disabled: true,
                                multiple: true,
                                editable: false,
                                valueField: 'valueMeaning',
                                textField: 'valueMeaning',
                                data: products3,
                                onChange: function (
                                    oldVal,
                                    newVal) {

                                  var rowIndex = obj.editRow
                                  if (rowIndex == "-1") {
                                    rowIndex = 0;
                                  }

                                  // jQuery(getEditRow(rowIndex,'ordercome').target).combobox('setText',"-请选择-");
                                },
                              },
                            },
                          },

                        ]],
                        toolbar: '#tb',
                        pagination: true,
                        pageSize: 50,
                        pageList: [
                          50,
                          100,
                          200],
                        pageNumber: 1,
                        sortName: 'date',
                        sortOrder: 'DESC',
                        onDblClickRow: function (
                            rowIndex,
                            rowData) {// 双击事件
                          $(
                              '#box')
                          .datagrid(
                              'unselectAll'); // 取消全部选中
                          if (obj.editRow != undefined) {
                            $(
                                '#box')
                            .datagrid(
                                'selectRow',
                                obj.editRow); // 选中当前编辑行
                            return;
                            // $('#box').datagrid('endEdit',
                            // obj.editRow);//保存上一次编辑
                          }

                          if (obj.editRow == undefined) {
                            $(
                                '#save,#redo')
                            .show();
                            obj.editRow = rowIndex;
                            $(
                                '#box')
                            .datagrid(
                                'selectRow',
                                rowIndex); // 选中当前编辑行
                            $(
                                '#box')
                            .datagrid(
                                'beginEdit',
                                rowIndex);// 打开新编辑
                            sysLy(
                                getEditRow(
                                    rowIndex,
                                    'ordercome'),
                                rowIndex);
                          }

                        },
                        onClickRow: function (
                            rowIndex) {// 单击事件
                          // if
                          // (lastIndex
                          // !=
                          // rowIndex){
                          // $('#box').datagrid('endEdit',
                          // lastIndex);
                          // $('#box').datagrid('beginEdit',
                          // rowIndex);
                          // }
                          // lastIndex
                          // =
                          // rowIndex;
                        },
                        onCheck: function (
                            rowIndex,
                            rowData) {
                          var rows = $(
                              '#box')
                          .datagrid(
                              'getChecked');
                          var index = $(
                              '#box')
                          .datagrid(
                              'getRowIndex',
                              rows[0]);
                          if (obj.editRow != undefined) {

                            if (index != obj.editRow
                                || rows.length != 1) {
                              $(
                                  '#box')
                              .datagrid(
                                  'unselectAll');
                              $(
                                  '#box')
                              .datagrid(
                                  'selectRow',
                                  obj.editRow);
                            }
                          }
                        },
                        onAfterEdit: function (
                            rowIndex,
                            rowData,
                            changes) {

                          rowData.question1level1 = productFormatter(
                              rowData.question1level1);
                          rowData.question1level2 = productFormatter2(
                              rowData.question1level2);
                          rowData.company = productFormatter3(rowData.company);
                          rowData.ordercome = productFormatter4(
                              rowData.ordercome);
                          console
                          .log(rowData);
                          addTr2(rowData);
                        }
                      });
                }
              });
            }
          });
        }
      });
    });

// 重置
function reset() {
  $("#username_search").val("");
  $("#manageruserId1_search").val("");
  $("#manageruserId2_search").val("");
  $("#company_search").val("");
  $('#ordercome_search').combobox('setValue', "")
  .combobox('setText', '-请选择-');
  $('#question1level1_search').combobox('setValue', "").combobox('setText',
      '-请选择-');
  $('#question1level2_search').combobox('setValue', "").combobox('setText',
      '-请选择-');
}

// 弹出更改责任人
function alertBox() {
  $('#dlg2').dialog('open').dialog('setTitle', '修改责任人');
  $("#id2").val($("#id").val());
  // 清空原有数据
  $('#user2').combobox("setText", "").combobox("setValue", "");
  $("#phone_2").val("");
  $("#email_2").val("");
  // 获取所有没有配置责任的人员
  $.ajax({
    type: "get",
    url: "/woUser/selectnotuserid.ajax",
    dataType: "json",
    success: function (data) {
      $('#user2').combobox('loadData', data);
    }
  });
}

/**
 * 更改责任人
 */
function updateDutyPeople() {
  if (!$('#fm2').form('validate')) {
    return;
  }
  var id = $("#id2").val();
  var userid = $('#user2').combobox("getValue");
  $.ajax({
    type: "post",
    url: "/contacts/updatebyid.ajax",
    data: {
      id: id,
      userid: userid
    },
    dataType: "json",
    success: function (data) {
      // {"result":4,"success":true,"message":"","code":""}
      if (data) {
          $('#dlg2').dialog('close');// 关闭窗口
          $("#username").combobox("setText",
          $('#user2').combobox("getText")).combobox(
          "setValue", $('#user2').combobox("getValue"));
          $("#phone").val($("#phone_2").val());
          $("#email").val($("#email_2").val());
      } else {
        $.messager.alert('系统提示', data.message, 'warning');
      }
    }
  });
}

// 设置人员
$('#user2').combobox({
  valueField: 'userId',
  textField: 'userName',
  onSelect: function (rec) {
    $("#phone_2").val(rec.mobile);// 填充内容
    $("#email_2").val(rec.email);// 填充内容
  }
});

// 取消
function cancel() {
  if (obj.editRow != undefined) {
    $.messager.confirm('确定操作', '当前正处于编辑状态,您确定要取消吗？', function (flag) {
      if (flag) {
        $('#dlg').dialog('close');
      }
    });
  } else {
    $('#dlg').dialog('close');
  }

}

// 导出
function exp() {
  var data = $('#dg').datagrid('getData');
  if (data.rows.length <= 0) {
    $.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
    return;
  }
  // var selectedvalue=$("input[name='tab']:checked").val();
  window.location.href = "/export/export5.html?" + 'question1level1_search='
      + WORK_ORDER_EXCEL.Q_question1level1_search
      + '&question1level2_search='
      + WORK_ORDER_EXCEL.Q_question1level2_search + '&ordercome_search='
      + WORK_ORDER_EXCEL.Q_ordercome_search + '&username_search='
      + WORK_ORDER_EXCEL.Q_username_search + '&manageruserId1_search='
      + WORK_ORDER_EXCEL.Q_manageruserId1_search
      + '&manageruserId2_search='
      + WORK_ORDER_EXCEL.Q_manageruserId2_search + '&company_search='
      + WORK_ORDER_EXCEL.Q_company_search;

}

// 将每次点击查询都置到第一页
function setFirstPage(ids) {
  var opts = $(ids).datagrid('options');
  var pager = $(ids).datagrid('getPager');
  opts.pageNumber = 1;
  opts.pageSize = opts.pageSize;
  pager.pagination('refresh', {
    pageNumber: 1,
    pageSize: opts.pageSize
  });
}

// 判断两个对象是否相等
function isObjectValueEqual(a, b) {
  if (a == null || a == "") {
    return false;
  }
  var aProps = Object.getOwnPropertyNames(a);
  var bProps = Object.getOwnPropertyNames(b);
  if (aProps.length != bProps.length) {
    return false;
  }

  for (var i = 0; i < aProps.length; i++) {
    var propName = aProps[i];
    if (a[propName] !== b[propName]) {
      return false;
    }
  }
  return true;
}

// 获取需要编辑的控件
function getEditRow3(lastIndex, field) {
  return category = jQuery('#box3').datagrid('getEditor', {
    index: lastIndex,
    field: field
  });
}

// 导入
function importOpen() {
//	$('#box3').datagrid('loadData', {
//		total : 0,
//		rows : []
//	});
  $('#box3').datagrid({url: ''});
  $('#box3').datagrid('loadData', {total: 0, rows: []});
  $("#file").val("");
  queryParams = {
    'userId': 123
  };
  $('#dlg3').dialog('open').dialog('setTitle', '人员责任导入');
  obj3 = {
    editRow: undefined,
    search: function () {
      $('#box3').datagrid('load', {
        user: $.trim($('input[name="user"]').val()),
        date_from: $('input[name="date_from"]').val(),
        date_to: $('input[name="date_to"]').val(),
      });
    },
    save: function () {
      // 将第一行设置为结束编辑状态
      var rowIndex = this.editRow;
      $('#box3').datagrid('endEdit', rowIndex);
      $('#box3').datagrid('acceptChanges');
    },
    redo: function () {
      $('#save,#redo').hide();
      this.editRow = undefined;
      $('#box3').datagrid('rejectChanges');
      $('#box3').datagrid('hideColumn', 'qtd');
    },
    edit: function () {
      if (obj3.editRow != undefined) {
        $.messager.alert('系统提示', "请先保存当前处于编辑状态下的数据!", 'warning');
        return;
      }
      var rows = $('#box3').datagrid('getSelections');
      if (rows.length == 1) {
        if (this.editRow != undefined) {
          $('#box3').datagrid('endEdit', this.editRow);
        }

        if (this.editRow == undefined) {
          var index = $('#box3').datagrid('getRowIndex', rows[0]);
          $('#save,#redo').show();
          this.editRow = index;
          $('#box3').datagrid('beginEdit', index);
          $('#box3').datagrid('unselectRow', index);
          sysLy(getEditRow3(index, 'ordercome'), index);

        }
      } else {
        $.messager.alert('提示', '修改必须或只能选择一行！', 'warning');
      }
      $('#box').datagrid('selectRow', this.editRow);
    },
    remove: function () {
      if (obj3.editRow != undefined) {
        $.messager.alert('系统提示', "请先保存当前处于编辑状态下的数据!", 'warning');
        return;
      }
      var rows = $('#box3').datagrid('getSelections');
      if (rows.length > 0) {
        $.messager.confirm('确定操作',
            '您确定要删除所选的导入记录吗？(仅删除该条导入数据,不会删除数据库内容)', function (flag) {
              if (flag) {
                for (var i = 0; i < rows.length; i++) {
                  var row = $("#box3").datagrid(
                      "getRowIndex", rows[i]);
                  $('#box3').datagrid('deleteRow', row);
                }
                $('#box3').datagrid('acceptChanges');
              }
            });
      } else {
        $.messager.alert('提示', '请选择要删除的记录！', 'info');
      }
    },
  };
  $('#box3')
  .datagrid(
      {
        width: 798,
        height: 385,
        striped: true,
        nowrap: true,
        rownumbers: true,
        pagination: false,//分页控件
        columns: [[
          {
            field: 'id',
            title: '编号',
            // sortable : true,
            width: 100,
            checkbox: true,
          },
          {
            field: 'userid',
            title: '人员ID',
            width: 100,
            hidden: true,
          },
          {
            field: 'manageruserid1',
            title: '一级领导ID',
            width: 100,
            hidden: true,
          },
          {
            field: 'manageruserid2',
            title: '二级领导ID',
            width: 100,
            hidden: true,
          },
          {
            field: 'username',
            title: '人员',
            // sortable : true,
            width: 80,
          }, {
            field: 'username1',
            title: '一级领导',
            // sortable : true,
            width: 80,
          },

          {
            field: 'username2',
            title: '二级领导',
            // sortable : true,
            width: 80,
          },

          {
            field: 'ordercome',
            title: '订单来源',
            // sortable : true,
            width: 180,
            editor: {
              type: 'combobox',
              options: {
                multiple: true,
                editable: false,
                required: true,
                valueField: 'valueMeaning',
                textField: 'valueMeaning',
                data: products4,
                onChange: function (oldVal, newVal) {
                  var rowIndex = obj3.editRow
                  if (rowIndex == "-1") {
                    rowIndex = 0;
                  }
                },
                onSelect: function (oldVal) {
                  var rowIndex = obj3.editRow;
                  if (rowIndex == "-1") {
                    rowIndex = 0;
                  }
                  if (oldVal.valueMeaning == '其他') {
                    jQuery(
                        getEditRow3(
                            rowIndex,
                            'ordercome').target)
                    .combobox(
                        'setValue',
                        '其他')
                    .combobox(
                        'setText',
                        '其他');
                  } else {
                    if (jQuery(
                        getEditRow3(
                            rowIndex,
                            'ordercome').target)
                    .combobox(
                        'getValues')[0] == '其他') {
                      jQuery(
                          getEditRow3(
                              rowIndex,
                              'ordercome').target)
                      .combobox(
                          'setValue',
                          oldVal.valueMeaning)
                      .combobox(
                          'setText',
                          oldVal.valueMeaning);
                    }
                  }
                },
                onUnselect: function (oldVal,
                    newVal) {

                },

              },
            },
          },
          {
            field: 'qtd',
            title: '其他',
            // sortable : true,
            hidden: true,
            width: 150,
            editor: {
              type: 'validatebox',
              options: {
                // required : true,
                validType: 'length[0,100]'
              },
            },
          },
          {
            field: 'question1level1',
            title: '责任位1',
            // sortable : true,
            width: 100,
            editor: {
              type: 'combobox',
              options: {
                editable: false,
                // required : true,
                valueField: 'value',
                textField: 'valueMeaning',
                data: products5,
                onChange: function (oldVal, newVal) {
                  var rowIndex = obj3.editRow
                  if (rowIndex == "-1") {
                    rowIndex = 0;
                  }
                  if (oldVal == ''
                      || oldVal == '-请选择-') {
                    jQuery(
                        getEditRow3(
                            rowIndex,
                            'company').target)
                    .combobox(
                        'setText',
                        "-请选择-")
                    .combobox(
                        'setValue',
                        "");
                    jQuery(
                        getEditRow3(
                            rowIndex,
                            'company').target)
                    .combobox('disable');
                    jQuery(
                        getEditRow3(
                            rowIndex,
                            'question1level2').target)
                    .combobox(
                        'loadData',
                        {});
                    jQuery(
                        getEditRow3(
                            rowIndex,
                            'question1level2').target)
                    .combobox(
                        'setText',
                        "")
                    .combobox(
                        'setValue',
                        "");

                  } else {
                    jQuery(
                        getEditRow3(
                            rowIndex,
                            'company').target)
                    .combobox('enable');
                    synchBrand2(getEditRow3(
                        rowIndex,
                        'question1level2'),
                        null, oldVal);
                  }

                },
              },

            },
          },
          {
            field: 'question1level2',
            title: '责任位2',
            // sortable : true,
            width: 100,
            editor: {
              type: 'combobox',
              options: {
                editable: false,
                valueField: 'value',
                textField: 'valueMeaning',
                data: products2
              },
            },
          },
          {
            field: 'company',
            title: '工贸',
            disabled: true,
            // sortable : true,
            width: 100,
            editor: {
              type: 'combobox',
              options: {
                disabled: true,
                multiple: true,
                editable: false,
                valueField: 'valueMeaning',
                textField: 'valueMeaning',
                data: products3,
                onChange: function (oldVal, newVal) {

                  var rowIndex = obj3.editRow
                  if (rowIndex == "-1") {
                    rowIndex = 0;
                  }

                  // jQuery(getEditRow3(rowIndex,'ordercome').target).combobox('setText',"-请选择-");
                },
              },
            },
          },

        ]],
        toolbar: '#tb3',
        onDblClickRow: function (rowIndex, rowData) {// 双击事件
          $('#box3').datagrid('unselectAll'); // 取消全部选中
          if (obj3.editRow != undefined) {
            $('#box3').datagrid('selectRow', obj3.editRow); // 选中当前编辑行
            return;
            // $('#box').datagrid('endEdit',
            // obj3.editRow);//保存上一次编辑
          }

          if (obj3.editRow == undefined) {
            $('#save,#redo').show();
            obj3.editRow = rowIndex;
            $('#box3').datagrid('selectRow', rowIndex); // 选中当前编辑行
            $('#box3').datagrid('beginEdit', rowIndex);// 打开新编辑
            sysLy(getEditRow3(rowIndex, 'ordercome'),
                rowIndex);
          }

        },
        onClickRow: function (rowIndex) {// 单击事件
          // if (lastIndex != rowIndex){
          // $('#box').datagrid('endEdit', lastIndex);
          // $('#box').datagrid('beginEdit', rowIndex);
          // }
          // lastIndex = rowIndex;
        },
        onCheck: function (rowIndex, rowData) {
          var rows = $('#box3').datagrid('getChecked');
          var index = $('#box3').datagrid('getRowIndex',
              rows[0]);
          if (obj3.editRow != undefined) {

            if (index != obj3.editRow || rows.length != 1) {
              $('#box3').datagrid('unselectAll');
              $('#box3').datagrid('selectRow',
                  obj3.editRow);
            }
          }
        },
        onAfterEdit: function (rowIndex, rowData, changes) {
          $('#save,#redo').hide();
          $('#box3').datagrid('loaded');
          $('#box3').datagrid('unselectAll');
          $('#box3').datagrid('selectRow', obj3.editRow);
          $('#box3').datagrid('hideColumn', 'qtd');
          $('#box3').datagrid('acceptChanges');
          obj3.editRow = undefined;
        },
      });
}

/**
 * 保存Excle
 */
function saveExcle() {
  if (obj3.editRow != undefined) {
    $.messager.alert('系统提示', "请先保存当前处于编辑状态下的数据!", 'warning');
    return;
  }
  // 获取所有box3选中行数据
  var rows = $('#box3').datagrid('getSelections');
  if (rows.length > 0) {

    var list = new Array();
    for (var i = 0; i < rows.length; i++) {
      row = rows[i];
      var rowNum = $("#box3").datagrid("getRowIndex", row);
      var dataV = {};
      dataV.id = row.id;
      dataV.rowNum = rowNum;
      dataV.question1level1 = productFormatter(row.question1level1);
      var zr2 = productFormatter2(row.question1level2);
      dataV.question1level2 = zr2 ? zr2 : "";
      var company = productFormatter3(row.company);
      dataV.company = company != null ? company : "";
      dataV.ordercome = productFormatter4(row.ordercome);
      dataV.userid = row.userid;
      dataV.manageruserid1 = row.manageruserid1;
      dataV.manageruserid2 = row.manageruserid2;
      if (!dataV.ordercome || dataV.ordercome == "") {
        $.messager.alert('系统提示', "数据错误:第" + rowNum + "行,订单来源不能为空!", 'warning');
        return;
      }
      list.push(dataV);
    }
    var jsonData = {
      "dataList": JSON.stringify(list)
    };
    load("#dlg3", "数据导入中，请稍候...");
//		$.messager.progress({
//			title : '请稍后',
//			msg : '数据导入中...'
//		});
    $.ajax({
      type: "post",
      url: '/contacts/findinsertlist.ajax',
      data: jsonData,
      dataType: "json",
      async: true,
      success: function (data) {
        if (data.success) {
          var jsonResult = JSON.parse(data.result);
          if (jsonResult != null) {
            var errorList = new Array();
            var idList = new Array();
            for (var t = 0; t < jsonResult.length; t++) {
              idList.push(jsonResult[t].rowId);
              errorList.push(jsonResult[t].error);
            }
            var idListStr = idList.join(",");
            for (var i = 0; i < rows.length; i++) {
              var rowId = rows[i].id;
              var index = $('#box3').datagrid('getRowIndex', rows[i]);
              if (idListStr.indexOf(rowId) >= 0) {
              } else {
                $('#box3').datagrid('deleteRow', index); //删除
              }
            }
            if (errorList.length > 0) {
              $.messager.alert('系统提示', errorList.join("<br><br>"));
            }

//						$.messager.alert(jsonResult[0].rowNum);
          }
//					$('#box3').datagrid('deleteRow', rowNum); // 删除第一条
//					$('#box3').datagrid('reload');// 删除后重新加载下
          $('#box').datagrid('unselectAll');
          disLoad();
//					$.messager.progress('close');
          $('#box3').datagrid('acceptChanges');
        } else {
          disLoad();
          $.messager.alert('系统提示', data.message);
          return;
        }
      },
      error: function () {
        disLoad()
        $.messager.progress('close');
      }
    });
  } else {
    $.messager.alert('系统提示', '请选择要导入的数据', 'warning');
    $.messager.progress('close');
  }

}

//取消
function cancelImport() {
  var data = $('#box3').datagrid('getData');
  var rowLength = data?data.rows.length:0;//当前页总行数
  if (rowLength > 0) {
    $.messager.confirm('确定操作', '当前存在未导入成功的数据,您确定要取消吗？', function (flag) {
      if (flag) {
        $('#dlg3').dialog('close');
        $('#box3').datagrid('loadData', {
          total: 0,
          rows: []
        });
        $("#file").val("");
      }
    });
  } else {
    $('#dlg3').dialog('close');
    $('#box3').datagrid('loadData', {
      total: 0,
      rows: []
    });
    $("#file").val("");
  }

}

/**
 * upLoad上传
 *
 */
function upLoad() {
  var filePath = $("#file").val();
  filePath = filePath.trim();
  var flag = checkFilePath(filePath);
  if (flag == true) {
    $("#importForm").ajaxSubmit({
      url: '/contacts/reviewcontactsupload.ajax',
      type: 'post',
      dataType: "json",
      success: function (data) {
        if (data.success) {
          aaData = data.result;
          var dataJson = $.parseJSON(aaData);
          $('#box3').datagrid('loadData', dataJson); // 将数据绑定到datagrid
        } else {
          $.messager.alert('系统提示', data.message, 'warning');
        }
      }
    });
    return false;
  }
  $("#file").val("");
}

/**
 * 校验文件格式
 *
 * @param filePath
 * @returns {Boolean}
 */
function checkFilePath(filePath) {
  if (!filePath) {
    $.messager.alert('系统提示', "请选择导入文件！", 'warning');
    return false;
  }
  var extendName = filePath.substring(filePath.lastIndexOf(".") + 1,
      filePath.length);
  if (!extendName) {
    $.messager.alert('系统提示', "文件类型不正确，文件的扩展名必须为.xls!", 'warning');
    return false;
  }
  var strRegex = "(xls|XLS)$";
  var re = new RegExp(strRegex);
  if (!re.test(extendName.toLowerCase())) {
    $.messager.alert('系统提示', "文件格式不合法，文件的扩展名必须为.xls格式!", 'warning');
    return false;
  }
  return true;
}

/**
 * 复制对象
 * @param obj
 * @returns {Clone}
 */
function clone(obj) {
  function Clone() {
  }

  Clone.prototype = obj;
  var o = new Clone();
  for (var a in o) {
    if (typeof o[a] == "object") {
      o[a] = clone(o[a]);
    }
  }
  return o;
}

//弹出加载层
function load(id, content) {
  $("<div class=\"datagrid-mask\"></div>").css(
      {display: "block", width: "100%", height: $(window).height()}).appendTo(
      id);
  $("<div class=\"datagrid-mask-msg\"></div>").html(content).appendTo(id).css({
    display: "block",
    left: ($(id).outerWidth(true) - 190) / 2,
    top: ($(id).height()) / 2
  });
}

//取消加载层
function disLoad() {
  $(".datagrid-mask").remove();
  $(".datagrid-mask-msg").remove();
}