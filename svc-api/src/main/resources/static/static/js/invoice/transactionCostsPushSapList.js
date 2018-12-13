var dataGrid = null;
// 查询
 var searchselect = function () {
	var options = dataGrid.datagrid('getPager').data("pagination").options;
	
      $.ajax({
         url      : "/zfbOrder/transactionCostsPushSapPage",
         type     : 'POST',
         dataType : 'json',
         data     : {		
             addTimeBegine:$("#addTimeBegine").datebox('getValue'),
             addTimeEnd:$("#addTimeEnd").datebox('getValue'),
             supplier:$("#supplier").val(),
             businessTypeName:$("#businessType").combobox('getValue'),
             pushResult:$("#pushResult").val(),
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
				 addTimeBegine:$("#addTimeBegine").datebox('getValue'),
		         addTimeEnd:$("#addTimeEnd").datebox('getValue'),
		         supplier:$("#supplier").val(),
		         businessTypeName:$("#businessType").combobox('getValue'),
	             pushResult:$("#pushResult").val(),
	         };
    var moreOptions = $(".morehide");
    for (var i = 0; i < moreOptions.length; i++) {
        $(moreOptions[i]).css("display", "none");
    } ;
    dataGrid = $('#datagrid_orderForecastGoal').datagrid({
        url: "/zfbOrder/transactionCostsPushSapPage",
		striped : true, // 隔行变色
		rownumbers : true,
		fit: true,
		pagination : true,
		singleSelect : true,
		fitColunms : true,
		idField : 'id',
		columns : [ [ 
					{title: '日期', field: 'time', sortable: false},
					{title: '供应商', field: 'supplier', sortable: false},
					{title: '业务类型', field: 'businessType', sortable: false},
					{title: '收入金额', field: 'startMoney', sortable: false},
					{title: '支出金额', field: 'endMoney', sortable: false},
					{title: '推送备注', field: 'pushNotes', sortable: false},
					{title: '对方账号', field: 'accountNumber', sortable: false},
					{title: '推送结果', field: 'pushResult', align: 'center',
						formatter: function (value, row, index) {
	                        var cpushResult = "";
	                        if (row.pushResult == '0') {
	                        	cpushResult = "未推送";
	                        } else if (row.pushResult == '1') {
	                        	cpushResult = '推送成功';
	                        } else if (row.pushResult == '2') {
	                        	cpushResult = '推送失败';
	                        }
	                        return cpushResult;
	                    }
					}
		 ] ],
		 toolbar: '#datagridToolbar_orderForecastGoal',
         pageSize: 50,
         pageList: [50, 100, 200],
	});
    dataGrid.datagrid('getPager').pagination({
		onSelectPage : function(pageNumber, pageSize) {
			searchselect();
		}
	});
    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
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
