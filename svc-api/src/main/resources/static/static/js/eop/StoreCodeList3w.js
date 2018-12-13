var queryParameters;
var datagrid;

$(function () {
    $('#add_Store').dialog('close');
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            {title: "序号", field: "id", hidden: true},
            {title: "仓库名称", field: "storeName", sortable: false},
            {title: "海尔仓库编码", field: "haierStoreCode",sortable: false},
            {title: "菜鸟仓库编码", field: "cainiaoStoreCode", sortable: false},
        ]],
        pageSize: 50,
        pageList: [50,100,200],
        rownumbers: true
    }
    $('#datagrid').datagrid(datagrid);
    searchs();

});
    var searchs =function() {
        queryParameters ={
            storeName:$("#storeName").val(),
            haierStoreCode:$("#haierStoreCode").val(),
            cainiaoStoreCode:$('#cainiaoStoreCode').val(),
        };
        //生成grid
        datagrid = $('#datagrid').datagrid({
            url: "/transferorder3w/StoreCode_query",
            fit: true,
            fitColumns: true,
            pagination: true,
            singleSelect: true,
            checkOnSelect:true,
            pageSize: 50,
            pageList: [50,100,200],
            nowrap: true,
            rownumbers: true,
            queryParams: queryParameters,
            columns: [[
                {field: 'id',title: '序号',align: 'center',hidden:true},
                {field: 'storeName',title: '仓库名称',sortable: false,align: 'center'},
                {field: 'haierStoreCode',title: '海尔仓库编码',sortable: false,align: 'center'},
                {field: 'cainiaoStoreCode',title: '菜鸟仓库编码',sortable: false,align: 'center'}
            ]],
            toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure'
        });
        $('#datagrid').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
    };

    var create = function () {
        jQuery.ajax({
            url: "/transferorder3w/create_Store_Code",
            type:"POST",
            data: {
                "StoreName": $("#store_Name").textbox("getValue"),
                "haierStoreCode": $("#haier_Store_Code").textbox("getValue"),
                "cainiaoStoreCode": $("#cainiao_Store_Code").textbox("getValue"),
            },success: function (data) {
                var msg = data;
                if(msg=="null"||msg==null){
                    $.messager.alert('提示', '创建仓库编码对照信息成功', 'info');
                    $('#add_Store').dialog('close');
                    searchs();
                }else{
                     alert(msg);
                }
            },
        });
    };

    var deleteS = function (){
        var row = $('#datagrid').datagrid('getSelected');
        confirm('错误的删除会导致业务数据异常，确定要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    url      : "/transferorder3w/delete_Store_Code",
                    type     : 'post',
                    dataType : 'json',
                    data     : {"id":row.id},
                    success  : function(data) {
                        var msg = data;
                        if(msg==null || msg=="null"){
                            $.messager.alert('提示', '删除仓库编码对照信息成功', 'info');
                            searchs();
                        }else{
                        alert(msg);
                        }
                    }
                });
            }
        })
    };
    var updates = function () {
     var row = $('#datagrid').datagrid('getSelected');
        jQuery.ajax({
            url: "/transferorder3w/update_Store_Code",
            type:"POST",
            data: {
                "id":row.id,
                "storeName": $("#store_Name").textbox("getValue"),
                "haierStoreCode": $("#haier_Store_Code").textbox("getValue"),
                "cainiaoStoreCode": $("#cainiao_Store_Code").textbox("getValue")
            },success: function (data) {
                var msg = data;
                if(msg==null||msg=="null"){
                    $.messager.alert('提示', '修改仓库编码对照信息成功', 'info');
                    $('#add_Store').dialog('close');
                    searchs();
                }else{
                     alert(msg);
                }
            },
        });
    };

$('#add').click(function () {
    $(".easyui-textbox").textbox('setValue','');
    $('#addForm_warehouse').form('reset');
    $('#addForm_warehouse').find('[__actType]').val('add');
    $('#add_Store').dialog({'title': '新增'});
    $('#add_Store').dialog('open');
});

$('#store_Pre').click(function () {

    if($("#store_Name").textbox("getValue") == null || $("#store_Name").textbox("getValue") == ""){
        $.messager.alert('提示', '请输入仓库名称', 'err');
    } else 	if($("#haier_Store_Code").textbox("getValue") == null || $("#haier_Store_Code").textbox("getValue") == ""){
        $.messager.alert('提示', '请输入海尔仓库编码', 'err');
    } else 	if($("#cainiao_Store_Code").textbox("getValue") == null || $("#cainiao_Store_Code").textbox("getValue") == ""){
        $.messager.alert('提示', '请输入菜鸟仓库编码', 'err');
    } else {
    var actType = $('#addForm_warehouse').find('[__actType]').val();
        if(actType=='add'){
            create();
        }else if(actType=="edit"){
            updates();
        }else{
            $.messager.alert('提示', '异常操作！请刷新页面从新操作', 'err');
        }

    }
});




$('#store_Close').click(function () {
    $("#add_Store").dialog('close');
});

//点击查询
$('#search').click(function(){
    searchs();
});

$('#delete').click(function () {
    var row = $('#datagrid').datagrid('getSelected');
    if(row==null){
        $.messager.alert('提示', '您没有选择要删除的数据', 'err');
    } else {
        deleteS();
    }
});

$("#update").on('click', function () {
    var row = datagrid.datagrid('getSelected');
    $('#addForm_warehouse').form('reset');
    $('#add_Store').dialog({'title': '修改'});
    if (row !== null) {
        $('#addForm_warehouse').form('load', row);
        $('#addForm_warehouse').find('[__actType]').val('edit');
        $('#add_Store').dialog('open');
    } else {
        $.messager.alert('提示', '请选择一条数据');
        return ;
    }

    $("#store_Name").textbox('setText', row.storeName);
    $("#store_Name").textbox('setValue', row.storeName);
    $("#haier_Store_Code").textbox('setText',row.haierStoreCode);
    $("#haier_Store_Code").textbox('setValue',row.haierStoreCode);
    $("#cainiao_Store_Code").textbox('setText',row.cainiaoStoreCode);
    $("#cainiao_Store_Code").textbox('setValue',row.cainiaoStoreCode);

});