var datagridOptions_warehouseListGoal = {
    fit: true,//自适应
    fitColumns: true,//fitColumns: true,
    singleSelect: true,//多选
    url: '',
//    idField: 'id',
    frozenColumns: [[{
        field: 'id',
        checkbox: false,
        hidden:true
    }]],
    columns: [[
        {title: '仓库（TC）代码', field: 'whCode', sortable: true},
        {title: '仓库名称', field: 'whName', sortable: true},
        {
            title: '状态', field: 'status', sortable: true,
            formatter: function (val, rec) {
                if (val == "0") {
                    return "未启用";
                } else if (val == "1") {
                    return "已启用";
                }
            }
        },
        {title: '网单中心代码', field: 'centerCode', sortable: true},
        {
            title: '是否支持货到付款', field: 'supportCod', sortable: true,
            formatter: function (val, rec) {
                if (val == "0") {
                    return "否";
                } else if (val == "1") {
                    return "是";
                }
            }
        },
        {title: '该TC支持的物流模式', field: 'supportedDeliveryMode', sortable: true},
        {title: 'LES送达方代码', field: 'lesFiveYard', sortable: true},
        {title: 'LES库位编码', field: 'lesWhCode', sortable: true},
        {title: 'les_accepter', field: 'lesAccepter', sortable: true},
        {title: '日日顺配送中心编码', field: 'rrsDeliverCode', sortable: true},
        {title: 'CRM地区编码', field: 'crmAreaCode', sortable: true},
        {title: '电商付款方', field: 'ehaierPayer', sortable: true},
        {title: '工贸代码', field: 'itcCode', sortable: true},
        {title: '销售组织编码', field: 'moCode', sortable: true},
        {title: '电商库位码', field: 'estorgeId', sortable: true},
        {title: '电商库位名称', field: 'estorgeName', sortable: true},
        {title: '送达方代码', field: 'transmitId', sortable: true},
        {title: '送达方名称', field: 'transmitDesc', sortable: true},
        {title: 'OE码（LES）', field: 'lesOeId', sortable: true},
        {title: '管理客户编码', field: 'customId', sortable: true},
        {title: '管理客户名称', field: 'customDesc', sortable: true},
        {title: '工贸代码', field: 'industryTradeId', sortable: true},
        {title: '工贸描述', field: 'industryTradeDesc', sortable: true},
        {title: '颗粒度编码', field: 'graininessId', sortable: true},
        {title: '网单经营体编码', field: 'netManagementId', sortable: true},
        {title: '电商售达方编码', field: 'esaleId', sortable: true},
        {title: '电商售达方名称', field: 'esaleName', sortable: true},
        {title: '销售组织编码', field: 'saleOrgId', sortable: true},
        {title: '分支', field: 'branch', sortable: true},
        {title: '电商付款方编码', field: 'paymentId', sortable: true},
        {title: '电商付款方名称', field: 'paymentName', sortable: true},
        {title: '地区编码（CRM专用）', field: 'areaId', sortable: true},
        {title: '日日顺配送编码', field: 'rrsDistributionId', sortable: true},
        {title: '日日顺配送名称', field: 'rrsDistributionName', sortable: true},
        {title: '日日顺售达方', field: 'rrsSaleId', sortable: true},
        {title: '日日顺售达方名称', field: 'rrsSaleName', sortable: true},
        {title: 'OMS重庆新日日顺付款方', field: 'omsRrsPaymentId', sortable: true},
        {title: 'OMS重庆新日日顺付款方名称', field: 'omsRrsPaymentName', sortable: true},
        {title: '客户编码', field: 'sapCustomerCode', sortable: true},
        {title: '客户编码名称', field: 'sapCustomerName', sortable: true},
        {title: '创建者', field: 'createUser', sortable: true},
        {title: '创建时间', field: 'createTime', sortable: true, formatter: formatDatebox},
        {title: '最后更新人', field: 'updateUser', sortable: true},
        {title: '最后更新时间', field: 'updateTime', sortable: true, formatter: formatDatebox}
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
    var datagrid = $('#datagrid_warehouseListGoal').datagrid(datagridOptions_warehouseListGoal);

    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_invWarehouse').form('reset');
        $('#addForm_invWarehouse').find('[__actType]').val('add');
        $('#addDlg_invWarehouse').dialog({'title': '新增'});
        $('#addDlg_invWarehouse').dialog('open');
        $("#id").val("insert");
        $('#whCode').attr("readonly", false);
        $('#whCode').css("background-color", "#FFFFFF");

        $("#whName").textbox('textbox').attr('maxlength', 10);
        $("#centerCode").textbox('textbox').attr('maxlength', 10);
        $("#supportedDeliveryMode").textbox('textbox').attr('maxlength', 10);
        $("#lesFiveYard").textbox('textbox').attr('maxlength', 10);
        $("#lesWhCode").textbox('textbox').attr('maxlength', 10);
        $("#lesAccepter").textbox('textbox').attr('maxlength', 20);
        $("#rrsDeliverCode").textbox('textbox').attr('maxlength', 12);
        $("#crmAreaCode").textbox('textbox').attr('maxlength', 10);
        $("#ehaierPayer").textbox('textbox').attr('maxlength', 12);
        $("#itcCode").textbox('textbox').attr('maxlength', 8);
        $("#moCode").textbox('textbox').attr('maxlength', 6);
        $("#estorgeId").textbox('textbox').attr('maxlength', 4);
        $("#estorgeName").textbox('textbox').attr('maxlength', 200);
        $("#transmitId").textbox('textbox').attr('maxlength', 20);
        $("#transmitDesc").textbox('textbox').attr('maxlength', 2000);
        $("#lesOeId").textbox('textbox').attr('maxlength', 20);
        $("#customId").textbox('textbox').attr('maxlength', 20);
        $("#customDesc").textbox('textbox').attr('maxlength', 2000);
        $("#industryTradeId").textbox('textbox').attr('maxlength', 20);
        $("#industryTradeDesc").textbox('textbox').attr('maxlength', 2000);
        $("#graininessId").textbox('textbox').attr('maxlength', 20);
        $("#netManagementId").textbox('textbox').attr('maxlength', 20);
        $("#esaleId").textbox('textbox').attr('maxlength', 20);
        $("#esaleName").textbox('textbox').attr('maxlength', 2000);
        $("#saleOrgId").textbox('textbox').attr('maxlength', 20);
        $("#branch").textbox('textbox').attr('maxlength', 2000);
        $("#paymentId").textbox('textbox').attr('maxlength', 20);
        $("#paymentName").textbox('textbox').attr('maxlength', 2000);
        $("#areaId").textbox('textbox').attr('maxlength', 20);
        $("#rrsDistributionId").textbox('textbox').attr('maxlength', 20);
        $("#rrsDistributionName").textbox('textbox').attr('maxlength', 2000);
        $("#rrsSaleId").textbox('textbox').attr('maxlength', 20);
        $("#rrsSaleName").textbox('textbox').attr('maxlength', 2000);
        $("#omsRrsPaymentId").textbox('textbox').attr('maxlength', 20);
        $("#omsRrsPaymentName").textbox('textbox').attr('maxlength', 2000);
        $("#sapCustomerCode").textbox('textbox').attr('maxlength', 20);
        $("#sapCustomerName").textbox('textbox').attr('maxlength', 1000);

    });

    $("#addDlgSaveBtn_invWarehouse").on('click', function () {
        var status = $("#status").val();
        var whCode = $("#whCode").val();
        var supportCod = $("#supportCod").val();
        console.log("sup" + supportCod);
        if (whCode == null || whCode == "") {
            $.messager.alert('提示', '请填写仓库TC代码');
            return;
        }
        if (status == null || status == "") {
            $.messager.alert('提示', '请选择状态');
            return;
        }
        if (supportCod == null || supportCod == "") {
            $.messager.alert('提示', '请选择是否支持货到付款');
            return;
        }
        if (!$('#addForm_invWarehouse').form('validate')) {
            return;
        }
        $('#addForm_invWarehouse').form('submit', {
            url: '/invwarehouse/addinvWarehouse',
            onSubmit: function () {
                if ($("#addForm_invWarehouse").form("validate"))
                    return true
                else
                    return false;
            },
            success: function (data) {
                var actType = $('#addForm_invWarehouse').find('[__actType]').val();
                var obj = data;
                if (obj == "codeIsSame") {
                    $.messager.alert('提示', '仓库(TC)代码已存在');
                    return;
                }
                if (obj == "fail") {
                    $.messager.alert('提示', '操作失败');
                    return;
                }
                if (obj == "success") {
                    $('#datagrid_warehouseListGoal').datagrid('reload');
                    $("#addForm_invWarehouse").form("clear");
                    if (actType === 'add') {
//        				alert('新增成功');
                        $.messager.alert('提示', '新增成功');
                    } else {
                        $.messager.alert('提示', '修改成功');
                    }
                    $('#addDlg_invWarehouse').dialog('close');
                }
            }
        });
    });
    var sec_code;
    $("#editBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_invWarehouse').dialog({'title': '修改'});
        if (selected !== null) {
            sec_code = selected.whCode;
            $('#addForm_invWarehouse').form('load', selected);
            $('#addForm_invWarehouse').find('[__actType]').val('edit');
            $('#addDlg_invWarehouse').dialog('open');
        } else {
            $.messager.alert('提示', '请选择一条数据');
        }
        var actType = $('#addForm_invWarehouse').find('[__actType]').val();
        if (actType === 'edit') {
//        	$('#whCode').textbox('textbox').attr('disabled',true);
            $('#whCode').attr("readonly", "readonly");
            $("#whCode").css("background-color", "#D6D6FF");
        }
        $("#id").val(sec_code);

        $("#whCode").textbox('textbox').attr('maxlength', 2);
        $("#whName").textbox('textbox').attr('maxlength', 10);
        $("#centerCode").textbox('textbox').attr('maxlength', 10);
        $("#supportedDeliveryMode").textbox('textbox').attr('maxlength', 10);
        $("#lesFiveYard").textbox('textbox').attr('maxlength', 10);
        $("#lesWhCode").textbox('textbox').attr('maxlength', 10);
        $("#lesAccepter").textbox('textbox').attr('maxlength', 20);
        $("#rrsDeliverCode").textbox('textbox').attr('maxlength', 12);
        $("#crmAreaCode").textbox('textbox').attr('maxlength', 10);
        $("#ehaierPayer").textbox('textbox').attr('maxlength', 12);
        $("#itcCode").textbox('textbox').attr('maxlength', 8);
        $("#moCode").textbox('textbox').attr('maxlength', 6);
        $("#estorgeId").textbox('textbox').attr('maxlength', 4);
        $("#estorgeName").textbox('textbox').attr('maxlength', 200);
        $("#transmitId").textbox('textbox').attr('maxlength', 20);
        $("#transmitDesc").textbox('textbox').attr('maxlength', 2000);
        $("#lesOeId").textbox('textbox').attr('maxlength', 20);
        $("#customId").textbox('textbox').attr('maxlength', 20);
        $("#customDesc").textbox('textbox').attr('maxlength', 2000);
        $("#industryTradeId").textbox('textbox').attr('maxlength', 20);
        $("#industryTradeDesc").textbox('textbox').attr('maxlength', 2000);
        $("#graininessId").textbox('textbox').attr('maxlength', 20);
        $("#netManagementId").textbox('textbox').attr('maxlength', 20);
        $("#esaleId").textbox('textbox').attr('maxlength', 20);
        $("#esaleName").textbox('textbox').attr('maxlength', 2000);
        $("#saleOrgId").textbox('textbox').attr('maxlength', 20);
        $("#branch").textbox('textbox').attr('maxlength', 2000);
        $("#paymentId").textbox('textbox').attr('maxlength', 20);
        $("#paymentName").textbox('textbox').attr('maxlength', 2000);
        $("#areaId").textbox('textbox').attr('maxlength', 20);
        $("#rrsDistributionId").textbox('textbox').attr('maxlength', 20);
        $("#rrsDistributionName").textbox('textbox').attr('maxlength', 2000);
        $("#rrsSaleId").textbox('textbox').attr('maxlength', 20);
        $("#rrsSaleName").textbox('textbox').attr('maxlength', 2000);
        $("#omsRrsPaymentId").textbox('textbox').attr('maxlength', 20);
        $("#omsRrsPaymentName").textbox('textbox').attr('maxlength', 2000);
        $("#sapCustomerCode").textbox('textbox').attr('maxlength', 20);
        $("#sapCustomerName").textbox('textbox').attr('maxlength', 1000);
    });

    /**
     * 删除
     */
    $("#deleteBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
            var id = selected.whCode;
            console.log("2222" + id);
            confirm('确定删除？', function (r) {
                if (r == true) {
                    $.post("/invwarehouse/removeInvWarehouse", {id: id}, function (data) {
                        if (data.text = "success") {
                            $.messager.alert('提示', "删除成功");
                            $('#datagrid_warehouseListGoal').datagrid('reload');
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示', '请选择一条数据');
        }
    });

    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
    });
});

