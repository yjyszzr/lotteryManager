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
					
					<form action="warningquantityline/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="warning_quantity_line_id" id="warning_quantity_line_id" value="${pd.warning_quantity_line_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注2:</td>
								<td><input type="text" name="warning_date" id="warning_date" value="${pd.warning_date}" maxlength="19" placeholder="这里输入备注2" title="备注2" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注3:</td>
								<td><input type="number" name="type" id="type" value="${pd.type}" maxlength="32" placeholder="这里输入备注3" title="备注3" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注4:</td>
								<td><input type="number" name="store_id" id="store_id" value="${pd.store_id}" maxlength="32" placeholder="这里输入备注4" title="备注4" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注5:</td>
								<td><input type="text" name="store_sn" id="store_sn" value="${pd.store_sn}" maxlength="128" placeholder="这里输入备注5" title="备注5" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注6:</td>
								<td><input type="text" name="store_name" id="store_name" value="${pd.store_name}" maxlength="128" placeholder="这里输入备注6" title="备注6" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注7:</td>
								<td><input type="number" name="sku_id" id="sku_id" value="${pd.sku_id}" maxlength="32" placeholder="这里输入备注7" title="备注7" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注8:</td>
								<td><input type="text" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" maxlength="128" placeholder="这里输入备注8" title="备注8" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注9:</td>
								<td><input type="text" name="sku_name" id="sku_name" value="${pd.sku_name}" maxlength="128" placeholder="这里输入备注9" title="备注9" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注10:</td>
								<td><input type="text" name="spec" id="spec" value="${pd.spec}" maxlength="128" placeholder="这里输入备注10" title="备注10" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注11:</td>
								<td><input type="text" name="unit_name" id="unit_name" value="${pd.unit_name}" maxlength="128" placeholder="这里输入备注11" title="备注11" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注12:</td>
								<td><input type="number" name="min_quantity" id="min_quantity" value="${pd.min_quantity}" maxlength="32" placeholder="这里输入备注12" title="备注12" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注13:</td>
								<td><input type="number" name="max_quantity" id="max_quantity" value="${pd.max_quantity}" maxlength="32" placeholder="这里输入备注13" title="备注13" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注14:</td>
								<td><input type="number" name="quantity" id="quantity" value="${pd.quantity}" maxlength="32" placeholder="这里输入备注14" title="备注14" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注15:</td>
								<td><input type="text" name="createby" id="createby" value="${pd.createby}" maxlength="128" placeholder="这里输入备注15" title="备注15" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注16:</td>
								<td><input type="text" name="create_time" id="create_time" value="${pd.create_time}" maxlength="19" placeholder="这里输入备注16" title="备注16" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注17:</td>
								<td><input type="text" name="updateby" id="updateby" value="${pd.updateby}" maxlength="128" placeholder="这里输入备注17" title="备注17" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注18:</td>
								<td><input type="text" name="update_time" id="update_time" value="${pd.update_time}" maxlength="19" placeholder="这里输入备注18" title="备注18" style="width:98%;"/></td>
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
			if($("#warning_date").val()==""){
				$("#warning_date").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#warning_date").focus();
			return false;
			}
			if($("#type").val()==""){
				$("#type").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#type").focus();
			return false;
			}
			if($("#store_id").val()==""){
				$("#store_id").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_id").focus();
			return false;
			}
			if($("#store_sn").val()==""){
				$("#store_sn").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_sn").focus();
			return false;
			}
			if($("#store_name").val()==""){
				$("#store_name").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_name").focus();
			return false;
			}
			if($("#sku_id").val()==""){
				$("#sku_id").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_id").focus();
			return false;
			}
			if($("#sku_encode").val()==""){
				$("#sku_encode").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_encode").focus();
			return false;
			}
			if($("#sku_name").val()==""){
				$("#sku_name").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_name").focus();
			return false;
			}
			if($("#spec").val()==""){
				$("#spec").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#spec").focus();
			return false;
			}
			if($("#unit_name").val()==""){
				$("#unit_name").tips({
					side:3,
		            msg:'请输入备注11',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#unit_name").focus();
			return false;
			}
			if($("#min_quantity").val()==""){
				$("#min_quantity").tips({
					side:3,
		            msg:'请输入备注12',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#min_quantity").focus();
			return false;
			}
			if($("#max_quantity").val()==""){
				$("#max_quantity").tips({
					side:3,
		            msg:'请输入备注13',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#max_quantity").focus();
			return false;
			}
			if($("#quantity").val()==""){
				$("#quantity").tips({
					side:3,
		            msg:'请输入备注14',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#quantity").focus();
			return false;
			}
			if($("#createby").val()==""){
				$("#createby").tips({
					side:3,
		            msg:'请输入备注15',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#createby").focus();
			return false;
			}
			if($("#create_time").val()==""){
				$("#create_time").tips({
					side:3,
		            msg:'请输入备注16',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#create_time").focus();
			return false;
			}
			if($("#updateby").val()==""){
				$("#updateby").tips({
					side:3,
		            msg:'请输入备注17',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#updateby").focus();
			return false;
			}
			if($("#update_time").val()==""){
				$("#update_time").tips({
					side:3,
		            msg:'请输入备注18',
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