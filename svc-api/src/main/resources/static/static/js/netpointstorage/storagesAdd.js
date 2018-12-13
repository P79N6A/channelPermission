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


// $('#citys').combobox({
//     onChange: function (newValue, oldValue) {
//         if (newValue) {
//             showCounty(String(newValue))
//             $("#county").combobox("clear")
//         }
//     }
// });
//
// function showCounty(newValue) {
//     $.ajax({
//         url: '/order/getRegions',
//         type:'POST',
//         dataType: 'json',
//         success: function (data) {
//             var tempCombox = [{"code": '', "name": "please", "childs": [{}]}];
//             $.each(data.rows, function (i, val) {
//                 if (val.parentId == newValue) {
//                     tempCombox.push({"value": val.code, "text": val.regionName, "childs": [{}]});
//                     $("#county").combobox("loadData", tempCombox);
//                 }
//             });
//         }
//     });
// }

function cityIdChanged(obj) {
    jQuery.ajax({
        url: "/storage/getRegionOptions",
        data:"provinceId="+obj.value,
        type: "GET",
        success: function(data) {
            var json = JSON.parse(data);
            var el = $("select.city1");
            $("#cityId").empty();
            for (key in json.data) {
                $("#cityId").append("<option value=\""+json.data[key]["cityId"]+"\">"+json.data[key]["cityName"]+"</option>");
            }
        }
    });
}

function regionIdChanged(obj) {
    jQuery.ajax({
        url: "/storage/getRegionByCiyId",
        data:"cityId="+obj.value,
        type: "GET",
        success: function(data) {
            var json = JSON.parse(data);
            var el = $("select.city1");
            $("#regionId").empty();
            for (key in json.data) {
                $("#regionId").append("<option value=\""+json.data[key]["regionId"]+"\">"+json.data[key]["regionName"]+"</option>");
            }
        }
    });
}


function addRegion() {
    var src = document.getElementById('regionId');
    var dest = document.getElementById('regionIds');
    add(src,dest);
}

function addSimProductIds() {
    var src = document.getElementById('ProductIds');
    var dest = document.getElementById('simProductIds');
    add(src,dest);
}

function add(src,dest) {
    for (var i = 0; i < src.options.length; i++)
    {
        if (src.options[i].selected && src.options[i].value != '0')
        {
            var exist = false;
            for (var j = 0; j < dest.options.length; j++)
            {
                if (dest.options[j].value == src.options[i].value || dest.options[j].value == '1')
                {
                    exist = true;
                    break;
                }
            }
            if (src.options[i].value == '1')
            {
                for (var i = dest.options.length - 1; i >= 0 ; i--)
                {
                    if (dest.options[i])
                    {
                        dest.options[i] = null;
                    }
                }
                exist = true;
                break;
            }
            if (!exist)
            {
                var opt = document.createElement('option');
                opt.value = src.options[i].value;
                opt.text = src.options[i].text;
                dest.options.add(opt);
            }
        }
    }
}


function delRegion()
{
    var dest = document.getElementById('regionIds');
    del(dest);
}
function delSimProductIds()
{
    var dest = document.getElementById('simProductIds');
    del(dest);
}

function del(dest){
    for (var i = dest.options.length - 1; i >= 0 ; i--)
    {
        if (dest.options[i].selected)
        {
            if(dest.options[i].value == '1')
            {
                var src = document.getElementById('regionId');
            }
            dest.options[i] = null;
        }
    }

}

//点击保存订单信息
function onSaveStoragesInfo() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息

    var storageName=baseInfo.storageName;//库位名称
    if(storageName ==""||storageName ==null){
        alert("库位名称不能为空,请填写!");
        return false;
    }
    var storageCode=baseInfo.storageCode;//库位码
    if(storageCode ==""||storageCode ==null){
        alert("库位码不能为空,请填写!");
        return false;
    }
    var storageType=baseInfo.storageType;//库位类型
    if(storageType ==""||storageType ==null){
        alert("库位类型不能为空,请选择!");
        return false;
    }
     var province=baseInfo.province;//省
  /*  if(province ==""||province ==null){
        alert("省不能为空,请选择!");
        return false;
    }*/
    var citys=baseInfo.citys;//市
   /* if(citys ==""||citys ==null){
        alert("市不能为空,请选择!");
        return false;
    }*/
    var centerCode=baseInfo.centerCode;//中心代码
    var industryCode=baseInfo.industryCode;//工贸代码
    var industryName=baseInfo.industryName;//工贸名称
    var area=baseInfo.area;//所属区域
    var isSupportCod=baseInfo.isSupportCod;//是否支持货到付款
    var limitTime=baseInfo.limitTime;//预计送达时间
    var remark=baseInfo.remark;//备注

    if(centerCode !=null ){
        centerCode.trim();
    }
    if(industryCode !=null ){
        industryCode.trim();
    }
    if(industryName !=null ){
        industryName.trim();
    }
    if(limitTime !=null ){
        limitTime.trim();
    }
    if(remark !=null ){
        remark.trim();
    }

    $.ajax({
        type: 'POST',
        traditional: true,
        async: false,
        url: '/storage/saveStorageInfo',
        dataType: 'json',
        data: {
            "name": storageName.trim(),//库位名称
            "code": storageCode.trim(),//库位码
            "type": storageType,//库位类型
            "contactProvinceId": province,//省
            "centerCity": citys,//市
            "centerCode": centerCode,//中心代码
            "industryCode": industryCode,//工贸代码
            "industryName": industryName,//工贸名称
            "area": area,//所属区域
            "isSupportCod": isSupportCod,//是否支持货到付款
            "limitTime": limitTime,//预计送达时间
            "remark": remark//备注

        },
            success: function (data) {
                if (data.flag == 1) {
                    $.messager.confirm('新增成功', '返回库位列表，请点击确认？', function(r){
                        if (r){
                            window.location.href="/storage/loadStorageListPage.html";
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

function checkStorageName() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息
    var storageName=baseInfo.storageName;//库位名称
    if(storageName == null||storageName == ""){
        alert("库位名称不能为空,请填写!");
        return false;
    }
}
function checkStorageCode() {
    var baseInfo=$('#baseInfo').serializeObject();//基本信息
    var storageCode=baseInfo.storageCode;//库位码
    if(storageCode == null||storageCode == ""){
        alert("库位码不能为空,请填写!");
        return false;
    }
}





