var datagridOptions_orderForecastGoal = {
	fit : true,
	fitColumns : true,
	singleSelect : true,
	showFooter : true,
	url : '',
	frozenColumns : [ [ {
		field : 'id',
		checkbox : true
	}, {
		title : '网单号',
		field : 'cOrderSn',
		sortable : true
	}, {
		title : '原网单号',
		field : 'cOrderSnOld',
		sortable : true
	} ] ],
	columns : [ [ {
		title : '来源订单号',
		field : 'sourceOrderSn',
		sortable : true
	}, {
		title : '订单来源',
		field : 'channelName',
		sortable : true
	}, {
		title : '销售代表',
		field : 'sellPeople',
		sortable : true
	}, {
		title : '品类',
		field : 'category',
		sortable : true
	}, {
		title : '品牌',
		field : 'brand',
		sortable : true
	}, {
		title : 'SKU',
		field : 'sku',
		sortable : true
	}, {
		title : '宝贝型号',
		field : 'productName',
		sortable : true
	}, {
		title : '收货人姓名',
		field : 'consignee',
		sortable : true
	}, {
		title : '订单付款时间',
		field : 'cPayTime',
		sortable : true,
		formatter : formatDatebox
	}, {
		title : '销售数量',
		field : 'number',
		sortable : true,
		align:'right'
	}, {
		title : '总价(发票金额)',
		field : 'productAmount',
		sortable : true,
		align:'right'
	}, {
		title : '网单金额',
		field : 'pAmount',
		sortable : true,
		align:'right'
	}, {
		title : '网单状态',
		field : 'cOrderStatus',
		sortable : true
	}, {
		title : '期间',
		field : 'period',
		sortable : true
	}, {
		title : '年度',
		field : 'year',
		sortable : true
	}, {
		title : '发票时间',
		field : 'invoiceTime',
		sortable : true,
		formatter : formatDatebox
	}, {
		title : '开票状态',
		field : 'invoiceStatus',
		sortable : true
	}, {
		title : '数据标识',
		field : 'dataStatus',
		sortable : true
	}, {
		title : '结算方式',
		field : 'settleType',
		sortable : true
	}, {
		title : '审核状态',
		field : 'auditStatus',
		sortable : true
	},{
		title : '业务审核人',
		field : 'businessAuditPeople',
		sortable : true
	},{
		title : '业务审核时间',
		field : 'businessAuditTime',
		sortable : true
	},{
		title : '财务审核人',
		field : 'financeAuditPeople',
		sortable : true
	},{
		title : '财务审核时间',
		field : 'financeAuditTime',
		sortable : true
	},
	] ],
	toolbar : '#datagridToolbar_orderForecastGoal',
	striped : true,
	autoRowHeight : true,
	nowrap : true,
	pagination : true,
	rownumbers : true,
};
$(function() {
	var datagrid = $('#datagrid_orderForecastGoal').datagrid(datagridOptions_orderForecastGoal);

	$("#addBtn_orderForecastGoal").on('click', function(event) {
		$('#addForm_orderForecastGoal').form('reset');
		$('#addForm_orderForecastGoal').find('[__actType]').val('add');
		$('#addDlg_orderForecastGoal').dialog({
			'title' : '新增'
		});
		$('#addDlg_orderForecastGoal').dialog('open');
	});
	$("#addDlgSaveBtn_orderForecastGoal").on(
			'click',
			function() {
				if (!$('#addForm_orderForecastGoal').form('validate')) {
					return;
				}
				var actType = $('#addForm_orderForecastGoal').find(
						'[__actType]').val();
				if (actType === 'add') {
					$.messager.alert('提示','新增成功');
				} else {
					$.messager.alert('提示','编辑成功');
				}
				$('#addDlg_orderForecastGoal').dialog('close');
			});
	$("#editBtn_orderForecastGoal").on('click',	function() {
				var selected = datagrid.datagrid('getSelected');
				// $('#addDlg_orderForecastGoal').dialog({'title': '修改'});
				if (selected !== null) {
					var id = selected.id;
					if (selected.auditStatus == "业务审核") {
						confirm('确认业务审核吗？', function(r) {
							if (r) {

								$.ajax({
									method : 'post',
									url : '/adjust/update',
									data : {
										type : "2",
										id : id
									},
									async : false,
									dataType : 'json',
									success : function(data) {
										var data1 = data;
										if (data1.success) {
											$('#datagrid_orderForecastGoal')
													.datagrid('reload');

											$.messager.alert('提示','审核成功');
										} else {
											$.messager.alert('提示','审核失败');
										}
									},
									error : function() {
										$.messager.alert('提示','审核失败');
									}
								});
							}
						})
					} else if (selected.auditStatus == "财务审核") {
						confirm('确认财务审核吗？', function(r) {
							if (r) {
								$.ajax({
									method : 'post',
									url : '/adjust/update',
									data : {
										type : "3",
										id : id
									},
									async : false,
									dataType : 'json',
									success : function(data) {
										var data1 = data;
										if (data1.success) {
											$('#datagrid_orderForecastGoal')
													.datagrid('reload');

											$.messager.alert('提示','审核成功');
										} else {
											$.messager.alert('提示','审核失败');
										}
									},
									error : function() {
										$.messager.alert('提示','审核失败');
									}
								});
							}
						})
					} else if (selected.auditStatus == "审核完成") {
						$.messager.alert('提示','该条数据已经审核完成');
					}

					// alert(JSON.stringify(selected));

					/*
					 * $('#addForm_orderForecastGoal').form('load', selected);
					 * $('#addForm_orderForecastGoal').find('[__actType]').val('edit');
					 * $('#addDlg_orderForecastGoal').dialog('open');
					 */

				} else {
					$.messager.alert('提示','请选择一条数据');
				}
			});
	$("#deleteBtn_orderForecastGoal").on('click', function(event) {
		event.preventDefault();
		var selected = datagrid.datagrid('getSelected');
		if (selected !== null) {
			confirm('确定删除？', function(r) {
				if (r) {
					$.messager.alert('提示','删除成功');
				}
			})

		} else {
			$.messager.alert('提示','请选择一条数据');
		}
	});
	$("#resetBtn_orderForecastGoal").on('click', function(event) {
		event.preventDefault();
		$('#paramForm_orderForecastGoal').form('reset');
	});

});


