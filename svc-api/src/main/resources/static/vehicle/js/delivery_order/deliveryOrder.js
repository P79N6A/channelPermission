var selections = selections || {
    length: function () {
        var len = 0;
        for (var key in selections) {
            var obj = selections[key]
            if (typeof obj == 'function') {
                continue
            }
            len++
        }
        return len
    },
    sum: function () {
        var val = 0;
        for (var key in selections) {
            var obj = selections[key]
            if (typeof obj == 'function') {
                continue
            }
            val += obj.price * obj.num
        }
        return parseFloat(val.toFixed(2))
    },
    volume: function () {
        var val = 0;
        for (var key in selections) {
            var obj = selections[key]
            if (typeof obj == 'function') {
                continue
            }
            val += obj.volume * obj.num
        }
        return parseFloat(val.toFixed(2))
    }
};
var standardMin = 0;
var standardMax = 0;

var validateCount = function (index, obj, changeRow) {
    var row = $("#datagrid_r6relateManage").datagrid("getRows")[index]
    var val = obj.value
    if (!val || val < 1) {
        val = 1;
    }
    obj.value = val
    if (row) {
        if (changeRow) {
            row.count = val
        }
    } else {
        row = {}
        var code = $("#itemCode").val()
        row.price = selections[code].price
    }
    $('[sumPrice]').html(parseFloat(parseFloat(row.price) * val).toFixed(2))
}

var decrement = function (index, obj, changeRow) {
    var row = $("#datagrid_r6relateManage").datagrid("getRows")[index]
    var val = $(obj).next().val() * 1 - 1
    if (val < 1) {
        val = 1
    }
    $(obj).next().val(val);
    if (row) {
        if (changeRow) {
            row.count = val
        }
    } else {
        row = {}
        var code = $("#itemCode").val()
        row.price = selections[code].price
    }
    $('[sumPrice]').html(parseFloat(parseFloat(row.price) * val).toFixed(2))
}

var increment = function (index, obj, changeRow) {
    var row = $("#datagrid_r6relateManage").datagrid("getRows")[index]
    var val = $(obj).prev().val() * 1 + 1
    // row.count = val
    if (row) {
        if (changeRow) {
            row.count = val
        }
    } else {
        row = {}
        var code = $("#itemCode").val()
        console.log(selections)
        console.log(code)
        row.price = selections[code].price
    }
    $('[sumPrice]').html(parseFloat(parseFloat(row.price) * val).toFixed(2))
    $(obj).prev().val(val);
}

var count = function (val, row, index) {
    val = val || 1
    row.count = val
    return '<div rowno="0" style="height: 22px;margin: 0 auto;overflow: hidden;text-align: left;width: 86px;">' +
        '<a rowno="0" style="border: 1px solid #DDDDDD;background: none repeat scroll 0 0 #FFFFFF;border: 1px solid #DDDDDD;display: block;float: left;height: 19px;line-height: 19px;margin-top: 0;overflow: hidden;text-align: center;width: 19px;"' +
        '                        href="javascript:;" onclick="decrement(' + index + ',this,true)">-</a>' +
        '<input  rowno="0" class="quantity-text" value="' + val + '"' +
        '                        style="float: left;border: 1px solid #DDDDDD;border-left:none;border-right:none; height: 19px;;overflow: hidden;padding-top: 2px; text-align:center;width: 34px;"' +
        '                        onblur="validateCount(' + index + ',this,true)">' +
        '<a rowno="0" style="border: 1px solid #DDDDDD;background: none repeat scroll 0 0 #FFFFFF;border: 1px solid #DDDDDD;display: block;float: left;height: 19px;line-height: 19px;margin-top: 0;overflow: hidden;text-align: center;width: 19px;"' +
        '                        href="javascript:void(0);"' +
        '                        onclick="increment(' + index + ',this,true)">+</a>' +
        '                </div>'
}

var price = function (val) {
    return '￥' + parseFloat(val).toFixed(2)
}

var volume = function (val) {
    return val
}

var bateRate = function (val) {
    return '￥' + parseFloat(val).toFixed(2)
}

var productName = function (val) {
    return '<a href="javascript:void(0);" target="_blank">' + val + '</a>'
}

