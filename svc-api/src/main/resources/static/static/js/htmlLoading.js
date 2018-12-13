var mask_width = $(window).width();
var mask_height = $(window).height();

var mask_html = "<div id='mask_loading' style='z-index:9999;position:absolute;left:0;width:100%;height:" + mask_height + "px;top:0;background:white;opacity:1;filter:alpha(opacity=100);'>";
mask_html += "<div style='position:absolute;cursor1:wait;left:" + ((mask_width / 2) - 75) + "px;top:200px;width:180px;padding:5px 5px 5px 30px;";
mask_html += "background:#fff url(/static/third/easyui/themes/insdep/images/loading.gif) no-repeat scroll 5px 10px;border:2px solid #ccc;color:#000;'>";
mask_html += "正在加载，请等待...";
mask_html += "</div>";
mask_html += "</div>";

$(function () {
    $('#mask_loading').remove();

});
document.write(mask_html);