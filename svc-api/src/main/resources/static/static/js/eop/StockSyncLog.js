/**
 * eop 库存同步
 * 孙玉凯
 * Created by PC011 on 2018/1/22.
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
            width: 120,
            field: 'sse',
            align: 'center'
            , sortable: true
        },
        {
            title: '物料编码',
            width: 152,
            field: 'sku',
            align: 'center'
            , sortable: true
        },
        {
            title: '外部产品编码',
            width: 120,
            field: 'sourceproductId',
            align: 'center'
            , sortable: true
        },
        {
            title: '库位编码',
            width: 120,
            field: 'sCode',
            align: 'center'
            , sortable: true
        },
        {
            title: '外部库位编码',
            width: 120,
            field: 'sourceStoreCode',
            align: 'center'
            , sortable: true
        },
        {
            title: '库存同步数量',
            width: 120,
            field: 'ehaierStockNum',
            align: 'left'
            , sortable: true
        },
        {
            title: '记录时间',
            width: 162,
            field: 'addTime',
            align: 'center'
            , sortable: true
        },
        {
            title: '同步结果',
            width: 120,
            field: 'stockSyncResult',
            align: 'center'
            , sortable: true
        },
        {
            title: '备注',
            width: 	120,
            field: 'textInfo',
            align: 'center'
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
         pageList: [50,100,200],
        showRefresh: true,
        displayMsg: '显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNo, pageSize) {

            var start = (pageNo - 1) * pageSize;//页码分页自增
            var sse = $('#sse').val();
            var sourceproductId = $('#sourceproductId').val();
            var sku = $('#sku').val();
            var scode = $('#scode').val();
            var sourceStoreCode = $('#sourceStoreCode').val();
            var stockSyncResult = $('#stockSyncResult').combobox('getValue');
            if (sku != "") {
                sku =  sku + "%";
            }
            if (sourceproductId != "") {
            	sourceproductId = sourceproductId+"%";
            }
            if (sse != "") {
            	sse = sse+"%";
            }
           if(sourceStoreCode != ""){
        	   sourceStoreCode=sourceStoreCode+"%";
           }
            var options = $('#gridView').datagrid('getPager').data("pagination").options;
            $.ajax({
                type: 'POST',
                async: true,
                url: '/eop/StocksynCstorage/logListF',
                data: {
                    page: options.pageNumber,
                    rows: options.pageSize,
                    'sse': sse,
                    'sourceproductId': sourceproductId,
                    'sku':sku,
                    'scode':scode,
                    'sourceStoreCode':sourceStoreCode,
                    'stockSyncResult':stockSyncResult,
                    'addTimeStart':$('#addTimeStart').datetimebox('getValue'),
            		'addTimeEnd':$('#addTimeEnd').datetimebox('getValue')
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
        $('#oDialog4').panel({title: "修改"});
        $("#id").val(rows1[0].id);
        $("#sku").val(rows1[0].sku);
        $("#tzSku").val(rows1[0].tzSku);
        $("#source").val(rows1[0].source);
        $('#stype').combobox('setValue', rows1[0].stype);
        $('#oDialog4').dialog('open');

    }
}

//新增
function Add() {
    $("#id").val("");
    $("#tzSku").val("");
    $("#source").val("");
    $("#sku").val("");
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
                    url: '/eop/monitoring/deletecommission_product',
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
    var source = $("#source").val();
    var tzSku = $("#tzSku").val();
    var stype= $('#stype').combobox('getValue');
    var url = '/eop/monitoring/updatecommission_product';

    if (sku == "") {
        autoAlt("物料编码不能为空");
        return false;
    }
    if (source == "") {
        autoAlt("来源店铺不能为空");
        return false;
    }
    if (tzSku == "") {
        autoAlt("外部编码不能为空");
        return false;
    }
    jiaoyan(sku);
    if(gloid == 0){
    	autoAlt("物料编码不存在！");
    	return;
    }
    if (id == "") {
        url = '/eop/monitoring/addcommission_product';
    }

    $.ajax({
        type: 'POST',
        async: false,
        url: url,
        data: {
            id: id,
            sku: sku,
            source: source,
            tzSku: tzSku,
            stype: stype,
            productId:gloid

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
function jiaoyan(sku){
	$.ajax({
        type: 'POST',
        async: false,
        url: '/eop/monitoring/jiaoyan',
        data: {
            sku: sku,
        },
        success: function (data, textStatus) {
          gloid=data;

        }

    });

}
function SearchClear() {
	$('#sse').val('');
    $('#sku').val('');
    $('#stockSyncResult').combobox('setValue', "成功");
    $('#sourceproductId').val(""); 
    $('#scode').val("");
    $('#sourceStoreCode').val("");
    $('#addTimeStart').datetimebox('setValue', '');
    $('#addTimeEnd').datetimebox('setValue', '');
    
}
//主表查询
function SearchUnit() {
	 var sse = $('#sse').val();
     var sourceproductId = $('#sourceproductId').val();
     var sku = $('#sku').val();
     var scode = $('#scode').val();
     var sourceStoreCode = $('#sourceStoreCode').val();
     var stockSyncResult = $('#stockSyncResult').combobox('getValue');
     if($('#addTimeStart').datetimebox('getValue') == ''){
    	autoAlt('开始时间必须选择！'); 
    	return;
     }
     if($('#addTimeEnd').datetimebox('getValue') == ''){
     	autoAlt('结束时间必须选择！'); 
     	return;
      }
     if (sku != "") {
         sku =  sku + "%";
     }
     if (sourceproductId != "") {
     	sourceproductId = sourceproductId+"%";
     }
     if (sse != "") {
     	sse = sse+"%";
     }
    if(sourceStoreCode != ""){
 	   sourceStoreCode=sourceStoreCode+"%";
    }
    var options = $('#gridView').datagrid('getPager').data("pagination").options;
    options.pageNumber = 1;
    $.ajax({
        type: 'POST',
        async: true,
        url: '/eop/StocksynCstorage/logListF',
        data: {

        	 page: options.pageNumber,
             rows: options.pageSize,
             'sse': sse,
             'sourceproductId': sourceproductId,
             'sku':sku,
             'scode':scode,
             'sourceStoreCode':sourceStoreCode,
             'stockSyncResult':stockSyncResult,
             'addTimeStart':$('#addTimeStart').datetimebox('getValue'),
     		'addTimeEnd':$('#addTimeEnd').datetimebox('getValue')
            

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
