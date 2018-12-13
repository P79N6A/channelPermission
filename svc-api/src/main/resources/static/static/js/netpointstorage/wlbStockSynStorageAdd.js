var dataGrid =null;
$.fn.combobox.defaults.icons=[{}]
$(function () {

})


//点击保存订单信息
function onSaveWlbStockSyncStorageInfo() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息

    var taoBaoShop=baseInfo.taoBaoShop;//所属淘宝店铺
    if(taoBaoShop ==""||taoBaoShop ==null){
        alert("所属淘宝店铺不能为空,请选择!");
        return false;
    }
    var storageCode=baseInfo.storageCode;//库位编码
    if(storageCode ==""||storageCode ==null){
        alert("库位编码不能为空,请选择!");
        return false;
    }
    var taoBaoStorageCode=baseInfo.taoBaoStorageCode;//淘宝仓库编码
    if(taoBaoStorageCode ==""||taoBaoStorageCode ==null){
        alert("淘宝仓库编码不能为空,请填写!");
        return false;
    }


    $.ajax({
        type: 'POST',
        traditional: true,
        async: false,
        url: '/stock/saveWlbStockSyncStorageInfo',
        dataType: 'json',
        data: {
            "source": taoBaoShop,//所属淘宝店铺
            "sCode": storageCode,//库位编码
            "taobaoStoreCode": taoBaoStorageCode.trim(),//淘宝仓库编码
        },
            success: function (data) {
                if (data.flag == 1) {
                    $.messager.confirm('新增成功', '返回物流宝库存同步库位列表，请点击确认？', function(r){
                        if (r){
                            window.location.href="/stock/loadWLBStockSyncStorageListPage.html";
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




