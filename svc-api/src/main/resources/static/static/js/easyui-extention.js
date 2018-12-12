var shenflejffes = {a: window.alert};
var rawAlert = shenflejffes.a;
(function () {
  var aaaa = function (msg, icon, fun) {
    var msg='<div style="max-height: 200px;max-width:100%;overflow-y: auto;word-break: break-all;">'+msg+'</div>';
    if (icon) {
      if (typeof icon === 'function') {
        $.messager.alert(' ', msg, 'info', icon);
      } else {
        $.messager.alert(' ', msg, icon, fun);
      }
    } else {
      $.messager.alert(' ', msg);
    }
  };
  window.alert = aaaa;
})();

function confirm(msg, cb) {
  $.messager.confirm(' ', msg, cb);
}

$.fn.datagrid.defaults.loadFilter = function (data) {
  //{"errorMessages":["未登录或登录超时,请登陆后再进行操作"],"fieldErrors":{},"success":false,"warningMessages":[]}
  if (!data.success && data.errorMessages && data.errorMessages.length > 0) {
    var err = data.errorMessages.join(";");
    return [];
  }
  var data = data.data;
  var records = data.records;
  var rows = data.rows;
  data.rows = data.records;
  data.total = data.totalCount;
  return data;
};
var __cmenu__;
$.fn.datagrid.defaults.onHeaderContextMenu = function (e, field) {
  e.preventDefault();
  if (!__cmenu__) {
    createColumnMenu();
  }
  __cmenu__.menu('show', {
    left: e.pageX,
    top: e.pageY
  });
};

function createDatagridCtxMenu($datagird) {
  __cmenu__ = $('<div/>').appendTo('body');
  __cmenu__.menu({
    onClick: function (item) {
      if (item.iconCls == 'icon-ok') {
        $datagird.datagrid('hideColumn', item.name);
        __cmenu__.menu('setIcon', {
          target: item.target,
          iconCls: 'icon-empty'
        });
      } else {
        $datagird.datagrid('showColumn', item.name);
        __cmenu__.menu('setIcon', {
          target: item.target,
          iconCls: 'icon-ok'
        });
      }
      //获得表格的所有的列名
      var allFields = $datagird.datagrid('getColumnFields');
      var showedFields = [];
      for (var index in allFields) {
        var field = allFields[index];
        var column = $datagird.datagrid('getColumnOption', field);
        if (!column.hidden) {
          showedFields.push(column.field);
        }
      }
      //保存这些列名数组
      CustomConfig.update(showedFields);
    }
  });
  var fields = $datagird.datagrid('getColumnFields');
  for (var i = 0; i < fields.length; i++) {
    var field = fields[i];
    var col = $datagird.datagrid('getColumnOption', field);
    var iconcls = col.hidden ? 'icon-empty' : 'icon-ok';
    __cmenu__.menu('appendItem', {
      text: col.title,
      name: field,
      iconCls: iconcls
    });
  }
}

/*$.fn.datebox.defaults.buttons.push({
 text: '清空',
 handler: function (target) {
 var $t = $(target);
 $t.datebox('setValue', '');
 $t.datebox('hidePanel');
 }
 });*/

$.fn.numberbox.defaults.icons = [{
  iconCls: 'icon-clear',
  handler: function (event) {
    var $this = $(this);
    $this.closest('.textbox').prev('.textbox-f').numberbox('clear');
  }
}];
$.fn.textbox.defaults.icons = [{
  iconCls: 'icon-clear',
  handler: function (event) {
    var $this = $(this);
    $this.closest('.textbox').prev('.textbox-f').textbox('clear');
  }
}];
$.fn.datebox.defaults.icons = [{
  iconCls: 'icon-clear',
  handler: function (event) {
    var $this = $(this);
    $this.closest('.textbox').prev('.textbox-f').datebox('clear');
  }
}];


$.fn.combobox.defaults.icons = [{
  iconCls: 'icon-clear',
  handler: function (event) {
    var $this = $(this);
    $this.closest('.textbox').prev('.textbox-f').combobox('clear');
  }
}];
$.fn.combogrid.defaults.icons =  [{
  iconCls: 'icon-clear',
  handler: function (event) {
    var $this = $(this);
    $this.closest('.textbox').prev('.textbox-f').combogrid('clear');
  }
}];

var codeReg = /\w+/;
//定义一个validatebox的验证类型，用来验证编码
$.extend($.fn.validatebox.defaults.rules, {
  codeType: {
    validator: function (value, param) {
      return false;
    },
    message: '请输入正确的编码信息，由字母或数字组成'
  }
});
$.fn.datebox.defaults.editable = false;

$.ajaxSetup({
  complete: function (xhr, status, a, b, c) {
    /*if (xhr.responseJSON) {
     console.log(xhr.responseJSON);
     var data=xhr.responseJSON;
     if(!data.success&&data.errorMessages&&data.errorMessages.length>0){
     var err=data.errorMessages.join(";");
     alert(err);
     return [];
     }
     }*/
    if (xhr.responseJSON) {
      var data = xhr.responseJSON;
      if (!data.success && data.errorMessages && data.errorMessages.length
          > 0) {
        var err = data.errorMessages.join(";");
        alert(err);
        /*var err = data.errorMessages[0];
        if (err === "未登录或登录超时,请登陆后再进行操作") {
          alert(err);
        }*/
      }
    }
  },
  /*error: function (xhr, statusText) {
    if (statusText === 'error') {
      if (xhr.responseJSON) {
        var data = xhr.responseJSON;
        if (!data.success && data.errorMessages && data.errorMessages.length
            > 0) {
          var err = data.errorMessages.join(";");
          alert(err);
        }
      }
    }
  }*/
});
function slideUpSlideClk(t) {
  var $this = $(t);
  if ($this.hasClass('open')) {
    $this.removeClass('open');
    setTimeout(function () {
      $this.addClass('close');
    }, 0);
    $(".search-panel").slideUp();
  } else {
    $this.removeClass('close');
    setTimeout(function () {
      $this.addClass('open');
    }, 0);
    $(".search-panel").slideDown();
  }
  setTimeout(function () {
    $('#datagrid').datagrid('resize');
  }, 500);
}
