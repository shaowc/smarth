$(function(){
	console.log('physical.ready');
});



/**
 * 冒泡提示插件
 * 基于zepto
 * by wcc
 * 栗子：$.Tip({content:'我是提示的内的内容'});
 * 参数列表
 * id : '',				//ID可为空,用来防止重复显示。
 * content : '',			//提示的文本，对应四种颜色有默认的提示语句
 * countdown : 2000, 		//关闭的倒计时
 * zindex : 2015,    	 	//层级
 * showbox : 'body',		//显示到的位置
 * css:{},
 * callback: null			//回调
 */
!(function($) {
	$.Tip = function (opt) {
		//参数列表
		$.Tip.defaults = {
			id : '',				//ID可为空,用来防止重复显示。
			content : '',			//提示的文本，对应四种颜色有默认的提示语句
			countdown : 2000, 		//关闭的倒计时
			zindex : 2015,    	 	//层级
			showbox : 'body',		//显示到的位置
			css:{
				'position': 'absolute',
				'padding':'10px 15px',
				'border-radius':'2px',
				'background-color': 'rgba(51,51,51,.9)',
				'color':'#fff',
				'font-size': '14px',
				'text-align': 'center',
				'transition': 'opacity .3s linear'
			},
			callback: null			//回调
		};
		//导入参数
		var opt = $.extend({}, $.Tip.defaults, opt);
		//判断ID 防止重复触发
		if($('#'+opt.id).length > 0){return false};
		// 定义DIV
		var $html = $('<div></div>');
		opt.id ? $html.attr('id',opt.id) : '';
		// opt.id && $html.attr('id',opt.id);
		//绑定css//加入文本
		$html.css(opt.css)
			 .html(opt.content)
			 .appendTo(opt.showbox)
			 .css({
			 	'width':'190px',
			 	'left':'50%',
			 	'opacity': '0',
			 	'z-index': opt.zindex
			 });
		//控制显示位置。
		var top = $(window).height()/2+$(window).scrollTop();
		// console.log(top);
		$html.css('top', top);
		//垂直居中和水平居中
		$html.css('margin-top',-$html.height()/2)
			 .css('margin-left',-$html.width()/2);
		$html.css('opacity',1);
		//秒后销毁组件
		setTimeout(function(){
			$html.css('opacity',0);
			//console.log('隐藏了');
			setTimeout(function(){
				$html.remove();
				//console.log(opt.callback);
				if(opt.callback){
					//console.log('回调了');
					opt.callback();
				}
			},500);
		},opt.countdown);
	};
})(Zepto);









/**
 * 移动端锁屏
 */
// $.Wait();
// $.Wait.close();
!(function($) {

	$.Wait = function (opt) {
		//参数列表
		$.Wait.defaults = {
			content : '',			//提示的文本，对应四种颜色有默认的提示语句
			movemask : function(){}
		};

		//导入参数
		var opt = $.extend({}, $.Wait.defaults, opt);

		if($('#fwait').length > 0){return}
		var p = Gethtml();
		//获得html
		function Gethtml(){
			var html = $('<div id="fwait"><dl><dt></dt><dd>数据处理中</dd></dl></div>');
			html.css({
				'position': 'fixed',
				'left': 0,
				'right': 0,
				'top':0,
				'bottom':0,
				'background-color': 'rgba(0,0,0,.5)',
				'font-size': '14px',
				'z-index':'10000'
			});
			html.find('dl').css({
				'position': 'absolute',
				'display': 'block',
				'background-color': '#fff',
				'top':'50%',
				'left': '50%',
				'margin-left': '-80px',
				'height': '48px',
				'line-height': '48px',
				'margin-top': '-24px',
				'width': '8rem',
				'-webkit-border-radius': '4px',
				'border-radius': '4px',
				'overflow': 'hidden'
			});
			html.find('dt').css({
				'float': 'left',
				'width': '48px',
				'height': '48px;',
				'background': 'url("/images/loading.svg") no-repeat center center',
				'-webkit-background-size': '50%',
				'background-size': '50%'
			});
			html.find('dd').css({
				'margin-left': '56px'
			});
			html.on('touchmove',function(){
				return false;
			});
			html.on('click',opt.movemask);
			$('body').append(html);
			return html;
		}

		//显示锁屏等待
		function showWait(){
			if(!p){
				p = Gethtml();
			}
			p.show();
		}
		showWait();
	}
	$.Wait.close = function(){
		$('#fwait').remove();
	};
})(Zepto);




