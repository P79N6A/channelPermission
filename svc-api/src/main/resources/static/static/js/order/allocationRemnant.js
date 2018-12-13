var datagridOptions_orderForecastGoal = {
    fit: true,//自适应
    // fitColumns: true,//fitColumns: true,
    singleSelect: true,//多选
    url: '',
    columns: [[
        {title: '调拨单号 ', field: 'db', sortable: false,   formatter: function(value,row,index){
                return '<a href="../exception/allocateLog?id='+row.id+'">'+row.db+'</a>';
            }},
        {title: '记账凭证号', field: 'materialCertification', sortable: false},
        {title: '机编码', field: 'snCode', sortable: false},
        {title: '物流单号', field: 'purchaseOrderCode', sortable: false},
        {title: '来源单号', field: 'sourceSn', sortable: false},
        {title: '库位', field: 'storageLocation', sortable: false, align: 'right'},
        {title: '物料编码', field: 'goodsCode', sortable: false,},
        {title: '数量', field: 'billCnt', sortable: false, },
        {title: 'LBX单号', field: 'orderCode', sortable: false},
        {title: '状态', field: 'status', sortable: false}
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
};

$(function () {
    var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);

    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
    });
});


$("#searchBtn_orderForecastGoal").click(function(){
    SearchUnit();
})
function SearchUnit() {
    var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);
var db = $("#db").val();
var orderCode = $("#orderCode").val();
var purchaseOrderCode = $("#purchaseOrderCode").val();
var goodsCode = $("#goodsCode").val();
var snCode = $("#snCode").val();

    datagrid = $('#datagrid_orderForecastGoal').datagrid({
        url: "/exception/findAllocationRemnant",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50,100,200],
        queryParams:{
            db:db ,
            orderCode:orderCode,
            purchaseOrderCode:purchaseOrderCode,
            goodsCode:goodsCode,
            snCode:snCode,
        },
        columns: [[
            {title: '调拨单号 ', field: 'db', sortable: false,formatter: function(value,row,index){
                    return '<a href="javascript:void(0);" onclick="jumpOrderList(\''+row.id+'\')">'+row.db+'</a>';
                }},
            {title: '记账凭证号', field: 'materialCertification', sortable: false},
            {title: '机编码', field: 'snCode', sortable: false},
            {title: '物流单号', field: 'purchaseOrderCode', sortable: false},
            {title: '来源单号', field: 'sourceSn', sortable: false},
            {title: '库位', field: 'storageLocation', sortable: false, align: 'right'},
            {title: '物料编码', field: 'goodsCode', sortable: false,},
            {title: '数量', field: 'billCnt', sortable: false, },
            {title: 'LBX单号', field: 'orderCode', sortable: false},
            {title: '状态', field: 'status', sortable: false,
                formatter: function(value,row,index){
                    var isrece="";
                    if(row.outSapStatus==1){
                        isrece="受理完成";
                    }
                    else if(row.inSapStatus==1){
                        isrece="入库完成";
                    }
                    else if(row.vomStatus==1){
                        isrece="推送VOM成功";
                    }
                    else if(row.orderCode != null && row.orderCode != ""){
                        isrece="下退仓成功";
                    }
                    else if(row.status==1){
                        isrece="推送HP成功";
                    }
                    return isrece;
                }
            }

        ]],
    });
}

function jumpOrderList(orderSn){
	window.top.addTab("调拨残次详情", "/exception/allocateLog?id="+orderSn, true);
}