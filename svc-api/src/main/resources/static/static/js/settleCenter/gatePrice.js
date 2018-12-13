var datagrid;
var queryParameters;
var LEVEL1_QJ={};
$(function () {
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            {title: "序号", field: "id", hidden: true},
            {title: "复选框", field: "checked", hidden: true},
            {title: "品牌", field: "brand", sortable: false},
            {title: "品类", field: "cateGory",sortable: false},
            {title: "sku", field: "sku", sortable: false},
            {title: "型号", field: "version", sortable: false},
            {title: "物流模式", field: "logisticsModel", sortable: false},
            {title: "大渠道", field: "bigChannel",sortable: false},
            {title: "采购价", field: "purPrice", sortable: false},
            {title: "价值链比率", field: "channelRate", sortable: false},
            {title: "冻结状态", field: "frozenFlag", sortable: false},
            {title: "闸口价", field: "gatePrice", sortable: false},
            {title: "直扣", field: "cut", sortable: false},
            {title: "后返", field: "back", sortable: false},
            {title: "开始时间", field: "beginTime", sortable: false},
            {title: "结束时间", field: "endTime", sortable: false},
            {title: "是否有效", field: "isValid", sortable: false},
            {title: "裸价", field: "barePrice", sortable: false},
            {title: "是否小于裸价", field: "isBigBarePrice", sortable: false},
            {title: "临时闸口价", field: "tempGatePrice", sortable: false},
            {title: "临时闸口价开始时间", field: "tempBeginTime", sortable: false},
            {title: "临时闸口价结束时间", field: "tempEndTime", sortable: false},
            {title: "标准毛利", field: "normalGrossprofit", sortable: false},
            {title: "实际毛利", field: "actualGrossprofit", sortable: false},
            {title: "下市状态", field: "lowerStatus", sortable: false},
            {title: "审核状态", field: "auditStatus", sortable: false},
            {title: "审核人", field: "auditBy", sortable: false},
            {title: "审核时间", field: "auditTime", sortable: false},
            {title: "执行天数", field: "execDays", sortable: false},
            {title: "创建人", field: "createBy", sortable: false},
            {title: "修改人", field: "updateBy", sortable: false}
        ]],
        pageSize: 50,
        pageList: [50,100,200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);

    $("#editBtn").on('click', function () {
        var selected = $('#datagrid').datagrid('getSelections');
        $('#userEdit').dialog({'title': '修改闸口价'});
        if (selected.length == 1) {
            $("#fm").form("clear");
            $('#fm').form('load', selected[0]);
            $('#fm').find('[__actType]').val('edit');
            $('#userEdit').dialog('open');
        } else {
            alert("请选择一条数据");
        }
    }).error(function (errorObj, statusText) {
        alert(statusText);
    });

    $("#save").on('click', function () {
        var data = $("#fm").serializeArray();
        if (!$("#gatePrice").textbox('getValue').trim()){
            $.messager.alert("提示信息", "请填写闸口价！");
            return
        }
        var url = "/gatePrice/updateGatePrice"
        $.ajax({
            url: url,
            data: data,
            dataType: 'json',
            type: "post",
            success: function (res) {
                if (res.success) {
                    alert("操作成功")
                } else {
                    var obj = eval(res);
                    var defut=(obj.message);
                    alert(defut);
                }
                $('#datagrid').datagrid("load")
                $('#userEdit').dialog('close')
            }
        })
    })

    $("#lowerBtn").click(function () {
        var rows = $('#datagrid').datagrid('getSelections')
        if (rows.length > 1) {
            $.messager.alert('提示信息', '请选择一条数据');
        }else if(rows.length == 0){
            $.messager.alert('提示信息', '请选择数据');
        } else {
            var tempGatePrice=(rows[0].tempGatePrice);
            if(rows[0].id){
                var id=rows[0].id;
                $.messager.confirm("确认", "确认要操作吗?", function (r) {
                    if (r) {
                        $.ajax({
                            url: '/gatePrice/updateGatePrice',
                            data: {id: id, lowerStatus: 'Y',gatePrice:'0.01',tempGatePrice:tempGatePrice},
                            type: 'post',
                            dataType: 'json',
                            success: function (res) {
                                if (res.success) {
                                    $.messager.alert('提示信息', res.message)
                                    $('#datagrid').datagrid('load')
                                } else {
                                    $.messager.alert('提示信息', res.message)
                                }
                            }
                        })
                    }
                })
            }else{
                $.messager.alert('提示信息', '请选择数据');
            }
        }
    })

    $("#importBtn").click(function () {
        $('#importExcel').dialog('open');
    });


    $("#upload").click(function () {
        if (!$("#file").val()) {
            $.messager.alert('提示信息', '请选择文件');
            return;
        }
        $("#upload").addClass("l-btn-disabled");
        $.ajaxFileUpload({
            url: '/gatePrice/import',
            secureuri: false,
            fileElementId: 'file',
            dataType: 'json',
            success: function (data) {
                $("#upload").removeClass(
                    "l-btn-disabled")
                if (data.success) {
                    $.messager.alert('提示信息', '上传成功!')
                    $('#importExcel').dialog('close');
                    dataGrid.datagrid('load')
                } else {
                    var msg = "";
                    $.messager.alert('提示信息', '上传失败!<br/>' + data.message)
                }
            },
            error: function (a, b, c, d) {
                $("#upload").removeClass("l-btn-disabled")
                $.messager.alert('提示信息', '上传失败!' + a.responseText)
            }
        })
    })
});
//获取品牌
$(function() {
    jQuery.getJSON("/gatePrice/queryBrands", function (result) {
        var JosnList = [];
        var cbsBrandsJson = {id: "", text: "全部"};
        JosnList.push(cbsBrandsJson)
        jQuery.each(result.data, function (i, v) {
            var item = {id: v, text: v}
            JosnList.push(item)
        });
        $("#brand").combobox({
            data: JosnList,
            valueField: 'id',
            textField: 'text',
            panelHeight: '300',
            editable: false,
            value: ''
        });
    });

});
//获取品牌
$(function() {

    jQuery.getJSON("/gatePrice/queryCategory", function (result) {
        var JosnList = [];
        var cbsCategoryJson = {id: "", text: "全部"};
        JosnList.push(cbsCategoryJson)
        jQuery.each(result.data, function (i, v) {
            var item = {id: v, text: v}
            JosnList.push(item)
        });
        $("#cateGory").combobox({
            data: JosnList,
            valueField: 'id',
            textField: 'text',
            panelHeight: '300',
            editable: false,
            value: ''
        });
    });
});

