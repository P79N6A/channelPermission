var param;
$(function () {
  getGuaranteePriceChannel();
  getGuaranteePriceSource();
  // $('#orderStatus').combobox('clear');

  var datagrid = $('#datagrid_orderPriceGate').datagrid(
      datagridOptions_orderPriceGate);

  //限制开始时间小于截止时间
  $("#createTimeMin").datebox({
    onSelect: function (beginDate) {
      $('#createTimeMax').datebox().datebox('calendar').calendar({
        validator: function (date) {
          return beginDate < date;//<=
        }
      });
    }
  });

  var formParam = $('#paramForm_orderPriceGate').serializeObject();
  param = {
    // id:$("#id").val(),
    orderSn: formParam.orderSn,
    cOrderSn: formParam.cOrderSn,
    channel: formParam.channel,
    orderSource: formParam.orderSource,
    orderStatusCode: formParam.orderStatusCode,
    gateStatus: formParam.gateStatus,
    gateType: formParam.gateType,
    /*    industryCode:$(".industryCode").val(),
     productGroup:$(".productGroup").val(),*/
    createTimeMin: $("#createTimeMin").datetimebox('getValue'),
    createTimeMax: $("#createTimeMax").datetimebox('getValue')
  };
  $("#searchBtn_orderPriceGate").on('click', function (event) {
    // param = $('#paramForm_orderPriceGate').serializeObject();
    var formParam = $('#paramForm_orderPriceGate').serializeObject();
    param = {
      // id:$("#id").val(),
      orderSn: formParam.orderSn,
      cOrderSn: formParam.cOrderSn,
      channel: formParam.channel,
      orderSource: formParam.orderSource,
      orderStatusCode: formParam.orderStatusCode,
      gateStatus: formParam.gateStatus,
      gateType: formParam.gateType,
      /*    industryCode:$(".industryCode").val(),
       productGroup:$(".productGroup").val(),*/
      createTimeMin: $("#createTimeMin").datetimebox('getValue'),
      createTimeMax: $("#createTimeMax").datetimebox('getValue')
    };
    console.log(param)
    $('#datagrid_orderPriceGate').datagrid('load', param);
    event.preventDefault();
  });

  $("#resetBtn_orderPriceGate").on('click', function (event) {
    event.preventDefault();
    $('#paramForm_orderPriceGate').form('reset');
  });

  //点击导出
  $('#exportBtn_orderPriceGate').click(function () {
    if (!datagrid) {
      $.messager.alert('提示', '请查询！', 'info');
      return;
    }
    $.messager.confirm('确认', '确定要导出吗？', function (r) {
      if (r) {
        $('#exportData').val(JSON.stringify(param));
        $('#paramForm_orderPriceGate').attr("action",
            '/order/exportOrderPriceGateList.html');
        $('#paramForm_orderPriceGate').submit();
      }
    });
  });
});

