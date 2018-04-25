<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.fh.util.IDCardUtil" %>
<%@page import="com.fh.util.DateUtil" %>

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

<style>
.button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 6px 36px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 10px;
    margin: 4px 2px;
    cursor: pointer;
}

.mybody{
	 background-color: #FFFFFF;
	 padding: 12px;
	 text-align: center;
}
</style>
	
<body class="mybody" align="center">
	<div>
		<p><font size="3" face="arial" color="red">返回信息：${rentity.msg}</font></p>
		</br>
		<p><font size="3" face="arial" color="red">返回码:${rentity.code}</font></p>
	</div>
	
</body>


</html>