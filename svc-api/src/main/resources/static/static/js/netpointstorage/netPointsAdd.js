var dataGrid =null;
$.fn.combobox.defaults.icons=[{}]
$(function () {
    showPro();
})
    //三级联动省市区
    function showPro() {
        $.ajax({
            url: '/netpoint/getRegions',
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
            url: '/netpoint/getRegions',
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


//点击保存订单信息
function onSaveNetPointsInfo() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息
    var contactInfo=$('#contactInfo').serializeObject();//收货人信息

    var netPointName=baseInfo.netPointName;//公司名称
    if(netPointName ==""||netPointName ==null){
        alert("公司名称不能为空,请填写!");
        return false;
    }else {

    }
    var netPointCode=baseInfo.netPointCode;//网点代码
    if(netPointCode ==""||netPointCode ==null){
        alert("网点代码不能为空,请填写!");
        return false;
    }
    var netPointN8=baseInfo.netPointN8;//8码
    if(netPointN8 ==""||netPointN8 ==null){
        alert("8码不能为空,请填写!");
        return false;
    }else {
        checkNetPointN8();
    }
    var province=baseInfo.province;//省
    if(province ==""||province ==null){
        alert("省不能为空,请选择!");
        return false;
    }
    var citys=baseInfo.citys;//市
    if(citys ==""||citys ==null){
        alert("市不能为空,请选择!");
        return false;
    }
    var contactAddress=baseInfo.contactAddress;//详细地址
    if(contactAddress ==""||citys ==contactAddress){
        alert("详细地址不能为空,请选择!");
        return false;
    }

    var TCCode=baseInfo.TCCode;//所属库位编码
    if(TCCode ==""||TCCode ==null){
        alert("所属库位编码不能为空,请填写!");
        return false;
    }
    var shippingTimeLimit=baseInfo.shippingTimeLimit;//配送时效
    if(shippingTimeLimit ==""||shippingTimeLimit ==null){
        alert("配送时效不能为空,请填写!");
        return false;
    }
    var shippingDistance=baseInfo.shippingDistance;//配送里程
    if(shippingDistance ==""||shippingDistance ==null){
        alert("配送里程不能为空,请填写!");
        return false;
    }
    var logisticsTimeLimit=baseInfo.logisticsTimeLimit;//物流配送
    if(logisticsTimeLimit ==""||logisticsTimeLimit ==null){
        alert("物流配送时效不能为空,请填写!");
        return false;
    }

    var contactName=contactInfo.contactName;//姓名
    if(contactName ==""|| contactName == null){
        alert("姓名不能为空,请填写!");
        return false;
    }

    var jingDu=baseInfo.jingDu;//经度
    var weiDu=baseInfo.weiDu;//纬度
    var phone=contactInfo.phone;//手机
    var areaCode=contactInfo.areaCode;//区号
    var mobile=contactInfo.mobile;//电话
    var userEmail=contactInfo.userEmail;//Email

    $.ajax({
        type: 'POST',
        traditional: true,
        async: false,
        url: '/netpoint/saveNetPointInfo',
        dataType: 'json',
        data: {
            "netPointName": netPointName,//公司名称
            "netPointCode": netPointCode,//网点代码
            "netPointN8": netPointN8,//8码
            "contactProvinceId": province,//省
            "contactCityId": citys,//市
            "contactAddress": contactAddress,//详细地址
            "jingDu": jingDu,//经度
            "weiDu": weiDu,//纬度
            "TCCode": TCCode,//所属库位编码
            "shippingTimeLimit": shippingTimeLimit,//配送时效
            "shippingDistance": shippingDistance,//配送里程
            "logisticsTimeLimit": logisticsTimeLimit,//物流配送
            "contactName": contactName,//姓名
            "contactMobile": phone,
            "areaCode": areaCode,
            "phoneNumber": mobile,//电话
            "contactEmail": userEmail,//Email
        },
            success: function (data) {
                if (data.flag == 1) {
                    alert("保存成功");
                    window.location.reload();
                    return false;
                } else if (data.flag == 2) {
                    alert('保存失败');
                    return false;
                }else if (data.flag == 4) {
                    alert('添加失败,网点8码已存在');
                    return false;
                }else if (data.flag == 5) {
                    alert('添加失败,网点代码已存在');
                    return false;
                }else {
                    alert("操作异常,添加失败")
                    return false;
                }
            }
        })



}

function checkNetPointName() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息
    var netPointName=baseInfo.netPointName;//公司名称
    if(netPointName == null||netPointName == ""){
        alert("公司名称不能为空,请填写!");
        return false;
    }
}
function checkNetPointCode() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息
    var netPointCode=baseInfo.netPointCode;//公司名称
    if(netPointCode == null||netPointCode == ""){
        alert("网点代码不能为空,请填写!");
        return false;
    }
}
function checkNetPointN8() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息
    var netPointN8=baseInfo.netPointN8;//公司名称
    if(netPointN8 == null||netPointN8 == ""){
        alert("8码不能为空,请填写!");
        return false;
    }
    if (!checkNetPointN8Regex(netPointN8)) {
        alert("网点8码格式不正确,必须为10位数字,请重新填写!");
        return false;
    }
}

