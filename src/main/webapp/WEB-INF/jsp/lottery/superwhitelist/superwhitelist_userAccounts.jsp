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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
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
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">订单编号</th>
									<th class="center">订单金额</th>
									<th class="center">倍数</th>
									<th class="center">注数</th>
									<th class="center">是否追加</th>
									<th class="center">订单状态</th>
									<th class="center">中奖金额</th>
								</tr>
							</thead>
							<tbody>
									<c:if test="${QX.cha == 1 }">
										<tr>
											<td class='center'>${pd.order_sn}</td>
											<td class='center'>${pd.ticket_amount}</td>
											<td class='center'>${pd.cathectic}</td>
											<td class='center'>${pd.bet_num}</td>
											<td class='center'>
												<c:choose>
													<c:when test="${pd.play_type == '00'}">标准</c:when>
													<c:when test="${pd.play_type == '05'}">追加</c:when>
													<c:otherwise>--</c:otherwise>
												</c:choose>
										 	</td>
											<td class='center'> 
											<c:choose>
												<c:when test="${pd.order_status == 0}">待付款</c:when>
												<c:when test="${pd.order_status == 1}">待出票</c:when>
												<c:when test="${pd.order_status == 2}">出票失败</c:when>
												<c:when test="${pd.order_status == 3}">待开奖</c:when>
												<c:when test="${pd.order_status == 4}">未中奖</c:when>
												<c:when test="${pd.order_status == 5}">已中奖</c:when>
												<c:when test="${pd.order_status == 6}">派奖中</c:when>
												<c:when test="${pd.order_status == 7}">已派奖</c:when>
												<c:when test="${pd.order_status == 8}">支付失败</c:when>
											</c:choose>
											</td>
											<td class='center'>${pd.winning_money}</td>
										</tr>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
							</tbody>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						</div>
					</div>
					投注方案详情
					<div  class="row">
						<div class="col-xs-12">
						<table   class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">投注内容</th>
									<th class="center">投注方式</th>
								</tr>
							</thead>
							<tbody>
									<c:if test="${QX.cha == 1 }">
										<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<a style = "color:red">${var.redBileList}</a><c:if test="${!empty var.redTowingList}">--<a style = "color:red">${var.redTowingList}</a></c:if>
												<a style = "color:blue">${var.blueBileList}</a><c:if test="${!empty var.blueTowingList}">--<a style = "color:blue">${var.blueTowingList}</a></c:if>
											</td>
											<td class='center'>
												<c:choose>
													<c:when test="${var.bet_type == '00'}">标准投注</c:when>
													<c:when test="${var.bet_type == '01'}">复式投注</c:when>
													<c:when test="${var.bet_type == '02'}">胆拖投注</c:when>
													<c:otherwise>--</c:otherwise>
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
							</tbody>
						</table>
						</div>
					</div>
				</div>
			</div>
		</div>

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
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
			function tosearch(status){
			if(status==0){
				$("#user_name").val("");
				$("#mobile").val("");
				$("#order_sn").val("");
				$("#lastStart").val("");
				$("#lastEnd").val("");
				$("#amountStart").val("");
				$("#amountEnd").val("");
				$("#order_status").empty();
				$("#lottery_classify_id").empty();
			}
			top.jzts();
			$("#Form").submit();
		}
			//订单详情页
			function toDetail(orderId){
				window.location.href='<%=basePath%>ordermanager/toDetail.do?order_id='+orderId;
			}
			
	</script>
</body>
</html>