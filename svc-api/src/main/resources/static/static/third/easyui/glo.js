//新增单据全局ID
var gloTabs=null;
var gloid = "11111";
var h_exceldata;
var h_excelsumdata;
var h_data;
//打开的单据的状态，默认10
var h_unitstatus = "10";
//打开的单据的制单人编码
var h_unitwcode = "";
/***
 * 用于码表的全局变量定义
 * @type {string}
 */
var filecode="";

var h_codeTable = "";
var h_cols1 = "0";
var h_cols2 = "0";
var h_cols3 = "0";
var h_cols4 = "0";
var h_cols5 = "0";
var h_cols6 = "0";
//当前选中的行
var h_rowidx = -1;
//当前选中的列
var h_cellidx = -1;

var h_startdate;
var h_enddate;
var myDate = new Date();
var h_year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
//alert("h_year:"+h_year);

var h_month = myDate.getMonth();    //获取当前月份(0-11,0代表1月)
//alert("h_month:"+h_month);
var h_day = myDate.getDate();         //获取当前日(1-31)
h_startdate = h_year + "-" + (h_month + 1) + "-1";
//构造一个日期对象：
var day = new Date(h_year, h_month, 0);
//alert("day:"+day);
//获取天数：
var daycount = day.getDate();
h_enddate = h_year + "-" + (h_month + 1) + "-" + daycount;

function findToEnter(event){
    var keyCode = event.keyCode?event.keyCode:event.which?event.which:event.charCode;
    if (keyCode ==13){
        //回车确认
        codeTableloaddata();
    }
}
function setstartdate(objid) {
    // alert("h_startdate:"+h_startdate);
    // document.getElementById(objid).value=h_startdate;
    $("#" + objid).val(h_startdate);
}
function setenddate(objid) {
    $("#" + objid).val(h_enddate);
    //document.getElementById(objid).value=h_enddate;

}
function StringToDate(str) {
//var str ='2012-08-12 23:13:15';
    str = str.replace(/-/g, "/");
    //alert("str:"+str);

    var dateFormater = new Date(str);
    return dateFormater;
}
/***
 * 保留小数方法
 * @param v
 * @param l
 */
function toFix(value,row,index)
{
   return Number(value).toFixed(2);
}
function ToIsoDate(strdate) {
    //alert("strdate:"+strdate);
    var d = StringToDate(strdate);
    //alert("d:"+d);
    //alert("h:"+d.getHours());
    //alert("m:"+d.getMinutes());
    //alert("getTimezoneOffset:"+d.getTimezoneOffset());

    d.setHours(d.getHours()-8, d.getMinutes() - d.getTimezoneOffset());
    //lert("nd:"+d);
    return d.toISOString()

    //return d.toISOString;//.toISOString();//toUTCString()
}
function GetDate() {
    var datex;
    var myDate = new Date();
    var year1 = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    var month1 = myDate.getMonth();    //获取当前月份(0-11,0代表1月)
    var day1 = myDate.getDate();         //获取当前日(1-31)
    datex = year1 + "-" + (month1 + 1) + "-" + day1;
    return datex;
}

function GetDateTime() {
    var datex;
    var myDate = new Date();
    var year1 = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    var month1 = myDate.getMonth();    //获取当前月份(0-11,0代表1月)
    var day1 = myDate.getDate();         //获取当前日(1-31)
    hour1 = myDate.getHours() < 10 ? "0" + myDate.getHours() : myDate.getHours();
    minute1 = myDate.getMinutes() < 10 ? "0" + myDate.getMinutes() : myDate.getMinutes();
    second1 = myDate.getSeconds() < 10 ? "0" + myDate.getSeconds() : myDate.getSeconds();
    datex = year1 + "-" + (month1 + 1) + "-" + day1 + " " + hour1 + ":" + minute1 + ":" + second1 + "";
    return datex;
}
function ClearCode(colname, colcode) {
    if ($("#" + colname) == null)
        return;
}
////获取时间
//$.post("/time",
//    {},
//    function (data) {
//        data = eval("(" + data + ")");
//        h_date1 = data.current.getDay();
//    });
/***
 * 用于表格的当前行
 * @type {null}
 */
var h_gridrow = null;

