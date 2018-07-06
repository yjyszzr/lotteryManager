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
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
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
					
					<form action="channeldistributor/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="channel_distributor_id" id="channel_distributor_id" value="${pd.channel_distributor_id}"/>
						<input type="hidden" name="channel_name" id="channel_name" value=""/>
						<input type="hidden" name="mobile" id="mobile" value="${pd.mobile}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道名称:</td>
								<td>
										<select  class="chosen-select form-control"    name="channel_id" id="channel_id" value="${pd.channel_id }"  style="width:99%;">
												 	<option value ="0"  oldName=""   channelNum = ""  <c:if test="${channel_id == 0}">selected</c:if>>请选择</option>
												 	<c:forEach var="channel" items="${channelAll }" >
													     <option value="${channel.channel_id}"  oldName="${channel.channel_name}"   channelNum = "${channel.channel_num}" <c:if test="${pd.channel_id==channel.channel_id}">selected</c:if>>${channel.channel_name} </option>
													</c:forEach>
									    </select>
									    </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道分销号:</td>
								<td><input type="text" name="channel_distributor_num" id="channel_distributor_num"  autocomplete="off"  value="${pd.channel_distributor_num}" maxlength="60" placeholder="这里输入渠道分销号" title="渠道分销号" style="width:99%;"/></td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td style="width:75px;text-align: right;padding-top: 13px;">用户名:</td> -->
<!-- 								 	<td>    -->
<%-- 								 		<select  class="chosen-select form-control"   name="user_id"  id = "user_id" value="${pd.user_id }"  data-placeholder="请选择"   style="width:99%;"> --%>
<%-- 												 	<option value ="0"    oldName=""  mobile="" <c:if test="${user_id == 0}">selected</c:if>>请选择</option> --%>
<%-- 												 	<c:forEach var="user" items="${userAll }" > --%>
<%-- 													    <option value="${user.user_id}"  oldName="${user.user_name}"  mobile="${user.mobile }" <c:if test="${pd.userId==user.user_id}">selected</c:if>>${user.user_name}<c:if test="${not empty user.real_name }">（${user.real_name}）</c:if></option> --%>
<%-- 													</c:forEach> --%>
<!-- 									    </select> -->
<!-- 								    </td> -->
<!-- 							</tr> -->
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								 	<td>   
								 		<select  class="chosen-select form-control"   name="user_id"  id = "user_id" value="${pd.user_id }"   style="width:90%;">
												 	<option value ="0"    oldName=""  mobile="" <c:if test="${pd.user_id == 0}">selected</c:if>>请选择</option>
												 	<c:forEach var="user" items="${userAll }" >
													    <option value="${user.user_id}"  oldName="${user.user_name}"   mobile="${user.mobile }"  <c:if test="${pd.user_id == user.user_id}">selected</c:if>>${user.mobile}</option>
													</c:forEach>
									    </select>
								    </td>
							</tr>
						
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户名:</td>
								
								<td>
									<input type="text" name="user_name" id="user_name" value="${pd.user_name}" placeholder="这里输入用户名"  style="width:99%;" readonly/>
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td> -->
<%-- 								<td><input type="text" name="mobile" id="mobile" value="${pd.mobile}" maxlength="20" placeholder="这里输入电话" title="电话" style="width:99%;"/></td> --%>
<!-- 							</tr> -->
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">分销佣金比例:</td>
								<td><input  type="number" name="distributor_commission_rate" autocomplete="off"  id="distributor_commission_rate" value="${pd.distributor_commission_rate}" maxlength="255" placeholder="这里输入分销佣金比例'%'"  style="width:99%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="remark" id="remark" value="${pd.remark}" maxlength="255" autocomplete="off"  placeholder="这里输入备注" title="备注" style="width:99%;"/></td>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		$("#user_id").change(function(){
		    var mobile= $("#user_id").find("option:selected").attr("mobile");
		    var oldName= $("#user_id").find("option:selected").attr("oldName");
		    $("#user_name").val(oldName);
		    $("#mobile").val(mobile);
		});
		
		$("#channel_id").change(function(){
		    var channelNum= $("#channel_id").find("option:selected").attr("channelNum");
		    var oldName= $("#channel_id").find("option:selected").attr("oldName");
		    $("#channel_distributor_num").val(channelNum);
		    $("#channel_name").val(oldName);
		});
		
		
		//保存
		function save(){
			if($("#user_name").val()==""){
				$("#user_name").tips({
					side:3,
		            msg:'请输入用户名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_name").focus();
			return false;
			}
			if($("#channel_distributor_num").val()==""){
				$("#channel_distributor_num").tips({
					side:3,
		            msg:'请输入渠道分销号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_distributor_num").focus();
			return false;
			}
			if($("#mobile").val()==""){
				$("#mobile").tips({
					side:3,
		            msg:'请输入电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mobile").focus();
			return false;
			}
			if($("#distributor_commission_rate").val()==""){
				$("#distributor_commission_rate").tips({
					side:3,
		            msg:'请输入分销佣金比例',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#distributor_commission_rate").focus();
			return false;
			}
			if($("#channel_name").val()==""){
				$("#channel_name").tips({
					side:3,
		            msg:'请输入渠道名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_name").focus();
			return false;
			}
			if($("#remark").val()==""){
				$("#remark").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#remark").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
	$(function() {
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
		});
		
		</script>
</body>
</html>