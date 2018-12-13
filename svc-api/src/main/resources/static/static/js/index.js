/**
 * Created by Magp on 2017/4/24.
 */
var $tabs;
var rightClkMenuIndex;
$(function () {
  $tabs = $('#frame-tabs');
  $tabs.tabs({
    onContextMenu: function (e, title, index) {
      e.preventDefault();
      rightClkMenuIndex = index;
      if (index === 0) {
        return;
      }
      $('#tabTitleMenu').menu('show', {
        left: e.pageX,
        top: e.pageY
      });
    },
    onClose: function (title, index) {
      if (title === "目标承诺") {
        window.top.refreshModelList = null;
      }
    }
  });

  $('.sidebar-toggle').on('click', function () {
    setTimeout(function () {
      $tabs.tabs('resize');
    }, 300);

  });

  $('.sidebar-menu').on('click', '[menu-link]', function () {
    var $this = $(this);
    var tabAnchor = $this.data('anchor');
    var tabTitle = $this.data('title');
    addTab(tabTitle, tabAnchor);
  });
  $(".skins-container").on('click', '[data-skin]', function () {
    var $this = $(this);
    var skin = $this.data('skin');
    $("body").prop('class', '').addClass(skin);
      var thecolor = $('.logo').css("background-color");
      $("#systemlist").css("background-color",thecolor);
    $('.control-sidebar').removeClass('control-sidebar-open');
  });
});

function addTab(tabTitle, tabAnchor, isNew) {
  if (isNew !== true) {
    var existTab = $tabs.tabs('getTab', tabTitle);
  }
  if (isNew === true || existTab == null || existTab.length < 1) {
    var contentDom = '<iframe title="' + tabTitle
        + '" style="position:absolute;height:0;" class="main-frame" src="' + tabAnchor
        + '"></iframe>';
    $tabs.tabs('add', {
      title: tabTitle,
      selected: true,
      content: contentDom,
      closable: true,

    });
    setTimeout(function () {
      $("iframe[title='" + tabTitle + "']").css('height', '100%');
    }, 0);
  } else {
    $tabs.tabs('select', tabTitle);
  }
}
function menuCloseTab() {
  closeTab(rightClkMenuIndex);
}
function closeTab(which) {
  $tabs.tabs('close', which);
}
function removeAllTabs() {
  var tabCount = $tabs.tabs('tabs');
  for (var i = tabCount.length - 1; i > 0; i--) {
    $tabs.tabs('close', i);
  }
}
function removeOtherTabs(title) {
  var tabCount = $tabs.tabs('tabs');
  for (var i = tabCount.length - 1; i > 0; i--) {
    var tab = tabCount[i];
    if (rightClkMenuIndex != i) {
      $tabs.tabs('close', i);
    }
  }
}
function menuRefreshTab() {
  refreshTab(rightClkMenuIndex);
}
function refreshTab(index) {
  var $tab = $tabs.tabs('getTab', index);
  var $iframe = $($tab.children('iframe'));
  var src = $iframe.prop('src');
  $iframe.attr('src', src);
}