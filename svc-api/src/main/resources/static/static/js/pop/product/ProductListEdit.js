/**
 * Pop可售商品详细页
 * Created by PC011 on 2017/11/10.
 */

var editIndex = undefined;
var nindex;
var flag1 = true;
var shiid;
var shiname;
var shengid;
var shengname;
var proid = "";
var messig = true;
var timefalg;
//页面加载判断页面状态
var gloid = $("#id").val();
var gid = $("#id").val();
if (gid == "$gloid") {
    gloid = "";
    $("#id").val("");
} else {
    $("#status").val("1");
    SearchUnit(gloid);
    BingSearchUnit();
}

$("#oDialog").css('display', 'none');
$("#oDialog1").css('display', 'none');
$("#oDialog2").css('display', 'none');
$("#oDialog3").css('display', 'none');
$("#channelId").combobox({
    url: '/pop/product/channelsList',
    valueField: "id",
    textField: "channelcode",
    required: false,
    editable: false,
    hasDownArrow: true,
    filter: function (q, row) {
        var opts = $(this).combobox('options');
        return row[opts.textField].indexOf(q) >= 0;
    },
    onSelect: function (record) {
    },
    onUnselect: function () {
    }
});
$('#onSale').combobox({
    editable: false

});
$('#upriceendtime').datetimebox({
    editable: false

});
$('#priceendtime').datetimebox({
    editable: false

});
$('#pricestarttime').datetimebox({
    editable: false

});
$('#upricestarttime').datetimebox({
    editable: false

});
//物料编码双击事件
$("#code").datagrid({
    onDblClickRow: function (index, row) {
        SetSelectSkuValue();
    }
});
$("#gridView").datagrid({
    onDblClickRow: function (index, row) {
        Edit();
    }
});

$("#ucounty").click(function () {
    $("#tree1").tree({//表格内按钮
        url: '/pop/product/regionsList?parentid=0&attributes=q',//请求路径，id为根节点的id
        onBeforeExpand: function (node) {
            $('#tree1').tree('options').url = '/pop/product/regionsList?parentid=' + node.id + '&attributes=' + node.attributes;
        }
    });
    $('#oDialog2').dialog('open');
});


//查询主表信息
function SearchUnit(gloid) {

    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/product/productListid',
        data: {
            'id': gloid
        },
        success: function (data, textStatus) {
            for (var i = 0; i < data.length; i++) {//遍历主表条件
                $("#channelId").combobox("setValue", data[0].channelId);
                $("#onSale").combobox("setValue", data[0].onSale);
                $("#departmentName").val(data[0].departmentName);
                $("#productTypeId").val(data[0].productTypeId);
                $("#productTypeName").val(data[0].productTypeName);
                $("#productCode").val(data[0].productCode);
                $("#sku").val(data[0].sku);
                $("#skuName").val(data[0].skuName);
            }
        }
    });
}

//查询子表信息
function BingSearchUnit() {
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/product/productDetailList',
        data: {
            'saleid': gloid
        },
        beforeSend: function () {
            ajaxLoading();
        },
        complete: function () {
            ajaxLoadEnd();
        },
        success: function (data, textStatus) {
            $('#gridView').datagrid("loadData", data);
        }
    });
}

