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
					
					<form action="superwhitelist/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="user_id_id" id="user_id_id" value="${pd.user_id_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注2:</td>
								<td><input type="text" name="user_name" id="user_name" value="${pd.user_name}" maxlength="60" placeholder="这里输入备注2" title="备注2" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注4:</td>
								<td><input type="text" name="email" id="email" value="${pd.email}" maxlength="100" placeholder="这里输入备注4" title="备注4" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注5:</td>
								<td><input type="text" name="password" id="password" value="${pd.password}" maxlength="255" placeholder="这里输入备注5" title="备注5" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注6:</td>
								<td><input type="text" name="salt" id="salt" value="${pd.salt}" maxlength="100" placeholder="这里输入备注6" title="备注6" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注7:</td>
								<td><input type="text" name="nickname" id="nickname" value="${pd.nickname}" maxlength="200" placeholder="这里输入备注7" title="备注7" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注8:</td>
								<td><input type="number" name="sex" id="sex" value="${pd.sex}" maxlength="32" placeholder="这里输入备注8" title="备注8" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注9:</td>
								<td><input type="number" name="birthday" id="birthday" value="${pd.birthday}" maxlength="32" placeholder="这里输入备注9" title="备注9" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注10:</td>
								<td><input type="text" name="detail_address" id="detail_address" value="${pd.detail_address}" maxlength="255" placeholder="这里输入备注10" title="备注10" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注11:</td>
								<td><input type="text" name="headimg" id="headimg" value="${pd.headimg}" maxlength="255" placeholder="这里输入备注11" title="备注11" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注13:</td>
								<td><input type="text" name="user_money_limit" id="user_money_limit" value="${pd.user_money_limit}" maxlength="22" placeholder="这里输入备注13" title="备注13" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注14:</td>
								<td><input type="text" name="frozen_money" id="frozen_money" value="${pd.frozen_money}" maxlength="22" placeholder="这里输入备注14" title="备注14" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注15:</td>
								<td><input type="number" name="pay_point" id="pay_point" value="${pd.pay_point}" maxlength="32" placeholder="这里输入备注15" title="备注15" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注16:</td>
								<td><input type="number" name="rank_point" id="rank_point" value="${pd.rank_point}" maxlength="32" placeholder="这里输入备注16" title="备注16" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注17:</td>
								<td><input type="number" name="reg_time" id="reg_time" value="${pd.reg_time}" maxlength="32" placeholder="这里输入备注17" title="备注17" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注18:</td>
								<td><input type="text" name="reg_ip" id="reg_ip" value="${pd.reg_ip}" maxlength="40" placeholder="这里输入备注18" title="备注18" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注19:</td>
								<td><input type="number" name="last_time" id="last_time" value="${pd.last_time}" maxlength="32" placeholder="这里输入备注19" title="备注19" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注20:</td>
								<td><input type="text" name="last_ip" id="last_ip" value="${pd.last_ip}" maxlength="40" placeholder="这里输入备注20" title="备注20" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注21:</td>
								<td><input type="text" name="mobile_supplier" id="mobile_supplier" value="${pd.mobile_supplier}" maxlength="255" placeholder="这里输入备注21" title="备注21" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注22:</td>
								<td><input type="text" name="mobile_province" id="mobile_province" value="${pd.mobile_province}" maxlength="255" placeholder="这里输入备注22" title="备注22" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注23:</td>
								<td><input type="text" name="mobile_city" id="mobile_city" value="${pd.mobile_city}" maxlength="255" placeholder="这里输入备注23" title="备注23" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注24:</td>
								<td><input type="text" name="reg_from" id="reg_from" value="${pd.reg_from}" maxlength="10" placeholder="这里输入备注24" title="备注24" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注25:</td>
								<td><input type="text" name="surplus_password" id="surplus_password" value="${pd.surplus_password}" maxlength="255" placeholder="这里输入备注25" title="备注25" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注26:</td>
								<td><input type="text" name="pay_pwd_salt" id="pay_pwd_salt" value="${pd.pay_pwd_salt}" maxlength="100" placeholder="这里输入备注26" title="备注26" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注27:</td>
								<td><input type="number" name="user_status" id="user_status" value="${pd.user_status}" maxlength="32" placeholder="这里输入备注27" title="备注27" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注28:</td>
								<td><input type="number" name="pass_wrong_count" id="pass_wrong_count" value="${pd.pass_wrong_count}" maxlength="32" placeholder="这里输入备注28" title="备注28" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注29:</td>
								<td><input type="number" name="user_type" id="user_type" value="${pd.user_type}" maxlength="32" placeholder="这里输入备注29" title="备注29" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注30:</td>
								<td><input type="text" name="is_real" id="is_real" value="${pd.is_real}" maxlength="10" placeholder="这里输入备注30" title="备注30" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注31:</td>
								<td><input type="text" name="user_remark" id="user_remark" value="${pd.user_remark}" maxlength="21845" placeholder="这里输入备注31" title="备注31" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注32:</td>
								<td><input type="number" name="add_time" id="add_time" value="${pd.add_time}" maxlength="32" placeholder="这里输入备注32" title="备注32" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注33:</td>
								<td><input type="text" name="push_key" id="push_key" value="${pd.push_key}" maxlength="80" placeholder="这里输入备注33" title="备注33" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注34:</td>
								<td><input type="text" name="device_channel" id="device_channel" value="${pd.device_channel}" maxlength="16" placeholder="这里输入备注34" title="备注34" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注35:</td>
								<td><input type="number" name="is_business" id="is_business" value="${pd.is_business}" maxlength="32" placeholder="这里输入备注35" title="备注35" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注36:</td>
								<td><input type="number" name="lon" id="lon" value="${pd.lon}" maxlength="32" placeholder="这里输入备注36" title="备注36" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注37:</td>
								<td><input type="number" name="lat" id="lat" value="${pd.lat}" maxlength="32" placeholder="这里输入备注37" title="备注37" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注38:</td>
								<td><input type="text" name="city" id="city" value="${pd.city}" maxlength="20" placeholder="这里输入备注38" title="备注38" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注39:</td>
								<td><input type="text" name="province" id="province" value="${pd.province}" maxlength="20" placeholder="这里输入备注39" title="备注39" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注40:</td>
								<td><input type="number" name="has_third_user_id" id="has_third_user_id" value="${pd.has_third_user_id}" maxlength="32" placeholder="这里输入备注40" title="备注40" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注41:</td>
								<td><input type="number" name="is_super_white" id="is_super_white" value="${pd.is_super_white}" maxlength="32" placeholder="这里输入备注41" title="备注41" style="width:98%;"/></td>
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
			if($("#user_name").val()==""){
				$("#user_name").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_name").focus();
			return false;
			}
			if($("#email").val()==""){
				$("#email").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#email").focus();
			return false;
			}
			if($("#password").val()==""){
				$("#password").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#password").focus();
			return false;
			}
			if($("#salt").val()==""){
				$("#salt").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#salt").focus();
			return false;
			}
			if($("#nickname").val()==""){
				$("#nickname").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#nickname").focus();
			return false;
			}
			if($("#sex").val()==""){
				$("#sex").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sex").focus();
			return false;
			}
			if($("#birthday").val()==""){
				$("#birthday").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#birthday").focus();
			return false;
			}
			if($("#detail_address").val()==""){
				$("#detail_address").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#detail_address").focus();
			return false;
			}
			if($("#headimg").val()==""){
				$("#headimg").tips({
					side:3,
		            msg:'请输入备注11',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#headimg").focus();
			return false;
			}
			if($("#user_money_limit").val()==""){
				$("#user_money_limit").tips({
					side:3,
		            msg:'请输入备注13',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_money_limit").focus();
			return false;
			}
			if($("#frozen_money").val()==""){
				$("#frozen_money").tips({
					side:3,
		            msg:'请输入备注14',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#frozen_money").focus();
			return false;
			}
			if($("#pay_point").val()==""){
				$("#pay_point").tips({
					side:3,
		            msg:'请输入备注15',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pay_point").focus();
			return false;
			}
			if($("#rank_point").val()==""){
				$("#rank_point").tips({
					side:3,
		            msg:'请输入备注16',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#rank_point").focus();
			return false;
			}
			if($("#reg_time").val()==""){
				$("#reg_time").tips({
					side:3,
		            msg:'请输入备注17',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#reg_time").focus();
			return false;
			}
			if($("#reg_ip").val()==""){
				$("#reg_ip").tips({
					side:3,
		            msg:'请输入备注18',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#reg_ip").focus();
			return false;
			}
			if($("#last_time").val()==""){
				$("#last_time").tips({
					side:3,
		            msg:'请输入备注19',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#last_time").focus();
			return false;
			}
			if($("#last_ip").val()==""){
				$("#last_ip").tips({
					side:3,
		            msg:'请输入备注20',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#last_ip").focus();
			return false;
			}
			if($("#mobile_supplier").val()==""){
				$("#mobile_supplier").tips({
					side:3,
		            msg:'请输入备注21',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mobile_supplier").focus();
			return false;
			}
			if($("#mobile_province").val()==""){
				$("#mobile_province").tips({
					side:3,
		            msg:'请输入备注22',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mobile_province").focus();
			return false;
			}
			if($("#mobile_city").val()==""){
				$("#mobile_city").tips({
					side:3,
		            msg:'请输入备注23',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mobile_city").focus();
			return false;
			}
			if($("#reg_from").val()==""){
				$("#reg_from").tips({
					side:3,
		            msg:'请输入备注24',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#reg_from").focus();
			return false;
			}
			if($("#surplus_password").val()==""){
				$("#surplus_password").tips({
					side:3,
		            msg:'请输入备注25',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#surplus_password").focus();
			return false;
			}
			if($("#pay_pwd_salt").val()==""){
				$("#pay_pwd_salt").tips({
					side:3,
		            msg:'请输入备注26',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pay_pwd_salt").focus();
			return false;
			}
			if($("#user_status").val()==""){
				$("#user_status").tips({
					side:3,
		            msg:'请输入备注27',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_status").focus();
			return false;
			}
			if($("#pass_wrong_count").val()==""){
				$("#pass_wrong_count").tips({
					side:3,
		            msg:'请输入备注28',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pass_wrong_count").focus();
			return false;
			}
			if($("#user_type").val()==""){
				$("#user_type").tips({
					side:3,
		            msg:'请输入备注29',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_type").focus();
			return false;
			}
			if($("#is_real").val()==""){
				$("#is_real").tips({
					side:3,
		            msg:'请输入备注30',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_real").focus();
			return false;
			}
			if($("#user_remark").val()==""){
				$("#user_remark").tips({
					side:3,
		            msg:'请输入备注31',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_remark").focus();
			return false;
			}
			if($("#add_time").val()==""){
				$("#add_time").tips({
					side:3,
		            msg:'请输入备注32',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#add_time").focus();
			return false;
			}
			if($("#push_key").val()==""){
				$("#push_key").tips({
					side:3,
		            msg:'请输入备注33',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#push_key").focus();
			return false;
			}
			if($("#device_channel").val()==""){
				$("#device_channel").tips({
					side:3,
		            msg:'请输入备注34',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#device_channel").focus();
			return false;
			}
			if($("#is_business").val()==""){
				$("#is_business").tips({
					side:3,
		            msg:'请输入备注35',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_business").focus();
			return false;
			}
			if($("#lon").val()==""){
				$("#lon").tips({
					side:3,
		            msg:'请输入备注36',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#lon").focus();
			return false;
			}
			if($("#lat").val()==""){
				$("#lat").tips({
					side:3,
		            msg:'请输入备注37',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#lat").focus();
			return false;
			}
			if($("#city").val()==""){
				$("#city").tips({
					side:3,
		            msg:'请输入备注38',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#city").focus();
			return false;
			}
			if($("#province").val()==""){
				$("#province").tips({
					side:3,
		            msg:'请输入备注39',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#province").focus();
			return false;
			}
			if($("#has_third_user_id").val()==""){
				$("#has_third_user_id").tips({
					side:3,
		            msg:'请输入备注40',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#has_third_user_id").focus();
			return false;
			}
			if($("#is_super_white").val()==""){
				$("#is_super_white").tips({
					side:3,
		            msg:'请输入备注41',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_super_white").focus();
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