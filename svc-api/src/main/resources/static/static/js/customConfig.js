/**
 *加载用户的表的列的配置信息
 */
var CustomConfig = {
  __moduleName__: '',
  "load": function ($datagrid, moduleName) {
    CustomConfig.__moduleName__ = moduleName || __moduleName__;
    //返回列的名字的列表
    return $.post('/customerConfig/getconfig',
        {moduleName: CustomConfig.__moduleName__})
    .error(
        function (a, b, c) {
          console.log('获取表配置的时候出错了：' + a);
        })
    .success(function (res) {
      CustomConfig.apply(res.data, $datagrid);
    });
  },
  "update": function (fields) {
    //保存列的名字的列表
    $.post('/customerConfig/setconfig',
        {
          moduleName: CustomConfig.__moduleName__,
          fields: fields.join(",,")
        }).error(
        function (a, b, c) {
          console.log('保存表配置的时候出错了：' + a);
        });
  },
  "apply": function (fields, $datagrid) {
    if (fields !== null && fields !== '') {
      //根据给定的名字的列表得到列的列表，并且隐藏未选择的列
      var fields = fields.split(",,");
      var fieldMap = {};
      for (var index in fields) {
        var field = fields[index];
        fieldMap[field] = field;
      }
      var allCols = $datagrid.datagrid('getColumnFields');
      for (var index in allCols) {
        var field = allCols[index];
        if (fieldMap[field] == null) {
          $datagrid.datagrid('hideColumn', field);
        }
      }
    }
    createDatagridCtxMenu($datagrid);
  }
}