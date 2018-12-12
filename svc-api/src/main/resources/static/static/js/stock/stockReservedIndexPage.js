var datagridOptions_warehouseListGoal = {
			striped : true, // 隔行变色
			rownumbers : true,
			fit: true,
			pagination : true,
			singleSelect : true,
			fitColunms : true,
			idField : 'id',
			frozenColumns:[[    
				        {
				        	title : '物料',
							width : 120,
							field : 'sku',
							align : 'center'
				            , sortable: true,
				            rowspan:2
				        },
				        {
				        	title : '库位',
							width : 120,
							field : 'sec_code',
							align : 'center'
				            , sortable: true,
				            rowspan:2
				        },
				        {
				        	title : '渠道',
							width : 120,
							field : 'channel_name',
							align : 'center'
				            , sortable: true,
				            rowspan:2
				        },
				        {
				        	title : '品类',
							width : 120,
							field : 'product_type_name',
							align : 'center'
				            , sortable: true,
				            rowspan:2
				        },
				        {
				        	title : '型号',
							width : 150,
							field : 'product_name',
							align : 'center'
				            , sortable: true,
				            rowspan:2
				        }
				        
						],[]],
				        columns: [[
				            {
				                title: '渠道锁定',
				                width: 70,
				                align: 'left'
				                , sortable: true,
				                colspan:4
				            }
				            
				            ,{
								title : '锁定占用',
								width : 100,
								field : 'optUser realeaseQty',
								align : 'center',
								  sortable: true,
						            rowspan:2,
						            formatter: function(value,row,index){
										if(row.channelLockQty==0){
											return '0';
										}else {
											return "<a id='oper' href='javascript:void(0);' onclick='showDetail("+row.sec_code+","+row.sku+","+row.channel_code+">"+row.channelLockQty+"</a>";	
										}
										 
									}
							},
				            {
				                title: '渠道共享',
				                width: 70,
				                align: 'left'
				                , sortable: true,
				                colspan:4
				            },{
								title : '渠道共享占用',
								width : 110,
								field : 'channelLockQty',
								align : 'center',
								 sortable: true,
						            rowspan:2,
						            formatter: function(value,row,index){
										if(row.waLockQty==0){
											return '0';
										}else {
											return "<a id='oper' href='javascript:void(0);' onclick='showDetail("+row.sec_code+","+row.sku+","+row.channel_code+",'1'>"+row.waLockQty+"</a>";	
										}
										 
									} 
				            }
				            ]
				        ,
				[   {
					title : '1-7天',
					width : 100,
					field : 'optUser1',
					align : 'center',
					formatter: function(value,row,index){
						return '0';
				}
				},  {
					title : '7-15天',
					width : 100,
					field : 'optUser2',
					align : 'center',
					formatter: function(value,row,index){
						return '0';
				}
				},  {
					title : '15-30天',
					width : 100,
					field : 'optUser3',
					align : 'center',
					formatter: function(value,row,index){
						return '0';
				}
				},  {
					title : '30天以上',
					width : 100,
					field : 'optUser4',
					align : 'center',
					formatter: function(value,row,index){
						return '0';
				}
				},  {
					title : '1-7天',
					width : 100,
					field : 'optUser',
					align : 'center',
					formatter: function(value,row,index){
						return '0';
				}
				},  {
					title : '7-15天',
					width : 100,
					field : 'optUser',
					align : 'center',
					formatter: function(value,row,index){
						return '0';
				}
				} , {
					title : '15-30天',
					width : 100,
					field : 'optUser',
					align : 'center',
					formatter: function(value,row,index){
						return '0';
				}
				} ,{
					title : '30天以上',
					width : 100,
					field : 'optUser',
					align : 'center',
					formatter: function(value,row,index){
						return '0';
					}
				}
				] ],
				 toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
};
if($("#canTransferStock").val()=="0"){
	document.getElementById("resetBtn_dmmtlPbcsMeasure").style.display='none';
	document.getElementById("safd").style.display='none';
}

