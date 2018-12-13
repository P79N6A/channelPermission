/**
 * eop 库存同步配置
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
             field: 'source',
             align: 'center'
             , sortable: false
         },
         {
             title: '物料编码',
             width: 120,
             field: 'sku',
             align: 'center'
             , sortable: false
         },
         {
             title: '套装SKU',
             width: 120,
             field: 'tzSku',
             align: 'center'
             , sortable: false
         },
         {
             title: '库位编码',
             width: 120,
             field: 'sCode',
             align: 'center'
             , sortable: false
         },
         {
             title: '是否套装',
             width: 120,
             field: 'stype',
             align: 'center'
             , sortable: false,
             formatter: function (value, row, index) {
                 if (row.stype == 1) {
                     return "否";
                 } else {
                     return "是";
                 }
             }
         },
         {
             title: '同步状态',
             width: 120,
             field: 'isOn',
             align: 'center'
             , sortable: false,
             formatter: function (value, row, index) {
                 if (row.isOn == 1) {
                     return "同步";
                 } else {
                     return "不同步";
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
            var source = $('#csource').combobox("getValue");
            var tzSku = $('#ctzSku').val();
            var sku = $('#csku').val();
            var sCode=$("#csCode").val();
            var isOn=$("#cisOn").combobox('getValue');
            if (sku != "") {
                sku =  sku + "%";
            }
            if (sCode != "") {
            	sCode =  sCode + "%";
            }
            if (tzSku != "") {
            	tzSku =  tzSku + "%";
            }
            if (source == "全部") {
            	source = "";
            }
           
            var options = $('#gridView').datagrid('getPager').data("pagination").options;
            $.ajax({
                type: 'POST',
                async: true,
                url: '/eop/StocksyncProstorage/commission_productListF',
                data: {
                	 page: options.pageNumber,
                     rows: options.pageSize,
                     'source': source,
                     'tzSku': tzSku,
                     'sku':sku,
                     isOn:isOn,
                     sCode:sCode
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
        $("#sCode").val(rows1[0].sCode);
        $("#source").val(rows1[0].source);
        $('#isOn').combobox('setValue', rows1[0].isOn);
        $('#stype').combobox('setValue', rows1[0].stype);
        $('#kuwei').combobox('setValue', "0");
        $('#oDialog4').dialog('open');

    }
}

//新增
function Add() {
    $("#id").val("");
    $("#source").val("");
    $("#sku").val("");
    $("#sCode").val("");
    $('#kuwei').combobox('setValue', "1");
    $('#stype').combobox('setValue', "1");
    $('#cisOn').combobox('setValue', "1");
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
                    url: '/eop/StocksyncProstorage/deletecommission_product',
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
    var source = $("#source").combobox("getValue");
    var sku = $("#sku").val();
    var sCode = $("#sCode").val();
    var stype = $('#stype').combobox('getValue');
    var isOn = $('#isOn').combobox('getValue');
    var kuwei = $("#kuwei").combobox("getValue");

    var url = '/eop/StocksyncProstorage/updatecommission_product';
    if (source == "") {
        autoAlt("来源店铺不能为空");
        return false;
    }
    if (sku == "") {
        autoAlt("物料编码不能为空");
        return false;
    }
    //验证指定库位的库存同步是否存在
    if(kuwei == "0"){
        jiaoyan(sku,sCode,source);
        if(gloid.count != "0"){
            autoAlt("物料编码" + sku + "的" + gloid.count + "库存同步配置已存在！");
            return;
        }
        if(gloid.id == 0){
            autoAlt("商品对应关系不存在！");
            return;
        }
    }
    if (id == "") {
        url = '/eop/StocksyncProstorage/addcommission_product';
    }

    $.ajax({
        type: 'POST',
        async: false,
        url: url,
        data: {
            id: id,
            sku: sku,
            source: source,
            stype: stype,
            isOn:isOn,
            sCode:sCode,
            kuwei:kuwei
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
function jiaoyan(sku,sCode,source){
	$.ajax({
        type: 'POST',
        async: false,
        url: '/eop/StocksyncProstorage/jiaoyan',
        data: {
            sku: sku,
            sCode:sCode,
            source:source
        },
        success: function (data, textStatus) {
          gloid=data;

        }

    });

}
$("#kuwei").combobox({
	onChange: function (n,o) {
		var kuwei = $("#kuwei").combobox("getValue");
		if(kuwei == 1){
			document.getElementById("sCode").readOnly=true;
		}else
		{
			document.getElementById("sCode").readOnly=false;
		}
	}
});


function SearchClear() {
    $('#ctzSku').val('');
    $('#csCode').val('');
    $('#ctzSku').val('');
    $('#cisOn').combobox('setValue', "1");
    $('#csku').val("");
    $('#csource').val('');
}
//主表查询
function SearchUnit() {
    var source =$("#csource").combobox("getValue");
    var tzSku = $('#ctzSku').val();
    var sku = $('#csku').val();
    var sCode=$("#csCode").val();
    var isOn=$("#cisOn").combobox('getValue');
    if (sku != "") {
        sku =  sku + "%";
    }
    if (sCode != "") {
    	sCode =  sCode + "%";
    }
    if (tzSku != "") {
    	tzSku =  tzSku + "%";
    }
    if (source == "全部") {
    	source = "";
    }
    var options = $('#gridView').datagrid('getPager').data("pagination").options;
    options.pageNumber = 1;
    $.ajax({
        type: 'POST',
        async: true,
        url: '/eop/StocksyncProstorage/commission_productListF',
        data: {

        	 page: options.pageNumber,
             rows: options.pageSize,
             'source': source,
             'tzSku': tzSku,
             'sku':sku,
             isOn:isOn,
             sCode:sCode
             
            

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
