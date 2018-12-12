
var datagridData_haierLimitHistoryT={'data':{'records':[
      {
          'categoryId':'冰箱',sortable:true,
          'edChannelId':'大客户渠道',sortable:true,
          'targetNum':'798.1',sortable:true,
          'limitNum':'2868.6',sortable:true,
          'loanNum':'0',sortable:true,
          'totalNum':'614.11',sortable:true,
          'onWayNum':'465.55',sortable:true,
          'usedNum':'171.18',sortable:true,
          'availableNum':'1617.76',sortable:true
      },
      {
          'categoryId':'冰箱',sortable:true,
          'edChannelId':'天猫渠道',sortable:true,
          'targetNum':'7078.1',sortable:true,
          'limitNum':'12749.6',sortable:true,
          'loanNum':'2017',sortable:true,
          'totalNum':'453.11',sortable:true,
          'onWayNum':'-4580.55',sortable:true,
          'usedNum':'2341.27.18',sortable:true,
          'availableNum':'-160.76',sortable:true
      },
      {
          'categoryId':'冷柜',sortable:true,
          'edChannelId':'大客户渠道',sortable:true,
          'targetNum':'798.1',sortable:true,
          'limitNum':'2868.6',sortable:true,
          'loanNum':'0',sortable:true,
          'totalNum':'614.11',sortable:true,
          'onWayNum':'465.55',sortable:true,
          'usedNum':'634.18',sortable:true,
          'availableNum':'876.76',sortable:true
      },
      {
          'categoryId':'厨电',sortable:true,
          'edChannelId':'大客户渠道',sortable:true,
          'targetNum':'798.1',sortable:true,
          'limitNum':'2868.6',sortable:true,
          'loanNum':'0',sortable:true,
          'totalNum':'614.11',sortable:true,
          'onWayNum':'3453.55',sortable:true,
          'usedNum':'171.18',sortable:true,
          'availableNum':'1617.76',sortable:true
      },
      {
          'categoryId':'彩电',sortable:true,
          'edChannelId':'大客户渠道',sortable:true,
          'targetNum':'798.1',sortable:true,
          'limitNum':'2868.6',sortable:true,
          'loanNum':'0',sortable:true,
          'totalNum':'453.11',sortable:true,
          'onWayNum':'34576.55',sortable:true,
          'usedNum':'67.18',sortable:true,
          'availableNum':'436567.76',sortable:true
      },
      {
          'categoryId':'冰箱',sortable:true,
          'edChannelId':'微商',sortable:true,
          'targetNum':'798.1',sortable:true,
          'limitNum':'2868.6',sortable:true,
          'loanNum':'0',sortable:true,
          'totalNum':'459.11',sortable:true,
          'onWayNum':'56.55',sortable:true,
          'usedNum':'78.18',sortable:true,
          'availableNum':'546.76',sortable:true
      },
      {
          'categoryId':'洗衣机',sortable:true,
          'edChannelId':'模卡渠道',sortable:true,
          'targetNum':'798.1',sortable:true,
          'limitNum':'2868.6',sortable:true,
          'loanNum':'0',sortable:true,
          'totalNum':'614.11',sortable:true,
          'onWayNum':'465.55',sortable:true,
          'usedNum':'171.18',sortable:true,
          'availableNum':'1617.76',sortable:true
      },
      {
          'categoryId':'热水器',sortable:true,
          'edChannelId':'统帅渠道',sortable:true,
          'targetNum':'98.1',sortable:true,
          'limitNum':'7897.6',sortable:true,
          'loanNum':'0',sortable:true,
          'totalNum':'614.11',sortable:true,
          'onWayNum':'465.55',sortable:true,
          'usedNum':'171.18',sortable:true,
          'availableNum':'1617.76',sortable:true
      },
      {
          'categoryId':'生活家电',sortable:true,
          'edChannelId':'商城渠道',sortable:true,
          'targetNum':'7567.1',sortable:true,
          'limitNum':'6753.6',sortable:true,
          'loanNum':'0',sortable:true,
          'totalNum':'7856.11',sortable:true,
          'onWayNum':'575.55',sortable:true,
          'usedNum':'567.18',sortable:true,
          'availableNum':'65.76',sortable:true
      },
      {
          'categoryId':'空调',sortable:true,
          'edChannelId':'微店渠道',sortable:true,
          'targetNum':'7654.1',sortable:true,
          'limitNum':'345.6',sortable:true,
          'loanNum':'0',sortable:true,
          'totalNum':'345.11',sortable:true,
          'onWayNum':'678.55',sortable:true,
          'usedNum':'677.18',sortable:true,
          'availableNum':'564.76',sortable:true
      }
],'totalCount':10}};

