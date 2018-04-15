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
					
					<form action="directsalescustomer/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="direct_sales_code" id="direct_sales_code" value="${pd.direct_sales_code}"/>
						<input type="hidden" name="status" id="status" value="${pd.status}"/>
						<input type="hidden" name="direct_sales_customer_id" id="direct_sales_customer_id" value="${pd.direct_sales_customer_id}"/>
						<input type="hidden" name="customer_id" id="customer_id" value="${pd.customer_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">内部直销订单编码:</td>
								<td><input type="text" name="direct_sales_code" id="direct_sales_code" value="${pd.direct_sales_code}" readonly="readonly" maxlength="50" placeholder="这里输入内部直销订单编码" title="内部直销订单编码" style="width:98%;"/></td>
							</tr>
							<%--<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">客户ID:</td>
								<td><input type="number" name="customer_id" id="customer_id" value="${pd.customer_id}" readonly="readonly" maxlength="32" placeholder="这里输入客户ID" title="客户ID" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">客户名称:</td>
								<td><input type="text" name="customer_name" id="customer_name" value="${pd.customer_name}" readonly="readonly" maxlength="50" placeholder="这里输入客户名称" title="客户名称" style="width:70%;"/>
								<a class="btn btn-mini btn-primary" onclick="selectFromStormSn();">选择客户</a>
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
			if($("#direct_sales_code").val()==""){
				$("#direct_sales_code").tips({
					side:3,
		            msg:'请输入内部直销订单编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#direct_sales_code").focus();
			return false;
			} 
			if($("#customer_id").val()==""){
				$("#customer_id").tips({
					side:3,
		            msg:'请输入客户ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#customer_id").focus();
			return false;
			}
			if($("#customer_name").val()==""){
				$("#customer_name").tips({
					side:3,
		            msg:'请输入客户名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#customer_name").focus();
			return false;
			}
			if($("#amount").val()==""){
				$("#amount").tips({
					side:3,
		            msg:'请输入消费金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#amount").focus();
			return false;
			}
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		var selectFromStormSnDiag = null;
		
		//选择客户
		function selectFromStormSn(){
			 top.jzts();
			 selectFromStormSnDiag = new top.Dialog();
			 selectFromStormSnDiag.Drag=true;
			 selectFromStormSnDiag.Title ="选择客户";
			 selectFromStormSnDiag.URL = '<%=basePath%>customer/listSelect.do';
			 selectFromStormSnDiag.Width = 1250;
			 selectFromStormSnDiag.Height = 755;
			 selectFromStormSnDiag.Modal = true;				//有无遮罩窗口
			 selectFromStormSnDiag. ShowMaxButton = true;	//最大化按钮
			 selectFromStormSnDiag.ShowMinButton = true;		//最小化按钮
			 selectFromStormSnDiag.CancelEvent = function(){ //关闭事件
				 selectFromStormSnDiag.close();
			 };
			 selectFromStormSnDiag.show();
		}
		//关闭选择调出仓库页面
		function closeFromStormSnDiag(sn,name){
			$("#customer_id").val(sn);
			$("#customer_name").val(name);
			selectFromStormSnDiag.close();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>