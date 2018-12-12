var processing = false;
$(function(){
    $( "#dialog-message" ).dialog({
        autoOpen:false,
        modal: true,
        closeOnEscape:false,
        resizable:false,
        close: function( event, ui ) {
            if(processing)
                $( this ).dialog( "open" );
        },
        minHeight:200,
        buttons:{
            '确 定':function(){
                $(this).dialog('close');
            }
        }
    });

    var options = {
        url:'/upload/doUpload',
        type:'POST',
        beforeSubmit: beforeSubmit,
        resetForm:true,
        error:function(){
            hideLoading();
            processing = false;
            $('#message').html("失败!");
            $('#submit').removeAttr('disabled');
            $('#errMsg').html("<font color='red'>"+"服务器繁忙或者响应失败，请稍后重试"+"</font>");
            $('#errMsg').show();
        },
        success:function(data) {
            hideLoading();
            var message = data.message;
            $('#submit').removeAttr('disabled');
            processing = false;
            if(data.success){
                $('#errMsg').hide();
                $('#message').html("成功！");
                $('#dialog-message').dialog("close");
                addRowJson(data);
            }else{
                $('#message').html("失败！");
                $('#errMsg').html("<font color='red'>"+data.message+"</font>");
                $('#errMsg').show();
            }
        }
    };
    $('#filterForm').submit(function(){
        try{
            $(this).ajaxSubmit(options);
        }catch(e){
            alert(e.message);
        }
        return false;
    });

    $('#body').height($(window).height() - 30);
    $('#list').height($(window).height() - 169);
});

function beforeSubmit(){
    if(checkFile()){
        $('#submit').attr('disabled','disabled');
        processing = true;
        $('#errMsg').html('');
        $('#errMsg').hide();
        $('#dialog-message').dialog("open");
        showLoading();
    }else
        return false;
}

var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
function checkFile()
{
    var target = document.getElementById('file');
    var filePath = target.value;
    if(!filePath)
    {
        alert('请选择文件');
        return false;
    }

    filePath = filePath.trim();
    var extendName = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length);
    if(!extendName)
    {
        alert("文件类型不正确，文件的扩展名必须为.xls或.XLS");
        return false;
    }

    var strRegex = "(xls|XLS)$";
    var re=new RegExp(strRegex);
    if (!re.test(extendName.toLowerCase())){
        alert("文件格式不合法，文件的扩展名必须为.xls或.XLS格式");
        return false;
    }

    var fileSize = 0;
    if(isIE && !target.files){
        var fileSys = new ActiveXObject("Scripting.FileSystemObject");
        if(!fileSys.FileExists(filePath)){
            alert("附件不存在，请重新输入");
            return false;
        }
        var file = fileSys.GetFile(filePath);
        fileSize = file.Size;
    }else{
        fileSize = target.files[0].size;
    }
    if(fileSize>1024*1024*5)
    {
        alert("文件大小不能超过5M");
        return false;
    }
    return true;
}


function showLoading() {
    $('#message').html('');
    $('#dialog-message').dialog('option','title','处理中');
    //document.getElementById("over").style.display = "block";
    document.getElementById("layout").style.display = "block";
}

function hideLoading(){
    $('#dialog-message').dialog('option','title','提示');
    document.getElementById("layout").style.display = "none";
}

function onDownload(){
    $('#download').submit();
}