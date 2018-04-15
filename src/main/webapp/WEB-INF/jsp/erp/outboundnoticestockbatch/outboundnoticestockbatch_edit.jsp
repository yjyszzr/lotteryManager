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
					
					<form action="outboundnoticestockbatch/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="outbound_notice_stock_batch_id" id="outbound_notice_stock_batch_id" value="${pd.outbound_notice_stock_batch_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">出库通知明细id:</td>
								<td><input type="number" name="notice_detail_id" id="notice_detail_id" value="${pd.notice_detail_id}" maxlength="32" placeholder="这里输入出库通知明细id" title="出库通知明细id" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品skuId:</td>
								<td><input type="number" name="sku_id" id="sku_id" value="${pd.sku_id}" maxlength="32" placeholder="这里输入商品skuId" title="商品skuId" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">skuName:</td>
								<td><input type="text" name="sku_name" id="sku_name" value="${pd.sku_name}" maxlength="100" placeholder="这里输入skuName" title="skuName" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">skuencode:</td>
								<td><input type="text" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" maxlength="60" placeholder="这里输入skuencode" title="skuencode" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">批次:</td>
								<td><input type="text" name="batch_code" id="batch_code" value="${pd.batch_code}" maxlength="60" placeholder="这里输入批次" title="批次" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">数量:</td>
								<td><input type="number" name="quantity" id="quantity" value="${pd.quantity}" maxlength="32" placeholder="这里输入数量" title="数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格:</td>
								<td><input type="text" name="spec" id="spec" value="${pd.spec}" maxlength="100" placeholder="这里输入规格" title="规格" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位:</td>
								<td><input type="text" name="unit_name" id="unit_name" value="${pd.unit_name}" maxlength="100" placeholder="这里输入单位" title="单位" style="width:98%;"/></td>
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
			if($("#notice_detail_id").val()==""){
				$("#notice_detail_id").tips({
					side:3,
		            msg:'请输入出库通知明细id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#notice_detail_id").focus();
			return false;
			}
			if($("#sku_id").val()==""){
				$("#sku_id").tips({
					side:3,
		            msg:'请输入商品skuId',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_id").focus();
			return false;
			}
			if($("#sku_name").val()==""){
				$("#sku_name").tips({
					side:3,
		            msg:'请输入skuName',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_name").focus();
			return false;
			}
			if($("#sku_encode").val()==""){
				$("#sku_encode").tips({
					side:3,
		            msg:'请输入skuencode',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_encode").focus();
			return false;
			}
			if($("#batch_code").val()==""){
				$("#batch_code").tips({
					side:3,
		            msg:'请输入批次',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#batch_code").focus();
			return false;
			}
			if($("#quantity").val()==""){
				$("#quantity").tips({
					side:3,
		            msg:'请输入数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#quantity").focus();
			return false;
			}
			if($("#spec").val()==""){
				$("#spec").tips({
					side:3,
		            msg:'请输入规格',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#spec").focus();
			return false;
			}
			if($("#unit_name").val()==""){
				$("#unit_name").tips({
					side:3,
		            msg:'请输入单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#unit_name").focus();
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