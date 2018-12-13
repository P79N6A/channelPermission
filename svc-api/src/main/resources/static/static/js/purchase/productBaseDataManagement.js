var datagrid;
var queryParameters;
var LEVEL1_QJ={};
$(function () {
    datagrid = {
        fit: true,
        fitColumns: false,
        toolbar: '#datagridToolbar',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[
            {
                field: 'materialCode',
                title: '物料编码',
                width: 140,
                align: 'center'
            },
            {
                field: 'materialDescription',
                title: '型号',
                width: 220,
                align: 'center'
            },
            {
                field: 'departmentName',
                title: '产品组',
                width: 100,
                align: 'center'
            },
            {
                field: 'cbsCategory',
                title: '品类',
                width: 100,
                align: 'center'
            },
            {
                field: 'status',
                title: '状态',
                width: 80,
                align: 'center'
            },
            {
                field: 'modifier',
                title: '最后更新人',
                width: 80,
                align: 'center'
            },
            {
                field: 'productType',
                title: '产品类型',
                width: 60,
                align: 'center'
            },
            {
                field: 'lengthNumber',
                title: '长度',
                width: 60,
                align: 'center'
            },
            {
                field: 'widthNumber',
                title: '宽度',
                width: 60,
                align: 'center'
            },
            {
                field: 'highNumber',
                title: '高度',
                width: 60,
                align: 'center'
            },
            {
                field: 'grossWeightNumber',
                title: '总重量',
                width: 60,
                align: 'center'
            },
            {
                field: 'weightUnit',
                title: '单位',
                width: 60,
                align: 'center'
            },
            {
                field: 'deleteFlag',
                title: '删除标志',
                width: 60,
                align: 'center'
            },
            {
                field: 'created',
                title: '创建时间',
                width: 220,
                align: 'center'
            },
            {
                field: 'lastUpd',
                title: '最后更新时间',
                width: 220,
                align: 'center'
            },
            {
                field: 'proBand',
                title: '品牌',
                width: 60,
                align: 'center'
            },
            {
                field: 'isAutoUpdate',
                title: '是否同步',
                width: 60,
                align: 'center'
            },{
                field: 'price',
                title: '价格',
                width: 80,
                align: 'center'
            },
            {field:'operation'
                ,title:'操作'
                ,width:160
                ,align:'center'
            }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);


});

$(function () {
    //取得产品组
    jQuery.getJSON("/purchase/getProductGroupByAuth", function(result){
        var productgroup = result.data;
        productgroup.unshift({value:'ALL',valueMeaning:'全部'});
        $("#product_group_id").combobox({
            data:result.data,
            valueField:'value',
            textField:'valueMeaning',
            panelHeight:'300',
            editable:false,
            value:'ALL'
        });
    });

//取得产品组
    jQuery.getJSON("/purchase/getProductGroupByAuth", function(result){
        var productgroup = result.data;
        productgroup.unshift({value:'ALL',valueMeaning:'全部'});
        $("#departmentInsert").combobox({
            data:result.data,
            valueField:'value',
            textField:'valueMeaning',
            panelHeight:'300',
            editable:false,
            value:'ALL'
        });
    });

    jQuery.getJSON("/purchase/getProductGroupByAuth", function(result){
        var productgroup = result.data;
        productgroup.unshift({value:'ALL',valueMeaning:'全部'});
        $("#dialog-form-input-department").combobox({
            data:result.data,
            valueField:'value',
            textField:'valueMeaning',
            panelHeight:'300',
            editable:false,
            value:'ALL'
        });
    });

    jQuery.getJSON("/purchase/getCbsCategoryByAuth", function (result) {
        var JosnList = [];
        var cbsCategoryJson = {id: "ALL", text: "全部"};
        JosnList.push(cbsCategoryJson)
        jQuery.each(result.data, function (i, v) {
            var item = {id: v, text: v}
            JosnList.push(item)
        });
        $("#category_id").combobox({
            data: JosnList,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            editable: false,
            value: 'ALL'
        });
    });



});


$("#n").click(function () {
    /*	$(this).dialog('close');*/
    $("#manualDiv").window('close');
});

$(function(){
    $("#manualDiv").panel('close');
});
function formatOper(val,row,index){
    return '<a href="#" class="button-edit" onclick="onEdit('+index+')">修改</a>'+
        '&nbsp;&nbsp;'+'<a href="#" onclick="editUser('+index+')">手动同步</a>';
}
function onEdit(index){
    $('#datagrid').datagrid('selectRow',index);// 关键在这里
    var row = $('#datagrid').datagrid('getSelected');

    $( "#dialog-form-input-id" ).val( row.id );
    $("#materialCodeLabel").textbox('setValue',row.materialCode);
    $( "#dialog-form-input-materialDescription" ).textbox( 'setValue',row.materialDescription );
    $("#cbsCategoryLabel").textbox('setValue',row.cbsCategory);
    $("#dialog-form-input-department").combobox('setValue', row.department );

    $("#modifier").textbox('setValue',row.modifier);
    $("#lastUpd").textbox('setValue',row.lastUpd);
    $("#dialog-form-input-status").combobox('setValue', row.status );
    $("#dialog-form-input-isAutoUpdate").combobox('setValue', row.isAutoUpdate );

    $("#price").textbox('setValue',row.price);

    return $('#dialog-message').dialog('open').dialog('setTitle', '编辑产品基础数据');
}


function editUser(index){

    $('#datagrid').datagrid('selectRow',index);// 关键在这里
    var row = $('#datagrid').datagrid('getSelected');

    jQuery.getJSON("/purchaseAllocation/manualSyncMdm?materialCode="+row.materialCode, function(result){
        alert(result.message);
        $('#datagrid').datagrid('reload');

    });
}


$("#btnInsert").on('click', function (event) {
    $('#dialog-insert-form').form('reset');
    $('#dialog-insert-form').find('[__actType]').val('add');
    $('#dialog-insert').dialog({'title': i18n['message.act.add']});

    $("#dialog-insert-form").form("clear");
    return $('#dialog-insert').dialog('open').dialog('setTitle', '增加产品基础数据');
});
function isDecimal(price){
    var regx = /(^[1-9]{1}[0-9]{0,5}$)|((^[1-9]{1}[0-9]{0,5})(\.[0-9]{1,2}$))|((^0{1})(\.[0-9]{1,2})$)|^0{1}$/;
    return regx.exec(price);
}

$("#editBtn").on('click', function () {
    if($("#price").val().trim() != "" && !isDecimal($("#price").val().trim())){
        alert('价格输入格式有误!');
        return false;
    }else{
        jQuery.ajax({
            url: "/purchaseAllocation/saveItemBase",
            data: $( "#dialog-form" ).serialize(),
            type: "POST",
            success: function(result) {
                if(result.success == true){
                    alert("修改成功");
                    $('#dialog-message').dialog('close');
                    $('#datagrid').datagrid('reload');
                }else
                    alert(result.message);
            }
        });
    }
})

$("#addDlgSaveBtn").on('click', function () {
    if( $("#materialCodeLabelInsert").val() == ""){
        alert('物料编码不能为空!');
        return false;
    }else if($("#materialCodeLabelInsert").val().length>60){
        alert('物料编码长度不能超过60位!');
        return false;
    }else if($("#materialDescriptionInsert").val().length>400){
        alert('物料名称长度不能超过400位!');
        return false;
    }else if($("#priceInsert").val().trim() != "" && !isDecimal($("#priceInsert").val().trim())){
        alert('价格输入格式有误!');
        return false;
    }else{
        jQuery.ajax({
            url: "/purchaseAllocation/insertItemBase",
            data: $( "#dialog-insert-form" ).serialize(),
            type: "POST",
            success: function(result) {
                if(result.success == true){
                    alert("添加成功");
                    $('#dialog-insert').dialog('close');
                    $('#datagrid').datagrid('reload');
                }else
                    alert(result.message);
                $('#datagrid').datagrid('reload');
            }
        });
    }
})

$('#export').click(function () {

    var category_id = $("#category_id").combobox("getValue");
    var product_group_id = $("#product_group_id").combobox("getValue");
    var materials_id = $("#materials_id").val();
    var materials_desc = $("#materials_desc").val();

    if (!datagrid) {
        $.messager.alert('提示', '请查询！', 'info');
        return;
    }
    /*    if (datagrid) {
            //获得所有行
            var getItems = $('#datagrid').datagrid('getRows');
            var exportData = new Array();
            //将订单号存入Array
            $.each(getItems, function (index, item) {
                exportData.push(item.order_id);
            });
            $("#exportData").val(JSON.stringify(exportData));
        }*/
    $.messager.confirm('确认', '确定要导出数据吗？', function (r) {
        if (r) {
            $("#exportData").val('');
            $('#filterForm').attr("action", 'exportProductBaseList.export');
            $('#filterForm').submit();
        }
    });
});


//返回查询页面
$("#close").click(function() {
    $("#passForm").form("clear");
    $("#passDiv").window('close');
    $('#oms_order_id_save').val("");
    $("#passForm").form("clear");
});



//点击查询
$('#search').click(function () {
    var product_group_id = $("#product_group_id").combobox("getValue");
    //如果是ALL，产品组设为空
    if (product_group_id == "ALL") {
        product_group_id = "";
    }
    var type = $("#type").combobox("getValue");
    //如果是ALL，渠道设为空
    if (type == "全部") {
        type = "";
    }

    var status = $("#status").combobox("getValue");
    //如果是ALL，订单类型设为空
    if (status == "3") {
        status = "";
    }
    var update = $("#update").combobox("getValue");
    //如果是ALL，订单类型设为空
    if (update == "2") {
        update = "";
    }
    var category_id = $("#category_id").combobox("getValue");
    //如果是ALL，品类设为空
    if (category_id == "ALL") {
        category_id = "";
    }
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url : "/purchaseAllocation/findProductBaseData",
        fit:true,
        pagination: true,
        pageSize:100,
        pageList:[100,200,300],
        nowrap: false,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        rownumbers: true,
        onLoadSuccess : function () {
            $(this).datagrid("fixRownumber");
        },
        queryParams: {
            type: type,
            product_group_id: product_group_id,
            materials_id: $("#materials_id").val(),
            materials_desc: $("#materials_desc").val(),
            status: status,
            update:update,
            category_id: category_id
        },
        loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        columns : [ [ {
            field: 'materialCode',
            title: '物料编码',
            width: 140,
            align: 'center'
        },
            {
                field: 'materialDescription',
                title: '型号',
                width: 220,
                align: 'center'
            },
            {
                field: 'departmentName',
                title: '产品组',
                width: 100,
                align: 'center'
            },
            {
                field: 'cbsCategory',
                title: '品类',
                width: 100,
                align: 'center'
            },
            {
                field: 'status',
                title: '状态',
                width: 80,
                formatter : function(value){
                    if(value==0){
                        return "初始化";
                    }else if(value==1){
                        return "使用中";
                    }else{
                        return "停用中";
                    }
                },
                align: 'center'
            },
            {
                field: 'modifier',
                title: '最后更新人',
                width: 80,
                align: 'center'
            },
            {
                field: 'productType',
                title: '产品类型',
                width: 60,
                align: 'center',
                formatter:function(value,rowData,rowIndex){
                    if(value=='T01') return '整机';
                    if(value=='T04') return '套机';
                    if(value=='T05') return '内机';
                    if(value=='T06') return '外机';
                    if(value=='Z09') return '支架';
                    if(value=='Z26') return '面板';
                    if(value=='Z27') return '壳体';
                    if(value=='T02') return '散件';
                    if(value=='T03') return '散件';
                    if(value=='T07') return '服务';
                    if(value=='Z02') return '烟管';
                    if(value=='Z03') return '集热器';
                    if(value=='Z04') return '控制器';
                    if(value=='Z05') return '膨胀罐';
                    if(value=='Z06') return '水箱';
                    if(value=='Z07') return '太阳能';
                    if(value=='Z08') return '循环泵';
                    if(value=='Z11') return '电磁阀';
                    if(value=='Z12') return '电动阀';
                    if(value=='Z13') return '阀门';
                    if(value=='Z14') return '附件';
                    if(value=='Z17') return '换热器';
                    if(value=='Z19') return '控制柜';
                    if(value=='Z20') return '水泵';
                    if(value=='Z21') return '桶托';
                    if(value=='Z22') return '真空管';
                    if(value=='Z25') return '采暖炉';
                    return '';
                }
            },
            {
                field: 'lengthNumber',
                title: '长度',
                width: 60,
                align: 'center'
            },
            {
                field: 'widthNumber',
                title: '宽度',
                width: 60,
                align: 'center'
            },
            {
                field: 'highNumber',
                title: '高度',
                width: 60,
                align: 'center'
            },
            {
                field: 'grossWeightNumber',
                title: '总重量',
                width: 60,
                align: 'center'
            },
            {
                field: 'weightUnit',
                title: '单位',
                width: 60,
                align: 'center'
            },
            {
                field: 'deleteFlag',
                title: '删除标志',
                width: 60,
                align: 'center'
            },
            {
                field: 'created',
                title: '创建时间',
                width: 220,
                formatter : function(value) {
                    var date = new Date(value);
                    var year = date.getFullYear().toString();
                    var month = (date.getMonth() + 1);
                    var day = date.getDate().toString();
                    var hour = date.getHours().toString();
                    var minutes = date.getMinutes().toString();
                    var seconds = date.getSeconds().toString();
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
                    return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
                },
                align: 'center'
            },
            {
                field: 'lastUpd',
                title: '最后更新时间',
                width: 220,
                formatter : function(value) {
                    var date = new Date(value);
                    var year = date.getFullYear().toString();
                    var month = (date.getMonth() + 1);
                    var day = date.getDate().toString();
                    var hour = date.getHours().toString();
                    var minutes = date.getMinutes().toString();
                    var seconds = date.getSeconds().toString();
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
                    return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
                },
                align: 'center'
            },
            {
                field: 'proBand',
                title: '品牌',
                width: 60,
                align: 'center'
            },
            {
                field: 'isAutoUpdate',
                title: '是否同步',
                width: 60,
                formatter : function(value){
                    if(value==0){
                        return "否";
                    }else{
                        return "是";
                    }
                },
                align: 'center'
            },{
                field: 'price',
                title: '价格',
                width: 80,
                formatter : function(value){
                    if(value==null){
                        return '';
                    }
                    return value;
                },
                align: 'center'
            },
            {field:'operation'
                ,title:'操作'
                ,width:160
                ,align:'center',
                formatter:formatOper
            }
        ] ],
        toolbar: '#datagridToolbar'
    });
});