var productGroup = {};

var addCart = function (index, code) {
    var carCode = $("#carCode").combobox("getValue");
    if (!carCode) {
        alert("请先选择车型！")
        return
    }
    var showPrice
    var sumPrice
    var count
    var name
    var productGroupCode
    if (!code) {
        var row = $("#datagrid_r6relateManage").datagrid("getRows")[index]
    	if(row.baseCode != $("select[comboname='baseCode']").combobox("getValue") ||
    			row.deliveryToCode != $("select[comboname='shopSku']").combobox("getValue")){
    		alert("送达方或生产基地不匹配,请重新查询物料信息");
    		return;
    	}
        showPrice = row.price
        sumPrice = row.price * row.count
        count = row.count
        name = row.materielName
        productGroupCode = row.productGroupCode
    } else {
        showPrice = selections[code].price
        count = selections[code].num
        sumPrice = parseInt(showPrice) * parseFloat(count)
        name = selections[code].name
        productGroupCode = selections[code].productGroupCode
    }
    if(showPrice=='0'){
    	alert("开票价为0不能加入整车!");
        return;
    }
    $('#show_price').text(parseFloat(showPrice).toFixed(2));
    $("[price]").text(parseFloat(showPrice).toFixed(2))
    $('[sumPrice]').text(parseFloat(sumPrice).toFixed(2))
    $("#rowIndex").val(index)
    $("#itemCode").val(code)
    $("[count]").val(count)
    $("[materielName]").text(name)
    var temp = productGroup[productGroupCode]
    $("#paymentCode").textbox({value: temp.paymentCode, readonly: true, icons: []})
    $('#paymentCode').textbox("setText", temp.paymentName)
    $('#addForm_r6relateManage').addClass('.add');
    $('#addForm_r6relateManage').removeClass('.edit');
    $('#addDlg_r6relateManage').dialog({'title': '确认信息'});
    $('#addDlg_r6relateManage').dialog('open');
}

function zcCart(val, row, index) {
    return '<input type="button" class="zcCart-btn" onclick="addCart(' + index + ')" value="加入整车"/>'
}

var tabledata = {
    "total": 1,
    "rows": [{
        "productCode": "/static/img/GD0M9D00W-01.jpg",
        "itemCode": "",
        "productName": "BCD-649WADV",
        "productGroupName": "冰箱",
        "productGroupCode": "AA",
        "brand": "海尔",
        "price": "3241.98",
        "field6": "0.17",
        "count": 1,
        "sold": "385",
        "max": "50",
        "base": "CQ10",
        "baseCount": ""
    }]
}
var tabDg1 = {
    fit: true,
    fitColumns: true,
    columns: [[
        {title: '产品', field: 'materielName', sortable: true, align: 'center', width: 300},
        {title: '小单号', field: 'itemNo', sortable: true, align: 'center', width: 300},
        // {title: '扣点', field: 'bateRate', sortable: true, align: 'center', width: 200},
        {
            title: '开票价',
            field: 'unitPrice',
            sortable: true,
            align: 'center',
            width: 200,
            formatter: function (val) {
                return parseFloat(val).toFixed(2)
            }
        },
        // {title: '台返', field: 'field5', sortable: true, align: 'center', width: 200},
        {title: '数量', field: 'qty', sortable: true, align: 'center', width: 300},
        {
            title: '总价', field: 'amount', sortable: true, align: 'center', width: 300, formatter: function (val) {
            return parseFloat(val).toFixed(2)
        }
        }
    ]],
    striped: true
}

