var datagridOptions_r6relateManage = {
    fit: true,
    fitColumns: true,
    singleSelect: true,
    //url: '/auth/dataDictionary/p',
    /*frozenColumns: [[{
     field: 'id', checkbox: true
     }]],*/
    width: $(window).width() - 10,
    columns: [[
        {title: '产品', field: 'field0', sortable: true, align: 'center'},
        {title: '产品名称', field: 'field1', sortable: true, align: 'center'},
        {title: '产品组', field: 'field2', sortable: true, align: 'center'},
        {title: '品牌', field: 'field3', sortable: true, align: 'center'},
        {title: '开票价', field: 'field4', sortable: true, align: 'center'},
        {title: '扣点', field: 'field5', sortable: true, align: 'center'},
        {title: '最近销量', field: 'field6', sortable: true, align: 'center'},
        {title: '数量', field: 'field7', sortable: true},
        {title: '最大量', field: 'field8', sortable: true, align: 'center'},
        {title: '基地', field: 'field9', sortable: true, align: 'center'},
        {title: '操作', field: 'field10', sortable: true},
    ]],
    toolbar: '#datagridToolbar_r6relateManage',
    striped: true,
    pagination: true,
    rownumbers: true,
};

var selections = {length: 0, sum: 0, totalnum: 0}
var numberItem = 0;

function getSelections(flag) {

    var price = parseFloat($("#s_price").text().replace(/[^0-9]/ig, "")).toFixed(2);
    var name = $("#s_GBNAME").text();
    var num = parseInt($("#num").val())
    var cuppoint = $("#show_backbl").text();
    var temp = selections[name];
    selections.sum += (parseInt(price) * parseInt(num));
    var tempvalue;
    if (temp) {
        tempvalue = temp.num;
        if (flag) {
            temp.num = num;
        } else {
            temp.num += num;
        }

    } else {

        eleShopCart.querySelector("span").innerHTML = ++numberItem;
        selections[name] = {
            price: price, name: name,
            num: num,
            cuppoint: cuppoint
        }
        selections.length += 1;

    }
    if (temp) {
        selections.totalnum = selections.totalnum - tempvalue + num;
    } else {
        selections.totalnum += num;
    }
    sethtmls();
}

$(function () {
    var datagrid = $('#datagrid_r6relateManage').datagrid(datagridOptions_r6relateManage);
    // datagrid.datagrid('loadData',datagridData_r6relateManage);
    //创建表头的菜单
    //CustomConfig.load(datagrid,"DataDictionary");
    //$("#searchPanel_r6relateManage").panel('resize');

    $(".zcCart-btn").on('click', function () {
        /*$('#addForm_r6relateManage').form('reset');
         $('#addForm_r6relateManage').find('[__actType]').val('add');*/
        $('#addDlg_r6relateManage').dialog({'title': '确认信息'});
        $('#addDlg_r6relateManage').dialog('open');
        var targ;
        if (!e) var e = window.event;
        if (e.target) {
            targ = e.target;
        } else if (e.srcElement) {
            targ = e.srcElement;
        }
        if (targ.nodeType == 3) {
            targ = targ.parentNode
        } // defeat Safari bug
        var rowtr = targ.parentNode.parentNode.parentNode;
        var productName = $($(rowtr).find('[target=_blank]')[1]).text();
        var price = $(rowtr).find('[field=field4]').find('div').text();
        var maxnum = $(rowtr).find('[field=field8]').find('div').text();
        var cutpoint = $(rowtr).find('[field=field5]').find('div').text();
        var num = $(rowtr).find('[field=field7]').find('.quantity-text').val();

        price = price.toString().replace(/\s+/g, '');
        maxnum = maxnum.toString().replace(/\s+/g, '');
        price = price.toString().substr(1);
        $('#s_GBNAME').html(productName);
        $('#s_price').html(price);
        $('#show_price').html(price);
        $('#show_backbl').html(cutpoint);
        $('#maxQty').val(maxnum);
        $('#num').val(num);
        $('#s_sumprice').html(parseFloat(price * num).toFixed(2));
        console.log(productName + price + maxnum + cutpoint + num);
        console.log(rowtr);
        $('#addForm_r6relateManage').addClass('.add');
        $('#addForm_r6relateManage').removeClass('.edit');

    });
    $("#addDlgSaveBtn_r6relateManage").on('click', function () {
        if (!$('#addForm_r6relateManage').form('validate')) {
            return;
        }
        var actType = $('#addForm_r6relateManage').is('.edit');
        console.log(actType + "====");
        var widths = parseInt(selections["totalnum"]) + parseInt($('#num').val());

        if (widths > 100) {
            var leftnum = 100 - parseInt(selections["totalnum"]);
            alert("超出最大装车体积，这种产品最多还能添加" + leftnum + "台");
            return;
        }
        // 绑定点击事件
        if (eleShopCart) {
            getSelections(actType);
        }
        setprogress();

        alert('提交成功');
        $('#addDlg_r6relateManage').dialog('close');
    });

    $('.pagination-info').css('margin', '0 100px 0 0');

    /*$(".combo").click(function(){
     if ($(this).prev().combobox("panel").is(":visible")) {
     $(this).prev().combobox("hidePanel");
     } else {
     $(this).prev().combobox("showPanel");
     }
     })*/
    $('#88code').combobox({
        prompt: '输入首关键字自动检索',
        required: false,
        //url:'${path }/portal/designer/tree',
        editable: true,
        hasDownArrow: true,
        filter: function (q, row) {
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) == 0;
        }
    });

});
function deleteItem(name, price, num) {
    confirm('确定删除？', function (r) {
        if (r) {
            console.log(selections);
            delete selections[name];
            selections["sum"] = selections["sum"] - price * num;
            selections["length"] = selections["length"] - 1;
            selections["totalnum"] = selections["totalnum"] - num;
            console.log(selections);
            sethtmls();
            numberItem = numberItem - 1;
            $('.cart_num').html(numberItem);
            setprogress();
        }
    })
};
function editItem(name, price, num) {
    $('#addDlg_r6relateManage').dialog({'title': '确认信息'});
    $('#addDlg_r6relateManage').dialog('open');
    $('#s_GBNAME').html(name);
    $('#s_price').html((price / 100).toFixed(2));
    $('#show_price').html((price / 100).toFixed(2));
    $('#show_backbl').html(selections[name]["cuppoint"]);
    $('#num').val(num);
    $('#s_sumprice').html(parseFloat(price / 100 * num).toFixed(2));
    $('#addForm_r6relateManage').addClass("edit");
};

