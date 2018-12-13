var ordersource=[];
var question1Level2data=[];
var question1Level3data=[];
var categoryLevelTwodata=[];
var categoryLevelThreedata=[];
var remark5_hidedata=[];
var company_hidedata=[];
var sfqd = "";
var yzrw1="";
var yzrw2="";
var yzrw3="";
var sjsl="";
var gdqx="";
var oldflag;
window.question1Level2copy="";
window.question1Level3copy="";
var VARS={
	'A':null,
	'B':null,
	'C':null
};

columns1=[];
columns2=[];
columns3=[];
columns1=[[
	//{'field':"ck",'title':"",'width':"",checkbox:true},
	{'field':"appealCount1",align:'center','title':"上诉次数",'width':"80"},
	{'field':"appealCount2",align:'center','title':"中间结果上诉次数",'width':"140"},
	{'field':"feedBackCount",align:'center','title':"用户咨询次数",'width':"120"},
	{'field':"company",align:'center','title':"工贸",'width':"90"},
	{'field':"phone",align:'center','title':"物流中心",'width':"160"},
	{'field':"id",align:'center','title':"工单号",'width':"120"},
	{'field':"question1Level1",align:'center','title':"责任位一",'width':"85"},
	{'field':"question1Level2",align:'center','title':"责任位二",'width':"85"},
	{'field':"question1Level3",align:'center','title':"责任位三",'width':"85"},
	{'field':"workOrderTo",align:'center','title':"工单去向",'width':"85"},
	/*{'field':"categoryLevelOne",'title':"处理分类",'width':"85"},
	 {'field':"categoryLevelTwo",'title':"商城问题一级",'width':"85"},
	 {'field':"categoryLevelThree",'title':"商城问题二级",'width':"85"},*/
	{'field':"netOrderId",align:'center','title':"网单号",'width':"160"},
	{'field':"orderId",align:'center','title':"订单号",'width':"180"},
	{'field':"tbOrderSn",align:'center','title':"TB单号",'width':"140"},
	{'field':"remark5",align:'center','title':"订单来源",'width':"150"},
	{'field':"workStatus",align:'center','title':"工单状态",'width':"80"},
	{'field':"productAmount",align:'center','title':"网单金额",'width':"80"},
	{'field':"insertTime",align:'center','title':"创建时间",'width':"150"},
	{'field':"workOrderTime",align:'center','title':"中间结果回复时间",'width':"140"},
	{'field':"context",'title':"反馈内容",'width':"340"},
	{'field':"remark3",align:'center','title':"创建人",'width':"90"},
	{'field':"remark1",align:'center','title':"中间结果操作人",'width':"90"},
	{'field':"remark2",align:'center','title':"最终结果操作人",'width':"90"},
	{'field':"closeType",align:'center','title':"工单关闭原因",'width':"100"},
	{'field':"sqmCount",align:'center','title':"SQM单量",'width':"100"},
	{'field':"desideText",align:'center','title':"传送状态",'width':"260"},
	{'field':"workCreateTime",align:'center','title':"创建时间",'width':"140"},
	{'field':"storeType",align:'center','title':"库存类型",'width':"140"},
	{'field':"channelName",align:'center','title':"订单渠道",'width':"140"}

]];
columns2=[[
//		{'field':"ck",'title':"",'width':"",checkbox:true},
	{'field':"question1Level1",'title':"责任位一",'width':"120"},
	{'field':"question1Level2",'title':"责任位二",'width':"120"},
	{'field':"unsettledNum",'title':"未处理条数",'width':"100"},
	{'field':"middleNum",'title':"中间结果条数",'width':"100"},
	{'field':"finallyNum",'title':"最终结果条数",'width':"100"},
	{'field':"finishPercent",'title':"工单处理完成率",'width':"100"},
]];
columns3=[[
//		{'field':"ck",'title':"",'width':"",checkbox:true},
	{'field':"wangId",'title':"人员",'width':"70"},
	{'field':"company",'title':"工贸",'width':"70"},
	{'field':"appealCount2",'title':"中间结果上诉次数",'width':"70"},
	{'field':"unsettledNum",'title':"未处理条数",'width':"100"},
	{'field':"middleNum",'title':"中间结果条数",'width':"100"},
	{'field':"finallyNum",'title':"最终结果条数",'width':"100"},
	{'field':"finishPercent",'title':"工单处理完成率",'width':"100"},
]];

//导出工单EXCEL参数
var WORK_ORDER_EXCEL={
	'Q_orderId_search':null,
//		'Q_orderCome_search':null,
	'Q_netOrderId_search':null,
	'Q_startTime_search':null,
	'Q_endTime_search':null,
	'Q_workStatus':null,
	'Q_level1_search':null,
	'Q_level2_search':null,
	'Q_level3_search':null,
	'Q_workOrderTo':null,
	'Q_channelName_search':null,
	'Q_company_search':null,
	'Q_person_search':null,
	'Q_addsearch_search':null,
	'Q_remark5_search':null,
	'Q_clientName':null,
	'Q_clientTel':null,
	'Q_phone_search':null,
	'Q_closeType_search':null,
	'Q_complaintFlg_search':null,
	'Q_id_search':null,
	'Q_centerType':null,

};
//导出责任统计EXCEL参数
var DUTY_STATISTIC_EXCEL={
	'startTime_search2':null,
	'endTime_search2':null,
	'level1_search2':null,
};
//导出人员统计EXCEL参数
var PERSONNEL_STATISTIC_EXCEL={
	'startTime_search3':null,
	'endTime_search3':null,
	'person_search3':null,
};
var LEVEL1_QJ={};
var LEVEL2_QJ={};
var LEVEL1_CA={};

//获取所有人员
$(function() {

	$.ajax({
		type : "get",
		url : "/woUser/getList",
		data : {
      page:1,
      rows:10000
		},
		dataType : "json",
		success : function(data) {
			data=data.rows;
			a = [];
			a.id = "";
			a.username = "-请选择-";
			data.unshift(a);
			$('#wangId_hide').combobox('loadData', data);
		}
	});

});
$('#wangId_hide').combobox( //人员隐藏
	{
		valueField : 'userId',
		textField : 'userName',
		onChange: function(n,o){
		},
		onLoadSuccess : function(data) {
			$("#wangId_hide + .combo").children("input").addClass("white");
		},
		onSelect : function(record) {
			$("#wangId").val(record.userName);
			$("#remark4").val(record.userId);
		}
	});
//获取责任位
$(function() {

	$.ajax({
		type : "get",
		url : "/dictionary/getdictionary.ajax",
		data : {
			"value_set_id" : "duty_1"
		},
		dataType : "json",
		success : function(data) {
			//复制对象
			tmp = data.slice(0);

			$('#question1Level1').combobox('loadData', tmp);
			LEVEL1_QJ=tmp;
			a = [];
			a.value = "";
			a.valueMeaning = "-请选择-";
			data.unshift(a);
			$('#level1_search').combobox('loadData', data);
			$('#level1_search2').combobox('loadData', data);
		}
	});

});
//获取二级责任位
$(function() {

	$.ajax({
		type : "get",
		url : "/dictionary/getdictionary.ajax",
		data : {
			"value_set_id" : "duty_2"
		},
		dataType : "json",
		success : function(data) {
			//复制对象
			tmp = data.slice(0);
			LEVEL2_QJ=tmp;
			// a = [];
			// a.value = "";
			// a.valueMeaning = "-请选择-";
			// data.unshift(a);
			// $('#level1_search').combobox('loadData', data);
			// $('#level1_search2').combobox('loadData', data);
		}
	});

});
//获取处理分类
/*
 $(function() {

 $.ajax({
 type : "get",
 url : "/dictionary/getdictionary.ajax",
 data : {
 "value_set_id" : "category_1"
 },
 dataType : "json",
 success : function(data) {
 //复制对象
 tmp = data.slice(0);
 $('#categoryLevelOne').combobox('loadData', tmp);
 LEVEL1_CA=tmp;
 a = [];
 a.value = "";
 a.valueMeaning = "-请选择-";
 data.unshift(a);
 $('#category1_search').combobox('loadData', data);
 $('#categoryLevelOne').combobox('loadData', data);
 // $('#category2_search').combobox('loadData', data);
 // $('#category3_search').combobox('loadData', data);
 }
 });

 });*/

//添加/修改责任位1
$('#question1Level1').combobox(
	{

		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(param) {
		},
		onSelect : function(record) {

			var n = record.value;
			$('#question1Level2').combobox({
				url : '/dictionary/gettwomenu.ajax?value_set_id=duty_2&parent_value=' + n,
				valueField : 'value',
				textField : 'valueMeaning',
				method : 'GET',
				onLoadSuccess : function(data) {

					question1Level2data=data;

					$('#question1Level3').combobox('setText','').combobox('setValue',"");
					$('#question1Level3').combobox('loadData', {});
//						$('#question1Level2').combobox('setText', '-请选择-');
					//存在一个bug 有些时候选择责任位1时责任位2就置灰了,原因暂未找到,先手动取消下。
					$('#question1Level2').combobox('enable');
					if(data.length>0){
						$('#question1Level2').combobox('setText','');

					}
				}
			});
		}
	});
$('#question1Level2').combobox({
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(param) {
		},
		onChange: function(n,o){
			//调用通过网单与责任位查询信息
			var nText="";
			for(var i=0;i<question1Level2data.length;i++){
				if(n==question1Level2data[i].value){
					nText=question1Level2data[i].valueMeaning;
					break;
				}
			}
			find(nText,null);
		},
		onSelect : function(record) {
			var m = record.value;
			$('#question1Level3').combobox({
				url : '/dictionary/gettwomenu.ajax?value_set_id=duty_3&parent_value=' + m,
				valueField : 'value',
				textField : 'valueMeaning',
				method : 'GET',
				onLoadSuccess : function(data) {
					question1Level3data=data;
					$('#question1Level3').combobox('enable');
					sjsl=data.length;
					if(data.length>0){
						$('#question1Level3').combobox('setText','');
					}else{
					}
				}
			});
		}
	});



$('#question1Level3').combobox(
	{
		valueField: 'value',
		textField: 'valueMeaning',
		onLoadSuccess: function (param) {
		},
		onChange: function (n, o) {
			//调用通过网单与责任位查询信息
			var nText = "";
			for (var i = 0; i < question1Level3data.length; i++) {
				if (n == question1Level3data[i].value) {
					nText = question1Level3data[i].valueMeaning;
					break;
				}
			}
			finds($("#question1Level2").combobox('getText'),null,null,nText);
		},
	});

function finds(nQuestion1Level2,nRemark5,nCompany,question1Level3){
	netOrderId = $("#netOrderId").val();//网单号
	remark5="";
	company="";
	if ($('.askFlg').attr('checked')){
		if(nRemark5!=null){
			remark5=nRemark5;//订单来源
		}else{
			remark5=$("#remark5_hide").combobox('getText');
		}

		if(nCompany!=null){
			company=nCompany;//订单来源
		}else{
			company=$("#company_hide").combobox('getText');
		}
	}else{
		remark5=$("#remark5").val();//订单来源

		if(nCompany!=null){
			company=nCompany;//订单来源
		}else{
			if($("#company_hide").parent("td").is(":hidden")){//如果下拉工贸是隐藏的
				company =$("#company").val();//取input工贸
			}else{
				company=$("#company_hide").combobox('getText');//取下拉工贸
			}
		}
	}
	remark5=remark5!="-请选择-"?remark5:"";
	company=company!="-请选择-"?company:"";
	question1Level1=$("#question1Level1").combobox('getText');

	question1Level2="";
	if(nQuestion1Level2!=null){
		question1Level2=nQuestion1Level2;
	}else{
		question1Level2=$("#question1Level2").combobox('getText');
	}
	if(question1Level2==""){return;}

	//$("#context").css("display", "none");
	// 查询人员
	data=[];

	if(question1Level1=="物流类"){
		if ($('.askFlg').attr('checked')){
			$.messager.alert('系统提示', '咨询类工单不能录入物流类责任位工单', 'warning');
			return;
		}
	}
	data={
		"question1level1_search" : question1Level1,
		"question1level2_search" : question1Level2,
		"company_search":company,
		"ordercome_search":remark5,
	};

	//判断是否是原责任位2
	if(question1Level2==""||question1Level2==$("#question1Level2-hidden").val()){
		return;
	}
	if(netOrderId==""){
		return;
	}

	$("#contextDiv2").html("");
	//$("#context").css("display", "none");
	// 查询评论信息
	$.ajax({
		type : "post",
		url : '/comment/selectcomment2.ajax',
		data : {
			"netOrderId" : netOrderId,
			"question1Level2" : question1Level2,
			"question1Level3" : question1Level3
		},
		dataType : "json",
		async : false,
		success : function(result) {
			if(result.success){
				if(result.message==1){
					$("#complaintFlg").attr("checked",true);
//					$("#HPBtn").attr("class", "easyui-linkbutton l-btn l-btn-small l-btn-plain");
				}else{
					$("#complaintFlg").attr("checked",false);
//					$("#HPBtn").attr("class", "easyui-linkbutton l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled");
				}
				result=result.result;
				if(result!=null&&result.length>0){
					for (var i = 0; i < result.length; i++) {
						var oldText = $("#contextDiv2").html();
						if (i > 0) {
							oldText += '<br/>';
						} else {
							oldText = '';
						}
						$("#contextDiv2").html(
							oldText + result[i].addtime + "  "
							+ result[i].adduser + "  "
							+ result[i].context);

					}

					// 查询图片信息
					$.ajax({
						type : "post",
						url : '/image/findreviewimagebyid.ajax',
						data : {
							reviewId :  result[0].reviewid
						},
						dataType : "json",
						async : false,
						success : function(data) {
							if(data.success){
								window.REVIEWID=result[0].reviewid;
								if(data.result){
									$("#tdStr").text("图片操作:");
									$("#down").show();
									$("#up").hide();
									$('#askFlg').attr('disabled', false);
//									$("#btnDownload").linkbutton("enable");
									//从新加载iframe
									$('#win').attr('src', '/img.html');
								}else{
									window.REVIEWID=null;
									//$("#tdStr").text("上传图片:");
									$("#up").show();
									$("#down").hide();
									$("#nullImg").hide();
									$('#askFlg').attr('disabled', false);$("#tdStr").text("上传图片:");
									$("#up").show();
									$("#down").hide();
									$("#nullImg").hide();
									$('#askFlg').attr('disabled', false);
								}
//								img=imageStr;
							}else{
								$.messager.alert('系统提示', data.message, 'warning');
							}
						}
					});
					$.messager.alert('系统提示', '存在网单号责任位一致的工单！', 'warning');
				}else{
					if(window.REVIEWID!=null){
						//$("#tdStr").text("上传图片:");
						$("#up").show();
						$("#down").hide();
						$("#nullImg").hide();
						$('#askFlg').attr('disabled', false);$("#tdStr").text("上传图片:");
						$("#up").show();
						$("#down").hide();
						$("#nullImg").hide();
						$('#askFlg').attr('disabled', false);
					}
				}
			}
		}
	});
	$("#backDiv2").html("");
	$("#backDiv3").html("");
	$("#finalResultDiv").html("");
	// 查询中间结果
	$.ajax({
		type : "post",
		url : '/middleresult/selectmiddleresult2.ajax',
		data : {
			"netOrderId" : netOrderId,
			"question1Level2" : question1Level2
		},
		dataType : "json",
		async : false,
		success : function(result) {
			if(result!=null&&result.length>0){
				for (var i = 0; i < result.length; i++) {
					var oldText = $("#backDiv2").html();
					if (i > 0) {
						oldText += '<br/>';
					} else {
						oldText = '';
					}
					$("#backDiv2").html(
						oldText + result[i].addtime + "  "
						+ result[i].adduser + "  "
						+ result[i].middleContext);
					// if(i+1==3)
					// {
					// 	if(row.workOrderTo=='HP')
					// 	{
					// 		break;
					// 	}
					// }
				}
			}
		}
	});

}





$('#categoryLevelOne').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(param) {
		},
		onSelect : function(record) {
			var n = record.value;
			$('#categoryLevelTwo').combobox({
				url : '/dictionary/gettwomenu.ajax?value_set_id=category_2&parent_value=' + n,
				valueField : 'value',
				textField : 'valueMeaning',
				method : 'GET',
				onLoadSuccess : function(data) {
					categoryLevelTwodata=data;
//						$('#question1Level2').combobox('setText', '-请选择-');
					//存在一个bug 有些时候选择责任位1时责任位2就置灰了,原因暂未找到,先手动取消下。
					$('#categoryLevelTwo').combobox('enable');
					if(data.length>0){
						$('#categoryLevelTwo').combobox('setText','请选择');
					}
				}
			});

			$('#categoryLevelThree').combobox('setText','');
		}
	});
