<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						<form action="querydefectivegoods/detail.do" method="post" name="Form" id="Form">
							<input type="hidden" id="defective_code" name="defective_code" value="${pd.defective_code}" />
<table class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
							<tr>
								<th class="center">不良品单号:</th>
								<th class="center">${pd.defective_code}</th>
								<th class="center">仓库编码：</th>
								<th class="center">${pd.store_sn}</th>
								<th class="center">仓库名称</th>
								<th class="center">${pd.store_name}</th>
								<th class="center">状态</th>
								<th class="center">
									<c:if test="${pd.status == 0}">待提交 </c:if>
									<c:if test="${pd.status == 1}">待审 </c:if>
									<c:if test="${pd.status == 2}">通过 </c:if>
									<c:if test="${pd.status == 8}">驳回 </c:if>
									<c:if test="${pd.status == 9}">已完成 </c:if>
								</th>
							</tr>
							<tr>
								<th class="center">提交人:</th>
								<th class="center">${pd.commitby}</th>
								<th class="center">提交时间:</th>
								<th class="center"><fmt:formatDate value="${pd.commit_time}" pattern="yyyy-MM-dd"/></th>
								<th class="center">审批人:</th>
								<th class="center">${pd.auditby}</th>
								<th class="center">审批时间:</th>
								<th class="center"><fmt:formatDate value="${pd.audit_time}" pattern="yyyy-MM-dd"/></th>
							</tr>
							<tr>
								<th class="center">提报说明:</th>
								<th  style="text-align:left;word-wrap:break-word;word-break:break-all;"  colspan=7>${pd.remark}</th>
							</tr>
						<!-- 检索  -->
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>

									<th class="center" style="width:50px;">序号</th>
									<th class="center">物料编码</th>
									<th class="center">物料名称</th>
									<th class="center">批次号</th>
									<th class="center">物料单位</th>
									<th class="center">物料规格</th>
									<th class="center">不良品数量</th>
									<th class="center">不良品说明</th>
									
								<!--<th class="center">备注8</th>
									<th class="center">备注9</th>
									<th class="center">备注10</th>
									<th class="center">备注11</th>
									<th class="center">备注12</th>
									<th class="center">备注13</th>
									<th class="center">备注14</th>
									<th class="center">备注15</th>
									<th class="center">操作</th> -->
								</tr>
							</thead>
													
							<tbody>
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
											<td class='center'>${var.defective_quantity}</td>
											<td class='center'>${var.remark}</td>
											
										<%--<td class='center'>${var.defective_detail_id}</td>
											<td class='center'>${var.defective_code}</td>
											<td class='center'>${var.sku_id}</td>
											<td class='center'>${var.status}</td>
											<td class='center'>${var.createby}</td>
											<td class='center'>${var.create_time}</td>
											<td class='center'>${var.updateby}</td>
											<td class='center'>${var.update_time}</td>
 											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.defective_detail_id}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" onclick="del('${var.defective_detail_id}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
													</c:if>
												</div>
											</td> --%>
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