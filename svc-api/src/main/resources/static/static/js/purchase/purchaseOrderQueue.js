var datagrid_puchaseOrderQueue = {
  fit: true,//自适应
  fitColumns: false,
  singleSelect: true,//是否单选
  url: '',
  frozenColumns: [[{
    field: 'id',
    checkbox: false,
    hidden:true
  }]],
  columns: [[
    {title: '网单号', field: 'poItemNo', sortable: false},
    {title: '订单号', field: 'poNo', sortable: false},
    {title: '状态', field: 'status', sortable: false},
    {title: 'Les单号', field: 'lesNo', sortable: false},
    {title: 'Les信息', field: 'lesMsg', sortable: false},
    {title: '订单时间', field: 'poTime', sortable: false},
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
  var datagrid = $('#purchaseOrderQueueTable').datagrid(datagrid_puchaseOrderQueue);

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

  $("#resetBtn").on('click', function (event) {
    event.preventDefault();
    $('#filterForm').form('reset');
    $("#startDate").datebox('setValue',nowTime);
    $("#endDate").datebox('setValue',nowTime);
  });
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
  datagrid = $('#purchaseOrderQueueTable').datagrid({
    url: "/purchaseOrder/getPurchaseOrderQueue",
    fit: true,
    fitColumns: false,
    singleSelect: true,//是否单选
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
      status: $("#status option:selected").val()
    },
    frozenColumns: [[{
      field: 'id',
      checkbox: false,
      hidden:true
    }]],
    columns: [[
      {title: '网单号', field: 'poItemNo', sortable: false,
        formatter : function (value,row,index) {
          return row.purchaseItem.poItemNo;
        }
      },
      {title: '订单号', field: 'poNo', sortable: false,
        formatter : function (value,row,index) {
          return row.purchaseOrder.poNo;
        }
      },
      {title: '状态', field: 'status', sortable: false,
        formatter : function (value,row,index) {
          var status = purchaseOrderQueueStatus(row.purchaseOrderQueue.status);
          if (row.purchaseOrderQueue.status == -1) {
            return '<font style="color:red;">'+status+'</font>';
          } else if (row.purchaseOrderQueue.status == 0) {
            return '<font style="color:blue;">'+status+'</font>';
          } else {
            return '<font style="color:green;">'+status+'</font>';
          }
        }
      },
      {title: 'Les单号', field: 'lesNo', sortable: false,
        formatter : function (value,row,index) {
          return row.purchaseOrderQueue.lesNo;
        }
      },
      {title: 'Les信息', field: 'lesMsg', sortable: false,
        formatter : function (value,row,index) {
          return row.purchaseOrderQueue.lesMsg;
        }
      },
      {title: '订单时间', field: 'poTime', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.purchaseOrderQueue.poTime);
        }
      },
      {title: '创建时间', field: 'createTime', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.purchaseOrderQueue.createTime);
        }
      },
      {title: '更新时间', field: 'updateTime', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.purchaseOrderQueue.updateTime);
        }
      }
    ]],
  });
});

function getMyDate(str){
  if (str != null) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(
            oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
    return oTime;
  }else {
    return '';
  }
};
//补0操作
function getzf(num){
  if(parseInt(num) < 10){
    num = '0'+num;
  }
  return num;
};

function purchaseOrderQueueStatus(status) {
  var statusA="";
  jQuery.ajax({
    url: "/purchaseOrder/purchaseOrderQueueStatus?status=" + status,   // 提交的页面
    type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
    async: false,
    success: function (data) {
      statusA = data;
    }
  });
  return statusA;
}