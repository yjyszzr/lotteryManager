<%@page import="com.fh.common.PurchaseConstants"%>
<%@page import="com.fh.util.DateUtil"%>
<%@page import="com.fh.common.TextConfig"%>
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
						<form action="queryotherinstorage/list.do" method="post" name="Form" id="Form">
						<!-- 操作类型采购、审批等 -->
						<input type="hidden" name="bsType" value="${pd.bsType}">
						<%--采购单类型 标准和生产 --%>
						<input type="hidden" name="business_type" value="${pd.business_type}">
						<table class="pgt-query-form-table">
							<tr>
							 <td class="pgt-query-form-td">
									其他入库编号：<input type="text" placeholder="其他入库编号" id="other_instorage_code" autocomplete="off" name="other_instorage_code" value="${pd.other_instorage_code }" />
								</td>
								<td class="pgt-query-form-td">
									仓库列表：
								 	<select name="stores" id="stores" data-placeholder="仓库列表" style="width: 150px;">
								 		<option value="">请选择仓库</option>
										<c:if test="${not empty storeList}">
											<c:forEach items="${storeList}" var="var" varStatus="vs">
									         	<c:choose>
													<c:when test="${pd.store_sn==var.store_sn}">
													    <option selected="selected" value="${var.store_sn}">${var.store_name}</option>
													</c:when>
													<c:otherwise>
														<option value="${var.store_sn}">${var.store_name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
								  	</select>
							    </td>
							   
								<td class="pgt-query-form-td">
									状态：
								 	<select name="status" id="status" data-placeholder="状态" value="${pd.status }" style="width: 120px;">
								 	<option value="">全部状态</option>
									<option value="${PurchaseConstants.BUSINESS_STATUS_NO_COMMIT}" ${PurchaseConstants.BUSINESS_STATUS_NO_COMMIT==pd.status and pd.status!="" ?"selected":""}>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_NO_COMMIT)}</option>
									<option value="${PurchaseConstants.BUSINESS_STATUS_CREATE}" ${PurchaseConstants.BUSINESS_STATUS_CREATE==pd.status?"selected":"" }>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_CREATE)}</option>
									
									<option value="${PurchaseConstants.BUSINESS_STATUS_AGREE}"  ${PurchaseConstants.BUSINESS_STATUS_AGREE==pd.status?"selected":"" }>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_AGREE)}</option>
									<option value="${PurchaseConstants.BUSINESS_STATUS_REJECT}"  ${PurchaseConstants.BUSINESS_STATUS_REJECT==pd.status?"selected":"" }>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_REJECT)}</option>
									<option value="${PurchaseConstants.BUSINESS_STATUS_COMPLETE}" ${PurchaseConstants.BUSINESS_STATUS_COMPLETE==pd.status?"selected":"" }>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_COMPLETE)}</option>
								  	</select>
								</td>
							    
								<c:if test="${QX.cha == 1 }">
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="查询">查询</a></td>
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="doResetForm();"  title="清空">清空</a></td>
								</c:if>
								<%-- <c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if> --%>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<!-- <th class="center">ID</th> -->
									<th class="center">其他入库编号</th>
									<th class="center">仓库编号</th>
									<th class="center">仓库名称</th>
									<th class="center">预计入库时间</th>
									<th class="center">状态</th>
									<th class="center">提交人</th>
									<th class="center">提交时间</th>
									<th class="center">审批人</th>
									<th class="center">审批时间</th>
<!-- 									<th class="center">备注</th>
 -->									<!-- <th class="center">审核人</th>
									<th class="center">审核时间</th> -->
									<!-- <th class="center">创建人</th>
									<th class="center">创建时间</th>
									<th class="center">更新人</th>
									<th class="center">更新时间</th> -->
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.other_instorage_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<%-- <td class='center'>${var.other_instorage_id}</td> --%>
											<td class='center'>${var.other_instorage_code}</td>
											<td class='center'>${var.store_sn}</td>
											<td class='center'>${var.store_name}</td>
											<td class='center'>${DateUtil.toDateStr(var.pre_arrival_time)}</td>
											<td class='center'>${TextConfig.getBusinessStatus(var.status)}</td>
											<td class='center'>${var.commitby}</td>
											<td class='center'>${var.commit_time==null?"":DateUtil.toSdfTimeStr(var.commit_time)}</td>
											<td class='center'>${var.auditby}</td>
											<td class='center'>${var.audit_time==null?"":DateUtil.toSdfTimeStr(var.audit_time)}</td>
<%-- 											<td class='center'>${var.remark}</td>
 --%>											<%-- <td class='center'>${var.auditby}</td>
											<td class='center'>${var.audit_time}</td> --%>
											<%-- <td class='center'>${var.createby}</td>
											<td class='center'>${var.create_time}</td>
											<td class='center'>${var.updateby}</td>
											<td class='center'>${var.update_time}</td> --%>
											<td class="center">
											
											
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<a class="btn btn-xs btn-success" onclick="detail('${var.other_instorage_code}');">
														明细详情
													</a>
													<a class="btn btn-xs btn-success" onclick="bound('${var.other_instorage_code}');">
														仓库记录
													</a>
												</div>	
											

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
		//修改
		function detail(code){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="报损单详情";
		    diag.URL = '<%=basePath%>queryotherinstorage/detail.do?other_instorage_code='+code;
 			 diag.Width = $(window).width();
			 diag.Height = $(window).height();
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}

		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>scrap/excel.do';
		}
		
		function bound(sn){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="出入库记录";
			 diag.URL = '<%=basePath%>queryallocation/bound.do?querytype=other_instorage_code&purchase_code='+sn;
			 diag.Width = 1250;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
	</script>


</body>
</html>