//保存
function SaveMasterUnit(messig) {
    var row1 = $('#gridView').datagrid('getRows');

    //获取表格全部行命令
    if (jiaoyan() == false) {
        return;
    }
    //主表保存

    var blf = false;
    endEditing();
    //获取id值
    var idValue = $("#id").val();// document.getElementById("id").value;
    var url = "";


    //校验时间

    //获取url值
    if (gloid == null || idValue == "") {
        url = "/pop/product/addProduct";
    }
    else {
        url = "/pop/product/updateProduct";
    }
    var sku1 = $("#skuName").val();
    var sku2 = $("#sku").val();
    var productTypeId1 = $("#productTypeId").val();
    var productTypeId2 = $("#productTypeName").val();
    var channelid1 = $("#channelId").textbox('getValue');
    var channelid2 = $("#channelId").combobox('getText');
    var productcode1 = $("#productCode").val();
    var onSale = $("#onSale").combobox('getValue');

    var rows = $('#gridView').datagrid('getChanges');
    var provinces = "", citys = "", ids = "", saleIds = "", countys = "", regionIds = "", supplyPrices = "",
        salePrices = "", limitPrices = "",
        priceStartTimes = "", priceEndTimes = "", rowstatus = "";
    for (var i = 0; i < rows.length; i++) {
        provinces += rows[i].province;
        if (i != rows.length) {
            provinces += ",";
        }
        ids += rows[i].id + ",";
        saleIds += gloid + ",";
        citys += rows[i].city + ",";
        countys += rows[i].county + ",";
        regionIds += rows[i].regionid + ",";
        supplyPrices += rows[i].supplyprice + ",";
        salePrices += rows[i].saleprice + ",";
        limitPrices += rows[i].limitprice + ",";
        priceStartTimes += rows[i].pricestarttime + ",";
        priceEndTimes += rows[i].priceendtime + ",";
        rowstatus += rows[i].rowstatus + ",";
    }
    rows.productDetail = $('#gridView').datagrid('getChanges');

    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',

        data: {
            'id': gloid,
            'channelId': channelid1,
            'sku': sku2,
            'skuName': sku1,
            'productTypeId': productTypeId1,
            'productCode': productcode1,
            'channelName': channelid2,
            'productTypeName': productTypeId2,
            'departmentName': $("#departmentName").val(),
            'onSale': onSale,

            'citys': citys,
            'provinces': provinces,
            'ids': ids,
            'saleIds': saleIds,
            'regionIds': regionIds,
            'countys': countys,
            'supplyPrices': supplyPrices,
            'salePrices': salePrices,
            'limitPrices': limitPrices,
            'priceStartTimes': priceStartTimes,
            'priceEndTimes': priceEndTimes,
            'rowstatus': rowstatus

        },
        beforeSend: function () {
            progress();
        },

        success: function (data) {
            $.messager.progress('close');
            if (data > 0) {

                $("#id").val(data);

                gloid = data;
                SearchUnit(data);

                autoAlt("保存成功!");
                BingSearchUnit(data);


            } else {
                autoAlt("保存失败，原因：" + data);
            }
        }
    });


}

//删除主表信息
function go_delete_sku() {
    //获取id值
    var idValue = $("#id").val();// document.getElementById("id").value;
    if (idValue == '') {
        autoAlt("请先保存此次操作在进行删除");
        return;
    }
    var url = "/pop/product/deleteProduct";


    $.messager.confirm('提示', '您确认要删除吗？', function (b) {
        if (b) {
            $.ajax({
                type: 'POST',
                url: url,
                data: {
                    'id': idValue
                },
                success: function (data) {
                    DeletesAuto();
                }
            });
        }
    });

}

