/**
 * Created by zhangbo on 2017/12/5.
 */
var datagrid_orderForecastGoal=null;
/*
 * 匹配信息列表
 * */
function datagrid_orderForecastGoalList(){
    var options = datagrid_orderForecastGoal.datagrid('getPager').data("pagination").options;
    var Pagination={
        cOrderSn :$("#cOrderSn").val().trim(),//网单号
        orderSn:$("#orderSn").val().trim(),//订单号
        repairSn:$("#repairSn").val().trim(),//退货单号
        contactName:$("#contactName").val().trim(),//联系人姓名
        contactMobile:$("#contactMobile").val().trim(),//联系人姓名
        handleStatus :$("#handleStatus").combobox("getValue"),//受理状态
        paymentStatus :$("#paymentStatus").combobox("getValue"),//付款状态
        receiptStatus :$("#receiptStatus").combobox("getValue"),//发票状态
        storageStatus :$("#storageStatus").combobox("getValue"),//货物状态
        typeActual :$("#typeActual").combobox("getValue"),//退换货类型
        page            : options.pageNumber,
        rows            : options.pageSize,
        type:1
    };
    $.ajax({
        beforeSend: function () {
            ShowDiv();
        },
        complete: function () {
            HiddenDiv();
        },
        url      : "/operationArea/datagrid_orderForecastGoal",
        type     : 'POST',
        dataType: 'json',
        contentType:"application/json",
       data     :JSON.stringify(Pagination),
        success  : function(data) {
            var row = {
                'data': {
                    'records':
                    data.obj,'totalCount':data.total
                }
            }
            datagrid_orderForecastGoal.datagrid('loadData', row);
            $("#datagrid_orderForecastGoal").datagrid('refreshRow');
        }
    })
}
$(function () {
    datagrid_orderForecastGoal = $("#datagrid_orderForecastGoal").datagrid({
        striped : true,
        rownumbers : true,
        fit: true,
        checkbox:true,
        fitColumns : true,
        loadMsg:"正在努力加载中",
        idField : 'id',
        pagination : true,
        pagePosition:'bottom',
        frozenColumns : [ [ {
            field : 'chk',
            checkbox : true
        } ] ],
        columns : [ [
            {
                width : '15%',
                title : '订单编号',
                field : 'orderSn',
                formatter: function(value,row,index){
                    return '<a href="../operationArea/orderNumberSelect?orderSn='+row.orderSn+'">'+row.orderSn+'</a>';
                }
            },{
                width : '15%',
                title : '网单编号',
                field : 'cOrderSn',
                formatter: function(value,row,index){
                    return '<a href="../operationArea/ProductView?cOrderSn='+row.cOrderSn+'">'+row.cOrderSn+'</a>';
                }
            }, {
                width : '10%',
                title : '退换货单号',
                field : 'repairSn',
                formatter: function(value,row,index){
                    return '<a href="../operationArea/ReturnEdit?id='+row.repairSn+'">'+row.repairSn+'</a>';
                }
            }, {
                width : '10%',
                title : '退换货类型',
                field : 'typeActual',
                formatter: function(value,row,index){
                    var isrece="";
                    if(row.typeActual=='1'){
                        isrece="退货退款";
                    }
                    if(row.typeActual=='2'){
                        isrece="退货不退款";
                    }
                    return isrece;
                }
            },
            {
                width : '5%',
                title : '退换货数量',
                field : 'count'
            },
            {
                width : '10%',
                title : '处理状态',
                field : 'handleStatus',
               formatter: function(value,row,index){
                    var isrece="";
                    if(row.handleStatus=='1'){
                        isrece="审核中";
                    }
                    if (row.handleStatus=='2'){
                        isrece="进行中";
                    }
                    if (row.handleStatus=='3'){
                        isrece="受理完成";
                    }
                    if (row.handleStatus=='4'){
                        isrece="已完结";
                    }
                    if (row.handleStatus=='5'){
                        isrece="已驳回";
                    }
                    if (row.handleStatus=='6'){
                        isrece="已终止";
                    }
                    if (row.handleStatus=='7'){
                        isrece="线下已退款";
                    }
                    if (row.handleStatus=='8'){
                        isrece="等待确认终止";
                    }
                    if (row.handleStatus=='9'){
                        isrece="等待HP确认拒收";
                    }
                    return isrece ;
                }
            },
            {
                width : '15%',
                title : '提交时间',
                field : 'addTime'
            },{
                width : '10%',
                title : '付款状态',
                field : 'PaymentStatus',
                formatter: function(value,row,index){
                    var isrece="";
                    if(row.PaymentStatus=='1'){
                        isrece="已付款";
                    }
                    if (row.PaymentStatus=='2'){
                        isrece="待退款";
                    }
                    if (row.PaymentStatus=='3'){
                        isrece="已退款";
                    }
                    if (row.PaymentStatus=='4'){
                        isrece="线下已退款";
                    }
                    if (row.PaymentStatus=='5'){
                        isrece="无需退款";
                    }
                    return isrece ;
                }
            },{
                width : '10%',
                title : '发票状态',
                field : 'receiptStatus',
                formatter: function(value,row,index){
                    var isrece="";
                    if(row.receiptStatus=='1'){
                        isrece="已开票";
                    }
                    if (row.receiptStatus=='2'){
                        isrece="未开票";
                    }
                    if (row.receiptStatus=='3'){
                        isrece="已召回";
                    }
                    if (row.receiptStatus=='4'){
                        isrece="已冲票";
                    }
                    if (row.receiptStatus=='10'){
                        isrece="待召回";
                    }
                    return isrece ;
                }
            },{
                width : '10%',
                title : '出入库状态',
                field : 'storageStatus',
                formatter: function(value,row,index){
                    var isrece="";
                    if(row.storageStatus=='1'){
                        isrece="已出库";
                    }
                    if (row.storageStatus=='2'){
                        isrece="未出库";
                    }
                    if (row.storageStatus=='3'){
                        isrece="已召回";
                    }
                    if (row.storageStatus=='4'){
                        isrece="已入库";
                    }
                    if (row.storageStatus=='10'){
                        isrece="待召回";
                    }
                    if(row.storageStatus=='122'){
                        isrece="已入22库";
                    }
                    if (row.storageStatus=='121'){
                        isrece="已入21库";
                    }
                    if (row.storageStatus=='110'){
                        isrece="已入10库";
                    }
                    if (row.storageStatus=='140'){
                        isrece="已入40库";
                    }
                    if (row.storageStatus=='141'){
                        isrece="已入41库";
                    }
                    if(row.storageStatus=='221'){
                        isrece="已入日日顺21库";
                    }
                    if (row.storageStatus=='21'){
                        isrece="已入金立库";
                    }
                    if (row.storageStatus=='100'){
                        isrece="菜鸟正品";
                    }
                    if (row.storageStatus=='104'){
                        isrece="菜鸟机损待鉴定";
                    }
                    if (row.storageStatus=='105'){
                        isrece="菜鸟箱损待鉴定";
                    }
                    return isrece ;
                }
            },{
                width : '5%',
                title : '联系人姓名',
                field : 'contactName'
            },{
                width : '15%',
                title : '联系人电话',
                field : 'contactMobile'
            },{
                width : '5%',
                title : '订单地区分配',
                field : 'region'
            },{
                width : '5%',
                title : '省份',
                field : 'province'
            }
        ] ],
        toolbar: '#datagridToolbar_orderForecastGoal'
    });
    datagrid_orderForecastGoalList();
    datagrid_orderForecastGoal.datagrid('getPager').pagination({
        pageSize: 50,
        pageList: [50,100,200],
        onSelectPage : function(pageNumber, pageSize) {
            datagrid_orderForecastGoalList();
        }
    });
});
//显示加载数据
function ShowDiv() {
    $("#loading").show();
}
//隐藏加载数据
function HiddenDiv() {
    $("#loading").hide();
}
/*
 * 点击搜索按钮执行的函数
 * */
