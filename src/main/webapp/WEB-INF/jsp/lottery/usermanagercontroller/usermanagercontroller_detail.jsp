<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.fh.util.IDCardUtil" %>
<%@page import="com.fh.util.DateUtil" %>

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

<style>
.button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 6px 16px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 10px;
    margin: 4px 2px;
    cursor: pointer;
}
</style>
	
<body class="no-skin">

<div style="padding-top: 12px;padding-bottom: 12px">
	<button class="button" onclick="sendVerifyCode('${entity.user_id}');">发送验证码</button>  
	<c:choose>
		<c:when test="${entity.user_status != 2}">
			<button class="button" onclick="freezAccount('${entity.user_id}')">账号冻结</button>  
		</c:when> 
		<c:otherwise>
			<button class="button" onclick="unFreezAccount('${entity.user_id}')">解冻该账号</button>  
		</c:otherwise> 
	</c:choose>
	<c:choose>
		<c:when test="${entity.is_real == 1}">
			<button class="button" onclick="realNameVerify('${entity.user_id}')">实名认证</button> 
		</c:when>
		<c:otherwise>
			<button class="button" onclick="unRealNameVerify('${entity.user_id}')">未认证</button> 
		</c:otherwise>
	</c:choose>
</div>

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					<form action="usermanagercontroller/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="user_id" id="user_id" value="${pd.user_id}"/>
						<div id="zhongxin" style="padding-top: 3px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户ID:</td>
								<td><input type="text" value="${entity.user_id}" maxlength="255" title="${entity.user_id}" style="width:28%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户昵称:</td>
								<td><input type="text" value="${entity.nickname}" maxlength="255" style="width:28%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">性别:</td>
								<td><input type="text" value="${entity.sex}" maxlength="255" style="width:28%;"/></td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号:</td>
								<td><input type="text" value="${entity.mobile}" maxlength="255" style="width:38%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">真实姓名:</td>
								<td><input type="text" value="${rentity.real_name}" maxlength="255" title="${rEntity.realname}" style="width:28%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">身份证号:</td>
								<td><input type="text" value="${rentity.id_code}" maxlength="255" style="width:68%;"/></td>
							</tr>
							
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">年龄:</td>
								<td><input type="text" value="${IDCardUtil.getAgeByIdCard(rentity.id_code)}" maxlength="255" style="width:38%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">地域:</td>
								<td><input type="text" value="${rentity.address_info}" maxlength="255" style="width:28%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;"></td>
								<td><input type="text" value="" maxlength="255" style="width:68%;"/></td>
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
	
	<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">银行卡号</th>
									<th class="center">开户行</th>
									<th class="center">状态</th>
									<th class="center">绑定时间</th>
									<th class="center">最近更新时间</th>
									<th class="center">是否解绑</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty list}">
									<c:forEach items="${list}" var="var" varStatus="vs">
										<tr>
											<td class='center'>${var.card_no}</td>
											<td class='center'>${var.bank_name}</td>
											<td class='center'>${var.status}</td>
											<td class="center">${DateUtil.toDateStr(var.add_time*1000)}</td>
											<td class="center">${DateUtil.toDateStr(var.last_time*1000)}</td>
											<td class='center'>${var.is_delete}</td>
										</tr>
									
									</c:forEach>
								</c:when>
								
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
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
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	
	<script type="text/javascript">
		function sendVerifyCode(user_id){
			var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送验证码";
			 diag.URL = '<%=basePath%>usermanagercontroller/phoneVerifySms.do?user_id='+user_id;
			 diag.Width = 250;
			 diag.Height = 155;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				//
				diag.close();
			 };
			 diag.show();
			 
		}
		
		function freezAccount(user_id){
			bootbox.confirm("确定要冻结该账号吗?", function(result) {
				if(result) {
					var url = '<%=basePath%>usermanagercontroller/freezAccount.do?op=frozen&user_id='+user_id;
					window.location.href = url;
				}
			});
		}
		
		function unFreezAccount(user_id){
			bootbox.confirm("确定要解冻该账号吗?", function(result) {
				if(result) {
					var url = '<%=basePath%>usermanagercontroller/freezAccount.do?op=unfrozen&user_id='+user_id;
					window.location.href = url;
				}
			});
		}
		
		function realNameVerify(user_id){
			bootbox.confirm("确定要清除该账号的实名认证信息?", function(result) {
				if(result) {
					var url = '<%=basePath%>usermanagercontroller/clearRealInfo.do?user_id='+user_id;
					window.location.href = url;
				}
			});
		}
		
		function unRealNameVerify(user_id){
			alert("用户" + user_id +"实名信息已清除~");
		}
	</script>

</body>
</html>