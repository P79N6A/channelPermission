$(function () {
    $.ajax({
        url: '/vehicle/tempLoad',
        data: {orderId: $("#orderId").val()},
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data.order && data.details) {
                setOrder(data.order)
                setSelections(data.details,data.order)
            }
        },
        error: function () {

        }
    })
})

function setOrder(order) {
	$("#whCode").combobox("disable")
    $("#88code").combobox("setValue", order.deliveryCode)
    $("#88code").combobox("setText", order.deliveryName)
    $("#88code").combobox("disable")
    $("#rrsCenterCode").textbox("setValue", order.distributionCentre)
    $("#rrsCenterCode").textbox("setValue", order.distributionCentreName)
    $("#rrsCenterCode").textbox("disable")
    $("#baseCode").combobox("setValue", order.jdCode)
    $("#baseCode").combobox("setText", order.jdName)
    $("#baseCode").combobox("disable")
    $("#nowdate").textbox("setText", $.fn.dateFormat("yyyy-MM-dd", new Date(order.orderTime)))
    $("#nowdate").textbox("disable")
    carCode.base = order.jdCode
    carCode.sendTo = order.deliveryCode
    carCode(order.carType, order.carTypeName)


}

function setSelections(details,order) {

    for (var i = 0; i < details.length; i++) {
        var detail = details[i]
        selections[detail.materielCode] = {
            materielId: detail.materielId,
            code: detail.materielCode,
            price: detail.unitPrice,
            name: detail.materielName,
            num: detail.qty,
            amount: detail.amount,
            volume: detail.volume,
            productGroupCode: detail.productGroup
        };
    }
    eleShopCart.querySelector("span").innerHTML = selections.length();
    sethtmls();
    standardMax = order.maxVolume
    standardMin = order.minVolume
    console.log(standardMax)
    console.log(standardMin)
    setprogress();
}