$('#search').click(function() {
        var options = datagrid_orderForecastGoal.datagrid('getPager').data("pagination").options;
        options.pageNumber = 1;
        datagrid_orderForecastGoal.datagrid('getPager').pagination('refresh');
        datagrid_orderForecastGoalList();
    }
)
/*
 * 导出报表
 * */
$('#export_Excel').click(function() {
        var Pagination={
            cOrderSn :$("#cOrderSn").val().trim(),//网单号
            orderSn:$("#orderSn").val().trim(),//订单号
            repairSn:$("#repairSn").val().trim(),//退货单号
            contactName:$("#contactName").val().trim(),//联系人姓名
            contactMobile:$("#contactMobile").val().trim(),//联系人姓名
            handleStatus :$("#handleStatus").combobox("getValue"),//受理状态
            paymentStatus :$("#paymentStatus").combobox("getValue"),//付款状态
            receiptStatus :$("#receiptStatus").combobox("getValue"),//发票状态
            storageStatus :$("#storageStatus").combobox("getValue"),//货物状态
            typeActual :$("#typeActual").combobox("getValue"),//退换货类型
        };
        $.ajax({
            url      : "/operationArea/export_ExcelOrderRepairs",
            type     : 'POST',
            dataType: 'json',
            contentType:"application/json",
            data     :JSON.stringify(Pagination),
            success  : function(data) {
                alert(data.msg);
            }
        })
    }
)
/*
 * 导出待退款报表
 * */
$('#export_Excel1').click(function() {
        var Pagination={
           type:2
        };
        $.ajax({
            url      : "/operationArea/export_ExcelOrderRepairs",
            type     : 'POST',
            dataType: 'json',
            contentType:"application/json",
            data     :JSON.stringify(Pagination),
            success  : function(data) {
                alert(data.msg);
            }
        })
    }
)