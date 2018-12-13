var datagridOptions_twRegionListGoal = {
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
        {title: '渠道名称 ', field: 'channelName', sortable: false},
        {title: '仓库编码', field: 'warehouseCode', sortable: false},
        {title: '配送仓库', field: 'warehouseName', sortable: false},
        {title: '省', field: 'province', sortable: false},
        {title: '市', field: 'city', sortable: false},
        {title: '县/区', field: 'county', sortable: false},
        {title: '创建者', field: 'createBy', sortable: false},
        {title: '创建时间', field: 'cTime', sortable: false},
        {title: '更新者', field: 'updateBy', sortable: false},
        {title: '更新时间', field: 'uTime', sortable: false},
        {title: '备注', field: 'remark', sortable: false}
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
    var datagrid = $('#datagrid_warehouseListGoal').datagrid(datagridOptions_twRegionListGoal);

    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_warehouse').form('reset');
        $('#addDlg_warehouse').dialog({'title': '新增'});
        $('#addDlg_warehouse').dialog('open');
        $("#a_remark").textbox('textbox').attr('maxlength', 200);
        $("#a_remark").val("");
        $("#a_regionId").val("");
        Test();//-----------------------------------------------------------仓库类型改变时
    });
    $("#addDlgSaveBtn_warehouse").click(function () {
        var channelId = $("#a_channelId").val();
        var regionId = $("#a_regionId").val();
        var warehouseId = $("#a_warehouseId").val();
        var remark = $("#a_remark").val();

        if (channelId == "" || channelId == null) {
            $.messager.alert('提示', '渠道编码不能为空');
            return;
        }
        if (regionId == "" || regionId == null) {
            $.messager.alert('提示', '区域名称不能为空');
            return;
        }
        if (warehouseId == "" || warehouseId == null) {
            $.messager.alert('提示', '配送仓库不能为空');
            return;
        }
        console.info("warehouseId" + warehouseId);
        var params = {
            channelId: channelId,
            regionId: regionId,
            warehouseId: warehouseId,
            remark: remark
        }
        $.post("/warehouse/addTwRegion", params, function (data) {
            if (data == "add") {
                $.messager.alert('提示', '新增成功');
                $('#addDlg_warehouse').dialog('close');
                $("#searchBtn").click();
            } else if (data == "regionIsSame") {
                $.messager.alert('提示', '选中区域均已存在');
            }
        })
    });

    //点击修改
    $("#editBtn_orderForecastGoal").on('click', function () {
        var selected = datagrid.datagrid('getSelected');
        $('#updateDlg_warehouse').dialog({'title': '修改'});
        if (selected !== null) {
            $('#updateForm_warehouse').form('load', selected);
            $("#warehouseId").append("<option value=" + selected.warehouseId + ">" + selected.warehouseName + "</option>");
            $("#regionId").val(selected.regionId);
            $("#regionParentPath").val(selected.regionParentPath);
            $('#updateDlg_warehouse').dialog('open');
        } else {
            $.messager.alert('提示', '请选择一条数据');
        }
        $("#remark").textbox('textbox').attr('maxlength', 200);
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
                    $.post("/warehouse/removeTwRegion", {id: id}, function (data) {
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
        $("#warehouseid").empty();
        $.post("/warehouse/autoLoadPid", {channelId: "", id: ""}, function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#warehouseid").append("<option value=" + data[i].id + ">" + data[i].warehouseName + "</option>");
            }
        });
        $('#paramForm_orderForecastGoal').form('reset');
    });
});

//点击查询
$("#searchBtn").on('click', function (event) {
    var sourceData = $("#source").val();
    var warehouseId = $("#warehouseid").val();
    var county = $("#selectCounty").textbox("getValue");
    datagrid = $('#datagrid_warehouseListGoal').datagrid({
        url: "/warehouse/findtwRegionList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50,100,200],
        queryParams: {
            channelId: sourceData,
            warehouseId: warehouseId,
            county: county
        },
        frozenColumns: [[{
        	 field: 'id',
             checkbox: false,
             hidden:true
        }]],
        columns: [[
            {title: '渠道名称 ', field: 'channelName', sortable: false},
            {title: '仓库编码', field: 'warehouseCode', sortable: false},
            {title: '配送仓库', field: 'warehouseName', sortable: false},
            {title: '省', field: 'province', sortable: false},
            {title: '市', field: 'city', sortable: false},
            {title: '县/区', field: 'county', sortable: false},
            {title: '创建者', field: 'createBy', sortable: false},
            {title: '创建时间', field: 'cTime', sortable: false},
            {title: '更新者', field: 'updateBy', sortable: false},
            {title: '更新时间', field: 'uTime', sortable: false},
            {title: '备注', field: 'remark', sortable: false}
        ]],
    });
});

