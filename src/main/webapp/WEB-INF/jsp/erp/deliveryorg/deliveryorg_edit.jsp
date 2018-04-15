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
					
					<form action="deliveryorg/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="delivery_org_id" id="delivery_org_id" value="${pd.delivery_org_id}"/>
						<input type="hidden" name="delivery_org_code" id="delivery_org_code" value="${pd.delivery_org_code}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
<%-- 							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">编码:</td>
								<td><input type="text" readonly="readonly" name="delivery_org_code" id="delivery_org_code" value="${pd.delivery_org_code}" maxlength="50" placeholder="这里输入编码" title="编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">人员ID:</td>
								<td><input type="text" name="user_id" id="user_id" value="${pd.user_id}" maxlength="50" placeholder="这里输入人员ID" title="人员ID" style="width:98%;"/></td>
							</tr>
							 --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">名称:</td>
								<td><input type="text" name="delivery_org_name" id="delivery_org_name" value="${pd.delivery_org_name}" maxlength="50" placeholder="这里输入名称" title="名称" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系人:</td>
								<td>
									<input type="hidden" name="user_id" id="user_id" value="${pd.user_id}"/>
									<input type="text" readonly="readonly" name="user_name" id="user_name" value="${pd.user_name}" maxlength="50" placeholder="这里输入联系人" title="联系人" style="width:70%;"/>
									<a class="btn btn-mini btn-primary" onclick="selectUser();">选择人员</a>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td><input type="text" name="user_tel" id="user_tel" value="${pd.user_tel}" maxlength="50" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="user_address" id="user_address" value="${pd.user_address}" maxlength="128" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
								<input type="hidden" name="type" id="type" value="0" />
								<input type="hidden" name="status" id="status" value="0" />
							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">类型:</td>
								<td><input type="number" name="type" id="type" value="${pd.type}" maxlength="32" placeholder="这里输入类型" title="类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">配送仓库:</td>
								<td>
									<input type="hidden" name="store_sn" id="store_sn" value="${pd.store_sn}"/>
									<input type="text" readonly="readonly" name="store_name" id="store_name" value="${pd.store_name}" maxlength="50" placeholder="这里输入配送仓库" title="配送仓库" style="width:70%;"/>
									<a class="btn btn-mini btn-primary" onclick="selectFromStormSn();">选择仓库</a>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备货仓库:</td>
								<td><input type="hidden" name="delivery_store_sn" id="delivery_store_sn" value="${pd.delivery_store_sn}"/>
									<input type="text" readonly="readonly" name="delivery_store_name" id="delivery_store_name" value="${pd.delivery_store_name}" maxlength="50" placeholder="这里输入备货仓库" title="备货仓库" style="width:70%;"/>
									<a class="btn btn-mini btn-primary" onclick="selectToStormSn();">选择仓库</a>
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
			if($("#delivery_org_name").val()==""){
				$("#delivery_org_name").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_org_name").focus();
			return false;
			}
			if($("#user_id").val()==""){
				$("#user_id").tips({
					side:3,
		            msg:'请输入人员ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_id").focus();
			return false;
			}
			if($("#user_name").val()==""){
				$("#user_name").tips({
					side:3,
		            msg:'请输入联系人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_name").focus();
			return false;
			}
			
			var myreg = /1[34578]\d{9}/;
			if($("#user_tel").val()==""){
				$("#user_tel").tips({
					side:3,
		            msg:'请输入电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_tel").focus();
				return false;
			}else if($("#user_tel").val().length != 11 || !myreg.test($("#user_tel").val())){
				$("#user_tel").tips({
					side:3,
		            msg:'手机号格式不正确',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#user_tel").focus();
				return false;
			}
			
			
			if($("#user_address").val()==""){
				$("#user_address").tips({
					side:3,
		            msg:'请输入地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_address").focus();
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
			if($("#status").val()==""){
				$("#status").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#status").focus();
			return false;
			}
			if($("#store_name").val()==""){
				$("#store_name").tips({
					side:3,
		            msg:'请选择配送仓库',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_name").focus();
			return false;
			}
			if($("#delivery_store_name").val()==""){
				$("#delivery_store_name").tips({
					side:3,
		            msg:'请选择备货仓库',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_store_name").focus();
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
		var selectFromStormSnDiag = null;
		var selectToStormSnDiag = null;
		
		//选择调出仓库
		function selectFromStormSn(){
			 var types = "0";
			 top.jzts();
			 selectFromStormSnDiag = new top.Dialog();
			 selectFromStormSnDiag.Drag=true;
			 selectFromStormSnDiag.Title ="选择调出仓库";
			 selectFromStormSnDiag.URL = '<%=basePath%>szystore/list.do?types='+types;
			 selectFromStormSnDiag.Width = 1250;
			 selectFromStormSnDiag.Height = 755;
			 selectFromStormSnDiag.Modal = true;				//有无遮罩窗口
			 selectFromStormSnDiag. ShowMaxButton = true;	//最大化按钮
			 selectFromStormSnDiag.ShowMinButton = true;		//最小化按钮
			 selectFromStormSnDiag.CancelEvent = function(){ //关闭事件
				 selectFromStormSnDiag.close();
			 };
			 selectFromStormSnDiag.show();
		}
		
		//关闭选择调出仓库页面
		function closeFromStormSnDiag(sn,name){
			$("#store_sn").val(sn);
			$("#store_name").val(name);
			selectFromStormSnDiag.close();
		}
		
		//选择调入仓库
		function selectToStormSn(){
			 var types = "0";
			 top.jzts();
			 selectToStormSnDiag = new top.Dialog();
			 selectToStormSnDiag.Drag=true;
			 selectToStormSnDiag.Title ="选择调入仓库";
			 selectToStormSnDiag.URL = '<%=basePath%>szystore/listTo.do?types='+types;
			 selectToStormSnDiag.Width = 1250;
			 selectToStormSnDiag.Height = 755;
			 selectToStormSnDiag.Modal = true;				//有无遮罩窗口
			 selectToStormSnDiag. ShowMaxButton = true;	//最大化按钮
			 selectToStormSnDiag.ShowMinButton = true;		//最小化按钮
			 selectToStormSnDiag.CancelEvent = function(){ //关闭事件
				 selectToStormSnDiag.close();
			 };
			 selectToStormSnDiag.show();
		}
		
		//关闭选择调入仓库页面
		function closeToStormSnDiag(sn,name){
			$("#delivery_store_sn").val(sn);
			$("#delivery_store_name").val(name);
			selectToStormSnDiag.close();
		}
		
		var selectUserDiag = null;
		
		//选择人员
		function selectUser(){
			 top.jzts();
			 selectUserDiag = new top.Dialog();
			 selectUserDiag.Drag=true;
			 selectUserDiag.Title ="选择人员";
			 selectUserDiag.URL = '<%=basePath%>deliveryorg/userListSelect.do';
			 selectUserDiag.Width = 1250;
			 selectUserDiag.Height = 755;
			 selectUserDiag.Modal = true;				//有无遮罩窗口
			 selectUserDiag. ShowMaxButton = true;	//最大化按钮
			 selectUserDiag.ShowMinButton = true;		//最小化按钮
			 selectUserDiag.CancelEvent = function(){ //关闭事件
				 selectUserDiag.close();
			 };
			 selectUserDiag.show();
		}
		//关闭选择调出仓库页面
		function closSelectUserDiag(sn,name){
			$("#user_id").val(sn);
			$("#user_name").val(name);
			selectUserDiag.close();
		}
		</script>
</body>
</html>