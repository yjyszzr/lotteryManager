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
					
					<form action="inboundcheckdetail/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="pgt_inbound_check_detail_id" id="pgt_inbound_check_detail_id" value="${pd.pgt_inbound_check_detail_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
		<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">入库通知单:</td>
								<td>${pd.inbound_notice_code}</td>
							</tr>
							<tr style="display:none" >
								<td style="width:75px;text-align: right;padding-top: 13px;">入库通知单详情ID:</td>
								<td><input type="number" name="notice_detail_id" id="notice_detail_id" value="${pd.notice_detail_id}" maxlength="32" placeholder="这里输入入库通知单详情ID" title="入库通知单详情ID" style="width:98%;"/></td>
							</tr>
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">业务编码:</td>
								<td>${pd.purchase_code} </td>
							</tr>
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">业务详情ID:</td>
								<td><input type="number" name="purchase_detail_id" id="purchase_detail_id" value="${pd.purchase_detail_id}" maxlength="32" placeholder="这里输入业务详情ID" title="业务详情ID" style="width:98%;"/></td>
							</tr> 
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">入库批次ID:</td>
								<td><input type="number" name="inbound_notice_stock_batch_id" id="inbound_notice_stock_batch_id" value="${pd.inbound_notice_stock_batch_id}" maxlength="32" placeholder="这里输入入库批次ID" title="入库批次ID" style="width:98%;"/></td>
							</tr> 
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">入库批次:</td>
								<td>${pd.batch_code}</td>
							</tr>
							 <tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">sku_id:</td>
								<td><input type="number" name="sku_id" id="sku_id" value="${pd.sku_id}" maxlength="32" placeholder="这里输入sku_id" title="sku_id" style="width:98%;"/></td>
							</tr> 
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>
								<td>${pd.sku_name}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品编码:</td>
								<td>${pd.sku_encode}</td>
							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">质检总数:</td>
								<td><input type="number" name="total_quantity" id="total_quantity" value="${pd.total_quantity}" maxlength="32" placeholder="这里输入质检总数" title="质检总数" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">良品数:</td>
								<td>${pd.good_quantity}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">不良品数:</td>
								<td>${pd.bad_quantity} </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">报损数:</td>
								<td> ${pd.scrap_quantity} </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">处理类型:</td>
<%-- 								<td><input type="number" name="bad_deal_type" id="bad_deal_type" value="${pd.bad_deal_type}" maxlength="32" placeholder="这里输入处理类型" title="处理类型" style="width:98%;"/></td>
 --%>							<td>	
 								<c:if test="${0==pd.bad_deal_type}">
 								退回供应商
 								</c:if>
 								<c:if test="${1==pd.bad_deal_type}">
 								入不良品库
 								</c:if>
 
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">注释:</td>
								<td> ${pd.note} </td>
							</tr>
									
						</select>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格:</td>
								<td> ${pd.spec} </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位名称:</td>
								<td> ${pd.unit_name} </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">质检文件:</td>
								<td id="fileTd">
								<c:forEach items="${fileList}" var="file" varStatus="vs">
									<a target="_blank" href="inboundcheckdetail/downloadCheckFile.do?quality_check_file_id=${file.quality_check_file_id}">${file.url }</a>
								</c:forEach>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
							
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>

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
			/* if($("#inbound_notice_code").val()==""){
				$("#inbound_notice_code").tips({
					side:3,
		            msg:'请输入入库通知单CODE',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#inbound_notice_code").focus();
			return false;
			}
			if($("#notice_detail_id").val()==""){
				$("#notice_detail_id").tips({
					side:3,
		            msg:'请输入入库通知单详情ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#notice_detail_id").focus();
			return false;
			}
			if($("#purchase_code").val()==""){
				$("#purchase_code").tips({
					side:3,
		            msg:'请输入业务编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_code").focus();
			return false;
			}
			if($("#purchase_detail_id").val()==""){
				$("#purchase_detail_id").tips({
					side:3,
		            msg:'请输入业务详情ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_detail_id").focus();
			return false;
			}
			if($("#inbound_notice_stock_batch_id").val()==""){
				$("#inbound_notice_stock_batch_id").tips({
					side:3,
		            msg:'请输入入库批次ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#inbound_notice_stock_batch_id").focus();
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
			if($("#sku_encode").val()==""){
				$("#sku_encode").tips({
					side:3,
		            msg:'请输入商品编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_encode").focus();
			return false;
			}
			if($("#total_quantity").val()==""){
				$("#total_quantity").tips({
					side:3,
		            msg:'请输入质检总数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#total_quantity").focus();
			return false;
			} */
			if($("#good_quantity").val()==""){
				$("#good_quantity").tips({
					side:3,
		            msg:'请输入良品数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#good_quantity").focus();
			return false;
			}
			else if( !(/^\d+$/.test($("#good_quantity").val()))){
				$("#good_quantity").tips({
					side:3,
		            msg:'请输入正确的数量值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#good_quantity").focus();
				return false;
			}
			
			if($("#bad_quantity").val()==""){
				$("#bad_quantity").tips({
					side:3,
		            msg:'请输入不良品数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bad_quantity").focus();
			return false;
			}else if( !(/^\d+$/.test($("#bad_quantity").val()))){
				$("#bad_quantity").tips({
					side:3,
		            msg:'请输入正确的数量值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bad_quantity").focus();
				return false;
			}
			if($("#scrap_quantity").val()==""){
				$("#scrap_quantity").tips({
					side:3,
		            msg:'请输入报损数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#scrap_quantity").focus();
			return false;
			}else if( !(/^\d+$/.test($("#scrap_quantity").val()))){
				$("#scrap_quantity").tips({
					side:3,
		            msg:'请输入正确的数量值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#scrap_quantity").focus();
				return false;
			}
			if($("#bad_deal_type").val()==""){
				$("#bad_deal_type").tips({
					side:3,
		            msg:'请输入处理类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bad_deal_type").focus();
			return false;
			}
			/* if($("#spec").val()==""){
				$("#spec").tips({
					side:3,
		            msg:'请输入规格',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#spec").focus();
			return false;
			}
			if($("#unit_name").val()==""){
				$("#unit_name").tips({
					side:3,
		            msg:'请输入单位名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#unit_name").focus();
			return false;
			} */
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		function removefile(){
			var len = $('#fileTd input').length;
			if(len > 1){
				$('#fileTd input:last').remove();
			}
		}

		function addfile(){
			var len = $('#fileTd input').length;
			$('#fileTd').append('<input type="file" name="file'+len+'" style="width:98%;"/>');
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>