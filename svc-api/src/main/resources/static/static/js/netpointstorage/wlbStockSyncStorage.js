
var datagrid;
var queryParameters;
$(function () {
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        striped: true,
        singleSelect: true,
        pagination: true,
        columns: [[
            // {title: "操作", field: "field0", sortable: false, formatter: go_detail},
            { title: "序号", field: "id", hidden: true },
            { title: "库位名称", field: "storagesName", sortable: false },
            { title: "库位编码", field: "sCode", sortable: false },
            { title: "淘宝仓库编码", field: "taobaoStoreCode", sortable: false },
            { title: "淘宝店铺", field: "source", sortable: false },
            { title: "添加时间", field: "addTime", sortable: false },
        ]],
        pageSize: 50,
        pageList: [50, 100, 200],
        rownumbers: true
    }

    $('#datagrid').datagrid(datagrid);


});



$('#search').click(function () {

    //库位类型
    var taoBaoShop = $("#taoBaoShop").combobox("getValue");
    if (taoBaoShop == "-1") {
        taoBaoShop = "";
    }
    $('#taoBaoShop').combobox('setValue', taoBaoShop);


    //提交参数
    queryParameters = {
        id: $("#id").val(),
        storageName: $("#storageName").val().trim(),//库位名称
        storageCode: $("#storageCode").val().trim(),//库位码
        taoBaoStorageCode: $("#taoBaoStorageCode").val().trim(),//淘宝仓库编码
        taoBaoShop: $("#taoBaoShop").combobox("getValue"),//淘宝店铺
    };

    datagrid = $('#datagrid').datagrid({
        url: "/stock/getStockSyncStorageList.html",
        fit: true,
        fitColumns: false,
        pagination: true,
        singleSelect: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        toolbar: '#datagridToolbar_dmmtlPbcsMtlMeasure',
        nowrap: true,
        rownumbers: true,
        queryParams: queryParameters,
        columns: [
            [{
                field: 'id',
                title: '序号',
                align: 'center',
                hidden: true
            },
                {
                    title: '库位名称 ',
                    field: 'storagesName',
                    align: 'center',
                },
                {
                    title: '库位编码 ',
                    field: 'sCode',
                    align: 'center',
                }, {
                title: '淘宝仓库编码 ',
                field: 'taobaoStoreCode',
                align: 'center'
            }, {
                title: '淘宝店铺 ',
                field: 'source',
                align: 'center',
                formatter: function (value, row, index) {
                    var sourceShop = "";
                    if (row.source == 'taobao') {
                        sourceShop = "淘宝淘宝官方旗舰店";
                    } else if (row.source == 'taobaorsq') {
                        sourceShop = '海尔热水器专卖店';
                    }else if (row.source == 'toptongshuai') {
                        sourceShop = '统帅日日顺乐家专卖店';
                    }else if (row.source == 'taobaoxb') {
                        sourceShop = '海尔新宝旗舰店';
                    }else if (row.source == 'tongshuaifx') {
                        sourceShop = '统帅品牌分销店';
                    }else if (row.source == 'WASHER') {
                        sourceShop = '海尔洗衣机官方旗舰店';
                    }else if (row.source == 'FRIDGE') {
                        sourceShop = '海尔冰冷官方旗舰店';
                    }else if (row.source == 'TMMK') {
                        sourceShop = 'mooka模卡官方旗舰店';
                    }else if (row.source == 'AIR') {
                        sourceShop = '淘宝空调旗舰店';
                    }else if (row.source == 'TMMKFX') {
                        sourceShop = '天猫模卡分销店铺';
                    }else if (row.source == 'GQGYS') {
                        sourceShop = '天猫分销';
                    }else {
                        sourceShop = '';
                    }
                    return sourceShop;
                }
            },
                {
                title: '添加时间',
                field: 'addTime',
                align: 'center',
                formatter:function(value,rowData,rowIndex){
                    if(value!='0' && value != null){
                        var date = new Date(value)//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                        Y = date.getFullYear() + '-';
                        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'
                        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' '
                        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':'
                        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':'
                        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds())
                        return Y+M+D+h+m+s;
                    }else {
                        return "";
                    }

                }
            }]
        ],
    });


});



$("#add_WlbStockSyncStorage").on('click', function (event) {
    window.location.href="/stock/wlbStockSyncStorageAdd";
});

$("#delete_WlbStockSyncStorage").on('click', function (event) {
    event.preventDefault();
    var selected = $('#datagrid').datagrid('getSelected');
    if (selected !== null) {
        confirm('确定删除？', function (r) {
            if (r) {
                $.ajax({
                    url      : "/stock/deleteWlbStockSyncStorage",
                    type     : 'GET',
                    dataType : 'json',
                    data     : {"id":selected.id},
                    success  : function(data) {
                        if (data.flag == '1') {
                            alert('删除成功');
                            $('#datagrid').datagrid('reload');
                        } else {
                            alert('删除失败');
                        }
                    }
                });
            }
        })
    } else {
        alert('请选择一条数据');
    }
});