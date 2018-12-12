var dataGrid = null;
//查询
 var searchselect = function () {
	 var options = dataGrid.datagrid('getPager').data("pagination").options;
      $.ajax({
         url      : "/orderOperation/findQueryOrderList",
         type     : 'POST',
         dataType : 'json',
          data     : {		smConfirmStatus:$("#smConfirmStatus").combobox('getValue'),
							isCod:$("#isCod").combobox('getValue'),
							orderSn:$("#orderSn").val(),
							sourceOrderNo:$("#sourceOrderNo").val(),
							mobile:$("#mobile").val(),
							consignee:$("#consignee").val(),
							productName:$("#productName").val(),
							source:$("#source").combobox('getValue'),
							orderStatus:$("#orderStatus").combobox('getValue'),
							//paymentName:$("#paymentName").combobox('getValue'),
							paymentStatus:$("#paymentStatus").combobox('getValue'),
							paymentCode:$("#paymentCode").combobox('getValue'),
							timesBegine:$("#timesBegine").val(),
							timesEnd:$("#timesEnd").val(),
							addTimeBegine:$("#addTimeBegine").datebox('getValue'),
							addTimeEnd:$("#addTimeEnd").datebox('getValue'),
							modifiedBegine:$("#modifiedBegine").datebox('getValue'),
							modifiedEnd:$("#modifiedEnd").datebox('getValue'),
							orderType:$("#orderType").combobox('getValue'),
							invoiceType:$("#invoiceType").combobox('getValue'),
							provinceId:$("#provinceId").combobox('getValue'),
							payTimeSort:$("#payTimeSort").combobox('getValue'),
							isUsePoint:$("#isUsePoint").combobox('getValue'),
							isProduceDaily:$("#isProduceDaily").combobox('getValue'),
				            page: options.pageNumber,
				            rows: options.pageSize
         }, 
         success  : function(data) {
        	  var datagridData_orderForecastGoal = {
          		    'data': {
          		        'records':
          		        	data.rows,'totalCount':data.total
          		            }
        	  }
        	  dataGrid.datagrid('loadData', datagridData_orderForecastGoal);
        	  options.total = data.total;
         }
     });
 }
    
      $('#searchBtn_orderForecastGoal').click(function() {
    	  var options = dataGrid.datagrid('getPager').data("pagination").options;
			options.pageNumber = 1;
			dataGrid.datagrid('getPager').pagination('refresh');
    	  searchselect()
    	  }
      )

