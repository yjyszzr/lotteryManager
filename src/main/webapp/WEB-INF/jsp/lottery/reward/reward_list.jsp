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
						<form action="reward/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												订单编号:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="订单编号" class="nav-search-input" id="order_sn" autocomplete="off" name="order_sn" value="${pd.order_sn }"/>
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												手机号:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="手机号" class="nav-search-input" id="mobile" autocomplete="off" name="mobile" value="${pd.mobile}"  onkeyup="value=value.replace(/[^\d]/g,'')"  />
											</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												用户昵称:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="用户昵称" class="nav-search-input" id="user_name" autocomplete="off" name="user_name" value="${pd.user_name}" />
											</span>
										</div>
									</td>
									</tr>
									<tr style="margin:2px">
									<td >
										<span class="input-icon" style="width:80px;text-align:right;">
												下单时间:
											</span>
											<span  >
												<input name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" />
												<input name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间"/>
											</span>
									</td>
									<td>
											<span class="input-icon" style="width:80px;text-align:right;">
													奖金范围:
												</span>
										 	<span  >
												<input name="amountStart" id="amountStart"  value="${pd.amountStart }" type="text"  style="width:74px;border-radius:5px !important" placeholder="0元"  onkeyup="value=value.replace(/[^\d]/g,'')" />
												<input name="amountEnd" id="amountEnd"  value="${pd.amountEnd}" type="text"  style="width:74px;border-radius:5px !important" placeholder="0元"  onkeyup="value=value.replace(/[^\d]/g,'')" />
											</span>
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
							</table> <!-- 检索结束 -->
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">订单编号</th>
									<th class="center">用户昵称</th>
									<th class="center">电话</th>
									<th class="center">彩种</th>
									<th class="center">投注金额</th>
									<th class="center">中奖金额</th>
									<th class="center">下单时间</th>
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
											<td class='center'>${var.order_sn}</td>
											<td class='center'>${var.user_name}</td>
											<td class='center'>${var.mobile}</td>
											<td class='center'>${var.lottery_name}</td>
											<td class='center'> ￥:${var.ticket_amount}元</td>
											<td class='center'><lable style="color:red">￥:${var.amount}元</lable></td>
											<td class='center'>${DateUtil.toSDFTime(var.pay_time*1000)} </td>
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
								总计:提现成功<lable style="color:red">￥:${rewardAmount}元</lable>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						</form>
						</div> <!-- /.col -->
					</div> <!-- /.row -->
				</div> <!-- /.page-content -->
			</div>
		</div> <!-- /.main-content -->
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
				$("#status").empty();
				$("#lastStart").val("");
				$("#lastEnd").val("");
				$("#amountStart").val("");
				$("#amountEnd").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
	</script>
</body>
</html>