$('#categoryLevelTwo').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(param) {
		},
		onSelect : function(record) {
			var n = record.value;
			$('#categoryLevelThree').combobox({
				url : '/dictionary/gettwomenu.ajax?value_set_id=category_3&parent_value=' + n,
				valueField : 'value',
				textField : 'valueMeaning',
				method : 'GET',
				onLoadSuccess : function(data) {
					categoryLevelThreedata=data;
					$('#categoryLevelThree').combobox('enable');
					if(data.length>0){
						$('#categoryLevelThree').combobox('setText','请选择');
					}
				}
			});
		}
	});






//界面责任位1
$('#level1_search').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(data) {

			$('#level1_search').combobox('setValue', "").combobox(
				'setText', '-请选择-');
			$('#level2_search').combobox('setValue', "").combobox('setText', '-请选择-');
		},
		onSelect : function(record) {

			if (record.valueMeaning == '-请选择-') {
				$('#level2_search').combobox('setValue', '');
				$('#level2_search').combobox('setText', '-请选择-');
				$('#level2_search').combobox('loadData', {});
                $('#level3_search').combobox('setValue', '');
                $('#level3_search').combobox('setText', '-请选择-');
				return;
			}
			var n = record.value;
			$.ajax({
				type : "GET",
				url : "/dictionary/gettwomenu.ajax",
				data : {
					"value_set_id":"duty_2",
					"parent_value" : n
				},
				dataType : "json",
				success : function(data) {
					a = [];
					a.value = "";
					a.valueMeaning = "-请选择-";
					data.unshift(a);
					$('#level2_search').combobox('loadData', data);

				}
			});
			$('#level2_search').combobox({
				valueField : 'value',
				textField : 'valueMeaning',
				onLoadSuccess : function(data) {
					if ($('#level1_search').combobox('getText') != '-请选择-') {
						$('#level2_search').combobox('setText', '-请选择-');
						$('#level3_search').combobox('loadData', "").combobox('setText', '-请选择-');

					}

				},
			});
		}
	});
//界面责任位2
$('#level2_search').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(data) {
			$('#level2_search').combobox('setValue', "").combobox(
				'setText', '-请选择-');
			$('#level3_search').combobox('setValue', "").combobox('setText', '');
		},
		onSelect : function(record) {
			if (record.valueMeaning == '-请选择-') {
				$('#level3_search').combobox('setValue', '');
				// $('#level3_search').combobox('setText', '');
				$('#level3_search').combobox('loadData', {});
                $('#level3_search').combobox('setText', '-请选择-');
				return;
			}
			var n = record.value;
			$.ajax({
				type : "GET",
				url : "/dictionary/gettwomenu.ajax",
				data : {
					"value_set_id":"duty_3",
					"parent_value" : n
				},
				dataType : "json",
				success : function(data) {
					a = [];
					a.value = "";
					a.valueMeaning = "-请选择-";
					data.unshift(a);
					$('#level3_search').combobox('loadData', data);

				}
			});
			$('#level3_search').combobox({
				valueField : 'value',
				textField : 'valueMeaning',
				onLoadSuccess : function(data) {
					if (data.length==1) {
                        $('#level3_search').combobox('setText', '');
                    }else {
                        $('#level3_search').combobox('setText', '-请选择-');
                    }
				},
			});
		}
	});
//界面责任位1
$('#level1_search2').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(data) {
			$('#level1_search2').combobox('setValue', "").combobox(
				'setText', '-请选择-');

		},
	});
//责任位类别
/*$('#category1_search').combobox(
 {
 valueField : 'value',
 textField : 'valueMeaning',
 onLoadSuccess : function(data) {
 $('#category1_search').combobox('setValue', "").combobox(
 'setText', '-请选择-');
 $('#category2_search').combobox('setValue', "").combobox('setText', '');
 },
 onSelect : function(record) {
 if ($('#category1_search').combobox('getText') == '-请选择-') {
 $('#category2_search').combobox('setValue', '');
 $('#category2_search').combobox('setText', '');
 $('#category2_search').combobox('loadData', {});
 return;
 }
 var n = record.value;
 $.ajax({
 type : "GET",
 url : "/dictionary/gettwomenu.ajax",
 data : {
 "value_set_id":"category_2",
 "parent_value" : n
 },
 dataType : "json",
 success : function(data) {
 a = [];
 a.value = "";
 a.valueMeaning = "-请选择-";
 data.unshift(a);
 $('#category2_search').combobox('loadData', data);

 }
 });
 }
 });
 $('#category2_search').combobox(
 {
 valueField : 'value',
 textField : 'valueMeaning',
 onLoadSuccess : function(data) {
 },
 onSelect : function(record) {
 if ($('#category2_search').combobox('getText') == '-请选择-') {
 $('#category3_search').combobox('setValue', '');
 $('#category3_search').combobox('setText', '');
 $('#category3_search').combobox('loadData', {});
 return;
 }
 var n = record.value;
 $.ajax({
 type : "GET",
 url : "/dictionary/gettwomenu.ajax",
 data : {
 "value_set_id":"category_3",
 "parent_value" : n
 },
 dataType : "json",
 success : function(data) {
 a = [];
 a.value = "";
 a.valueMeaning = "-请选择-";
 data.unshift(a);
 $('#category3_search').combobox('loadData', data);

 }
 });
 }
 });*/
//获取订单来源
$(function() {
	$.ajax({
		type : "GET",
		url : "/dictionary/getdictionary.ajax",
		data : {
			"value_set_id" : "order_source"
		},
		dataType : "json",
		success : function(data) {
			a = [];
			a.value = "";
			a.valueMeaning = "-请选择-";
			data.unshift(a);
//			remark5_hidedata=data;
			$('#remark5_search').combobox('loadData', data);
			$('#remark5_hide').combobox('loadData', data);
//			ordersource=data;
			var orderSn = getUrlParam("createworkorder");
			//通过网单号查询
			if(orderSn!="" && orderSn!=null){
				isKeyBtn=true;
				isKeyBtns=true;
				newBackList();
				$.ajax({
					type : "post",
					url : '/workorder/getnetorder.ajax',
					data : {
						"netOrderId" : orderSn
					},
					dataType : "json",
					async : false,
					success: function(data){

						$('#orderId').val('');
						$('#orderIds').val('');
						$('#productName').val('');
						$('#number').val('');
						$('#netOrderId').val(orderSn);
						$('#sku').val('');
						$('#productAmount').val('');
						$('#buyerMobile').val('');
						$('#buyer').val('');
						$('#netPointId').val('');
//							$('#createTime').val('');
//							$('#payTime').val('');
						$('#createTime').datetimebox("clear");
						$('#payTime').datetimebox("clear");
						$('#regionName').val('');
						$('#address').val('');
						$('#orderCome').val('');
						$('#company').val('');
						$('#wangId').val('');
						$('#phone').val('');
						$('#remark5').val('');
						$('#orderCome').val('');
						$('#storeId').val('');
						if (data.success) {

							//启用责任位1 2
							$('#question1Level1').combobox('enable');
							$('#question1Level2').combobox('enable');
							var pprm = data.result;
//								pprm = jQuery.parseJSON(pprm);
//								var prm = pprm.orderProducts;
							$('#orderId').val(pprm.orderSn);
							$("#orderIds").val(pprm.orderSn);
							$('#productName').val(pprm.productName);
							$('#number').val(pprm.number);
							$('#sku').val(pprm.sku);
							$('#productType').val(pprm.productName);
							$('#productAmount').val(pprm.productAmount);
							$('#buyer').val(pprm.buyer);
							$('#buyerMobile').val(pprm.buyerMobile);
							$('#netPointId').val(pprm.netPointId);
							//var newTime = new Date(pprm.createTime*1000);
//									$('#createTime').val(newTime.format("yyyy-MM-dd hh:mm:ss"));
							//var str=newTime.format("yyyy-MM-dd hh:mm:ss");
							$("#createTime").datetimebox("setValue", pprm.addtime);
							var newTime1 = new Date(pprm.payTime*1000);
//									$('#payTime').val(newTime1.format("yyyy-MM-dd hh:mm:ss"));
							var str2=newTime.format("yyyy-MM-dd hh:mm:ss");
							$("#payTime").datetimebox("setValue", str2);
							$('#regionName').val(pprm.regionName);
							$('#company').val("");
							$('#address').val(pprm.address);
							$('#fm').form('validate');
//								$('#orderCome').val('SG');
							$('#orderCome').val(pprm.shippingTime);
							$('#company').val(pprm.company);
							if(pprm.company==null||pprm.company==""){
//									alert("a ");
								$('#company_hide').parent("td").show();
								$('#company_hide').combobox("setText","-请选择-").combobox("setValue","");
								$("#company_hide + .combo").children("input").addClass("white");
								$('#company').parent("td").hide();
							}else{
								$('#company_hide').parent("td").hide();
								$('#company').parent("td").show();
							}
							var n=pprm.source;
							for(var i=0;i<ordersource.length;i++){
								if(ordersource[i].value==n){
									$('#remark5').val(ordersource[i].valueMeaning);
									find(null,ordersource[i].valueMeaning);
								}
							}
							$('#storeId').val(pprm.storeId);
//								var ptrm = pprm.ordWfRegion;

//									$('#wangId').val(ptrm.personTradeCfg.commissioner);
							$('#phone').val(pprm.phone);

//								$("#corderPrimary").val(pprm.corderPrimary);
//								$("#orderPrimary").val(pprm.orderPrimary);
						} else {
							$('#company_hide').parent("td").hide();
							$('#company').parent("td").show();
							$.messager.alert('系统提示', data.message, 'warning');
						}

					}});
			}
		}
	});
});
//订单来源
$('#remark5_search').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
//			onLoadSuccess : function(data) {
//				$('#remark5_search').combobox('setValue', "").combobox(
//						'setText', '-请选择-');
//			},
	});
$('#remark5_hide').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onChange: function(n,o){
			//调用通过网单与责任位查询信息
			var nRemark5="";
			for(var i=0;i<remark5_hidedata.length;i++){
				if(n==remark5_hidedata[i].value){
					nRemark5=remark5_hidedata[i].valueMeaning;
					break;
				}
			}
			find($("#question1Level2").combobox('getText'),nRemark5);
		},
	});
$('#company_hide').combobox( //工贸隐藏
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onChange: function(n,o){
			//调用通过网单与责任位查询信息
			var nCompany="";
			for(var i=0;i<company_hidedata.length;i++){
				if(n==company_hidedata[i].value){
					nCompany=company_hidedata[i].valueMeaning;
					break;
				}
			}
			find(null,null,nCompany);
		},
	});
//获取工单状态
$(function() {
	$.ajax({
		type : "GET",
		url : "/dictionary/getdictionary.ajax",
		data : {
			"value_set_id" : "work_status"
		},
		dataType : "json",
		success : function(data) {
			a = [];
			a.value = "";
			a.valueMeaning = "-请选择-";
			data.unshift(a);
			$('#workStatus').combobox('loadData', data);
		}
	});
});
// 工单状态
$('#workStatus').combobox({
	valueField : 'value',
	textField : 'valueMeaning',
	onLoadSuccess : function(data) {
		$('#workStatus').combobox('setValue', "").combobox('setText', '-请选择-');
	},
});
//设置工单去向
$(function() {
	var wOrderTo ='[{"id":"-请选择-","text":"-请选择-"},{"id":"SQM","text":"SQM"},{"id":"HP","text":"HP"},{"id":"SJZX","text":"SJZX"}]';
	var data=$.parseJSON(wOrderTo);
	$('#workOrderTo').combobox("loadData", data);
	$('#workOrderTo').combobox('setValue', "").combobox('setText', '-请选择-');
})
//设置订单渠道查询条件
$(function() {
	var wOrderTo ='[{"id":"-请选择-","text":"-请选择-"},{"id":"大客户渠道","text":"大客户渠道"},{"id":"京东渠道","text":"京东渠道"},{"id":"模卡渠道","text":"模卡渠道"},{"id":"微店渠道","text":"微店渠道"},{"id":"商城渠道","text":"商城渠道"},{"id":"天猫渠道","text":"天猫渠道"},{"id":"易迅渠道","text":"易迅渠道"},{"id":"海朋渠道","text":"海朋渠道"},{"id":"统帅渠道","text":"统帅渠道"},{"id":"无渠道","text":"无渠道"}]';
	var data=$.parseJSON(wOrderTo);
	$('#channelName_search').combobox("loadData", data);
	$('#channelName_search').combobox('setValue', "").combobox('setText', '-请选择-');
})
//设置工单类别
$(function() {
	var cenType ='[{"id":"-请选择-","text":"-请选择-"},{"id":"WA类","text":"WA类"},{"id":"OTO类","text":"OTO类"},{"id":"小电类","text":"小电类"}]';
	var data=$.parseJSON(cenType);
	$('#cType').combobox("loadData", data);
	$('#cType').combobox('setValue', "").combobox('setText', '-请选择-');
})
//获取工单关闭原因
$(function() {
	$.ajax({
		type : "GET",
		url : "/dictionary/getdictionary.ajax",
		data : {
			"value_set_id" : "close_type"
		},
		dataType : "json",
		success : function(data) {
			//复制对象
			tmp = data.slice(0);
			window.CLOSETYPE=tmp;
			a = [];
			a.value = "";
			a.valueMeaning = "-请选择-";
			data.unshift(a);
			$('#closeType_search').combobox('loadData', data);

		}
	});
});
// 工单状态
$('#closeType_search').combobox({
	valueField : 'value',
	textField : 'valueMeaning',
	onLoadSuccess : function(data) {
		$('#workStatus').combobox('setValue', "").combobox('setText', '-请选择-');
	},
});

////获取工单来源
//$(function() {
//	$.ajax({
//		type : "GET",
//		url : "/dictionary/getdictionary.ajax",
//		data : {
//			"value_set_id" : "work_source"
//		},
//		dataType : "json",
//		success : function(data) {
//			a = [];
//			a.value = "";
//			a.valueMeaning = "-请选择-";
//			data.unshift(a);
//			$('#orderCome_search').combobox('loadData', data);
//		}
//	});
//});
//// 工单来源
//$('#orderCome_search').combobox(
//		{
//			valueField : 'value',
//			textField : 'valueMeaning',
//			onLoadSuccess : function(data) {
//				$('#orderCome_search').combobox('setValue', "").combobox(
//						'setText', '-请选择-');
//			},
//		});



//获取工贸
$(function() {

	$.ajax({
		type : "get",
		url : "/dictionary/getdictionary.ajax",
		data : {
			"value_set_id" : "company"
		},
		dataType : "json",
		success : function(data) {
			a = [];
			a.value = "";
			a.valueMeaning = "-请选择-";
			data.unshift(a);
			company_hidedata=data;
			$('#company_hide').combobox('loadData', data);
			$('#company_search').combobox('loadData', data);
		}
	});

});

//界面工贸
$('#company_search').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(data) {
			$('#company_search').combobox('setValue', "").combobox(
				'setText', '-请选择-');
			$('#phone_search').combobox('setValue', "").combobox('setText', '');
		},
		onSelect : function(record) {
			if (record.valueMeaning == '-请选择-') {
				$('#phone_search').combobox('setValue', '');
				$('#phone_search').combobox('setText', '');
				$('#phone_search').combobox('loadData', {});
				return;
			}
			var n = record.value;
			$.ajax({
				type : "GET",
				url : "/dictionary/gettwomenu.ajax",
				data : {
					"value_set_id":"logistics_center",
					"parent_value" : n
				},
				dataType : "json",
				success : function(data) {
					var a = {};
					a.value = "";
					a.valueMeaning = "-请选择-";
					data.unshift(a);
					$('#phone_search').combobox('loadData', data);
				}
			});
			$('#phone_search').combobox({
				valueField : 'value',
				textField : 'valueMeaning',
				onLoadSuccess : function(data) {
					if ($('#company_search').combobox('getText') != '-请选择-') {
						$('#phone_search').combobox('setText', '-请选择-');
					}

				},
			});
		}
	});
//界面物流中心
$('#phone_search').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(data) {
			$('#phone_search').combobox('setValue', "").combobox(
				'setText', '-请选择-');

		},
	});


