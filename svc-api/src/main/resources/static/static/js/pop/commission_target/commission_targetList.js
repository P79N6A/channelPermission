/**
 * pop模块佣金目标表
 * 孙玉凯
 * Created by PC011 on 2017/11/21.
 */


var gloid;
var id;
/***
 * 自动加载
 */
var myDate = new Date();
var near = myDate.getFullYear(); //获取完整的年份(4位,1970-????)


$('#cpolicyYear').val(near);

function getDaysInOneMonth(year, month){
    month = parseInt(month,10);
    var d= new Date(year,month,0);  //这个是都可以兼容的
    var date = new Date(year+"/"+month+"/0")   //IE浏览器可以获取天数，谷歌浏览器会返回NaN
    return d.getDate();
}

$('#gridView').datagrid({
    striped: true, // 隔行变色
    rownumbers: true,
    fit: true,
    pagination: true,
    singleSelect: true,
    fitColunms: true,
    idField: 'id',
    remoteSort: false,
    showFooter: true,
    frozenColumns:[[    
        {
            title: '渠道',
            width: 70,
            field: 'channelName',
            align: 'center'
            , sortable: true,
            rowspan:2
        },
        {
            title: '品牌',
            width: 30,
            field: 'brandName',
            align: 'left'
            , sortable: true,
            rowspan:2
            
        },
        {
            title: '品类',
            width: 50,
            field: 'categoryName',
            align: 'left'
            , sortable: true,
            rowspan:2
        },
        {
            title: '政策年度',
            width: 60,
            field: 'policyYear',
            align: 'left'
            , sortable: true,
            rowspan:2
        }
        ],[]],    
        columns: [[
        {
            title: '1月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '2月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '3月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '4月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '5月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '6月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '7月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '8月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '9月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '10月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '11月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        {
            title: '12月',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        ,
        
        {
            title: '年度',
            width: 130,
            align: 'left'
            , sortable: true,
            colspan:2
        }
        
        ],[
        
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue1',
            align: 'left'
            , sortable: true
        }
        ,
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy1',
            align: 'left'
            , sortable: true
        }
        ,
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue2',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy2',
            align: 'left'
            , sortable: true
        }
        ,
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue3',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy3',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue4',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy4',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue5',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy5',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue6',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy6',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue7',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy7',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue8',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy8',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue9',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy9',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue10',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy10',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue11',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy11',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValue12',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'targetPolicy12',
            align: 'left'
            , sortable: true
        },
        {
            title: '目标(万元)',
            width: 65,
            field: 'targetValueSum',
            align: 'left'
            , sortable: true
        },
        {
            title: '政策(%)',
            width: 50,
            field: 'yearStandardReward',
            align: 'left'
            , sortable: true
        },
        {
            title: 'channelId',
            width: 1,
            field: 'channelId',
            align: 'left'
            , sortable: true,
            hidden:true
        },
        {
            title: 'brandId',
            width: 1,
            field: 'brandId',
            align: 'left'
            , sortable: true,
            hidden:true
        },
        {
            title: 'categoryId',
            width: 1,
            field: 'categoryId',
            align: 'left'
            , sortable: true,
            hidden:true
        },
        {
            title: 'channelType',
            width: 1,
            field: 'channelType',
            align: 'left'
            , sortable: true,
            hidden:true
        }
        
    ]],
    toolbar: '#datagridToolbar_orderForecastGoal',
    onClickRow: function (rowIndex, row) {
        $("#id").val(row.id);
    }
});
//表格and分页js加载
$(function () {
    $('#gridView').datagrid('getPager').pagination({
        total: 0,
        pageSize: 10,
        pageList: [50,100,200],
        showRefresh: false,
        displayMsg: '显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function (pageNo, pageSize) {
            var start = (pageNo - 1) * pageSize;//页码分页自增
            var categoryId = $('#ccategoryId').combobox('getValue');
            var policyYear = $('#cpolicyYear').val();
            var brandId = $('#cbrandId').combobox('getValue');
            var channelType = $('#cchannelType').combobox('getValue');
            var cchannelId = $('#cchannelId').combobox('getValue');
            if(categoryId == '全部'){
                categoryId='';
            }
            if(brandId == '全部'){
                brandId='';
            }
            if(cchannelId == '全部'){
                cchannelId='';
            }



            var options = $('#gridView').datagrid('getPager').data("pagination").options;
            $.ajax({
                type: 'POST',
                async: true,
                url: '/pop/commission_target/commissionListF',
                data: {

                	 page: options.pageNumber,
                     rows: options.pageSize,
                     categoryId:categoryId,
                     policyYear:policyYear,
                     brandId:brandId,
                     channelType:channelType,
                     channelId:cchannelId
                },
                success: function (data, textStatus) {
                    $('#gridView').datagrid('loadData', data);

                    //页码跟随分页进行-loadData后面执行
                    var rowNumbers = $('.datagrid-cell-rownumber');
                    $(rowNumbers).each(function (index) {
                        var row = parseInt($(rowNumbers[index]).html()) + parseInt(start);
                        $(rowNumbers[index]).html("");
                        $(rowNumbers[index]).html(row);
                    });

                }
            });

        }
    });
});
//品类
$("#ccategoryId").combobox({
    url: '/pop/product/departmentproducttypeList',
    valueField: "id",
    textField: "typeName",
    required: false,
    editable: true,
    hasDownArrow: true,
    filter: function (q, row) {
        var opts = $(this).combobox('options');
        return row[opts.textField].indexOf(q) >= 0;
    },
    onSelect: function (record) {
    },
    onUnselect: function () {
    }
});
//品牌
$("#cbrandId").combobox({
    url: '/pop/product/brandList',
    valueField: "id",
    textField: "typeName",
    required: false,
    editable: true,
    hasDownArrow: true,
    filter: function (q, row) {
        var opts = $(this).combobox('options');
        return row[opts.textField].indexOf(q) >= 0;
    },
    onSelect: function (record) {
    },
    onUnselect: function () {
    }
});
//品牌
$("#brandId").combobox({
    url: '/pop/product/brandList',
    valueField: "id",
    textField: "typeName",
    required: true,
    editable: false,
    hasDownArrow: true,
    filter: function (q, row) {
        var opts = $(this).combobox('options');
        return row[opts.textField].indexOf(q) >= 0;
    },
    onSelect: function (record) {
    },
    onUnselect: function () {
    }
});
//品类
$("#categoryId").combobox({
    url: '/pop/product/departmentproducttypeList',
    valueField: "id",
    textField: "typeName",
    required: true,
    editable: false,
    hasDownArrow: true,
    filter: function (q, row) {
        var opts = $(this).combobox('options');
        return row[opts.textField].indexOf(q) >= 0;
    },
    onSelect: function (record) {
    },
    onUnselect: function () {
    }
});
//查询渠道
$("#cchannelId").combobox({
    url: '/pop/product/channelsList',
    valueField: "id",
    textField: "channelcode",
    required: false,
    editable: true,
    hasDownArrow: true,
    filter: function (q, row) {
        var opts = $(this).combobox('options');
        return row[opts.textField].indexOf(q) >= 0;
    },
    onSelect: function (record) {
    },
    onUnselect: function () {
    }
});
//渠道
$("#channelId").combobox({
    url: '/pop/product/channelsList',
    valueField: "id",
    textField: "channelcode",
    required: true,
    editable: false,
    hasDownArrow: true,
    filter: function (q, row) {
        var opts = $(this).combobox('options');
        return row[opts.textField].indexOf(q) >= 0;
    },
    onSelect: function (record) {
    },
    onUnselect: function () {
    }
});
//判断渠道来源
$("#cchannelType").combobox({
    onSelect: function (record) {
        if(record.value == 1){
            channelsInfoListNo("cchannelId");
        }
        if(record.value == 2){
            channelsNo("cchannelId");
        }
    }

});
//判断渠道来源
$("#channelType").combobox({
    onSelect: function (record) {
        if(record.value == 1){
            channelsInfoList("channelId");
        }
        if(record.value == 2){
            channels("channelId");
        }
    }

});
$("#cpolicyYear").click(function () {
    $('#oDialog2').dialog('open');

});

