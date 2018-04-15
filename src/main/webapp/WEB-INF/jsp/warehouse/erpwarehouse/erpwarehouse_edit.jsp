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
					
					<form action="erpwarehouse/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="warehouse_id" id="warehouse_id" value="${pd.warehouse_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库编码:</td>
								<td>
								   <c:if test="${msg == 'save'}">
								 		<input type="text" name="warehouse_code" id="warehouse_code" value="${pd.warehouse_code}" onblur="hasCode('${pd.warehouse_code }')" maxlength="50" placeholder="这里输入仓库编码" title="仓库编码" style="width:98%;"/>
								   </c:if>
								   <c:if test="${msg == 'edit'}">
								 		<input type="text" name="warehouse_code" id="warehouse_code" value="${pd.warehouse_code}" readonly="readonly" onblur="hasCode('${pd.warehouse_code }')" maxlength="50" placeholder="这里输入仓库编码" title="仓库编码" style="width:98%;"/>
								   </c:if>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
								<td><input type="text" name="warehouse_name" id="warehouse_name" value="${pd.warehouse_name}" maxlength="50" placeholder="这里输入仓库名称" title="仓库名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">上级仓库编码:</td>
								<td>
								 	<input type="text" name="parent_code" id="parent_code" value="${pd.parent_code}" onblur="hasParentCode('${pd.parent_code }')" maxlength="50" placeholder="这里输入上级仓库编码" title="上级仓库编码" style="width:98%;"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库类型:</td>
								<td>
								    <select class="chosen-select form-control" name="warehouse_type" id="warehouse_type" value="${pd.warehouse_type}" data-placeholder="请选择" style="vertical-align:top;width:98%;">
									<option value="0">总仓</option>
									<option value="1">大区仓库</option>
									<option value="2">城市仓库</option>
									<option value="3">门店仓库</option>
								  	</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库地址:</td>
								<td><input type="text" name="address" id="address" value="${pd.address}" maxlength="500" placeholder="这里输入仓库地址" title="仓库地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td>
								    <select class="chosen-select form-control" name="status" id="status" value="${pd.status}" data-placeholder="请选择" style="vertical-align:top;width:98%;">
									<option value="0">正常</option>
									<option value="1">冻结</option>
								  	</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">经度:</td>
								<td><input type="text" name="longitude" id="longitude" value="${pd.longitude}" maxlength="50" placeholder="这里输入经度" title="经度" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">纬度:</td>
								<td><input type="text" name="latitude" id="latitude" value="${pd.latitude}" maxlength="50" placeholder="这里输入纬度" title="纬度" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">区域:</td>
								<td>
								    <select class="chosen-select form-control" name="district_sn" id="district_sn" value="${pd.district_sn}" data-placeholder="请选择" style="vertical-align:top;width:98%;">
									    <c:choose>
											<c:when test="${not empty districts}">
												<c:forEach items="${districts}" var="district" varStatus="vs">
												     <option value="${district.district_sn}">${district.district_name}</option>
												</c:forEach>
											</c:when>
										</c:choose>
									</select>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save('${msg }');">保存</a>
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
		function save(msg){
			if($("#warehouse_code").val()==""){
				$("#warehouse_code").tips({
					side:3,
		            msg:'请输入仓库编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#warehouse_code").focus();
			return false;
			}
			if($("#warehouse_name").val()==""){
				$("#warehouse_name").tips({
					side:3,
		            msg:'请输入仓库名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#warehouse_name").focus();
			return false;
			}
			if($("#warehouse_type").val()==""){
				$("#warehouse_type").tips({
					side:3,
		            msg:'请输入仓库类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#warehouse_type").focus();
			return false;
			}
			if($("#address").val()==""){
				$("#address").tips({
					side:3,
		            msg:'请输入仓库地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#address").focus();
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
			if($("#longitude").val()==""){
				$("#longitude").tips({
					side:3,
		            msg:'请输入经度',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#longitude").focus();
			return false;
			}
			if($("#latitude").val()==""){
				$("#latitude").tips({
					side:3,
		            msg:'请输入纬度',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#latitude").focus();
			return false;
			}
			if($("#district_sn").val()==""){
				$("#district_sn").tips({
					side:3,
		            msg:'请输入区域',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#district_sn").focus();
			return false;
			}
			if(msg == 'save'){
				var code = $.trim($("#warehouse_code").val());
				var parent_code = $.trim($("#parent_code").val());
				$.ajax({
					type: "POST",
					url: '<%=basePath%>erpwarehouse/hasCodeAndParentCode.do',
			    	data: {warehouse_code:code},
					dataType:'json',
					cache: false,
					success: function(data){
						 if("success" != data.result){
							 $("#address").tips({
									side:3,
						            msg:'该仓库已存在，不能重复添加',
						            bg:'#AE81FF',
						            time:3
						        });
							 $("#warehouse_code").val('');
							 $("#parent_code").val('');
						 }else{
							 $("#Form").submit();
							 $("#zhongxin").hide();
							 $("#zhongxin2").show();
						 }
					}
				});
			}else{
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		}
		
		//判断仓库编码是否存在
		function hasCode(warehouse_code){
			var code = $.trim($("#warehouse_code").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>erpwarehouse/hasCode.do',
		    	data: {warehouse_code:code},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" != data.result){
						 $("#warehouse_code").tips({
								side:3,
					            msg:'仓库编码 '+code+' 不存在',
					            bg:'#AE81FF',
					            time:3
					        });
						 $("#warehouse_code").val('');
					 }
				}
			});
		}
		
		//判断上级仓库编码是否存在
		function hasParentCode(warehouse_code){
			var code = $.trim($("#parent_code").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>erpwarehouse/hasCode.do',
		    	data: {warehouse_code:code},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" != data.result){
						 $("#parent_code").tips({
								side:3,
					            msg:'上级仓库编码'+code+' 不存在',
					            bg:'#AE81FF',
					            time:3
					        });
						 $("#parent_code").val('');
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