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
    // addTab("整车上单", "delivery_order/deliveryOrder.html");
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

//    var pie = echarts.init(document.getElementById("pie"));
//
//    option_pie = {
//        tooltip: {
//            trigger: 'item',
//            formatter: "{a} <br/>{b} : {c} ({d}%)"
//        },
//        legend: {
//            orient: '',
//            left: 'left',
//            data: ['项目', '变更项目']
//        },
//        series: [
//            {
//                name: '访问来源',
//                type: 'pie',
//                radius: '80%',
//                center: ['50%', '60%'],
//                data: [
//                    {value: 90, name: '未变更项目'},
//                    {value: 10, name: '变更项目'}
//                ],
//                itemStyle: {
//                    emphasis: {
//                        shadowBlur: 10,
//                        shadowOffsetX: 0,
//                        shadowColor: 'rgba(0, 0, 0, 0.5)'
//                    }
//                },
//                color: ['#73A7E0', '#DF8344']
//            }
//        ]
//    };
//
//    pie.setOption(option_pie);
//
//    var pie1 = echarts.init(document.getElementById("pie1"));
//
//    option_pie1 = {
//        tooltip: {
//            trigger: 'item',
//            formatter: "{a} <br/>{b} : {c} ({d}%)"
//        },
//        legend: {
//            orient: '',
//            left: 'left',
//            data: ['当天未对赌', '1-3天未对赌', '3-7天未对赌', '7以上未对赌']
//        },
//        series: [
//            {
//                name: '访问来源',
//                type: 'pie',
//                radius: '80%',
//                center: ['50%', '60%'],
//                data: [
//                    {value: 70, name: '当天未对赌'},
//                    {value: 10, name: '1-3天未对赌'},
//                    {value: 10, name: '3-7天未对赌'},
//                    {value: 10, name: '7以上未对赌'}
//                ],
//                itemStyle: {
//                    emphasis: {
//                        shadowBlur: 10,
//                        shadowOffsetX: 0,
//                        shadowColor: 'rgba(0, 0, 0, 0.5)'
//                    }
//                },
//                color: ['#00A75A', '#F39D12', '#3C8DBD', '#DD4A38']
//            }
//        ]
//    };
//
//    pie1.setOption(option_pie1);


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

$(function () {
    // test(200);

    jQuery.ajax({
        url: "/pageload/accordion",
        success: function (data) {
            // var temp = JSON.parse(data);
            if (data === "timeout"){
                // alert("登录超时，请重新登录");
                window.location.href="login.html";
            }
            if (data === null || data === ""){
                alert("您暂时没有任何菜单权限,请联系管理员");
            } else {
                // document.getElementById('currentsystem').innerHTML = JSON.parse(data)[0].text;
                $.each(JSON.parse(data), function (index, item) {
                    document.getElementById('systemlist').innerHTML +='<a id="'+item.id+'"style="cursor:pointer;" onclick="test('+item.id+',\''+item.text+'\')">'+item.text+'</a></br>';
                    document.getElementById('circleMenu').innerHTML +='<li><input type="checkbox"><a for="" onclick="test('+item.id+',\''+item.text+'\')">'+item.text+'</a></li>';
                    $("#page_userName").html(item.userName);
                    $("#data_userName").html(item.userName);            
                });
                setTimeout(function() { toggleOptions('.selector'); }, 100);
            }
        }
    });

});

function pageload(id) {
    jQuery.ajax({
        url: "/pageload/getMenuById",
        data:{
            "id":id
        },
        success: function (data) {
            var _html = "";
            $.each(JSON.parse(data), function (index, item) {
                _html += '<li><a menu-link style="cursor:pointer;" id="'+item.id+'" data-anchor="'+item.data+'" data-title="'+item.text+'" >'+item.text+'</a></li>';
                //document.getElementById(a).innerHTML +='<li><a menu-lin class="fa fa-arrow-down no_background" id="'+item.id+'">'+item.text+'</a></li></br>';
            });
            $("#a_"+id).html(_html);
        }
    });
}
/*

function add(text,data) {
    addTab(text,data)
}

*/
function test(mId,name) {

    document.getElementById('test').innerHTML = "";
    $('#currentsystem').text(name);
    jQuery.ajax({
        url: "/pageload/model",
        data:{
            "id":mId
        },
        success: function (data) {
            var _html = "";
            $.each(JSON.parse(data), function (index, item) {
                _html+= '<li><a href="javascript:;" onclick="pageload('+item.id+')" data-title="'+item.text+'" data-anchor=""><i></i><span>'+item.text+'</span> <i></i></a><ul class="treeview-menu" id="a_'+item.id
                    +'"></ul></li>'
                //document.getElementById('test').innerHTML +='<ul menu-lin href="#" data-title="'+item.text+'" id="'+item.id+'" class="fa fa-list no_background;"  onclick="pageload('+item.id+')">'+item.text+'</ul></br>';
            });

            $("#test").html(_html);
        }
    });
}

$("#logoutBtn_zhushidiao").click(function () {
    $.ajax({
        url:"/login/logout",
        type:"post"
    });
    window.location.href="login.html";
});



/* circle menu */
var angleStart = -360;

// jquery rotate animation
function rotate(li,d) {
    $({d:angleStart}).animate({d:d}, {
        step: function(now) {
            $(li)
               .css({ transform: 'rotate('+now+'deg)' })
               .find('a')
                  .css({ transform: 'rotate('+(-now)+'deg)' });
        }, duration: 0
    });
}

// show / hide the options
function toggleOptions(s) {
    $(s).toggleClass('open');
    var li = $(s).find('li');
    var deg = $(s).hasClass('half') ? 180/(li.length-1) : 360/li.length;
    for(var i=0; i<li.length; i++) {
        var d = $(s).hasClass('half') ? (i*deg)-90 : i*deg;
        $(s).hasClass('open') ? rotate(li[i],d) : rotate(li[i],angleStart);
    }
}

$('.selector button').click(function(e) {
    toggleOptions($(this).parent());
});