<%@page import="com.fh.util.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
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
						<form action="worldcupmanager/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;display:none">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="">1</option>
									<option value="">2</option>
								  	</select>
								</td>
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if>
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
									<th class="center">奖项</th>
									<th class="center">额度</th>
									<th class="center">人数</th>
									<th class="center">平均奖金</th>
									<th class="center">审核时间</th>
									<th class="center">审核人</th>
<!-- 									<th class="center">审核人Id</th> -->
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
<%-- 												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label> --%>
<!-- 											</td> -->
											<td class='center' style="width: 30px;">${vs.index+1}</td>
<%-- 											<td class='center'>${var.id}</td> --%>
											<td class='center'>${var.prize}</td>
											<td class='center'>${var.quota}元</td>
											<td class='center'>${var.people_num}</td>
											<td class='center'>${var.average}</td>
											<td class='center'>
												<c:choose>
													<c:when test="${empty var.audit_time}">--</c:when>
													<c:otherwise>${DateUtil.toSDFTime(var.audit_time*1000)}</c:otherwise>
												</c:choose>
											</td>
											<td class='center'>
												<c:choose>
													<c:when test="${empty var.auditor}">--</c:when>
													<c:otherwise>${var.auditor}</c:otherwise>
												</c:choose>
											</td>
<%-- 											<td class='center'>${var.auditor_id}</td> --%>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
														<c:choose>
															<c:when test="${var.status == '0'}">
																<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.id}');" style="border-radius: 5px;cursor:pointer;" >编辑</a>
															</c:when>
															<c:when test="${var.status == '1'}">
																<a class="btn btn-xs btn-danger" title="派奖" onclick="edit('${var.id}');" style="border-radius: 5px;cursor:pointer;" >派奖</a>
															</c:when>
															<c:when test="${var.status == '2'}">
																已派奖
															</c:when>
															<c:when test="${var.status == '-1'}">
																<a class="btn btn-xs btn-orange" title="开奖" onclick="openThePrize('${var.id}');" style="border-radius: 5px;cursor:pointer;" >开奖</a>
															</c:when>
														</c:choose>
													</c:if>
<%-- 													<c:if test="${QX.del == 1 }"> --%>
<%-- 													<a class="btn btn-xs btn-danger" onclick="del('${var.id}');"> --%>
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
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
		<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
	
		//关闭加载状态
		$(top.hangge());
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>worldcupmanager/goEdit.do?id='+Id;
			 diag.Width = 450;
			 diag.Height = 265;
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
		
		
		//开奖
		function openThePrize(Id){
			var status = 0;
			bootbox.confirm("确定要开奖吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>worldcupmanager/openThePrize.do?id="+Id+"&status="+ status;
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
	</script>
</body>
</html>