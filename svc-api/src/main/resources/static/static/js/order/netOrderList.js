/**
 * 吴坤洋 网单 2017-10-25
 */
var dataGrid = null;

 
//查询
 var searchselect = function () {
 	if($("#cOrderSn").val().trim()==''&&$("#orderSn").val().trim()==''&&$("#addTimeMin").datebox("getValue")==''&&$("#addTimeMax").datebox("getValue")==''&&$("#sourceOrderSn").val().trim()==''
 		&&$("#mobile").val().trim()==''&&$("#sku").val().trim()==''&&$("#sCode").val().trim()==''&&$("#receiptAddTimeMin").datebox("getValue")==''&&$("#receiptAddTimeMax").datebox("getValue")==''
 			&&$("#lessShipTimeMin").datebox("getValue")==''&&$("#lessShipTimeMax").datebox("getValue")==''&&$("#userAcceptTimeMin").datebox("getValue")==''&&$("#userAcceptTimeMax").datebox("getValue")==''){
 		alert('请输入 网单号-订单号-下单时间段-来源订单号-手机号-商品sku-库位编码-开票时间-LES出库时间 或者签收时间 作为条件查询');
 		return;
 	}
	 var options = dataGrid.datagrid('getPager').data("pagination").options;
 	 // var source = $("#source").combobox("getValues").join(",");
      $.ajax({
         url      : "/operationArea/search",
         type     : 'POST',
         dataType : 'json',
          data     : {
        	    cOrderSn : $("#cOrderSn").val(),
        	    orderSn :$("#orderSn").val().trim(),
        	  	sourceOrderSn:$("#sourceOrderSn").val().trim(),//来源订单号
        	  	mobile :$("#mobile").val().trim(),//手机号
      		  	consignee:$("#consignee").val().trim(),//收货人
      		  	productName:$("#productName").val().trim(),//商品名称
      		  	sku:$("#sku").val().trim(),//商品SKU
      		  	// price:$("#price").val().trim(),//商品单价
      		  	// numberMin :$("#numberMin").val().trim(),//购买数量
      		  	// numberMax :$("#numberMax").val().trim(),//购买数量
      		  	scode :$("#sCode").val().trim(),//库位编码
      		  	addTimeMin:$("#addTimeMin").datetimebox("getValue"),//下单时间 开始
      		  	addTimeMax :$("#addTimeMax").datetimebox("getValue"),//下单时间 截止
      		  	// source : source,//订单来源
      		  	source :$("#source").combobox("getValue"),//订单来源
      	  	  	paymentCode :$("#paymentCode").combobox("getValue"),//支付方式
      	  	  	paymentStatus :$("#paymentStatus").combobox("getValue"),//支付状态
      		  	orderStatus:$("#orderStatus").combobox("getValue"),//订单状态
      		  	firstConfirmTime:$("#firstConfirmTime").combobox("getValue"),//从未确认过
      		  	status:$("#status").combobox("getValue"),//网单状态
      		  	isNotConfirm :$("#isNotConfirm").combobox("getValue"),//需确认订单
      		  	isTsCode :$("#isTsCode").combobox("getValue"),//转运发货
    		  	/*isHpTimeout :$("#isHpTimeout").combobox("getValue"),//派工异常
*/    		  	supportOneDayLimit :$("#supportOneDayLimit").combobox("getValue"),//24小时限时达
      		  	// isEs :$("#isEs").combobox("getValue"),//节能补贴
      		  	isBook :$("#isBook").combobox("getValue"),//预定网单
      		  	regionAssign:$("#regionAssign").combobox("getValue"),
      		  	/*isActivity :$("#isActivity").combobox("getValue"),//团购网单
*/      		  	isExternal:$("#isExternal").combobox("getValue"),//淘宝套装网单
//      		  	isnotice :$("#isNotice").combobox("getValue"),//预警
//      		  	noticetime :$("#noticeTime").combobox("getValue"),//预警时间
              	isReceipt:$("#isReceipt").combobox("getValue"),//需要开具发票
      		    shippingMode:$("#shippingMode").combobox("getValue"),//物流模式
      		  makeReceiptTypeStatus:$("#makeReceiptType").combobox("getValue"),//开票类型
              	isMakeReceipt:$("#isMakeReceipt").combobox("getValue"),//开票状态
      		  	// memberRankId:$("#memberRankId").combobox("getValue"),//会员等级
      		  	orderType:$("#orderType").combobox("getValue"),//订单类型
      		  brandId:$("#brandId").combobox("getValue"),//品牌
	      		cateId:$("#cateId").combobox("getValue"),//商品分类
	      		isBackend:$("#isBackend").combobox("getValue"),//后台订单
	      		autoConfirmNumMin :$("#autoConfirmNumMin").val(),//已确认次数
	      		autoConfirmNumMax :$("#autoConfirmNumMax").val(),//已确认次数
	      		// o2o:$("#o2o").combobox("getValue"),//是否O2O网单
	      		stockType:$("#optype").combobox("getValue"),//网单库存类型
	      		receiptAddTimeMin:$("#receiptAddTimeMin").datebox("getValue"),//开票时间
	      		receiptAddTimeMax:$("#receiptAddTimeMax").datebox("getValue"),//开票时间 结束
	      		payTimeMin:$("#payTimeMin").datebox("getValue"),//付款时间
	      		payTimeMax:$("#payTimeMax").datebox("getValue"),//付款时间 结束
	      		// isUseCoupon:$("#isUseCoupon").combobox("getValue"),//使用优惠券
	      		// isUsePoint:$("#isUsePoint").combobox("getValue"),//使用积分
	      		/*isSelfSell:$("#isSelfSell").combobox("getValue").trim(),//自营
*/	      		confirmTimeMin:$("#confirmTimeMin").datebox("getValue"),//订单确认时间
	      		confirmTimeMax:$("#confirmTimeMax").datebox("getValue"),//订单确认时间 结束
	      		// sellPeople :$("#sellPeople").val(),//销售代表
	      		// couponCode :$("#couponCode").val(),//推广码
	      		lessShipTimeMin:$("#lessShipTimeMin").datebox("getValue"),//LES出库时间
	      		lessShipTimeMax:$("#lessShipTimeMax").datebox("getValue"),//LES出库时间 结束
	      		userAcceptTimeMin:$("#userAcceptTimeMin").datebox("getValue"),//签收时间
	      		userAcceptTimeMax:$("#userAcceptTimeMax").datebox("getValue"),//签收时间 结束
			    idGift:$("#idGift").combobox("getValue"),//是否是赠品
	      		provinceId:$("#provinceId").val(),
	      		// isProduceDaily:$("#isProduceDaily").combobox("getValue"),
             page            : options.pageNumber,
             rows            : options.pageSize
         },
          beforeSend: function(){
              var win = $.messager.progress({
                  title:'请等待',
                  msg:'正在执行……',
                  text:'执行中',
                  interval:700
              });
          }, complete: function(data){
              //AJAX请求完成时隐藏loading提示
              $.messager.progress('close');
          },
         success  : function(data) {
        	  var datagridData_orderForecastGoal = {
          		    'data': {
          		        'records':
          		        	data.rows,'totalCount':data.total
          		            }
        	  }
        	  dataGrid.datagrid('loadData', datagridData_orderForecastGoal);
         }
     });
 }
    
      $('#search').click(function() {
    	  var options = dataGrid.datagrid('getPager').data("pagination").options;
			options.pageNumber = 1;
			dataGrid.datagrid('getPager').pagination('refresh');
    	  searchselect()
    	  }



      )
