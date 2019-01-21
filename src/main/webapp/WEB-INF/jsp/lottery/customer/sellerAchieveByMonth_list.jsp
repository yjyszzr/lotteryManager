<%@page import="com.fh.util.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
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
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:60px;text-align: right;">手机号:</td>
								<td > ${pd.PHONE}</td>
								<td style="width:60px;text-align: right;">姓名:</td>
								<td > ${pd.USERNAME} </td>
						      	<td style="text-align: left;" colspan="10">
								    <select  name="pyear" id="pyear" value="${pd.pyear}"  style="width:204px;border-radius:5px !important"   onchange="changeYear(this.value,'${pd.USER_ID}')" >
								        <option value="2018" <c:if test="${pd.pyear==2018}">selected</c:if> >2018年</option>
								        <option value="2019" <c:if test="${pd.pyear==2019}">selected</c:if> >2019年</option>
								    </select>
                               	</td>
								<td style="padding: 5px;"> 
									<a class="btn btn-light btn-xs" onclick="toExcel('${pd.USER_ID}');" title="导出到EXCEL">导出到EXCEL</a>
								</td>  
							</tr>
						</table>
						
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">月份</th>
									<th class="center">增加用户量</th>
									<th class="center">销售金额</th>
									<th class="center">红包使用量</th>
								</tr>
							</thead>
							<tbody>
							
							
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.eveMon}</td>
											<td class='center'>${var.curPersons}</td>
											<td class='center'>${var.curMoneyPaid}</td>
											<td class='center'>${var.curBonus}</td>
										</tr>
									</c:forEach>
								</c:when>
								
							</c:choose>	
							</tbody>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						</div>
					</div>
					

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
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
	
	//导出excel
	function toExcel(userId){
		window.location.href='<%=basePath%>usermanagercontroller/excelSellersDetail.do?user_id='+userId;
	}
	
	function tosearch(){
		top.jzts();
		$("#Form").submit();
	}
	
	function changeYear(value,userId){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>usermanagercontroller/toSellerDetail.do?pyear='+value+'&user_id='+userId,
    		data: {},
			dataType:'json',
			cache: false,
			success: function(data){
				//tosearch();
			}
		});
	}
	</script>
</body>
</html>