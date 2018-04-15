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
					
					<form action="supplier/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="supplier_id" id="supplier_id" value="${pd.supplier_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">ID</td>
								<td><input type="number" name="supplier_id" id="supplier_id" value="${pd.supplier_id}" maxlength="32" placeholder="ID" title="备注1" style="width:98%;"/></td>
							</tr>
							<%-- <tr  >
								<td style="width:75px;text-align: right;padding-top: 13px;">类型:</td>
								<td><input type="number" name="supplier_type" id="supplier_type" value="${pd.supplier_type==null?0:pd.supplier_type}" maxlength="32" placeholder="这里输入备注2" title="备注2" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">编号:</td>
								<td><input type="text" name="supplier_sn" id="supplier_sn" value="${pd.supplier_sn}" readonly="readonly" maxlength="20" placeholder="供应商编号" title="供应商编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">名称:</td>
								<td><input type="text" name="supplier_name" id="supplier_name" value="${pd.supplier_name}" maxlength="60" placeholder="供应商名称" title="供应商名称" style="width:98%;"/></td>
							</tr>
							<tr  style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">:</td>
								<td><input type="text" name="deposit" id="deposit" value="${pd.deposit==null?0:pd.deposit}" maxlength="12" placeholder="这里输入备注5" title="备注5" style="width:98%;"/></td>
							</tr>
							<%-- <tr   style="display:none" >
								<td style="width:75px;text-align: right;padding-top: 13px;">备注6:</td>
								<td><input type="text" name="taxes" id="taxes" value="${pd.taxes==null?0:pd.taxes}" maxlength="12" placeholder="这里输入备注6" title="备注6" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系人:</td>
								<td><input type="text" name="contact" id="contact" value="${pd.contact}" maxlength="50" placeholder="这里输入联系人" title="联系人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td><input type="text" name="tel" id="tel" value="${pd.tel}" maxlength="100" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="address" id="address" value="${pd.address}" maxlength="255" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="supplier_remark" id="supplier_remark" value="${pd.supplier_remark}" maxlength="21845" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr>
						 	<tr  style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">是否启用</td>
								<td><input type="number" name="is_show" id="is_show" value="${pd.is_show==null?1:pd.is_show}" maxlength="32" placeholder="这里输入备注8" title="备注8" style="width:98%;"/></td>
							</tr> 
							<%-- <tr  style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">备注9:</td>
								<td><input type="number" name="is_deleted" id="is_deleted" value="${pd.is_deleted==null?0:pd.is_deleted}" maxlength="32" placeholder="这里输入备注9" title="备注9" style="width:98%;"/></td>
							</tr> 
							<trstyle="display:none" >
								<td style="width:75px;text-align: right;padding-top: 13px;">备注10:</td>
								<td><input type="number" name="admin_id" id="admin_id" value="${pd.admin_id==null?0:pd.admin_id}" maxlength="32" placeholder="这里输入备注10" title="备注10" style="width:98%;"/></td>
							</tr>  --%>
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
		/* 	if($("#supplier_id").val()==""){
				$("#supplier_id").tips({
					side:3,
		            msg:'请输入备注1',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#supplier_id").focus();
			return false;
			}
			if($("#supplier_type").val()==""){
				$("#supplier_type").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#supplier_type").focus();
			return false;
			} */
			if($("#supplier_sn").val()==""){
				$("#supplier_sn").tips({
					side:3,
		            msg:'请输入编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#supplier_sn").focus();
			return false;
			}
			if($("#supplier_name").val()==""){
				$("#supplier_name").tips({
					side:3,
		            msg:'请输入供应商名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#supplier_name").focus();
			return false;
			}
			/* if($("#deposit").val()==""){
				$("#deposit").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#deposit").focus();
			return false;
			}
			if($("#taxes").val()==""){
				$("#taxes").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#taxes").focus();
			return false;
			} */
			if($("#contact").val()==""){
				$("#contact").tips({
					side:3,
		            msg:'请输入联系人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#contact").focus();
			return false;
			}
			var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
			if($("#tel").val()==""){
				
				$("#tel").tips({
					side:3,
		            msg:'输入手机号',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#tel").focus();
				return false;
			}else if($("#tel").val().length != 11 && !myreg.test($("#tel").val())){
				$("#tel").tips({
					side:3,
		            msg:'手机号格式不正确',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#tel").focus();
				return false;
			}
			/* if($("#address").val()==""){
				$("#address").tips({
					side:3,
		            msg:'请输入地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#address").focus();
			return false;
			} */
			/* if($("#supplier_remark").val()==""){
				$("#supplier_remark").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#supplier_remark").focus();
			return false;
			} */
			/* if($("#is_show").val()==""){
				$("#is_show").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_show").focus();
			return false;
			}
			if($("#is_deleted").val()==""){
				$("#is_deleted").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_deleted").focus();
			return false;
			}
			if($("#admin_id").val()==""){
				$("#admin_id").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#admin_id").focus(); 
			return false;
			}*/
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