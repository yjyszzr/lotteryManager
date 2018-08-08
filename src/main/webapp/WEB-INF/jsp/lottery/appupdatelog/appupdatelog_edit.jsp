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
					
					<form action="appupdatelog/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="_id" id="_id" value="${pd._id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">渠道号*</td>
								<td>
									<select name="app_name" id="app_name" onchange="change1(this.value)">
		                                <option>app名称必选</option>     					 
		                          	</select>
		                        </td>
		                        <td>
		                          	<select id="channel" name="channel">
								 		<option>app下载渠道必选</option>                       
                      				</select>
								</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">app新版本号*</td>
								<td colspan="2"> 
								<input type="number" name="points1" id="points1" min=0 step=1  max=10 value=0 />
								.
								<input type="number" name="points2" id="points2" min=0 step=1  max=10 value=0 />
								.
								<input type="number" name="points3" id="points3" min="0" step="1"  max="10" value=0 />
								
								</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">更新日志*</td>
								<td colspan="2">
									<input type="text" name="text1" id="text1" value="${pd.text1}" maxlength="32" placeholder="这里输入升级日志,请只输入文字" title="更新日志1" style="width:98%;"/>
									<input type="text" name="text2" id="text2" value="${pd.text2}" maxlength="32" placeholder="这里输入升级日志,请只输入文字" title="更新日志2" style="width:98%;"/>
									<input type="text" name="text2" id="text2" value="${pd.text2}" maxlength="32" placeholder="这里输入升级日志,请只输入文字" title="更新日志2" style="width:98%;"/>
									<input type="text" name="text4" id="text4" value="${pd.text4}" maxlength="32" placeholder="这里输入升级日志,请只输入文字" title="更新日志4" style="width:98%;"/>
								</td>
							</tr>
							<tr>
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">缩略图：</label>
								<div class="col-sm-9">
								   <span class="btn btn-mini btn-primary" onclick="$('#fileUpload').trigger('click');"  id="showOnePhoto"> app上传</span>  
								</div>
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
			if($("#id").val()==""){
				$("#id").tips({
					side:3,
		            msg:'请输入备注1',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#id").focus();
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
		
		
		
		</script>
</body>
</html>