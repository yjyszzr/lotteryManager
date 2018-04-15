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
					
					<form action="outboundnoticedetail/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="outbound_notice_detail_id" id="outbound_notice_detail_id" value="${pd.outbound_notice_detail_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">发货通知编码:</td>
								<td><input type="text" name="outbound_notice_code" id="outbound_notice_code" value="${pd.outbound_notice_code}" maxlength="50" placeholder="这里输入发货通知编码" title="发货通知编码" style="width:98%;" disabled="disabled"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">业务单据编码:</td>
								<td><input type="text" name="purchase_code" id="purchase_code" value="${pd.purchase_code}" maxlength="50" placeholder="这里输入业务单据编码" title="业务单据编码" style="width:98%;" disabled="disabled"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物料名称:</td>
								<td><input type="text" name="sku_name" id="sku_name" value="${pd.sku_name}" maxlength="500" placeholder="这里输入物料名称" title="物料名称" style="width:98%;" disabled="disabled"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预计发货数量:</td>
								<td><input type="number" name="pre_send_quantity" id="pre_send_quantity" value="${pd.pre_send_quantity}" maxlength="32" placeholder="这里输入预计发货数量" title="预计发货数量" style="width:98%;" disabled="disabled"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">出库数量:</td>
								<td><input type="number" name="send_quantity" id="send_quantity" value="${pd.send_quantity}" maxlength="32" placeholder="这里输入出库数量" title="出库数量" style="width:98%;"/></td>
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
		            msg:'请输入发货通知编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#outbound_notice_code").focus();
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
			if($("#sku_name").val()==""){
				$("#sku_name").tips({
					side:3,
		            msg:'请输入物料名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sku_name").focus();
			return false;
			}
			if($("#pre_send_quantity").val()==""){
				$("#pre_send_quantity").tips({
					side:3,
		            msg:'请输入预计发货数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pre_send_quantity").focus();
			return false;
			}
			if($("#send_quantity").val()==""){
				$("#send_quantity").tips({
					side:3,
		            msg:'请输入出库数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#send_quantity").focus();
			return false;
			}
			
		
			if((/^(\+|-)?\d+$/.test($("#send_quantity").val())) &&$("#send_quantity").val()> 0)
			{  
		         
		    }else{  
		        alert(" 出库数量中请输入正整数！");  
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