$("#cxhongzhi").on('click', function (event) {
    event.preventDefault();
    $('#filterForm').form('reset');
});

$.getJSON("/invParams/getParams?group=GROUP_CHANNEL_AGE", function (result) {
    jQuery.each(result.data, function (i, v) {
        $("#channelCode").append("<option value=" + v.paramKey + ">" + v.paramName + "</option>");
    });
	});

$.getJSON("/itemAttribute/getAllProductTypes", function(result){
	jQuery.each(result.data,function(i, v){                   
		var key = v;
		if ( key == '全部'){
			key='';
		}
		$("#product_type_name"). append("<option value=" + key + ">" + v + "</option>");                    
	});	
});

$.getJSON("/invParams/getParams?group=GROUP_CHANNEL_STOCK", function (result) {
    jQuery.each(result.data, function (i, v) {
		if ( v.paramKey =='ALL') {
			if ('$isReserved'=='true' ){
				$("#channelFrom").append("<option value=" + 'WA' + ">" + v.paramName + "</option>");
			}
		} else {
       		 $("#channelFrom").append("<option value=" + v.paramKey + ">" + v.paramName + "</option>");
		}
    });
	});
$.getJSON("/invParams/getParams?group=GROUP_CHANNEL_STOCK", function (result) {
    jQuery.each(result.data, function (i, v) {
		if ( v.paramKey =='ALL') {
			if ('$isReserved' =='false' ) {
				$("#channelTo").append("<option value=" + 'WA' + ">" + 'WA共享' + "</option>");
			}
		} else {
        	$("#channelTo").append("<option value=" + v.paramKey + ">" + v.paramName + "</option>");
		}
    });
	});
$(function () {
    var datagrid = $('#dg').datagrid(datagridOptions_warehouseListGoal);
});

