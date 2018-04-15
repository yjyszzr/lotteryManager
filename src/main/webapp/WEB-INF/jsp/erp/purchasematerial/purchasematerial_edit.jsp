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
					
					<form action="purchasematerial/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="material_id" id="material_id" value="${pd.material_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
						    <c:if test="${msg == 'save'}">
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">生产订单编码:</td>
									<td><input type="text" name="purchase_code" id="purchase_code" value="${pd.purchase_code}" readonly="readonly" maxlength="50" placeholder="这里输入生产订单编码" title="生产订单编码" style="width:98%;"/></td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>
									<td>
									    <input type="text" name="purchase_detail_sku_name" id="purchase_detail_sku_name" value="${pd.purchase_detail_sku_name}" maxlength="50" readonly="readonly" placeholder="请选择商品" style="width:98%;"/>
									    <input type="hidden" name="purchase_detail_sku_id" id="purchase_detail_sku_id" value="${pd.purchase_detail_sku_id}" />
									    <!-- <a class="btn btn-mini btn-primary" onclick="selectPurchaseDetailSku();">选择商品</a> -->
									</td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
									<td>
									    <input type="text" name="store_name" id="store_name" value="${pd.store_name}" readonly="readonly" maxlength="32" placeholder="这里输入仓库名称" title="仓库名称" style="width:82%;"/>
									    <input type="hidden" name="store_sn" id="store_sn" value="${pd.store_sn}" />
										<a class="btn btn-mini btn-primary" onclick="selectStore('${pd.purchase_code}');">选择仓库</a>
									</td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">辅料名称:</td>
									<td>
									    <input type="text" name="sku_name" id="sku_name" value="${pd.sku_name}" maxlength="50" readonly="readonly" placeholder="请选择辅料名称" style="width:82%;"/>
									    <input type="hidden" name="sku_id" id="sku_id" value="${pd.sku_id}" />
									    <a class="btn btn-mini btn-primary" onclick="selectSku();">选择辅料</a>
									</td>
								</tr>
							</c:if>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">申请用料数量:</td>
								<td><input type="number" name="total_apply_quantity" id="total_apply_quantity" value="${pd.total_apply_quantity}" maxlength="11" placeholder="这里输入申请用料数量" title="申请用料数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预计到货时间:</td>
								<td>
								    <input class="span10 date-picker" name="pre_arrival_time" id="pre_arrival_time" value="${pd.pre_arrival_time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:98%;" placeholder="预计到货时间" title="预计到货时间"/>
								</td>
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
		            msg:'请输入生产订单编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_code").focus();
			return false;
			}
			if($("#purchase_detail_sku_id").val()==""){
				$("#purchase_detail_sku_id").tips({
					side:3,
		            msg:'请输入商品名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_id").focus();
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
			if($("#sku_name").val()==""){
				$("#sku_name").tips({
					side:3,
		            msg:'请输入辅料名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_name").focus();
			return false;
			}
			if($("#total_apply_quantity").val()==""){
				$("#total_apply_quantity").tips({
					side:3,
		            msg:'请输入申请用料数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#total_apply_quantity").focus();
			return false;
			}
			if(parseInt($("#total_apply_quantity").val())<=0){
				$("#total_apply_quantity").tips({
					side:3,
		            msg:'申请用料数量不能小于0！',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#total_apply_quantity").focus();
			return false;
			}
			if($("#pre_arrival_time").val()==""){
				$("#pre_arrival_time").tips({
					side:3,
		            msg:'请输入预计到货时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pre_arrival_time").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		/* var selectPurchaseDetailSkuDiag = null; */
		var selectSkuDiag = null;
		
		//选择商品
		<%-- function selectPurchaseDetailSku(){
			 top.jzts();
			 selectPurchaseDetailSkuDiag = new top.Dialog();
			 selectPurchaseDetailSkuDiag.Drag=true;
			 selectPurchaseDetailSkuDiag.Title ="选择商品";
			 selectPurchaseDetailSkuDiag.URL = '<%=basePath%>goodssku/list.do';
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
		function closeSelectPurchaseDetailSkuDiag(Id,name){
			$("#purchase_detail_sku_id").val(Id);
			$("#purchase_detail_sku_name").val(name);
			selectPurchaseDetailSkuDiag.close();
		} --%>
		
		//选择辅料
		function selectSku(){
			 top.jzts();
			 selectSkuDiag = new top.Dialog();
			 selectSkuDiag.Drag=true;
			 selectSkuDiag.Title ="选择辅料";
			 selectSkuDiag.URL = '<%=basePath%>goodssku/listSku.do';
			 selectSkuDiag.Width = 1250;
			 selectSkuDiag.Height = 755;
			 selectSkuDiag.Modal = true;				//有无遮罩窗口
			 selectSkuDiag. ShowMaxButton = true;	//最大化按钮
			 selectSkuDiag.ShowMinButton = true;		//最小化按钮
			 selectSkuDiag.CancelEvent = function(){ //关闭事件
				 selectSkuDiag.close();
			 };
			 selectSkuDiag.show();
		}
		
		//关闭选择辅料页面
		function closeSelectSkuDiag(Id,name){
			$("#sku_id").val(Id);
			$("#sku_name").val(name);
			selectSkuDiag.close();
		}
		
		//选择供应商仓库
		function selectStore(sn){
			var types = "4";
			 top.jzts();
			 selectStoreDiag = new top.Dialog();
			 selectStoreDiag.Drag=true;
			 selectStoreDiag.Title ="选择供应商仓库";
			 selectStoreDiag.URL = '<%=basePath%>szystore/listForSelect.do?types='+types;
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
			$("#store_sn").val(sn);
			$("#store_name").val(name);
			selectStoreDiag.close();
		}
		
		/* var selectStoreDiag = null; */
		
		//选择供应商仓库
		<%-- function selectStore(sn){
			 top.jzts();
			 selectStoreDiag = new top.Dialog();
			 selectStoreDiag.Drag=true;
			 selectStoreDiag.Title ="选择供应商仓库";
			 selectStoreDiag.URL = '<%=basePath%>supplierstore/list.do?purchase_code='+sn;
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
			$("#store_sn").val(sn);
			$("#store_name").val(name);
			selectStoreDiag.close();
		} --%>
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		})
	</script>
</body>
</html>