// 工单分页查询
function searchReview() {
	setFirstPage("#dg");
	var selectedvalue=$("input[name='tab']:checked").val();
	if(selectedvalue==1){
		startTime= $('#startTime_search').combobox('getValue');
		endTime= $('#endTime_search').combobox('getValue');
		if(startTime>endTime){
			$.messager.alert('系统提示', '开始时间不能晚于结束时间!', 'warning');
			return;
		}
		WORK_ORDER_EXCEL.Q_orderId_search= $('#orderId_search').val();
//		WORK_ORDER_EXCEL.Q_orderCome_search= $('#orderCome_search').combobox('getValue');
		WORK_ORDER_EXCEL.Q_netOrderId_search= $('#netOrderId_search').val();
		WORK_ORDER_EXCEL.Q_startTime_search= $('#startTime_search').combobox('getValue');
		WORK_ORDER_EXCEL.Q_endTime_search= $('#endTime_search').combobox('getValue');
		WORK_ORDER_EXCEL.Q_workStatus= $('#workStatus').combobox('getValue');
		WORK_ORDER_EXCEL.Q_level1_search= $('#level1_search').combobox('getText') != '-请选择-' ? $('#level1_search').combobox('getText') : "";
		WORK_ORDER_EXCEL.Q_level2_search= $('#level2_search').combobox('getText') != '-请选择-' ? $('#level2_search').combobox('getText') : "";
		WORK_ORDER_EXCEL.Q_level3_search= $('#level3_search').combobox('getText') != '-请选择-' ? $('#level3_search').combobox('getText') : "";
		WORK_ORDER_EXCEL.Q_workOrderTo= $('#workOrderTo').combobox('getText') != '-请选择-' ? $('#workOrderTo').combobox('getText') : "";
		WORK_ORDER_EXCEL.Q_channelName_search= $('#channelName_search').combobox('getText') != '-请选择-' ? $('#channelName_search').combobox('getText') : "";
		WORK_ORDER_EXCEL.Q_centerType= $('#cType').combobox('getText') != '-请选择-' ? $('#cType').combobox('getText') : "";
		WORK_ORDER_EXCEL.Q_company_search= $('#company_search').combobox('getText') != '-请选择-' ? $('#company_search').combobox('getText') : "";
		WORK_ORDER_EXCEL.Q_person_search= $('#person_search').val();
		WORK_ORDER_EXCEL.Q_addsearch_search= $('#addsearch_search').val();
		WORK_ORDER_EXCEL.Q_remark5_search= $('#remark5_search').combobox('getText') != '-请选择-' ? $('#remark5_search').combobox('getText'): "";
		WORK_ORDER_EXCEL.Q_clientName= $('#clientName').val();
		WORK_ORDER_EXCEL.Q_clientTel= $('#clientTel').val();
		WORK_ORDER_EXCEL.Q_phone_search= $('#phone_search').combobox('getText') != '-请选择-' ? $('#phone_search').combobox('getText') : "";
		WORK_ORDER_EXCEL.Q_closeType_search=$('#closeType_search').combobox('getValue');
		WORK_ORDER_EXCEL.Q_complaintFlg_search= $('#complaintFlg_search').combobox('getValue');
		WORK_ORDER_EXCEL.Q_id_search= $('#id_search').val();
        $('#dg')
			.datagrid(
				{
					url : '/workorder/workorderpage.ajax',
					columns:columns1,
					pageSize: 50,//每页显示的记录条数，默认为10
					pageList: [50,100,200],//可以设置每页记录条数的列表
					queryParams : {
						'orderId_search' : $('#orderId_search').val(),
//								'orderCome_search' : $('#orderCome_search').combobox('getValue'),
						'netOrderId_search' : $('#netOrderId_search').val(),
						'startTime_search' : $('#startTime_search').combobox('getValue'),
						'endTime_search' : $('#endTime_search').combobox('getValue'),
						'workStatus' : $('#workStatus').combobox('getValue'),
						'cType' : $('#cType').combobox(
							'getText') != '-请选择-' ? $('#cType')
							.combobox('getText') : "",
						'workOrderTo' : $('#workOrderTo').combobox(
							'getText') != '-请选择-' ? $('#workOrderTo')
							.combobox('getText') : "",
						'level1_search' : $('#level1_search').combobox(
							'getText') != '-请选择-' ? $('#level1_search')
							.combobox('getText') : "",
						'level2_search' : $('#level2_search').combobox(
							'getText') != '-请选择-' ? $('#level2_search')
							.combobox('getText') : "",
						'level3_search' : $('#level3_search').combobox(
							'getText') != '-请选择-' ? $('#level3_search')
							.combobox('getText') : "",
						'company_search' : $('#company_search').combobox(
							'getText') != '-请选择-' ? $('#company_search')
							.combobox('getText') : "",
						'phone_search' :WORK_ORDER_EXCEL.Q_phone_search,
						'person_search' : $('#person_search').val(),
						'addsearch_search' : $('#addsearch_search').val(),
						'remark5_search' : $('#remark5_search').combobox(
							'getText') != '-请选择-' ? $('#remark5_search')
							.combobox('getText')
							: "",
						'clientName' : $('#clientName').val(),
						'clientTel' : $('#clientTel').val(),
						'closeType_search':$('#closeType_search').combobox('getValue'),
						'complaintFlg_search':$('#complaintFlg_search').combobox('getValue'),
						'id_search':$('#id_search').val(),
						'storeId_search':$('#storeId_search').val(),
						'channelName_search' : $('#channelName_search').combobox(
							'getText') != '-请选择-' ? $('#channelName_search')
							.combobox('getText') : ""
						/*'category1_search' : $('#category1_search').combobox(
						 'getText') != '-请选择-' ? $('#category1_search')
						 .combobox('getText') : "",
						 'category2_search' : $('#category2_search').combobox(
						 'getText') != '-请选择-' ? $('#category2_search')
						 .combobox('getText') : "",
						 'category3_search' : $('#category3_search').combobox(
						 'getText') != '-请选择-' ? $('#category3_search')
						 .combobox('getText') : ""*/
					},
					onClickRow: function (index, row) { //easyui封装好的时间（被单机行的索引，被单击行的值）
						if(VARS.A=="TRUE"){
							//调用查看
							viewPool(row);
							VARS.A=null;
						}
					},
					loadMsg : '数据装载中......',
					onLoadSuccess : function(data) {

						// 将某个单元格设置为红色
						var panel = $(this).datagrid('getPanel');
						var tr = panel.find('div.datagrid-body tr');

						tr.each(function() {

							var td = $(this).children(
								'td[field="netOrderId"]');
							// 申述次数
							var td0 = $(this).children(
								'td[field="appealCount1"]');
							// 中间结果申诉次数
							var td1 = $(this).children(
								'td[field="appealCount2"]');
							// 用户咨询次数
							var td2 = $(this).children(
								'td[field="feedBackCount"]');
							// 工单状态
							var td3 = $(this).children(
								'td[field="workStatus"]');
							// 工单号
							var td4 = $(this).children(
								'td[field="id"]');
							var td5 = $(this).children(
							'td[field="tbOrderSn"]');
							//网单接口ID
//									var td5 = $(this).children(
//									'td[field="corderPrimary"]');
							//订单接口ID
//									var td6 = $(this).children(
//									'td[field="orderPrimary"]');
							var tdOrderId = $(this).children(
								'td[field="orderId"]');

							var tdNetOrderId = $(this).children(
								'td[field="netOrderId"]');
							var textValue = td.children("div").text();
							var textValue0 = td0.children("div").text();
							var textValue1 = td1.children("div").text();
							var textValue2 = td2.children("div").text();
							var value = td3.children("div").text();
							var textValue4 = td4.children("div").text();
//									var textValue5 = td5.children("div").text();
//									var textValue6 = td6.children("div").text();
							var orderIdValue = tdOrderId.children("div")
								.text();
							var netOrderIdValue = tdNetOrderId.children("div")
								.text();
							// 工单关闭原因
							var td7 = $(this).children(
								'td[field="closeType"]');
							var closeValue = td7.children("div").text();
							//工单关闭原因转换
							for(var a=0;a<CLOSETYPE.length;a++){
								if(closeValue==CLOSETYPE[a].value){
									closeValue=CLOSETYPE[a].valueMeaning;
									td7.children("div").html(closeValue);
									break;
								}
							}
							if (textValue0 >= 2) {
								td0.children("div").css({
									"color" : "red"
								});
							}
							if (textValue1 >= 2) {
								td1.children("div").css({
									"color" : "red"
								});
							}
							if (textValue2 >= 3) {
								// td2.children("div").css({
								// 	"color" : "red"
								// });
								td2.parent().css({
									"color" : "red"
								});
							}


							if (value == 0) {
								value = '未处理';
							} else if (value == 1) {
								value = '已确认';
							} else if (value == 2) {
								value = '中间结果';
							} else if (value == 3) {
								value = '最终结果';
							} else {
								value = '';
							}
							td3.children("div").html(value);
							// 将网单号与订单号增加超链接
							if(textValue.substr(0, 4).toUpperCase()!='WDZX'){
								//网单号
								td.children("div").html(
									//'<a href="http://www.ehaier.com/admin/corder.php?a=edit&id='
									//+ textValue5+'">'
									//+ textValue + '</a>');
									'<a href="javascript:void(0)" onclick="return lookOrderId(\''+netOrderIdValue+'\')">'
									+ netOrderIdValue + '</a>');

								//订单号字段超链接
								tdOrderId.children("div").html(
									'<a href="javascript:void(0)" onclick="return lookorderSn(\''+orderIdValue+'\')">'
									+ orderIdValue + '</a>');
							}

							td4.children("div").html(
								'<a href="javascript:void(0)" onclick="viewPools()">'
								+ textValue4 + '</a>');
						});
						// 当没有返回数据时提示信息
						if (data.total == 0 && data.rows.length == 0) {
//									$.messager.alert('系统提示', '查询无数据', 'warning');
						}
					}

				});
	}else if(selectedvalue==2){
		startTime= $('#startTime_search2').combobox('getValue');
		endTime= $('#endTime_search2').combobox('getValue');
		if(startTime>endTime){
			$.messager.alert('系统提示', '开始时间不能大于结束时间', 'warning');
			return;
		}
		DUTY_STATISTIC_EXCEL.startTime_search2=$('#startTime_search2').combobox('getValue');
		DUTY_STATISTIC_EXCEL.endTime_search2= $('#endTime_search2').combobox('getValue');
		DUTY_STATISTIC_EXCEL.level1_search2=$('#level1_search2').combobox('getText') != '-请选择-' ? $('#level1_search2').combobox('getText') : "";
		if (!$('#search2').form('validate')) {
			return;
		}
		$('#dg')
			.datagrid(
				{
					url : '/workorder/dutystatistic.ajax',
					columns:columns2,
					pageSize: 50,//每页显示的记录条数，默认为10
					pageList: [50,100,200],//可以设置每页记录条数的列表
					queryParams : {
						'startTime_search2' : $('#startTime_search2')
							.combobox('getValue'),
						'endTime_search2' : $('#endTime_search2').combobox(
							'getValue'),
						'level1_search2' : $('#level1_search2').combobox(
							'getText') != '-请选择-' ? $('#level1_search2')
							.combobox('getText') : "",
					},
					onClickRow: function (index, row) { //easyui封装好的时间（被单机行的索引，被单击行的值）
						if(VARS.B!=null){
							//调用查看
							findDetail(row,VARS.B);
							VARS.B=null;
						}
					},
					loadMsg : '数据装载中......',
					onLoadSuccess : function(data) {
						// 将某个单元格设置为红色
						var panel = $(this).datagrid('getPanel');
						var tr = panel.find('div.datagrid-body tr');
						tr.each(function() {

							var td = $(this).children(
								'td[field="unsettledNum"]');
							var td1 = $(this).children(
								'td[field="middleNum"]');
							var td3 = $(this).children(
								'td[field="finallyNum"]');
							var textValue = td.children("div").text();
							var textValue2 = td1.children("div").text();
							var textValue3 = td3.children("div")
								.text();
							// 将网单号与订单号增加超链接
							td.children("div").html(
								'<a href="javascript:void(0)" onclick="onOff(0)">'
								+ textValue + '</a>');
							td1.children("div").html(
								'<a href="javascript:void(0)" onclick="onOff(2)">'
								+ textValue2 + '</a>');
							td3.children("div").html(
								'<a href="javascript:void(0)" onclick="onOff(3)">'
								+ textValue3 + '</a>');
						});
						MergeCells('dg', 'question1Level1');
						// 当没有返回数据时提示信息
						if (data.total == 0 && data.rows.length == 0) {
//							$.messager.alert('系统提示', '查询无数据', 'warning');
						}
					}

				});
	}else if(selectedvalue==3){
		startTime= $('#startTime_search3').combobox('getValue');
		endTime= $('#endTime_search3').combobox('getValue');
		if(startTime>endTime){
			$.messager.alert('系统提示', '开始时间不能晚于结束时间!', 'warning');
			return;
		}
		PERSONNEL_STATISTIC_EXCEL.startTime_search3= $('#startTime_search3').combobox('getValue');
		PERSONNEL_STATISTIC_EXCEL.endTime_search3=$('#endTime_search3').combobox('getValue');
		PERSONNEL_STATISTIC_EXCEL.person_search3=$('#person_search3').val();
		if (!$('#search3').form('validate')) {
			return;
		}
		$('#dg').datagrid(
			{
				url : '/workorder/personnelstatistic.ajax',
				columns:columns3,
				pageSize: 50,//每页显示的记录条数，默认为10
				pageList: [50,100,200],//可以设置每页记录条数的列表
				queryParams : {
					'startTime_search3' : $('#startTime_search3')
						.combobox('getValue'),
					'endTime_search3' : $('#endTime_search3').combobox(
						'getValue'),
					'person_search3' : $('#person_search3').val(),
				},
				onClickRow: function (index, row) { //easyui封装好的时间（被单机行的索引，被单击行的值）
					if(VARS.C!=null){
						//调用查看
						findDetail(row,VARS.C);
						VARS.C=null;
					}
				},
				loadMsg : '数据装载中......',
				onLoadSuccess : function(data) {
					// 将某个单元格设置为红色
					var panel = $(this).datagrid('getPanel');
					var tr = panel.find('div.datagrid-body tr');
					tr.each(function() {
						var td = $(this).children(
							'td[field="unsettledNum"]');
						var td1 = $(this).children(
							'td[field="middleNum"]');
						var td3 = $(this).children(
							'td[field="finallyNum"]');
						var textValue = td.children("div").text();
						var textValue2 = td1.children("div").text();
						var textValue3 = td3.children("div")
							.text();
						// 将网单号与订单号增加超链接
						td.children("div").html(
							'<a href="javascript:void(0)" onclick="onOff(0)">'
							+ textValue + '</a>');
						td1.children("div").html(
							'<a href="javascript:void(0)" onclick="onOff(2)">'
							+ textValue2 + '</a>');
						td3.children("div").html(
							'<a href="javascript:void(0)" onclick="onOff(3)">'
							+ textValue3 + '</a>');
					});
					// 当没有返回数据时提示信息
					if (data.total == 0 && data.rows.length == 0) {
//							$.messager.alert('系统提示', '查询无数据', 'warning');
					}
				}

			});
	}
}
// 用来临时存放添加或修改工单时的地址
var url;
// 添加工单
function newBackList() {
	$('#dlg').dialog('open').dialog('setTitle', '创建工单');
	addInit();
}
//用户点击工单号链接后将开关设置为TRUE
function viewPools(){
	VARS.A="TRUE";
}
function onOff(state){

	var selectedvalue=$("input[name='tab']:checked").val();
	if(selectedvalue==2){
		VARS.B=state;
	}else if(selectedvalue==3){
		VARS.C=state;
	}
}