$("#importBtn_orderForecastGoal").on('click', function (event) {
	if($("#datagrid_orderForecastGoal").datagrid('getData').total == 0){
			$.messager.alert('提示','请查询！','info');
				return;    
			} 
			$.messager.confirm('确认','确定要导出吗(最大导出50000条)？', function(r){
				if (r){
                    // var source = $("#source").combobox("getValues").join(",");
					var queryParameters = {
							cOrderSn : $("#cOrderSn").val(),
			        	    orderSn :$("#orderSn").val().trim(),
			        	  	sourceOrderSn:$("#sourceOrderSn").val().trim(),//来源订单号
			        	  	mobile :$("#mobile").val().trim(),//手机号
			      		  	consignee:$("#consignee").val().trim(),//收货人
			      		  	productName:$("#productName").val().trim(),//商品名称
			      		  	sku:$("#sku").val().trim(),//商品SKU
			      		  	// price:$("#price").val().trim(),//商品单价
			      		  	// numberMin :$("#numberMin").val().trim(),//购买数量
			      		  	// numberMax :$("#numberMax").val().trim(),//购买数量
			      		  	scode :$("#sCode").val().trim(),//库位编码
							addTimeMin:$("#addTimeMin").datetimebox("getValue"),//下单时间 开始
                        	addTimeMax :$("#addTimeMax").datetimebox("getValue"),//下单时间 截止
                        	source :$("#source").combobox("getValues"),//订单来源
			      	  	  	paymentCode :$("#paymentCode").combobox("getValue"),//支付方式
			      	  	  	paymentStatus :$("#paymentStatus").combobox("getValue"),//支付状态
			      		  	orderStatus:$("#orderStatus").combobox("getValue"),//订单状态
			      		  	firstConfirmTime:$("#firstConfirmTime").combobox("getValue"),//从未确认过
			      		  	status:$("#status").combobox("getValue"),//网单状态
			      		  	isNotConfirm :$("#isNotConfirm").combobox("getValue"),//需确认订单
			      		  	isTsCode :$("#isTsCode").combobox("getValue"),//转运发货
			    		  	/*isHpTimeout :$("#isHpTimeout").combobox("getValue"),//派工异常
			*/    		  	supportOneDayLimit :$("#supportOneDayLimit").combobox("getValue"),//24小时限时达
			      		  	// isEs :$("#isEs").combobox("getValue"),//节能补贴
			      		  	isBook :$("#isBook").combobox("getValue"),//预定网单
			      		  	regionAssign:$("#regionAssign").combobox("getValue"),
			      		  	/*isActivity :$("#isActivity").combobox("getValue"),//团购网单
			*/      		  	isExternal:$("#isExternal").combobox("getValue"),//淘宝套装网单
//			      		  	isnotice :$("#isNotice").combobox("getValue"),//预警
//			      		  	noticetime :$("#noticeTime").combobox("getValue"),//预警时间
                        	isReceipt:$("#isReceipt").combobox("getValue"),//需要开具发票
			      		    shippingMode:$("#shippingMode").combobox("getValue"),//物流模式
			      		  makeReceiptTypeStatus:$("#makeReceiptType").combobox("getValue"),//开票类型
                        	isMakeReceipt:$("#isMakeReceipt").combobox("getValue"),//开票状态
			      		  	// memberRankId:$("#memberRankId").combobox("getValue"),//会员等级
			      		  	orderType:$("#orderType").combobox("getValue"),//订单类型
			      		  brandId:$("#brandId").combobox("getValue"),//品牌
				      		cateId:$("#cateId").combobox("getValue"),//商品分类
				      		isBackend:$("#isBackend").combobox("getValue"),//后台订单
				      		autoConfirmNumMin :$("#autoConfirmNumMin").val(),//已确认次数
				      		autoConfirmNumMax :$("#autoConfirmNumMax").val(),//已确认次数
				      		// o2o:$("#o2o").combobox("getValue"),//是否O2O网单
				      		stockType:$("#optype").combobox("getValue"),//网单库存类型
				      		receiptAddTimeMin:$("#receiptAddTimeMin").datebox("getValue"),//开票时间
				      		receiptAddTimeMax:$("#receiptAddTimeMax").datebox("getValue"),//开票时间 结束
				      		payTimeMin:$("#payTimeMin").datebox("getValue"),//付款时间
				      		payTimeMax:$("#payTimeMax").datebox("getValue"),//付款时间 结束
				      		// isUseCoupon:$("#isUseCoupon").combobox("getValue"),//使用优惠券
				      		// isUsePoint:$("#isUsePoint").combobox("getValue"),//使用积分
				      		/*isSelfSell:$("#isSelfSell").combobox("getValue").trim(),//自营
			*/	      		confirmTimeMin:$("#confirmTimeMin").datebox("getValue"),//订单确认时间
				      		confirmTimeMax:$("#confirmTimeMax").datebox("getValue"),//订单确认时间 结束
				      		// sellPeople :$("#sellPeople").val(),//销售代表
				      		// couponCode :$("#couponCode").val(),//推广码
				      		lessShipTimeMin:$("#lessShipTimeMin").datebox("getValue"),//LES出库时间
				      		lessShipTimeMax:$("#lessShipTimeMax").datebox("getValue"),//LES出库时间 结束
				      		userAcceptTimeMin:$("#userAcceptTimeMin").datebox("getValue"),//签收时间
				      		userAcceptTimeMax:$("#userAcceptTimeMax").datebox("getValue"),//签收时间 结束
                        	idGift:$("#idGift").combobox("getValue"),//是否是赠品
				      		provinceId:$("#provinceId").val()
					};
					
					/*$.ajax({
					    type: 'get',
					    url: '/operationArea/exportOrderList',
					    data: queryParameters,
					    async: false
					});*/
					
					/*$('#exportData').val(JSON.stringify(queryParameters));
					//     '/orderOperation/exportOrderList'
					$('#paramForm_orderForecastGoal').attr("action", "/operationArea/exportOrderList");
					$('#paramForm_orderForecastGoal').submit();*/
					$.download("/operationArea/exportOrderList",queryParameters,'post' );	

				}
			});
	    });
      jQuery.download = function(url, data, method){ // 获得url和data
    	    if( url && data ){ 
    	        // data 是 string 或者 array/object
                data = typeof data == 'string' ? data : jQuery.param(data,true); // 把参数组装成 form的 input
                var inputs = '';
    	        jQuery.each(data.split('&'), function(){ 
    	            var pair = this.split('=');
    	            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />'; 
    	        }); // request发送请求
    	        jQuery('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>').appendTo('body').submit().remove();
    	    };
    	};

