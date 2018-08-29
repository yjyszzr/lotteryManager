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
	<!-- 表单构建组建 -->
	<link rel="shortcut icon" href="favicon.ico"> <link href="plugins/fhform/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="plugins/fhform/css/animate.min.css" rel="stylesheet">
    <link href="plugins/fhform/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	<style>
        .droppable-active{background-color:#ffe!important}.tools a{cursor:pointer;font-size:80%}.form-body .col-md-6,.form-body .col-md-12{min-height:400px}.draggable{cursor:move}
    </style>
	
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
					
					<form action="appdoc/${msg }.do" name="Form" id="Form" method="post">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td ></td>
								<td><input type="hidden"  id="content"  name="content"   value=""/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">文案分类:</td>
								<td>
							       <select id="classify" name = "classify"  value="${pd.classify} style ="width:42%">
								        	<option value="1" >提现文案说明</option>
								   </select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">文案内容:</td>
								<td> </td>
							</tr>
							<tr>
								<td> </td>
								<td> <div  id="editor"></div> </td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-warming" onclick="toPreShow()">预览</a>
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


	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 表单构建组建 -->
    <script src="plugins/fhform/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="plugins/fhform/js/content.min.js?v=1.0.0"></script>
    <script src="plugins/fhform/js/ajaxfileupload.js"></script>
    <script src="plugins/fhform/js/jquery-ui-1.10.4.min.js"></script>
    <script src="plugins/fhform/js/beautifyhtml/beautifyhtml.js"></script>
	<!-- 富文本编辑框-->
	<script src="static/ace/js/wangEditor.min.js"</script>
	<!-- 富文本编辑框-->
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<script src="static/ace/js/bootbox.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 上传控件 -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		   var E = window.wangEditor;
	       var editor = new E('#editor');
	       editor.customConfig.showLinkImg = false;//关闭网络上传tab
	       editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
	       editor.customConfig.uploadImgMaxLength = 1;
	       editor.customConfig.debug=true;
	       editor.customConfig.uploadFileName = 'myFileName';
	       editor.customConfig.uploadImgServer = '<%=basePath%>pictures/fileUploadForWangEditor.do';  // 上传图片到服务器
	       editor.customConfig.uploadImgHooks = {
	               customInsert: function (insertImg, result, editor) {
	                   insertImg(result.data);
	               }
	           };
	      editor.create();
	      editor.txt.append('${pd.content}');
		
		$(top.hangge());
		
		function save(status){
			if($("#classify").val()==""){
				$("#classify").tips({
					side:3,
		            msg:'请选择分类',
		            bg:'#AE81FF',
		            time:2
		        });
			return false;
			}
			
			var content = editor.txt.html();
			if('' == content){
				$("#content").tips({
					side:3,
		            msg:'请填写文案信息',
		            bg:'#AE81FF',
		            time:2
		        });
			return false;
			}
			
			$("#content").val(content);
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		//预览
		function toPreShow(){
			var toPreShowContent = editor.txt.html();
			var str = '<style type="text/css">#mydiv{margin:0 auto;  text-align:center; width:375px; height:667px; border:1px solid red; overflow:hidden; } #mydiv img{ max-width:375px; max-height:200px;overflow:hidden; }</style>';
			var toPreShowContentValue= str+"<div id='mydiv'  style='OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>" +  toPreShowContent +"</div>";
			bootbox.confirm(toPreShowContentValue, function(result) {
				if(result) {
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