//查看
function viewPool(rows) {

	console.log(rows);
	var selRows = $("#dg").datagrid("getSelections");
	if (selRows.length == 1) {
		var row = [];
		if(rows){
			row=rows;
		}else{
			row=$('#dg').datagrid('getSelected');
		}

		$('#dlg').dialog('open').dialog('setTitle', '工单信息');
		editInit(row,selRows);

	} else {
		$.messager.alert('系统提示', '请选择一条要查看的记录', 'warning');
	}
}
//添加初始化
function addInit(){
	$('#complaintPhone').attr('readonly', false);
	$('#orderIds').val('');
	$("#trOrderId").show();

	$('#ss').html("");
	$('input:checkbox[name="complaintFlg"]').prop('disabled', false);
	$('#complaintPhone').attr('readonly', true);
	$('#complaintPhone').validatebox({required:false});
	$("#wangId").parent("td").show();
	$('#wangId_hide').parent("td").hide();
	window.askFlgBtn=0;
	window.REVIEWID=null;//存放添加界面中下载图片时传入的ID
	window.question1Level2copy="";
	window.question1Level3copy="";
	$("#backDiv2").html("");
	$("#backDiv3").html("");
	$("#finalResultDiv").html("");
	$("#contextDiv2").html("");
	$('#question1Level2').combobox('loadData', {});
	$('#question1Level3').combobox('loadData', {});
	//从新加载iframe
	$('#win').attr('src', '/img.html');
	$("#addComent").css("display", "none");
	$('#context').attr('disabled', false);//启用
	$("#context").val("");//清空用户反馈
  $('#context').validatebox({required: true, readonly: false});//必填
//	$("#backDiv").css("display", "none");
	$("#backDiv").html('');
	$("#context").css("display", "block");
	$("#contextDiv").html("");
	$('#fm').form('clear');
//	$("#HPBtn").attr("class", "easyui-linkbutton l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled");
	$('#backContext2').attr('readonly', true);
	$('#backContext3').attr('readonly', true);
//	$('#complaintFlg').attr('disabled', false);
	$('#netOrderId').validatebox({required:true});
//	$("#btnDownload").linkbutton("disable");
	// 设置URL为添加工单地址
	url = '/workorder/addworkorder.ajax';
	//$("#tdStr").text("上传图片:");
	$("#up").show();
	$("#down").hide();
	$("#nullImg").hide();
	$('#askFlg').attr('readonly', false);
	$('#askFlg').css('opacity', 1);//设置是否咨询样式
	$('#question1Level1').combobox('enable');
	$('#question1Level2').combobox('enable');
	$('#question1Level3').combobox('enable');
	$("#backContext2").attr('readonly', true);
	$("#backContext3").attr('readonly', true);
	$('#remark5_hide').parent("td").hide();
	$('#remark5').parent("td").show();
	$('#company_hide').parent("td").hide();
	$('#company').parent("td").show();
	$('#fm').form('validate');
	//禁用责任位1 2 3
	$('#question1Level1').combobox('disable');
	$('#question1Level2').combobox('disable');
	$('#question1Level3').combobox('disable');
	//动态删除css
	$("#createTime + .combo").children("input").removeClass("white");
	$("#payTime + .combo").children("input").removeClass("white");
	$("#remark5_hide + .combo").children("input").removeClass("white");
	$("#company_hide + .combo").children("input").removeClass("white");
	$('#buyer').validatebox({required:false});
	$('#buyerMobile').validatebox({
		required:false,
		validType: ''
	});
}


//修改初始化
function editInit(row,selRows){

	$("#trOrderId").hide();
	$('#complaintPhone').attr('readonly', true);
	if(row.complaintPhone!=''){
		$('input:checkbox[name="complaintFlg"]').prop('disabled', true);
	}else{
		$('input:checkbox[name="complaintFlg"]').prop('disabled', false);
	}
	$('#channelCode').val('');
	$('#channelName').val(row.channelName);
    $('#nextUserName').val('');
	$('#orderIds').val('');
	$('#ss').html("");
	if(null!=row.storeType&&""!=row.storeType)
	{
		$("#storeType").val(row.storeType);
	}
	else
	{
		$("#storeType").val("");
	}
	//tbOrderSn回显
	if(null!=row.tbOrderSn && ""!=row.tbOrderSn) {
		$('#tbOrderSn').val(row.tbOrderSn);
	}else {
		$('#tbOrderSn').val("");
	}

	$("#jgpd").show();
	var stausValue = row.sqmStatus;
	if(stausValue=='4')
	{
		$("#jgpd").hide();
	}
	dbsfts=row.complaintFlg;
	oldflag=dbsfts;
	if(dbsfts==1)
	{
		$('#complaintPhone').validatebox({required:true});//必填
	}
	if(dbsfts==0)
	{
		$('#complaintPhone').validatebox({required:false});//去除必填
	}

	$("#wangId").val(row.wangId)
    $("#wangId").parent("td").show();
    $('#wangId_hide').parent("td").hide();

	window.REVIEWID=null;//存放添加界面中下载图片时传入的ID
	$("#backDiv").html("");
	$("#backDiv2").html("");
	$("#backDiv3").html("");
	$("#finalResultDiv").html("");
	$("#contextDiv2").html("");
//	$('#question1Level2').combobox('loadData', {});
	//从新加载iframe
	$('#win').attr('src',  '/img.html');
	$("#nullImg").hide();
	//$("#tdStr").text("上传图片:");
	$("#down").hide();
	$("#up").show();
	$('#backContext2').attr('readonly', false);
	$('#backContext3').attr('readonly', false);
//	$("#btnDownload").linkbutton("enable");
	$('#netOrderId').attr('readonly', true);
	$('#askFlg').attr('readonly', true);
	$('#askFlg').css('opacity', 0.5);//设置是否咨询样式
	$('#remark5_hide').parent("td").hide();
	$('#remark5').parent("td").show();
	$('#company_hide').parent("td").hide();
	$('#company').parent("td").show();

	$("#createTime + .combo").children("input").removeClass("white");
	$("#payTime + .combo").children("input").removeClass("white");
	$("#remark5_hide + .combo").children("input").removeClass("white");
	$("#company_hide + .combo").children("input").removeClass("white");
	$("#addComent").css("display", "block");
	$("#context").attr("readonly", "readonly");
	$("#backDiv").css("display", "block");
	$('#id').val(row.id);
	$('#sqmCount').val(row.sqmCount);
	$('#fm').form('load', row);
	window.question1Level2copy=$('#question1Level2').combobox('getText');
	if(null==row.question1Level3)
	{
		window.question1Level3copy='';
		$('#question1Level3').combobox('setText', window.question1Level3copy);
	}
	else
	{
		window.question1Level3copy=row.question1Level3;
	}
	var zrw1=row.question1Level1;
	yzrw1=row.question1Level1;
	var zrw2=window.question1Level2copy;
	yzrw2=window.question1Level2copy;
	var zrw3=window.question1Level3copy;
	yzrw3=window.question1Level3copy;
	gdqx=row.workOrderTo;
	$.ajax({
		type : "get",
		url : "/dictionary/findThirdPartyCount.ajax",
		data : {
			"question1Level1" : zrw1,
			"question1Level2" : zrw2,
			"question1Level3" : zrw3
		},
		dataType : "json",
		async:false,
		success : function(data) {
			//复制对象
			sfqd = data.slice(0);
			// $('#question1Level1').combobox('loadData', tmp);
		}
	});
	if(sfqd=='已取到')
	{
		$('#question1Level1').combobox('disable');
		$('#question1Level2').combobox('disable');
		$('#question1Level3').combobox('disable');
	}
	else
	{
		$('#question1Level1').combobox('enable');
		$('#question1Level2').combobox('enable');
		$('#question1Level3').combobox('enable');
	}
	if(null==row.question1Level3)
	{
		$('#question1Level1').combobox('enable');
		$('#question1Level2').combobox('enable');
		$('#question1Level3').combobox('enable');
	}
	$('#context').attr('disabled', true);//禁用
	$("#context").val("");//清空用户反馈
	$('#context').validatebox({required:false});//去除必填
	$("#remark5_hide").combobox('setText',row.remark5);
	$("#company_hide").combobox('setText',row.company);

	window.askFlgBtn=1;
	if ($('.askFlg').attr('checked'))  {

		$('#remark5_hide').parent("td").show();
		$('#remark5').parent("td").hide();
		$('#remark5_hide').validatebox({required:true});
		$("#remark5_hide + .combo").children("input").addClass("white");

		$('#company_hide').parent("td").show();
		$('#company').parent("td").hide();
		$('#company_hide').validatebox({required:true});
		$("#company_hide + .combo").children("input").addClass("white");
		$('#buyerMobile').validatebox({
			required:true,
			validType: 'phoneRex'
		});
		$('#buyer').validatebox({
			required:true,
		});
	}else{
		$('#buyerMobile').validatebox({
			required:false,
			validType: ''
		});
		$('#buyer').validatebox({
			required:false,
		});
	}
//	if($("#complaintFlg").attr("checked")=="checked"){
//		$("#HPBtn").attr("class", "easyui-linkbutton l-btn l-btn-small l-btn-plain");
//	}else{
//		$("#HPBtn").attr("class", "easyui-linkbutton l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled");
//	}
	if(!selRows.backContext3){
		$("#backContext3").val("");
	}
	if(!selRows.backContext2){
		$("#backContext2").val("");
	}
//	$("#comment2").val(row.context);
//	$("#comment1").val(row.context);
//	$("#comment3").val(row.backContext2);

	if (row.workStatus == '3') {
		$("#addComent").css("display", "none");
	}
	var html="";
	//noinspection JSAnnotator
	html=html+"判定结果"
		+"<input name=\"desidePass\" type=\"radio\" id=\"scanInput\" value=\"1\" /><label for=\"scanInput\">通过</label><input name=\"desidePass\" type=\"radio\" id=\"handInput\" value=\"0\"/><label for=\"handInput\">不通过</label></td> <button type=\"button\" class=\"btn btn-danger btn-sm  radius\" onclick=\"resetJudgeResult()\">重置</button>";
	$("#jgpd").html(
		html
	);

	if (row.workStatus != null && row.workStatus == "3") {
		url = '';
		$("#tdStr").text("图片操作:");
		$("#up").hide();
		$("#finalResultDiv").html("");
		// 查询最终结果
		$.ajax({
			type : "post",
			url : '/middleresult/findFinalResult.ajax',
			data : {
				reviewId : row.id
			},
			dataType : "json",
			async : false,
			success : function(result) {
				for (var i = 0; i < result.length; i++) {
					var findOldText = $("#finalResultDiv").html();
					if (i > 0) {
						findOldText += '<br/>';
					} else {
						findOldText = '';
					}
					$("#finalResultDiv").html(
						findOldText + result[i].addtime + "  "
						+ result[i].adduser + "  "
						+ result[i].middleContext);
					if(result[i]!=null)
					{
						$("#backContext3").val(
							result[0].addtime + "  " + result[0].adduser + "  "
							+ result[0].middleContext);
					}
				}
			}
		});


	} else {
		// 设置URL为修改工单地址
		url = '/workorder/updateworkorder.ajax';
	}
	$("#contextDiv").html("");
	$("#context").css("display", "none");
	// 查询评论信息
	$.ajax({
		type : "post",
		url : '/comment/selectcomment.ajax',
		data : {
			reviewId : row.id
		},
		dataType : "json",
		async : false,
		success : function(result) {
			for (var i = 0; i < result.length; i++) {
				var oldText = $("#contextDiv").html();
				if (i > 0) {
					oldText += '<br/>';
				} else {
					oldText = '';
				}
				if(result[i]!=null)
				{
					$("#contextDiv").html(
						oldText + result[i].addtime + "  "
						+ result[i].adduser + "  "
						+ result[i].context);
				}
			}
		}
	});
	$("#finalResultDiv").html("");
	// 查询最终结果
	$.ajax({
		type : "post",
		url : '/middleresult/findFinalResult.ajax',
		data : {
			reviewId : row.id
		},
		dataType : "json",
		async : false,
		success : function(result) {
			for (var i = 0; i < result.length; i++) {
				var findOldText = $("#finalResultDiv").html();
				if (i > 0) {
					findOldText += '<br/>';
				} else {
					findOldText = '';
				}
				if (null!=result[i].addtime&&""!=result[i].addtime) {
					$("#finalResultDiv").html(
						findOldText + result[i].addtime + "  "
						+ result[i].adduser + "  "
						+ result[i].middleContext);
				}
			}
		}
	});
	oldText="";
	$("#backDiv").html("");
	$("#backContext2").val('');
	// 查询中间结果
	$.ajax({
		type : "post",
		url : '/middleresult/selectmiddleresult.ajax',
		data : {
			reviewId : row.id
		},
		dataType : "json",
		async : false,
		success : function(result) {
			var newText = $("#backDiv3").html();
			for (var i = 0; i < result.length; i++) {
				var oldText = $("#backDiv").html();
				if (i > 0) {
					oldText += '<br/>';
				} else {
					oldText = '';
				}
				$("#backDiv").html(
					oldText + result[i].addtime + "  "
					+ result[i].adduser + "  "
					+ result[i].middleContext);
				if(result[i]!=null)
				{
					$("#backDiv3").html(
						newText + "("+result[0].addtime + "  "
						+ result[0].adduser + "  "
						+ result[0].middleContext+")");
				}
				if(i+1==3)
				{
					if(null!=row.workOrderTo&&row.workOrderTo=='HP')
					{
						break;
					}
				}
			}
		}
	});
	// 查询图片信息
	$.ajax({
		type : "post",
		//url : '/image/findreviewimagebyid.ajax',
		url : '/image/findimgs.ajax',
		data : {
			reviewId : row.id
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.success){
				if(data.result){
					$("#tdStr").text("图片操作:");
					$("#he").hide();
					$("#down").show();
					$("#up").hide();
					//列出图片名字
					$('#tb').children().remove();
					$.each(data.result,function(index,value) {
						$('#tb').append("<tr><td>"+value.name+"</td><td><a href=\"javascript:void(0)\" id=\"btnDownload"+index+"\" onclick=\"downImage("+value.id+");\">下载</a></td>"+
						"<td><a href=\"javascript:void(0)\" id=\"btnDel"+index+"\" onclick=\"delImage("+value.id+");\">删除</a></td></tr>");
						$("#btnDownload"+ index).linkbutton({
						});
						$("#btnDel"+ index).linkbutton({
						});
					});
					//<a href="javascript:void(0)" class="easyui-linkbutton" id="btnDownload" data-options="plain:true,iconCls:'icon-save'">下载图片</a>


				}else{
					if(url==""){
						$("#nullImg").show();
					}
				}
//				img=imageStr;
			}else{
				$.messager.alert('系统提示', data.message, 'warning');
			}

		}
	});

	if(url==""){//判断是否是最终结果
		$("#backContext2").attr('readonly', true);
		$("#backContext3").attr('readonly', true);
		$('#question1Level1').combobox('disable');
		$('#question1Level2').combobox('disable');
		$('#question1Level3').combobox('disable');
		//动态删除css
		$('#remark5_hide').parent("td").hide();
		$('#remark5').parent("td").show();
		$('#remark5').attr('readonly', true);
		$("#createTime + .combo").children("input").removeClass("white");
		$("#payTime + .combo").children("input").removeClass("white");
		$("#remark5_hide + .combo").children("input").removeClass("white");

		$('#company_hide').parent("td").hide();
		$('#company').parent("td").show();
		$('#company').attr('readonly', true);
		$("#company_hide + .combo").children("input").removeClass("white");

	}else{
		$("#backContext2").attr('readonly', false);
		$("#backContext3").attr('readonly', false);


		//获取责任位2下拉
		zr1=row.question1Level1;
		question1Level2ValueCheck="";
		for(var i=0;i<LEVEL1_QJ.length;i++){
			if(LEVEL1_QJ[i].valueMeaning==zr1){
				question1Level2ValueCheck=LEVEL1_QJ[i].value;
				break;
			}
		}
		$.ajax({
			type : "GET",
			url : "/dictionary/gettwomenu.ajax",
			data : {
				"value_set_id":"duty_2",
				"parent_value" : question1Level2ValueCheck
			},
			dataType : "json",
			success : function(data) {
				$('#question1Level2').combobox('loadData', data);
				$('#question1Level2').combobox('setText', window.question1Level2copy);
			}
		});

		//获取责任位3下拉
		zr2=row.question1Level2;
		question1Level3ValueCheck="";
		for(var i=0;i<LEVEL2_QJ.length;i++){
			if(LEVEL2_QJ[i].valueMeaning==zr2){
				question1Level3ValueCheck=LEVEL2_QJ[i].value;
				break;
			}
		}
		$.ajax({
			type : "GET",
			url : "/dictionary/gettwomenu.ajax",
			data : {
				"value_set_id":"duty_3",
				"parent_value" : question1Level3ValueCheck
			},
			dataType : "json",
			success : function(data) {
				$('#question1Level3').combobox('loadData', data);
				$('#question1Level3').combobox('setText', window.question1Level3copy);
			}
		});
	}



}
// 弹出添加评论信息窗口
function addComentInfo() {
	/*
	 * var context=prompt("请输入评论内容","");//将输入的内容赋给变量 name ，
	 * //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值 if(context)//如果返回的有内容 {
	 * var oldText = $("#context").val(); //alert("保存成功！"+oldText);
	 * $("#context").val(oldText+'\n'+context+(new Date()));
	 * $("#context").show(); }
	 */
	$('#dlg2').dialog('open').dialog('setTitle', '反馈');
}
//弹出查看详情窗口
function findDetail(now,workStatus){
	//清空fm3中的数据
	dataForm=[];
	dataForm.startTime_search= "";
	dataForm.endTime_search= "";
	dataForm.level1_search="";
	dataForm.level2_search="";
	dataForm.workStatus="";
	dataForm.person_search="";
	dataForm.company_search="";
	$('#fm3').form('load', dataForm);
	var selectedvalue=$("input[name='tab']:checked").val();
	if(selectedvalue==2){
		//如果是选择责任位的情况下 将检索责任位2
		if(DUTY_STATISTIC_EXCEL.level1_search2!=null && DUTY_STATISTIC_EXCEL.level1_search2!=""){
			dataForm.startTime_search= DUTY_STATISTIC_EXCEL.startTime_search2;
			dataForm.endTime_search= DUTY_STATISTIC_EXCEL.endTime_search2;
			dataForm.level1_search=now.question1Level1;
			dataForm.level2_search=now.question1Level2;
			dataForm.workStatus=workStatus;
			//设置fm3中的数据
			$('#fm3').form('load', dataForm);
			queryParams = {
				'startTime_search' : DUTY_STATISTIC_EXCEL.startTime_search2,
				'endTime_search' : DUTY_STATISTIC_EXCEL.endTime_search2,
				'workStatus' : workStatus,
				level1_search :now.question1Level1,
				level2_search :now.question1Level2,
				'person_search' : $('#person_search').val(),
			};
		}else{
			dataForm.startTime_search= DUTY_STATISTIC_EXCEL.startTime_search2;
			dataForm.endTime_search= DUTY_STATISTIC_EXCEL.endTime_search2;
			dataForm.level1_search=now.question1Level1;
			dataForm.level2_search=now.question1Level2;
			dataForm.workStatus=workStatus;
			//设置fm3中的数据
			$('#fm3').form('load', dataForm);
			queryParams = {
				'startTime_search' : DUTY_STATISTIC_EXCEL.startTime_search2,
				'endTime_search' : DUTY_STATISTIC_EXCEL.endTime_search2,
				'workStatus' : workStatus,
				level1_search :now.question1Level1,
				level2_search :now.question1Level2,
				'person_search' : $('#person_search').val(),
			};
		}
	}else if(selectedvalue==3){
		//设置fm3中的数据
		dataForm.startTime_search=PERSONNEL_STATISTIC_EXCEL.startTime_search3;
		dataForm.endTime_search=PERSONNEL_STATISTIC_EXCEL.endTime_search3;
		dataForm.person_search=now.wangId;
		dataForm.company_search=now.company;
		dataForm.workStatus=workStatus;
		if(dataForm.wangId==""){
			dataForm.wangId=" ";
		}
		if(dataForm.company_search==""){
			dataForm.company_search=" ";
		}
		$('#fm3').form('load', dataForm);
		queryParams = {
			'startTime_search' : PERSONNEL_STATISTIC_EXCEL.startTime_search3,
			'endTime_search' : PERSONNEL_STATISTIC_EXCEL.endTime_search3,
			'workStatus' : workStatus,
			'person_search' : now.wangId,
			'company_search':now.company,
		};
		if(queryParams.company_search==""){
			queryParams.company_search=" ";
		}
		if(queryParams.person_search==""){
			queryParams.person_search=" ";
		}
	}
	$('#dlg3').dialog('open').dialog('setTitle', '工单详情');
	$('#dg3').datagrid(
		{
			url : '/workorder/workorderpage.ajax',
			columns:columns1,
			pageSize: 50,//每页显示的记录条数，默认为10
			pageList: [50,100,200],//可以设置每页记录条数的列表
			queryParams : queryParams,
			onClickRow: function (index, row) { //easyui封装好的时间（被单机行的索引，被单击行的值）
				if(VARS.A=="TRUE"){
					//调用查看
					viewPool(row);
					VARS.A=null;
				}
			},
			loadMsg : '数据装载中......',
			onLoadSuccess : function(data) {
				// 将某个单元格设置为红色
				var panel = $(this).datagrid('getPanel');
				var tr = panel.find('div.datagrid-body tr');
				tr.each(function() {
					var tdNetOrderId = $(this).children(
						'td[field="netOrderId"]');
					var td = $(this).children(
						'td[field="netOrderId"]');
					// 申述次数
					var td0 = $(this).children(
						'td[field="appealCount1"]');
					// 中间结果申诉次数
					var td1 = $(this).children(
						'td[field="appealCount2"]');
					// 用户咨询次数
					var td2 = $(this).children(
						'td[field="feedBackCount"]');
					// 工单状态
					var td3 = $(this).children(
						'td[field="workStatus"]');
					// 工单号
					var td4 = $(this).children(
						'td[field="id"]');
//					var td5 = $(this).children(
//					'td[field="tbOrderSn"]');
					//网单接口ID
//						var td5 = $(this).children(
//						'td[field="corderPrimary"]');
//						//订单接口ID
//						var td6 = $(this).children(
//						'td[field="orderPrimary"]');
					var tdOrderId = $(this).children(
						'td[field="orderId"]');
					var textValue = td.children("div").text();
					var textValue0 = td0.children("div").text();
					var textValue1 = td1.children("div").text();
					var textValue2 = td2.children("div").text();
					var value = td3.children("div").text();
					var textValue4 = td4.children("div").text();
//						var textValue5 = td5.children("div").text();
//						var textValue6 = td6.children("div").text();
					var orderIdValue = tdOrderId.children("div")
						.text();
					var netOrderIdValue = tdNetOrderId.children("div")
						.text();
					// 工单关闭原因
					var td7 = $(this).children(
						'td[field="closeType"]');
					var closeValue = td7.children("div").text();
					//工单关闭原因转换
					for(var a=0;a<CLOSETYPE.length;a++){
						if(closeValue==CLOSETYPE[a].value){
							closeValue=CLOSETYPE[a].valueMeaning;
							td7.children("div").html(closeValue);
							break;
						}
					}
					if (textValue0 >= 2) {
						td0.children("div").css({
							"color" : "red"
						});
					}
					if (textValue1 >= 2) {
						td1.children("div").css({
							"color" : "red"
						});
					}
					if (textValue2 >= 3) {
						td2.children("div").css({
							"color" : "red"
						});
					}

					if (value == 0) {
						value = '未处理';
					} else if (value == 1) {
						value = '已确认';
					} else if (value == 2) {
						value = '中间结果';
					} else if (value == 3) {
						value = '最终结果';
					} else {
						value = '';
					}
					td3.children("div").html(value);
					// 将网单号与订单号增加超链接
					if(textValue.substr(0, 4).toUpperCase()!='WDZX'){
						//网单号
						td.children("div").html(
							'<a href="javascript:void(0)" onclick="return lookOrderId(\''+netOrderIdValue+'\')">'
							+ netOrderIdValue + '</a>');

						//订单号字段超链接
						tdOrderId.children("div").html(
							'<a href="javascript:void(0)" onclick="return lookorderSn(\''+orderIdValue+'\')">'
							+ orderIdValue + '</a>');
					}

					td4.children("div").html(
						'<a href="javascript:void(0)" onclick="viewPools()">'
						+ textValue4 + '</a>');
				});
				// 当没有返回数据时提示信息
				if (data.total == 0 && data.rows.length == 0) {
//						$.messager.alert('系统提示', '查询无数据', 'warning');
				}else{
				}
			}

		});

}
// 添加评论信息方法
function saveComment() {
	if (!$('#fm2').form('validate')) {
		return;
	}
	var row = $('#dg').datagrid('getSelected');
	var oldText = $("#contextDiv").html();
	var comment1 = $("#context2").val();
	if (comment1 == null || comment1 == ""
		|| comment1.replace(/[ ]/g, "") == "") {
		$.messager.alert('系统提示', '请填写反馈信息', 'warning');
		return;
	}
	// $("#context").val(oldText+'\n'+context2+(new Date()));
	// 添加评论信息
	$.ajax({
		type : "post",
		url : '/comment/addcomment.ajax',
		data : {
			reviewId : row.id,
			comment1 : comment1
		},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$.messager.alert('系统提示', '添加成功', 'warning');
				var context2 = data.result;
				$("#contextDiv").html(context2 + '<br/>' + oldText);
				// 保存最后一次评论信息
//				$("#comment1").val($("#context2").val());
			}else{
				$.messager.alert('系统提示', data.message, 'warning');
			}
		}
	});
	$("#context2").val("");
	$("#backDiv").html("");
	$("#backDiv1").html("");
	$("#backDiv2").html("");
	$("#backDiv3").html("");
	$("#backContext2").val('');
	$('#dlg2').dialog('close');
	// $('#dg').datagrid('reload');
}