//点击查询
$("#searchBtn_orderForecastGoal").on('click', function (event) {
    // 核算月份
    var attYearMonth = $("#attYearMonth").textbox("getValue");
    // 付款时间开始
    var memberLevels = $("#memberLevels").textbox("getValue");
    // 付款时间结束
    var memberLevele = $("#memberLevele").textbox("getValue");
    // 核算方式
    var jsfs = $("#jsfs").combobox("getValue");
    // 销售代表
    var sourceOrder = $("#sourceOrder").textbox("getValue");
    // 状态
    var field2 = $("#field2").combobox("getValue");
    // 发票时间开始
    var field0s = $("#field0s").textbox("getValue");
    // 发票时间结束
    var field0e = $("#field0e").textbox("getValue");
    $("#att_YearMonth").val(attYearMonth);
    $("#c_PayTimeStart").val(memberLevels);
    $("#c_PayTimeEnd").val(memberLevele);
    $("#settle_Type").val(jsfs);
    $("#sell_People").val(sourceOrder);
    $("#data_Status").val(field2);
    $("#invoice_TimeStart").val(field0s);
    $("#invoice_TimeEnd").val(field0e);
    $("#code").val("yes");

    //加载分页
    datagrid = $('#datagrid_orderForecastGoal').datagrid({
        url: "/adjust/findDistributeOrderList",
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
            attYearMonth : attYearMonth,
            cPayTimeStart : memberLevels,
            cPayTimeEnd : memberLevele,
            settleType : jsfs,
            sellPeople : sourceOrder,
            dataStatus : field2,
            invoiceTimeStart : field0s,
            invoiceTimeEnd : field0e
        },
        frozenColumns : [ [ {
            field : 'id',
            checkbox : true
        }, {
            title : '网单号',
            field : 'cOrderSn',
            sortable : true
        }, {
            title : '原网单号',
            field : 'cOrderSnOld',
            sortable : true
        } ] ],
        columns : [ [ {
            title : '来源订单号',
            field : 'sourceOrderSn',
            sortable : true
        }, {
            title : '订单来源',
            field : 'channelName',
            sortable : true
        }, {
            title : '销售代表',
            field : 'sellPeople',
            sortable : true
        }, {
            title : '品类',
            field : 'category',
            sortable : true
        }, {
            title : '品牌',
            field : 'brand',
            sortable : true
        }, {
            title : 'SKU',
            field : 'sku',
            sortable : true
        }, {
            title : '宝贝型号',
            field : 'productName',
            sortable : true
        }, {
            title : '收货人姓名',
            field : 'consignee',
            sortable : true
        }, {
            title : '订单付款时间',
            field : 'cPayTime',
            sortable : true,
            formatter : formatDatebox
        }, {
            title : '销售数量',
            field : 'number',
            sortable : true,
            align:'right'
        }, {
            title : '总价(发票金额)',
            field : 'productAmount',
            sortable : true,
            align:'right'
        }, {
            title : '网单金额',
            field : 'pAmount',
            sortable : true,
            align:'right'
        }, {
            title : '网单状态',
            field : 'cOrderStatus',
            sortable : true
        }, {
            title : '期间',
            field : 'period',
            sortable : true
        }, {
            title : '年度',
            field : 'year',
            sortable : true
        }, {
            title : '发票时间',
            field : 'invoiceTime',
            sortable : true,
            formatter : formatDatebox
        }, {
            title : '开票状态',
            field : 'invoiceStatus',
            sortable : true
        }, {
            title : '数据标识',
            field : 'dataStatus',
            sortable : true
        }, {
            title : '结算方式',
            field : 'settleType',
            sortable : true
        }, {
            title : '审核状态',
            field : 'auditStatus',
            sortable : true
        },{
            title : '业务审核人',
            field : 'businessAuditPeople',
            sortable : true
        },{
            title : '业务审核时间',
            field : 'businessAuditTime',
            sortable : true
        },{
            title : '财务审核人',
            field : 'financeAuditPeople',
            sortable : true
        },{
            title : '财务审核时间',
            field : 'financeAuditTime',
            sortable : true
        },
        ] ],
    });

});

