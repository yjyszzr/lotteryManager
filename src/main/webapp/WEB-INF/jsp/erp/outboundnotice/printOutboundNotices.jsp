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

</style>

</head>
<body >

				<%-- 	<c:forEach items="${imglist}" var="var" varStatus="vs">					
						
							<div style="width:180px;height:115px;">
							<img  onclick="window.print()""
			                 src="<%=basePath%>uploadFiles/twoDimensionCode/${var.path}"
			               width="140px" height="110px">
							</td>
							</div>
					</c:forEach> --%>
				

	<div id="zhongxin" onclick="window.print()">
			<c:choose>
				<c:when test="${not empty list}">
					<c:forEach items="${list}" var="var" varStatus="vs">	
					 <!-- <hr> -->
					 <div align="center" style="page-break-after: always;">
					  
					  	 <%-- <table width="850">
							 <tr>
						 	  	<td width="33%" ><img width="80px" height="80px" src="static/pgt_logo.png" alt="Logo" /></td>
						 	  	<td width="33%" style="text-align:center;"><b style="font-size: 40px;" >出库单</b></td>
						 	  	<td width="33%" style="text-align:right;vertical-align:bottom;"><img src="data:image/png;base64,${var.barcode}" alt="Logo" /></td>
							 </tr>
						 </table> --%>
						 <table width="850">
						 <tr>
						 	<td width="33%" ><img width="80px" height="80px" src="static/pgt_logo.png" alt="Logo" /></td>
						 	<td width="33%" style="text-align:center;"><b style="font-size: 40px;" >出库拣货单</b></td>
						 	<td width="33%" style="text-align:right;vertical-align:bottom;"><img src="data:image/png;base64,${var.barcode}" alt="Logo" /></td>
						 </tr>
						 </table>
						 
						 <table width="850">
						<%--  <td width="33%" style="padding-bottom: 10px">
					 		<span>出库单号： ${var.out_bound_code}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					 		</td>
					 		<td width="33%" style="padding-bottom: 10px">
					 		<span>仓库编码：${var.store_sn}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					 		</td>
					 		<td width="33%" style="padding-bottom: 10px">
					 		<span>仓库名称：${var.store_name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> --%>
					 		<tr>
					 			<td width="33%" style="padding-bottom: 10px">
					 				<span>客户：${var.consignee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
					 			<td width="33%" style="padding-bottom: 10px">
					 				<span>通知单日期：<fmt:formatDate value="${var.create_time }" pattern="yyyy-MM-dd "/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
					 			<td width="33%" style="padding-bottom: 10px">
					 				<span>通知单编码：${var.outbound_notice_code }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
					 		</tr>
					 		<tr>
					 			<td width="33%" style="padding-bottom: 10px">
						 			<span>通知单类型：<c:if test="${var.bill_type==0 }"> 领料出库</c:if>
											 <c:if test="${var.bill_type==1 }"> 调拨出库</c:if>
											 <c:if test="${var.bill_type==2 }">采购退货出库</c:if>
											 <c:if test="${var.bill_type==8 }">生产退货出库</c:if>
											 <c:if test="${var.bill_type==3 }">报损出库</c:if>
											 <c:if test="${var.bill_type==4 }">内销出库</c:if>
											 <c:if test="${var.bill_type==5 }">销售出库</c:if>
											 <c:if test="${var.bill_type==7 }">不良品出库</c:if>
											 <c:if test="${var.bill_type==6 }">其他出库</c:if>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
								<td width="37%" style="padding-bottom: 10px" colspan="2">
									<span>业务编码：${var.purchase_code }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
						    </tr>
					 	</table>
					 	<table  width="850" border="1" >
					 		<tr>
				 				<th class="center" width="20%">物料编码</th>
								<th class="center" width="30%">物料名称</th>
								<th class="center" width="10%">规格型号</th>
								<th class="center" width="10%">单位</th>
								<th class="center" width="10%">数量</th>
								<th class="center" width="20%">仓库</th>
					 		</tr>
					 		<c:forEach items="${var.details}" var="detail" varStatus="ds">
					 		<tr>
					 			<td height="50px">${detail.sku_encode }</td>
					 			<td height="50px">${detail.sku_name }</td>
					 			<td align="center" height="50px">${detail.spec }</td>
					 			<td align="center" height="50px">${detail.unit_name }</td>
					 			<td align="center" height="50px">${detail.pre_send_quantity }</td>
					 			<td align="center" height="50px">${var.store_name }</td>
					 		</tr>
					 		</c:forEach>
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