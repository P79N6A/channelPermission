
var datagrid;
var queryParameters;
$(function () {
    var moreOptions = $(".morehide");
    var osn = $("#orderSn").val().trim();
    for (var i = 0; i < moreOptions.length; i++) {
        $(moreOptions[i]).css("display", "none");
    };
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        striped: true,
        singleSelect: true,
        pagination: true,
        columns: [[
            // {title: "操作", field: "field0", sortable: false, formatter: go_detail},
            { title: "序号", field: "id", hidden: true },
            { title: "网单编号", field: "cOrderSn", sortable: false },
            { title: "订单编号", field: "orderSn", sortable: false },
            { title: "网单状态", field: "statusTs", sortable: false },
            { title: "订单状态", field: "orderStatus", sortable: false },
            { title: "订单支付状态", field: "cPaymentStatus", sortable: false },
            { title: "开票状态", field: "isMakeReceipt", sortable: false },
            { title: "商品名称", field: "productName", sortable: false },
            { title: "价格", field: "price", sortable: false },
            { title: "数量", field: "number", sortable: false },
            { title: "金额", field: "money", sortable: false },
            { title: "库位", field: "sCode", sortable: false },
            { title: "下单时间", field: "addTimeMin", sortable: false },
            { title: "备注", field: "remark", sortable: false },
            { title: "首次确认时间", field: "firstConfirmTime", sortable: false },
            { title: "已确认次数", field: "autoConfirmNumMin", sortable: false },
            { title: "转人工时间", field: "smManualTime", sortable: false }
        ]],
        pageSize: 50,
        pageList: [50, 100, 200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);

    //生成默认起始时间
    var addTimeMin = $('#addTimeMin').datetimebox('getValue');
    var finalTime = null;
    if (addTimeMin != null || addTimeMin != "") {
        finalTime = addTimeMin;
    }
    if (addTimeMin == "" || addTimeMin == null) {
        addTimeMin = new Date();
        addTimeMin.setMonth(addTimeMin.getMonth() - 1);
        finalTime = addTimeMin.format("yyyy-MM-dd hh:mm:ss");
    }
    //生成默认截止时间
    var addTimeMax = $('#addTimeMax').datetimebox('getValue');
    if (addTimeMax == "" || addTimeMax == null) {
        addTimeMax = new Date().format("yyyy-MM-dd hh:mm:ss");
    }
    $('#addTimeMin').datetimebox('setValue',finalTime );

    $('#addTimeMax').datetimebox('setValue',addTimeMax );

});


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
$('#receiptAddTimeMin').datetimebox({ buttons: buttons });
$('#receiptAddTimeMax').datetimebox({ buttons: buttons });
$('#payTimeMin').datetimebox({ buttons: buttons });
$('#payTimeMax').datetimebox({ buttons: buttons });
$('#confirmTimeMin').datetimebox({ buttons: buttons });
$('#confirmTimeMax').datetimebox({ buttons: buttons });


