/**
 * 苏宁团购
 * 肖剑
 * 2018/5/14.
 */


var gloid;
/***
 * 自动加载
 */
var myDate = new Date();
var near = myDate.getFullYear(); //获取完整的年份(4位,1970-????)

$('#cpolicyYear').val(near);
$('#policyYear').val(near);


$('#gridView').datagrid({
    striped : true, // 隔行变色
    rownumbers : true,
    fit : true,
    pagination : true,
    singleSelect : true,
    fitColunms : true,
    idField : 'id',
    remoteSort : false,
    showFooter : true,

    columns : [ [
        {
            title : '店铺来源',
            width : 158,
            field : 'source',
            align : 'center',
            sortable: false
        },
        {
            title : '团购名称',
            width : 151,
            field : 'groupName',
            align : 'center',
            sortable: false
        },
        {
            title : '团购商品',
            width : 151,
            field : 'sku',
            align : 'center',
            sortable: false
        },
        {
            title : '订金开始时间',
            width : 162,
            field : 'depositStartTime',
            align : 'center',
            sortable: false
        },
        {
            title : '订金结束时间',
            width : 162,
            field : 'depositEndTime',
            align : 'center',
            sortable: false
        },
        {
            title : '尾款开始时间',
            width : 162,
            field : 'balanceStartTime',
            align : 'center',
            sortable: false
        },
        {
            title : '尾款结束时间',
            width : 162,
            field : 'balanceEndTime',
            align : 'center',
            sortable: false
        },
        {
            title : '订金',
            width : 142,
            field : 'depositAmount',
            align : 'center',
            sortable: false
        }, {
            title : '尾款',
            width : 142,
            field : 'balanceAmount',
            align : 'center',
            sortable: false
        },
        {
            title : '发货时机',
            width : 120,
            field : 'shippingOpporunity',
            align : 'center',
            sortable: false,
            formatter : function(value, row, index) {
                if (row.shippingOpporunity == 1) {
                    return "尾款发货";
                } else {
                    return "订金发货";
                }
            }
        },
        {
            title : '是否开启',
            width : 120,
            field : 'status',
            align : 'center',
            sortable: false,
            formatter : function(value, row, index) {
                if (row.status == true) {
                    return "开启";
                } else {
                    return "关闭";
                }
            }
        },
        {
            title : 'id',
            width : 120,
            field : 'id',
            align : 'center',
            sortable: false,
            hidden : true
        },

        {
            title : 'siteId',
            width : 120,
            field : 'siteId',
            align : 'center',
            sortable: false,
            hidden : true
        },
        {
            title : 'productSpecs',
            width : 120,
            field : 'productSpecs',
            align : 'center',
            sortable: false,
            hidden : true
        },

    ] ],
    toolbar : '#datagridToolbar_orderForecastGoal',
    onClickRow : function(rowIndex, row) {
        gloid = row.id;
    }
});

