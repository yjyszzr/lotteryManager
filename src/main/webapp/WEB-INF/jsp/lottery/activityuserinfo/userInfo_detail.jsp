<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.fh.util.DateUtil" %>
<%@page import="com.fh.util.IDCardUtil" %>

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
<body  >
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					<form action="usermanagercontroller/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="user_id" id="user_id" value="${pd.user_id}"/>
						<div id="zhongxin" style="padding-top: 3px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户ID:</td>
								<td style="padding: 13px;"> <lable >${entity.user_id}</lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户昵称:</td>
								<td style="padding: 13px;"> <lable >${entity.nickname} </lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">性别:</td>
								<td style="padding: 13px;"> 
									<lable >
										<c:if test="${!entity.sex}">男</c:if>
										<c:if test="${entity.sex}">女</c:if>
									</lable>
								</td>  
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号:</td>
								<td style="padding: 13px;"> <lable >${entity.mobile} </lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">真实姓名:</td>
								<td style="padding: 13px;"> <lable >${rentity.real_name} </lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">身份证号:</td>
								<td style="padding: 13px;"> <lable >${rentity.id_code} </lable></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">年龄:</td>
								<td style="padding: 13px;"> <lable >${IDCardUtil.getAgeByIdCard(rentity.id_code)}	<c:if test="${!empty rentity.id_code}"> 岁</c:if></lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址或IP:</td>
<%-- 								<td style="padding: 13px;"> <lable >${rentity.address_info}</lable></td> --%>
								<td style="padding: 13px;"> <lable >${entity.reg_ip}</lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td style="padding: 13px;"> <lable >
									<c:choose>
										<c:when test="${entity.user_status == 0 }">正常</c:when>
										<c:when test="${entity.user_status == 1 }"><lable style = "color:red">锁定</c:when>
										<c:when test="${entity.user_status == 2 }"><lable style = "color:red">冻结</c:when>
									</c:choose>
								 </lable></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">来源:</td>
								<td style="padding: 13px;"> <lable >${entity.reg_from}</lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;"></td>
								<td style="padding: 13px;"> <lable ></lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;"></td>
								<td style="padding: 13px;"> <lable ></lable></td>
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
	<script type="text/javascript">
	$(top.hangge());//关闭加载状态
	</script>

</body>
</html>