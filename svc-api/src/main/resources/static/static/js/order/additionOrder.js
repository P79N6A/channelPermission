var dataGrid =null;
var editIndex = undefined;
$.fn.combobox.defaults.icons=[{}]
$(function () {
    $("#dialog-div").hide();
    showPro();
    dataGrid=   $('#tab').datagrid({
        striped : true, // 隔行变色
        rownumbers : true,
    /*    fit: true,*/
        singleSelect : true,
        fitColunms : true,
        onClickCell : onClickCell,
        onAfterEdit : onAfterEdit,
        columns:[[
            {
                title : '商品主键',
                width : 120,
                field : 'id',
                align : 'center',
                sortable: false,
            /*    hidden:true*/
            },
            {
                title : '商品名称',
                width : 120,
                field : 'productName',
                align : 'center',
                sortable: false
            },
            {
                title : '单价(节能后补贴)',
                width : 120,
                field : 'unitPrice',
                align : 'center',
                sortable: false,
                editor:'text'
            },
            {
                title : '数量',
                width : 120,
                field : 'number',
                align : 'center',
                sortable: false,
                editor:'numberbox'
            },
            {
                title : '货号',
                width : 120,
                field : 'sku',
                align : 'center',
                sortable: false,
                hidden:'true'
            },
            {
                title : '运费',
                width : 120,
                field : 'freight',
                align : 'center',
                sortable: false,
                editor:'text'
            },
            {
                title : '限价',
                width : 120,
                field : 'limitedPrice',
                align : 'center',
                sortable: false
            },
            {
                title : '价格',
                width : 120,
                field : 'saleGuidePrice',
                align : 'center',
                sortable: false
            },
            {
                title : '操作',
                width : 120,
                field : 'operation',
                align : 'center',
                sortable: false,
                formatter: function(value,row,index){
                    var id=row.id;
                    var rowIndex=$('#tab').datagrid('getRowIndex',row);
                    var obj=row;
                    return ' <a id="remove" href="#" class="easyui-linkbutton" data-options="iconCls:icon-cancel" onclick="onDelete('+id+')">删除</a>'
                }
            }
        ]],
        striped: true
    });

})



    //三级联动省市区
    function showPro() {
        $.ajax({
            url: '/order/getRegions2',
            type:'POST',
            dataType: 'json',
            success: function (data) {
                var tempCombox = [{"code": '', "name": "please", "childs": [{}]}];
                $.each(data.rows, function (i, val) {
                    if (val.regionType == 1) {
                        if (val.parentId == 0) {
                            tempCombox.push({"value": val.id, "text": val.regionName, "childs": [{}]});
                            $("#province").combobox("loadData", tempCombox);
                        }
                    }
                });
            }


        });
    }

    $('#province').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue) {
                showCity(newValue)
                $("#citys").combobox("clear")
                $("#county").combobox("clear")
            }
        }
    });

    function showCity(newValue) {
    var parentId=$('#province').combobox('getValue');
        $.ajax({
            url: '/order/getRegions2',
            type:'POST',
            dataType: 'json',
            data: {
                "parentId":parentId
            },
            success: function (data) {
                var tempCombox = [{"code": '', "name": "please", "childs": [{}]}];
                $.each(data.rows, function (i, val) {
                    if (val.parentId == newValue) {
                        tempCombox.push({"value": val.id, "text": val.regionName, "childs": [{}]});
                        $("#citys").combobox("loadData", tempCombox);
                    }
                });
            }
        });
    }


    $('#citys').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue) {
                showCounty(String(newValue))
                $("#county").combobox("clear")
            }
        }
    });

    function showCounty(newValue) {
    var parentId=$('#citys').combobox('getValue');
        $.ajax({
            url: '/order/getRegions2',
            type:'POST',
            dataType: 'json',
            data: {
                "parentId":parentId
            },
            success: function (data) {
                var tempCombox = [{"code": '', "name": "please", "childs": [{}]}];
                $.each(data.rows, function (i, val) {
                    if (val.parentId == newValue) {
                        tempCombox.push({"value": val.id, "text": val.regionName, "childs": [{}]});
                        $("#county").combobox("loadData", tempCombox);
                    }
                });
            }
        });
}
//点击添加商品执行
function addProduct() {
    $( "#dialog-div" ).dialog({
        conter:true,
        autoOpen: false,
        modal: true,
        height:"80%",
        draggable:false,
        inline:true,
        resizable: true,
        title: '选择商品',
        // top:400,
        close: function( event, ui ) {
            $( "#dialog-div" ).dialog( "close" );
        }

    });
   /* //置灰弹出层的确定按钮(取消置灰enable)
    $('#determine').linkbutton('disable');*/
    //加载弹出层的商品分类下拉列表框
    $('#ProductCates').combobox({
        url:'/order/getProductCates',
        valueField:'id',
        textField:'text'
    });
    //加载弹出层的品牌下拉列表框
    $('#Brands').combobox({
        url:'/order/getBrands',
        valueField:'id',
        textField:'text'
    });
    //加载商品表格
    $.ajax({
        url:'/order/getProducts',
        success: function (data) {
            var body='';
            var div='<div style="margin-top: 10px">';
            var count=0;
            if (data.length==0){
                body+='<div style="text-align: center"><h3 style="color: red;font-weight: bold">条件检索没有数据</h3></div>'
            }
            if (data.length!=0){
                for(var i = 0;i<data.length;i++){
                    if (count==3){
                        div='<div style="margin-top: 10px">';
                        count=0;
                    }
                    var id=data[i].id;
                    var productName=data[i].productName;
                    div+='<label class="checkbox-inline" style="width: 30%">' +
                        '<input type="checkbox" id="inlineCheckbox1" value="'+id+'">'+productName+
                    '</label>';
                    if (count==2||i==data.length-1){
                        div+='</div>';
                        body+=div;
                    }
                    count++
                }
            }
            $('#lmsisSb').html(body);
        }
    })
}
function selectProduct() {
    var ProductCates=$('#ProductCates').combobox('getValue') ;
    var Brands=$('#Brands').combobox('getValue');
    var productName=$('#productName').textbox('getValue');
    var sku=$('#sku').textbox('getValue');
                $.ajax({
        type: 'POST',
        url:'/order/getProductsByJson',
        data: {
            "sku": sku,
            "productName": productName,
            "ProductCates": ProductCates,
            "Brands":Brands
        },
        success: function (data) {
            var body='';
            var div='<div style="margin-top: 10px">';
            var count=0;
            if (data.length==0){
                body+='<div style="text-align: center"><h3 style="color: red;font-weight: bold">条件检索没有数据</h3></div>'
            }
            if (data.length!=0){
                for(var i = 0;i<data.length;i++){
                    if (count==3){
                        div='<div style="margin-top: 10px">';
                        count=0;
                    }
                    var id=data[i].id;
                    var productName=data[i].productName;
                    div+='<label class="checkbox-inline" style="width: 30%">' +
                        '<input type="checkbox" name="check" id="inlineCheckbox1" value="'+id+'">'+productName+
                        '</label>';
                    if (count==2||i==data.length-1){
                        div+='</div>';
                        body+=div;
                    }
                    count++
                }
            }
            $('#lmsisSb').html(body);
        }
    })
}
function Determine() {
    var checkbox=$("input[type='checkbox']");
    var mycars=new Array();
    for (var i=0;i<checkbox.length;i++){
      if (checkbox[i].checked){
          mycars.push(checkbox[i].value);
      }
    }
    if (mycars.length==0){
        $.messager.alert('提示',"至少要选择一个商品")
        return;
    }
    $( "#dialog-div").dialog( "close" );
    $.ajax({
        type: 'POST',
        url:'/order/appendProduct',
        data:{
            data:JSON.stringify(mycars),
        },
        success: function (data) {
            for (var i=0;i<data.length;i++){
                dataGrid.datagrid('appendRow',data[i]);
            }
        }
    })
}
function Cancel() {

}
//删除dataGird表格中的商品
function onDelete(id) {
    var rows = $('#tab').datagrid("getRows");
    for (var i=0;i<rows.length;i++){
        if (id==rows[i].id){
            $('#tab').datagrid('deleteRow', i);
        }
    }
    dataGrid.datagrid("loadData", rows);
    dataGrid.datagrid('reload');
}
//点击保存订单信息
function onSaveOrderInfo() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息
    var receiptInfo=$('#receiptInfo').serializeObject();//收货人信息

    var invoiceInfo=$('#invoiceInfo').serializeObject();//发票信息
    var otherInfo=$('#otherInfo').serializeObject();//其他信息
    var rows=$('#tab').datagrid('getRows');
    var idGift=baseInfo.idGift;//是否赠品

    var isProduceDaily=baseInfo.isProduceDaily;//是否天猫日日单
    var orderUserName=baseInfo.orderUserName;//下单人
