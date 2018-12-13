$(function(){
			//onQuery(1);
			$('#queryButton').click(function(){ 
            	$('#queryButton').attr('disabled',true);
            	onQuerys(1); 
            }); 
			
			   $('#dg').datagrid({
		    	striped : true, // 隔行变色
				rownumbers : true,
				fit: true,
				pagination : true,
				nowrap: true,
				singleSelect : false,
				fitColunms : true,
				columns : [ [  {
			        field: 'all',
			        checkbox: true
			    }, {
					title : '物料编码',
					width : 150,
					field : 'sku',
					align : 'center'
				}, {
					title : '库位编码',
					width : 150,
					field : 'secCode',
					align : 'center'
					
				}, {
					title : '库位名称',
					width : 150,
					field : 'secName',
					align : 'center'
				}, {
					title : '单据号',
					width : 130,
					field : 'refno',
					align : 'center'
				}, {
					title : '锁定数量',
					width : 130,
					field : 'lockQty',
					align : 'center'
				}, {
					title : '释放数量',
					width : 130,
					field : 'realeaseQty',
					align : 'center'
				},  {
					title : '操作人',
					width : 130,
					field : 'optUser',
					align : 'center'
				}, {
					title : '占用时间',
					width : 223,
					align : 'center',
					field : 'lockTime',
						formatter: function(value,row,index){
							return formatDatebox(row.lockTime);
					}
				},{
					title : '操作',
					width : 180,
					field : 'shifang',
					align : 'center',
					formatter: function(value,row,index){
					 	return "<a id='oper' href='#' onclick='unlockStock("+index+")' >全部释放</a> <a id='opener' href='#' onclick='onRelease("+index+")'>部分数量释放</a>";
				}
				} ] ],
				 toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
			});
			   $("#dg").datagrid('getPager').pagination({
		    	 pageSize: 50,
			     pageList: [50,200,300],
				onSelectPage : function(pageNumber, pageSize) {
					onQuerys(1);
				}
			});
        });


var timecount = 0;
function onQuerys(pageIndex){
	
	/*if(!checkDate("startDate","endDate")){
		return false;
	}else{*/
		var options = $("#dg").datagrid('getPager').data("pagination").options;
		  $("#size").val(options.pageSize);
		  $("#start").val(options.pageNumber);
		$('#pageIndex').val(pageIndex)
		timecount = 0;
		$("#list").html("<div style='width:100%;height:300px;text-align:center;'><br/><br/>数据正在加载，请稍后。。。<br/><br/><div id='timecount' style='width:100%;height:20px;text-align:center;font-size:10pt;font-family:Arial,Helvetica,sans-serif;'></div><br/><img src='/resources/images/tendency.gif'/></div>");
		
		$.ajax({
			url: "/invBaseStock/getInvStockLockList",
			data: $('#filterForm').serialize(),
			type: "POST",
			success: function(data) {
				try{
					$("#dg").datagrid('loadData',data);
					}catch(err){
					alert("没有数据");
					}
				
			}
		});
		
		setInterval("timer()",1000);
		return false;
	/*}*/
}
function timer(){
	timecount++;
	$("#timecount").html(timecount);
}
//方法二(推荐):
function gettime(t){
    var _time=new Date(t);
    var   year=_time.getFullYear();//2017
    var   month=_time.getMonth()+1;//7
    var   date=_time.getDate();//10
    var   hour=_time.getHours();//10
    var   minute=_time.getMinutes();//56
    var   second=_time.getSeconds();//15
    return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;//这里自己按自己需要的格式拼接
}

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
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
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
  
    return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)  
}
$("#dialog-div").css('display', 'none');
$(function(){
	
	if($(".tool-toggle").length){
		$(".tool-toggle").toggle(function(){
			var index=$(".tool-toggle").index(this);
			$(this).attr("class","tool-toggle tool-toggle-hide");
			$("fieldset").eq(index).addClass("hidefieldset");
			$("fieldset form").eq(index).hide();
		},function(){
			var index=$(".tool-toggle").index(this);
			$(this).attr("class","tool-toggle tool-toggle-show");
			$("fieldset").eq(index).removeClass("hidefieldset");
			$("fieldset form").eq(index).show();
		})
	}
	
});
function onRelease(index){
	$( "#dialog-div" ).dialog({
	 	autoOpen: false,
		modal: true,
		resizable: true,
		minHeight:200,
		width:600,		
		title: '释放数量',
		close: function( event, ui ) {
			$( "#dialog-div" ).dialog( "close" );
		}
	});

	
	var rows1= $('#dg').datagrid('getData').rows[index];
	$("#refnoId").val(rows1.refno);
	$("#skuId").val(rows1.sku);
	$("#secCodeId").val(rows1.secCode);
	$("#lockQtyId").val(rows1.lockQty);
	$("#id").val(rows1.id);
	$("#optUserId").val(rows1.optUser);
	$('#maxNumId').html(rows1.lockQty);
	
}