//点击查询
$('#searchBtn').click(function () {
    queryParameters ={
        brand:$("#brand").combobox("getValue"),
        cateGory:$("#cateGory").combobox("getValue"),
        sku:$('#sku').val(),
        bigChannel:$("#bigChannel").combobox("getValue"),
        isValid:$("#isValid").combobox("getValue"),
        beginTime:$("#beginTime").datetimebox('getValue'),
        endTime:$("#endTime").datetimebox('getValue'),
        frozenFlag:$("#frozenFlag").combobox("getValue"),
        auditStatus:$("#auditStatus").combobox("getValue"),
        isBigBarePrice:$("#isBigBarePrice").combobox("getValue"),
        isSmallNormal:$("#isSmallNormal").combobox("getValue"),
        isLower:$('#isLower').combobox('getValue'),
        execDaysFrom:$('#execDaysFrom').val(),
        execDaysTo:$('#execDaysTo').val()
    };
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/gatePrice/queryPage",
        fit: true,
        fitColumns : false,
        pagination: true,
        singleSelect: false,
        checkOnSelect:true,
        pageSize: 50,
        pageList: [50, 100, 200 ],
        nowrap : false,
        rownumbers : true,
        queryParams: queryParameters,
        columns: [
            [
                {
                    field: 'id',
                    title: '全选',
                    align: 'center',
                    checkbox: true
                },
                {
                    field:'brand',
                    title:'品牌',
                    align: 'center',
                    width:104
                },
                {
                    field:'cateGory',
                    title:'品类',
                    align: 'center',
                    width:59
                },
                {
                    field:'sku',
                    title:'sku',
                    align: 'center',
                    width:112
                },
                {
                    field:'version',
                    title:'型号',
                    align: 'center',
                    width:144
                },
                {
                    field: 'logisticsModel',
                    title:'物流模式',
                    align: 'center',
                    width:104
                },
                {
                    field: 'bigChannel',
                    title:'大渠道',
                    align: 'center',
                    width:104
                },
                {
                    field: 'purPrice',
                    title:'采购价',
                    align: 'center',
                    width:104
                },
                {
                    field: 'channelRate',
                    title:'价值链比率',
                    align: 'center',
                    width:104
                },{
                field: 'frozenFlag',
                title:'冻结状态',
                align: 'center',
                width:104,
                formatter: function (value) {
                    if (value == 'X') {
                        return "冻结";
                    }
                    if (value == 'N') {
                        return "未冻结";
                    }
                }
            },{
                field: 'gatePrice',
                title:'闸口价',
                align: 'center',
                width:75
            },{
                field: 'cut',
                title:'直扣',
                align: 'center',
                width:60
            },{
                field: 'back',
                title:'后返',
                align: 'center',
                width:60
            },{
                field: 'beginTime',
                title:'开始时间',
                align: 'center',
                width:110
            },{
                field: 'endTime',
                title:'结束时间',
                align: 'center',
                width:110
            },{
                field: 'isValid',
                title:'是否有效',
                align: 'center',
                width:87
            },{
                field: 'barePrice',
                title:'裸价',
                align: 'center',
                width:75
            },{
                field: 'isBigBarePrice',
                title:'是否小于裸价',
                align: 'center',
                width:100
            },{
                field: 'tempGatePrice',
                title:'临时闸口价',
                align: 'center',
                width:100
            },{
                field: 'tempBeginTime',
                title:'临时闸口价开始时间',
                align: 'center',
                width:110
            },{
                field: 'tempEndTime',
                title:'临时闸口价结束时间',
                align: 'center',
                width:110
            },{
                field: 'normalGrossprofit',
                title:'标准毛利',
                align: 'center',
                width:100
            },{
                field: 'actualGrossprofit',
                title:'实际毛利',
                align: 'center',
                width:100
            },{
                field: 'lowerStatus',
                title:'下市状态',
                align: 'center',
                width:100,
                formatter: function (value) {
                    if (value == 'Y') {
                        return "下市";
                    }else {
                        return "上市";
                    }
                }
            },{
                field: 'auditStatus',
                title:'审核状态',
                align: 'center',
                width:100,
                formatter: function (value) {
                    if (value == 'W') {
                        return "待审核";
                    }
                    if (value == 'S') {
                        return "通过";
                    }
                    if (value == 'F') {
                        return "拒绝";
                    }
                }
            },{
                field: 'auditBy',
                title:'审核人',
                align: 'center',
                width:100
            },{
                field: 'auditTime',
                title:'审核时间',
                align: 'center',
                width:110
            },{
                field: 'execDays',
                title:'执行天数',
                align: 'center',
                width:100
            },{
                field: 'createBy',
                title:'创建人',
                align: 'center',
                width:100
            },{
                field: 'updateBy',
                title:'修改人',
                align: 'center',
                width:100
            }
            ]
        ],
        toolbar: '#datagridToolbar'
    });
});


//点击导出
$('#export').click(function(){
    var total= $("#datagrid").datagrid("getData").total;
    if(total<=0){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid').datagrid('getChecked');
    var syncData = new Array();
    //判断是否存在满足条件的行
    $.each(checkedItems, function(index, item){
        syncData.push(item.id);
    });
    $.messager.confirm('确认','确定要导出吗？', function(r){
        if (r){
            $('#exportData').val(JSON.stringify(queryParameters));
            $('#ids').val(JSON.stringify(syncData));
            $('#paramForm').attr("action", '/gatePrice/exportGatePriceList');
            $('#paramForm').submit();
        }
    });
});
