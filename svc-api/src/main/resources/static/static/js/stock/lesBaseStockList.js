var les = {
    fit: true,//自适应
    singleSelect: true,//多选
    url: '',
    columns: [[
        {
            width : '7%',
            title : '库存地点',
            field : 'LGORT1'
        },{
            width : '7%',
            title : '库存地点',
            field : 'LGORT'
        }, {
            width : '7%',
            title : '物料号码',
            field : 'MATNR'
        }, {
            width : '8%',
            title : '物料号码(新)',
            field : 'MATNR_NEW'
        },{
            width : '7%',
            title : '批次编号',
            field : 'ATWART'
        },
        {
            width : '7%',
            title : '实物库存',
            field : 'CLABS'
        },{
            width : '7%',
            title : '在运库存',
            field : 'CUMLM'
        },{
            width : '8%',
            title : '基本计量单位',
            field : 'MEINS'
        },{
            width : '7%',
            title : '数量',
            field : 'ZMENGE1'
        },{
            width : '7%',
            title : '单位',
            field : 'VRKME'
        },{
            width : '7%',
            title : '开票未提',
            field : 'MENGE1',
        },{
            width : '7%',
            title : '可用库存数',
            field : 'MENGE2'
        },{
            width : '7%',
            title : '物料组',
            field : 'MATKL'
        },{
            width : '9%',
            title : '实物库存体积',
            field : 'VOLUM'
        },{
            width : '7%',
            title : '单位',
            field : 'ZVOLEH'
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
    var datagrid = $('#les').datagrid(les);
});
function onSelect() {
    var sku=$("#sku").val();
    var lesSecCode=$("#lesSecCode").val();
    if($.trim(sku)==""|| $.trim(sku)==null || $.trim(sku)=="undefined"){
        alert("物料编码和LES库存编码为必填项！");
        return;
    }
    if($.trim(lesSecCode)==""|| $.trim(lesSecCode)==null || $.trim(lesSecCode)=="undefined"){
        alert("物料编码和LES库存编码为必填项！");
        return;
    }
    datagrid = $('#les').datagrid({
        url: "/selectStock/getLesStockList1",
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
            sku : $("#sku").val(),//物料编码
            lesSecCode : $("#lesSecCode").val(),//实际库存大于
        },
        columns: [[
            {
                width : '7%',
                title : '库存地点',
                field : 'LGORT1'
            },{
                width : '7%',
                title : '库存地点',
                field : 'LGORT'
            }, {
                width : '7%',
                title : '物料号码',
                field : 'MATNR'
            }, {
                width : '8%',
                title : '物料号码(新)',
                field : 'MATNR_NEW'
            },{
                width : '7%',
                title : '批次编号',
                field : 'ATWART'
            },
            {
                width : '7%',
                title : '实物库存',
                field : 'CLABS'
            },{
                width : '7%',
                title : '在运库存',
                field : 'CUMLM'
            },{
                width : '8%',
                title : '基本计量单位',
                field : 'MEINS'
            },{
                width : '7%',
                title : '数量',
                field : 'ZMENGE1'
            },{
                width : '7%',
                title : '单位',
                field : 'VRKME'
            },{
                width : '7%',
                title : '开票未提',
                field : 'MENGE1',
            },{
                width : '7%',
                title : '可用库存数',
                field : 'MENGE2'
            },{
                width : '7%',
                title : '物料组',
                field : 'MATKL'
            },{
                width : '9%',
                title : '实物库存体积',
                field : 'VOLUM'
            },{
                width : '7%',
                title : '单位',
                field : 'ZVOLEH'
            }
        ]],
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
        $('#les').datagrid('resize');
    }, 500);
}