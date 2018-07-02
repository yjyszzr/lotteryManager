<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
					
					<form action="userwithdraw/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="status" id="status" value=""/>
						<input type="hidden" id="withdrawSn" name= "withdrawSn" value="${pd.withdrawal_sn }"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
									<td style="text-align: right;width:200px">
	                                	<label class=" no-padding-right" for="form-field-1">提现单号：</label>
                                	</td>
                                	<td style="text-align: left;width:200px"  >
	                                	<label class=" no-padding-right" for="form-field-1">${pd.withdrawal_sn}</label>
	                                </td>
                               		<td style="text-align: right;width:200px" >
	                                	<label class=" no-padding-right" for="form-field-1">真实姓名：</label>
                           		  	</td>
                           		  	<td style="text-align: left;width:200px" >	
	                                	<label class=" no-padding-right" for="form-field-1">${pd.real_name }</label>
                           		  	</td>
							</tr>
							<tr>
									<td style="text-align: right;width:200px">
	                                	<label class=" no-padding-right" for="form-field-1">身份证号：</label>
                                	</td>
                                	<td style="text-align: left;width:200px"  >
	                                	<label class=" no-padding-right" for="form-field-1">${pd.id_code}</label>
	                                </td>
                           		  	<td style="text-align: right;width:200px" >
	                                	<label class=" no-padding-right" for="form-field-1">手机号：</label>
                           		  	</td>
                           		  	<td style="text-align: left;width:200px" >	
	                                	<label class=" no-padding-right" for="form-field-1">${pd.mobile }</label>
                           		  	</td>
							</tr>
							<tr>
									<td style="text-align: right;width:200px">
	                                	<label class=" no-padding-right" for="form-field-1">发卡行：</label>
                                	</td>
                                	<td style="text-align: left;width:200px"  >
	                                	<label class=" no-padding-right" for="form-field-1">${pd.bank_name}</label>
	                                </td>
									<td style="text-align: right;width:200px">
	                                	<label class=" no-padding-right" for="form-field-1">银行卡号：</label>
                                	</td>
                                	<td style="text-align: left;width:200px"  >
	                                	<label class=" no-padding-right" for="form-field-1">${pd.card_no}</label>
	                                </td>
							</tr>
							<tr>
								 	<td style="text-align: right;width:200px" >
	                                	<label class=" no-padding-right" for="form-field-1">可提现金额：</label>
                           		  	</td>
                           		  	<td style="text-align: left;width:200px" >	
	                                	<label class=" no-padding-right" for="form-field-1">${pd.user_money }元</label>
                           		  	</td>
								<td style="text-align: right;width:200px" >
	                                	<label class=" no-padding-right" for="form-field-1">提现金额：</label>
                           		  	</td>
                           		  	<td style="text-align: left;width:200px" >	
	                                	<label class=" no-padding-right" for="form-field-1"><lable style="color:red;font-weight:bold">${pd.amount }元</lable></label>
                           		  	</td>
							</tr>
							<tr>
								 	<td style="text-align: right;width:200px" >
	                                	<label class=" no-padding-right" for="form-field-1">审核状态：</label>
                           		  	</td>
                           		  	<td style="text-align: left;width:200px" >	
	                                	<label class=" no-padding-right" for="form-field-1">
	                                	<c:choose>
	                                		<c:when test="${pd.status == 1}"><lable style="color:green">通过</lable></c:when>
	                                		<c:when test="${pd.status == 0}"><lable style="color:orange;font-weight:bold">待审核</lable></c:when>
	                                		<c:when test="${pd.status == 2}"><lable style="color:red;font-weight:bold">拒绝</lable></c:when>
	                                	</c:choose>
	                                	</label>
                           		  	</td>
								<td style="text-align: right;width:200px" >
	                                	<label class=" no-padding-right" for="form-field-1">备注：</label>
                           		  	</td>
                           		  	<td style="text-align: left;width:200px" >
                           		  	<c:choose>
                                		<c:when test="${pd.status == 0}">
                                			<input type="text" name="remarks" id="remarks" value="${pd.remarks}" autocomplete="off"   placeholder="备注" style="border-radius:5px !important"  />
                                		</c:when>
                                		<c:otherwise>${pd.remarks }</c:otherwise>
                                	</c:choose>	
                           		  	
<%-- 	                                	<label class=" no-padding-right" for="form-field-1"><lable style="color:red;font-weight:">${pd.amount }元</lable></label> --%>
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
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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