/*function getSource(refno,sku,secCode,lockQty,realeaseQty,id,optUser){
	$( "#dialog-form-input-id" ).val( "" );
	$( "#dialog-form-input-option" ).val( "create" );			
	$("#refnoId").val(refno);
	$("#skuId").val(sku);
	$("#secCodeId").val(secCode);
	$("#lockQtyId").val(lockQty);
	$("#realeaseQty").val(realeaseQty);
	$("#id").val(id);
	$("#optUserId").val(optUser);
	$("#maxNumId").html(lockQty-realeaseQty);
}*/
//部分释放
function sureRelease(){
	//var rows1= $('#dg').datagrid('getData').rows[index];
		var refno=$("#refnoId").val();
		/*$("#refnoId").val(refno);*/
		var sku=$("#skuId").val();
		/*$("#skuId").val(sku);*/
		var secCode=$("#secCodeId").val();
		/*$("#secCodeId").val(secCode);*/
		var lockQty=$("#lockQtyId").val();
		
		/*$("#lockQtyId").val(lockQty);*/
		var id=$("#id").val();
		/*$("#id").val(id);*/
		var num=$("#numId").val();
		var optUser=$("#optUserId").val();
		/*$("#optUserId").val(optUser);*/
		if ($.trim(secCode)==''||$.trim(secCode)==null || $.trim(secCode)=="undefined") {
			alert("库位编码不能为空");
			return false;
		}
		if ($.trim(sku)==''||$.trim(sku)==null || $.trim(sku)=="undefined") {
			alert("物料编码不能为空");
			return false;
		}
		if ($.trim(refno)==''||$.trim(refno)==null || $.trim(refno)=="undefined") {
			alert("网单号不能为空");
			return false;
		}
		if ($.trim(num)==''||$.trim(num)==null || $.trim(num)=="undefined") {
			alert("释放数量不能为空");
			return false;
		}
		if(!isNum(num)){
			return false;
		}
		if(parseInt(num)>parseInt(lockQty)){
			alert("释放数量应小于最大释放数量！");
			$("#numId").val("");
			return;
		}
		$.messager.confirm('询问',"确定要释放单号是"+refno+"的冻结记录吗？",function(r){
		    if(r){
		/*if(confirm("确定要释放单号是"+refno+"的冻结记录吗？")) {*/
			jQuery.ajax({
					url: "/memStockLock/cancelLockStock",   // 提交的页面
					data: {"refno":refno, "sku":sku, "secCode":secCode, "releaseQty":num, "id":id, "optUser": optUser}, // 从表单中获取数据
					type: "GET",                  
					success: function(data) {
						if ( data.success == true ) {
							//锁定剩余库存
							var frozenQty=lockQty-num;
							var data={};
							 if($("input[type='checkbox']").is(':checked')){
								 var refNo=$("#refNo").val();
								 data = {"sku":sku, "secCode":secCode, "frozenQty":frozenQty,"refNo":refNo}
							 }else{
								 data = {"sku":sku, "secCode":secCode, "frozenQty":frozenQty}
							 }
							if(frozenQty > 0){
								jQuery.ajax({
									url: "/memStockLock/lockStock",   
									data:data, 
									type: "GET",                 
									success: function(data) {
										if (data.message.trim().length >0 ){
											alert(data.message);
											return;
										}else{
											var pageIndex = $('#pageIndex').val();
											onQuery(pageIndex);
											$( "#dialog-div" ).dialog( "close" );
											return;
										} 
									}
								});
							}else{
								var pageIndex = $('#pageIndex').val();
								onQuery(pageIndex);
								$( "#dialog-div" ).dialog( "close" );
							}
						} else {
							alert(data.message);
						}
					}
			});
		}
		
		$('#dialog-div').dialog('close');
		 });
}

