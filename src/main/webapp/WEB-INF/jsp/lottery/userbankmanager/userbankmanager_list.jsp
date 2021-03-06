﻿<%@page import="com.fh.util.DateUtil"%>
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
							
						<!-- 检索  -->
						<form action="userbankmanager/list.do" method="post" name="Form" id="Form">
						<table  style="margin-top:5px;border-collapse:separate; border-spacing:10px;">
								<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												真实姓名:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="真实姓名" class="nav-search-input" id="real_name" autocomplete="off" name="real_name" value="${pd.real_name }"  />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												银行名称:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="银行名称" class="nav-search-input" id="bank_name" autocomplete="off" name="bank_name" value="${pd.bank_name}" />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												卡号:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="卡号" class="nav-search-input" id="card_no" autocomplete="off" name="card_no" value="${pd.card_no}" />
											</span>
										</div>
									</td>
							</tr>
							<tr style="margin:2px">
									<td >
										<span class="input-icon" style="width:80px;text-align:right;">
												添加时间:
											</span>
											<span>
												<input  name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
												<input  name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间" title="结束时间"/>
											</span>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													平台来源:
												</span>
										 	<select  name="app_code_name" id="app_code_name" data-placeholder="请选择" value="${pd.app_code_name }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected>全部</option>
											<option value="10" <c:if test="${pd.app_code_name == 10}">selected</c:if>>球多多</option>
											<option value="11" <c:if test="${pd.app_code_name == 11}">selected</c:if>>圣和彩店</option>
										  	</select>
									  	</div>
									</td>
									<c:if test="${QX.cha == 1 }">
										<td style="vertical-align:top;padding-left:2px">
											<span class="input-icon" style="width:80px;"> </span>
											<span>
													<a class="btn btn-light btn-xs blue" onclick="tosearch(1);"  title="搜索"  style="border-radius:5px;color:blue !important; width:50px">搜索</a>
											</span>
											<span class="input-icon" style="width:43px;"> </span>
											<span>
													<a class="btn btn-light btn-xs blue" onclick="tosearch(0);"  title="清空"  style="border-radius:5px;color:blue !important; width:50px">清空</a>
											</span>
										</td>
									</c:if>
								</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">用户ID</th>
									<th class="center">真实姓名</th>
									<th class="center">银行名称</th>
									<th class="center">银行卡号</th>
									<th class="center">银行卡logo</th>
									<th class="center">添加时间</th>
									<th class="center">最近修改</th>
									<th class="center">平台来源</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>${var.user_id}</td>
											<td class='center'><a onclick="toBankDetail('${var.user_id}');" style=" cursor:pointer;">${var.real_name}</a></td>
											<td class='center'>${var.bank_name}</td>
											<td class='center'>${var.card_no}</td>
											<td class='center'><img src="${var.bank_logo}" style="width:90px;hight:30px"></td>
											<td class='center'>${DateUtil.toSDFTime(var.add_time*1000)}</td>
											<td class='center'>${DateUtil.toSDFTime(var.last_time*1000)}</td>
											<td class='center'> 
												<c:choose>
													<c:when test="${var.app_code_name == 10 }">球多多</c:when>
													<c:when test="${var.app_code_name == 11 }">圣和彩店</c:when>
												</c:choose>
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
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
		//检索
		function tosearch(status){
			if(status==0){
				$("#bank_name").val("");
				$("#card_no").val("");
				$("#real_name").val("");
				$("#lastStart").val("");
				$("#lastEnd").val("");
				$("#app_code_name").empty();
			}
			top.jzts();
			$("#Form").submit();
		}
		
		
		//用户银行卡列表
		function toBankDetail(userId){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="用户银行卡列表";
			 diag.URL = '<%=basePath%>userbankmanager/toBankDetail.do?user_id='+userId;
			 diag.Width = 850;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = false;	//最大化按钮
		     diag.ShowMinButton = false;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
	</script>
</body>
</html>