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
        columns: [[
            {
                field: 'id',
                title: '序号',
                width: 10,
                align: 'center',
                hidden:true
            },
            {
                field:'category_id',
                title:'品类',
                width:100,
                align:'center'
            },
            {
                field: 'ed_channel_name',
                title: '渠道',
                width: 50,
                align: 'center'
            },
            {
                field: 'target_num',
                title: '指标',
                width: 50,
                align: 'center'
            },
            {
                field:'limit_num',
                title:'额度',
                width:80,
                align:'center'
            },{
                field:'total_num',
                title:'总库存',
                width:50,
                align:'center'
            },
            {
                field:'on_way_num',
                title:'在途',
                width:50,
                align:'center'
            },
            {
                field:'loan_num',
                title:'拆借',
                width:50,
                align:'center'
            },
            {
                field:'used_num',
                title:'本周已用',
                width:100,
                align:'center'
            },
            {
                field: 'available_num',
                title: '可用额度',
                width: 50,
                align: 'center'
            }
        ]],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);


});


$('#export').click(function () {
    onExport();
});
function onExport() {
    $('#filterForm').attr("action", '/gatehistory/exportGateOfHistoryLimit');
    $('#filterForm').submit();
}
$(function () {
    var buttonsEnd = $.extend([], $.fn.datebox.defaults.buttons);
    buttonsEnd.splice(1, 0, {
        text: '清除',
        handler: function(target){
            $(target).datebox('setValue', '');
            $('#'+$(target).attr('id')+'_txt').val('');
            dateweek_end = '';
            date_end = '';
            $(target).combo("hidePanel");
        }
    });
    $('#report_year_week').datebox({
        buttons: buttonsEnd
    });

    $("#report_year_week").datebox("setValue", "$!currentweek");
});

function myformatter(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    date = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    date_start = date;
    jQuery.getJSON("/gatehistory/findateWeek?date="+ date+"&type=0", function(result){
        dateweek_start = result.data;
        $("#report_year_week_txt").val(dateweek_start);
    });
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
//点击查询
$('#search').click(function () {

    datagrid = $('#datagrid').datagrid({
        url: "/gatehistory/findGateOfHistoryLimit",
        fit: true,
        fitColumns: true,
        singleSelect: true,
        nowrap: true,
        rownumbers: true,
        queryParams: {report_year_week:$("#report_year_week_txt").val().replace("年","").replace("周","")},
        columns: [
            [
                {
                    field: 'id',
                    title: '序号',
                    width: 10,
                    align: 'center',
                    hidden:true
                },
                {
                    field:'category_id',
                    title:'品类',
                    width:100,
                    align:'center'
                },
                {
                    field: 'ed_channel_name',
                    title: '渠道',
                    width: 50,
                    align: 'center'
                },
                {
                    field: 'target_num',
                    title: '指标',
                    width: 50,
                    align: 'center'
                },
                {
                    field:'limit_num',
                    title:'额度',
                    width:80,
                    align:'center'
                },{
                field:'total_num',
                title:'总库存',
                width:50,
                align:'center'
            },
                {
                    field:'on_way_num',
                    title:'在途',
                    width:50,
                    align:'center'
                },
                {
                    field:'loan_num',
                    title:'拆借',
                    width:50,
                    align:'center'
                },
                {
                    field:'used_num',
                    title:'本周已用',
                    width:100,
                    align:'center'
                },
                {
                    field: 'available_num',
                    title: '可用额度',
                    width: 50,
                    align: 'center'
                }
            ]
        ]
    });
});

