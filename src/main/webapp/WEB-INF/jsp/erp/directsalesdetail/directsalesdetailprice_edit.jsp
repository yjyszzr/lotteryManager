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
					
					<form action="directsalesdetail/editprice.do" name="Form" id="Form" method="post">
						<input type="hidden" name="direct_sales_detail_id" id="direct_sales_detail_id" value="${pd.direct_sales_detail_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">内部直销订单编码:</td>

								<td><input type="text" name="direct_sales_code" id="direct_sales_code" value="${pd.direct_sales_code}" readonly="readonly" maxlength="50" placeholder="这里输入内部直销订单编码" title="内部直销订单编码" style="width:98%;"/></td>

							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品编码:</td>
								<td><input type="text" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" readonly="readonly" maxlength="50" placeholder="这里输入商品编码" title="商品编码" style="width:70%;"/>
								</td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>

								<td><input type="text" name="sku_name" id="sku_name" value="${pd.sku_name}" readonly="readonly" maxlength="50" placeholder="这里输入商品名称" title="商品名称" style="width:98%;"/>
									<input hidden="text" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" readonly="readonly" maxlength="50" placeholder="这里输入商品编码" title="商品编码" style="width:98%;"/>
									
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">数量:</td>

								<td><input type="number" name="quantity" id="quantity" value="${pd.quantity}" readonly="readonly" maxlength="32" placeholder="这里输入数量" title="数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位:</td>
								<td><input type="text" name="unit" id="unit" value="${pd.unit}" readonly="readonly" maxlength="20" placeholder="这里输入单位" title="单位" style="width:98%;"/></td>

							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单价:</td>
								<td><input type="number" name="price" id="price" value="${pd.price}" maxlength="20" placeholder="这里输入单价" title="单价" style="width:98%;"/></td>
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

			if($("#price").val()==""){
				$("#price").tips({
					side:3,
		            msg:'请输入单价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#price").focus();
			return false;
			}
		
			if($("#price").val() < 0){
				$("#price").tips({
					side:3,
		            msg:'单价不能小于0',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#price").focus();
			return false;
			}
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		var selectPurchaseDetailSkuDiag = null;
		
		//选择商品
		function selectPurchaseDetailSku(){
			 top.jzts();
			 selectPurchaseDetailSkuDiag = new top.Dialog();
			 selectPurchaseDetailSkuDiag.Drag=true;
			 selectPurchaseDetailSkuDiag.Title ="选择商品";
			 selectPurchaseDetailSkuDiag.URL = '<%=basePath%>goodssku/list.do';
			 selectPurchaseDetailSkuDiag.Width = 1250;
			 selectPurchaseDetailSkuDiag.Height = 755;
			 selectPurchaseDetailSkuDiag.Modal = true;				//有无遮罩窗口
			 selectPurchaseDetailSkuDiag.ShowMaxButton = true;	//最大化按钮
			 selectPurchaseDetailSkuDiag.ShowMinButton = true;		//最小化按钮
			 selectPurchaseDetailSkuDiag.CancelEvent = function(){ //关闭事件
				 selectPurchaseDetailSkuDiag.close();
			 };
			 selectPurchaseDetailSkuDiag.show();
		}
		
		//关闭选择商品页面
		function closeSelectPurchaseDetailSkuDiag(Id,name){
			$("#sku_id").val(Id);
			$("#sku_name").val(name);
			selectPurchaseDetailSkuDiag.close();
		}
		//关闭选择调出仓库页面
		function closeFromStormSnDiag(Id,name){
			$("#sku_id").val(Id);
			$("#sku_name").val(name);
			selectFromStormSnDiag.close();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>