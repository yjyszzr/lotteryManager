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
					
					<form action="purchasematerialcommit/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="material_detail_id" id="material_detail_id" value="${pd.material_detail_id}"/>
						<input type="hidden" name="purchase_code" id="purchase_code" value="${pd.purchase_code}"/>
						<input type="hidden" name="purchase_detail_id" id="purchase_detail_id" value="${pd.purchase_detail_id}"/>
						<input type="hidden" name="supplier_sn" id="supplier_sn" value="${pd.supplier_sn}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库编码:</td>
								<td><input type="text" readonly="readonly" name="store_sn" id="store_sn" value="${pd.store_sn}" maxlength="20" placeholder="这里输入仓库编码" title="仓库编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
								<td><input type="text" readonly="readonly" name="store_name" id="store_name" value="${pd.store_name}" maxlength="50" placeholder="这里输入仓库名称" title="仓库名称" style="width:70%;"/>
									<a class="btn btn-mini btn-primary" onclick="selectStore('${pd.supplier_sn}');">选择仓库</a>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物料编码:</td>
								<td>
								<input type="hidden" name="sku_id" id="sku_id" value="${pd.sku_id}" maxlength="32" placeholder="这里输入物料ID" title="物料ID"/>
								<input type="hidden" name="spec" id="spec" value="${pd.spec}" maxlength="32" placeholder="这里输入物料ID" title="物料ID"/>
								<input type="text" readonly="readonly" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" maxlength="60" placeholder="这里输入物料编码" title="物料编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物料名称:</td>
								<td><input type="text" readonly="readonly" name="sku_name" id="sku_name" value="${pd.sku_name}" maxlength="100" placeholder="这里输入物料名称" title="物料名称" style="width:70%;"/>
									<a class="btn btn-mini btn-primary" onclick="selectPurchaseDetailSku();">选择物料</a>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">领料数量:</td>
								<td><input type="number" name="quantity" id="quantity" value="${pd.quantity}" maxlength="50" placeholder="这里输入领料数量" title="领料数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物料单位:</td>
								<td><input type="text" readonly="readonly" name="unit" id="unit" value="${pd.unit}" maxlength="50" placeholder="这里输入物料单位" title="物料单位" style="width:98%;"/></td>
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
			if($("#purchase_code").val()==""){
				$("#purchase_code").tips({
					side:3,
		            msg:'请输入采购单编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_code").focus();
			return false;
			}
			if($("#purchase_detail_id").val()==""){
				$("#purchase_detail_id").tips({
					side:3,
		            msg:'请输入采购单明细ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_detail_id").focus();
			return false;
			}
			if($("#store_sn").val()==""){
				$("#store_sn").tips({
					side:3,
		            msg:'请输入仓库编码',
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
			if($("#sku_id").val()==""){
				$("#sku_id").tips({
					side:3,
		            msg:'请输入物料ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_id").focus();
			return false;
			}
			if($("#sku_encode").val()==""){
				$("#sku_encode").tips({
					side:3,
		            msg:'请输入物料编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_encode").focus();
			return false;
			}
			if($("#sku_name").val()==""){
				$("#sku_name").tips({
					side:3,
		            msg:'请输入物料名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_name").focus();
			return false;
			}
			if($("#quantity").val()==""){
				$("#quantity").tips({
					side:3,
		            msg:'请输入领料数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#quantity").focus();
			return false;
			}
			var num = $("#quantity").val();
			if( !(/^\d+$/.test(num))){
				$("#quantity").tips({
					side:3,
		            msg:'请输入正确的数量值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#quantity").focus();
				return false;
			}
			
			if(Number(num) <= 0){
				$("#quantity").tips({
					side:3,
		            msg:'请输入领料数量大于0',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#quantity").focus();
			return false;
			}
			
			if($("#unit").val()==""){
				$("#unit").tips({
					side:3,
		            msg:'请输入物料单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#unit").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		//选择商品
		function selectPurchaseDetailSku(){
			if($("#store_sn").val()==""){
				$("#store_sn").tips({
					side:3,
		            msg:'请选择仓库',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_sn").focus();
			return false;
			}
			 top.jzts();
			 selectPurchaseDetailSkuDiag = new top.Dialog();
			 selectPurchaseDetailSkuDiag.Drag=true;
			 selectPurchaseDetailSkuDiag.Title ="选择商品";
			 selectPurchaseDetailSkuDiag.URL = '<%=basePath%>goodssku/listStore.do?store_sn='+$("#store_sn").val();
			 selectPurchaseDetailSkuDiag.Width = 1250;
			 selectPurchaseDetailSkuDiag.Height = 755;
			 selectPurchaseDetailSkuDiag.Modal = true;				//有无遮罩窗口
			 selectPurchaseDetailSkuDiag. ShowMaxButton = true;	//最大化按钮
			 selectPurchaseDetailSkuDiag.ShowMinButton = true;		//最小化按钮
			 selectPurchaseDetailSkuDiag.CancelEvent = function(){ //关闭事件
				 selectPurchaseDetailSkuDiag.close();
			 };
			 selectPurchaseDetailSkuDiag.show();
			 
		}
		
		//关闭选择商品页面
		function closeSelectPurchaseDetailSkuDiag(Id,name, encode, barcode, unit, spec, isopenBatch, attrCate, taxRate){
			$("#sku_id").val(Id);
			$("#sku_name").val(name);
			$("#sku_encode").val(encode);
			$("#spec").val(spec);
			$("#unit").val(unit);
			selectPurchaseDetailSkuDiag.close();
		}
		
		//选择供应商仓库
		function selectStore(supplier_sn){
			var types = "0,4";
			 top.jzts();
			 selectStoreDiag = new top.Dialog();
			 selectStoreDiag.Drag=true;
			 selectStoreDiag.Title ="选择供应商仓库";
			 selectStoreDiag.URL = '<%=basePath%>szystore/listBySupplier.do?supplier_sn='+supplier_sn 
					 +'&supplier_sn='+supplier_sn;
			 selectStoreDiag.Width = 1250;
			 selectStoreDiag.Height = 755;
			 selectStoreDiag.Modal = true;				//有无遮罩窗口
			 selectStoreDiag. ShowMaxButton = true;	//最大化按钮
			 selectStoreDiag.ShowMinButton = true;		//最小化按钮
			 selectStoreDiag.CancelEvent = function(){ //关闭事件
				 selectStoreDiag.close();
			 };
			 selectStoreDiag.show();
		}
		
		//关闭选择供应商仓库页面
		function closeSelectStoreDiag(sn,name){
			$("#sku_id").val('');
			$("#sku_name").val('');
			$("#sku_encode").val('');
			$("#spec").val('');
			$("#unit").val('');
			
			$("#store_sn").val(sn);
			$("#store_name").val(name);
			selectStoreDiag.close();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>