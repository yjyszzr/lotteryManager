<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.fh.util.DateUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
						<div id="zhongxin" style="padding-top: 13px;">
							<form action="userwithdraw/${msg }.do" name="Form" id="Form" method="post">
								<input type="hidden" name="status" id="status" value=""/>
								<input type="hidden" id="withdrawSn" name= "withdrawSn" value="${pd.withdrawal_sn }"/>
									<table class="table table-striped table-bordered table-hover">
										<tr>
											<td colspan="6">
			                                	<label class=" no-padding-right">账户明细</label>
		                                	</td>
									</tr>
									<tr>
											<td style="text-align: right;width:200px" ><label class=" no-padding-right">总余额：</label></td>
		                           		  	<td style="text-align: left;width:200px" ><label class=" no-padding-right">${pd.user_money_limit+pd.user_money }</label></td>
										 	<td style="text-align: right;width:200px" ><label class=" no-padding-right">总中奖金额：</label></td>
		                           		  	<td style="text-align: left;width:200px" >
		                           		  		<label class=" no-padding-right">
		                           		  		<c:choose>
		                           		  			<c:when test="${empty awardAmount }">0元</c:when>
		                           		  			<c:when test="${!empty awardAmount }">${awardAmount }元</c:when>
		                           		  		</c:choose>
		                           		  			
		                           		  		</label>
	                           		  		</td>
											<td style="text-align: right;width:200px" ><label class=" no-padding-right">当前可提现金额：</label></td>
		                           		  	<td style="text-align: left;width:200px" ><label class=" no-padding-right">${pd.user_money }元</label></td>
									</tr>
										</table>
								<table id="table_report" class="table table-striped table-bordered table-hover">
									<tr>
										<td colspan="4">
		                                	<label class=" no-padding-right">提现信息</label>
	                                	</td>
									</tr>
									<tr>
											<td style="text-align: right;width:200px"><label class=" no-padding-right">提现单号：</label></td>
		                                	<td style="text-align: left;width:200px"   ><label class=" no-padding-right">${pd.withdrawal_sn}</label></td>
		                               		<td style="text-align: right;width:200px"><label class=" no-padding-right">真实姓名：</label></td>
		                           		  	<td style="text-align: left;width:200px"   ><label class=" no-padding-right">${pd.real_name }</label></td>
									</tr>
									<tr>
											<td style="text-align: right;width:200px"><label class=" no-padding-right">身份证号：</label></td>
		                                	<td style="text-align: left;width:200px"  ><label class=" no-padding-right">${pd.id_code}</label></td>
		                           		  	<td style="text-align: right;width:200px" ><label class=" no-padding-right">手机号：</label></td>
		                           		  	<td style="text-align: left;width:200px" ><label class=" no-padding-right">${pd.mobile }</label></td>
									</tr>
									<tr>
											<td style="text-align: right;width:200px"><label class=" no-padding-right">发卡行：</label></td>
		                                	<td style="text-align: left;width:200px"  ><label class=" no-padding-right">${pd.bank_name}</label></td>
											<td style="text-align: right;width:200px"><label class=" no-padding-right">银行卡号：</label></td>
		                                	<td style="text-align: left;width:200px"  ><label class=" no-padding-right">${pd.card_no}</label></td>
									</tr>
									<tr>
											<td style="text-align: right;width:200px" ><label class=" no-padding-right">提现金额：</label></td>
		                           		  	<td style="text-align: left;width:200px" ><label class=" no-padding-right" style="color:red;font-weight:bold">${pd.amount }元</label></td>
	                           		  		<td style="text-align: right;width:200px" ><label class=" no-padding-right">审核状态：</label></td>
		                           		  	<td style="text-align: left;width:200px" >	
			                                	<label class=" no-padding-right">
			                                	<c:choose>
			                                		<c:when test="${pd.status == 1}"><lable style="color:green">通过</lable></c:when>
			                                		<c:when test="${pd.status == 0}"><lable style="color:orange;font-weight:bold">待审核</lable></c:when>
			                                		<c:when test="${pd.status == 2}"><lable style="color:red;font-weight:bold">拒绝</lable></c:when>
			                                		<c:when test="${pd.status == 3}"><lable style="color:gray;font-weight:bold">等待银行审批</lable></c:when>
			                                	</c:choose>
			                                	</label>
		                           		  	</td>
									</tr>
									<c:if test="${pd.status != 0}">
										<tr>
		                           		  		<td style="text-align: right;width:200px" ><label class=" no-padding-right">审核人：</label></td>
			                           		  	<td style="text-align: left;width:200px" ><label class=" no-padding-right"> ${pd.auditor }</label> 	</td>
												<td style="text-align: right;width:200px" ><label class=" no-padding-right">审核时间：</label></td>
			                           		  	<td style="text-align: left;width:200px" ><c:if test="${!empty pd.audit_time }"><label class=" no-padding-right" >${DateUtil.toSDFTime(pd.audit_time*1000) }</label></c:if> </td>
										</tr>
									</c:if>
									<tr>
											<td style="text-align: right;width:200px" ><label class=" no-padding-right">备注：</label></td>
		                           		  	<td style="text-align: left;" colspan="3">
			                           		  	<c:choose>
			                                		<c:when test="${pd.status == 0}">
			                                			<input type="text" name="remarks" id="remarks" value="${pd.remarks}" autocomplete="off"   placeholder="备注" style="width:98%;border-radius:5px !important"  />
			                                		</c:when>
			                                		<c:otherwise>${pd.remarks }</c:otherwise>
			                                	</c:choose>	
		                           		  	</td>
									</tr>
									
										<c:choose>
		                                		<c:when test="${pd.status == 0}">
			                                		<tr>
														<td style="text-align: center;width:200px" >
														</td>
														<td style="text-align: right;width:200px" >
															<a class="btn btn-mini btn-primary" onclick="toManualAudit('1','${pd.withdrawal_sn}');">通过</a>
														</td>
														<td style="text-align: left;width:200px" >
															<a class="btn btn-mini btn-danger" onclick="toManualAudit('2','${pd.withdrawal_sn}');">拒绝</a>
														</td>
														<td style="text-align: center;width:200px" >
														</td>
													</tr>
		                                		</c:when>
			                           	</c:choose>
									</table>
							</form>
							<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<th colspan="9">
                                	<label class=" no-padding-right">最近提现纪录</label>
                               	</th>
                           <thead>
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">提现编号</th>
									<th class="center">真实姓名</th>
