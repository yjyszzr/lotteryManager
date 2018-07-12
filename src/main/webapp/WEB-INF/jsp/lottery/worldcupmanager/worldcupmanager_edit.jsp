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
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
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
					
					<form action="worldcupmanager/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<input type="hidden" name="status" id="status" value="1"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">奖项:</td>
								<td>${pd.prize}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">金额:</td>
								<td>${pd.quota}元</td>
								<input type="hidden"  id="quota" value="${pd.quota }"/> 
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">人数:</td>
								<td><input type="text" name="people_num"  autocomplete="off"  id="people_num" value="${pd.people_num }"  oninput="calculationAverage(event)" onporpertychange="calculationAverage(event)" maxlength="32" placeholder="这里输入人数" title="人数" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">平均奖金:</td>
								<td><input type="number" name = "average"  id="average" value="${pd.average }"  readOnly style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">开奖</a>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#people_num").val()==""){
				$("#people_num").tips({
					side:3,
		            msg:'请输入人数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#people_num").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		function calculationAverage(e){
			var peopleNum = 	$("#people_num").val().replace(/[^\d]/g,'');
			var quota = 	$("#quota").val();
			$("#people_num").val(peopleNum);
				if(peopleNum == 0 ){
					$("#average").val(0);
				}else{
					var average = quota/peopleNum;
					$("#average").val(average.toFixed(2));
				}
			}
		</script>
</body>
</html>