var datagridOptions_r6relateManage = {
    url: "/vehicle/productList",
    fit: true,
    fitColumns: true,
    singleSelect: true,
    loadMsg: '正在加载信息...',
    columns: [[
		{title: '基地编码', field: 'baseCode',hidden:true, align: 'center', width: 300},
		{title: '送达发编码', field: 'deliveryToCode',hidden:true, align: 'center', width: 300},
        {title: '物料编码', field: 'materielCode', sortable: true, align: 'center', width: 300},
        {title: '产品名称', field: 'materielName', sortable: true, align: 'center', formatter: productName, width: 300},
        {title: '产品组', field: 'productGroupName', sortable: true, align: 'center', width: 300},
        {title: '品牌', field: 'brandName', sortable: true, align: 'center', width: 300},
        {title: '体积（m³）', field: 'volume', sortable: true, align: 'center', width: 300},
        {title: '开票价', field: 'price', sortable: true, align: 'center', formatter: price, width: 300},
        // {title: '扣点', field: 'bateRate', sortable: true, align: 'center', formatter: bateRate,width:200},
        {title: '数量', field: 'count', sortable: true, align: 'center', formatter: count, width: 300},
        // {title: '最大量', field: 'max', sortable: true, align: 'center',width:200},
        {title: '基地', field: 'baseName', sortable: true, align: 'center', width: 200},
        {title: '操作', field: 'option', sortable: true, align: 'center', formatter: zcCart, width: 300}
    ]],
    toolbar: '#datagridToolbar_r6relateManage',
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 20, 30],
    rownumbers: true,
    autoRowHeight: true,
    nowrap: false,
    onLoadSuccess:function(data){
        var pg = $("select[comboname='productGroup']").combobox("getValue");
        var item = $("#itemId").textbox("getValue");
        if (pg!=""||item!=""){
       if (data.total==0) {
           alert("中心工厂型号优先级无效(请联系供应链在OES系统维护)")}
       }
    }
};

function chWhCode(code){
	var row=$("#88code").combobox("getData");
	if(code==''){
		$('#88code').combobox('select','');
	}
	for(var i=0;i<row.length;i++){
		if(row[i].value==code){
			$('#88code').combobox('select',row[i].value)
		}
	}
}

function ch88Code(code){
	var row=$("#whCode").combobox("getData");
	if(code==''){
		$('#whCode').combobox('select','');
	}
	for(var i=0;i<row.length;i++){
		if(row[i].value==code){
			$('#whCode').combobox('select',row[i].value)
		}
	}
}

function getSelections(index, row) {
	var productGroupStr;
	if(row.productGroup == undefined){
		productGroupStr = row.productGroupCode;
	} else {
		productGroupStr = row.productGroup;
	}
    selections[row.materielCode] = {
        materielId: row.materielId,
        code: row.materielCode,
        price: row.price,
        name: row.materielName,
        num: row.count,
        volume: row.volume,
        index: index,
        productGroupCode: productGroupStr
    };
    eleShopCart.querySelector("span").innerHTML = selections.length();
    sethtmls();
}

function center(sendTo) {
    if (sendTo) {
        $.ajax({
            url: '/vehicle/center',
            data: {deliveryToCode: sendTo},
            dataType: "json",
            type: 'post',
            success: function (data) {
                var obj = data.data
                if (obj) {
                    $('#rrsCenterCode').textbox('setValue', obj.rrsCenterCode)
                    $('#rrsCenterCode').textbox('setText', obj.rrsCenterName)
                } else {
                    $('#rrsCenterCode').textbox("setValue", '')
                    $('#rrsCenterCode').textbox("setText", '')

                }
            }
        })
    } else {
        $('#rrsCenterCode').textbox("setValue", '')
        $('#rrsCenterCode').textbox("setText", '')
    }
}

function carCode(carType, carTypeName) {
    var base = carCode.base
    var sendTo = carCode.sendTo
    var carCodeOpt = $("select[comboname='carCode']").combobox("options")
    var prompts = ['请选择送达方和基地', '请选择送达方', '请选择基地', '请选择车型']
    var lel = 0;
    if (base) {
        lel += 1
    }
    if (sendTo) {
        lel += 2
    }
    carCodeOpt.prompt = prompts[lel]
    $("select[comboname='carCode']").combobox(carCodeOpt)
    if (lel == 3) {
        $.ajax({
            url: '/vehicle/carCode',
            data: {base: base, sendTo: sendTo},
            dataType: 'json',
            type: 'post',
            success: function (data) {
                var array
                if (data.success) {
                    array = $.parseJSON(data.data)
                    var temp = {}
                    for (var i = 0; i < array.length; i++) {
                        temp[array[i].LIBKEY] = array[i]
                    }
                    carCode.carInfo = temp
                    carCodeOpt.data = data.array
                    carCodeOpt.prompt = prompts[3]
                    if ($("#orderId").val()) {
                        carCodeOpt.value = carType ? carType : $("#carCode").combobox("getValue");
                        carCodeOpt.text = carTypeName ? carTypeName : $("#carCode").combobox("getText");
                        $("#carCode").combobox("disable")
                    }
                    $("select[comboname='carCode']").combobox(carCodeOpt);
                } else {
                    carCode.carInfo = null
                    carCodeOpt.prompt = prompts[0]
                    carCodeOpt.data = null
                    $("select[comboname='carCode']").combobox("loadData", []);
                    alert("查询出错，请选择有效的送达方和基地！")
                }
                
            }

        })
    } else {
        $("select[comboname='carCode']").combobox("loadData", []);
    }
}

