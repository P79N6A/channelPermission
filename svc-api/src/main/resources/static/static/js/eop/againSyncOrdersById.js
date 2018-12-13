

function SearchClear() {
    $('#reqType').combobox('setValue', "");
    $('#orderId').textbox('setValue', '');
}

function add() {
    var reqType = $("#reqType").combobox("getValue");
    var orderId = $("#orderId").val();
    if (orderId == "") {
        autoAlt("外部订单ID不用为空");
        return false;
    }
    if (reqType == ""){
        autoAlt("渠道名称不用为空");
        return false;
    }
    $.ajax({
        type : 'POST',
        async : false,
        url : "/eop/againSync/pointIdSyncOrders",
        datatype : 'JSON',
        data : {
            reqType:reqType,
            orderId:orderId
        },

        success : function(data, textStatus) {
            autoAlt(data);
        },
        error : function(data) {
            autoAlt("error")
        }
    });
}





