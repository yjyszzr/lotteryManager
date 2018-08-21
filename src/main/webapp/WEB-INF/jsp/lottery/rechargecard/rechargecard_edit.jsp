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
					
					<form action="rechargecard/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="recharge_card_id_id" id="recharge_card_id_id" value="${pd.recharge_card_id_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr style="display: none;">
								<td style="width:75px;text-align: right;padding-top: 13px;">充值卡id:</td>
								<td><input type="number" name="recharge_card_id" id="recharge_card_id" value="${pd.recharge_card_id}" maxlength="32" placeholder="这里输入备注1" title="备注1" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">充值卡名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="50" placeholder="充值卡名称不能重复,给前端显示,例如:1000元" title="充值卡名称" style="width:98%;"  onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" /></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">充值卡描述:</td>
								<td><input type="text" name="description" id="description" value="${pd.description}" maxlength="200" placeholder="这里输入充值卡描述" title="充值卡描述" style="width:98%;"  onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" /></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">实际价值:</td>
								<td><input type="number" name="real_value" id="real_value" value="${pd.real_value}" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))" min=1 step=1  max=100   style="width:204px;border-radius:5px !important" /></td>
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
			if($("#name").val()==""){
				$("#name").tips({
					side:3,
		            msg:'请输入充值卡名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
			}
			if($("#description").val()==""){
				$("#description").tips({
					side:3,
		            msg:'请输入充值卡描述',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#description").focus();
			return false;
			}
			if($("#real_value").val()==""){
				$("#real_value").tips({
					side:3,
		            msg:'请输入实际价值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#real_value").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
	    /*
	     * 特殊字符在ASCII码中所表示的范围为32~48，57~65，90~97
	     * event.returnValue=false;设置键盘输入主false，则不能在文本框中输入内容
	     */
	    function checkComments(){
	        if (( event.keyCode > 32 && event.keyCode < 48) ||
	            ( event.keyCode > 57 && event.keyCode < 65) ||
	            ( event.keyCode > 90 && event.keyCode < 97)
	           ) {
	                event.returnValue = false;
	           }
	    }
		
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>