<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.fh.common.ScrapConstants"%>
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
						<form action="queryscrap/list.do" method="post" name="Form" id="Form">
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
									报损单号：<input type="text" placeholder="这里输入报损单号" id="scrap_code" autocomplete="off" name="scrap_code" value="${pd.scrap_code }" placeholder="这里输入报损单号"/>
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
								 	<select name="status" id="id" data-placeholder="请选择" style="width: 120px;">
									<option value="">全部</option>
									<option value="${ScrapConstants.BUSINESS_STATUS_UNSUBMIT}">未提交</option>
									<option value="${ScrapConstants.BUSINESS_STATUS_CREATE}">待鉴定</option>
									<option value="${ScrapConstants.BUSINESS_STATUS_AUDIT}">待审批</option>
									<option value="${ScrapConstants.BUSINESS_STATUS_AGREE}">通过</option>
									<option value="${ScrapConstants.BUSINESS_STATUS_REJECT}">驳回</option>
									<option value="${ScrapConstants.BUSINESS_STATUS_COMPLETE}">完成</option>
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
									<th class="center">报损单号</th>
									<th class="center">仓库编码</th>
									<th class="center">仓库名称</th>
									<th class="center">状态</th>
									<th class="center">提交人</th>
									<th class="center">提交时间</th>
									<th class="center">鉴定人</th>
									<th class="center">鉴定时间</th>
									<th class="center">审核人</th>
									<th class="center">审核时间</th>
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.scrap_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.scrap_code}</td>
											<td class='center'>${var.store_sn}</td>
											<td class='center'>${var.store_name}</td>
											<td class='center'>
											<c:choose>
											<c:when test="${var.status==ScrapConstants.BUSINESS_STATUS_UNSUBMIT}">未提交</c:when>
											<c:when test="${var.status==ScrapConstants.BUSINESS_STATUS_CREATE}">待鉴定</c:when>
											<c:when test="${var.status==ScrapConstants.BUSINESS_STATUS_AUDIT}">待审核</c:when>
											<c:when test="${var.status==ScrapConstants.BUSINESS_STATUS_AGREE}">通过</c:when>
											<c:when test="${var.status==ScrapConstants.BUSINESS_STATUS_REJECT}">驳回</c:when>
											<c:when test="${var.status==ScrapConstants.BUSINESS_STATUS_COMPLETE}">完成</c:when>
											</c:choose>
											</td>
											<td class='center'>${var.commitby}</td>
											<td class='center'>
											<fmt:formatDate value="${var.commit_time}" pattern="yyyy-MM-dd"/></td>
											<td class='center'>${var.auditby}</td>
											<td class='center'>
											<fmt:formatDate value="${var.audit_time}" pattern="yyyy-MM-dd"/></td>
											<td class='center'>${var.verifyby}</td>
											<td class='center'>
											<fmt:formatDate value="${var.verify_time}" pattern="yyyy-MM-dd"/></td>
											<td class="center">
												<div class="hidden-sm hidden-xs btn-group">
													<a class="btn btn-xs btn-success" onclick="detail('${var.scrap_code}');">
														明细详情
													</a>
													<a class="btn btn-xs btn-success" onclick="bound('${var.scrap_code}');">
														仓库记录
													</a>
												</div>												
											</td>
										</tr>									
									</c:forEach>
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
								<td style="vertical-align:top;"><a class="btn btn-mini btn-success" onclick="printscrap();">打印报损单</a></td>
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
		function detail(scrap_code){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="报损单详情";
		    diag.URL = '<%=basePath%>queryscrap/detail.do?scrap_code='+scrap_code;
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
			
		//复选框全选控制
		var active_class = 'active';
		$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
			var th_checked = this.checked;//checkbox inside "TH" table header
			$(this).closest('table').find('tbody > tr').each(function(){
				var row = this;
				if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
				else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
			});
		});
		
			//报损单打印
			function printscrap(){
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				 if(document.getElementsByName('ids')[i].checked)
					 {
					 if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					 }
			}
			if(str=='')
				{
				   alert(" 还未选择任何内容 ");
				    return;
				}
			
			var url = "<%=basePath%>queryscrap/printscrap.do?sn="+str;
			window.open(url, "", 'left=250,top=150,width=1000,height=700,toolbar=no,menubar=no,status=no,scrollbars=yes,resizable=yes');
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
			 diag.URL = '<%=basePath%>queryallocation/bound.do?querytype=scrap&purchase_code='+sn;
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