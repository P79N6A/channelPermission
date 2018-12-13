var dataGrid = null;
//查询
 var searchselect = function () {
	 var options = dataGrid.datagrid('getPager').data("pagination").options;
      $.ajax({
         url      : "/orderOperation/findTbOrderGroupBuy",
         type     : 'POST',
         dataType : 'json',
          data     : {		sku:$("#sku").val(),
				            page: options.pageNumber,
				            rows: options.pageSize
         }, 
         success  : function(data) {
        	  var datagridData_tbgroupbuy = {
          		    'data': {
          		        'records':
          		        	data.rows,'totalCount':data.total
          		            }
        	  }
        	  dataGrid.datagrid('loadData', datagridData_tbgroupbuy);
        	  options.total = data.total;
         }
     });
 }
    
      $('#searchBtn_tbgroupbuy').click(function() {
    	  var options = dataGrid.datagrid('getPager').data("pagination").options;
			options.pageNumber = 1;
			dataGrid.datagrid('getPager').pagination('refresh');
    	  searchselect()
    	  }
      )

    function delObj(el)
	{
		el = el.parentNode;
		el.parentNode.removeChild(el);
	}
	
$(function () {
    var moreOptions = $(".morehide");
    for (var i = 0; i < moreOptions.length; i++) {
        $(moreOptions[i]).css("display", "none");
    } ;
    dataGrid = $('#datagrid_tbgroupbuy').datagrid({
        fit: true,
	    fitColumns: true,
	    singleSelect: false,
	    frozenColumns: [[{
	        field: 'id', checkbox: true
	    }]],
		striped : true, // 隔行变色
	    toolbar: '#datagridToolbar_tbgroupbuy',
	    striped: true,
	    pagination: true,
	    rownumbers: true,
		columns: [[
			{title: '团购名称', field: 'groupName', sortable: false,
			        	formatter: function(value,row,index){
							return "<span title='" + row.groupName + "'>" + row.groupName + "</span>" ;
			}},
			{title: '团购商品sku', field: 'sku', sortable: false},
			{title: '订金金额', field: 'depositAmount', sortable: false,
			        	formatter: function(value,row,index){
							return "￥"+row.depositAmount ;
			}},
			{title: '尾款金额', field: 'balanceAmount', sortable: false,
			        	formatter: function(value,row,index){
							return "￥"+row.balanceAmount ;
			}},
			{title: '订金开始时间', field: 'depositStartTime', sortable: false},
			{title: '订金结束时间', field: 'depositEndTime', sortable: false},
			{title: '尾款开始时间', field: 'balanceStartTime', sortable: false},
			{title: '尾款结束时间', field: 'balanceEndTime', sortable: false},
			{title: '发货时机', field: 'shippingOpporunity', sortable: false,
			        	formatter: function(value,row,index){
							var isNot="";
							if(row.shippingOpporunity =='0'){
								isNot="订金发货";
							}
							if(row.shippingOpporunity =='1'){
								isNot='尾款发货';
							}
							return isNot ;
			}},
			{title: '状态', field: 'status', sortable: false,
			        	formatter: function(value,row,index){
							var isNot="";
							if(row.status =='1'){
								isNot="开启";
							}else {
								isNot='关闭';
							}
							return isNot ;
			}},
			{title: '套装设置', field: 'productSpecs', sortable: false},
		]],
	});
    dataGrid.datagrid('getPager').pagination({
    	 pageSize: 50,
	     pageList: [50,100,200],
		onSelectPage : function(pageNumber, pageSize) {
		searchselect();
		}
	});
    $("#addBtn_tbgroupbuy").on('click', function (event) {
        $('#addForm_tbgroupbuy').form('reset');
        $('#addForm_tbgroupbuy').find('[__actType]').val('add');
        $('#addDlg_tbgroupbuy').dialog({'title': '新增'});
        $('#addDlg_tbgroupbuy').dialog('open');
        $("#package").empty();
        $('#specIds').val("");
        $('#prices').val("");
    });
    
    
    $("#addDlgSaveBtn_tbgroupbuy").on('click', function () {
        if (!$('#addForm_tbgroupbuy').form('validate')) {
            return;
        }
       var depositStartTime = $("#d122").val();
        if(depositStartTime==null || depositStartTime==""){
        	alert("订金开始时间不能为空");
        	return;
        }
       var depositEndTime = $("#d123").val();
        if(depositEndTime==null || depositEndTime==""){
        	alert("订金结束时间不能为空");
        	return;
        }
       var balanceStartTime = $("#d124").val();
        if(balanceStartTime==null || balanceStartTime==""){
        	alert("尾款开始时间不能为空");
        	return;
        }
       var balanceEndTime = $("#d125").val();
        if(balanceEndTime==null || balanceEndTime==""){
        	alert("尾款结束时间不能为空");
        	return;
        }
             var sku = $("#skugroup").val();
             var productSpecIds = document.getElementsByName("productSpecIds[]");
             var productPrices = document.getElementsByName("productPrices[]");
			 var SpecIds= document.getElementById("specIds");  
             var Prices = document.getElementById('prices');
             //如果sku为空则判断套装输入有没有问题
             if(sku == ''){
			 if(productSpecIds.length==productPrices.length){
			 	
			 	if(productPrices.length ==0 && productPrices.length ==0){
			 		alert("套装sku和价格不能为空");
			 		return;
			 	}else{
		             for (var i = productSpecIds.length - 1; i >= 0; i--)
		             {
		                      if (productSpecIds[i] != null)
		                      {
		                      	if(productSpecIds[i].value==null||productSpecIds[i].value==""){
                                    $('#specIds').val("");
                                    $('#prices').val("");
					             	alert("套装sku不能为空");
									return;
					            }
								SpecIds.value = SpecIds.value + ',' + productSpecIds[i].value;
		                      }
		                      if (productPrices[i] != null)
		                      {
		                      	
		                      	 if(productPrices[i].value==null||productPrices[i].value==""){
					             	alert("套装价格不能为空");
									return;
					             }
		                      	 var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
					             if(!reg.test(productPrices[i].value)){
                                     $('#specIds').val("");
                                     $('#prices').val("");
					             	alert("请输入正确的套装价格");
									return;
					             }
								Prices.value = Prices.value + ',' + productPrices[i].value;
		                      }
		             }
			 	
			 	}
			 }else{
			 	alert("套装内sku数量与价格数量不匹配，请正确添加");
			 	return;
			 }
         }
        var actType = $('#addForm_tbgroupbuy').find('[__actType]').val();
        if (actType === 'add') {
            $.ajax({
	        	url      : "/orderOperation/addTbOrderGroupBuy",
	         	type     : 'POST',
	         	dataType : 'json',
	          	data     : $('#addForm_tbgroupbuy').serialize(), 
		        success  : function(data) {
		        	if (data==1) {
			            alert('添加成功');
			           	searchselect(); 
			        }
					if(data==0){
			            alert('添加失败');
			        }
					if(data==2){
			            alert('添加失败,sku与团购名称已存在');
			        }
		        }
     		});
        } else {
           $.ajax({
	        	url      : "/orderOperation/addTbOrderGroupBuy",
	         	type     : 'POST',
	         	dataType : 'json',
	          	data     : $('#addForm_tbgroupbuy').serialize(), 
		        success  : function(data) {
		        	if (data==1) {
			            alert('编辑成功');
			           	searchselect(); 
			        }
					if(data==0){
			            alert('编辑失败');
			        }
					if(data==2){
			            alert('编辑失败,sku与团购名称已存在');
			        }
		        }
     		});
        }
        $('#addDlg_tbgroupbuy').dialog('close');
    });
    
    $("#createObj").on('click', function (event) {
        event.preventDefault();
        var pack = document.getElementById("package");
        var el= document.createElement("div");
 		el.innerHTML="<div>套装商品Sku&nbsp;：&nbsp;<input name='productSpecIds[]' class='easyui-textbox'/>&nbsp;&nbsp;&nbsp;&nbsp;商品价格：<input name='productPrices[]' class='easyui-numberbox'  data-options='precision:2'/><a href='javascript:void(0)' onclick='delObj(this)'>删除</a></div>";
        document.getElementById("package").appendChild(el);
    });

    $("#editBtn_tbgroupbuy").on('click', function () {
        var selected = dataGrid.datagrid('getSelected');
        var selecteds = dataGrid.datagrid('getSelections');
        $('#addDlg_tbgroupbuy').dialog({'title': '修改'});
        if (selecteds !== null && selecteds.length==1) {
            $('#addForm_tbgroupbuy').form('load', selected);
            $('#addForm_tbgroupbuy').find('[__actType]').val('edit');
            $('#addDlg_tbgroupbuy').dialog('open');
            $("#package").empty();
            $('#specIds').val("");
        	$('#prices').val("");
            var pack = document.getElementById("package");
			$.ajax({
				url      : "/orderOperation/getProductSpecs",
				type     : 'GET',
				dataType : 'json',
				data     : {"id":selected.id}, 
				success  : function(data) {
					if(data.start == 1){
						for(var s in data.result){
		        			var el= document.createElement("div");
		 					el.innerHTML="<div>套装商品Sku&nbsp;：&nbsp;<input name='productSpecIds[]' class='easyui-textbox' value='"+data.result[s].sku+"'/>&nbsp;&nbsp;&nbsp;&nbsp;商品价格：<input name='productPrices[]' class='easyui-numberbox'  data-options='precision:2' value='"+data.result[s].price+"'/><a href='javascript:void(0)' onclick='delObj(this)'>删除</a></div>";
		        			document.getElementById("package").appendChild(el);
						}
					}else{
						alert("获取套装失败");
					}
				}
			});            
        } else {
            alert('请选择一条数据');
        }
    });
    $("#deleteBtn_tbgroupbuy").on('click', function (event) {
        event.preventDefault();
		var row = dataGrid.datagrid('getSelections');
        if (row !== null) {
            confirm('确定删除？', function (r) {
                if (r) {
					var idsArray = new Array() 
					for (var i = 0; i < row.length; i++) {  
               			idsArray[i]=row[i].id;   
                  	}
                     $.ajax({
			        	url      : "/orderOperation/delTbOrderGroupBuy",
			         	type     : 'GET',
			         	dataType : 'json',
			          	data     : {"ids[]":idsArray}, 
				        success  : function(data) {
				        	if (data) {
					            alert('删除成功');
					            searchselect();
					        } else {
					            alert('删除失败');
					        }
				        }
		     		}); 				
                }
            })
        } else {
            alert('请选择一条数据');
        }
    });
    $("#resetBtn_tbgroupbuy").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_tbgroupbuy').form('reset');
    });
    searchselect();
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
        $('#datagrid_tbgroupbuy').datagrid('resize');
    }, 500);

};
/*
$('#datagrid_tbgroupbuy').datagrid({

//双击事件
    onDblClickRow :function(rowIndex,rowData){
        addTab("网单详情[WD170817233445]","order/norderDetail",false);
    }
});*/
