var datagridOptions_orderPriceSourceChannel = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    url: '',
    frozenColumns: [[{
        field: 'id', checkbox: false, hidden:false
    }]],
    columns: [[
        {title: '渠道名称', field: 'channelName', sortable: false},
        {title: '订单来源名称', field: 'orderSourceName', sortable: false},
        {title: '闸口状态', field: 'restrictStatus', sortable: false},
        {title: '闸口类型', field: 'restrictType', sortable: false},
        {title: '负责人', field: 'responsePerson', sortable: false},
        {title: '手机号码', field: 'mobile', sortable: false},
        {title: '电子邮件', field: 'email', sortable: false},
        {title: '发送类型', field: 'sendType', sortable: false},
    ]],
    toolbar: '#datagridToolbar_orderPriceSourceChannel',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
};
$(function () {
    var datagrid = $('#datagrid_orderPriceSourceChannel').datagrid(datagridOptions_orderPriceSourceChannel);

    $("#addBtn_orderPriceSourceChannel").on('click', function (event) {
        $('#addForm_orderPriceSourceChannel').form('reset');
        $('#addForm_orderPriceSourceChannel').find('[__actType]').val('add');
        $('#addDlg_orderPriceSourceChannel').dialog({'title': '新增'});
        getGuaranteePriceChannelAdd();
        $('#addDlg_orderPriceSourceChannel').dialog('open');
    });
    $("#addDlgSaveBtn_orderPriceSourceChannel").on('click', function () {
        if (!$('#addForm_orderPriceSourceChannel').form('validate')) {
            return;
        }
        var actType = $('#addForm_orderPriceSourceChannel').find('[__actType]').val();
        var postParam;
        if (actType == 'add') {
          postParam = {
            'channelCode': $("#channelAdd").combobox('getValue'),
            'channelName': $("#channelAdd").combobox('getText'),
            'sourceCode': $("#sourceAdd").combobox('getValue'),
            'sourceName': $("#sourceAdd").combobox('getText'),
            'status': $("#statusAdd option:selected").val(),
            'gateType': $("#gateTypeAdd option:selected").val(),
            'person': $("#personAdd").val(),
            'mobile': $("#mobileAdd").val(),
            'email': $("#emailAdd").val(),
            'sendType': $("#sendTypeAdd option:selected").val()
          }
        } else {
          postParam = {
            'id': $("#id").val(),
            'channelCode': $("#channelAdd").combobox('getValue'),
            'channelName': $("#channelAdd").combobox('getText'),
            'sourceCode': $("#sourceAdd").combobox('getValue'),
            'sourceName': $("#sourceAdd").combobox('getText'),
            'status': $("#statusAdd option:selected").val(),
            'gateType': $("#gateTypeAdd option:selected").val(),
            'person': $("#personAdd").val(),
            'mobile': $("#mobileAdd").val(),
            'email': $("#emailAdd").val(),
            'sendType': $("#sendTypeAdd option:selected").val()
          }
        }
        $.ajax({
          type: "post",
          url: '/guaranteePriceUnLockController/saveOrderPriceSourceChannel',
          data: postParam,
          dataType: 'json',
          success: function (data) {
            if (data.success == true) {
              alert("保存成功");
            } else {
              alert("保存失败:"+ data.message);
            }
          },
          error: function () {
            alert("请求错误");
          }
        });

        $('#addDlg_orderPriceSourceChannel').dialog('close');
        $('#datagrid_orderPriceSourceChannel').datagrid('reload');
    });
    $("#editBtn_orderPriceSourceChannel").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_orderPriceSourceChannel').dialog({'title': '修改'});
        if (selected !== null) {
          $('#addForm_orderPriceSourceChannel').form('load', selected);
          $('#addForm_orderPriceSourceChannel').find('[__actType]').val('edit');
          getGuaranteePriceChannelAdd();
          getGuaranteePriceSourceAdd(selected.channelCode);
          $("#channelAdd").combobox('setValue',selected.channelCode);
          $("#sourceAdd").combobox('setValue',selected.orderSource);
          $("#statusAdd").val(selected.status);
          $("#gateTypeAdd").val(selected.gateType);
          $("#personAdd").val(selected.person);
          $("#mobileAdd").val(selected.mobile);
          $("#emailAdd").val(selected.email);
          $("#sendTypeAdd").val(selected.sendType);
          $('#addDlg_orderPriceSourceChannel').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_orderPriceSourceChannel").on('click', function (event) {
        event.preventDefault();
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
          var id = selected.id;
            confirm('确定删除？', function (r) {
              if (r == true) {
                $.post("/guaranteePriceUnLockController/deleteOrderPriceSourceChannelById", {id: id}, function (data) {
                  if (data.text = "success") {
                    $.messager.alert('提示', "删除成功");
                    $('#datagrid_orderPriceSourceChannel').datagrid('reload');
                  } else {
                    $.messager.alert('提示', "删除失败");
                    $('#datagrid_orderPriceSourceChannel').datagrid('reload');
                  }
                });
              }
            })

        } else {
            alert('请选择一条数据');
        }
    });
    $("#resetBtn_orderPriceSourceChannel").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderPriceSourceChannel').form('reset');
    });
});

