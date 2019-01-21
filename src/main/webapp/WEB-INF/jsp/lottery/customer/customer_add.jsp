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
					
					<form action="customer/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="_id" id="_id" value="${pd._id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">*手机号:</td>
								<td><input type="text" name="mobile" id="mobile" value="${pd.mobile}" maxlength="11" placeholder="这里输入手机号" title="这里输入手机号" style="width:154px;" onkeyup="value=value.replace(/[^\d]/g,'')" /></td>
<%-- 								<input type="text" placeholder="手机号" style="width:154px;" class="nav-search-input" id="mobile" autocomplete="off" name="mobile" value="${pd.mobile}"   onkeyup="value=value.replace(/[^\d]/g,'')"  /> --%>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户名:</td>
								<td><input type="text" name="user_name" id="user_name" value="${pd.user_name}" maxlength="20" placeholder="这里输入用户名" title="这里输入用户名" style="width:154px;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户来源:</td>
								<td>
									<select  name="user_source" id="user_source" data-placeholder="请选择" value="${pd.user_source}" style="width:154px;border-radius:5px !important"  >
										<option value="1" <c:if test="${pd.user_state == 1}">selected</c:if>>公司资源</option>
										<option value="2" <c:if test="${pd.user_state == 2}">selected</c:if>>微信群</option>
										<option value="3" <c:if test="${pd.user_state == 3}">selected</c:if>>QQ群</option>
										<option value="4" <c:if test="${pd.user_state == 4}">selected</c:if>>好友推荐</option>
										<option value="5" <c:if test="${pd.user_state == 5}">selected</c:if>>电话访问</option>
										<option value="6" <c:if test="${pd.user_state == 6}">selected</c:if>>其它</option>
									</select>
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
			if($("#mobile").val()==""){
				$("#mobile").tips({
					side:3,
		            msg:'请输入手机号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mobile").focus();
				return false;
			}
			
			var mobile = $("#mobile").val().replace(/(^\s*)|(\s*$)/g, "");
			if (mobile != "") {
				//alert("mobile=" + mobile);
				var _var = $.ajax({
					type: "POST",
					url: '<%=basePath%>customer/checkMobile.do?t='+new Date().getTime() 
							+ "&mobile=" + mobile,
		    		data: {},
					dataType:'json',
					cache: false,
					success: function(data){
						//alert("data=" + data);
						if (data.flag == false) {
							$("#mobile").tips({
								side:3,
					            msg: data.msg,
					            bg:'#AE81FF',
					            time:2
					        });
							$("#mobile").focus();
							return false;		
						} else {
							$("#Form").submit();
							$("#zhongxin").hide();
							$("#zhongxin2").show();
							
						}
					}
				}); 
			}
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>