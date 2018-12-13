var datagrid_puchaseItem = {
  fit: true,//自适应
  fitColumns: true,
  singleSelect: false,//是否单选
  url: '',
  frozenColumns: [[{
    field: 'id',
    checkbox: true
  }]],
  columns: [[
    {title: '采购网单号', field: 'poItemNo', sortable: false},
    {title: '订单来源号', field: 'sourceSn', sortable: false},
    {title: '订单详情', field: 'poId', sortable: false},
    {title: '渠道', field: 'channel', sortable: false},
    {title: '供应商', field: 'supplier', sortable: false},
    {title: '物料号', field: 'sku', sortable: false},
    {title: '物料描述', field: 'productTypeName', sortable: false},
    {title: '商品品牌', field: 'brand', sortable: false},
    {title: '订单数量', field: 'poQty', sortable: false},
    {title: '入库数量', field: 'inputQty', sortable: false},
    {title: '单价', field: 'price', sortable: false},
    {title: '总价', field: 'poItemAmount', sortable: false},
    {title: '单位', field: 'unit', sortable: false},
    {title: '库存地点', field: 'secCode', sortable: false},
    {title: '批次', field: 'itemType', sortable: false},
    {title: '特殊标记', field: 'sign', sortable: false},
    {title: '待收欠款', field: 'collect', sortable: false},
    {title: '付款状态', field: 'payStatus', sortable: false},
    {title: '同步状态', field: 'status', sortable: false},
    {title: '最晚到货时间', field: 'latestRevDate', sortable: false},
    {title: '入库时间', field: 'inputTime', sortable: false},
    {title: '网单时间', field: 'poTime', sortable: false},
    {title: '创建时间', field: 'createTime', sortable: false},
    {title: '更新时间', field: 'updateTime', sortable: false}
  ]],
  toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
  striped: true,
  autoRowHeight: true,
  nowrap: true,
  pagination: true,
  rownumbers: true,
  pageSize: 100,
  pageList: [100, 200, 300],
};
$(function () {
  var datagrid = $('#purchaseItemTable').datagrid(datagrid_puchaseItem);

  /*$("#startDate").datebox({
    onSelect: function (startDate) {
      $('#endDate').datebox().datebox('calendar').calendar({
        validator: function(endDate){
          return startDate<=endDate;
        }
      });
    }
  });*/

  function addzero(v) {if (v < 10) return '0' + v;return v.toString();}
  var nowTime = new Date().getFullYear().toString() +"-"+ addzero(new Date().getMonth() + 1) +"-"+ addzero(new Date().getDate());
  $("#startDate").datebox('setValue',nowTime);
  $("#endDate").datebox('setValue',nowTime);

  jQuery.getJSON("/stock/getChannels", function(result){
    var channel = result.data;
    //添加全部
    $("#channel").combobox({
      data:channel,
      valueField:'channelCode',
      textField:'name',
      panelHeight:'auto',
      editable:false,
      value:'ALL'
    });
  });

  $("#resetBtn").on('click', function (event) {
    event.preventDefault();
    $('#filterForm').form('reset');
    $("#startDate").datebox('setValue',nowTime);
    $("#endDate").datebox('setValue',nowTime);
  });

  $("#submitBtn").on('click', function () {
    var selected = datagrid.datagrid('getSelections');
    if (selected.length > 0) {
      var result = "";
      for (var i = 0; i < selected.length; i++) {
        if (selected[i].status == 10) {
          if (confirmItem(selected[i].poItemId)) {
            result = result + "【"+selected[i].poItemNo+"】";
          }
        }
      }
      if (result.length > 0) {
        alert("数据" + result + "提交成功！");
      } else {
        alert("选中数据没有新建状态！");
      }
      $('#purchaseItemTable').datagrid('reload');
    } else {
      alert("请选择要取消的网单");
      return false;
    }
  });

  $("#cancelBtn").on('click', function () {
    var selected = datagrid.datagrid('getSelections');
    if (selected.length > 0) {
      var result = "";
      for (var i = 0; i < selected.length; i++) {
        if (selected[i].status == 10) {
          if (cancelItem(selected[i].poItemId)) {
            result = result + "【"+selected[i].poItemNo+"】";
          }
        }
      }
      if (result.length > 0) {
        alert("数据" + result + "取消成功！");
      } else {
        alert("选中数据没有新建状态！");
      }
      $('#purchaseItemTable').datagrid('reload');
    } else {
      alert("请选择要取消的网单");
      return false;
    }
  });

  $("#exportBtn").on('click',function () {
    var start = $("#startDate").datebox('getValue');
    var end = $("#endDate").datebox('getValue');
    if (start == '' || end == '') {
      alert("日期不能为空！");
      return;
    }
    $('#filterForm').attr("action", "/purchaseOrder/exportPurchaseItemData");
    $('#filterForm').submit();
  })

});