$("#searchBtn_orderPriceSourceChannel").on('click', function (event) {

  //加载分页
  datagrid = $('#datagrid_orderPriceSourceChannel').datagrid({
    url: "/guaranteePriceUnLockController/getOrderPriceSourceChannelList",
    fit: true,
    fitColumns: true,
    singleSelect: true,//多选
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
    queryParams: {
      channel: $('#channel').combobox('getValue'),
      source: $('#source').combobox('getValue'),
      status: $("#status option:selected").val(),
      gateType: $("#gateType option:selected").val(),
      sendType: $("#sendType option:selected").val(),
      person: $("#person").val()
    },
    frozenColumns: [[{
      field: 'id', checkbox: false, hidden:true
    }]],
    columns: [[
      {title: '渠道名称', field: 'channelName', sortable: false},
      {title: '订单来源名称', field: 'orderSourceName', sortable: false},
      {title: '闸口状态', field: 'status', sortable: false,
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
      },
      {title: '负责人', field: 'person', sortable: false},
      {title: '手机号码', field: 'mobile', sortable: false},
      {title: '电子邮件', field: 'email', sortable: false},
      {title: '发送类型', field: 'sendType', sortable: false,
        formatter: function (val) {
          if (val == "1") {
            return "短信";
          } else if (val == "2") {
            return "邮件";
          } else if (val == "3") {
            return "短信和邮件";
          } else if (val == "0") {
            return "不通知";
          }
        }
      }
    ]]
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

  $("#channelAdd").combobox({
    onChange: function (newValue, oldValue) {
      var channelCode = $("#channelAdd").combobox('getValue')
      if (channelCode == ''){
        $('#sourceAdd').combobox('clear');
        var sourceSelect = [];
        $("#sourceAdd").combobox("loadData", sourceSelect);
      }
      else {
        getGuaranteePriceSourceAdd(channelCode);
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

function getGuaranteePriceChannelAdd(){
  $('#channelAdd').combobox('clear');
  jQuery.getJSON("/guaranteePriceUnLockController/getGuaranteePriceInvStockChannel", function(result){
    var channelSelect = [];
    jQuery.each(result.data,function(i, v){
      jQuery.each(v,function(key, value){
        $("#dialog-form-input-channel-code").append("<option value=" + key + ">" + value + "</option>");
        channelSelect.push({"text":value,"id":key});
      });
    });
    $("#channelAdd").combobox("loadData", channelSelect);
  });

}

function getGuaranteePriceSourceAdd(channel){
  $('#sourceAdd').combobox('clear');
  jQuery.getJSON("/guaranteePriceUnLockController/getGuaranteePriceInvChannel2OrderSource?channelCode="+channel, function(result){
    var sourceSelect = [];
    for(var i=0;i<result.data.length;i++){
      sourceSelect.push({"text":result.data[i].note,"id":result.data[i].order_source});
    }
    $("#sourceAdd").combobox("loadData", sourceSelect);
  });
}

//日期大小比较，开始日期必须小于等于结束日期
function checkDate(){
  var startDate=jQuery("#startDate").val();
  var endDate=jQuery("#endDate").val();
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