var datagridOptions_orderPriceGate = {
  fit: true,
  pagination: true,
  singleSelect: true,
  pageSize: 100,
  pageList: [100, 200, 300],
  nowrap: true,
  rownumbers: true,
  selectOnCheck: true,
  checkOnSelect: true,

  // fitColumns: true,
  url: '/order/getOrderPriceGateList.html',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [
    [
      {
        field: 'orderSource',
        title: '订单来源',
        width: 140,
        align: 'center',
      },
      {
        field: 'channelCode',
        title: '渠道',
        width: 90,
        align: 'center'
      },
      {
        field: 'industryCode',
        title: '产业',
        width: 90,
        align: 'center'
      },
      {
        field: 'productGroup',
        title: '产品组',
        width: 90,
        align: 'center'
      },
      {
        field: 'orderSn',
        title: '订单号',
        width: 170,
        align: 'center'
      },
      {
        field: 'orderStatus',
        title: '订单状态',
        width: 70,
        align: 'center'
      },
      {
        field: 'corderSn',
        title: '网单号',
        width: 160,
        align: 'center'
      },
      {
        field: 'sku',
        title: '物料编码',
        width: 130,
        align: 'center'
      },
      {
        field: 'cateName',
        title: '品类名称',
        width: 80,
        align: 'center'
      },
      {
        field: 'productName',
        title: '型号',
        width: 80,
        align: 'center'
      },
      {
        field: 'orderProductPrice',
        title: '网单单价',
        width: 110,
        align: 'center'
      },
      {
        field: 'couponAmount',
        title: '商城优惠券',
        width: 70,
        align: 'center'
      },
      {
        field: 'platformCouponAmount',
        title: '平台优惠券',
        width: 70,
        align: 'center'
      },
      {
        field: 'orderProductAmount',
        title: '网单表productAmount',
        width: 140,
        align: 'center'
      },
      {
        field: 'orderProductNumber',
        title: '网单数量',
        width: 60,
        align: 'center'
      },
      {
        field: 'guaranteePrice',
        title: '保本价',
        width: 80,
        align: 'center'
      },
      {
        field: 'subductionPrice',
        title: '保本价差额/费用池优惠券',
        width: 145,
        align: 'center'
      },
      {
        field: 'orderAddTime',
        title: '下单时间',
        width: 140,
        align: 'center'
      },
      {
        field: 'createTime',
        title: '被闸时间',
        width: 140,
        align: 'center'
      },
      {
        field: 'lockReason',
        title: '被闸原因',
        width: 170,
        align: 'center'
      },
      {
        field: 'gateStatus',
        title: '闸口状态',
        width: 60,
        align: 'center',
        formatter: function (value) {
          if (value == '0') {
            return '放行';
          }
          if (value == '1') {
            return '闸住';
          }
          return value;
        }
      },
      {
        field: 'gateType',
        title: '闸口类型',
        width: 60,
        align: 'center',
        formatter: function (value) {
          if (value == '1') {
            return '保本价';
          }
          if (value == '2') {
            return '费用池';
          }
          return value;
        }
      },
      {
        field: 'operator',
        title: '放行操作人',
        width: 50,
        align: 'center'
      },
      {
        field: 'responsiblePerson',
        title: '责任人',
        width: 50,
        align: 'center'
      },
      {
        field: 'unlockReason',
        title: '放行原因',
        width: 170,
        align: 'center'
      },
      {
        field: 'unlockTime',
        title: '放行时间',
        width: 140,
        align: 'center'
      }]],
  toolbar: '#datagridToolbar_orderPriceGate',
  striped: true,
  onBeforeLoad: function (param) {
    var firstLoad = $(this).attr("firstLoad");
    if (firstLoad == "false" || typeof (firstLoad) == "undefined") {
      $(this).attr("firstLoad", "true");
      return false;
    }
    return true;
  }
};

function getGuaranteePriceChannel() {
  $('#channelCode').combobox('clear');
  jQuery.getJSON("/order/getGuaranteePriceChannel", function (result) {
    var arrayObj = result.data;
    arrayObj.unshift({channel_name: "全部", channel_code: ""});
    result.data = arrayObj;

    $("#channelCode").combobox({
      data: result.data,
      valueField: 'channel_code',
      textField: 'channel_name',
      value: '',
      onChange: function (channel) {
        if (channel == '') {
          return;
        }
        $.getJSON('/order/getGuaranteePriceSource?channel=' + channel,
            function (source) {
              source.data.unshift(
                  {order_source_name: '全部', order_source: ''});
              $('#orderSource').combobox({
                data: source.data,
                valueField: 'order_source', //值字段
                textField: 'order_source_name', //显示的字段
                panelHeight: 'auto',
                editable: false,//不可编辑，只能选择
                value: ''
              });
            });

      }
    });

  });
}
var getGuaranteePriceSource = function () {
  var array = [{order_source_name: '全部', order_source: ''}];
  $('#orderSource').combobox({
    data: array,
    valueField: 'order_source', //值字段
    textField: 'order_source_name', //显示的字段
    panelHeight: 'auto',
    editable: false,//不可编辑，只能选择
    value: ''
  });
};