//    if(orderUserName ==""||orderUserName ==null){
//        alert("下单人不能为空,请填写!");
//        return false;
//    }
    var userEmail=baseInfo.userEmail;//Email
//    if(userEmail ==""||userEmail ==null){
//        alert("Email不能为空,请填写!");
//        return false;
//    }
    var orderValidPeriod=baseInfo.orderValidPeriod;//订单过期时间
//    var industrys=baseInfo.industrys;//所属产业
//    if("---所属产业---"==industrys||""==industrys){
//        alert("所属产业不能为空,请选择!");
//        return false;
//    }
//    var source=baseInfo.source;//订单来源
//    if("---订单来源---"==source||""==source){
//        alert("订单来源不能为空,请选择!");
//        return false;
//    }
    var source = $('#source').combobox('getValue');
    if ("" == source) {
        alert("请选择订单来源");
        return false;
    }
    var industrys = $('#industrys').combobox('getValue');
    if ("" == industrys) {
        alert("请选择所属产业");
        return false;
    }

    var sourceOrderSn=baseInfo.sourceOrderSn;//来源订单号
//    if(sourceOrderSn ==""||sourceOrderSn ==null){
//        alert("来源订单号不能为空,请填写!");
//        return false;
//    }

    /*=====收货人信息====*/
    var consignee=receiptInfo.consignee;//收货人
    if(consignee ==""||consignee ==null){
        alert("收货人不能为空,请填写!");
        return false;
    }

    var province=receiptInfo.province;//省
    if(province ==""||province ==null){
        alert("省不能为空,请选择!");
        return false;
    }
    var citys=receiptInfo.citys;//市
    if(citys ==""||citys ==null){
        alert("市不能为空,请选择!");
        return false;
    }
    var county=receiptInfo.county;//区
    if(county ==""||county ==null){
        alert("区不能为空,请选择!");
        return false;
    }
    var address=receiptInfo.address;//详细地址
    if(address ==""||address ==null){
        alert("详细地址不能为空,请填写!");
        return false;
    }
    var zipcode=receiptInfo.zipcode;//邮政编码
    if(zipcode ==""||zipcode ==null){
        alert("邮政编码不能为空,请填写!");
        return false;
    }

    var mobile=receiptInfo.mobile;//电话
