/**
 * eop 商品关系配置
 * hanhaoyang
 * Created by PC011 on 2018/7/12.
 */

document.write("<script src=\"/static/js/eop/md5.js\" type=\"text/javascript\"></script>");
var gloid;
/***
 * 自动加载
 */
var myDate = new Date();
var near = myDate.getFullYear(); //获取完整的年份(4位,1970-????)

$('#cpolicyYear').val(near);
$('#policyYear').val(near);


$('#gridView').datagrid({
    striped: true, // 隔行变色
    rownumbers: true,
    fit: true,
    pagination: true,
    singleSelect: true,
    fitColunms: true,
    idField: 'id',
    remoteSort: false,
    showFooter: true,

    columns: [[

        
    	{
            title: '套装设置名称',
            width: 168,
            field: 'settingName',
            align: 'center'
            , sortable: false
        },
        {
            title: '套装Sku',
            width: 151,
            field: 'externalSkus',
            align: 'center'
            , sortable: false
        },
        {
            title: '套装来源',
            width: 158,
            field: 'configIds',
            align: 'center'
            , sortable: false
        },
        {
            title: '套装开始时间',
            width: 162,
            field: 'startTime',
            align: 'center'
            , sortable: false
        },
        {
            title: '套装结束时间',
            width: 162,
            field: 'endTime',
            align: 'center'
            , sortable: false
        },
        {
            title: '是否生效',
            width: 120,
            field: 'effect',
            align: 'center'
            , sortable: false,
            formatter: function (value, row, index) {
                if (row.effect == 1) {
                    return "生效";
                } else {
                    return "失效";
                }
            }
        },
        {
            title: '添加时间',
            width: 162,
            field: 'addTime',
            align: 'center'
            , sortable: false
        },
        {
            title: 'id',
            width: 120,
            field: 'id',
            align: 'center'
            , sortable: false,
            hidden:true
        },
        {
            title: 'ConfigIds映射',
            width: 120,
            field: 'ConfigIdsMM',
            align: 'center'
            , sortable: false,
            hidden:true
           
        },
        {
            title: 'productSpecs',
            width: 120,
            field: 'productSpecs',
            align: 'center'
            , sortable: false,
            hidden:true
           
        },
        
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    onClickRow: function (rowIndex, row) {
        gloid = row.id;
    }
});

//表格and分页js加载
$(function () {
    $('#gridView').datagrid('getPager').pagination({
        total: 0,
        pageSize: 10,
         pageList: [50,100,200],
        showRefresh: true,
        displayMsg: '显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNo, pageSize) {

            var start = (pageNo - 1) * pageSize;//页码分页自增
            var externalSkus = $('#cexternalSkus').val();
            var configIds = $('#cconfigIds').combobox('getValue');


           
            var options = $('#gridView').datagrid('getPager').data("pagination").options;
            $.ajax({
                type: 'POST',
                async: true,
                url: '/eop/externalSalesettings/commission_productListF',
                data: {
                    page: options.pageNumber,
                    rows: options.pageSize,
                    'externalSkus': externalSkus,
                    'configIds': configIds,
                },
                success: function (data, textStatus) {
                    $('#gridView').datagrid('loadData', data);


                    //页码跟随分页进行-loadData后面执行
                    var rowNumbers = $('.datagrid-cell-rownumber');
                    $(rowNumbers).each(function (index) {
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
    onDblClickRow: function (index, row) {
        list();
    }
});
//双击物料编码
$("#code").datagrid({
    onDblClickRow: function (index, row) {
    	SetCodeValuep();
    }
});

$("#cpolicyYear").click(function () {
    $('#oDialog2').dialog('open');

});

$("#cpolicyYear").datepicker({
    language: "zh-CN",
    todayHighlight: true,
    format: 'yyyy',
    autoClose: true,
    startView: 'years',
    maxViewMode: 'years',
    minViewMode: 'years',
    orientation: 'bottom',
    container: '#datetimepicker',
    pickerPosition: 'top-right'

}).on('changeDate', function (ev) {
    $('#oDialog2').dialog('close');
}).on('hide', function (e) {

    $("#cpolicyYear").datepicker('show');
});

$("#policyYear").click(function () {
    $('#oDialog3').dialog('open');

});

$("#policyYear").datepicker({
    language: "zh-CN",
    todayHighlight: true,
    format: 'yyyy',
    autoClose: true,
    startView: 'years',
    maxViewMode: 'years',
    minViewMode: 'years',
    orientation: 'bottom',
    container: '#datetimepicker1',
    pickerPosition: 'top-right'

}).on('changeDate', function (ev) {
    $('#oDialog3').dialog('close');
}).on('hide', function (e) {
    console.log(e + "1");
    $("#policyYear").datepicker('show');
});
$("#oDialog4").css('display', 'none');
$("#oDialog").css('display', 'none');


//物料条件
function product() {
    var sku = $("#sku1").val() == '' ? '%' : $("#sku1").val() + '%';
    var productName = $("#productName1").val() == '' ? '%' : $("#productName1").val() + '%';
    $.ajax({
        type: 'POST',
        async: true,
        url: '/purchase/product/productsList',
        data: {
            sku: sku,
            productName: productName
        },

        success: function (data, textStatus) {
            $('#code').datagrid("loadData", data);
        },
        complete: function (XMLHttpRequest, textStatufs) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
//set值
function SetCodeValuep() {
    var row = $('#code').datagrid('getSelected');
    $("#productName").val(row.productName);
    $("#brandId").val(row.brandId);
    $("#sku").val(row.sku);
    $('#oDialog').dialog('close');
    prosku(row.brandId);
    typesku(row.productTypeId1);

}
//查询品牌
function prosku(sku) {
    $.ajax({
        type: 'POST',
        async: true,
        url: '/purchase/product/brandidList',
        data: {
            id: sku
        },
        success: function (data, textStatus) {
            console.log(data);
            if (data == null || data == "") {
                $("#departmentName").val("");
            } else {
                $("#brandName").val(data[0].brandName);
                $("#brandId").val(data[0].id);
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
//根据id查询品类品类名称
function typesku(productTypeId) {
    $.ajax({
        type: 'POST',
        async: true,
        url: '/purchase/product/producttypesListsku1',
        data: {
            id: productTypeId
        },
        success: function (data, textStatus) {

            $("#categoryName").val(data[0].typeName);
            $("#categoryId").val(data[0].id);
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
//修改
function list() {
	
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt("请选择要修改的单据");
        return;
    } else {
        $('#oDialog4').panel({title: "修改"});
        $("#id").val(rows1[0].id);
        $("#price").html(rows1[0].productSpecs)
        $("#settingName").val(rows1[0].settingName);
        $('#startTime').datetimebox('setValue', rows1[0].startTime);
        $('#endTime').datetimebox('setValue', rows1[0].endTime);
        $('#effect').combobox('setValue', rows1[0].effect);
        $("#externalSkus").val(rows1[0].externalSkus);
        $('#configIds').combobox('setValues', rows1[0].ConfigIdsMM);
        $("#update").show();
    	for (var i = 0 ;i<count;i++)
    	{
    		$("#del"+i).click();
    	}
    	count=0;
        $('#oDialog4').dialog('open');

    }
}

//新增
function Add() {
    $("#id").val("");
    $('#startTime').datetimebox('setValue', "");
    $('#endTime').datetimebox('setValue',"");
    $("#settingName").val("");
    $('#effect').combobox('setValue', 1);
    $('#configIds').combobox('setValues', "");

    var myDate = new Date();
    var minutes = myDate.getMinutes();
    var seconds = myDate.getSeconds();
    if(minutes=="0"){
        minutes = "00";
    }
    if(seconds=="0"){
        seconds = "00";
    }
    var radomStr = Math.floor(Math.random()*10000+1);
    var concatStr = radomStr.toString()+minutes.toString()+seconds.toString();
    var md5Str = hex_md5(concatStr);

    $("#externalSkus").val("HE"+md5Str.substring(0,6).toUpperCase());
    $('#oDialog4').dialog('open');
    $("#update").hide();
	for (var i = 0 ;i<count;i++)
	{
		$("#del"+i).click();
	}
	count=0;
	jQuery('#configIds').combobox('clear');
    $('#oDialog4').panel({title: "新增"});

}

//删除
function Delete() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt( "请选择要删除的单据");
        return;
    } else {
        $.messager.confirm('提示', '您确定要删除这一行吗?', function (b) {
            if (b) {
                $.ajax({
                    type: 'POST',
                    async: false,
                    url: '/eop/externalSalesettings/deletecommission_product',
                    data: {
                        id: rows1[0].id
                    },
                    success: function (data, textStatus) {
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
function SetCodeValue4() {
	
	getsub();
	var configIds = $('#configIds').combobox('getValues');
	var productSpecs=$("#skuandamount").val();
	var settingName=$("#settingName").val();
	var externalSkus=$("#externalSkus").val();
	 var startTime = $('#startTime').datetimebox('getValue');
	 var endTime = $('#endTime').datetimebox('getValue');
	 var timestamp1 = Date.parse(new Date(startTime));
	 var timestamp2 = Date.parse(new Date(endTime));
	 timestamp1 = timestamp1 / 1000;
	 timestamp2 = timestamp2 / 1000;
	 var timestamp = Date.parse(new Date());
	 timestamp = timestamp / 1000;
	 var effect=$('#effect').combobox('getValue');
	 var type=$('#type').combobox('getValue');
    var id = $("#id").val();

    var url = '/eop/externalSalesettings/updatecommission_product';
    if (settingName == "") {
        autoAlt("套装设置名称不能为空");
        return false;
    }
    if(settingName.length >255) {
        autoAlt("套装设置名称不能超过255个汉字");
        return false;
    }
    if (startTime == "") {
        autoAlt("套装开始时间不能为空");
        return false;
    }
    if (endTime == "") {
        autoAlt("套装结束时间不能为空");
        return false;
    }
    if(timestamp1-timestamp2>0){
    	autoAlt("套装结束时间需要大于开始时间");
        return false;
    }
    if (configIds == "") {
        autoAlt("外部系统不能为空");
        return false;
    }
    if(count == 0){
    	autoAlt("套装商品必须添加！");
    	return;
    }
    if (id == "") {
        url = '/eop/externalSalesettings/addcommission_product';
    }

    $.ajax({
        type: 'POST',
        async: false,
        url: url,
        data: {
            id: id,
            settingName: settingName,
            externalSkus: externalSkus,
            productSpecs: productSpecs,
            configIds: configIds.toString(),
            startTime:timestamp1,
            endTime:timestamp2,
            type:type,
            addTime:timestamp,
            effect:effect
            

        },
        success: function (data, textStatus) {
        	if(data == 0){
        		autoAlt("操作失败！");
        	}
        	if(data == 1){
        		autoAlt("操作成功！");
        	}
        	if(data.substring(2,4)=="成功"){
        		autoAlt(data);
        		$('#oDialog4').dialog('close');
        		 SearchUnit();
        	}else{
        		autoAlt(data);
        	}
        }

    });
}
function jiaoyan(sku){
	$.ajax({
        type: 'POST',
        async: false,
        url: '/eop/externalSalesettings/jiaoyan',
        data: {
            sku: sku,
        },
        success: function (data, textStatus) {
          gloid=data;

        }

    });

}

function SearchClear() {
    $('#cconfigIds').combobox('select', '');
    $('#cexternalSkus').val('');
}

var count=0 ;
function additem(id)
{
    var row,cell,str;
    row = document.getElementById(id).insertRow();
    if(row != null )
    {
		cell = row.insertCell();
		cell.innerHTML="套装商品Sku：<font color=\"red\">*</font> <input id=\"Sku"+count+"\" type=\"text\" name=\"Sku"+count+"\" value= \"\" /> 商品价格：<font color=\"red\">*</font> <input id=\"amount"+count+"\" type=\"text\" name=\"amount"+count+"\"  maxlength= \"6\" onkeyup=\"keyUp(this)\" onkeypress=\"keyPress(this)\" onblue=\"onBlur(this)\"><input id=\"del"+count+"\" type=\"button\" value=\"删除\" onclick=\'deleteitem(this);\'>";
		count ++;
    }
}

function deleteitem(obj)
{
	var curRow = obj.parentNode.parentNode;
	tb.deleteRow(curRow.rowIndex);
}

function getsub()
{
	var skuAndAmount = "";
	for (var i = 0 ;i<count;i++)
	{
		skuAndAmount += document.getElementsByName("Sku"+i)[0].value + "--" + document.getElementsByName("amount"+i)[0].value + ";";
	}
	document.getElementById("skuandamount").value = skuAndAmount;
}

function keyPress(ob) {
 if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/)) ob.value = ob.t_value; else ob.t_value = ob.value; if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)) ob.o_value = ob.value;
}
function keyUp(ob) {
 if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/)) ob.value = ob.t_value; else ob.t_value = ob.value; if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)) ob.o_value = ob.value;
        }
function onBlur(ob) {
if(!ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))ob.value=ob.o_value;else{if(ob.value.match(/^\.\d+$/))ob.value=0+ob.value;if(ob.value.match(/^\.$/))ob.value=0;ob.o_value=ob.value};
}
//主表查询
function SearchUnit() {
	 var externalSkus = $('#cexternalSkus').val();
     var configIds = $('#cconfigIds').combobox('getValue');

    var options = $('#gridView').datagrid('getPager').data("pagination").options;
    options.pageNumber = 1;
    $.ajax({
        type: 'POST',
        async: true,
        url: '/eop/externalSalesettings/commission_productListF',
        data: {
        	 page: options.pageNumber,
             rows: options.pageSize,
             'externalSkus': externalSkus,
             'configIds': configIds,
        },
        success: function (data, textStatus) {
        	$('#gridView').datagrid('getPager').pagination('refresh');
            $('#gridView').datagrid('loadData', data);
            if(data.rows == ""){
                $(".pagination-num").val("0");
                }else{
                $(".pagination-num").val("1");
                }
        },
        error: function (e) {
            $.messager.alert(e);
        }
    });

}
