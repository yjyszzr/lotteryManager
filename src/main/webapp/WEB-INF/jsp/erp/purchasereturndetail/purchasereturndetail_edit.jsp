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
					
					<form action="purchasereturndetail/${msg }.do?status=${pd.status}" name="Form" id="Form" method="post">
						<input type="hidden" name="return_detail_id" id="return_detail_id" value="${pd.return_detail_id}"/>
						<input type="hidden" name="return_quantity" id="return_quantity" value="${pd.return_quantity}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">发货数量:</td>
								<td><input type="number" name="send_quantity" id="send_quantity" value="${pd.send_quantity}" maxlength="11" placeholder="这里输入发货数量" title="发货数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退货说明:</td>
								<td><input type="text" name="remark" id="remark" value="${pd.remark}" maxlength="500" placeholder="这里输入退货说明" title="退货说明" style="width:98%;"/></td>
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
			if($("#send_quantity").val()==""){
				$("#send_quantity").tips({
					side:3,
		            msg:'请输入发货数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#send_quantity").focus();
			return false;
			}
			if(parseInt($("#send_quantity").val()) < 0){
				$("#send_quantity").tips({
					side:3,
		            msg:'发货数量不能小于0！',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#send_quantity").focus();
			return false;
			}
			if(parseInt($("#return_quantity").val()) < parseInt($("#send_quantity").val())){
				$("#send_quantity").tips({
					side:3,
		            msg:'发货数量不能大于退货数量！',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#send_quantity").focus();
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