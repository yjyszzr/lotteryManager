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
					
					<form action="allocationdetail/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="allocation_detail_id" id="allocation_detail_id" value="${pd.allocation_detail_id}"/>
						<input type="hidden" name="from_store_sn" id="from_store_sn" value="${pd.from_store_sn}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<c:if test="${msg == 'save'}">
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">调拨单编码:</td>
									<td><input type="text" name="allocation_code" id="allocation_code" value="${pd.allocation_code}" readonly="readonly" maxlength="50" placeholder="这里输入调拨单编码" title="调拨单编码" style="width:98%;"/></td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>
									<td>
									    <input type="text" name="sku_name" id="sku_name" value="${pd.sku_name}" maxlength="50" readonly="readonly" placeholder="这里输入物料名称" style="width:78%;"/>
									    <input type="hidden" name="sku_id" id="sku_id" value="${pd.sku_id}" />
									    <input type="hidden" name="sku_encode" id="sku_encode" value="${pd.sku_encode}" />
									    <a class="btn btn-mini btn-primary" onclick="selectAllocationDetailSku();">选择商品</a>
									</td>
								</tr>
							</c:if>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预计调拨数量:</td>
								<td><input type="number" name="pre_quantity" id="pre_quantity" value="${pd.pre_quantity}" maxlength="11" placeholder="这里输入预计调拨数量" title="预计调拨数量" style="width:98%;"/></td>
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
			if($("#allocation_code").val()==""){
				$("#allocation_code").tips({
					side:3,
		            msg:'请输入调拨单编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#allocation_code").focus();
			return false;
			}
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
			if($("#pre_quantity").val()==""){
				$("#pre_quantity").tips({
					side:3,
		            msg:'请输入预计调拨数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pre_quantity").focus();
			return false;
			}
			if(parseInt($("#pre_quantity").val())<=0){
				$("#pre_quantity").tips({
					side:3,
		            msg:'预计调拨数量不能小于0！',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pre_quantity").focus();
			return false;
			}

			if("${pd.existSku_encodes}".indexOf($("#sku_encode").val())>=0){
				$("#sku_name").tips({
					side:3,
		            msg:'调拨单中此物料已存在,不允许重复！',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_name").focus();
			return false;
			}
		
			//判断物料编码是否已存在
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		var selectAllocationDetailSkuDiag = null;
		
		//选择物料
		function selectAllocationDetailSku(){
			 top.jzts();
			 selectAllocationDetailSkuDiag = new top.Dialog();
			 selectAllocationDetailSkuDiag.Drag=true;
			 selectAllocationDetailSkuDiag.Title ="选择商品";
			 selectAllocationDetailSkuDiag.URL = '<%=basePath%>goodssku/listAllocation.do?store_sn='+$("#from_store_sn").val();
			 selectAllocationDetailSkuDiag.Width = 1250;
			 selectAllocationDetailSkuDiag.Height = 755;
			 selectAllocationDetailSkuDiag.Modal = true;				//有无遮罩窗口
			 selectAllocationDetailSkuDiag. ShowMaxButton = true;	//最大化按钮
			 selectAllocationDetailSkuDiag.ShowMinButton = true;		//最小化按钮
			 selectAllocationDetailSkuDiag.CancelEvent = function(){ //关闭事件
				 selectAllocationDetailSkuDiag.close();
			 };
			 selectAllocationDetailSkuDiag.show();
		}
		
		//关闭选择物料页面
		function closeSelectAllocationDetailSkuDiag(Id,name,encode){
			$("#sku_id").val(Id);
			$("#sku_name").val(name);
			$("#sku_encode").val(encode);
			selectAllocationDetailSkuDiag.close();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>