//导出
$("#importBtn_checkList").click(function () {
    $("#exportForm").attr("action","/adjust/exportCheckList");
    $("#exportForm").submit();
});

// 日期格式化方法
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}
// 日期格式化
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



function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {
	// If JSONData is not an object then JSON.parse will parse the JSON string
	// in an Object
	var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;

	var CSV = '';
	// Set Report title in first row or line

	CSV += ReportTitle + '\r\n\n';

	// This condition will generate the Label/Header
	if (ShowLabel) {
		var row = "";

		// This loop will extract the label from 1st index of on array
		for ( var index in arrData[0]) {

			// Now convert each value to string and comma-seprated
			row += index + ',';
		}

		row = row.slice(0, -1);

		// append Label row with line break
		CSV += row + '\r\n';
	}

	// 1st loop is to extract each row
	for (var i = 0; i < arrData.length; i++) {
		var row = "";

		// 2nd loop will extract each column and convert it in string
		// comma-seprated
		for ( var index in arrData[i]) {
			row += '"' + arrData[i][index] + '",';
		}

		row.slice(0, row.length - 1);

		// add a line break after each row
		CSV += row + '\r\n';
	}

	if (CSV == '') {
		$.messager.alert('提示',"Invalid data");
		return;
	}

	// Generate a file name
	var fileName = "MyReport_";
	// this will remove the blank-spaces from the title and replace it with an
	// underscore
	fileName += ReportTitle.replace(/ /g, "_");

	// Initialize file format you want csv or xls
	var uri = 'data:text/csv;charset=utf-8,' + escape(CSV);

	// Now the little tricky part.
	// you can use either>> window.open(uri);
	// but this will not work in some browsers
	// or you will not get the correct file extension

	// this trick will generate a temp <a /> tag
	var link = document.createElement("a");
	link.href = uri;

	// set the visibility hidden so it will not effect on your web-layout
	link.style = "visibility:hidden";
	link.download = fileName + ".csv";

	// this part will append the anchor tag and remove it after automatic click
	document.body.appendChild(link);
	link.click();
	document.body.removeChild(link);
}

/*$("#importBtn_orderForecastGoal").click(function() {
	var data = JSON.stringify($('#datagrid_orderForecastGoal').datagrid('getData').rows);
	if (data == '')
		return;

	JSONToCSVConvertor(data, "Download", true);
});*/


// EasyUI datagrid 动态导出Excel
/*
 * function ExporterExcel() {
 * 
 * alert(22); //获取Datagride的列 var rows =
 * $('#datagrid_orderForecastGoal').datagrid('getRows'); var columns =
 * $("#datagrid_orderForecastGoal").datagrid("options").columns[0]; var oXL =
 * new ActiveXObject("Excel.Application"); //创建AX对象excel var oWB =
 * oXL.Workbooks.Add(); //获取workbook对象 var oSheet = oWB.ActiveSheet; //激活当前sheet
 * //设置工作薄名称 oSheet.name = "导出Excel报表"; //设置表头 for (var i = 0; i <
 * columns.length; i++) { oSheet.Cells(1, i+1).value = columns[i].title; }
 * //设置内容部分 for (var i = 0; i < rows.length; i++) { //动态获取每一行每一列的数据值 for (var j =
 * 0; j < columns.length; j++) { oSheet.Cells(i + 2, j+1).value =
 * rows[i][columns[j].field]; } } oXL.Visible = true; //设置excel可见属性 }
 */
