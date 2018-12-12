var cbs = {
    fit: true,//自适应
    singleSelect: true,//多选
    url: '',
    columns: [[
        {
            width : '10%',
            title : '物料编号',
            field : 'sku'
        },{
            width : '7%',
            title : '品类',
            field : 'cbsCategory'
        }, {
            width : '10%',
            title : '产品型号',
            field : 'productName'
        }, {
            width : '9%',
            title : 'LES库位编码',
            field : 'lesSecCode'
        },{
            width : '8%',
            title : '库位编码',
            field : 'secCode'
        },
        {
            width : '15%',
            title : '库位名称',
            field : 'secName'
        },{
            width : '7%',
            title : '实际库存',
            field : 'stockQty'
        },{
            width : '7%',
            title : '占用库存',
            field : 'frozenQty'
        },{
            width : '5%',
            title : '批次',
            field : 'itemProperty'
        },{
            width : '9%',
            title : '创建时间',
            field : 'createTime'
        }, {
            width : '9%',
            title : '更新时间',
            field : 'updateTime'
        },{
            width : '10%',
            title : '操作',
            field : '操作',
            formatter: function(value,row,index){
                var sku=row.sku;
                var lesSecCode=row.lesSecCode;

                return '<a href="#" onclick="queryLesStock(\'' + sku+ '\',\'' + lesSecCode+ '\')">LES库存查询</a> '
            }
        }
    ]],
    toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
}
$(function () {
    var datagrid = $('#cbs').datagrid(cbs);
    /*
    * 加载品类下拉列表
    * */
    $("#product_type_name").combobox({
        url: '/stock/getAllProductTypes',
        valueField: "value",
        textField: "text",
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
})
function onSelect() {
    datagrid = $('#cbs').datagrid({
        url: "/selectStock/getLesBaseStockList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: false,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        queryParams: {
            startDate : $("#startDate").datebox("getValue"),//更新开始时间
            endDate : $("#endDate").datebox("getValue"),//更新结束时间
            secCode : $("#secCode").val(),//库位编码
            sku : $("#sku").val(),//物料编码
            stockQty : $("#stockQty").val(),//实际库存大于
            avaiableQty : $("#avaiableQty").val(),//可用库存大于
            itemProperty :$("#itemProperty").combobox("getValue"),//商品属性
            cbsCategory :$("#product_type_name").combobox("getValue"),//品类
            productName : $("#productName").val(),//产品型号
        },
        columns: [[
            {
                width : '10%',
                title : '物料编号',
                field : 'sku'
            },{
                width : '7%',
                title : '品类',
                field : 'cbsCategory'
            }, {
                width : '10%',
                title : '产品型号',
                field : 'productName'
            }, {
                width : '8%',
                title : 'LES库位编码',
                field : 'lesSecCode'
            },{
                width : '7%',
                title : '库位编码',
                field : 'secCode'
            },
            {
                width : '15%',
                title : '库位名称',
                field : 'secName'
            },{
                width : '7%',
                title : '实际库存',
                field : 'stockQty'
            },{
                width : '7%',
                title : '占用库存',
                field : 'frozenQty'
            },{
                width : '5%',
                title : '批次',
                field : 'itemProperty'
            },{
                width : '9%',
                title : '创建时间',
                field : 'createTime'
            }, {
                width : '9%',
                title : '更新时间',
                field : 'updateTime'
            },{
                width : '10%',
                title : '操作',
                field : '操作',
                formatter: function(value,row,index){
                    var sku=row.sku;
                    var lesSecCode=row.lesSecCode;

                   return '<a href="#" onclick="queryLesStock(\'' + sku+ '\',\'' + lesSecCode+ '\')">LES库存查询</a> '
               }
            }
        ]],
    });
}
function queryLesStock(sku,lesSecCode) {
    $.messager.progress({
        title: '请稍后',
        msg: '正在处理，请稍后...'
    });
    jQuery.ajax({
        url: "/selectStock/getLesStockList.html",
        type: "get",
        data: {"sku": sku, "lesSecCode": lesSecCode},
        success: function (data) {
            $.messager.progress('close');
            $("#LesStockId").show();
            $("#stocklist").html(data);
            $("#LesStockId").dialog({
                collapsible: true,
                minimizable: false,
                maximizable: false,
                buttons: [{
                    text: '取消',
                    handler: function () {
                        $('#LesStockId').dialog('close');
                    }
                }]
            });
        }
    });
}
function onExport(){
    $('#paramForm_dmmtlPbcsMtlMeasure').submit();
}
function slideUpSlideClk(t) {
    var $this = $(t);
    if ($this.hasClass('open')) {
        $this.removeClass('open');
        setTimeout(function () {
            $this.addClass('close');
        }, 0);
        $(".search-panel").slideUp();
    } else {
        $this.removeClass('close');
        setTimeout(function () {
            $this.addClass('open');
        }, 0);
        $(".search-panel").slideDown();
    }
    setTimeout(function () {
        $('#cbs').datagrid('resize');
    }, 500);
}