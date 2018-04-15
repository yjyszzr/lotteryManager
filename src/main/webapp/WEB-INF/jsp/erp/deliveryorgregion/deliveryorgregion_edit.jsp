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
					
					<form action="deliveryorgregion/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="delivery_org_region_id" id="delivery_org_region_id" value="${pd.delivery_org_region_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">配送组织ID:</td>
								<td>
								<input type="text" name="delivery_org_name" id="delivery_org_name" value="${pd.delivery_org_name}" maxlength="32"  style="width:98%;"  onclick="selectDeliveryOrgregion(${pd.delivery_org_id});"/>
								<input type="hidden" name="delivery_org_id" id="delivery_org_id" value="${pd.delivery_org_id}" maxlength="32"  style="width:98%;"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">配送范围编码:</td>
								<td><input type="text" name="region_code" id="region_code" value="${pd.region_code}" maxlength="128" placeholder="这里输入配送范围编码" title="配送范围编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">配送范围名称:</td>
								<td><input type="text" name="region_name" id="region_name" value="${pd.region_name}" maxlength="128" placeholder="这里输入配送范围名称" title="配送范围名称" style="width:98%;"/></td>
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
			if($("#delivery_org_id").val()==""){
				$("#delivery_org_id").tips({
					side:3,
		            msg:'请输入配送组织ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_org_id").focus();
			return false;
			}
			if($("#region_code").val()==""){
				$("#region_code").tips({
					side:3,
		            msg:'请输入配送范围编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#region_code").focus();
			return false;
			}
			if($("#region_name").val()==""){
				$("#region_name").tips({
					side:3,
		            msg:'请输入配送范围名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#region_name").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		var selectDeliveryOrgregionDiag = null;
		
		//选择配送组织
		function selectDeliveryOrgregion(deliveryOrgId){
			 //top.jzts();
			 selectDeliveryOrgregionDiag = new top.Dialog();
			 selectDeliveryOrgregionDiag.Drag=true;
			 selectDeliveryOrgregionDiag.Title ="选择配送组织";
			 selectDeliveryOrgregionDiag.URL = '<%=basePath%>deliveryorg/listSelect.do';
			 selectDeliveryOrgregionDiag.Width = 1250;
			 selectDeliveryOrgregionDiag.Height = 755;
			 selectDeliveryOrgregionDiag.Modal = true;				//有无遮罩窗口
			 selectDeliveryOrgregionDiag. ShowMaxButton = true;	//最大化按钮
			 selectDeliveryOrgregionDiag.ShowMinButton = true;		//最小化按钮
			 selectDeliveryOrgregionDiag.CancelEvent = function(){ //关闭事件
				 selectDeliveryOrgregionDiag.close();
			 };
			 selectDeliveryOrgregionDiag.show();
		}
		function closeSelectOrg(id,name){
			$("#delivery_org_id").val(id);
			$("#delivery_org_name").val(name);
			selectDeliveryOrgregionDiag.close();
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>