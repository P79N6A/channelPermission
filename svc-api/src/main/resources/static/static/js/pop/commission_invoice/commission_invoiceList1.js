/**
 * pop模块佣金结算表
 * 孙玉凯
 * Created by PC011 on 2017/11/21.
 */


var gloid;
/***
 * 自动加载
 */
var myDate = new Date();
var near = myDate.getFullYear(); //获取完整的年份(4位,1970-????)


$('#cpolicyYear').val(near);
function getDaysInOneMonth(year, month) {
    month = parseInt(month, 10);
    var d = new Date(year, month, 0);  //这个是都可以兼容的
    var date = new Date(year + "/" + month + "/0")   //IE浏览器可以获取天数，谷歌浏览器会返回NaN
    return d.getDate();
}

$('#gridView').datagrid({
    striped: true, // 隔行变色
    rownumbers: true,
    fit: true,
    pagination: true,
    singleSelect: true,
    fitColunms: false,
    idField: 'id',
    remoteSort: false,
    showFooter: true,
    frozenColumns:[[    

       
        {
            title: '品类',
            width: 50,
            field: 'col04',
            align: 'left'
            , sortable: true,
            rowspan:2
        },
        {
            title: '品牌',
            width: 50,
            field: 'col05',
            align: 'left'
            , sortable: true,
            rowspan:2
        },
        {
            title: '渠道',
            width: 60,
            field: 'col06',
            align: 'left'
            , sortable: true,
            rowspan:2
        },
        ],[]],    
        columns: [[
        {
            title: '1月',
            width: 70,
            align: 'left'
            , sortable: true,
            colspan:4
        }
        ,
        {
            title: '2月',
            width: 70,
            align: 'left'
            , sortable: true,
            colspan:4
        }
        ,
        {
            title: '3月',
            width: 70,
            align: 'left'
            , sortable: true,
            colspan:4
        }
        ,

        {
            title: '4月',
            width: 120,
            align: 'left'
            , sortable: true,
            colspan:4
        },
        {
            title: '5月',
            width: 120,
            align: 'left'
            , sortable: true,
            colspan:4
        },
        {
            title: '6月',
            width: 120,
            align: 'left'
            , sortable: true,
            colspan:4
        },
        {
            title: '7月',
            width: 120,
            align: 'left'
            , sortable: true,
            colspan:4
        },
        {
            title: '8月',
            width: 120,
            align: 'left'
            , sortable: true,
            colspan:4
        },
        {
            title: '9月',
            width: 120,
            align: 'left'
            , sortable: true,
            colspan:4
        },
        {
            title: '10月',
            width: 120,
            align: 'left'
            , sortable: true,
            colspan:4
        },
        {
            title: '11月',
            width: 120,
            align: 'left'
            , sortable: true,
            colspan:4
        },
        {
            title: '12月',
            width: 120,
            align: 'left'
            , sortable: true,
            colspan:4
        },
        {
            title: '年度',
            width: 70,
            align: 'left'
            , sortable: true,
            colspan:4
        }
        ]
    ,
    [
		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col07',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}
		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col08',
		    align: 'left'
		    , sortable: true
		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col09',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col10',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col11',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col12',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col13',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col14',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col15',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col16',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col17',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col18',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col19',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col20',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col21',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col22',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		}
		,		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col23',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col24',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col25',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col26',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		}
		,		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col27',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col28',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col29',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col30',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		}
		,		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col31',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col32',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col33',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col34',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		}
		,		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col35',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col36',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col37',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col38',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		}
		,		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col39',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col40',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col41',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col42',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		}
		,		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col43',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col44',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col45',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col46',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		}
		,		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col47',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col48',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col49',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col50',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		}
		,		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col51',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col52',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col53',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col54',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		}
		,		{
		    title: '结算金额(万)',
		    width: 80,
		    field: 'col55',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

		},
		{
		    title: '目标值(万)',
		    width: 70,
		    field: 'col56',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '政策(%)',
		    width: 50,
		    field: 'col57',
		    align: 'left'
		    , sortable: true

		},
		{
		    title: '返佣金额(元)',
		    width: 80,
		    field: 'col58',
		    align: 'left'
		    , sortable: true
		    ,formatter:function(value,row,index){ if(value == undefined){return "0.00"}else{return toFix(value);}}

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
        showRefresh: false,
        displayMsg: '显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNo, pageSize) {
            var start = (pageNo - 1) * pageSize;//页码分页自增
            var categoryId = $('#ccategoryId').combobox('getValue');
            var cpolicyYear = $('#cpolicyYear').val();
            var brandId = $('#cbrandId').combobox('getValue');
            var channelType = $('#cchannelType').combobox('getValue');
            var cchannelId = $('#cchannelId').combobox('getValue');

            if (brandId == '全部' || brandId== "" ) {
                brandId = 0;
            }
            if (cchannelId == '全部' ||cchannelId == "") {
                cchannelId = 0;
            }
            if (categoryId == "全部" || categoryId == "") {
                categoryId = '';
            }

            var options = $('#gridView').datagrid('getPager').data("pagination").options;
            $.ajax({
                type: 'POST',
                async: true,
                url: '/pop/commission_target/commission_product_invoice1',
                data: {

                	page: options.pageNumber,
                    rows: options.pageSize,

                    'policyYear': cpolicyYear,
                    'startTime': '',
                    'endTime': '',
                    'categoryId': categoryId,
                    'brandId':brandId,
                    'channelType':channelType,
                    'channelId':cchannelId

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
                },
                beforeSend: function () {
                	ajaxLoading();
            	},
            	complete: function() {
            		ajaxLoadEnd();
            	}
            });

        }
    });
});
//品类
$("#ccategoryId").combobox({
    url: '/pop/product/departmentproducttypeList',
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
//品类
$("#categoryId").combobox({
    url: '/pop/product/departmentproducttypeList',
    valueField: "id",
    textField: "typeName",
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
//品牌
$("#cbrandId").combobox({
    url: '/pop/product/brandList',
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
    url: '/pop/product/channelsList',
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
    url: '/pop/product/channelsList',
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
$("#gridView").datagrid({
    onDblClickRow: function (index, row) {
        list();
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


//修改
function list() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt("请选择要修改的单据");
        return;
    } else {
        $('#policyYear').combobox('setValue', rows1[0].policyYear);
        $("#categoryId").combobox("setValue", rows1[0].categoryId);
        $("#yearTargetSymbol").combobox("setValue", rows1[0].yearTargetSymbol);
        $('#yearTargetValue').numberbox('setValue', rows1[0].yearTargetValue);
        $('#monthPolicy').numberbox('setValue', rows1[0].monthPolicy);
        $('#quarterStandardReward').numberbox('setValue', rows1[0].quarterStandardReward);
        $('#yearStandardReward').numberbox('setValue', rows1[0].yearStandardReward);
        $("#id").val(rows1[0].id);
        $("#categoryName").val(rows1[0].categoryName);
        $('#oDialog4').dialog('open');

    }
}

//新增
function Add() {
    $("#policyYear").combobox("setValue", near);
    $("#categoryId").combobox("setValue", "");
    $("#yearTargetSymbol").combobox("setValue", "");
    $('#yearTargetValue').numberbox('setValue', "");
    $('#monthPolicy').numberbox('setValue', "");
    $('#quarterStandardReward').numberbox('setValue', "");
    $('#yearStandardReward').numberbox('setValue', "");
    $("#id").val("");
    $("#categoryName").val("");
    $('#oDialog4').dialog('open');


}

//删除
function Delete() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt("请选择要删除的单据");
        return;
    } else {
        $.messager.confirm('提示', '您确定要删除这一行吗?', function (b) {
            if (b) {
                $.ajax({
                    type: 'POST',
                    async: false,
                    url: '/pop/commission/deletecommission',
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
    var policyYear = $('#policyYear').combobox('getValue');
    var categoryId1 = $("#categoryId").textbox('getValue');
    var yearTargetSymbol = $("#yearTargetSymbol").combobox('getText');
    var yearTargetValue = $("#yearTargetValue").numberbox('getValue');
    var monthPolicy = $("#monthPolicy").numberbox('getValue');
    var yearStandardReward = $("#yearStandardReward").numberbox('getValue');
    var categoryName = $("#categoryId").combobox('getText');
    var quarterStandardReward = $("#quarterStandardReward").numberbox('getValue');
    var url = '/pop/commission/updatecommission';
    if (policyYear.length == 0) {
        autoAlt("政策年度不能为空");
        return false;
    }
    if (yearTargetSymbol.length == 0) {
        autoAlt("年度目标符号不能为空");
        return false;
    }
    if (yearTargetValue.length == 0) {
        autoAlt("年度目标不能为空");
        return false;
    }
    if (monthPolicy.length == 0) {
        autoAlt("月度政策不能为空");
        return false;
    }
    if (quarterStandardReward.length == 0) {
        autoAlt("季度目标达标奖励不能为空");
        return false;
    }
    if (yearStandardReward.length == 0) {
        autoAlt("年度目标达标奖励不能为空");
        return false;
    }

    if (Number(yearTargetValue) > 99999999.99) {
        autoAlt("年度目标最大值不能超过'99999999.99'");
        return false;
    }

    if (Number(monthPolicy) > 99999999.99) {
        autoAlt("月度政策不能超过'99999999.99'");
        return false;
    }
    if (Number(quarterStandardReward) > 99999999.99) {
        autoAlt("季度目标达标奖励不能超过'99999999.99'");
        return false;
    }
    if (Number(yearStandardReward) > 99999999.99) {
        autoAlt("年度目标达标奖励不能超过'99999999.99'");
        return false;
    }

    if (id == "") {
        url = '/pop/commission/addcommission';
    }

    $.ajax({
        type: 'POST',
        async: false,
        url: url,
        data: {
            id: id,
            policyYear: policyYear,
            categoryId: categoryId1,
            categoryName: categoryName,
            yearTargetSymbol: yearTargetSymbol,
            yearTargetValue: yearTargetValue,
            monthPolicy: monthPolicy,
            yearStandardReward: yearStandardReward,
            quarterStandardReward: quarterStandardReward

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
function SearchClear() {
    $('#cpolicyYear').val(near);
    $('#ccategoryId').combobox('setValue', "全部");
    $('#cbrandId').combobox('setValue', "全部");
    $('#cchannelId').combobox('setValue', "全部");
    $('#cpolicyYear').val(near);
    $('#cchannelType').combobox('setValue', "2");
    $('#cchannelType').combobox('setText', "渠道");
}

//导出
$("#importBtn_orderList").click(function () {
	var categoryId = $('#ccategoryId').combobox('getValue');
    var cpolicyYear = $('#cpolicyYear').val();
    var brandId = $('#cbrandId').combobox('getValue');
    var channelType = $('#cchannelType').combobox('getValue');
    var cchannelId = $('#cchannelId').combobox('getValue');
    $("#policyYear2").val(cpolicyYear);
    $("#channelType2").val(channelType);
    if (brandId == '全部' || brandId== "" ) {
        $("#brandId2").val(0);
    }else{
    	$("#brandId2").val(brandId);
    }
    if (cchannelId == '全部' ||cchannelId == "") {
    	$("#channelId2").val(0);
    }else{
    	$("#channelId2").val(cchannelId);
    }
    if (categoryId == "全部" || categoryId == "") {
    	$("#categoryId2").val('');
    }else{
    	$("#categoryId2").val(categoryId);
    }

    $("#exportForm").attr("action","/pop/commission_target/exportCommission_invoice1");
    $("#exportForm").submit();

});
//主表查询
function SearchUnit() {
    var categoryId = $('#ccategoryId').combobox('getValue');
    var cpolicyYear = $('#cpolicyYear').val();
    var brandId = $('#cbrandId').combobox('getValue');
    var channelType = $('#cchannelType').combobox('getValue');
    var cchannelId = $('#cchannelId').combobox('getValue');

    if (brandId == '全部' || brandId== "" ) {
        brandId = 0;
    }
    if (cchannelId == '全部' ||cchannelId == "") {
        cchannelId = 0;
    }
    if (categoryId == "全部" || categoryId == "") {
        categoryId = '';
    }
    var options = $('#gridView').datagrid('getPager').data("pagination").options;
    options.pageNumber = 1;
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/commission_target/commission_product_invoice1',
        data: {

            page: options.pageNumber,
            rows: options.pageSize,

            'policyYear': cpolicyYear,
            'startTime': '',
            'endTime': '',
            'categoryId': categoryId,
            'brandId':brandId,
            'channelType':channelType,
            'channelId':cchannelId

        },
        success: function (data, textStatus) {
            
        	h_data = JSON.stringify(data.rows);
        	SearchUnit1();
        }
    
    });

    function SearchUnit1() {
        var categoryId = $('#ccategoryId').combobox('getValue');
        var cpolicyYear = $('#cpolicyYear').val();
        var brandId = $('#cbrandId').combobox('getValue');
        var channelType = $('#cchannelType').combobox('getValue');
        var cchannelId = $('#cchannelId').combobox('getValue');

        if (brandId == '全部' || brandId== "" ) {
            brandId = 0;
        }
        if (cchannelId == '全部' ||cchannelId == "") {
            cchannelId = 0;
        }
        if (categoryId == "全部" || categoryId == "") {
            categoryId = '';
        }
        var options = $('#gridView').datagrid('getPager').data("pagination").options;
        options.pageNumber = 1;
        $.ajax({
            type: 'POST',
            async: true,
            url: '/pop/commission_target/commission_product_invoice1',
            data: {

                page: 0,
                rows: 100000,

                'policyYear': cpolicyYear,
                'startTime': '',
                'endTime': '',
                'categoryId': categoryId,
                'brandId':brandId,
                'channelType':channelType,
                'channelId':cchannelId

            },
            success: function (data, textStatus) {
            	$('#gridView').datagrid('getPager').pagination('refresh');
            	var data1 = JSON.stringify(data.rows);
                
                data1 = Sum2(data, data1, "col07", "col10", "col11", "col14", "col15", "col18", "col19", "col22", "col23", "col26","col27","col30","col31","col34","col35","col38","col39","col42","col43","col46","col47","col50","col51","col54","col55","col58");
                ajaxLoadEnd();
                $('#gridView').datagrid('loadData', data1);
            },
            error: function (e) {
                $.messager.alert(e);
            }
        });
    }	
}
