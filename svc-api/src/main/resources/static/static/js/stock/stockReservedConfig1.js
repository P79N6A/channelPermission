var datagrid_WwwHpRecords = {
    fit: true,//自适应
    singleSelect: true,//多选
    url: '',
    columns: [[
        {
            width : '5%',
            title : '操作',
            field : 'id',
            hidden:true
        },
        {
            width : '8%',
            title : '操作',
            field : 'allowUpdate',
            formatter: function(value,row,index){
                var allowUpdate=row.allowUpdate;
                var id=row.id;
                if (allowUpdate==1){
                    return '<a href="#" onclick="getConfig(\'' + row.id+ '\')">修改</a>  '
                }else {
                    return '修改';
                }
            }
        },{
            width : '15%',
            title : '渠道编码',
            field : 'channelCode'
        }, {
            width : '20%',
            title : '单号',
            field : 'ref'
        }, {
            width : '15%',
            title : '状态',
            field : 'status',
            formatter: function(value,row,index){
                var isrece="";
                if(row.status=='0'){
                    isrece="停用";
                }
                if (row.status=='1'){
                    isrece="启用";
                }
                return isrece ;
            }
        },{
            width : '20%',
            title : '锁定时间(小时)',
            field : 'lockHours'
        },
        {
            width : '20%',
            title : '更新时间',
            field : 'updateTime'
        }
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    striped: true,
    autoRowHeight: true,
    nowrap: true,
    pagination: true,
    rownumbers: true,
    pageSize: 50,
    pageList: [50, 100, 200],
}
$(function () {
    var datagrid = $('#datagrid_WwwHpRecords').datagrid(datagrid_WwwHpRecords);
    $('#status').combobox({
        readonly:true  //是否可编辑
    });
})
//查询
function xxx ()  {
    datagrid = $('#datagrid_WwwHpRecords').datagrid({
        url: "/configure/getStockReservedList",
        fit: true,
        singleSelect: true,//多选
        striped: true,
        autoRowHeight: true,
        nowrap: false,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        queryParams: {
            schannelCode : $("#channelCode").val(),//渠道编号
            sref : $("#ref").val(),//单号
            sstatus :$("#statusValue").combobox("getValue"),//状态
        },
        columns: [[
            {
                width : '5%',
                title : '操作',
                field : 'id',
                hidden:true
            },
            {
                width : '5%',
                title : '操作',
                field : 'allowUpdate',
                formatter: function(value,row,index){
                    var allowUpdate=row.allowUpdate;
                    var id=row.id;
                    if (allowUpdate==1){
                        return '<a href="#" onclick="getConfig(\'' + row.id+ '\')">修改</a>  '
                    }else {
                        return '修改';
                    }
                }
            },{
                width : '15%',
                title : '渠道编码',
                field : 'channelCode'
            }, {
                width : '20%',
                title : '单号',
                field : 'ref'
            }, {
                width : '15%',
                title : '状态',
                field : 'status',
                formatter: function(value,row,index){
                    var isrece="";
                    if(row.status=='0'){
                        isrece="停用";
                    }
                    if (row.status=='1'){
                        isrece="启用";
                    }
                    return isrece ;
                }
            },{
                width : '20%',
                title : '锁定时间(小时)',
                field : 'lockHours'
            },
            {
                width : '15%',
                title : '更新时间',
                field : 'updateTime'
            }
        ]],
    });
};
function getConfig ( id ) {
    $("#dialog-message .modal-body p").text("正在加载...");
    var title;
    if ( id != '') {
        title= '修改配置';
    } else {
        title= '新增配置';
    }
    jQuery.ajax({
        url: "/configure/getStockReservedRow?id="+id,// 提交的页面
        type: "GET",
        success: function(data) {
            var id=data.id;
            var channelCode=data.channelCode;
            var status=data.status;
            var lockHours=data.lockHours;
            var createTime=data.createTime;
            var ref=data.ref;
            if (channelCode!=''||channelCode!=null){
                $("#channelCode1").val(channelCode)
            }
            if (id!=''||id!=null){
                $("#id").val(id)
            }
            if (lockHours!=''||lockHours!=null){
                $("#lockHours").val(lockHours)
            }
            if (status!=''||status!=null){
            }
            if (status==1||status=='1'){
                /*$("#status").val(status)*/
                document.getElementById("status1").options[1].selected = true;
            }
            if (status==0||status=='0'){
                document.getElementById("status1").options[0].selected = true;
            }
            if (ref!=''||ref!=null){
                $("#ref1").val(ref)
            }
            if (createTime!=''||createTime!=null){
                $("#createTime").val(createTime)
            }
            $('#addForm_dmmtlPbcsMtlMeasure').form('reset');
            $('#addForm_dmmtlPbcsMtlMeasure').find('[__actType]').val('add');
            $('#addDlg_dmmtlPbcsMtlMeasure').dialog({'title': title});
            $('#addDlg_dmmtlPbcsMtlMeasure').dialog('open');
        }
    });
    return false;
}
function saveConfig () {
    var channelCode = $("#channelCode1").val();
    var lockHours = $("#lockHours").val();
    var ref1 = $("#ref1").val();
    if (ref1.length>29){
        alert("单号参数过长");
        return false;
    }
    if ( jQuery.trim(channelCode) == "" ) {
        alert("渠道不能为空");
        return false;
    }
    if (channelCode.length>9){
        alert("渠道参数过长");
        return false;
    }
    if ( jQuery.trim(lockHours) == "" ) {
        alert("锁定时间不能为空");
        return false;
    }
    var regex = /^[1-9]*[1-9][0-9]*$/;
    if ( !regex.test(lockHours)) {
        alert("锁定时间必须是整数");
        return false;
    }
    if ( lockHours.length > 10 ) {
        alert("输入整数太大");
        return false;
    }
    $("#saveBtn").hide();
    $("#showBtn").show();
    jQuery.ajax({
        url: "/configure/saveReservedConfig",   // 提交的页面
        data: $('#configform').serialize(), // 从表单中获取数据
        type: "GET",                   // 设置请求类型为"POST"，默认为"GET"
        success: function(data) {
            alert(data.message);
            if( data.message=='操作成功'){
                $('#addDlg_dmmtlPbcsMtlMeasure').dialog('close');
                xxx();
            }
        }
    });

}

/*
* 重置
* */
$('#redo').click(function() {

        $('#statusValue').combobox('setValue', "-1");

        $('#channelCode').textbox('setValue', "");
        $('#ref').textbox('setValue', "");
    }
)
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
        $('#datagrid_WwwHpRecords').datagrid('resize');
    }, 500);
}