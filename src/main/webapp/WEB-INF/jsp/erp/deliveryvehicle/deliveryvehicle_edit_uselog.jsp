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
	<link rel="stylesheet" href="static/ace/css/jquery.cxcalendar.css" />
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
					
					<form action="deliveryvehicle/saveUseLog.do" name="Form" id="Form" method="post">
						<input type="hidden" name="delivery_vehicle_work_id" id="delivery_vehicle_work_id" value="${pd.delivery_vehicle_work_id}"/>
						<input type="hidden" name="status" id="status" value="${pd.status}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">出勤时间:</td>
								<td>
								    <input id="start_time" name="start_time" value="${pd.start_time}" type="text" readonly="readonly" onchange="judgeTime(0);" data-position="bottom" style="width:98%;" placeholder="这里输入出勤时间" title="出勤时间"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退勤时间:</td>
								<td>
								    <input id="end_time" name="end_time" value="${pd.end_time}" type="text" readonly="readonly" onchange="judgeTime(1);" data-position="bottom" style="width:98%;" placeholder="这里输入退勤时间" title="退勤时间"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">工作时间:</td>
								<td><input type="text" name="total_time" id="total_time" value="${pd.total_time}" maxlength="12" readonly="readonly" placeholder="工作时间" title="工作时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">出勤里程:</td>
								<td><input type="text" name="start_mileage" id="start_mileage" value="${pd.start_mileage}" maxlength="12" onchange="judgeMileage(0);" placeholder="这里输入出勤里程" title="出勤里程" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退勤里程:</td>
								<td><input type="text" name="end_mileage" id="end_mileage" value="${pd.end_mileage}" maxlength="12" onchange="judgeMileage(1);" placeholder="这里输入退勤里程" title="退勤里程" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">行驶里程:</td>
								<td><input type="text" name="total_mileage" id="total_mileage" value="${pd.total_mileage}" maxlength="12" readonly="readonly" placeholder="行驶里程" title="行驶里程" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
								    <c:if test="${pd.status!=2}">
										<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
										<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									</c:if>
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
	<script src="static/ace/js/jquery.cxcalendar.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			/* if($("#start_time").val()==""){
				$("#start_time").tips({
					side:3,
		            msg:'请输入出勤时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#start_time").focus();
			return false;
			}
			if($("#end_time").val()==""){
				$("#end_time").tips({
					side:3,
		            msg:'请输入退勤时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#end_time").focus();
			return false;
			}
			if($("#start_mileage").val()==""){
				$("#start_mileage").tips({
					side:3,
		            msg:'请输入出勤里程',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#start_mileage").focus();
			return false;
			}
			if($("#end_mileage").val()==""){
				$("#end_mileage").tips({
					side:3,
		            msg:'请输入退勤里程',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#end_mileage").focus();
			return false;
			} */
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		function judgeTime(flag){
			var start_time = $("#start_time").val().trim();
			var end_time = $("#end_time").val().trim();
			if((start_time !=null&&start_time !="")&&(end_time !=null&&end_time !="")){
			    if(new Date(Date.parse(start_time))>new Date(Date.parse(end_time))){
			    	 if(flag == 0){
					     $("#start_time").tips({
					        side:3,
			                msg:'出勤时间必须小等于退勤时间',
			                bg:'#AE81FF',
			                time:2
			             });
					     $("#start_time").val("");
			    	 }else{
			    		 $("#end_time").tips({
					        side:3,
			                msg:'出勤时间必须小等于退勤时间',
			                bg:'#AE81FF',
			                time:2
			             });
			    		 $("#end_time").val("");
			    	 }
			    	 $("#total_time").val("");
			     }else{
			    	 var d1 = new Date(Date.parse(start_time));
			    	 var d2 = new Date(Date.parse(end_time));
			    	 var total = parseInt(d2 - d1)/1000/60/60;
			    	 var fTotal = parseFloat(total);    
			    	 if (isNaN(fTotal)) {   
			    	   return;    
			    	 }          
			    	 fTotal = Math.round(fTotal*100)/100;
			    	 $("#total_time").val(fTotal+"");
			     }
			}
		}
		
		function judgeMileage(flag){
			if($("#status").val()==2)return;
			var start_mileage = $("#start_mileage").val().trim();
			var end_mileage = $("#end_mileage").val().trim();
			var start_mileage_replace = start_mileage.replace(/[^\d\.]/g, '');
			var end_mileage_replace = end_mileage.replace(/[^\d\.]/g, '');
			if((start_mileage !=null&&start_mileage !="")&&(start_mileage_replace ==null||start_mileage_replace =="")){
				$("#start_mileage").tips({
			        side:3,
	                msg:'出勤里程只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#start_mileage").val("");
			     $("#total_mileage").val("");
			     return;
			}
			if((start_mileage !=null&&start_mileage !="")&&(start_mileage_replace !=null||start_mileage_replace !="")&&(parseInt(start_mileage)<=0)){
				 $("#start_mileage").tips({
			        side:3,
	                msg:'出勤里程只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#start_mileage").val("");
			     $("#total_mileage").val("");
			     return;
			}
			if((end_mileage !=null&&end_mileage !="")&&(end_mileage_replace ==null||end_mileage_replace =="")){
				$("#end_mileage").tips({
			        side:3,
	                msg:'退勤里程只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#end_mileage").val("");
			     $("#total_mileage").val("");
			     return;
			}
			if((end_mileage !=null&&end_mileage !="")&&(end_mileage_replace !=null||end_mileage_replace !="")&&(parseInt(end_mileage)<=0)){
				 $("#end_mileage").tips({
			        side:3,
	                msg:'退勤里程只能输入大于零的数值',
	                bg:'#AE81FF',
	                time:2
	             });
			     $("#end_mileage").val("");
			     $("#total_mileage").val("");
			     return;
			}
			if((start_mileage !=null&&start_mileage !="")&&(end_mileage !=null&&end_mileage !="")){
			    if(parseFloat(start_mileage)>parseFloat(end_mileage)){
			    	 if(flag == 0){
					     $("#start_mileage").tips({
					        side:3,
			                msg:'出勤里程必须小等于退勤里程',
			                bg:'#AE81FF',
			                time:2
			             });
					     $("#start_mileage").val("");
			    	 }else{
			    		 $("#end_mileage").tips({
					        side:3,
			                msg:'出勤里程必须小等于退勤里程',
			                bg:'#AE81FF',
			                time:2
			             });
			    		 $("#end_mileage").val("");
			    	 }
			    	 $("#total_mileage").val("");
			     }else{
			    	 var total = parseFloat(end_mileage)-parseFloat(start_mileage);
			    	 $("#total_mileage").val(total+"");
			     }
			}
		}
		
		$(function() {
			// 选择日期和时间
			$('#start_time').cxCalendar({
			  type: 'datetime',
			  format: 'YYYY-MM-DD HH:mm:ss'
			});
			$('#end_time').cxCalendar({
			  type: 'datetime',
			  format: 'YYYY-MM-DD HH:mm:ss'
			});
		});
		</script>
</body>
</html>