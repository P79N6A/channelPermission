var dataYear = [
    {
        "month": '1月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '2月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '3月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '4月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '5月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '6月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    }, {
        "month": '7月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '8月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '9月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '10月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '11月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '12月',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    },
    {
        "month": '总计',
        "badProdNum": 18867,
        "saleNum": 526940,
        "badRate": '3.58%',
        "averBadRate": '3.59',
    }];


//点击导出不良品
$('#btn_import_bad').click(function(){
    var fromTimeBad=$("#fromTimeBad option:selected");
    var toTimeBad=$("#toTimeBad option:selected");
    var optionsyear=$("#reportTimeYear option:selected");
    var years=parseInt(optionsyear.text());
    var outmonthbegin=fromTimeBad.val();
    var outmonthend=toTimeBad.val();
    console.log(outmonthbegin,outmonthend,years);
    if(outmonthbegin>outmonthend){
        $.messager.confirm('提示','请按顺序选择月份')
    }else{
        $.messager.confirm('确认','确定要导出吗？', function(r){
            if (r){
                $("#beginbad").val(outmonthbegin);
                $("#endbad").val(outmonthend);
                $("#year").val(years);
                $('#badimput').attr("action", '/Rejectsdetail/export');
                $('#badimput').submit();
            }
        });
    }
});


//点击导出出库商品
$('#btn_import_out').click(function(){
    var fromTimeOut=$("#fromTimeOut option:selected");
    var toTimeOut=$("#toTimeOut option:selected");
    var optionsyear=$("#reportTimeYear option:selected");
    var years=parseInt(optionsyear.text());
    var beginmonth=fromTimeOut.val();
    var endmonth=toTimeOut.val();
    console.log(beginmonth,endmonth,years);
    if(beginmonth>endmonth){
        $.messager.confirm('提示','请按顺序选择月份')
    }else {
        $.messager.confirm('确认', '确定要导出吗？', function (r) {
            if (r) {
                $("#beginout").val(beginmonth);
                $("#endout").val(endmonth);
                $("#years").val(years);
                $('#outimput').attr("action", '/Rejectsdetail/exportout');
                $('#outimput').submit();
            }
        });
    }
});



//不良品数导入
$("#input-b1").fileinput({
    // theme: 'fa',
    uploadUrl: '/Rejectsdetail/import',
    allowedFileExtensions: ['xls', 'xlsx'],
    overwriteInitial: false,
    language: 'zh',
    // maxFileSize: 1000,
    // maxFilesNum: 10,
    //allowedFileTypes: ['image', 'video', 'flash'],
    slugCallback: function (filename) {
        console.log(filename);
        return filename.replace('(', '_').replace(']', '_');
    }
});
$("#input-b1").on("fileuploaded", function (event,result, data, previewId, index) {
    var se=result.response.message;
    $.messager.alert('提示信息', se);

    console.log(event,data,previewId,index);
});
//出库数导入
$("#input-b2").fileinput({
    // theme: 'fa',
    uploadUrl: '/Rejectsdetail/outimport', // you must set a valid URL here else you will get an error
    allowedFileExtensions: ['xls', 'xlsx'],
    overwriteInitial: false,
    language: 'zh',
    // maxFileSize: 1000,
    // maxFilesNum: 10,
    //allowedFileTypes: ['image', 'video', 'flash'],
    slugCallback: function (filename) {
        return filename.replace('(', '_').replace(']', '_');
    }
});
$("#input-b2").on("fileuploaded", function (event,result, data, previewId, index) {
    var se=result.response.message;
    $.messager.alert('提示信息', se);

    console.log(event,data,previewId,index);
});

//不良品数模板
$("#btn_b1").on('click',function(){
    $.ajax({
        url:'',
        method:'get',
        success:function(data){
            console.log('success');
        }
    });
});
//出库数模板
$("#btn_b2").on('click',function(){
    $.ajax({
        url:'',
        method:'get',
        success:function(data){
            console.log('success');
        }
    });
});

$(function(){
    init();

    //年列表选择
    $("#reportTimeYear").on('change',function(){

        console.log("年列表选择");
        var optionsmonth=$("#reportTimeMonth option:selected");
        var optionsyear=$("#reportTimeYear option:selected");
        var months=optionsmonth.text();
        var years=parseInt(optionsyear.text());
        if(months=="全年"){
            $("#pieDiv").css('height','400px');
            $("#drawPieHandle").css('height','400px');
            //销毁饼图
            echarts.dispose(document.getElementById('pieDiv'));
            echarts.dispose(document.getElementById('drawPieHandle'));
            var myChart = echarts.init(document.getElementById('main'));
            $.ajax({
                url:'/Rejectsdetail/getList',
                type:'get',
                data:{"month": months, "year": years},
                // data:{val:$("#reportTimeMonth").val()},
                dataType:'JSON',
                success:function(data){
                    drawBarYear(data.xData,data.yData);
                    //myChart.setOption(data.xData,data.yData);
                },
                error:function(){
                    console.log('error');
                }
            });

            $.ajax({
                url:'/Rejectsdetail/getList',
                type:'get',
                data:{"month": months, "year": years},
                // data:{val:$("#reportTimeMonth").val()},
                dataType:'JSON',
                success:function(data){
                    $("#table").bootstrapTable('destroy');
                    var av=data.avgrate;
                    $('#table').bootstrapTable({
                        data: data.rows,
                        //url: '/Home/GetDepartment',         //请求后台的URL（*）
                        method: 'get',                      //请求方式（*）
                        //toolbar: '#toolbar',                //工具按钮用哪个容器
                        striped: true,                      //是否显示行间隔色
                        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                        pagination: false,                   //是否显示分页（*）
                        sortable: false,                     //是否启用排序
                        sortOrder: "asc",                   //排序方式
                        //queryParams: oTableInit.queryParams,//传递参数（*）
                        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                        pageNumber:1,                       //初始化加载第一页，默认第一页
                        pageSize: 10,                       //每页的记录行数（*）
                        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                        strictSearch: true,
                        showColumns: true,                  //是否显示所有的列
                        showRefresh: true,                  //是否显示刷新按钮
                        minimumCountColumns: 2,             //最少允许的列数
                        clickToSelect: true,                //是否启用点击选中行
                        height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
                        cardView: false,                    //是否显示详细视图
                        detailView: false,                   //是否显示父子表
                        columns: [{
                            field: 'month',
                            title: '月份',
                            formatter:function (data,rows,index) {
                                return (rows[0]);
                            }
                        }, {
                            field: 'rejectnum',
                            title: '不良品',
                            formatter:function (data,rows,index) {
                                return (rows[1]);
                            }
                        }, {
                            field: 'outqty',
                            title: '出库数',
                            formatter:function (data,rows,index) {
                                return (rows[2]);
                            }
                        }, {
                            field: 'badRate',
                            title: '不良率',
                            formatter:function (data,rows,index) {
                                if((rows[2]==0 && rows[1]!=0)) {
                                    return "100.00%";
                                }if((rows[2]!=0 && rows[1]==0) || (rows[1]==0 && rows[2]==0)){
                                    return "0.00%";
                                }
                                return (((rows[1]/rows[2])*100).toFixed(2))+"%";
                            }
                        }, {
                            field: 'averBadRate',
                            title: '均不良率',
                            formatter:function (data,rows,avgrate) {
                                return av;
                            }
                        }]
                    });
                },
                error:function(){
                    console.log('error');
                }

            });
            $.ajax({
                url:'/Rejectsdetail/responsibility',
                type:'get',
                data:{"year": years},
                dataType:'JSON',
                success:function(data){
                    //console.log(data);
                    //画饼图
                    drawPie(data);
                },
                error:function(){
                    console.log('error');
                }
            });

            //请求处理方式分析饼图接口数据
            $.ajax({
                url:'/Rejectsdetail/Processing',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){

                    //画饼图
                    drawPieH(data);
                },
                error:function(){
                    console.log('error');
                }
            });
        }else{
            $("#pieDiv").css('height','400px');
            $("#drawPieHandle").css('height','400px');
            //销毁饼图
            echarts.dispose(document.getElementById('pieDiv'));
            echarts.dispose(document.getElementById('drawPieHandle'));
//请求bar图接口数据
            $.ajax({
                url:'/Rejectsdetail/getList',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){
                    console.log(data);
                    drawBarMonth(data.xData,data.yData);
                },
                error:function(){
                    console.log('error');
                }
            });

            //请求表格接口数据
            $.ajax({
                url:'/Rejectsdetail/getList',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){
                    $("#table").bootstrapTable('destroy');

                    $('#table').bootstrapTable({
                        data: data.rows,
                        //url: '/Home/GetDepartment',         //请求后台的URL（*）
                        method: 'get',                      //请求方式（*）
                        //toolbar: '#toolbar',                //工具按钮用哪个容器
                        striped: true,                      //是否显示行间隔色
                        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                        pagination: false,                   //是否显示分页（*）
                        sortable: false,                     //是否启用排序
                        sortOrder: "asc",                   //排序方式
                        //queryParams: oTableInit.queryParams,//传递参数（*）
                        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                        pageNumber:1,                       //初始化加载第一页，默认第一页
                        pageSize: 10,                       //每页的记录行数（*）
                        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                        strictSearch: true,
                        showColumns: true,                  //是否显示所有的列
                        showRefresh: true,                  //是否显示刷新按钮
                        minimumCountColumns: 2,             //最少允许的列数
                        clickToSelect: true,                //是否启用点击选中行
                        height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
                        cardView: false,                    //是否显示详细视图
                        detailView: false,                   //是否显示父子表
                        columns: [{
                            field: 'industry',
                            title: '产业'
                        }, {
                            field: 'rejectnum',
                            title: '不良品数'
                        }, {
                            field: 'outqty',
                            title: '出库数'
                        }, {
                            field: 'badRate',
                            title: '不良率',
                            formatter:function (value,row,index,data) {
                                if(row.outqty==0 && row.rejectnum!=0){
                                    return "100.00%";
                                } if((row.outqty!=0 && row.rejectnum==0) || (row.outqty==0 && row.rejectnum==0)){
                                    return "0.00%";
                                }
                                return ((((row.rejectnum/row.outqty)*100).toFixed(2))+"%")
                            }
                        }]
                    });
                },
                error:function(){
                    console.log('error');
                }
            });
            //请求责任占比分析饼图接口数据
            $.ajax({
                url:'/Rejectsdetail/responsibility',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){
                    //画饼图
                    drawPie(data);
                },
                error:function(){
                    console.log('error');
                }
            });

            //请求处理方式分析饼图接口数据
            $.ajax({
                url:'/Rejectsdetail/Processing',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){

                    //画饼图
                    drawPieH(data);
                },
                error:function(){
                    console.log('error');
                }
            });
        }
    });

    //月列表选择
    $("#reportTimeMonth").on('change',function(){

        var optionsmonth=$("#reportTimeMonth option:selected");
        var optionsyear=$("#reportTimeYear option:selected");
        var months=optionsmonth.text();
        var years=parseInt(optionsyear.text());
        if(months=="全年"){
            /*$("#pieDiv").css('height','0px');
            $("#drawPieHandle").css('height','0px');*/
            //销毁饼图
            echarts.dispose(document.getElementById('pieDiv'));
            echarts.dispose(document.getElementById('drawPieHandle'));

            var myChart = echarts.init(document.getElementById('main'));
            $.ajax({
                url:'/Rejectsdetail/getList',
                type:'get',
                data:{"month": months, "year": years},
                // data:{val:$("#reportTimeMonth").val()},
                dataType:'JSON',
                success:function(data){
                    drawBarYear(data.xData,data.yData);
                    //myChart.setOption(data.xData,data.yData);
                },
                error:function(){
                    console.log('error');
                }
            });

            $.ajax({
                url:'/Rejectsdetail/getList',
                type:'get',
                data:{"month": months, "year": years},
                // data:{val:$("#reportTimeMonth").val()},
                dataType:'JSON',
                success:function(data){
                    $("#table").bootstrapTable('destroy');
                    var av=data.avgrate;
                    $('#table').bootstrapTable({
                        data: data.rows,
                        //url: '/Home/GetDepartment',         //请求后台的URL（*）
                        method: 'get',                      //请求方式（*）
                        //toolbar: '#toolbar',                //工具按钮用哪个容器
                        striped: true,                      //是否显示行间隔色
                        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                        pagination: false,                   //是否显示分页（*）
                        sortable: false,                     //是否启用排序
                        sortOrder: "asc",                   //排序方式
                        //queryParams: oTableInit.queryParams,//传递参数（*）
                        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                        pageNumber:1,                       //初始化加载第一页，默认第一页
                        pageSize: 10,                       //每页的记录行数（*）
                        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                        strictSearch: true,
                        showColumns: true,                  //是否显示所有的列
                        showRefresh: true,                  //是否显示刷新按钮
                        minimumCountColumns: 2,             //最少允许的列数
                        clickToSelect: true,                //是否启用点击选中行
                        height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
                        cardView: false,                    //是否显示详细视图
                        detailView: false,                   //是否显示父子表
                        columns: [{
                            field: 'month',
                            title: '月份',
                            formatter:function (data,rows,index) {
                                return (rows[0]);
                            }
                        }, {
                            field: 'rejectnum',
                            title: '不良品',
                            formatter:function (data,rows,index) {
                                return (rows[1]);
                            }
                        }, {
                            field: 'outqty',
                            title: '出库数',
                            formatter:function (data,rows,index) {
                                return (rows[2]);
                            }
                        }, {
                            field: 'badRate',
                            title: '不良率',
                            formatter:function (data,rows,index) {
                                if((rows[2]==0 && rows[1]!=0)) {
                                    return "100.00%";
                                }if((rows[2]!=0 && rows[1]==0) || (rows[1]==0 && rows[2]==0)){
                                    return "0.00%";
                                }
                                return (((rows[1]/rows[2])*100).toFixed(2))+"%";
                            }
                        }, {
                            field: 'averBadRate',
                            title: '均不良率',
                            formatter:function (data,rows,avgrate) {
                                return av;
                            }
                        }]
                    });
                },
                error:function(){
                    console.log('error');
                }

            });
            //请求责任占比分析饼图接口数据
            $.ajax({
                url:'/Rejectsdetail/responsibility',
                type:'get',
                data:{"year": years},
                dataType:'JSON',
                success:function(data){
                    //console.log(data);
                    //画饼图
                    drawPie(data);
                },
                error:function(){
                    console.log('error');
                }
            });

            //请求处理方式分析饼图接口数据
            $.ajax({
                url:'/Rejectsdetail/Processing',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){
                    //画饼图
                    drawPieH(data);
                },
                error:function(){
                    console.log('error');
                }
            });

        }else{
           /* $("#pieDiv").css('height','400px');
            $("#drawPieHandle").css('height','400px');*/
            //销毁饼图
            echarts.dispose(document.getElementById('pieDiv'));
            echarts.dispose(document.getElementById('drawPieHandle'));
            //请求bar图接口数据
            $.ajax({
                url:'/Rejectsdetail/getList',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){
                    drawBarMonth(data.xData,data.yData);
                },
                error:function(){
                    console.log('error');
                }
            });

            //请求表格接口数据
            $.ajax({
                url:'/Rejectsdetail/getList',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){
                    $("#table").bootstrapTable('destroy');

                    $('#table').bootstrapTable({
                        data: data.rows,
                        //url: '/Home/GetDepartment',         //请求后台的URL（*）
                        method: 'get',                      //请求方式（*）
                        //toolbar: '#toolbar',                //工具按钮用哪个容器
                        striped: true,                      //是否显示行间隔色
                        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                        pagination: false,                   //是否显示分页（*）
                        sortable: false,                     //是否启用排序
                        sortOrder: "asc",                   //排序方式
                        //queryParams: oTableInit.queryParams,//传递参数（*）
                        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                        pageNumber:1,                       //初始化加载第一页，默认第一页
                        pageSize: 10,                       //每页的记录行数（*）
                        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                        strictSearch: true,
                        showColumns: true,                  //是否显示所有的列
                        showRefresh: true,                  //是否显示刷新按钮
                        minimumCountColumns: 2,             //最少允许的列数
                        clickToSelect: true,                //是否启用点击选中行
                        height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
                        cardView: false,                    //是否显示详细视图
                        detailView: false,                   //是否显示父子表
                        columns: [{
                            field: 'industry',
                            title: '产业'
                        }, {
                            field: 'rejectnum',
                            title: '不良品数'
                        }, {
                            field: 'outqty',
                            title: '出库数'
                        }, {
                            field: 'badRate',
                            title: '不良率',
                            formatter:function (value,row,index,data) {
                                if(row.outqty==0 && row.rejectnum!=0){
                                    return "100.00%";
                                } if((row.outqty!=0 && row.rejectnum==0) || (row.outqty==0 && row.rejectnum==0)){
                                    return "0.00%";
                                }
                                return ((((row.rejectnum/row.outqty)*100).toFixed(2))+"%")
                            }
                        }]
                    });
                },
                error:function(){
                    console.log('error');
                }
            });


            //请求责任占比分析饼图接口数据
            $.ajax({
                url:'/Rejectsdetail/responsibility',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){
                    //console.log(data);
                    //画饼图
                    drawPie(data);
                },
                error:function(){
                    console.log('error');
                }
            });

            //请求处理方式分析饼图接口数据
            $.ajax({
                url:'/Rejectsdetail/Processing',
                type:'get',
                data:{"month": months, "year": years},
                dataType:'JSON',
                success:function(data){

                    //画饼图
                    drawPieH(data);
                },
                error:function(){
                    console.log('error');
                }
            });
        }
    });
});




