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
						<form action="worldcupgj/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr style="margin:2px">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												国家队:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="国家队" class="nav-search-input" id="contry_name" autocomplete="off" name="contry_name" value="${pd.contry_name}" />
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
									<c:if test="${QX.edit == 1 }">
										<td style="vertical-align:top;padding-left:2px">
											<span class="input-icon" style="width:80px;"> </span>
											<span>
													<a class="btn btn-light btn-xs blue" onclick="updateSellStatus('-1','0');"  title="一键开售"  style="border-radius:5px;color:blue !important; width:75px">一键开售</a>
											</span>
											<span class="input-icon" style="width:43px;"> </span>
											<span>
													<a class="btn btn-light btn-xs" onclick="updateSellStatus('-1','1');"  title="一键停售"  style="border-radius:5px;color:blue !important; width:75px">一键停售</a>
											</span>
										</td>
									</c:if>
								</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">国家队名称</th>
									<th class="center">竞彩网当前销售状态</th>
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
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td style= "padding-left:560px; padding-top:10px; "><img src="${var.contry_pic}" style="width:30px;hight:30px"  alt="${var.contry_name}"/><span style="margin:15px;font-size:120%">${var.contry_name}</span></td>
											<td class='center'> 
												<c:choose>
														<c:when test="${var.bet_status == 0 }">开售</c:when>
														<c:when test="${var.bet_status == 1 }">停售</c:when>
												</c:choose>
											</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
												<c:if test="${QX.edit == 1 }">
														<c:choose>
															<c:when test="${empty var.sell || var.sell== 0 }"><a  onclick="updateSellStatus('${var.id}','1');" style="border-radius: 5px;cursor:pointer;" class="btn btn-xs btn-warning" title="停售"   >停售</a></c:when>
															<c:when test="${var.sell == 1 }"><a  onclick="updateSellStatus('${var.id}','0');" style="border-radius: 5px;cursor:pointer;" class="btn btn-xs btn-blue" title="开售"   >开售</a></c:when>
														</c:choose>
													</c:if>
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
				$("#contry_name").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		
		
		function updateSellStatus(Id,status){
				var str = "";
			if(status == 1){
				str = "确定要停售吗?";
			}else{
				str = "确定要开售吗?";
			}
			bootbox.confirm(str, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>worldcupgj/updateSellStatus.do?id="+Id+"&sell="+status;
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
	</script>
</body>
</html>