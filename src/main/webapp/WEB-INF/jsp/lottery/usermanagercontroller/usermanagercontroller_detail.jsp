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
	<button class="button" style="border-radius: 5px;"  onclick="sendVerifyCode('${entity.user_id}');">发送验证码</button>  
	<c:choose>
		<c:when test="${entity.user_status != 2}">
			<button class="button" style="border-radius: 5px;"  onclick="freezAccount('${entity.user_id}')">账号冻结</button>  
		</c:when> 
		<c:otherwise>
			<button class="button" style="border-radius: 5px;"  onclick="unFreezAccount('${entity.user_id}')">解冻该账号</button>  
		</c:otherwise> 
	</c:choose>
	<c:choose>
		<c:when test="${entity.is_real == 1}">
			<button class="button"  style="border-radius: 5px;"  onclick="realNameVerify('${entity.user_id}')">实名认证</button> 
		</c:when>
		<c:otherwise>
			<button class="button" style="border-radius: 5px;"  onclick="unRealNameVerify('${entity.user_id}')">未认证</button> 
		</c:otherwise>
	</c:choose>
	<div style="padding-top: 12px;">
		<label class="col-sm-3 control-label no-padding-right" for="form-field-1">该用户的交易版终极开关：</label>
		<div>
		 	<input style="width:200px" type="range" min=0 max=2 step=1 id="myRange" maxlength="18" value="${entity.is_business}" onchange="myFunction('${entity.user_id}')">
			<p id="demo"></p>
		</div>
	</div>
	
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
								<td style="padding: 13px;"> <lable >${entity.user_id}</lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户昵称:</td>
								<td style="padding: 13px;"> <lable >${entity.nickname} </lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">性别:</td>
								<td style="padding: 13px;"> 
									<lable >
										<c:if test="${!entity.sex}">男</c:if>
										<c:if test="${entity.sex}">女</c:if>
									</lable>
								</td>  
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号:</td>
								<td style="padding: 13px;"> <lable >${entity.mobile} </lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">真实姓名:</td>
								<td style="padding: 13px;"> <lable >${rentity.real_name} </lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">身份证号:</td>
								<td style="padding: 13px;"> <lable >${rentity.id_code} </lable></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">年龄:</td>
								<td style="padding: 13px;"> <lable >${IDCardUtil.getAgeByIdCard(rentity.id_code)} 岁</lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址或IP:</td>
<%-- 								<td style="padding: 13px;"> <lable >${rentity.address_info}</lable></td> --%>
								<td style="padding: 13px;"> <lable >${entity.reg_ip}</lable></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td style="padding: 13px;"> <lable >
									<c:choose>
										<c:when test="${entity.user_status == 0 }">正常</c:when>
										<c:when test="${entity.user_status == 1 }"><lable style = "color:red">锁定</c:when>
										<c:when test="${entity.user_status == 2 }"><lable style = "color:red">冻结</c:when>
									</c:choose>
								 </lable></td>
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
									<th class="center">是否默认卡</th>
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
											<td class='center'>
												<c:choose>
													<c:when test="${var.status}">是</c:when>
													<c:otherwise>否</c:otherwise>
												</c:choose>
											 </td>
											<td class="center">${DateUtil.toDateStr(var.add_time*1000)}</td>
											<td class="center">${DateUtil.toDateStr(var.last_time*1000)}</td>
											<td class='center'>
												<c:choose>
													<c:when test="${var.is_delete == 0 }">未解绑</c:when>
													<c:otherwise>已解绑</c:otherwise>
												</c:choose>
											 </td>
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
	$(top.hangge());//关闭加载状态
	
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
		
		
		function myFunction(user_id) {
		    var x = document.getElementById("myRange").value;
		    var text = ''
		    var isBusiness = 1;
		    switch(x){
		       case '1': text='开';isBusiness=1; break;
		       case '0': text='关';isBusiness=0; break;
		       case '2': text='失效';isBusiness=2; break;
		    }
		    document.getElementById("demo").innerHTML = text;
		    
			bootbox.confirm("确定要改变开关信息吗?", function(result) {
				if(result) {
					var url = '<%=basePath%>usermanagercontroller/changeUserSwitch.do?is_business='+isBusiness+'&user_id='+user_id;
					window.location.href = url;
				}
			});
		}
		
		function myFunctionShow() {
		    var x = document.getElementById("myRange").value;
		    var text = ''
		    var isBusiness = 1;
		    switch(x){
		       case '1': text='开'; break;
		       case '0': text='关'; break;
		       case '2': text='失效';break;
		    }
		    document.getElementById("demo").innerHTML = text;
		}
		myFunctionShow();
		
		
	</script>

</body>
</html>