function saveOrder() {

	var feedCount =parseInt($('#sqmCount').val());
	var type = $('#sqmType').val();
	var status=$('#sqmStatus').val();
	var pass = $('input:radio[name="desidePass"]:checked').val();
	if (feedCount != null && feedCount+1 >= 3&&(pass == 0 || pass != 1 )&& $('#backContext3').val() != ''){
		if(type=="TS")
		{
			biJiaoZRW(1);
		}
		else
		{
			if(gdqx!='HP')
			{
				saveConfirm();
			}
			else
			{
				biJiaoZRW(0);
			}
		}
	} else {
		biJiaoZRW(0);
	}
}
function saveConfirm() {
	$.messager.defaults = { ok: "是", cancel: "否" };
	$.messager.confirm('系统提示', '</br>咨询次数已到三次，是否转为投诉？</br></br>', function(r) {
		if (r) {
			biJiaoZRW(1);
		} else {
			biJiaoZRW(0);
		}
	});
}
function biJiaoZRW(ifComplain)
{

	var sjslCount =parseInt(sjsl);
	var zrw1=$('#question1Level1').combobox('getText');
	var zrw2=$('#question1Level2').combobox('getText');
	var zrw3=$('#question1Level3').combobox('getText');
	if(sjslCount>0)
	{
		if(null==zrw3||''==zrw3)
		{
			$.messager.alert('系统提示', '请填写责任位3', 'warning');
			return;
		}
	}
	$.ajax({
		type : "get",
		async:false,
		url : "/dictionary/findThirdPartyCount.ajax",
		data : {
			"question1Level1" : zrw1,
			"question1Level2" : zrw2,
			"question1Level3" : zrw3
		},
		dataType : "json",
		success : function(data) {
			//复制对象
			sfqd = data.slice(0);
			// $('#question1Level1').combobox('loadData', tmp);
		}
	});
	if(sfqd=='已取到')
	{
		if(url=='/workorder/updateworkorder.ajax'){
			if((null==yzrw3||''==yzrw3)&&(null==zrw3||''==zrw3))
			{
				saveBackList(ifComplain);
			}
			else if(zrw1==yzrw1&&zrw2==yzrw2&&zrw3==yzrw3)
			{
				saveBackList(ifComplain);
			}
			else
			{
				$.messager.alert('系统提示', '不允许修改为当前责任位组合，如有需要请创建新工单', 'warning');
				return;
			}
		}
		else
		{
			saveBackList(ifComplain);
		}
	}
	else if(sfqd=='未取到')
	{

		saveBackList(ifComplain);
	}
}
// 添加/修改工单信息
function saveBackList(ifComplain) {
	if (ifComplain != null){
		$('#sqmState').val(ifComplain);
	}
	if (url == "") {
		$.messager.alert('系统提示', '该信息已做过最终结果', 'warning');
		return;
	}

	if(!$("#wangId_hide").parent("td").is(":hidden")){
		if($('#wangId').val()==''||$('#wangId').val()=='-请选择-'){
			$.messager.alert('系统提示', '请选择人员', 'warning');
			return;
		}
	}
	var selectedNum = 0;
	/*if($('#categoryLevelOne').combobox('getText')==''||$('#categoryLevelOne').combobox('getText')=='请选择'){
	 selectedNum = selectedNum + 1;
	 }
	 if($('#categoryLevelTwo').combobox('getText')==''||$('#categoryLevelTwo').combobox('getText')=='请选择'){
	 selectedNum = selectedNum + 1;
	 }
	 if($('#categoryLevelThree').combobox('getText')==''||$('#categoryLevelThree').combobox('getText')=='请选择'){
	 selectedNum = selectedNum + 1;
	 }*/
	/*if(selectedNum !=0 && selectedNum !=3){
	 $.messager.alert('系统提示', '请选择完整的责任位类别信息，或者全部置空', 'warning');
	 return;
	 }*/


	dataValue=serializeObject($('#fm'));
	if($("#complaintFlg").attr("checked")=="checked"){ //判断投诉信息是否选中
		dataValue.complaintFlg=1;
		dataValue.sqmType="TS";
	}else{
		dataValue.complaintFlg=0;
		dataValue.sqmType="ZX";
	}
	dataValue.question1Level1 =  $("#question1Level1").combobox('getText');
	dataValue.question1Level2 = $("#question1Level2").combobox('getText');
	dataValue.question1Level3 = $("#question1Level3").combobox('getText');
	dataValue.nextUserName=$("#nextUserName").val();
	dataValue.complaintPhone = $("#complaintPhone").val();
	dataValue.storeType = $("#storeType").val();

	//新增tbOrderSn
	dataValue.tbOrderSn = $('#tbOrderSn').val();

	if ($('.askFlg').attr('checked')){
		dataValue.remark5=$("#remark5_hide").combobox('getText') != '-请选择-' ? $('#remark5_hide').combobox('getText') : "";
		dataValue.company=$("#company_hide").combobox('getText') != '-请选择-' ? $('#company_hide').combobox('getText') : "";
		if(dataValue.remark5==""){
			$.messager.alert('系统提示', '请选择订单来源!', 'warning');
			return;
		}
	}
	if(!$("#company_hide").parent("td").is(":hidden")){
		dataValue.company=$("#company_hide").combobox('getText') != '-请选择-' ? $('#company_hide').combobox('getText') : "";
	}
	//////////////////////////////////////////////////////////////////////img
	var imgArr = new Array(3);
	var imgNameArr = new Array(3);
	var ofrm1 = document.getElementById("win").document;
	if (ofrm1==undefined){//fire/chrome
		ofrm1 = document.getElementById("win").contentWindow.document;
		var $imgs = $(ofrm1.getElementById('preview')).children();
		$.each($imgs,function(index,value) {
			imgArr[index] = value.currentSrc;
			imgNameArr[index] = value.title;
		});
		dataValue.imgArr=imgArr;//封装图片information和name
		dataValue.imgNameArr=imgNameArr;
	}else{//ie
		var $imgs = $(document.frames["win"].document.getElementById("preview")).children();
		$.each($imgs,function(index,value) {
			imgArr[index] = value.currentSrc;
			imgNameArr[index] = value.title;
		});
		dataValue.imgArr=imgArr;
		dataValue.imgNameArr=imgNameArr;
	}


	var wll=$('#question1Level1').combobox('getText');
	var zr2=$('#question1Level2').combobox('getText');

	var ifPass = $('input:radio[name="desidePass"]:checked').val();
	var sqmStatus=$("#sqmStatus").val();
	/*if(sqmStatus!="4"&&sqmStatus!="3"&&ifPass!=""&& $('#backContext3').val() != ""){
	 $.messager.alert('系统提示', '外部系统未返回中间结果，不允许关单', 'warning');
	 return;
	 }*/

	if (sqmStatus!="4"&&(ifPass != 0 && ifPass != 1 && $('#backContext3').val() != '') || ((ifPass == 0 || ifPass == 1) && $('#backContext3').val() == '')){
		$.messager.alert('系统提示', '请填写完整的判定结果及最终结果', 'warning');
		return;
	}
	if(dataValue.complaintFlg!=oldflag&&$('#backContext3').val() != ''){
		$.messager.alert('系统提示','投诉信息和判定结果不同时更改', 'warning');
		return;
	}

	if(sqmStatus=='0'&&$('#backContext3').val() != ''&&sqmStatus!='4'){
		$.messager.alert('系统提示','还未推送到外部系统,不允许关单', 'warning');
		return;
	}


	//对于物流类工单，工单内容必须完整才可以提交保存，WDZX工单不能录入物流类责任位工单
	if($('.askFlg').attr('checked')&&wll=='物流类'){
		$.messager.alert('系统提示', '咨询类工单不能录入物流类责任位工单', 'warning');
		return;
	}
	var complaintPhone = $("#complaintPhone").val();
	var sftsPhone=true;
	if(null!=complaintPhone && ""!=complaintPhone)
	{
		if(!(/^1(3|4|5|7|8)\d{9}$/.test(complaintPhone))){
			sftsPhone=false;
		}
	}
	if(sftsPhone==false)
	{

		$.messager.alert('系统提示', '手机号码有误，请重填', 'warning');
		sftsPhone=true;
		return false;
	}
	if(wll=='物流类'){
		if(zr2=='第三方物流配送'){
			if($('#orderId').val()==''||$('#productName').val()==''||$('#number').val()==''
				||$('#netOrderId').val()==''||$('#sku').val()==''||$('#productAmount').val()==''
				||$('#buyerMobile').val()==''||$('#buyer').val()==''
				||$("#createTime").datetimebox("getValue")==''||$("#payTime").datetimebox("getValue")==''||$('#regionName').val()==''
				||$('#productType').val()==''||$('#address').val()==''||$('#orderCome').val()==''
				||$('#company').val()==''||$('#wangId').val()==''
			){
				$.messager.alert('系统提示', '对于 物流类-第三方物流配送 工单，工单内容除配送网点,其它信息必须完整才可以提交保存', 'warning');
				return;
			}
		}else{
			if($('#orderId').val()==''||$('#productName').val()==''||$('#number').val()==''
				||$('#netOrderId').val()==''||$('#sku').val()==''||$('#productAmount').val()==''
				||$('#buyerMobile').val()==''||$('#buyer').val()==''
				||$("#createTime").datetimebox("getValue")==''||$("#payTime").datetimebox("getValue")==''||$('#regionName').val()==''
				||$('#productType').val()==''||$('#address').val()==''||$('#orderCome').val()==''
				||$('#company').val()==''||$('#wangId').val()==''
			){
				$.messager.alert('系统提示', '对于物流类工单，工单内容必须完整才可以提交保存', 'warning');
				return;
			}
		}
	}
	$.ajax({
		type : "post",
		url : url,
		data : dataValue,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				$.messager.alert('系统提示', data.result, 'warning');
				$('#dlg').dialog('close');
				$('#dg').datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.message, 'warning');
			}
			// }
			// if (result.succesinfo){
			// $.messager.alert('系统提示',result.succesinfo,'warning');
			// $('#dlg').dialog('close');
			// $('#dg').datagrid('reload');
			// }
		}
	});

}
function resetJudgeResult(){
	$('#handInput').attr("checked",false );
	$('#scanInput').attr("checked",false );
}
// 订单号回车后逻辑
isKeyBtn=false;
isKeyBtns=false;


