<%@page import="com.fh.util.DateUtil"%>
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
						<div class="col-xs-12">
							
						<!-- 检索  -->
						<form action="remainuserdata/list.do" method="post" name="Form" id="Form">
						<input type="hidden" name="dateType" id="dateType">
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
								<c:if test="${QX.cha == 1 }">
									<td style="vertical-align: top; padding-left: 2px">
									<span class="input-icon" style="width: 30px;"> </span> 
									<a class="btn btn-light btn-xs blue" onclick="tosearch(1);" title="周"
												style="border-radius: 5px; color: blue !important; width: 50px">周</a>
									</td>
								</c:if>
								<c:if test="${QX.cha == 1 }">
									<td style="vertical-align: top; padding-left: 2px">
									<span class="input-icon" style="width: 30px;"> </span> 
									<a class="btn btn-light btn-xs blue" onclick="tosearch(2);" title="月"
												style="border-radius: 5px; color: blue !important; width: 50px">月</a>
									</td>
								</c:if>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">日期</th>
									<th class="center">购彩数</th>
									<th class="center">第2${pd.type }</th>
									<th class="center">第3${pd.type }</th>
									<th class="center">第4${pd.type }</th>
									<th class="center">第5${pd.type }</th>
									<th class="center">第6${pd.type }</th>
									<th class="center">第7${pd.type }</th>
									<c:if test="${!empty pd.typeForDay}">
									<th class="center">第15${pd.type }</th>
									<th class="center">第30${pd.type }</th>
									</c:if>
									<c:if test="${empty pd.typeForDay}">
									<th class="center">第8${pd.type }</th>
									<th class="center">第9${pd.type }</th>
									<th class="center">第10${pd.type }</th>
									<th class="center">第11${pd.type }</th>
									<th class="center">第12${pd.type }</th>
									</c:if>
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
											<td class='center'>${var.count_user }</td>
											<td class='center'>${var.count2 }<c:if test="${!empty var.count2}">%</c:if></td>
											<td class='center'>${var.count3 }<c:if test="${!empty var.count3}">%</c:if></td>
											<td class='center'>${var.count4 }<c:if test="${!empty var.count4}">%</c:if></td>
											<td class='center'>${var.count5 }<c:if test="${!empty var.count5}">%</c:if></td>
											<td class='center'>${var.count6 }<c:if test="${!empty var.count6}">%</c:if></td>
											<td class='center'>${var.count7 }<c:if test="${!empty var.count7}">%</c:if></td>
											<c:if test="${!empty pd.typeForDay}">
											<td class='center'>${var.count15 }<c:if test="${!empty var.count15}">%</c:if></td>
											<td class='center'>${var.count30 }<c:if test="${!empty var.count30}">%</c:if></td>
											</c:if>
											<c:if test="${empty pd.typeForDay}">
											<td class='center'>${var.count8 }<c:if test="${!empty var.count8}">%</c:if></td>
											<td class='center'>${var.count9 }<c:if test="${!empty var.count9}">%</c:if></td>
											<td class='center'>${var.count10 }<c:if test="${!empty var.count10}">%</c:if></td>
											<td class='center'>${var.count11 }<c:if test="${!empty var.count11}">%</c:if></td>
											<td class='center'>${var.count12 }<c:if test="${!empty var.count12}">%</c:if></td>
											</c:if>
										
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
			//window.location.href='<%=basePath%>remainuserdata/excel.do';
			 $("#Form").attr("action","remainuserdata/excel.do").submit();
			 $("#Form").attr("action","remainuserdata/list.do");

		}
		
	</script>


</body>
</html>