$(function () {
    var moreOptions = $(".morehide");
    var osn = $("#orderSn").val().trim();
    for (var i = 0; i < moreOptions.length; i++) {
        $(moreOptions[i]).css("display", "none");
    } ;
    dataGrid = $('#datagrid_orderForecastGoal').datagrid({
		striped : true, // 隔行变色
		rownumbers : true,
		fit: true,
		pagination : true,
		singleSelect : true,
		fitColunms : true,
		idField : 'id',
		columns : [ [ {
			title : '网单编号 ',
			width : 175,
			field : 'cOrderSn',
			align : 'center',
			formatter: function(value,row,index){
				return '<a href="javascript:void(0);" onclick="jumpNetOrderList(\''+row.cOrderSn+'\')">'+row.cOrderSn+'</a>';
			}
		}, {
			title : '订单编号 ',
			width : 215,
			field : 'orderSn',
			align : 'center',
			formatter: function(value,row,index){
					return '<a href="javascript:void(0);" onclick="jumpOrderList(\''+row.orderSn+'\')">'+row.orderSn+'</a>';
			}
		}, {
			title : '发票',
			width : 75,
			field : 'isReceipt',
			align : 'center',
			formatter: function(value,row,index){
				var isrece="";
				if(row.isReceipt=='0'){
					isrece="不需要";
				}else {
					isrece='需要';
				}
				return isrece ;
		}
		}, {
			title : '配送时效(天)',
			width : 100,
			field : 'diliveryTime',
			align : 'right'
		}, {
			title : '预定',
			width : 80,
			field : 'isbook',
			align : 'center',
			formatter: function(value,row,index){
					var isb="";
					if(row.isbook =='0'){
						isb="NO";
					}else {
						isb='YES';
					}
					return isb ;
			}
		}, {
			title : '节能补贴',
			width : 120,
			field : 'isEs',
			align : 'center'
		},  {
			title : '商品名称',
			width : 260,
			field : 'productName',
			align : 'center'
		}, {
			title : '价格',
			width : 90,
			field : 'price'
		},{
			title : '数量',
			width : 70,
			field : 'number',
			align : 'center'
		}, {
			title : '金额',
			width : 80,
			field : 'money',
			align : 'right'
		}, {
			title : '库位 ',
			width : 100,
			field : 'sCode',
			align : 'center'
		}, {
			title : '下单时间',
			width : 195,
			field : 'addTimeMin',
			align : 'center'
		}, {
			
			title : '备注 ',
			width : 140,
			field : 'remark',
			align : 'center'
		} , {
			title : '状态 ',
			width : 100,
			field : 'statusTs',
			align : 'center'
		}, {
			title : '首次确认时间 ',
			width : 195,
			field : 'firstConfirmTime',
			align : 'center'
		}, {
			title : '已确认次数 ',
			width : 120,
			field : 'autoConfirmNumMin',
			align : 'center'
		}, {
			title : '转人工时间 ',
			width : 195,
			field : 'smManualTime',
			align : 'center',
            formatter: function(value,row,index){
                var isb="";
                if(row.smManualTime =='1970-01-01 08:00:00'){

                }else {
                    isb=row.smManualTime;
                }
                return isb ;
            }
		} ] ],
		 toolbar: '#datagridToolbar_orderForecastGoal'
	});
    dataGrid.datagrid('getPager').pagination({
    	 pageSize: 50,
	     pageList: [50,100,200],
		onSelectPage : function(pageNumber, pageSize) {
			var gridOpts = $('#datagrid_orderForecastGoal').datagrid('options');
			gridOpts.pageNumber = pageNumber;
			gridOpts.pageSize = pageSize;
			searchselect();
		}
	});
    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_orderForecastGoal').form('reset');
        $('#addForm_orderForecastGoal').find('[__actType]').val('add');
        $('#addDlg_orderForecastGoal').dialog({'title': '新增'});
        $('#addDlg_orderForecastGoal').dialog('open');
    });
    
    
    $("#addDlgSaveBtn_orderForecastGoal").on('click', function () {
        if (!$('#addForm_orderForecastGoal').form('validate')) {
            return;
        }
        var actType = $('#addForm_orderForecastGoal').find('[__actType]').val();
        if (actType === 'add') {
            alert('新增成功');
        } else {
            alert('编辑成功');
        }
        $('#addDlg_orderForecastGoal').dialog('close');
    });
    $("#editBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_orderForecastGoal').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_orderForecastGoal').form('load', selected);
            $('#addForm_orderForecastGoal').find('[__actType]').val('edit');
            $('#addDlg_orderForecastGoal').dialog('open');
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
            confirm('确定删除？', function (r) {
                if (r) {
                    alert('删除成功');
                }
            })

        } else {
            alert('请选择一条数据');
        }
    });
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        // event.preventDefault();
        // document.getElementById("paramForm_orderForecastGoal").reset();
        $("#cOrderSn").textbox("setValue","");
		$("#orderSn").textbox("setValue","");
		$("#sourceOrderSn").textbox("setValue","");
		$("#mobile").textbox("setValue","");
		$("#consignee").textbox("setValue","");
		$("#productName").textbox("setValue","");
		$("#sku").textbox("setValue","");
        $("#cateId").combobox("setValue","");
        $("#sCode").textbox("setValue","");
        $("#addTimeMin").datebox("setValue","");
        $("#addTimeMax").datebox("setValue","");
        $("#source").combobox("setValue","");
        $("#paymentCode").combobox("setValue","");
        $("#paymentStatus").combobox("setValue","");
        $("#orderStatus").combobox("setValue","");
        $("#firstConfirmTime").combobox("setValue","");
        $("#status").combobox("setValue","");
        $("#isNotConfirm").combobox("setValue","");
        $("#isTsCode").combobox("setValue","");
        $("#supportOneDayLimit").combobox("setValue","");
        $("#isBook").combobox("setValue","");
        $("#isExternal").combobox("setValue","");
        $("#regionAssign").combobox("setValue","");
        $("#isReceipt").combobox("setValue","");
        $("#shippingMode").combobox("setValue","");
        $("#makeReceiptType").combobox("setValue","0");
        $("#isMakeReceipt").combobox("setValue","0");
        $("#provinceId").combobox("setValue","");
        $("#orderType").combobox("setValue","");
        $("#brandId").combobox("setValue","");
        $("#isBackend").combobox("setValue","");
        $("#autoConfirmNumMin").textbox("setValue","");
        $("#autoConfirmNumMax").textbox("setValue","");
        $("#optype").combobox("setValue","");
        $("#receiptAddTimeMin").datebox("setValue","");
        $("#receiptAddTimeMax").datebox("setValue","");
        $("#payTimeMin").datebox("setValue","");
        $("#payTimeMax").datebox("setValue","");
        $("#confirmTimeMin").datebox("setValue","");
        $("#confirmTimeMax").datebox("setValue","");
        $("#lessShipTimeMin").datebox("setValue","");
        $("#lessShipTimeMax").datebox("setValue","");
        $("#userAcceptTimeMin").datebox("setValue","");
        $("#userAcceptTimeMax").datebox("setValue","");


    });

	if(osn!=null && osn !=""){
		searchselect();
	}