var h_defsql = "2>1";//默认条件
var h_filter = "2>1";//对应变量为codevalue
function codeTableloaddata() {
    try {

        var code1 = document.getElementById("filter_code1").value;
        var name1 = document.getElementById("filter_name1").value;
        h_filter = h_cols1 + " like '%" + code1 + "%' AND " + h_cols2 + " Like '%" + name1 + "%'";
        //alert(h_filter);
        $.ajax({
            type: 'POST',
            async: true,
            url: '../../api/FrontAppSelectCode',
            data: {
                table: h_codeTable,
                col1: h_cols1,
                col2: h_cols2,
                col3: h_cols3,
                col4: h_cols4,
                col5: h_cols5,
                col6: h_cols6,
                codevalue: " ((" + h_defsql + ") AND (" + h_filter + ")) "
            },
            success: function (data, textStatus) {
                var data1 = $.parseJSON(data);
                $('#codegrid').datagrid("loadData", data1);
            },
            complete: function (XMLHttpRequest, textStatus) {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    catch (err) {
        alert(+err.message);
    }
    finally {

    }
}
function codeTableloaddatag() {
    try {

        var code1 = document.getElementById("filter_code").value;
        var name1 = document.getElementById("filter_name").value;
        h_filter = h_cols1 + " like '%" + code1 + "%' AND " + h_cols2 + " Like '%" + name1 + "%'";
        //alert("条件:"+h_filter);
        $.ajax({
            type: 'POST',
            async: true,
            url: '../../api/FrontAppSelectCode',
            data: {
                table: h_codeTable,
                col1: h_cols1,
                col2: h_cols2,
                col3: h_cols3,
                col4: h_cols4,
                col5: h_cols5,
                col6: h_cols6,
                codevalue: " ((" + h_defsql + ") AND (" + h_filter + ")) "
            },
            success: function (data, textStatus) {
                var data1 = $.parseJSON(data);
                $('#codegridg').datagrid("loadData", data1);
            },
            complete: function (XMLHttpRequest, textStatus) {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    catch (err) {
        alert(+err.message);
    }
    finally {

    }
}
/***
 * 传递4个字段
 * @param fromtable
 * @param column1
 * @param column2
 * @param column3
 * @param column4
 * @param tocolumn1
 * @param tocolumn2
 * @param tocolumn3
 * @param tocolumn4
 * @param defsql
 * @constructor
 */
function OpenViewGrid(fromtable, column1, column2, column3, column4, tocolumn1, tocolumn2, tocolumn3, tocolumn4, defsql) {

    //alert(fromtable);
    OpenViewGrid6(fromtable, column1, column2, column3, column4, '0', '0', tocolumn1, tocolumn2, tocolumn3, tocolumn4, '', '', defsql)
}
/***
 * 传递5个字段
 * @param fromtable
 * @param column1
 * @param column2
 * @param column3
 * @param column4
 * @param column5
 * @param tocolumn1
 * @param tocolumn2
 * @param tocolumn3
 * @param tocolumn4
 * @param tocolumn5
 * @param defsql
 * @constructor
 */
function OpenViewGrid5(fromtable, column1, column2, column3, column4, column5, tocolumn1, tocolumn2, tocolumn3, tocolumn4, tocolumn5, defsql) {

    //alert(5);
    OpenViewGrid6(fromtable, column1, column2, column3, column4, column5, '0', tocolumn1, tocolumn2, tocolumn3, tocolumn4, tocolumn5, '', defsql)
}
/***
 * 打开码表选择框
 * @param c1
 * @param c2
 * @constructorOpenViewGridg
 */
function OpenViewGrids(fromtable, column1, column2, column3, column4, column5, column6, tocolumn1, tocolumn2, tocolumn3, tocolumn4, tocolumn5, tocolumn6, defsql) {

    h_codeTable = fromtable;
    h_cols1 = column1;
    h_cols2 = column2;
    h_cols3 = column3;
    h_cols4 = column4;
    h_cols5 = column5;
    h_cols6 = column6;

    //目标字段，如果目标字段为空，则默认为与码表字段一致
    h_tocols1 = tocolumn1;
    h_tocols2 = tocolumn2;
    h_tocols3 = tocolumn3;
    h_tocols4 = tocolumn4;
    h_tocols5 = tocolumn5;
    h_tocols6 = tocolumn6;

    h_defsql = defsql
    codeTableloaddatas();
    $('#sDialog').dialog('open');
}
function OpenViewGrid6(fromtable, column1, column2, column3, column4, column5, column6, tocolumn1, tocolumn2, tocolumn3, tocolumn4, tocolumn5, tocolumn6, defsql) {

    h_codeTable = fromtable;
    h_cols1 = column1;
    h_cols2 = column2;
    h_cols3 = column3;
    h_cols4 = column4;
    h_cols5 = column5;
    h_cols6 = column6;

    //目标字段，如果目标字段为空，则默认为与码表字段一致
    h_tocols1 = tocolumn1;
    h_tocols2 = tocolumn2;
    h_tocols3 = tocolumn3;
    h_tocols4 = tocolumn4;
    h_tocols5 = tocolumn5;
    h_tocols6 = tocolumn6;

    // h_defsql = defsql
    document.getElementById("filter_code1").value = "%";
    document.getElementById("filter_name1").value = "%";
    h_defsql = defsql;
    codeTableloaddata();
    $('#oDialog').dialog('open');
}

/****
 * 用于表格的弹出选择对话框
 * @param fromtable
 * @param selectRowg
 * @param 码表字段1
 * @param 码表字段2
 * @param 码表字段3
 * @param 码表字段4
 * @param 码表字段5
 * @param 码表字段6
 * @param 目标字段1
 * @param 目标字段2
 * @param 目标字段3
 * @param 目标字段4
 * @param 目标字段5
 * @param 目标字段6
 * @param 默认的Sql过滤语句
 * @constructor
 */
function codeTableloaddatas() {
    try {

        var code1 = document.getElementById("filter_code").value;
        var name1 = document.getElementById("filter_name").value;
        h_filter = h_cols1 + " like '" + code1 + "%' AND " + h_cols2 + " Like '" + name1 + "%'";
        $.ajax({
            type: 'POST',
            async: true,
            url: '../../api/FrontAppSelectCode',
            data: {
                table: h_codeTable,
                col1: h_cols1,
                col2: h_cols2,
                col3: h_cols3,
                col4: h_cols4,
                col5: h_cols5,
                col6: h_cols6,
                codevalue: " ((" + h_defsql + ") AND (" + h_filter + ")) "
            },
            success: function (data, textStatus) {
                var data1 = $.parseJSON(data);
                $('#codegrids').datagrid("loadData", data1);
                $('#codegrids').datagrid('selectAll')
            },
            complete: function (XMLHttpRequest, textStatus) {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    catch (err) {
        alert(+err.message);
    }
    finally {

    }
}
function OpenViewGridg(fromtable, rowidx, column1, column2, column3, column4, column5, column6, tocolumn1, tocolumn2, tocolumn3, tocolumn4, tocolumn5, tocolumn6, defsql) {

    h_codeTable = fromtable;
    h_rowidx = rowidx;
    h_cols1 = column1;
    h_cols2 = column2;
    h_cols3 = column3;
    h_cols4 = column4;
    h_cols5 = column5;
    h_cols6 = column6;

    //目标字段，如果目标字段为空，则默认为与码表字段一致
    h_tocols1 = tocolumn1;
    h_tocols2 = tocolumn2;
    h_tocols3 = tocolumn3;
    h_tocols4 = tocolumn4;
    h_tocols5 = tocolumn5;
    h_tocols6 = tocolumn6;


    document.getElementById("filter_code").value = "%";
    document.getElementById("filter_name").value = "%";
    h_defsql = defsql;
    if (fromtable != "b_cityport") {
        codeTableloaddatag();
    }
    else {

    }


    $('#gDialog').dialog('open');
}
/***
 * 打开日期对话框
 * @constructor
 */
function OpenDateForm(paradate) {
    h_cols1 = paradate;
    //var curdateValue = $("#" + paradate).val();
    //$('#dateControl').calendar('moveTo',new date(curdateValue));
    $('#dDialog').dialog('open');
}
function SetDateValue() {

    date1 = $("#dateControl");
    y = date1.calendar('options')['year'];
    m = date1.calendar('options')['month'];
    if (parseInt(m) <= 9) {
        m = "0" + m;
    }
    selectdate1 = date1.calendar('options')['current'];
    d = selectdate1.getDate();
    if (parseInt(d) <= 9) {
        d = "0" + d;
    }
    $("#" + h_cols1).val(y + "-" + m + "-" + d);
    $("#" + h_cols1).focus();
    $('#dDialog').dialog('close');
}
/***
 * 选择码表，用于grid表格
 * @param c1
 * @param c2
 * @constructor
 */
function setCellValue() {
    var selected = $("#codegridg").datagrid("getSelected");
    //{
    if (h_tocols1 == '') {
        // $('#' + h_cols1).val(selected.col1);
    }
    else {
        //alert(h_tocols1);
        //$('#' + h_tocols1).val(selected.col1);
        var editors = $('#gridView').datagrid('getEditors', editIndex);
        $(editors[h_tocols1].target).val(selected.col1);
        // $(editors[2].target).val(selected.col3);
        //alert('1:' + $(editors[h_tocols1].target).val());
        //alert('cell:' + selected.col1);
        //$('#codegridg').datagrid("loadData", data1);

    }
    if (h_tocols2 == '') {
        // $('#' + h_cols2).val(selected.col2);
    }
    else {
        // $('#' + h_tocols2).val(selected.col2);
        var editors = $('#gridView').datagrid('getEditors', h_rowidx);
        $(editors[h_tocols2].target).val(selected.col2);
    }
    if (h_tocols3 == '') {
        // $('#' + h_cols3).val(selected.col3);
    }
    else {
        // $('#' + h_tocols3).val(selected.col3);
        var editors = $('#gridView').datagrid('getEditors', h_rowidx);
        $(editors[h_tocols3].target).val(selected.col3);
    }
    if (h_tocols4 == '') {
        //  $('#' + h_cols4).val(selected.col4);
    }
    else {
        //$('#' + h_tocols4).val(selected.col4);
        var editors = $('#gridView').datagrid('getEditors', h_rowidx);
        $(editors[h_tocols4].target).val(selected.col4);
    }
    if (h_tocols5 == '') {
        //  $('#' + h_cols4).val(selected.col4);
    }
    else {
        //$('#' + h_tocols4).val(selected.col4);
        var editors = $('#gridView').datagrid('getEditors', h_rowidx);
        $(editors[h_tocols5].target).val(selected.col5);
    }
    if (h_tocols6 == '') {
        //  $('#' + h_cols4).val(selected.col4);
    }
    else {
        //$('#' + h_tocols4).val(selected.col4);
        var editors = $('#gridView').datagrid('getEditors', h_rowidx);
        $(editors[h_tocols6].target).val(selected.col6);
    }
    //}
    //alert('1');
    $('#codegridg').datagrid('loadData', {total: 0, rows: []});
    $('#gDialog').dialog('close');
}
function CloseCellDialog() {
    $('#codegridg').datagrid('loadData', {total: 0, rows: []});
    //$('#codegridg').datagrid("reloadFooter");
    $('#gDialog').dialog('close');
}
/***
 * 选择码表，用于Record表
 * @param c1
 * @param c2
 * @constructor
 */
function SetCodeValue() {
    var selected = $("#codegrid").datagrid("getSelected");
    if (h_tocols1 == "FHR") {
        $('#fhr').val(selected.col1);
        $('#fhrn').val(selected.col2);
        var hfrMs = selected.col2;
        hfrMs += "\r\nADDR:" + (typeof(selected.col3) == 'undefined' ? "" : selected.col3);
        hfrMs += "\r\nTEL:" + (typeof(selected.col4) == 'undefined' ? "" : selected.col4);
        hfrMs += "\r\nFAX:" + (typeof(selected.col5) == 'undefined' ? "" : selected.col5);

        $('#fhrdesc').val(hfrMs);
    }
    else if (h_tocols1 == "SHR") {
        $('#shr').val(selected.col1);
        $('#shrn').val(selected.col2);
        var shrMs = selected.col2;
        shrMs += "\r\nADDR:" + (typeof(selected.col3) == 'undefined' ? "" : selected.col3);

        shrMs += "\r\nTEL:" + (typeof(selected.col4) == 'undefined' ? "" : selected.col4);

        shrMs += "\r\nFAX:" + (typeof(selected.col5) == 'undefined' ? "" : selected.col5);

        $('#shrdesc').val(shrMs);
    }
    else if (h_tocols1 == "TZR") {
        $('#tzr').val(selected.col1);
        $('#tzrn').val(selected.col2);
        var tzrMs = selected.col2;
        tzrMs += "\r\nADDR:" + (typeof(selected.col3) == 'undefined' ? "" : selected.col3);
        tzrMs += "\r\nTEL:" + (typeof(selected.col4) == 'undefined' ? "" : selected.col4);

        tzrMs += "\r\nFAX:" + (typeof(selected.col5) == 'undefined' ? "" : selected.col5);


        $('#tzrdesc').val(tzrMs);
    }
    else if (h_tocols1 == "DLR") {
        $('#dlr').val(selected.col1);
        $('#dlrn').val(selected.col2);
        var dlrMs = selected.col2;
        dlrMs += "\r\nADDR:" + (typeof(selected.col3) == 'undefined' ? "" : selected.col3);
        dlrMs += "\r\nTEL:" + (typeof(selected.col4) == 'undefined' ? "" : selected.col4);
        dlrMs += "\r\nFAX:" + (typeof(selected.col5) == 'undefined' ? "" : selected.col5);
        $('#dlrdesc').val(dlrMs);
    }
    else {
        if (h_tocols1 == '') {
            $('#' + h_cols1).val(selected.col1);
        }
        else {
            //alert(h_tocols1);
            $('#' + h_tocols1).val(selected.col1);
        }
        if (h_tocols2 == '') {
            $('#' + h_cols2).val(selected.col2);
        }
        else {
            $('#' + h_tocols2).val(selected.col2);
        }
        if (h_tocols3 == '') {
            $('#' + h_cols3).val(selected.col3);
        }
        else {
            $('#' + h_tocols3).val(selected.col3);
        }
        if (h_tocols4 == '') {
            $('#' + h_cols4).val(selected.col4);
        }
        else {
            $('#' + h_tocols4).val(selected.col4);
        }
    }
    $('#oDialog').dialog('close');
}
/***
 * 选择码表，用于grid表格-----作废
 * @param c1
 * @param c2
 * @constructor
 */
function SetCodeValueg() {
    var selected = $("#codegridg").datagrid("getSelected");
    if (h_tocols1 == "container") {
        var editors = $('#gridView').datagrid('getEditors', h_rowidx);
        $(editors[h_cellidx].target).val(selected.col1);
        $(editors[2].target).val(selected.col3);
        //alert(selected.col1);
        //处理特殊字段
    }
    else if (h_tocols1 == "etype") {
        //处理特殊字段
        //alert(h_tocols1);
        var editors = $('#gridView').datagrid('getEditors', h_rowidx);
        $(editors[0].target).val(selected.col1);
        //alert(selected.col1);

        $(editors[1].target).val(selected.col2);
        $(editors[2].target).val(selected.col3);

        //alert(selected.col1);
    }
    else if (h_tocols1 == "ccode") {
        //处理特殊字段
        //alert(h_tocols1);
        var editors = $('#gridView').datagrid('getEditors', h_rowidx);
        $(editors[8].target).val(selected.col1);
        //alert(selected.col1);
        $(editors[9].target).val(selected.col2);
    }
    else if (h_tocols1 == "DLR") {
        //处理特殊字段

    }
    else {
        if (h_tocols1 == '') {
            $('#' + h_cols1).val(selected.col1);
        }
        else {
            //alert(h_tocols1);
            $('#' + h_tocols1).val(selected.col1);
        }
        if (h_tocols2 == '') {
            $('#' + h_cols2).val(selected.col2);
        }
        else {
            $('#' + h_tocols2).val(selected.col2);
        }
        if (h_tocols3 == '') {
            $('#' + h_cols3).val(selected.col3);
        }
        else {
            $('#' + h_tocols3).val(selected.col3);
        }
        if (h_tocols4 == '') {
            $('#' + h_cols4).val(selected.col4);
        }
        else {
            $('#' + h_tocols4).val(selected.col4);
        }
    }
    $('#gDialog').dialog('close');
}

/***
 * 委托单中，获取委托类型的值
 */
function getUtype() {
    var utypeValue = 0;
    var utypes = ["utype1", "utype2", "utype3", "utype4","utype5"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if(oitem == null ){
            continue;
        }
        oitemCodeValue = oitem.getAttribute("code");
        ck = oitem.checked;
        if (ck) {
            utypeValue += parseInt(oitemCodeValue);
        }
    }
    return utypeValue;
}
/***
 * 中信保审批
 * @returns {number}
 */
function getRadioShenpi() {
    var stypeValue = "";
    var stypes = ["shenpi1", "shenpi2"];
    for (i = 0; i < stypes.length; i++) {
        oitem = document.getElementById(stypes[i]);
        if (oitem == null) {
            // alert("-?"+utypes[i]);
            continue;
        }
        if (oitem.checked) {
            stypeValue = oitem.getAttribute("code");
            break;
        }
    }
    return stypeValue;
}
/***
 * 押金审批
 * @returns {number}
 */
function getRadioYajin() {
    var stypeValue = "";
    var stypes = ["sjh1", "sjh2"];
    for (i = 0; i < stypes.length; i++) {
        oitem = document.getElementById(stypes[i]);
        if (oitem == null) {
            // alert("-?"+utypes[i]);
            continue;
        }
        if (oitem.checked) {
            stypeValue = oitem.getAttribute("code");
            break;
        }
    }
    return stypeValue;
}

function setRaidoYajin(stypeValue) {
    var stypes = ["sjh1", "sjh2"];
    for (i = 0; i < stypes.length; i++) {
        oitem = document.getElementById(stypes[i]);
        //alert("oitem:"+oitem);
        if (oitem == null) {
            // alert("-?"+utypes[i]);
            continue;
        }
        ovalue = oitem.getAttribute("code");
        //alert("ovalue:"+ovalue);

        if (ovalue == stypeValue) {
            //alert('1');
            oitem.checked = true;
        }
    }
}
function setRaidoShenpi(stypeValue) {
    var stypes = ["shenpi1", "shenpi2"];
    for (i = 0; i < stypes.length; i++) {
        oitem = document.getElementById(stypes[i]);
        //alert("oitem:"+oitem);
        if (oitem == null) {
            // alert("-?"+utypes[i]);
            continue;
        }
        ovalue = oitem.getAttribute("code");
        //alert("ovalue:"+ovalue);

        if (ovalue == stypeValue) {
            //alert('1');
            oitem.checked = true;
        }
    }
}
/**
 * 计算合计行
 * */

function Count1(data1, data, col,colvalue) {
    var sum = 0;
    var jsonrowcount = data1.length;
    var jsonheader = "{\"total\":" + jsonrowcount + ",\"rows\":";
    var jsonbottom = ",\"footer\":[";
    var jsonsumrow = "{\"" + col + "\":" + colvalue + "}";
    jsonsumrow += "]}";
    var ndata = $.parseJSON(jsonheader + data + jsonbottom + jsonsumrow);
    return ndata;
}
/**
 * 合计某一列的值
 * @param data1
 * @param data
 * @param col
 * @constructor
 */
function Sum1(data1, data, col) {
    var sum = 0;
    for (var i = 0; i < data1.rows.length; i++) {
    	if(data1.rows[i][col] != undefined){
        sum = sum + Number(data1.rows[i][col]);
    	}
    }
    var jsonrowcount = data1.length;
    var jsonheader = "{\"total\":" + data1.total + ",\"rows\":";
    var jsonbottom = ",\"footer\":[";
    var jsonsumrow = "{\"" + col + "\":" + sum.toFixed(2) + "}";
    jsonsumrow += "]}";
    var ndata = $.parseJSON(jsonheader + data + jsonbottom + jsonsumrow);
    console.log(ndata)
    return ndata;
}

/**
 * 合计某2列的值
 * @param data1
 * @param data
 * @param col
 * @constructor
 */
function Sum2(data1, data, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10) {
    var sum1 = 0;
    var sum2 = 0;
    var sum4 = 0;
    var sum5 = 0;
    var sum6 = 0;
    var sum7 = 0;
    var sum8 = 0;
    var sum9 = 0;
    var sum10 = 0;
    
    for (var i = 0; i < data1.length; i++) {
        sum1 = sum1 + Number(data1[i][col1]);
        sum2 = sum2 + Number(data1[i][col2]);
        sum3 = sum3 + Number(data1[i][col3]);
        sum4 = sum4 + Number(data1[i][col4]);
        sum5 = sum5 + Number(data1[i][col5]);
        sum6 = sum6 + Number(data1[i][col6]);
        sum7 = sum7 + Number(data1[i][col7]);
        sum8 = sum8 + Number(data1[i][col8]);
        sum9 = sum9 + Number(data1[i][col9]);
        sum10 = sum10 + Number(data1[i][col10]);
    }
    var jsonrowcount = data1.length;
    var jsonheader = "{\"total\":" + data1.total + ",\"rows\":";
    var jsonbottom = ",\"footer\":[";
    var jsonsumrow = "{\"" + col1 + "\":" + sum1.toFixed(2) + ",\"" + col2 + "\":" + sum2.toFixed(2) + ",\"" + col3 + "\":" + sum3.toFixed(2) +",\"" + col4 + "\":" + sum4.toFixed(2) +",\"" + col5 + "\":" + sum5.toFixed(2) +",\"" + col6 + "\":" + sum6.toFixed(2) +",\"" + col7 + "\":" + sum7.toFixed(2) +",\"" + col8 + "\":" + sum8.toFixed(2) +",\"" + col9 + "\":" + sum9.toFixed(2) +",\"" + col10 + "\":" + sum10.toFixed(2) + "}";
    jsonsumrow += "]}";
    
    var ndata = $.parseJSON(jsonheader + h_data + jsonbottom + jsonsumrow);
    return ndata;
}

/**
 * 合计某3列的值
 * @param data1
 * @param data
 * @param col
 * @constructor
 */
function Sum2(data1, data, col1, col2, col3,col4, col5, col6,col7, col8, col9,col10,col11, col12, col13,col14, col15, col16,col17, col18, col19,col20, col21, col22, col23,col24, col25,col26) {
    var sum1 = 0;
    var sum2 = 0;
    var sum3 = 0;
    var sum4 = 0;
    var sum5 = 0;
    var sum6 = 0;
    var sum7 = 0;
    var sum8 = 0;
    var sum9 = 0;
    var sum10 = 0;
    
    var sum11 = 0;
    var sum12 = 0;
    var sum13 = 0;
    var sum14 = 0;
    var sum15 = 0;
    var sum16 = 0;
    var sum17 = 0;
    var sum18 = 0;
    var sum19 = 0;
    var sum20 = 0;
    var sum21 = 0;
    var sum22 = 0;
    var sum23 = 0;
    var sum24 = 0;
    var sum25 = 0;
    var sum26 = 0;
    for (var i = 0; i < data1.rows.length; i++) {
    	if(data1.rows[i][col1] != undefined){
        sum1 = sum1 + Number(data1.rows[i][col1]);
    	}
    	if(data1.rows[i][col2] != undefined){
            sum2 = sum2 + Number(data1.rows[i][col2]);
        	}
    	if(data1.rows[i][col3] != undefined){
            sum3 = sum3 + Number(data1.rows[i][col3]);
        	}
    	if(data1.rows[i][col4] != undefined){
            sum4 = sum4 + Number(data1.rows[i][col4]);
        	}
    	if(data1.rows[i][col5] != undefined){
            sum5 = sum5 + Number(data1.rows[i][col5]);
        	}
    	if(data1.rows[i][col6] != undefined){
            sum6 = sum6 + Number(data1.rows[i][col6]);
        	}
    	if(data1.rows[i][col7] != undefined){
            sum7 = sum7 + Number(data1.rows[i][col7]);
        	}
    	if(data1.rows[i][col8] != undefined){
            sum8 = sum8 + Number(data1.rows[i][col8]);
        	}
    	if(data1.rows[i][col9] != undefined){
            sum9 = sum9 + Number(data1.rows[i][col9]);
        	}
    	if(data1.rows[i][col10] != undefined){
            sum10 = sum10 + Number(data1.rows[i][col10]);
        	}
    	
    	if(data1.rows[i][col11] != undefined){
            sum11 = sum11 + Number(data1.rows[i][col11]);
        	}
        	if(data1.rows[i][col12] != undefined){
                sum12 = sum12 + Number(data1.rows[i][col12]);
            	}
        	if(data1.rows[i][col13] != undefined){
                sum13 = sum13 + Number(data1.rows[i][col13]);
            	}
        	if(data1.rows[i][col14] != undefined){
                sum14 = sum14 + Number(data1.rows[i][col14]);
            	}
        	if(data1.rows[i][col15] != undefined){
                sum15 = sum15 + Number(data1.rows[i][col15]);
            	}
        	if(data1.rows[i][col16] != undefined){
                sum16 = sum16 + Number(data1.rows[i][col16]);
            	}
        	if(data1.rows[i][col17] != undefined){
                sum17 = sum17 + Number(data1.rows[i][col17]);
            	}
        	if(data1.rows[i][col18] != undefined){
                sum18 = sum18 + Number(data1.rows[i][col18]);
            	}
        	if(data1.rows[i][col19] != undefined){
                sum19 = sum19 + Number(data1.rows[i][col19]);
            	}
        	if(data1.rows[i][col20] != undefined){
                sum20 = sum20 + Number(data1.rows[i][col20]);
            	}
        	if(data1.rows[i][col21] != undefined){
                sum21 = sum21 + Number(data1.rows[i][col21]);
            	}
            	if(data1.rows[i][col22] != undefined){
                    sum22 = sum22 + Number(data1.rows[i][col22]);
                	}
            	if(data1.rows[i][col23] != undefined){
                    sum23 = sum23 + Number(data1.rows[i][col23]);
                	}
            	if(data1.rows[i][col24] != undefined){
                    sum24 = sum24 + Number(data1.rows[i][col24]);
                	}
            	if(data1.rows[i][col25] != undefined){
                    sum25 = sum25 + Number(data1.rows[i][col25]);
                	}
            	if(data1.rows[i][col26] != undefined){
                    sum26 = sum26 + Number(data1.rows[i][col26]);
                	}
    }
    var jsonheader = "{\"total\":" + data1.total + ",\"rows\":";
    var jsonbottom = ",\"footer\":[";
    var jsonsumrow = "{\"" + col1 + "\":" + sum1.toFixed(2) + ",\"" + col2 + "\":" + sum2.toFixed(2) +  ",\"" + col3 + "\":" + sum3.toFixed(2) + ",\"" + col4 + "\":" + sum4.toFixed(2) + ",\"" + col5 + "\":" + sum5.toFixed(2) + ",\"" + col6 + "\":" + sum6.toFixed(2) + ",\"" + col7 + "\":" + sum7.toFixed(2) + ",\"" + col8 + "\":" + sum8.toFixed(2) + ",\"" + col9 + "\":" + sum9.toFixed(2) + ",\"" + col10 + "\":" + sum10.toFixed(2) +",\""+ col11 + "\":" + sum11.toFixed(2) + ",\"" + col12 + "\":" + sum12.toFixed(2) +  ",\"" + col13 + "\":" + sum13.toFixed(2) + ",\"" + col14 + "\":" + sum14.toFixed(2) + ",\"" + col15 + "\":" + sum15.toFixed(2) + ",\"" + col16 + "\":" + sum16.toFixed(2) + ",\"" + col17 + "\":" + sum17.toFixed(2) + ",\"" + col18 + "\":" + sum18.toFixed(2) + ",\"" + col19 + "\":" + sum19.toFixed(2) + ",\"" + col20 + "\":" + sum20.toFixed(2) +",\""+col21 + "\":" + sum21.toFixed(2) + ",\"" + col22 + "\":" + sum22.toFixed(2) +  ",\"" + col23 + "\":" + sum23.toFixed(2) + ",\"" + col24 + "\":" + sum24.toFixed(2) + ",\"" + col25 + "\":" + sum25.toFixed(2) +",\"" + col26 + "\":" + sum26.toFixed(2) +  "}";
    jsonsumrow += "]}";
    var ndata = $.parseJSON(jsonheader + h_data + jsonbottom + jsonsumrow);
    
    return ndata;
}
/***
 * 客户会员等级
 * @returns {number}
 */
function getRadioDengji() {
    var stypeValue = "";
    var utypes = ["birth1", "birth2", "birth3", "birth4"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            continue;
        }
        if (oitem.checked) {
            stypeValue = oitem.getAttribute("code");
            break;
        }
    }
    return stypeValue;
}
function setRadioDengji(utypeValue) {
    var utypes = ["birth1", "birth2", "birth3", "birth4"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            continue;
        }
        ovalue = oitem.getAttribute("code");
        if (ovalue == utypeValue) {
            oitem.checked = true;
        }
    }
}


/***
 * 客户信息，设置客户类型多重属性
 * @param utypeValue
 */
function setCcodesflage(sflageValue) {
    var sflages = ["sflage1", "sflage2", "sflage3", "sflage4","sflage5", "sflage6", "sflage7", "sflage8","sflage9","sflage10","sflage11","sflage12"];
    for (i = 0; i < sflages.length; i++) {
        oitem = document.getElementById(sflages[i]);
        if(oitem == null ){
            continue;
        }
        oitemCodeValue = oitem.getAttribute("code");
        if ((parseInt(sflageValue) & parseInt(oitemCodeValue)) == parseInt(oitemCodeValue)) {
            oitem.checked = true;
        }
        else {
            oitem.checked = false;
        }
    }
}


/***
 * 客户信息，获取客户类型多重属性
 */
function getCcodesflage() {
    var sflageValue = 0;
    var sflages = ["sflage1", "sflage2", "sflage3", "sflage4", "sflage5", "sflage6",
        "sflage7", "sflage8", "sflage9", "sflage10", "sflage11", "sflage12"];
    for (i = 0; i < sflages.length; i++) {
        oitem = document.getElementById(sflages[i]);
        if (oitem == null) {
            continue;
        }
        oitemCodeValue = oitem.getAttribute("code");
        ck = oitem.checked;
        if (ck) {
            sflageValue += parseInt(oitemCodeValue);
        }
    }
    return sflageValue;
}
/***
 * 客户列表，映射客户类型
 * @param sflageValue
 * @returns {string}
 */
function getCcodeflageCellText1(sflageValue)
{
    var rowText="";
    var sflagesText= ["外贸客户","同行(货代企业)","工厂","合资公司","海尔小微","电产CRF或CIF","电产FOB","境外客户","OBF"];
    var sflages = [2, 4, 8,16,32,64,128,256,512];
    for (i = 0; i < sflages.length; i++) {
        oitemCodeValue = parseInt(sflages[i]);
        //oitemCodeValue = oitem.getAttribute("code");
        if ((parseInt(sflageValue) & parseInt(oitemCodeValue)) == parseInt(oitemCodeValue)) {
            if(rowText.length==0)
            {
                rowText=sflagesText[i];
            }
            else {
                rowText+="/"+sflagesText[i];
            }
        }
        else {
            //  oitem.checked = false;
        }
    }
    return rowText;
}

/***
 * 服务商类型映射方法
 * @param sflageValue
 * @returns {string}
 */
function getCcodeflageCellText2(sflageValue)
{
    var rowText="";
    var sflagesText= ["外贸客户","船公司","海运代理","空运代理","拖车公司","报关行","报检公司","装卸公司","仓储公司","干线运输公司","综合服务"];
    var sflages = [2, 4, 8,16,32,64,128,256,512,1024,2048];
    for (i = 0; i < sflages.length; i++) {
        oitemCodeValue = parseInt(sflages[i]);
        //oitemCodeValue = oitem.getAttribute("code");
        if ((parseInt(sflageValue) & parseInt(oitemCodeValue)) == parseInt(oitemCodeValue)) {
           if(rowText.length==0)
           {
               rowText=sflagesText[i];
           }
            else {
               rowText+="/"+sflagesText[i];
           }
        }
        else {
          //  oitem.checked = false;
        }
    }
    return rowText;
}

/***
 * 委托单中，设置委托类型
 * @param utypeValue
 */
function setUtype(utypeValue) {
    // var utypeValue=18;
    var utypes = ["utype1", "utype2", "utype3", "utype4","utype5"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if(oitem == null ){
            continue;
        }
        oitemCodeValue = oitem.getAttribute("code");
        if ((parseInt(utypeValue) & parseInt(oitemCodeValue)) == parseInt(oitemCodeValue)) {
            oitem.checked = true;
        }
        else {
            oitem.checked = false;
        }
    }
}
/***
 * 获取业务类型
 */
function getStype() {
    var stypeValue = "";
    var stypes = ["stype1", "stype2", "stype3", "stype4", "stype5", "stype6", "stype7", "stype8", "stype9", "stype10"];
    for (i = 0; i < stypes.length; i++) {
        oitem = document.getElementById(stypes[i]);
        if (oitem == null) {
            // alert("-?"+utypes[i]);
            continue;
        }
        if (oitem.checked) {
            stypeValue = oitem.getAttribute("code");
            break;
        }
    }
    return stypeValue;
}
/***
 * 设置业务类型
 * @param stypeValue
 */
function setStype(stypeValue) {
    var stypes = ["stype1", "stype2", "stype3", "stype4", "stype5", "stype6", "stype7", "stype8", "stype9", "stype10"];
    for (i = 0; i < stypes.length; i++) {
        oitem = document.getElementById(stypes[i]);
        if (oitem == null) {
            // alert("-?"+utypes[i]);
            continue;
        }
        ovalue = oitem.getAttribute("code");
        if (ovalue == stypeValue) {
            oitem.checked = true;
        }
    }
}
/***
 * 客户单，获取客户类型的值
 */
function getCcode1() {
    var utypeValue = 0;
    var utypes = ["utype1", "utype2", "utype3", "utype4", "utype5", "utype6",
        "utype7", "utype8", "utype9", "utype10", "utype11", "utype12"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            // alert("-?"+utypes[i]);
            continue;
        }
        oitemCodeValue = oitem.getAttribute("code");
        ck = oitem.checked;
        if (ck) {
            utypeValue += parseInt(oitemCodeValue);
        }
    }
    return utypeValue;
}

function setFlageRadio(utypeValue) {
    var utypes = ["utype1", "utype2", "utype3", "utype4", "utype5", "utype6",
        "utype7", "utype8", "utype9", "utype10", "utype11", "utype12"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        ovalue = oitem.getAttribute("code");
        if (ovalue == stypeValue) {
            oitem.checked = true;
        }
    }
}
function getFlageRadio() {
    var stypeValue = "";
    var utypes = ["utype1", "utype2", "utype3", "utype4", "utype5", "utype6",
        "utype7", "utype8", "utype9", "utype10", "utype11", "utype12"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            continue;
        }
        else {
            if (oitem.checked) {
                stypeValue = oitem.getAttribute("code");
                break;
            }
        }
    }
    return stypeValue;
}


function setFlageRadio(utypeValue) {
    var utypes = ["utype1", "utype2", "utype3", "utype4", "utype5", "utype6",
        "utype7", "utype8", "utype9", "utype10", "utype11", "utype12"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            continue;
        }
        ovalue = oitem.getAttribute("code");
        if (ovalue == utypeValue) {
            oitem.checked = true;
        }
    }
}
function getCrtypeRadio() {
    var stypeValue = "";
    var utypes = ["cutype1", "cutype2", "cutype3", "cutype4"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            continue;
        }
        if (oitem.checked) {
            stypeValue = oitem.getAttribute("code");
            break;
        }
    }
    return stypeValue;
}
function setCrtypeRadio(utypeValue) {
    var utypes = ["cutype1", "cutype2", "cutype3", "cutype4"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            continue;
        }
        ovalue = oitem.getAttribute("code");
        if (ovalue == utypeValue) {
            oitem.checked = true;
        }
    }
}
/**
 *客户/服务商 性质类型
 */
function getScope() {
    var stypeValue = "";
    var utypes = ["scope1", "scope2", "scope3"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            continue;
        }
        if (oitem.checked) {
            stypeValue = oitem.getAttribute("code");
            break;
        }
    }
    return stypeValue;
}
function setScope(utypeValue) {
    var utypes = ["scope1", "scope2", "scope3"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            continue;
        }
        ovalue = oitem.getAttribute("code");
        if (ovalue == utypeValue) {
            oitem.checked = true;
        }
    }
}

/***
 * 客户单，设置客户类型
 * @param utypeValue
 */
function setCcode1(utypeValue) {
    // var utypeValue=18;
    var utypes = ["utype1", "utype2", "utype3", "utype4", "utype5", "utype6",
        "utype7", "utype8", "utype9", "utype10", "utype11", "utype12"];
    for (i = 0; i < utypes.length; i++) {
        oitem = document.getElementById(utypes[i]);
        if (oitem == null) {
            continue;
        }
        // alert("oitem:" + oitem);
        if (oitem == null) {
            //alert("-?"+utypes[i]);
            continue;
        }
        else {
            oitemCodeValue = oitem.getAttribute("code");
            if ((parseInt(utypeValue) & parseInt(oitemCodeValue)) == parseInt(oitemCodeValue)) {
                oitem.checked = true;
            }
            else {
                oitem.checked = false;
            }
        }
    }
}
function GetID() {
    //alert('11112');
    var guid = "";
    for (var i = 1; i <= 32; i++) {
        var n = Math.floor(Math.random() * 16.0).toString(16);
        guid += n;
        if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
            guid += "-";
    }
    guid += "";
    //alert(guid);
    return guid;
}
function getRow(table, col, filter, orderby) {
    var bcValue;
    $.ajax({
        type: 'POST',
        async: false,
        url: '../../api/FrontAppSelect',
        data: {
            table: table,
            cols: col,
            filter: filter,
            limitvtype: 'X9_000000_00',
            limitbcode: '0',
            orderby: orderby
        },
        success: function (data, textStatus) {
            bcValue = $.parseJSON(data);
            //alert("F:"+bcValue);

        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    //alert(bcValue);
    return bcValue;
}
function getRow2(table, col, filter, orderby) {
    var bcValue;
    $.ajax({
        type: 'POST',
        async: false,
        url: '../../api/FrontAppSelect2',
        data: {
            table: table,
            cols: col,
            filter: filter,
            limitvtype: 'X9_000000_00',
            limitbcode: '0',
            orderby: orderby
        },
        success: function (data, textStatus) {
            bcValue = $.parseJSON(data);
            //alert("F:"+bcValue);

        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    //alert(bcValue);
    return bcValue;
}
function getViewData(table, col, filter, orderby,async) {
    var bcValue;
    $.ajax({
        type: 'POST',
        async: async,
        url: '../../api/FrontAppSelect2',
        data: {
            table: table,
            cols: col,
            filter: filter,
            limitvtype: 'X9_000000_00',
            limitbcode: '0',
            orderby: orderby
        },
        success: function (data, textStatus) {
            bcValue = $.parseJSON(data);
            //alert("F:"+bcValue);

        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    //alert(bcValue);
    return bcValue;
}
/***
 * 发送消息
 * @param tobcode
 * @param tobname
 * @param towcode
 * @param towname
 * @param info
 * @param url
 * @constructor
 */
function SendMessage(tobcode, tobname, towcode, towname, info, url) {
    $.post("../api/set",
        {
            'frombcode': h_bcode,
            'fromwcode': h_wcode,
            'frombname': h_bname,
            'fromwname': h_wname,
            'tobcode': tobcode,
            'tobname': tobname,
            'towcode': towcode,
            'towname': towname,
            'info': info,
            'url': url
        },
        function (data) {
            data = eval("(" + data + ")");
        });
}

/***
 * 发送消息，由于路径不一致，需要自己制定路径
 * @param sendurl
 * @param tobcode
 * @param tobname
 * @param towcode
 * @param towname
 * @param info
 * @param url
 * @constructor
 */
function SendMessageAsUrl(sendurl, tobcode, tobname, towcode, towname, info, url) {
    $.post(sendurl,
        {
            'frombcode': h_bcode,
            'fromwcode': h_wcode,
            'frombname': h_bname,
            'fromwname': h_wname,
            'tobcode': tobcode,
            'tobname': tobname,
            'towcode': towcode,
            'towname': towname,
            'info': info,
            'url': url
        },
        function (data) {
        });
}
function BindingGridView(table, col, filter, orderby,async,gridViewid) {
    var dataString;
    $.ajax({
        type: 'POST',
        async: async,
        url: '../../api/FrontAppSelect2',
        data: {
            table: table,
            cols: col,
            filter: filter,
            limitvtype: 'X9_000000_00',
            limitbcode: '0',
            orderby: orderby
        },
        success: function (data, textStatus) {
             //dataViewSet = $.parseJSON(data);
            dataString=data;// JSON.stringify($.parseJSON(data)); //可以将json对象转换成json对符串
           // var data1 = $.parseJSON(data);

        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    return dataString;
}
/***
 * 设置按钮是否可以操作
 * @param w1
 * @param w2
 * @param status1
 * @constructor
 */
function SetButtonStatus(w1, w2, status1) {

    //alert("w1:"+w1);
    //alert("w2:"+w2);
    //alert("status1:"+status1);

    //判断，如果制单人与当前单据制作人不一致，需要禁用按钮
    if(w1!=w2)
    {
        ButtonStatus(true)
    }
    else {
        if(status1 != "10" && status1!="11")
        {
            ButtonStatus(true)
        }
        else {
            ButtonStatus(false);
        }
    }
    //if (w1 != w2 && status1 != "10" && h_unitstatus != "11") {
    //
    //}
    ///**
    // * //检测进入人员是否是制单人,返回不允许修改
    // */
    //
    //if (status1 != "10" && status1 != "11") {
    //    $('#savebtn').linkbutton({
    //        disabled: true
    //    });
    //    $('#saveasbtn').linkbutton({
    //        disabled: true
    //    });
    //    $('#delbtn').linkbutton({
    //        disabled: true
    //    });
    //    $('#ratifybtn').linkbutton({
    //        disabled: true
    //    });
    //    $('#printbtn').linkbutton({
    //        disabled: true
    //    });
    //
    //    if ($('#addbtng') != null) {
    //        $('#addbtng').linkbutton({
    //            disabled: true
    //        });
    //    }
    //    if ($('#addbtng') != null) {
    //        $('#savebtng').linkbutton({
    //            disabled: true
    //        });
    //    }
    //    if ($('#addbtng') != null) {
    //        $('#delbtng').linkbutton({
    //            disabled: true
    //        });
    //    }
    //}
}

function ButtonStatus(dsip)
{
    $('#savebtn').linkbutton({
        disabled: dsip
    });
    $('#saveasbtn').linkbutton({
        disabled: dsip
    });
    $('#delbtn').linkbutton({
        disabled: dsip
    });
    $('#ratifybtn').linkbutton({
        disabled: dsip
    });
    $('#printbtn').linkbutton({
        disabled: dsip
    });
    $('#invoice').linkbutton({
        disabled: dsip
    });
    if ($('#addbtng') != null) {
        $('#addbtng').linkbutton({
            disabled: dsip
        });
    }
    if ($('#savebtng') != null) {
        $('#savebtng').linkbutton({
            disabled: dsip
        });
    }
    if ($('#delbtng') != null) {
        $('#delbtng').linkbutton({
            disabled: dsip
        });
    }
    if ($('#delbtng2') != null) {
        $('#delbtng2').linkbutton({
            disabled: dsip
        });
    }
    if ($('#quobtng1') != null) {
        $('#quobtng1').linkbutton({
            disabled: dsip
        });
    }
    if ($('#abolish') != null) {
        $('#abolish').linkbutton({
            disabled: dsip
        });
    }
    if ($('#importbutton') != null) {
        $('#importbutton').linkbutton({
            disabled: dsip
        });
    }

    //if ($('#addbtng') != null) {
    //    $('#quobtng2').linkbutton({
    //        disabled: true
    //    });
    //}
    //if ($('#addbtng') != null) {
    //    $('#quobtng3').linkbutton({
    //        disabled: true
    //    });
    //}
    //if ($('#addbtng') != null) {
    //    $('#quobtng4').linkbutton({
    //        disabled: true
    //    });
    //}
    //if ($('#addbtng') != null) {
    //    $('#quobtng5').linkbutton({
    //        disabled: true
    //    });
    //}
    if ($('#quobtng6') != null) {
        $('#quobtng6').linkbutton({
            disabled: dsip
        });
    }
}
//弹出窗口
function autoAlt(mig) {
    $.messager.alert('提示', mig);
}
//function autoAlt(msg, icon, tm) {
//    var interval;
//    var time = 1000;
//    var x;
//    if (null == tm || '' == tm) {
//        x = Number(2);
//    } else {
//        x = Number(tm);
//    }
//    //
//    if (null == icon || '' == icon) {
//        icon = "infoSunnyIcon";
//    }
//    $.messager.alert(' ', '<font size=\"2\" color=\"#666666\"><strong>' + msg + '</strong></font>', icon, function () {
//    });
//    $(".messager-window .window-header .panel-title").append("系统提示（" + x + "秒后自动关闭）");
//    interval = setInterval(fun, time);
//    function fun() {
//        --x;
//        if (x == 0) {
//            clearInterval(interval);
//            $(".messager-body").window('close');
//        }
//        $(".messager-window .window-header .panel-title").text("");
//        $(".messager-window .window-header .panel-title").append("系统提示（" + x + "秒后自动关闭）");
//    }
//}

function autoAlt15(msg, icon, tm) {
    var interval;
    var time = 1000;
    var x;
    if (null == tm || '' == tm) {
        x = Number(15);
    } else {
        x = Number(tm);
    }
    //
    if (null == icon || '' == icon) {
        icon = "infoSunnyIcon";
    }
    $.messager.alert(' ', '<font size=\"2\" color=\"#666666\"><strong>' + msg + '</strong></font>', icon, function () {
    });
    $(".messager-window .window-header .panel-title").append("系统提示（" + x + "秒后自动关闭）");
    interval = setInterval(fun, time);
    function fun() {
        --x;
        if (x == 0) {
            clearInterval(interval);
            $(".messager-body").window('close');
        }
        $(".messager-window .window-header .panel-title").text("");
        $(".messager-window .window-header .panel-title").append("系统提示（" + x + "秒后自动关闭）");
    }
}
/***
 * 根据客户编码，获取客户的账期信息
 */
function getCcodeAccInfo(ccodeValue) {
    var bcValue = null;
    var bcs = "";
    $.ajax({
        type: 'POST',
        async: false,
        url: '../../api/FrontAppSelect',
        data: {
            table: "b_userccode",
            cols: "emonth as col01,accdays as col02",
            filter: "ccode='" + ccodeValue + "'",
            limitvtype: 'X9_000000_00',
            limitbcode: '0',
            orderby: " ccode asc"
        },
        success: function (data, textStatus) {
            bcValue = $.parseJSON(data);
            if (bcValue == null || bcValue == "" || bcValue.length == 0) {
                autoAlt("没有找到此客户信息");
                bcs = "";
            }
            else {
                bcs = bcValue[0].col01 + ";" + bcValue[0].col02;
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    return bcs;
}
/***
 * 在表中插入一行数据
 * @constructor
 */
function InsertUnit(table,cols,values,opurl)
{
    var bcValue=0;
    $.ajax({
        type: 'POST',
        async: false,
        url: opurl,
        data: {
            table: table,
            cols: cols,
            filter: values
        },
        success: function (data, textStatus) {
            if(data=="1")
            {
                bcValue="1";
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    return bcValue;
}
function InsertAttr(values)
{
    var cols="id,uid,uidx,utype,wcode,wname,bcode,bname,fid,fname,predate,ucode,del";
    return InsertUnit("b_file",cols,values,"../../api/FrontAppCommInsert");
}
function UpdateUnit(table, col, filter) {
    var bcValue = null;
    $.ajax({
        type: 'POST',
        async: false,
        url: '../../api/FrontAppCommUpdate',
        data: {
            table: table,
            cols: col,
            filter: filter
        },
        success: function (data, textStatus) {
            if (data >= 1) {
                bcValue = data;
            }
            else if (data == 0) {
                bcValue = 0;
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    return bcValue;
}


/***
 * 对于码表，删除名字的同时，自动清空编码的值
 * @param srccol
 * @param tagcol
 */
function clearValue(srccol, tagcol) {
    if ($("#" + srccol) == null)
        return;
    if ($("#" + tagcol) == null)
        return;
    $("#" + tagcol).val("");

}
/***
 * 对于码表，删除名字的同时，自动清空编码的值
 * @param srccol
 * @param tagcol
 */
function deleteValue(srccol, tagcol) {
    if ($("#" + srccol) == null)
        return;
    if ($("#" + tagcol) == null)
        return;
    var key = document.getElementsByTagName('body')[0];
    key.onkeydown = function (ev) {

        if (ev.keyCode == 8
            || ev.keyCode == 46) {
            $("#" + srccol).val("");
            $("#" + tagcol).val("");
        }
    };
}
function GetGridColumns(datagridID)
{
    $("#"+datagridID).datagrid('getColumnFields');
    for(var j = 0 ; j < cols.length + 1; j++) {
        value = cols[j];
        alert(value);
    }
    //for(var i = 0 ; i < data.length + 1 ; i++ ){
    //    alert("!!!!!!!!!0" + cols[j]);
    //    var indicatorStartValue0 =data.value;
    //
    //    alert("!!!!!!!!!11"+indicatorStartValue0);
    //    html += "<set value='"+indicatorStartValue0 +"'/>";
    //}
}
/**
 * 键盘Enter触发检索功能
 */
function onEnter(){
    if(window.event.keyCode == 13){
        SearchUnit()
    }
}
//ajax加载
function progress() {
    var win = $.messager.progress({
        title: '提示：',
        msg: '正在处理.......'
    });

}
//获取全部渠道
function channelsInfoList(key){
    $("#"+key).combobox({
        url: '/purchase/product/channelsInfoList',
        valueField: "id",
        textField: "channelcode",
        required: false,
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
}
function channels(key){
    $("#"+key).combobox({
        url: '/purchase/product/channelsList',
        valueField: "id",
        textField: "channelcode",
        required: false,
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
}
function channelsInfoListNo(key){
    $("#"+key).combobox({
        url: '/purchase/product/channelsInfoList',
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
}
function channelsNo(key){
    $("#"+key).combobox({
        url: '/purchase/product/channelsList',
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
}
function ajaxLoadEnd() {
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}