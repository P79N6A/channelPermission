//添加
$(function() {
	$("#dialog-message").panel('close');
});
$("#btnAdd").click(function() {
	$("#dialog-message input").val("");
	$("#dialog-message").show();
	$("#dialog-message").dialog('open');
});


//$("#y").click(
//		function() {
//			$.messager.confirm('确认', '确定要保存这个Job任务吗？', function(r) {
//				if (r) {
//					jQuery.ajax({
//						url : url,
//						data : {
//							jobId : $("#jobId").val(),
//							jobName : $("#add-jobName").val(),
//							jobStatus : $("#add-jobStatus").val(),
//							cron : $("#add-cron").val(),
//							jobType : $("#add-jobType").val(),
//							cfgDataStr : $("#add-cfgDataStr").val()
//						},
//						type : "POST",
//						success : function(data) {
//							$.messager.alert('提示', '保存成功', 'info');
//							$("#dialog-message").dialog('close');
//							$('#dataGrid').parent().find(
//									"div .datagrid-header-check").children(
//									"input[type='checkbox']").eq(0).attr(
//									"checked", false);
//						}
//					});
//				}
//			});
//		});

$("#n").click(function() {
	$("#dialog-message").panel('close');
});

//点击查询
$('#btnSearch').click(function () {
    load();
});

function load() {
    //生成grid
    $('#dataGrid').datagrid({
        url: "/Job/getJobList",
        type:"POST",
        fit: true,
        pagination: true,
        singleSelect: false,
        checkOnSelect:true,
        pageSize: 100,
        pageList: [1,2,100,200,300],
        nowrap: false,
        rownumbers: true,
        queryParams: {
            jobName : $("#jobName").val(),
            jobStatus : $("#jobStatus").val()
        },
        columns : [ [
            {
                field : 'jobName',
                title : 'Job名称',
                width : '100',
                align : 'center'
            },
            {
                field : 'jobStatus',
                title : 'Job状态',
                width : '100',
                align : 'center',
                formatter : function(value) {
                    return (value == '1' ? "启用" : "停用");
                }
            },
            {
                field : 'jobType',
                title : 'Job类型',
                width : '100',
                align : 'center'
            },
            {
                field : 'cfgDataStr',
                title : 'Job配置数据 ',
                width : '500',
                align : 'center'
            },
            {
                field : 'cron',
                title : 'Cron表达式',
                width : '200',
                align : 'center',
                formatter : function(value) {
                    value = "'"+value+"'";
                    return value+'<a href="#" onclick="onViewExPlan('+value+');">查看</a>'
                }

            },
            {
                field : 'updateUser',
                title : '最后修改人',
                width : '100',
                align : 'center'
            },
            {
                field : 'updateTime',
                title : '最后修改时间',
                width : '100',
                align : 'center',
                formatter : function(value) {
                    var date = new Date(value);
                    var year = date.getFullYear()
                        .toString();
                    var month = (date.getMonth() + 1);
                    var day = date.getDate().toString();
                    var hour = date.getHours().toString();
                    var minutes = date.getMinutes()
                        .toString();
                    var seconds = date.getSeconds()
                        .toString();
                    if (month < 10) {
                        month = "0" + month;
                    }
                    if (day < 10) {
                        day = "0" + day;
                    }
                    if (hour < 10) {
                        hour = "0" + hour;
                    }
                    if (minutes < 10) {
                        minutes = "0" + minutes;
                    }
                    if (seconds < 10) {
                        seconds = "0" + seconds;
                    }
                    return year + "-" + month + "-" + day
                        + " " + hour + ":" + minutes
                        + ":" + seconds;
                }
            } ] ]
    });
    //}
    $('#dataGrid').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);

}

function onViewExPlan(cronExp) {
	jQuery.getJSON("/Job/onViewCronPlan?cronExp=" + cronExp, function(result) {
        var reg = new RegExp("---","g");
	    var str = result.message.replace(reg,"<br>");
	    alert(str);
	});
}
function save(){
	debugger;
	var url = "";
	if($("#jobId").val()!=""){
		url = "/Job/updateJob"
	}else{
		url = "/Job/addJob"
	}
	$.messager.confirm('确认', '确定要保存这个Job任务吗？', function(r) {
		if (r) {
			jQuery.ajax({
				url : url,
				data : {
					jobId : $("#jobId").val(),
					jobName : $("#add-jobName").val(),
					jobStatus : $("#add-jobStatus").val(),
					cron : $("#add-cron").val(),
					jobType : $("#add-jobType").val(),
					cfgDataStr : $("#add-cfgDataStr").val()
				},
				type : "POST",
				success : function(data) {
					$.messager.alert('提示', '保存成功', 'info');
					$("#dialog-message").dialog('close');
					$('#dataGrid').parent().find(
							"div .datagrid-header-check").children(
							"input[type='checkbox']").eq(0).attr(
							"checked", false);
                    load();
				}
			});
		}
	});

}
function toUpdate(){
	var row = $('#dataGrid').datagrid('getSelected');
	if (row !== null) {
		$("#dialog-message input").val("");
		$("#dialog-message").show();
		$("#dialog-message").dialog('open');
		$("#jobId").val(row.jobId);
		$("#add-jobName").val(row.jobName);
		$("#add-jobType").val(row.jobType);
		$("#add-cfgDataStr").val(row.cfgDataStr);
		$("#add-cron").val(row.cron);
		$("#add-jobStatus").val(row.jobStatus);
	}else{
		alert('请选择一条数据');
	}
}