var frame = document.getElementById('iframe');

frame.addEventListener('load',function(event) {
  try {
    var result = frame.contentWindow.document.body.textContent;
    if (result != "") {
      alert(result);
    }
  } catch(er){
    alert("系统异常");
  }
});

//点击查询
$("#searchBtn").on('click', function (event) {

  var start = $("#startDate").datebox('getValue');
  var end = $("#endDate").datebox('getValue');
  if (start == '' || end == '') {
    alert("日期不能为空！");
    return;
  }
  //加载分页
  datagrid = $('#purchaseItemTable').datagrid({
    url: "/purchaseOrder/findPurchaseItemData",
    fit: true,
    fitColumns: false,
    singleSelect: false,//是否单选
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 100,
    pageList: [100, 200, 300],
    queryParams: {
      startDate: start,
      endDate: end,
      poItemNo: $("#poItemNo").val(),
      status: $("#status option:selected").val(),
      sourceSn: $("#sourceSn").val(),
      channel: $("#channel").val(),
      supplier: $("#supplier").val(),
      sku: $("#sku").val()
    },
    frozenColumns: [[{
      field: 'id',
      checkbox: true
    }]],
    columns: [[
      {title: '采购网单号', field: 'poItemNo', sortable: false},
      {title: '订单来源号', field: 'sourceSn', sortable: false},
      {title: '订单详情', field: 'poId', sortable: false,
        formatter: function (value, row, index) {
          return "<a href='javascript:void(0)' style='color: blue' onclick='queryOrder(\""+row.poId+"\")'>查看订单</a>"
        }
      },
      {title: '渠道', field: 'channel', sortable: false},
      {title: '供应商', field: 'supplier', sortable: false},
      {title: '物料号', field: 'sku', sortable: false},
      {title: '物料描述', field: 'productTypeName', sortable: false},
      {title: '商品品牌', field: 'brand', sortable: false},
      {title: '订单数量', field: 'poQty', sortable: false,
        formatter : function (value, row, index) {
          return '<font color="blue">'+row.poQty+'</font>';
        }
      },
      {title: '入库数量', field: 'inputQty', sortable: false,
        formatter : function (value, row, index) {
          return '<font color="red">'+row.inputQty+'</font>';
        }
      },
      {title: '单价', field: 'price', sortable: false,
        formatter: function (value, row, index) {
          return row.price.toFixed(2);
        }
      },
      {title: '总价', field: 'poItemAmount', sortable: false,
        formatter: function (value, row, index) {
          return row.poItemAmount.toFixed(2);
        }
      },
      {title: '单位', field: 'unit', sortable: false},
      {title: '库存地点', field: 'secCode', sortable: false},
      {title: '批次', field: 'itemType', sortable: false},
      {title: '特殊标记', field: 'sign', sortable: false,
        formatter : function (value, row, index) {
          if (row.sign == 1) {
            return "自提";
          } else {
            return "物流";
          }
        }
      },
      {title: '待收欠款', field: 'collect', sortable: false,
        formatter : function (value, row, index) {
          if (row.collect =="P1") {
            return "已付款";
          } else {
            return "未付款";
          }
        }
      },
      {title: '付款状态', field: 'payStatus', sortable: false},
      {title: '同步状态', field: 'status', sortable: false,
        formatter: function (value, row, index) {
          var status = purchaseItemStatus(row.status);
          if (row.status == 10) {
            return '<font style="color:red;">'+status+'</font>';
          } else if (row.status == 20) {
            return '<font style="color:blue;">'+status+'</font>';
          } else {
            return '<font style="color:green;">'+status+'</font>';
          }
        }
      },
      {title: '最晚到货时间', field: 'latestRevDate', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.latestRevDate);
        }
      },
      {title: '入库时间', field: 'inputTime', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.inputTime);
        }
      },
      {title: '网单时间', field: 'poTime', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.poTime);
        }
      },
      {title: '创建时间', field: 'createTime', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.createTime);
        }
      },
      {title: '更新时间', field: 'updateTime', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.updateTime);
        }
      }
    ]]
  });
});

