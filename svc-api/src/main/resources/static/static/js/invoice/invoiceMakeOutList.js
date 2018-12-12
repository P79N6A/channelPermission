
var datagrid;
var queryParameters;

$(function () {
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_invoiceMakeOutList',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            {title: "序号", field: "id", hidden: true},
            {title: "复选框", field: "checked", hidden: true},
            {title: "网单号", field: "corderSn", sortable: true},
            {title: "网单类型", field: "corderType",sortable: true},
            {title: "物料编号", field: "sku", sortable: true},
            {title: "商品名称", field: "productName", sortable: true},
            {title: "商品分类", field: "productCateName", sortable: true},
            {title: "数量", field: "number",sortable: true},
            {title: "含税单价", field: "price", sortable: true},
            {title: "含税金额", field: "amount", sortable: true},
            {title: "纳税人识别号", field: "taxPayerNumber", sortable: true},
            {title: "发票号", field: "invoiceNumber", sortable: true},
            {title: "税控码", field: "fiscalCode", sortable: true},
            {title: "发票类型", field: "type", sortable: true},
            {title: "电子发票标志", field: "electricFlag", sortable: true},
            {title: "发票状态", field: "state", sortable: true},
            {title: "开票状态", field: "eaiWriteState", sortable: true},
            {title: "货票同行", field: "isTogether", sortable: true},
            {title: "开票时间", field: "billingTime", sortable: true},
            {title: "作废时间", field: "invalidTime", sortable: true},
            {title: "推送状态", field: "statusType", sortable: true},
            {title: "是否成功", field: "success", sortable: true},
            {title: "推送次数", field: "tryNum", sortable: true},
            {title: "实时开票信息查询", field: "operation", sortable: true}
        ]],
        pageSize: 50,
        pageList: [50,100,200],
        rownumbers: true
    }

    $('#datagrid_invoiceMakeOutList').datagrid(datagrid);
});

var buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
buttons.splice(1, 0, {
    text: '清除',
    handler: function(target){
        $(target).datetimebox('setValue', '');
        $(target).combo("hidePanel");
    }
});
$('#billing_time_start').datetimebox({ buttons: buttons});
$('#billing_time_end').datetimebox({ buttons: buttons});
$('#invalid_time_start').datetimebox({ buttons: buttons});
$('#invalid_time_end').datetimebox({ buttons: buttons});
$('#corder_add_time_start').datetimebox({ buttons: buttons});
$('#corder_add_time_end').datetimebox({ buttons: buttons});

