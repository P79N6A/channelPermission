var datagrid;
var queryParameters;
var LEVEL1_QJ={};
$(function () {
    datagrid = {
        fit: true,
        fitColumns: false,
        toolbar: '#datagridToolbar',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
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
                align: 'center'
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
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);



});

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
    if(window.total && window.total > 20000){
        $.messager.alert('提示', '最多导出20000条记录，请缩小查询范围！', 'info');
        return;
    }
    $.messager.confirm('确认','确定要导出吗？', function(r){
        if (r){
            $('#filterForm').attr("action", 'exportT2OrderList.export');
            $('#filterForm').submit();
        }
    });
});

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
$('#search').click(function () {

    if (!compareDate($('#start_week').val(), $('#end_week').val(), 1, "开始日期大于结束日期，请重新选择!")) {
        return false;
    }
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "queryBaseErr",
        type:"post",
        fit: true,
        pagination: true,
        singleSelect: true,
        pageSize: 100,
        pageList: [100,200,300],
        nowrap: true,
        rownumbers: true,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        loadFilter: function(data){
            window.total = data.total;
            return data;
        },
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
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);

});

