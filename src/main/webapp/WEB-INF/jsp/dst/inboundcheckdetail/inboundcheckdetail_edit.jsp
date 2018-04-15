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
								<td><input type="text" name="inbound_notice_code" readonly="readonly" id="inbound_notice_code" value="${pd.inbound_notice_code}" maxlength="50" placeholder="这里输入入库通知单CODE" title="入库通知单CODE" style="width:98%;"/></td>
							</tr>
							<tr style="display:none" >
								<td style="width:75px;text-align: right;padding-top: 13px;">入库通知单详情ID:</td>
								<td><input type="number" name="notice_detail_id" id="notice_detail_id" value="${pd.notice_detail_id}" maxlength="32" placeholder="这里输入入库通知单详情ID" title="入库通知单详情ID" style="width:98%;"/></td>
							</tr>
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">业务编码:</td>
								<td><input type="text" name="purchase_code" id="purchase_code" value="${pd.purchase_code}" maxlength="50" placeholder="这里输入业务编码" title="业务编码" style="width:98%;"/></td>
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
								<td><input type="number" name="batch_code" id="batch_code" readonly="readonly"  value="${pd.batch_code}" maxlength="32" placeholder="这里输入入库批次" title="入库批次" style="width:98%;"/></td>
							</tr>
							 <tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">sku_id:</td>
								<td><input type="number" name="sku_id" id="sku_id" value="${pd.sku_id}" maxlength="32" placeholder="这里输入sku_id" title="sku_id" style="width:98%;"/></td>
							</tr> 
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>
								<td><input type="text" name="sku_name" id="sku_name" readonly="readonly"  readonly="readonly"  value="${pd.sku_name}" maxlength="100" placeholder="这里输入商品名称" title="商品名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品编码:</td>
								<td><input type="text" name="sku_encode" id="sku_encode" readonly="readonly"  value="${pd.sku_encode}" maxlength="60" placeholder="这里输入商品编码" title="商品编码" style="width:98%;"/></td>
							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">质检总数:</td>
								<td><input type="number" name="total_quantity" id="total_quantity" value="${pd.total_quantity}" maxlength="32" placeholder="这里输入质检总数" title="质检总数" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">良品数:</td>
								<td><input type="number" name="good_quantity" min="0" id="good_quantity" value="${pd.good_quantity}" maxlength="32" placeholder="这里输入良品数" title="良品数" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">不良品数:</td>
								<td><input type="number" name="bad_quantity" min="0" id="bad_quantity" value="${pd.bad_quantity}" maxlength="32" placeholder="这里输入不良品数" title="不良品数" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">报损数:</td>
								<td><input type="number" name="scrap_quantity" min="0" id="scrap_quantity" value="${pd.scrap_quantity}" maxlength="32" placeholder="这里输入报损数" title="报损数" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">处理类型:</td>
<%-- 								<td><input type="number" name="bad_deal_type" id="bad_deal_type" value="${pd.bad_deal_type}" maxlength="32" placeholder="这里输入处理类型" title="处理类型" style="width:98%;"/></td>
 --%>							<td>	<select class="chosen-select form-control" name="bad_deal_type" id="bad_deal_type" data-placeholder="不良品处理类型" style="vertical-align:top;width: 120px;">
								 	
									<option value="0" ${0==pd.bad_deal_type?"selected":""}>退回供应商</option>
									<option value="1" ${1==pd.bad_deal_type?"selected":""}>入不良品库</option>
									</select>
									</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">注释:</td>
								<td><input type="text" name="note" id=""note""   value="${pd.note}" maxlength="50" placeholder="这里输入注释" title="注释" style="width:98%;"/></td>
							</tr>
									
						</select>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格:</td>
								<td><input type="text" name="spec" id="spec" value="${pd.spec}" readonly="readonly"  maxlength="100" placeholder="这里输入规格" title="规格" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位名称:</td>
								<td><input type="text" name="unit_name" id="unit_name" readonly="readonly"  value="${pd.unit_name}" maxlength="100" placeholder="这里输入单位名称" title="单位名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">质检文件:</td>
								<td id="fileTd">
								<input type="file" name="file" id="file" value="${pd.url}" maxlength="500" placeholder="这里输入质检文件" title="质检文件" style="width:98%;"/>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									<a class="btn btn-mini btn-danger" onclick="addfile();">+</a>
									<a class="btn btn-mini btn-danger" onclick="removefile();">-</a>
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