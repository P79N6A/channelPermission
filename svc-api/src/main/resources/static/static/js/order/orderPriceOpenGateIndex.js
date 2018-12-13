var datagridOptions_orderPriceGate = {
    fit: true,
    fitColumns: true,
    singleSelect: false,
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: [[
      {title: '渠道', field: 'channelCode', sortable: false},
      {title: '订单来源', field: 'orderSource', sortable: false},
      {title: '产业', field: 'industryCode', sortable: false},
      {title: '产品组', field: 'productGroup', sortable: false},
      {title: '订单号', field: 'orderSn', sortable: false},
      {title: '网单号', field: 'corderSn', sortable: false},
      {title: '物料编码', field: 'sku', sortable: false},
      {title: '品类名称', field: 'cateName', sortable: false},
      {title: '品牌名称', field: 'brandName', sortable: false},
      {title: '订单状态', field: 'orderStatus', sortable: false},
      {title: '网单状态', field: 'orderProductStatus', sortable: false},
      {title: '型号', field: 'productName', sortable: false},
      {title: '网单单价', field: 'orderProductPrice', sortable: false},
      {title: '网单数量', field: 'orderProductNumber', sortable: false},
      {title: '商城优惠券金额', field: 'couponAmount', sortable: false},
      {title: '平台承担优惠券金额', field: 'platformCouponAmount', sortable: false},
      {title: '网单付款金额', field: 'orderProductAmount', sortable: false},
      {title: '保本价', field: 'guaranteePrice', sortable: false},
      {title: '保本价差额/费用池优惠券', field: 'subductionPrice', sortable: false},
      {title: '下单时间', field: 'orderAddTime', sortable: false},
      {title: '被闸时间', field: 'createTime', sortable: false},
      {title: '被闸原因', field: 'lockReason', sortable: false},
      {title: '放行操作人', field: 'operator', sortable: false},
      {title: '责任人', field: 'responsiblePerson', sortable: false},
      {title: '放行原因', field: 'unlockReason', sortable: false},
      {title: '放行时间', field: 'unlockTime', sortable: false},
      {title: '闸口状态', field: 'gateStatus', sortable: false},
      {title: '闸口类型', field: 'gateType', sortable: false}
    ]],
    toolbar: '#datagridToolbar_orderPriceGate',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 10,
    pageList: [10,20, 50, 100, 200, 300],
};
$(function () {
    var datagrid = $('#datagrid_orderPriceGate').datagrid(datagridOptions_orderPriceGate);

    $("#resetBtn_orderPriceGate").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderPriceGate').form('reset');
    });

  $("#unlockBtn_orderPriceGate").on('click', function (event) {
    var selected = datagrid.datagrid('getSelections');
    if (selected.length > 0) {
      var corderSn="";
      for(var i=0;i<selected.length;i++){
        if (corderSn != "") {
          corderSn = corderSn + "," +selected[i].orderSn+"@"+selected[i].corderSn;
        } else {
          corderSn = selected[i].orderSn+"@"+selected[i].corderSn;
        }
      }
      $("#cOrderNo").val(corderSn);
      $("#responsiblePerson").textbox('clear');
      $("#reason").textbox('clear');
      $('#addDlg_orderPriceGate').dialog('open');
    }else {
      alert('请选择一条数据');
    }
  });

  $("#addDlgSaveBtn_orderPriceGate").on('click', function () {
    if (!$('#addForm_orderPriceGate').form('validate')) {
      return;
    }
    var  postParam = {
        'responsiblePerson': $("#responsiblePerson").val(),
        'reason': $("#reason").val(),
        'cOrderSns': $("#cOrderNo").val()
      }
    if(jQuery("#responsiblePerson").val().trim() == ""){
      alert('请输入责任人姓名');
      return false;
    }else if(jQuery("#responsiblePerson").val().trim().length > 10){
      alert('责任人姓名不能超过10个汉字');
      return false;
    }else if(jQuery("#reason").val().trim() == ""){
      alert('请输入放行原因');
      return false;
    }else if(jQuery("#reason").val().trim().length > 200){
      alert('放行原因不能超过200个汉字');
      return false;
    }else{
      $.ajax({
        type: "post",
        url: '/guaranteePriceUnLockController/unLockGuaranteePrice',
        data: postParam,
        dataType: 'json',
        success: function (data) {
          if (data.success == true) {
            alert("解锁成功");
          } else {
            alert("解锁失败:"+ data.message);
          }
        },
        error: function () {
          alert("请求错误");
        }
      });
      $('#addDlg_orderPriceGate').dialog('close');
      $('#datagrid_orderPriceGate').datagrid('reload');
    }

  });

});

