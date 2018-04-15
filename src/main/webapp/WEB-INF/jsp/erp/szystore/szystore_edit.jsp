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
					
					<form  name="Form" id="Form" method="post">
						<input type="hidden" name="store_id" id="store_id" value="${pd.store_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
					
						<table id="table_report" class="table table-striped table-bordered table-hover">
						
						
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
								<td><input type="text" name="store_name" id="store_name" value="${pd.store_name}" maxlength="20" placeholder="仓库名称" title="仓库名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库类型:</td>
								<td>
								
									<select class="chosen-select form-control" name="store_type_id" id="store_type_id" data-placeholder="请选择" style="width:98%;">
									<option value="1" ${pd.store_type_id=="1"?'selected':''}>总仓虚拟仓</option>
									<option value="2" ${pd.store_type_id=="2"?'selected':''} >大区仓</option>
									<option value="3" ${pd.store_type_id=="3"?'selected':''} >城市仓</option>
									<option value="4" ${pd.store_type_id=="4"?'selected':''} >门店仓</option>
									<option value="5" ${pd.store_type_id=="5"?'selected':''} >OEM仓</option>
									<option value="6" ${pd.store_type_id=="6"?'selected':''} >总仓</option>
									<option value="7" ${pd.store_type_id=="7"?'selected':''} >俱乐部虚拟仓</option>
								  	</select>
								</td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库类别:</td>
								<td>
								
									<select class="chosen-select form-control" name="store_type_sort" id="store_type_sort" data-placeholder="请选择" style="width:98%;">
									<option value="0" ${pd.store_type_sort=="0"?'selected':''} >良品仓</option>
									<option value="1" ${pd.store_type_sort=="1"?'selected':''} >不良品仓</option>
									<option value="2" ${pd.store_type_sort=="2"?'selected':''} >物流仓</option>
									
									<option value="4" ${pd.store_type_sort=="4"?'selected':''} >虚拟仓</option>
									<option value="5" ${pd.store_type_sort=="5"?'selected':''} >报损仓</option>
									<option value="6" ${pd.store_type_sort=="6"?'selected':''} >待检仓</option>
								  	</select>
								</td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系人:</td>
								<td><input type="text" name="contact" id="contact" value="${pd.contact }" maxlength="32" placeholder=""  style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td><input type="text" name="tel" id="tel" value="${pd.tel }" maxlength="20" placeholder="" title="" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="address" id="address" value="${pd.address }" maxlength="60" placeholder="" title="" style="width:98%;"/></td>
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
			
			if($("#store_name").val()==""){
				$("#store_name").tips({
					side:3,
		            msg:'请输入仓库名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_name").focus();
			return false;
			}
			
			$.ajax({
				  type: 'POST',
				  url: '<%=basePath%>szystore/edit.do',
				  data: {'store_id':$("#store_id").val(),'store_name':$("#store_name").val(),'store_type_id':$("#store_type_id").val(),'store_type_sort':$("#store_type_sort").val(),'contact':$("#contact").val(),'tel':$("#tel").val(),'address':$("#address").val()},
				  success: function(data){
					  if(data.msg=="success")
						{
						top.Dialog.close();
						}
					else
						{
						  alert(data.msg);
						 // top.Dialog.close();
						}
				  }
				}); 
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>