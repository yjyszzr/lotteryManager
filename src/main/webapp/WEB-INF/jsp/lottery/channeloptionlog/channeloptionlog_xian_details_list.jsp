<%@page import="com.fh.util.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
<body class="no-skin" background="">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- 检索  -->
							<form action="xiandata/list.do" method="post" name="Form" id="Form">
							<input type="hidden" id="defaultTime" name="defaultTime" value="123" />
								<table style="margin-top: 5px;">
									 
									<tr style="margin: 2px">
										<td>

											<div class="nav-search">
												  门店:
													<select name="channel_id" id="channel_id" data-placeholder="请选择" value="${pd.channel_id }" style="width: 154px; border-radius: 5px !important">
														<option value="" selected="selected">全部</option>
														<c:forEach items="${channelMap}" var="map" varStatus="vs">
															<option value="${map.key}" <c:if test="${pd.channel_id == map.key}">selected</c:if>>${map.value }</option>
														</c:forEach>
													</select> 
												  节点:
													<select name="operation_node" id="operation_node" data-placeholder="请选择" value="${pd.operation_node }" style="width: 70px; border-radius: 5px !important">
													<option value="" selected="selected">全部</option>
													<option value="10" <c:if test="${pd.operation_node == 10}">selected</c:if>>领取</option>
													<option value="1" <c:if test="${pd.operation_node == 1}">selected</c:if>>激活</option>
													<option value="2" <c:if test="${pd.operation_node == 2}">selected</c:if>>购彩</option>
													</select> 
												<span class="input-icon" style="width: 10px; text-align: right;"> </span>日期:  <span>
													<input name="lastStart" id="lastStart" value="${pd.lastStart }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 150px; border-radius: 5px !important" placeholder="开始时间" title="开始时间" />
													<input name="lastEnd" id="lastEnd" value="${pd.lastEnd }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 150px; border-radius: 5px !important" placeholder="结束时间" title="结束时间" />
												</span> 
												<span class="input-icon" style="width: 10px;"> </span> 
												<span class="input-icon"> <input type="text" placeholder="输入店铺或店员名称" class="nav-search-input" id="keywords" autocomplete="off" name="keywords" value="${pd.keywords}" />
												</span>
												<c:if test="${QX.cha == 1 }">
													<span class="input-icon" style="width: 10px;"> </span>
													<span> 
														<a class="btn btn-light btn-xs blue" onclick="tosearch(1);" title="搜索" style="border-radius: 5px; color: blue !important; width: 50px">搜索</a>
													</span>
													<span> 
														<a class="btn btn-light btn-xs blue" onclick="tosearch(0);" title="清空" style="border-radius: 5px; color: blue !important; width: 50px">清空</a>
													</span>
												</c:if>
												<c:if test="${QX.toExcel == 1 }">
												<span> 
													<a class="btn btn-light btn-xs blue" onclick="toExcel();" title="导出EXCEL" style="border-radius: 5px; color: blue !important; width: 70px"> 导出EXCEL</a>
												</span>
											</c:if>
											</div>
										</td>



									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center">所属门店</th>
											<th class="center">日期</th>
											<th class="center">用户名</th>
											<th class="center">手机号</th>
											<th class="center">所属店员</th>
											<th class="center">节点</th>
											<th class="center">购彩金额</th>
											<th class="center">门店提成</th>
											<th class="center">店员提成</th>
											 							 
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->
											<c:choose>
												<c:when test="${not empty varList}">
													<c:if test="${QX.cha == 1 }">
														<c:forEach items="${varList}" var="var" varStatus="vs">
															<tr>
																<td class='center'>${var.channel_name}</td>
																<td class='center'>
																<c:if test="${!empty var.option_time}">
																${DateUtil.toSDFTime(var.option_time*1000)}
																</c:if>
																</td>
																<td class='center'>${var.user_name}</td>
																<td class='center'>${var.mobile}</td>
																<td class='center'>${var.dis_name}</td>
																<td class='center'>${var.operation_node}</td>
																<td class='center'>${var.option_amount}</td>
																<td class='center'>${var.option_amount_chl}</td>
																<td class='center'>${var.option_amount_cdt}</td>
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
														<td colspan="100" class="center">没有相关数据</td>
													</tr>
												</c:otherwise>
											</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: top;">
												本页合计：用户数${consumerSum}个，累计购彩金额${optionAmount}元，门店提成
												${optionAmountChl}元，店员提成${optionAmountCdt}元</td>
										</tr>
										<tr>
											<td style="vertical-align: top;"><div class="pagination"
													style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
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
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
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
// 		function tosearch(){
// 			top.jzts();
// 			$("#Form").submit();
// 		}
		
		function tosearch(status){
			if(status==0){
				$("#channel_id").val("");
				$("#operation_node").val("");
				$("#lastStart").val("");
				$("#lastEnd").val("");
				$("#keywords").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		
		//导出excel
		function toExcel(){
			//window.location.href='<%=basePath%>xiandata/excel.do';
			 $("#Form").attr("action","xiandata/excel.do").submit();
			 $("#Form").attr("action","xiandata/list.do");

		}
	</script>


</body>
</html>