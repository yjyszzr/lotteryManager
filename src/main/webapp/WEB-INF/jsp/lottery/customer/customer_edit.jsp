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
					
					<form action="customer/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号:</td>
								<td>${pd.mobile}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">姓名:</td>
								<td><input type="text" name="user_name" id="user_name" value="${pd.user_name}" maxlength="20" placeholder="这里输入姓名"  title="这里输入姓名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户类型:</td>
								<td>
									<c:if test="${pd.user_state == 1}">
										新用户
									</c:if>
									<c:if test="${pd.user_state == 2}">
										老用户
									</c:if>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户来源:</td>
								<td>
									<c:if test="${pd.user_source == 1}">
										 公司资源
									</c:if>
									<c:if test="${pd.user_source == 2}">
										 微信群
									</c:if>
									<c:if test="${pd.user_source == 3}">
										 QQ群
									</c:if>
									<c:if test="${pd.user_source == 4}">
										 好友推荐
									</c:if>
									<c:if test="${pd.user_source == 5}">
										 电话访问
									</c:if>
									<c:if test="${pd.user_source == 6}">
										 其它
									</c:if>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
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
			if($("#id").val()==""){
				$("#id").tips({
					side:3,
		            msg:'请输入备注1',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#id").focus();
			return false;
			}
			if($("#user_id").val()==""){
				$("#user_id").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_id").focus();
			return false;
			}
			if($("#mobile").val()==""){
				$("#mobile").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mobile").focus();
			return false;
			}
			if($("#user_name").val()==""){
				$("#user_name").tips({
					side:3,
		            msg:'请输入用户名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_name").focus();
			return false;
			}
			if($("#user_state").val()==""){
				$("#user_state").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_state").focus();
			return false;
			}
			if($("#user_source").val()==""){
				$("#user_source").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_source").focus();
			return false;
			}
			if($("#pay_state").val()==""){
				$("#pay_state").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pay_state").focus();
			return false;
			}
			if($("#first_pay_time").val()==""){
				$("#first_pay_time").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#first_pay_time").focus();
			return false;
			}
			if($("#first_add_time").val()==""){
				$("#first_add_time").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#first_add_time").focus();
			return false;
			}
			if($("#first_add_seller_name").val()==""){
				$("#first_add_seller_name").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#first_add_seller_name").focus();
			return false;
			}
			if($("#first_add_seller_id").val()==""){
				$("#first_add_seller_id").tips({
					side:3,
		            msg:'请输入备注11',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#first_add_seller_id").focus();
			return false;
			}
			if($("#last_add_time").val()==""){
				$("#last_add_time").tips({
					side:3,
		            msg:'请输入备注12',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#last_add_time").focus();
			return false;
			}
			if($("#last_add_seller_name").val()==""){
				$("#last_add_seller_name").tips({
					side:3,
		            msg:'请输入备注13',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#last_add_seller_name").focus();
			return false;
			}
			if($("#last_add_seller_id").val()==""){
				$("#last_add_seller_id").tips({
					side:3,
		            msg:'请输入备注14',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#last_add_seller_id").focus();
			return false;
			}
			if($("#distribute_state").val()==""){
				$("#distribute_state").tips({
					side:3,
		            msg:'请输入备注15',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#distribute_state").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>