function sethtmls() {
    var html = '';
    for (var tm  in selections) {
        if (tm == 'length' || tm == 'sum' || tm == 'totalnum') {
            continue
        }
        var tp = selections[tm]
        html += '<tr style="height: 35px;border-bottom: dashed 1px;">' +
            '                   <td><a itemName href="#">' + tp.name + '</a></td>' +
            '                    <td><span itemPrice class="cart_price">￥' + (tp.price / 100).toFixed(2) + '</span>' +
            '                    <span style="width:20px;margin-left: 5px" itemNum><span style="margin-right:5px">×</span>' + tp.num + '</span></td>' +
            '                    <td><img style="cursor: pointer;" onclick="editItem(\'' + tp.name + '\'' + ',' + '\'' + tp.price + '\'' + ',' + '\'' + tp.num + '\'' + ')"  src="../static/third/easyui/themes/insdep/icons/mini-pencil.png" style="margin: 0 5px 0 5px">' +
            '                    <img style="cursor: pointer;" onclick="deleteItem(\'' + tp.name + '\'' + ',' + '\'' + tp.price + '\'' + ',' + '\'' + tp.num + '\'' + ')" src="../static/third/easyui/themes/insdep/icons/cancel.png"></td>' +
            '            </tr>'
    }
    quickDataFns.message_list.content = '<div class="ibar_plugin_content">' +
        '    <div class="ibar_cart_group ibar_cart_product">' +
        '        <table style="overflow: auto;margin-top: 15px;margin-bottom: 15px;">' +
        html +
        '        </table>' +
        '    </div>' +
        '    <div class="cart_handler">' +
        '        <div class="cart_handler_header"><span class="cart_handler_left">共<span' +
        '               itemKinds class="cart_price">' + selections.length + '</span>种商品</span><span class="cart_handler_right" sumPrice>￥' + (selections.sum / 100).toFixed(2) + '</span></div>' +
        '        <a style="cursor: pointer;" onclick="attention()" class="cart_go_btn" target="_blank">装车检查</a></div>' +
        '</div>';
};
function setvalusss(e) {
    if (e.value == '') {
        e.value = 0;
    } else if (e.value * 1 < $('#minQty').val() * 1) {
        e.value = $('#minQty').val() * 1;
    } else if (e.value * 1 > $('#maxQty').val() * 1) {
        e.value = $('#maxQty').val() * 1;
    }
    $('#s_sumprice').html((($('#s_price').text() * 1) * $('#num').val()).toFixed(2));
};
function replaceValue(e) {
    var nowv = parseInt($(e).text());
    var maxv = parseInt($('#maxQty').text());
    if (nowv > maxv) {
        $(e).html(maxv);
    }
}

function setprogress() {
    var widths = parseInt(selections["totalnum"]);
    var kongjian = widths * 150 / 100;
    $('#progressid').css('width', widths + "%");
    $('#progressp').html(widths + "%");
    $('#progressM').html((widths * 150 / 100).toFixed(2) + "m³");
    console.log(widths);
    if (150 >= kongjian && kongjian >= 140) {
        $('#progressid').removeClass('progress-bar-danger');
        $('#progressid').addClass('progress-bar-success');
    } else {
        $('#progressid').addClass('progress-bar-danger');
        $('#progressid').removeClass('progress-bar-success');
    }
};
function attention() {
    /*var widths = parseInt(selections["totalnum"]);
    var kongjian = widths * 150 / 100;
    if (150 >= kongjian && kongjian >= 140) {
        alert("装车成功");
    } else if (kongjian < 140) {
        alert("未达到装车标准范围");
    }else if (kongjian > 150) {
        alert("超出装车的标准范围");
    }*/
    confirm("点击确定，系统将为您预算您添加的商品是否能够装下您选择的车型！",function () {
        if (r) {

        }
    });
}
