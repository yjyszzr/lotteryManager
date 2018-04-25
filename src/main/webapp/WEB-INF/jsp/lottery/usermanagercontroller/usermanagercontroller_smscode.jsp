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
}
</style>
	
<body class="mybody" >
	<div>
	<table align="center" padding-top="12px">
		<tr>
		    <th>手机号：</th>
		    <th>${entity.mobile}</th>
  		</tr>
  		
  		<tr>
    		<td>手机验证码：</td>
    		<td>${entity.smscode}</td>
  		</tr>
  		
  		<tr>
  			<td><button class="button" onclick="onSend('${entity.mobile}','${entity.smscode}')">发送</button></td>
  		</tr>
  		
	</table>
	
	<script type="text/javascript">
		function onSend(phone,smscode){
			alert("phone:" + phone + " smscode:" + smscode);
			window.location.href='<%=basePath%>usermanagercontroller/postSmsCode.do?mobile='+phone+'&smsCode='+smscode;
		}
	</script>
	
</body>


</html>