var searchselect = function () {
        //校验起止时间
        //下单时间
        if ($('#addTimeMin').datetimebox('getValue') && $('#addTimeMax').datetimebox('getValue')) {
            if ($('#addTimeMin').datetimebox('getValue') > $('#addTimeMax').datetimebox('getValue')) {
                $.messager.alert('错误', '时间区间不正确,请重新选择', 'error');
                return false;
            }
        }
        //开票时间
        if ($('#receiptAddTimeMin').datetimebox('getValue') && $('#receiptAddTimeMax').datetimebox('getValue')) {
            if ($('#receiptAddTimeMin').datetimebox('getValue') > $('#receiptAddTimeMax').datetimebox('getValue')) {
                $.messager.alert('错误', '时间区间不正确,请重新选择', 'error');
                return false;
            }
        }
        //付款时间
        if ($('#payTimeMin').datetimebox('getValue') && $('#payTimeMax').datetimebox('getValue')) {
            if ($('#payTimeMin').datetimebox('getValue') > $('#payTimeMax').datetimebox('getValue')) {
                $.messager.alert('错误', '时间区间不正确,请重新选择', 'error');
                return false;
            }
        }
        //订单确认时间
        if ($('#confirmTimeMin').datetimebox('getValue') && $('#confirmTimeMax').datetimebox('getValue')) {
            if ($('#confirmTimeMin').datetimebox('getValue') > $('#confirmTimeMax').datetimebox('getValue')) {
                $.messager.alert('错误', '时间区间不正确,请重新选择', 'error');
                return false;
            }
        }


        var receiptAddTimeMin = $('#receiptAddTimeMin').datetimebox('getValue');
        var payTimeMin = $('#payTimeMin').datetimebox('getValue');
        var confirmTimeMin = $('#confirmTimeMin').datetimebox('getValue');

        var receiptAddTimeMax = $('#receiptAddTimeMax').datetimebox('getValue');
        var payTimeMax = $('#payTimeMax').datetimebox('getValue');
        var confirmTimeMax = $('#confirmTimeMax').datetimebox('getValue');

        //订单来源
        var source = $("#source").combobox("getValue");
        if (source == "-1") {
            source = "";
        }
        $('#source').combobox('setValue', source);
        //支付方式
        var paymentCode = $("#paymentCode").combobox("getValue");
        if (paymentCode == "-1") {
            paymentCode = "";
        }
        $('#paymentCode').combobox('setValue', paymentCode);
        //支付状态
        var cPaymentStatus = $("#cPaymentStatus").combobox("getValue");
        if (cPaymentStatus == "-1") {
            cPaymentStatus = "";
        }
        $('#cPaymentStatus').combobox('setValue', cPaymentStatus);
        //订单状态
        var orderStatus = $("#orderStatus").combobox("getValue");
        if (orderStatus == "-1") {
            orderStatus = "";
        }
        $('#orderStatus').combobox('setValue', orderStatus);
        //是否从未确认过
        var firstConfirmTime = $("#firstConfirmTime").combobox("getValue");
        if (firstConfirmTime == "-1") {
            firstConfirmTime = "";
        }
        $('#firstConfirmTime').combobox('setValue', firstConfirmTime);
        //网单状态
        var status = $("#status").combobox("getValue");
        if (status == "-1") {
            status = "";
        }
        $('#status').combobox('setValue', status);
        //无需确认订单
        var isNotConfirm = $("#isNotConfirm").combobox("getValue");
        if (isNotConfirm == "-1") {
            isNotConfirm = "";
        }
        $('#isNotConfirm').combobox('setValue', isNotConfirm);
        //是否转运发货
        var isTsCode = $("#isTsCode").combobox("getValue");
        if (isTsCode == "-1") {
            isTsCode = "";
        }
        $('#isTsCode').combobox('setValue', isTsCode);
        //是否是预定网单
        var isBook = $("#isBook").combobox("getValue");
        if (isBook == "-1") {
            isBook = "";
        }
        $('#isBook').combobox('setValue', isBook);
        //是否是淘宝套装
        var externalSaleSettingId = $("#externalSaleSettingId").combobox("getValue");
        if (externalSaleSettingId == "-1") {
            externalSaleSettingId = "";
        }
        $('#externalSaleSettingId').combobox('setValue', externalSaleSettingId);
        //是否开具发票
        var isReceipt = $("#isReceipt").combobox("getValue");
        if (isReceipt == "-1") {
            isReceipt = "";
        }
        $('#isReceipt').combobox('setValue', isReceipt);
        //物流模式
        var shippingMode = $("#shippingMode").combobox("getValue");
        if (shippingMode == "-1") {
            shippingMode = "";
        }
        $('#shippingMode').combobox('setValue', shippingMode);
        //开票类型
        var makeReceiptType = $("#makeReceiptType").combobox("getValue");
        if (makeReceiptType == "-1") {
            makeReceiptType = "";
        }
        $('#makeReceiptType').combobox('setValue', makeReceiptType);
        //开票状态
        var isMakeReceipt = $("#isMakeReceipt").combobox("getValue");
        if (isMakeReceipt == "-1") {
            isMakeReceipt = "";
        }
        $('#isMakeReceipt').combobox('setValue', isMakeReceipt);
        //发票类型
        var invoiceType = $("#invoiceType").combobox("getValue");
        if (invoiceType == "-1") {
            invoiceType = "";
        }
        $('#invoiceType').combobox('setValue', invoiceType);
        //发票审核状态
        var istate = $("#istate").combobox("getValue");
        if (istate == "-1") {
            istate = "";
        }
        $('#istate').combobox('setValue', istate);
        //网单库存类型
        var stockType = $("#stockType").combobox("getValue");
        if (stockType == "-1") {
            stockType = "";
        }
        $('#stockType').combobox('setValue', stockType);
        //订单类型
        var orderType = $("#orderType").combobox("getValue");
        if (orderType == "-1") {
            orderType = "";
        }
        $('#orderType').combobox('setValue', orderType);
        //品牌
        var brandId = $("#brandId").combobox("getValue");
        if (brandId == "-1") {
            brandId = "";
        }
        $('#brandId').combobox('setValue', brandId);
        //商品分类
        var cateId = $("#cateId").combobox("getValue");
        if (cateId == "-1") {
            cateId = "";
        }
        $('#cateId').combobox('setValue', cateId);
        //后台订单
        var isBackend = $("#isBackend").combobox("getValue");
        if (isBackend == "-1") {
            isBackend = "";
        }
        $('#isBackend').combobox('setValue', isBackend);
        //后台订单
        var isProduceDaily = $("#isProduceDaily").combobox("getValue");
        if (isProduceDaily == "-1") {
            isProduceDaily = "";
        }
        $('#isProduceDaily').combobox('setValue', isProduceDaily);

        //提交参数
        queryParameters = {
            id: $("#id").val(),
            cOrderSn: $("#cOrderSn").val().trim(),//网单编号
            orderSn: $("#orderSn").val().trim(),//订单号
            sourceOrderSn: $("#sourceOrderSn").val().trim(),//来源订单号
            mobile: $("#mobile").val().trim(),//手机号
            consignee: $("#consignee").val().trim(),//收货人
            sku: $("#sku").val().trim(),//商品SKU
            addTimeMin:  $('#addTimeMin').datetimebox('getValue' ),//下单时间
            addTimeMax: $('#addTimeMax').datetimebox('getValue' ),
            scode: $("#sCode").val().trim(),//库位编码
            source: $("#source").combobox("getValue"),//订单来源
            paymentCode: $("#paymentCode").combobox("getValue"),//支付方式
            cPaymentStatus: $("#cPaymentStatus").combobox("getValue"),//支付状态
            orderStatus: $("#orderStatus").combobox("getValue"),//订单状态
            firstConfirmTime: $("#firstConfirmTime").combobox("getValue"),//从未确认过
            status: $("#status").combobox("getValue"),//网单状态
            isNotConfirm: $("#isNotConfirm").combobox("getValue"),//无需确认订单
            isTsCode: $("#isTsCode").combobox("getValue"),//转运发货
            isBook: $("#isBook").combobox("getValue"),//预定网单
            externalSaleSettingId: $("#externalSaleSettingId").combobox("getValue"),//淘宝套装网单
            isReceipt: $("#isReceipt").combobox("getValue"),//需要开具发票
            shippingMode: $("#shippingMode").combobox("getValue"),//物流模式
            makeReceiptType: $("#makeReceiptType").combobox("getValue"),//开票类型
            isMakeReceipt: $("#isMakeReceipt").combobox("getValue"),//开票状态
            invoiceType: $("#invoiceType").combobox("getValue"),//发票类型
            istate: $("#istate").combobox("getValue"),//发票审核状态
            stockType: $("#stockType").combobox("getValue"),//网单库存类型
            orderType: $("#orderType").combobox("getValue"),//订单类型
            brandId: $("#brandId").combobox("getValue"),//品牌
            cateId: $("#cateId").combobox("getValue"),//商品分类
            isBackend: $("#isBackend").combobox("getValue"),//后台订单
            isProduceDaily: $("#isProduceDaily").combobox("getValue"),//是否日日单
            receiptAddTimeMin: $("#receiptAddTimeMin").datebox("getValue"),//开票时间
            receiptAddTimeMax: $("#receiptAddTimeMax").datebox("getValue"),//开票时间 结束
            payTimeMin: $("#payTimeMin").datebox("getValue"),//付款时间
            payTimeMax: $("#payTimeMax").datebox("getValue"),//付款时间 结束
            confirmTimeMin: $("#confirmTimeMin").datebox("getValue"),//订单确认时间
            confirmTimeMax: $("#confirmTimeMax").datebox("getValue"),//订单确认时间 结束
            rand: Math.random(),

        };
        // if(datagrid){
        //     //grid加载
        //     $('#datagrid').datagrid('load',queryParameters);
        // }else{
        datagrid = $('#datagrid').datagrid({
            url: "/invoice/getSpecializedInvoiceOrderProductsList.html",
            fit: true,
            fitColumns: false,
            pagination: true,
            singleSelect: true,
            pageSize: 50,
            pageList: [50, 100, 200],
            toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
            nowrap: true,
            rownumbers: true,
            queryParams: queryParameters,
            columns: [
                [{
                    field: 'id',
                    title: '序号',
                    align: 'center',
                    hidden: true
                },
                    {
                        title: '网单编号 ',
                        field: 'cOrderSn',
                        align: 'center',
                        formatter: function (value, row, index) {
                            //<a href="javascript:void(0)">'+row.cOrderSn+'</a><br/><span>查看详情</span>
                            var returnOrderProduct = "<a href='javascript:void(0);' onclick='addTab2(" + '"' + row.cOrderSn + '"' + ");return false;'>" + row.cOrderSn + "</a><br/>";
                            //                        <a href="javascript:void(0)" onclick="(\''+row.corderSn+'\')">'+row.corderSn+'</a>
                            if (row.isMakeReceipt == 9 && row.makeReceiptType == '2' && row.status > '8') {
                                returnOrderProduct = returnOrderProduct + "<a href='javascript:void(0);' onclick='eInvoiceBatchBilling(" + '"' + 1 + '", "' + row.id + '"' + ");return false;'>开票</a> "
                            }
                            if (row.isMakeReceipt == 3 || row.isMakeReceipt == '4' && row.status > '8') {
                                returnOrderProduct = returnOrderProduct + "<a href='javascript:void(0);' onclick='eInvoiceBatchReBilling(" + '"' + 2 + '", "' + row.id + '"' + ");return false;'>重开发票</a>";
                            }
                            return returnOrderProduct;

                        }
                    },
                    {
                        title: '订单编号 ',
                        field: 'orderSn',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var returnOrderProduct = "<a href='javascript:void(0);' onclick='addTab3(" + '"' + row.orderSn + '"' + ");return false;'>" + row.orderSn + "</a><br/>";
                            return returnOrderProduct;
                        }
                    },
                    {
                        title: '网单状态 ',
                        field: 'statusTs',
                        align: 'center'
                    }, {
                    title: '订单状态 ',
                    field: 'orderStatus',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var orderS = "";
                        if (row.orderStatus == '200') {
                            orderS = "未确认";
                        } else if (row.orderStatus == '201') {
                            orderS = '已确认';
                        } else if (row.orderStatus == '202') {
                            orderS = '已取消';
                        } else if (row.orderStatus == '203') {
                            orderS = '已完成';
                        }
                        return orderS;
                    }
                }, {
                    title: '订单支付状态 ',
                    field: 'cPaymentStatus',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var paymentStatus = "";
                        if (row.cPaymentStatus == '200') {
                            paymentStatus = "未付款";
                        } else if (row.cPaymentStatus == '201') {
                            paymentStatus = '已付款';
                        } else if (row.cPaymentStatus == '206') {
                            paymentStatus = '待退款';
                        } else if (row.cPaymentStatus == '207') {
                            paymentStatus = '已退款';
                        }
                        return paymentStatus;
                    }
                }, {
                    title: '开票状态 ',
                    field: 'isMakeReceipt',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var makeReceiptStatus = "";
                        if (row.isMakeReceipt == '1') {
                            makeReceiptStatus = "未开票";
                        } else if (row.isMakeReceipt == '9') {
                            makeReceiptStatus = '待开票';
                        } else if (row.isMakeReceipt == '5') {
                            makeReceiptStatus = '开票中';
                        } else if (row.isMakeReceipt == '6') {
                            makeReceiptStatus = '开票失败';
                        } else if (row.isMakeReceipt == '2') {
                            makeReceiptStatus = '已开票';
                        } else if (row.isMakeReceipt == '3') {
                            makeReceiptStatus = '作废发票';
                        } else if (row.isMakeReceipt == '4') {
                            makeReceiptStatus = '冲红发票';
                        } else if (row.isMakeReceipt == '10') {
                            makeReceiptStatus = '取消开票';
                        } else if (row.isMakeReceipt == '20') {
                            makeReceiptStatus = '期初数据封存';
                        }
                        return makeReceiptStatus;
                    }
                },
                    /*{
                        title : '配送时效(天)',
                        field : 'diliveryTime',
                        align : 'center'
                    },
                    {
                        title : '预定',
                        field : 'isBook',
                        align : 'center',
                        formatter: function(value,row,index){
                            var isb="";
                            if(row.isBook =='0'){
                                isb="NO";
                            }else {
                                isb='YES';
                            }
                            return isb ;
                        }
                    },*/
                    {
                        field: 'productName',
                        title: '商品名称',
                        sortable: false,
                        align: 'center'
                    },
                    {
                        title: '价格',
                        field: 'price',
                        align: 'center'
                    }, {
                    title: '数量',
                    field: 'number',
                    align: 'center'
                }, {
                    title: '金额',
                    field: 'money',
                    align: 'center'
                }, {
                    title: '库位 ',
                    field: 'sCode',
                    align: 'center'
                }, {
                    title: '下单时间',
                    field: 'addTimeMin',
                    align: 'center'
                }, {
                    title: '备注 ',
                    field: 'remark',
                    align: 'center'
                }, {
                    title: '首次确认时间 ',
                    field: 'firstConfirmTime',
                    align: 'center'
                }, {
                    title: '已确认次数 ',
                    field: 'autoConfirmNumMin',
                    align: 'center'
                }, {
                    title: '转人工时间 ',
                    field: 'smManualTime',
                    align: 'center',
                }]
            ],
        });



}


