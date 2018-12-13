
var datagrid =null;
var queryParameters;
var editIndex = undefined;
$.fn.combobox.defaults.icons=[{}]
$(function () {
    // showproducttypesNames();
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        striped: true,
        singleSelect: true,
        pagination: true,
        columns: [[
            // {title: "操作", field: "field0", sortable: false, formatter: go_detail},
            { title: "序号", field: "id", hidden: true },
            { title: "统计分类", field: "typeName", sortable: false },
            { title: "金额", field: "money", sortable: false },
            { title: "数量", field: "countNumber", sortable: false },
        ]],
        pageSize: 50,
        pageList: [50, 100, 200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);

});

$(function () {
    $("#dialog-div").hide();
    dataGrid=   $('#tab').datagrid({
        striped : true, // 隔行变色
        rownumbers : true,
        /*    fit: true,*/
        singleSelect : true,
        fitColunms : true,
        onClickCell : onClickCell,
        columns:[[
            {
                title : '商品类型主键',
                width : 120,
                field : 'id',
                align : 'center',
                sortable: false,
                /*    hidden:true*/
            },
            {
                title : '商品类型名称',
                width : 120,
                field : 'typeName',
                align : 'center',
                sortable: false
            },
            {
                title : '操作',
                width : 120,
                field : 'operation',
                align : 'center',
                sortable: false,
                formatter: function(value,row,index){
                    var id=row.id;
                    var rowIndex=$('#tab').datagrid('getRowIndex',row);
                    var obj=row;
                    return ' <a id="remove" href="#" class="easyui-linkbutton" data-options="iconCls:icon-cancel" onclick="onDelete('+id+')">删除</a>'
                }
            }
        ]],
        striped: true
    });

})
//日期控件
var buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
buttons.splice(1, 0, {
    text: '清除',
    handler: function (target) {
        $(target).datetimebox('setValue', '');
        $(target).combo("hidePanel");
    }
});
$('#addTimeMin').datetimebox({ buttons: buttons });
$('#addTimeMax').datetimebox({ buttons: buttons });