//表格and分页js加载
$(function() {
    $('#gridView').datagrid('getPager').pagination({
        total : 0,
        pageSize : 10,
        pageList : [ 50, 100, 200 ],
        showRefresh : true,
        displayMsg : '显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage : function(pageNo, pageSize) {

            var start = (pageNo - 1) * pageSize; //页码分页自增
            var source = $("#csource").combobox('getValue');
            var groupName = $("#cgroupName").val();
            var sku = $("#csku").val();
            var depositStartTime1 = $("#cdepositStartTime").datetimebox('getValue');
            var depositStartTime = Date.parse(new Date(depositStartTime1));
            depositStartTime = depositStartTime / 1000;
            var depositEndTime1 = $("#cdepositEndTime").datetimebox('getValue');
            var depositEndTime = Date.parse(new Date(depositEndTime1));
            depositEndTime = depositEndTime / 1000;
            var balanceStartTime1 = $("#cbalanceStartTime").datetimebox('getValue');
            var balanceStartTime = Date.parse(new Date(balanceStartTime1));
            balanceStartTime = balanceStartTime / 1000;
            var balanceEndTime1 = $("#cbalanceEndTime").datetimebox('getValue');
            var balanceEndTime = Date.parse(new Date(balanceEndTime1));
            balanceEndTime = balanceEndTime / 1000;
            var shippingOpporunity = $("#cshippingOpporunity").combobox('getValue');
            var status = $("#cstatus").combobox('getValue');

            if (isNaN(depositStartTime)) {
                depositStartTime = null;
            }
            if (isNaN(depositEndTime)) {
                depositEndTime = null;
            }
            if (isNaN(balanceStartTime)) {
                balanceStartTime = null;
            }
            if (isNaN(balanceEndTime)) {
                balanceEndTime = null;
            }

            var options = $('#gridView').datagrid('getPager').data("pagination").options;
            $.ajax({
                type : 'POST',
                async : true,
                url : '/eop/SuNingGroups/commission_productListF',
                data : {
                    page : options.pageNumber,
                    rows : options.pageSize,
                    source : source,
                    groupName : groupName,
                    sku : sku,
                    depositStartTime : depositStartTime,
                    depositEndTime : depositEndTime,
                    balanceStartTime : balanceStartTime,
                    balanceEndTime : balanceEndTime,
                    shippingOpporunity : shippingOpporunity,
                    status : status,
                },
                success : function(data, textStatus) {
                    $('#gridView').datagrid('loadData', data);
                    //页码跟随分页进行-loadData后面执行
                    var rowNumbers = $('.datagrid-cell-rownumber');
                    $(rowNumbers).each(function(index) {
                        var row = parseInt($(rowNumbers[index]).html()) + parseInt(start);
                        $(rowNumbers[index]).html("");
                        $(rowNumbers[index]).html(row);
                    });

                }
            });

        }
    });
});


//双击事件
$("#gridView").datagrid({
    onDblClickRow : function(index, row) {
        list();
    }
});
//双击物料编码
/*$("#code").datagrid({
    onDblClickRow : function(index, row) {
        SetCodeValuep();
    }
});*/

$("#cpolicyYear").click(function() {
    $('#oDialog2').dialog('open');

});

$("#cpolicyYear").datepicker({
    language : "zh-CN",
    todayHighlight : true,
    format : 'yyyy',
    autoClose : true,
    startView : 'years',
    maxViewMode : 'years',
    minViewMode : 'years',
    orientation : 'bottom',
    container : '#datetimepicker',
    pickerPosition : 'top-right'
}).on('changeDate', function(ev) {
    $('#oDialog2').dialog('close');
}).on('hide', function(e) {

    $("#cpolicyYear").datepicker('show');
});

$("#policyYear").click(function() {
    $('#oDialog3').dialog('open');

});

$("#policyYear").datepicker({
    language : "zh-CN",
    todayHighlight : true,
    format : 'yyyy',
    autoClose : true,
    startView : 'years',
    maxViewMode : 'years',
    minViewMode : 'years',
    orientation : 'bottom',
    container : '#datetimepicker1',
    pickerPosition : 'top-right'
}).on('changeDate', function(ev) {
    $('#oDialog3').dialog('close');
}).on('hide', function(e) {
    console.log(e + "1");
    $("#policyYear").datepicker('show');
});
$("#oDialog4").css('display', 'none');
$("#oDialog").css('display', 'none');


//物料条件
function product() {
}

//set值
function SetCodeValuep() {
}

//查询品牌
function prosku(sku) {

}

//根据id查询品类品类名称
function typesku(productTypeId) {
    alert("...")
}

