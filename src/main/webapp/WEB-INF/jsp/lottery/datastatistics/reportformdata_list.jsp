﻿<%@page import="com.fh.util.DateUtil"%>
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

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12" style="overflow:scroll;">

							<!-- 检索  -->
							<form action="reportformdata/list.do" method="post" name="Form"
								id="Form">
								<table style="margin-top: 5px;">
									<tr>
										<td><span class="input-icon" style="width: 50px;"> </span>日期：</td>
										<td style="padding-left: 2px;"><input
											class="span10 date-picker" name="lastStart" id="lastStart"
											value="${pd.lastStart }" type="text"
											data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width: 118px;" placeholder="注册开始日期" title="注册开始日期" /></td>
										<td>至</td>
										<td style="padding-left: 2px;"><input
											class="span10 date-picker" name="lastEnd" id="lastEnd"
											value="${pd.lastEnd }" type="text"
											data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width: 118px;" placeholder="注册结束日期" title="注册结束日期" /></td>
										
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: top; padding-left: 2px"><span
												class="input-icon" style="width: 30px;"> </span> <a
												class="btn btn-light btn-xs blue" onclick="tosearch(1);"
												title="搜索"
												style="border-radius: 5px; color: blue !important; width: 50px">搜索</a></td>
										</c:if>
										<td style="vertical-align: top; padding-left: 2px"><span
											class="input-icon" style="width: 30px;"> </span> <a
											class="btn btn-light btn-xs blue" onclick="tosearch(0);"
											title="清空"
											style="border-radius: 5px; color: blue !important; width: 50px">清空</a>
										</td>
										<c:if test="${QX.toExcel == 1 }">
											<td style="vertical-align: top; padding-left: 2px;"><span
												class="input-icon" style="width: 30px;"> </span> <a
												class="btn btn-light btn-xs blue" onclick="toExcel();"
												title="导出EXCEL"
												style="border-radius: 5px; color: blue !important; width: 70px">
													导出EXCEL</a></td>
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
										</td>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									 style="margin-top:5px;min-width:2500px;">
									<thead>
										<tr>
											<th class="center">日期</th>
											<th class="center">当${pd.day }实付购彩额</th><!-- 1 -->
											<th class="center">当${pd.day }优惠券金额</th><!-- 2 -->
											<th class="center">当${pd.day }订单金额</th><!-- 3 -->
											<!--<th class='center'>胜平负</th><!-- 4 -->
											<!--<th class='center'>让球胜平负</th><!-- 5 -->
											<!--<th class='center'>比分</th><!-- 6 -->
											<!--<th class='center'>总进球</th><!-- 7 -->
											<!--<th class='center'>半全场</th><!-- 8 -->
											<!--<th class='center'>2选1</th><!-- 9 -->
											<!--<th class='center'>混合投注</th><!-- 10 -->
											<!--<th class='center'>世界杯</th><!-- 11 -->
											<!--<th class='center'>胜平负比例</th> 12 -->
											<!--<th class='center'>让球胜平负比例</th> 13 -->
											<!--<th class='center'>比分比例</th> 14 -->
											<!--<th class='center'>总进球比例</th> 15 -->
											<!--<th class='center'>半全场比例</th> 16 -->
											<!--<th class='center'>2选1比例</th> 17 -->
											<!--<th class='center'>混合投注比例</th> 18 -->
											<!--<th class='center'>世界杯比例</th> 19 -->
											<th class='center'>当${pd.day }购彩用户数</th><!-- 20 -->
											<th class='center'>当${pd.day }三方支付</th><!-- 201 -->
											<th class='center'>当${pd.day }余额支付</th><!-- 202 -->
											<th class='center'>当${pd.day }订单量</th><!-- 203 -->
											<th class='center'>当${pd.day }提现金额</th><!-- 21 -->
											<th class='center'>当${pd.day }充值金额</th><!-- 22 -->
											<th class='center'>当${pd.day }出票失败金额</th><!-- 39 -->
											<th class='center'>当${pd.day }支付失败金额</th><!-- 40 -->
											<th class='center'>当${pd.day }新增的注册用户数</th><!-- 23 -->
											<th class='center'>当${pd.day }新增注册并购彩用户数</th><!-- 24 -->
											<th class='center'>当${pd.day }新增注册并认证用户数</th><!-- 25 -->
											<th class='center'>当${pd.day }新增注册并购彩用户的购彩金额</th><!-- 26 -->
											<th class='center'>当${pd.day }新增的认证用户数</th><!-- 27 -->
											<th class='center'>当${pd.day }新增认证并购彩用户数</th><!-- 28 -->
											<th class='center'>当${pd.day }新增认证并购彩用户的购彩金额</th><!-- 29 -->
											<th class='center'>当${pd.day }注册到购彩的转化率</th><!-- 30 -->
											<th class='center'>当${pd.day }新增的购彩用户数</th><!-- 31 -->
											<th class='center'>当${pd.day }新增购彩用户的购彩金额</th><!-- 32 -->
											<th class='center'>当${pd.day }新增购彩用户人均购彩金额</th><!-- 33 -->
											<th class='center'>老用户购彩人数</th><!-- 34 -->
											<th class='center'>老用户的购彩金额</th><!-- 35 -->
											<th class='center'>老用户人均购彩金额</th><!-- 36 -->
											<th class='center'>累计新增购彩用户数</th><!-- 37 -->
											<th class='center'>总购彩金额</th><!-- 38 -->
										</tr>
									</thead>

									<tbody>
										<c:choose>
											<c:when test="${not empty varList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
															<td class='center'>${var.date }</td>
															<td class='center'>${var.data1 }</td>
															<td class='center'>${var.data2 }</td>
															<td class='center'>${var.data3 }</td>
															<!--<td class='center'>${var.ply2 }<c:if test="${ empty var.ply2}">0.00</c:if></td>
															<td class='center'>${var.ply1 }<c:if test="${ empty var.ply1}">0.00</c:if></td>
															<td class='center'>${var.ply3 }<c:if test="${ empty var.ply3}">0.00</c:if></td>
															<td class='center'>${var.ply4 }<c:if test="${ empty var.ply4}">0.00</c:if></td>
															<td class='center'>${var.ply5 }<c:if test="${ empty var.ply5}">0.00</c:if></td>
															<td class='center'>${var.ply6 }<c:if test="${ empty var.ply6}">0.00</c:if></td>
															<td class='center'>${var.ply7 }<c:if test="${ empty var.ply7}">0.00</c:if></td>
															<td class='center'>${var.ply8 }<c:if test="${ empty var.ply8}">0.00</c:if></td>
															<!--<td class='center'>
																<c:if test="${not empty var.ply2}">
																<fmt:formatNumber type="number" value="${var.ply2/var.data3 *100 }" maxFractionDigits="2"/>%	
																</c:if>			
																<c:if test="${empty var.ply2}">
																0.00%
																</c:if>										
															</td>
															<td class='center'>
																<c:if test="${not empty var.ply1}">
																<fmt:formatNumber type="number" value="${var.ply1/var.data3 *100 }" maxFractionDigits="2"/>%	
																</c:if>				
																<c:if test="${empty var.ply1}">
																0.00%
																</c:if>													
															</td>
															<td class='center'>
																<c:if test="${not empty var.ply3}">
																<fmt:formatNumber type="number" value="${var.ply3/var.data3 *100 }" maxFractionDigits="2"/>%	
																</c:if>					
																<c:if test="${empty var.ply3}">
																0.00%
																</c:if>												
															</td>
															<td class='center'>
																<c:if test="${not empty var.ply4}">
																<fmt:formatNumber type="number" value="${var.ply4/var.data3 *100 }" maxFractionDigits="2"/>%	
																</c:if>					
																<c:if test="${empty var.ply4}">
																0.00%
																</c:if>												
															</td>
															<td class='center'>
																<c:if test="${not empty var.ply5}">
																<fmt:formatNumber type="number" value="${var.ply5/var.data3 *100 }" maxFractionDigits="2"/>%	
																</c:if>					
																<c:if test="${empty var.ply5}">
																0.00%
																</c:if>												
															</td>
															<td class='center'>
																<c:if test="${not empty var.ply6}">
																<fmt:formatNumber type="number" value="${var.ply6/var.data3 *100 }" maxFractionDigits="2"/>%	
																</c:if>					
																<c:if test="${empty var.ply6}">
																0.00%
																</c:if>												
															</td>
															<td class='center'>
																<c:if test="${not empty var.ply7}">
																<fmt:formatNumber type="number" value="${var.ply7/var.data3 *100 }" maxFractionDigits="2"/>%	
																</c:if>					
																<c:if test="${empty var.ply7}">
																0.00%
																</c:if>												
															</td>
															<td class='center'>
																<c:if test="${not empty var.ply8}">
																<fmt:formatNumber type="number" value="${var.ply8/var.data3 *100 }" maxFractionDigits="2"/>%	
																</c:if>				
																<c:if test="${empty var.ply8}">
																0.00%
																</c:if>													
															</td>-->
															<td class='center'>${var.data20 }</td>
															<td class='center'>${var.data201 }</td>
															<td class='center'>${var.data202 }</td>
															<td class='center'>${var.data203 }</td>
															<td class='center'>${var.data21 }</td>
															<td class='center'>${var.data22 }</td>
															<td class='center'>${var.data39 }</td>
															<td class='center'>${var.data40 }</td>
															<td class='center'>${var.data23 }</td>
															<td class='center'>${var.data24 }</td>
															<td class='center'>${var.data25 }</td>
															<td class='center'>${var.data26 }</td>
															<td class='center'>${var.data27 }</td>
															<td class='center'>${var.data28 }</td>
															<td class='center'>${var.data29 }</td>
															<td class='center'>${var.data30 }<c:if test="${empty var.data30}">0.00%</c:if></td>
															<td class='center'>${var.data31 }</td>
															<td class='center'>${var.data32 }</td>
															<td class='center'>
																<c:if test="${var.data31 != 0}">
																<fmt:formatNumber type="number" value="${var.data32/var.data31 }" maxFractionDigits="2"/>
																</c:if>
																<c:if test="${var.data31 == 0}">
																0.00
																</c:if>
															</td>
															<td class='center'>${var.data34 }</td>
															<td class='center'>${var.data35 }</td>
															<td class='center'>
																<c:if test="${var.data34 != 0}">
																<fmt:formatNumber type="number" value="${var.data35/var.data34 }" maxFractionDigits="2"/>	
																</c:if>	
																<c:if test="${var.data34 == 0}">
																0.00
																</c:if>
															</td>
															<td class='center'>${var.data37 }</td>
															<td class='center'>${var.data38 }</td>
															
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
													<td colspan="100" class="center">没有相关数据</td>
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
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>


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
		function tosearch(status) {
			if (status == 0) {
				$("#lastStart").val("");
				$("#lastEnd").val("");
				$("#handyCheck").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		$(function() {

			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});

			//下拉框
			if (!ace.vars['touch']) {
				$('.chosen-select').chosen({
					allow_single_deselect : true
				});
				$(window).off('resize.chosen').on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({
							'width' : $this.parent().width()
						});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen',
						function(e, event_name, event_val) {
							if (event_name != 'sidebar_collapsed')
								return;
							$('.chosen-select').each(function() {
								var $this = $(this);
								$this.next().css({
									'width' : $this.parent().width()
								});
							});
						});
				$('#chosen-multiple-style .btn').on(
						'click',
						function(e) {
							var target = $(this).find('input[type=radio]');
							var which = parseInt(target.val());
							if (which == 2)
								$('#form-field-select-4').addClass(
										'tag-input-style');
							else
								$('#form-field-select-4').removeClass(
										'tag-input-style');
						});
			}
		});
		//导出excel
		function toExcel(){
			//window.location.href='<%=basePath%>totaldata/excel.do';
			 $("#Form").attr("action","reportformdata/excel.do").submit();
			 $("#Form").attr("action","reportformdata/list.do");
		}
	</script>


</body>
</html>