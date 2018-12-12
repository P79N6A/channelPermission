/*
$(function() {
    addTab("Welcome","portal.html",true);
    $(".noticeTitle").on('click',function(){
        addTab("公告","/project/bizNotice/index ");
    });

    $("#profile").click(function(){
      var title = $(this).text();
      addTab(title, "/auth/sysUser/profile", true);
    });


});
*/
$(function () {
    $(".noticeTitle").on('click', function () {
        addTab("公告", "/project/bizNotice/index ");
    });
    addTab("整车上单", "delivery_order/deliveryOrder.html");
    /*var lineCharts = echarts.init(document.getElementById("lineCharts"));

     option = {
     tooltip : {
     trigger: 'axis',
     axisPointer: {
     type: 'cross',
     label: {
     backgroundColor: '#6a7985'
     }
     }
     },
     legend: {
     data:['销量目标']
     },
     grid: {
     left: '3%',
     right: '4%',
     bottom: '3%',
     containLabel: true
     },
     xAxis : [
     {
     type : 'category',
     boundaryGap : false,
     data : ['2013','2014','2015','2016','2017']
     }
     ],
     yAxis : [
     {
     type : 'value'
     }
     ],
     series : [
     {
     name:'销量目标',
     type:'line',
     stack: '总量',
     symbol: 'none',
     itemStyle: {
     normal: {
     areaStyle: {
     color : ('#689DD3')
     },
     color:'#689DD3'
     }
     },
     data:[50, 90, 160, 800, 1000, 1200]
     }
     ]
     };

     lineCharts.setOption(option);*/

    var pie = echarts.init(document.getElementById("pie"));

    option_pie = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: '',
            left: 'left',
            data: ['项目', '变更项目']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '80%',
                center: ['50%', '60%'],
                data: [
                    {value: 90, name: '未变更项目'},
                    {value: 10, name: '变更项目'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                color: ['#73A7E0', '#DF8344']
            }
        ]
    };

    pie.setOption(option_pie);

    var pie1 = echarts.init(document.getElementById("pie1"));

    option_pie1 = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: '',
            left: 'left',
            data: ['当天未对赌', '1-3天未对赌', '3-7天未对赌', '7以上未对赌']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '80%',
                center: ['50%', '60%'],
                data: [
                    {value: 70, name: '当天未对赌'},
                    {value: 10, name: '1-3天未对赌'},
                    {value: 10, name: '3-7天未对赌'},
                    {value: 10, name: '7以上未对赌'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                color: ['#00A75A', '#F39D12', '#3C8DBD', '#DD4A38']
            }
        ]
    };

    pie1.setOption(option_pie1);


    $("#profile").click(function () {
        var title = $(this).text();
        addTab(title, "/auth/sysUser/profile", true);
    });


    //$("#systemName").css("display","");

    $('.logo').hover(function () {
        var thecolor = $('.logo').css("background-color");
        $("#systemlist").css("background-color", thecolor);

    }, function () {
        var thecolor = $('.logo').css("background-color");
        $("#systemlist").css("background-color", thecolor);
    });
    /*$("#systemlist").click(function () {
        var strFlag = $("#systemlist").val();
        if ("kucun" == strFlag){
            $('.kucun').css("display","");
            $('.caigou').css("display","none");
            $('.gongdan').css("display","none");
        }else if ("caigou" == strFlag){
            $('.kucun').css("display","none");
            $('.caigou').css("display","");
            $('.gongdan').css("display","none");
        }else if ("gongdan" == strFlag){
            $('.kucun').css("display","none");
            $('.caigou').css("display","none");
            $('.gongdan').css("display","");
        }
    })*/


    $(".change-system").mouseover(function () {
        if ($("#selectopen").val() == 0) {
            $(".ehaier-systems").show();
            $("#selectopen").val(1);
        } else {
            $(".ehaier-systems").hide();
            $("#selectopen").val(0);
        }
    });

    $(".change-system").mouseout(function () {
        if ($("#selectopen").val() == 1) {
            $(".ehaier-systems").hide();
            $("#selectopen").val(0);
        } else {
            $(".ehaier-systems").show();
            $("#selectopen").val(1);
        }
    });

    $(".ehaier-systems").mouseover(function () {
        $(".ehaier-systems").show();
    });

    $(".ehaier-systems").mouseout(function () {
        $(".ehaier-systems").hide();
    });


});

var selectSystem = function (strFlag, name) {
    $(".sidebar-menu").find(".treeview").css("display", "none")
    $("." + strFlag).css("display", "")
    $('#currentsystem').text(name);
    $('#systemlist').css("display", "none");
};