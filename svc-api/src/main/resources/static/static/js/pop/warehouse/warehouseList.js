var datagridOptions_warehouseListGoal = {
    fit: true,//自适应
    fitColumns: true,//fitColumns: true,
    singleSelect: true,//多选
    url: '',
//    idField: 'id',
    frozenColumns: [[{
    	 field: 'id',
         checkbox: false,
         hidden:true
    }]],
    columns: [[
        {title: '渠道名称 ', field: 'channelName', sortable: true},
        {title: '仓库编码', field: 'warehouseCode', sortable: true},
        {title: '仓库名称', field: 'warehouseName', sortable: true},
        {title: '海尔仓库编码', field: 'warehouseCodeHaier', sortable: true},
        {title: '海尔仓库名称', field: 'warehouseNameHaier', sortable: true},
        {
            title: '仓库类型', field: 'warehouseType', sortable: true,
            formatter: function (val, rec) {
                if (val == "1") {
                    return "始发仓";
                } else if (val == "2") {
                    return "所属仓";
                }
            }
        },
        {title: '始发仓', field: 'pname', sortable: true},
        {title: '送达方编码', field: 'transmitCode', sortable: true},
        {title: '所在城市', field: 'city', sortable: true},
        {title: '创建者', field: 'createBy', sortable: true},
        {title: '创建时间', field: 'cTime', sortable: true},
//        {title: '更新者', field: 'updateby', sortable: true},
//        {title: '更新时间', field: 'uTime', sortable: true},
        {title: '备注', field: 'remark', sortable: true}
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
};
$(function () {
    var datagrid = $('#datagrid_warehouseListGoal').datagrid(datagridOptions_warehouseListGoal);

    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_warehouse').form('reset');
        $('#addForm_warehouse').find('[__actType]').val('add');
        $('#addDlg_warehouse').dialog({'title': '新增'});
        $('#addDlg_warehouse').dialog('open');
        $("#pnameDiv").css("display", "none");
//        $('#channelcode').textbox('textbox').attr('disabled',false); 
        $("#warehouseCode").textbox('textbox').attr('maxlength', 50);
        $("#warehouseName").textbox('textbox').attr('maxlength', 50);
        $("#warehouseCodeHaier").textbox('textbox').attr('maxlength', 50);
        $("#warehouseNameHaier").textbox('textbox').attr('maxlength', 50);
        $("#transmitCode").textbox('textbox').attr('maxlength', 20);
        $("#city").textbox('textbox').attr('maxlength', 20);
        $("#remark").textbox('textbox').attr('maxlength', 200);
        Test();//-----------------------------------------------------------点击新增时
    });

    $("#addDlgSaveBtn_warehouse").on('click', function () {
        var pid = $("#pid").val();
        var channel_code = $("#channelId").combobox("getValue");
        var warehousetype = $("#warehouseType").combobox("getValue");
        if (channel_code == "" || channel_code == null) {
            $.messager.alert('提示', '请选择渠道编码');
            return;
        }
        if (warehousetype == 0 || warehousetype == "") {
            $.messager.alert('提示', '请选择仓库类型');
            return;
        }
        if (warehousetype == 2) {
            if (pid == "" || pid == null) {
                $.messager.alert('提示', '请选择始发库 , 或者改变仓库类型');
                return;
            }
        }
        if (warehousetype == 1) {
            $("#pid").val("0");
        }

        console.info("warehousetype" + warehousetype);
        if (!$('#addForm_warehouse').form('validate')) {
            return;
        }
        $('#addForm_warehouse').form('submit', {
            url: '/warehouse/addWarehouse',
            onSubmit: function () {
                if ($("#addForm_warehouse").form("validate"))
                    return true
                else
                    return false;
            },
            success: function (data) {
                var actType = $('#addForm_warehouse').find('[__actType]').val();
                var obj = data;
                if (obj == "codeIsSame") {
                    $.messager.alert('提示', '仓库编码已存在');
                }
                if (obj == "success") {
                    $('#datagrid_warehouseListGoal').datagrid('reload');
                    $("#addForm_warehouse").form("clear");
                    if (actType === 'add') {
//        				alert('新增成功');
                        $.messager.alert('提示', '新增成功');
                    } else {
                        $.messager.alert('提示', '修改成功');
                    }
                    $('#addDlg_warehouse').dialog('close');
                }
            }
        });
    });

    $("#editBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_warehouse').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_warehouse').form('load', selected);
            $('#addForm_warehouse').find('[__actType]').val('edit');
            $('#addDlg_warehouse').dialog('open');
        } else {
            $.messager.alert('提示', '请选择一条数据');
        }
        var warehousetype = $("#warehouseType").combobox("getValue");
        if (warehousetype == 1) {
            $("#pnameDiv").css("display", "none");
        }
        $("#warehouseCode").textbox('textbox').attr('maxlength', 50);
        $("#warehouseName").textbox('textbox').attr('maxlength', 50);
        $("#warehouseCodeHaier").textbox('textbox').attr('maxlength', 50);
        $("#warehouseNameHaier").textbox('textbox').attr('maxlength', 50);
        $("#transmitCode").textbox('textbox').attr('maxlength', 20);
        $("#city").textbox('textbox').attr('maxlength', 20);
        $("#remark").textbox('textbox').attr('maxlength', 200);
//        Test();//-----------------------------------------------------------点击修改时
    });

    /**
     * 删除
     */
    $("#deleteBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
            var id = selected.id;
            console.log("2222" + id);
            confirm('确定删除？', function (r) {
                if (r == true) {
                    $.post("/warehouse/removeWarehouse", {id: id}, function (data) {
                        if (data.text = "success") {
                            $.messager.alert('提示', "删除成功");
                            $('#datagrid_warehouseListGoal').datagrid('reload');
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示', '请选择一条数据');
        }
    });

    $("#resetBtn_orderForecastGoal").on('click', function (event) {
        event.preventDefault();
        $('#paramForm_orderForecastGoal').form('reset');
    });
});

//点击查询
$("#searchBtn").on('click', function (event) {
    var sourceData = $("#source").combobox("getValue");
    var warehouseCodeData = $("#warehousecode").textbox("getValue");
    var warehouseNameData = $("#warehousename").textbox("getValue");
    var warehouseCodeHaierData = $("#warehousecodehaier").textbox("getValue");
    var warehouseNameHaierData = $("#warehousenamehaier").textbox("getValue");
    var warehouseTypeData = $("#warehousetype").combobox("getValue");
    var transmitCodeData = $("#transmitcode").textbox("getValue");
    datagrid = $('#datagrid_warehouseListGoal').datagrid({
        url: "/warehouse/findWarehouseList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        queryParams: {
            channelId: sourceData,
            warehouseCode: warehouseCodeData,
            warehouseName: warehouseNameData,
            warehouseCodeHaier: warehouseCodeHaierData,
            warehouseNameHaier: warehouseNameHaierData,
            warehouseType: warehouseTypeData,
            transmitCode: transmitCodeData
        },
        frozenColumns: [[{
        	 field: 'id',
             checkbox: false,
             hidden:true
        }]],
        columns: [[
            {title: '渠道名称 ', field: 'channelName', sortable: true},
            {title: '仓库编码', field: 'warehouseCode', sortable: true},
            {title: '仓库名称', field: 'warehouseName', sortable: true},
            {title: '海尔仓库编码', field: 'warehouseCodeHaier', sortable: true},
            {title: '海尔仓库名称', field: 'warehouseNameHaier', sortable: true},
            {
                title: '仓库类型', field: 'warehouseType', sortable: true,
                formatter: function (val, rec) {
                    if (val == "1") {
                        return "始发仓";
                    } else if (val == "2") {
                        return "所属仓";
                    }
                }
            },
            {title: '始发仓', field: 'pname', sortable: true},
            {title: '送达方编码', field: 'transmitCode', sortable: true},
            {title: '所在城市', field: 'city', sortable: true},
            {title: '创建者', field: 'createBy', sortable: true},
            {title: '创建时间', field: 'cTime', sortable: true},
//        {title: '更新者', field: 'updateby', sortable: true},
//        {title: '更新时间', field: 'uTime', sortable: true},
            {title: '备注', field: 'remark', sortable: true}
        ]],
    });

})

$("#warehouseType").combobox({
    onChange: function (n, o) {
        Test();//-----------------------------------------------------------仓库类型改变时
        var warehousetype = $("#warehouseType").combobox("getValue");
        var channel_code = $("#channelId").combobox("getValue");
        console.info("warehousetype" + warehousetype);
        if (warehousetype == 2) {
            $("#pnameDiv").css("display", "block");
        }
        if (warehousetype == 1) {
            $("#pnameDiv").css("display", "none");
        }
    }
});

$("#channelId").combobox({
    onChange: function (n, o) {
        Test();//-----------------------------------------------------------渠道发生改变时
    }
});

function Test() {
    $("#pid").empty();
    var warehousetype = $("#warehouseType").combobox("getValue");
    var channel_code = $("#channelId").combobox("getValue");
    var id = $("#id").val();
    console.info("id=" + id);
    if (warehousetype == 2) {
        $.post("/warehouse/autoLoadPid", {channelId: channel_code, id: id}, function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#pid").append("<option value=" + data[i].id + ">" + data[i].warehouseName + "</option>");
            }
        })
        $("#pnameDiv").css("display", "block");
    }
}

