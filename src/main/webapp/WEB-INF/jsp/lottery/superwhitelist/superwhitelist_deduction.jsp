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
					
					<form action="superwhitelist/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="user_id" id="user_id" value="${pd.user_id}"/>
						<input type="hidden" name="store_id" id="store_id" value="${pd.store_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户id:</td>
								<td>						
									 ${pd.user_id}
								</td>	 
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户名:</td>
								<td id="user_name">									
								 	${pd.user_name}
								 </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">店铺:</td>
								<td id="store_name">
									${pd.name}
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">账户余额:</td>
								<td id="money">
									${pd.money}
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">扣款原因:</td>
								<td>
									<select id="process_type" name="process_type"  value="" style ="width:42%">
								        <option value="4" >提取</option>
								        <option value="9" >输入错误</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">扣款金额:</td>
								<td><input type="number" name="number" id="number" value="" maxlength="32" placeholder="这里输入扣款金额" title="扣款金额" style="width:42%;"/></td>
							</tr>
							
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">确认</a>
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
			if($("#number").val()==""){
				$("#number").tips({
					side:3,
		            msg:'请输入扣款金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#frozen_money").focus();
				return false;
			}
			var money = $("#money").text().replace(/(^\s*)|(\s*$)/g, "")
			if($("#number").val() > money){
				$("#number").tips({
					side:3,
		            msg:'扣款金额应小于或等于帐户余额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#frozen_money").focus();
				return false;
			}
			
// 			if($("#user_name").val()==""){
// 				$("#user_name").tips({
// 					side:3,
// 		            msg:'请输入备注2',
// 		            bg:'#AE81FF',
// 		            time:2
// 		        });
// 				$("#user_name").focus();
// 			return false;
// 			}
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
			
			var str = "请确认，\n"
				+ "\n用户名：" + $("#user_name").text().replace(/(^\s*)|(\s*$)/g, "")
				+ "\n店铺：" + $("#store_name").text().replace(/(^\s*)|(\s*$)/g, "")
				+ "\n扣款金额：" + $("#number").val() 
				;
// 			alert("str=" + str)
			if (window.confirm(str)) {
				$("#Form").submit();
                return true;
             }else{
                //alert("取消");
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