$("#cpolicyYear").datepicker({
    language: "zh-CN",
    todayHighlight: true,
    format: 'yyyy',
    autoClose: true,
    startView: 'years',
    maxViewMode:'years',
    minViewMode:'years',
    orientation:'bottom',
    container :'#datetimepicker',
    pickerPosition:'top-right'

}).on('changeDate',function(ev){
    $('#oDialog2').dialog('close');
}).on('hide', function(e){

    $("#cpolicyYear").datepicker('show');
});

$("#policyYear").click(function () {
    $('#oDialog3').dialog('open');

});

$("#policyYear").datepicker({
    language: "zh-CN",
    todayHighlight: true,
    format: 'yyyy',
    autoClose: true,
    startView: 'years',
    maxViewMode:'years',
    minViewMode:'years',
    orientation:'bottom',
    container :'#datetimepicker1',
    pickerPosition:'top-right'

}).on('changeDate',function(ev){
    $('#oDialog3').dialog('close');
}).on('hide', function(e){

    $("#policyYear").datepicker('show');
});
$("#gridView").datagrid({
    onDblClickRow: function (index, row) {
        list();
    }
});
$(function () {
    $('#targetValue1').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue2').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue3').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue4').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue5').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue6').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue7').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue8').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue9').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue10').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue11').numberbox('textbox').attr('maxlength', 7);
    $('#targetValue12').numberbox('textbox').attr('maxlength', 7);

});
$("#oDialog4").css('display', 'none');


