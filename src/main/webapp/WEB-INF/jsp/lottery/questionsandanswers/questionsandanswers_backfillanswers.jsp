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
					
					<form action="questionsandanswers/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<input type="hidden" name="status" id="status" value=""/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
									<td style="text-align: right;">
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">相关赛事：</label>
                                	</td>
                               		<td style="text-align: left;"   colspan="4">
                                		 <div class="col-sm-4">${matchPd.changci} &nbsp${matchPd.home_team_name} VS ${matchPd.visiting_team_name }（${matchPd.league_name}）</div>
                                		<div class="col-sm-5"> </div>
                                	</td>
							</tr>
							<tr>
									<td style="text-align: right;">
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">活动范围：</label>
                                	</td>
                               		<td style="text-align: left;"   colspan="4">
                                		 <div class="col-sm-4">
                                		 <lable style=" "><c:if test="${pd.scope_of_activity==1 }">竞彩足球</c:if></lable> 
										</div>
                                		<div class="col-sm-5"> </div>
                                	</td>
							</tr>
							<tr>
									<td style="text-align: right;" >
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">累计购彩：</label>
                                	</td>
                                	<td style="text-align: left;"    colspan="4">
	                                	<div class="col-sm-4">
	                                		<lable style=" ">${pd.limit_lottery_amount }</lable>&nbsp元
	                               		</div>
	                                	<div class="col-sm-5"> </div>
	                                </td>
							</tr>
							<tr>
									<td style="text-align: right;" >
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">活动时间：</label>
                                	</td>
                                	<td  style="text-align: left;"    colspan="4">
											<div class="col-sm-4">
												<lable style="color:red">${DateUtil.toSDFTime(pd.start_time*1000)}</lable>  --  <lable style="color:red">${DateUtil.toTimeSubtraction(matchPd.match_time)}</lable>
											</div>
	                                	<div class="col-sm-5"> </div>
									</td>
							</tr>
							<tr>
									<td style="text-align: right;" >
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">奖池金额：</label>
                                	</td>
                                	<td style="text-align: left;"    colspan="4">
	                                	<div class="col-sm-4">
		                                  	 	<lable style=" ">${pd.bonus_pool }</lable>&nbsp元
                                  	 	</div>
		                                <div class="col-sm-5">  </div>
	                                </td>
							</tr>
							<c:forEach   items="${questionAndAnswerList}" var="questionAndAnswer" varStatus="questionAndAnswerStatus">
							<c:choose>
								<c:when test="${questionAndAnswerStatus.index == 0}">
									<tr>
											<td style="text-align: right;" rowspan="${fn:length(questionAndAnswerList)}" >
			                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">题目设置：</label>
		                                	</td>
		                                	<td style="text-align: left;" >
			                                	<div class="col-sm-4">
			                                	${questionAndAnswer.questionSetting }
				                                  	 	<input type="hidden" name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].questionSetting" value="${questionAndAnswer.questionSetting }"/> 
				                                  	 	<input type="hidden"  name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].questionNum" value="${questionAndAnswer.questionNum }"/> 
		                                  	 	</div>
			                                </td>
		                                	<td  style="text-align: right;"  rowspan="${fn:length(questionAndAnswerList)}">
		                                	<div class="col-sm-4"  control-label no-padding-right" for="form-field-1">回答设置：</div>
			                                </td>
		                                	<td  >
		                                	<div class="col-sm-4">
		                                	<label style="float:left;padding-left: 5px;padding-top:7px;">
													<input  name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].answerStatus1" type="radio"       value = "0" class="ace" id="answerStatus${questionAndAnswerStatus.index }1"   onclick="setSelectStatus(${questionAndAnswerStatus.index },1)"/>
													<span class="lbl">${questionAndAnswer.answerSetting1 }</span>
														 	<input type="hidden"  name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].answerSetting1" value = "${questionAndAnswer.answerSetting1 }"/>
												</label>
		                                	</div>
			                                </td>
		                                	<td>
		                                	<div class="col-sm-4">
		                                	<label style="float:left;padding-left: 5px;padding-top:7px;">
													<input name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].answerStatus2"   type="radio"       value = "0" class="ace" id="answerStatus${questionAndAnswerStatus.index }2" onclick="setSelectStatus(${questionAndAnswerStatus.index },2)" />
													<span class="lbl">${questionAndAnswer.answerSetting2 }</span>
														 	<input type="hidden"  name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].answerSetting2" value = "${questionAndAnswer.answerSetting2 }"/>
												</label>
		                                	</div>
			                                </td>
									</tr>
								</c:when>
								<c:when test="${questionAndAnswerStatus.index > 0}">
									<tr>
		                                	<td style="text-align: left;" >
			                                	<div class="col-sm-4">
			                                	${questionAndAnswer.questionSetting }
			                                	<input type="hidden" name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].questionSetting" value="${questionAndAnswer.questionSetting }"/> 
			                                	<input type="hidden"  name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].questionNum" value="${questionAndAnswer.questionNum }"/> 
		                                  	 	</div>
				                                <div class="col-sm-5">  </div>
			                                </td>
		                                	<td  >
		                                	<div class="col-sm-4">
		                                		<label style="float:left;padding-left: 5px;padding-top:7px;">
													<input  name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].answerStatus1" type="radio"       value = "0" class="ace" id="answerStatus${questionAndAnswerStatus.index }1"  onclick="setSelectStatus(${questionAndAnswerStatus.index },1)"/>
													<span class="lbl">${questionAndAnswer.answerSetting1 }</span>
													<input type="hidden"  name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].answerSetting1" value = "${questionAndAnswer.answerSetting1 }"/>
												</label>
		                                	</div>
			                                </td>
		                                	<td   >
		                                	<div class="col-sm-4">
		                                		<label style="float:left;padding-left: 5px;padding-top:7px;">
													<input  name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].answerStatus2" type="radio"       value = "0" class="ace" id="answerStatus${questionAndAnswerStatus.index }2"  onclick="setSelectStatus(${questionAndAnswerStatus.index },2)"/>
													<span class="lbl">${questionAndAnswer.answerSetting2}</span>
													<input type="hidden"  name="questionAndAnswersEntityList[${questionAndAnswerStatus.index}].answerSetting2" value = "${questionAndAnswer.answerSetting2 }"/>
												</label>
		                                	</div>
			                                </td>
									</tr>
								</c:when>
							</c:choose>
						</c:forEach>
							<tr>
									<td style="text-align: right;" >
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">参与人数：</label>
                                	</td>
                                	<td style="text-align: left;"    colspan="4">
	                                	<div class="col-sm-4">
	                                  	 	<input type="text" id="numOfPeople"  name="numOfPeople" placeholder="0人"   autocomplete="off"   onkeyup="value=value.replace(/[^\d]/g,'')" style="width:140px;border-radius:5px !important"/> &nbsp人
	                               		</div>
	                                	<div class="col-sm-5"> </div>
	                                </td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="20">
										<a class="btn btn-mini btn-primary" style="border-radius: 5px;" onclick="saveQuestionAndAnswers(2);">提交答案</a>
										<a class="btn btn-mini btn-danger" style="border-radius: 5px;"  onclick="top.Dialog.close();">取消</a>
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
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		function setSelectStatus(listIndex,buttonId){  
			if(buttonId == 1){
	            $("#answerStatus"+listIndex+buttonId).attr("checked",true);  
	            $("#answerStatus"+listIndex+buttonId).val(1);
	            $("#answerStatus"+listIndex+"2").attr("checked",false);  
	            $("#answerStatus"+listIndex+"2").val(0);
	        }else if(buttonId == 2){   
	            $("#answerStatus"+listIndex+buttonId).attr("checked",true);  
	            $("#answerStatus"+listIndex+buttonId).val(1);
	            $("#answerStatus"+listIndex+"1").attr("checked",false);  
	            $("#answerStatus"+listIndex+"1").val(0);
	        }  
	    } 
		//保存
		function saveQuestionAndAnswers(status){
			for(var q = 0 ; q < 6 ; q++){
				var num = 0
					num+= $("#answerStatus"+q+"1").val();
					num+= $("#answerStatus"+q+"2").val();
				if(num== 0){
		 				$("#answerStatus"+q+"1").tips({
		 					side:3,
		 		            msg:'请选择答案',
		 		            bg:'#AE81FF',
		 		            time:2
		 		        });
		 				$("#answerStatus"+q+"1").focus();
		 			return false;
				}
			}
			if($("#numOfPeople").val()==""){
				$("#numOfPeople").tips({
					side:3,
		            msg:'请输入人数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#numOfPeople").focus();
			return false;
			}
			$("#status").val(status);
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		</script>
</body>
</html>