//修改
function list() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt("请选择要修改的单据");
        return;
    } else {
        $('#oDialog4').panel({
            title : "修改"
        });
        $("#id").val(rows1[0].id);
        $('#source').combobox('setValue', rows1[0].source);
        $("#siteId").val(rows1[0].siteId);
        $("#groupName").textbox('setValue', rows1[0].groupName);
        $("#sku").textbox('setValue', rows1[0].sku);
        $("#depositAmount").textbox('setValue', rows1[0].depositAmount);
        $("#balanceAmount").textbox('setValue', rows1[0].balanceAmount);
        $('#depositStartTime').datetimebox('setValue', rows1[0].depositStartTime);
        $('#depositEndTime').datetimebox('setValue', rows1[0].depositEndTime);
        $('#balanceStartTime').datetimebox('setValue', rows1[0].balanceStartTime);
        $('#balanceEndTime').datetimebox('setValue', rows1[0].balanceEndTime);
        $('#status').combobox('setValue', rows1[0].status);
        $('#shippingOpporunity').combobox('setValue', rows1[0].shippingOpporunity);
        $("#productSpec").textbox('setValue', rows1[0].productSpecs);
        for (var i = 0; i < count; i++) {
            $("#del" + i).click();
        }
        count = 0;
        var packJson = $("#productSpec").val();
        var groupTable = $('#tb');
        groupTable.empty("");
        $.each(eval("(" + packJson + ")"), function(i, n) {
            groupTable.append('<tr><td>&nbsp;&nbsp;&nbsp;套装商品Sku：<font color=\"red\">*</font>&nbsp;'+
                "<input id=\"productSpecIds" + i + "\" type=\"text\" name=\"productSpecIds" + i + "\" value= "+    n.sku + " /> "
                +"订金价格：<font color=\"red\">*</font>&nbsp;" +
                "<input id=\"depositPrices" + i + "\" type=\"text\" name=\"depositPrices" + i + "\" style=" + "\"width: 70px" +
                "\" maxlength= \"6\" onkeyup=\"keyUp(this)\" onkeypress=\"keyPress(this)\" onblur=\"onBlur(this)\"" +
                "value= "+    n.depositAmount + " />" +
                "尾款价格：<font color=\"red\">*</font>&nbsp;" +
                "<input id=\"balancePrices" + i + "\" type=\"text\" name=\"balancePrices" + i + "\" style=" + "\"width: 70px" +
                "\" maxlength= \"6\" onkeyup=\"keyUp(this)\" onkeypress=\"keyPress(this)\" onblur=\"onBlur(this)\"" +
                "value= "+    n.tailAmount + " /> " +
                "<input id=\"del" + i + "\" type=\"button\" value=\"删除\" onclick=\'deleteitem(this);\'><br></td></td>");
            count = i + 1;
        });



        $('#oDialog4').dialog('open');


    }
}





//新增
function Add() {
    $("#id").val("");
    $("#productSpec").textbox('setValue', '');
//    $("#source").combobox('setValue', '0');
    $("#groupName").textbox('setValue', '');
    $("#sku").textbox('setValue', '');
    $("#depositAmount").textbox('setValue', '');
    $("#balanceAmount").textbox('setValue', '');
    $("#depositStartTime").datetimebox('setValue', '2018-01-01 00:00:00');
    $("#depositEndTime").datetimebox('setValue', '2018-01-01 00:00:00');
    $("#balanceStartTime").datetimebox('setValue', '2018-01-01 00:00:00');
    $("#balanceEndTime").datetimebox('setValue', '2018-01-01 00:00:00');
    $("#shippingOpporunity").combobox('setValue','');
    $("#status").combobox('setValue','');
    $("#productSpec").textbox('setValue', '');
    $('#oDialog4').dialog('open');
    $("#update").hide();
    for (var i = 0; i < count; i++) {
        $("#del" + i).click();
    }
    count = 0;
    jQuery('#configIds').combobox('clear');
    $('#oDialog4').panel({
        title : "新增"
    });


}

//删除
function Delete() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt("请选择要删除的单据");
        return;
    } else {
        $.messager.confirm('提示', '您确定要删除这一行吗?', function(b) {
            if (b) {
                $.ajax({
                    type : 'POST',
                    async : false,
                    url : '/eop/SuNingGroups/deletecommission_product',
                    data : {
                        id : rows1[0].id
                    },
                    success : function(data, textStatus) {
                        if (data == 1) {
                            autoAlt("删除成功！");
                            $('#oDialog4').dialog('close');
                            SearchUnit();
                        } else {
                            autoAlt("操作失败！");
                        }

                    }
                });

            }
        });
    }
}