$(function () {
    var datagrid = $('#datagrid_r6relateManage').datagrid(datagridOptions_r6relateManage);
    $('#frame-tabs').tabs('resize');

    $("#searchBtn").on('click', function () {
        $("#searchBtn").attr("disabled", "disabled");
        var p = datagrid.datagrid("getPager").data("pagination").options;
        var sendTo = $("#88code").combobox("getValue");
        var base = $("select[comboname='baseCode']").combobox("getValue");
        var pg = $("select[comboname='productGroup']").combobox("getValue");
        var item = $("#itemId").textbox("getValue");
        var data = {
            deliveryToCode: sendTo,
            baseCode: base,
            productGroupCode: pg,
            materielCode: item,
            pageIndex: p.pageNumber,
            pageSize: p.pageSize
        }
        if (sendTo && base) {
            datagrid.datagrid('load', data);
            $("#searchBtn").removeAttr("disabled");
        } else {
            $("#searchBtn").removeAttr("disabled");
            datagrid.datagrid("loaded")
            alert("送达方和基地是必选！")
        }
    })

    $("#return").on('click', function () {
        $('#confirm_order').dialog('close');
    })

    $("#orderCommit").on('click', function () {
        $.messager.progress({
            title: '请稍候',
            text: '数据处理中...'
        });
        $("#confirm_order").dialog("close");
        var orderNo = $("#orderNo").val();
        $.ajax({
            url: "/vehicle/submitOrder",
            data: {orderNo: orderNo},
            dataType: "json",
            type: "post",
            success: function (data) {
                $.messager.progress('close');
                if (data.success) {
                    $(".submitO-title").html('<b class="icon4"></b>' +
                        '                    <p>您的订单提交成功！</p>')

                } else {
                    $(".submitO-title").html('<b class="icon3"></b>' +
                        '                    <p>对不起，您的订单提交失败</p>')
                    $(".returnMsg").html(data.msg)
                }
                $('#confirmdig_success').dialog({'title': '提交订单信息'});
                $('#confirmdig_success').dialog('open');
            },
            error: function () {
                $.messager.progress('close');
                alert("网络异常！")
            }
        })
    })

    // $(".gohistory").on('click', function () {
    //
    //     var mi = {
    //         miid: 482,
    //         data: "/vehicle/history",
    //         title: "历史订单",
    //         type: "url"
    //     }
    //     window.top.showMenuTab(mi)
    //     // addTab("历史订单", "delivery_order/historyOrder.html",true);
    //     window.location.reload()
    //     $('#confirmdig_r6relateManage').dialog('close');
    //     $('#confirmdig_success').dialog('close');
    //     $("#confirm_order").dialog("close")
    // });

    $("#addDlgSaveBtn_r6relateManage").on('click', function () {
        if (!$('#addForm_r6relateManage').form('validate')) {
            throw new Error;
        }
        $.messager.progress({
            title: '请稍候',
            text: '数据处理中...',
        });
        var index = $("#rowIndex").val()
        var code = $("#itemCode").val()
        var row = datagrid.datagrid("getRows")[index]
        if (row) {
            row.count = parseInt($("#num").val())
            $("#datagrid_r6relateManage").datagrid("refreshRow", parseInt(index));
        } else {
            row = {}
            row.count = parseInt($("#num").val())
            selections[code].num = row.count
            row.price = selections[code].price
            row.volume = selections[code].volume
            row.materielCode = code
            row.materielName = selections[code].name
            row.productGroup = selections[code].productGroupCode
            row.materielId = selections[code].materielId
        }
        var widths
        if (selections.volume() == 0) {
            widths = (parseInt(row.count) * parseFloat(row.volume)) / standardMax * 100
        } else {
            if (selections[row.materielCode]) {
                selections[row.materielCode].num = row.count
            }
            widths = selections.volume() / standardMax * 100;
        }
        if (widths > 100) {
            var leftnum = parseInt((standardMax - selections.volume()) / parseFloat(row.volume));
            alert("超出最大装车体积，这种产品最多还能添加" + Math.abs(leftnum) + "台");
            $("#addDlgSaveBtn_r6relateManage").linkbutton("enable")
            $.messager.progress('close');
            throw new Error;
        }
        var sendTo = $("#88code").combobox("getValue");
        var base = $("select[comboname='baseCode']").combobox("getValue");
//        var sendTo = row.deliveryToCode;
//        var base = row.baseCode;
       
        $.ajax({
            url: '/vehicle/itemCheck',
            data: {deliveryCode: sendTo, baseCode: base, materielCode: row.materielCode, count: row.count},
            dataType: "json",
            type: "post",
            success: function (data) {
                $.messager.progress('close');
                if (data.success) {
                    getSelections(index, row);
                    setprogress();
                    $("#whCode").combobox("disable");
                    $("#88code").combobox("disable");
                    $("select[comboname='baseCode']").combobox("disable");
                    $("select[comboname='carCode']").combobox("disable");
                    $.messager.alert(' ', '提交成功', 'info');
                    $('#addDlg_r6relateManage').dialog('close');
                } else {
                    $.messager.alert('', data.msg, 'error');
                }

            },
            error: function () {
                $.messager.progress('close');
                $.messager.alert('', '提交失败', 'error');
            }
        })
    });

    $.ajax({
        url: '/vehicle/productGroup',
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data) {
                productGroup = data.obj;
                $("select[comboname='productGroup']").combobox({
                    prompt: '请选择产品组',
                    data: data.array,
                    valueField: "value",
                    textField: "text",
                    editable: false,
                    required: false,
                    hasDownArrow: true
                })
            }
        }
    })

    $('#88code').combobox({
        prompt: '输入关键字自动检索',
        url: '/vehicle/sendTo',
        valueField: "value",
        textField: "text",
        required: false,
        editable: true,
        hasDownArrow: true,
        filter: function (q, row) {
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;
        },
        onSelect: function (record) {
            carCode.sendTo = record.value
            carCode()
            center(record.value)
            ch88Code(record.value)
        },
        onUnselect: function (record) {
        	$('#rrsCenterCode').textbox('setValue', null);
            carCode.sendTo = null
            carCode()
        }
    });
    
    $('#whCode').combobox({
        prompt: '输入关键字自动检索',
        url: '/vehicle/whCode',
        valueField: "value",
        textField: "text",
        required: false,
        editable: true,
        hasDownArrow: true,
        filter: function (q, row) {
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;
        },
        onSelect: function (record) {
        	chWhCode(record.value);
//            carCode.sendTo = record.value
//            center(record.value)
        },
        onUnselect: function (record) {
        	chWhCode('');
//            carCode.sendTo = null
//            carCode()
        }
    });

    $("select[comboname='baseCode']").combobox({
        prompt: '输入关键字自动检索',
        url: '/vehicle/baseCode',
        valueField: "value",
        textField: "text",
        required: false,
        editable: true,
        hasDownArrow: true,
        filter: function (q, row) {
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;
        },
        onSelect: function (record) {
            carCode.base = record.value
            carCode()
        },
        onUnselect: function () {
            carCode.base = null
            carCode()
        }
    })

    $("select[comboname='carCode']").combobox({
        prompt: '请先选择送达方和基地',
        valueField: "value",
        textField: "text",
        required: false,
        hasDownArrow: true,
        onSelect: function (record) {
            var info = carCode.carInfo[record.value]
            standardMax = info.IJSC_MAXVOLUME;
            standardMin = info.IJSC_MINVOLUME;
            $('#minrange').textbox('setValue', info.IJSC_MINVOLUME);
            $('#maxrange').textbox('setValue', info.IJSC_MAXVOLUME);
        },
        onUnselect: function () {
            $('#minrange').textbox('setValue', '');
            $('#maxrange').textbox('setValue', '');
            standardMax = 0
            standardMin = 0
        }
    });

    var nowdate = new Date();
    nowdate = $.fn.dateFormat("yyyy-MM-dd", nowdate);
    $("#nowdate").textbox('setValue', nowdate);

    $("#confirmDlgSaveBtn_r6relateManage").on('click', function () {
        var orderNo = $("#orderNo").val();
        $.ajax({
            url: '/vehicle/checkOrder',
            data: {orderNo: orderNo},
            type: "post",
            dataType: "json",
            success: function (data) {
                var arr = data.details
                var amount = 0
                for (var i = 0; i < arr.length; i++) {
                    amount += arr[i].amount
                }
                data.order.amount = amount.toFixed(2)
                for (var key in data.order) {
                    if (data.order[key]) {
                        if (key == "type") {
                            continue
                        }
                        if (key == "orderTime") {
                            $("[" + key + "]").html($.fn.dateFormat("yyyy-MM-dd", new Date(data.order[key])))
                        } else {
                            $("[" + key + "]").html(data.order[key])
                        }
                    }
                    $("[sumVolume]").html(selections.volume() + "m³")
                }
                tabDg1.data = {"total": data.details.length, rows: data.details}
                $("#tabDg1").datagrid(tabDg1)
                $("#confirmdig_r6relateManage").dialog('close')
                $("#confirm_order").dialog({'title': '提交订单信息'});
                $("#confirm_order").dialog('open')
            }
        })
    })
});

