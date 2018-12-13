
var dg = {
    fit: true,
    fitColumns: true,
    toolbar: '#datagridToolbar',
    striped: true,
    singleSelect: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 20, 30, 40, 50],
    rownumbers: true
}

var dataGrid = null;
$(function () {
  dataGrid = $("#dg").datagrid(dg);
  $('input[type=radio][name=Fruit]').change(function(){
    window.location.href = this.value
  })
});

