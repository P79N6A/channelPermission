var datagrid;
var queryParameters;
var LEVEL1_QJ={};
$(function () {
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar',
        striped: true,
        singleSelect: false,
        pagination: true,
        pagePosition: 'bottom',
        columns: [[

            {
                field: 'oper_user_name',
                title: '操作人',
                width: 120,
                align: 'center'
            },
            {
                field: 'log_time',
                title: '操作时间',
                width: 180,
                align: 'center'
            },
            {
                field: 'typeName',
                title: '操作类型',
                width: 120,
                align: 'center'
            },
            {
                field: 'content',
                title: '操作内容',
                width: 750,
                align: 'center'
            }
        ]],
        pageSize: 100,
        pageList: [100,200,300],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);

});
//获取品牌
$(function() {
    var typeJson = [{name:"全部",text:'ALL'},{name:'导入',text:'0'},{name:'修改',text:'1'},
        {name:'新增',text:'2'},{name:'启用',text:'3'},{name:'禁用',text:'4'},{name:'删除',text:'5'}]
    $("#type").combobox({
        data:typeJson,
        valueField:'text',
        textField:'name',
        panelHeight:'auto',
        editable:false,
        value:'ALL'
    });

    //品类
    jQuery.getJSON("/purchase/getCbsCategoryByAuth", function(result){
        var JosnList=[];
        var cbsCategoryJson = {id: "全部", text: "全部"};
        JosnList.push(cbsCategoryJson)
        jQuery.each(result.data,function(i, v){
            var item={id:v,text:v};
            JosnList.push(item);
        });
        $("#category").combobox({
            data:JosnList,
            valueField:'text',
            textField:'text',
            panelHeight:'auto',
            editable:false,
            value:'全部'
        });
    });
    //取得渠道列表
    jQuery.getJSON("/purchase/getChannelsByAuth", function(result){
        var channel = result.data;
        //添加全部
        channel.unshift( {channelCode:'ALL',name:'全部'});
        $("#channel").combobox({
            data:channel,
            valueField:'channelCode',
            textField:'name',
            panelHeight:'auto',
            editable:false,
            value:'ALL'
        });
        $("#jude_way_channel").combobox({
            data:channel,
            valueField:'channelCode',
            textField:'name',
            panelHeight:'auto',
            editable:false,
            value:'ALL'
        });
        $("#gate_way_channel").combobox({
            data:channel,
            valueField:'channelCode',
            textField:'name',
            panelHeight:'auto',
            editable:false,
            value:'ALL'
        });
    });

});

//点击查询
$('#searchBtn').click(function () {
    if($('#log_time_start').datebox('getValue') && $('#log_time_end').datebox('getValue')){
        if($('#log_time_start').datebox('getValue')>$('#log_time_end').datebox('getValue')){
            $.messager.alert('错误','操作日开始不能大于操作日结束','error');
            return false;
        }
    }
    var nameValue = $("#oper_user_name").val();
    //如果是ALL，操作人设为空
    if(nameValue=="ALL"){
        nameValue="";
    }
    var judeValue = $("#jude_way_channel").combobox("getValue");
    //如果是ALL，判断方式-渠道设为空
    if(judeValue=="ALL"){
        judeValue="";
    }
    var gateValue = $("#gate_way_channel").combobox("getValue");
    //如果是ALL，关闸方式-渠道设为空
    if(gateValue=="ALL"){
        gateValue="";
    }
    var category = $("#category").combobox("getValue");
    //如果是ALL，品类设为空
    if(category=="全部"){
        category="";
    }
    var channel = $("#channel").combobox("getValue");
    //如果是ALL，渠道设为空
    if(channel=="ALL"){
        channel="";
    }
    var type = $("#type").combobox("getValue");
    //如果是ALL，操作类型设为空
    if(type=="ALL"){
        type="";
    }
    //生成grid
    datagrid = $('#datagrid').datagrid({
        url: "/logaudit/findLogQueryList",
        fit: true,
        fitColumns: true,
        pagination: true,
        singleSelect: false,
        checkOnSelect:true,
        pageSize: 100,
        pageList: [100,200,300],
        nowrap: false,
        rownumbers: true,
        queryParams: {
            oper_user_name:nameValue,
            jude_way_channel:judeValue,
            log_time_start:$("#log_time_start").datebox('getValue'),
            log_time_end:$("#log_time_end").datebox('getValue'),
            gate_way_channel:gateValue,
            category:category,
            channel:channel,
            type:type,
            order_id:$("#order_id").val(),
            content:$("#content").val()
        },loadFilter: function(data){
            window.total = data.total;
            return data;
        },
        columns: [
            [
                {
                    field: 'oper_user_name',
                    title: '操作人',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'log_time',
                    title: '操作时间',
                    width: 180,
                    align: 'center'
                },
                {
                    field: 'typeName',
                    title: '操作类型',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'content',
                    title: '操作内容',
                    width: 750,
                    align: 'center'
                }
            ]
        ],
        toolbar: '#datagridToolbar'
    });
});


//点击导出
$('#exportall').click(function(){

    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    if(window.total && window.total > 20000){
        $.messager.alert('提示', '最多导出20000条记录，请缩小查询范围！', 'info');
        return;
    }
    $.messager.confirm('确认','确定要导出吗？', function(r){
        if (r){
            $('#paramForm').attr("action", '/logaudit/exportLogAuditList');
            $('#paramForm').submit();
        }
    });
});
