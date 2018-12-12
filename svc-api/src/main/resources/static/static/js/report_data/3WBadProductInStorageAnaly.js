$("#contractBegintime").datebox({  
    onSelect : function(beginDate){  
        $('#contractEndtime').datebox().datebox('calendar').calendar({  
            validator: function(date){  
                return beginDate<=date;//<=  
            }  
        });  
    }  
});


var datagridData_industry = {
    'data': {
        'records': 
        [
            {
                'industryCategory': '洗衣机', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'industryCategory': '冰箱', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'industryCategory': '热水器', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'industryCategory': '冷柜', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'industryCategory': '空调', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'industryCategory': '厨电', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'industryCategory': '彩电', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'industryCategory': '总计', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            }
        ], 'totalCount': 10
    }
};

var datagridData_channel = {
    'data': {
        'records': 
        [
            {
                'channelCategory': 'mooka模卡官方旗舰店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '海尔冰冷官方旗舰店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '海尔洗衣机官方旗舰店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '卡萨帝官方旗舰店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '生态授权店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '淘宝村淘', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '淘宝海尔厨房大电旗舰店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '淘宝海尔官方旗舰店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '淘宝海尔热水器专卖店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '淘宝空调旗舰店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '天猫海尔电视', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            },
            {
                'channelCategory': '统帅日日顺乐家专卖店', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            }
            ,
            {
                'channelCategory': '总计', sortable: true,
                'inStoreNotExpire': '10101414400', sortable: true,
                'inStoreHasExpire': '10101414400', sortable: true,
                'inStoreTotal': '10101402200', sortable: true,
                'notInStoreNotExpire': '某某某', sortable: true,
                'notInStoreHasExpire': '交易', sortable: true,
                'notInStoreTotal': '10000', sortable: true,
                'badProductTotal': '10000', sortable: true,
                'badProductInStoreRate': '10000', sortable: true
            }
        ], 'totalCount': 10
    }
};


var datagridOptions_industry = {
    width:'100%',
    height:'auto',
    fit: false,
    fitColumns: true,
    // singleSelect: true,
    //url: '/module2/invMachineSet/p',
    // frozenColumns: [[{
    //     field: 'id', checkbox: true
    // }]],
    columns: [[
        {title: '产业别', field: 'industryCategory', sortable: false,align:'center'},
        {title: '已入库未超期', field: 'inStoreNotExpire', sortable: false,align:'center'},
        {title: '已入库已超期', field: 'inStoreHasExpire', sortable: false,align:'center'},
        {title: '已入库合计', field: 'inStoreTotal', sortable: false,align:'center'},
        {title: '未入库未超期', field: 'notInStoreNotExpire', sortable: false,align:'center'},
        {title: '未入库已超期', field: 'notInStoreHasExpire', sortable: false,align:'center'},
        {title: '未入库合计', field: 'notInStoreTotal', sortable: false,align:'center'},
        {title: '不良品总计', field: 'badProductTotal', sortable: false,align:'center'},
        {title: '不良品完成入库率', field: 'badProductInStoreRate', sortable: false,align:'center'}
     ]],
    rowStyler: function(index,row){
        if (row.industryCategory == '总计'){//这里是判断哪些行
            return 'font-weight:bold;';  
        }
    },
    toolbar: '#datagridToolbar',
    striped: true,
    pagination: false,
    pageSize: 10,
    pageList: [10, 20, 30, 40, 50],
    rownumbers: true
};

var datagridOptions_channel = {
    width:'100%',
    height:'auto',
    fit: false,
    fitColumns: true,
    // singleSelect: true,
    //url: '/module2/invMachineSet/p',
    // frozenColumns: [[{
    //     field: 'id', checkbox: true
    // }]],
    columns: [[
        {title: '销售渠道别', field: 'channelCategory', sortable: false,align:'center'},
        {title: '已入库未超期', field: 'inStoreNotExpire', sortable: false,align:'center'},
        {title: '已入库已超期', field: 'inStoreHasExpire', sortable: false,align:'center'},
        {title: '已入库合计', field: 'inStoreTotal', sortable: false,align:'center'},
        {title: '未入库未超期', field: 'notInStoreNotExpire', sortable: false,align:'center'},
        {title: '未入库已超期', field: 'notInStoreHasExpire', sortable: false,align:'center'},
        {title: '未入库合计', field: 'notInStoreTotal', sortable: false,align:'center'},
        {title: '不良品总计', field: 'badProductTotal', sortable: false,align:'center'},
        {title: '不良品完成入库率', field: 'badProductInStoreRate', sortable: false,align:'center'}
     ]],
     rowStyler: function(index,row){
        if (row.channelCategory == '总计'){//这里是判断哪些行
            return 'font-weight:bold;';  
        }
    },    
    striped: true,
    rownumbers: true
};

function editUser(index){
    // $('#datagridIndustry').datagrid('selectRow',index);// 关键在这里  
    // var row = $('#datagridIndustry').datagrid('getSelected');

 
}


var datagridIndustry = $('#datagridIndustry').datagrid(datagridOptions_industry);
var datagridChannel = $('#datagridChannel').datagrid(datagridOptions_channel);
$(function () {
    
	
	$.ajax({
	        url:'findBadProductAnaly',
	        type:'POST',
	        /*data:'',*/
	        dataType : "json", //返回数据形式为json
	        success : function(data) {
	        	  var datagridData_itemAttribute = {
	            		    'data': {
	            		        'records':
	            		        	data.rows,'totalCount':data.total
	            		            }
	          	  }
	        	  datagridIndustry.datagrid('loadData', datagridData_itemAttribute);
	        	  datagridChannel.datagrid('loadData', datagridData_itemAttribute);                  
	       },
	           error : function(errorMsg) {
	               alert("不好意思，图表请求数据失败啦!");
	                myChart.hideLoading();
	              }
	 });

 
 
});