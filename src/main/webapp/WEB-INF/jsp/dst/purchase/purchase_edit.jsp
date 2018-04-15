<%@page import="com.fh.common.TextConfig"%>
<%@page import="com.fh.common.PurchaseConstants"%>
<%@page import="com.fh.util.DateUtil"%>
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
					<div style="display:none">
								${typeText=TextConfig.getPurchaseBsTypeText(pd.business_type)}
							</div>
					<form action="purchase/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="purchase_id" id="purchase_id" value="${pd.purchase_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">${typeText }编码:</td>
								<td><input type="text" name="purchase_code" readonly="readonly"  id="purchase_code" value="${pd.purchase_code}" maxlength="30" placeholder="这里输入采购编码" title="采购编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">${typeText }日期:</td>
							    <td><input class="span10 date-picker" name="purchase_date" id="purchase_date" value="${pd.purchase_date==null?'':DateUtil.toDateStr(pd.purchase_date)}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="采购日期" title="采购日期" style="width:98%;"/></td>
						</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">供应商:</td>
								
								<td><input type="hidden" name="supplier_sn" id="supplier_sn" value="${pd.supplier_sn}"/> 
								<input type="text" name="supplier_name" id="supplier_name" readonly="readonly" value="${pd.supplier_name}" onclick='${pd.supplier_name==null?"selectSupplier()":""};' maxlength="20" placeholder="这里输入供应商" title="供应商" style="width:98%;"/></td>
							</tr>
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">票据类型:</td>
								<td><input type="number" name="bill_type" id="bill_type" value="${pd.bill_type}" maxlength="32" placeholder="这里输入票据类型" title="票据类型" style="width:98%;"/></td>
							</tr>
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">业务类型:</td>
								<td><input type="number" name="business_type" id="business_type" value="${pd.business_type}" maxlength="32" placeholder="这里输入业务类型" title="业务类型" style="width:98%;"/></td>
							</tr>
							
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">采购组织:</td>
								<td><input type="text" name="purchase_org" id="purchase_org" value="${pd.purchase_org}" maxlength="50" placeholder="这里输入采购组织" title="采购组织" style="width:98%;"/></td>
							</tr>
							<tr>
							
							<td style="width:75px;text-align: right;padding-top: 13px;">采购组:</td>
							
							<td>
							
							<select class="chosen-select form-control" name="purchase_group_id" id="purchase_group_id" data-placeholder="选择采购组" style="vertical-align:top;width:98%;">
								 	
								 	<c:forEach items="${gpList}" var="gp" varStatus="vs">
								 			<option value="${gp.purchase_group_id}" ${gp.purchase_group_id==pd.purchase_group_id?"selected":""}>${gp.purchase_group_name}</option>
								 	</c:forEach>
									
								
									</select>
							</td>
							</tr>
						<tr style='display:none'>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="text"  id="status" value="${TextConfig.getBusinessStatus(pd.status)}" readonly="readonly" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/>
								<input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;display:none"/></td>
							</tr>
						<%-- 	<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建人:</td>
								<td><input type="text" name="createby" id="createby" value="${pd.createby}" maxlength="50" placeholder="这里输入创建人" title="创建人" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建日期:</td>
								<td><input class="span10 date-picker" name="create_time" id="create_time" value="${pd.create_time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="创建时间" title="创建时间" style="width:98%;"/></td>
								
							</tr>
						 	<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">更新人:</td>
								<td><input type="text" name="updateby" id="updateby" value="${pd.updateby}" maxlength="50" placeholder="这里输入更新人" title="更新人" style="width:98%;"/></td>
							</tr>
							<tr style='display:${msg.equals("save")?"none":"" }'>
								<td style="width:75px;text-align: right;padding-top: 13px;">更新时间:</td>
								<td><input type="text" name="update_time" id="update_time" value="${pd.update_time}" maxlength="19" placeholder="这里输入更新时间" title="更新时间" style="width:98%;"/></td>
							</tr>
						--%>	
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
		            msg:'请输入${typeText }编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_code").focus();
			return false;
			}
			if($("#supplier_sn").val()==""){
				$("#supplier_sn").tips({
					side:3,
		            msg:'请输入供应商',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#supplier_sn").focus();
			return false;
			}
			if($("#purchase_date").val()==""){
				$("#purchase_date").tips({
					side:3,
		            msg:'请输入采购日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_date").focus();
			return false;
			}
			if(!$("#purchase_group_id").val()){
				$("#purchase_group_id").tips({
					side:3,
		            msg:'请选择采购组',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_group_id").focus();
			return false;
			}
		<%--if($("#bill_type").val()==""){
				$("#bill_type").tips({
					side:3,
		            msg:'请输入票据类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bill_type").focus();
			return false;
			}
			if($("#business_type").val()==""){
				$("#business_type").tips({
					side:3,
		            msg:'请输入业务类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#business_type").focus();
			return false;
			}
			
		 	if($("#purchase_org").val()==""){
				$("#purchase_org").tips({
					side:3,
		            msg:'请输入采购组织',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_org").focus();
			return false;
			} */
			/*	if($("#status").val()==""){
				$("#status").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#status").focus();
			return false;
			}
				if($("#createby").val()==""){
				$("#createby").tips({
					side:3,
		            msg:'请输入创建人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#createby").focus();
			return false;
			}
			if($("#create_time").val()==""){
				$("#create_time").tips({
					side:3,
		            msg:'请输入创建日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#create_time").focus();
			return false;
			}
			 if($("#updateby").val()==""){
				$("#updateby").tips({
					side:3,
		            msg:'请输入更新人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#updateby").focus();
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
			} --%>
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		//选择商品
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
		
		//关闭选择商品页面
		function closeselectSupplier(supplier_sn,supplier_name){
			$("#supplier_sn").val(supplier_sn);
			$("#supplier_name").val(supplier_name);
			selectSupplierW.close();
			
			
			//selectSupplier = null;
		}
		</script>
</body>
</html>