$(function() {
	var datagrid;
	$("#searchBtn_dmmbcsMtlMeasure").hide();
	$('#orderStatus').combobox({
		prompt : '请搜索',
		url : '/vehicle/orderStatus',
		valueField : "value",
		textField : "text",
		required : false,
		editable : true,
		hasDownArrow : true,
		filter : function(q, row) {
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q) >= 0;
		},
		onLoadSuccess : function() {
			var array = $(this).combobox("getData")
			showStatus.status = {}
			for (var i = 0; i < array.length; i++) {
				showStatus.status[array[i].value] = array[i].text
			}
		}
	});

	var buttons = $.extend([], $.fn.datebox.defaults.buttons);
	var clearDateBox = function(id) {
		var t = {
			startDate : "endDate",
			endDate : "startDate"
		}
		$("#" + t[id]).datebox('calendar').calendar({
			validator : function(date) {
				return true
			}
		})
	}
	buttons.splice(1, 0, {
		text : '清空',
		handler : function(target) {
			$(target).datebox("setValue", "")
			clearDateBox($(target).attr("id"))
		}
	});

	$('#startDate').datebox({
		onSelect : function(select) {
			$('#endDate').datebox('calendar').calendar({
				validator : function(date) {
					var d1 = new Date(select);
					return date >= d1
				}
			});
		},
		icons : [],
		buttons : buttons
	});

	$('#endDate').datebox({
		onSelect : function(select) {
			$('#startDate').datebox('calendar').calendar({
				validator : function(date) {
					var d1 = new Date(select);
					return date <= d1
				}
			});
		},
		icons : [],
		buttons : buttons
	});

	// var datagrid = $('#datagrid').datagrid(datagridOptions);

	$("#return").on('click', function() {
		/*
		 * window.location = "historyOrder.html"; removeTab("历史订单详情")
		 */
	})

	$(".datagrid-btable").find("[field='opt']").on("click", "[detail]",
			function() {
				var tds = $(this).parent().parent().parent().children();
				var obj = {}
				for (var i = 0; i < tds.length; i++) {
					obj[$(tds[i]).attr("field")] = tds[i].innerText
				}
				$("#detailDialog").dialog("open")

			})

	$("#searchBtn").on('click', function() {
		$("#searchBtn").linkbutton("disable")
//		var p = datagrid.datagrid("getPager").data("pagination").options;

		datagrid = $('#datagrid').datagrid({
			url : "/vehicle/historyOrder",
			fit : true,
			fitColumns : true,
			singleSelect : false,
			queryParams : {
				order : $("#order").textbox("getValue"),
				startDate : $("#startDate").datebox("getValue"),
				endDate : $("#endDate").datebox("getValue"),
				status : $("#orderStatus").combobox("getValue")
//				page : p.pageNumber,
//				rows : p.pageSize
			},
			loadMsg : '正在加载信息...',
			columns : [ [ {
				title : '',
				field : '',
				checkbox : true,
				sortable : true,
				align : 'center',
				width : 200
			}, {
				title : '单号',
				field : 'orderNo',
				sortable : true,
				align : 'center',
				width : 200
			}, {
				title : '开单日期',
				field : 'orderTime',
				sortable : true,
				align : 'center',
				formatter : formatDate,
				width : 200
			}, {
				title : '基地',
				field : 'jdName',
				sortable : true,
				align : 'center',
				width : 200
			}, {
				title : '送达方编码',
				field : 'deliveryCode',
				sortable : true,
				align : 'center',
				width : 200
			}, {
				title : '送达方名称',
				field : 'deliveryName',
				sortable : true,
				align : 'center',
				width : 200
			}, {
				title : '总体积',
				field : 'loadingVolume',
				sortable : true,
				align : 'center',
				formatter : loadingVolume,
				width : 200
			}, {
				title : '是否拼车',
				field : 'ispingcar',
				sortable : true,
				align : 'center',
				formatter : ispingcar,
				width : 200
			}, {
				title : '整车状态',
				field : 'status',
				sortable : true,
				align : 'center',
				formatter : showStatus,
				width : 200
			}, {
				title : '操作',
				field : 'opt',
				sortable : true,
				align : 'center',
				formatter : showWhat,
				width : 200
			} ] ],
			toolbar : '#datagridToolbar',
			striped : true,
			pagination : true,
			pageSize : 50,
			pageList : [ 50, 100, 200 ],
			rownumbers : true,
			autoRowHeight : true,
			nowrap : false
		});
		$("#searchBtn").linkbutton("enable");
		$("#searchBtn_dmmbcsMtlMeasure").show();
	})

	$("#addDlgSaveBtn").on(
			'click',
			function() {
				if (!$('#addForm').form('validate')) {
					return;
				}
				var actType = $('#addForm').is('.edit');
				var widths = parseInt(selections["totalnum"])
						+ parseInt($('#num').val());

				if (widths > 100) {
					var leftnum = 100 - parseInt(selections["totalnum"]);
					alert("超出最大装车体积，这种产品最多还能添加" + leftnum + "台");
					return;
				}
				// 绑定点击事件
				if (eleShopCart) {
					getSelections(actType);
				}
				setprogress();

				alert('提交成功');
				$('#addDlg').dialog('close');
			});

	$('.pagination-info').css('margin', '0 100px 0 0');

});