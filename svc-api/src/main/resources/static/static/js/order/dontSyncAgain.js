function SearchClear() {
    $('#source').combobox('setValue', "");
    $('#orderId').textbox('setValue', '');
}
function send() {
    var source = $("#source").combobox("getValue");
    var orderId = $("#orderId").val();
    if (orderId == "") {
        autoAlt("外部订单ID不用为空");
        return false;
    }
    if (source == ""){
        autoAlt("渠道名称不用为空");
        return false;
    }
    $.ajax({
        type : 'POST',
        async : false,
        url : "/eop/againSync/dontSyncAgain",
        datatype : 'JSON',
        data : {
            source:source,
            orderId:orderId
        },
        success : function(data, textStatus) {
            autoAlt(data)
        },
        error : function(data) {
            autoAlt("error")
        }
    });
}