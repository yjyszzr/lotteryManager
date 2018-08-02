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
	<%@ include file="../system/index/top.jsp"%>

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
					
					<form action="switchappconfig/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">app版本号*</td>
<%-- 								<input type="text" name="version" id="version" value="${pd.version}" maxlength="10" placeholder="这里输入app版本号,格式x.x.x" title="app版本号" style="width:98%;"/>
 --%>								
								<td> 
								<input type="number" name="points1" id="points1" min="0" step="1"  max="10" value=0 />
								.
								<input type="number" name="points2" id="points2" min="0" step="1"  max="10" value=0 />
								.
								<input type="number" name="points3" id="points3" min="0" step="1"  max="10" value=0 />
								
								</td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">平台*</td>
								<td>
								<!-- <input type="text" name="platform" id="platform" value="${pd.platform}" maxlength="1" title="平台" style="width:98%;"/> -->
									<select class="chosen-select form-control" name="platform" id="platform" value="${pd.platform}"  data-placeholder="请选择平台" style="vertical-align:top;width: 120px;">
										<option value="0">ios</option>
										<option value="1">android</option>
								  	</select
								
								</td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">业务版本*</td>
								<td>
								<!--  <input type="text" name="business_type" id="business_type" value="${pd.business_type}" maxlength="255" placeholder="这里输入业务版本:1-交易版 2-资讯版" title="业务版本" style="width:98%;"/>-->
									<select class="chosen-select form-control" name="business_type" id="business_type" value="${pd.business_type}"  data-placeholder="请选择业务版本" style="vertical-align:top;width: 120px;">
										<option value="1">交易版 </option>
								  	</select
								</td>
							</tr>
							<tr>
 								<td style="width:90px;text-align: right;padding-top: 13px;">渠道*</td>
<%--								<td>
								 	<select class="chosen-select form-control" name="channel" id="channel" value="${pd.channel}"  data-placeholder="请选择渠道" style="vertical-align:top;width: 120px;">
			           					<c:forEach var="channelDTO" items="${channelDTOList}">
			        						<option value="${channelDTO.channel}">${channelDTO.channelName}</option>
			        					</c:forEach>																												
								  	</select>
								</td> --%>
								<td>
									<select name="app_name" id="app_name" onchange="change1(this.value)">
		                                <option>app名称必选</option>     					 
		                          	</select>
		                          	<select id="channel" name="channel">
								 		<option>app下载渠道必选</option>                       
                      				</select>
								</td>
							</tr>
							
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">开关*</td>
								<%--<td>
									<input style="width:200px" type="range" min=0 max=1 step=1 id="turn_on" maxlength="18" value="${pd.turn_on}" onchange="myFunction()">
								    <p id="demo"></p>
							    </td>
							 	<td><input type="text" name="turn_on" id="turn_on" value="${pd.turn_on}" maxlength="1" placeholder="这里输入0- 关 1- 开" title="开关" style="width:98%;"/></td> --%>
								<!--  <td> 
									<label>
										<input name="turn_on" id="turn_on" value="${pd.turn_on}" checked="checked" class="ace ace-switch ace-switch-3" type="checkbox" />
										<span class="lbl"></span>
									</label>
								
								</td> -->
								<td><input type="text" name="turn_on" id="turn_on" value="${pd.turn_on}" maxlength="1" placeholder="这里输入0- 关 1- 开" title="开关" style="width:98%;"/></td>
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
	<%@ include file="../system/index/foot.jsp"%>
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
			if($("#platform").val()==""){
				$("#platform").tips({
					side:3,
		            msg:'请输入平台:0-ios 1-android',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#platform").focus();
			return false;
			}
			if($("#business_type").val()==""){
				$("#business_type").tips({
					side:3,
		            msg:'请输入业务版本:1-交易版 2-资讯版',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#business_type").focus();
			return false;
			}
			
			if($("#app_name").val()=="app名称必选" || $("#app_name").val()==""){
				$("#level1").tips({
					side:3,
		            msg:'app名称必选',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#app_name").focus();
			return false;
			}
			
			if($("#channel").val()=="app下载渠道必选" || $("#channel").val()==""){
				$("#app_code_name").tips({
					side:3,
		            msg:'app下载渠道必选',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#channel").focus();
			return false;
			}
			
			if($("#turn_on").val()==""){
				$("#turn_on").tips({
					side:3,
		            msg:'请填写开关',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#turn_on").focus();
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
		
		//初始第一级
		$(function() {
			$.ajax({
				type: "POST",
				url: '<%=basePath%>switchappconfig/getLevels.do?tm='+new Date().getTime(),
		    	data: {},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#app_name").html('<option>app名称必选</option>');
					 $.each(data.list, function(i, dvar){
							$("#app_name").append("<option value="+dvar.DICTIONARIES_ID+">"+dvar.NAME+"</option>");
					 });
				}
			});
		});
		//第一级值改变事件(初始第二级)
		function change1(value){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>switchappconfig/getLevels.do?tm='+new Date().getTime(),
		    	data: {DICTIONARIES_ID:value},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#channel").html('<option>app下载渠道必选</option>');
					 $.each(data.list, function(i, dvar){
							$("#channel").append("<option value="+dvar.DICTIONARIES_ID+">"+dvar.NAME+"</option>");
					 });
				}
			});
		}
		
		function myFunction() {
		    var x = document.getElementById("turn_on").value;
		    var text = ''
		    switch(x){
		       case '1': text='开'; break;
		       case '0': text='关'; break;

		    }
		    document.getElementById("demo").innerHTML = text;
		}
		
		function myFunctionShow() {
		    var x = document.getElementById("turn_on").value;
		    var text = ''
		    switch(x){
		       case '1': text='开'; break;
		       case '0': text='关'; break;
		    }
		    document.getElementById("demo").innerHTML = text;
		}
		
		myFunctionShow();
		
		</script>
</body>
</html>