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
					
					<form action="deliveryvehiclecompany/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="delivery_vehicle_company_id" id="delivery_vehicle_company_id" value="${pd.delivery_vehicle_company_id}"/>
						<input type="hidden" name="delivery_org_id" id="delivery_org_id" value="${pd.delivery_org_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">公司名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="50" placeholder="这里输入公司名称" title="公司名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系人:</td>
								<td><input type="text" name="contact" id="contact" value="${pd.contact}" maxlength="50" placeholder="这里输入联系人" title="联系人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系电话:</td>
								<td><input type="text" name="tel" id="tel" value="${pd.tel}" maxlength="50" placeholder="这里输入联系电话" title="联系电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="address" id="address" value="${pd.address}" maxlength="50" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
							</tr>
							<input type="hidden" name="type" id="type" value="0"/>
							<input type="hidden" name="status" id="status" value="0"/>
<%-- 							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">类型:</td>
								<td><input type="number" name="type" id="type" value="${pd.type}" maxlength="32" placeholder="这里输入类型" title="类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr>
 --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准金额:</td>
								<td><input type="number" name="standard_amount" id="standard_amount" value="${pd.standard_amount}" maxlength="9" placeholder="这里输入标准金额" title="标准金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准时间:</td>
								<td><input type="number" name="standard_time" id="standard_time" value="${pd.standard_time}" maxlength="9" placeholder="这里输入标准时间" title="标准时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准里程:</td>
								<td><input type="number" name="standard_mileage" id="standard_mileage" value="${pd.standard_mileage}" maxlength="9" placeholder="这里输入标准里程" title="标准里程" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准时间外金额:</td>
								<td><input type="number" name="out_time_amount" id="out_time_amount" value="${pd.out_time_amount}" maxlength="9" placeholder="这里输入标准时间外金额" title="标准时间外金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准里程外金额:</td>
								<td><input type="number" name="out_mileage_amount" id="out_mileage_amount" value="${pd.out_mileage_amount}" maxlength="9" placeholder="这里输入标准里程外金额" title="标准里程外金额" style="width:98%;"/></td>
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
			if($("#delivery_org_id").val()==""){
				$("#delivery_org_id").tips({
					side:3,
		            msg:'请输入配送组织ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_org_id").focus();
			return false;
			}
			if($("#name").val()==""){
				$("#name").tips({
					side:3,
		            msg:'请输入公司名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
			}
			if($("#contact").val()==""){
				$("#contact").tips({
					side:3,
		            msg:'请输入联系人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#contact").focus();
			return false;
			}
			var myreg = /1[34578]\d{9}/;
			if($("#tel").val()==""){
				$("#tel").tips({
					side:3,
		            msg:'请输入联系电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#tel").focus();
				return false;
			}else if($("#tel").val().length != 11 || !myreg.test($("#tel").val())){
				$("#tel").tips({
					side:3,
		            msg:'手机号格式不正确',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#tel").focus();
				return false;
			}
			if($("#address").val()==""){
				$("#address").tips({
					side:3,
		            msg:'请输入地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#address").focus();
			return false;
			}
			if($("#type").val()==""){
				$("#type").tips({
					side:3,
		            msg:'请输入类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#type").focus();
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
			if($("#standard_amount").val()==""){
				$("#standard_amount").tips({
					side:3,
		            msg:'请输入标准金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#standard_amount").focus();
			return false;
			}else if(Number($("#standard_amount").val()) < 0.0){
				$("#standard_amount").tips({
					side:3,
		            msg:'请输入正确的标准金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#standard_amount").focus();
			return false;
			}
			if($("#standard_time").val()==""){
				$("#standard_time").tips({
					side:3,
		            msg:'请输入标准时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#standard_time").focus();
			return false;
			}else if(Number($("#standard_time").val()) < 0.0){
				$("#standard_time").tips({
					side:3,
		            msg:'请输入正确的标准时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#standard_time").focus();
			return false;
			}
			if($("#standard_mileage").val()==""){
				$("#standard_mileage").tips({
					side:3,
		            msg:'请输入标准里程',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#standard_mileage").focus();
			return false;
			}else if(Number($("#standard_mileage").val()) < 0.0){
				$("#standard_mileage").tips({
					side:3,
		            msg:'请输入正确的标准里程',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#standard_mileage").focus();
			return false;
			}
			if($("#out_time_amount").val()==""){
				$("#out_time_amount").tips({
					side:3,
		            msg:'请输入标准时间外金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#out_time_amount").focus();
			return false;
			}else if(Number($("#out_time_amount").val()) < 0.0){
				$("#out_time_amount").tips({
					side:3,
		            msg:'请输入正确的标准时间外金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#out_time_amount").focus();
			return false;
			}
			if($("#out_mileage_amount").val()==""){
				$("#out_mileage_amount").tips({
					side:3,
		            msg:'请输入标准里程外金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#out_mileage_amount").focus();
			return false;
			}else if(Number($("#out_mileage_amount").val()) < 0.0){
				$("#out_mileage_amount").tips({
					side:3,
		            msg:'请输入正确的标准里程外金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#out_mileage_amount").focus();
			return false;
			}
			if($("#createby").val()==""){
				$("#createby").tips({
					side:3,
		            msg:'请输入创建人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#createby").focus();
			return false;
			}
			if($("#create_time").val()==""){
				$("#create_time").tips({
					side:3,
		            msg:'请输入创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#create_time").focus();
			return false;
			}
			if($("#updateby").val()==""){
				$("#updateby").tips({
					side:3,
		            msg:'请输入更新人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#updateby").focus();
			return false;
			}
			if($("#update_time").val()==""){
				$("#update_time").tips({
					side:3,
		            msg:'请输入更新时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#update_time").focus();
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