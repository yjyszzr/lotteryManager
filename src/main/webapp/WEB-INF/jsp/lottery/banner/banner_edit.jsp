<%@page import="com.fh.util.DateUtil"%>
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
						        <div class="row"  style="padding:5px">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">标题：</label>
								<div class="col-sm-9" >
									<input type="text" name="banner_name" id="banner_name" value="${pd.banner_name}" maxlength="30" placeholder="这里输入标题" title="标题30个字"  class="col-xs-10 col-sm-5"   />
								</div>
                            </div>
						        <div class="row"  style="padding:5px">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">投放资源：</label>
								 <div class="col-sm-2">
									    <select  name="banner_param" id="banner_param" value="${pd.banner_param}"  style="width:188px;">
									        <option value="1" selected <c:if test="${pd.banner_param==1}">selected</c:if>>文章Id</option>
									        <option value="2" <c:if test="${pd.banner_param==2}">selected</c:if>>赛事Id</option>
									        <option value="3"  <c:if test="${pd.banner_param==3}">selected</c:if>>活动URL</option>
									        <option value="4"  <c:if test="${pd.banner_param==4}">selected</c:if>>小白课堂</option>
									    </select>
									</div>
								 
								<div class="col-sm-7">
									<input type="text" name="banner_link" id="banner_link" value="${pd.banner_link}" maxlength="255" placeholder="这里输入URL或ID" title="链接" style="width:106px;"/>
								</div>
                            </div>
                            	<input name="banner_image" class="hidden"   id="banner_image" value="${pd.banner_image}" />
                            	<input  type="file" id="fileUpload" name="file"  onchange="ajaxFileUpload(this,'fileUpload')" style="display:none"/>
						            <div class="row"  style="padding:5px">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">缩略图：</label>
									<div class="col-sm-9">
								 	  <span class="btn btn-mini btn-primary" onclick="$('#fileUpload').trigger('click');"> 图片上传</span>  
								</div>
                            </div>
								<div>
									<div class="col-sm-3"></div>
									<div class="col-sm-9">
										<img id="photoShow"  <c:if test="${not empty pd.banner_image_show}">src="${pd.banner_image_show}" width="100px",hight="50px"  </c:if> >
									</div>
	                            </div>
	   						     <div class="row"  style="padding:5px">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">排序：</label>
								<div class="col-sm-9" >
									<input type="number" name="banner_sort" id="banner_sort" value="${pd.banner_sort}" maxlength="32" placeholder="排序字段" title="排序字段"    onkeyup="value=value.replace(/[^\d]/g,'')" style="width:235px"   class="col-xs-10 col-sm-5" />
								</div>
                            </div>
	   						     <div class="row"  style="padding:5px">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">版本类型：</label>
									 <div class="col-sm-9">
									    <select  name="is_transaction" id="is_transaction" value="${pd.is_transaction}"  style="width:235px;">
									        <option value="1"  selected<c:if test="${pd.is_transaction==1}">selected</c:if>>资讯版</option>
									        <option value="2" <c:if test="${pd.is_transaction==2}">selected</c:if>>交易版</option>
									    </select>
								</div>
                            </div>
	   						     <div class="row"  style="padding:5px">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">发布平台：</label>
									 <div class="col-sm-9">
									    <select  name="app_code_name" id="app_code_name" value="${pd.app_code_name}"  style="width:235px;">
									        <option value="10"  selected<c:if test="${pd.app_code_name==10}">selected</c:if>>球多多</option>
									        <option value="11" <c:if test="${pd.app_code_name==11}">selected</c:if>>圣和彩店</option>
									    </select>
								</div>
                            </div>
	   						     <div class="row"  style="padding:5px">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">展示位置：</label>
									 <div class="col-sm-9">
									    <select  name="show_position" id="show_position" value="${pd.show_position}"  style="width:235px;">
									        <option value="0" <c:if test="${pd.show_position==0}">selected</c:if>>首页轮播图</option>
									    	<option value="1" <c:if test="${pd.show_position==1}">selected</c:if>>发现页轮播图</option>
											<option value="2" <c:if test="${pd.show_position==2}">selected</c:if>>大厅页面开屏图</option>
											<option value="3" <c:if test="${pd.show_position==3}">selected</c:if>>商城轮播图</option>
									    </select>
								</div>
                            </div>
	   						     <div class="row"  style="padding:5px">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">发布状态：</label>
									 <div class="col-sm-9">
									    <select  name="is_show" id="is_show" value="${pd.is_show}"  style="width:235px;">
									        <option value="0" <c:if test="${pd.is_show==0}">selected</c:if>>过期</option>
									        <option value="1" <c:if test="${pd.is_show==1}">selected</c:if>>发布</option>
									    </select>
								</div>
                            </div>
							     <div class="row"  style="padding:5px">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">开始日期：</label>
								<div class="col-sm-9" >
									<input   name="start_time" id="start_time"  value="${DateUtil.toSDFTime(pd.start_time*1000)} " type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="readonly" style="width:235px;" placeholder="开始日期" title="开始日期"/>
								</div>
                            </div>
							     <div class="row"  style="padding:5px">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">结束日期：</label>
								<div class="col-sm-9" >
									<input  name="end_time" id="end_time"  value="${DateUtil.toSDFTime(pd.end_time*1000)} " type="text"   onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width:235px;" placeholder="结束日期" title="结束日期"/>
								</div>
                            </div>
							     <div class="row"  style="padding:5px;text-align: center;">
							     <div class="col-sm-3" ></div>
							     <div class="col-sm-3"   >
									<a class="btn btn-mini btn-primary" style="float:left" onclick="save();">保存</a>
							     </div>
							     <div class="col-sm-3" >
									<a class="btn btn-mini btn-danger"  style="float:left" onclick="top.Dialog.close();">取消</a>
							     </div>
							     <div class="col-sm-3" > </div>
                            </div>
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
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
  	<script src="plugins/fhform/js/content.min.js?v=1.0.0"></script>
  	<!-- 文件上传 -->
    <script src="plugins/fhform/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
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
		            msg:'请上传图片',
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
		            msg:'请输入链接参数',
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
		
		//上传图片
		function ajaxFileUpload(fileObj,fileId){
		    $.ajaxFileUpload({
		        url:'<%=basePath%>pictures/fileUpload.do',
		        secureuri:false,                           //是否启用安全提交,默认为false
		        fileElementId:fileId,               		//文件选择框的id属性
		        dataType:'json',                           //服务器返回的格式,可以是json或xml等
		        success:function(data, status){            //服务器响应成功时的处理函数
		            if(data.result){
			            	$("#photoShow").attr("src", data.IMG_SHOW_PATH).attr("width","100px").attr("hight","50px");
			            	$("#banner_image").val(data.PATH);
		            } 
		        }
		    });
		  }
		</script>
	</body>
</html>