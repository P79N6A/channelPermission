var dataGrid = null;
//查询
 var searchselect = function () {
	 var options = dataGrid.datagrid('getPager').data("pagination").options;
      $.ajax({
         url      : "/zfbOrder/matchingOrderList",
         type     : 'POST',
         dataType : 'json',
          data     : {		
							orderSn:$("#orderSn").val(),
							addTimeBegine:$("#addTimeBegine").datebox('getValue'),
							addTimeEnd:$("#addTimeEnd").datebox('getValue'),
							source:$("#source").combobox('getValue'),
              memberLoginId:$("#memberLoginId").val(),
              businessTypeCode:$("#businessTypeCode").combobox('getValue'),
              differenceStatus:$("#differenceStatus").combobox('getValue'),
              incomeMoneymin:$("#incomeMoneymin").val(),
              incomeMoneymax:$("#incomeMoneymax").val(),
              expenditureMoneymin:$("#expenditureMoneymin").val(),
              expenditureMoneymax:$("#expenditureMoneymax").val(),
              remake:$("#remake").val(),
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
								source:$("#source").combobox('getValue'),
         memberLoginId:$("#memberLoginId").val(),
         businessTypeCode:$("#businessTypeCode").combobox('getValue'),
         differenceStatus:$("#differenceStatus").combobox('getValue'),
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
        url: "/zfbOrder/matchingOrderList",
		striped : true, // 隔行变色
		rownumbers : true,
		fit: true,
		pagination : true,
		singleSelect : true,
		fitColunms : true,
		idField : 'id',
		columns : [ [ 

					{title: '商户订单号', field: 'ordersn', sortable: false},
            		{title: '业务类型', field: 'businessTypeName', sortable: false},
            {title: '差异', field: 'differenceStatus', sortable: false,
                formatter: function(value,row,index){
                    var isNot="";
                    if(row.differenceStatus ==1){
                        isNot="没有差异";
                    }
                    if(row.differenceStatus ==2){
                        isNot="有差异";
                    }
                    return isNot;
                }
            },
            {title: '创建时间', field: 'createTime', sortable: false},
            {title: '支付宝流水收入金额', field: 'incomeMoney', sortable: false},
            {title: '支付宝流水支出金额', field: 'expenditureMoney', sortable: false},
            {title: '订单总金额', field: 'productAmounto', sortable: false},
			{title: '实付金额', field: 'orderamount', sortable: false},
            {title: '发票金额', field: 'billAmount', sortable: false},
            {title: '网单使用积分', field: 'points', sortable: false},
            {title: '网单集分宝', field: 'jfbAmount', sortable: false},
            {title: '网单点券', field: 'djAmount', sortable: false},
            {title: '网单红包', field: 'hbAmount', sortable: false},
            {title: '网单金额', field: 'productAmount', sortable: false},
			{title: '网单数量', field: 'number', sortable: false},
			{title: '网单使用优惠券', field: 'couponAmount', sortable: false},
			{title: '节能补贴金额', field: 'esAmount', sortable: false},
			{title: '订单优惠价格分摊', field: 'itemShareAmount', sortable: false},
           //{title: '淘宝运费', field: 'shippingAmount', sortable: false},
           // {title: '总节能补贴', field: 'totalEsAmount', sortable: false},
            {title: '账务流水单号 ', field: 'accountsNo', sortable: false},
            {title: '业务流水单号 ', field: 'businessNo', sortable: false},

            //{title: '账户余额', field: 'accountBalance', sortable: false},
           // {title: '商品名称', field: 'productName', sortable: false},
            {title: '对方账户', field: 'memberLoginId', sortable: false},
           // {title: '支付方式', field: 'paymentName', sortable: false},
            {title: '备注', field: 'remake', sortable: false},

            //{title: '淘宝业务类型', field: 'taobaoBusinessType', sortable: false},
            {title: '订单来源', field: 'source', sortable: false,
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
                        isNot="生态授权店";
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
                        isNot="国美海尔官方旗舰店";
                    }
                    if(row.source =='GMZXTS'){
                        isNot="国美统帅官方旗舰店";
                    }
                    if(row.source =='SNYG'){
                        isNot="苏宁统帅官方旗舰店";
                    }
                    if(row.source =='SNHEGQ'){
                        isNot="苏宁海尔官方旗舰店";
                    }
                    if(row.source =='SNQDZX'){
                        isNot="苏宁渠道中心";
                    }
                    if(row.source =='DDW'){
                        isNot="当当";
                    }
                    if(row.source =='JDHEBXGQ'){
                        isNot="京东海尔集团冰箱官方旗舰店";
                    }
                    if(row.source =='JDHEGQ'){
                        isNot="京东海尔官方旗舰店";
                    }
                    return isNot ;
                }}
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
    
    $("#importBtn_orderForecastGoal").on('click', function (event) {
    	if(!dataGrid){
		$.messager.alert('提示','请查询！','info');
			return;	
		}
		$.messager.confirm('确认','确定要导出吗？', function(r){
			if (r){
				$('#exportData').val(JSON.stringify(queryParameters));
				$('#paramForm_orderForecastGoal').attr("action", '/zfbOrder/exportMatchingOrderList');
				$('#paramForm_orderForecastGoal').submit();
			}
		});
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
