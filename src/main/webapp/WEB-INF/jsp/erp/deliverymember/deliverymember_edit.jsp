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
					
					<form action="deliverymember/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="delivery_member_id" id="delivery_member_id" value="${pd.delivery_member_id}"/>
						<input type="hidden" name="delivery_org_id" id="delivery_org_id" value="${pd.delivery_org_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">名称:</td>
								<td>
								<input type="hidden" name="user_id" id="user_id" value="${pd.user_id}"/>
								<input type="text" readonly="readonly" name="user_name" id="user_name" value="${pd.user_name}" maxlength="100" placeholder="这里输入名称" title="名称" style="width:70%;"/>
								<c:if test="${msg == 'save' }">
									<a class="btn btn-mini btn-primary"  onclick="selectUser();">选择配送人员</a>
								</c:if>
								</td>
							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">配送组织:</td>
								<td>
									<input type="hidden" name="delivery_org_id" id="delivery_org_id" value="${pd.delivery_org_id}"/>
									<input type="text" readonly="readonly" name="delivery_org_name" id="delivery_org_name" value="${pd.delivery_org_name}" maxlength="32" placeholder="这里输入配送组织" title="配送组织" style="width:70%;"/>
									<a class="btn btn-mini btn-primary" onclick="selectOrg();">选择配送组织</a>
								</td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">人员公司:</td>
								<td>
								<input type="hidden" name="delivery_user_company_id" id="delivery_user_company_id" value="${pd.delivery_user_company_id}"/>
								<input type="text" readonly="readonly" name="delivery_user_company_name" id="delivery_user_company_name" value="${pd.delivery_user_company_name}" maxlength="32" placeholder="这里输入人员公司" title="人员公司" style="width:70%;"/>								
									<a class="btn btn-mini btn-primary" onclick="selectUserCompany();">选择人员公司</a>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">人员类型:</td>
								<td>
									<select class="chosen-select form-control" name="type" id="type" data-placeholder="请选择" style="vertical-align:top;width: 98%;">
									<option value="1" ${pd.type=="1"?'selected':''} >自营</option>
									<option value="2" ${pd.type=="2"?'selected':''}>第三方</option>
								  	</select>
								 </td>
							</tr>
							<input type="hidden" name="work_type" id="work_type" value="0"/>
							<input type="hidden" name="status" id="status" value="0"/>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">岗位类型:</td>
								<td><input type="number" name="work_type" id="work_type" value="${pd.work_type}" maxlength="32" placeholder="这里输入岗位类型" title="岗位类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
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
			if($("#user_id").val()==""){
				$("#user_id").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_id").focus();
			return false;
			}
			if($("#delivery_org_id").val()==""){
				$("#delivery_org_id").tips({
					side:3,
		            msg:'请选择配送组织',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_org_id").focus();
			return false;
			}
			
			if($("#type").val()==""){
				$("#type").tips({
					side:3,
		            msg:'请输入人员类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#type").focus();
			return false;
			}
			
			if($("#type").val()=="1"){
				$("#delivery_user_company_id").val("0");
			}else{
				if($("#delivery_user_company_id").val()=="" || $("#delivery_user_company_id").val()=="0"){
					$("#delivery_user_company_id").tips({
						side:3,
			            msg:'请选择人员公司',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#delivery_user_company_id").focus();
				return false;
				}
			}
			
			if($("#work_type").val()==""){
				$("#work_type").tips({
					side:3,
		            msg:'请输入岗位类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#work_type").focus();
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

			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
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
		function closSelectUserDiag(id,name){
			$("#user_id").val(id);
			$("#user_name").val(name);
			selectUserDiag.close();
		}
		
		var selectOrgDiag = null;
		
		//选择人员
		function selectOrg(){
			 selectOrgDiag = new top.Dialog();
			 selectOrgDiag.Drag=true;
			 selectOrgDiag.Title ="选择组织";
			 selectOrgDiag.URL = '<%=basePath%>deliveryorg/listSelect.do';
			 selectOrgDiag.Width = 1250;
			 selectOrgDiag.Height = 755;
			 selectOrgDiag.Modal = true;				//有无遮罩窗口
			 selectOrgDiag. ShowMaxButton = true;	//最大化按钮
			 selectOrgDiag.ShowMinButton = true;		//最小化按钮
			 selectOrgDiag.CancelEvent = function(){ //关闭事件
				 selectOrgDiag.close();
			 };
			 selectOrgDiag.show();
		}
		function closeSelectOrg(id,name){
			$("#delivery_org_id").val(id);
			$("#delivery_org_name").val(name);
			selectOrgDiag.close();
			$("#delivery_user_company_id").val("");
			$("#delivery_user_company_name").val("");
		}
		
		var closSelectUserCompanyDiag = null;
		//选择人员
		function selectUserCompany(){
			if($("#delivery_org_id").val()==""){
				$("#delivery_org_id").tips({
					side:3,
		            msg:'请选择配送组织',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#delivery_org_id").focus();
				return false;
			}

			 closSelectUserCompanyDiag = new top.Dialog();
			 closSelectUserCompanyDiag.Drag=true;
			 closSelectUserCompanyDiag.Title ="选择人员公司";
			 closSelectUserCompanyDiag.URL = '<%=basePath%>deliveryusercompany/listSelect.do?delivery_org_id='+$("#delivery_org_id").val();
			 closSelectUserCompanyDiag.Width = 1250;
			 closSelectUserCompanyDiag.Height = 755;
			 closSelectUserCompanyDiag.Modal = true;				//有无遮罩窗口
			 closSelectUserCompanyDiag. ShowMaxButton = true;	//最大化按钮
			 closSelectUserCompanyDiag.ShowMinButton = true;		//最小化按钮
			 closSelectUserCompanyDiag.CancelEvent = function(){ //关闭事件
				 closSelectUserCompanyDiag.close();
			 };
			 closSelectUserCompanyDiag.show();
		}
		function closeSelectUserCompany(id,name){
			$("#delivery_user_company_id").val(id);
			$("#delivery_user_company_name").val(name);
			closSelectUserCompanyDiag.close();
		}
		</script>
</body>
</html>