/**
 * 请求等待，锁屏
 */
$.showLoadingToast = function(){
	var toastHtml = '<div id="loadingToast" class="weui_loading_toast" style="display: none;">'+
	'    <div class="weui_mask_transparent"></div>'+
	'    <div class="weui_toast">'+
	'        <div class="weui_loading">'+
	'            <div class="weui_loading_leaf weui_loading_leaf_0"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_1"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_2"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_3"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_4"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_5"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_6"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_7"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_8"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_9"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_10"></div>'+
	'            <div class="weui_loading_leaf weui_loading_leaf_11"></div>'+
	'        </div>'+
	'        <p class="weui_toast_content">数据加载中</p>'+
	'    </div>'+
	'</div>';
	var toast = $('#loadingToast');
	if(toast.length == 0){
		$('body').append(toastHtml);
	}

	$('#loadingToast').show();
}
//隐藏加载数据锁屏
$.hideLoadingToast = function(){
	$('#loadingToast').hide();
}


/**
 * 简单封装的ajax请求
 * @param  {str} id      ajaxID 用来防止重复触发
 * @param  {str} url     请求地址
 * @param  {str} type    请求类型 POST/GET
 * @param  {obj} data    提交数据
 * @param  {fn} success  提交成功的回调
 */
$.pajax = function(id,url,type,data,success){
	$.ajax({
		url: url,
		type: type,
		dataType: 'json',
		data: data,
		timeout:60000,
		beforeSend:function(){
			if($('body').attr(id) == 'true'){
				return false;
			}
			$('body').attr(id,'true');
			$.showLoadingToast();
		},
		success:function(data){
			if(data.success){
				success(data.data);
			}else{
				$.Tip({'id':'ajaxerror','content':data.msg || '服务器返回错误，请稍后再试'});
			}
		},
		error:function(){
			$.Tip({'id':'ajaxerror','content':'请求错误请稍后再试'});
		},
		complete:function(){
			$('body').removeAttr(id);
			$.hideLoadingToast();
		}
	});
}




/**
 * comform
 */
/**
 * comform
 * @param  {[type]} title  [description]
 * @param  {[type]} msg    [description]
 * @param  {[type]} ok     [description]
 * @param  {[type]} cancel [description]
 * @return {[type]}        [description]
 */
$.comform = function(title,msg,ok,cancel){
	var comformHtml = '<div class="weui_dialog_confirm" id="comform1" style="display: none;">'+
'    <div class="weui_mask"></div>'+
'    <div class="weui_dialog">'+
'        <div class="weui_dialog_hd"><strong class="weui_dialog_title">'+title+'</strong></div>'+
'        <div class="weui_dialog_bd">'+msg+'</div>'+
'        <div class="weui_dialog_ft">'+
'            <a href="javascript:;" class="weui_btn_dialog default">取消</a>'+
'            <a href="javascript:;" class="weui_btn_dialog primary">确定</a>'+
'        </div>'+
'    </div>'+
'</div>';;
	var comform = $('#comform1');
	if(comform.length == 0){
		$('body').append(comformHtml);
		$('#comform1').show().on('click','.weui_btn_dialog',function(){
			$('#comform1').remove();
		}).on('click', '.default', function () {
            if(typeof cancel == 'function'){
            	cancel();
            }
        }).on('click','.primary',function(){
        	if(typeof ok == 'function'){
        		ok();
        	}
        });
	}
}
