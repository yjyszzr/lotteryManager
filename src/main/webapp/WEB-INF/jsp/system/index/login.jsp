<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${pd.SYSNAME}</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="static/login/bootstrap.min.css" />
<link rel="stylesheet" href="static/login/css/camera.css" />
<link rel="stylesheet" href="static/login/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="static/login/matrix-login.css" />
<link href="static/login/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="static/login/js/jquery-1.5.1.min.js"></script>
<!-- <script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script> -->
<!-- 软键盘控件start -->
<!-- <link href="static/login/keypad/css/framework/form.css" rel="stylesheet" type="text/css"/> -->
<!-- 软键盘控件end -->
 <style type="text/css">
    /*
   body{
    -webkit-transform: rotate(-3deg);
    -moz-transform: rotate(-3deg);
    -o-transform: rotate(-3deg);
	padding-top:20px;
    }
    */
    body {background-color: #F5F5DC}
      .cavs{
    	z-index:1;
    	position: fixed;
    	width:95%;
    	margin-left: 20px;
    	margin-right: 20px;
    }
/* 	.main_input_box{ */
/* 		 float: left; */
/* 	} */
/* 	.main_input_box input{ */
/* 		margin-bottom: 29px; */
/* 		height: 32px !important; */
/* 		float: left; */
/* 	} */

.main_input_box .add-on {
    padding: 9.9px 9px;
    *line-height: 33px;
    color: #fff;
    width: 30px;
    display: inline-block;
}

.main_input_box input{
    height: 32px ! important;

}
.control-group input{
    height: 30px ! important;

}
 

  </style>
  <script>
  		//window.setTimeout(showfh,3000); 
  		var timer;
		function showfh(){
			fhi = 1;
			//关闭提示晃动屏幕，注释掉这句话即可
// 			timer = setInterval(xzfh2, 10); 
		};
		var current = 0;
		function xzfh(){
			current = (current)%360;
			document.body.style.transform = 'rotate('+current+'deg)';
			current ++;
			if(current>360){current = 0;}
		};
		var fhi = 1;
		var current2 = 1;
		function xzfh2(){
			if(fhi>50){
				document.body.style.transform = 'rotate(0deg)';
				clearInterval(timer);
				return;
			}
			current = (current2)%360;
			document.body.style.transform = 'rotate('+current+'deg)';
			current ++;
			if(current2 == 1){current2 = -1;}else{current2 = 1;}
			fhi++;
		};
	</script>
</head>
<body>

	<c:if test="${pd.isMusic == 'yes' }">
	<div style="display: none">
	    <audio src="static/login/music/fh1.mp3" autoplay=""></audio>
	</div>	
	</c:if>
	<canvas class="cavs"></canvas>
	<div style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
		<!-- 登录 -->
		<div id="windows1">
		<div id="loginbox" >
			<form action="" method="post" name="loginForm" id="loginForm">
				<div class="control-group normal_text">
					<h3>
						<!-- <img src="static/login/logo.png" alt="Logo" /> -->
						彩小秘管理平台
					</h3>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i><img height="37" src="static/login/user.png" /></i>
							</span>
							<input type="text" name="loginname" id="loginname"  autocomplete="off"  placeholder="请输入手机号" autocomplete="off"  onkeyup="value=value.replace(/[^\d]/g,'')" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i><img height="37" src="static/login/suo.png" /></i>
							</span>
							<input type="password" name="password" id="password"  autocomplete="off"  placeholder="请输入密码" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lr">
								<i><img height="37" src="static/login/suo.png" /></i>
							</span>
								<input  type="text" name="code" id="code" placeholder="请输入手机验证码"  value="123456"  autocomplete="off"  style="height:30px; border:0px; display:inline-block; width:53%; line-height:28px;  margin-bottom:3px  !important;" autocomplete="off" style = "height:37px;float:left"/>
								 <input type="button" onclick="sendMsgCode();" id="sendMsgBtn" value="获取验证码" style="background:#FFD700;padding: 0px; width: 22%;height: 38px !important;}" />  
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
<!-- 							<span class="add-on bg_lr">  </span> -->
							<div style="width:50px;display:inline-block;"></div>
							<div style="width:54%;display:inline-block;"></div>
<!-- 								<input  type="text" name="code" id="code" placeholder="请输入手机验证码"  value=""  autocomplete="off"  style="height:30px; border:0px; display:inline-block; width:53%; line-height:28px;  margin-bottom:3px  !important;" autocomplete="off" style = "height:37px;float:left"/> -->
								 <input type="button" onclick="severCheck();" class="flip-link btn btn-info" id="to-recover" value = "登录" style="display:inline-block; background:#49afcd; width: 22%;height: 38px !important;}" />  
						</div>
					</div>
				</div>
<!-- 				<div class="control-group hidden"> -->
<!--  					<div  style="float:left;padding-left:10%;">  -->
<!-- 						<div class="controls"> -->
<!-- 						<div class="main_input_box "  > -->
<!-- 							<span class="add-on bg_lr"> -->
<!-- 							<i><img height="37" src="static/login/shouji.png"  /></i></span> -->
<!-- 					</div>   -->
<!--  						<div style="float: left;" >   -->
<!-- 							<input  type="text" name="code" id="code" placeholder="请输入手机验证码"  value=""  autocomplete="off" /> -->
<!-- 						</div> -->
<!-- 						<div style="float: left; "  > -->
<!-- 						<span style="height:32px;  cursor:pointer;  " > -->
<!-- 						 <input type="button" onclick="sendMsgCode();" id="sendMsgBtn" value="获取验证码" style="float: left; background:#FFD700;padding: 0px; width: 90px;height: 40px !important;}" />   -->
<!-- 						</span> -->
<!-- 						</div> -->
<!-- 				</div> -->
<!-- 				</div> -->
				<!-- <div style="float:left;padding-left:10%;">
					<div style="float: left;">
						<input name="form-field-checkbox" id="saveid" type="checkbox"
							onclick="savePaw();" style="padding-top:0px;" />
					</div>
					<div style="float: left;margin-top:3px;">
						<font color="white">记住密码</font>
					</div> 
				</div> -->
				<div class="form-actions hidden">
					<div style="width:85%;padding-left:10%;" " >

						<!-- <div style="float: left;padding-top:2px;">
							<i><img src="static/login/yan.png" /></i>
						</div>
						<div style="float: left;" class="codediv">
							<input type="text" name="code" id="code" class="login_code"
								style="height:16px; padding-top:4px;" />
						</div>
						<div style="float: left;">
							<i><img style="height:22px;" id="codeImg" alt="点击更换" title="点击更换" src="" /></i>
						</div> -->
					<%-- 	<c:if test="${pd.isZhuce == 'yes' }">
						<span class="pull-right"><a href="javascript:changepage(1);" class="btn btn-success">注册</a></span>
						</c:if> --%>
<!-- 							<span class="pull-right"><a onclick="severCheck();" class="flip-link btn btn-info" id="to-recover">登录</a></span> -->
					</div>
				</div>
			</form>
			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr">Copyright © 数字传奇 2018</span></font>
				</div>
			</div>
		</div>
		</div>
		<!-- 注册 -->
<!-- 		<div id="windows2" style="display: none;"> -->
<!-- 		<div id="loginbox"> -->
<!-- 			<form action="" method="post" name="loginForm" id="loginForm"> -->
<!-- 				<div class="control-group normal_text"> -->
<!-- 					<h3> -->
<!-- 						<img src="static/login/logo.png" alt="Logo" /> -->
<!-- 						彩小秘管理平台 -->
<!-- 					</h3> -->
<!-- 				</div> -->
<!-- 				<div class="control-group"> -->
<!-- 					<div class="controls"> -->
<!-- 						<div class="main_input_box"> -->
<!-- 							<span class="add-on bg_lg"> -->
<!-- 							<i>用户</i> -->
<!-- 							</span><input type="text" name="USERNAME" id="USERNAME" value="" placeholder="请输入用户名" /> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="control-group"> -->
<!-- 					<div class="controls"> -->
<!-- 						<div class="main_input_box"> -->
<!-- 							<span class="add-on bg_ly"> -->
<!-- 							<i>密码</i> -->
<!-- 							</span><input type="password" name="PASSWORD" id="PASSWORD" placeholder="请输入密码"   value=""/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="control-group"> -->
<!-- 					<div class="controls"> -->
<!-- 						<div class="main_input_box"> -->
<!-- 							<span class="add-on bg_ly"> -->
<!-- 							<i>重输</i> -->
<!-- 							</span><input type="password" name="chkpwd" id="chkpwd" placeholder="请重新输入密码"   value=""/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="control-group"> -->
<!-- 					<div class="controls"> -->
<!-- 						<div class="main_input_box"> -->
<!-- 							<span class="add-on bg_lg"> -->
<!-- 							<i>姓名</i> -->
<!-- 							</span><input type="text" name="NAME" id="name" value="" placeholder="请输入姓名" /> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="control-group"> -->
<!-- 					<div class="controls"> -->
<!-- 						<div class="main_input_box"> -->
<!-- 							<span class="add-on bg_lg"> -->
<!-- 							<i>邮箱</i> -->
<!-- 							</span><input type="text" name="EMAIL" id="EMAIL" value="" placeholder="请输入邮箱" /> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="form-actions"> -->
<!-- 					<div style="width:86%;padding:13px;"> -->

<!-- 						<div style="float: left;padding-top:2px;">
<!-- 							<i><img src="static/login/yan.png" /></i> -->
<!-- 						</div> -->
<!-- 						<div style="float: left;" class="codediv"> -->
<!-- 							<input type="text" name="rcode" id="rcode" class="login_code" -->
<!-- 								style="height:16px; padding-top:4px;" /> -->
<!-- 						</div> -->
<!-- 						<div style="float: left;"> -->
<!-- 							<i><img style="height:22px;" id="zcodeImg" alt="点击更换" title="点击更换" src="" /></i> -->
<!-- 						</div> --> 
<!-- 						<span class="pull-right" style="padding-right:3%;"><a href="javascript:changepage(2);" class="btn btn-success">取消</a></span> -->
<!-- 						<span class="pull-right"><a onclick="register();" class="flip-link btn btn-info" id="to-recover">提交</a></span> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</form> -->
<!-- 			<div class="controls"> -->
<!-- 				<div class="main_input_box"> -->
<!-- 					<font color="white"><span id="nameerr">Copyright © 数字传奇 2018</span></font> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		</div> -->
		
	</div>
<!-- 	<div id="templatemo_banner_slide" class="container_wapper"> -->
<!-- 		<div class="camera_wrap camera_emboss" id="camera_slide"> -->
			<!-- 背景图片 -->
<%-- 			<c:choose> --%>
<%-- 				<c:when test="${not empty pd.listImg}"> --%>
<%-- 					<c:forEach items="${pd.listImg}" var="var" varStatus="vs"> --%>
<%-- 						<div data-src="static/login/images/${var.FILEPATH }"></div> --%>
<%-- 					</c:forEach> --%>
<%-- 				</c:when> --%>
<%-- 				<c:otherwise> --%>
<!-- 					<div data-src="static/login/images/banner_slide_01.jpg"></div> -->
<!-- 					<div data-src="static/login/images/banner_slide_01.jpg"></div> -->
<!-- 					<div data-src="static/login/images/banner_slide_01.jpg"></div> -->
<!-- 					<div data-src="static/login/images/banner_slide_01.jpg"></div> -->
<!-- 					<div data-src="static/login/images/banner_slide_01.jpg"></div> -->
<!-- 					<div data-src="static/login/images/banner_slide_01.jpg"></div> -->
<%-- 				</c:otherwise> --%>
<%-- 			</c:choose> --%>
<!-- 		</div> -->
		<!-- #camera_wrap_3 -->
<!-- 	</div> -->

	<script type="text/javascript">
	var countdown=60; 
	function sendMsgCode(){
	    var obj = $("#sendMsgBtn");
	    var loginName = $("#loginname").val();
// 	    var url= "";
<%-- 	    var url="<%=path%>login_toSend/smsCode.do"; --%>
	    $.ajax({  
	        type:"POST",
	        url: "login_smsCode",  
	        data:{mobile:loginName},
	        dataType:'json',
	        cache: false,
	        success: function (data) {
			    settime(obj);
	        } ,error: function (data){
				$("#loginname").tips({
					side : 1,
					msg : "该手机号不合法",
					bg : '#FF5080',
					time : 15
				});
				showfh();
				$("#loginname").focus();
	        },  
	    });  
    }
	
	
	function settime(obj) { //发送验证码倒计时
	    if (countdown == 0) { 
	        obj.attr('disabled',false); 
	        //obj.removeattr("disabled"); 
	        obj.val("重新获取");
	        countdown = 60; 
	        return;
	    } else { 
	        obj.attr('disabled',true);
	        obj.val("重新发送(" + countdown + ")");
	        countdown--; 
	    } 
	setTimeout(function() { 
	    settime(obj) }
	    ,1000) 
	}
	 /**
	     *     使用方法
	     * $(selector).tips({   //selector 为jquery选择器
	     *  msg:'your messages!',    //你的提示消息  必填
	     *  side:1,  //提示窗显示位置  1，2，3，4 分别代表 上右下左 默认为1（上） 可选
	     *  color:'#FFF', //提示文字色 默认为白色 可选
	     *  bg:'#F00',//提示窗背景色 默认为红色 可选
	     *  time:2,//自动关闭时间 默认2秒 设置0则不自动关闭 可选
	     *  x:0,//横向偏移  正数向右偏移 负数向左偏移 默认为0 可选
	     *  y:0,//纵向偏移  正数向下偏移 负数向上偏移 默认为0 可选
	     * })
	     */
		//服务器校验
		function severCheck(){
			if(check()){
				var loginname = $("#loginname").val();
				var password = $("#password").val();
				var code_ = $("#code").val();
				var code = "qq313596790fh"+loginname+",fh,"+password+"QQ978336446fh"+",fh,"+code_;
				$.ajax({
					type: "POST",
					url: 'login_login',
			    	data: {KEYDATA:code,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" == data.result){
							saveCookie();
							window.location.href="main/index";
						}else if("usererror" == data.result){
							$("#loginname").tips({
								side : 1,
								msg : "用户名或密码有误",
								bg : '#FF5080',
								time : 1
							});
							showfh();
							$("#loginname").focus();
						}else if("codeerror" == data.result){
							 $("#code").tips({
								side : 1,
								msg : "验证码输入有误",
								bg : '#FF5080',
								time : 1
							});
							showfh();
							$("#code").focus(); 
						}
						else{
							$("#code").tips({
								side : 1,
								msg : "参数有误",
								bg : '#FF5080',
								time : 1
							});
							showfh();
							$("#code").focus();
						}
					}
				});
			}
		}
	
		$(document).ready(function() {
			changeCode1();
			$("#codeImg").bind("click", changeCode1);
			$("#zcodeImg").bind("click", changeCode2);
		});

		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		function changeCode1() {
			$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
		}
		function changeCode2() {
			$("#zcodeImg").attr("src", "code.do?t=" + genTimestamp());
		}

		//客户端校验
		function check() {

			if ($("#loginname").val() == "") {
				$("#loginname").tips({
					side : 2,
					msg : '用户名不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}
			if ($("#password").val() == "") {
				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#password").focus();
				return false;
			}
			if ($("#code").val() == "") {
				$("#code").tips({
					side : 2,
					msg : '验证码不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#code").focus();
				return false;
			}

			$("#loginbox").tips({
				side : 1,
				msg : '正在登录 , 请稍后 ...',
				bg : '#68B500',
				time : 3
			});

			return true;
		}

		function savePaw() {
			if (!$("#saveid").attr("checked")) {
				$.cookie('loginname', '', {
					expires : -1
				});
				$.cookie('password', '', {
					expires : -1
				});
				$("#loginname").val('');
				$("#password").val('');
			}
		}

		function saveCookie() {
			if ($("#saveid").attr("checked")) {
				$.cookie('loginname', $("#loginname").val(), {
					expires : 7
				});
				$.cookie('password', $("#password").val(), {
					expires : 7
				});
			}
		}
		
		jQuery(function() {
			var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if (typeof(loginname) != "undefined"
					&& typeof(password) != "undefined") {
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#saveid").attr("checked", true);
				$("#code").focus();
			}
		});
		
		//登录注册页面切换
		function changepage(value) {
			if(value == 1){
				$("#windows1").hide();
				$("#windows2").show();
				changeCode2();
			}else{
				$("#windows2").hide();
				$("#windows1").show();
				changeCode1();
			}
		}
		
	//注册
	function rcheck(){
		if($("#USERNAME").val()==""){
			$("#USERNAME").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#USERNAME").focus();
			$("#USERNAME").val('');
			return false;
		}else{
			$("#USERNAME").val(jQuery.trim($('#USERNAME').val()));
		}
		if($("#PASSWORD").val()==""){
			$("#PASSWORD").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PASSWORD").focus();
			return false;
		}
		if($("#PASSWORD").val()!=$("#chkpwd").val()){
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		if($("#EMAIL").val()==""){
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}
		/* if ($("#rcode").val() == "") {
			$("#rcode").tips({
				side : 1,
				msg : '验证码不得为空',
				bg : '#AE81FF',
				time : 3
			});
			$("#rcode").focus();
			return false;
		} */
		return true;
	}
	
	//提交注册
	function register(){
		if(rcheck()){
			var nowtime = date2str(new Date(),"yyyyMMdd");
			$.ajax({
				type: "POST",
				url: 'appSysUser/registerSysUser.do',
		    	data: {USERNAME:$("#USERNAME").val(),PASSWORD:$("#PASSWORD").val(),NAME:$("#name").val(),EMAIL:$("#EMAIL").val(),rcode:$("#rcode").val(),FKEY:$.md5('USERNAME'+nowtime+',fh,'),tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("00" == data.result){
						$("#windows2").hide();
						$("#windows1").show();
						$("#loginbox").tips({
							side : 1,
							msg : '注册成功,请登录',
							bg : '#68B500',
							time : 3
						});
						changeCode1();
					}else if("04" == data.result){
						$("#USERNAME").tips({
							side : 1,
							msg : "用户名已存在",
							bg : '#FF5080',
							time : 15
						});
						showfh();
						$("#USERNAME").focus();
					}
					/* else if("06" == data.result){
						$("#rcode").tips({
							side : 1,
							msg : "验证码输入有误",
							bg : '#FF5080',
							time : 15
						});
						showfh();
						$("#rcode").focus();
					} */
				}
			});
		}
	}
	
	//邮箱格式校验
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}
	//js  日期格式
	function date2str(x,y) {
	     var z ={y:x.getFullYear(),M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()};
	     return y.replace(/(y+|M+|d+|h+|m+|s+)/g,function(v) {return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-(v.length>2?v.length:2))});
	 	};
	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
	<c:if test="${'1' == pd.msg}">
		<script type="text/javascript">
		$(tsMsg());
		function tsMsg(){
			alert('此用户在其它终端已经早于您登录,您暂时无法登录');
		}
		</script>
	</c:if>
	<c:if test="${'2' == pd.msg}">
		<script type="text/javascript">
			$(tsMsg());
			function tsMsg(){
				alert('您被系统管理员强制下线或您的帐号在别处登录');
			}
		</script>
	</c:if>
	<script src="static/login/js/bootstrap.min.js"></script>
	<script src="static/js/jquery-1.7.2.js"></script>
	<script src="static/login/js/jquery.easing.1.3.js"></script>
	<script src="static/login/js/jquery.mobile.customized.min.js"></script>
	<script src="static/login/js/camera.min.js"></script>
	<script src="static/login/js/templatemo_script.js"></script>
	<script src="static/login/js/ban.js"></script>
	<script type="text/javascript" src="static/js/jQuery.md5.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
	
	 <!-- 软键盘控件start -->
	 <!--
	<script type="text/javascript" src="static/login/keypad/js/form/keypad.js"></script>
	<script type="text/javascript" src="static/login/keypad/js/framework.js"></script>
	  -->
	<!--软键盘控件end -->
</body>

</html>