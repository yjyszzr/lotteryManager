<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.fh.util.DateUtil"%>
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
	<style type="text/css">
#xuxian{
width:100%;
height:0;
border-bottom:#778899 1px dashed;
overflow:hidden;
}/*#778899表示灰色，边框宽度为一个像素，边框线型为虚线*/
.news{
overflow:hidden;
}
.news .reply {
display:block;
  float: left;
  cursor:pointer;
  }
.news .userName {
display:block;
  float: right;
  }

</style>
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
					<form action="complain/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
<!-- 						<input type="hidden"  name = "reply" id = "reply"/> -->
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">投诉者:</td>
								<td>${pd.user_name }</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td>${pd.mobile }</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">时间:</td>
								<td>${DateUtil.toSDFTime(var.complain_time*1000)}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">投诉内容:</td>
								<td>${pd.complain_content }</td>
							</tr>
							<c:if test="${!empty pd.reply }">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">回馈:</td>
									<td class='center'>
												<div>
													<c:forEach items="${pd.varList}" var="replyEntityList" varStatus = "vss">
														<div  class="news">
															<div class="reply" title="${replyEntityList.reply}">
																	<c:choose>
																	<c:when test="${fn:length(replyEntityList.reply)  <= 30 }">
																		${replyEntityList.reply}
																	</c:when>
																	<c:otherwise>
													 					 ${fn:substring(replyEntityList.reply,0,30)}...  
																	</c:otherwise>
																</c:choose>
															</div>
															<div  class="userName">
																<lable style="color:red">
																	${replyEntityList.userName}
																	${DateUtil.toSDFTime(replyEntityList.time*1000)}
																</lable>
															</div>
														<c:if test="${! vss.last}">
															<div id="xuxian"></div>
														</c:if>
														</div>
													</c:forEach>
												</div>
											</td>
							</tr>
							</c:if>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">
								<c:choose>
									<c:when test="${!empty pd.reply }">再次回馈</c:when>
									<c:when test="${empty pd.reply }">回馈</c:when>
								</c:choose>
								</td>
								<td> <textarea  id = "reply" name = "reply" style="width:100%; height:100px; border-style:none; border-width:0px; overflow-y:visible; font-size:14px;border:solid 1px gray""></textarea></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">处理状态:</td>
								<td> 
										<label style="float:left;padding-left: 5px;padding-top:7px;">
											<input name="is_read" type="radio"   <c:if test="${pd.is_read==0}">checked="checked"</c:if>   value = "0" class="ace" id="label_defaults_2" />
											<span class="lbl">未处理完毕</span>
										</label>
										<label style="float:left;padding-left: 8px;padding-top:7px;">
											<input  name="is_read" type="radio"  <c:if test="${pd.is_read==1}">checked="checked"</c:if>   value = "1" class="ace" id="label_defaults_1" />
											<span class="lbl">处理完毕</span>
										</label>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
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
		//保存
		function save(){
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>