$('#search').click(function () {
    searchselect();
});

function getOrderProductsDetail(modifyFlg, cOrderSn) {
    if (cOrderSn == undefined) {
        cOrderSn = "";
    }
    jQuery("#dialog-message").dialog("open");
    jQuery("#dialog-message").window("center");
    $("#list").html("<div style='width:100%;height:100%;text-align:center;'><br/><br/>数据正在加载，请稍后。。。<br/><br/><div id='timecount' style='width:100%;height:20px;text-align:center;font-size:10pt;font-family:Arial,Helvetica,sans-serif;'></div><br/></div>");
    jQuery.ajax({
        url: "/invoice/getOrderOroductsDetail.html",
        data: {
            "modifyFlg": modifyFlg,
            "cOrderSn": cOrderSn
        },
        type: "get",
        success: function (data) {
            $("#list").html(data);
        }
    });
}

$(function () {
    $("#dialog-message").dialog({
        autoOpen: false,
        modal: false,
        closeOnEscape: false,
        resizable: true,
        minHeight: 200,
        width: 1150,
        title: '网单详情',
        close: function (event, ui) {
            $("#dialog-message" ).dialog("close");
        }
    });
});
//开票
function eInvoiceBatchBilling(modifyFlg, id) {
    if (id == undefined) {
        id = "";
    }
    $.messager.confirm('确认', '确定要开发票吗?', function (r) {
        if (r) {
            jQuery.ajax({
                url: "/invoice/eInvoiceBatchBillingByOrderProductId",
                data: { orderProductId: id },
                type: "POST",
                success: function (result) {
                    jQuery("#cOrderSns_invoiceQueue").val("");
                    alert(result.message);
                }
            });
        }
    });
}