//    if(mobile ==""||mobile ==null){
//        alert("电话不能为空,请选择!");
//        return false;
//    }
    var deliverTime=receiptInfo.deliverTime;//配送时间

    var phone=receiptInfo.phone;//手机号
//    if(phone ==""||phone ==null){
//        alert("手机号不能为空,请填写!");
//        return false;
//    }
    /*======================*/
    if(rows == null ||""==rows){
        alert("商品信息不能为空,请最少选择一件商品!");
        return false;
    }

    /*============发票信息==============*/
    var InvoiceContent=invoiceInfo.InvoiceContent;
    var invoiceTitle=invoiceInfo.invoiceTitle;
    if(InvoiceContent =="普通发票"){
        if(invoiceTitle ==""||invoiceTitle ==null){
            alert("发票抬头不能为空,请填写!");
            return false;
        }
    }
    var companyName=invoiceInfo.companyName;
    if(InvoiceContent == "增值税发票"){
    if(companyName ==""||companyName ==null){
        alert("公司名称不能为空,请填写!");
        return false;
    }
    }
    var taxPayerNumber=invoiceInfo.taxPayerNumber;
    if(InvoiceContent == "增值税发票"){
        if(taxPayerNumber ==""||taxPayerNumber ==null){
            alert("纳税人识别号不能为空,请填写!");
            return false;
        }
    }
    var registerAddress=invoiceInfo.registerAddress;
    if(InvoiceContent == "增值税发票"){
        if(registerAddress ==""||registerAddress ==null){
            alert("注册地址不能为空,请填写!");
            return false;
        }
    }
    var registerPhone=invoiceInfo.registerPhone;
    if(InvoiceContent == "增值税发票"){
        if(registerPhone ==""||registerPhone ==null){
            alert("注册电话不能为空,请填写!");
            return false;
        }
    }
    var bankName=invoiceInfo.bankName;
    if(InvoiceContent == "增值税发票"){
        if(bankName ==""||bankName ==null){
            alert("开户银行不能为空,请填写!");
            return false;
        }
    }
    var bankCardNumber=invoiceInfo.bankCardNumber;
    if(InvoiceContent == "增值税发票"){
        if(bankCardNumber ==""||bankCardNumber ==null){
            alert("开户账号不能为空,请填写!");
            return false;
        }
    }
    var isInvoice=invoiceInfo.isInvoice;
    var isElectronicInvoice=invoiceInfo.isElectronicInvoice;

    var productInvoiceTogether=invoiceInfo.productInvoiceTogether;
    /*===================================*/
    /*================其他信息==================*/
    var connectOrderNum=otherInfo.connectOrderNum;
