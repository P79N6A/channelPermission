/**
 * Created by Magp on 2017/4/25.
 */
$(function () {
  var bgs = [
    'static/img/loginbg1.jpg',
    'static/img/loginbg2.png',
    'static/img/loginbg3.jpg',
    'static/img/loginbg4.jpg',
    'static/img/loginbg5.jpg'
  ];
  var indexCount = parseInt((Math.random() * bgs.length));
  $('.login-bg').css('background-image', 'url("' + bgs[indexCount] + '")');
  if (browser.webkit) {
    setInterval(function () {
      indexCount++;
      if (indexCount >= bgs.length) {
        indexCount = 0
      }
      $('.login-bg').css('background-image', 'url("' + bgs[indexCount] + '")');
    }, 10000);
  }

  var imgDomStr = '<span style="display:none;">';
  for (var i = 0; i < bgs.length; i++) {
    imgDomStr += '<img src="' + bgs[i] + '" />';
  }
  imgDomStr += '</span>';
  var $imgbgs = $(imgDomStr);
  $imgbgs.appendTo($('body'));
  $('#loginBtn').on('click', function (event) {
    event.preventDefault();
    $("#errMsg").text('');
    $("#err").hide();
    var param = $('#loginForm').serializeObject();
    $.post('/auth/rest/login', param).success(function (res) {
      if(res.success){
        window.location='/main';
      }else{
        $("#errMsg").text(res.errorMessages);
        $("#err").show();
      }
    });
  });
  //判断是否是顶级窗口，如果不是顶级窗口，刷新顶级窗口
  if(!(window.top==window.self)){
    window.top.location.reload();
  }
});


$(function () {
    $(document).keyup(function (e) {
        if (e.keyCode == 13) {
            login();
        }
    });
});

$("#loginBtn_zhushidiao").click(function () {
    login();
});

function login() {
    $.ajax({
        url:"/login/toLogin",
        type:"post",
        data:{
            "userCode": $("#inputEmail3").val(),
            "userPassword":$("#inputPassword3").val()
        },success:function (data) {
            // alert("111");
            if (data == "密码正确") {
                window.location.href="main.html";
            }else {
                alert(data);
            }
        }
    });
}