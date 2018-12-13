var datagrid_pushSapResult = {
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
    {title: '推送单号', field: 'corderSn', sortable: false},
    {title: '物料编码', field: 'sku', sortable: false},
    {title: '库位编码', field: 'secCode', sortable: false},
    {title: '交易类型', field: 'billType', sortable: false},
    {title: '借贷标志', field: 'mark', sortable: false},
    {title: '渠道', field: 'channelCode', sortable: false},
    {title: '接口编码', field: 'interfaceCode', sortable: false},
    {title: '状态', field: 'statusF', sortable: false},
    {title: '创建时间', field: 'addTimeF', sortable: false},
    {title: '更新时间', field: 'updateTimeF', sortable: false}
  ]],
  toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
  striped: true,
  autoRowHeight: true,
  nowrap: true,
  pagination: true,
  rownumbers: true,
  pageSize: 50,
  pageList: [50, 100, 200],
};
$(function () {
  var datagrid = $('#pushSapResultTable').datagrid(datagrid_pushSapResult);

  function addzero(v) {if (v < 10) return '0' + v;return v.toString();}
  var date = new Date();
  var nowTime = date.getFullYear().toString() +"-"+ addzero(date.getMonth() + 1) +"-"+ addzero(date.getDate())
      +" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
  $("#startTime").datetimebox('setValue',nowTime);
  $("#endTime").datetimebox('setValue',nowTime);

  $("#resetBtn").on('click', function (event) {
    event.preventDefault();
    $('#filterForm').form('reset');
    $("#startTime").datebox('setValue',nowTime);
    $("#endTime").datebox('setValue',nowTime);
  });
});

//点击查询
$("#searchBtn").on('click', function (event) {

  //加载分页
  datagrid = $('#pushSapResultTable').datagrid({
    url: "/pushSapResult/getPurchaseOrderQueue",
    fit: true,
    fitColumns: false,
    singleSelect: true,//是否单选
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
    queryParams: {
      startTime: $("#startTime").val(),
      endTime: $("#endTime").val(),
      corderSn: $("#corderSn").val(),
      status: $("#status option:selected").val(),
      billType: $("#billType option:selected").val()
    },
    frozenColumns: [[{
      field: 'id',
      checkbox: false,
      hidden:true
    }]],
    columns: [[
      {title: '推送单号', field: 'corderSn', sortable: false},
      {title: '物料编码', field: 'sku', sortable: false},
      {title: '库位编码', field: 'secCode', sortable: false},
      {title: '交易类型', field: 'billType', sortable: false,
        formatter : function (value, row, index) {
          return billType(row.billType);
        }
      },
      {title: '借贷标志', field: 'mark', sortable: false,
        formatter : function (value, row, index) {
          if (row.mark =="S") {
            return "入库";
          } else {
            return "出库";
          }
        }
      },
      {title: '渠道', field: 'channelCode', sortable: false},
      {title: '接口编码', field: 'interfaceCode', sortable: false,
        formatter: function (value, row, index) {
          if (row.interfaceCode == "CreatePurchaseOrderInfoFromEhaier") {
            return "3PL采购入库";
          } else if (row.interfaceCode == "PushProSaleOrderToEhaierSAP") {
            return "日日顺销售出库";
          } else if (row.interfaceCode == "PushReturnInfoToGVS") {
            return "退货入库";
          } else if (row.interfaceCode == "TransDNInfoFromEHAIERToGVS") {
            return "电商日日顺&统帅PO收货过账接口";
          } else if (row.interfaceCode == "TransferGoodsInfoToEhaierSAP") {
            return "调拨入库、调拨出库";
          } else if (row.interfaceCode == "TransOrderInfoFromEhaierToGVSHandler") {
            return "标准销售出库接口";
          } else if (row.interfaceCode == "TransReBackInfoFromEHAIERToGVS") {
            return "日日顺&&统帅正品退货";
          } else {
            return "未知";
          }
        }
      },
      {title: '状态', field: 'statusF', sortable: false,
        formatter : function (value, row, index) {
          if (row.statusF =="0") {
            return "未知";
          } else if (row.statusF =="1") {
            return "成功";
          } else if (row.statusF =="2") {
            return "失败"
          } else if (row.statusF =="3") {
            return "错误"
          } else if (row.statusF =="-1") {
            return "特殊处理"
          }
        }
      },
      {title: '创建时间', field: 'addTimeF', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.addTimeF);
        }
      },
      {title: '更新时间', field: 'updateTimeF', sortable: false,
        formatter : function (value,row,index) {
          return getMyDate(row.updateTimeF);
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
}

//交易类型
function billType(value) {
  var map = {ZBCC:'销售出库',YTIB:'存性变更出库',ZGI6:'调拨出库',
        BRSI:'转运出库',ZBJT:'京东虚拟退货出库',ZBCR:'采购入库',YTRB:'存性变更入库',
        ZBCT:'退货入库',ZBCJ:'拒收入库',ZGR6:'调拨入库',ZRSR:'转运入库',
        DBFF:'调拨冻结',XSFF:'销售冻结',SDFF:'手动冻结',DBRR:'取消调拨释放',
        XSRR:'取消销售释放',SDRR:'取消手动释放',RARR:'重新分配库位释放',YTR1:'3W正品退仓',
        SYNC:'同步库存',UN:'未定义的'}
  return map[value];
}