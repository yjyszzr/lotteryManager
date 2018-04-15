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
					
					<form action="otherinstoragedetail/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="other_instorage_detail_id" id="other_instorage_detail_id" value="${pd.other_instorage_detail_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">ID:</td>
								<td><input type="number" name="other_instorage_detail_id" id="other_instorage_detail_id" value="${pd.other_instorage_detail_id}" maxlength="32" placeholder="这里输入ID" title="ID" style="width:98%;"/></td>
							</tr>
							<tr >
								<td style="width:75px;text-align: right;padding-top: 13px;">入库编码:</td>
								<td><input type="text" readonly='readonly' name="other_instorage_code" id="other_instorage_code" value="${pd.other_instorage_code}" maxlength="50" placeholder="这里输入入库编码" title="入库编码" style="width:98%;"/></td>
							</tr>
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">sku_id:</td>
								<td><input type="number" name="sku_id" id="sku_id" value="${pd.sku_id}" maxlength="32" placeholder="这里输入sku_id" title="sku_id" style="width:98%;"/></td>
							</tr>
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">物料编码:</td>
								<td><input type="text" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" maxlength="60" placeholder="这里输入sku编码" title="sku编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物料名称:</td>
								<td><input type="text" name="sku_name" readonly="readonly" onclick="selectPurchaseDetailSku()" id="sku_name" value="${pd.sku_name}" maxlength="100" placeholder="这里输入商品名称" title="商品名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格:</td>
								<td><input type="text" name="spec"  readonly="readonly" id="spec" value="${pd.spec}" maxlength="100" placeholder="这里输入规格" title="规格" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位:</td>
								<td><input type="text" name="unit" readonly="readonly"  id="unit" value="${pd.unit}" maxlength="20" placeholder="这里输入单位" title="单位" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">数量:</td>
								<td><input type="number" name="quantity" id="quantity" value="${pd.quantity}" maxlength="32" placeholder="这里输入数量" title="数量" style="width:98%;"/></td>
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
	<%@ include file="../common/selectGoods.jsp"%>
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
		/* 	if($("#other_instorage_detail_id").val()==""){
				$("#other_instorage_detail_id").tips({
					side:3,
		            msg:'请输入ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#other_instorage_detail_id").focus();
			return false;
			}
			if($("#other_instorage_code").val()==""){
				$("#other_instorage_code").tips({
					side:3,
		            msg:'请输入入库编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#other_instorage_code").focus();
			return false;
			}
			if($("#sku_id").val()==""){
				$("#sku_id").tips({
					side:3,
		            msg:'请输入sku_id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_id").focus();
			return false;
			}
			if($("#sku_encode").val()==""){
				$("#sku_encode").tips({
					side:3,
		            msg:'请输入sku编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_encode").focus();
			return false;
			} */
			if($("#sku_name").val()==""){
				$("#sku_name").tips({
					side:3,
		            msg:'请输入商品名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_name").focus();
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
			if($("#unit").val()==""){
				$("#unit").tips({
					side:3,
		            msg:'请输入单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#unit").focus();
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
			}else if( !(/^\d+$/.test($("#quantity").val())) || $("#quantity").val()<1){
				$("#quantity").tips({
					side:3,
		            msg:'请输入正确的数量值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#quantity").focus();
				return false;
			
			}
			/* if($("#arrive_quantity").val()==""){
				$("#arrive_quantity").tips({
					side:3,
		            msg:'请输入到货数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#arrive_quantity").focus();
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
			} */
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
			if(${sku_id_error==0}){
				$("#sku_name").tips({
					side:3,
		            msg:'同一入库单明细中不能有重复的物料',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_name").focus();
			
			}
		});
		
		</script>
</body>
</html>