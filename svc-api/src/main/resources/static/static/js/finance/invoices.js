var datagridOptions_dmmtlPbcsMtlMeasure = {
    fit: true,
    fitColumns: true,
    singleSelect: false,
    frozenColumns: [[{
        field: 'id', checkbox: true
    }]],
    columns: covertColumns(tableHead),
    toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 20, 30, 40, 50],
    rownumbers: true
};
var datagrid = null;
$(function () {
    datagrid = $('#datagrid').datagrid(datagridOptions_dmmtlPbcsMtlMeasure);
    if($('#grid').length>0){
        var gridtable = $('#grid').datagrid(grid)
    }
    if (!datagrid.length) {
        datagrid = $('#dg').datagrid(dg);
    }
    if (!datagrid.length) {
        datagrid = $('#datagrid1').datagrid(datagrid1);
    }
    if (!datagrid.length) {
        datagrid = $('#dgt').datagrid(dgt);
    }
    $("#searchBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
        /*var param = $('#paramForm_dmmtlPbcsMtlMeasure').serializeObject();
        datagrid.datagrid({queryParams: param});*/
        event.preventDefault();
        var options = datagrid.datagrid('getPager').data("pagination").options;
        var InvoicequeryParams = {	cOrderSn:$("#cOrderSn").val(),
                  invoiceTitle:$("#invoiceTitle").val(),
                  taxPayerNumber:$("#taxPayerNumber").val(),
                  registerAddressAndPhone:$("#registerAddressAndPhone").val(),
                  bankNameAndAccount:$("#bankNameAndAccount").val(),
                  isEnergySaving:$("#isEnergySaving").combobox('getValue'),
                  isReInvoice:$("#isReInvoice").combobox('getValue'),
                  type:$("#type").combobox('getValue'),
                  state:$("#state").combobox('getValue'),
                  electricFlag:$("#electricFlag").combobox('getValue'),
                  tryNum:$("#tryNum").val(),
                  startFirstPushTime:$("#startFirstPushTime").datebox('getValue'),
                  endFirstPushTime:$("#endFirstPushTime").datebox('getValue'),
                  startInvalidTime:$("#startInvalidTime").datebox('getValue'),
                  endInvalidTime:$("#endInvalidTime").datebox('getValue'),
                  success:$("#success").combobox('getValue'),
                  startCOrderAddTime:$("#startCOrderAddTime").datebox('getValue'),
                  endCOrderAddTime:$("#endCOrderAddTime").datebox('getValue'),
                  isTogether:$("#isTogether").combobox('getValue'),
                  cOrderType:$("#cOrderType").combobox('getValue'),
                  invoiceNumber:$("#invoiceNumber").val(),
                  page: options.pageNumber,
                  rows: options.pageSize
        };
        datagrid.datagrid({
                url: "/invoiceOperation/search",
                queryParams: InvoicequeryParams,
                fit: true,
                pagination: true,
                pageSize: 20,
                pageList: [10, 20, 30],
                nowrap: true,
                rownumbers: true,
                singleSelect: false,
                selectOnCheck: true,
                checkOnSelect: true,
                columns: [[
                    {title: "网单号", field: "cOrderSn", sortable: true},
                    {
                        title: "网单类型", field: "cOrderType", sortable: true, formatter: function (val, rowData, rowIndex) {
                        if (val == '1') {
                            return '普通网单';
                        } else if (val == '2') {
                            return '差异网单';
                        } else if (val == '3'){
                            return '专项开票';
                        } else{
                            return '';
                        }
                    }
                    },
                    {title: "物料编号", field: "sku", sortable: true},
                    {title: "商品名称", field: "productName", sortable: true},
                    {title: "商品分类", field: "productCateName", sortable: true},
                    {title: "数量", field: "number", sortable: true},
                    {title: "含税单价", field: "price", sortable: true},
                    {title: "含税金额", field: "amount", sortable: true},
                    {title: "纳税人识别号", field: "taxPayerNumber", sortable: true},
                    {title: "发票号", field: "invoiceNumber", sortable: true},
                    {title: "税控码", field: "fiscalCode", sortable: true},
                    {
                        title: "发票类型", field: "type", sortable: true, formatter: function (val, rowData, rowIndex) {
                        if (val == '1') {
                            return '增值税发票';
                        } else if (val == '2') {
                            return '普通发票';
                        } else{
                            return '';
                        }
                    }
                    },
                    {
                        title: "电子发票标志", field: "electricFlag", sortable: true, formatter: function (val, rowData, rowIndex) {
                        if (val == '1') {
                            return '电子发票';
                        } else if (val == '2') {
                            return '纸制发票';
                        } else{
                            return '';
                        }
                    }
                    },
                    {
                        title: "发票状态", field: "state", sortable: true, formatter: function (val, rowData, rowIndex) {
                        if (val == '0') {
                            return '待开票';
                        } else if (val == '1') {
                            return '开票中';
                        } else if (val == '4') {
                            return '已开票';
                        } else if (val == '5'){
                            return '已取消开票';
                        } else{
                            return '';
                        }
                    }
                    },
                    {
                        title: "货票同行", field: "isTogether", sortable: true, formatter: function (val, rowData, rowIndex) {
                        if (val == '1') {
                            return '货票同行';
                        } else if (val == '2') {
                            return '非货票同行';
                        } else{
                            return '';
                        }
                    }
                    },
                    {title: "首次推送开票时间", field: "firstPushTime", sortable: true, formatter:function(value,row,index){
                        if (value){
                            return new Date(parseInt(value) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
                        }else{
                            return '';
                        }
                        }
                    },
                    {title: "开票时间", field: "billingTime", sortable: true, formatter:function(value,row,index){
                        if (value){
                            return new Date(parseInt(value) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
                        }else{
                            return '';
                        }
                        }
                    },
                    {title: "作废时间", field: "invalidTime", sortable: true, formatter:function(value,row,index){
                        if (value){
                            return new Date(parseInt(value) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
                        }else{
                            return '';
                        }
                        }
                    },
                    {
                        title: "推送状态", field: "success", sortable: true, formatter: function (val, rowData, rowIndex) {
                        if (val == '0') {
                            return '未提交或提交失败';
                        } else if (val == '1') {
                            return '已提交';
                        } else{
                            return '';
                        }
                    }
                    },
                    {
                        title: "是否成功", field: "success", sortable: true, formatter: function (val, rowData, rowIndex) {
                        if (val == '0') {
                            return '待推送';
                        } else if (val == '1') {
                            return '成功';
                        } else{
                            return '';
                        }
                    }
                    },
                    {title: "推送次数", field: "tryNum", sortable: true},
                    {
                        title: "实时开票信息查询", field: "success", sortable: true, formatter: function (val, rowData, rowIndex) {
                        return '<a>实时开票信息查询</a>';
                    }
                    }
                ]],
                /*toolbar: '#sale_road_grid_bar',*/
                striped: true,
                autoRowHeight: true,
                nowrap: true,
                pagination: true,
                rownumbers: true,
            });
    });
    $("#addBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
        $('#addForm_dmmtlPbcsMtlMeasure').form('reset');
        $('#addForm_dmmtlPbcsMtlMeasure').find('[__actType]').val('add');
        $('#addDlg_dmmtlPbcsMtlMeasure').dialog({'title': i18n['message.act.add']});
        $('#addDlg_dmmtlPbcsMtlMeasure').dialog('open');
    });

    $("#importSave").on('click', function () {
        if (!$('#importForm').form('validate')) {
            return;
        }
        $('#importDialog').dialog('close');
        alert("导入成功！")

    })

    $("#import").on('click', function (event) {
        var options = datagrid.datagrid('getPager').data("pagination").options;
        var InvoicequeryParams = {	cOrderSn:$("#cOrderSn").val(),
                  invoiceTitle:$("#invoiceTitle").val(),
                  taxPayerNumber:$("#taxPayerNumber").val(),
                  registerAddressAndPhone:$("#registerAddressAndPhone").val(),
                  bankNameAndAccount:$("#bankNameAndAccount").val(),
                  isEnergySaving:$("#isEnergySaving").combobox('getValue'),
                  isReInvoice:$("#isReInvoice").combobox('getValue'),
                  type:$("#type").combobox('getValue'),
                  state:$("#state").combobox('getValue'),
                  electricFlag:$("#electricFlag").combobox('getValue'),
                  tryNum:$("#tryNum").val(),
                  startFirstPushTime:$("#startFirstPushTime").datebox('getValue'),
                  endFirstPushTime:$("#endFirstPushTime").datebox('getValue'),
                  startInvalidTime:$("#startInvalidTime").datebox('getValue'),
                  endInvalidTime:$("#endInvalidTime").datebox('getValue'),
                  success:$("#success").combobox('getValue'),
                  startCOrderAddTime:$("#startCOrderAddTime").datebox('getValue'),
                  endCOrderAddTime:$("#endCOrderAddTime").datebox('getValue'),
                  isTogether:$("#isTogether").combobox('getValue'),
                  cOrderType:$("#cOrderType").combobox('getValue'),
                  invoiceNumber:$("#invoiceNumber").val(),
                  page: options.pageNumber,
                  rows: options.pageSize
        };
        $.ajax({
            url      : "/invoiceOperation/export_Excel",
            type     : 'POST',
            data     : InvoicequeryParams,
            dataType : "json",
            success  : function(data) {
                alert(data.msg);
            }
        })
    });

    $("#addDlgSaveBtn_dmmtlPbcsMtlMeasure").on('click', function () {
        if (!$('#addForm_dmmtlPbcsMtlMeasure').form('validate')) {
            return;
        }
        var actType = $('#addForm_dmmtlPbcsMtlMeasure').find('[__actType]').val();
        var url;
        if (actType === 'add') {
            url = '/report/dmmtlPbcsMtlMeasure/c';
        } else {
            url = '/report/dmmtlPbcsMtlMeasure/u';
        }
        $.post(url, $('#addForm_dmmtlPbcsMtlMeasure').serializeObject()).success(
            function (res) {
                if (res.success) {
                    if (actType === 'add') {
                        alert(i18n['message.act.add.success']);
                    } else {
                        alert(i18n['message.act.edit.success']);
                    }
                    $('#addDlg_dmmtlPbcsMtlMeasure').dialog('close');
                } else {
                    if (actType === 'add') {
                        alert(i18n['message.act.add.fail']);
                    } else {
                        alert(i18n['message.act.edit.fail']);
                    }
                }
            }).error(function (errorObj, statusText) {
            alert(statusText);
        }).done(function () {
            datagrid.datagrid('reload');
        });
    });

    $("[detail]").on('click', function (event) {
        if (this.className != 'easyui-linkbutton') {
            var selected = true;
        } else {
            datagrid.datagrid('getSelected');
        }
        if (selected) {
            $('#detailgrid').datagrid(detail);
            $('#detailDialog').dialog({'title': '详情'});
            $('#detailDialog').dialog('open');
        } else {
            alert(i18n['message.select-one']);
        }
    });


    $("#editBtn_dmmtlPbcsMtlMeasure").on('click', function () {
        var selected = datagrid.datagrid('getSelections');
        $('#addDlg_dmmtlPbcsMtlMeasure').dialog({'title': i18n['message.act.edit']});
        if (selected.length == 1) {
            $('#addForm_dmmtlPbcsMtlMeasure').form('load', selected[0]);
            $('#addForm_dmmtlPbcsMtlMeasure').find('[__actType]').val('edit');
            $('#addDlg_dmmtlPbcsMtlMeasure').dialog('open');
            datagrid.datagrid('reload');
        } else {
            alert(i18n['message.select-one']);
        }
    }).error(function (errorObj, statusText) {
        alert(statusText);
    });

    $("#deleteBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
        event.preventDefault();
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
            confirm(i18n['message.delete.confirm'], function (r) {
                if (r) {
                    $.post('/report/dmmtlPbcsMtlMeasure/d', {id: selected.id}).success(function (res) {
                        alert(i18n['message.act.delete.success']);
                    }).error(function () {
                        alert(i18n['message.act.delete.fail']);
                    }).done(function () {
                        datagrid.datagrid('reload');
                    });
                }
            })

        } else {
            alert(i18n['message.select-one']);
        }
    });
    $("#resetBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_dmmtlPbcsMtlMeasure').form('reset');
    });
});