//重新发票
function eInvoiceBatchReBilling(modifyFlg, id) {
    if (id == undefined) {
        id = "";
    }
    $.messager.confirm('确认', '确定要重新开票吗?', function (r) {
        if (r) {
            jQuery.ajax({
                url: "/invoice/eInvoiceBatchReBillingByOrderProductId",
                data: { "orderProductId": id },
                type: "POST",
                success: function (result) {
                    jQuery("#cOrderSns_invoices").val("");
                    alert(result.message);
                }
            });
        }
    });

}
var showhideoptions = function (t) {
    var $this = $(t);
    if ($this.hasClass('shouqi')) {

        $this.removeClass('shouqi');
        setTimeout(function () {
            $this.addClass('zhankai');
            var moreOptions = $(".morehide");
            for (var i = 0; i < moreOptions.length; i++) {
                $(moreOptions[i]).css("display", "");
            }
            ;
            $("#moreSpan").text("收起");
            $("#moreImag").attr("src", "../static/img/up_icon.png");
        }, 0);

    } else {
        $this.removeClass('zhankai');
        setTimeout(function () {
            $this.addClass('shouqi');
            var moreOptions = $(".morehide");
            for (var i = 0; i < moreOptions.length; i++) {
                $(moreOptions[i]).css("display", "none");
            }
            ;
            $("#moreSpan").text("更多");
            $("#moreImag").attr("src", "../static/img/down_icon.png");
        }, 0);
    }
    setTimeout(function () {
        $('#datagrid').datagrid('resize');
    }, 500);

};
function addTab2(corderSn) {
    window.top.addTab("网单详情和订单详情", "/operationArea/ProductView?cOrderSn=" + corderSn, true);
}
function addTab3(orderSn) {
    window.top.addTab("订单查询网单", "/operationArea/orderNumberSelect?orderSn=" + orderSn, true);
}
/*function edit(){
    //这里的 id 是主页面的form的ID,也就是当前id
    var row = $("#id").datagrid('getSelected');

    //这里的Id 是需要异步加载的标签的id
//例如：<div id="id" class="easyui-window" title="新增会员"
    // data-options="modal:true,closed:true,iconCls:'icon-save',href:'/rest/user/user-add'"
    //   style="width:800px;height:600px;padding:10px;">
    //  The window content.
    // </div>
    $('#id').window('open');   //打开form表达窗口

    //回显数据


}*/

