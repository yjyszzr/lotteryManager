﻿<%@page import="com.fh.util.DateUtil"%>
<%@page import="com.fh.util.MoneyUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
						<div class="col-xs-12"  style="overflow:scroll;">
							
						<!-- 检索  -->
						<form action="firstagaindata/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td><span class="input-icon" style="width: 270px;"> </span>日期：</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:118px;" placeholder="注册开始日期" title="注册开始日期"/></td>
								<td>至</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:118px;" placeholder="注册结束日期" title="注册结束日期"/></td>
								<c:if test="${QX.cha == 1 }">
									<td style="vertical-align: top; padding-left: 2px">
									<span class="input-icon" style="width: 30px;"> </span> 
									<a class="btn btn-light btn-xs blue" onclick="tosearch(1);" title="搜索"
												style="border-radius: 5px; color: blue !important; width: 50px">搜索</a>
									</td>
								</c:if>
								<td style="vertical-align: top; padding-left: 2px">
								<span class="input-icon" style="width: 30px;"> </span>
								 <a class="btn btn-light btn-xs blue" onclick="tosearch(0);" title="清空"
											style="border-radius: 5px; color: blue !important; width: 50px">清空</a>
								</td>
								<c:if test="${QX.toExcel == 1 }">
								<td style="vertical-align: top; padding-left: 2px;">
								<span class="input-icon" style="width: 30px;"> </span> 
								<a class="btn btn-light btn-xs blue" onclick="toExcel();" title="导出EXCEL"
									style="border-radius: 5px; color: blue !important; width: 70px"> 导出EXCEL</a></td>
								</c:if>
								<td><span class="input-icon"  style="width: 30px;"></span>
											<label class="radio-inline">
										  		<input type="radio" name="dateType" id="dateType" value="0" <c:if test="${pd.dateType == 0}">checked</c:if> > 日
											</label>
											<label class="radio-inline">
										  		<input type="radio" name="dateType" id="dateType" value="1" <c:if test="${pd.dateType == 1}">checked</c:if>> 周
											</label>
											<label class="radio-inline">
										  		<input type="radio" name="dateType" id="dateType" value="2" <c:if test="${pd.dateType == 2}">checked</c:if>> 月
											</label>
											<label class="radio-inline">
										  		<input type="radio" name="dateType" id="dateType" value="3" <c:if test="${pd.dateType == 3}">checked</c:if>> 区间
											</label>
										</td>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;min-width:1500px;">	
							<thead>
								<tr>
									<th class="center">日期</th>
									<th class="center">注册用户</th>
									<th class="center">首购用户</th>
									<th class="center">首购金额</th>
									<th class="center">复购用户</th>
									<th class="center">复购金额</th>
									<th class="center">充值用户</th>
									<th class="center">充值金额</th>
									<th class="center">认证用户数</th>
									<th class="center">新增认证并购彩用户数</th>
									<th class="center">新增认证并购彩用户的购彩金额</th>
									<th class="center">注册并认证用户数</th>
									<th class="center">注册并认证用户的购彩金额</th>
									<th class="center">注册并充值用户</th>
									<th class="center">注册并充值金额</th>
									<th class="center">注册新增购彩用户数</th>
									<th class="center">注册新增购彩金额</th>
									<th class="center">注册首购后复购用户数</th>
									<th class="center">注册首购后复购金额</th>
									<th class="center">注册-购彩转化率</th>
									<th class="center">注册首购后复购转化率</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>${var.date }</td>
											<td class='center'>${var.registerCount }</td>
											<td class='center'>${var.firstCount }</td>
											<td class='center'>${var.firstAmount }</td>
											<td class='center'>${var.againCount }</td>
											<td class='center'>${var.againAmount }</td>
											<td class='center'>${var.countRecharge }</td>
											<td class='center'>${var.amountRecharge }</td>
											<td class='center'>${var.realCount }</td>
											<td class='center'>${var.realOrderCount }</td>
											<td class='center'>${var.realOrderAmount }</td>
											<td class='center'>${var.registerRealCount }</td>
											<td class='center'>${var.registerRealAmount }</td>
											<td class='center'>${var.registerRechargeCount }</td>
											<td class='center'>${var.registerRechargeAmount }</td>
											<td class='center'>${var.registerOrderCount }</td>
											<td class='center'>${var.registerOrderAmount }</td>
											<td class='center'>${var.registerAgainOrderCount }</td>
											<td class='center'>${var.registerAgainOrderAmount }</td>
											<td class='center'>${var.percentA }<c:if test="${empty var.percentA}">0.00%</c:if></td>
											<td class='center'>${var.percentB }<c:if test="${empty var.percentB}">0.00%</c:if></td>
										
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
<%-- 									<c:if test="${QX.add == 1 }"> --%>
<!-- 									<a class="btn btn-mini btn-success" onclick="add();">新增</a> -->
<%-- 									</c:if> --%>
<%-- 									<c:if test="${QX.del == 1 }"> --%>
<!-- 									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a> -->
<%-- 									</c:if> --%>
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
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(status){
			if (status == 0) {
				$("#lastStart").val("");
				$("#lastEnd").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
		
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
			
			 
		});
		//导出excel
		function toExcel(){
			//window.location.href='<%=basePath%>firstagaindata/excel.do';
			 $("#Form").attr("action","firstagaindata/excel.do").submit();
			 $("#Form").attr("action","firstagaindata/list.do");

		}
		
	</script>


</body>
</html>