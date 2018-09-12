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
					
					<form action="distributebonus/${msg }.do" name="Form" id="Form" method="post">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">活动赠送红包</td>
								<td colspan="4">
									<select  name="selectBonus" id ="selectBonus"  onclick="showList()"></select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"><input  type= "radio"  name = "chooseOne" value = "1" checked= "true"/>手机号</td>
								<td colspan="4" id = "phone_td"><input type="text" name="receiver" id="receiver" value="${pd.receiver}" maxlength="11" placeholder="这里输入接收人手机号" title="接收人手机号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><input type= "radio" name = "chooseOne" value = "2" />excel</input> </td>
								<td colspan="3" id="file_td">
									<input  type="file" id="fileUpload" name="file"  onchange="ajaxFileUpload(this,'fileUpload')" style="display:none"/>
									<input type="text" style="width:100%" name="file_url" id="file_url" readonly="readonly" onmouseover="this.title=this.value"  value="${pd.file_url}" />
								</td >
								<td id="file_upload_td"><span class="btn btn-mini btn-primary" onclick="$('#fileUpload').trigger('click');">excel上传</span></td>						
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="commitProv();">提交审批</a>
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
	<!-- 文件上传 -->
    <script src="plugins/fhform/js/ajaxfileupload.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function commitProv(){
			if($("#selectBonus").val()==""){
				$("#selectBonus").tips({
					side:3,
		            msg:'请选择活动赠送红包',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#selectBonus").focus();
			return false;
			}
			
/* 			if($("#receiver").val()==""){
				$("#receiver").tips({
					side:3,
		            msg:'请输入接收人手机号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#receiver").focus();
			return false;
			} */
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function(){
			var phone_td_style = document.getElementById('phone_td').style;
			var file_td_style = document.getElementById('file_td').style;
			var file_upload_td_style = document.getElementById('file_upload_td').style;
            $("input[name='chooseOne']").click(function(){
             	if($(this).val() == "1"){
					phone_td_style.display = 'block';
					file_td_style.display = 'none';
					file_upload_td_style.display = 'none';
                }else{
					phone_td_style.display = 'none';
					file_td_style.display = 'block';
					file_upload_td_style.display = 'block';
                }
            });
		})
		
		function showList(){
 			$.ajax({
				type: "POST",
				url: '<%=basePath%>activitybonus/getDistributeBonusList.do?tm='+new Date().getTime(),
	    		data: {},
				dataType:'json',
				cache: false,
				success: function(data){
						 $.each(data.list, function(i, dvar){
								$("#selectBonus").append("<option value="+dvar.bonus_id+">"+dvar.bonus_id +"(满"+ dvar.min_goods_amount +"元减"+dvar.bonus_price+"元)"+  "</option>");
						 });
				}
			}); 
		}
		
		function ajaxFileUpload(fileObj,fileId){
		    $.ajaxFileUpload({
		        url:'<%=basePath%>pictures/excelFileUpload.do',
		        secureuri:false,                           //是否启用安全提交,默认为false
		        fileElementId:fileId,               		//文件选择框的id属性
		        dataType:'json',                           //服务器返回的格式,可以是json或xml等
		        success:function(data, status){            //服务器响应成功时的处理函数
		            if(data.result){
		            	$("#file_url").val(data.file_url);
		            } 
		        },
		        error:function(data,status){
		        	alert("上传失败,请检查");
		        }
		    });
		}
		
		$(function() {//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>