<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.fh.common.ScrapConstants" %>
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
							<input type="hidden" name="store_id" id="store_id" value="${pd.store_id }">
						<!-- 检索  -->
						<form action="scrapdetail/list.do?scrap_code=${pd.scrap_code }+&scrap_id=${pd.scrap_id}+&store_id=${pd.store_id}+&option=${option}" method="post" name="Form" id="Form">
						<table class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
							<tr>
								<th class="center">报损单号:</th>
								<th class="center">${pd.scrap_code}</th>
								<th class="center">仓库编码：</th>
								<th class="center">${pd.store_sn}</th>
								<th class="center">仓库名称</th>
								<th class="center">${pd.store_name}</th>
								<th class="center">状态</th>
								<th class="center">
									<c:if test="${pd.status == 0}">待提交 </c:if>
									<c:if test="${pd.status == 1}">待鉴定 </c:if>
									<c:if test="${pd.status == 2}">待审批 </c:if>
									<c:if test="${pd.status == 3}">通过 </c:if>
									<c:if test="${pd.status == 8}">驳回 </c:if>
									<c:if test="${pd.status == 9}">已完成 </c:if>
								</th>
							</tr>
							<tr>
								<th class="center">提交人:</th>
								<th class="center">${pd.commitby}</th>
								<th class="center">提交时间:</th>
								<th class="center"><fmt:formatDate value="${pd.commit_time}" pattern="yyyy-MM-dd"/></th>
								<th class="center">鉴定人:</th>
								<th class="center">${pd.auditby}</th>
								<th class="center">鉴定时间:</th>
								<th class="center"><fmt:formatDate value="${pd.audit_time}" pattern="yyyy-MM-dd"/></th>
							</tr>
							<tr>
								<th class="center">审批人:</th>
								<th class="center">${pd.verifyby}</th>
								<th class="center">审批时间:</th>
								<th class="center"><fmt:formatDate value="${pd.verify_time}" pattern="yyyy-MM-dd"/></th>
								<th class="center"></th>
								<th class="center"></th>
								<th class="center"></th>
								<th class="center"></th>
							</tr>
							<tr>
								<th class="center">报损说明:</th>
								<th  style="text-align:left;word-wrap:break-word;word-break:break-all;"  colspan=7>${pd.remark}</th>
							</tr>
							
						</table>
								<table id="simple-table"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center" style="width: 50px;">序号</th>
											<th class="center">物料编码</th>
											<th class="center">物料名称</th>
											<th class="center">批次号</th>
											<th class="center">物料单位</th>
											<th class="center">物料规格</th>
											<th class="center">核准状态</th>
											<th class="center">报损数量</th>
											<th class="center">核准数量</th>
											<th class="center">核准人</th>
											<th class="center">核准时间</th>
											<!-- <th class="center">单位</th> -->
											<th class="center">报损说明</th>
											<th class="center">处理建议类型</th>
											<th class="center">处理建议说明</th>
										</tr>
									</thead>

									<tbody id="tbody2">
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
														
															<td class='center' style="width: 30px;">${vs.index+1}</td>
															<td class='center'>${var.sku_encode}</td>
															<td class='center'>${var.sku_name}</td>
															<td class='center'>${var.batch_code}</td>
															<td class='center'>${var.unit}</td>
															<td class='center'>${var.spec}</td>
															<td class='center'><c:if test="${var.status==0 }">待审</c:if>
																<c:if test="${var.status==1 }">核准完成</c:if></td>
															<td id="sq_${vs.index}" class='center'>${var.scrap_quantity}</td>
															<td id="aq_${vs.index}" sta_="${var.status}" class='center'>${var.audit_quantity}</td>
															<td class='center'>${var.auditby}</td>
															<td class='center'>
															<fmt:formatDate value="${var.audit_time}" pattern="yyyy-MM-dd"/></td>
															<%-- <td class='center'>${var.unit}</td> --%>
															<td class='center'>${var.remark}</td>
															<td class='center'><c:if
																	test="${var.deal_suggest_type==ScrapConstants.DEAL_SUGGEST_TYP_0 }">可用</c:if>
																<c:if
																	test="${var.deal_suggest_type==ScrapConstants.DEAL_SUGGEST_TYP_1 }">不可用</c:if>
																<c:if
																	test="${var.deal_suggest_type==ScrapConstants.DEAL_SUGGEST_TYP_2 }">其它</c:if>
															</td>
															<td class='center'>${var.deal_suggest_des}</td>
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
								<c:if test="${option == 'add' }">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add('${pd.scrap_code}', '${pd.store_id }');">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if>
								</c:if>
								<c:if test="${option == 'check' }">
									<a class="btn btn-mini btn-success" onclick="complete('${pd.scrap_id}');">完成</a>
								</c:if>
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
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
	</script>


</body>
</html>