$("#searchBtn_orderPriceGate").on('click', function (event) {
  checkDate();
  //加载分页
  datagrid = $('#datagrid_orderPriceGate').datagrid({
    url: "/guaranteePriceUnLockController/getGuaranteePriceList",
    fit: true,
    fitColumns: true,
    singleSelect: false,//多选
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 10,
    pageList: [10,20, 50, 100, 200, 300],
    queryParams: {
      startDate: $("#startTime").val(),
      endDate: $("#endTime").val(),
      channel: $('#channel').combobox('getValue'),
      source: $('#source').combobox('getValue'),
      industry: $('#industry').combobox('getValue'),
      productGroup: $('#productGroup').combobox('getValue'),
      orderSn: $("#orderNo").val(),
      cOrderSn: $("#sorderNo").val(),
      gateStatus: $("#isGate option:selected").val(),
      gateType: $("#gateType option:selected").val(),
    },
    frozenColumns: [[{
      field: 'id', checkbox: true
    }]],
    columns: [[
      {title: '渠道', field: 'channelCode', sortable: false},
      {title: '订单来源', field: 'orderSource', sortable: false},
      {title: '产业', field: 'industryCode', sortable: false},
      {title: '产品组', field: 'productGroup', sortable: false},
      {title: '订单号', field: 'orderSn', sortable: false},
      {title: '网单号', field: 'corderSn', sortable: false},
      {title: '物料编码', field: 'sku', sortable: false},
      {title: '品类名称', field: 'cateName', sortable: false},
      {title: '品牌名称', field: 'brandName', sortable: false},
      {title: '订单状态', field: 'orderStatus', sortable: false},
      {title: '网单状态', field: 'orderProductStatus', sortable: false},
      {title: '型号', field: 'productName', sortable: false},
      {title: '网单单价', field: 'orderProductPrice', sortable: false},
      {title: '网单数量', field: 'orderProductNumber', sortable: false},
      {title: '商城优惠券金额', field: 'couponAmount', sortable: false},
      {title: '平台承担优惠券金额', field: 'platformCouponAmount', sortable: false},
      {title: '网单付款金额', field: 'orderProductAmount', sortable: false},
      {title: '保本价', field: 'guaranteePrice', sortable: false},
      {title: '保本价差额/费用池优惠券', field: 'subductionPrice', sortable: false},
      {title: '下单时间', field: 'orderAddTime', sortable: false},
      {title: '被闸时间', field: 'createTime', sortable: false},
      {title: '被闸原因', field: 'lockReason', sortable: false},
      {title: '放行操作人', field: 'operator', sortable: false},
      {title: '责任人', field: 'responsiblePerson', sortable: false},
      {title: '放行原因', field: 'unlockReason', sortable: false},
      {title: '放行时间', field: 'unlockTime', sortable: false},
      {title: '闸口状态', field: 'gateStatus', sortable: false,
        formatter: function (val) {
          if (val == "0") {
            return "失效";
          } else if (val == "1") {
            return "生效";
          }
        }
      },
      {title: '闸口类型', field: 'gateType', sortable: false,
        formatter: function (val) {
          if (val == "1") {
            return "保本价";
          } else if (val == "2") {
            return "费用池";
          }
        }
      }
    ]],
  });
});

$(document).ready(function(){
  getGuaranteePriceChannel();
  $("#channel").combobox({
    onChange: function (newValue, oldValue) {
      var channelCode = $("#channel").combobox('getValue')
      if (channelCode == ''){
        $('#source').combobox('clear');
        var sourceSelect =[{ 'text':'请选择','id':''}];
        $("#source").combobox("loadData", sourceSelect);
      }
      else {
        getGuaranteePriceSource(channelCode);
      }
    }
  })

  $("#source").combobox({
    onChange: function (newValue, oldValue) {
      var sourceCode = $("#source").combobox('getValue')
      if (sourceCode == ''){
        $('#industry').combobox('clear');
        var industrySelect =[{ 'text':'请选择','id':''}];
        $("#industry").combobox("loadData", industrySelect);
      }
      else {
        $('#industry').combobox('clear');
        jQuery.getJSON("/guaranteePriceUnLockController/getOrderPriceIndustryBySource?source="+sourceCode, function(result){
          var industrySelect =[{ 'text':'请选择','id':''}];
          for(var i=0;i<result.data.length;i++){
            industrySelect.push({"text":result.data[i].industry_name,"id":result.data[i].industry_code});
          }
          $("#industry").combobox("loadData", industrySelect);
        });
      }
    }
  })

  $("#industry").combobox({
    onChange: function (newValue, oldValue) {
      var industryCode = $("#industry").combobox('getValue')
      if (industryCode == ''){
        $('#productGroup').combobox('clear');
        var productGroupSelect =[{ 'text':'请选择','id':''}];
        $("#productGroup").combobox("loadData", productGroupSelect);
      }
      else {
        $('#productGroup').combobox('clear');
        jQuery.getJSON("/guaranteePriceUnLockController/getOrderPriceProductGroup?industry="+industryCode, function(result){
          var productGroupSelect =[{ 'text':'请选择','id':''}];
          for(var i=0;i<result.data.length;i++){
            productGroupSelect.push({"text":result.data[i].product_group_name,"id":result.data[i].product_group});
          }
          $("#productGroup").combobox("loadData", productGroupSelect);
        });
      }
    }
  })

});

function getGuaranteePriceChannel(){
  $('#channel').combobox('clear');
  jQuery.getJSON("/guaranteePriceUnLockController/getGuaranteePriceChannel", function(result){
    var channelSelect =[{ 'text':'请选择','id':''}];
    for(var i=0;i<result.data.length;i++){
      channelSelect.push({"text":result.data[i].channel_name,"id":result.data[i].channel_code});
    }
    $("#channel").combobox("loadData", channelSelect);
  });

}

function getGuaranteePriceSource(channel){
  $('#source').combobox('clear');
  jQuery.getJSON("/guaranteePriceUnLockController/getGuaranteePriceSource?channel="+channel, function(result){
    var sourceSelect =[{ 'text':'请选择','id':''}];
    for(var i=0;i<result.data.length;i++){
      sourceSelect.push({"text":result.data[i].order_source_name,"id":result.data[i].order_source});
    }
    $("#source").combobox("loadData", sourceSelect);
  });
}

//日期大小比较，开始日期必须小于等于结束日期
function checkDate(){
  var startDate=jQuery("#startTime").val();
  var endDate=jQuery("#endTime").val();
  if(startDate!=""&&endDate!=""){
    var s=new Date(startDate.replace(/\-/g,"\/"));
    var e=new Date(endDate.replace(/\-/g,"\/"));
    if(s>e){
      alert("开始日期必须小于或等于结束日期！");
      return false;
    }
  }
  return true;
}

