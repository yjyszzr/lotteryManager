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
					
					<form action="goodsshoperp/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="goods_id" id="goods_id" value="${pd.goods_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注1:</td>
								<td><input type="number" name="goods_id" id="goods_id" value="${pd.goods_id}" maxlength="32" placeholder="这里输入备注1" title="备注1" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商城商品名称:</td>
								<td><input type="text" name="goods_name" id="goods_name" value="${pd.goods_name}" maxlength="100" placeholder="这里输入商城商品名称" title="商城商品名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注3:</td>
								<td><input type="number" name="sku_id" id="sku_id" value="${pd.sku_id}" maxlength="32" placeholder="这里输入备注3" title="备注3" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品货号:</td>
								<td><input type="text" name="goods_sn" id="goods_sn" value="${pd.goods_sn}" maxlength="60" placeholder="这里输入商品货号" title="商品货号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品条形码:</td>
								<td><input type="text" name="goods_barcode" id="goods_barcode" value="${pd.goods_barcode}" maxlength="60" placeholder="这里输入商品条形码" title="商品条形码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注62:</td>
								<td><input type="number" name="goods_id_from_erp" id="goods_id_from_erp" value="${pd.goods_id_from_erp}" maxlength="32" placeholder="这里输入备注62" title="备注62" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注63:</td>
								<td><input type="number" name="sku_id_from_erp" id="sku_id_from_erp" value="${pd.sku_id_from_erp}" maxlength="32" placeholder="这里输入备注63" title="备注63" style="width:98%;"/></td>
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
			if($("#goods_id").val()==""){
				$("#goods_id").tips({
					side:3,
		            msg:'请输入备注1',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_id").focus();
			return false;
			}
			if($("#goods_name").val()==""){
				$("#goods_name").tips({
					side:3,
		            msg:'请输入商城商品名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_name").focus();
			return false;
			}
			if($("#sku_id").val()==""){
				$("#sku_id").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_id").focus();
			return false;
			}
			if($("#goods_sn").val()==""){
				$("#goods_sn").tips({
					side:3,
		            msg:'请输入商品货号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_sn").focus();
			return false;
			}
			if($("#goods_barcode").val()==""){
				$("#goods_barcode").tips({
					side:3,
		            msg:'请输入商品条形码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_barcode").focus();
			return false;
			}
			if($("#goods_id_from_erp").val()==""){
				$("#goods_id_from_erp").tips({
					side:3,
		            msg:'请输入备注62',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_id_from_erp").focus();
			return false;
			}
			if($("#sku_id_from_erp").val()==""){
				$("#sku_id_from_erp").tips({
					side:3,
		            msg:'请输入备注63',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_id_from_erp").focus();
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