function searchselect(){
//	var options = dataGrid.datagrid('getPager').data("pagination").options;
//	options.pageNumber = 1;
//	dataGrid.datagrid('getPager').pagination('refresh');
	onQuery(1);
}
function onQuery(pageIndex){
//	 var options = $("#dg").datagrid('getPager').data("pagination").options;
//	$('#page').val(options.pageNumber);
//	$('#rows').val(options.pageSize);
//	$('#list').html("正在加载...");
//	jQuery.ajax({
//		url: "/stockReserved/queryStockAgeList",   // 提交的页面
//		data: $('#filterForm').serialize(), // 从表单中获取数据
//		type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
//		success: function(datas) {
//			/* var datagridData_orderForecastGoal = {
//          		    'data': {
//          		        'records':
//          		        	datas.obj.rowList,'totalCount':datas.obj.pager.rowsCount
//          		            }
//        	  } */
//			$("#dg").datagrid('loadData',datas.obj.rowList);
//		}
//	});//
//	return false;
//	
//
//    var whCodeData = $("#wh_code").textbox("getValue");
//    var whNameData = $("#wh_name").textbox("getValue");
//    var centerCodeData = $("#center_code").textbox("getValue");
//
//    $("#e_wh_code").val(whCodeData);
//    $("#e_wh_name").val(whNameData);
//    $("#e_center_code").val(centerCodeData);
//    $("#code").val("yes");

    //加载分页
    datagrid = $('#dg').datagrid({
        url: "/stockReserved/queryStockAgeList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        queryParams:{
        	ssku:$("#ssku").val(),
        	ssecCode:$("#ssecCode").val(),
        	channelCode:$("#channelCode").val()
        	
//        	$('#filterForm').serialize(),
        },
        frozenColumns:[[    
	        {
	        	title : '物料',
				width : 120,
				field : 'sku',
				align : 'center'
	            , sortable: true,
	            rowspan:2
	        },
	        {
	        	title : '库位',
				width : 120,
				field : 'sec_code',
				align : 'center'
	            , sortable: true,
	            rowspan:2
	        },
	        {
	        	title : '渠道',
				width : 120,
				field : 'channel_name',
				align : 'center'
	            , sortable: true,
	            rowspan:2
	        },
	        {
	        	title : '品类',
				width : 120,
				field : 'product_type_name',
				align : 'center'
	            , sortable: true,
	            rowspan:2
	        },
	        {
	        	title : '型号',
				width : 150,
				field : 'product_name',
				align : 'center'
	            , sortable: true,
	            rowspan:2
	        }
	        
			],[]],
	        columns: [[
	            {
	                title: '渠道锁定',
	                width: 70,
	                align: 'left'
	                , sortable: true,
	                colspan:4
	            }
	            
	            ,{
					title : '锁定占用',
					width : 100,
					field : 'optUser realeaseQty',
					align : 'center',
					  sortable: true,
			            rowspan:2,
			            formatter: function(value,row,index){
							if(row.channelLockQty==0){
								return '0';
							}else {
								return "<a id='oper' href='javascript:void(0);' onclick='showDetail("+row.sec_code+","+row.sku+","+row.channel_code+">"+row.channelLockQty+"</a>";	
							}
							 
						}
				},
	            {
	                title: '渠道共享',
	                width: 70,
	                align: 'left'
	                , sortable: true,
	                colspan:4
	            },{
					title : '渠道共享占用',
					width : 110,
					field : 'channelLockQty',
					align : 'center',
					 sortable: true,
			            rowspan:2,
			            formatter: function(value,row,index){
							if(row.waLockQty==0){
								return '0';
							}else {
								return "<a id='oper' href='javascript:void(0);' onclick='showDetail("+row.sec_code+","+row.sku+","+row.channel_code+",'1'>"+row.waLockQty+"</a>";	
							}
							 
						} 
	            }
	            ]
	        ,
	[   {
		title : '1-7天',
		width : 100,
		field : 'optUser1',
		align : 'center',
		formatter: function(value,row,index){
			return '0';
	}
	},  {
		title : '7-15天',
		width : 100,
		field : 'optUser2',
		align : 'center',
		formatter: function(value,row,index){
			return '0';
	}
	},  {
		title : '15-30天',
		width : 100,
		field : 'optUser3',
		align : 'center',
		formatter: function(value,row,index){
			return '0';
	}
	},  {
		title : '30天以上',
		width : 100,
		field : 'optUser4',
		align : 'center',
		formatter: function(value,row,index){
			return '0';
	}
	},  {
		title : '1-7天',
		width : 100,
		field : 'optUser123',
		align : 'center',
		formatter: function(value,row,index){
			return '0';
	}
	},  {
		title : '7-15天',
		width : 100,
		field : 'optUser23',
		align : 'center',
		formatter: function(value,row,index){
			return '0';
	}
	} , {
		title : '15-30天',
		width : 100,
		field : 'optUser34',
		align : 'center',
		formatter: function(value,row,index){
			return '0';
	}
	} ,{
		title : '30天以上',
		width : 100,
		field : 'optUser333',
		align : 'center',
		formatter: function(value,row,index){
			return '0';
		}
	}
	] ],
    });
}

