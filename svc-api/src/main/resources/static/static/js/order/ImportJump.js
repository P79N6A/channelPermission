function beforeSubmit() {
    // var processing = false;
    var source = $('#source').combobox('getValue');
    if ("" == source) {
        alert("请选择订单来源");
        return false;
    }
    var industrys = $('#industrys').combobox('getValue');
    if ("" == industrys) {
        alert("请选择所属产业");
        return false;
    }
    if (checkFile()) {
        $('#submit').attr('disabled', 'disabled');
        // processing = true;
        $('#errMsg').html('');
        $('#errMsg').hide();
        $('#result').hide();
        // $('#dialog-message').dialog("open");
        // showLoading();
    }else {
        return false;
    }

    return true;

}

    
function checkFile() {
    var target = document.getElementById('file');
    var filePath = target.value;
    if (!filePath) {
        alert('请选择文件');
        return false;
    }

    filePath = filePath.trim();
    var extendName = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length);
    if (!extendName) {
        alert("文件类型不正确，文件的扩展名必须为.xls或.XLS");
        return false;
    }

    var strRegex = "(xls|XLS |xlsx | csv |CSV)$";
    var re = new RegExp(strRegex);
    if (!re.test(extendName.toLowerCase())) {
        alert("文件格式不合法，文件的扩展名必须为.xls或.XLS格式");
        return false;
    }

    var fileSize = 0;
    if (!target.files) {
        var fileSys = new ActiveXObject("Scripting.FileSystemObject");
        if (!fileSys.FileExists(filePath)) {
            alert("附件不存在，请重新输入");
            return false;
        }
        var file = fileSys.GetFile(filePath);
        fileSize = file.Size;
    } else {
        fileSize = target.files[0].size;
    }

    return true;
}

function showLoading() {
    $('#message').html('');
    $('#dialog-message').dialog('option', 'title', '处理中');
    //document.getElementById("over").style.display = "block";
    document.getElementById("layout").style.display = "block";
}


function hideLoading() {
    $('#dialog-message').dialog('option', 'title', '提示');
    document.getElementById("layout").style.display = "none";
}
//返回调用画面
function onGoBack() {
    var url = '/order/addOrderList';
    window.location.href = url;
}

$(function () {
    $("#dialog-message").dialog({
        autoOpen: false,
        modal: false,
        closeOnEscape: false,
        resizable: false,
        minHeight: 130,
        width: 320,
        title: '请稍后'
    });
});


$("#submit").on('click', function () {
    if(!beforeSubmit()){
        return;
    }

    jQuery("#dialog-message").dialog("open");
    //$("#list").html("<div style='width:318px;height:130px;text-align:center;'><br/><br/>数据正在加载，请稍后。。。<br/><br/><div id='timecount' style='width:100%;height:20px;text-align:center;font-size:10pt;font-family:Arial,Helvetica,sans-serif;'></div><br/></div>");
    $("#handleResultDetail").attr("style", "display:none;padding:5px;width:800px;height:400px;");

    var formData = new FormData($("#importForm")[0]);
    console.log(formData);
    $.ajax({
        cache: false,
        type: "post",
        // beforeSend:beforeSubmit,
        data: formData,
        url: '/order/importInvWarehouses',
        dataType: 'json',
        contentType: false, //必须
        processData: false, //必须
        success: function (data) {
            console.log(data);
            var message = data.message;
            $('#submit').removeAttr('disabled');
            // processing = false;
            var resultstr="";
            if (data.success) {

                //$('#errMsg').hide();
                //$('#resultWarn').hide();
               // $('#message').html("导入状况");
                resultstr=resultstr+"导入状况</br>";
               // $('#total').html("共" + data.data.total + "条记录");
                resultstr=resultstr+"共" + data.data.total + "条记录</br>";
                ///$("#success").html("创建" + data.data.success + "条记录");
                resultstr=resultstr+"创建" + data.data.success + "条记录</br>";
                if (data.data.warn != "") {
                   // $('#ignore').html("异常" + data.data.ignore + "条记录" + data.data.warn);
                    resultstr=resultstr+"未创建数据" + data.data.ignore + "条" + data.data.warn+"</br>";
                } else {
                   // $('#ignore').html("异常" + data.data.ignore + "条记录");
                    resultstr=resultstr+"未创建数据" + data.data.ignore + "条</br>";
                }
                //$("#result").show();
            } else {
                //$('#resultWarn').hide();
                //$('#message').html("失败！");
                resultstr=resultstr+"失败！</br>";
                if (!data.message) {
                   // $('#errMsg').html("服务器繁忙或者响应失败，请稍后重试");
                    resultstr=resultstr+"服务器繁忙或者响应失败，请稍后重试</br>";
                } else {
                   // $('#errMsg').html(data.message);
                    resultstr=resultstr+data.message;
                }
               // $('#errMsg').show();
                //$("#result").hide();
            }


            //alert(result.message);
            $("#dialog-message").dialog("close");
            $("#handleResultDetail").html(resultstr);
            $("#handleResultDetail").show();
            $("#handleResultDetail").dialog({
                collapsible: true,
                minimizable: false,
                maximizable: false
            });
            $("#textfield").val("");
            $("#file").val("");
        },
        error: function (arg1, arg2, arg3) {
            $("#dialog-message").dialog("close");
            console.log(arg1 + "--" + arg2 + "--" + arg3);
        }
    });

});