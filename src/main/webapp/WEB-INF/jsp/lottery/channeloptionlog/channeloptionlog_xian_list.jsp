<%@page import="com.fh.util.DateUtil"%>
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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
	<div id="u14" class="ax_default _三级标题">
		<div id="u14_div" class=""></div>
		<div id="u14_text" class="text ">
			<p> <span style="width: 70px;"></span>
				<span style="width: 70px;"><h4>首页</h4></span>
			</p>
		</div>
	</div>
	<div id="u15" class="ax_default line">
		<img id="u15_img" class="img "
			src="static/line.png">
	</div>

	<table border="0" style="border: 1px solid #000000;" cellpadding="0"
		cellspacing="1"  width="100%" height="400" >
		<tr>
			<td style="padding:40px;"><div id="u32" class="ax_default _图片">
					<img id="u32_img" height="150" width="150" class="img "
						src="static/xian.png">
				</div>
			</td>
			<td><table  width="400" height="250" >
					<tr>

						<td><h4>昨日注册用户</h4></td>
						<td><h4>${h }</h4></td>
						<td></td>
					</tr>
					<tr>
						<td><h4>昨日购彩总金额</h4></td>
						<td><h4>${optionAmount }</h4></td>
						<td></td>
					</tr>
					<tr>
						<td><h4>昨日激活用户</h4></td>
						<td><h4>${l }</h4></td>
						<td></td>
					</tr>
					<tr>
						<td><h4>昨日购彩用户</h4></td>
						<td><h4>${m }</h4></td>
						<td><span class="input-icon" style="width: 70px;"> </span> </td>
						<td>
							<a href="<%=basePath%>xiandata/list.do?lastStart=${pd.lastStart }&lastEnd=${pd.lastEnd }"><h3>查看详情</h3></a></td>
					</tr>
				</table></td>
		</tr>

	</table>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
	</script>


</body>
</html>