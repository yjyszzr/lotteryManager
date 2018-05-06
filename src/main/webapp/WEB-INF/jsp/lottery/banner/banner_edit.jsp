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
					
					<form action="banner/${msg }.do" name="Form" id="Form" method="post" class="form-horizontal m-t">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<input type="hidden" name="create_time" id="create_time" value="${var.create_time}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						   <div >
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">标题：</label>
								<div class="col-sm-9" >
									<input type="text" name="banner_name" id="banner_name" value="${pd.banner_name}" maxlength="30" placeholder="这里输入标题" title="标题30个字"  class="col-xs-10 col-sm-5"   />
								</div>
                            </div>
						   <div  >
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">投放资源：</label>
								 <div class="col-sm-2">
									    <select  name="banner_param" id="banner_param" value="${pd.banner_param}"  style="width:188px;">
									        <option value="1" <c:if test="${pd.banner_param==1}">selected</c:if>>文章Id</option>
									        <option value="2" <c:if test="${pd.banner_param==2}">selected</c:if>>赛事Id</option>
									        <option value="3"  <c:if test="${pd.banner_param==3}">selected</c:if>>活动URL</option>
									    </select>
									</div>
								 
								<div class="col-sm-7">
									<input type="text" name="banner_link" id="banner_link" value="${pd.banner_link}" maxlength="255" placeholder="这里输入URL或ID" title="链接" style="width:177px;"/>
								</div>
                            </div>
                            	<input name="banner_image" class="hidden"   id="banner_image" value="${pd.banner_image}" />
                            	<input  type="file" id="fileUpload" name="file"  onchange="ajaxFileUpload(this,'fileUpload')" style="display:none"/>
						       <div >
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">缩略图：</label>
									<div class="col-sm-9">
								 	  <span class="btn btn-mini btn-primary" onclick="$('#fileUpload').trigger('click');"> 图片上传</span>  
								</div>
                            </div>
								<div>
									<div class="col-sm-3"></div>
									<div class="col-sm-9">
										<img id="photoShow"  <c:if test="${not empty pd.banner_image}">src="<%=basePath%>${pd.banner_image}" width="100px",hight="50px"  </c:if> >
									</div>
	                            </div>
	   						<div >
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">排序：</label>
								<div class="col-sm-9" >
									<input type="number" name="banner_sort" id="banner_sort" value="${pd.banner_sort}" maxlength="32" placeholder="排序字段" title="排序字段"    onkeyup="value=value.replace(/[^\d]/g,'')"   class="col-xs-10 col-sm-5" />
								</div>
                            </div>
	   						<div >
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">是否显示：</label>
									 <div class="col-sm-9">
									    <select  name="is_show" id="is_show" value="${pd.is_show}"  style="width:188px;">
									        <option value="0" <c:if test="${pd.is_show==0}">selected</c:if>>否</option>
									        <option value="1" <c:if test="${pd.is_show==1}">selected</c:if>>是</option>
									    </select>
								</div>
                            </div>
							<div >
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">开始日期：</label>
								<div class="col-sm-9" >
									<input class="date-picker" name="start_time" id="start_time"  value="${pd.start_time }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:188px;" placeholder="开始日期" title="开始日期"/>
								</div>
                            </div>
							<div >
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">结束日期：</label>
								<div class="col-sm-9" >
									<input class="date-picker" name="end_time" id="end_time"  value="${pd.end_time }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:188px;" placeholder="结束日期" title="结束日期"/>
								</div>
                            </div>
						<table  id="table_report" class="table table-striped table-bordered table-hover">
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
  	<script src="plugins/fhform/js/content.min.js?v=1.0.0"></script>
  	<!-- 文件上传 -->
    <script src="plugins/fhform/js/ajaxfileupload.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#banner_name").val()==""){
				$("#banner_name").tips({
					side:3,
		            msg:'请输入标题',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#banner_name").focus();
			return false;
			}
			if($("#banner_image").val()==""){
				$("#banner_image").tips({
					side:3,
		            msg:'请输入图片路径',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#banner_image").focus();
			return false;
			}
			if($("#banner_link").val()==""){
				$("#banner_link").tips({
					side:3,
		            msg:'请输入链接',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#banner_link").focus();
			return false;
			}
			if($("#banner_param").val()==""){
				$("#banner_param").tips({
					side:3,
		            msg:'请输入链接参数列表(投放资源):  1:文章id;2:活动URL;3:赛事id ',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#banner_param").focus();
			return false;
			}
			if($("#banner_sort").val()==""){
				$("#banner_sort").tips({
					side:3,
		            msg:'请输入排序字段',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#banner_sort").focus();
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
			if($("#start_time").val()==""){
				$("#start_time").tips({
					side:3,
		            msg:'请输入投放开始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#start_time").focus();
			return false;
			}
			if($("#end_time").val()==""){
				$("#end_time").tips({
					side:3,
		            msg:'请输入投放结束时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#end_time").focus();
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
		
		 var basePath = "<%=basePath%>"; 
			//上传营业执照图片
			function ajaxFileUpload(fileObj,fileId){
			    $.ajaxFileUpload({
			        url:'<%=basePath%>pictures/fileUpload.do',
			        secureuri:false,                           //是否启用安全提交,默认为false
			        fileElementId:fileId,               		//文件选择框的id属性
			        dataType:'json',                           //服务器返回的格式,可以是json或xml等
			        success:function(data, status){            //服务器响应成功时的处理函数
			            if(data.result){
				            	$("#photoShow").attr("src", basePath +data.PATH).attr("width","100px").attr("hight","50px");
				            	$("#banner_image").val(data.PATH);
			            } 
			        }
			    });
			  }
		
		
		</script>
</body>
</html>