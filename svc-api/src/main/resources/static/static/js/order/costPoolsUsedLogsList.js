var dataGrid ;
var queryParameters;
$(function () {
    //------ 初始化列表
    datagrid = {
        fit: true,
        // fitColumns: true,
        toolbar: '#datagridToolbar_costPoolsUsedLogs',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            {title: "订单号", field: "orderSn", sortable: false},
            {title: "网单号", field: "cOrderSn", sortable: false},
            {title: "商品数量", field: "number", sortable: false},
            {title: '渠道', field: 'channel', sortable: false},
            {title: '产业', field: 'chanYe', sortable: false},
            {title: '费用类型', field: 'type', sortable: false},
            {title: '费用使用方式', field: 'usedType', sortable: false},
            {title: '发生费用金额', field: 'amount', sortable: false},
            {title: '时间', field: 'addTime', sortable: false},
        ]],
        pageSize: 50,
        pageList: [50,100,200],
        rownumbers: true
    }
    $('#datagrid').datagrid(datagrid);
//日期控件
    var buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
    buttons.splice(1, 0, {
        text: '清除',
        handler: function (target) {
            $(target).datetimebox('setValue', '');
            $(target).combo("hidePanel");
        }
    });
    $('#addTimeMin').datetimebox({ buttons: buttons });
    $('#addTimeMax').datetimebox({ buttons: buttons });

    //-----查询方法    营销费用池日志查询
    $('#searchCostPoolsUsedLogs').click(function () {

        //检验记录起始时间和结束时间
        if ($('#addTimeMin').datetimebox('getValue') && $('#addTimeMax').datetimebox('getValue')) {
            if ($('#addTimeMin').datetimebox('getValue') > $('#addTimeMax').datetimebox('getValue')) {
                $.messager.alert('错误', '时间区间不正确,请重新选择', 'error');
                return false;
            }
        }

        queryParameters={
            orderSn: $("#orderSn").val(),//订单号
            addTimeMin: $("#addTimeMin").datebox("getValue"),//记录起始时间
            addTimeMax: $("#addTimeMax").datebox("getValue"),//记录结束时间
            channel:$("#channel").combobox('getValue'),//所属渠道
            chanYe:$("#chanYe").combobox('getValue'),//所属产业
            type:$("#type").combobox('getValue'),//优惠类型
            usedType:$("#usedType").combobox('getValue'),//费用使用方式
            source:$("#source").combobox('getValue')//订单来源
        };

        dataGrid = $('#datagrid').datagrid({
            url: "/costPool/searchCPSUsedLogsList",
            striped : true, // 隔行变色
            rownumbers : true,
            fit: true,
            pagination : true,
            singleSelect : true,
            nowrap: false,
            pageSize: 50,
            pageList: [50, 100, 200],
            queryParams: queryParameters,
            idField : 'id',
            columns : [ [
                {title: '订单号', field: 'orderSn', align: 'center'},
                {title: '网单号', field: 'cOrderSn', align: 'center'},
                {title: '商品数量', field: 'number', align: 'center'},
                {title: '渠道 ', field: 'channel', align: 'center',width : 120,
                formatter:function(value,rowData,rowIndex){
                    if(value=='1') return '商城';
                    if(value=='2') return '天猫';
                    if(value=='3') return '电商平台';
                    if(value=='4') return '微店';
                    return value;
                }},
                {title: '产业', field: 'chanye', align: 'center',width : 150,
                formatter:function(value,rowData,rowIndex){
                    if(value=='C200076226') return '冰箱户';
                    if(value=='C200076227') return '冷柜户';
                    if(value=='C200076228') return '洗衣机户';
                    if(value=='C200076229') return '空调户';
                    if(value=='C200076230') return '热水器户';
                    if(value=='C200076231') return '厨电户';
                    if(value=='C200076232') return '彩电户';
                    if(value=='C200076233') return '商用空调及其他';
                    if(value=='C20170825') return '海尔集团电子商务有限公司(赠品)';
                    return value;
                }},
                {title: '费用类型', field: 'type',align: 'center',width : 80,
                formatter:function(value,rowData,rowIndex){
                    if(value=='0') return '赠品机';
                    if(value=='1') return '优惠券';
                    return value;
                }},
                {title: '费用使用方式', field: 'usedType',align: 'center',width : 100,
                formatter:function(value,rowData,rowIndex){
                    if(value=='1') return '占用费用';
                    if(value=='2') return '释放费用';
                    if(value=='3') return '费用借调';
                    return value;
                }},
                {title: '发生费用金额', field: 'amount', align: 'center',width : 135},
                {title: '添加时间', field: 'addTime',align: 'center',width : 200,
                formatter:function(value,rowData,rowIndex){
                    if(value!='0'){
                        var date = new Date(value * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                        Y = date.getFullYear() + '-';
                        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'
                        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' '
                        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':'
                        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':'
                        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds())
                        return Y+M+D+h+m+s;
                    }
                    return value;
                    }},
            ]],
            toolbar: '#datagridToolbar_costPoolsUsedLogs'
        });
        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
    });



	    
});

//点击导出
$('#importCostPoolsUsedLogs').click(function(){
    $.messager.confirm('确认','确定要导出吗？', function(r){
        if (r){
            queryParameters={
                orderSn: $("#orderSn").val(),//订单号
                addTimeMin: $("#addTimeMin").datebox("getValue"),//记录起始时间
                addTimeMax: $("#addTimeMin").datebox("getValue"),//记录结束时间
                channel:$("#channel").combobox('getValue'),//所属渠道
                chanYe:$("#chanYe").combobox('getValue'),//所属产业
                type:$("#type").combobox('getValue'),//优惠类型
                usedType:$("#usedType").combobox('getValue'),//费用使用方式
                source:$("#source").combobox('getValue')//订单来源
            };
            $('#exportData').val(JSON.stringify(queryParameters));
            $('#paramForm_costPoolsUsedLogs').attr("action", '/costPool/exportCostPoolsUsedLogs');
            $('#paramForm_costPoolsUsedLogs').submit();
        }
    });
});