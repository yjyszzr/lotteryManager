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
					
					<form action="ticketchannellotteryclassify/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">出票公司名称:</td>
								<td><input type="text" name="ticket_channel_name" id="ticket_channel_name" value="${pd.ticket_channel_name}" maxlength="255" placeholder="这里输入出票公司名称" title="出票公司名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">彩种名称:</td>
								<td><input type="text" name="lottery_classify_name" id="lottery_classify_name" value="${pd.lottery_classify_name}" maxlength="255" placeholder="这里输入彩种名称" title="彩种名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">彩种编码:</td>
								<td><input type="text" name="game" id="game" value="${pd.game}" maxlength="255" placeholder="这里输入彩种编码" title="彩种编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">最小投注金额:</td>
								<td><input type="text" name="min_bet_amount" id="min_bet_amount" value="${pd.min_bet_amount}" maxlength="13" placeholder="这里输入最小投注金额" title="最小投注金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">最大投注金额:</td>
								<td><input type="text" name="max_bet_amount" id="max_bet_amount" value="${pd.max_bet_amount}" maxlength="13" placeholder="这里输入最大投注金额" title="最大投注金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">截售时间:</td>
								<td><input type="number" name="sale_end_time" id="sale_end_time" value="${pd.sale_end_time}" maxlength="32" placeholder="这里输入截售时间" title="截售时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开机时间:</td>
								<td><input type="number" name="matchine_open_time" id="matchine_open_time" value="${pd.matchine_open_time}" maxlength="32" placeholder="这里输入开机时间" title="开机时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">关机时间:</td>
								<td><input type="number" name="matchine_close_time" id="matchine_close_time" value="${pd.matchine_close_time}" maxlength="32" placeholder="这里输入关机时间" title="关机时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input type="number" name="add_time" id="add_time" value="${pd.add_time}" maxlength="32" placeholder="这里输入创建时间" title="创建时间" style="width:98%;"/></td>
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
			if($("#ticket_channel_name").val()==""){
				$("#ticket_channel_name").tips({
					side:3,
		            msg:'请输入出票公司名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ticket_channel_name").focus();
			return false;
			}
			if($("#lottery_classify_name").val()==""){
				$("#lottery_classify_name").tips({
					side:3,
		            msg:'请输入彩种名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#lottery_classify_name").focus();
			return false;
			}
			if($("#game").val()==""){
				$("#game").tips({
					side:3,
		            msg:'请输入彩种编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#game").focus();
			return false;
			}
			if($("#min_bet_amount").val()==""){
				$("#min_bet_amount").tips({
					side:3,
		            msg:'请输入最小投注金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#min_bet_amount").focus();
			return false;
			}
			if($("#max_bet_amount").val()==""){
				$("#max_bet_amount").tips({
					side:3,
		            msg:'请输入最大投注金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#max_bet_amount").focus();
			return false;
			}
			if($("#sale_end_time").val()==""){
				$("#sale_end_time").tips({
					side:3,
		            msg:'请输入截售时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sale_end_time").focus();
			return false;
			}
			if($("#matchine_open_time").val()==""){
				$("#matchine_open_time").tips({
					side:3,
		            msg:'请输入开机时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#matchine_open_time").focus();
			return false;
			}
			if($("#matchine_close_time").val()==""){
				$("#matchine_close_time").tips({
					side:3,
		            msg:'请输入关机时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#matchine_close_time").focus();
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
			if($("#add_time").val()==""){
				$("#add_time").tips({
					side:3,
		            msg:'请输入创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#add_time").focus();
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