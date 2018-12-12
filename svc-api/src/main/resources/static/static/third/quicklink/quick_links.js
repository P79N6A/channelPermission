jQuery(function ($) {
    //创建DOM
    var
        quickHTML = document.querySelector("div.quick_link_mian"),
        quickShell = $(document.createElement('div')).html(quickHTML).addClass('quick_links_wrap'),
        quickLinks = quickShell.find('.quick_links');
    quickPanel = quickLinks.next();
    quickShell.appendTo('.mui-mbar-tabs');

    //具体数据操作
    var
        quickPopXHR,
        popTmpl = '<div class="ibar_plugin_title"><h3 style="margin-top: 15px"><%=title%></h3></div><div class="pop_panel"><%=content%></div><div class="arrow"><i></i></div><div class="fix_bg"></div>',
        quickPop = quickShell.find('#quick_links_pop')

    //showQuickPop
    var
        prevPopType,
        prevTrigger,
        doc = $(document),
        popDisplayed = false,
        hideQuickPop = function () {
            if (prevTrigger) {
                prevTrigger.removeClass('current');
            }
            $('.quick_links_wrap').css("width", '');
            popDisplayed = false;
            prevPopType = '';
            quickPop.hide();
            quickPop.animate({left: 280, queue: true});
        },
        showQuickPop = function (type) {
            $('.quick_links_wrap').css("width", '320px');
            if (quickPopXHR && quickPopXHR.abort) {
                quickPopXHR.abort();
            }
            if (type !== prevPopType) {
                var fn = quickDataFns[type];
                quickPop.html(ds.tmpl(popTmpl, fn));
                fn.init.call(this, fn);
            }
            doc.unbind('click.quick_links').one('click.quick_links', hideQuickPop);
            quickPop[0].className = 'quick_links_pop quick_' + type;
            popDisplayed = true;
            prevPopType = type;
            quickPop.show();
            quickPop.animate({left: 0, queue: true});
        };
    quickShell.bind('click.quick_links', function (e) {
        e.stopPropagation();
    });
    quickPop.delegate('a.ibar_closebtn', 'click', function () {
        quickPop.hide();
        $('.quick_links_wrap').css("width", '');
        quickPop.animate({left: 280, queue: true});
        if (prevTrigger) {
            prevTrigger.removeClass('current');
        }
    });

    //通用事件处理
    var
        view = $(window),
        quickLinkCollapsed = !!ds.getCookie('ql_collapse'),
        getHandlerType = function (className) {
            return className.replace(/current/g, '').replace(/\s+/, '');
        },
        showPopFn = function () {
            var type = getHandlerType(this.className);
            if (popDisplayed && type === prevPopType) {
                return hideQuickPop();
            }

            showQuickPop(this.className);
            if (prevTrigger) {
                prevTrigger.removeClass('current');
            }
            prevTrigger = $(this).addClass('current');
        },
        quickHandlers = {
            //购物车，最近浏览，商品咨询
            my_qlinks: showPopFn,
            message_list: showPopFn,
            history_list: showPopFn,
            leave_message: showPopFn,
            mpbtn_histroy: showPopFn,
            mpbtn_recharge: showPopFn,
            mpbtn_wdsc: showPopFn,
            //返回顶部
            return_top: function () {
                ds.scrollTo(0, 0);
                hideReturnTop();
            }
        };
    quickShell.delegate('a', 'click', function (e) {
        var type = getHandlerType(this.className);
        if (type && quickHandlers[type]) {
            quickHandlers[type].call(this);
            e.preventDefault();
        }
    });

    //Return top
    var scrollTimer, resizeTimer, minWidth = 1350;

    function resizeHandler() {
        clearTimeout(scrollTimer);
        scrollTimer = setTimeout(checkScroll, 160);
    }

    function checkResize() {
        quickShell[view.width() > 1340 ? 'removeClass' : 'addClass']('quick_links_dockright');
    }

    function scrollHandler() {
        clearTimeout(resizeTimer);
        resizeTimer = setTimeout(checkResize, 160);
    }

    function checkScroll() {
        view.scrollTop() > 100 ? showReturnTop() : hideReturnTop();
    }

    function showReturnTop() {
        quickPanel.addClass('quick_links_allow_gotop');
    }

    function hideReturnTop() {
        quickPanel.removeClass('quick_links_allow_gotop');
    }

    view.bind('scroll.go_top', resizeHandler).bind('resize.quick_links', scrollHandler);
    quickLinkCollapsed && quickShell.addClass('quick_links_min');
    resizeHandler();
    scrollHandler();
});