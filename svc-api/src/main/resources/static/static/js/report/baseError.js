var dateweek_start;
var dateweek_end;

var date_start;
var date_end;

/*var start_week=$("#start_week").val();
var end_week=$("#end_week").val();
var menu_path=$("#menu_path").val();
var interface_path=$("#interface_path").val();
var order_id=$("#order_id").val();*/

function compareDate(startdate, enddate, replaceblag, message) {
    if (replaceblag) {
        if (startdate) {
            startdate = startdate.replace("年", "");
            startdate = startdate.replace("周", "");
        }
        if (enddate) {
            enddate = enddate.replace("年", "");
            enddate = enddate.replace("周", "");
        }
    }
    if (startdate && enddate) {
        if (startdate > enddate) {
            //alert(message);
            $.messager.alert('错误', message, 'error');
            return false;
        }
    }
    return true;
}

//点击查询
$("#search").on('click', function createTbody(){
    if (!compareDate($('#start_week').val(), $('#end_week').val(), 1, "开始日期大于结束日期，请重新选择!")) {
        return false;
    }

  
    //grid加载
    if (datagrid) {
        $('#dataGrid').datagrid('load',
                {
                   start_week: $("#start_week").val(),
                   end_week: $("#end_week").val(),
                   menu_path: $("#menu_path").val(),
                   interface_path: $("#interface_path").val(),
                   order_id: $("#order_id").val()
                  
                });
    } else {
        datagrid = $('#dataGrid').datagrid({
            url: "queryBaseErr",
            type:"post",
            fit: true,
	        pagination: true,
	        singleSelect: true,
	        pageSize: 100,
	        pageList: [1,2,100,200,300],
	        nowrap: true,
	        rownumbers: true,
	        singleSelect: false,
			selectOnCheck: true,
			checkOnSelect: true,
            queryParams: {
                start_week: $("#start_week").val(),
                end_week: $("#end_week").val(),
                menu_path: $("#menu_path").val(),
                interface_path: $("#interface_path").val(),
                order_id: $("#order_id").val()
               
            },
            columns: [
                [
/*                 {
                     field: 'check',
                     title: '全选',
                     width: 20,
                     align: 'center',
                     checkbox: true
                 },*/

                    {
                        field: 'order_id',
                        title: '订单ID',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'menu_path',
                        title: '业务模块',
                        width: 100,
                        align: 'center'
                    },
                    {
                        field: 'interface_path',
                        title: '接口地址',
                        width: 250,
                        align: 'center'
                    },
                    {
                        field: 'message',
                        title: '错误信息',
                        width: 200,
                        align: 'center'
                    },
                    {
                        field: 'log_time',
                        title: '日志时间',
                        width: 100,
                        align: 'center',
                        formatter : function(value) {
                            var date = new Date(value);
                            var year = date.getFullYear().toString();
                            var month = (date.getMonth() + 1);
                            var day = date.getDate().toString();
                            var hour = date.getHours().toString();
                            var minutes = date.getMinutes().toString();
                            var seconds = date.getSeconds().toString();
                            if (month < 10) {
                                month = "0" + month;
                            }
                            if (day < 10) {
                                day = "0" + day;
                            }
                            if (hour < 10) {
                                hour = "0" + hour;
                            }
                            if (minutes < 10) {
                                minutes = "0" + minutes;
                            }
                            if (seconds < 10) {
                                seconds = "0" + seconds;
                            }
                            return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
                        }
                    },
                    {
                        field: 'user_id',
                        title: '用户名',
                        width: 200,
                        align: 'center'
                    },
                    {
                        field: 'role_id',
                        title: '角色',
                        width: 200,
                        align: 'center',
                    }
  
                ]
            ]
        });
    }
    $('#dataGrid').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
});

function myformatter_arrival_start(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    date_start = date;
    return date;
}

function myformatter_arrival_end(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    date_end = date;
    return date;
}



function myparser(s) {
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0], 10);
    var m = parseInt(ss[1], 10);
    var d = parseInt(ss[2], 10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
        return new Date(y, m - 1, d);
    } else {
        return new Date();
    }
}

var datagrid;
$(function () {
    var buttonsStart = $.extend([], $.fn.datebox.defaults.buttons);
    buttonsStart.splice(1, 0, {
        text: '清除',
        handler: function (target) {
            $(target).datebox('setValue', '');
            $('#' + $(target).attr('id') + '_txt').val('');
            dateweek_start = '';
            date_start = '';
            $(target).combo("hidePanel");
        }
    });
    var buttonsEnd = $.extend([], $.fn.datebox.defaults.buttons);
    buttonsEnd.splice(1, 0, {
        text: '清除',
        handler: function (target) {
            $(target).datebox('setValue', '');
            $('#' + $(target).attr('id') + '_txt').val('');
            dateweek_end = '';
            date_end = '';
            $(target).combo("hidePanel");
        }
    });
    $('#start_week').datebox({
        buttons: buttonsStart
    });
    $('#end_week').datebox({
        buttons: buttonsEnd
    });

    var buttonsStart1 = $.extend([], $.fn.datebox.defaults.buttons);
    buttonsStart1.splice(1, 0, {
        text: '清除',
        handler: function (target) {
            $(target).datebox('setValue', '');
            $('#' + $(target).attr('id') + '_txt').val('');
            report_dateweek_start = '';
            $(target).combo("hidePanel");
        }
    });
    var buttonsEnd1 = $.extend([], $.fn.datebox.defaults.buttons);
    buttonsEnd1.splice(1, 0, {
        text: '清除',
        handler: function (target) {
            $(target).datebox('setValue', '');
            $('#' + $(target).attr('id') + '_txt').val('');
            report_dateweek_end = '';
            $(target).combo("hidePanel");
        }
    });
});

$('#export').click(function(){
	if(!datagrid){
		$.messager.alert('提示','请查询！','info');
		return;	
	}
	$.messager.confirm('确认','确定要导出吗？', function(r){
		if (r){
			$('#filterForm').attr("action", 'exportT2OrderList.export');
			$('#filterForm').submit();
		}
	});
});

//点击全部导出
/*$('#exportall').click(function () {
    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
    $.messager.confirm('确认', '确定要全部导出吗？', function (r) {
        if (r) {
            $("#exportData").val('');
            $('#filterForm').attr("action", 'exportAllT2OrderList.export');
            $('#filterForm').submit();
        }
    });
});
*/