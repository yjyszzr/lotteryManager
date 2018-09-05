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
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">渠道号*</td>
								<td style="text-align: left;padding-top: 13px;">app名称:</td>
								<td>
									<select name="app_code_name" id="app_code_name"  onchange="change1(this.value)">
										<option value="${pd.app_code_name}">${pd.app_name}</option>
		                          	</select>
		                        </td>
		                        <td style="text-align: left;padding-top: 13px;">app渠道名称:</td>
		                        <td>
		                          	<select id="channel"  name="channel">
								 		<option value="${pd.channel}">${pd.channel_name}</option>                        
                      				</select>
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">app新版本号*</td>
								<td colspan="4"> 
								<input type="number" name="points1" id="points1" value="${pd.points1}" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))" min=0 step=1  max=10 value=0 />
								.
								<input type="number" name="points2" id="points2" value="${pd.points2}" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))" min=0 step=1  max=10 value=0 />
								.
								<input type="number" name="points3" id="points3" value="${pd.points3}" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))" min="0" step="1"  max="10" value=0 />
								
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">更新日志*</td>
								<td colspan="4">
									<input type="text" name="text1" id="text1" value="${pd.text1}" maxlength="60" placeholder="升级说明" title="升级说明" style="width:98%;margin-bottom:4px;"/>
									<input type="text" name="text2" id="text2" value="${pd.text2}" maxlength="60" placeholder="至少填一项" title="升级说明" style="width:98%;margin-bottom:4px;"/>
									<input type="text" name="text3" id="text3" value="${pd.text3}" maxlength="60" placeholder="结尾不要输入标点符号" title="升级说明" style="width:98%;margin-bottom:4px;"/>
									<input type="text" name="text4" id="text4" value="${pd.text4}" maxlength="60" placeholder="简洁描述" title="升级说明" style="width:98%;margin-bottom:4px;"/>
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">是否强制升级*</td>
								<td colspan="4">
									<input type="text" name="update_install" id="update_install" value="${pd.update_install}" maxlength="1" placeholder="输入是否强制升级:0-不强制 1-强制" title="强制升级开关" style="width:98%;"/>
								</td>
							</tr>
							<tr>
									<div class="modal fade" id="loadingModal">
									    <div style="width: 300px;height:20px; z-index: 20000; position: absolute; text-align: center; left: 50%; top: 50%;margin-left:-100px;margin-top:-10px">
									        <h5>正在上传...</h5>
									        <div class="progress progress-striped active" style="margin-bottom: 0;">
									            <div class="progress-bar" style="width: 100%;"></div>
									        </div>
									    </div>
									</div>
							</tr>
							<tr >
								<td style="width:120px;text-align: right;padding-top: 13px;">apk路径*</td>
								<td colspan="3">
									<input  type="file" id="fileUpload" name="file"  onchange="ajaxFileUpload(this,'fileUpload')" style="display:none"/>
									<input type="text" style="width:100%" name="apk_path" id="apk_path" readonly="readonly" onmouseover="this.title=this.value"  value="${pd.apk_path}" />
								</td>
								<td><span class="btn btn-mini btn-primary" onclick="$('#fileUpload').trigger('click');">apk上传</span></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="5">
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
	<script type="text/javascript" src="static/ace/js/bootstrap.js"></script>
  	<!-- 文件上传 -->
    <script src="plugins/fhform/js/ajaxfileupload.js"></script>
		<!--提示框-->
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#app_name").val()=="" || $("#app_name").val()=="app名称必选"){
				$("#app_name").tips({
					side:3,
		            msg:'请选择app名称',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#app_name").focus();
			return false;
			}
			
			if($("#channel").val()=="" || $("#channel").val()=="app下载渠道必选"){
				$("#channel").tips({
					side:3,
		            msg:'请选择渠道号',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#channel").focus();
			return false;
			}
			
			if($("#points1").val() == 0 ){
				$("#points1").tips({
					side:3,
		            msg:'app版本号第一位大于0',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#points1").focus();
			return false;
			}
			
			if($("#points1").val() < 0 || $("#points2").val() < 0 || $("#points3").val() < 0){
				$("#points1").tips({
					side:3,
		            msg:'app版本号不能为负数',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#points1").focus();
			return false;
			}
			
			if(isNull($("#text1").val()) == true && isNull($("#text2").val()) == true && isNull($("#text3").val()) == true &&isNull($("#text4").val()) == true){
				$("#text1").tips({
					side:3,
		            msg:'至少填写一条升级日志',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#text1").focus();
			return false;
			}
			
			if(isNull($("#update_install").val()) == true){
				$("#update_install").tips({
					side:3,
		            msg:'请填写正确的强制升级要求',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#update_install").focus();
			return false;
			}
			
			var updateInstall = parseInt($("#update_install").val());
			if(updateInstall >= 2){
				$("#update_install").tips({
					side:3,
		            msg:'请填写正确的强制升级要求',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#update_install").focus();
			return false;
			}
			
			if(isNull($("#apk_path").val()) == true){
				$("#apk_path").tips({
					side:3,
		            msg:'保存之前请确保apk上传成功',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#apk_path").focus();
			return false;
			}
			
			//校验apk的正确性，通过apk的名称来判断正确的apk
			var version = $("#points1").val()+"."+$("#points2").val()+"."+$("#points3").val();
			var channel = $("#channel").val();
			var dowLoadUrl = $("#apk_path").val();
			if(dowLoadUrl.indexOf(version) < 0){
				$("#points1").tips({
					side:3,
		            msg:'app新版本号与上传的包的版本号不一致，核对后重新上传包',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#points1").focus();
				return false;
			}
			
			if(dowLoadUrl.indexOf(channel) < 0){
				$("#channel").tips({
					side:3,
		            msg:'渠道号与上传的包的版本号不一致，请核对后重新上传包',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#channel").focus();
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
					 $.each(data.list, function(i, dvar){
							$("#app_code_name").append("<option value="+dvar.DICTIONARIES_ID+"  >"+dvar.NAME+"</option>");
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
	
		function ajaxFileUpload(fileObj,fileId){
			$("#loadingModal").modal('show');
		    $.ajaxFileUpload({
		        url:'<%=basePath%>pictures/apkFileUpload.do',
		        secureuri:false,                           //是否启用安全提交,默认为false
		        fileElementId:fileId,               		//文件选择框的id属性
		        dataType:'json',                           //服务器返回的格式,可以是json或xml等
		        success:function(data, status){            //服务器响应成功时的处理函数
		            if(data.result){
		            	$("#loadingModal").modal('hide');
		            	$("#apk_path").val(data.apk_path);
		            } 
		        },
		        error:function(data,status){
		        	$("#loadingModal").modal('hide');
		        	alert("上传失败,请检查");
		        }
		    });
		  }
		
		function isNull(str){
			if(str == "") return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}

		</script>
</body>
</html>