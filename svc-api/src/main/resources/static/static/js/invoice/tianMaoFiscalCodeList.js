
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
            {title: "网单号", field: "corderSn", sortable: false},
            {title: "网单类型", field: "corderType",sortable: false},
            {title: "物料编号", field: "sku", sortable: false},
            {title: "商品名称", field: "productName", sortable: false},
            {title: "商品分类", field: "productCateName", sortable: false},
            {title: "数量", field: "number",sortable: false},
            {title: "含税单价", field: "price", sortable: false},
            {title: "含税金额", field: "amount", sortable: false},
            {title: "纳税人识别号", field: "taxPayerNumber", sortable: false},
            {title: "税控码", field: "fiscalCode", sortable: false},
            {title: "发票号", field: "invoiceNumber", sortable: false},
            {title: "发票类型", field: "type", sortable: false},
            {title: "电子发票标志", field: "electricFlag", sortable: false},
            {title: "发票状态", field: "state", sortable: false},
            {title: "开票状态", field: "eaiWriteState", sortable: false},
            {title: "货票同行", field: "isTogether", sortable: false},
            {title: "开票时间", field: "billingTime", sortable: false},
            {title: "作废时间", field: "invalidTime", sortable: false},
            {title: "推送状态", field: "statusType", sortable: false},
            {title: "是否成功", field: "success", sortable: false},
            {title: "推送次数", field: "tryNum", sortable: false},
            {title: "实时开票信息查询", field: "operation", sortable: false}
        ]],
        pageSize: 50,
        pageList: [50,100,200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);
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

    if($('#corder_add_time_start').datetimebox('getValue') && $('#corder_add_time_end').datetimebox('getValue')){
        if($('#corder_add_time_start').datetimebox('getValue')>$('#corder_add_time_end').datetimebox('getValue')){
            $.messager.alert('错误','下单时间区间不正确','error');
            return false;
        }
    }
    queryParameters ={
        corder_sn:$("#corder_sn").val(),
        sourceOrderSn:$("#sourceOrderSn").val(),
        invoice_title:$("#invoice_title").val(),
        tax_payer_number:$('#tax_payer_number').val(),
        type:$("#type").combobox("getValue"),
        state:$("#state").combobox("getValue"),
        billing_time_start:$('#billing_time_start').datetimebox('getValue'),
        billing_time_end:$('#billing_time_end').datetimebox('getValue'),
        corder_add_time_start:$('#corder_add_time_start').datetimebox('getValue'),
        corder_add_time_end:$('#corder_add_time_end').datetimebox('getValue')
    };
        //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/invoice/getTianMaoFiscalCodeList.html",
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
                    sortable: false,
                    align: 'left'
                },
                {
                    field:'corderType',
                    title:'网单类型',
                    sortable: false,
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
                    sortable: false,
                    align: 'center'
                },
                {
                    field:'productName',
                    title:'商品名称',
                    sortable: false,
                    align:'center'
                },
                {
                    field:'productCateName',
                    title:'商品分类',
                    sortable: false,
                    align:'center'
                },
                {
                    field:'number',
                    title:'数量',
                    sortable: false,
                    align:'center'
                },
                {
                    field:'price',
                    title:'含税单价',
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
                    align:'center'
                },
                {
                    field: 'fiscalCode',
                    title: '税控码',
                    sortable: false,
                    align: 'center'
                },
                {
                    field: 'type',
                    title: '发票类型',
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
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
                    sortable: false,
                    align: 'left'
                },
                {
                    field: 'operation',
                    title: ' 实时开票信息查询',
                    sortable: false,
                    align: 'center',
                    formatter:function(value,rowData,rowIndex){
                        //只有开票状态为待开票以外的行显示
                        //if(rowData.state!=0){
                            return "<a href='javascript:void(0);' onclick='showInvoiceInfo("+'"'+rowData.corderSn+'","'+rowData.electricFlag+'"'+");return false;'>实时开票信息查询</a>";
                        //}else{
                          //  return "";
                        //}
                    }
                }
            ]
        ],
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
    });
    $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
});

//点击导出
$('#export').click(function(){
    if(!datagrid){
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
            $('#paramForm_dmmtlPbcsMtlMeasure').attr("action", '/invoice/exportTianMaoFiscalCodeList.html');
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