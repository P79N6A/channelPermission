$(function () {

    addTab("整车上单", "/purchase/vehicle/order");
    $('.logo').hover(function () {
        var thecolor = $('.logo').css("background-color");
        $("#systemlist").css("background-color", thecolor);

    }, function () {
        var thecolor = $('.logo').css("background-color");
        $("#systemlist").css("background-color", thecolor);
    });

});