//    if(connectOrderNum ==""|| connectOrderNum == null){
//        alert("关联订单号不能为空,请填写!");
//        return false;
//    }
    var remark=otherInfo.remark;
    /*========================================*/

    $.ajax({
        type: 'POST',
        traditional:true,
        async : false,
        url:'/order/onSaveOrderInfo',
        dataType : 'json',
        data:{
            "idGift":idGift,//是否赠品
            "industrys":industrys,//所属产业
            "isProduceDaily":isProduceDaily,//是否天猫日日单
            "orderUserName":orderUserName,//下单人
            "orderValidPeriod":orderValidPeriod,//订单过期时间
            "source":source,//订单来源
            "sourceOrderSn":sourceOrderSn,//来源订单号
            "userEmail":userEmail,//Email
            "address":address,//详细地址
            "consignee":consignee,//收货人
            "province":province,//省
            "citys":citys,//市
            "county":county,//区
            "zipcode":zipcode,//邮政编码
            "mobile":mobile,//电话
            "deliverTime":deliverTime,//配送时间
            "phone":phone,//手机号
            "data":JSON.stringify(rows),
            "invoiceTitle":invoiceTitle,

            "companyName":companyName,
            "taxPayerNumber":taxPayerNumber,
            "registerAddress":registerAddress,
            "registerPhone":registerPhone,
            "bankName":bankName,
            "bankCardNumber":bankCardNumber,

            "isInvoice":isInvoice,//是否开具发票
            "isElectronicInvoice":isElectronicInvoice,//是否开具电子发票
            "InvoiceContent":InvoiceContent,//是否开具电子发票
            "productInvoiceTogether":productInvoiceTogether,
            "connectOrderNum":connectOrderNum,
            "remark":remark
},
        success: function (data) {
            if (data.text == "success") {
                alert("保存成功");
             //   window.location.reload();
                return false;
            }else if(data.text == "fail"){
                alert('保存失败');
                return false;
            }else if(data.text == "useup"){
                alert("月度费用已占用完，不允许继续录入赠品机");
                return false;
            }else{
                alert(data.text)
                return false;
            }
        }
    })



}


function orderUserNames() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息
    var orderUserName=baseInfo.orderUserName;//下单人