$('#search').click(function () {
    //商品类型名称
    var rows=$('#tab').datagrid('getRows');
    json = JSON.stringify(rows);
    var productTypeIdStr ;
    var idArray = new Array();
    if(json != null) {
        $.each($.parseJSON(json), function(idx, obj) {
            idArray.push(obj.id);
        });
        for (var i=0;i<idArray.length;i++){
            productTypeIdStr= idArray.join(",");
        }

    }
    //写入时间
    if ($('#addTimeMin').datetimebox('getValue') && $('#addTimeMax').datetimebox('getValue')) {
        if ($('#addTimeMin').datetimebox('getValue') > $('#addTimeMax').datetimebox('getValue')) {
            $.messager.alert('错误', '时间区间不正确,请重新选择', 'error');
            return false;
        }
    }
    //提交参数
    queryParameters = {
        id: $("#id").val(),
        producttypesIds: productTypeIdStr,//商品类型id
        addTimeMin:  $('#addTimeMin').datetimebox('getValue' ),//开始时间
        addTimeMax: $('#addTimeMax').datetimebox('getValue' )//结束时间
    };

    datagrid = $('#datagrid').datagrid({
        url: "/order/getTianMaoDataStatistics.html",
        fit: true,
        fitColumns: false,
        pagination: true,
        singleSelect: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        nowrap: true,
        rownumbers: true,
        queryParams: queryParameters,
        columns: [
            [{
                field: 'id',
                title: '序号',
                align: 'center',
                hidden: true
            },
                {
                    title: '统计分类 ',
                    field: 'typeName',
                    align: 'center',
                    width:'200px'
                },
                {
                    title: '金额 ',
                    field: 'money',
                    align: 'center',
                } ,{
                title: '数量 ',
                field: 'countNumber',
                align: 'center'
            }]
        ]

    });

//点击添加商品执行


});
$('#datagrid').datagrid({
    onLoadSuccess: function(data){
        var saleMoney= data.rows[0].money;
        var saleNumber= data.rows[0].countNumber;
        var quanQudaoMoney = data.rows[4].money;
        var quanQudaoNumber = data.rows[4].countNumber;
        if (saleMoney == null){
            saleMoney =0;
        }if (saleNumber == null){
            saleNumber =0;
        }
        if (quanQudaoMoney == null){
            quanQudaoMoney =0;
        }if (quanQudaoNumber == null){
            quanQudaoNumber =0;
        }
        var subtractionNumber = saleMoney-quanQudaoMoney;
        var subtractionNumberFloat = twoFloat(subtractionNumber);

        $('#datagrid').datagrid('insertRow',{
            index: 5,	// 索引从0开始
            row: {
                typeName: '销售额-全渠道',
                money: subtractionNumberFloat,
                countNumber: saleNumber-quanQudaoNumber,
            }
        });


    }
});
function addProductType() {
    $( "#dialog-div" ).dialog({
        conter:true,
        autoOpen: false,
        modal: true,
        height:"80%",
        draggable:false,
        inline:true,
        resizable: true,
        title: '选择商品',
        // top:400,
        close: function( event, ui ) {
            $( "#dialog-div" ).dialog( "close" );
        }

    });

    //加载商品类型表格
    $.ajax({
        url:'/order/getProductTypes.html',
        type:'POST',
        dataType: 'json',
        success: function (data) {
            var body='';
            var div='<div style="margin-top: 10px">';
            var count=0;
            if (data.length==0){
                body+='<div style="text-align: center"><h3 style="color: red;font-weight: bold">条件检索没有数据</h3></div>'
            }
            if (data.length!=0){
                for(var i = 0;i<data.length;i++){
                    if (count==3){
                        div='<div style="margin-top: 10px">';
                        count=0;
                    }
                    var id=data[i].id;
                    var typeName=data[i].typeName;
                    div+='<label class="checkbox-inline" style="width: 30%">' +
                        '<input type="checkbox" name="haha" id="inlineCheckbox1" value="'+id+'">'+typeName+
                        '</label>';
                    if (count==2||i==data.length-1){
                        div+='</div>';
                        body+=div;
                    }
                    count++
                }
            }
            $('#lmsisSb').html(body);
        }
    })
}
function selectProduct() {
    var typeName=$('#typeName').textbox('getValue');
    $.ajax({
        type: 'POST',
        url:'/order/getProductttypesByTypeName',
        data: {
            "typeName": typeName,
        },
        success: function (data) {
            var body='';
            var div='<div style="margin-top: 10px">';
            var count=0;
            if (data.length==0){
                body+='<div style="text-align: center"><h3 style="color: red;font-weight: bold">条件检索没有数据</h3></div>'
            }
            if (data.length!=0){
                for(var i = 0;i<data.length;i++){
                    if (count==3){
                        div='<div style="margin-top: 10px">';
                        count=0;
                    }
                    var id=data[i].id;
                    var typeName=data[i].typeName;
                    div+='<label class="checkbox-inline" style="width: 30%">' +
                        '<input type="checkbox" name="check" id="inlineCheckbox1" value="'+id+'">'+typeName+
                        '</label>';
                    if (count==2||i==data.length-1){
                        div+='</div>';
                        body+=div;
                    }
                    count++
                }
            }
            $('#lmsisSb').html(body);
        }
    })
}
function Determine() {
    var checkbox=$("input[type='checkbox']");
    var mycars=new Array();
    for (var i=0;i<checkbox.length;i++){
        if (checkbox[i].checked){
            mycars.push(checkbox[i].value);
        }
    }
    if (mycars.length==0){
        $.messager.alert('提示',"至少要选择一个商品")
        return;
    }
    $( "#dialog-div").dialog( "close" );
    $.ajax({
        type: 'POST',
        url:'/order/appendProducttypes',
        data:{
            data:JSON.stringify(mycars),
        },
        success: function (data) {
            for (var i=0;i<data.length;i++){
                dataGrid.datagrid('appendRow',data[i]);
            }
        }
    })
}

function onClickCell(index, field){
    if (endEditing()){
        $('#tab').datagrid('selectRow', index)
            .datagrid('editCell', {index:index,field:field});
        editIndex = index;
    }
}
//删除dataGird表格中的商品
function onDelete(id) {
    var rows = $('#tab').datagrid("getRows");
    for (var i=0;i<rows.length;i++){
        if (id==rows[i].id){
            $('#tab').datagrid('deleteRow', i);
        }
    }
    dataGrid.datagrid("loadData", rows);
    dataGrid.datagrid('reload');
}

$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});
function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#tab').datagrid('validateRow', editIndex)){
        $('#tab').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function twoFloat(number){
    number=Math.round(parseFloat(number)*100)/100;
    var number1=number.toString().split(".");
    if(number1.length==1){
        number=number.toString()+".00";
        return number;
    }
    if(number1.length>1){
        if(number1[1].length<2){
            number=number.toString()+"0";
        }
        return number;
    }
}