var param;
$(function () {

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
    cOrderSn: formParam.cOrderSn,
    createTimeMin: $("#createTimeMin").datetimebox('getValue'),
    createTimeMax: $("#createTimeMax").datetimebox('getValue')
  };
  console.log(param)
  $("#searchBtn_orderPriceGate").on('click', function (event) {
    // param = $('#paramForm_orderPriceGate').serializeObject();
    var formParam = $('#paramForm_orderPriceGate').serializeObject();
    param = {
      cOrderSn: formParam.cOrderSn,
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

  //点击签收
  $('#exportBtn_orderPriceGate').click(function () {

    var selected = datagrid.datagrid('getSelected');
    if (selected !== null) {
      var postParam = {
        'systemId' : selected.systemId
      }
      $.ajax({
        type: "post",
        url: '/dispatch/reSign',
        data: postParam,
        dataType: 'json',
        success: function (data) {
          $.messager.alert('提示',data.message);
          $('#datagrid_orderPriceGate').datagrid('reload');
        },
        error: function () {
          $.messager.alert("订单重新签收失败");
          $('#datagrid_orderPriceGate').datagrid('reload');
        }
      });
    } else {
      $.messager.alert('提示', '请选择一条数据');
    }
  });

});

var datagridOptions_orderPriceGate = {
  fit: true,
  pagination: true,
  singleSelect: true,
  pageSize: 50,
  pageList: [50, 100, 200],
  nowrap: true,
  rownumbers: true,
  selectOnCheck: true,
  checkOnSelect: true,
  // fitColumns: true,
  url: '/dispatch/failList.html',
  frozenColumns: [[{
    field: 'systemId', checkbox: true
  }]],
  columns: [
    [
      {
        field: 'orderSn',
        title: '网单编号',
        width: 170,
        align: 'center',
      },
      {
        field: 'tbNo',
        title: 'TB编号',
        width: 170,
        align: 'center'
      },
      {
        field: 'lbxNo',
        title: 'LBX单号',
        width: 180,
        align: 'center'
      },
      {
        field: 'sku',
        title: '物料编码',
        width: 120,
        align: 'center'
      },
      /*{
        field: 'productName',
        title: '商品名称',
        width: 120,
        align: 'center'
      },*/
      {
        field: 'wwwMark',
        title: '网单类型',
        width: 80,
        align: 'center'
      },
      {
        field: 'notifyTime',
        title: '传入时间',
        width: 170,
        align: 'center',
        sortable: true
      },
      {
        field: 'errorMessage',
        title: '处理结果',
        width: 720,
        align: 'center'
      },
      {
        field: 'counts',
        title: '失败次数',
        width: 80,
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