//    if(orderUserName == null||orderUserName == ""){
//        alert("下单人不能为空,请填写!");
//        return false;
//    }
}
/*function mobiles() {
    var receiptInfo=$('#receiptInfo').serializeObject();//收货人信息
    var mobile=receiptInfo.mobile;//电话
    if(mobile == null ||""==mobile){
        alert("电话不能为空,请填写!");
        return false;
    }
}*/
//校验关联订单号
function checkconnectOrderNum() {
    var otherInfo=$('#otherInfo').serializeObject();//其他信息
    var connectOrderNum=otherInfo.connectOrderNum;
//    if(connectOrderNum == null ||""==connectOrderNum){
//        alert("关联订单号不能为空,请填写!");
//        return false;
//    }
/*    if(connectOrderNum.size <51){
        alert("关联订单号长度不能超过50,请重新填写!");
        return false;
    }*/
    if (!connectOrderNums(connectOrderNum)) {

        alert("关联订单号长度不能超过50,请重新填写!");
        return false;
    }
}
//校验详细地址
function checkaddress() {
    var receiptInfo=$('#receiptInfo').serializeObject();//收货人信息
    var address=receiptInfo.address;
    if(address == null ||""==address){
        alert("详细地址不能为空,请填写!");
        return false;
    }
}
//校验发票抬头
function checkinvoiceTitle() {
    var invoiceInfo=$('#invoiceInfo').serializeObject();//发票信息
    var value = $('#InvoiceContent option:selected') .val();//选中的值;
    var invoiceTitle=invoiceInfo.invoiceTitle;
    if(value=="普通发票"){
        if(invoiceTitle == null ||""==invoiceTitle){
            alert("发票抬头不能为空,请填写!");
            return false;
        }
    }

}
function companyNames() {
    var invoiceInfo = $('#invoiceInfo').serializeObject();//发票信息
    var value = $('#InvoiceContent option:selected').val();//选中的值;
    var companyName = invoiceInfo.companyName;
    if (value == "增值税发票") {
        if (companyName == null || "" == companyName) {
            alert("公司不能为空,请填写!");
            return false;
        }
    }

}
function taxPayerNumbers() {
    var invoiceInfo=$('#invoiceInfo').serializeObject();//发票信息
    var value = $('#InvoiceContent option:selected') .val();//选中的值;
    var taxPayerNumber=invoiceInfo.taxPayerNumber;
    if(value=="增值税发票"){
        if(taxPayerNumber == null ||""==taxPayerNumber){
            alert("纳税人识别号不能为空,请填写!");
            return false;
        }
    }

}
function registerAddresss() {
    var invoiceInfo=$('#invoiceInfo').serializeObject();//发票信息
    var value = $('#InvoiceContent option:selected') .val();//选中的值;
    var registerAddress=invoiceInfo.registerAddress;
    if(value=="增值税发票"){
        if(registerAddress == null ||""==registerAddress){
            alert("注册地址不能为空,请填写!");
            return false;
        }
    }

}
function registerPhones() {
    var invoiceInfo=$('#invoiceInfo').serializeObject();//发票信息
    var value = $('#InvoiceContent option:selected') .val();//选中的值;
    var registerPhone=invoiceInfo.registerPhone;
    if(value=="增值税发票"){
        if(registerPhone == null ||""==registerPhone){
            alert("注册电话不能为空,请填写!");
            return false;
        }
        if (!checkMobile(registerPhone)) {

            alert("注册电话格式不正确,请填写!");
            return false;
        }

    }

}
function bankNames() {
    var invoiceInfo=$('#invoiceInfo').serializeObject();//发票信息
    var value = $('#InvoiceContent option:selected') .val();//选中的值;
    var bankName=invoiceInfo.bankName;
    if(value=="增值税发票"){
        if(bankName == null ||""==bankName){
            alert("开户银行不能为空,请填写!");
            return false;
        }
    }

}
function bankCardNumbers() {
    var invoiceInfo=$('#invoiceInfo').serializeObject();//发票信息
    var value = $('#InvoiceContent option:selected') .val();//选中的值;
    var bankCardNumber=invoiceInfo.bankCardNumber;
    if(value=="增值税发票"){
        if(bankCardNumber == null ||""==bankCardNumber){
            alert("开户账号不能为空,请填写!");
            return false;
        }
        if (!Nums(bankCardNumber)) {

            alert("开户账号格式不正确,请重新填写!");
            return false;
        }
    }

}
//校验省市区
/*function checkProvice(rec) {
    var county= rec.text;
    if(county==null||county==''){
        alert("省市区不能为空!");
        return false;
    }
}*/
//检验邮政编码
function checkzipcode() {
    var receiptInfo=$('#receiptInfo').serializeObject();//收货人信息
    var zipcode=receiptInfo.zipcode;
    if(zipcode == null ||""==zipcode){
        alert("邮政编码不能为空,请填写!");
        return false;
    }

    if(zipcode.length != 6){
        alert("邮政编码格式不正确,必须是六位数字!");
        return false;
    }
}
//校验Email
function checkEmsil() {
    var baseInfo=$('#baseInfo').serializeObject();
    var userEmail=baseInfo.userEmail;//Email
    if(userEmail == null||""==userEmail){
        return true;
    }
    if(!isEmail(userEmail)){
        alert("Emai格式不正确,请重新填写!");
        return false;}
}
//校验所属产业
/*function checkIndustrys1(rec) {
    var industrys=rec.industryCode;//所属产业
    if("-所属产业-"==industrys){
        alert("所属产业不能为空,请选择!");
        return false;
    }
}*/
//校验手机号
function checkphone() {
    var receiptInfo=$('#receiptInfo').serializeObject();//收货人信息
    var phone=receiptInfo.phone;
//    if(phone == null ||""==phone){
//        alert("手机号不能为空,请填写!");
//        return false;
//    }
    if (!checkMobile(phone)) {

        alert("手机号格式不正确,请重新填写!");
        return false;
    }

}
//校验订单来源
/*function checkSource(rec) {
    var source=rec.configType;//订单来源
    if("-订单来源-"==source){
        alert("订单来源不能为空,请选择!");
        return false;
    }
}*/
//校验邮件
function isEmail(strEmail) {
    if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
        return true;
    else
        return false;
}


