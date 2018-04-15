<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<title></title>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" /><!--  
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />-->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>

<script language="javascript" src="static/js/jquery.jqprint-0.3.js"></script>

<style type="text/css">
table {
	border-collapse: collapse; /* 边框合并属性  */
}

th {
	border: 0px solid #666666;
}

td {
	border: 0px solid #666666;
}
</style>

</head>
<body >

					<c:forEach items="${imglist}" var="var" varStatus="vs">					
						
							<div style="width:180px;height:115px;">
							<img  onclick="window.print()""
			                 src="<%=basePath%>uploadFiles/twoDimensionCode/${var.path}"
			               width="140px" height="110px">
							</td>
							</div>
					</c:forEach>
				
<%-- <button onClick="$(this).attr('display','none');window.print()">打印</button>--%>
<%-- 	<div id="zhongxin">
	
		<table width="" border="0" align="center" cellspacing="0"
			cellpadding="0" bgcolor="#221144" 
			>
			<c:choose>
				<c:when test="${not empty imglist}">
					<c:forEach items="${imglist}" var="var" varStatus="vs">					
						<tr>
							<td style="margin:0 0" align="center" bgcolor="#FFFFFF">
							<img  
			                 src="<%=basePath%>uploadFiles/twoDimensionCode/${var.path}"
			               width="240px" height="150px;">
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="100" class="center">没有相关数据...</td>
					</tr>
				</c:otherwise>
			</c:choose>


		</table>
		<table width="650" height="30" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td align="right"><button onClick="window.print()">打印</button>&nbsp;</td>
			</tr>
		</table>
		<hr />
		
	</div>

	<div id="zhongxin2" class="center" style="display: none">
		<br />
		<br />
		<br />
		<br />
		<br />
		<img src="static/images/jiazai.gif" /><br />
		<h4 class="lighter block green">提交中...</h4>
	</div>
--%>
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

	<script type="text/javascript">
		$(top.hangge());
	</script>
</body>
</html>