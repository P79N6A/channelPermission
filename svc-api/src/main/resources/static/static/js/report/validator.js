//自定义验证
$.extend($.fn.validatebox.defaults.rules, {
	phoneRex : {
		validator : function(value) {
			var rex = /^1[3-8]+\d{9}$/;
			var rex2 = /^((0\d{2,3})-?)(\d{7,8})(-?(\d{3,}))?$/;
			if (rex.test(value) || rex2.test(value)) {
				return true;
			} else {
				return false;
			}

		},
		message : '请输入正确手机或电话'
	},
	intOrFloat : {// 验证整数
		validator : function(value) {
			return /^\d+(\d+)?$/i.test(value);
		},
		message : '请输入正确的数量'
	},
	mail : {// 验证邮箱
		validator : function(value) {
			//return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/i.test(value);
			var reg=/\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
			   return (reg.test(value) && value.length<50);
		},
		message : '请输入正确的邮箱格式'
	},
	currency : {// 验证发票金额 
        validator : function(value) {  
            return /^\d+(\.\d+)?$/i.test(value);  
        },  
        message : '请输入正确的发票金额'  
    },
    isDate : {// 验证日期
        validator : function(value) {
            return /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/i.test(value);  
        },  
        message : '输入格式不正确，请按yyyy-MM-dd HH:mm:ss的格式输入！'
    } 
});
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
} 
$(function() {
	$('input.easyui-validatebox').validatebox(
			{
				tipOptions : { // the options to create tooltip
					showEvent : 'mouseenter',
					hideEvent : 'mouseleave',
					showDelay : 0,
					hideDelay : 0,
					zIndex : '',
					onShow : function() {
						if (!$(this).hasClass('validatebox-invalid')) {
							if ($(this).tooltip('options').prompt) {
								$(this).tooltip('update',
										$(this).tooltip('options').prompt);
							} else {
								$(this).tooltip('tip').hide();
							}
						} else {
							$(this).tooltip('tip').css({
								color : '#000',
								borderColor : '#CC9933',
								backgroundColor : '#FFFFCC'
							});
						}
					},
					onHide : function() {
						if (!$(this).tooltip('options').prompt) {
							$(this).tooltip('destroy');
						}
					}
				}
			}).tooltip({
		position : 'right',
		content : function() {
			var opts = $(this).validatebox('options');
			return opts.prompt;
		},
		onShow : function() {
			$(this).tooltip('tip').css({
				color : '#000',
				borderColor : '#CC9933',
				backgroundColor : '#FFFFCC'
			});
		}
	});
});