$(function () {
	 var queryParameters={		smConfirmStatus:$("#smConfirmStatus").combobox('getValue'),
								isCod:$("#isCod").combobox('getValue'),
								orderSn:$("#orderSn").val(),
								sourceOrderNo:$("#sourceOrderNo").val(),
								mobile:$("#mobile").val(),
								consignee:$("#consignee").val(),
								productName:$("#productName").val(),
								source:$("#source").combobox('getValue'),
								orderStatus:$("#orderStatus").combobox('getValue'),
								//paymentName:$("#paymentName").combobox('getValue'),
								paymentStatus:$("#paymentStatus").combobox('getValue'),
								paymentCode:$("#paymentCode").combobox('getValue'),
								timesBegine:$("#timesBegine").val(),
								timesEnd:$("#timesEnd").val(),
								addTimeBegine:$("#addTimeBegine").datebox('getValue'),
								addTimeEnd:$("#addTimeEnd").datebox('getValue'),
								modifiedBegine:$("#modifiedBegine").datebox('getValue'),
								modifiedEnd:$("#modifiedEnd").datebox('getValue'),
								orderType:$("#orderType").combobox('getValue'),
								invoiceType:$("#invoiceType").combobox('getValue'),
								provinceId:$("#provinceId").combobox('getValue'),
								payTimeSort:$("#payTimeSort").combobox('getValue'),
								isUsePoint:$("#isUsePoint").combobox('getValue'),
								isProduceDaily:$("#isProduceDaily").combobox('getValue'),
	         };
    var moreOptions = $(".morehide");
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
		columns : [ [ 
					{title: '订单号 ', field: 'orderSn', sortable: true,
			        	formatter: function(value,row,index){
							return '<a href="javascript:void(0)" onclick="addTab2(\''+row.orderSn+'\')">'+row.orderSn+'</a>';
						}
					},
			        {title: '货到付款', field: 'isCod', sortable: true,
			        	formatter: function(value,row,index){
							var isNot="";
							if(row.isCod =='0'){
								isNot="否";
							}else {
								isNot='是';
							}
							return isNot ;
						}	
			        },
			        {title: '订单来源', field: 'source', sortable: true,
			        	formatter: function(value,row,index){
							var isNot="";
							if(row.source =='TBSC'){
								isNot="海尔官方淘宝旗舰店";
							}
							if(row.source =='TOPBOILER'){
								isNot="海尔热水器专卖店";
							}
							if(row.source =='TONGSHUAI'){
								isNot="淘宝网统帅日日顺乐家专卖店";
							}
							if(row.source =='TONGSHUAIFX'){
								isNot="统帅品牌商";
							}
							if(row.source =='TOPXB'){
								isNot="海尔新宝旗舰店";
							}
							if(row.source =='MOOKA'){
								isNot="mooka模卡官方旗舰店";
							}
							if(row.source =='WASHER'){
								isNot="海尔洗衣机旗舰店";
							}
							if(row.source =='FRIDGE'){
								isNot="海尔冰冷旗舰店";
							}
							if(row.source =='AIR'){
								isNot="海尔空调旗舰店";
							}
							if(row.source =='TBCT'){
								isNot="村淘海尔商家";
							}
							if(row.source =='GQGYS'){
								isNot="海尔官方旗舰店供应商";
							}
							if(row.source =='TMKSD'){
								isNot="天猫卡萨帝旗舰店";
							}
							if(row.source =='TMTV'){
								isNot="海尔电视旗舰店";
							}
							if(row.source =='TBCFDD'){
								isNot="海尔厨房大电旗舰店";
							}
							if(row.source =='TBXCR'){
								isNot="天猫小超人旗舰店";
							}
							if(row.source =='TOPSHJD'){
								isNot="海尔生活电器专卖店";
							}
							if(row.source =='TOPDHSC'){
								isNot="海尔生活家电旗舰店";
							}
							if(row.source =='GMZX'){
								isNot="统帅国美旗舰店";
							}
							if(row.source =='GMZXTS'){
								isNot="统帅国美官方旗舰店";
							}
							if(row.source =='GMTSZYCW'){
								isNot="统帅国美旗舰店(厨卫)";
							}
							if(row.source =='GMTSZYKT'){
								isNot="统帅国美旗舰店(空调)";
							}
							if(row.source =='GMTSZYBX'){
								isNot="统帅国美旗舰店(冰箱)";
							}
							if(row.source =='GMTSZYXYJ'){
								isNot="统帅国美旗舰店(洗衣机)";
							}
							if(row.source =='yihaodian'){
								isNot="一号店";
							}
							if(row.source =='yihaodiants'){
								isNot="一号店统帅";
							}
							if(row.source =='YHDZY'){
								isNot="一号店自营";
							}
							if(row.source =='YHDQWZY'){
								isNot="一号店全网自营";
							}
							if(row.source =='jianhang'){
								isNot="建行";
							}
							if(row.source =='ICBC'){
								isNot="工商";
							}
							if(row.source =='YMX'){
								isNot="亚马逊";
							}
							if(row.source =='SNYG'){
								isNot="苏宁";
							}
							if(row.source =='SNHEGQ'){
								isNot="苏宁海尔集团官方旗舰店";
							}
							if(row.source =='SNQDZX'){
								isNot="苏宁渠道中心";
							}
							if(row.source =='DDW'){
								isNot="当当";
							}
							if(row.source =='YLW'){
								isNot="邮乐";
							}
							if(row.source =='YDYZ'){
								isNot="有赞微商城";
							}
							if(row.source =='KDGW'){
								isNot="微店";
							}
							if(row.source =='MD'){
								isNot="萌店";
							}
							if(row.source =='JDXYJ'){
								isNot="京东";
							}
							return isNot ;
						}
			        },
			        {title: '来源订单号', field: 'sourceOrderNo', sortable: true},
			        {title: '商品名称', field: 'productName', sortable: true},
			        {title: '收货人', field: 'consignee', sortable: true},
			        {title: '手机号码', field: 'mobile', sortable: true},
			        {title: '发票类型', field: 'invoiceType', sortable: true,
			        	formatter: function(value,row,index){
							var isNot = "";
							if(row.invoiceType =='1'){
								isNot="增值税发票";
							}
							if(row.invoiceType =='2'){
								isNot="普通发票";
							}
							return isNot ;
						}
			        },
			        {title: '下单时间', field: 'orderAddTime', sortable: true,
			        	formatter: function(value,row,index){
							var isNot = row.orderAddTime;
							if(row.orderAddTime =='1970-01-01 08:00:00'){
								return "";
							}
							return row.orderAddTime;
						}	
			        },
			        {title: '更新时间', field: 'orderModified', sortable: true,
			        	formatter: function(value,row,index){
							var isNot = row.orderModified;
							if(row.orderModified =='0000-00-00 00:00:00'){
								isNot="";
							}
								return isNot;
						}	
			        },
			        {title: '同步到商城时间', field: 'syncTime', sortable: true,
			        	formatter: function(value,row,index){
							var isNot = row.syncTime;
							if(row.syncTime =='1970-01-01 08:00:00'){
								isNot="";
							}
							return isNot;
						}	
			        },
			        {title: '付款时间', field: 'payTime', sortable: true,
			        	formatter: function(value,row,index){
							var isNot = row.payTime;
							if(row.payTime =='1970-01-01 08:00:00'){
								isNot="";
							}
							return isNot;
						}	
			        },
			        {title: '首次确认时间', field: 'firstConfirmTime', sortable: true,
			        	formatter: function(value,row,index){
							var isNot = row.firstConfirmTime;
							if(row.firstConfirmTime =='1970-01-01 08:00:00'){
								isNot="";
							}
							return isNot ;
						}	
			        },
			        {title: '订单金额', field: 'orderMoney', sortable: true},
			        {title: '订单类型', field: 'orderType', sortable: true,
			        	formatter: function(value,row,index){
							var isNot="";
							if(row.orderType =='0'){
								isNot="普通订单";
							}
							if(row.orderType =='2'){
								isNot="定金-尾款";
							}
							return isNot ;
						}
			        },
			        {title: '订单状态', field: 'orderStatus', sortable: true,
			        	formatter: function(value,row,index){
							var isNot="";
							if(row.orderStatus =='200'){
								isNot="未确认";
							}
							if(row.orderStatus =='201'){
								isNot="已确认";
							}
							if(row.orderStatus =='203'){
								isNot="已完成";
							}
							if(row.orderStatus =='202'){
								isNot="已取消";
							}
							return isNot ;
						}
			        },
			        {title: '支付方式', field: 'paymentName', sortable: true},
			        {title: '支付状态', field: 'paymentStatus', sortable: true,
			           formatter: function(value,row,index){
							var isNot="";
							if(row.paymentStatus =='100'){
								isNot="未付款";
							}
							if(row.paymentStatus =='101'){
								isNot="已付款";
							}
							if(row.paymentStatus =='102'){
								isNot="待退款";
							}
							if(row.paymentStatus =='103'){
								isNot="已退款";
							}
							return isNot ;
						}
			        },
			        {title: '已确认次数', field: 'autoConfirmNum', sortable: true},
			        {title: '省份', field: 'provinceId', sortable: true,
				        formatter: function(value,row,index){
								var isNot="";
								if(row.provinceId =='13'){
									isNot="安徽";
								}
								if(row.provinceId =='2'){
									isNot="北京";
								}
								if(row.provinceId =='23'){
									isNot="重庆";
								}
								if(row.provinceId =='14'){
									isNot="福建";
								}
								if(row.provinceId =='25'){
									isNot="贵州";
								}
								if(row.provinceId =='20'){
									isNot="广东";
								}
								if(row.provinceId =='29'){
									isNot="甘肃";
								}
								if(row.provinceId =='21'){
									isNot="广西";
								}
								if(row.provinceId =='18'){
									isNot="湖北";
								}
								if(row.provinceId =='17'){
									isNot="河南";
								}
								if(row.provinceId =='22'){
									isNot="海南";
								}
								if(row.provinceId =='19'){
									isNot="湖南";
								}
								if(row.provinceId =='9'){
									isNot="黑龙江";
								}
								if(row.provinceId =='4'){
									isNot="河北";
								}
								if(row.provinceId =='15'){
									isNot="江西";
								}
								if(row.provinceId =='8'){
									isNot="吉林";
								}
								if(row.provinceId =='11'){
									isNot="江苏";
								}
								if(row.provinceId =='7'){
									isNot="辽宁";
								}
								if(row.provinceId =='6'){
									isNot="内蒙古";
								}
								if(row.provinceId =='31'){
									isNot="宁夏";
								}
								if(row.provinceId =='30'){
									isNot="青海";
								}
								if(row.provinceId =='24'){
									isNot="四川";
								}
								if(row.provinceId =='5'){
									isNot="山西";
								}
								if(row.provinceId =='10'){
									isNot="上海";
								}
								if(row.provinceId =='16'){
									isNot="山东";
								}
								if(row.provinceId =='28'){
									isNot="陕西";
								}
								if(row.provinceId =='3'){
									isNot="天津";
								}
								if(row.provinceId =='27'){
									isNot="西藏";
								}
								if(row.provinceId =='32'){
									isNot="新疆";
								}
								if(row.provinceId =='26'){
									isNot="云南";
								}
								if(row.provinceId =='12'){
									isNot="浙江";
								}
								return isNot ;
							}
						},	
					{title: '是否日日单', field: 'isProduceDaily', sortable: true,
						formatter: function(value,row,index){
							var isNot="";
							if(row.isProduceDaily =='1'){
								isNot="是";
							}else{
								isNot="否";
							}
							return isNot ;
						}
					}
		 ] ],
		 toolbar: '#datagridToolbar_orderForecastGoal',
		//onClickRow : function(rowIndex,rowData) {
		//	searchMxlist(rowData.applyCode);
		//}
	});
    dataGrid.datagrid('getPager').pagination({
    	 pageSize: 50,
	     pageList: [50,100,200],
		onSelectPage : function(pageNumber, pageSize) {
			searchselect();
		}
	});
    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_orderForecastGoal').form('reset');
        $('#addForm_orderForecastGoal').find('[__actType]').val('add');
        $('#addDlg_orderForecastGoal').dialog({'title': '新增'});
        $('#addDlg_orderForecastGoal').dialog('open');
    });
    
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
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
    $("#importBtn_orderForecastGoal").on('click', function (event) {
    	if(!dataGrid){
		$.messager.alert('提示','请查询！','info');
			return;	
		}
		$.messager.confirm('确认','确定要导出吗？', function(r){
			if (r){
				$('#exportData').val(JSON.stringify(queryParameters));
				$('#paramForm_orderForecastGoal').attr("action", '/orderOperation/exportOrderList');
				$('#paramForm_orderForecastGoal').submit();
			}
		});
    });


});
    function addTab2(orderSn) {
    	window.top.addTab("订单查询网单", "/operationArea/SinglePageJump?orderSn="+orderSn, true);
	}
 

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
        addTab("网单详情[WD170817233445]","order/norderDetail",false);
    }
});*/
