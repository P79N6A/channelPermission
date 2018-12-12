/**
 * Pop模块佣金政策表
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
            title: 'id',
            width: 0,
            field: 'id',
            align: 'left',
            hidden: true
            , sortable: true

        },
        {
            title: '品类id',
            width: 50,
            field: 'categoryId',
            align: 'left'
            , sortable: true,
            hidden: true
        },
        {
            title: '政策年度',
            width: 70,
            field: 'policyYear',
            align: 'left'
            , sortable: true
        },
        {
            title: '品类',
            width: 150,
            field: 'categoryName',
            align: 'left'
            , sortable: true
        },
        {
            title: '品牌',
            width: 150,
            field: 'brandName',
            align: 'left'
            , sortable: true
        },
        {
            title: '渠道',
            width: 90,
            field: 'channelName',
            align: 'left'
            , sortable: true
        },
        {
            title: '年度目标符号',
            width: 70,
            field: 'yearTargetSymbol',
            align: 'left'
            , sortable: true
        },
        {
            title: '年度目标(万元)',
            width: 100,
            field: 'yearTargetValue',
            align: 'left'
            , sortable: true
        },
        {
            title: '月度政策(%)',
            width: 90,
            field: 'monthPolicy',
            align: 'left'
            , sortable: true,
            hidden: true
        },
        {
            title: '季度目标达标奖励(%)',
            width: 100,
            field: 'quarterStandardReward',
            align: 'left'
            , sortable: true
        },
        {
            title: '年度目标达标奖励(%)',
            width: 100,
            field: 'yearStandardReward',
            align: 'left'
            , sortable: true
        },
        {
            title: 'channelId',
            width: 1,
            field: 'channelId',
            align: 'left'
            , sortable: true,
            hidden:true
        },
        {
            title: 'brandId',
            width: 1,
            field: 'brandId',
            align: 'left'
            , sortable: true,
            hidden:true
        },
        {
            title: 'channelType',
            width: 1,
            field: 'channelType',
            align: 'left'
            , sortable: true,
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
        showRefresh: false,
        displayMsg: '显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNo, pageSize) {
            var start = (pageNo - 1) * pageSize;//页码分页自增
            var categoryId = $('#ccategoryId').combobox('getValue');
            var policyYear = $('#cpolicyYear').val();
            var brandId = $('#cbrandId').combobox('getValue');
            var channelType = $('#cchannelType').combobox('getValue');
            var cchannelId = $('#cchannelId').combobox('getValue');

            if(brandId == '全部'){
                brandId='';
            }
            if(cchannelId == '全部'){
                cchannelId='';
            }
            if (categoryId == "全部") {
                categoryId = "";
            }

            var options = $('#gridView').datagrid('getPager').data("pagination").options;
            $.ajax({
                type: 'POST',
                async: true,
                url: '/pop/commission/commissionListF',
                data: {

                    page: options.pageNumber,
                    rows: options.pageSize,

                    'categoryId': categoryId,
                    'policyYear': policyYear,
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
                    if(data == ""){
                        $(".pagination-num").val("0");
                        }else{
                        $(".pagination-num").val("1");
                        }

                }
            });

        }
    });
});
//品类
$("#ccategoryId").combobox({
    url: '/pop/product/producttypesList',
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
    url: '/pop/product/producttypesList',
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
//品牌
$("#brandId").combobox({
    url: '/pop/product/brandList',
    valueField: "id",
    textField: "typeName",
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
$("#cpolicyYear").click(function () {
    $('#oDialog2').dialog('open');

});

$("#cpolicyYear").datepicker({
    language: "zh-CN",
    todayHighlight: true,
    format: 'yyyy',
    autoClose: true,
    startView: 'years',
    maxViewMode:'years',
    minViewMode:'years',
    orientation:'bottom',
    container :'#datetimepicker',
    pickerPosition:'top-right'

}).on('changeDate',function(ev){
    $('#oDialog2').dialog('close');
}).on('hide', function(e){
    
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
    maxViewMode:'years',
    minViewMode:'years',
    orientation:'bottom',
    container :'#datetimepicker1',
    pickerPosition:'top-right'

}).on('changeDate',function(ev){
    $('#oDialog3').dialog('close');
}).on('hide', function(e){
    console.log(e+"1");
    $("#policyYear").datepicker('show');
});

//修改
function list() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt("请选择要修改的单据");
        return;
    } else {
        // $('#policyYear').val(rows1[0].policyYear);
        $('#policyYear').val( rows1[0].policyYear);
        $("#categoryId").combobox("setValue", rows1[0].categoryId);
        $("#yearTargetSymbol").combobox("setValue", rows1[0].yearTargetSymbol);
        $('#yearTargetValue').numberbox('setValue', rows1[0].yearTargetValue);
        $('#monthPolicy').numberbox('setValue', rows1[0].monthPolicy);
        $('#quarterStandardReward').numberbox('setValue', rows1[0].quarterStandardReward);
        $('#yearStandardReward').numberbox('setValue', rows1[0].yearStandardReward);
        $("#id").val(rows1[0].id);
        $("#categoryName").val(rows1[0].categoryName);
        $('#brandId').combobox('setValue', rows1[0].brandId);
        $("#channelType").combobox("setValue", rows1[0].channelType);
        if( rows1[0].channelType == 1){
            channelsInfoList("channelId");
        }
        else{
            channels("channelId");
        }
        $('#channelId').combobox('setValue', rows1[0].channelId);
        $('#oDialog4').dialog('open');

    }
}

//新增
function Add() {
    // $('#policyYear').val( near);
    $("#policyYear").val( near);
    $("#categoryId").combobox("setValue", "");
    $("#yearTargetSymbol").combobox("setValue", "");
    $("#brandId").combobox("setValue", "");
    $("#channelId").combobox("setValue", "");
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
    var policyYear = $('#policyYear').val();
    var categoryId1 = $("#categoryId").textbox('getValue');
    var yearTargetSymbol = $("#yearTargetSymbol").combobox('getText');
    var yearTargetValue = $("#yearTargetValue").numberbox('getValue');
    var monthPolicy = $("#monthPolicy").numberbox('getValue');
    var yearStandardReward = $("#yearStandardReward").numberbox('getValue');
    var categoryName = $("#categoryId").combobox('getText');
    var quarterStandardReward = $("#quarterStandardReward").numberbox('getValue');
    var brandId= $('#brandId').combobox('getValue');
    var channelType= $("#channelType").combobox('getValue');
    var channelId= $('#channelId').combobox('getValue');
    var url = '/pop/commission/updatecommission';
    if (policyYear.length == 0) {
        autoAlt("政策年度不能为空");
        return false;
    }
    if (yearTargetSymbol.length == 0) {
        autoAlt("年度目标符号不能为空");
        return false;
    }
    if (brandId.length == 0) {
        autoAlt("品牌不能为空");
        return false;
    }
    if (channelId.length == 0) {
        autoAlt("渠道不能为空");
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
            quarterStandardReward: quarterStandardReward,
            channelType: channelType,
            channelId: channelId,
            brandId: brandId,


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
//触发主表单击行获取子表信息
//    $("#gridView").datagrid({
//        onClickRow: function (index, row) {
//            GetOptionDetail(row.id);
//            gloid = row.id;
//        }
//    });


//主表查询
function SearchUnit() {
    var categoryId = $('#ccategoryId').combobox('getValue');
    var policyYear = $('#cpolicyYear').val();
    var brandId = $('#cbrandId').combobox('getValue');
    var channelType = $('#cchannelType').combobox('getValue');
    var cchannelId = $('#cchannelId').combobox('getValue');

    if(brandId == '全部'){
        brandId='';
    }
    if(cchannelId == '全部'){
        cchannelId='';
    }
    if (categoryId == "全部") {
        categoryId = "";
    }


    var options = $('#gridView').datagrid('getPager').data("pagination").options;
    options.pageNumber = 1;
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/commission/commissionListF',
        data: {

            page: options.pageNumber,
            rows: options.pageSize,

            'categoryId': categoryId,
            'policyYear': policyYear,
            'brandId':brandId,
            'channelType':channelType,
            'channelId':cchannelId


        },
        success: function (data, textStatus) {
        	$('#gridView').datagrid('getPager').pagination('refresh');
            $('#gridView').datagrid('loadData', data);
        },
        error: function (e) {
            $.messager.alert(e);
        }
    });

}
