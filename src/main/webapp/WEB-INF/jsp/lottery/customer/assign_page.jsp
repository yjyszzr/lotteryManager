<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>

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
							<form action="customer/updateSaler.do" name="assignForm" id="assignForm" method="post" enctype="multipart/form-data">
								<div id="zhongxin">
								<table style="width:95%;" >
									<input type="hidden"  id="user_name"  name="user_name" value=""  />
									<input type="hidden"  id="ids"  name="ids" value="${ids}"  />
									<tr>
										<td style="padding-top: 20px;">
											<select  name="user_id" id="user_id" data-placeholder="请选择"   style="width:262px;" onChange="changeUserSelect();"  >
											<option value="" selected="selected">请指定销售人员</option>
											<c:forEach items="${pageDataList}" var="var" varStatus="vs">
												<option value="${var.USER_ID}" >${var.USERNAME}</option>
											</c:forEach>
										</select>
										</td>
									</tr>
									<tr>
										<td style="text-align: center;padding-top: 10px;">
											<a class="btn btn-mini btn-primary" onclick="save();">确认</a>
											<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
										</td>
									</tr>
								</table>
								</div>
								<div id="zhongxin2" class="center" style="display:none"><br/><img src="static/images/jzx.gif" /><br/><h4 class="lighter block green"></h4></div>
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

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 上传控件 -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		function changeUserSelect(){
		    var deptsSelect = $("#user_id").find("option:selected").text();
		    $("#user_name").val(deptsSelect);
		}
		function getUserSelect(){       
		    return 
		}
		 
		
		//保存
		function save(){
			if($("#user_id").val()==""){
				$("#user_id").tips({
					side:3,
		            msg:'请选择销售人员',
		            bg:'#AE81FF',
		            time:3
		        });
				return false;
			}
			$("#assignForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	</script>


</body>
</html>