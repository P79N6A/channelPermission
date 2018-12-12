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

  //判断是否是顶级窗口，如果不是顶级窗口，刷新顶级窗口
  if(!(window.top==window.self)){
    window.top.location.reload();
  }
});

//回车时，默认是登陆
function on_return(){
    if(window.event.keyCode == 13){
        if ($("#loginBtn")!=null && $("#inputEmail3")!=null && $("#inputPassword3")!=null){
            window.location.href = "main_temp.html";
        }
    }
};