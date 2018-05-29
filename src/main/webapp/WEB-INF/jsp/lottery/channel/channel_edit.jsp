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
					
					<form action="channel/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="channel_id" id="channel_id" value="${pd.channel_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道名称:</td>
								<td><input type="text" name="channel_name" id="channel_name" value="${pd.channel_name}" maxlength="60" placeholder="这里输入渠道名称" title="渠道名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道号:</td>
								<td><input type="text" name="channel_num" id="channel_num" value="${pd.channel_num}" maxlength="60" placeholder="这里输入渠道号" title="渠道号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">佣金比例:</td>
								<td><input type="number" name="commission_rate" id="commission_rate" value="${pd.commission_rate}" maxlength="32" placeholder="这里输入佣金比例" title="佣金比例" style="width:90%;"/>%</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属企业:</td>
								<td>
									<select  name="channel_type" id="channel_type" value="${pd.channel_type }"  style="width:188px;">
												 	<option value =""   <c:if test="${channel_type == 0}">selected</c:if>>请选择</option>
												    <option value="1"     <c:if test="${pd.channel_type==1}">selected</c:if>>西安每一天便利店</option>
									    </select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道联系人:</td>
								<td><input type="text" name="channel_contact" id="channel_contact" value="${pd.channel_contact}" maxlength="60" placeholder="这里输入渠道联系人" title="渠道联系人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td><input type="text" name="channel_mobile" id="channel_mobile" value="${pd.channel_mobile}" maxlength="20" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="channel_address" id="channel_address" value="${pd.channel_address}" maxlength="255" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td style="width:75px;text-align: right;padding-top: 13px;">是否删除:</td> -->
<%-- 								<td><input type="number" name="deleted" id="deleted" value="${pd.deleted}" maxlength="32" placeholder="这里输入是否删除" title="是否删除" style="width:98%;"/></td> --%>
<!-- 							</tr> -->
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="remark" id="remark" value="${pd.remark}" maxlength="255" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
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
			if($("#channel_num").val()==""){
				$("#channel_num").tips({
					side:3,
		            msg:'请输入渠道号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_num").focus();
			return false;
			}
			if($("#commission_rate").val()==""){
				$("#commission_rate").tips({
					side:3,
		            msg:'请输入佣金比例',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#commission_rate").focus();
			return false;
			}
			if($("#channel_type").val()==""){
				$("#channel_type").tips({
					side:3,
		            msg:'请输入渠道类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_type").focus();
			return false;
			}
			if($("#channel_contact").val()==""){
				$("#channel_contact").tips({
					side:3,
		            msg:'请输入渠道联系人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_contact").focus();
			return false;
			}
			if($("#channel_mobile").val()==""){
				$("#channel_mobile").tips({
					side:3,
		            msg:'请输入电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_mobile").focus();
			return false;
			}
			if($("#channel_address").val()==""){
				$("#channel_address").tips({
					side:3,
		            msg:'请输入地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_address").focus();
			return false;
			}
			if($("#deleted").val()==""){
				$("#deleted").tips({
					side:3,
		            msg:'请输入是否删除',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#deleted").focus();
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
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>