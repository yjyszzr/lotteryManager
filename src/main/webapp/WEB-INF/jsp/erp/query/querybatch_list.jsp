<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.fh.common.ScrapConstants"%>
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

							<!-- 检索  -->
							<form action="querybatch/list.do" method="post" name="Form"
								id="Form">
								<table class="pgt-query-form-table">
									<tr>
										<td class="pgt-query-form-td">批次编号：<input type="text"
											placeholder="批次编号" id="batch_code" autocomplete="off"
											name="batch_code" value="${batch_code}"
											placeholder="这里输入批次编号" />
										</td>
										<c:if test="${QX.cha == 1 }">
											<td class="pgt-query-form-td"><a
												class="btn btn-light btn-xs" onclick="tosearch();"
												title="查询">查询</a></td>
											<td class="pgt-query-form-td"><a
												class="btn btn-light btn-xs" onclick="doResetForm();"
												title="清空">清空</a></td>
											<c:if test="${empty pd}">
												<td class="pgt-query-form-td" >
												
													<font size="3" color="red">没有相关数据!</font>

												</td>
											</c:if>
										</c:if>
									</tr>
									
								</table>
							<c:if test="${not empty pd && pd.first != 1}">
								<table class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<tr>
										<th class="center">采购单编号:</th>
										<th class="center">${pd.purchase_code}</th>
										<th class="center">供应商：</th>
										<th class="center">${pd.supplier_name}</th>
										<th class="center">采购组:</th>
										<th class="center">${pd.purchase_group_name}</th>
										<th class="center">采购日期:</th>
										<th class="center">
										<fmt:formatDate
												value="${pd.purchase_date}" pattern="yyyy-MM-dd" />
										</th>
									</tr>
									<tr>
										<th class="center">提交人:</th>
										<th class="center">${pd.commitby}</th>
										<th class="center">提交时间:</th>
										<th class="center"><fmt:formatDate value="${pd.commit_time}" pattern="yyyy-MM-dd" /></th>
										<th class="center">审批人:</th>
										<th class="center">${pd.auditby}</th>
										<th class="center">审批时间:</th>
										<th class="center"><fmt:formatDate
												value="${pd.audit_time}" pattern="yyyy-MM-dd" /></th>
									</tr>
									<tr>
										<th class="center">发货通知单编码 :</th>
										<th class="center">${pd.inbound_pre_notice_code}</th>
										<th class="center">发货仓库编码:</th>
										<th class="center">${pd.store_sn}</th>
										<th class="center">发货仓库名称:</th>
										<th class="center">${pd.store_name}</th>
										<th class="center">预计到货时间:</th>
										<th class="center"><fmt:formatDate
												value="${pd.pre_arrival_time}" pattern="yyyy-MM-dd" /></th>
									</tr>
									<tr>
										<th class="center">发货提交人:</th>
										<th class="center">${pd.notice_commitby}</th>
										<th class="center">发货提交时间:</th>
										<th class="center"><fmt:formatDate
												value="${pd.notice_commit_time}" pattern="yyyy-MM-dd" /></th>
										<th class="center">发货审批人:</th>
										<th class="center">${pd.notice_auditby}</th>
										<th class="center">发货审批时间:</th>
										<th class="center"><fmt:formatDate
												value="${pd.notice_audit_time}" pattern="yyyy-MM-dd" /></th>
									</tr>

								</table>
								<table id="simple-table"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center" style="width: 50px;">序号</th>
											<th class="center">物料编码</th>
											<th class="center">物料名称</th>
											<th class="center">物料批次</th>
											<th class="center">质检总数</th>
											<th class="center">良品数</th>
											<th class="center">不良品数</th>
											<th class="center">报损数</th>
											<th class="center">处理类型</th>
											<th class="center">质检员</th>
											<th class="center">质检时间</th>
										</tr>
									</thead>

									<tbody id="tbody2">
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty checkList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${checkList}" var="var" varStatus="vs">
														<tr>
															<td class='center' style="width: 30px;">${vs.index+1}</td>
															<td class='center'>${var.sku_encode}</td>
															<td class='center'>${var.sku_name}</td>
															<td class='center'>${var.batch_code}</td>
															<td class='center'>${var.total_quantity}</td>
															<td class='center'>${var.good_quantity}</td>
															<td class='center'>${var.bad_quantity}</td>
															<td class='center'>${var.scrap_quantity}</td>
															<td class='center'>
																<c:if test="${var.bad_deal_type==0}">
																	退回供应商
																</c:if>
																<c:if test="${var.bad_deal_type==1}">
																	入不良品库
																</c:if>
															</td>
															<td class='center'>${var.updateby}</td>
															<td class='center'>${var.commit_time}</td>
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

						<table id="simple-table2" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">操作类型</th>
									<th class="center">入出库单号</th>
									<th class="center">入出库通知单号</th>
									<th class="center">入出库类型</th>
									<th class="center">业务单号</th>
									<th class="center">仓库编号</th>
									<th class="center">仓库名称</th>
									<th class="center">操作数量</th>
									<th class="center">操作人员</th>
									<th class="center">操作时间</th>
									
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty boundList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${boundList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>
											<c:if test="${var.io_type == 1 }">入库</c:if>
											<c:if test="${var.io_type == 0 }">出库</c:if>
											</td>
											<td class='center'>${var.code}</td>
											<td class='center'>${var.notice_code}</td>
											<td class='center'>${var.type}</td>
											<td class='center'>${var.purchase_code}</td>
											<td class='center'>${var.store_sn}</td>
											<td class='center'>${var.store_name}</td>
											<td class='center'>${var.quantity}</td>
											<td class='center'>${var.createby}</td>
											<td class='center'>${var.create_time}</td>
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
						</c:if>
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
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
	</script>


</body>
</html>