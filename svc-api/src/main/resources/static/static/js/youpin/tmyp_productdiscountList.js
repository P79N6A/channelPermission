
var datagrid;
var queryParameters;
$(function () {
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
            {title: "复选框", field: "checked", hidden: true},
            { title: "商品品牌型号名称", field: "productName", sortable: false },
            { title: "商品类型", field: "productType", sortable: false },
            { title: "零售价", field: "salePrice", sortable: false },
            { title: "采购价", field: "purchasePrice", sortable: false },
            { title: "折扣率", field: "discount", sortable: false },
            { title: "添加时间", field: "addTimeStr", sortable: false },
            { title: "修改时间", field: "modifyTimeStr", sortable: false },
            { title: "sku物料", field: "sku", sortable: false },
        ]],
        pageSize: 50,
        pageList: [50, 100, 200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);


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

var searchselect = function () {
        //添加时间
        if ($('#addTimeMin').datetimebox('getValue') && $('#addTimeMax').datetimebox('getValue')) {
            if ($('#addTimeMin').datetimebox('getValue') > $('#addTimeMax').datetimebox('getValue')) {
                $.messager.alert('错误', '时间区间不正确,请重新选择', 'error');
                return false;
            }
        }

        //提交参数
        queryParameters = {
            id: $("#id").val(),
            product_Name:  $("#product_Name").val().trim(),//商品品牌型号名称
            product_Type:  $("#product_Type").val().trim(),//商品类型
            sale_Price: $("#sale_Price").val().trim(),//零售价
            purchase_Price: $("#purchase_Price").val().trim(),//采购价
            discount: $("#discount").val().trim(),//折扣率
            sku: $("#sku").val().trim(),//sku物料
            addTimeMin:  $('#addTimeMin').datetimebox('getValue' ),//添加时间
            addTimeMax: $('#addTimeMax').datetimebox('getValue' ),
        };

        datagrid = $('#datagrid').datagrid({
            url: "/youpin/getTmyp_ProductDiscountList",
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
                },{
                    field: 'checked',
                    align: 'center',
                    checkbox:true
                },
                    {
                        title: '商品品牌型号名称 ',
                        field: 'productName',
                        align: 'center',
                    },
                    {
                        title: '商品类型 ',
                        field: 'productType',
                        align: 'center',
                    }, {
                    title: '零售价 ',
                    field: 'salePrice',
                    align: 'center'
                }, {
                    title: '采购价 ',
                    field: 'purchasePrice',
                    align: 'center'
                }, {
                    title: '折扣率 ',
                    field: 'discount',
                    align: 'center'
                },  {
                    title: '添加时间 ',
                    field: 'addTimeStr',
                    align: 'center'
                } ,{
                    title: '修改时间 ',
                    field: 'modifyTimeStr',
                    align: 'center'
                },{
                    title: 'sku物料 ',
                    field: 'sku',
                    align: 'center',
                }]
            ],
        });



}
$('#search').click(function () {
    searchselect();
});


//页面跳转
$("#add_TmypProductDiscount").on('click', function (event) {
    window.location.href="/youpin/tmypProductDiscountAdd.html";
});
//修改窗口打开
$("#update_TmypProductDiscount").on('click', function (event) {
    var row = $("#datagrid").datagrid('getSelected');

    if (row !== null) {
        $('#update_tmypProductDiscount_info').dialog({'title': '修改'});
        // $('#id').val(row.id);
        // $("#up_productName").val(row.productName);
        // $("#up_productType").val(row.productType);
        // $("#up_salePrice").val(row.salePrice);
        // $("#up_purchasePrice").val(row.purchasePrice);
        // $("#up_sku").val(row.sku);
        // $("#up_discount").val(row.discount);
        $('#update_tmypProductDiscount_info').form('load', row);
        $('#update_tmypProductDiscount_info').dialog('open');

    }else {
        $.messager.alert('警告', '请选择一条数据！', 'warning');
        return;
    }

});


$("#tmypProductDiscount_info_update").on('click', function (event) {


    //价格
    var baseInfo=$('#update_externalOrders_info_form').serializeObject();//基本信息
    var up_productName = baseInfo.productName;
    if($("#up_productName").val().trim() ==""||up_productName ==null){
        alert("商品品牌型号名称不能为空,请选择!");
        return false;
    }
    var up_productType = baseInfo.productType;
    if($("#up_productType").val().trim() ==""||up_productType ==null){
        alert("商品类型不能为空,请选择!");
        return false;
    };
    var up_salePrice = baseInfo.salePrice;
    if($("#up_salePrice").val().trim() ==""||up_salePrice ==null){
        alert("零售价不能为空,请选择!");
        return false;
    };
    if (!checkNumber(up_salePrice)) {
        alert("零售价必须为数字,请重新填写!");
        return false;
    };
    var up_purchasePrice = baseInfo.purchasePrice;
    if($("#up_purchasePrice").val().trim() ==""||up_purchasePrice ==null){
        alert("采购价不能为空,请选择!");
        return false;
    };
    if (!checkNumber(up_purchasePrice)) {
        alert("采购价必须为数字,请重新填写!");
        return false;
    };
    var up_discount = baseInfo.discount;
    if($("#up_discount").val().trim() ==""||up_discount ==null){
        alert("折扣率不能为空,请选择!");
        return false;
    };
    if (!checkNumber(up_discount)) {
        alert("折扣率必须为数字,请重新填写!");
        return false;
    };
    var up_sku = baseInfo.sku;
    if($("#up_sku").val().trim() ==""||up_sku ==null){
        alert("sku物料不能为空,请选择!");
        return false;
    };
    $.ajax({
        url      : "/youpin/updateTmypProductDiscountInfo",
        type     : 'POST',
        dataType : 'json',
        async : false,
        data     : {
            id:$('#id').val(),
            productName: up_productName.trim(),
            productType: up_productType.trim(),
            salePrice:  up_salePrice.trim(),
            purchasePrice:  up_purchasePrice.trim(),
            discount:  up_discount.trim(),
            sku:  up_sku.trim(),
        },
        success  : function(data) {
            $('#update_tmypProductDiscount_info').dialog('close');

            if (data.flag == 1) {
                $.messager.alert('提示','修改成功！','info');
                searchselect();
            } else if (data.flag == 2) {
                $.messager.alert('提示','修改失败！','info');
                return false;
            }else {
                $.messager.alert('提示','操作异常,添加失败！','info');
                return false;
            };
        }
    });
});
//检验数字
function checkNumber(str) {
    var re1 = /^[0-9]*$/;
    var re2 = /^(-?\d+)(\.\d+)?$/;
    if (re1.test(str) || re2.test(str)) {
        return true;
    } else {
        return false;
    };
};
//设置修改窗口位置

var closeDialog = function () {
    $("#update_tmypProductDiscount_info").dialog('close');
    $('#update_externalOrders_info_form').form('clear');

}