$('#orderIds').keypress(function(e) {
	console.log(11);
	if (e.which == 13) {
		isKeyBtns=true;
		var id = $(this).val();
		$.ajax({
			type : "post",
			url : '/workorder/getnetOrderFroOrderId.ajax',
			data : {
				orderIds : id
			},
			dataType : "json",
			async : true,
			success : function(data) {
				var pprm = data.result;
				if(pprm.length>0){
					var  a="";
					for(var i=0;i<pprm.length;i++){

						a=a+"<a href='#'   style='text-decoration: none' onclick='dian(\""+pprm[i].netOrderId+"\")'>"+pprm[i].netOrderId+"&nbsp;&nbsp;&nbsp;"+pprm[i].productName+"</a></br>"
					}
					$("#ss").html(a);
				}else{
					$.messager.alert('系统提示', "未查询到网单信息", 'warning');
					$("#ss").html("");
				}
			}
		});

	}
});

function focusOrderids(){
	isKeyBtns=false;
}
function blurOrderids(obj){
	if(!isKeyBtns) {
		$.ajax({
			type: "post",
			url: '/workorder/getnetOrderFroOrderId.ajax',
			data: {
				orderIds: $(obj).val()
			},
			dataType: "json",
			async: false,
			success: function (data) {
				var pprm = data.result;
				if (pprm.length > 0) {
					var a = "";
					for (var i = 0; i < pprm.length; i++) {
						a=a+"<a href='#'  style='text-decoration: none'onclick='dian(\""+pprm[i].netOrderId+"\")'>"+pprm[i].netOrderId+"&nbsp;&nbsp;&nbsp;"+pprm[i].productName+"</a></br>"
					}
					$("#ss").html(a);
				} else {
					$.messager.alert('系统提示', "未查询到网单信息", 'warning');
					$("#ss").html("");
				}
				isKeyBtns=false;
			}

		});

	}


}




//点击根据订单号出来的网单
function dian(netid) {
	$("#netOrderId").val(netid);
	console.log("dian");
	$.ajax({
		type : "post",
		url : '/workorder/getnetorder.ajax',
		data : {
			"netOrderId" : netid
		},
		dataType : "json",
		async : false,
		success : function(data) {
			$('#orderId').val('');
            $('#orderIds').val('');
			$('#productName').val('');
			$('#number').val('');
//				$('#netOrderId').val('');
			$('#sku').val('');
			$('#productAmount').val('');
			$('#buyerMobile').val('');
			$('#buyer').val('');
			$('#netPointId').val('');
//				$('#createTime').val('');
//				$('#payTime').val('');
			$('#createTime').datetimebox("clear");
			$('#payTime').datetimebox("clear");
			$('#regionName').val('');
			$('#address').val('');
			$('#orderCome').val('');
			$('#company').val('');
			$('#wangId').val('');
			$('#phone').val('');
			$('#remark5').val('');
			$('#orderCome').val('');
			$('#centerType').val('');
			$('#storeId').val('');
			$('#storeType').val('');
			$("#channelCode").val('');
			$("#channelName").val('');
            $("#nextUserName").val('');
			if (data.success) {



				//启用责任1 2
				$('#question1Level1').combobox('enable');
				$('#question1Level2').combobox('enable');
				var pprm = data.result;
//					pprm = jQuery.parseJSON(pprm);
//					var prm = pprm.orderProducts;
                $("#nextUserName").val(pprm.nextUserName);
				$("#channelName").val(pprm.channelName);
				$("#channelCode").val(pprm.channelCode);
				$('#orderId').val(pprm.orderSn);
                $("#orderIds").val(pprm.orderSn);
				$('#productName').val(pprm.productName);
				$('#number').val(pprm.number);
				$('#storeType').val(pprm.storeType);
				$('#sku').val(pprm.sku);
				$('#productType').val(pprm.productName);
				$('#productAmount').val(pprm.productAmount);
				$('#buyer').val(pprm.buyer);
				$('#buyerMobile').val(pprm.buyerMobile);
				$('#netPointId').val(pprm.netPointName);
				$('#centerType').val(pprm.centerType);
				//var newTime = new Date(pprm.createTime*1000);
//						$('#createTime').val(newTime.format("yyyy-MM-dd hh:mm:ss"));
				//var str=newTime.format("yyyy-MM-dd hh:mm:ss");
				$("#createTime").datetimebox("setValue", pprm.addtime);
				var newTime1 = new Date(pprm.payTime*1000);
//						$('#payTime').val(newTime1.format("yyyy-MM-dd hh:mm:ss"));
				var str2=newTime1.format("yyyy-MM-dd hh:mm:ss");
				$("#payTime").datetimebox("setValue", str2);
				$('#regionName').val(pprm.regionName);
				$('#company').val("");
				$('#address').val(pprm.address);
				$('#fm').form('validate');
//					$('#orderCome').val('SG');
				$('#orderCome').val(pprm.shippingTime);
				$('#company').val(pprm.company);
				$('#storeId').val(pprm.storeId);
				if(pprm.company==null||pprm.company==""){
//						alert("a ");
					$('#company_hide').parent("td").show();
					$('#company_hide').combobox("setText","-请选择-").combobox("setValue","");
					$("#company_hide + .combo").children("input").addClass("white");
					$('#company').parent("td").hide();
				}else{
					$('#company_hide').parent("td").hide();
					$('#company').parent("td").show();
				}
				var n=pprm.source;
				for(var i=0;i<ordersource.length;i++){
					if(ordersource[i].value==n){
						$('#remark5').val(ordersource[i].valueMeaning);

						find(null,ordersource[i].valueMeaning);
						break;
					}
				}
//					var ptrm = pprm.ordWfRegion;

//						$('#wangId').val(ptrm.personTradeCfg.commissioner);
				$('#phone').val(pprm.phone);

//					$("#corderPrimary").val(pprm.corderPrimary);
//					$("#orderPrimary").val(pprm.orderPrimary);
			} else {
				$('#company_hide').parent("td").hide();
				$('#company').parent("td").show();
				$.messager.alert('系统提示', data.message, 'warning');
			}
		}
	});
}

//输入网单号回车

$('#netOrderId').keypress(function(e) {
	// var code = event.keyCode;
	// var ev = document.all ? window.event : e;
	// if(ev.keyCode==13) {
	if (e.which == 13) {
		isKeyBtn=true;
		var id = $(this).val();
		$('#orderIds').val('');$('#ss').html('');
		$.ajax({
			type : "post",
			url : '/workorder/getnetorder.ajax',
			data : {
				"netOrderId" : id
			},
			dataType : "json",
			async : false,
			success : function(data) {
				$('#orderId').val('');
                $("#orderIds").val('');
				$('#productName').val('');
				$('#number').val('');
//				$('#netOrderId').val('');
				$('#sku').val('');
				$('#productAmount').val('');
				$('#buyerMobile').val('');
				$('#buyer').val('');
				$('#netPointId').val('');
//				$('#createTime').val('');
//				$('#payTime').val('');
				$('#createTime').datetimebox("clear");
				$('#payTime').datetimebox("clear");
				$('#regionName').val('');
				$('#address').val('');
				$('#orderCome').val('');
				$('#company').val('');
				$('#wangId').val('');
				$('#phone').val('');
				$('#remark5').val('');
				$('#orderCome').val('');
				$('#centerType').val('');
				$('#storeId').val('');
				$('#storeType').val('');
				$("#channelCode").val('');
				$("#channelName").val('');
				$("#nextUserName").val('');
				if (data.success) {

					//启用责任1 2
					$('#question1Level1').combobox('enable');
					$('#question1Level2').combobox('enable');
					var pprm = data.result;
                    console.log(pprm);

//					pprm = jQuery.parseJSON(pprm);
//					var prm = pprm.orderProducts;
          $("#nextUserName").val(pprm.nextUserName);
					$("#channelName").val(pprm.channelName);
					$("#channelCode").val(pprm.channelCode);
					$('#orderId').val(pprm.orderSn);
					$("#orderIds").val(pprm.orderSn);
					$('#productName').val(pprm.productName);
					$('#number').val(pprm.number);
					$('#storeType').val(pprm.storeType);
					$('#sku').val(pprm.sku);
					$('#productType').val(pprm.productName);
					$('#productAmount').val(pprm.productAmount);
					$('#buyer').val(pprm.buyer);
					$('#buyerMobile').val(pprm.buyerMobile);
					$('#netPointId').val(pprm.netPointName);
					$('#centerType').val(pprm.centerType);
					//var newTime = new Date(pprm.createTime*1000);
//						$('#createTime').val(newTime.format("yyyy-MM-dd hh:mm:ss"));
					//var str=newTime.format("yyyy-MM-dd hh:mm:ss");
					$("#createTime").datetimebox("setValue", pprm.addtime);
					var newTime1 = new Date(pprm.payTime*1000);
//						$('#payTime').val(newTime1.format("yyyy-MM-dd hh:mm:ss"));
					var str2=newTime1.format("yyyy-MM-dd hh:mm:ss");
					$("#payTime").datetimebox("setValue", str2);
					$('#regionName').val(pprm.regionName);
					$('#company').val("");
					$('#address').val(pprm.address);
					$('#fm').form('validate');
//					$('#orderCome').val('SG');
					$('#orderCome').val(pprm.shippingTime);
					$('#storeId').val(pprm.storeId);
          $('#company').val(pprm.company);
					if(pprm.company==null||pprm.company==""){
//						alert("a ");
						$('#company_hide').parent("td").show();
						$('#company_hide').combobox("setText","-请选择-").combobox("setValue","");
						$("#company_hide + .combo").children("input").addClass("white");
						$('#company').parent("td").hide();
					}else{
						$('#company_hide').parent("td").hide();
						$('#company').parent("td").show();
					}


					var n=pprm.source;
					for(var i=0;i<ordersource.length;i++){
						if(ordersource[i].value==n){
							$('#remark5').val(ordersource[i].valueMeaning);

							find(null,ordersource[i].valueMeaning);
							break;
						}
					}
//					var ptrm = pprm.ordWfRegion;

//						$('#wangId').val(ptrm.personTradeCfg.commissioner);
					$('#phone').val(pprm.phone);

//					$("#corderPrimary").val(pprm.corderPrimary);
//					$("#orderPrimary").val(pprm.orderPrimary);

					//新增tb单号
					$('#tbOrderSn').val(pprm.tbOrderSn);
				} else {
					$('#company_hide').parent("td").hide();
					$('#company').parent("td").show();
					$.messager.alert('系统提示', data.message, 'warning');
				}
			}
		});
	}
});


//function reviewAudit() {
//	$('#auditForm').form('submit', {
//		url : this.action,
//		onSubmit : function() {
//			return $(this).form('validate');
//		},
//		success : function(result) {
//			var result = eval('(' + result + ')');
//			if (result.errorinfo) {
//				$.messager.alert('系统提示', result.errorinfo, 'warning');
//			}
//			if (result.succesinfo) {
//				$.messager.alert('系统提示', result.succesinfo, 'warning');
//				$('#audit').dialog('close');
//				$('#dg').datagrid('reload');
//			}
//		}
//	});
//}
//导出
function exp() {
	var data=$('#dg').datagrid('getData');
	if(data.rows.length<=0){
		$.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
		return;
	}
	var selectedvalue=$("input[name='tab']:checked").val();
	if(selectedvalue==1){
		window.location.href="/export/workOrder?"+
			'orderId_search='+WORK_ORDER_EXCEL.Q_orderId_search
//				+'&orderCome_search='+WORK_ORDER_EXCEL.Q_orderCome_search
			+'&netOrderId_search=' + WORK_ORDER_EXCEL.Q_netOrderId_search
			+'&closeType_search='+WORK_ORDER_EXCEL.Q_closeType_search
			+'&startTime_search='+WORK_ORDER_EXCEL.Q_startTime_search
			+'&endTime_search='+WORK_ORDER_EXCEL.Q_endTime_search
			+'&workStatus='+WORK_ORDER_EXCEL.Q_workStatus
			+'&level1_search='+WORK_ORDER_EXCEL.Q_level1_search
			+'&level2_search='+WORK_ORDER_EXCEL.Q_level2_search
			+'&level3_search='+WORK_ORDER_EXCEL.Q_level3_search
			+'&workOrderTo='+WORK_ORDER_EXCEL.Q_workOrderTo
			+'&channelName_search='+WORK_ORDER_EXCEL.Q_channelName_search
			+'&centerType='+WORK_ORDER_EXCEL.Q_centerType
			+'&company_search='+WORK_ORDER_EXCEL.Q_company_search
			+'&person_search='+WORK_ORDER_EXCEL.Q_person_search
			+'&addsearch_search='+WORK_ORDER_EXCEL.Q_addsearch_search
			+'&remark5_search='+WORK_ORDER_EXCEL.Q_remark5_search
			+'&clientName='+WORK_ORDER_EXCEL.Q_clientName
			+'&clientTel='+WORK_ORDER_EXCEL.Q_clientTel
			+'&phone_search='+WORK_ORDER_EXCEL.Q_phone_search
			+'&complaintFlg_search='+WORK_ORDER_EXCEL.Q_complaintFlg_search
			+'&id_search='+WORK_ORDER_EXCEL.Q_id_search;
	}else if(selectedvalue==2){
		window.location.href="/export/export2.html?"+
			'startTime_search2='+DUTY_STATISTIC_EXCEL.startTime_search2
			+'&endTime_search2='+DUTY_STATISTIC_EXCEL.endTime_search2
			+'&level1_search2='+DUTY_STATISTIC_EXCEL.level1_search2;
	}else if(selectedvalue==3){
		window.location.href="/export/export3.html?"+
			'startTime_search3='+PERSONNEL_STATISTIC_EXCEL.startTime_search3
			+'&endTime_search3='+PERSONNEL_STATISTIC_EXCEL.endTime_search3
			+'&person_search3='+PERSONNEL_STATISTIC_EXCEL.person_search3;
	}
}
//弹窗导出
function windowExp() {
	var data=$('#dg3').datagrid('getData');
	if(data.rows.length<=0){
		$.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
		return;
	}
	$('#fm3')
		.form(
			'submit',
			{
				url : "/export/workOrder",
			});

}
//表单选中后事件
function onClickCell(index, field) {
	return;
}

//将form表单内的所有提交项转成对象
function serializeObject(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};
// 去除空格方法
function fun_trim(obj) {
	if ($.trim($(obj).val()).length <= 0) {
		$(obj).val($.trim($(obj).val()));
	}
}
// 时间转换方法
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
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
			.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
				: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
//下载图片
//$("#btnDownload").click(function(){
//
//	var row = $('#dg').datagrid('getSelected');
//	if(window.REVIEWID!=null){
//		window.open('image/downloadimage.ajax?reviewId='+window.REVIEWID);
//	}else{
//		window.open('image/downloadimage.ajax?reviewId='+row.id);
//	}
//
//});
function downImage(id) {
	window.open('/image/downloadimage.ajax?id='+id);
}

