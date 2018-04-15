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
					
					<form action="inboundnoticestockbatch/${msg }.do" name="Form" id="Form" method="post">
						
						<div id="zhongxin" style="padding-top: 13px;">
						
						
						<input type="hidden" name="batchcode" id="batchcode" value="${pd.batchcode}"/>
				
						<table id="table_report" class="table table-striped table-bordered table-hover">
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开始:</td>
								<td><input type="number" name="startnumber" id="startnumber" maxlength="10" placeholder="这里输入数量" title="数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">结束:</td>
								<td><input type="number" name="endnumber" id="endnumber" maxlength="10" placeholder="这里输入数量" title="数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="printcode('${pd.batchcode}');">打印</a>
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
		function printcode(batchCode){
			if($("#startnumber").val()==""){
				$("#startnumber").tips({
					side:3,
		            msg:'请输入开始',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#startnumber").focus();
			return false;
			}
			if($("#endnumber").val()==""){
				$("#endnumber").tips({
					side:3,
		            msg:'请输入结束',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#endnumber").focus();
			return false;
			}
			
			var quantity = $("#quantity").val();
			var startnumber = $("#startnumber").val();
			var endnumber = $("#endnumber").val();

			if(parseInt(startnumber) < 1 || parseInt(startnumber) > parseInt(quantity)){
				$("#startnumber").tips({
					side:3,
		            msg:'请输入范围1-'+parseInt(quantity),
		            bg:'#AE81FF',
		            time:2
		        });
				$("#startnumber").focus();
				return false;
			}
			
			if(parseInt(startnumber) > parseInt(endnumber) || parseInt(endnumber) > parseInt(quantity)){
				$("#endnumber").tips({
					side:3,
		            msg:'请输入范围' + parseInt(startnumber) + '-' + parseInt(quantity),
		            bg:'#AE81FF',
		            time:2
		        });
				$("#endnumber").focus();
				return false;
			}
			
			
var url = "<%=basePath%>stockbatch/printbatchPage.do?batch_code="+batchCode+"&tm="+new Date().getTime()+"&beginPos=" + startnumber+"&endPos=" + endnumber;
window.open(url, "", 'left=250,top=150,width=750,height=250,toolbar=no,menubar=no,status=no,scrollbars=yes,resizable=yes');
    top.Dialog.close();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>