/*新增*/
function SetCodeValue4() {

    var flag = getsub();
    if (flag == false){
        return false;
    }
    var productSpecs = $("#productSpec").val();
    var source = $("#source").combobox('getValue');
    var groupName = $("#groupName").val();
    var sku = $("#sku").val();
    var depositAmount = $("#depositAmount").val();
    var balanceAmount = $("#balanceAmount").val();
    var depositStartTime1 = $("#depositStartTime").datetimebox('getValue');
    var depositStartTime = Date.parse(new Date(depositStartTime1));
    depositStartTime = depositStartTime / 1000;
    var depositEndTime1 = $("#depositEndTime").datetimebox('getValue');
    var depositEndTime = Date.parse(new Date(depositEndTime1));
    depositEndTime = depositEndTime / 1000;
    var balanceStartTime1 = $("#balanceStartTime").datetimebox('getValue');
    var balanceStartTime = Date.parse(new Date(balanceStartTime1));
    balanceStartTime = balanceStartTime / 1000;
    var balanceEndTime1 = $("#balanceEndTime").datetimebox('getValue');
    var balanceEndTime = Date.parse(new Date(balanceEndTime1));
    balanceEndTime = balanceEndTime / 1000;
    var shippingOpporunity = $("#shippingOpporunity").combobox('getValue');
    var status = $("#status").combobox('getValue');
    var id = $("#id").val();
    var url = '/eop/SuNingGroups/updatecommission_product';
    if (source == "") {
        autoAlt("来源店铺不能为空");
        return false;
    }
    if (groupName == "") {
        autoAlt("团购名称不能为空");
        return false;
    }
    if (groupName.length >255) {
        autoAlt("团购名称超过最大值");
        return false;
    }
//    if (sku == "") {
//        autoAlt("Sku名称不能为空");
//        return false;
//    }
    if (depositAmount == "") {
        autoAlt("订金金额不能为空");
        return false;
    }
    if (balanceAmount == "") {
        autoAlt("尾款金额不能为空");
        return false;
    }
    if (isNaN(depositStartTime)) {
        autoAlt("订金开始时间不能为空");
        return false;
    }
    if (isNaN(depositEndTime)) {
        autoAlt("订金结束时间不能为空");
        return false;
    }
    if (isNaN(balanceStartTime)) {
        autoAlt("尾款开始时间不能为空");
        return false;
    }
    if (isNaN(balanceEndTime)) {
        autoAlt("尾款结束时间不能为空");
        return false;
    }
    if (shippingOpporunity == "") {
        autoAlt("发货时机不能为空");
        return false;
    }
    if (status == "") {
        autoAlt("是否启用不能为空");
        return false;
    }
    if (id == "") {
        url = '/eop/SuNingGroups/addcommission_product';
    }
    if(sku!==""){
        if (jiaoyan(sku) == 0){
            autoAlt("sku不正确");
            return false;
        }
    }else{
        if(productSpec==""){
            autoAlt("操作失败！你有商品输入不完全");
            return false;
        }
    }
    if (depositStartTime > depositEndTime ) {
        autoAlt("订金开始时间不能大于结束时间");
        return false;
    }
    if (balanceStartTime > balanceEndTime ) {
        autoAlt("尾款开始时间不能大于结束时间");
        return false;
    }
    if (proof(depositAmount)) {
        return false;
    }
    if (proof2(balanceAmount)) {
        return false;
    }

    $.ajax({
        type : 'POST',
        async : false,
        url : url,
        datatype : 'JSON',
        data : {
            id : id,
            source : source,
            groupName : groupName,
            sku : sku,
            depositAmount : depositAmount,
            balanceAmount : balanceAmount,
            depositStartTime : depositStartTime,
            depositEndTime : depositEndTime,
            balanceStartTime : balanceStartTime,
            balanceEndTime : balanceEndTime,
            shippingOpporunity : shippingOpporunity,
            status : status,
            productSpecs : productSpecs
        },

        success : function(data, textStatus) {
            if (data == 0) {
                autoAlt("操作失败！");
            }
            if (data == 1) {
                autoAlt("操作成功！");
            }
            if (data.substring(2, 4) == "成功") {
                autoAlt(data);
                $('#oDialog4').dialog('close');
                SearchUnit();
            } else {
                autoAlt(data);
            }
        },
        error : function(data) {
            alert("error")
        }
    })
    ;
}

