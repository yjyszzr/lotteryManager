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
					
					<form action="channeloptionlog/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="option_id" id="option_id" value="${pd.option_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道消费者用户名称:</td>
								<td><input type="text" name="user_name" id="user_name" value="${pd.user_name}" maxlength="60" placeholder="这里输入渠道消费者用户名称" title="渠道消费者用户名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">身份证编号:</td>
								<td><input type="number" name="id_card_num" id="id_card_num" value="${pd.id_card_num}" maxlength="32" placeholder="这里输入身份证编号" title="身份证编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">真实姓名:</td>
								<td><input type="text" name="true_name" id="true_name" value="${pd.true_name}" maxlength="255" placeholder="这里输入真实姓名" title="真实姓名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td><input type="text" name="mobile" id="mobile" value="${pd.mobile}" maxlength="255" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">操作节点:</td>
								<td><input type="text" name="operation_node" id="operation_node" value="${pd.operation_node}" maxlength="255" placeholder="这里输入操作节点" title="操作节点" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">操作金额:</td>
								<td><input type="number" name="option_amount" id="option_amount" value="${pd.option_amount}" maxlength="32" placeholder="这里输入操作金额" title="操作金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">操作时间:</td>
								<td><input type="number" name="option_time" id="option_time" value="${pd.option_time}" maxlength="32" placeholder="这里输入操作时间" title="操作时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">来源:</td>
								<td><input type="text" name="source" id="source" value="${pd.source}" maxlength="20" placeholder="这里输入来源" title="来源" style="width:98%;"/></td>
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
			if($("#user_name").val()==""){
				$("#user_name").tips({
					side:3,
		            msg:'请输入渠道消费者用户名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_name").focus();
			return false;
			}
			if($("#id_card_num").val()==""){
				$("#id_card_num").tips({
					side:3,
		            msg:'请输入身份证编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#id_card_num").focus();
			return false;
			}
			if($("#true_name").val()==""){
				$("#true_name").tips({
					side:3,
		            msg:'请输入真实姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#true_name").focus();
			return false;
			}
			if($("#mobile").val()==""){
				$("#mobile").tips({
					side:3,
		            msg:'请输入电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mobile").focus();
			return false;
			}
			if($("#operation_node").val()==""){
				$("#operation_node").tips({
					side:3,
		            msg:'请输入操作节点',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#operation_node").focus();
			return false;
			}
			if($("#status").val()==""){
				$("#status").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#status").focus();
			return false;
			}
			if($("#option_amount").val()==""){
				$("#option_amount").tips({
					side:3,
		            msg:'请输入操作金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#option_amount").focus();
			return false;
			}
			if($("#option_time").val()==""){
				$("#option_time").tips({
					side:3,
		            msg:'请输入操作时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#option_time").focus();
			return false;
			}
			if($("#source").val()==""){
				$("#source").tips({
					side:3,
		            msg:'请输入来源',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#source").focus();
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