//校验来源订单号
function checkSourceOrderSn() {
//    var baseInfo=$('#baseInfo').serializeObject();
//    var sourceOrderSn=baseInfo.sourceOrderSn;//Email
//    if(sourceOrderSn == null||""==sourceOrderSn){
//        alert("订单来源不能为空,请填写!");
//        return false;
//    }
}
//校验收货人
function checkconsignee() {
    var receiptInfo=$('#receiptInfo').serializeObject();//收货人信息
    var consignee=receiptInfo.consignee;
    if(consignee == null || ""==consignee){
        alert("收货人不能为空,请填写!");
        return false;
    }
}
//校验手机号
function checkMobile(str) {
    var re = /^1\d{10}$/;
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
}

function connectOrderNums(str) {
    var re = /^\S{1,50}$/;
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
}
function Nums(str) {
    var re = /^\d{16,19}$/;
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
}

function indent() {
 /*   var url = '/orderOperation/orderList';
    window.open= url;*/
    window.open('/orderOperation/orderList');
}

function impotr(){
    var url = '/order/importInvWarehouse';
    window.location.href = url;
    return false;
}
$("select#InvoiceContent").change(function(){

    var value = $('#InvoiceContent option:selected') .val();//选中的值;
      if(value=="增值税发票")  {
          // document.getElementById("common").style.display="";
          /*$("#increase").hide();
          $("#common").show();*/
          $("#common").attr("style","display:none;");//隐藏div

          $("#increase").attr("style","display:block;");//显示div
          // document.getElementById("increase").style.display="none";
      }
    if(value=="普通发票")  {
       /* document.getElementById("common").style.display="none";
        document.getElementById("increase").style.display="";*/
        $("#increase").attr("style","display:none;");//隐藏div

        $("#common").attr("style","display:block;");//显示div
    }

     });


		$.extend($.fn.datagrid.methods, {
			editCell: function(jq,param){
				return jq.each(function(){
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field){
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor1;
					}
				});
			}
		});

		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#tab').datagrid('validateRow', editIndex)){
				$('#tab').datagrid('endEdit', editIndex);
                editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickCell(index, field){
			if (endEditing()){
				$('#tab').datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				editIndex = index;
			}
		}


		//单元格失去焦点执行的方法
        function onAfterEdit(index, row, changes) {
            $('#tab').datagrid('updateRow',{
                index: index,
                row: {
                    saleGuidePrice: row.unitPrice*row.number+parseFloat(row.freight)
                }
            });
//            var updated = $('#tab').datagrid('getChanges', 'updated');
//            if (updated.length < 1) {
//            editRow = undefined;
//            $('#tab').datagrid('unselectAll');
//            return;
//            } else {
//
//            // 传值
//            alert(row);
//            }
        }