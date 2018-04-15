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
					
					<form action="inbound/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="inbound_id" id="inbound_id" value="${pd.inbound_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">入库单号:</td>
								<td><input type="text" name="inbound_code" id="inbound_code" value="${pd.inbound_code}" maxlength="50" placeholder="这里输入入库单号" title="入库单号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">入库类型:</td>
								<td><input type="number" name="inbound_type" id="inbound_type" value="${pd.inbound_type}" maxlength="32" placeholder="这里输入入库类型" title="入库类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">业务单号:</td>
								<td><input type="text" name="purchase_code" id="purchase_code" value="${pd.purchase_code}" maxlength="50" placeholder="这里输入业务单号" title="业务单号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">业务单明细号:</td>
								<td><input type="number" name="purchase_detail_id" id="purchase_detail_id" value="${pd.purchase_detail_id}" maxlength="32" placeholder="这里输入业务单明细号" title="业务单明细号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">到货通知号:</td>
								<td><input type="text" name="inbound_notice_code" id="inbound_notice_code" value="${pd.inbound_notice_code}" maxlength="50" placeholder="这里输入到货通知号" title="到货通知号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">业务时间:</td>
								<td><input type="text" name="business_time" id="business_time" value="${pd.business_time}" maxlength="19" placeholder="这里输入业务时间" title="业务时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库编号:</td>
								<td><input type="text" name="store_sn" id="store_sn" value="${pd.store_sn}" maxlength="20" placeholder="这里输入仓库编号" title="仓库编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
								<td><input type="text" name="store_name" id="store_name" value="${pd.store_name}" maxlength="50" placeholder="这里输入仓库名称" title="仓库名称" style="width:98%;"/></td>
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
			if($("#inbound_code").val()==""){
				$("#inbound_code").tips({
					side:3,
		            msg:'请输入入库单号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#inbound_code").focus();
			return false;
			}
			if($("#inbound_type").val()==""){
				$("#inbound_type").tips({
					side:3,
		            msg:'请输入入库类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#inbound_type").focus();
			return false;
			}
			if($("#purchase_code").val()==""){
				$("#purchase_code").tips({
					side:3,
		            msg:'请输入业务单号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_code").focus();
			return false;
			}
			if($("#purchase_detail_id").val()==""){
				$("#purchase_detail_id").tips({
					side:3,
		            msg:'请输入业务单明细号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_detail_id").focus();
			return false;
			}
			if($("#inbound_notice_code").val()==""){
				$("#inbound_notice_code").tips({
					side:3,
		            msg:'请输入到货通知号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#inbound_notice_code").focus();
			return false;
			}
			if($("#business_time").val()==""){
				$("#business_time").tips({
					side:3,
		            msg:'请输入业务时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#business_time").focus();
			return false;
			}
			if($("#store_sn").val()==""){
				$("#store_sn").tips({
					side:3,
		            msg:'请输入仓库编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_sn").focus();
			return false;
			}
			if($("#store_name").val()==""){
				$("#store_name").tips({
					side:3,
		            msg:'请输入仓库名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_name").focus();
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