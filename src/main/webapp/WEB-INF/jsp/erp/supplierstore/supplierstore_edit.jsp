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
					
					<form action="supplierstore/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="supplier_store_id" id="supplier_store_id" value="${pd.supplier_store_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">供应商编码:</td>
								<td><input type="text" readonly="readonly" name="supplier_sn" id="supplier_sn" value="${pd.supplier_sn}" maxlength="50" onclick="selectSupplier();" placeholder="这里输入供应商编码" title="供应商编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">供应商名称:</td>
								<td><input type="text" readonly="readonly"  name="supplier_name" readonly="readonly" id="supplier_name" value="${pd.supplier_name}" maxlength="200" placeholder="这里输入供应商名称" title="供应商名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库编码:</td>
								<td><input type="text" readonly="readonly" name="store_sn" id="store_sn" value="${pd.store_sn}" maxlength="50" onclick="selectStore('${pd.store_sn}')" placeholder="这里输入仓库编码" title="仓库编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
								<td><input type="text" readonly="readonly" name="store_name" readonly="readonly" id="store_name" value="${pd.store_name}" maxlength="50" placeholder="这里输入仓库名称" title="仓库名称" style="width:98%;"/></td>
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
			if($("#supplier_name").val()==""){
				$("#supplier_name").tips({
					side:3,
		            msg:'请输入供应商名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#supplier_name").focus();
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
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		//选择供应商
		function selectSupplier(){
			 top.jzts();
			 selectSupplierW = new top.Dialog();
			 selectSupplierW.Drag=true;
			 selectSupplierW.Title ="选择供应商";
			 selectSupplierW.URL = '<%=basePath%>supplier/listForSelect.do';
			 selectSupplierW.Width = 1250;
			 selectSupplierW.Height = 755;
			 selectSupplierW.Modal = true;				//有无遮罩窗口
			 selectSupplierW. ShowMaxButton = true;	//最大化按钮
			 selectSupplierW.ShowMinButton = true;		//最小化按钮
			 selectSupplierW.CancelEvent = function(){ //关闭事件
				 selectSupplierW.close();
			 };
			 selectSupplierW.show();
			 
		}
		
		//关闭选择供应商
		function closeselectSupplier(supplier_sn,supplier_name){
			$("#supplier_sn").val(supplier_sn);
			$("#supplier_name").val(supplier_name);
			selectSupplierW.close();
		}
		//选择供应商仓库
		function selectStore(sn){
			var types = "0";
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
		</script>
</body>
</html>