function delImage(id) {
	$.ajax({
		type : "post",
		url : '/image/delById.ajax',
		data : {
			id : id
		},
		dataType : "json",
		async : true,
		success : function(data) {
			if (data.success) {
				if(data.result) {
					$.messager.alert('系统提示', '删除图片成功', 'warning');
					//局部刷新
					queryImages();
				}

			}else{
				$.messager.alert('系统提示', data.message, 'warning');
			}
		}
	});
}
function  queryImages(){
	var row=$('#dg').datagrid('getSelected');
	// 查询图片信息
	$.ajax({
		type : "post",
		//url : '/image/findreviewimagebyid.ajax',
		url : '/image/findimgs.ajax',
		data : {
			reviewId : row.id
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.success){
				if(data.result){
					$("#tdStr").text("图片操作:");
					$("#he").hide();
					$("#down").show();
					$("#up").hide();
					//列出图片名字
					$('#tb').children().remove();
					$.each(data.result,function(index,value) {
						$('#tb').append("<tr><td>"+value.name+"</td><td><a href=\"javascript:void(0)\" id=\"btnDownload"+index+"\" onclick=\"downImage("+value.id+");\">下载</a></td>"+
						"<td><a href=\"javascript:void(0)\" id=\"btnDel"+index+"\" onclick=\"delImage("+value.id+");\">删除</a></td></tr>");
						$("#btnDownload"+ index).linkbutton({
						});
						$("#btnDel"+ index).linkbutton({
						});
					});
					//<a href="javascript:void(0)" class="easyui-linkbutton" id="btnDownload" data-options="plain:true,iconCls:'icon-save'">下载图片</a>

				}else{
					if(data.message=="无图片"){
						$("#tdStr").text("上传图片:");
						$("#down").hide();
						$("#up").show();
						//$("#nullImg").show();
					}
				}
			}else{
				$.messager.alert('系统提示', data.message, 'warning');
			}

		}
	});

}




//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r != null) return unescape(r[2]); return null; //返回参数值
}
//日期控件精确到小时
function ww4(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var h = date.getHours();
	return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':00:00';

}
//是否咨询选中可编辑
$(".askFlg").click(function(){
	if(window.askFlgBtn==1){
		return false;
	}
	if ($('.askFlg').is(':checked'))  {
		//启用责任位1 2
		$('#question1Level1').combobox('enable');
		$('#question1Level2').combobox('enable');
		$('#question1Level3').combobox('enable');
		//清空表单
		$('#orderId').val('');
        $('#orderIds').val('');
		$('#productName').val('');
		$('#number').val('');
		$('#netOrderId').val('');
		$('#sku').val('');
		$('#productAmount').val('');
		$('#buyerMobile').val('');
		$('#buyer').val('');
		$('#netPointId').val('');
//		$('#createTime').val('');
//		$('#payTime').val('');
		$('#createTime').datetimebox("clear");
		$('#payTime').datetimebox("clear");
		$('#regionName').val('');
		$('#address').val('');
		$('#orderCome').val('');
		$('#company').val('');
		$('#wangId').val('');
		$('#phone').val('');
		$('#remark5').val('');
		$('#orderCome').val('');

//		$("input[type=reset]").trigger("click");


		$('#netOrderId').validatebox({required:false});
		$('#buyer').validatebox({required:true});
		$('#buyerMobile').validatebox({
			required:true,
			validType: 'phoneRex'
		});
		$('.askFlg').attr('checked',true);
		$('#orderId').attr('readonly', false);
		$('#productName').attr('readonly', false);
		$('#number').attr('readonly', false);
		$('#netOrderId').attr('readonly', true);
		$('#sku').attr('readonly', false);
		$('#productAmount').attr('readonly', false);
		$('#buyerMobile').attr('readonly', false);
		$('#buyer').attr('readonly', false);
		$('#netPointId').attr('readonly', false);

//		$('#createTime').attr('readonly', false);
//		$('#payTime').attr('readonly', false);
		$('#createTime').datetimebox({readonly:false});
		$('#payTime').datetimebox({readonly:false});

		$('#regionName').attr('readonly', false);
		$('#address').attr('readonly', false);
		$('#orderCome').attr('readonly', false);

//		$('#company').attr('readonly', false);
//		$('#wangId').attr('readonly', false);
//		$('#phone').attr('readonly', false);
		$('#remark5_hide').parent("td").show();
		$('#remark5').parent("td").hide();
		$('#remark5_hide').validatebox({required:true});
		$('#remark5_hide').combobox('setValue', "").combobox('setText', '-请选择-');

		$('#company_hide').parent("td").show();
		$('#company').parent("td").hide();
		$('#company_hide').validatebox({required:true});
		$('#company_hide').combobox('setValue', "").combobox('setText', '-请选择-');
//		$("#remark5_hide + .combo").children("input").removeAttr("readonly");
//		$('#orderCome').attr('readonly', false);
		//动态追加css
		$("#remark5_hide + .combo").children("input").addClass("white");
		$("#createTime + .combo").children("input").addClass("white");
		$("#payTime + .combo").children("input").addClass("white");
		$("#company_hide + .combo").children("input").addClass("white");

	}else{
		//禁用责任位1 2
		$('#question1Level1').combobox('disable');
		$('#question1Level2').combobox('disable');
		$('#question1Level3').combobox('disable');
		$('#question1Level1').combobox('setText',"").combobox('setValue',"");
		$('#question1Level2').combobox('setText',"").combobox('setValue',"");
		$('#question1Level3').combobox('setText',"").combobox('setValue',"");
		//清空表单
		$('#orderId').val('');
        $('#orderIds').val('');
		$('#productName').val('');
		$('#number').val('');
		$('#netOrderId').val('');
		$('#sku').val('');
		$('#productAmount').val('');
		$('#buyerMobile').val('');
		$('#buyer').val('');
		$('#netPointId').val('');
//		$('#createTime').val('');
//		$('#payTime').val('');
		$('#createTime').datetimebox("clear");
		$('#payTime').datetimebox("clear");
		$('#regionName').val('');
		$('#address').val('');
		$('#orderCome').val('');
		$('#company').val('');
		$('#wangId').val('');
		$('#phone').val('');
		$('#remark5').val('');

		$('#netOrderId').validatebox({required:true});
		$('#buyer').validatebox({required:false});
		$('#buyerMobile').validatebox({
			required:false,
			validType: ''
		});
		$('#netOrderId').data('options',"required:true");
		//不可用
		$('#orderId').attr('readonly', true);
		$('#productName').attr('readonly', true);
		$('#number').attr('readonly', true);
		$('#netOrderId').attr('readonly', false);
		$('#sku').attr('readonly', true);
		$('#productAmount').attr('readonly', true);
		$('#buyerMobile').attr('readonly', true);
		$('#buyer').attr('readonly', true);
		$('#netPointId').attr('readonly', true);
//		$('#createTime').attr('readonly', true);
//		$('#payTime').attr('readonly', true);
		$('#createTime').datetimebox({readonly:true});
		$('#payTime').datetimebox({readonly:true});
		$('#regionName').attr('readonly', true);
		$('#address').attr('readonly', true);
		$('#orderCome').attr('readonly', true);
		$('#company').attr('readonly', true);
//		$('#wangId').attr('readonly', true);
		$('#phone').attr('readonly', true);
		$('#remark5_hide').parent("td").hide();
		$('#remark5').parent("td").show();
		$('#remark5').attr('readonly', true);

		$('#company_hide').parent("td").hide();
		$('#company').parent("td").show();
		$('#company').attr('readonly', true);

		$('#orderCome').attr('readonly', true);
		//动态删除css
		$("#createTime + .combo").children("input").removeClass("white");
		$("#payTime + .combo").children("input").removeClass("white");
		$("#remark5_hide + .combo").children("input").removeClass("white");
		$("#company_hide + .combo").children("input").removeClass("white");
	}

});
//是否是投诉点击事件
//$("#complaintFlg").click(function(){
//	 if($("#complaintFlg").attr("checked")=="checked"){
//		 $("#HPBtn").attr("class", "easyui-linkbutton l-btn l-btn-small l-btn-plain");
//	 }else{
//		 $("#HPBtn").attr("class", "easyui-linkbutton l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled");
//	 }
//});


//关闭设置
$('#dlg').dialog({
	modal:true,
	onClose:function(){
		$('#orderId').attr('readonly', true);
		$('#productName').attr('readonly', true);
		$('#number').attr('readonly', true);
		$('#netOrderId').attr('readonly', false);
		$('#sku').attr('readonly', true);
		$('#productAmount').attr('readonly', true);
		$('#buyerMobile').attr('readonly', true);
		$('#buyer').attr('readonly', true);
		$('#netPointId').attr('readonly', true);
//		$('#createTime').attr('readonly', true);
//		$('#payTime').attr('readonly', true);
		$('#createTime').datetimebox({readonly:true});
		$('#payTime').datetimebox({readonly:true});
		$('#regionName').attr('readonly', true);
		$('#address').attr('readonly', true);
		$('#orderCome').attr('readonly', true);
		$('#company').attr('readonly', true);
		$('#wangId').attr('readonly', true);
		$('#phone').attr('readonly', true);
		$('#remark5').attr('readonly', true);
	}
});
//控制各个功能的搜索条件
$("input[name='tab']").change(function(){
	var selectedvalue=$("input[name='tab']:checked").val();
	//清空原有数据
	$('#dg').datagrid('loadData', { total: 0, rows: [] });
	if(selectedvalue==1){
		$("#filter1").show();
		$("#filter2").hide();
		$("#filter3").hide();
		$("#addBtn").show();
		$("#lookBtn").show();
		$('#dg').datagrid({
			url:'',
      fitColumns: false,
			columns:columns1
		});
	}else if(selectedvalue==2){
		$("#filter1").hide();
		$("#filter2").show();
		$("#filter3").hide();
		$("#addBtn").hide();
		$("#lookBtn").hide();
//		//自动精确到一周
//		 var curr_time = new Date();
//		   var strDate = curr_time.getFullYear()+"-";
//		   strDate += curr_time.getMonth()+1+"-";
//		   strDate += curr_time.getDate()+" ";
//		   strDate += curr_time.getHours()+":";
//		   var strDateEnd = curr_time.getFullYear()+"-";
//		   strDateEnd += curr_time.getMonth()+"-";
//		   strDateEnd += curr_time.getDate()+" ";
//		   strDateEnd += curr_time.getHours()+":";
//		   $("#startTime_search2").datetimebox("setValue", strDateEnd);
//		   $("#endTime_search2").datetimebox("setValue", strDate);

//		$("#filter6").hide();

		$('#dg').datagrid({
			url:'',
      fitColumns: false,
			columns:columns2
		});
	}else if(selectedvalue==3){
		$("#filter1").hide();
		$("#filter2").hide();
		$("#filter3").show();
		$("#addBtn").hide();
		$("#lookBtn").hide();
		$('#dg').datagrid({
			url:'',
      fitColumns: false,
			columns:columns3
		});

	}

});

//绑定焦点事件
$(document).ready(function() {
	//focusblur
	jQuery.focusblur = function(focusid) {
		var focusblurid = $(focusid);
		var defval = focusblurid.val();
		//获得焦点事件
		focusblurid.focus(function() {
			var thisval = $(this).val();
			if (thisval == defval) {
				$(this).val("");
			}
		});
		//失去焦点事件
		focusblurid.blur(function() {
			if(url!='/workorder/addworkorder.ajax'){//用来区分是否是添加工单  不是添加工单 禁用网单号失去焦点事件
				return;
			}
			if(!isKeyBtn){
				var id = $(this).val();
				if(id!=""&& id!=null){
					$.ajax({
						type : "post",
						url : '/workorder/getnetorder.ajax',
						data : {
							"netOrderId" : id
						},
						dataType : "json",
						async : false,
						success : function(data) {

							$("#ss").html("")

							$('#orderId').val('');
                            $('#orderIds').val('');
							$('#productName').val('');
							$('#number').val('');
//							$('#netOrderId').val('');
							$('#sku').val('');
							$('#productAmount').val('');
							$('#buyerMobile').val('');
							$('#buyer').val('');
							$('#netPointId').val('');
//							$('#createTime').val('');
//							$('#payTime').val('');
							$('#createTime').datetimebox("clear");
							$('#payTime').datetimebox("clear");
							$('#regionName').val('');
							$('#address').val('');
							$('#orderCome').val('');
							$('#company').val('');
							$('#wangId').val('');
							$('#phone').val('');
							$('#remark5').val('');
							$('#orderCome').val('');
							$('#storeType').val('');
							$('#channelCode').val('');
							$("#channelName").val('');
                            $("#nextUserName").val();
							if (data.success) {

								$('#question1Level1').combobox('enable');
								$('#question1Level2').combobox('enable');
								var pprm = data.result;
                                $("#nextUserName").val(pprm.nextUserName);
                              //新增tb单号
            					$('#tbOrderSn').val(pprm.tbOrderSn);
//					pprm = jQuery.parseJSON(pprm);
//					var prm = pprm.orderProducts;
								$("#channelCode").val(pprm.channelCode);
								$("#channelName").val(pprm.channelName);
								$('#orderId').val(pprm.orderSn);
                                $("#orderIds").val(pprm.orderSn);
								$('#productName').val(pprm.productName);
								$('#number').val(pprm.number);
								$('#sku').val(pprm.sku);
								$('#productType').val(pprm.productName);
								$('#productAmount').val(pprm.productAmount);
								$('#buyer').val(pprm.buyer);
								$('#buyerMobile').val(pprm.buyerMobile);
								$('#netPointId').val(pprm.netPointId);
								$('#centerType').val(pprm.centerType);
								//var newTime = new Date(pprm.createTime*1000);
//						$('#createTime').val(newTime.format("yyyy-MM-dd hh:mm:ss"));
								//var str=newTime.format("yyyy-MM-dd hh:mm:ss");
								$("#createTime").datetimebox("setValue", pprm.addtime);
								var newTime1 = new Date(pprm.payTime*1000);
//						$('#payTime').val(newTime1.format("yyyy-MM-dd hh:mm:ss"));
								var str2=newTime1.format("yyyy-MM-dd hh:mm:ss");
								$("#payTime").datetimebox("setValue", str2);
								$('#regionName').val(pprm.regionName);
								$('#company').val("");
								$('#address').val(pprm.address);
								//启用责任1 2
								$('#question1Level1').combobox('enable');
								$('#question1Level2').combobox('enable');
								var pprm = data.result;
//								pprm = jQuery.parseJSON(pprm);
//								var prm = pprm.orderProducts;
								$('#orderId').val(pprm.orderSn);
                                $("#orderIds").val(pprm.orderSn);
								$('#productName').val(pprm.productName);
								$('#number').val(pprm.number);
								$('#sku').val(pprm.sku);
								$('#productType').val(pprm.productName);
								$('#productAmount').val(pprm.productAmount);
								$('#buyer').val(pprm.buyer);
								$('#buyerMobile').val(pprm.buyerMobile);
								$('#netPointId').val(pprm.netPointName);
								$('#centerType').val(pprm.centerType);
								$('#storeType').val(pprm.storeType);
								//var newTime = new Date(pprm.createTime*1000);
//									$('#createTime').val(newTime.format("yyyy-MM-dd hh:mm:ss"));
								//var str=newTime.format("yyyy-MM-dd hh:mm:ss");
								$("#createTime").datetimebox("setValue", pprm.addtime);
								var newTime1 = new Date(pprm.payTime*1000);
//									$('#payTime').val(newTime1.format("yyyy-MM-dd hh:mm:ss"));
								var str2=newTime1.format("yyyy-MM-dd hh:mm:ss");
								$("#payTime").datetimebox("setValue", str2);
								$('#regionName').val(pprm.regionName);
								$('#company').val("");
								$('#address').val(pprm.address);
								$('#fm').form('validate');
//								$('#orderCome').val('SG');
								$('#orderCome').val(pprm.shippingTime);
								$('#company').val(pprm.company);
								if(pprm.company==null||pprm.company==""){
//									alert("a ");
									$('#company_hide').parent("td").show();
									$('#company_hide').combobox("setText","-请选择-").combobox("setValue","");
									$("#company_hide + .combo").children("input").addClass("white");
									$('#company').parent("td").hide();
								}else{
									$('#company_hide').parent("td").hide();
									$('#company').parent("td").show();
								}


								var n=pprm.source;///订单来源
								for(var i=0;i<ordersource.length;i++){
									if(ordersource[i].value==n){
										$('#remark5').val(ordersource[i].valueMeaning);

										find(null,ordersource[i].valueMeaning);
										break;
									}
								}
								$('#storeId').val(pprm.storeId);
//								var ptrm = pprm.ordWfRegion;

//									$('#wangId').val(ptrm.personTradeCfg.commissioner);
								$('#phone').val(pprm.phone);

//								$("#corderPrimary").val(pprm.corderPrimary);
//								$("#orderPrimary").val(pprm.orderPrimary);
								//调用通过网单与责任位查询信息
							} else {
								$('#company_hide').parent("td").hide();
								$('#company').parent("td").show();
								$.messager.alert('系统提示', data.message, 'warning');
							}
						}
					});
				}
			}
			isKeyBtn=false;
		});
	};
	/*下面是调用方法*/
	$.focusblur("#netOrderId");
});