function deleteItem(code) {
    confirm('确定删除？', function (r) {
        if (r) {
            var index = selections[code].index;
            delete selections[code];
            if (selections.length() == 0) {
                $("#88code").combobox("enable");
                $("select[comboname='baseCode']").combobox("enable");
                $("select[comboname='carCode']").combobox("enable");
            }
            if (index) {
                var row = $('#datagrid_r6relateManage').datagrid('getRows')[index]
                row.count = 1
                $('#datagrid_r6relateManage').datagrid("refreshRow", parseInt(index))
            }
            sethtmls();
            $('.cart_num').html(selections["length"]);
            setprogress();
        }
    })
};

function editItem(code) {
    addCart(null, code)
};

function sethtmls() {
    var html = '';
    for (var tm  in selections) {
        var tp = selections[tm]
        if (typeof tp == 'function') {
            continue
        }
        html += '<tr style="height: 35px;border-bottom: dashed 1px;">' +
            '                   <td><a itemName href="#">' + tp.name + '</a></td>' +
            '                    <td><span itemPrice class="cart_price">￥' + parseFloat(tp.price).toFixed(2) + '</span>' +
            '                    <span style="width:20px;margin-left: 5px" itemNum><span style="margin-right:5px">×</span>' + tp.num + '</span></td>' +
            '                    <td><img style="cursor: pointer;" onclick="editItem(\'' + tp.code + '\')"  src="/static/third/easyui/themes/insdep/icons/mini-pencil.png" style="margin: 0 5px 0 5px">' +
            '                    <img style="cursor: pointer;" onclick="deleteItem(\'' + tp.code + '\')" src="/static/third/easyui/themes/insdep/icons/cancel.png"></td>' +
            '            </tr>'
    }
    quickDataFns.message_list.content = '<div class="ibar_plugin_content">' +
        '    <div class="ibar_cart_group ibar_cart_product" style="overflow:auto;">' +
        '        <table style="overflow: auto;margin-top: 15px;margin-bottom: 15px;">' +
        html +
        '        </table>' +
        '    </div>' +
        '    <div class="cart_handler">' +
        '        <div class="cart_handler_header"><span class="cart_handler_left">共<span' +
        '               itemKinds class="cart_price">' + selections.length() + '</span>种商品</span><span class="cart_handler_right">￥' + selections.sum().toFixed(2) + '</span></div>' +
        '        <a style="cursor: pointer;" onclick="attention()" class="cart_go_btn" target="_blank">装车检查</a></div>' +
        '</div>';
};