function init(){
    // 基于准备好的dom，初始化echarts实例

    var myChart = echarts.init(document.getElementById('main'));

    $("#pieDiv").css('height','400px');
    $("#drawPieHandle").css('height','400px');

    $.ajax({
        url:'/Rejectsdetail/getList',
        type:'get',
        data:{"month": "全年", "year": 2018},
        // data:{val:$("#reportTimeMonth").val()},
        dataType:'JSON',
        success:function(data){
            drawBarYear(data.xData,data.yData);
            //myChart.setOption(data.xData,data.yData);
        },
        error:function(){
            console.log('error');
        }
    });

    $.ajax({
        url:'/Rejectsdetail/getList',
        type:'get',
        data:{"month": "全年", "year": 2018},
        // data:{val:$("#reportTimeMonth").val()},
        dataType:'JSON',
        success:function(data){
            var av=data.avgrate;
            $('#table').bootstrapTable({
                data: data.rows,
                //url: '/Home/GetDepartment',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                //toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: false,                   //是否显示分页（*）
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                //queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                showRefresh: true,                  //是否显示刷新按钮
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
                height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                columns: [{
                    field: 'month',
                    title: '月份',
                    formatter:function (data,rows,index) {
                        return (rows[0]);
                    }
                }, {
                    field: 'rejectnum',
                    title: '不良品',
                    formatter:function (data,rows,index) {
                        return (rows[1]);
                    }
                }, {
                    field: 'outqty',
                    title: '出库数',
                    formatter:function (data,rows,index) {
                        return (rows[2]);
                    }
                }, {
                    field: 'badRate',
                    title: '不良率',
                    formatter:function (data,rows,index) {
                        if((rows[2]==0 && rows[1]!=0)) {
                            return "100.00%";
                        }if((rows[2]!=0 && rows[1]==0) || (rows[1]==0 && rows[2]==0)){
                            return "0.00%";
                        }
                        return (((rows[1]/rows[2])*100).toFixed(2))+"%";
                    }
                }, {
                    field: 'averBadRate',
                    title: '均不良率',
                    formatter:function (data,rows,avgrate) {
                        return av;
                    }
                }]
            });
        },
        error:function(){
            console.log('error');
        }
    });
    //请求责任占比分析饼图接口数据
    $.ajax({
        url:'/Rejectsdetail/responsibility',
        type:'get',
        data:{"year": 2018},
        dataType:'JSON',
        success:function(data){
            //console.log(data);
            //画饼图
            drawPie(data);
        },
        error:function(){
            console.log('error');
        }
    });

    //请求处理方式分析饼图接口数据
    $.ajax({
        url:'/Rejectsdetail/Processing',
        type:'get',
        data:{"year": 2018},
        dataType:'JSON',
        success:function(data){

            //画饼图
            drawPieH(data);
        },
        error:function(){
            console.log('error');
        }
    });
}

