var processing = false;
$(function(){

  $( "#dialog-message" ).dialog({
    autoOpen:false,
    modal: true,
    closeOnEscape:false,
    resizable:false,
    height:200,
    close: function( event, ui ) {
      if(processing)
        $( this ).dialog( "open" );
    },
    buttons: {

    }
  });
  var options = {
    url:'/purchaseOrder/importPurchaseOrderData',
    type:'POST',
    async:true,
    beforeSubmit: beforeSubmit,
    resetForm:true,
    error:function(){
      hideLoading();
      processing = false;
      $('#message').html("失败!");
      $('#submit').removeAttr('disabled');
      $('#errMsg').html("服务器繁忙或者响应失败，请稍后重试");
      $('#errMsg').show();
      $('#result').hide();
      $("#file").val("");//清空文件域
      $("#textfield").val("");//清空文件域
    },
    success:function(data) {
      hideLoading();
      var message = data.message;
      $('#submit').removeAttr('disabled');
      processing = false;
      if(data.success){
        $('#errMsg').hide();
        $('#resultWarn').hide();
        $('#message').html("导入状况");
        $('#total').html("共"+data.data.total+"条记录");
        $("#success").html("创建"+data.data.success+"条记录");
        if(data.data.warn != ""){
          $('#ignore').html("异常"+data.data.ignore+"条记录");
        }else {
          $('#ignore').html("异常"+data.data.ignore+"条记录");
        }
        $("#result").show();
      }else{
        $('#resultWarn').hide();
        $('#message').html("失败！");
        if(!data.message){
          $('#errMsg').html("服务器繁忙或者响应失败，请稍后重试");
        }else{
          $('#errMsg').html(data.message);
        }
        $('#errMsg').show();
        $("#result").hide();
      }
    }
  }


  $('#importForm').submit(function(){
    try{
      $(this).ajaxSubmit(options);
    }catch(e){
      alert(e.message);
    }

    $("#file").val("");//清空文件域
    return false;	//防止刷新
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
    $('#result').hide();
    $('#dialog-message').dialog("open");
    showLoading();
  }else
    return false;
}

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
    $("#textfield").val("");
    return false;
  }

  var strRegex = "(xls|XLS)$";
  var re=new RegExp(strRegex);
  if (!re.test(extendName.toLowerCase())){
    alert("文件格式不合法，文件的扩展名必须为.xls或.XLS格式");
    $("#textfield").val("");
    return false;
  }

  var fileSize = 0;
  if(!target.files){
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
//返回调用画面
function onDownload(){
  $('#download').submit();
}