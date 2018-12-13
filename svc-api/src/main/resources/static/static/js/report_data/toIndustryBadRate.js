!(function(){

    var rejectArray = [];
    var signArray = [];
    var totalArray = [];
    var industryArray = [];
    //total为从后台获取的所有产业不良品总量
    var total = 0;
    $.ajax({
        type : "post",
        async : false, //同步执行
        url : "findBadRate",
        data : {},
       dataType : "json", //返回数据形式为json
        success : function(result) {
          if (result) {
       	   for(var i=0;i<result.length;i++){       
       		   rejectArray.push(result[i].reject);    //挨个取出类别并填入类别数组
	            }
           for(var i=0;i<result.length;i++){       
        	   signArray.push(result[i].sign);    //挨个取出类别并填入类别数组
	    	            }
           for(var i=0;i<result.length;i++){       
        	   totalArray.push(result[i].total);    //挨个取出类别并填入类别数组
	    	    	    }
           for(var i=0;i<result.length;i++){       
        	   industryArray.push(result[i].industry);    //挨个取出类别并填入类别数组
	    	    	    }
          }                      
       },
           error : function(errorMsg) {
               alert("不好意思，图表请求数据失败啦!");
                myChart.hideLoading();
              }
      });
    //将各个产业的不良品数量加上一起
    for ( var i = 0; i <totalArray.length; i++){
        total = total+totalArray[i];
    }
    // 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据
option = {
    title: {
        text: '天猫渠道各产业不良率',
        subtext: '',
        left:'center'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data:['拒收','签收'],
        top:'30px'
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
            data : industryArray
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'拒收',
            type:'bar',
            barWidth : '50%',
            stack: '订单',
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data:rejectArray
        },
        {
            name:'签收',
            type:'bar',
            stack: '订单',
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data:signArray
        },
        {
            name:'不良品总数',
            type:'line',
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter:function(param){
                        var temp = (param.value/total).toFixed(4);
                        	if(temp!= 'NaN'){
                        return temp*10000/100+'%';
                        	}
                    }
                }
            },
            data:totalArray
        }
    ]
};


// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
})();