//画年图
function drawBarYear(xData,yData){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    var year = $("#reportTimeYear option:selected").text(),month = $("#reportTimeMonth option:selected").text();

    // 指定图表的配置项和数据
var colors = ['#5793f3', '#d14a61', '#675bba'];

var optionYear = {
    color: colors,
    title : {
        text: year+month+'京东渠道不良品发生率',
        subtext: '',
        x:'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    grid: {
        left:'4%',
        right: '4%',
        top:'20%',
        bottom:'25px'
    },
    toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data:['不良品数','不良品率','均不良率']
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLabel: {
                show: true,
                textStyle: {
                    fontWeight:'bolder'
                }
            },
            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '不良品数',
            min: 0,
            // max: 25,
            position: 'left',
            axisLine: {
                lineStyle: {
                    color: colors[0]
                }
            },
            axisLabel: {
                formatter: '{value}'
            }
        },
        {
            type: 'value',
            name: '不良品率',
            min: 0,
            // max: 250,
            position: 'right',
            axisLine: {
                lineStyle: {
                    color: colors[1]
                }
            },
            axisLabel: {
                formatter: '{value}%'
            }
        }
        
        
    ],
    series: [
        {
            name:'不良品数',
            type:'bar',
            label:{
                normal:{
                    show: true,
                    position: 'top',
                    distance:18,
                    color:'#000',
                    formatter:'{c}'
                }
            },
            data:xData  //[18867, 15792, 20300, 13338, 15018, 19947, 18185, 0, 0, 0, 0, 0]
        },
        
        {
            name:'不良品率',
            type:'line',
            yAxisIndex: 1,
            markLine: {
                data: [
                    {type: 'average', name: '均不良率'}
                ],
                lineStyle:{
                    normal:{
                        color:  '#28ec5e'

                    }


                }
            },
            label:{
                normal:{
                    show: true,
                    position: 'top',
                    color:'#000',
                    formatter:'{c}%'
                }
            },
            data:yData  //[3.58, 4.15, 4.73, 3.54, 3.59, 2.39, 4.36, 0, 0, 0, 0, 0]
        }
    ]
    // ,
    // label: {
    //     normal: {
    //         show: true,
    //         position: 'top',
    //         textStyle: {
    //           color: '#ff9800'
    //         }
    //     }
    //  }
};
    myChart.setOption(optionYear);
}


