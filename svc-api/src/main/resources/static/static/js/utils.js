/**
 * Created by Magp on 2017/4/26.
 */
var browser = function () {//获得浏览器类型和版本
  var agent = navigator.userAgent.toLowerCase(),
      opera = window.opera,
      browser = {
        //检测当前浏览器是否为IE
        ie: /(msie\s|trident.*rv:)([\w.]+)/.test(agent),
        //检测当前浏览器是否为Opera
        opera: (!!opera && opera.version),
        //检测当前浏览器是否是webkit内核的浏览器(edge会伪装成webkit，所以这里把edge排除掉)
        webkit: (agent.indexOf(' applewebkit/') > -1 && agent.indexOf(' edge/')
        < 0),
        //检测当前浏览器是否是运行在mac平台下
        mac: (agent.indexOf('macintosh') > -1),
        //检测当前浏览器是否处于“怪异模式”下
        quirks: (document.compatMode == 'BackCompat')
      };
  //检测当前浏览器内核是否是gecko内核
  browser.gecko = (navigator.product == 'Gecko' && !browser.webkit
  && !browser.opera && !browser.ie);
  var version = 0;
  // Internet Explorer 6.0+
  if (browser.ie) {
    var v1 = agent.match(/(?:msie\s([\w.]+))/);
    var v2 = agent.match(/(?:trident.*rv:([\w.]+))/);
    if (v1 && v2 && v1[1] && v2[1]) {
      version = Math.max(v1[1] * 1, v2[1] * 1);
    } else if (v1 && v1[1]) {
      version = v1[1] * 1;
    } else if (v2 && v2[1]) {
      version = v2[1] * 1;
    } else {
      version = 0;
    }
    //检测浏览器模式是否为 IE11 兼容模式
    browser.ie11Compat = document.documentMode == 11;
    //检测浏览器模式是否为 IE9 兼容模式
    browser.ie9Compat = document.documentMode == 9;
    //检测浏览器模式是否为 IE10 兼容模式
    browser.ie10Compat = document.documentMode == 10;
    //检测浏览器是否是IE8浏览器
    browser.ie8 = !!document.documentMode;
    //检测浏览器模式是否为 IE8 兼容模式
    browser.ie8Compat = document.documentMode == 8;
    //检测浏览器模式是否为 IE7 兼容模式
    browser.ie7Compat = ((version == 7 && !document.documentMode)
    || document.documentMode == 7);
    //检测浏览器模式是否为 IE6 模式 或者怪异模式
    browser.ie6Compat = (version < 7 || browser.quirks);
    browser.ie9above = version > 8;
    browser.ie9below = version < 9;
  }
  // Gecko.
  if (browser.gecko) {
    var geckoRelease = agent.match(/rv:([\d\.]+)/);
    if (geckoRelease) {
      geckoRelease = geckoRelease[1].split('.');
      version = geckoRelease[0] * 10000 + (geckoRelease[1] || 0) * 100
          + (geckoRelease[2] || 0) * 1;
    }
  }
  //检测当前浏览器是否为Chrome, 如果是，则返回Chrome的大版本号
  if (/chrome\/(\d+\.\d)/i.test(agent)) {
    browser.chrome = +RegExp['\x241'];
  }
  //检测当前浏览器是否为Safari, 如果是，则返回Safari的大版本号
  if (/(\d+\.\d)?(?:\.\d)?\s+safari\/?(\d+\.\d+)?/i.test(agent)
      && !/chrome/i.test(agent)) {
    browser.safari = +(RegExp['\x241'] || RegExp['\x242']);
  }
  // Opera 9.50+
  if (browser.opera) {
    version = parseFloat(opera.version());
  }
  // WebKit 522+ (Safari 3+)
  if (browser.webkit) {
    version = parseFloat(agent.match(/ applewebkit\/(\d+)/)[1]);
  }
  //检测当前浏览器版本号
  browser.version = version;
  return browser;
}();

var AdminLTEOptions = {
  //Bootstrap.js tooltip
  enableBSToppltip: false,
  animationSpeed: 100,
};
$.fn.serializeObject = function () {
  var sa = this.serializeArray();
  var res = {};
  for (var index in sa) {
    var nv = sa[index];
    if (nv.value !== '') {
      if (res[nv.name]) {
        res[nv.name] = res[nv.name] + ',' + nv.value;
      } else {
        res[nv.name] = nv.value;
      }
    }
  }
  return res;
};

function addTab(title, url, isNew) {
  window.top.addTab(title, url, isNew);
}
function removeTab(which) {//which可以是index也可以是title
  window.top.closeTab(which);
}
function refreshTab(which) {//which可以是index也可以是title
  window.top.refreshTab(which);
}
function plain2cacTree(data, parentField, idField) {
  for (var i1 = 0; i1 < data.length; i1++) {
    var vo = data[i1];
    for (var i2 = 0; i2 < data.length; i2++) {
      var voInner = data[i2];
      if (vo[idField] == voInner[parentField]) {
        var children = vo.children || [];
        children.push(voInner);
        voInner.isChild = true;
        vo.children = children;
      }
    }
  }
  for (var i = 0; i < data.length; i++) {
    var vo = data[i];
    vo.text = vo.feeName;
    if (vo.isChild) {
      data.splice(i, 1);
      i--;
    }
  }
  return (data);
}

function isVarEmpty(v) {
  return (v === null || (typeof v === 'undefined') || v === "");
}

/*$(document).on('keydown', '.j_num', function (event) {
  var keyCode = event.keyCode;
  //190小数点，8退格，46delete，37-40方向键
  if (keyCode >= 48 && keyCode <= 57
      || keyCode == 190 || keyCode == 8 || keyCode == 46
      || keyCode >= 37 && keyCode <= 40
  ) {

  } else {
    event.preventDefault();
  }
});*/
$(document).on('blur', '.j_num', function () {
  var $this = $(this);
  var acc=$this.data('acc');
  var val = $this.val();
  if (!isVarEmpty(val)) {
    var floatVal = parseFloat(val);
    if (!isNaN(floatVal)) {
      if(isVarEmpty(acc)){
        $this.val(floatVal);
      }else{
        $this.val(floatVal.toFixed(acc));
      }
    } else {
      $this.val('');
    }
  }
});
$(document).on('change', '.j_num', function () {
  var $this = $(this);
  var acc=$this.data('acc');
  var val = $this.val();
  if (!isVarEmpty(val)) {
    for(var i=0;i<val.length;i++){
      var char=val.charAt(i);
      if(!isNumChar(char)){
        alert("该输入域只允许填写数字");
        break;
      }
    }
    var floatVal = parseFloat(val);
    if (!isNaN(floatVal)) {
      if(isVarEmpty(acc)){
        $this.val(floatVal);
      }else{
        $this.val(floatVal.toFixed(acc));
      }
    } else {
      $this.val('');
    }
  }
});
function isNumChar(char){
  if(char==="0"
  ||char==="1"
  ||char==="2"
  ||char==="3"
  ||char==="4"
  ||char==="5"
  ||char==="6"
  ||char==="7"
  ||char==="8"
  ||char==="9"
  ||char==="."
  ){
    return true;
  }else{
    return false;
  }
}

var comdifyReg=/\d{1,3}(?=(\d{3})+$)/g;

//给数字添加千分位
function comdify(n){
  if(!n){
    return n;
  }
  n=n.toString();
  var n1=n.replace(/^(\d+)((\.\d+)?)$/,function(s,s1,s2){return s1.replace(comdifyReg,"$&,")+s2;});
  return n1;
}