//修改
function list() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt("请选择要修改的单据");
        return;
    } else {
        $('#oDialog4').panel({title: "修改"});
        // $('#policyYear').val(rows1[0].policyYear);
        $('#brandId').combobox('setValue', rows1[0].brandId);
        $("#categoryId").combobox("setValue", rows1[0].categoryId);
        $("#channelType").combobox("setValue", rows1[0].channelType);
        if( rows1[0].channelType == 1){
            channelsInfoList("channelId");
        }
        else{
            channels("channelId");
        }
        $('#channelId').combobox('setValue', rows1[0].channelId);
        $('#policyYear').val(rows1[0].policyYear);
        $("#targetValue1").numberbox('setValue',rows1[0].targetValue1);
        $("#targetValue2").numberbox('setValue',rows1[0].targetValue2);
        $("#targetValue3").numberbox('setValue',rows1[0].targetValue3);
        $("#targetValue4").numberbox('setValue',rows1[0].targetValue4);
        $("#targetValue5").numberbox('setValue',rows1[0].targetValue5);
        $("#targetValue6").numberbox('setValue',rows1[0].targetValue6);
        $("#targetValue7").numberbox('setValue',rows1[0].targetValue7);
        $("#targetValue8").numberbox('setValue',rows1[0].targetValue8);
        $("#targetValue9").numberbox('setValue',rows1[0].targetValue9);
        $("#targetValue10").numberbox('setValue',rows1[0].targetValue10);
        $("#targetValue11").numberbox('setValue',rows1[0].targetValue11);
        $("#targetValue12").numberbox('setValue',rows1[0].targetValue12);
        $("#targetPolicy1").numberbox('setValue',rows1[0].targetPolicy1);
        $("#targetPolicy2").numberbox('setValue',rows1[0].targetPolicy2);
        $("#targetPolicy3").numberbox('setValue',rows1[0].targetPolicy3);
        $("#targetPolicy4").numberbox('setValue',rows1[0].targetPolicy4);
        $("#targetPolicy5").numberbox('setValue',rows1[0].targetPolicy5);
        $("#targetPolicy6").numberbox('setValue',rows1[0].targetPolicy6);
        $("#targetPolicy7").numberbox('setValue',rows1[0].targetPolicy7);
        $("#targetPolicy8").numberbox('setValue',rows1[0].targetPolicy8);
        $("#targetPolicy9").numberbox('setValue',rows1[0].targetPolicy9);
        $("#targetPolicy10").numberbox('setValue',rows1[0].targetPolicy10);
        $("#targetPolicy11").numberbox('setValue',rows1[0].targetPolicy11);
        $("#targetPolicy12").numberbox('setValue',rows1[0].targetPolicy12);
        $("#quarterStandardReward").numberbox('setValue',rows1[0].quarterStandardReward);
        $("#yearStandardReward").numberbox('setValue',rows1[0].yearStandardReward);
        $("#id").val(rows1[0].id);
        $('#oDialog4').dialog('open');

    }
}

