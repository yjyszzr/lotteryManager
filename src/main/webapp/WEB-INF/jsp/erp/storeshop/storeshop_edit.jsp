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
					
					<form action="storeshop/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="store_shop_id" id="store_shop_id" value="${pd.store_shop_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
								<td><%-- <input type="number" name="store_id" id="store_id" value="${pd.store_id}" maxlength="32" placeholder="这里输入仓库id" title="仓库id" style="width:98%;"/> --%>
								<select class="chosen-select form-control" name="store_id" id="store_id" data-placeholder="请选择" style="width:98%;" onChange="getChangeStore()">
								<c:if test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
									    <c:if test="${var.store_id!=store_id}">
										<option value="${var.store_id}">${var.store_name}</option>
										</c:if>
										<c:if test="${var.store_id==store_id}">
									    <option value="${var.store_id}" selected>${var.store_name}</option>
									    </c:if>
									</c:forEach>
								</c:if>
								</select>
								<input name="store_name" id="store_name" value="${pd.store_name}" type="hidden" />
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">店铺名称:</td>
								<td><%-- <input type="number" name="shop_id" id="shop_id" value="${pd.shop_id}" maxlength="32" placeholder="这里输入店铺ID" title="店铺ID" style="width:98%;"/> --%>
								<select class="chosen-select form-control" name="shop_id" id="shop_id" data-placeholder="请选择" style="width:98%;" onChange="getChangeShop()">
								<c:if test="${not empty shopList}">
									<c:forEach items="${shopList}" var="var" varStatus="vs">
										<c:if test="${var.shop_id!=shop_id}">
									    <option value="${var.shop_id}">${var.shop_name}</option>
									    </c:if>
									    <c:if test="${var.shop_id==shop_id}">
									    <option value="${var.shop_id}" selected>${var.shop_name}</option>
									    </c:if>
									</c:forEach>
								</c:if>
								</select>
								<input name="shop_name" id="shop_name" value="${pd.shop_name}" type="hidden" />
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
	<!-- cookie -->
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			getChangeStore();
			getChangeShop();
			if($("#store_id").val()==""){
				$("#store_id").tips({
					side:3,
		            msg:'请输入仓库id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_id").focus();
			return false;
			}
			if($("#shop_id").val()==""){
				$("#shop_id").tips({
					side:3,
		            msg:'请输入店铺ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#shop_id").focus();
			return false;
			}
			var type = $.cookie("store_shop_jsp");
			var store_shop_id = 0
			if(type=="edit"){
				store_shop_id = $("#store_shop_id").val();
			}else if(type=="add"){
				store_shop_id = 0;
			}else{
				alert("程序bug！");
				return false;
			}
			$.ajax({
		        type:"post",
		        url:"storeshop/isSel.do",
		        scriptCharset:'utf-8',
		        dataType:'json',
		        cache:false,
		        data:{
		        	store_shop_id:store_shop_id,
		        	store_id:$("#store_id").val(),
		        	shop_id:$("#shop_id").val()
		        },
		        success:function (data) {
		        	if(data=="1"){
		        		alert("仓库id，店铺id只能一对一。");
		        	}else{
		        		$("#Form").submit();
		        	}
		        },  
		        error:function (xhr, ajaxOptions, thrownError) {  
					alert("ajax err");
		        }  
		    });
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		function getChangeStore(){
			var sel = document.getElementById("store_id");
			var index = sel.selectedIndex;
			document.getElementById("store_name").value = sel.options[index].text;

		}
		function getChangeShop(){
			var sel = document.getElementById("shop_id");
			var index = sel.selectedIndex;
			document.getElementById("shop_name").value = sel.options[index].text;
		}
		</script>
</body>
</html>