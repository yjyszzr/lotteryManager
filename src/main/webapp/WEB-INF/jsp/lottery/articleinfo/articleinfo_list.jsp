<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.fh.util.DateUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
						<form action="articleinfo/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												内容:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="内容" class="nav-search-input" id="content" autocomplete="off" name="content" value="${pd.content }"   />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												来源:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="来源" class="nav-search-input" id="source" autocomplete="off" name="source" value="${pd.source}" />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												标题:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="标题" class="nav-search-input" id="title" autocomplete="off" name="title" value="${pd.title}" />
											</span>
										</div>
									</td>
									<c:if test="${QX.cha == 1 }">
										<td style="vertical-align:top;padding-left:2px">
											<span class="input-icon" style="width:80px;"> </span>
											<span>
													<a class="btn btn-light btn-xs blue" onclick="tosearch(1);"  title="搜索"  style="border-radius:5px;color:blue !important; width:50px">搜索</a>
											</span>
											<span class="input-icon" style="width:43px;"> </span>
											<span>
													<a class="btn btn-light btn-xs blue" onclick="tosearch(0);"  title="清空"  style="border-radius:5px;color:blue !important; width:50px">清空</a>
											</span>
										</td>
									</c:if>
								</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
<!-- 									<th class="center" style="width:35px;"> -->
<!-- 									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label> -->
<!-- 									</th> -->
									<th class="center" style="width:50px;">序号</th>
<!-- 									<th class="center">id</th> -->
									<th class="center">标题</th>
									<th class="center">内容</th>
									<th class="center">来源</th>
									<th class="center">发布日期</th>
									<th class="center">抓取时间</th>
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
<!-- 											<td class='center'> -->
<%-- 												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.article_info_id}" class="ace" /><span class="lbl"></span></label> --%>
<!-- 											</td> -->
											<td class='center' style="width: 30px;">${vs.index+1}</td>
												<c:choose>
												<c:when test="${fn:length(var.title)  <= 20 }">
													<td  title="${var.title}"><a style="cursor:pointer;">${var.title}</a></td>
												</c:when>
												<c:otherwise>
													<td title="${var.title}"> <a style="cursor:pointer;">${fn:substring(var.title,0,20)}... </a></td>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${fn:length(var.contentSub)  <= 40}">
													<td><a style="cursor:pointer;">${var.contentSub}</a></td>
												</c:when>
												<c:otherwise>
													<td title="${var.contentSub}"><a style="cursor:pointer;">${fn:substring(var.contentSub,0,40)}...</a></td>
												</c:otherwise>
											</c:choose>
<%-- 											<td class='center'>${var.id}</td> --%>
											<td class='center'>${var.source}</td>
											<td class='center'>${var.date}</td>
<%-- 											<td class='center'>${var.content}</td> --%>
<%-- 											<td class='center'>${var.title}</td> --%>
											<td class='center'>${DateUtil.toSDFTime(var.create_time*1000)}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<c:choose>
														<c:when test="${var.status == 0 || empty var.status}">
															<a class="btn btn-xs btn-success" title="编辑"   style="border-radius: 5px;"   onclick="edit('${var.id}');">编辑</a>
														</c:when>
														<c:when test="${var.status == 1 }">--</c:when>
													</c:choose>
												
													</c:if>
<%-- 													<c:if test="${QX.del == 1 }"> --%>
<%-- 													<a class="btn btn-xs btn-danger" onclick="del('${var.article_info_id}');"> --%>
<!-- 														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i> -->
<!-- 													</a> -->
<%-- 													</c:if> --%>
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
								<td style="vertical-align:top;">
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
			if(status==0){
				$("#title").val("");
				$("#content").val("");
				$("#source").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>articleinfo/goEdit.do?id='+Id;
			 diag.Width = 800;
			 diag.Height = 800;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		</script>
	</body>
</html>