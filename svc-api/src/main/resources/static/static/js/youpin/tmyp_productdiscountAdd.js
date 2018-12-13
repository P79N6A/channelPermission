var dataGrid =null;
$.fn.combobox.defaults.icons=[{}]
$(function () {

})


//点击保存订单信息
function onSaveTmyp_productdiscountInfo() {
    var baseInfo=$('#baseInfo').serializeObject();//商品型号名称

    var product_Name=baseInfo.product_Name;//库位名称
    if($("#product_Name").val().trim() ==""||product_Name ==null){
        alert("商品品牌型号名称不能为空,请填写!");
        return false;
    }
    var product_Type=baseInfo.product_Type;//商品类型
    if($("#product_Type").val().trim() ==""||product_Type ==null){
        alert("商品类型不能为空,请填写!");
        return false;
    }
    var sale_Price=baseInfo.sale_Price;//零售价
    if($("#sale_Price").val().trim() ==""||sale_Price ==null){
        alert("零售价不能为空,请填写!");
        return false;
    }
    if (!checkNumber(sale_Price)) {
        alert("零售价必须为数字,请重新填写!");
        return false;
    }
    var purchase_Price=baseInfo.purchase_Price;//采购价
    if($("#purchase_Price").val().trim() ==""||purchase_Price ==null){
        alert("采购价不能为空,请填写!");
        return false;
    }
    if (!checkNumber(purchase_Price)) {
        alert("采购价必须为数字,请重新填写!");
        return false;
    }
    var sku=baseInfo.sku;//sku物料
    if($("#sku").val().trim() ==""||sku ==null){
        alert("sku物料不能为空,请填写!");
        return false;
    }
    var discount=baseInfo.discount;//折扣率

    if(discount != null ){
        if (!checkNumber(discount)) {
            alert("折扣率必须为数字,请重新填写!");
            return false;
        }
    }

    if(discount !=null ){
        discount.trim();
    }

    $.ajax({
        type: 'POST',
        traditional: true,
        async: false,
        url: '/youpin/saveTmypProductDiscountInfo',
        dataType: 'json',
        data: {
            "productName": product_Name.trim(),//商品型号名称
            "productType": product_Type.trim() ,//商品类型
            "salePrice": sale_Price.trim(),//零售价
            "purchasePrice": purchase_Price.trim(),//采购价
            "discount": discount,//折扣率
            "sku": sku.trim(),//sku物料
        },
            success: function (data) {
                if (data.flag == 1) {
                    $.messager.confirm('新增成功', '返回优品折扣率列表，请点击确认？', function(r){
                        if (r){
                            window.location.href="/youpin/loadTmypProductDiscountPage.html";
                        }else {
                            window.location.reload();
                        }
                    });
                } else if (data.flag == 2) {
                    alert('保存失败');
                    return false;
                }else {
                    alert("操作异常,添加失败")
                    return false;
                }
            }
        })


    function checkNumber(str) {
        var re1 = /^[0-9]*$/;
        var re2 = /^(-?\d+)(\.\d+)?$/;
        if (re1.test(str) || re2.test(str)) {
            return true;
        } else {
            return false;
        }
    }
}