//校验详细地址
function checkContactAddress() {
    var baseInfo=$('#baseInfo').serializeObject();//收货人信息
    var contactAddress=baseInfo.contactAddress;
    if(contactAddress == null ||""==contactAddress){
        alert("详细地址不能为空,请填写!");
        return false;
    }
}
function checkTCCode() {
    var baseInfo=$('#baseInfo').serializeObject();//收货人信息
    var TCCode=baseInfo.TCCode;
    if(TCCode == null ||""==TCCode){
        alert("所属库位编码不能为空,请填写!");
        return false;
    }
}
function checkShippingTimeLimit() {
    var baseInfo=$('#baseInfo').serializeObject();//收货人信息
    var shippingTimeLimit=baseInfo.shippingTimeLimit;
    if(shippingTimeLimit == null ||""==shippingTimeLimit){
        alert("配送时效不能为空,请填写!");
        return false;
    }
    if (!TimeLimitRegex(shippingTimeLimit)) {
        alert("配送时效格式不正确,请输入一个大于0的数字!");
        return false;
    }
}
function checkShippingDistance() {
    var baseInfo=$('#baseInfo').serializeObject();//收货人信息
    var shippingDistance=baseInfo.shippingDistance;
    if(shippingDistance == null ||""==shippingDistance){
        alert("配送里程不能为空,请填写!");
        return false;
    }
    if (!TimeLimitRegex(shippingDistance)) {
        alert("配送历程格式不正确,请输入一个大于0的数字!");
        return false;
    }
}
function checkLogisticsTimeLimit() {
    var baseInfo=$('#baseInfo').serializeObject();//收货人信息
    var logisticsTimeLimit=baseInfo.logisticsTimeLimit;
    if(logisticsTimeLimit == null ||""==logisticsTimeLimit){
        alert("物流配送时效不能为空,请填写!");
        return false;
    }
    if (!TimeLimitRegex(logisticsTimeLimit)) {
        alert("物流配送时效格式不正确,请输入一个大于0的数字!");
        return false;
    }
}
function checkUserName() {
    var contactInfo=$('#contactInfo').serializeObject();//姓名
    var contactName = contactInfo.contactName;
    if(contactName == null ||""==contactName){
        alert("姓名不能为空,请填写!");
        return false;
    }
}

//校验手机号
function checkphone() {
    var contactInfo=$('#contactInfo').serializeObject();//手机号
    var phone=contactInfo.phone;

    if(phone != "" && phone !=null){
        if (!checkMobile(phone)) {
            alert("手机号格式不正确,手机号应为11位数字,请重新填写!");
            return false;
        }
    }


}

//校验Email
function checkEmsil() {
    var contactInfo=$('#contactInfo').serializeObject();
    var userEmail=contactInfo.userEmail;//Email
    if(!isEmail(userEmail)){
        alert("Emai格式不正确,请输入合法的电子邮箱地址,例如:'fred@domain.com'!");
        return false;}
}
//校验区号
function checkAreaCode() {
    var contactInfo=$('#contactInfo').serializeObject();
    var areaCode=contactInfo.areaCode;//区号
    if(areaCode != "" && areaCode !=null){
        if(!isAreaCode(areaCode)){
            alert("区号格式不正确,请输入不超过4位的区号");
            return false;}
    }

}
//校验邮件
function isEmail(strEmail) {
    if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
        return true;
    else
        return false;
}
//校验区号
function isAreaCode(strAreaCode) {
    var re = /^\d{1,4}$/;
    if (re.test(strAreaCode)){
        return true;
    } else
        return false;
}

//校验手机号
function checkMobile(str) {
    var re = /^1\d{10}$/;
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
}//校验8码
function checkNetPointN8Regex(str) {
    var re = /^\d{10}$/;
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
}
//检验配送时效,里程,物流时效
function TimeLimitRegex(str) {
    var re = /^\+?[1-9][0-9]*$/;
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
}