//画月图
function drawBarMonth(xData,yData){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    var year = $("#reportTimeYear option:selected").text(),month = $("#reportTimeMonth option:selected").text();

    // 指定图表的配置项和数据
var colors = ['#5793f3', '#d14a61', '#675bba'];

var optionMonth = {
    color: colors,
    title : {
        text: year+month+'京东渠道不良品发生率',
        subtext: '',
        x:'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    grid: {
        left:'4%',
        right: '4%',
        top:'20%',
        bottom:'25px'
    },
    toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data:['不良品数','不良品率']
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLabel: {
                show: true,
                textStyle: {
                    fontWeight:'bolder'
                }
            },
            data: ['冰箱','洗衣机','冷柜','空调','热水器','厨电','彩电']
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '不良品数',
            min: 0,
            // max: 25,
            position: 'left',
            axisLine: {
                lineStyle: {
                    color: colors[0]
                }
            },
            axisLabel: {
                formatter: '{value}'
            }
        },
        {
            type: 'value',
            name: '不良品率',
            min: 0,
            // max: 250,
            position: 'right',
            axisLine: {
                lineStyle: {
                    color: colors[1]
                }
            },
            axisLabel: {
                formatter: '{value}%'
            }
        }
        // {
        //     type: 'value',
        //     name: '降水量',
        //     min: 0,
        //     max: 250,
        //     position: 'right',
        //     offset: 80,
        //     axisLine: {
        //         lineStyle: {
        //             color: colors[1]
        //         }
        //     },
        //     axisLabel: {
        //         formatter: '{value} ml'
        //     }
        // },
        
    ],
    series: [
        {
            name:'不良品数',
            type:'bar',
            data:xData//[18867, 15792, 20300, 13338, 15018, 19947, 18185]
        },
        // {
        //     name:'不良率',
        //     type:'bar',
        //     yAxisIndex: 1,
        //     data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
        // },
        {
            name:'不良品率',
            type:'line',
            yAxisIndex: 1,
            data:yData//[3.58, 4.15, 4.73, 3.54, 3.59, 2.39, 4.36][18867, 15792, 20300, 13338, 15018, 19947, 18185]
        }
    ],
    label: {
        normal: {
            show: true,
            position: 'top',
            textStyle: {
              color: '#ff9800'
            }
        }
     }
};

    myChart.setOption(optionMonth);
}


function drawPie(data){
    var jd=data[0];
    var hr=data[1];
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('pieDiv'));

    var year = $("#reportTimeYear option:selected").text(),month = $("#reportTimeMonth option:selected").text();

    var optionPie = {
        title : {
            text: year+month+'京东责任占比分析',
            subtext: '',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['京东责任','海尔责任']
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '70%'],
                data:[
                    {value:jd, name:'京东责任'},
                    {value:hr, name:'海尔责任'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0)'
                    }
                }
            }
        ]
    };


    myChart.setOption(optionPie);
}
function drawPieH(data){
    var Rejection=data[0];
    var Takepartjd=data[1];
    var afterwhere=data[2];
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('drawPieHandle'));

    var year = $("#reportTimeYear option:selected").text(),month = $("#reportTimeMonth option:selected").text();

    var optionPie = {
        title : {
            text: year+month+'京东处理方式分析',
            subtext: '',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['拒收','售后哪买哪退','京东取件']
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '70%'],
                data:[
                    {value:Rejection, name:'拒收'},
                    {value:afterwhere, name:'售后哪买哪退'},
                    {value:Takepartjd, name:'京东取件'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0)'
                    }
                }
            }
        ]
    };
    myChart.setOption(optionPie);
}