$("#update_price_money").on('click', function (event) {
    var row = $("#datagrid").datagrid('getSelected');
    if (row !== null) {
        $('#price_money_info').dialog({'title': '修改价格和金额'});
        $('#price_money_info').dialog('open');
        $("#price_money_info_form").form('load',row);
    }else {
        $.messager.alert('警告', '请选择一条数据！', 'warning');
        return;
    }

});

$(function(){
    $("#price_money_info_update").click(function(){
        //价格
        var baseInfo=$('#price_money_info_form').serializeObject();//基本信息
        var price = baseInfo.price;
        if(price ==""||price ==null){
            alert("价格不能为空,请填写!");
            return false;
        }
        if (!checkPriceAndMoney(price)) {
            alert("价格必须为数字,请重新填写!");
            return false;
        }
        //金额
        var money = baseInfo.money;
        if(money ==""||money ==null){
            alert("金额不能为空,请填写!");
            return false;
        }
        if (!checkPriceAndMoney(money)) {
            alert("金额必须为数字,请重新填写!");
            return false;
        }
        $.ajax({
            url      : "/invoice/updateOrderProductsPriceAndProductAmount.html",
            type     : 'POST',
            dataType : 'json',
            data     : {
                id:$('#id').val(),
                price: price.trim(),
                money: money.trim(),
            },
            success  : function(data) {
                $('#price_money_info').dialog('close');
                if (data.flag == 1) {
                    $.messager.alert('提示','修改成功！','info');
                    searchselect();
                } else if (data.flag == 2) {
                    $.messager.alert('提示','修改失败！','info');
                    return false;
                }else {
                    $.messager.alert('提示','操作异常,添加失败！','info');
                    return false;
                }
            }
        });
    });
});
function chekPrice() {
    var baseInfo=$('#price_money_info_form').serializeObject();//基本信息
    var price = baseInfo.price;
    if(price == null||price == ""){
        alert("价格不能为空,请填写!");
        return false;
    }
    if (!checkPriceAndMoney(price)) {
        alert("价格必须为数字,请重新填写!");
        return false;
    }
}
function checkMoney() {
    var baseInfo=$('#price_money_info_form').serializeObject();//基本信息
    var money = baseInfo.money;
    if(money == null||money == ""){
        alert("金额不能为空,请填写!");
        return false;
    }
    if (!checkPriceAndMoney(money)) {
        alert("金额必须为数字,请重新填写!");
        return false;
    }
}
function checkPriceAndMoney(str) {
    var re1 = /^[0-9]*$/;
    var re2 = /^(-?\d+)(\.\d+)?$/;
    if (re1.test(str) || re2.test(str)) {
        return true;
    } else {
        return false;
    }
}
//点击导出
$('#export').click(function () {

    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }

    $.messager.confirm('确认', '确定要导出吗？', function (r) {
        if (r) {
            $('#exportData').val(JSON.stringify(queryParameters));
            $('#paramForm_dmmtlPbcsMtlMeasure').attr("action", '/invoice/exportSpecializedInvoiceOrderProductsList.html');
            $('#paramForm_dmmtlPbcsMtlMeasure').submit();
        }
    });
});