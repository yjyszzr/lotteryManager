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
					
					<form action="lotteryclassify/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="lottery_classify_id" id="lottery_classify_id" value="${pd.lottery_classify_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">彩种名称:</td>
								<td><input type="text" name="lottery_name" id="lottery_name" value="${pd.lottery_name}" maxlength="50" placeholder="这里输入彩种名称" title="彩种名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">彩种logo:</td>
								<td>
									<input name="lottery_img" class="hidden"   id="lottery_img" value="${pd.lottery_img}" />
                            		<input  type="file" id="fileUpload" name="file"  onchange="ajaxFileUpload(this,'fileUpload')" style="display:none"/>
										<div class="col-sm-9" style="float:left">
									 	  <span class="btn btn-mini btn-primary" onclick="$('#fileUpload').trigger('click');"> 图片上传</span>  
										</div>
										<div class="col-sm-9"  style="float:left"> 
											<img id="photoShow"  src="${pd.lottery_img}" width="24px",hight="24px"  >
										</div>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">排序:</td>
								<td><input type="number" name="sort" id="sort" value="${pd.sort}" maxlength="32" placeholder="这里输入排序" title="排序" style="width:98%;"  /></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td>
								    <select  name="status" id="status" value="${pd.status}" style="width:98%;">
								        <option value="" >请选择</option>
								        <option value="0" <c:if test="${pd.status == 0}">selected</c:if>>售卖</option>
								        <option value="1" <c:if test="${pd.status == 1}">selected</c:if>>停售</option>
								    </select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态原因:</td>
								<td>
								   <input type="text" name="status_reason" id="status_reason" value="${pd.status_reason}" maxlength="50" placeholder="这里输入停售原因" style="width:98%;"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属店铺:</td>
								<td>
								    <select  name="app_code_name" id="app_code_name" value="${pd.app_code_name}" style="width:98%;">
								        <option value="" >请选择</option>
								        <option value="10" <c:if test="${pd.app_code_name == 10}">selected</c:if>>球多多</option>
								        <option value="11" <c:if test="${pd.app_code_name == 11}">selected</c:if>>圣和彩店</option>
								    </select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否显示:</td>
								<td>
									<select  name="is_show" id="is_show" value="${pd.is_show}" style="width:98%;">
								        <option value="" >请选择</option>
								        <option value="0" <c:if test="${pd.is_show == 0}">selected</c:if>>不显示</option>
								        <option value="1" <c:if test="${pd.is_show == 1}">selected</c:if>>显示</option>
								    </select>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">跳转链接:</td>
								<td><input type="text" name="redirect_url" id="redirect_url" value="${pd.redirect_url}" maxlength="256" placeholder="这里输入跳转链接" title="跳转链接" style="width:98%;"/></td>
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
  	<!-- 文件上传 -->
    <script src="plugins/fhform/js/ajaxfileupload.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#lottery_name").val()==""){
				$("#lottery_name").tips({
					side:3,
		            msg:'请输入彩种名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#lottery_name").focus();
			return false;
			}
			if($("#lottery_img").val()==""){
				$("#lottery_img").tips({
					side:3,
		            msg:'请输入彩种logo',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#lottery_img").focus();
			return false;
			}
			if($("#sort").val()==""){
				$("#sort").tips({
					side:3,
		            msg:'请输入排序',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sort").focus();
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
			if($("#status_reason").val()==""){
				$("#status_reason").tips({
					side:3,
		            msg:'请输入当前状态下的描述',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#status_reason").focus();
			return false;
			}
			if($("#app_code_name").val()==""){
				$("#app_code_name").tips({
					side:3,
		            msg:'请选择店铺',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#app_code_name").focus();
			return false;
			}
			if($("#is_show").val()==""){
				$("#is_show").tips({
					side:3,
		            msg:'请输入是否展示',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_show").focus();
			return false;
			}
			if($("#redirect_url").val()==""){
				$("#redirect_url").tips({
					side:3,
		            msg:'请输入跳转链接',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#redirect_url").focus();
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
		
		
		//上传图片
		function ajaxFileUpload(fileObj,fileId){
		    $.ajaxFileUpload({
		        url:'<%=basePath%>pictures/fileUpload.do',
		        secureuri:false,                           //是否启用安全提交,默认为false
		        fileElementId:fileId,               		//文件选择框的id属性
		        dataType:'json',                           //服务器返回的格式,可以是json或xml等
		        success:function(data, status){            //服务器响应成功时的处理函数
		            if(data.result){
			            	$("#photoShow").attr("src", data.IMG_SHOW_PATH).attr("width","24px").attr("hight","24px");
			            	$("#lottery_img").val(data.PATH);
		            } 
		        }
		    });
		  }
		
		</script>
</body>
</html>