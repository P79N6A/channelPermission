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
				remoteSort:false,
				fit: true,
				pagination : true,
				nowrap: true,
				 pageSize: 50,
			     pageList: [50,100,200,300],
				singleSelect : true,
				fitColunms : true,
				columns : [ [ {
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
					title : 'LES库位编码',
					width : 150,
					field : 'lesSecCode',
					align : 'center'
				}, {
					title : '总库存量',
					width : 130,
					field : 'stockQty',
					align : 'left'
				}, {
					title : '冻结库存数量',
					width : 130,
					field : 'frozenQty',
					align : 'center'
				}, {
					title : '超卖待释放数量',
					width : 130,
					field : 'overSold',
					align : 'center'
				},  {
					title : '创建时间',
					width : 160,
					field : 'createTime',
					align : 'center',
					formatter: function(value,row,index){
						return formatDatebox(row.createTime);
				}
				}, {
					title : '更新时间',
					width : 168,
					field : 'updateTime',
					align : 'center',
						formatter: function(value,row,index){
							return formatDatebox(row.updateTime);
					}
				},{
					title : '操作',
					width : 90,
					field : 'shifang',
					align : 'center',
					formatter: function(value,row,index){
						
						return "<a id='oper' href='loadInvStockLockPage?sku="+row.sku+"&secCode="+row.secCode+"' onclick='execute("+JSON.stringify(row)+")'>点击执行</a>";
				}
				} ] ],
				 toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
			});

			dataGrid.datagrid('getPager').pagination({
		    	 
				onSelectPage : function(pageNumber, pageSize) {
					var gridOpts = $('#dg').datagrid('options');
					if (pageNumber == 0) {
            pageNumber = 1;
					}
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
					url: "/invBaseStock/getInvBaseStockList",
					data: $('#filterForm').serialize(),
					type: "post",
					success: function(data) {
						dataGrid.datagrid('loadData',data);
					}
				});
			}
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
		function onExport(){
			
			/*$('#filterForm').serialize(),*/
			$("#filterForm").attr("action", "ManualReleaseInventory.export");
			$('#filterForm').submit();
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
	    
	    function execute(toStr){
			
			window.location.href="loadInvStockLockPage?sku="+toStr.sku+"&secCode="+toStr.secCode;
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
	    