//删除主表表时删除全部子表
function DeletesAuto() {
    $.ajax({
        type: 'POST',
        async: false,
        url: '/pop/product/deleteProductDetailAtuo',
        data: {
            saleid: gloid
        },
        success: function (data, textStatus) {
            location.href = "/pop/product/productList";
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

//删除子表信息
function Deletes() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt("请选择要删除的价格区域");
        return;

    } else {
        $.messager.confirm('提示', '您确定要删除这一行吗?', function (b) {
            if (b) {
                endEditing();
                var rows = $('#gridView').datagrid('getChecked');
                for (var i = 0; i < rows.length; i++) {
                    $.ajax({
                        type: 'POST',
                        async: false,
                        url: '/pop/product/deleteProductDetail',
                        data: {
                            id: rows[i].id
                        },
                        success: function (data, textStatus) {
                        },
                        complete: function (XMLHttpRequest, textStatus) {
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });

                }
                //重新查询子表信息
                BingSearchUnit(gloid);
            }
        });
    }
}

//修改子表
function Edit() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt("请选择要修改的价格区域");
        return;

    } else {

        var rows = $('#gridView').datagrid('getSelected');

        $("#uid").val(rows.id);
        $("#ucounty").val(rows.county);
        $("#uregionid").val(rows.regionid);
        $("#usupplyprice").numberbox('setValue', rows.supplyprice);
        $("#usaleprice").numberbox('setValue', rows.saleprice);
        $("#ulimitprice").numberbox('setValue', rows.limitprice);
        $("#uprovince").val(rows.province);
        $("#ucity").val(rows.city);

        $('#upricestarttime').datetimebox('setValue', rows.pricestarttime);
        $('#upriceendtime').datetimebox('setValue', rows.priceendtime);
        $('#oDialog4').dialog('open');
        //BingSearchUnit(gloid);
    }
}

/**
 * 物料编码弹出模块
 * @constructor
 */
function Open() {
    $('#code').datagrid('loadData', {total: 0, rows: []});
    $('#oDialog').dialog('open');
}
//物料条件
function product() {
    var sku = $("#sku1").val() == '' ? '%' : $("#sku1").val() + '%';
    var productName = $("#productName").val() == '' ? '%' : $("#productName").val() + '%';
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/product/productsList',
        data: {
            sku: sku,
            productName: productName
        },
        beforeSend: function () {
            progress();
        },
        success: function (data, textStatus) {
            $.messager.progress('close');
            $('#code').datagrid("loadData", data);
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
//set值
//function SetCodeValuep() {
//    var row = $('#code').datagrid('getSelected');
//    $("#skuName").val(row.productName);
//    $("#productTypeId").val(row.productTypeId);
//    $("#sku").val(row.sku);
//    $('#oDialog').dialog('close');
//    prosku(row.sku);
//    typesku(row.productTypeId1);
//    $('#code').datagrid('loadData', {total: 0, rows: []});
//    $("#sku1").val("");
//    $("#productName").val("");
//}
//查询产品组
function prosku(sku) {
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/product/departmentcodeskuList',
        data: {
            sku: sku
        },
        success: function (data, textStatus) {
            if (data == null || data == "") {
                $("#departmentName").val("");
            } else {
                $("#departmentName").val(data[0].departmentName);
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function SetSelectSkuValue() {
    var row = $('#code').datagrid('getSelected');

    $("#skuName").val(row.productName);
    $("#productTypeId").val(row.productTypeId1);
    $("#sku").val(row.sku);

    $('#oDialog').dialog('close');

    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/product/getSelectSkuValue',
        data: {
            sku: row.sku,
            productTypeId: row.productTypeId1
        },
        success: function (data, textStatus) {
            if (data == null || data == "") {
                $("#departmentName").val("");
                $("#productTypeName").val("");
            } else {
                $("#departmentName").val(data[0].departmentName);
                $("#productTypeName").val(data[0].typeName);
            }

        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

    $('#code').datagrid('loadData', {total: 0, rows: []});
    $("#sku1").val("");
    $("#productName").val("");
}
//根据id查询品类品类名称
function typesku(productTypeId) {
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/product/producttypesListsku1',
        data: {
            id: productTypeId
        },
        success: function (data, textStatus) {
            $("#productTypeName").val(data[0].typeName);
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

/**
 *表格模块
 */
function editGrdViewBcode1(index) {
    $('#gridView').datagrid('selectRow', index);// 关键在这里
    $('#gridView').datagrid('beginEdit', index);
    nindex = index;
    var row = $('#gridView').datagrid('getSelected');
    //tree加载
    $("#tree1").tree({//表格内按钮
        url: '/pop/product/regionsList?parentid=0&attributes=q',//请求路径，id为根节点的id
        onBeforeExpand: function (node) {
            $('#tree1').tree('options').url = '/pop/product/regionsList?parentid=' + node.id + '&attributes=' + node.attributes;
        }
    });
    $('#oDialog2').dialog('open');
}
//增加按钮修改成弹窗形式
function editGrdViewBcode11() {
    if (gloid == "") {
        autoAlt("请先保存信息在增加区域！");
        return;
    }
    $("#tree").tree({//表格外按钮
        url: '/pop/product/regionsList?parentid=0&attributes=q',//请求路径，id为根节点的id
        onBeforeExpand: function (node) {
            $('#tree').tree('options').url = '/pop/product/regionsList?parentid=' + node.id + '&attributes=' + node.attributes;
        }
    });
    $('#oDialog1').dialog('open');
}
//复制按钮
function Copy() {
    var rows = $("#gridView").datagrid("getSelected");
    if (rows == "" || rows == undefined) {
        autoAlt("请先选择区域再复制！");
        return;
    }

    $.ajax({
        type: 'POST',
        async: false,
        url: '/pop/product/addProductDetail',
        data: {
            saleId: gloid,
            regionId: rows.regionid,
            supplyPrice: rows.supplyprice,
            salePrice: rows.saleprice,
            limitPrice: rows.limitprice,
            priceStartTime: '',
            priceEndTime: '',
            province: rows.province,
            city: rows.city,
            county: rows.county
        },
        success: function (data, textStatus) {
            if (data == 1) {
                autoAlt("复制成功成功！");
                BingSearchUnit();
            }

        }

    });
}

//查询区域
function SearchCounty() {
    var startDateTime = $('#start_time').datetimebox('getValue');		// get datetimebox value
    var endDateTime = $('#end_time').datetimebox('getValue');		// get datetimebox value
    $.ajax({
        type: 'POST',
        url: '/pop/product/productDetailcountList',
        data: {
            'startDateTime': startDateTime,
            'endDateTime': endDateTime,
            'saleid': gloid,
            'county': $("#zcounty").val() == '' ? '%' : $("#zcounty").val() + '%'
        },
        beforeSend: function () {
            ajaxLoading();
        },
        complete: function () {
            ajaxLoadEnd();
        },
        success: function (data) {
            $('#gridView').datagrid("loadData", data);


        }
    });
}
function SetCodeValue1() {
    var id1 = $("#tree").tree('getSelected');
    if (id1 == null || id1.id == undefined || id1.id == null) {
        autoAlt("请正确选择区域！");
        return;
    }
    $('#oDialog3').dialog('open');
    $('#oDialog1').dialog('close');

}

function SetCodeValue2() {
    var id = $("#tree1").tree('getSelected').id;
    var text = $("#tree1").tree('getSelected').text;
    $("#uregionid").val(id);
    $("#ucounty").val(text);
    $('#oDialog2').dialog('close')
}

function SetCodeValue3() {

    var id = $("#tree").tree('getSelected').id;
    var pricestarttime = $('#pricestarttime').datetimebox('getValue');
    var priceendtime = $('#priceendtime').datetimebox('getValue');
    var supplyprice = $("#supplyprice").numberbox('getValue');
    var saleprice = $("#saleprice").numberbox('getValue');
    var limitprice = $("#limitprice").numberbox('getValue');

    if (isNumber(supplyprice, "供货价格不能为空！") == false) {
        return false;
    }
    if (Number(supplyprice) > 99999999.99) {
        autoAlt("供货价格不能最大值不能超过'99999999.99'");
        return false;
    }
    if (isNumber(saleprice, "销售价格不能为空！") == false) {
        return false;
    }
    if (Number(saleprice) > 99999999.99) {
        autoAlt("销售价格不能最大值不能超过'99999999.99'");
        return false;
    }
    if (isNumber(limitprice, "限制价格不能为空！") == false) {
        return false;
    }
    if (Number(limitprice) > 99999999.99) {
        autoAlt("限制价格不能最大值不能超过'99999999.99'");
        return false;
    }
    if (pricestarttime.length == 0) {
        autoAlt("价格适用开始时间不能为空！");
        return;
    }
    if (priceendtime.length == 0) {
        autoAlt("价格适用结束时间不能为空！");
        return;
    }
    if (CompareDate(pricestarttime, priceendtime)) {
        autoAlt("价格适用开始时间不能大于价格适用结束时间！");
        return;
    }
    Timejiao(id, pricestarttime, priceendtime, 0);
    if (timefalg == 1) {
        autoAlt("相同区域价格适用时间重复！");
        return;
    }

    $.ajax({
        type: 'POST',
        async: false,
        url: '/pop/product/addProductDetail',
        data: {
            saleId: gloid,
            regionId: id,
            supplyPrice: supplyprice,
            salePrice: saleprice,
            limitPrice: limitprice,
            priceStartTime: pricestarttime,
            priceEndTime: priceendtime
        },
        success: function (data, textStatus) {
            if (data == 1) {
                autoAlt("添加成功！");
                $('#oDialog3').dialog('close');
                $('#pricestarttime').datetimebox('setValue', "");
                $('#priceendtime').datetimebox('setValue', "");
                $("#supplyprice").numberbox('setValue', "");
                $("#saleprice").numberbox('setValue', "");
                $("#limitprice").numberbox('setValue', "");
                BingSearchUnit();
            } else {
                autoAlt("添加失败！")
            }

        }

    });
}

function SetCodeValue4() {
    var regionid = $("#uregionid").val();
    var uid = $("#uid").val();
    var pricestarttime = $('#upricestarttime').datetimebox('getValue');
    var priceendtime = $('#upriceendtime').datetimebox('getValue');
    var supplyprice = $("#usupplyprice").numberbox('getValue');
    var saleprice = $("#usaleprice").numberbox('getValue');
    var limitprice = $("#ulimitprice").numberbox('getValue');
    var province = $("#uprovince").val();
    var city = $("#ucity").val();
    var county = $("#ucounty").val();
    var regiontype = true;
    $.ajax({
        type: 'POST',
        async: false,
        url: '/pop/product/regionsListId',
        data: {
            id: regionid
        },
        success: function (data, textStatus) {
            if (data[0].regiontype != 3) {
                regiontype = false;
            }
        }

    });
    if (regiontype == false) {
        autoAlt("请选择正确的县级单位！");
        return false;
    }
    if (isNumber(supplyprice, "供货价格不能为空！") == false) {
        return false;
    }
    if (Number(supplyprice) > 99999999.99) {
        autoAlt("供货价格最大值不符合规范");
        return false;
    }
    if (isNumber(saleprice, "销售价格不能为空！") == false) {
        return false;
    }
    if (Number(saleprice) > 99999999.99) {
        autoAlt("销售价格最大值不符合规范");
        return false;
    }
    if (isNumber(limitprice, "限制价格不能为空！") == false) {
        return false;
    }
    if (Number(limitprice) > 99999999.99) {
        autoAlt("限制价格最大值不符合规范");
        return false;
    }
    if (pricestarttime.length == 0) {
        autoAlt("价格适用开始时间不能为空！");
        return;
    }
    if (priceendtime.length == 0) {
        autoAlt("价格适用结束时间不能为空！");
        return;
    }
    if (CompareDate(pricestarttime, priceendtime)) {
        autoAlt("价格适用开始时间不能大于价格适用结束时间！");
        return;
    }
    Timejiao(regionid, pricestarttime, priceendtime, uid);
    if (timefalg == 1) {
        autoAlt("相同区域价格重复！");
        return;
    }

    $.ajax({
        type: 'POST',
        async: false,
        url: '/pop/product/updateProductDetail',
        data: {
            id: uid,
            priceStartTime: pricestarttime,
            priceEndTime: priceendtime,
            supplyPrice: supplyprice,
            salePrice: saleprice,
            limitPrice: limitprice,
            saleId: gloid,
            regionId: regionid,
            province: province,
            city: city,
            county: county
        },
        success: function (data, textStatus) {
            $('#oDialog4').dialog('close');
            BingSearchUnit();
        }

    });
}
function close3() {
    $('#oDialog3').dialog('close');
    $('#pricestarttime').datetimebox('setValue', "");
    $('#priceendtime').datetimebox('setValue', "");
    $("#supplyprice").numberbox('setValue', "");
    $("#saleprice").numberbox('setValue', "");
    $("#limitprice").numberbox('setValue', "");
}

/**
 *
 */

function jiaoyan() {

    endEditing();

    //判断渠道是否为空
    var v2 = $("#channelId").combobox('getText');

    var len2 = $.trim(v2).length;

    if (len2 <= 0) {
        autoAlt("渠道不能为空！");
        return false;
    }

    //判断物料是否为空

    var v3 = $("#sku").val();
    var len3 = $.trim(v3).length;

    if (len3 <= 0) {
        autoAlt("物料编码不能为空！");
        return false;
    }
    var proid = true;

    //判断渠道相同商品编码是否相同
    $.ajax({
        type: 'POST',
        async: false,
        url: '/pop/product/productCodeList',
        data: {
            channelid: $("#channelId").textbox('getValue'),
            productcode: $("#productCode").val(),
            id: (gloid == '' ? '0' : gloid)
        },
        success: function (data, textStatus) {

            if (data > 0) {
                proid = false;
            }

        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

}
function Timejiao(regionId, start, end, id) {
    $.ajax({
        type: 'POST',
        async: false,
        url: '/pop/product/pricTime',
        data: {
            regionId: regionId,
            saleid: gloid,
            starttime: start,
            endtime: end,
            id: id

        },
        success: function (data, textStatus) {
            timefalg = data;
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
function clearNoNum(obj) {
    if (obj.value.length > 11) obj.value = obj.value.slice(0, 11)
}
//验证数字
function isNumber(value, text) {
    //验证是否为数字
    var patrn = /^(-)?\d+(\.\d+)?$/;
    if (patrn.exec(value) == null || value == "") {
        autoAlt(text);
        return false;
    } else {

        return true
    }
}
//返回
function fan() {
    location.href = "/pop/product/productList";
}
function CompareDate(d1, d2) {
    return ((new Date(d1.replace(/-/g, "\/"))) > (new Date(d2.replace(/-/g, "\/"))));
}
function todate(str_time) {
//格式2014-03-11 12:00:00
    var dateArr = str_time.substring(0, 10).split('-');
    var timeArr = str_time.substring(11, 19).split(':');

    return new Date(parseInt(dateArr[0]), parseInt(dateArr[1]) - 1, parseInt(dateArr[2]), parseInt(timeArr[1]), parseInt(timeArr[2]));
}

//键盘触发
function onEnter() {
    if (window.event.keyCode == 13) {
        //提交按钮，登录
        SearchCounty();
    }
}
//重置查询条件
function SearchClear() {
    $('#start_time').datetimebox('setValue', '');
    $('#end_time').datetimebox('setValue', '');
    $('#zcounty').val("");
}