function onExport(pageIndex){
	$('#filterForm').attr("action","/stockReserved/queryStockAgeListExport");
	$('#filterForm').submit();
}
//预留
function saveReservedOrRelease ( reservedflag ) {
	var channelFrom = $("#channelFrom").val();
	var channelTo = $("#channelTo").val();
	var lockHours = $("#lockHours").val();
	var qty = $("#qty").val();
	//物料编码 ， 调出库位
	var sku = $("#sku").val();
	var secCode = $("#secCode").val();
	if ( jQuery.trim(sku).length<=0 ){
		alert("物料编码不能为空");
		return false;
	}
	if ( jQuery.trim(secCode).length<=0 ){
		alert("调出库位不能为空");
		return false;
	}
	if ( jQuery.trim(channelFrom).length<=0 ) {
		alert("调出渠道不能为空");
		return false;
	}
	if ( jQuery.trim(channelTo) == "" ) {
		alert("调入渠道不能为空");
		return false;
	}
	if ( jQuery.trim(qty) == "" ) {
		alert("数量不能为空");
		return false;
	}
	var regex = /^[1-9]*[1-9][0-9]*$/;
	if ( !regex.test(qty)) {
		alert("数量必须是大于0的整数");
		return false;
	}
	if ( qty.length > 10 ) {
		alert("数量，输入整数太大");
		return false;
	}
	//只有预留到渠道时，才有锁定时间
	
	if ($("#isReserved").val()=="true") {
		if ( jQuery.trim(lockHours).length <=0 ) {
			alert("锁定时间不能为空");
			return false;
		}
		var regex = /^[1-9]*[1-9][0-9]*$/;
		if ( !regex.test(lockHours)) {
			alert("锁定时间必须是大于0的整数");
			return false;
		}
		if ( lockHours.length > 10 ) {
			alert("锁定时间，输入整数太大");
			return false;
		}
	}
	
	jQuery.ajax({
		url: "/stockReserved/checkTransferCntBySecCodeAndSku",   // 提交的页面
		data: $('#reservedform').serialize(), // 从表单中获取数据
		type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
		success: function(data) {
			if ( data.data > 0 ) {
				//如果有提示信息，则需显示提示信息让用户做选择，否则直接保存
				if(data.message != null && data.message.length >0 ) {
					$.messager.confirm(data.message, "确定调拨"+data.data+"吗?",function(r){
						 if(r){
							 transferTo(data.data,$("#isReserved").val());
						 }
					})
				} else {
					transferTo(data.data,$("#isReserved").val());
				}
			} else{
				if ( data.message != null && data.message.length > 0 ){
					alert(data.message);
				} else {
					alert("当前库存为0");
				}
				$("#dialog-message .modal-body p").html("");
				$("#dialog-message").modal('hide');
			}
			
		}
	});
	
}
function transferTo( cnt,reservedflag ){
	if(reservedflag=="true"){
		reservedflag =true;
	}else {
		reservedflag =false;
	}
	$("#qty").val(cnt);
	var url = reservedflag ? "/stockReserved/saveStockReservedByChannel":"/stockReserved/releaseStockReservedFromChannel";
	jQuery.ajax({
		url: url,
		data: $('#reservedform').serialize(),
		type: "GET",
		success: function(data) {
					alert(data.message);
					if ( data.data == true) {
						$("#dialog-message .modal-body p").html("");
						$("#dialog-message").modal('hide');
						onQuery($("#pageIndex").val());
					}
		}
	});
}
function getRow (type, sku, secCode, channelCode,isReserved ) {
	$("#dialog-message .modal-body p").text("正在加载...");
	if ( type == 'reserved' ) {
		$("#title").text("库存锁定");
		$("#isReserved").val("true");
		$("#lockHours").attr("disabled",false);
	} else {
		$("#title").text("库存解锁");
		$("#isReserved").val("false");
		$("#lockHours").attr("disabled",true);
	}
	$("#sku").val("");
	$("#secCode").val("");
	$("#qty").val("");
	$("#lockHours").val("");
	        $('#addForm_orderForecastGoal').form('reset');
	        $('#addForm_orderForecastGoal').find('[__actType]').val('add');
	        $('#addDlg_orderForecastGoal').dialog({'title': '新增'});
	        $('#addDlg_orderForecastGoal').dialog('open');
	/* window.location.href="/stockReservedController/getStockReserved?optType="+type+"&sku="+sku+"&secCode="+secCode+"&channelCode="+channelCode+"&isReserved="+isReserved;  // 提交的页面 */
	/* $.ajax({
		url: "/stockReservedController/getStockReserved?optType="+type+"&sku="+sku+"&secCode="+secCode+"&channelCode="+channelCode+"&isReserved="+isReserved,   // 提交的页面
		type: "GET",
		success: function(data) {
			//$("#dialog-message").html(data);
			
			$("#dialog-message .modal-body p").html(data);
			$("#dialog-message").modal('show');
		}
	}); */
	//$("#dialog-message").dialog("open");
	return false;
}
/*  jQuery.getJSON("/stockReservedController/getAllProductTypes", function(result){
		jQuery.each(result.data,function(i, v){                   
			var key = v;
			if ( key == '全部'){
				key='';
			}
    		$("#product_type_name"). append("<option value=" + key + ">" + v + "</option>");                    
		});	
	}); */
	
