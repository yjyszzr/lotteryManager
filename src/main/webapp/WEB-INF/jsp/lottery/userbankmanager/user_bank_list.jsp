<%@page import="com.fh.util.DateUtil"%>
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
	<style type="text/css">
	.td_bg{background:url(<%=basePath%>static/images/mr.png) no-repeat;}
	</style>
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
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
						<div id="zhongxin" style="padding-top: 13px;">
							
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">真实姓名</th>
									<th class="center">银行卡号</th>
									<th class="center">银行卡照片</th>
									<th class="center">银行名称</th>
									<th class="center">添加时间</th>
									<th class="center">最近修改</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty pageDataList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${pageDataList}" var="var" varStatus="vs">
										<tr>
											<c:if test="${var.status }">
												<td  class = "center td_bg">${var.real_name}</td>
											</c:if>
											<c:if test="${!var.status  }">
												<td  class = "center">${var.real_name}</td>
											</c:if>
											<td class='center'>${var.card_no}</td>
											<td class='center'><img src="${var.bank_logo}" style="width:90px;hight:30px"></td>
											<td class='center'>${var.bank_name}</td>
											<td class='center'>${DateUtil.toSDFTime(var.add_time*1000)}</td>
											<td class='center'>${DateUtil.toSDFTime(var.last_time*1000)}</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</div>
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
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		</script>
</body>
</html>