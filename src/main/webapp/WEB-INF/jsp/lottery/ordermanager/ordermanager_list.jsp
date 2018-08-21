﻿<%@page import="com.fh.util.DateUtil"%>
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
						<!-- 检索  -->
						<form action="ordermanager/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;" >
								<tr style="margin:2px ">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
												订单编号:
											</span>
											<span class="input-icon">
												<input type="text" placeholder="订单编号" class="nav-search-input" id="order_sn" autocomplete="off" name="order_sn" value="${pd.order_sn }"   onkeyup="value=value.replace(/[^\d]/g,'')"  />
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
										<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													订单状态:
												</span>
										 	<select  name="order_status" id="order_status" data-placeholder="请选择" value="${pd.order_status }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected="selected">全部</option>
													<option value="0" <c:if test="${pd.order_status!=NULL && pd.order_status!='' && pd.order_status == 0}">selected</c:if>>待付款</option>
													<option value="1" <c:if test="${pd.order_status == 1}">selected</c:if>>待出票</option>
													<option value="2" <c:if test="${pd.order_status == 2}">selected</c:if>>出票失败</option>
													<option value="3" <c:if test="${pd.order_status == 3}">selected</c:if>>待开奖</option>
													<option value="4" <c:if test="${pd.order_status == 4}">selected</c:if>>未中奖</option>
													<option value="5" <c:if test="${pd.order_status == 5}">selected</c:if>>已中奖</option>
													<option value="6" <c:if test="${pd.order_status == 6}">selected</c:if>>派奖中</option>
													<option value="7" <c:if test="${pd.order_status == 7}">selected</c:if>>审核中</option>
													<option value="8" <c:if test="${pd.order_status == 8}">selected</c:if>>支付失败</option>
										  	</select>
										  	</div>
									</td>
									</tr>
									<tr style="margin:2px">
									<td>
										<div class="nav-search">
											<span class="input-icon" style="width:80px;text-align:right;">
													彩种:
												</span>
										 	<select  name="lottery_classify_id" id="lottery_classify_id" data-placeholder="请选择" value="${pd.lottery_classify_id }" style="width:154px;border-radius:5px !important"  >
											<option value="" selected="selected">全部</option>
													<option value="1" <c:if test="${pd.lottery_classify_id == 1}">selected</c:if>>竞彩足球</option>
										  	</select>
										  	</div>
									</td>
									<td >
										<span class="input-icon" style="width:80px;text-align:right;">
												购彩时间:
											</span>
											<span  >
												<input name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
												<input name="lastEnd" id="lastEnd"  value="${pd.lastEnd }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间" title="结束时间"/>
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
									<c:if test="${QX.toExcel == 1 }">
										<td style="vertical-align:top;padding-left:2px">
										<span class="input-icon" style="width:80px;"> </span>
											<span>
												<a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"  style="border-radius:5px;color:blue !important; width:150px">导出到EXCEL </a>
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
									<th class="center">订单编号</th>
									<th class="center">回执编号</th>
									<th class="center">用户名称</th>
									<th class="center">手机号</th>
									<th class="center">彩种名称</th>
									<th class="center">支付</th>
									<th class="center">投注金额</th>
									<th class="center">中奖金额</th>
<!-- 									<th class="center">红包金额</th> -->
									<th class="center">购彩时间</th>
									<th class="center">订单状态</th>
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
											<td class='center'><a onclick="toDetail('${var.order_id}');" style=" cursor:pointer;">${var.order_sn}</a></td>
											<td class='center'>${var.pay_order_sn}</td>
											<td class='center'>${var.user_name}</td>
											<td class='center'>${var.mobile}</td>
											<td class='center'>${var.lottery_name}</td>
											<c:choose>
												<c:when test="${var.order_status == 0 }"><td class='center'>--</td></c:when>
												<c:otherwise>
													<td class='center'>
														<c:if test="${var.surplus !='0.00' }">
														余额：	${var.surplus }
														</c:if>
														<c:if test="${var.third_party_paid !='0.00' }">
															<c:if test="${!empty var.pay_name }">
																${var.pay_name}：${var.third_party_paid }
															</c:if>
															<c:if test="${empty var.pay_name }">
																第三方：${var.third_party_paid }
															</c:if>
														</c:if>
														<c:if test="${var.bonus !='0.00' }">
															红包：	${var.bonus }
														</c:if>
	<%-- 												<c:if test="${!empty var.pay_name}">${var.pay_name}</c:if> --%>
	<%-- 												<c:if test="${ var.surplus > 0}">&nbsp余额</c:if> --%>
	<%-- 												<c:if test="${ var.bonus > 0}">&nbsp红包</c:if> --%>
													</td>
												</c:otherwise>
											</c:choose>
											<td class='center'>${var.ticket_amount}</td>
											<td class='center'>${var.winning_money}</td>
<%-- 											<td class='center'>${var.bonus}</td> --%>
											<td class='center'>${DateUtil.toSDFTime(var.add_time*1000)}</td>
											<td class='center'> 
												<c:choose>
													<c:when test="${var.order_status == 0}">待付款</c:when>
													<c:when test="${var.order_status == 1}">待出票</c:when>
													<c:when test="${var.order_status == 2}">出票失败</c:when>
													<c:when test="${var.order_status == 3}">待开奖</c:when>
													<c:when test="${var.order_status == 4}">未中奖</c:when>
													<c:when test="${var.order_status == 5}">已中奖</c:when>
													<c:when test="${var.order_status == 6}">派奖中</c:when>
													<c:when test="${var.order_status == 7}">审核中</c:when>
													<c:when test="${var.order_status == 8}"><lable style ="color:red">支付失败</lable></c:when>
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
								<td style="vertical-align:top;"> 支付成功${allAmount }元</td>
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
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="订单详情";
				 diag.URL = '<%=basePath%>ordermanager/toDetail.do?order_id='+orderId;
				 diag.Width = 1300;
				 diag.Height = 320;
				 diag.Modal = true;				//有无遮罩窗口
				 diag. ShowMaxButton = false;	//最大化按钮
			     diag.ShowMinButton = false;		//最小化按钮
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 if('${page.currentPage}' == '0'){
							 tosearch();
						 }else{
							 tosearch();
						 }
					}
					diag.close();
				 };
				 diag.show();
			}
			
			//导出excel
			function toExcel(){
// 				var lastStart = $("#lastStart").val();
// 				var lastEnd = $("#lastEnd").val();
// 				var orderStatus = $("#order_status").val();
// 				if(lastStart =="" && lastEnd == ""  && orderStatus == "" ){
					bootbox.confirm("<h4><strong>温馨提示</strong> </h4><hr><h5>&nbsp&nbsp默认导出今天的数据。</h5><br><h5>&nbsp&nbsp您可以按照<span style='color:red'>时间</span>筛选导出！</h5><br> &nbsp&nbsp<input id=\"selectionTime\"   type=\"text\" onfocus=\"WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})\" readonly=\"readonly\" style=\"width:186px;border-radius:5px !important\" placeholder=\"时间筛选\"/><br><br>", function(result) {
						if(result) {
							var selectionTime = $("#selectionTime").val();
						    var items = selectionTime.split("-");
						    var newStr = items.join("");
							window.location.href='<%=basePath%>ordermanager/excel.do?selectionTime='+newStr;
						}
					});
// 				}else{
<%-- 				window.location.href='<%=basePath%>ordermanager/excel.do?lastStart='+lastStart+'&lastEnd='+lastEnd+'&order_status='+orderStatus; --%>
// 				}
			}
			
	</script>
</body>
</html>