//添加点击区域的输入框时加载tree
$("#a_county").click(function () {
    $('#oDialog').dialog('open');
    $("#tree").tree({
        url: '/warehouse/tree',//请求路径，id为根节点的id
        checkbox: true,
        cascadeCheck: true,
        lines: true
    });
    $("#a_county").css("background-color", "#FFFFCC");
});
$("#a_county").blur(function () {
    $("#a_county").css("background-color", "#D6D6FF");
})
//获取tree的选中值
function SetCodeValue() {
    var nodes = $('#tree').tree('getChecked');
    var name = '';
    var code = '';
    for (var i = 0; i < nodes.length; i++) {
        if (name != '') name += ',';
        name += nodes[i].text;
        if (code != '') code += ',';
        code += nodes[i].id;
    }
    $("#a_county").val(name);
    $("#a_regionId").val(code);
    $('#oDialog').dialog('close');
}

//修改点击区域的输入框时加载tree
$("#county").click(function () {
    $('#uDialog').dialog('open');
    var three = $("#regionId").val();
    var path = $("#regionParentPath").val();
    var arr = path.split("/");
    var one = arr[1];
    var two = arr[2];
    $("#update_tree").tree({
        url: '/warehouse/tree',//请求路径，id为根节点的id
        cascadeCheck: false,
        onlyLeafCheck: true,
        lines: true,
        onLoadSuccess: function (node, data) {
            var node = $('#update_tree').tree('find', one);
            $(this).tree("expand", node.target); //展开父节点

            var node1 = $('#update_tree').tree('find', two);
            $(this).tree("expand", node1.target); //展开子节点

            var node2 = $('#update_tree').tree('find', three);
            $('#update_tree').tree('select', node2.target);//选中定位项
        },
    });
    $("#county").css("background-color", "#FFFFCC");
});
function SetUpdateCode() {
    var id = $("#update_tree").tree('getSelected').id;
    var text = $("#update_tree").tree('getSelected').text;
    $("#regionId").val(id);
    $("#county").val(text);
    $('#uDialog').dialog('close');
}
//保存修改
$("#updateDlgSaveBtn_warehouse").click(function () {
    var id = $("#id").val();
    var channelId = $("#channelId").val();
    var regionId = $("#regionId").val();
    var warehouseId = $("#warehouseId").val();
    var remark = $("#remark").val();

    if (channelId == "" || channelId == null) {
        $.messager.alert('提示', '渠道编码不能为空');
        return;
    }
    if (regionId == "" || regionId == null) {
        $.messager.alert('提示', '区域名称不能为空');
        return;
    }
    if (warehouseId == "" || warehouseId == null) {
        $.messager.alert('提示', '配送仓库不能为空');
        return;
    }

    console.info("warehouseId" + warehouseId);
    var params = {
        id: id,
        channelId: channelId,
        regionId: regionId,
        warehouseId: warehouseId,
        remark: remark
    }
    $.post("/warehouse/updateTwRegion", params, function (data) {
        if (data == "edit") {
            $.messager.alert('提示', '修改成功');
            $('#updateDlg_warehouse').dialog('close');
            $("#searchBtn").click();
        } else if (data == "regionIsSame") {
            $.messager.alert('提示', '该区域已存在');
        }
    })
});


$("#a_channelId").combobox({
    onChange: function (n, o) {
        Test();//-----------------------------------------------------------仓库类型改变时
    }
});

function autoForUpdate() {
    TestForUpdate();
}

function auto1() {
    Test1();
}
//添加时加载对应仓库
function Test() {
    $("#a_warehouseId").empty();
    var channel_code = $("#a_channelId").combobox("getValue");
    var id = $("#id").val();
    console.info("id=" + id);
    $.post("/warehouse/autoLoadPid", {channelId: channel_code, id: ""}, function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#a_warehouseId").append("<option value=" + data[i].id + ">" + data[i].warehouseName + "</option>");
        }
    })
};

//修改时加载对应仓库
function TestForUpdate() {
    $("#warehouseId").empty();
    var channel_code = $("#channelId").val();
    var id = $("#id").val();
    console.info("id=" + id);
    $.post("/warehouse/autoLoadPid", {channelId: channel_code, id: ""}, function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#warehouseId").append("<option value=" + data[i].id + ">" + data[i].warehouseName + "</option>");
        }
    })
};

//查询时时加载对应仓库
function Test1() {
    $("#warehouseid").empty();
    var channel_code = $("#source").val();
    if (channel_code == null || channel_code == "") {
        $.post("/warehouse/autoLoadPid", {channelId: "", id: ""}, function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#warehouseid").append("<option value=" + data[i].id + ">" + data[i].warehouseName + "</option>");
            }
        });
    } else {
        var id = $("#id").val();
        console.info("id=" + id);
        $.post("/warehouse/autoLoadPid", {channelId: channel_code, id: ""}, function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#warehouseid").append("<option value=" + data[i].id + ">" + data[i].warehouseName + "</option>");
            }
        });
    }
};
// function progress() {
//     var win = $.messager.progress({
//         title: '提示：',
//         msg: '正在处理.......'
//     });
// }
