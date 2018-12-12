/**
 * Created by Administrator on 2016/5/9.
 * Page全局变量获取
 */
/***
 * 异步加载，造成表格无法加载数据
 */
$.ajaxSetup({
    async: false
});
//alert(111111111);
//当前单据的制单人
var h_curwcode = "";
//当前单据的状态
var h_curstatus = "10";

//默认值
var h_wcode;
var h_bcode = "000110";
var h_wname;
var h_bname;
var h_ccode;
var h_date;
var h_status = "新建";

//列表
var h_wcodes;
var h_bcodes;
var h_ccodes;
var h_statues = [{
    "scode": 10,
    "sname": "新建"
}, {
    "scode": 11,
    "sname": "修改"
}, {
    "scode": 20,
    "sname": "待审"
}, {
    "scode": 21,
    "sname": "驳回"
}, {
    "scode": 70,
    "sname": "执行"
}];



////获取人员列表
//$.post("../api/FrontAppWcodes",
//    {
//        wcode: "%",
//        wname: "%"
//    },
//    //返回赋值
//    function (data) {
//        h_wcodes = $.parseJSON(data);
//    });
////所有客户查询
//$.post("../api/FrontAppCcodes",
//    {
//        ctype: '0'
//    },
//    //返回赋值
//    function (data) {
//        h_ccodes = $.parseJSON(data);
//    });
////所有部门查询
//$.post("../api/FrontAppBcodes",
//    {
//        bcode: "%",
//        bname: "%"
//    },
//    //返回赋值
//    function (data) {
//        h_bcodes = $.parseJSON(data);
//    });

/****
 * 发送消息给指定的人
 * @param 消息接收人编码
 * @param 消息接收人名称
 * @param 消息正文
 * @constructor
 */
function SendMessage(tobcode, tobname, messageinfo) {
    $.post("api/set",
        {
            'tobcode': tobcode,
            'tobname': tobname,
            'info': messageinfo
        },
        function (data) {
            data = eval("(" + data + ")");
        });
}
$.ajaxSetup({
    async: true
});

/***
 * 根据当前日期，加上一定天数，得到新日期
 * @param date1
 * @param days
 * @returns {Date}
 */
function addDate(ndate, num) {

    var translateDate = "", dateString = "", monthString = "", dayString = "";
    translateDate = ndate.replace("-", "/").replace("-", "/");
    var newDate = new Date(translateDate);
    newDate = newDate.valueOf();
    newDate = newDate + num * 24 * 60 * 60 * 1000;
    newDate = new Date(newDate);
    //如果月份长度少于2，则前加 0 补位
    if ((newDate.getMonth() + 1).toString().length == 1) {
        monthString = 0 + "" + (newDate.getMonth() + 1).toString();
    } else {
        monthString = (newDate.getMonth() + 1).toString();
    }
    //如果天数长度少于2，则前加 0 补位
    if (newDate.getDate().toString().length == 1) {
        dayString = 0 + "" + newDate.getDate().toString();
    } else {
        dayString = newDate.getDate().toString();
    }
    dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
    return dateString;


}

/**
 * 比较两个时间段，如果date1>date2,返回1，date1<date2，返回-1，相等，返回0
 * @param date1
 * @param date2
 * @returns {number}
 */
function dateCompare(date1, date2) {
    date1 = date1.replace(/\-/gi, "/");
    date2 = date2.replace(/\-/gi, "/");
    var time1 = new Date(date1).getTime();
    var time2 = new Date(date2).getTime();
    if (time1 > time2) {
        return 1;
    } else if (time1 == time2) {
        return 0;
    } else {
        return -1;
    }
}
//alert(dateCompare("2011-06-27", "2011-06-28"));
function Getdate() {
    var myDate = new Date();
    var fyear = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    var hmonth = myDate.getMonth();    //获取当前月份(0-11,0代表1月)
    var hday = myDate.getDate();         //获取当前日(1-31)
    cdate = fyear + "-" + (hmonth + 1) + "-" + hday;
    return cdate;
}