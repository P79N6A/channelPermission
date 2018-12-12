var datagridOptions_orderForecastGoal = {
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
        {title: '渠道编码 ', field: 'channelCode', sortable: true},
        {title: '渠道名称', field: 'channelName', sortable: true},
        {title: '金额额度', field: 'moneyLimit', sortable: true, align: 'right'},
        {title: '报警金额', field: 'moneyAlert', sortable: true, align: 'right'},
        {title: '锁定金额', field: 'moneyLock', sortable: true, align: 'right'},
        {title: '创建者', field: 'createBy', sortable: true},
        {title: '创建时间', field: 'cTime', sortable: true},
        {title: '更新者', field: 'updateBy', sortable: true},
        {title: '更新时间', field: 'uTime', sortable: true},
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
    var datagrid = $('#datagrid_channelForecastGoal').datagrid(datagridOptions_orderForecastGoal);

    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_orderForecastGoal').form('reset');
        $('#addForm_orderForecastGoal').find('[__actType]').val('add');
        $('#addDlg_orderForecastGoal').dialog({'title': '新增'});
        $('#addDlg_orderForecastGoal').dialog('open');

        $('#channelCode').attr("readonly", false);

        $("#channelName").textbox('textbox').attr('maxlength', 50);
        $("#moneyLimit").textbox('textbox').attr('maxlength', 10);
        $("#moneyAlert").textbox('textbox').attr('maxlength', 10);
        $("#moneyLock").textbox('textbox').attr('maxlength', 10);
        $("#remark").textbox('textbox').attr('maxlength', 200);
        $("#channelCode").css("background-color", "#FFFFFF");
    });
    $("#addDlgSaveBtn_orderForecastGoal").on('click', function () {
        var code = $('#channelCode').val();
        var li = $("#moneyLimit").textbox('getValue');
        var al = $("#moneyAlert").textbox('getValue');
        var lo = $("#moneyLock").textbox('getValue');
        if (code == "" || code == null) {
            $.messager.alert('提示', '请输入渠道编码');
            return;
        }
        if ((+al) > (+li)) {
            $.messager.alert('提示', '报警金额不得大于金额额度');
            return;
        }
        if ((+lo) > (+al)) {
            $.messager.alert('提示', '锁定金额不得大于报警金额');
            return;
        }


        if (!$('#addForm_orderForecastGoal').form('validate')) {
            return;
        }
        $('#addForm_orderForecastGoal').form('submit', {
            url: '/pop/addChannel',
            onSubmit: function () {
                if ($("#addForm_orderForecastGoal").form("validate"))
                    return true
                else
                    return false;
            },
            success: function (data) {
                var actType = $('#addForm_orderForecastGoal').find('[__actType]').val();
                var obj = data;
                console.log(obj);
                if (obj == "codeIsSame") {
                    $.messager.alert('提示', '渠道编码已存在');
                }
                if (obj == "nameIsSame") {
                    $.messager.alert('提示', '渠道名称已存在');
                }
                if (obj == "success") {
                    $('#datagrid_channelForecastGoal').datagrid('reload');
                    $("#addForm_orderForecastGoal").form("clear");
                    if (actType === 'add') {
                        $.messager.alert('提示', '新增成功');
                    } else {
                        $.messager.alert('提示', '修改成功');
                    }
                    $('#addDlg_orderForecastGoal').dialog('close');
                }
            }
        });
    });
    $("#editBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#addDlg_orderForecastGoal').dialog({'title': '修改'});
        if (selected !== null) {
            $('#addForm_orderForecastGoal').form('load', selected);
            $('#addForm_orderForecastGoal').find('[__actType]').val('edit');
            $('#addDlg_orderForecastGoal').dialog('open');
        } else {
            $.messager.alert('提示', '请选择一条数据');
        }
        var actType = $('#addForm_orderForecastGoal').find('[__actType]').val();
//        alert(actType);
        if (actType === 'edit') {
//        	$('#channelCode').textbox('textbox').attr('disabled',true); 
            $('#channelCode').attr("readonly", "readonly");
            $("#channelCode").css("background-color", "#D6D6FF");
        }

//        $("#channelCode").textbox('textbox').attr('maxlength',20);
        $("#channelName").textbox('textbox').attr('maxlength', 50);
        $("#moneyLimit").textbox('textbox').attr('maxlength', 10);
        $("#moneyAlert").textbox('textbox').attr('maxlength', 10);
        $("#moneyLock").textbox('textbox').attr('maxlength', 10);
        $("#remark").textbox('textbox').attr('maxlength', 200);
    });

    /**
     * 删除渠道
     */
    $("#deleteBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        if (selected !== null) {
            var id = selected.id;
            confirm('确定删除？', function (r) {
                if (r == true) {
                    $.post("/pop/removeChannel", {id: id}, function (data) {
                        if (data.text = "success") {
                            $.messager.alert('提示', "删除成功");
                            $('#datagrid_channelForecastGoal').datagrid('reload');
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
    var channelNameData = $("#channelname").textbox("getValue");
    var moneyMinData = $("#moneyMin").textbox("getValue");
    var moneyMaxData = $("#moneyMax").textbox("getValue");
    datagrid = $('#datagrid_channelForecastGoal').datagrid({
        url: "/pop/findChannelList",
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
            channelName: channelNameData,
            moneyMin: moneyMinData,
            moneyMax: moneyMaxData
        },
        frozenColumns: [[{
            field: 'id', 
            checkbox: false,
            hidden:true
        
        }]],
        columns: [[
            {title: '渠道编码 ', field: 'channelCode', sortable: true},
            {title: '渠道名称', field: 'channelName', sortable: true},
            {title: '金额额度', field: 'moneyLimit', sortable: true, align: 'right'},
            {title: '报警金额', field: 'moneyAlert', sortable: true, align: 'right'},
            {title: '锁定金额', field: 'moneyLock', sortable: true, align: 'right'},
            {title: '创建者', field: 'createBy', sortable: true},
            {title: '创建时间', field: 'cTime', sortable: true},
            {title: '更新者', field: 'updateBy', sortable: true},
            {title: '更新时间', field: 'uTime', sortable: true},
            {title: '备注', field: 'remark', sortable: true}
        ]],
    });

});