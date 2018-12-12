!(function(){
    // 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据
option = {
    title: {
        text: '天猫2018年不良率走势图',
        subtext: '',
        left:'center'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['不良率'],
        top:'30px'
    },
    toolbox: {
        show: true,
        feature: {
            dataZoom: {
                yAxisIndex: 'none'
            },
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']},
            restore: {},
            saveAsImage: {}
        }
    },
    xAxis:  {
        type: 'category',
        boundaryGap: false,
        data: (function(){
            var month=[];
            $.ajax({
                 type : "post",
                 async : false, //同步执行
                 url : "findBadProductCount",
                 data : {},
                dataType : "json", //返回数据形式为json
                 success : function(result) {
                   if (result) {
                	   for(var i=0;i<result.length;i++){       
         	              month.push(result[i].month);    //挨个取出类别并填入类别数组
         	            }   
                   }
                                        
                },
                    error : function(errorMsg) {
                        alert("不好意思，图表请求数据失败啦!");
                         myChart.hideLoading();
                       }
               })
                 return month;
              })() ,
    },
    yAxis: {
        type: 'value',
        axisLabel: {
            formatter: '{value} %'
        }
    },
    series: [
        {
            name:'不良率',
            type:'line',
            data: (function(){
                var badrate=[];
                $.ajax({
                type : "post",
                async : false, //同步执行
                url : "findBadProductCount",
                data : {},
               dataType : "json", //返回数据形式为json
                success : function(result) {
                  if (result) {
                	   for(var i=0;i<result.length;i++){       
        	               badrate.push(result[i].badrate);    //挨个取出销量并填入销量数组
        	             }
                  }
                                       
               },
                   error : function(errorMsg) {
                       alert("不好意思,图表请求数据失败啦!");
                        myChart.hideLoading();
                      }
              })
                return badrate;
             })() 
			,
            markPoint: {
                data: [
                    {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
                ]
            },
            markLine: {
                data: [
                    {
                        type: 'average', 
                        name: '平均值',
                        label: {
                            normal: {
                                position: 'end',
                                formatter: 2.5
                            }
                        }
                    }
                    // ,
                    // [{
                    //     symbol: 'none',
                    //     x: '90%',
                    //     yAxis: 'max'
                    // }, {
                    //     symbol: 'circle',
                    //     label: {
                    //         normal: {
                    //             position: 'start',
                    //             formatter: '最大值'
                    //         }
                    //     },
                    //     type: 'max',
                    //     name: '最高点'
                    // }]
                ]
            }
        }
    ]
};


// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
})();



