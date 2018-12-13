var dataGrid = null;
// 查询
 var searchselect = function () {
	var options = dataGrid.datagrid('getPager').data("pagination").options;
	var startTime = $("#addTimeBegine").datebox('getValue');
	var endTime = $("#addTimeEnd").datebox('getValue');
	 if(startTime=="" || endTime==""){
		 alert("请输入开始时间与结束时间");
		 return false;
	 }
      $.ajax({
         url      : "/zfbOrder/orderReportFormList",
         type     : 'POST',
         dataType : 'json',
         data     : {		
             addTimeBegine:$("#addTimeBegine").datebox('getValue'),
             addTimeEnd:$("#addTimeEnd").datebox('getValue'),
             memberLoginId:$("#memberLoginId").val(),
             businessTypeCode:$("#businessTypeCode").combobox('getValue'),
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
				 addTimeBegine:$("#addTimeBegine").datebox('getValue'),
		         addTimeEnd:$("#addTimeEnd").datebox('getValue'),
		         memberLoginId:$("#memberLoginId").val(),
		         businessTypeCode:$("#businessTypeCode").combobox('getValue'),
		         remake:$("#remake").val(),
		         shopName:$("#shopName").combobox('getValue')
	         };
    var moreOptions = $(".morehide");
    for (var i = 0; i < moreOptions.length; i++) {
        $(moreOptions[i]).css("display", "none");
    } ;
    dataGrid = $('#datagrid_orderForecastGoal').datagrid({
        url: "/zfbOrder/orderReportFormList",
		striped : true, // 隔行变色
		rownumbers : true,
		fit: true,
		pagination : true,
		singleSelect : true,
		fitColunms : true,
		idField : 'id',
		columns : [ [ 
					{title: '对方账户', field: 'memberLoginId', sortable: false},
					{title: '收入金额', field: 'incomeMoney', sortable: false},
					{title: '支出金额', field: 'expenditureMoney', sortable: false},
					{title: '业务类型', field: 'businessTypeName', sortable: false},
					{title: '备注', field: 'remake', sortable: false},
					{title: '店铺名称', field: 'shopName', sortable: false}
					
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