function setprogress() {
    var widths = selections.volume() / standardMax * 100;
    var kongjian = selections.volume()
    if (widths < 0) {
        widths = 0;
        kongjian = 0;
    }
    $('#progressid').css('width', widths + "%");
    $('#progressp').html(widths.toFixed(2) + "%");
    $('#progressM').html(kongjian.toFixed(2) + "m³");
    if (standardMax >= kongjian && kongjian >= standardMin) {
        $('#progressid').removeClass('progress-bar-danger');
        $('#progressid').addClass('progress-bar-success');
    } else {
        $('#progressid').addClass('progress-bar-danger');
        $('#progressid').removeClass('progress-bar-success');
    }
};

function attention(tempSave) {
    var obj = {
        "88code": {code: "", name: "", msg: "送达方"},
        rrsCenterCode: {code: "", name: "", msg: "配送中心"},
        carCode: {code: "", name: ""},
        baseCode: {code: "", name: ""}
    }
    for (var key in obj) {
        var code, name
        if (key == "rrsCenterCode") {
            code = $("#" + key).textbox("getValue")
            name = $("#" + key).textbox("getText")
        } else {
            code = $("#" + key).combobox("getValue")
            name = $("#" + key).combobox("getText")
        }
        if (!code || !name) {
            alert("未选择" + obj[key].msg)
            return
        }
        obj[key].code = code
        obj[key].name = name
    }
    if (selections.length() == 0) {
        alert("未选择装车物品")
        return
    }
    var text, url;
//    alert("selections.volume()"+selections.volume());
//    alert("minrange.value"+minrange.value);
//    alert("maxrange.value"+maxrange.value);
    
    if (tempSave) {
        text = "确定暂存吗？"
        url = '/vehicle/tempSave'
    } else {
    	 if(parseInt(selections.volume())<parseInt(minrange.value)){
    	    	alert("装车未满足【标准范围】最小体积"+parseInt(minrange.value).toFixed(2)+"m³");
    	    	return 
    	    }
    	    if(parseInt(selections.volume())>parseInt(maxrange.value)){
    	    	alert("装车已超过【标准范围】最大体积"+parseInt(maxrange.value).toFixed(2)+"m³");
    	    	return 
    	    }
        text = "点击确定，系统将为您预算您添加的商品是否能够装下您选择的车型！"
        url = '/vehicle/carCheck'
    }
    confirm(text, function (r) {
        if (r) {
            $.messager.progress({
                title: '请稍候',
                text: '数据处理中...'
            });
            var materielCodes = []
            for (var k in selections) {
                if (typeof selections[k] == "function") {
                    continue
                }
                materielCodes.push({
                    quantity: selections[k].num + "",
                    customerOrder: "1",
                    centerCode: obj.rrsCenterCode.code,
                    gbid: selections[k].code,
                    gbName: selections[k].name,
                    materielId: selections[k].materielId
                })
            }
            var data = {
                orderId: $("#orderId").val(),
                deliveryCode: obj["88code"].code,
                deliveryName: obj["88code"].name.split(":")[1],
                jdCode: obj.baseCode.code,
                jdName: obj.baseCode.name,
                distributionCentre: obj.rrsCenterCode.code,
                distributionCentreName: obj.rrsCenterCode.name,
                materielIds: JSON.stringify(materielCodes),
                carType: obj.carCode.code,
                carTypeName: obj.carCode.name,
                minVolumeStr: $("#minrange").val(),
                maxVolumeStr: $("#maxrange").val()
            }
            $.ajax({
                url: url,
                data: data,
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    $.messager.progress('close');
                    if (data.success) {
                        if (tempSave) {
                            alert("暂存成功！")
                            window.location.reload()
                        } else {
                            $("#orderId").val(data.orderId)
                            $("#orderNo").val(data.orderNo)
                            $("[imgUrl]").attr("src", data.imgUrl)
                            $('#confirmdig_r6relateManage').dialog({'title': '确认信息'});
                            $('#confirmdig_r6relateManage').dialog('open');
                        }
                    } else {
                        alert(data.msg)
                    }
                },
                error: function () {
                    $.messager.progress('close');
                    alert("网络错误！请稍候再试")
                }
            })

        }
    });
}

