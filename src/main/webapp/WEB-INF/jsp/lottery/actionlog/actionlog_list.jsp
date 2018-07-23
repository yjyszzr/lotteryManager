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

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">

							<form action="actionlog/list.do" method="post" name="Form"
								id="Form">
								<!-- 检索  -->
								<table style="margin-top: 5px;">
									<tr style="margin: 2px">
										<td>
											<div class="nav-search">
												  操作类型: 
												  <select name="action_type" id="action_type" data-placeholder="请选择" value="${pd.action_type }"
													style="width: 70px; border-radius: 5px !important">
													<option value="" selected="selected">全部</option>
													<option value="0" <c:if test="${pd.action_type == '0'}">selected</c:if>>修改</option>
													<option value="1" <c:if test="${pd.action_type == '1'}">selected</c:if>>添加</option>
													<option value="2" <c:if test="${pd.action_type == '2'}">selected</c:if>>删除</option>
												</select> 
												<span class="input-icon" style="width: 10px; text-align: right;"> </span>
												时间: <span>
													<input name="lastStart" id="lastStart" value="${pd.lastStart }" type="text"
													onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
													readonly="readonly" style="width: 150px; border-radius: 5px !important"
													placeholder="开始时间" title="开始时间" /> 
													<input name="lastEnd" id="lastEnd" value="${pd.lastEnd }" type="text"
													onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
													readonly="readonly" style="width: 150px; border-radius: 5px !important"
													placeholder="结束时间" title="结束时间" />
													</span> 
													<span class="input-icon" style="width: 10px;"> </span> 
													 
												<c:if test="${QX.cha == 1 }">
													<span class="input-icon" style="width: 10px;"> </span>
													<span> <a class="btn btn-light btn-xs blue" onclick="tosearch(1);" title="搜索"
														style="border-radius: 5px; color: blue !important; width: 50px">搜索</a>
													</span>
													<span> <a class="btn btn-light btn-xs blue" onclick="tosearch(0);" title="清空"
														style="border-radius: 5px; color: blue !important; width: 50px">清空</a>
													</span>
												</c:if>
												 
											</div>
										</td>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center">时间</th>
											<th class="center">用户ID</th>
											<th class="center">用户姓名</th>
											<th class="center">操作类型</th>
											<th class="center">操作菜单</th>
											<th class="center">操作内容</th>
											<th class="center">操作结果</th>
											<th class="center">操作IP</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
															<td class='center'>${DateUtil.toSDFTime(var.action_time*1000)}</td>
															<td class='center'>${var.user_id}</td>
															<td class='center'>${var.user_name}</td>
															<td class='center'>
																<c:if test="${var.action_type == 0 }">
																	<p class="text-primary">修改</p>
																</c:if> 
																<c:if test="${var.action_type == 1 }">
																	<p class="text-success">添加</p>
																</c:if> 
																<c:if test="${var.action_type == 2 }">
																	<p class="text-danger">删除</p>
																</c:if>
															</td>
															<td class='center'>
																<c:if test="${var.detailObj != var.action_object }">
																	<a href="javascript:detail('${var.detailObj }')">${var.action_object }</a>
																</c:if> 
																<c:if test="${var.detailObj == var.action_object }">
																	${var.action_object }
																</c:if>
															</td>
															<td class='center'>
																<c:if test="${var.detail != var.action_text }">
																	<a href="javascript:detail('${var.detail }')">${var.action_text }</a>
																</c:if> 
																<c:if test="${var.detail == var.action_text }">
																	${var.action_text }
																</c:if>
															</td>
															<td class='center'>
																<c:if test="${var.action_status == 0 }">失败</c:if> 
																<c:if test="${var.action_status == 1 }">成功</c:if>
															</td>
															<td class='center'>${var.action_ip}</td>

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
									<table style="width: 100%;">

										<tr>
											<td style="vertical-align: top;"><div class="pagination"
													style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
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
				$("#action_type").val("");
				$("#lastStart").val("");
				$("#lastEnd").val("");
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

			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on(
					'click',
					function() {
						var th_checked = this.checked;//checkbox inside "TH" table header
						$(this).closest('table').find('tbody > tr').each(
								function() {
									var row = this;
									if (th_checked)
										$(row).addClass(active_class).find(
												'input[type=checkbox]').eq(0)
												.prop('checked', true);
									else
										$(row).removeClass(active_class).find(
												'input[type=checkbox]').eq(0)
												.prop('checked', false);
								});
					});
		});

		function detail(del) {
			bootbox.alert(del);
		}
	</script>


</body>
</html>