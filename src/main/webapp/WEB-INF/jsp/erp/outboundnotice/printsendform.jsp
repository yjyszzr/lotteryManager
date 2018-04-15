<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<title>面单</title>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
table {
	border-collapse: collapse; /* 边框合并属性  */
}
</style>

</head>
<body>
	<div id="zhongxin" onclick="window.print()">
		<table style="width: 850px;">
			<c:choose>
				<c:when test="${not empty list}">
					<c:forEach items="${list}" var="var" varStatus="vs">
						<tr>
							<td width="33%"><img width="80px" height="80px"
								src="static/pgt_logo.png" alt="Logo" /></td>
							<td width="30%" style="text-align: center;"><b
								style="font-size: 40px;"> 商品送货单</b></td>
							<td width="37%"
								style="text-align: right; vertical-align: bottom;"></td>
						</tr>
						<tr>
							<td width="33%" style="padding-bottom: 10px; padding-top: 20px">
								<span style="font-size: 19px">电话:${var.tel}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</td>
							<td width="30%" style="padding-bottom: 10px; padding-top: 20px">
								<span style="font-size: 19px">收货人:${var.consignee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</td>
							<td width="37%" style="padding-bottom: 10px; padding-top: 20px">
								<span style="font-size: 19px">单号:${var.order_sn}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding-bottom: 10px; padding-top: 20px">
								<span style="font-size: 19px">地址:
									${var.address}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
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
	</div>
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