function proof(num){
    var special = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
    if(!special.test(num) ){
        autoAlt("输入的订金数值不合法,最多为2位小数");
        return true;
    }
}
function proof2(num){
    var special = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
    if(!special.test(num) ){
        autoAlt("输入的尾款数值不合法,最多为2位小数");
        return true;
    }
}

function jiaoyan(sku) {
    var result = 0;
    $.ajax({
        type: 'POST',
        async: false,
        url: '/eop/SuNingGroups/jiaoyan',
        data: {
            sku: sku,
        },
        success: function (data, textStatus) {
            if (data == 0) {
                result = 0;
            }else {
                result = 1
            }
            return result;
        }
    });
    return result;
}


function SearchClear() {
    $('#csource').combobox('setValue', "0");
    $('#cgroupName').textbox('setValue', '');
    $('#csku').textbox('setValue', '');
    $('#cdepositStartTime').datetimebox('setValue', "");
    $('#cdepositEndTime').datetimebox('setValue', "");
    $('#cbalanceStartTime').datetimebox('setValue', "");
    $('#cbalanceEndTime').datetimebox('setValue', "");
    $('#cstatus').combobox('setValue', "");
    $('#cshippingOpporunity').combobox('setValue', "");
}

/*添加sku*/
var count = 0;

function additem(id) {
    var row,
        cell,
        str;
    row = document.getElementById(id).insertRow();
    if (row != null) {

        cell = row.insertCell();
        cell.innerHTML = "&nbsp;&nbsp;&nbsp;" +
            "套装商品Sku：<font color=\"red\">*</font> <input id=\"productSpecIds" + count + "\" type=\"text\" name=\"productSpecIds" + count + "\" value= \"\" /> " +
            "订金价格：<font color=\"red\">*</font> <input id=\"depositPrices" + count + "\" type=\"text\" name=\"depositPrices" + count + "\" style=" + "\"width: 70px" + "\" maxlength= \"6\" onkeyup=\"keyUp(this)\" onkeypress=\"keyPress(this)\" onblur=\"onBlur(this)\">" +
            "尾款价格：<font color=\"red\">*</font> <input id=\"balancePrices" + count + "\" type=\"text\" name=\"balancePrices" + count + "\" style=" + "\"width: 70px" + "\" maxlength= \"6\" onkeyup=\"keyUp(this)\" onkeypress=\"keyPress(this)\" onblur=\"onBlur(this)\">" +
            "&nbsp;<input id=\"del" + count + "\" type=\"button\" value=\"删除\" onclick=\'deleteitem(this);\'>";
        count++;
    }
}

/*删除一个sku*/
function deleteitem(obj) {
    var curRow = obj.parentNode.parentNode;
    tb.deleteRow(curRow.rowIndex);
}

/*获得所有sku*/
function getsub() {
    var productSpec = "";
    var keys = true;
    var s1 = false;
    var s2 = false;
    for (var i = 0; i < count; i++) {
        if($("#productSpecIds" + i).val() != null){

            s1 = proof3(document.getElementsByName("depositPrices" + i)[0].value);
            s2 = proof4(document.getElementsByName("balancePrices" + i)[0].value);
            if (s1 == true) {
                autoAlt("输入套装商品的订金数值不合法,最多为2位小数");
                keys = false;
                return keys;
            }
            if (s2 == true) {
                autoAlt("输入套装商品的尾款数值不合法,最多为2位小数");
                keys = false;
                return keys;
            }
            productSpec += document.getElementsByName("productSpecIds" + i)[0].value + "--" + document.getElementsByName("depositPrices" + i)[0].value + "--" + document.getElementsByName("balancePrices" + i)[0].value + ";";
        }
    }
    document.getElementById("productSpec").value = productSpec;
    return keys;
}