//点击查询
$('#search').click(function () {
    if($('#billing_time_start').datetimebox('getValue') && $('#billing_time_end').datetimebox('getValue')){
        if($('#billing_time_start').datetimebox('getValue')>$('#billing_time_end').datetimebox('getValue')){
            $.messager.alert('错误','开票时间区间不正确','error');
            return false;
        }
    }
    if($('#invalid_time_start').datetimebox('getValue') && $('#invalid_time_end').datetimebox('getValue')){
        if($('#invalid_time_start').datetimebox('getValue')>$('#invalid_time_end').datetimebox('getValue')){
            $.messager.alert('错误','发票作废时间区间不正确','error');
            return false;
        }
    }
    if($('#corder_add_time_start').datetimebox('getValue') && $('#corder_add_time_end').datetimebox('getValue')){
        if($('#corder_add_time_start').datetimebox('getValue')>$('#corder_add_time_end').datetimebox('getValue')){
            $.messager.alert('错误','下单时间区间不正确','error');
            return false;
        }
    }
    queryParameters ={
        corder_sn:$("#corder_sn").val(),
        invoice_title:$("#invoice_title").val(),
        tax_payer_number:$('#tax_payer_number').val(),
        register_address_and_phone:$("#register_address_and_phone").val(),
        bank_name_and_account:$("#bank_name_and_account").val(),
        energy_saving:$("#energy_saving").combobox("getValue"),
        is_reinvoice:$("#is_reinvoice").combobox("getValue"),
        type:$("#type").combobox("getValue"),
        state:$("#state").combobox("getValue"),
        electric_flag:$("#electric_flag").combobox("getValue"),
        try_num:$("#try_num").val(),
        billing_time_start:$('#billing_time_start').datetimebox('getValue'),
        billing_time_end:$('#billing_time_end').datetimebox('getValue'),
        invalid_time_start:$('#invalid_time_start').datetimebox('getValue'),
        invalid_time_end:$('#invalid_time_end').datetimebox('getValue'),
        status_type:$("#status_type").combobox("getValue"),
        success:$("#success").combobox("getValue"),
        corder_add_time_start:$('#corder_add_time_start').datetimebox('getValue'),
        corder_add_time_end:$('#corder_add_time_end').datetimebox('getValue'),
        timeA:$("#timeA").combobox("getValue"),
        is_together:$("#is_together").combobox("getValue"),
        timeB:$("#timeB").combobox("getValue"),
        corder_type:$("#corder_type").combobox("getValue"),
        invoice_number:$("#invoice_number").val()
    };
        //生成grid
    datagrid = $('#datagrid_invoiceMakeOutList').datagrid({
        url: "/invoice/findInvoiceMakeOutList",
        fit: true,
        fitColumns: true,
        pagination: true,
        singleSelect: false,
        checkOnSelect:true,
        pageSize: 50,
        pageList: [50,100,200],
        nowrap: false,
        rownumbers: true,
        queryParams: queryParameters,
        columns: [
            [
                {
                    field: 'id',
                    title: '序号',
                    align: 'center',
                    hidden:true
                },
                {
                    field: 'checked',
                    align: 'center',
                    checkbox:true
                },
                {
                    field: 'corderSn',
                    title: '网单号',
                    sortable: true,
                    align: 'left'
                },
                {
                    field:'corderType',
                    title:'网单类型',
                    sortable: true,
                    align:'left',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='1') return '普通网单';
                        if(value=='2') return '差异网单';
                        if(value=='3') return '专项开票';
                        return value;
                    }
                },
                {
                    field: 'sku',
                    title: '物料编号',
                    sortable: true,
                    align: 'center'
                },
                {
                    field:'productName',
                    title:'商品名称',
                    sortable: true,
                    align:'center'
                },
                {
                    field:'productCateName',
                    title:'商品分类',
                    sortable: true,
                    align:'center'
                },
                {
                    field:'number',
                    title:'数量',
                    sortable: true,
                    align:'center'
                },
                {
                    field:'price',
                    title:'含税单价',
                    sortable: true,
                    align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return '￥'+value + '元';
                        }
                    }
                },
                {
                    field:'amount',
                    title:'含税金额',
                    sortable: true,
                    align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return '￥'+value + '元';
                        }
                    }
                },
                {
                    field:'taxPayerNumber',
                    title:'纳税人识别号',
                    sortable: true,
                    align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return '【'+value+'】';
                        }
                    }
                },
                {
                    field:'invoiceNumber',
                    title:'发票号',
                    sortable: true,
                    align:'center'
                },
                {
                    field: 'fiscalCode',
                    title: '税控码',
                    sortable: true,
                    align: 'center'
                },
                {
                    field: 'type',
                    title: '发票类型',
                    sortable: true,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='1') return '增值税发票';
                        if(value=='2') return '普通发票';
                        if(value=='3') return '增值税发票(普)';
                        return value;
                    }
                },
                {
                    field: 'electricFlag',
                    title: '电子发票标志',
                    sortable: true,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='1') return '电子发票';
                        if(value=='0') return '纸质发票';
                        return value;
                    }
                },
                {
                    field: 'state',
                    title: '发票状态',
                    sortable: true,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='0') return '待开票';
                        if(value=='1') return '开票中';
                        if(value=='4') return '已开票';
                        if(value=='5') return '已取消开票';
                        return value;
                    }
                },
                {
                    field:'eaiWriteState',
                    title:'开票状态',
                    sortable: true,
                    align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='') return '正常';
                        if(value=='3') return '当月作废';
                        if(value=='4') return '跨月冲红';
                        return value;
                    }
                },
                {
                    field: 'isTogether',
                    title: '货票同行',
                    sortable: true,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='1') return '货票同行';
                        if(value=='2') return '非货票同行';
                        return value;
                    }
                },
                {
                    field: 'firstPushTime',
                    title: '首次推送开票时间',
                    sortable: true,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return '【'+value+'】';
                        }
                    }
                },
                {
                    field: 'billingTime',
                    title: '开票时间',
                    sortable: true,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return '【'+value+'】';
                        }
                    }
                },
                {
                    field: 'invalidTime',
                    title: '作废时间',
                    sortable: true,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value == '' || value == null){
                            return '';
                        }else{
                            return '【'+value+'】';
                        }
                    }
                },
                {
                    field: 'statusType',
                    title: '推送状态',
                    sortable: true,
                    align: 'left',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='1') return '首次推送';
                        if(value=='2') return '推送修改';
                        if(value=='3') return '推送取消';
                        if(value=='4') return '推送作废';
                        return value;
                    }
                },
                {
                    field: 'success',
                    title: '是否成功',
                    sortable: true,
                    align: 'left',
                    formatter:function(value,rowData,rowIndex){
                        if(value=='0') return '待推送';
                        if(value=='1') return '成功';
                        return value;
                    }
                },
                {
                    field: 'tryNum',
                    title: '推送次数',
                    sortable: true,
                    align: 'left'
                },
                {
                    field: 'operation',
                    title: ' 实时开票信息查询',
                    sortable: true,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        //只有开票状态为待开票以外的行显示
                        if(rowData.state!=0){
                            return "<a href='javascript:void(0);' onclick='showInvoiceInfo("+'"'+rowData.corderSn+'","'+rowData.electricFlag+'"'+");return false;'>实时开票信息查询</a>";
                        }else{
                            return "";
                        }
                    }
                }
            ]
        ],
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
    });
    $('#datagrid_invoiceMakeOutList').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
});
//点击重推
$('#reSend').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid_invoiceMakeOutList').datagrid('getChecked');
    var reSendData = new Array();
    //判断是否存在未成功的行
    $.each(checkedItems, function(index, item){
        //状态为未成功的才能重推
        if(item.success==0){
            reSendData.push(item.id);
        }
    });
    //判断是否存在未成功的行
    if(reSendData==null||reSendData.length==0){
        //alert("请至少选择一行状态为未成功的行！");
        $.messager.alert('错误','请至少选择一行状态为未成功的行！','error');
        return;
    }
    $.messager.confirm('确认','确定要重推吗？（只重推未成功的行）', function(r){
        if (r){
            $.messager.progress({
                title:'请稍后',
                msg:'正在处理，请稍后...'
            });
            jQuery.ajax({
                url: "/invoice/reSendInvoices",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"reSendData": JSON.stringify(reSendData)},
                success: function (data) {
                    $.messager.progress('close');
                    if(!data.success) {
                        $.messager.alert('错误',data.message,'error');
                        return;
                    }else {
                        $.messager.alert('提示','总计：'+data.totalCount+'条，完成：'+data.data+'条，未完成：'+(data.totalCount-(data.data))+'条.<br/>请等待同步！','info');
                        $('#datagrid_invoiceMakeOutList').datagrid('reload', queryParameters);
                        $('#datagrid_invoiceMakeOutList').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                }
            });
        }
    });
});
//点击取消
$('#cancel').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid_invoiceMakeOutList').datagrid('getChecked');
    var cancelData = new Array();
    //判断是否状态行
    $.each(checkedItems, function(index, item){
        //	<?php if ($val['state'] == 1 && $val['statusType'] != 3 && $val['electricFlag'] == 0): ?>
        if(item.state==1&&item.statusType!=3&&item.electricFlag==0){
            cancelData.push(item.id);
        }
    });
    //判断
    if(cancelData==null||cancelData.length==0){
        //alert("请至少选择一行可以取消的行！");
        $.messager.alert('错误','请至少选择一行开票中且未推送取消的纸质发票！','error');
        return;
    }
    $.messager.confirm('确认','确定要取消吗？（只取消开票中且未推送取消的纸质发票）', function(r){
        if (r){
            $.messager.progress({
                title:'请稍后',
                msg:'正在处理，请稍后...'
            });
            jQuery.ajax({
                url: "/invoice/cancelInvoices",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"cancelData": JSON.stringify(cancelData)},
                success: function (data) {
                    $.messager.progress('close');
                    if(!data.success) {
                        $.messager.alert('错误',data.message,'error');
                        return;
                    }else {
                        $.messager.alert('提示','总计：'+data.totalCount+'条，完成：'+data.data+'条，未完成：'+(data.totalCount-(data.data))+'条.<br/>请等待同步！','info');
                        $('#datagrid_invoiceMakeOutList').datagrid('reload',queryParameters);
                        $('#datagrid_invoiceMakeOutList').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                }
            });
        }
    });
});
//点击作废
$('#forceCancel').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid_invoiceMakeOutList').datagrid('getChecked');
    var forceCancelData = new Array();
    //判断是否状态行
    $.each(checkedItems, function(index, item){
        //	<?php if ($val['state'] == 4 && empty($val['eaiWriteState']) && $val['statusType'] != 4): ?>
        if(item.state==4&&(item.eaiWriteState==undefined||item.eaiWriteState==null||item.eaiWriteState==''||item.eaiWriteState=='0')&&item.statusType!=4){
            forceCancelData.push(item.id);
        }
    });
    //判断
    if(forceCancelData==null||forceCancelData.length==0){
        //alert("请至少选择一行可以作废的行！");
        $.messager.alert('错误','请至少选择一行已开票且未推送作废、开票状态为正常的发票！','error');
        return;
    }
    $.messager.confirm('确认','确定要作废吗？（只作废已开票且未推送作废的发票）', function(r){
        if (r){
            $("#forceCancelDiv").show();
            $("#forceCancelDiv").dialog({
                collapsible: true,
                minimizable: false,
                maximizable: false,
                buttons: [{
                    text: '确定',
                    iconCls: 'icon-ok',
                    handler: function() {
                        $.messager.progress({
                            title:'请稍后',
                            msg:'正在处理，请稍后...'
                        });
                        jQuery.ajax({
                            url: "/invoice/invalidInvoice",   // 提交的页面
                            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                            data:{"forceCancelData": JSON.stringify(forceCancelData),"invalidReason":$("#invalidReason").combobox("getValue")},
                            success: function (data) {
                                $.messager.progress('close');
                                if(!data.success){
                                    $.messager.alert('错误',data.message,'error');
                                    return;
                                }else{
                                    $.messager.alert('提示','总计：'+data.totalCount+'条，完成：'+data.data+'条，未完成：'+(data.totalCount-(data.data))+'条.<br/>请等待同步！','info');
                                    $('#datagrid_invoiceMakeOutList').datagrid('reload', queryParameters);
                                    $('#datagrid_invoiceMakeOutList').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                                }
                            }
                        });
                        $('#forceCancelDiv').dialog('close');
                    }
                }, {
                    text: '取消',
                    handler: function() {
                        $('#forceCancelDiv').dialog('close');
                    }
                }]
            });
        }
    });
});
//点击同步发票状态
$('#syncstatus').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    //获得选中行
    var checkedItems = $('#datagrid_invoiceMakeOutList').datagrid('getChecked');
    var syncData = new Array();
    //判断是否存在满足条件的行
    $.each(checkedItems, function(index, item){
        //状态为待开票以外的行  电子发票也可以同步发票状态，去掉原来条件  && item.electricFlag==0
        if(item.state!=0){
            syncData.push(item.id);
        }
    });
    //判断是否存在非待开票的行
    if(syncData==null||syncData.length==0){
        //alert("请至少选择一行状态不是待开票的行！");
        $.messager.alert('错误','请至少选择一行发票且开票状态为待开票以外的行！','error');
        return;
    }
    $.messager.confirm('确认','确定要同步吗？（只同步待开票以外的行）', function(r){
        if (r){
            $.messager.progress({
                title:'请稍后',
                msg:'正在处理，请稍后...'
            });
            jQuery.ajax({
                url: "/invoice/syncStatusInvoices",   // 提交的页面
                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
                data:{"syncData": JSON.stringify(syncData)},
                success: function (data) {
                    $.messager.progress('close');
                    if(!data.success) {
                        $.messager.alert('错误',data.message,'error');
                    }else {
                        $.messager.alert('提示','总计：'+data.totalCount+'条，完成：'+data.data+'条，未完成：'+(data.totalCount-(data.data))+'条','info');
                        $('#datagrid_invoiceMakeOutList').datagrid('reload', queryParameters);
                        $('#datagrid_invoiceMakeOutList').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
                    }
                }
            });
        }
    });
});
//点击导出
$('#export').click(function(){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }

    //获得选中行
    var checkedItems = $('#datagrid_invoiceMakeOutList').datagrid('getChecked');
    var syncData = new Array();
    //判断是否存在满足条件的行
    $.each(checkedItems, function(index, item){
        syncData.push(item.id);
    });
    $.messager.confirm('确认','确定要导出吗？', function(r){
        if (r){
            $('#exportData').val(JSON.stringify(queryParameters));
            $('#ids').val(JSON.stringify(syncData));
            $('#paramForm_dmmtlPbcsMtlMeasure').attr("action", '/invoice/exportInvoiceMakeOutList');
            $('#paramForm_dmmtlPbcsMtlMeasure').submit();
        }
    });
});
//实时开票信息查询
function showInvoiceInfo(corderSn,electricFlag){
    console.info(corderSn+";"+electricFlag);
    if(electricFlag==0){
        $.messager.progress({
            title:'请稍后',
            text:'正在处理，请稍后...',
            interval:'100'
        });
        //金税实时开票数据
        jQuery.ajax({
            url: "/invoice/getTaxInvoicesInfo",   // 提交的页面
            type: "GET",
            data:{"corderSn": corderSn},
            success: function (data) {
                $.messager.progress('close');
                var result = data.data;
                if(!data.success){
                    $.messager.alert('提示',data.message,'info');
                    return;
                }
                $("#WDH").html(result.wdh);
                $("#FPZT").html(getNameFromCode("FPZT",result.fpzt));
                $("#FPLX").html(getNameFromCode("FPLX",result.fplx));
                $("#KPZT").html(getNameFromCode("KPZT",result.kpzt));
                $("#KPRQ").html(result.kprq);
                $("#HPTX").html(result.hptx);
                $("#WDRQ").html(result.wdrq);
                $("#KHBM").html(result.khbm);
                $("#KHMC").html(result.khmc);
                $("#KHSH").html(result.khsh);
                $("#KHDZ").html(result.khdz);
                $("#KHKHYHZH").html(result.khkhyhzh);
                $("#BZ").html(result.bz);
                $("#CPDM").html(result.cpdm);
                $("#CPMC").html(result.cpmc);
                $("#CPXH").html(result.cpxh);
                $("#CPDW").html(result.cpdw);
                $("#CPSL").html(result.cpsl);
                $("#HSDJ").html(result.hsdj);
                $("#HSJE").html(result.hsje);
                $("#BHSDJ").html(result.bhsdj);
                $("#BHSJE").html(result.bhsje);
                $("#JSJE").html(result.jsje);
                $("#JSSL").html(result.jssl);
                $("#JFJE").html(result.jfje);
                $("#JLJE").html(result.jlje);
                $("#JLBZ").html(result.jlbz);
                $("#SKFS").html(result.skfs);
                $("#LBJSDH").html(result.lbjsdh);
                $("#KWBM").html(result.kwbm);
                $("#ADD1").html(result.add1);
                $("#ADD2").html(result.add2);
                $("#RRRQ").html(result.rrrq);
                $("#GXRQ").html(result.gxrq);
                $("#FPHM").html(result.fphm);
                $("#SKRQ").html(result.skrq);
                $("#KPMAN").html(result.kpman);
                $("#ZFRQ").html(result.zfrq);
                //纸质发票
                $("#taxRecordInvoiceDiv").show();
                $("#taxRecordInvoiceDiv").dialog({
                    collapsible: true,
                    minimizable: false,
                    maximizable: false,
                    buttons: [ {
                        text: '取消',
                        handler: function() {
                            $('#taxRecordInvoiceDiv').dialog('close');
                        }
                    }]
                });
            }
        });
    }else{
        $.messager.progress({
            title:'请稍后',
            msg:'正在处理，请稍后...'
        });
        //电子发票实时开票数据
        jQuery.ajax({
            url: "/invoice/getElectricInvoiceInfo",   // 提交的页面
            type: "POST",
            data:{"corderSn": corderSn},
            success: function (data) {
                $.messager.progress('close');
                var result = data.data;
                if(!data.success){
                    $.messager.alert('提示',data.message,'info');
                    return;
                }
                $("#code").html(result.code);
                $("#status").html(getNameFromCode("FPZT_E",result.status));
                $("#totalAmount").html(result.totalAmount);
                $("#noTaxAmount").html(result.noTaxAmount);
                $("#taxAmount").html(result.taxAmount);
                $("#generateTime").html(result.generateTime);
                $("#validTime").html(result.validTime);
                $("#viewUrl").attr("href",result.viewUrl);
                $("#viewUrl").html(result.viewUrl);
                $("#fiscalCode").html(result.fiscalCode);
                //电子发票
                $("#electricInvoiceInfoDiv").show();
                $("#electricInvoiceInfoDiv").dialog({
                    collapsible: true,
                    minimizable: false,
                    maximizable: false,
                    buttons: [ {
                        text: '取消',
                        handler: function() {
                            $('#electricInvoiceInfoDiv').dialog('close');
                        }
                    }]
                });
            }
        });
    }
}

function getNameFromCode(flag, code){
    var name = code;
    if(flag == "FPZT"){//发票状态
        if(code == 0){
            name="待开票";
        }else if(code == 1){
            name="开票中";
        }else if(code == 4){
            name="已开票";
        }else if(code == 5){
            name="已取消开票";
        }
    }else if(flag == "FPLX"){//发票类型
        if(code == 1){
            name="增值税发票";
        }else if(code == 2){
            name="普通发票";
        }
    }else if(flag == "KPZT"){//金税开票状态
        if(code == ""){
            name="正常";
        }else if(code == 3){
            name="当月作废";
        }else if(code == 4){
            name="跨月冲红";
        }
    }else if(flag == "FPZT_E"){//电子发票状态
        if(code == 1){
            name="正常";
        }else if(code == 2){
            name="作废";
        }else if(code == 3){
            name="冲红";
        }else if(code == 4){
            name="被红冲";
        }
    }
    return name;
}