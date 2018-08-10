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
						<div class="col-xs-12" style="overflow:scroll;">
							
						<!-- 检索  -->
						<form action="orderplaydata/list.do" method="post" name="Form" id="Form">
						<input type="hidden" name="dateType" id="dateType" value="${pd.dateType }">
						<table style="margin-top:5px;">
							<tr>
							<td><span class="input-icon" style="width: 270px;"> </span>日期：</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:118px;" placeholder="注册开始日期" title="注册开始日期"/></td>
								<td>至</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:118px;" placeholder="注册结束日期" title="注册结束日期"/></td>
								<c:if test="${QX.cha == 1 }">
									<td style="vertical-align: top; padding-left: 2px">
									<span class="input-icon" style="width: 30px;"> </span> 
									<a class="btn btn-light btn-xs blue" onclick="tosearch(0);" title="日"
												style="border-radius: 5px; color: blue !important; width: 50px">日</a>
									</td>
								</c:if>
								<td style="vertical-align: top; padding-left: 2px">
								<span class="input-icon" style="width: 30px;"> </span>
								 <a class="btn btn-light btn-xs blue" onclick="tosearch(1);" title="周"
											style="border-radius: 5px; color: blue !important; width: 50px">周</a>
								</td>
								<td style="vertical-align: top; padding-left: 2px;">
								<span class="input-icon" style="width: 30px;"> </span> 
								<a class="btn btn-light btn-xs blue" onclick="tosearch(2);" title="月"
									style="border-radius: 5px; color: blue !important; width: 50px"> 月</a></td>
									
								<td style="vertical-align: top; padding-left: 2px;">
								<span class="input-icon" style="width: 30px;"> </span> 
								<a class="btn btn-light btn-xs blue" onclick="tosearch(3);" title="区间"
									style="border-radius: 5px; color: blue !important; width: 50px"> 区间</a></td>
									
								<c:if test="${QX.toExcel == 1 }">
								<td style="vertical-align: top; padding-left: 2px;">
								<span class="input-icon" style="width: 30px;"> </span> 
								<a class="btn btn-light btn-xs blue" onclick="toExcel();" title="导出"
									style="border-radius: 5px; color: blue !important; width: 70px"> 导出Excel</a></td>
								</c:if>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover table-condensed" style="margin-top:5px;min-width:1500px;" >	
							<thead>
								<tr>
									<th class='center' rowspan="2">时间</th>
									
									<th class='center' colspan="3">胜平负</th>
									<th class='center' colspan="3">让球胜平负</th>
									<th class='center' colspan="3">比分</th>
									<th class='center' colspan="3">总进球</th>
									<th class='center' colspan="3">半全场</th>
									<th class='center' colspan="3">2选1</th>
									<th class='center' colspan="3">混合投注</th>
								</tr>
								<tr>
									<th class='center'>购彩用户数</th>
									<th class='center'>购彩金额</th>
									<th class='center'>订单数</th>
									<th class='center'>购彩用户数</th>
									<th class='center'>购彩金额</th>
									<th class='center'>订单数</th>
									<th class='center'>购彩用户数</th>
									<th class='center'>购彩金额</th>
									<th class='center'>订单数</th>
									<th class='center'>购彩用户数</th>
									<th class='center'>购彩金额</th>
									<th class='center'>订单数</th>
									<th class='center'>购彩用户数</th>
									<th class='center'>购彩金额</th>
									<th class='center'>订单数</th>
									<th class='center'>购彩用户数</th>
									<th class='center'>购彩金额</th>
									<th class='center'>订单数</th>
									<th class='center'>购彩用户数</th>
									<th class='center'>购彩金额</th>
									<th class='center'>订单数</th>
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
											
											<td class='center'>${var.userCount2 }</td>
											<td class='center'>${var.amount2 }</td>
											<td class='center'>${var.orderCount2 }</td>
											
											<td class='center'>${var.userCount1 }</td>
											<td class='center'>${var.amount1 }</td>
											<td class='center'>${var.orderCount1 }</td>
											
											<td class='center'>${var.userCount3 }</td>
											<td class='center'>${var.amount3 }</td>
											<td class='center'>${var.orderCount3 }</td>
											
											<td class='center'>${var.userCount4 }</td>
											<td class='center'>${var.amount4 }</td>
											<td class='center'>${var.orderCount4 }</td>
											
											<td class='center'>${var.userCount5 }</td>
											<td class='center'>${var.amount5 }</td>
											<td class='center'>${var.orderCount5 }</td>
											
											<td class='center'>${var.userCount7 }</td>
											<td class='center'>${var.amount7 }</td>
											<td class='center'>${var.orderCount7 }</td>
											
											<td class='center'>${var.userCount6 }</td>
											<td class='center'>${var.amount6 }</td>
											<td class='center'>${var.orderCount6 }</td>
											 
										
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
				$("#dateType").val("0");
			}
			if (status == 1) {
				$("#dateType").val("1");
			}
			if (status == 2) {
				$("#dateType").val("2");
			}
			if (status == 3) {
				$("#dateType").val("3");
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
			
			 $("#table").bootstrapTable('destroy').bootstrapTable({
			        fixedColumns: true, 
			        fixedNumber: 1 //固定列数
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
			//window.location.href='<%=basePath%>orderplaydata/excel.do';
			 $("#Form").attr("action","orderplaydata/excel.do").submit();
			 $("#Form").attr("action","orderplaydata/list.do");

		}
		
	</script>


</body>
</html>