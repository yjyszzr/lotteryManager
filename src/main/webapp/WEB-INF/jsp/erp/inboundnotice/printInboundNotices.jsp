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
<title>打印</title>
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
</style>

</head>
<body >
	<div id="zhongxin" onclick="window.print()">
			<c:choose>
				<c:when test="${not empty list}">
					<c:forEach items="${list}" var="var" varStatus="vs">	
					 <div align="center" style="page-break-after: always;position: relative;">
						 <table width="850">
						 <tr>
					 	  	<td width="33%" ><img width="80px" height="80px" src="static/pgt_logo.png" alt="Logo" /></td>
					 	  	<td width="33%" style="text-align:center;"><b style="font-size: 40px;" >入库单</b></td>
					 	  	<td width="33%" style="text-align:right;vertical-align:bottom;"><img src="data:image/png;base64,${var.barcode}" alt="Logo" /></td>
						 </tr>
						 </table>
					 	
					 	
					 	<table width="850">
					 	  <tr>
					 	  	<td width="33%" style="padding-bottom: 10px"><span>入库单号：${var.inbound_code}</span></td>
					 	  	<td width="33%" style="padding-bottom: 10px"><span>仓库编码：${var.store_sn}</span></td>
					 	  	<td width="33%" style="padding-bottom: 10px"><span>仓库名称：${var.store_name}</span></td>
					 	  </tr>
					 	  <tr>
					 	  	<td style="padding-bottom: 10px">
						 	  <span>入库类型：${var.inbound_type}
							  </span>
							</td>
					 	  	<td style="padding-bottom: 10px">
					 	  	  <span>业务单号：${var.purchase_code}</span>
					 	  	</td>
					 	  	<td style="padding-bottom: 10px">
					 	  	 <span>入库通知单：${var.inbound_notice_code}</span>
					 	  	</td>
						 </tr>
					 	</table>
					 	<table  width="850" border="1" align="center" cellspacing="1" cellpadding="1" >
					 		<tr>
				 				<th class="center" width="20%">物料编码</th>
								<th class="center" width="20%">物料条码</th>
								<th class="center" width="30%">物料名称</th>
								<th class="center" width="10%">数量</th>
								<th class="center" width="10%">单位</th>
								<th class="center" width="10%">规格</th>
					 		</tr>
					 		<c:forEach items="${var.details}" var="detail" varStatus="ds">
					 		<tr>
					 			<td height="50px">${detail.sku_encode }</td>
					 			<td height="50px">${detail.sku_encode }</td>
					 			<td align="center" height="50px">${detail.sku_name }</td>
					 			<td align="center" height="50px">${detail.quantity }</td>
					 			<td align="center" height="50px"></td>
					 			<td align="center" height="50px">${detail.spec}</td>
					 		</tr>
					 		</c:forEach>
					 	</table>
					 	<table width="850" style="margin-top: -15px">
					 		<tr>
					 		<td width="50%"><p align="right"><span>入库员：</span>${var.name }</td>
					 		<td width="50%"><p align="right"><span>入库时间：<fmt:formatDate value="${var.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
					 		</tr>
					 	</table>
					 </div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="100" class="center">没有相关数据...</td>
					</tr>
				</c:otherwise>
			</c:choose>
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