//新增
function Add() {
    $('#oDialog4').panel({title: "新增"});
    $("#policyYear").val(near);
    $('#brandId').combobox('setValue', "");
    $("#categoryId").combobox("setValue", "");
    $('#channelId').combobox('setValue', "");

    $("#targetValue1").numberbox('setValue',"");
    $("#targetValue2").numberbox('setValue',"");
    $("#targetValue3").numberbox('setValue',"");
    $("#targetValue4").numberbox('setValue',"");
    $("#targetValue5").numberbox('setValue',"");
    $("#targetValue6").numberbox('setValue',"");
    $("#targetValue7").numberbox('setValue',"");
    $("#targetValue8").numberbox('setValue',"");
    $("#targetValue9").numberbox('setValue',"");
    $("#targetValue10").numberbox('setValue',"");
    $("#targetValue11").numberbox('setValue',"");
    $("#targetValue12").numberbox('setValue',"");
    
    $("#targetPolicy1").numberbox('setValue',"");
    $("#targetPolicy2").numberbox('setValue',"");
    $("#targetPolicy3").numberbox('setValue',"");
    $("#targetPolicy4").numberbox('setValue',"");
    $("#targetPolicy5").numberbox('setValue',"");
    $("#targetPolicy6").numberbox('setValue',"");
    $("#targetPolicy7").numberbox('setValue',"");
    $("#targetPolicy8").numberbox('setValue',"");
    $("#targetPolicy9").numberbox('setValue',"");
    $("#targetPolicy10").numberbox('setValue',"");
    $("#targetPolicy11").numberbox('setValue',"");
    $("#targetPolicy12").numberbox('setValue',"");
    $("#quarterStandardReward").numberbox('setValue',"");
    $("#yearStandardReward").numberbox('setValue',"");
    $("#id").val("");
    $('#oDialog4').dialog('open');


}

//删除
function Delete() {
    var rows1 = $('#gridView').datagrid('getSelections');
    if (rows1 == "") {
        autoAlt( "请选择要删除的单据");
        return;
    } else {
        $.messager.confirm('提示', '您确定要删除这一行吗?', function (b) {
            if (b) {
                $.ajax({
                    type: 'POST',
                    async: false,
                    url: '/pop/commission_target/deletecommission',
                    data: {
                        id: rows1[0].id


                    },
                    success: function (data, textStatus) {
                        if (data == 1) {
                            autoAlt("删除成功！");
                            $('#oDialog4').dialog('close');
                            SearchUnit();
                        } else {
                            autoAlt("操作失败！");
                        }

                    }

                });

            }
        });
    }
}

