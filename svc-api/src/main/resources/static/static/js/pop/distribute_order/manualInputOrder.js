var dataGrid =null;
$(function(){
			/*onQuerys(1);*/
			$('#queryButton').click(function(){ 
            	$('#queryButton').attr('disabled',true);
            	onQuerys(1);
            });
				  
			dataGrid=   $('#dg').datagrid({
				striped : true, // 隔行变色
				rownumbers : true,
				fit: true,
				pagination : true,
				singleSelect : true,
				fitColunms : true,
				idField : 'id',
				frozenColumns:[[    
					{
					        	title : '订单号',
								width : 120,
								field : 'orderSn',
								align : 'center'
					            , sortable: false,
					            rowspan:2,
					            formatter: function(value,row,index){
									
									return "<a id='op' href='#' onclick=onRelease("+JSON.stringify(row)+")>"+row.orderSn+"</a>";
							}
					        },{
					        	title : '渠道',
								width : 120,
								field : 'channelCode',
								align : 'center'
					            , sortable: false,
					            rowspan:2
					        }, 
					        {
					        	title : '下单时间',
								width : 120,
								field : 'addTime',
								align : 'center'
					            , sortable: false,
					            rowspan:2
					        },{
					        	title : '付款时间',
								width : 120,
								field : 'payTime',
								align : 'center'
					            , sortable: false,
					            rowspan:2
					        },
					        {
					        	title : '订单金额',
								width : 120,
								field : 'orderFee',
								align : 'center'
					            , sortable: false,
					            rowspan:2
					        }
							],[]],
					        columns: [[
					            {
					                title: '收货人信息',
					                width: 70,
					                field : 'invoice',
					                align: 'center'
					                , sortable: false,
					                colspan:3
					            },{
									title : '发票',
									width : 100,
									field : 'invoiceType',
									align : 'center',
									sortable: false,
							        rowspan:2,
							        formatter: function(value,row,index){
							        	if(row.invoiceType==0){
							        		return'无'
							        	}else{
							        		return'电子发票'
							        	}
								}
								},{
									title : '订单状态',
									width : 110,
									field : 'orderStatus',
									align : 'center',
									sortable: false,
							        rowspan:2,
							        formatter: function(value,row,index){
							        	if(row.orderStatus==1){
							        		return'已确认'
							        	}else{
							        		return'未确认'
							        	}
								}
					            },{
									title : '备注',
									width : 110,
									field : 'notes',
									align : 'center',
									sortable: false,
							        rowspan:2,
							        
					            },{
									title : '操作',
									width : 110,
									field : 'channelLockQty',
									align : 'center',
									sortable: false,
							        rowspan:2,
							        formatter: function(value,row,index){
										
										return "<a id='oper' href='#' onclick=onRelease("+JSON.stringify(row)+")>编辑</a>";
								}
							            
					            }
					            ]
					        ,
					[   {
						title : '姓名',
						width : 100,
						field : 'name',
						align : 'center',
						
					},  {
						title : '手机号',
						width : 100,
						field : 'phone',
						align : 'center',
						
					},  {
						title : '地址',
						width : 100,
						field : 'optUser3',
						align : 'center',
						formatter: function(value,row,index){
							return row.province+' '+row.city+' '+row.district+' '+row.districtCode+' '+row.street+' '+row.address;
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
	});

			dataGrid.datagrid('getPager').pagination({
		    	 
				onSelectPage : function(pageNumber, pageSize) {
					var gridOpts = $('#dg').datagrid('options');
					gridOpts.pageNumber = pageNumber;
					gridOpts.pageSize = pageSize;
					onQuerys(1);
					//console.log(pageNumber)
				}
			});
        });
		


		var timecount = 0;
		function onQuerys(pageIndex){
			 
			if(!checkDate("startDate","endDate")){
				return false;
			}else{
				var options = $("#dg").datagrid('getPager').data("pagination").options;
				 $("#size").val(options.pageSize);
				  $("#start").val(options.pageNumber);
				$('#pageIndex').val(pageIndex)
				timecount = 0;
				$("#list").html("<div style='width:100%;height:300px;text-align:center;'><br/><br/>数据正在加载，请稍后。。。<br/><br/><div id='timecount' style='width:100%;height:20px;text-align:center;font-size:10pt;font-family:Arial,Helvetica,sans-serif;'></div><br/><img src='/resources/images/tendency.gif'/></div>");
				
				jQuery.ajax({
					url: "/manualInputOrder/getManualInputOrderAll",
					data: $('#filterForm').serialize(),
					type: "post",
					success: function(data) {
						dataGrid.datagrid('loadData',data);
					}
				});
			}
		}
		
		
		function addShop(){
			jQuery.ajax({
				url: "/manualInputOrder/saveManualInputOrder",
				data: $('#paramForm_dmmtlPbcsMtlMeasure').serialize(),
				type: "post",
				success: function() {
					alert("保存成功")
				}
				
			});
		}
		function timer(){
			timecount++;
			$("#timecount").html(timecount);
		}
		
		//日期大小比较，开始日期必须小于等于结束日期
		function checkDate(startDateId,endDateId){
			var startDate= $("#startDate").datebox('getValue');
			var endDate=$("#endDate").datebox('getValue');
			if(startDate!=""&&endDate!=""){
				var s=new Date(startDate.replace(/\-/g,"\/"));
				var e=new Date(endDate.replace(/\-/g,"\/"));
				if(s>e){
					alert("开始日期必须小于或等于结束日期！");
					return false;
				}
			}
			return true;
		}

		
	    
	    
	  
	    
	 
	    
	    
	    var datagridData_invMachineSet;
	    //添加订单
	    function onReleases(){
	    	$( "#dialog-div" ).dialog({
	    	 	autoOpen: false,
	    		modal: true,
	    		height:"80%",
	    		resizable: true,	
	    		title: '增加',
	    		close: function( event, ui ) {
	    			$( "#dialog-div" ).dialog( "close" );
	    			$("#dataTypef").val('');
	    		}
	    		
	    	});	
	    	
	    	$("#dataTypef").val("");
	    	$("#orderId").val("");
	    	 $("#orderFee").textbox("setValue", "");
	    	$("#orderSns").textbox("setValue","");
	    	$("#channelCodes").combobox("setValue","");
	    	$("#payTime").textbox("setValue","");
	    	$("#notes").textbox("setValue","");
	    	$("#name").textbox("setValue","");
	    	$("#mobile").textbox("setValue","");
	    	$("#phone").textbox("setValue","");
	    	$("#zip").textbox("setValue","");
	    	$("#province").combobox("clear");
	    	//$("#province").combobox("setValue","");
//	    	$("#province").combobox("unselect","请选择省份..");
//	    	$("#province").combobox("select","山东省");
	    	$("#invoiceTypes").combobox("setValue","");
	    	$("#citys").combobox("setValue","");
	    	$("#county").combobox("setValue","");
	    	$("#address").textbox("setValue","");
	    	$("#invoiceTitle").textbox("setValue","");
	    	$('#datagrides').datagrid('loadData',{total:0,rows:[]});
	    	showPro();
	    	
	    }
	    //编辑订单
	    function onRelease(str){
//	    	var data = $('#province').combobox('getData');
	    	
	    	$("#dataTypef").val("");
	    	$("#orderId").val("");
	    	 $("#orderFee").textbox("setValue", "");
	    	$("#orderSns").textbox("setValue","");
	    	$("#channelCodes").combobox("setValue","");
	    	$("#payTime").textbox("setValue","");
	    	$("#notes").textbox("setValue","");
	    	$("#name").textbox("setValue","");
	    	$("#mobile").textbox("setValue","");
	    	$("#phone").textbox("setValue","");
	    	$("#zip").textbox("setValue","");
	    	$("#province").combobox("clear");
	    	
	    	//$('#datagrides').datagrid('loadData',{total:0,rows:[]});
	    	//$("#province").combobox("setValue","");
//	    	$("#province").combobox("unselect","请选择省份..");
//	    	$("#province").combobox("select","山东省");
	    	$("#invoiceTypes").combobox("setValue","");
	    	$("#citys").combobox("setValue","");
	    	$("#county").combobox("setValue","");
	    	$("#address").textbox("setValue","");
	    	$("#invoiceTitle").textbox("setValue","");
	    	$( "#dialog-div" ).dialog({
	    	 	autoOpen: false,
	    		modal: true,
	    		height:"80%",
	    		resizable: true,	
	    		title: '编辑',
	    		close: function( event, ui ) {
	    			$( "#dialog-div" ).dialog( "close" );
	    			$("#dataTypef").val('');
	    		}
	    		
	    	});	

	    	
	    	$("#dataTypef").val(str);
	    	$("#orderId").val(str.id);
	    	 $("#orderFee").textbox("setValue", str.orderFee);
	    	$("#orderSns").textbox("setValue",str.orderSn);
	    	$("#channelCodes").combobox("setValue",str.channelCode);
	    	$("#payTime").textbox("setValue",str.payTime);
	    	$("#notes").textbox("setValue",str.notes);
	    	$("#name").textbox("setValue",str.name);
	    	$("#mobile").textbox("setValue",str.mobile);
	    	$("#phone").textbox("setValue",str.phone);
	    	$("#zip").textbox("setValue",str.zip);
	    	$("#province").combobox("clear");
	    	$("#province").combobox("setValue",str.province);
//	    	$("#province").combobox("unselect","请选择省份..");
//	    	$("#province").combobox("select","山东省");
	    	$("#invoiceType").combobox("setValue",str.invoiceType);
	    	$("#citys").combobox("setValue",str.city);
	    	$("#county").combobox("setValue",str.district);
	    	$("#address").textbox("setValue",str.address);
	    	$("#invoiceTitle").textbox("setValue",str.invoiceTitle);
	    	var orderId=$("#orderId").val();
	    	selectAllbyid(orderId);
	    	showPro();
	    	
	    }
	    var datagrid=null;
	    $(function () {
//    	    showPro();
    	    /*selectAllbyid()	
    	   var datagrid = $('#datagrid').datagrid(datagridOptions_invMachineSet);*/
    	     
	    	
    	    //datagrid.datagrid('loadData', datagridData_invMachineSet.data.records);
    	});
	    //根据订单查询商品
	    function selectAllbyid(orderId){
	    	//var orderId=$("#orderId").val();
	    	jQuery.ajax({
				url: "/manualInputOrder/getManualInputOrderProductsbyOrderId",
				data: {
					orderId,orderId
				},
				type: "post",
				success: function(data) {
					datagrid = $('#datagrides').datagrid(datagridOptions_invMachineSet);
					datagrid.datagrid('loadData',data);
				}
			});
	    }
	    
	    //三级联动省市区
	    function showPro(){
    	    $.ajax({
    	        url : '/manualInputOrder/getRegions',
    	         dataType: 'json',  
    	         success: function (jsonstr) {  
    	        	 var tempCombox=[{"code":'',"name":"please","childs":[{}]}];
    	        	 $.each(jsonstr.rows,function(i,val){
    	        		 if(val.regionType == 1){
    	        			 if(val.parentId==0){
    	        			 tempCombox.push({"value":val.id,"text":val.regionName,"childs":[{}]});
    	        			 $("#province").combobox("loadData", tempCombox);
    	        			 }
    	        		 }
    	        	 });
	                }
    	        
    	         
    	    });
    	}
	    
	    $('#province').combobox({
	    onChange : function(newValue, oldValue) {			
         if (newValue) {
             showCity(newValue)
             $("#citys").combobox("clear")
             $("#county").combobox("clear")					
         }
	    }
	    });
	    
    	function showCity(newValue){
    		$.ajax({
    	        url : '/manualInputOrder/getRegions',
    	         dataType: 'json',  
    	         success: function (jsonstr) {  
    	        	 var tempCombox=[{"code":'',"name":"please","childs":[{}]}];
    	        	 $.each(jsonstr.rows,function(i,val){
    	        		 if(val.parentId == newValue){
    	        			 tempCombox.push({"value":val.id,"text":val.regionName,"childs":[{}]});
    	        			 $("#citys").combobox("loadData", tempCombox);
    	        		 }
    	        	 });
	                }
    	        
    	         
    	    });
    	}
    	   

    	 $('#citys').combobox({
    		    onChange : function(newValue, oldValue) {			
    	         if (newValue) {
    	        	 showCounty(String(newValue))
    	             	 			
    	         }
    		    }
    	 });
    		    
    	function showCounty(newValue){
    		$.ajax({
    	        url : '/manualInputOrder/getRegions',
    	         dataType: 'json',  
    	         success: function (jsonstr) {  
    	        	 var tempCombox=[{"code":'',"name":"please","childs":[{}]}];
    	        	 $.each(jsonstr.rows,function(i,val){
    	        		 if(val.parentId == newValue){
    	        			 tempCombox.push({"value":val.code,"text":val.regionName,"childs":[{}]});
    	        			 $("#county").combobox("loadData", tempCombox);
    	        		 }
    	        	 });
	                }
    	        
    	         
    	    });         
    	   		
    	            
    	}

	    
	    
//加载商品页面
	    	var datagridOptions_invMachineSet = {
	    	    fit: true,
	    	    fitColumns: true,
	    	    singleSelect: true,
	    	    //url: '/module2/invMachineSet/p',
	    	    /*frozenColumns: [[{
	    	        field: 'id', checkbox: true
	    	    }]],*/
	    	    
	    	    columns: [[
	    	        {title: '商品编码', field: 'sku', sortable: false,width: 20,align:'center'},
	    	        {title: '商品型号', field: 'productName', sortable: false,width: 20,align:'center'},
	    	        {title: '单价', field: 'price', sortable: false,width: 20,align:'center'},
	    	        {title: '数量', field: 'num', sortable: false,width: 20,align:'center'},
	    	        {title: '小计（元）', field: 'totalPrice', sortable: false,width: 20,align:'center',formatter:function(val,row,index){
	    	        	return row.price*row.num;
	    	        }},
	    	        {title: '操作', field: 'operation', sortable: false,width: 20,align:'center',formatter:function(val,row,index){
	    	            return '<a href="#" onclick="updateP('+row.id+')">编辑</a>'+'<a href="#" onclick="removeP('+row.id+')">删除</a>';
	    	        }}
	    	     ]],
	    	    /*分页toolbar: '#datagridToolbar',
	    	    striped: true,
	    	    pagination: true,
	    	    pageSize: 10,
	    	    pageList: [10,30, 50],
	    	    rownumbers: true*/
	    	};

	    	

//	   / }

	    	
	    //商品添加
	    function Add() {
	        $('#oDialog4').dialog('open');
	        $('#sku').val("");
	        $('#productName').val("");
	        $('#price').val("");
	        $('#num').val("");
	        $('#oDialog4').panel({title: "新增"});

	    }
	  //set值
	    function SetCodeValuep() {
	        var row = $('#code').datagrid('getSelected');
	        $("#productName").val(row.productName);
	        $("#sku").val(row.sku);
	        $('#oDialog').dialog('close');
	        //typesku(row.productTypeId1);

	    }
	  //物料编码触发
	    $("#sku").click(function () {
	        $('#code').datagrid('loadData', {total: 0, rows: []});
	        $("#sku1").val("");
	        $("#productName1").val("");
	        $('#oDialog').dialog('open');

	    });
	    function product() {
	        var sku = $("#sku1").val() == '' ? '%' : $("#sku1").val() + '%';
	        var productName = $("#productName1").val() == '' ? '%' : $("#productName1").val() + '%';
	        $.ajax({
	            type: 'POST',
	            async: true,
	            url: '/pop/product/productsList',
	            data: {
	                sku: sku,
	                productName: productName
	            },

	            success: function (data, textStatus) {
	                $('#code').datagrid("loadData", data);
	            },
	            complete: function (XMLHttpRequest, textStatus) {
	            },
	            error: function (XMLHttpRequest, textStatus, errorThrown) {
	            }
	        });
	    }
	    //商品编辑
	    function SetCodeValue4() {

	        var id = $("#channelId2").val();
	        var sku = $("#sku").val();
	        var productName = $("#productName").val();
	        var price = $("#price").val();
	        var num = $("#num").val();
	        var orderId= $("#orderId").val();
	        
	        var url = '/manualInputOrder/insertManualInputOrderProducts';

	        if (sku == "") {
	            alert("商品编码不能为空");
	            return false;
	        }
	        if (price == "") {
	        	alert("单价不能为空");
	            return false;
	        }
	        if (num == "") {
	            alert("数量不能为空");
	            return false;
	        }
	        if(id !=""&& id!=null){
	        	url='/manualInputOrder/updateManualInputOrderProducts';
	        	
	        }
	        $.ajax({
	            type: 'POST',
	            async: false,
	            url: url,
	            data: {
	            	
	                id: id,
	                sku: sku,
	                productName: productName,
	                price: price,
	                num: num,
	                orderId:orderId

	            },
	            success: function (data, textStatus) {
	                if (data == 1) {
	                    alert("操作成功！");
	                    $('#oDialog4').dialog('close');
	                    var id =$("#orderId").val();
	                    selectAllbyid(id);
	                   // SearchUnit();
	                } else {
	                    alert("操作失败！");
	                }

	            }

	        });
	    }
	    //修改商品
	    function updateP(Str) {
	    	  $.ajax({
		            type: 'POST',
		            async: false,
		            url: '/manualInputOrder/findProduct',
		            data: {
		            	
		                id: Str
		            },
		            success: function (data, textStatus) {
		            	
		            	$("#channelId2").val(data.id);
		    	    	$('#sku').val(data.sku);
		    	        $('#productName').val(data.productName);
		    	        $('#price').val(data.price);
		    	        $('#num').val(data.num);
		    	        $('#oDialog4').panel({title: "修改"});
		    	        $('#oDialog4').dialog('open');
		            }

		        });
	    
	       // $("#channelId2").combobox('setValue', str);
	        
	        
	        //SetCodeValue4();
	        
	    }
	    //修改订单
	    function onSave(){
	    	
	    	var id=$("#orderId").val();
	    	var orderSn=$("#orderSns").val();
	    	var channelCode=$("#channelCode").combobox('getValue');
	    	var orderFee=$("#orderFee").val();
	    	var payTime=$("#payTime").val();
	    	var notes=$("#notes").val();
	    	var name=$("#name").val();
	    	var mobile=$("#mobile").val();
	    	var phone=$("#phone").val();
	    	var zip=$("#zip").val();
	    	var province=$("#province").combobox('getText');
	    	var city=$("#citys").combobox('getText');
	    	var county=$("#county").combobox('getText');
	    	var districtCode=$('#county').combobox('getValue');
	    	var address=$("#address").val();
	    	var invoiceType=$("#invoiceTypes").combobox('getValue');
	    	var invoiceTitle=$("#invoiceTitle").val();
	    	
	    	var url = '/manualInputOrder/saveManualInputOrder';
	    	if(id==null&&id==""){
	    		url="/manualInputOrder/insertManualInputOrder";
	    	}
	    	if (orderSn == ""&&orderSn==null) {
	            alert("订单号不能为空");
	            return false;
	        }
	    	if (channelCode == ""&&channelCode==null) {
	            alert("渠道不能为空");
	            return false;
	        }
	    	if (orderFee == "") {
	            alert("订单金额不能为空");
	            return false;
	        }
	    	if (payTime == "") {
	            alert("交易时间不能为空");
	            return false;
	        }
	    	if (name == "") {
	            alert("名字不能为空");
	            return false;
	        }
	    	if (phone == "") {
	            alert("手机号不能为空");
	            return false;
	        }
	    	if (zip == "") {
	            alert("邮编不能为空");
	            return false;
	        }
	    	if (province == "") {
	            alert("省不能为空");
	            return false;
	        }
	    	if (city == ""&&city==null) {
	            alert("城市不能为空");
	            return false;
	        }
	    	if (county == ""&&county==null) {
	            alert("区县不能为空");
	            return false;
	        }
	    	if (address == ""&&address==null) {
	            alert("详细地址不能为空");
	            return false;
	        }
	    	if (invoiceType == ""&&invoiceType==null) {
	            alert("发票类型不能为空");
	            return false;
	        }
	    	
	    	 $.ajax({
		            type: 'POST',
		            async: false,
		            url: url,
		            data: {
		            	id:id,
		            	orderSn: orderSn,
		            	channelCode: channelCode,
		            	orderFee: orderFee,
		            	payTime: payTime,
		            	notes:notes,
		            	name: name,
		            	mobile:mobile,
		            	phone:phone,
		            	zip:zip,
		            	province:province,
		            	city:city,
		            	county:county,
		            	address:address,
		            	invoiceType:invoiceType,
		            	invoiceTitle:invoiceTitle,
		            	districtCode:districtCode
		            },
		            success: function (data, textStatus) {
		                if (data == 1) {
		                    alert("操作成功！");
		                    $('#dialog-div').dialog('close');
		                    onQuerys(1);
		                   // SearchUnit();
		                } else {
		                    alert("操作失败！");
		                }

		            }

		        });
		    }
	    	
	    //删除商品
	    function removeP(str){
	    	 $.ajax({
		            type: 'POST',
		            async: false,
		            url: '/manualInputOrder/deleteManualInputOrderProducts',
		            data: {
		            	
		                id: str,
		            },
		            success: function (data, textStatus) {
		                if (data == 1) {
		                    alert("操作成功！");
		                   // $('#oDialog4').dialog('close');
		                   // SearchUnit();
		                    var id =$("#orderId").val();
		                    selectAllbyid(id);
		                } else {
		                    alert("操作失败！");
		                }

		            }

		        });
	    }
	    

	    