function getMyDate(str){
  if (str != null) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
  } else {
    return '';
  }
};
//补0操作
function getzf(num){
  if(parseInt(num) < 10){
    num = '0'+num;
  }
  return num;
}

function confirmItem(poItemId) {
  var rs = false;
  jQuery.ajax({
    url: "/purchaseOrder/confirmPurchaseItemByPoItemId?poItemId=" + poItemId,   // 提交的页面
    type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
    async: false,
    success: function (result) {
      if (result.success == true) {
        rs = true;
      } else {
        rs = false;
      }
    }
  });
  return rs;
}

function cancelItem(poItemId) {
  var rs = false;
  jQuery.ajax({
    url: "/purchaseOrder/cancelPurchaseItemByPoItemId?poItemId=" + poItemId,   // 提交的页面
    type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
    async: false,
    success: function (result) {
      if (result.success == true) {
        rs = true;
      } else {
        rs = false;
      }
    }
  });
  return rs;
}

function queryOrder(poId) {
  $("#purchaseOrder").datagrid({
    url: "/purchaseOrder/getPurchaseOrderData",
    fit: true,
    fitColumns: false,
    singleSelect: true,//是否单选
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: false,
    rownumbers: true,
    queryParams: {
      poIdStr: poId
    },
    columns: [[
      {title: '订单编号', field: 'poNo', sortable: false},
      {title: '来源渠道', field: 'channelCode', sortable: false},
      {title: '供应商', field: 'suplier', sortable: false},
      {title: '售达方', field: 'soldTo', sortable: false},
      {title: '送达方', field: 'deliveryTo', sortable: false},
      {title: '总价', field: 'amount', sortable: false,
        formatter: function (value, row, index) {
          return row.amount.toFixed(2);
        }
      },
      {title: '订单类型', field: 'type', sortable: false},
      {title: '订单状态', field: 'status', sortable: false,
        formatter: function (value, row, index) {
          var status = purchaseOrderStatus(row.status);
          if (row.status == 100) {
            return '<font style="color:red;">'+status+'</font>';
          } else {
            return '<font style="color:green;">'+status+'</font>';
          }
        }
      },
      {title: '收货人', field: 'receiver', sortable: false},
      {title: '移动电话', field: 'mobile', sortable: false},
      {title: '固定电话', field: 'telephone', sortable: false},
      {title: '所在省', field: 'province', sortable: false},
      {title: '所在市', field: 'city', sortable: false},
      {title: '所有县/区', field: 'county', sortable: false},
      {title: '地址', field: 'address', sortable: false},
      {title: '创建时间', field: 'createTime', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.createTime);
        }
      },
      {title: '更新时间', field: 'updateTime', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.updateTime);
        }
      }
    ]]
  });
  $('#addDlg_purchaseItem').dialog('open');
}

function purchaseOrderStatus(status) {
  var statusA="";
  jQuery.ajax({
    url: "/purchaseOrder/purchaseOrderStatus?status=" + status,   // 提交的页面
    type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
    async: false,
    success: function (data) {
      statusA = data;
    }
  });
  return statusA;
}

function purchaseItemStatus(status) {
  var statusA="";
  jQuery.ajax({
    url: "/purchaseOrder/purchaseItemStatus?status=" + status,   // 提交的页面
    type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
    async: false,
    success: function (data) {
      statusA = data;
    }
  });
  return statusA;
}