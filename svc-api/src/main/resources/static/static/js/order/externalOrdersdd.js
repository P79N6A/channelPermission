var dataGrid =null;
$.fn.combobox.defaults.icons=[{}]
$(function () {

})


//点击保存订单信息
function onSaveExternalOrdersInfo() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息

    var taoBaoShop=baseInfo.taoBaoShop;//所属淘宝店铺
    if(taoBaoShop ==""||taoBaoShop ==null){
        alert("所属淘宝店铺不能为空,请选择!");
        return false;
    }
    var type=baseInfo.type;//交易类型
    if(type ==""||type ==null){
        alert("交易类型不能为空,请选择!");
        return false;
    }
    var sourceOrderSn=baseInfo.sourceOrderSn;//来源订单号
    if(sourceOrderSn ==""||sourceOrderSn ==null){
        alert("来源订单号不能为空,请填写!");
        return false;
    }


    $.ajax({
        type: 'POST',
        traditional: true,
        async: false,
        url: '/order/saveExternalOrdersInfo.html',
        dataType: 'json',
        data: {
            "source": taoBaoShop,//所属淘宝店铺
            "type": type,//交易类型
            "sourceOrderSn": sourceOrderSn.trim(),//来源订单号
        },
            success: function (data) {
                if (data.flag == 1) {
                    $.messager.confirm('新增成功', '返回3W订单接入人工处理列表，请点击确认？', function(r){
                        if (r){
                            window.location.href="/order/loadExternalOrderListPage.html";
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



}




