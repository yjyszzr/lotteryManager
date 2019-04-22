<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
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
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
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
					
					<form action="superwhitelist/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="user_id" id="user_id" value="${pd.user_id}"/>
						<input type="hidden" name="store_id" id="store_id" value="${pd.store_id}"/>
						<input type="hidden" name="mobile" id="mobile" value="${pd.mobile}"/>
						<input type="hidden" name="app_code_name" id="app_code_name" value="${pd.app_code_name}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户id:</td>
								<td>						
									 ${pd.user_id}
								</td>	 
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号:</td>
								<td id="mobile">									
								 	${pd.mobile}
								 </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">平台来源:</td>
								<td id="store_name">
										圣和彩店
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退款原因:</td>
								<td>
									<select id="type" name="type"  value="" style ="width:42%">
								        <option value="0" >帐户退款</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退款金额:</td>
								<td><input type="number" name="number" onclick="noFuc(this.value)" id="number" maxlength="32" placeholder="这里输入退款金额" title="退款金额" style="width:42%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">确认</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			try {
				var length = $("#number").val().toString().split(".")[1].length
				if (length > 2) {
					$("#number").tips({
						side:3,
			            msg:'小数点位最多支持两位',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#frozen_money").focus();
					return false;
				}
			} catch (e) {

			}

			if($("#number").val()==""){
				$("#number").tips({
					side:3,
		            msg:'请输入退款金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#frozen_money").focus();
			return false;
			}

			var str = "请确认，\n"
					+ "\手机号：" + $("#mobile").val()
					+ "\n店铺：" + $("#store_name").text().replace(/(^\s*)|(\s*$)/g, "")
					+ "\n退款金额：" + $("#number").val()
					;

			if (window.confirm(str)) {
				$("#Form").submit();
                return true;
             }else{
                return false;
            }
			
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});

        function noFue(val){
            document.getElementById('number').value = val >= 0 ? val : 0;
        }
		</script>
</body>
</html>