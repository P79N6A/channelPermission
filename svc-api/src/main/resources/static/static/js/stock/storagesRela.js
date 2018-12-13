var datagrid_bigStoragesRela = {
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
    {title: '库位编码', field: 'code', sortable: false},
    {title: '库位名称', field: 'name', sortable: false},
    {title: '主库位编码', field: 'masterCode', sortable: false},
    {title: '主库位名称', field: 'masterName', sortable: false},
    {title: '中心库位编码', field: 'centerCode', sortable: false},
    {title: '中心库位名称', field: 'centerName', sortable: false},
    {title: '多级库位编码', field: 'mulStoreCode', sortable: false,hidden: true},
    {title: '多级库位', field: 'mulStoreName', sortable: false},
    {title: '是否主库', field: 'isMaster', sortable: false}
  ]],
  toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
  striped: true,
  autoRowHeight: true,
  nowrap: true,
  pagination: true,
  rownumbers: true,
  pageSize: 10,
  pageList: [10, 20, 50],
};
$(function () {
  var datagrid = $('#storagesRelaTable').datagrid(datagrid_bigStoragesRela);

  $("#resetBtn").on('click', function (event) {
    event.preventDefault();
    $('#filterForm').form('reset');
  });

  $("#addBtn").on('click', function (event) {
    $('#addForm_bigStoragesRela').form('reset');
    $('#addForm_bigStoragesRela').find('[__actType]').val('add');
    $('#addDlg_storagesRela').dialog({'title': '新增'});
    getStorages();
    $('#addDlg_storagesRela').dialog('open');
  });
  $("#addDlgSaveBtn").on('click', function () {
    if (!$('#addForm_bigStoragesRela').form('validate')) {
      return;
    }
    var actType = $('#addForm_bigStoragesRela').find('[__actType]').val();
    var postParam;
    if (actType == 'add') {
      var mulStore = $("#mulStore").combobox('getValues').join(",");
      postParam = {
        'centerStorage': $("#centerStorage").combobox('getValue'),
        'masterStorage': $("#masterStorage").combobox('getValue'),
        'storage': $("#storage").combobox('getValue'),
        'mulStore': mulStore,
        'isMaster': $("#isMaster option:selected").val()
      }
    } else {
      postParam = {
        'id': $("#id").val(),
        'centerStorage': $("#centerStorage").combobox('getValue'),
        'masterStorage': $("#masterStorage").combobox('getValue'),
        'storage': $("#storage").combobox('getValue'),
        'mulStore': $("#mulStore").combobox('getValues'),
        'isMaster': $("#isMaster option:selected").val()
      }
    }
    $.ajax({
      type: "post",
      url: '/bigStoragesRela/saveOrUpdate',
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

    $('#addDlg_storagesRela').dialog('close');
    $('#storagesRelaTable').datagrid('reload');
  });
  $("#editBtn").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_storagesRela').dialog({'title': '修改'});
    if (selected !== null) {
      $('#addForm_bigStoragesRela').form('load', selected);
      $('#addForm_bigStoragesRela').find('[__actType]').val('edit');
      getStorages();
      $("#centerStorage").combobox('setValue',selected.centerCode);
      $("#masterStorage").combobox('setValue',selected.masterCode);
      $("#storage").combobox('setValue',selected.code);
      var muStore = selected.mulStoreCode;
      $("#mulStore").combobox('setValues',muStore.split(","));
      $("#isMaster").val(selected.isMaster);
      $('#addDlg_storagesRela').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });

});

//点击查询
$("#searchBtn").on('click', function (event) {

  //加载分页
  datagrid = $('#storagesRelaTable').datagrid({
    url: "/bigStoragesRela/getStoragesRela",
    fit: true,
    fitColumns: false,
    singleSelect: true,//是否单选
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 10,
    pageList: [10, 20, 50],
    queryParams: {
      code: $("#code").val(),
      masterCode: $("#masterCode").val(),
      centerCode: $("#centerCode").val(),
      name: $("#name").val(),
      masterName: $("#masterName").val(),
      centerName: $("#centerName").val()
    },
    frozenColumns: [[{
      field: 'id',
      checkbox: false,
      hidden:true
    }]],
    columns: [[
      {title: '库位编码', field: 'code', sortable: false},
      {title: '库位名称', field: 'name', sortable: false},
      {title: '主库位编码', field: 'masterCode', sortable: false},
      {title: '主库位名称', field: 'masterName', sortable: false},
      {title: '中心库位编码', field: 'centerCode', sortable: false},
      {title: '中心库位名称', field: 'centerName', sortable: false},
      {title: '多级库位编码', field: 'mulStoreCode', sortable: false,hidden: true},
      {title: '多级库位', field: 'mulStoreName', sortable: false},
      {title: '是否主库', field: 'isMaster', sortable: false,
        formatter: function(val) {
          if (val == '1') {
            return "从库";
          } else if (val == '0') {
            return "主库";
          }
        }
      }
    ]],
  });
});

function getStorages(){
  $("#centerStorage").combobox('clear');
  $("#masterStorage").combobox('clear');
  $("#storage").combobox('clear');
  $("#mulStore").combobox('clear');
  jQuery.getJSON("/bigStoragesRela/getStorages?type=2", function(result){
    var storageSelect = [];
    for(var i=0;i<result.data.length;i++){
      $("#dialog-form-input-channel-code").append("<option value=''></option>");
      storageSelect.push({"id":result.data[i].code,"text":result.data[i].name});
    }
    $("#centerStorage").combobox("loadData", storageSelect);
    $("#masterStorage").combobox("loadData", storageSelect);
    $("#storage").combobox("loadData", storageSelect);
    $("#mulStore").combobox("loadData", storageSelect);
  });

}