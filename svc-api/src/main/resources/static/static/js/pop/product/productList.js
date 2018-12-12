/**
 * Pop模块可售商品
 * 孙玉凯
 * Created by PC011 on 2017/11/10.
 */


var gloid;
/***
 * 自动加载
 */

$('#datagrid_orderForecastGoal').datagrid({
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

        }, {
            title: '渠道',
            width: 50,
            field: 'channelName',
            align: 'left'
            , sortable: true
        }, {
            title: '品类',
            width: 50,
            field: 'productTypeName',
            align: 'left'
            , sortable: true
        }, {
            title: '物料编码',
            width: 130,
            field: 'sku',
            align: 'left'
            , sortable: true
        },
        {
            title: '商品编码',
            width: 130,
            field: 'productCode',
            align: 'left'
            , sortable: true
        },
        {
            title: '产品组',
            width: 90,
            field: 'departmentName',
            align: 'left'
            , sortable: true
        }
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    onClickRow: function (rowIndex, row) {
        GetOptionDetail(row.id);
        gloid = row.id;
    }
});
//表格and分页js加载
$('#datagrid_orderForecastGoal').datagrid('getPager').pagination({
    total: 0,
    pageSize: 10,
    pageList: [10, 20, 30, 40, 50],
    showRefresh: false,
    displayMsg: '显示 {from} - {to} 条记录   共 {total} 条记录',
    onSelectPage: function (pageNo, pageSize) {

        var start = (pageNo - 1) * pageSize;//页码分页自增
        var productTypeId1 = $('#productTypeId').combobox('getValue');
        var channelid1 = $('#channelId').combobox('getValue');
        var sku1 = $('#sku').val();
        if (productTypeId1 == "全部") {
            productTypeId1 = "";
        }
        if (channelid1 == "全部") {
            channelid1 = "";
        }
        if (sku1 == "全部") {
            sku1 = "";
        } else {
            sku1 = "%" + sku1 + "%";
        }
        var options = $('#datagrid_orderForecastGoal').datagrid('getPager').data("pagination").options;
        $.ajax({
            type: 'POST',
            async: true,
            url: '/pop/product/productListF',
            data: {
                page: options.pageNumber,
                rows: options.pageSize,
                'productTypeId': productTypeId1,
                'channelId': channelid1,
                'sku': sku1
            },
            success: function (data, textStatus) {
                $('#datagrid_orderForecastGoal').datagrid('loadData', data);

                //页码跟随分页进行-loadData后面执行
                var rowNumbers = $('.datagrid-cell-rownumber');
                $(rowNumbers).each(function (index) {
                    var row = parseInt($(rowNumbers[index]).html()) + parseInt(start);
                    $(rowNumbers[index]).html("");
                    $(rowNumbers[index]).html(row);
                });
//                

            }
        });

    }
});
//品类
$("#productTypeId").combobox({
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
//渠道
$("#channelId").combobox({
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




function list() {

    if (gloid == undefined) {
        $.messager.alert("提示：", "请选择要修改的单据");
        return;
    } else {

        self.location.href = "/pop/product/productListEdit?gloid='" + gloid + "'";
    }
}

//触发主表单击行获取子表信息
//    $("#datagrid_orderForecastGoal").datagrid({
//        onClickRow: function (index, row) {
//            GetOptionDetail(row.id);
//            gloid = row.id;
//        }
//    });

//根据主表sku物料编码来查询子表信息
function GetOptionDetail(id) {
    //$.messager.alert(id);
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/product/productDetailList',
        data: {
            'saleid': id
        },
        success: function (data, textStatus) {
            $('#gridView1').datagrid("loadData", data);
        },
        error: function (e) {
            $.messager.alert(e);
        }
    });
}
//主表查询
function SearchUnit() {
    var productTypeId1 = $('#productTypeId').combobox('getValue');
    //var productTypeId2 = $('#productTypeId').combobox('getText');
    var channelid1 = $('#channelId').combobox('getValue');
    var sku1 = $('#sku').val();
    if (productTypeId1 == "全部") {
        productTypeId1 = "";
    }
    if (channelid1 == "全部") {
        channelid1 = "";
    }
    if (sku1 == "全部") {
        sku1 = "";
    } else {
        sku1 = "%" + sku1 + "%";
    }

    var options = $('#datagrid_orderForecastGoal').datagrid('getPager').data("pagination").options;
    options.pageNumber = 1;
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/product/productListF',
        data: {

            page: options.pageNumber,
            rows: options.pageSize,

            'productTypeId': productTypeId1,
            'channelId': channelid1,
            'sku': sku1

        },
        success: function (data, textStatus) {
            $('#datagrid_orderForecastGoal').datagrid('loadData', data);
            if(data == ""){
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