function explainAgeRow (allAge, waData ,chalId, waId) {
	
	var entries = JSON.parse(allAge);
	var map = {"c1":0, "c2":0,"c3":0,"c4":0};
	for (i=0; i< entries.length; i++){
		entry = entries[i];
		var age = entry["age"];
		var qty = entry["stockQuantity"];
		var cnt = 0;
		if ( age >= 0 && age <=7) {
			cnt = map["c1"]+parseInt(qty);
			map["c1"]=cnt;
		} else if( age >7 && age<=15) {
			cnt = map["c2"]+parseInt(qty);
			map["c2"]=cnt;
		} else if ( age > 15 && age <=30){
			cnt = map["c3"]+parseInt(qty);
			map["c3"]=cnt;
		} else {
			cnt = map["c4"]+parseInt(qty);
			map["c4"]=cnt;
		}
	}
	waDataAge  = parseInt(waData);
	var waMap = {"c1":0,"c2":0, "c3":0, "c4":0};
	var channelMap = {"c1":0,"c2":0, "c3":0, "c4":0};
	
	//if ( waDataAge > 0 ){
		var chaC4 = parseInt(map["c4"]) - waDataAge;
		channelMap["c4"] = chaC4 >=0 ? chaC4: 0;
		waMap["c4"] = chaC4 >=0 ? waDataAge : parseInt(map["c4"]);
		//剩下的WA库存
		waDataAge = chaC4 < 0 ? -chaC4 :0; 
		var chaC3 = parseInt(map["c3"]) - waDataAge;
		channelMap["c3"] = chaC3 >=0 ? chaC3 : 0;
		waMap["c3"] = chaC3 >= 0 ? waDataAge : parseInt(map["c3"]);
		waDataAge = chaC3 <0 ? -chaC3 : 0;
		var chaC2 = parseInt(map["c2"]) - waDataAge;
		channelMap["c2"] = chaC2 >= 0 ?chaC2:0;
		waMap["c2"] = chaC2 >= 0 ? waDataAge : parseInt(map["c2"]);
		waDataAge = chaC2 <0 ? -chaC2 : 0;
		var chaC1 = parseInt(map["c1"]) - waDataAge;
		channelMap["c1"] = chaC1 >= 0 ? chaC1:0;
		waMap["c1"] = chaC1 >= 0 ? waDataAge : parseInt(map["c1"]);
	//}
	$("#"+chalId+"_c1").text(channelMap["c1"]);
	$("#"+chalId+"_c2").text(channelMap["c2"]);
	$("#"+chalId+"_c3").text(channelMap["c3"]);
	$("#"+chalId+"_c4").text(channelMap["c4"]);
	
	$("#"+waId+"_c1").text(waMap["c1"]);
	$("#"+waId+"_c2").text(waMap["c2"]);
	$("#"+waId+"_c3").text(waMap["c3"]);
	$("#"+waId+"_c4").text(waMap["c4"]);
	return map;
}

function showDetail( secCode, sku, channel, iswa ){
	if ( iswa == 1) {
		$("#detailtitle").text("共享占用");
	} else {
		$("#detailtitle").text("渠道占用");
	}
	jQuery.ajax({
		url: "/stockReserved/getLockDetails?secCode="+secCode+"&sku="+sku+"&channel="+channel+"&iswa="+iswa,
		type: "GET",
		success: function(data) {
			$("#dialog-lock-detail .modal-body p").html(data);
			$("#dialog-lock-detail").modal('show');
		}
	});
}


