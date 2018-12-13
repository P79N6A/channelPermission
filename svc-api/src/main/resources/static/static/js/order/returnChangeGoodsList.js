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
        addTimeMin:$("#addTimeMin").val().trim(),//提交时间
        addTimeMax:$("#addTimeMax").val().trim(),
/*        paymentTimeMin:$("#paymentTimeMin").val().trim(),//完成时间
        paymentTimeMax:$("#paymentTimeMax").val().trim(),*/
        orderSn:$("#orderSn").val().trim(),//订单号
        sourceOrderSn:$("#sourceOrderSn").val().trim(),//来源订单号
        tbOrderSn:$("#tbOrderSn").val().trim(),//TB单号
        repairSn:$("#repairSn").val().trim(),//退货单号
        contactName:$("#contactName").val().trim(),//联系人姓名
        contactMobile:$("#contactMobile").val().trim(),//联系人姓名
        handleStatus :$("#handleStatus").combobox("getValue"),//受理状态
        paymentStatus :$("#paymentStatus").combobox("getValue"),//付款状态
        receiptStatus :$("#receiptStatus").combobox("getValue"),//发票状态
        storageStatus :$("#storageStatus").combobox("getValue"),//货物状态
        source:$("#source").combobox('getValue'),//订单来源
        hpFirst:$("#hpFirst").combobox('getValue'),//hp一次质检
        hpSecond:$("#hpSecond").combobox('getValue'),//hp二次质检
        hpThird:$("#hpThird").combobox('getValue'),//hp换箱结果
        typeActual :$("#typeActual").combobox("getValue"),//退换货类型
        shippingMode :$("#shippingMode").combobox("getValue"),//物流模式
        stockType :$("#stockType").combobox("getValue"),//库存类型
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

    var stockType='[{"value":"","valueMeaning":"库存类型"},{"value":"3W","valueMeaning":"3W"},{"value":"WA","valueMeaning":"WA"}]';
    var data=$.parseJSON(stockType);
    $('#stockType').combobox("loadData", data);
    $('#stockType').combobox('setValue', "").combobox('setText', '库存类型');

    var SM='[{"value":"","valueMeaning":"物流模式"},{"value":"B2B2C","valueMeaning":"B2B2C"},{"value":"B2C","valueMeaning":"B2C"}]';
    var SMdata=$.parseJSON(SM);
    $('#shippingMode').combobox("loadData", SMdata);
    $('#shippingMode').combobox('setValue', "").combobox('setText', '物流模式');

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
                    return '<a href="javascript:void(0);" onclick="jumpOrderList(\''+row.orderSn+'\')">'+row.orderSn+'</a>';
                }
            },{
                width : '15%',
                title : '网单编号',
                field : 'cOrderSn',
                formatter: function(value,row,index){
                    return '<a  href="javascript:void(0);" onclick="jumpNetOrderList(\''+row.cOrderSn+'\')">'+row.cOrderSn+'</a>';
                }
            }, {
                width : '10%',
                title : '退换货单号',
                field : 'repairSn',
                formatter: function(value,row,index){
                    return '<a href="javascript:void(0);" onclick="jumpReturnList(\''+row.repairSn+'\')">'+row.repairSn+'</a>';
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
           /* {title: '订单来源', field: 'source', align:'center',sortable: false,width : 190,
                formatter: function(value,row,index){
                    var isNot="";
                    if(row.source =='TBSC'){
                        isNot="海尔官方淘宝旗舰店";
                    }
                    if(row.source =='TOPBOILER'){
                        isNot="海尔热水器专卖店";
                    }
                    if(row.source =='TONGSHUAI'){
                        isNot="淘宝网统帅日日顺乐家专卖店";
                    }
                    if(row.source =='TONGSHUAIFX'){
                        isNot="统帅品牌商";
                    }
                    if(row.source =='TOPXB'){
                        isNot="海尔新宝旗舰店";
                    }
                    if(row.source =='MOOKA'){
                        isNot="mooka模卡官方旗舰店";
                    }
                    if(row.source =='WASHER'){
                        isNot="海尔洗衣机旗舰店";
                    }
                    if(row.source =='FRIDGE'){
                        isNot="海尔冰冷旗舰店";
                    }
                    if(row.source =='AIR'){
                        isNot="海尔空调旗舰店";
                    }
                    if(row.source =='TBCT'){
                        isNot="村淘海尔商家";
                    }
                    if(row.source =='GQGYS'){
                        isNot="生态授权店";
                    }
                    if(row.source =='TMKSD'){
                        isNot="天猫卡萨帝旗舰店";
                    }
                    if(row.source =='TMTV'){
                        isNot="海尔电视旗舰店";
                    }
                    if(row.source =='TBCFDD'){
                        isNot="海尔厨房大电旗舰店";
                    }
                    if(row.source =='TBXCR'){
                        isNot="天猫小超人旗舰店";
                    }
                    if(row.source =='TOPSHJD'){
                        isNot="海尔生活电器专卖店";
                    }
                    if(row.source =='TOPDHSC'){
                        isNot="海尔生活家电旗舰店";
                    }
                    if(row.source =='GMZX'){
                        isNot="统帅国美旗舰店";
                    }
                    if(row.source =='GMZXTS'){
                        isNot="统帅国美官方旗舰店";
                    }
                    if(row.source =='SNYG'){
                        isNot="苏宁";
                    }
                    if(row.source =='SNHEGQ'){
                        isNot="苏宁海尔集团官方旗舰店";
                    }
                    if(row.source =='SNQDZX'){
                        isNot="苏宁渠道中心";
                    }
                    if(row.source =='DDW'){
                        isNot="当当";
                    }
                    if(row.source =='JDHEBXGQ'){
                        isNot="京东海尔集团冰箱官方旗舰店";
                    }
                    if(row.source =='JDHEGQ') {
                        isNot="京东海尔官方旗舰店";
                    }
                    return isNot ;
                }
            },*/
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
    /*datagrid_orderForecastGoalList();*/
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
            addTimeMin:$("#addTimeMin").val().trim(),//提交时间
            addTimeMax:$("#addTimeMax").val().trim(),
/*            paymentTimeMin:$("#paymentTimeMin").val().trim(),//完成时间
            paymentTimeMax:$("#paymentTimeMax").val().trim(),*/
            orderSn:$("#orderSn").val().trim(),//订单号
            sourceOrderSn:$("#sourceOrderSn").val().trim(),//来源订单号
            tbOrderSn:$("#tbOrderSn").val().trim(),//
            repairSn:$("#repairSn").val().trim(),//退货单号
            contactName:$("#contactName").val().trim(),//联系人姓名
            contactMobile:$("#contactMobile").val().trim(),//联系人姓名
            handleStatus :$("#handleStatus").combobox("getValue"),//受理状态
            paymentStatus :$("#paymentStatus").combobox("getValue"),//付款状态
            receiptStatus :$("#receiptStatus").combobox("getValue"),//发票状态
            storageStatus :$("#storageStatus").combobox("getValue"),//货物状态
            source:$("#source").combobox('getValue'),//订单来源
            hpFirst:$("#hpFirst").combobox('getValue'),//hp一次质检
            hpSecond:$("#hpSecond").combobox('getValue'),//hp二次质检
            hpThird:$("#hpThird").combobox('getValue'),//hp换箱结果
            typeActual :$("#typeActual").combobox("getValue"),//退换货类型
            type:1
        };
       /* $.ajax({
            url      : "/operationArea/export_ExcelOrderRepairs",
            type     : 'POST',
            dataType: 'json',
            contentType:"application/json",
            data     :JSON.stringify(Pagination),
            success  : function(data) {
                alert(data.msg);
            }
        })*/
    $.messager.confirm('确认','确定要导出吗？', function(r){
        if (r){
       $('#exportData').val(JSON.stringify(Pagination));
            $('#paramForm_orderForecastGoal').attr("action", '/operationArea/export_ExcelOrderRepairs');
            $('#paramForm_orderForecastGoal').submit();
            /* $.ajax({
           url      : "/operationArea/export_ExcelOrderRepairs",
           type     : 'get',
           dataType: 'json',
           contentType:"application/json",
                 async:false,
           data     :JSON.stringify(Pagination),
           success  : function(data) {
               alert(data.msg);
           }
       })*/
          /*  $.post(
                "/operationArea/export_ExcelOrderRepairs",JSON.stringify(Pagination)
            )*/
        }
    });
    }
)
/*
 * 导出待退款报表
 * */
$('#export_Excel1').click(function() {
        var Pagination={
           type:2
        };
    $.messager.confirm('确认','确定要导出吗？', function(r){
        if (r){
            $('#exportData').val(JSON.stringify(Pagination));
            $('#paramForm_orderForecastGoal').attr("action", '/operationArea/export_ExcelOrderRepairs');
            $('#paramForm_orderForecastGoal').submit();
        }
    });
    }
)



function jumpNetOrderList(NetOrderSn){
	window.top.addTab("网单详情", "/operationArea/ProductView?cOrderSn="+NetOrderSn, true);
}

function jumpOrderList(orderSn){
	window.top.addTab("订单详情", "/operationArea/orderNumberSelect?orderSn="+orderSn, true);
}

function jumpReturnList(ReturnSn){
	window.top.addTab("退换货详情", "/operationArea/ReturnEdit?id="+ReturnSn, true);
}