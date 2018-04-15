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
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
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
					
					<form action="szystore/savewarerel.do" name="Form" id="Form1" method="post">
						
						<input type="hidden"  value="${store_id}" id="store_id" name="store_id" />
						
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
						
						
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;"> 上级仓库:</td>
								<td>
								
									 <c:choose>
								<c:when test="${pdstore.parent_store_id==null||pdstore.parent_store_id==0}">
									     
								 <select class="chosen-select form-control" name="parent_store_id" id="parent_store_id" data-placeholder="请选择" style="width:98%;">
									<option value=""></option>
									<c:forEach items="${varList}" var="var" varStatus="vs">
									
									<option value="${var.store_id}">${var.store_name}</option>
									</c:forEach>
								</select>
								</c:when>
								
								<c:otherwise>
								
									<select class="chosen-select form-control" name="parent_store_id" id="parent_store_id" data-placeholder="请选择" style="width:98%;">
									
									<c:if test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
									<c:choose>
									<c:when test="${pdstore.parent_store_id==var.store_id}">
									     <option selected="selected" value="${var.store_id}">${var.store_name}</option>
									</c:when>
									<c:otherwise>
									<option value="${var.store_id}">${var.store_name}</option>
									 </c:otherwise>
									</c:choose>
									</c:forEach>
									</c:if>
									
								  	</select>
								
								 </c:otherwise>
								</c:choose>
								
								
								</td>
							</tr>
							
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">不良品仓库:</td>
								<td>
								
								 <c:choose>
								<c:when test="${pdstore.bad_store_id==null||pdstore.bad_store_id==0}">
									     
								 <select class="chosen-select form-control" name="bad_store_id" id="bad_store_id" data-placeholder="请选择" style="width:98%;">
									<option value=""></option>
									<c:forEach items="${varList}" var="var" varStatus="vs">
									
									<c:if test="${var.store_type_sort==1}">
									<option value="${var.store_id}">${var.store_name}</option>
									</c:if>
									
									</c:forEach>
								</select>
								</c:when>
								
								<c:otherwise>
								
								<select class="chosen-select form-control" name="bad_store_id" id="bad_store_id" data-placeholder="请选择" style="width:98%;">
									<c:if test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
									<c:choose>
									<c:when test="${pdstore.bad_store_id==var.store_id}">
									     <option selected="selected" value="${var.store_id}">${var.store_name}</option>
									</c:when>
									<c:otherwise>
									<c:if test="${var.store_type_sort==0}">
									<option value="${var.store_id}">${var.store_name}</option>
										</c:if>
									
									 </c:otherwise>
									</c:choose>
									
									</c:forEach>
										</c:if>
								  	</select>
								
								 </c:otherwise>
								</c:choose>
								
									
								</td>
							</tr>
							
							
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;"> 物流仓库:</td>
								<td>
								
								 <c:choose>
								<c:when test="${pdstore.logistic_store_id==null||pdstore.logistic_store_id==0}">
									     
								 <select class="chosen-select form-control" name="logistic_store_id" id="logistic_store_id" data-placeholder="请选择" style="width:98%;">
									<option value=""></option>
									<c:forEach items="${varList}" var="var" varStatus="vs">
									<c:if test="${var.store_type_sort==2}">
									<option value="${var.store_id}">${var.store_name}</option>
									</c:if>
									
									</c:forEach>
								</select>
								</c:when>
								
								<c:otherwise>
								
								<select class="chosen-select form-control" name="logistic_store_id" id="logistic_store_id" data-placeholder="请选择" style="width:98%;">
									
									<c:if test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
						         	<c:choose>
									<c:when test="${pdstore.logistic_store_id==var.store_id}">
									     <option selected="selected" value="${var.store_id}">${var.store_name}</option>
									</c:when>
									<c:otherwise>
									<c:if test="${var.store_type_sort==2}">
									<option value="${var.store_id}">${var.store_name}</option>
									</c:if>
									
									 </c:otherwise>
									</c:choose>
									
									</c:forEach>
										</c:if>
								  	</select>
								
								 </c:otherwise>
								</c:choose>
								
									
								</td>
							</tr>
							
									<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;"> 报损仓库:</td>
								<td>
								
								 <c:choose>
								<c:when test="${pdstore.scrap_store_id==null||pdstore.scrap_store_id==0}">
									     
								 <select class="chosen-select form-control" name="scrap_store_id" id="scrap_store_id" data-placeholder="请选择" style="width:98%;">
									<option value=""></option>
									<c:forEach items="${varList}" var="var" varStatus="vs">
									<c:if test="${var.store_type_sort==5}">
									<option value="${var.store_id}">${var.store_name}</option>
									</c:if>
									
									</c:forEach>
								</select>
								</c:when>
								
								<c:otherwise>
								
									<select class="chosen-select form-control" name="scrap_store_id" id="scrap_store_id" data-placeholder="请选择" style="width:98%;">
									
									<c:if test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
						         	<c:choose>
									<c:when test="${pdstore.scrap_store_id==var.store_id}">
									     <option selected="selected" value="${var.store_id}">${var.store_name}</option>
									</c:when>
									<c:otherwise>
										<c:if test="${var.store_type_sort==5}">
										<option value="${var.store_id}">${var.store_name}</option>
										</c:if>
									
									 </c:otherwise>
									</c:choose>
									
									</c:forEach>
										</c:if>
								  	</select>
								
								 </c:otherwise>
								</c:choose>
								
								
								</td>
							</tr>
							
									<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;"> 虚拟仓库:</td>
								<td>
								
							   <c:choose>
								<c:when test="${pdstore.virtual_store_id==null||pdstore.virtual_store_id==0}">
									     
								 <select class="chosen-select form-control" name="virtual_store_id" id="virtual_store_id" data-placeholder="请选择" style="width:98%;">
									<option value=""></option>
									<c:forEach items="${varList}" var="var" varStatus="vs">
									
									<c:if test="${var.store_type_sort==4}">
										<option value="${var.store_id}">${var.store_name}</option>
									</c:if>
								
									</c:forEach>
								</select>
								</c:when>
								
								<c:otherwise>
								
								<select class="chosen-select form-control" name="virtual_store_id" id="virtual_store_id" data-placeholder="请选择" style="width:98%;">
									
									<c:if test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
						         	<c:choose>
									<c:when test="${pdstore.virtual_store_id==var.store_id}">
									     <option selected="selected" value="${var.store_id}">${var.store_name}</option>
									</c:when>
									<c:otherwise>
									<c:if test="${var.store_type_sort==4}">
									<option value="${var.store_id}">${var.store_name}</option>
									</c:if>
									 </c:otherwise>
									</c:choose>
									
									</c:forEach>
										</c:if>
								  	</select>
								
								 </c:otherwise>
								</c:choose>
								
									
								</td>
							</tr>
							
								<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;"> 待检仓库:</td>
								<td>
								
								<c:choose>
								<c:when test="${pdstore.quality_store_id==null||pdstore.quality_store_id==0}">
									     
								 <select class="chosen-select form-control" name="quality_store_id" id="quality_store_id" data-placeholder="请选择" style="width:98%;">
									<option value=""></option>
									<c:forEach items="${varList}" var="var" varStatus="vs">
									
									<c:if test="${var.store_type_sort==6}">
									<option value="${var.store_id}">${var.store_name}</option>
									</c:if>
									
									</c:forEach>
								</select>
								</c:when>
									
									<c:otherwise>
									
									<select class="chosen-select form-control" name="quality_store_id" id="quality_store_id" data-placeholder="请选择" style="width:98%;">
									
									<c:if test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
						         	<c:choose>
									<c:when test="${pdstore.quality_store_id==var.store_id}">
									     <option selected="selected" value="${var.store_id}">${var.store_name}</option>
									</c:when>
									<c:otherwise>
									<c:if test="${var.store_type_sort==6}">
									<option value="${var.store_id}">${var.store_name}</option>
									</c:if>
									
									 </c:otherwise>
									</c:choose>
									
									</c:forEach>
										</c:if>
								  	</select>
									
									 </c:otherwise>
								</c:choose>
									
								</td>
							</tr>
							
							
							
								<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="savewarerel();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td> 
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
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存仓关系
		function savewarerel() {
			     /* alert("2");
			    console.info($("#Form1")) */
				$("#Form1").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
				//top.Dialog.close();
			}
		</script>
</body>
</html>