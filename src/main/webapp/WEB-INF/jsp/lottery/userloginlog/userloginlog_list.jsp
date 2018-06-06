<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.fh.util.DateUtil"%>
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
						<form action="userloginlog/list.do" method="post" name="Form" id="Form">
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
									<th class="center">用户ID</th>
									<th class="center">用户名</th>
									<th class="center">手机</th>
<!-- 									<th class="center">登录类型</th> -->
									<th class="center">登录IP</th>
									<th class="center">登录时间</th>
									<th class="center">退出时间</th>
<!-- 									<th class="center">登录设备</th> -->
<!-- 									<th class="center">手机厂商</th> -->
									<th class="center">手机型号/设备型号</th>
									<th class="center">手机系统版本号</th>
									<th class="center">登录状态</th>
<!-- 									<th class="center">宽</th> -->
<!-- 									<th class="center">高</th> -->
<!-- 									<th class="center">手机串号</th> -->
<!-- 									<th class="center">登录来源</th> -->
<!-- 									<th class="center">登录参数</th> -->
<!-- 									<th class="center">用户设备渠道</th> -->
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>${var.user_id}</td>
											<td class='center'>${var.user_name}</td>
											<td class='center'>${var.mobile}</td>
<%-- 											<td class='center'>${var.login_type}</td> --%>
											<td class='center'>${var.login_ip}</td>
											<td class='center'>${DateUtil.toSDFTime(var.login_time*1000)}</td>
											<td class='center'>${DateUtil.toSDFTime(var.logout_time*1000)}</td>
<%-- 											<td class='center'>${var.plat}</td> --%>
<%-- 											<td class='center'>${var.brand}</td> --%>
											<td class='center'>
												<c:choose>
													<c:when test="${var.plat == 'h5'}">h5</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${var.mid == 'iPhone1,1'}">iPhone 2G</c:when>
															<c:when test="${var.mid == 'iPhone1,2'}">iPhone 3G</c:when>
															<c:when test="${var.mid == 'iPhone2,1'}">iPhone 3GS</c:when>
															<c:when test="${var.mid == 'iPhone3,1' || var.mid == 'iPhone3,2' || var.mid == 'iPhone3,3'}">iPhone 4</c:when>
															<c:when test="${var.mid == 'iPhone4,1'}">iPhone 4s</c:when>
															<c:when test="${var.mid == 'iPhone5,1' || var.mid == 'iPhone5,2'}">iPhone 5</c:when>
															<c:when test="${var.mid == 'iPhone5,3' || var.mid == 'iPhone5,4'}">iPhone 5c</c:when>
															<c:when test="${var.mid == 'iPhone6,1' || var.mid == 'iPhone6,2'}">iPhone 5s</c:when>
															<c:when test="${var.mid == 'iPhone7,1'}">iPhone 6 Plus</c:when>
															<c:when test="${var.mid == 'iPhone7,2'}">iPhone 6</c:when>
															<c:when test="${var.mid == 'iPhone8,1'}">iPhone 6s</c:when>
															<c:when test="${var.mid == 'iPhone8,2'}">iPhone 6s Plus</c:when>
															<c:when test="${var.mid == 'iPhone8,4'}">iPhone SE</c:when>
															<c:when test="${var.mid == 'iPhone9,1'}">iPhone 7</c:when>
															<c:when test="${var.mid == 'iPhone9,2'}">iPhone 7 Plus</c:when>
															<c:when test="${var.mid == 'iPhone10,1'}">iPhone 8</c:when>
															<c:when test="${var.mid == 'iPhone10,2'}">iPhone 8 Plus</c:when>
															<c:when test="${var.mid == 'iPhone10,3'}">iPhone X</c:when>
															<c:otherwise>${var.mid}</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											
											
											</td>
											<td class='center'>
											<c:choose>
												<c:when test="${var.plat == 'android'}">
													<c:choose>
														<c:when test="${var.os == 19}">Android 4.4 (KitKat)</c:when>
														<c:when test="${var.os == 20}">Android 4.4W (KitKat Wear)</c:when>
														<c:when test="${var.os == 21}">Android 5.0 (Lollipop)</c:when>
														<c:when test="${var.os == 22}">Android 5.1 (Lollipop)</c:when>
														<c:when test="${var.os == 23}">Android 6.0 (Marshmallow)</c:when>
														<c:when test="${var.os == 24}">Android 7.0 (Nougat)</c:when>
														<c:when test="${var.os == 25}">Android 7.1.1 (Nougat)</c:when>
														<c:when test="${var.os == 26}">Android 8.0 (Oreo)</c:when>
														<c:when test="${var.os == 27}">Android 8.0 (Oreo)</c:when>
													</c:choose>
												</c:when>
												<c:when test="${var.plat == 'iphone'}">	iOS ${var.os}</c:when>
												<c:when test="${var.plat == 'h5'}">h5</c:when>
											</c:choose>
										</td>
<%-- 											<td class='center'>${var.w}</td> --%>
<%-- 											<td class='center'>${var.h}</td> --%>
<%-- 											<td class='center'>${var.imei}</td> --%>
<%-- 											<td class='center'>${var.login_source}</td> --%>
<%-- 											<td class='center'>${var.login_params}</td> --%>
<%-- 											<td class='center'>${var.device_channel}</td> --%>
											<td class='center'>
												<c:choose>
													<c:when test="${var.login_status == 0}">登录成功</c:when>
													<c:when test="${var.login_status == 1}"><label style = "color:red">登录失败</label></c:when>
												</c:choose>
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