function SetCodeValue4() {

    var id = $("#id").val();
    var brandId= $('#brandId').combobox('getValue');
    var categoryId= $("#categoryId").combobox('getValue');
    var channelType= $("#channelType").combobox('getValue');
    var channelId= $('#channelId').combobox('getValue');
    var policyYear= $('#policyYear').val();
    var targetValue1= $("#targetValue1").numberbox('getText');
    var targetValue2= $("#targetValue2").numberbox('getText');
    var targetValue3= $("#targetValue3").numberbox('getText');
    var targetValue4= $("#targetValue4").numberbox('getText');
    var targetValue5= $("#targetValue5").numberbox('getText');
    var targetValue6= $("#targetValue6").numberbox('getText');
    var targetValue7= $("#targetValue7").numberbox('getText');
    var targetValue8=$("#targetValue8").numberbox('getText');
    var targetValue9= $("#targetValue9").numberbox('getText');
    var targetValue10= $("#targetValue10").numberbox('getText');
    var targetValue11= $("#targetValue11").numberbox('getText');
    var targetValue12= $("#targetValue12").numberbox('getText');
    
    var targetPolicy1= $("#targetPolicy1").numberbox('getText');
    var targetPolicy2= $("#targetPolicy2").numberbox('getText');
    var targetPolicy3= $("#targetPolicy3").numberbox('getText');
    var targetPolicy4= $("#targetPolicy4").numberbox('getText');
    var targetPolicy5= $("#targetPolicy5").numberbox('getText');
    var targetPolicy6= $("#targetPolicy6").numberbox('getText');
    var targetPolicy7= $("#targetPolicy7").numberbox('getText');
    var targetPolicy8= $("#targetPolicy8").numberbox('getText');
    var targetPolicy9= $("#targetPolicy9").numberbox('getText');
    var targetPolicy10= $("#targetPolicy10").numberbox('getText');
    var targetPolicy11= $("#targetPolicy11").numberbox('getText');
    var targetPolicy12= $("#targetPolicy12").numberbox('getText');
    
    var quarterStandardReward= $("#quarterStandardReward").numberbox('getText');
    var yearStandardReward= $("#yearStandardReward").numberbox('getText');
    var targetValueSum = Number(targetValue1)+Number(targetValue2)+Number(targetValue3)+
        Number(targetValue4)+Number(targetValue5)+Number(targetValue6)+Number(targetValue7)+Number(targetValue8)+Number(targetValue9)+Number(targetValue10)+
        Number(targetValue11)+Number(targetValue12);
    var url = '/pop/commission_target/updatecommission';
    if (brandId.length == 0) {
        autoAlt("品牌不能为空");
        return false;
    }
    if (categoryId.length == 0) {
        autoAlt("品类不能为空");
        return false;
    }
    if (channelType.length == 0) {
        autoAlt("渠道来源不能为空");
        return false;
    }
    if (channelId.length == 0) {
        autoAlt("渠道不能为空");
        return false;
    }
    if (policyYear.length == 0) {
        autoAlt("政策年度不能为空");
        return false;
    }
    if (targetValue1.length == 0) {
        autoAlt("1月份目标不能为空");
        return false;
    }

    if (targetValue2.length == 0) {
        autoAlt("2月份目标不能为空");
        return false;
    }
    if (targetValue3.length == 0) {
        autoAlt("3月份目标不能为空");
        return false;
    }
    if (targetValue4.length == 0) {
        autoAlt("4月份目标不能为空");
        return false;
    }
    if (targetValue5.length == 0) {
        autoAlt("5月份目标不能为空");
        return false;
    }
    if (targetValue6.length == 0) {
        autoAlt("6月份目标不能为空");
        return false;
    }
    if (targetValue7.length == 0) {
        autoAlt("7月份目标不能为空");
        return false;
    }
    if (targetValue8.length == 0) {
        autoAlt("8月份目标不能为空");
        return false;
    }
    if (targetValue9.length == 0) {
        autoAlt("9月份目标不能为空");
        return false;
    }
    if (targetValue10.length == 0) {
        autoAlt("10月份目标不能为空");
        return false;
    }
    if (targetValue11.length == 0) {
        autoAlt("11月份目标不能为空");
        return false;
    }
    if (targetValue12.length == 0) {
        autoAlt("12月份目标不能为空");
        return false;
    }

    if (targetPolicy1.length == 0) {
        autoAlt("1月份政策不能为空");
        return false;
    }

    if (targetPolicy2.length == 0) {
        autoAlt("2月份政策不能为空");
        return false;
    }
    if (targetPolicy3.length == 0) {
        autoAlt("3月份政策不能为空");
        return false;
    }
    if (targetPolicy4.length == 0) {
        autoAlt("4月份政策不能为空");
        return false;
    }
    if (targetPolicy5.length == 0) {
        autoAlt("5月份政策不能为空");
        return false;
    }
    if (targetPolicy6.length == 0) {
        autoAlt("6月份政策不能为空");
        return false;
    }
    if (targetPolicy7.length == 0) {
        autoAlt("7月份政策不能为空");
        return false;
    }
    if (targetPolicy8.length == 0) {
        autoAlt("8月份政策不能为空");
        return false;
    }
    if (targetPolicy9.length == 0) {
        autoAlt("9月份政策不能为空");
        return false;
    }
    if (targetPolicy10.length == 0) {
        autoAlt("10月份政策不能为空");
        return false;
    }
    if (targetPolicy11.length == 0) {
        autoAlt("11月份政策不能为空");
        return false;
    }
    if (targetPolicy12.length == 0) {
        autoAlt("12月份政策不能为空");
        return false;
    }
    if (id == "") {
        url = '/pop/commission_target/addcommission';
    }
    var flage;
     flage =jiaoyan();
    if(flage == false){
    	autoAlt("相同渠道、品类、品牌不能相同");
    	return;
    }
    $.ajax({
        type: 'POST',
        async: false,
        url: url,
        data: {
            id: id,
            policyYear: policyYear,
            categoryId: categoryId,
            channelType: channelType,
            channelId: channelId,
            brandId: brandId,

            targetValue1: targetValue1,
            targetValue2: targetValue2,
            targetValue3: targetValue3,
            targetValue4: targetValue4,
            targetValue5: targetValue5,
            targetValue6: targetValue6,
            targetValue7: targetValue7,
            targetValue8: targetValue8,
            targetValue9: targetValue9,
            targetValue10: targetValue10,
            targetValue11: targetValue11,
            targetValue12: targetValue12,
            targetValueSum: targetValueSum,
            targetPolicy1:targetPolicy1,
            targetPolicy2:targetPolicy2,
            targetPolicy3:targetPolicy3,
            targetPolicy4:targetPolicy4,
            targetPolicy5:targetPolicy5,
            targetPolicy6:targetPolicy6,
            targetPolicy7:targetPolicy7,
            targetPolicy8:targetPolicy8,
            targetPolicy9:targetPolicy9,
            targetPolicy10:targetPolicy10,
            targetPolicy11:targetPolicy11,
            targetPolicy12:targetPolicy12,
            yearStandardReward:yearStandardReward,
            quarterStandardReward:quarterStandardReward



        },
        success: function (data, textStatus) {
            if (data == 1) {
                autoAlt("操作成功！");
                $('#oDialog4').dialog('close');
                SearchUnit();
            } else {
                autoAlt("操作失败！");
            }

        }

    });
}
function SearchClear() {
	    $('#cpolicyYear').val(near);
	    $('#ccategoryId').combobox('setValue', "全部");
	    $('#cbrandId').combobox('setValue', "全部");
	    $('#cchannelType').combobox('setValue', "2");
	    $('#cchannelType').combobox('setText', "渠道");
	    $('#cchannelId').combobox('setValue', "全部");
}
function jiaoyan(){
		var flag;
		var brandId= $('#brandId').combobox('getValue');
	    var categoryId= $("#categoryId").combobox('getValue');
	    var channelType= $("#channelType").combobox('getValue');
	    var channelId= $('#channelId').combobox('getValue');
	    var policyYear= $('#policyYear').val();
	    var id= $('#id').val();
    var url = '/pop/commission_target/jiaoyancommission';
    $.ajax({
        type: 'POST',
        async: false,
        url: url,
        data: {
        	policyYear: policyYear,
            categoryId: categoryId,
            channelType: channelType,
            channelId: channelId,
            brandId: brandId,
            id:id

        },
        success: function (data, textStatus) {
        	flag=data;
        },
        error: function (e) {
            $.messager.alert(e);
        }
    });
    return flag;
}
//主表查询
function SearchUnit() {
    var categoryId = $('#ccategoryId').combobox('getValue');
    var policyYear = $('#cpolicyYear').val();
    var brandId = $('#cbrandId').combobox('getValue');
    var channelType = $('#cchannelType').combobox('getValue');
    var cchannelId = $('#cchannelId').combobox('getValue');
    if(categoryId == '全部'){
        categoryId='';
    }
    if(brandId == '全部'){
        brandId='';
    }
    if(cchannelId == '全部'){
        cchannelId='';
    }

    var options = $('#gridView').datagrid('getPager').data("pagination").options;
    options.pageNumber = 1;
    $.ajax({
        type: 'POST',
        async: true,
        url: '/pop/commission_target/commissionListF',
        data: {

            page: options.pageNumber,
            rows: options.pageSize,
            categoryId:categoryId,
            policyYear:policyYear,
            brandId:brandId,
            channelType:channelType,
            channelId:cchannelId

        },
        success: function (data, textStatus) {
        	$('#gridView').datagrid('getPager').pagination('refresh');
            $('#gridView').datagrid('loadData', data);
            if(data == ""){
                $(".pagination-num").val("0");
                }else{
                $(".pagination-num").val("1");
                }
        },
        error: function (e) {
            $.messager.alert(e);
        }
    });

}
