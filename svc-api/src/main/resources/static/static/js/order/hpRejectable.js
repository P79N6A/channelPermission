/**
 * Created by zhangbo on 2017/11/4.
 */
var datagrid_WwwHpRecords=null;
/*
 * 匹配信息列表
 * */
function datagrid_WwwHpRecords1(){
    var options = datagrid_WwwHpRecords.datagrid('getPager').data("pagination").options;
    var Pagination={
        hpTbSn : $("#hpTbSn").val(),//tb单号
        cOrderSn :$("#cOrderSn").val().trim(),//网单号
        addTimeMin:$("#addTimeMin").datebox("getValue"),//下单时间 开始
        addTimeMax :$("#addTimeMax").datebox("getValue"),//下单时间 截止
        success :$("#success").combobox("getValue"),//标记
        page            : options.pageNumber,
        rows            : options.pageSize
    };
    $.ajax({
        url      : "/operationArea/datagrid_WwwHpRecords",
        type     : 'POST',
        dataType: 'json',
        contentType:"application/json",
        data     :JSON.stringify(Pagination),
        success  : function(data) {
            var row = {
                'data': {
                    'records':
                    data.obj,'totalCount':data.total
                }
            }
            datagrid_WwwHpRecords.datagrid('loadData', row);
            $("#datagrid_WwwHpRecords").datagrid('refreshRow');
        }
    })
}

$(function () {
    datagrid_WwwHpRecords = $("#datagrid_WwwHpRecords").datagrid({
        striped : true, // 隔行变色
        rownumbers : true,
        fit: true,
        checkbox:true,
        fitColumns : true,
        loadMsg:"正在努力加载中",
        idField : 'id',
        pagination : true,
        pagePosition:'bottom',
        frozenColumns : [ [ {
            field : 'chk',
            checkbox : true
        } ] ],
        columns : [ [
            {
                width : '20%',
                title : 'VOM退货单号',
                field : 'vomRepairSn'
            },{
                width : '15%',
                title : '网单号',
                field : 'cOrderSn'
            }, {
                width : '15%',
                title : 'TB单号',
                field : 'hpTbSn'
            }, {
                width : '15%',
                title : '匹配状态',
                field : 'success',
                formatter: function(value,row,index){
                    var isrece="";
                    if(row.success=='0'){
                        isrece="失败";
                    }
                    if (row.success=='1'){
                        isrece="成功";
                    }
                    if (row.success=='2'){
                        isrece="待匹配";
                    }
                    if (row.success=='3'){
                        isrece="带重新匹配";
                    }
                    return isrece ;
                }
            },{
                width : '20%',
                title : '备注',
                field : 'remark'
            },
            {
                width : '20%',
                title : '物料号',
                field : 'sku'
            },
            {
                width : '15%',
                title : '添加时间',
                field : 'addTime',
                formatter: function(value,row,index){
                    var addtime=row.addTime;
                    return new Date(parseInt(addtime)).toLocaleString().replace(/:\d{1,2}$/,' ');
                }
            },{
                width : '15%',
                title : '最后更新时间',
                field : 'modifytime',
                formatter: function(value,row,index){
                    var addtime=row.modifytime;
                    return new Date(parseInt(addtime)).toLocaleString().replace(/:\d{1,2}$/,' ');
                }
            }
        ] ],
        toolbar: '#datagridToolbar_orderForecastGoal'
    });
    datagrid_WwwHpRecords1();
    datagrid_WwwHpRecords.datagrid('getPager').pagination({
        pageSize: 50,
        pageList: [50,100,200],
        onSelectPage : function(pageNumber, pageSize) {
            datagrid_WwwHpRecords1();
        }
    });
});
/*
 * 点击搜索按钮执行的函数
 * */
$('#search').click(function() {
        var options = datagrid_WwwHpRecords.datagrid('getPager').data("pagination").options;
        options.pageNumber = 1;
        datagrid_WwwHpRecords.datagrid('getPager').pagination('refresh');
        datagrid_WwwHpRecords1()
    }
)
/*
 * 匹配异常
 * */
$('#exceptional_point').click(function() {
        //获取所选行数据
        var selRows = $('#datagrid_WwwHpRecords').datagrid('getChecked');
        if (selRows.length==0){
            alert("请至少选择一行数据进行匹配");
            return;
        }
        for(var i=0;i<selRows.length;i++){
            var hp=selRows[i];
            if(hp.success!=0){
                alert("请选择匹配状态失败的进行重置");
                return;
            }
        }
        $.ajax({
            url      : "/operationArea/exceptional_point",
            type     : 'POST',
            dataType: 'json',
            contentType:"application/json",
            data     :JSON.stringify(selRows),
            success  : function(data) {
                alert(data.msg);
                datagrid_WwwHpRecords.datagrid('clearSelections');
                $("#datagrid_WwwHpRecords").datagrid('clearSelections');
                var options = datagrid_WwwHpRecords.datagrid('getPager').data("pagination").options;
                var Pagination={
                    hpTbSn : $("#hpTbSn").val(),//tb单号
                    cOrderSn :$("#cOrderSn").val().trim(),//网单号
                    addTimeMin:$("#addTimeMin").datebox("getValue"),//下单时间 开始
                    addTimeMax :$("#addTimeMax").datebox("getValue"),//下单时间 截止
                    success :$("#success").combobox("getValue"),//标记
                    page            : options.pageNumber,
                    rows            : options.pageSize
                };
                $.ajax({
                    url      : "/operationArea/datagrid_WwwHpRecords",
                    type     : 'POST',
                    dataType: 'json',
                    contentType:"application/json",
                    data     :JSON.stringify(Pagination),
                    success  : function(data) {
                        var row = {
                            'data': {
                                'records':
                                data.obj,'totalCount':data.total
                            }
                        }
                        datagrid_WwwHpRecords.datagrid('loadData', row);
                        $("#datagrid_WwwHpRecords").datagrid('refreshRow');
                    }
                })
            }
        })
    }
)
/*
 * 导出报表
 * */
$('#export_Excel').click(function() {
        var Pagination={
            hpTbSn : $("#hpTbSn").val(),//tb单号
            cOrderSn :$("#cOrderSn").val().trim(),//网单号
            addTimeMin:$("#addTimeMin").datebox("getValue"),//下单时间 开始
            addTimeMax :$("#addTimeMax").datebox("getValue"),//下单时间 截止
            success :$("#success").combobox("getValue"),//标记
        };
        $.ajax({
            url      : "/operationArea/export_Excel",
            type     : 'POST',
            dataType: 'json',
            contentType:"application/json",
            data     :JSON.stringify(Pagination),
            success  : function(data) {
                alert(data.msg);
            }
        })
    }
)

