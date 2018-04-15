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
					
					<form action="erpgoods/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="goods_id" id="goods_id" value="${pd.goods_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>
								<td><input type="text" name="goods_name" id="goods_name" value="${pd.goods_name}" maxlength="120" placeholder="这里输入商品名称" title="商品名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品编码:</td>
								<td>
								   <c:if test="${msg == 'save'}">
								 		<input type="text" name="goods_sn" id="goods_sn" value="${pd.goods_sn}" maxlength="60" onblur="hasCode('${pd.goods_sn }')" placeholder="这里输入商品编码" title="商品编码" style="width:98%;"/>
								   </c:if>
								   <c:if test="${msg == 'edit'}">
								 		<input type="text" name="goods_sn" id="goods_sn" value="${pd.goods_sn}" maxlength="60" readonly="readonly" onblur="hasCode('${pd.goods_sn }')" placeholder="这里输入商品编码" title="商品编码" style="width:98%;"/>
								   </c:if>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">计量单位:</td>
								<td><input type="text" name="unit_name" id="unit_name" value="${pd.unit_name}" maxlength="50" placeholder="这里输入计量单位" title="计量单位" style="width:98%;"/></td>
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
			if($("#goods_name").val()==""){
				$("#goods_name").tips({
					side:3,
		            msg:'请输入商品名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_name").focus();
			return false;
			}
			if($("#goods_sn").val()==""){
				$("#goods_sn").tips({
					side:3,
		            msg:'请输入商品编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#goods_sn").focus();
			return false;
			}
			if($("#unit_name").val()==""){
				$("#unit_name").tips({
					side:3,
		            msg:'请输入计量单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#unit_name").focus();
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
			if(msg == 'save'){
				var goods_sn = $.trim($("#goods_sn").val());
				$.ajax({
					type: "POST",
					url: '<%=basePath%>erpgoods/hasSn.do',
			    	data: {goods_sn:goods_sn},
					dataType:'json',
					cache: false,
					success: function(data){
						 if("success" != data.result){
							 $("#Form").submit();
							 $("#zhongxin").hide();
							 $("#zhongxin2").show();
						 }else{
							 $("#goods_sn").tips({
									side:3,
						            msg:'该商品已存在，不能重复添加',
						            bg:'#AE81FF',
						            time:3
						        });
							 $("#goods_sn").val('');
						 }
					}
				});
			}else{
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>