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
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号:</td>
								<td id="mobile">									
								 	${pd.mobile}
								 </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">平台来源:</td>
								<td id="store_name">
									球多多
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">充值金额:</td>
								<td id="money_limit">
									${pd.money_limit}
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">可提现金额:</td>
								<td id="money">
									${pd.money}
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">扣款原因:</td>
								<td>
									<select id="process_type" name="process_type"  value="" style ="width:42%">
								        <option value="4" >提现</option>
								        <option value="9" >输入错误</option>
									</select>
								</td>
							</tr>

							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">扣款位置:</td>
								<td>
									<select id="refound_loc" name="refound_loc"  value="" style ="width:42%">
										<option value="1" >充值金额</option>
										<option value="0" >可提现金额</option>
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
				$("#number").focus();
				return false;
			}

			debugger;
            var refoundLoc = $("#refound_loc").val();


			var money = $("#money").text().replace(/(^\s*)|(\s*$)/g, "");
            var moneyLimit = $("#money_limit").text().replace(/(^\s*)|(\s*$)/g, "");

			if(refoundLoc == "1"  && parseFloat($("#number").val()) > parseFloat(moneyLimit)){
				$("#number").tips({
					side:3,
		            msg:'扣款金额应小于或等于充值金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#number").focus();
				return false;
			}

            if(refoundLoc == "0"  && parseFloat($("#number").val()) > parseFloat(money)){
                $("#number").tips({
                    side:3,
                    msg:'扣款金额应小于或等于可提现金额',
                    bg:'#AE81FF',
                    time:2
                });
                $("#number").focus();
                return false;
            }

			try {
				var length = $("#number").val().toString().split(".")[1].length
				if (length > 2) {
					$("#number").tips({
						side:3,
			            msg:'小数点位最多支持两位',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#number").focus();
					return false;
				}
			} catch (e) {
				// TODO: handle exception
			}





			
			var str = "请确认，\n"
				+ "\n手机号：" + $("#mobile").text().replace(/(^\s*)|(\s*$)/g, "")
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