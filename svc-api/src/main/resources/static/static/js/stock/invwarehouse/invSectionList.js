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
    }, {
        title: '库位编码',
        field: 'secCode',
        sortable: false
    }]],
    columns: [[
        {title: 'LES库位', field: 'lesSecCode', sortable: false},
        {title: '库位名称', field: 'secName', sortable: false},
        {
            title: '状态', field: 'status', sortable: false,
            formatter: function (val, rec) {
                if (val == "0") {
                    return "未启用";
                } else if (val == "1") {
                    return "已启用";
                }
            }
        },
        {title: '仓库（TC）代码', field: 'whCode', sortable: false},
        {title: '库位属性', field: 'sectionProperty', sortable: false},
        {title: '渠道编码', field: 'channelCode', sortable: false},
        {title: '批次', field: 'itemProperty', sortable: false},
        {title: '销售组织编码', field: 'corpCode', sortable: false},
        {title: '付款方编码', field: 'custCode', sortable: false},
        {title: '地区编码', field: 'regionCode', sortable: false},
        {title: '分渠道送达方', field: 'ehaierDeliverCode', sortable: false},
        {title: '物流OE码', field: 'les0eCode', sortable: false},
        {title: '创建者', field: 'createUser', sortable: false},
        {title: '创建时间', field: 'createTime', sortable: false, formatter: formatDatebox},
        {title: '最后更新人', field: 'updateUser', sortable: false},
        {title: '最后更新时间', field: 'updateTime', sortable: false, formatter: formatDatebox}
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
        $('#addForm_invSection').form('reset');
        $('#addForm_invSection').find('[__actType]').val('add');
        $('#addDlg_invSection').dialog({'title': '新增'});
        $('#addDlg_invSection').dialog('open');
        $("#id").val("insert");
        $('#secCode').attr("readonly", false);
        $('#secCode').css("background-color", "#FFFFFF");

        $("#lesSecCode").textbox('textbox').attr('maxlength', 10);
        $("#secName").textbox('textbox').attr('maxlength', 30);
        $("#whCode").textbox('textbox').attr('maxlength', 2);
        $("#sectionProperty").textbox('textbox').attr('maxlength', 5);
        $("#corpCode").textbox('textbox').attr('maxlength', 10);
        $("#custCode").textbox('textbox').attr('maxlength', 16);
        $("#ehaierDeliverCode").textbox('textbox').attr('maxlength', 30);
        $("#les0eCode").textbox('textbox').attr('maxlength', 30);
        $("#itemProperty").textbox('textbox').attr('maxlength', 2);
        $("#channelCode").textbox('textbox').attr('maxlength', 10);
    });

    $("#addDlgSaveBtn_invSection").on('click', function () {
        var channel_code = $("#channelCode").val();
        var seccode = $("#secCode").val();
        if (seccode == null || seccode == "") {
            $.messager.alert('提示', '请选择库位编码');
            return;
        }
        if (channel_code == null || channel_code == "") {
            $.messager.alert('提示', '请选择渠道编码');
            return;
        }
        if (!$('#addForm_invSection').form('validate')) {
            return;
        }
        $('#addForm_invSection').form('submit', {
            url: '/invwarehouse/addInvSection',
            onSubmit: function () {
                if ($("#addForm_invSection").form("validate"))
                    return true
                else
                    return false;
            },
            success: function (data) {
                var actType = $('#addForm_invSection').find('[__actType]').val();
                var obj = data;
                if (obj == "codeIsSame") {
                    $.messager.alert('提示', '库位编码已存在');
                    return;
                }
                if (obj == "nameIsSame") {
                    $.messager.alert('提示', '仓库名称已存在');
                  return;
                }
                if (obj == "success") {
                    $('#datagrid_warehouseListGoal').datagrid('reload');
                    $("#addForm_invSection").form("clear");
                    if (actType === 'add') {
//        				alert('新增成功');
                        $.messager.alert('提示', '新增成功');
                    } else {
                        $.messager.alert('提示', '修改成功');
                    }
                    $('#addDlg_invSection').dialog('close');
                }else {
                  $.messager.alert('错误', "保存数据失败，请检查数据格式长度");
                  return;
                }

            }
        });
    });
    var sec_code;
    $("#editBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_invSection').dialog({'title': '修改'});
        if (selected !== null) {
            sec_code = selected.secCode;
            $('#addForm_invSection').form('load', selected);
            $('#addForm_invSection').find('[__actType]').val('edit');
            $('#addDlg_invSection').dialog('open');
        } else {
            $.messager.alert('提示', '请选择一条数据');
        }
        var actType = $('#addForm_invSection').find('[__actType]').val();
        if (actType === 'edit') {
//        	$('#secCode').textbox('textbox').attr('disabled',true); 
            $('#secCode').attr("readonly", "readonly");
            $("#secCode").css("background-color", "#D6D6FF");
        }
        $("#id").val(sec_code);

        $("#lesSecCode").textbox('textbox').attr('maxlength', 10);
        $("#secName").textbox('textbox').attr('maxlength', 30);
        $("#whCode").textbox('textbox').attr('maxlength', 2);
        $("#sectionProperty").textbox('textbox').attr('maxlength', 5);
        $("#corpCode").textbox('textbox').attr('maxlength', 10);
        $("#custCode").textbox('textbox').attr('maxlength', 16);
        $("#ehaierDeliverCode").textbox('textbox').attr('maxlength', 30);
        $("#les0eCode").textbox('textbox').attr('maxlength', 30);
        $("#itemProperty").textbox('textbox').attr('maxlength', 2);
        $("#channelCode").textbox('textbox').attr('maxlength', 10);

    });

    /**
     * 删除
     */
    $("#deleteBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        var id = selected.secCode;
        if (selected !== null) {
            confirm('确定删除？', function (r) {
                if (r == true) {
                    $.post("/invwarehouse/removeInvSection", {id: id}, function (data) {
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
    var secCodeData = $("#sec_code").textbox("getValue");
    var lesSecCodeData = $("#les_sec_code").textbox("getValue");
    var secNameData = $("#sec_name").textbox("getValue");
    //为导出form赋值
    $("#e_sec_code").val(secCodeData);
    $("#e_les_sec_code").val(lesSecCodeData);
    $("#e_sec_name").val(secNameData);
    $("#code").val("yes");

    //加载分页
    datagrid = $('#datagrid_warehouseListGoal').datagrid({
        url: "/invwarehouse/findInvSectionList",
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
            secCode: secCodeData,
            lesSecCode: lesSecCodeData,
            secName: secNameData
        },
        frozenColumns: [[{
            field: 'id',
            checkbox: false,
            hidden:true
        }, {
            title: '库位编码',
            field: 'secCode',
            sortable: false
        }]],
        columns: [[
            {title: 'LES库位', field: 'lesSecCode', sortable: false},
            {title: '库位名称', field: 'secName', sortable: false},
            {
                title: '状态', field: 'status', sortable: false,
                formatter: function (val, rec) {
                    if (val == "0") {
                        return "未启用";
                    } else if (val == "1") {
                        return "已启用";
                    }
                }
            },
            {title: '仓库（TC）代码', field: 'whCode', sortable: false},
            {title: '库位属性', field: 'sectionProperty', sortable: false},
            {title: '渠道编码', field: 'channelCode', sortable: false},
            {title: '批次', field: 'itemProperty', sortable: false},
            {title: '销售组织编码', field: 'corpCode', sortable: false},
            {title: '付款方编码', field: 'custCode', sortable: false},
            {title: '地区编码', field: 'regionCode', sortable: false},
            {title: '分渠道送达方', field: 'ehaierDeliverCode', sortable: false},
            {title: '物流OE码', field: 'les0eCode', sortable: false},
            {title: '创建者', field: 'createUser', sortable: false},
            {title: '创建时间', field: 'createTime', sortable: false, formatter: formatDatebox},
            {title: '最后更新人', field: 'updateUser', sortable: false},
            {title: '最后更新时间', field: 'updateTime', sortable: false, formatter: formatDatebox}
        ]],
    });

});

//点击导入
$('#importBtn').click(function () {
    var url = '/invwarehouse/importInvSection';
    window.location.href = url;
    return false;
});
//点击导出
$("#exportBtn").click(function () {
    $("#exportForm").attr("action", "/invwarehouse/exportInvSectionList");
    $("#exportForm").submit();
});
//日期格式化方法
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
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
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

    return dt.format("yyyy-MM-dd hh:mm:ss"); // 扩展的Date的format方法(上述插件实现)
}
