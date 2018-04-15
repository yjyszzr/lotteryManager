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
					
					<form action="outboundnotice/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="outbound_notice_id" id="outbound_notice_id" value="${pd.outbound_notice_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">发货单编码:</td>
								<td><input type="text" name="outbound_notice_code" id="outbound_notice_code" value="${pd.outbound_notice_code}" maxlength="50" placeholder="这里输入发货单编码" title="发货单编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库编号:</td>
								<td><input type="text" name="store_sn" id="store_sn" value="${pd.store_sn}" maxlength="20" placeholder="这里输入仓库编号" title="仓库编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
								<td><input type="text" name="store_name" id="store_name" value="${pd.store_name}" maxlength="50" placeholder="这里输入仓库名称" title="仓库名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单据类型:</td>
								<td><input type="number" name="bill_type" id="bill_type" value="${pd.bill_type}" maxlength="32" placeholder="这里输入单据类型" title="单据类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">业务单据编码:</td>
								<td><input type="text" name="purchase_code" id="purchase_code" value="${pd.purchase_code}" maxlength="50" placeholder="这里输入业务单据编码" title="业务单据编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预计发货日期:</td>
								<td><input type="text" name="pre_send_time" id="pre_send_time" value="${pd.pre_send_time}" maxlength="19" placeholder="这里输入预计发货日期" title="预计发货日期" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">实际发货日期:</td>
								<td><input type="text" name="send_time" id="send_time" value="${pd.send_time}" maxlength="19" placeholder="这里输入实际发货日期" title="实际发货日期" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建人:</td>
								<td><input type="text" name="createby" id="createby" value="${pd.createby}" maxlength="50" placeholder="这里输入创建人" title="创建人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input type="text" name="create_time" id="create_time" value="${pd.create_time}" maxlength="19" placeholder="这里输入创建时间" title="创建时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">更新人:</td>
								<td><input type="text" name="updateby" id="updateby" value="${pd.updateby}" maxlength="50" placeholder="这里输入更新人" title="更新人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">更新时间:</td>
								<td><input type="text" name="update_time" id="update_time" value="${pd.update_time}" maxlength="19" placeholder="这里输入更新时间" title="更新时间" style="width:98%;"/></td>
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
			if($("#outbound_notice_code").val()==""){
				$("#outbound_notice_code").tips({
					side:3,
		            msg:'请输入发货单编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#outbound_notice_code").focus();
			return false;
			}
			if($("#store_sn").val()==""){
				$("#store_sn").tips({
					side:3,
		            msg:'请输入仓库编号',
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
			if($("#bill_type").val()==""){
				$("#bill_type").tips({
					side:3,
		            msg:'请输入单据类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bill_type").focus();
			return false;
			}
			if($("#purchase_code").val()==""){
				$("#purchase_code").tips({
					side:3,
		            msg:'请输入业务单据编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_code").focus();
			return false;
			}
			if($("#pre_send_time").val()==""){
				$("#pre_send_time").tips({
					side:3,
		            msg:'请输入预计发货日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pre_send_time").focus();
			return false;
			}
			if($("#send_time").val()==""){
				$("#send_time").tips({
					side:3,
		            msg:'请输入实际发货日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#send_time").focus();
			return false;
			}
			if($("#status").val()==""){
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
		            msg:'请输入创建时间',
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
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>