//通过网单，责任位，所属工贸，订单来源，查询人员，反馈，中间结果等信息
function find(nQuestion1Level2,nRemark5,nCompany){

	netOrderId = $("#netOrderId").val();//网单号
	remark5="";
	company="";
	if ($('.askFlg').attr('checked')){
		if(nRemark5!=null){
			remark5=nRemark5;//订单来源
		}else{
			remark5=$("#remark5_hide").combobox('getText');
		}

		if(nCompany!=null){
			company=nCompany;//订单来源
		}else{
			company=$("#company_hide").combobox('getText');
		}
	}else{
		remark5=$("#remark5").val();//订单来源

		if(nCompany!=null){
			company=nCompany;//订单来源
		}else{
			if($("#company_hide").parent("td").is(":hidden")){//如果下拉工贸是隐藏的
				company =$("#company").val();//取input工贸
			}else{
				company=$("#company_hide").combobox('getText');//取下拉工贸
			}
		}
	}
	remark5=remark5!="-请选择-"?remark5:"";
	company=company!="-请选择-"?company:"";
	question1Level1=$("#question1Level1").combobox('getText');

	question1Level2="";
	if(nQuestion1Level2!=null){
		question1Level2=nQuestion1Level2;
	}else{
		question1Level2=$("#question1Level2").combobox('getText');
	}
	if(question1Level2==""){return;}
	$("#wangId").val("");

	//$("#context").css("display", "none");
	// 查询人员
	data=[];
//	if(question1Level1=="物流类"){
//		if ($('.askFlg').attr('checked')){
//			$.messager.alert('系统提示', '咨询类工单不能录入物流类责任位工单', 'warning');
//			return;
//			company="";remark5="";
//		}else{
//			data={
//					"question1level1_search" : question1Level1,
//					"question1level2_search" : question1Level2,
//					"company_search":company
//			};
//		}
//	}else if(question1Level1=="发票类"){
//		data={
//				"question1level1_search" : question1Level1
//		};
//	}else{
//		data={
//				"question1level1_search" : question1Level1,
//				"question1level2_search" : question1Level2,
//				"ordercome_search":remark5,
//		};
//	}
	if(question1Level1=="物流类"){
		if ($('.askFlg').attr('checked')){
			$.messager.alert('系统提示', '咨询类工单不能录入物流类责任位工单', 'warning');
			return;
		}
	}
	data={
		"question1level1_search" : question1Level1,
		"question1level2_search" : question1Level2,
		"company_search":company,
		"ordercome_search":remark5,
	};
	if((!(remark5==""&&company=="")&&remark5!="-请选择-")){
		$.ajax({
			type : "post",
			url : '/contacts/findcontacts.ajax',
			data : data,
			dataType : "json",
			success : function(result) {
//				alert(result);
				if(result!=null&&result.length>0){
					$("#wangId").val(result[0].username);
					$("#remark4").val(result[0].userid);
					$("#wangId").parent("td").show();
					$('#wangId_hide').parent("td").hide();
				}else{
					$("#wangId").parent("td").hide();
					$('#wangId_hide').parent("td").show();
				}
				$('#wangId_hide').combobox("setText","-请选择-").combobox("setValue","");
				//验证表单
				$('#fm').form('validate');
			}
		});
	}
	//判断是否是原责任位2
	if(question1Level2==""||question1Level2==$("#question1Level2-hidden").val()){
		return;
	}
	if(netOrderId==""){
		return;
	}

	$("#contextDiv2").html("");
	//$("#context").css("display", "none");
	// 查询评论信息
	$.ajax({
		type : "post",
		url : '/comment/selectcomment2.ajax',
		data : {
			"netOrderId" : netOrderId,
			"question1Level2" : question1Level2
		},
		dataType : "json",
		async : false,
		success : function(result) {
			if(result.success){
				if(result.message==1){
					$("#complaintFlg").attr("checked",true);
//					$("#HPBtn").attr("class", "easyui-linkbutton l-btn l-btn-small l-btn-plain");
				}else{
					$("#complaintFlg").attr("checked",false);
//					$("#HPBtn").attr("class", "easyui-linkbutton l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled");
				}
				result=result.result;
				if(result!=null&&result.length>0){
					for (var i = 0; i < result.length; i++) {
						var oldText = $("#contextDiv2").html();
						if (i > 0) {
							oldText += '<br/>';
						} else {
							oldText = '';
						}
						$("#contextDiv2").html(
							oldText + result[i].addtime + "  "
							+ result[i].adduser + "  "
							+ result[i].context);

					}

					// 查询图片信息
					$.ajax({
						type : "post",
						url : '/image/findreviewimagebyid.ajax',
						data : {
							reviewId :  result[0].reviewid
						},
						dataType : "json",
						async : false,
						success : function(data) {
							if(data.success){
								window.REVIEWID=result[0].reviewid;
								if(data.result){
									$("#tdStr").text("图片操作:");
									$("#down").show();
									$("#up").hide();
									$('#askFlg').attr('disabled', false);
//									$("#btnDownload").linkbutton("enable");
									//从新加载iframe
									$('#win').attr('src', '/img.html');
								}else{
									window.REVIEWID=null;
									//$("#tdStr").text("上传图片:");
									$("#up").show();
									$("#down").hide();
									$("#nullImg").hide();
									$('#askFlg').attr('disabled', false);$("#tdStr").text("上传图片:");
									$("#up").show();
									$("#down").hide();
									$("#nullImg").hide();
									$('#askFlg').attr('disabled', false);
								}
//								img=imageStr;
							}else{
								$.messager.alert('系统提示', data.message, 'warning');
							}
						}
					});
					$.messager.alert('系统提示', '存在网单号责任位一致的工单！', 'warning');
				}else{
					if(window.REVIEWID!=null){
						//$("#tdStr").text("上传图片:");
						$("#up").show();
						$("#down").hide();
						$("#nullImg").hide();
						$('#askFlg').attr('disabled', false);$("#tdStr").text("上传图片:");
						$("#up").show();
						$("#down").hide();
						$("#nullImg").hide();
						$('#askFlg').attr('disabled', false);
					}
				}
			}
		}
	});
	$("#backDiv2").html("");
	$("#backDiv3").html("");
	$("#finalResultDiv").html("");
	// 查询中间结果
	$.ajax({
		type : "post",
		url : '/middleresult/selectmiddleresult2.ajax',
		data : {
			"netOrderId" : netOrderId,
			"question1Level2" : question1Level2
		},
		dataType : "json",
		async : false,
		success : function(result) {
			if(result!=null&&result.length>0){
				for (var i = 0; i < result.length; i++) {
					var oldText = $("#backDiv2").html();
					if (i > 0) {
						oldText += '<br/>';
					} else {
						oldText = '';
					}
					$("#backDiv2").html(
						oldText + result[i].addtime + "  "
						+ result[i].adduser + "  "
						+ result[i].middleContext);
					// if(i+1==3)
					// {
					// 	if(row.workOrderTo=='HP')
					// 	{
					// 		break;
					// 	}
					// }
				}
			}
		}
	});
}
//重置按钮
function resetReview(){
	var selectedvalue=$("input[name='tab']:checked").val();
	if(selectedvalue==1){
		$("#netOrderId_search").val("");
		$("#orderId_search").val("");
//		$('#orderCome_search').combobox('setValue', "").combobox('setText', '-请选择-');
		$('#workStatus').combobox('setValue', "").combobox('setText', '-请选择-');
		$('#workOrderTo').combobox('setValue', "").combobox('setText', '-请选择-');
		$('#cType').combobox('setValue', "").combobox('setText', '-请选择-');
		$('#channelName_search').combobox('setValue', "").combobox('setText', '-请选择-');
		$('#remark5_search').combobox('setValue', "").combobox('setText', '-请选择-');
		$("#clientName").val("");
		$("#clientTel").val("");
		$('#company_search').combobox('setValue', "").combobox('setText', '-请选择-');
		$('#phone_search').combobox('loadData', {});
		$('#phone_search').combobox('setValue', "").combobox('setText', '');
		$("#person_search").val("");
		$("#addsearch_search").val("");
		$('#startTime_search').datetimebox("clear");
		$('#endTime_search').datetimebox("clear");
		$('#level1_search').combobox('setValue', "").combobox('setText', '-请选择-');
		$('#level2_search').combobox('setValue', "").combobox('setText', '-请选择-');
        $('#level3_search').combobox('setValue', "").combobox('setText', '-请选择-');
        $('#closeType_search').combobox('setValue', "").combobox('setText', '-请选择-');
		$("#id_search").val("");
		$('#complaintFlg_search').combobox('select', "");
	}else if(selectedvalue==2){
		$('#startTime_search2').datetimebox("clear");
		$('#endTime_search2').datetimebox("clear");
		$('#level1_search2').combobox('setValue', "").combobox('setText', '-请选择-');
	}else if(selectedvalue==3){
		$('#startTime_search3').datetimebox("clear");
		$('#endTime_search3').datetimebox("clear");
		$("#person_search3").val("");
	}

}
//订单详细2015/10/17
function lookorderSn(orderSn){
	if(orderSn==""){
		$.messager.alert('系统提示', "订单ID不能为空", 'warning');
		return false;
	}
	//alert(orderSn);
	$('#dlg4').dialog('open').dialog('setTitle', '订单详细');

	$.ajax({
		type : "post",
		url : '/workorder/findorderbyid.ajax',
		data : {"orderId":orderSn},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {

				$("#orderSn").html(data.result.orderSn);
				//订单状态
				$("#orderStatus").html(data.result.orderStatus);

				var n=data.result.source;
				for(var i=0;i<ordersource.length;i++){
					if(ordersource[i].value==n){
						$("#source").html(ordersource[i].valueMeaning);
						break;
					}
				}


				//是否日日单(1:是，0:否)：isProduceDaily
				if(data.result.isProduceDaily=='0'){
					$("#isProduceDaily").html("否");
				}if(data.result.isProduceDaily=='1'){
					$("#isProduceDaily").html("是");
				}
				$("#sourceOrderSn").html(data.result.sourceOrderSn);
				if(data.result.finishTime>0){
					var newTime = new Date(data.result.finishTime*1000); //毫秒数转换
					var str=newTime.format("yyyy-MM-dd hh:mm:ss");
					$("#finishTime").html(str);
				}else{
					$("#finishTime").html("");
				}


				$("#tradeSn").html(data.result.tradeSn);
				$("#agent").html(data.result.agent);


//				$("#syncTime").html(str);
//				$.messager.alert('系统提示', data.result, 'warning');
//				$('#dlg').dialog('close');
//				$('#dg').datagrid('reload');

			} else {
				$.messager.alert('系统提示', data.message, 'warning');
			}
		}
	});
}
//关闭设置
$('#dlg4').dialog({
	onClose:function(){
		$("#orderSn").html("");
		$("#orderStatus").html("");
		$("#source").html("");

		$("#isProduceDaily").html("");
		$("#sourceOrderSn").html("");
		$("#finishTime").html("");

		$("#tradeSn").html("");
		$("#agent").html("");
		$("#syncTime").html("");
	}
});
//网单详细2015/10/17
function lookOrderId(OrderId){
	if(OrderId==""){
		$.messager.alert('系统提示', "网单号不能为空", 'warning');
		return false;
	}
	//alert(OrderId);
	$('#dlg5').dialog('open').dialog('setTitle', '网单详细');

	$.ajax({
		type : "post",
		url : '/workorder/findcorderbyid.ajax',
		//url : 'http://172.16.63.67:7996/workorder/findcorderbyid.ajax',
		data : {"corderId":OrderId},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				$("#orderId1").html(data.result.cOrderSn);
				$("#price1").html(data.result.price);
				$("#number1").html(data.result.number);

				$("#netPointId1").html(data.result.netPointId);
				$("#sCode1").html(data.result.sCode);
				//网单状态
				$("#status1").html(data.result.status);

				$("#lessOrderSn1").html(data.result.lessOrderSn);
				var newTime = new Date(data.result.lessShipTime*1000); //毫秒数转换
				var str=newTime.format("yyyy-MM-dd hh:mm:ss");
				$("#lessShipTime1").html(str);
				$("#isMakeReceipt1").html(data.result.isMakeReceipt);

				$("#receiptNum1").html(data.result.receiptNum);
				$("#receiptAddTime1").html(data.result.receiptAddTime);
				//开票类型 0 初始值 1 库房开票  2 共享开票：makeReceiptType
				if(data.result.makeReceiptType=='0'){
					$("#makeReceiptType1").html("初始值");
				}
				if(data.result.makeReceiptType=='1'){
					$("#makeReceiptType1").html("库房开票");
				}
				if(data.result.makeReceiptType=='2'){
					$("#makeReceiptType1").html("共享开票");
				}


				$("#shippingFee1").html(data.result.shippingFee);
				//结算状态0 未结算 1已结算：settlementStatus
				if(data.result.settlementStatus=='0'){
					$("#settlementStatus1").html("未结算");
				}
				if(data.result.settlementStatus=='1'){
					$("#settlementStatus1").html("已结算");
				}
				$("#receiptOrRejectTime1").html(data.result.receiptOrRejectTime);
//				$.messager.alert('系统提示', data.result, 'warning');
//				$('#dlg').dialog('close');
//				$('#dg').datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.message, 'warning');
			}
		}
	});
}


//是否投诉下拉框
$(function() {
	var dataList=new Array();
	data={};
	data.value="";
	data.valueMeaning="-请选择-";
	dataList.push(data);
	data1={};
	data1.value="1";
	data1.valueMeaning="是";
	dataList.push(data1);
	data2={};
	data2.value="0";
	data2.valueMeaning="否";
	dataList.push(data2);
	$('#complaintFlg_search').combobox('loadData', dataList);

});
//是否投诉下拉框
$('#complaintFlg_search').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
//			onLoadSuccess : function(data) {
//				$('#remark5_search').combobox('setValue', "").combobox(
//						'setText', '-请选择-');
//			},
	});

//将每次点击查询都置到第一页
function setFirstPage(ids){
	var opts = $(ids).datagrid('options');
	var pager = $(ids).datagrid('getPager');
	opts.pageNumber = 1;
	opts.pageSize = opts.pageSize;
	pager.pagination('refresh',{
		pageNumber:1,
		pageSize:opts.pageSize
	});
}


// 合并同行
function MergeCells(tableID, fldList) {
	var Arr = fldList.split(",");
	var dg = $('#' + tableID);
	var fldName;
	var RowCount = dg.datagrid("getRows").length;
	var span;
	var PerValue = "";
	var CurValue = "";
	var PerId = "";
	var CurId = "";
	var length = Arr.length - 1;
	for (i = length; i >= 0; i--) {
		fldName = Arr[i];
		PerValue = "";
		PerId = "";
		span = 1;
		for (row = 0; row <= RowCount; row++) {
			if (row == RowCount) {
				CurValue = "";
			} else {
				CurValue = dg.datagrid("getRows")[row][fldName];
				CurId = dg.datagrid("getRows")[row].userid;
			}
			// if(userid==id){
			if (PerValue == CurValue && PerId == CurId) {
				span += 1;
			}
			// }

			else {
				var index = row - span;
				dg.datagrid('mergeCells', {
					index : index,// 列号
					field : fldName,// 行号
					rowspan : span,
					colspan : null
				});
				span = 1;
				PerValue = CurValue;
				PerId = CurId;

			}
		}
	}
}

//判断投诉信息是否选中，选中则把投诉电话设置为必填
function checkComplaintFlg(){
	var tsxz= document.getElementsByName('complaintFlg')[0].checked?true:false;
	if(tsxz==true)
	{
		$('#complaintPhone').validatebox({required:true});//必填
		$('#complaintPhone').attr('readonly', false);
	}
	else
	{
		$('#complaintPhone').validatebox({required:false});//去除必填
		$('#complaintPhone').val('');
		$('#complaintPhone').attr('readonly', true);

	}
}

//检查投诉电话
function checkPhone(phone){
	if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){
		$.messager.alert('系统提示', '手机号码有误，请重填', 'warning');
		return false;
	}
}
//获取订单来源全部(忽略是否启用)
$(function() {

	$.ajax({
		type : "get",
		url : "/dictionary/getdictionaryignoreflg.ajax",
		data : {
			"value_set_id" : "order_source"
		},
		async: false,
		dataType : "json",
		success : function(data) {
			remark5_hidedata=data;
			ordersource=data;
		}
	});

});