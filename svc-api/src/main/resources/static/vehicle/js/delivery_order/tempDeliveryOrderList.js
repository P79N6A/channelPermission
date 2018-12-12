var tempEdit = function (orderId) {
    window.location.href = "/vehicle/order?orderId=" + orderId
}

var tempCancel = function (orderNo) {
    confirm("确定作废吗？", function (r) {
        if (r) {
            $.ajax({
                url: "/vehicle/updateStatus",
                data: {orderNo: orderNo, status: "21"},
                dataType: "json",
                type: "post",
                success: function (data) {
                    if (data.success) {
                        alert("作废成功！");
                        $("#searchBtn").click();
                    } else {
                        alert(data.msg);
                    }
                }
            })
        }
    })
}


$(function () {
   
	$("#searchBtn").on('click', function() {
		$("#searchBtn").linkbutton("disable")

		datagrid = $('#datagrid').datagrid({
			url : "/vehicle/tempOrder",
			fit : true,
			fitColumns : true,
			singleSelect : false,
			queryParams : {
				startDate: $("#startDate").datebox("getValue"),
				endDate: $("#endDate").datebox("getValue"),
				status: '01'
			},
			loadMsg : '正在加载信息...',
			columns : [ [  {title: '订单号', field: 'orderNo', sortable: true, align: 'center',width:200},
            {title: '开单日期', field: 'orderTime', sortable: true, align: 'center', formatter: formatDate,width:200},
            {title: '送达方', field: 'deliveryCode', sortable: true, align: 'center',width:200},
            {title: '配送中心', field: 'distributionCentreName', sortable: true, align: 'center',width:200},
            {title: '生产基地', field: 'jdName', sortable: true, align: 'center',width:200},
            {title: '总体积', field: 'loadingVolume', sortable: true, align: 'center',width:200},
//            {title: '比例', field: 'filed5', sortable: true, align: 'center',width:200},
            {title: '操作', field: 'filed7', sortable: true, align: 'center', formatter: showWhat,width:200}
			] ],
			toolbar : '#datagridToolbar',
			striped : true,
			pagination : true,
			pageSize : 50,
			pageList : [ 50, 100, 200 ],
			rownumbers : true,
			autoRowHeight : true,
			nowrap : false
		});
		$("#searchBtn").linkbutton("enable");
	})
    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    var clearDateBox = function (id) {
        var t = {startDate: "endDate", endDate: "startDate"}
        $("#" + t[id]).datebox('calendar').calendar({
            validator: function (date) {
                return true
            }
        })
    }
    buttons.splice(1, 0, {
        text: '清空',
        handler: function (target) {
            $(target).datebox("setValue", "")
            clearDateBox($(target).attr("id"))
        }
    });

    $('#startDate').datebox({
        onSelect: function (select) {
            $('#endDate').datebox('calendar').calendar({
                validator: function (date) {
                    var d1 = new Date(select);
                    return date >= d1
                }
            });
        },
        icons: [],
        buttons: buttons
    });

    $('#endDate').datebox({
        onSelect: function (select) {
            $('#startDate').datebox('calendar').calendar({
                validator: function (date) {
                    var d1 = new Date(select);
                    return date <= d1
                }
            });
        },
        icons: [],
        buttons: buttons
    });


});