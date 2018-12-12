//  $(window).resize(function () {
//        $('#dataGrid').datagrid('resize');
//    });

    $('#mark').combobox({
        data: [{v: '', t: '全部'}, {v: 'S', t: '入库'}, {v: 'H', t: "出库"}],
        valueField: 'v',
        textField: 't',
        panelHeight: 'auto',
        editable: false
    });

    $('#bill_type').combobox({
        data: [
            {v: '', t: '全部'},
            {v: 'ZBCC', t: '销售出库（ZBCC）'},
            {v: 'ZBCR', t: "采购入库（ZBCR）"},
            {v: 'ZBCT', t: "退货入库（ZBCT）"},
            {v: 'ZGR6', t: "调拨入库（ZGR6）"},
            {v: 'ZGI6', t: "调拨出库（ZGI6）"},
            {v: 'YTIB', t: "存性变更出库（YTIB）"},
            {v: 'YTRB', t: "存性变更入库（YTRB）"},
            {v: 'ZBCJ', t: "拒收入库（ZBCJ）"}
        ],
        valueField: 'v',
        textField: 't',
        panelHeight: 'auto',
        editable: false
    });

    $('#item_property').combobox({
        data: [
            {v: '', t: '全部'},
            {v: '10', t: '正品'},
            {v: '21', t: "不良品"},
            {v: '22', t: "开箱正品"},
            {v: '40', t: "样品机"},
            {v: '41', t: "夺宝机"}
        ],
        valueField: 'v',
        textField: 't',
        panelHeight: 'auto',
        editable: false
    });

    $('#process_status').combobox({
        data: [{v: '', t: '全部'}, {v: '0', t: '未处理'}, {v: '1', t: "已更新库存"}, {v: '2', t: "完成"}],
        valueField: 'v',
        textField: 't',
        panelHeight: 'auto',
        editable: false
    });

    function searchList() {
       ww('/stockTrans/queryInvStockTrans.html')
    };

    function onExport() {
        //$('#ff').form('submit', {url: '/stockTrans/exportStockTrans.html'})
        $('#paramForm_dmmtlPbcsMtlMeasure').attr("action", '/stockTrans/exportStockTrans.html');
        $('#paramForm_dmmtlPbcsMtlMeasure').submit();
        
    }


    var dataGrid;
    $(function () {
    	$("#cxhongzhi").on('click', function (event) {
    	    event.preventDefault();
    	    $('#paramForm_dmmtlPbcsMtlMeasure').form('reset');
    	});
    	ww("");
    });
    function ww(url){
    	if(url!=""){
    	 $('#datagrid').datagrid('load', {
             corder_sn: $('#corder_sn').val(),
             sku: $('#sku').val(),
             sec_code: $('#sec_code').val(),
             bill_type: $('#bill_type').combobox("getValue"),
             mark: $('#mark').combobox("getValue"),
             item_property: $('#item_property').combobox("getValue"),
             process_status: $('#process_status').combobox("getValue"),
             bill_time_s: $('#bill_time_s').datebox("getValue"),
             bill_time_e: $('#bill_time_e').datebox("getValue")
         });
    	}
    $('#datagrid').datagrid({
        url: url,
        fit: true,
        fitColumns: true,
        pagination: true,
        nowrap: true,
        pageSize: 50,
        pageList: [50,100,300],
        rownumbers: true,
        singleSelect: true,
        rowStyler: function (index, row) {
            if (row.delay == 1) {
                return 'color:red;';
            }
        },
        columns: [[
            {
                field: "sku",
                title: '物料',
                align: 'center',
            },
            {
                field: "sec_code",
                title: '库位',
                align: 'center',
            },
            {
                field: "corder_sn",
                title: '单据号',
                align: 'left',
            },
            {
                field: "channel_code",
                title: '渠道',
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.channel_name) {
                        return row.channel_name;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: "bill_type",
                title: '交易类型',
                align: 'center',
            },
            {
                field: "mark",
                title: '借贷标志',
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == 'S') {
                        return '入库';
                    } else {
                        return '出库';
                    }
                }
            },
            {
                field: "item_property",
                title: '批次',
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == '10') {
                        return '正品';
                    } else if (value == '21') {
                        return '不良品';
                    } else if (value == '22') {
                        return '开箱正品';
                    } else if (value == '40') {
                        return '样品机';
                    } else if (value = -'41') {
                        return '夺宝机';
                    } else {
                        return value;
                    }
                }
            },
            {
                field: "quantity",
                title: '数量',
                align: 'right',
            },
            {
                field: "bill_time",
                title: '交易时间',
                align: 'center',
            },
            {
                field: "is_froze",
                title: '是否占用库存',
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return '否';
                    } else {
                        return '是';
                    }
                }
            },
            {
                field: "process_status",
                title: '处理状态',
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == '0') {
                        return '未处理';
                    } else if (value == '1') {
                        return '已更新库存';
                    } else {
                        return '完成';
                    }
                }
            },
            {
                field: "last_process_time",
                title: '处理时间',
                align: 'center',
            },
            {
                field: "add_time",
                title: '添加时间',
                align: 'center',
            },
            {
                field: "message",
                title: '处理结果',
                align: 'center',
            }
        ]],
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
    });
    }