function proof3(num){
    var special = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
    if(!special.test(num) ){
        return true;
    }
}
function proof4(num){
    var special = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
    if(!special.test(num) ){
        return true;
    }
}

/*下3 用来验证输入金额*/
function keyPress(ob) {
    if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/))
        ob.value = ob.t_value;
    else
        ob.t_value = ob.value;
    if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
        ob.o_value = ob.value;
}

function keyUp(ob) {
    if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/))
        ob.value = ob.t_value; else
        ob.t_value = ob.value;
    if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
        ob.o_value = ob.value;
}

function onBlur(ob) {
    if (!ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))
        ob.value = ob.o_value;
    else {
        if (ob.value.match(/^\.\d+$/))
            ob.value = 0 + ob.value;
        if (ob.value.match(/^\.$/))
            ob.value = 0;
        ob.o_value = ob.value
    }
    ;
}

//主表查询
function SearchUnit() {
    var source = $("#csource").combobox('getValue');
    var groupName = $("#cgroupName").val();
    var sku = $("#csku").val();
    var depositStartTime1 = $('#cdepositStartTime').datetimebox('getValue');
    var depositStartTime = Date.parse(new Date(depositStartTime1));
    depositStartTime = depositStartTime / 1000;
    var depositEndTime1 = $('#cdepositEndTime').datetimebox('getValue');
    var depositEndTime = Date.parse(new Date(depositEndTime1));
    depositEndTime = depositEndTime / 1000;
    var balanceStartTime1 = $('#cbalanceStartTime').datetimebox('getValue');
    var balanceStartTime = Date.parse(new Date(balanceStartTime1));
    balanceStartTime = balanceStartTime / 1000;
    var balanceEndTime1 = $('#cbalanceEndTime').datetimebox('getValue');
    var balanceEndTime = Date.parse(new Date(balanceEndTime1));
    balanceEndTime = balanceEndTime / 1000;
    var shippingOpporunity = $('#cshippingOpporunity').combobox('getValue');
    var status = $("#cstatus").combobox('getValue');

    if (isNaN(depositStartTime)) {
        depositStartTime = null;
    }
    if (isNaN(depositEndTime)) {
        depositEndTime = null;
    }
    if (isNaN(balanceStartTime)) {
        balanceStartTime = null;
    }
    if (isNaN(balanceEndTime)) {
        balanceEndTime = null;
    }
    if (!isNaN(depositStartTime) && !isNaN(depositEndTime)){
        if (depositStartTime > depositEndTime) {
            autoAlt("订金开始时间不能大于结束时间");
            return false;
        }
    }
    if (!isNaN(balanceStartTime) && !isNaN(balanceEndTime)){
        if (balanceStartTime > balanceEndTime) {
            autoAlt("尾款开始时间不能大于结束时间");
            return false;
        }
    }
    var options = $('#gridView').datagrid('getPager').data("pagination").options;
    options.pageNumber = 1;
    $.ajax({
        type : 'POST',
        async : true,
        datatype : 'JSON',
        url : '/eop/SuNingGroups/commission_productListF',
        data : {
            page : options.pageNumber,
            rows : options.pageSize,
            source : source,
            groupName : groupName,
            sku : sku,
            depositStartTime : depositStartTime,
            depositEndTime : depositEndTime,
            balanceStartTime : balanceStartTime,
            balanceEndTime : balanceEndTime,
            shippingOpporunity : shippingOpporunity,
            status : status,
        },
        success : function(data, textStatus) {
            $('#gridView').datagrid('getPager').pagination('refresh');
            $('#gridView').datagrid('loadData', data);
            if (data.rows == "") {
                $(".pagination-num").val("0");
            } else {
                $(".pagination-num").val("1");
            }
        },
        error : function(e) {
            $.messager.alert(e);
        }
    });

}