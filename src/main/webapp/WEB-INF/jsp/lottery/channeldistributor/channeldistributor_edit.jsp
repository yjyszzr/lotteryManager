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
					
					<form action="channeldistributor/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="channel_distributor_id" id="channel_distributor_id" value="${pd.channel_distributor_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户名:</td>
								<td><input type="text" name="user_name" id="user_name" value="${pd.user_name}" maxlength="60" placeholder="这里输入用户名" title="用户名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道分销号:</td>
								<td><input type="text" name="channel_distributor_num" id="channel_distributor_num" value="${pd.channel_distributor_num}" maxlength="60" placeholder="这里输入渠道分销号" title="渠道分销号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td><input type="text" name="mobile" id="mobile" value="${pd.mobile}" maxlength="20" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">分销佣金比例:</td>
								<td><input type="text" name="distributor_commission_rate" id="distributor_commission_rate" value="${pd.distributor_commission_rate}" maxlength="255" placeholder="这里输入分销佣金比例" title="分销佣金比例" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道名称:</td>
								<td><input type="text" name="channel_name" id="channel_name" value="${pd.channel_name}" maxlength="25" placeholder="这里输入渠道名称" title="渠道名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="remark" id="remark" value="${pd.remark}" maxlength="255" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
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
		            msg:'请输入用户名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_name").focus();
			return false;
			}
			if($("#channel_distributor_num").val()==""){
				$("#channel_distributor_num").tips({
					side:3,
		            msg:'请输入渠道分销号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_distributor_num").focus();
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
			if($("#distributor_commission_rate").val()==""){
				$("#distributor_commission_rate").tips({
					side:3,
		            msg:'请输入分销佣金比例',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#distributor_commission_rate").focus();
			return false;
			}
			if($("#channel_name").val()==""){
				$("#channel_name").tips({
					side:3,
		            msg:'请输入渠道名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_name").focus();
			return false;
			}
			if($("#remark").val()==""){
				$("#remark").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#remark").focus();
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