//点击查询
$("#searchBtn").on('click', function (event) {
    var whCodeData = $("#wh_code").textbox("getValue");
    var whNameData = $("#wh_name").textbox("getValue");
    var centerCodeData = $("#center_code").textbox("getValue");

    $("#e_wh_code").val(whCodeData);
    $("#e_wh_name").val(whNameData);
    $("#e_center_code").val(centerCodeData);
    $("#code").val("yes");

    //加载分页
    datagrid = $('#datagrid_warehouseListGoal').datagrid({
        url: "/invwarehouse/findInvWareHouseList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        queryParams: {
            whCode: whCodeData,
            whName: whNameData,
            centerCode: centerCodeData
        },
        frozenColumns: [[{
            field: 'id',
            checkbox: false,
            hidden:true
        }]],
        columns: [[
            {title: '仓库（TC）代码', field: 'whCode', sortable: true},
            {title: '仓库名称', field: 'whName', sortable: true},
            {
                title: '状态', field: 'status', sortable: true,
                formatter: function (val, rec) {
                    if (val == "0") {
                        return "未启用";
                    } else if (val == "1") {
                        return "已启用";
                    }
                }
            },
            {title: '网单中心代码', field: 'centerCode', sortable: true},
            {
                title: '是否支持货到付款', field: 'supportCod', sortable: true,
                formatter: function (val, rec) {
                    if (val == "0") {
                        return "否";
                    } else if (val == "1") {
                        return "是";
                    }
                }
            },
            {title: '该TC支持的物流模式', field: 'supportedDeliveryMode', sortable: true},
            {title: 'LES送达方代码', field: 'lesFiveYard', sortable: true},
            {title: 'LES库位编码', field: 'lesWhCode', sortable: true},
            {title: 'les_accepter', field: 'lesAccepter', sortable: true},
            {title: '日日顺配送中心编码', field: 'rrsDeliverCode', sortable: true},
            {title: 'CRM地区编码', field: 'crmAreaCode', sortable: true},
            {title: '电商付款方', field: 'ehaierPayer', sortable: true},
            {title: '工贸代码', field: 'itcCode', sortable: true},
            {title: '销售组织编码', field: 'moCode', sortable: true},
            {title: '电商库位码', field: 'estorgeId', sortable: true},
            {title: '电商库位名称', field: 'estorgeName', sortable: true},
            {title: '送达方代码', field: 'transmitId', sortable: true},
            {title: '送达方名称', field: 'transmitDesc', sortable: true},
            {title: 'OE码（LES）', field: 'lesOeId', sortable: true},
            {title: '管理客户编码', field: 'customId', sortable: true},
            {title: '管理客户名称', field: 'customDesc', sortable: true},
            {title: '工贸代码', field: 'industryTradeId', sortable: true},
            {title: '工贸描述', field: 'industryTradeDesc', sortable: true},
            {title: '颗粒度编码', field: 'graininessId', sortable: true},
            {title: '网单经营体编码', field: 'netManagementId', sortable: true},
            {title: '电商售达方编码', field: 'esaleId', sortable: true},
            {title: '电商售达方名称', field: 'esaleName', sortable: true},
            {title: '销售组织编码', field: 'saleOrgId', sortable: true},
            {title: '分支', field: 'branch', sortable: true},
            {title: '电商付款方编码', field: 'paymentId', sortable: true},
            {title: '电商付款方名称', field: 'paymentName', sortable: true},
            {title: '地区编码（CRM专用）', field: 'areaId', sortable: true},
            {title: '日日顺配送编码', field: 'rrsDistributionId', sortable: true},
            {title: '日日顺配送名称', field: 'rrsDistributionName', sortable: true},
            {title: '日日顺售达方', field: 'rrsSaleId', sortable: true},
            {title: '日日顺售达方名称', field: 'rrsSaleName', sortable: true},
            {title: 'OMS重庆新日日顺付款方', field: 'omsRrsPaymentId', sortable: true},
            {title: 'OMS重庆新日日顺付款方名称', field: 'omsRrsPaymentName', sortable: true},
            {title: '客户编码', field: 'sapCustomerCode', sortable: true},
            {title: '客户编码名称', field: 'sapCustomerName', sortable: true},
            {title: '创建者', field: 'createUser', sortable: true},
            {title: '创建时间', field: 'createTime', sortable: true, formatter: formatDatebox},
            {title: '最后更新人', field: 'updateUser', sortable: true},
            {title: '最后更新时间', field: 'updateTime', sortable: true, formatter: formatDatebox}
        ]],
    });

});

//点击导入
$('#importBtn').click(function () {
    var url = '/invwarehouse/importInvWarehouse';
    window.location.href = url;
    return false;
})
//点击导出
$("#exportBtn").click(function(){
    $("#exportForm").attr("action","/invwarehouse/exportInvWarehouseList");
    $("#exportForm").submit();
});
//日期格式化方法
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }

    return dt.format("yyyy-MM-dd hh:mm:ss"); // 扩展的Date的format方法(上述插件实现)
}