<!-- 									<th class="center">电话</th> -->
									<th class="center">提现金额</th>
									<th class="center">银行名称</th>
									<th class="center">卡号</th>
									<th class="center">申请提现时间</th>
									<th class="center">状态</th>
									<th class="center">备注</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty pageDataList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${pageDataList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>	${var.withdrawal_sn}</td>
											<td class='center'>${var.real_name}</td>
<%-- 											<td class='center'>${var.mobile}</td> --%>
											<td class='center'>${var.amount}</td>
											<td class='center'>${var.bank_name}</td>
											<td class='center'>${var.card_no}</td>
											<td class='center'>${DateUtil.toSDFTime(var.add_time*1000)}</td>
											<td class='center'> 
												<c:choose>
													<c:when test="${var.status==0}"><lable style="color:orange;font-weight:bold">待审核</lable></c:when>
													<c:when test="${var.status==1}"><lable style="color:green">通过</lable></c:when>
													<c:when test="${var.status==2}"><lable style="color:red;font-weight:bold">拒绝</lable></c:when>
													<c:when test="${var.status==3}"><lable style="color:red;font-weight:bold">审批中</lable></c:when>
												</c:choose>
											</td>
											<c:choose>
												<c:when test="${fn:length(var.remarks)  <= 5 }"><td > ${var.remarks } </td></c:when>
												<c:otherwise><td title="${var.remarks}"> <a style="cursor:pointer;">${fn:substring(var.remarks,0,5)}... </a></td></c:otherwise>
											</c:choose>
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
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());

		function toManualAudit(status,withdrawSn){
					top.jzts();
					$("#status").val(status);
					$("#Form").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
			}
	</script>
</body>
</html>