//function autoLoad(){
//	var warehousetype = $("#warehouseType").combobox("getValue");
//	var channel_code = $("#channelId").combobox("getValue");
//	if(warehousetype==2){
//		$.post("/warehouse/autoLoadPid",{channelId:channel_code},function(data){
//			for (var i = 0; i < data.length; i++) {
//				$("#pid").append("<option value="+data[i].id+">" + data[i].warehouseName + "</option>");
//			}
//		})
//		$("#pnameDiv").css("display", "block");
//	}
//}

//$("#pid").focus(function(){
//	Test();
//})
/*
 $('#datagrid_warehouseListGoal').datagrid({

 //双击事件
 onDblClickRow :function(rowIndex,rowData){
 addTab("订单详情[rsq_47026570109147374]","order/orderDetails.html",false);
 }
 });
 */

function slideUpSlideClk(t) {
    var $this = $(t);
    if ($this.hasClass('open')) {
        $this.removeClass('open');
        setTimeout(function () {
            $this.addClass('close');
        }, 0);
        $(".search-panel").slideUp();
    } else {
        $this.removeClass('close');
        setTimeout(function () {
            $this.addClass('open');
        }, 0);
        $(".search-panel").slideDown();
    }
    setTimeout(function () {
        $('#datagrid_warehouseListGoal').datagrid('resize');
    }, 500);
}
function ajaxLoading() {
    $("<div class=\"datagrid-mask\" style='height: auto'></div>").css({
        display : "block",
        width : "100%",
        height : $(window).height()
    }).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo(
        "body").css({
        display : "block",
        height : "auto",
        left : ($(document.body).outerWidth(true) - 190) / 2,
        top : ($(window).height() - 45) / 2
    });
}

function ajaxLoadEnd() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}