var datagridOptions_haierLimitHistoryT = {
  fit: true,
  fitColumns: true,
  singleSelect: true,
  //url: '/auth/haierLimitHistoryT/p',
  frozenColumns: [[{
    field: 'id', checkbox: true
  }]],
  columns: [[
        {title: '品类', field: 'categoryId',sortable:true},
        {title: '渠道', field: 'edChannelId',sortable:true},
        {title: '指标', field: 'targetNum',sortable:true},
        {title: '额度', field: 'limitNum',sortable:true},
        {title: '拆借', field: 'loanNum',sortable:true},
        {title: '总库存', field: 'totalNum',sortable:true},
        {title: '在途', field: 'onWayNum',sortable:true},
        {title: '本周已用', field: 'usedNum',sortable:true},
        {title: '可用额度', field: 'availableNum',sortable:true},
  ]],
  toolbar: '#datagridToolbar_haierLimitHistoryT',
  striped: true,
  pagination: true,
  rownumbers: true,
};
$(function () {
  var datagrid = $('#datagrid_haierLimitHistoryT').datagrid(datagridOptions_haierLimitHistoryT);
  datagrid.datagrid('loadData',datagridData_haierLimitHistoryT);
  //创建表头的菜单
  //CustomConfig.load(datagrid,"HaierLimitHistoryT");
  //$("#searchPanel_haierLimitHistoryT").panel('resize');

  /*$("#searchBtn_haierLimitHistoryT").on('click', function (event) {
    var param = $('#paramForm_haierLimitHistoryT').serializeObject();
    datagrid.datagrid({queryParams: param});
    event.preventDefault();
  });*/
  $("#addBtn_haierLimitHistoryT").on('click', function (event) {
    $('#addForm_haierLimitHistoryT').form('reset');
    $('#addForm_haierLimitHistoryT').find('[__actType]').val('add');
    $('#addDlg_haierLimitHistoryT').dialog({'title':'新增'});
    $('#addDlg_haierLimitHistoryT').dialog('open');
  });
  $("#addDlgSaveBtn_haierLimitHistoryT").on('click', function () {
    if(!$('#addForm_haierLimitHistoryT').form('validate')){return;}
    var actType = $('#addForm_haierLimitHistoryT').find('[__actType]').val();
    if(actType === 'add'){
      alert( '新增成功');
    }else{
      alert('编辑成功');
    }
    $('#addDlg_haierLimitHistoryT').dialog('close');
  });
  $("#editBtn_haierLimitHistoryT").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_haierLimitHistoryT').dialog({'title':'修改'});
    if (selected !== null) {
      $('#addForm_haierLimitHistoryT').form('load', selected);
      $('#addForm_haierLimitHistoryT').find('[__actType]').val('edit');
      $('#addDlg_haierLimitHistoryT').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });
  $("#deleteBtn_haierLimitHistoryT").on('click',function (event) {
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
  $("#resetBtn_haierLimitHistoryT").on('click',function (event) {
    event.preventDefault();
    $('#paramForm_haierLimitHistoryT').form('reset');
  });
});