function isNum(id){
	if(id == ""){
		return true;
	}
	var regx = /^[0-9]{1,10}$/;
	if (!isNaN(id)) {
		if(regx.exec(id)){
			return true;
		}else{
			alert("请输入0或正整数，不能输入空格，且长度不超过10位!");
			return false;
		}
	} else {
		alert("释放数量必须输入数字");
		return false;
	}		
}
var timecount = 0;
function onQuery(pageIndex){
	if(!checkDate("startDate","endDate")){
		return false;
	}else{
		$('#pageIndex').val(pageIndex)
		timecount = 0;
		$("#list").html("<div style='width:100%;height:300px;text-align:center;'><br/><br/>数据正在加载，请稍后。。。<br/><br/><div id='timecount' style='width:100%;height:20px;text-align:center;font-size:10pt;font-family:Arial,Helvetica,sans-serif;'></div><br/><img src='/resources/images/tendency.gif'/></div>");
		jQuery.ajax({
			url: "/invBaseStock/getInvStockLockList.html",
			data: $('#filterForm').serialize(),
			type: "GET",
			success: function(data) {
				$("#list").html(data);
    			$('#queryButton').removeAttr("disabled");
			}
		});
		setInterval("timer()",1000);
		return false;
	}
}
function timer(){
	timecount++;
	$("#timecount").html(timecount);
}


//批量释放数据
function batchRelease(){
	var ckvals = $('#dg').datagrid('getChecked'); 
	if(ckvals.length == 0 ){
		alert('请勾选要全部释放的数据')
		return ;
	}
	$.messager.confirm('询问','确定所勾选的数据全部释放吗？',function(r){
	     if(r){
		 var arrInvStockLock = new Array();
		 var ids = "";
		 $.each(ckvals,function(){
			 alert(ckvals);
			 var secCode=this.secCode;
			 var sku=this.sku;
			 var refno=this.refno;
			 var lockQty=this.lockQty;
			 var optUser=this.optUser;
			 var id=this.id;
			 var invStockLock=new InvStockLock(sku,secCode,refno,lockQty,id,optUser)
			 arrInvStockLock.push(invStockLock);
		 });
		 
		 var jsonInvStockLock = JSON.stringify(arrInvStockLock);
		 jQuery.ajax({
				url: "/invBaseStock/batchRelease",
				data:"jsonInvStockLock="+jsonInvStockLock,
				type: "GET",
				success: function(data) {
					if(data.success == true){
						$('#queryButton').attr('disabled',true);
		            	onQuery(1); 
					}else{
						alert(data.message);
					}
				}
			});
	 }else{
		// alert(2);
	 }
	});
}

function InvStockLock(sku,secCode,refno,lockQty,id,optUser){
	this.sku = sku;
	this.secCode = secCode;
	this.refno = refno;
	this.lockQty = lockQty;
	this.id = id;
	this.optUser = optUser;
}

function radio1(){
	  //$("#refNo").removeAttr("readonly"); 
	  if($("input[type='checkbox']").is(':checked')){
	    $("#refNo").removeAttr("readonly"); 
	    $("#span1").show();
	  }else{
	    $("#refNo").attr("readonly","readonly"); 
	    $("#span1").hide();
	    $("#refNo").val(""); 
	  }
	}

//全部释放
function unlockStock(index) {
		
    var rows1 = $('#dg').datagrid('getData').rows[index];
 	var refno=rows1.refno;
	var sku=rows1.sku;
	var secCode=rows1.secCode;
	var lockQty=rows1.lockQty;
	var id=rows1.id
	var optUser=rows1.optUser;
	$.messager.confirm('询问',"确定要释放单号是"+refno+"的冻结记录吗？",function(r){
	if(r){
	jQuery.ajax({
			url: "/memStockLock/cancelLockStock",   // 提交的页面
			data: {"refno":refno, "sku":sku, "secCode":secCode, "releaseQty":lockQty, "id":id, "optUser": optUser}, // 从表单中获取数据
			type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
			success: function(data) {
				if ( data.success == true ) {
					var pageIndex = $('#pageIndex').val();
					onQuery(pageIndex);
				} else {
					alert(data.message);
				}
			}
	});
	}
});
}
function back(){
	window.location.href="/invBaseStock/loadInvBaseStockPage";
}

$(function(){
	$("input[type=checkbox][name=checkbox]").click(function(){    
		 allchk();   
	});
	$("#all").click(function(){    
	    if(this.checked){    
	        $("input[type=checkbox][name=checkbox]").attr("checked", true);   
	    }else{    
	        $("input[type=checkbox][name=checkbox]").attr("checked", false); 
	    }    
	}); 
})
function allchk(){ 
    var chknum = $("input[type=checkbox][name=checkbox]").size();//选项总个数 
    var chk = 0; 
    $("input[type=checkbox][name=checkbox]").each(function () {   
        if($(this).attr("checked")=='checked'){ 
            chk++; 
        } 
    }); 
    if(chknum==chk){//全选 
        $(".all").attr("checked",true); 
    }else{//不全选 
        $(".all").attr("checked",false); 
    } 
}