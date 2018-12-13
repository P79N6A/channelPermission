var dataGrid = null;
//查询
 var searchselect = function () {
	 var options = dataGrid.datagrid('getPager').data("pagination").options;
      $.ajax({
         url      : "/zfbOrder/orderList",
         type     : 'POST',
         dataType : 'json',
          data     : {		
              orderSn:$("#orderSn").val(),
              addTimeBegine:$("#addTimeBegine").datebox('getValue'),
              addTimeEnd:$("#addTimeEnd").datebox('getValue'),
              memberLoginId:$("#memberLoginId").val(),
              businessTypeCode:$("#businessTypeCode").combobox('getValue'),
              incomeMoneymin:$("#incomeMoneymin").val(),
              incomeMoneymax:$("#incomeMoneymax").val(),
              expenditureMoneymin:$("#expenditureMoneymin").val(),
              expenditureMoneymax:$("#expenditureMoneymax").val(),
              remake:$("#remake").val(),
              shopName:$("#shopName").combobox('getValue'),

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
	 var queryParameters={		
								orderSn:$("#orderSn").val(),
								addTimeBegine:$("#addTimeBegine").datebox('getValue'),
								addTimeEnd:$("#addTimeEnd").datebox('getValue'),
         memberLoginId:$("#memberLoginId").val(),
         businessTypeCode:$("#businessTypeCode").combobox('getValue'),
         incomeMoneymin:$("#incomeMoneymin").val(),
         incomeMoneymax:$("#incomeMoneymax").val(),
         expenditureMoneymin:$("#expenditureMoneymin").val(),
         expenditureMoneymax:$("#expenditureMoneymax").val(),
         remake:$("#remake").val(),
	         };
    var moreOptions = $(".morehide");
    for (var i = 0; i < moreOptions.length; i++) {
        $(moreOptions[i]).css("display", "none");
    } ;
    dataGrid = $('#datagrid_orderForecastGoal').datagrid({
        url: "/zfbOrder/orderList",
		striped : true, // 隔行变色
		rownumbers : true,
		fit: true,
		pagination : true,
		singleSelect : true,
		fitColunms : true,
		idField : 'id',
		columns : [ [ 
					{title: '账务流水单号 ', field: 'accountsNo', sortable: false},
					{title: '业务流水单号 ', field: 'businessNo', sortable: false},
					{title: '商户订单号', field: 'ordersn', sortable: false},
					{title: '商品名称', field: 'productName', sortable: false},
					{title: '对方账户', field: 'memberLoginId', sortable: false},
					{title: '收入金额', field: 'incomeMoney', sortable: false},
					{title: '支出金额', field: 'expenditureMoney', sortable: false},
					{title: '账户余额', field: 'accountBalance', sortable: false},
					{title: '支付方式', field: 'paymentName', sortable: false},
					{title: '业务类型', field: 'businessTypeName', sortable: false},
					{title: '备注', field: 'remake', sortable: false},
					{title: '创建时间', field: 'time', sortable: false},
					{title: '淘宝业务类型', field: 'taobaoBusinessType', sortable: false},
					{title: '店铺名称', field: 'shopName', sortable: false}
					
		 ] ],
		 toolbar: '#datagridToolbar_orderForecastGoal',
         pageSize: 50,
         pageList: [50, 100, 200],
		//onClickRow : function(rowIndex,rowData) {
		//	searchMxlist(rowData.applyCode);
		//}
	});
    dataGrid.datagrid('getPager').pagination({
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
        addTab("网单详情[WD170817233445]","order/norderDetail",false);
    }
});*/
