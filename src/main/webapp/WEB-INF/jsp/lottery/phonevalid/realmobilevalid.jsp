<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />

<style type="text/css">
.table1{-webkit-box-shadow:1px 1px 1px #00FFFF;-moz-box-shadow:1px 1px 1px #00FFFF;box-shadow:1px 1px 1px #00FFFF;border-spacing:20px;}
.td1{-webkit-box-shadow:1px 1px 1px #00FFFF;-moz-box-shadow:1px 1px 1px #00FFFF;box-shadow:1px 1px 1px 1px #00FFFF;background:#FAEBD7;padding:10px;}
.idInput{
 width: 24.95rem;
 height: 2.1rem;
 background: url(../img/input_bg.png) center / contain no-repeat;
 font-size: 1.6rem;
 color: #1b1920;
 letter-spacing: 1.2rem; //最主要的部分，字间距
 padding-left: 1.2rem;
}
</style>

</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							
						<!-- 检索  -->
						<form action="userrealmanager/realMobileValid.do" method="post" name="Form" id="Form">
						<table style="border-collapse:separate; border-spacing:10px;">
							<tr style = "height: 1.0rem">
							
							</tr>
							<tr>
								<td  >
								真实姓名 : 
								</td >
								<td > 
									<input type = "text" style = "height: 2.1rem" id = "real_name"> </input>
								</td>
							</tr>
							<tr>
								<td >
								真实手机号 : 
								</td>
								<td>
									<input type = "text" style = "height: 2.1rem" id = "real_mobile"> </input>
								</td >
							</tr>
							<tr>
								<td >
								身份证号 : 
								</td>
								<td >
									<input style="width:400px" type = "text" id = "id_no" style = "height: 2.1rem" > </input>
								</td>								
							</tr>
						</table>
						<!-- 检索  -->
					
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<a class="btn btn-mini btn-success" onclick="validRealMobile();">校验</a>
									<a class="btn btn-mini btn-success" onclick="clearInfo();">清空</a>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
							<tr>
							</tr>
						</table>
						</div>
						</form>
					
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		
		function clearInfo(){
			$("#real_mobile").val("") ;
			$("#real_name").val("");
			$("#id_no").val("");
		}
		
		function validRealMobile(){
			var real_mobile = $("#real_mobile").val();
			var real_name = $("#real_name").val();
			var id_no = $("#id_no").val();
			var regMob = /^1\d{10}$/;
			
			if(isNull(real_mobile) || !regMob.test(real_mobile)){
				$("#real_mobile").tips({
					side:3,
		            msg:'手机号填写有误',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#real_mobile").focus();
				return false;
			}
			
			if(isNull(real_name)){
				$("#real_name").tips({
					side:3,
		            msg:'真实姓名不能为空',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#real_name").focus();
				return false;
			}
			
			if(isNull(id_no)){
				$("#id_no").tips({
					side:3,
		            msg:'身份证号不能为空',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#id_no").focus();
				return false;
			}
			
			if(!IdentityCodeValid(id_no)){
				return;
			}
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>usermanagercontroller/realMobileValid.do?tm='+new Date().getTime(),
		    	data: {real_mobile:real_mobile,real_name:real_name,id_no:id_no},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					alert(data.data);
				},
				error:function(data){
					alert(data.data);
				}
			});
		}
		
		function isNull(str){
			if(str == "") return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		
		
		/*
		根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
		    地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
		    出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
		    顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
		    校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。

		出生日期计算方法。
		    15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
		    2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗...
		下面是正则表达式:
		 出生日期1800-2099  (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])
		 身份证正则表达式    /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i      
		 15位校验规则 6位地址编码+6位出生日期+3位顺序号
		 18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位

		 校验位规则     公式:∑(ai×Wi)(mod 11)……………………………………(1)
		                公式(1)中： 
		                i----表示号码字符从由至左包括校验码在内的位置序号； 
		                ai----表示第i位置上的号码字符值； 
		                Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。
		                i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
		                Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1

		*/
		//身份证号合法性验证 
		//支持15位和18位身份证号
		//支持地址编码、出生日期、校验位验证
		function IdentityCodeValid(code) { 
		            var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
		            var tip = "";
		            var pass= true;

		            if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
		                tip = "号码格式错误";
		                pass = false;
		            }

		           else if(!city[code.substr(0,2)]){
		                tip = "地址编码错误";
		                pass = false;
		            }
		            else{
		                //18位身份证需要验证最后一位校验位
		                if(code.length == 18){
		                    code = code.split('');
		                    //∑(ai×Wi)(mod 11)
		                    //加权因子
		                    var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
		                    //校验位
		                    var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
		                    var sum = 0;
		                    var ai = 0;
		                    var wi = 0;
		                    for (var i = 0; i < 17; i++)
		                    {
		                        ai = code[i];
		                        wi = factor[i];
		                        sum += ai * wi;
		                    }
		                    var last = parity[sum % 11];
		                    if(parity[sum % 11] != code[17]){
		                        tip = "校验位错误";
		                        pass =false;
		                    }
		                }
		            }
		            if(!pass){
						$("#id_no").tips({
							side:3,
				            msg:'身份证'+tip,
				            bg:'#AE81FF',
				            time:2
				        });
						$("#id_no").focus();
		            }
		            return pass;
			}
		
	</script>


</body>
</html>