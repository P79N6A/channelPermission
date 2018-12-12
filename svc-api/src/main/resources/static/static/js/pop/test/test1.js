/**
 * purchase模块空调政策表
 * 孙玉凯
 * Created by PC011 on 2017/11/21.
 */


var gloid;
/***
 * 自动加载
 */
var myDate = new Date();
var near = myDate.getFullYear(); //获取完整的年份(4位,1970-????)

// $('#cpolicyYear').combobox('setText', "1");
$('#cpolicyYear').val(near);
$('#policyYear').val(near);
//  $('#policyYear').combobox('setValue', near);
// $('#cpolicyYear').combobox('setValue',near);

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
            title: '店铺',
            width: 70,
            field: 'categoryName',
            align: 'left'
            , sortable: true
        },
        {
            title: '库位编码',
            width: 70,
            field: 'brandName',
            align: 'left'
            , sortable: true
        },
        {
            title: '外部库位编码',
            width: 70,
            field: 'channelName',
            align: 'left'
            , sortable: true
        }

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
        pageList: [10, 20, 30, 40, 50],
        showRefresh: true,
        displayMsg: '显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNo, pageSize) {

            var start = (pageNo - 1) * pageSize;//页码分页自增
            var categoryId = $('#ccategoryId').combobox('getValue');
            var cbrandId = $('#cbrandId').combobox('getValue');
            var channelType = $('#cchannelType').combobox('getValue');
            var cchannelId = $('#cchannelId').combobox('getValue');
            var cpolicyYear = $('#cpolicyYear').val();
            var sku = $('#csku').val();
            if (sku != "") {
                sku =  sku + "%";
            }
            if (categoryId == "全部") {
                categoryId = "";
            }
            if (cbrandId == "全部") {
                cbrandId = "";
            }
            if(cchannelId == '全部'){
                cchannelId='';
            }
            var options = $('#gridView').datagrid('getPager').data("pagination").options;
            $.ajax({
                type: 'POST',
                async: true,
                url: '/purchase/commission_product/commission_productListF',
                data: {
                    page: options.pageNumber,
                    rows: options.pageSize,
                    'remark': categoryId,
                    'brandId': cbrandId,
                    'policyYear': cpolicyYear,
                    channelType:channelType,
                    channelId:cchannelId,
                    'sku':sku
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
                    pager.pagination('refresh', {
                        total: data.length,
                        pageNumber: pageNo
                    });

                }
            });

        }
    });
});
//品类
$("#ccategoryId").combobox({
    url: '/purchase/product/departmentproducttypeList',
    valueField: "id",
    textField: "typeName",
    required: false,
    editable: true,
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

//品牌
$("#cbrandId").combobox({
    url: '/purchase/product/brandList',
    valueField: "id",
    textField: "typeName",
    required: false,
    editable: true,
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
//查询渠道
$("#cchannelId").combobox({
    url: '/purchase/product/channelsList',
    valueField: "id",
    textField: "channelcode",
    required: false,
    editable: true,
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
//渠道
$("#channelId").combobox({
    url: '/purchase/product/channelsList',
    valueField: "id",
    textField: "channelcode",
    required: true,
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
//判断渠道来源
$("#cchannelType").combobox({
    onSelect: function (record) {
        if(record.value == 1){
            channelsInfoListNo("cchannelId");
        }
        if(record.value == 2){
            channelsNo("cchannelId");
        }
    }

});
//判断渠道来源
$("#channelType").combobox({
    onSelect: function (record) {
        if(record.value == 1){
            channelsInfoList("channelId");
        }
        if(record.value == 2){
            channels("channelId");
        }
    }

});

$(function(){
    $('#monthPolicy').numberbox('textbox').attr('maxlength', 7);
})
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

//物料编码触发
$("#sku").click(function () {
    $('#code').datagrid('loadData', {total: 0, rows: []});
    $("#sku1").val("");
    $("#productName1").val("");
    $('#oDialog').dialog('open');

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
        complete: function (XMLHttpRequest, textStatus) {
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
         $('#policyYear').val(rows1[0].policyYear);
        $('#oDialog4').panel({title: "修改"});
        $("#id").val(rows1[0].id);
        $("#brandId").val(rows1[0].brandId);
        $("#brandName").val(rows1[0].brandName);
        $("#categoryId").val(rows1[0].categoryId);
        $("#categoryName").val(rows1[0].categoryName);
        $("#sku").val(rows1[0].sku);
        $("#productName").val(rows1[0].productName);
        $("#monthPolicy").numberbox('setValue',rows1[0].monthPolicy);
        $("#channelType").combobox("setValue", rows1[0].channelType);
        if( rows1[0].channelType == 1){
            channelsInfoList("channelId");
        }
        else{
            channels("channelId");
        }
        $("#channelId").combobox("setValue", rows1[0].channelId);
        $('#oDialog4').dialog('open');

    }
}

//新增
function Add() {
    // $('#policyYear').val( near);
    $("#id").val("");
    $("#brandId").val("");
    $("#brandName").val("");
    $("#categoryId").val("");
    $("#categoryName").val("");
    $("#sku").val("");
    $("#productName").val("");
    $("#monthPolicy").numberbox('setValue',"");
    $("#channelType").combobox('setValue',"");
    $("#channelId").combobox('setValue',"");
    $('#policyYear').val(near);
    $('#oDialog4').dialog('open');
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
                    url: '/purchase/commission_product/deletecommission_product',
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

    var id = $("#id").val();

    var sku = $("#sku").val();
    var productName = $("#productName").val();
    var brandName = $("#brandName").val();
    var brandId = $("#brandId").val();
    var categoryId = $("#categoryId").val();
    var categoryName = $("#categoryName").val();
    var monthPolicy = $("#monthPolicy").numberbox('getValue');
    var channelType= $('#channelType').combobox('getValue');
    var channelId= $('#channelId').combobox('getValue');
    var policyYear= $('#policyYear').val();
    var url = '/purchase/commission_product/updatecommission_product';

    if (sku == "") {
        autoAlt("物料编码不能为空");
        return false;
    }
    if (monthPolicy == "") {
        autoAlt("月度政策不能为空");
        return false;
    }
    if (Number(monthPolicy) > 100) {
        autoAlt("月度政策不能大于100");
        return false;
    }
    if(channelId == ""){
        autoAlt("渠道不能为空");
    }
    var flage;
    flage =jiaoyan();
   if(flage == false){
   	autoAlt("相同渠道、物料编码、政策年度不能相同");
   	return;
   }
    if (id == "") {
        url = '/purchase/commission_product/addcommission_product';
    }

    $.ajax({
        type: 'POST',
        async: false,
        url: url,
        data: {
            id: id,
            sku: sku,
            productName: productName,
            brandName: brandName,
            brandId: brandId,
            categoryId: categoryId,
            monthPolicy: monthPolicy,
            categoryName: categoryName,
            channelId:channelId,
            channelType:channelType,
            policyYear:policyYear

        },
        success: function (data, textStatus) {
            if (data == 1) {
                autoAlt("操作成功！");
                $('#oDialog4').dialog('close');
                SearchUnit();
            } else {
                autoAlt("操作失败！");
            }

        }

    });
}
function jiaoyan(){
	var flag;
	var sku= $('#sku').val();
    var channelType= $("#channelType").combobox('getValue');
    var channelId= $('#channelId').combobox('getValue');
    var policyYear= $('#policyYear').val();
    var id= $('#id').val();
var url = '/purchase/commission_product/jiaoyancommission';
$.ajax({
    type: 'POST',
    async: false,
    url: url,
    data: {
    	policyYear: policyYear,
        channelType: channelType,
        channelId: channelId,
        sku: sku,
        id:id

    },
    success: function (data, textStatus) {
    	flag=data;
    },
    error: function (e) {
        $.messager.alert(e);
    }
});
return flag;
}
function SearchClear() {
    $('#cpolicyYear').val(near);
    $('#ccategoryId').combobox('setValue', "全部");
    $('#cbrandId').combobox('setValue', "全部");
    $('#cchannelType').combobox('setValue', "2");
    $('#cchannelType').combobox('setText', "渠道");
    $('#cchannelId').combobox('setValue', "全部");
    $('#csku').textbox('setValue', "");
}
//主表查询
function SearchUnit() {
    var categoryId = $('#ccategoryId').combobox('getValue');
    var cbrandId = $('#cbrandId').combobox('getValue');
    var channelType = $('#cchannelType').combobox('getValue');
    var cchannelId = $('#cchannelId').combobox('getValue');
    var cpolicyYear = $('#cpolicyYear').val();
    var sku = $('#csku').val();
    if (sku != "") {
        sku =  sku + "%";
    }
    if (categoryId == "全部") {
        categoryId = "";
    }
    if (cbrandId == "全部") {
        cbrandId = "";
    }
    if(cchannelId == '全部'){
        cchannelId='';
    }

    var options = $('#gridView').datagrid('getPager').data("pagination").options;
    options.pageNumber = 1;
    $.ajax({
        type: 'POST',
        async: true,
        url: '/purchase/commission_product/commission_productListF',
        data: {

            page: options.pageNumber,
            rows: options.pageSize,

            'remark': categoryId,
            'brandId': cbrandId,
            'policyYear': cpolicyYear,
            channelType:channelType,
            channelId:cchannelId,
            'sku':sku

        },
        success: function (data, textStatus) {
            $('#gridView').datagrid('loadData', data);
        },
        error: function (e) {
            $.messager.alert(e);
        }
    });

}

$("#importBtn_orderList").click(function () {
    var categoryId = $('#ccategoryId').combobox('getValue');
    var cbrandId = $('#cbrandId').combobox('getValue');
    var channelType = $('#cchannelType').combobox('getValue');
    var cchannelId = $('#cchannelId').combobox('getValue');
    var cpolicyYear = $('#cpolicyYear').val();
    var sku = $('#csku').val();
    $("#channelType2").val(channelType);
    $("#policyYear2").val(cpolicyYear);
    if (sku != "") {
        sku =  sku + "%";
    }
    if (categoryId == "全部") {
        categoryId = "";
    }
    if (cbrandId == "全部") {
        cbrandId = "";
    }
    if(cchannelId == '全部'){
        cchannelId='';
    }
    if (sku != "") {
        $("#sku2").val( sku + "%");
    }
    if (categoryId == "全部") {
        $("#categoryId2").val("");
    }else{
        $("#categoryId2").val(categoryId);
    }

    if (cbrandId == "全部") {
        $("#brandId2").val("");
    }else{
        $("#brandId2").val(cbrandId);
    }

    $("#exportForm").attr("action","/purchase/commission_product/exportCommission_product");
    $("#exportForm").submit();

});
$("#importdemo").click(function () {
  

    $("#exportForm").attr("action","/purchase/commission_product/exportdemo");
    $("#exportForm").submit();

});


//导入
function importZhjj(){
    $.messager.progress({title : '导入中...',msg : '导入中...'});
    $("#fileForm").ajaxSubmit({
        type: 'post',
        url  : '/purchase/commission_product/importcommission_product',
        success:function(data){
            $.messager.progress('close');

            if(data.msg.substring(0,2) == "重复"){
                $.messager.confirm('提示', '物料编码重复是否覆盖?', function (b) {
                    if (b) {
                        $.messager.progress({title : '导入中...',msg : '导入中...'});
                        $("#fileForm").ajaxSubmit({
                            type: 'post',
                            url: '/purchase/commission_product/importcommission_product1',
                            success: function (data) {
                                $.messager.progress('close');

                                $("#file").val("");
                                $.messager.alert('提示', data.msg);
                                SearchUnit();

                            },
                            error: function (result) {
                                $.messager.progress('close');
                            }
                        });
                    }
                });
            }else{
                $("#file").val("");
                $.messager.alert('提示',data.msg);
                SearchUnit();
            }
        },
        error : function(result){
            $.messager.progress('close');
        }
    });

}
