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
    {title: '主库转运时效（小时）', field: 'masterShippingTime', sortable: false},
    {title: '主库运输距离（公里）', field: 'masterDistance', sortable: false},
    {title: '中心库转运时效（小时）', field: 'centerShippingTime', sortable: false},
    {title: '中心库运输距离（公里）', field: 'centerDistance', sortable: false},
    {title: '是否开通', field: 'flag', sortable: false}
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
  var datagrid = $('#bigStoragesRelaTable').datagrid(datagrid_bigStoragesRela);

  $("#resetBtn").on('click', function (event) {
    event.preventDefault();
    $('#filterForm').form('reset');
  });

  $("#addBtn").on('click', function (event) {
    $('#addForm_bigStoragesRela').form('reset');
    $('#addForm_bigStoragesRela').find('[__actType]').val('add');
    $('#addDlg_bigStoragesRela').dialog({'title': '新增'});
    getStorages();
    $('#addDlg_bigStoragesRela').dialog('open');
  });
  $("#addDlgSaveBtn").on('click', function () {
    if (!$('#addForm_bigStoragesRela').form('validate')) {
      return;
    }
    var actType = $('#addForm_bigStoragesRela').find('[__actType]').val();
    var postParam;
    if (actType == 'add') {
      postParam = {
        'centerStorage': $("#centerStorage").combobox('getValue'),
        'masterStorage': $("#masterStorage").combobox('getValue'),
        'storage': $("#storage").combobox('getValue'),
        'masterShippingTime': $("#masterShippingTime").val(),
        'masterDistance': $("#masterDistance").val(),
        'centerShippingTime': $("#centerShippingTime").val(),
        'centerDistance': $("#centerDistance").val(),
        'flag': $("#flag option:selected").val()
      }
    } else {
      postParam = {
        'id': $("#id").val(),
        'centerStorage': $("#centerStorage").combobox('getValue'),
        'masterStorage': $("#masterStorage").combobox('getValue'),
        'storage': $("#storage").combobox('getValue'),
        'masterShippingTime': $("#masterShippingTime").val(),
        'masterDistance': $("#masterDistance").val(),
        'centerShippingTime': $("#centerShippingTime").val(),
        'centerDistance': $("#centerDistance").val(),
        'flag': $("#flag option:selected").val()
      }
    }
    $.ajax({
      type: "post",
      url: '/bigStoragesRela/saveOrUpdateBig',
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

    $('#addDlg_bigStoragesRela').dialog('close');
    $('#bigStoragesRelaTable').datagrid('reload');
  });
  $("#editBtn").on('click', function () {
    var selected = datagrid.datagrid('getSelected');
    $('#addDlg_bigStoragesRela').dialog({'title': '修改'});
    if (selected !== null) {
      $('#addForm_bigStoragesRela').form('load', selected);
      $('#addForm_bigStoragesRela').find('[__actType]').val('edit');
      getStorages();
      $("#centerStorage").combobox('setValue',selected.centerCode);
      $("#masterStorage").combobox('setValue',selected.masterCode);
      $("#storage").combobox('setValue',selected.code);
      $("#masterShippingTime").val(selected.masterShippingTime);
      $("#masterDistance").val(selected.masterDistance);
      $("#centerShippingTime").val(selected.centerShippingTime);
      $("#centerDistance").val(selected.centerDistance);
      $("#flag").val(selected.flag);
      $('#addDlg_bigStoragesRela').dialog('open');
    } else {
      alert('请选择一条数据');
    }
  });

});

//点击查询
$("#searchBtn").on('click', function (event) {

  //加载分页
  datagrid = $('#bigStoragesRelaTable').datagrid({
    url: "/bigStoragesRela/getBigStoragesRela",
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
      {title: '主库转运时效（小时）', field: 'masterShippingTime', sortable: false},
      {title: '主库运输距离（公里）', field: 'masterDistance', sortable: false},
      {title: '中心库转运时效（小时）', field: 'centerShippingTime', sortable: false},
      {title: '中心库运输距离（公里）', field: 'centerDistance', sortable: false},
      {title: '是否开通', field: 'flag', sortable: false,
        formatter: function(val) {
          if (val == '1') {
            return "是";
          } else if (val == '0') {
            return "否";
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
  jQuery.getJSON("/bigStoragesRela/getStorages?type=1", function(result){
    var storageSelect = [];
    for(var i=0;i<result.data.length;i++){
      $("#dialog-form-input-channel-code").append("<option value=''></option>");
      storageSelect.push({"id":result.data[i].code,"text":result.data[i].name});
    }
    $("#centerStorage").combobox("loadData", storageSelect);
    $("#masterStorage").combobox("loadData", storageSelect);
    $("#storage").combobox("loadData", storageSelect);
  });

}