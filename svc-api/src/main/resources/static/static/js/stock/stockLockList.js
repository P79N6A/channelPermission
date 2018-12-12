var dataGrid =null;

    /* var detail = {
        fit: true,
        fitColumns: true,
        singleSelect: true,
        rownumbers: true
    };
    function searchBackUp(num) {
        $("#dg").empty()
        $("#dg").html($("#backup" + num).html())
        var ths = $("#backup"+num+" thead")[0].innerText;
        $("#dg").datagrid({
            fit: true,
            fitColumns: true,
            toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
            striped: true,
            columns: covertColumns(ths),
            pagination: true,
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50],
            rownumbers: true
        })
    } */
    function OccupancyStock(){
    	window.location.href="/memStockLock/loadStockLockPage";
    }
  $(function () {
	  
	  $("#cxhongzhi").on('click', function (event) {
	        event.preventDefault();
	        $('#filterForm').form('reset');
	    });
	  $("#addBtn_dmmtlPbcsMtlMeasure").on('click', function (event) {
          $('#addForm_dmmtlPbcsMtlMeasure').form('reset');
          $('#addForm_dmmtlPbcsMtlMeasure').find('[__actType]').val('add');
          $('#addDlg_dmmtlPbcsMtlMeasure').dialog({'title': "冻结库存"});
          $('#addDlg_dmmtlPbcsMtlMeasure').dialog('open');
          $("#fitem").hide();
      });
	  $("#chebox").change(function(){
		  if($("#chebox").prop('checked')){
			  $("#refNos").textbox("setValue","WD")
		  }else{
			  $("#refNos").textbox("setValue","")
		  }
	  });
    dataGrid = $('#datagrid_orderForecastGoal').datagrid({
    	striped : true, // 隔行变色
		rownumbers : true,
		fit: true,
		pagination : true,
		nowrap: true,
		singleSelect : true,
		fitColunms : true,
		pageSize: 50,
	    pageList: [50, 100, 200],
		columns : [ [ {
			title : '单据号',
			width : 150,
			field : 'refno',
			align : 'center'
		}, {
			title : '物料编号',
			width : 150,
			field : 'sku',
			align : 'center'
			
		}, {
			title : '库位编码',
			width : 100,
			field : 'secCode',
			align : 'center'
		}, {
			title : '库位名称',
			width : 150,
			field : 'secName',
			align : 'left'
		}, {
			title : '占用数量',
			width : 80,
			field : 'lockQty',
			align : 'center'
		}, {
			title : '释放数量',
			width : 120,
			field : 'realeaseQty',
			align : 'center'
		},  {
			title : '操作人',
			width : 160,
			field : 'optUser',
			align : 'center'
		}, {
			title : '占用时间',
			width : 123,
			field : 'lockTime',
				formatter: function(value,row,index){
					return gettime(row.lockTime);
			}
		},{
			title : '操作',
			width : 90,
			field : 'shifang',
			align : 'center',
			formatter: function(value,row,index){
			 	return "<a id='oper' href='#' onclick='unlockStock("+JSON.stringify(row)+")'>释放</a>";
		}
		} ] ],
		 toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
	});
    dataGrid.datagrid('getPager').pagination({
   	 
		onSelectPage : function(pageNumber, pageSize) {
			var gridOpts = $('#datagrid_orderForecastGoal').datagrid('options');
			gridOpts.pageNumber = pageNumber;
			gridOpts.pageSize = pageSize;
			onQuery(1);
			/*console.log(pageNumber)*/
		}
	});
    });
    
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
  
	function onQuery(pageIndex){
		  var options = $("#datagrid_orderForecastGoal").datagrid('getPager').data("pagination").options;
		  $("#size").val(options.pageSize);
		  $("#start").val(options.pageNumber);
		if(!isNum($( "#lockQty" ).val())){
			return false;
		}
			$('#pageIndex').val(pageIndex);
			$('#list').html("正在加载...");
			$.ajax({
				url: "/memStockLock/queryStockLockListPage",   // 提交的页面
				data: $('#filterForm').serialize(), // 从表单中获取数据
				type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
				success: function(data) {
					 $('#datagrid_orderForecastGoal').datagrid('loadData',data);
				}
			});
			
			
			
			/* dataGrid = $('#datagrid_orderForecastGoal').datagrid({
				 url: "/memStockLock/queryStockLockListPage",
			    	striped : true, // 隔行变色
					rownumbers : true,
					fit: true,
					pagination : true,
					nowrap: true,
					singleSelect : true,
					fitColunms : true,
					 pageSize: 50,
				        pageList: [50, 100, 200],
					 queryParams:{
						 secCode:$("#secCode").textbox('getValue'),
				        	sku:$("#sku").textbox('getValue'),
				        	refNo:$("#refNo").textbox('getValue'),
				        	lockQty:$("#lockQty").textbox('getValue'),
				        	startTime:$("#startTime").textbox('getValue'),
				        	endTime:$("#endTime").textbox('getValue'),
				        	start:$("#start").val(),
				        	size:$("#size").val()
				        },
					columns : [ [ {
						title : '单据号',
						width : 150,
						field : 'refno',
						align : 'center'
					}, {
						title : '物料编号',
						width : 150,
						field : 'sku',
						align : 'center'
						
					}, {
						title : '库位编码',
						width : 100,
						field : 'secCode',
						align : 'center'
					}, {
						title : '库位名称',
						width : 150,
						field : 'secName',
						align : 'left'
					}, {
						title : '占用数量',
						width : 80,
						field : 'lockQty',
						align : 'center'
					}, {
						title : '释放数量',
						width : 120,
						field : 'realeaseQty',
						align : 'center'
					},  {
						title : '操作人',
						width : 160,
						field : 'optUser',
						align : 'center'
					}, {
						title : '占用时间',
						width : 123,
						field : 'lockTime',
							formatter: function(value,row,index){
								return gettime(row.lockTime);
						}
					},{
						title : '操作',
						width : 90,
						field : 'shifang',
						align : 'center',
						formatter: function(value,row,index){
						 	return "<a id='oper' href='#' onclick='unlockStock("+JSON.stringify(row)+")'>释放</a>";
					}
					} ] ],
					 toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
				});*/
	
	}
	
	function updateChannel(){
		$.ajax({
				url: "/memStockLock/updateStockLockChannel",   // 提交的页面
				data: $('#filterForm').serialize(), // 从表单中获取数据
				type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
				success: function(data) {
					$("#list").html(data);
				}
			});
	}
	
	function unlockStock(refno) {
		$.messager.confirm('询问','确定要释放单号是'+refno.refno+'的冻结记录吗？',function(r){
	                if(r){
			$.ajax({
    				url: "/memStockLock/cancelLockStock",   // 提交的页面
    				data: {"refno":refno.refno, "sku":refno.sku, "secCode":refno.secCode, "releaseQty":refno.lockQty, "id":refno.id, "optUser": refno.optUser}, // 从表单中获取数据
    				type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
    				success: function(data) {
						if ( data.success == true ) {
    						var pageIndex = $('#pageIndex').val();
        					onQuery(pageIndex);
        					alert(data.data);
						} else {
							alert(data.message);
						}
    				}
    		});
				}
		})
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
			alert("\u8bf7\u8f93\u5165\u6570\u5b57");
			return false;
		}		
	}
	
	
	function onLock(){
		var che = $("#chebox").prop('checked');
		
			var row = $('#addForm_dmmtlPbcsMtlMeasure').serializeObject();
			var sku = $("#sku").val("getValue");
			if ($.trim(row.secCode) =="") {
				alert("库位编码不能为空");
				return false;
			}
			if ($.trim(row.sku) =="") {
				alert("物料编码不能为空");
				return false;
			}
			if(!isNum(row.frozenQty)){
				return false;
			}
			if(che==true){
		    if(!isLength(row.refNos)){
		      return false;
		      }
		    }
    		jQuery.ajax({
    				url: "/memStockLock/lockStock",   
    				data: $('#addForm_dmmtlPbcsMtlMeasure').serialize(), 
    				type: "GET",                 
    				success: function(data) {
						if (data.message.trim().length >0 ){
							alert(data.message);
							return;
						} else {
    						$("#refNo").val(data.data);
							alert('占用成功!');
							$('#addDlg_dmmtlPbcsMtlMeasure').dialog('close')
						}
						
    				}
    			});
			
			return false;
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
				alert("锁定数量必须输入数字");
				return false;
			}		
		}
		
		function isLength(str){
		  if(str == ""){
				return true;
			}
		  var regx = /[WD][0-9]{1,28}$/;
		  if(regx.exec(str)){
					return true;
				}else{
					alert("以WD开头，数字组成，长度不超过30，且不能输入空格");
					return false;
				}
		}
		
		function radio1(){
		  //$("#refNo").removeAttr("readonly"); 
		  if($("input[type='checkbox']").is(':checked')){
		    $("#refNo").removeAttr("readonly"); 
		    $("#refNo").val("WD"); 
		    $("#span1").show();
		  }else{
		    $("#refNo").attr("readonly","readonly"); 
		    $("#span1").hide();
		    $("#refNo").val(""); 
		  }
		}
		
		