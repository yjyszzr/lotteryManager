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
					
					<form action="purchasereturn/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="return_id" id="return_id" value="${pd.return_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退货编码:</td>
								<td><input type="text" name="purchase_return_code" id="purchase_return_code" value="${pd.purchase_return_code}" readonly="readonly" maxlength="50" placeholder="这里输入退货编码" title="退货编码" style="width:98%;"/></td>
							</tr>
							<c:choose>
							    <c:when test="${msg == 'save'}">
									<tr>
										<td style="width:75px;text-align: right;padding-top: 13px;">采购订单编码:</td>
										<td><input type="text" name="purchase_code" id="purchase_code" value="${pd.purchase_code}" maxlength="50" placeholder="这里输入采购订单编码" title="采购订单编码" style="width:98%;"/></td>
									</tr>
									<tr>
										<td style="width:75px;text-align: right;padding-top: 13px;">供应商编码:</td>
										<td><input type="text" name="supplier_sn" id="supplier_sn" value="${pd.supplier_sn}" maxlength="20" placeholder="这里输入供应商编码" title="供应商编码" style="width:98%;"/></td>
									</tr>
							    </c:when>
							    <c:otherwise>
							        <tr>
										<td style="width:75px;text-align: right;padding-top: 13px;">采购订单编码:</td>
										<td><input type="text" name="purchase_code" id="purchase_code" value="${pd.purchase_code}" maxlength="50" readonly="readonly" placeholder="这里输入采购订单编码" title="采购订单编码" style="width:98%;"/></td>
									</tr>
									<tr>
										<td style="width:75px;text-align: right;padding-top: 13px;">供应商编码:</td>
										<td><input type="text" name="supplier_sn" id="supplier_sn" value="${pd.supplier_sn}" readonly="readonly" maxlength="20" placeholder="这里输入供应商编码" title="供应商编码" style="width:98%;"/></td>
									</tr>
							    </c:otherwise>
							</c:choose>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退货说明:</td>
								<td><input type="text" name="remark" id="remark" value="${pd.remark}" maxlength="500" placeholder="这里输入退货说明" title="退货说明" style="width:98%;"/></td>
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
			if($("#purchase_return_code").val()==""){
				$("#purchase_return_code").tips({
					side:3,
		            msg:'请输入退货编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_return_code").focus();
			return false;
			}
			if($("#purchase_code").val()==""){
				$("#purchase_code").tips({
					side:3,
		            msg:'请输入采购订单编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_code").focus();
			return false;
			}
			if($("#supplier_sn").val()==""){
				$("#supplier_sn").tips({
					side:3,
		            msg:'请输入供应商编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#supplier_sn").focus();
			return false;
			}
			if($("#remark").val()==""){
				$("#remark").tips({
					side:3,
		            msg:'请输入退货说明',
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