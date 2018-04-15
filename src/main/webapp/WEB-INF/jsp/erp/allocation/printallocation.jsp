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
<meta name="viewport" content="width=device-width, initial-scale=1.0" />  
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
					 	  	<td width="33%" style="text-align:center;"><b style="font-size: 40px;" >调拨单</b></td>
					 	  	<td width="33%" style="text-align:right;vertical-align:bottom;"><img src="data:image/png;base64,${var.barcode}" alt="Logo" /></td>
						 </tr>
						 </table>
					 	<table width="850">
					 	<tr>
					 		<td style="padding-bottom: 10px">调拨单号：${var.allocation_code}</td>
					 	</tr>
					 	<tr>
					 		<td width="33%" style="padding-bottom: 10px">
					 		<span>调出仓库名称： ${var.from_store_name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					 		</td>
					 		<td width="33%" style="padding-bottom: 10px">
					 		<span>预计发货时间：<fmt:formatDate value="${var.pre_send_time}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					 		</td>
					 		<td width="33%" style="padding-bottom: 10px">
					 		<span>实际发货时间：<fmt:formatDate value="${var.send_time}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					 		</td>
					 	</tr>
					 	
					 	<tr>
					 		<td width="33%" style="padding-bottom: 10px">
					 		<span>调入仓库名称： ${var.to_store_name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					 		</td>
					 		<td width="33%" style="padding-bottom: 10px">
					 		<span>预计到货时间：<fmt:formatDate value="${var.pre_arrival_time}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					 		</td>
					 		<td width="33%" style="padding-bottom: 10px">
					 		<span>实际到货时间：<fmt:formatDate value="${var.arrival_time}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					 		</td>
					 	</tr>
					 	</table>
					 	<table  width="850" border="1">
					 	<tr>
				 		   <th class="center" width="25%">物料编码</th>
						   <th class="center" width="35%">物料名称</th>
						   <th class="center" width="10%">调拨数量</th>
						   <th class="center" width="10%">实际数量</th>
						   <th class="center" width="10%">单位</th>
						   <th class="center" width="10%">规格</th>
					    </tr>
					 	<c:forEach items="${var.details}" var="detail" varStatus="ds">
					 	<tr>
					 	    <td height="50px">${detail.sku_encode }</td>
					 	    <td height="50px" align="center" >${detail.sku_name }</td>
					 	    <td align="center" height="50px">${detail.pre_quantity }</td>
					 		<td align="center" height="50px">${detail.quantity }</td>
					 		<td align="center" height="50px">${detail.unit_name }</td>
					 		<td align="center" height="50px">${detail.spec}</td>
					    </tr>
					 	</c:forEach>
					 	</table>
					 	<table width="850" style="margin-top: -15px">
					 	<tr>
					 		<td width=""><p align=""><span>提交人：${var.commitby }</span>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					 		<td width=""><p align=""><span>提交时间：<fmt:formatDate value="${var.commit_time }" pattern="yyyy-MM-dd "/></span></p></td>
					 		<td width=""><p align=""><span>审核人：${var.auditby }</span>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					 		<td width=""><p align=""><span>审核时间：<fmt:formatDate value="${var.audit_time }" pattern="yyyy-MM-dd "/></span></p></td>
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