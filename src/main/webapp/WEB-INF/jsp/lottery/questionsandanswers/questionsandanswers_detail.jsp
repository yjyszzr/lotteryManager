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
						<input type="hidden" name="end_time" id="end_time" value="${DateUtil.toTimeSubtraction(matchPd.match_time)}"/>
						<input type="hidden" name="match_id" id="match_id" value="${matchPd.match_id}"/>
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
										    <select  name="scope_of_activity" id="scope_of_activity" value="${pd.scope_of_activity}" style="width:140px;border-radius:5px !important">
										        <option value="1" <c:if test="${pd.bonus_type==1}">selected</c:if>>竞彩足球</option>
										    </select>
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
	                                  	 	<input type="text" id="limit_lottery_amount"  name="limit_lottery_amount" placeholder="0元"   autocomplete="off"   value="${pd.limit_lottery_amount}"  onkeyup="value=value.replace(/[^\d]/g,'')" style="width:140px;border-radius:5px !important"/> &nbsp元
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
												<input name="start_time" id="start_time"  value="${pd.start_time }" type="text" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:140px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
												--  <lable style="color:red">${DateUtil.toTimeSubtraction(matchPd.match_time)}</lable>  结束竞猜
											</div>
	                                	<div class="col-sm-5"> </div>
									</td>
							</tr>
							<tr>
									<td style="text-align: right;" >
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">奖池初始额：</label>
                                	</td>
                                	<td style="text-align: left;"    colspan="4">
	                                	<div class="col-sm-4">
		                                  	 	<input type="text" id="bonus_pool"  name="bonus_pool" placeholder="0元"   autocomplete="off"   onkeyup="value=value.replace(/[^\d]/g,'')"  value="${pd.bonus_amount}" style="width:140px;border-radius:5px !important"/>&nbsp元
                                  	 	</div>
		                                <div class="col-sm-5">  </div>
	                                </td>
							</tr>
							<tr>
									<td style="text-align: right;" rowspan="6" >
	                                	<label class="col-sm-3 control-label no-padding-right" for="form-field-1">题目设置：</label>
                                	</td>
                                	<td style="text-align: left;" >
	                                	<div class="col-sm-4">
		                                  	 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="题设1"   autocomplete="off"  value="${pd.bonus_amount}" style="width:140px;border-radius:5px !important"/> 
                                  	 	</div>
	                                </td>
                                	<td  style="text-align: right;"  rowspan="6">
                                	<div class="col-sm-4"  control-label no-padding-right" for="form-field-1">回答设置：</div>
	                                </td>
                                	<td  >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"  autocomplete="off"  value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
                                	<td   >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"  autocomplete="off"    value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
							</tr>
							<tr>
                                	<td style="text-align: left;" >
	                                	<div class="col-sm-4">
		                                  	 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="题设2"   autocomplete="off"   value="${pd.bonus_amount}" style="width:140px;border-radius:5px !important"/>
                                  	 	</div>
		                                <div class="col-sm-5">  </div>
	                                </td>
                                	<td  >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
                                	<td   >
                                	<div class="col-sm-4">
                                			<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
							</tr>
							<tr>
                                	<td style="text-align: left;" >
	                                	<div class="col-sm-4">
		                                  	 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="题设3"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:140px;border-radius:5px !important"/>
                                  	 	</div>
		                                <div class="col-sm-5">  </div>
	                                </td>
                                	<td  >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
                                	<td   >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
							</tr>
							<tr>
                                	<td style="text-align: left;" >
	                                	<div class="col-sm-4">
		                                  	 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="题设4"   autocomplete="off"   value="${pd.bonus_amount}" style="width:140px;border-radius:5px !important"/>
                                  	 	</div>
		                                <div class="col-sm-5">  </div>
	                                </td>
                                	<td  >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
                                	<td   >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
							</tr>
							<tr>
                                	<td style="text-align: left;" >
	                                	<div class="col-sm-4">
		                                  	 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="题设5"   autocomplete="off"   value="${pd.bonus_amount}" style="width:140px;border-radius:5px !important"/>
                                  	 	</div>
		                                <div class="col-sm-5">  </div>
	                                </td>
                                	<td  >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
                                	<td   >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
							</tr>
							<tr>
                                	<td style="text-align: left;" >
	                                	<div class="col-sm-4">
		                                  	 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="题设6"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:140px;border-radius:5px !important"/>
                                  	 	</div>
		                                <div class="col-sm-5">  </div>
	                                </td>
                                	<td  >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
                                	<td   >
                                	<div class="col-sm-4">
                                		 	<input type="text" id="bonus_amount"  name="bonus_amount" placeholder="选项"   autocomplete="off"   value="${pd.bonus_amount}"  style="width:60px;border-radius:5px !important"/>
                                	</div>
	                                </td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="20">
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
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#start_time").val()==""){
				$("#start_time").tips({
					side:3,
		            msg:'请输入起始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#start_time").focus();
			return false;
			}
			if($("#end_time").val()==""){
				$("#end_time").tips({
					side:3,
		            msg:'请输入结束时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#end_time").focus();
			return false;
			}
			if($("#limit_lottery_amount").val()==""){
				$("#limit_lottery_amount").tips({
					side:3,
		            msg:'请输入最低参与金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#limit_lottery_amount").focus();
			return false;
			}
			if($("#bonus_pool").val()==""){
				$("#bonus_pool").tips({
					side:3,
		            msg:'请输入奖池',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bonus_pool").focus();
			return false;
			}
			if($("#question_and_answer").val()==""){
				$("#question_and_answer").tips({
					side:3,
		            msg:'请输入竞猜题',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#question_and_answer").focus();
			return false;
			}
			if($("#num_of_people").val()==""){
				$("#num_of_people").tips({
					side:3,
		            msg:'请输入人数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#num_of_people").focus();
			return false;
			}
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