(function(){
    

//    $("#saveOrder").on('click',function(){
//        
//        var param1 = $("#invoiceInfo").serializeObject();
//        var param2 = $("#baseInfo").serializeObject();
//        var param3 = $("#receiptInfo").serializeObject();
//        // var param4 = $("#productInfo").serializeObject();
//        var param6 = $('#table').bootstrapTable('getData', true);
//        var param5 = $('#otherInfo').serializeObject();
//        console.log(param1);
//        console.log(param2);
//        console.log(param3);
//        // console.log(param4);
//        console.log(param5);
//        console.log(param6);
//    });
}());


//保存
function save(){
	var params = $('#baseInfo').serializeObject();
	var receiptInfo = $('#receiptInfo').serializeObject();
	params.consignee=receiptInfo.consignee;
	params.province=receiptInfo.province;
	params.citys=receiptInfo.citys;
	params.county=receiptInfo.county;
	params.address=receiptInfo.address;
	params.zipcode=receiptInfo.zipcode;
	params.mobile=receiptInfo.mobile;
	params.phone=receiptInfo.phone;
	params.deliverTime=receiptInfo.deliverTime;
	params.invocie= JSON.stringify($('#invoiceInfo').serializeObject())
//	var producr = JSON.stringify($('#table').bootstrapTable('getData'));
	params.productsVo= JSON.stringify($('#table').bootstrapTable('getData'));
	 $.messager.confirm('询问','确定保存吗？',function(r){
	if(r){
	$.ajax({
        url : '/orderOperation/copyProductSave',
        type        : "POST",
        dataType : 'json',
        contentType : "application/json;charset=utf-8",
        data        : JSON.stringify(params),
         success: function (jsonstr) {  
        	 if(jsonstr.success){
        		 $.messager.alert("提示",jsonstr.msg);
        	 }
        	
            }
    });  
		 }
	 });
}
function closeOrders(){
    window.history.back();
}

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a,
	function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};
