
var datagridData_orderForecastCoefficientLog={'data':{'records':[
      {
          'channelCode': '天猫渠道', sortable: true,
          'productGroup': '家用空调', sortable: true,
          'secCode': 'XNWA', sortable: true,
          'coefficient': '0.00010000', sortable: true,
          'createtime':'2014-07-15 21:19:41',sortable:true,
          'createuser':'',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },
      {
          'channelCode': '商城渠道', sortable: true,
          'productGroup': '智饮机', sortable: true,
          'secCode': 'FSWA', sortable: true,
          'coefficient': '0.04470000', sortable: true,
          'createtime':'2014-07-15 21:19:41',sortable:true,
          'createuser':'',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },
      {
          'channelCode': '大客户渠道', sortable: true,
          'productGroup': '社会化彩电', sortable: true,
          'secCode': 'SHWA', sortable: true,
          'coefficient': '0.21210000', sortable: true,
          'createtime':'2014-07-15 21:19:41',sortable:true,
          'createuser':'',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },

      {
          'channelCode': '天猫渠道', sortable: true,
          'productGroup': '冰箱', sortable: true,
          'secCode': 'AKWA', sortable: true,
          'coefficient': '0.0014', sortable: true,
          'createtime':'2014/7/15 21:19',sortable:true,
          'createuser':'冯希柱',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },
      {
          'channelCode': '天猫渠道', sortable: true,
          'productGroup': '冷柜', sortable: true,
          'secCode': 'AKWA', sortable: true,
          'coefficient': '0.0014', sortable: true,
          'createtime':'2014/7/15 21:19',sortable:true,
          'createuser':'冯希柱',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },
      {
          'channelCode': '天猫渠道', sortable: true,
          'productGroup': '家用空调', sortable: true,
          'secCode': 'AKWA', sortable: true,
          'coefficient': '0.0026', sortable: true,
          'createtime':'2014/7/15 21:19',sortable:true,
          'createuser':'冯希柱',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },
      {
          'channelCode': '天猫渠道', sortable: true,
          'productGroup': '波轮洗衣机', sortable: true,
          'secCode': 'AKWA', sortable: true,
          'coefficient': '0.0014', sortable: true,
          'createtime':'2014/7/15 21:19',sortable:true,
          'createuser':'冯希柱',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },
      {
          'channelCode': '天猫渠道', sortable: true,
          'productGroup': '滚筒洗衣机', sortable: true,
          'secCode': 'AKWA', sortable: true,
          'coefficient': '0.0009', sortable: true,
          'createtime':'2014/7/15 21:19',sortable:true,
          'createuser':'冯希柱',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },
      {
          'channelCode': '天猫渠道', sortable: true,
          'productGroup': '平板电视', sortable: true,
          'secCode': 'AKWA', sortable: true,
          'coefficient': '0.0009', sortable: true,
          'createtime':'2014/7/15 21:19',sortable:true,
          'createuser':'冯希柱',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },
      {
          'channelCode': '天猫渠道', sortable: true,
          'productGroup': '电热水器', sortable: true,
          'secCode': 'AKWA', sortable: true,
          'coefficient': '0.0018', sortable: true,
          'createtime':'2014/7/15 21:19',sortable:true,
          'createuser':'冯希柱',sortable:true,
          'updatetime':'',sortable:true,
          'updateuser':'',sortable:true,
      },

],'totalCount':10}};

var datagridOptions_orderForecastCoefficientLog = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  //url: '/predictive/orderForecastCoefficientLog/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
      {title: '渠道', field: 'channelCode', sortable: true},
      {title: '库存编码', field: 'secCode', sortable: true},
      {title: '产品组', field: 'productGroup', sortable: true},
      {title: '库位系数', field: 'coefficient', sortable: true},
      {title: '创建时间', field: 'createtime',sortable:true},
      {title: '创建人', field: 'createuser',sortable:true},
      {title: '修改时间', field: 'updatetime',sortable:true},
      {title: '修改人', field: 'updateuser',sortable:true}
  ]],
  toolbar: '#datagridToolbar_orderForecastCoefficientLog',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid_orderForecastCoefficientLog').datagrid(datagridOptions_orderForecastCoefficientLog);
  datagrid.datagrid('loadData',datagridData_orderForecastCoefficientLog);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"OrderForecastCoefficientLog");
  //$("#searchPanel_orderForecastCoefficientLog").panel('resize');

  /*$("#searchBtn_orderForecastCoefficientLog").on('click', function (event) {
    var param = $('#paramForm_orderForecastCoefficientLog').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_orderForecastCoefficientLog").on('click', function (event) {
    $('#addForm_orderForecastCoefficientLog').form('reset');
    $('#addForm_orderForecastCoefficientLog').find('[__actType]').val('add');
    $('#addDlg_orderForecastCoefficientLog').dialog({'title':'新增'});
    $('#addDlg_orderForecastCoefficientLog').dialog('open');
  });
  $("#addDlgSaveBtn_orderForecastCoefficientLog").on('click', function () {
    if(!$('#addForm_orderForecastCoefficientLog').form('validate')){return;}
    var actType = $('#addForm_orderForecastCoefficientLog').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_orderForecastCoefficientLog').dialog('close');
  });
  $("#editBtn_orderForecastCoefficientLog").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_orderForecastCoefficientLog').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_orderForecastCoefficientLog').form('load', selected);
      $('#addForm_orderForecastCoefficientLog').find('[__actType]').val('edit');
      $('#addDlg_orderForecastCoefficientLog').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_orderForecastCoefficientLog").on('click',function (event) {
    event.preventDefault();
    var selected = datagrid.datagrid('getSelected');
    if (selected !== null) {
      confirm('确定删除？',function(r){
        if(r){
          alert('删除成功');
        }
      })

    }else{
      alert('请选择一条数据');
    }
  });
  $("#resetBtn_orderForecastCoefficientLog").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_orderForecastCoefficientLog').form('reset');
  });
});
