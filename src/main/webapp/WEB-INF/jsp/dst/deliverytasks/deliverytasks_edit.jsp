<%@page import="com.fh.util.DateUtil"%>
<%@page import="java.util.Date"%>
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
					
					<form action="deliverytasks/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="delivery_tasks_id" id="delivery_tasks_id" value="${pd.delivery_tasks_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<%-- <tr style="">
								<td style="width:75px;text-align: right;padding-top: 13px;">ID:</td>
								<td><input type="number" name="delivery_tasks_id" id="delivery_tasks_id" value="${pd.delivery_tasks_id}" maxlength="32" placeholder="这里输入ID" title="ID" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">配送编码:</td>
								<td><input type="text" name="delivery_tasks_code" id="delivery_tasks_code" readonly="readonly" value="${pd.delivery_tasks_code}" maxlength="50" placeholder="这里输入配送编码" title="配送编码" style="width:98%;"/></td>
							</tr>
							<tr >
								<td style="width:95px;text-align: right;padding-top: 13px;">配送组织:</td>
								<td>
									<select class="chosen-select form-control" name="delivery_org_id" id="delivery_org_id" data-placeholder="选择配送组织" onchange="changeOrg()" style="vertical-align:top;width: 120px;">
								 	
								 	<c:forEach items="${deliveryOrgs}" var="org" varStatus="vs">
								 			<option value="${org.delivery_org_id}" ${org.delivery_org_id==pd.delivery_org_id?"selected":""}>${org.delivery_org_name}</option>
								 	</c:forEach>
								<td>
							</tr>
							<tr style="display:none">
								<td style="width:95px;text-align: right;padding-top: 13px;">配送组织名称:</td>
								<td><input type="text" name="delivery_org_name" readonly="readonly" id="delivery_org_name" value="${pd.delivery_org_name}" maxlength="32" placeholder="这里输入配送组织" title="配送组织ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">工作日:</td>
								<td><input class="span10 date-picker" name="work_day" id="work_day" onchange="changeWorkDay()" value="${pd.work_day==null?DateUtil.getDay():DateUtil.toDateStr(pd.work_day)}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="这里输入工作日" title="工作日" style="width:98%;"/></td>
								
							</tr>
						
							
							<tr  style="display:none" >
								<td style="width:95px;text-align: right;padding-top: 13px;">车辆工作ID:</td>
								<td><input type="number"  name="delivery_vehicle_work_id" id="delivery_vehicle_work_id" value="${pd.delivery_vehicle_work_id}" maxlength="32" placeholder="这里输入车辆工作ID" title="车辆工作ID" style="width:98%;"/></td>
							</tr>
							
							
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">配送车辆:</td>
								<td><input type="text" onclick="toSelecteDliveryVehicleWorkList()" name="vehicle_number" id="vehicle_number" value="${pd.vehicle_number}" maxlength="128" placeholder="这里输入车牌号" title="车牌号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">车辆联系人:</td>
								<td><input type="text" readonly="readonly" name="vehicle_contact" id="vehicle_contact" value="${pd.vehicle_contact}" maxlength="128" placeholder="这里输入车辆联系人" title="车辆联系人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">车辆电话:</td>
								<td><input type="text" name="vehicle_tel" readonly="readonly" id="vehicle_tel" value="${pd.vehicle_tel}" maxlength="128" placeholder="这里输入车辆电话" title="车辆电话" style="width:98%;"/></td>
							</tr>
							
							<tr style='display:none'>
								<td style="width:95px;text-align: right;padding-top: 13px;">用户ID:</td>
								<td><input type="text"  name="user_id" id="user_id" value="${pd.user_id}" maxlength="128" placeholder="这里输入userid" title="userid" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">配送员:</td>
								<td><input type="text"  onclick="toSelectUserList()" readonly="readonly" name="user_name" id="user_name"  value="${pd.user_name}" maxlength="128" placeholder="这里输入用户名称" title="用户名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">配送员电话:</td>
								<td><input type="text" name="user_tel" id="user_tel" value="${pd.user_tel}" maxlength="128" placeholder="这里输入用户电话" title="用户电话" style="width:98%;"/></td>
							</tr>
							<tr style="display:none">
								<td style="width:95px;text-align: right;padding-top: 13px;">类型:</td><%--0:自营，1:第三方 --%>
								<td>
								<select class="chosen-select form-control" onclick="return false;" readonly="readonly" name="type" id="type" data-placeholder="选择类型" style="vertical-align:top;width: 120px;">
								 	
								 	
								 			<option value="0" ${(pd.type==null||pd.type==''||pd.type==0)?"selected":""}>自营</option>
								 			<option value="1" ${pd.type==1?"selected":""}>第三方</option>
								 	
									
								
									</select>
<%-- 								<input type="number" name="type" id="type" readonly="readonly" value="${(pd.type==null||pd.type=='')?0:pd.type}" maxlength="32" placeholder="这里输入类型" title="类型" style="width:98%;"/></td>
 --%>								
							</tr>
							<%-- <tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建人:</td>
								<td><input type="text" name="createby" id="createby" value="${pd.createby}" maxlength="50" placeholder="这里输入创建人" title="创建人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input type="text" name="create_time" id="create_time" value="${pd.create_time}" maxlength="19" placeholder="这里输入创建时间" title="创建时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">更新人:</td>
								<td><input type="text" name="updateby" id="updateby" value="${pd.updateby}" maxlength="50" placeholder="这里输入更新人" title="更新人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">更新时间:</td>
								<td><input type="text" name="update_time" id="update_time" value="${pd.update_time}" maxlength="19" placeholder="这里输入更新时间" title="更新时间" style="width:98%;"/></td>
							</tr> --%>
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
			/* if($("#delivery_tasks_id").val()==""){
				$("#delivery_tasks_id").tips({
					side:3,
		            msg:'请输入ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_tasks_id").focus();
			return false;
			} */
			if($("#delivery_tasks_code").val()==""){
				$("#delivery_tasks_code").tips({
					side:3,
		            msg:'请输入配送编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_tasks_code").focus();
			return false;
			}
			if($("#delivery_org_id").val()==""){
				$("#delivery_org_id").tips({
					side:3,
		            msg:'请输入配送组织ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_org_id").focus();
			return false;
			}
			if($("#work_day").val()==""){
				$("#work_day").tips({
					side:3,
		            msg:'请输入工作日',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#work_day").focus();
			return false;
			}
			/* if($("#delivery_vehicle_work_id").val()==""){
				$("#delivery_vehicle_work_id").tips({
					side:3,
		            msg:'请输入车辆工作ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_vehicle_work_id").focus();
			return false;
			} */
			if($("#vehicle_number").val()==""){
				$("#vehicle_number").tips({
					side:3,
		            msg:'请输入车牌号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#vehicle_number").focus();
			return false;
			}
			if($("#vehicle_contact").val()==""){
				$("#vehicle_contact").tips({
					side:3,
		            msg:'请输入车辆联系人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#vehicle_contact").focus();
			return false;
			}
			if($("#vehicle_tel").val()==""){
				$("#vehicle_tel").tips({
					side:3,
		            msg:'请输入车辆电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#vehicle_tel").focus();
			return false;
			}
			/* if($("#user_id").val()==""){
				$("#user_id").tips({
					side:3,
		            msg:'请输入userid',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_id").focus();
			return false;
			} */
			if($("#user_name").val()==""){
				$("#user_name").tips({
					side:3,
		            msg:'请输入用户名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_name").focus();
			return false;
			}
			if($("#user_tel").val()==""){
				$("#user_tel").tips({
					side:3,
		            msg:'请输入用户电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_tel").focus();
			return false;
			}else if(!(/^1[0-9]{10}$/.test($("#user_tel").val())|| /^\d{3,4}-\d{7,8}$/.test($("#user_tel").val()))){
				$("#user_tel").tips({
					side:3,
		            msg:'请输入正确的电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_tel").focus();
			return false;
			}
			if($("#type").val()==""){
				$("#type").tips({
					side:3,
		            msg:'请输入类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#type").focus();
			return false;
			}
			/* if($("#status").val()==""){
				$("#status").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#status").focus();
			return false;
			}
			if($("#createby").val()==""){
				$("#createby").tips({
					side:3,
		            msg:'请输入创建人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#createby").focus();
			return false;
			}
			if($("#create_time").val()==""){
				$("#create_time").tips({
					side:3,
		            msg:'请输入创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#create_time").focus();
			return false;
			}
			if($("#updateby").val()==""){
				$("#updateby").tips({
					side:3,
		            msg:'请输入更新人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#updateby").focus();
			return false;
			}
			if($("#update_time").val()==""){
				$("#update_time").tips({
					side:3,
		            msg:'请输入更新时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#update_time").focus();
			return false;
			} */
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		
		function  toSelectUserList(){
			var delivery_org_id = $("#delivery_org_id").val();
			if($("#delivery_org_id").val()==""){
				$("#delivery_org_id").tips({
					side:3,
		            msg:'请先选择配送组织',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_org_id").focus();
			return false;
			}
			 top.jzts();
			 selectUserdiag = new top.Dialog();
			 selectUserdiag.Drag=true;
			 selectUserdiag.Title ="选择用户";
		     selectUserdiag.URL = '<%=basePath%>deliverytasks/listUserForSelect.do?delivery_org_id='+delivery_org_id;
			 selectUserdiag.Width = 1024;
			 selectUserdiag.Height = 600;
			 selectUserdiag.Modal = true;				//有无遮罩窗口
			 selectUserdiag. ShowMaxButton = true;	//最大化按钮
		     selectUserdiag.ShowMinButton = true;		//最小化按钮 
			 selectUserdiag.CancelEvent = function(){ //关闭事件
				/*  if(selectUserdiag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
				selectUserdiag.close();
			 };
			 selectUserdiag.show();
			 top.closeSelectrUserDiag =function(user_id ,user_name){
				   $('#user_id').val(user_id);
				   $('#user_name').val(user_name);
					selectUserdiag.close();

			}
		}
		function  toSelecteDliveryVehicleWorkList(){
			var delivery_org_id = $("#delivery_org_id").val();
			var work_day = $("#work_day").val();
			if($("#delivery_org_id").val()==""){
				$("#delivery_org_id").tips({
					side:3,
		            msg:'请先选择配送组织',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_org_id").focus();
			return false;
			}
			if($("#work_day").val()==""){
				$("#work_day").tips({
					side:3,
		            msg:'请先选择工作日',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#work_day").focus();
			return false;
			}

			 top.jzts();
			 selecteDliveryVehicleWorkDiag = new top.Dialog();
			 selecteDliveryVehicleWorkDiag.Drag=true;
			 selecteDliveryVehicleWorkDiag.Title ="选择车辆工作";
			 //选配送中的
		     selecteDliveryVehicleWorkDiag.URL = '<%=basePath%>/deliveryvehicle/listForSelect.do?status=1&operType=1&delivery_org_id='+delivery_org_id+"&work_day="+work_day;
			 selecteDliveryVehicleWorkDiag.Width = 1024;
			 selecteDliveryVehicleWorkDiag.Height = 600;
			 selecteDliveryVehicleWorkDiag.Modal = true;				//有无遮罩窗口
			 selecteDliveryVehicleWorkDiag. ShowMaxButton = true;	//最大化按钮
		     selecteDliveryVehicleWorkDiag.ShowMinButton = true;		//最小化按钮 
			 selecteDliveryVehicleWorkDiag.CancelEvent = function(){ //关闭事件
				/*  if(selecteDliveryVehicleWorkDiag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
				selecteDliveryVehicleWorkDiag.close();
			 };
			 selecteDliveryVehicleWorkDiag.show();
			 top.colseDliveryVehicleWorkDiag =function(delivery_vehicle_work_id ,delivery_org_id,delivery_org_name,vehicle_number,vehicle_contact,vehicle_tel){
				   $('#delivery_vehicle_work_id').val(delivery_vehicle_work_id);
				   $('#delivery_org_id').val(delivery_org_id);
				   $('#delivery_org_name').val(delivery_org_name);
				   $('#vehicle_number').val(vehicle_number);
				   $('#vehicle_contact').val(vehicle_contact);
				   $('#vehicle_tel').val(vehicle_tel);
					selecteDliveryVehicleWorkDiag.close();

			}
		}
		/**
			改变组织
		*/
		function changeOrg(){
			var selectText = $("#delivery_org_id  option:selected").text();
			$("#delivery_org_name").val(selectText);
			clearVal();
			
		}
		function changeWorkDay(){
			clearVal();
		}
		function clearVal(){
			$("#delivery_vehicle_work_id").val("");
			$("#vehicle_number").val("");
			$("#vehicle_contact").val("");
			$("#vehicle_tel").val("");
			$("#user_id").val("");
			$("#user_name").val("");
			$("#user_tel").val("");
			
			
		}
		</script>
</body>
</html>