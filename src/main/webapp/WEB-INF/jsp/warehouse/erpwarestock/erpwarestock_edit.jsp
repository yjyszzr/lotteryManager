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
					
					<form action="erpwarestock/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="warestock_id" id="warestock_id" value="${pd.warestock_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库编码:</td>
								<td>
								   <c:if test="${msg == 'save'}">
								 		<input type="text" name="warehouse_code" id="warehouse_code" value="${pd.warehouse_code}" onblur="hasCode('${pd.warehouse_code }')" maxlength="50" placeholder="这里输入仓库编码" title="仓库编码" style="width:98%;"/>
								   </c:if>
								   <c:if test="${msg == 'edit'}">
								 		<input type="text" name="warehouse_code" id="warehouse_code" value="${pd.warehouse_code}" onblur="hasCode('${pd.warehouse_code }')" readonly="readonly" maxlength="50" placeholder="这里输入仓库编码" title="仓库编码" style="width:98%;"/>
								   </c:if>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品编码:</td>
								<td>
								   <c:if test="${msg == 'save'}">
								 		<input type="text" name="goods_sn" id="goods_sn" value="${pd.goods_sn}" maxlength="60" onblur="hasSn('${pd.goods_sn }')" placeholder="这里输入商品编码" title="商品编码" style="width:98%;"/>
								   </c:if>
								   <c:if test="${msg == 'edit'}">
								 		<input type="text" name="goods_sn" id="goods_sn" value="${pd.goods_sn}" readonly="readonly" maxlength="60" onblur="hasSn('${pd.goods_sn }')" placeholder="这里输入商品编码" title="商品编码" style="width:98%;"/>
								   </c:if>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">实际数量:</td>
								<td><input type="number" name="actual_quantity" id="actual_quantity" value="${pd.actual_quantity}" maxlength="32" placeholder="这里输入实际数量" title="实际数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">可用数量:</td>
								<td><input type="number" name="available_quantity" id="available_quantity" value="${pd.available_quantity}" maxlength="32" placeholder="这里输入可用数量" title="可用数量" style="width:98%;"/></td>
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
			if($("#actual_quantity").val()==""){
				$("#actual_quantity").tips({
					side:3,
		            msg:'请输入实际数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#actual_quantity").focus();
			return false;
			}
			if($("#available_quantity").val()==""){
				$("#available_quantity").tips({
					side:3,
		            msg:'请输入可用数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#available_quantity").focus();
			return false;
			}
			if(msg == 'save'){
				var code = $.trim($("#warehouse_code").val());
				var goods_sn = $.trim($("#goods_sn").val());
				$.ajax({
					type: "POST",
					url: '<%=basePath%>erpwarestock/hasCodeAndSn.do',
			    	data: {warehouse_code:code,goods_sn:goods_sn},
					dataType:'json',
					cache: false,
					success: function(data){
						 if("success" != data.result){
							 $("#warehouse_code").tips({
									side:3,
						            msg:'该仓库的该商品已存在，不能重复添加',
						            bg:'#AE81FF',
						            time:3
						        });
							 $("#warehouse_code").val('');
							 $("#goods_sn").val('');
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
		
		//判断商品编码是否存在
		function hasSn(goods_sn){
			var goods_sn = $.trim($("#goods_sn").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>erpgoods/hasSn.do',
		    	data: {goods_sn:goods_sn},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" != data.result){
						 $("#goods_sn").tips({
								side:3,
					            msg:'商品编码 '+goods_sn+' 不存在',
					            bg:'#AE81FF',
					            time:3
					        });
						 $("#goods_sn").val('');
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