//    searchselect();
});
var showhideoptions = function (t) {
    var $this = $(t);
    if ($this.hasClass('shouqi')) {

        $this.removeClass('shouqi');
        setTimeout(function () {
            $this.addClass('zhankai');
            var moreOptions = $(".morehide");
            for (var i = 0; i < moreOptions.length; i++) {
                $(moreOptions[i]).css("display", "");
            }
            ;
            $("#moreSpan").text("收起");
            $("#moreImag").attr("src", "../static/img/up_icon.png");
        }, 0);

    } else {
        $this.removeClass('zhankai');
        setTimeout(function () {
            $this.addClass('shouqi');
            var moreOptions = $(".morehide");
            for (var i = 0; i < moreOptions.length; i++) {
                $(moreOptions[i]).css("display", "none");
            }
            ;
            $("#moreSpan").text("更多");
            $("#moreImag").attr("src", "../static/img/down_icon.png");
        }, 0);
    }
    setTimeout(function () {
        $('#datagrid_orderForecastGoal').datagrid('resize');
    }, 500);

};
/*
$('#datagrid_orderForecastGoal').datagrid({

//双击事件
    onDblClickRow :function(rowIndex,rowData){
        addTab("网单详情[WD170817233445]","order/norderDetail.html",false);
    }
});*/


function jumpNetOrderList(NetOrderSn){
	window.top.addTab("网单详情", "/operationArea/ProductView?cOrderSn="+NetOrderSn, true);
}

function jumpOrderList(orderSn){
	window.top.addTab("订单详情", "/operationArea/orderNumberSelect?orderSn="+orderSn, true);
}