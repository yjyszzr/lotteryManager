<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<body class="gray-bg">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-24">
						   <div class="wrapper wrapper-content">
						        <div class="row">
								  	<div class="col-xs-24">
										<div class="widget-box">
											<div class="widget-body">
												<div class="widget-main">
													<div class="row">
														<div class="col-sm-5" style="width:100%;">
											                <div class="ibox float-e-margins">
											                    <div class="ibox-content" style="padding: 0px;">
<!-- 											                        <div class="alert alert-info"> -->
<!-- 											                          	编辑文章 -->
<!-- 											                        </div> -->
											                        <form action="articlecontroller/${msg }.do" name="Form" id="Form" method="post"  target="_self" class="form-horizontal m-t">
									                        			<div id="zhongxin" style="padding-top: 13px;">
											                        	<div class="row" style="padding:5px">
											                                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"><lable style="color:red;">*</lable>文章标题：</label>
											                                <div class="col-sm-9">
											                                <input type=hidden id="article_id"   name ="article_id" value="${pd.article_id}"/>
											                                <input type=hidden id="content"  name="content"   value=""/>
											                                <input type=hidden id="status"  name="status"   value=""/>
											                                   <input type="text" id="title"  name="title" placeholder="文章标题" class="col-xs-10 col-sm-5"   value="${pd.title}"/>
											                                </div>
											                            </div>
											                          
											                           <div class="row"  style="padding:5px">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"><lable style="color:red;">*</lable>作者：</label>
																			<div class="col-sm-9">
																				<input type="text" id="author" name="author" placeholder="作者" class="col-xs-10 col-sm-5"    value="${pd.author}"/>
																			</div>
											                            </div>
											                               <div class="row"  style="padding:5px">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"><lable style="color:red;">*</lable>图文类型：</label>
											                                <div class="col-sm-9">
																					<input name="article_thumb1" class="hidden"   id="article_thumb1" value="${pd.article_thumb1}" />
																					<input name="article_thumb2" class="hidden"   id="article_thumb2" value="${pd.article_thumb2}"/>
																					<input name="article_thumb3" class="hidden"   id="article_thumb3" value="${pd.article_thumb3}"/>
																				<label style="float:left;padding-left: 8px;padding-top:7px;">
																					<input  name="list_style" type="radio" checked <c:if test="${pd.list_style==1}">checked="checked"</c:if>    value = "1" class="ace" id="list_style1" />
																					<span class="lbl">单张图片</span>   
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="list_style" type="radio"  <c:if test="${pd.list_style==3}">checked="checked"</c:if>  value = "3" class="ace" id="list_style3" />
																					<span class="lbl">三张图片</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="list_style" type="radio"  <c:if test="${pd.list_style==4}">checked="checked"</c:if>  value = "4" class="ace" id="list_style4" />
																					<span class="lbl">视频缩略图</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="list_style" type="radio"  <c:if test="${pd.list_style==0}">checked="checked"</c:if>  value = "0" class="ace" id="list_style0" />
																					<span class="lbl">纯文本</span>
																				</label>
											                                </div>
											                            </div>
																			   	<input  type="file" id="fileUpload" name="file"  onchange="ajaxFileUpload(this,'fileUpload')" style="display:none"/>
											                            <div class="slt1" >
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1">缩略图：</label>
																			<div class="col-sm-9">
																			   <span class="btn btn-mini btn-primary" onclick="$('#fileUpload').trigger('click');"  id="showOnePhoto"> 单张上传</span>  
																			</div>
											                            </div>
																			<div  class="slt1">
																				<div class="col-sm-3"></div>
																				<div class="col-sm-9">
																					<img id="photoShow"  <c:if test="${not empty pd.article_thumb1_show}">src="${pd.article_thumb1_show}" width="100px",hight="50px"  </c:if> >
																				</div>
												                            </div>
												                            
									                                      <div class="slt2"   style="display:none;">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1">缩略图：</label>
																			<div class="col-sm-9">
																			   <span class="btn btn-mini btn-primary" onclick="uploadThreePhoto()" id="showThirdPhoto"> 三张上传</span>  
																			</div>
											                            </div >
											                            
											                            	<div  class="slt2" >
																				<div class="col-sm-3"></div>
																				<div class="col-sm-9">
																					<img id="photoShow1"    <c:if test="${not empty pd.article_thumb1_show}">src="${pd.article_thumb1_show}" width="100px",hight="50px"  </c:if>  alt="">
																					<img id="photoShow2"    <c:if test="${not empty pd.article_thumb2_show}">src="${pd.article_thumb2_show}" width="100px",hight="50px"  </c:if>   alt="">
																					<img id="photoShow3"    <c:if test="${not empty pd.article_thumb3_show}">src="${pd.article_thumb3_show}" width="100px",hight="50px"  </c:if>   alt="">
																				</div>
												                            </div>
										                                <div class="slt3"   style="display:none;">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1">副标题：</label>
																			<div class="col-sm-9">
																			   <input type="text" id="summary" name="summary" placeholder="副标题" class="col-xs-10 col-sm-5"    value="${pd.summary}"/>
																			</div>
											                            </div >
											                            
											                               <div class="row"  style="padding:5px">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"><lable style="color:red;">*</lable>内容分类：</label>
											                                <div class="col-sm-9">
																				<label style="float:left;padding-left: 8px;padding-top:7px;">
																					<input  name="extend_cat" type="radio" checked <c:if test="${pd.extend_cat==1}">checked="checked"</c:if>  value = "1" class="ace" id="extend_cat1" />
																					<span class="lbl">今日关注</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="extend_cat" type="radio"  <c:if test="${pd.extend_cat==2}">checked="checked"</c:if>  value = "2" class="ace" id="extend_cat2" />
																					<span class="lbl">竞彩预测</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="extend_cat" type="radio" <c:if test="${pd.extend_cat==3}">checked="checked"</c:if>  value = "3" class="ace" id="extend_cat3" />
																					<span class="lbl">牛人分析</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="extend_cat" type="radio" <c:if test="${pd.extend_cat==4}">checked="checked"</c:if>  value = "4" class="ace" id="extend_cat4" />
																					<span class="lbl">其他</span>
																				</label>
											                                </div>
											                            </div>
											                                <div class="row"  style="padding:5px">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"><lable style="color:red;">*</lable>是否原创：</label>
											                                <div class="col-sm-9">
																				<label style="float:left;padding-left: 8px;padding-top:7px;">
																					<input  name="is_original" type="radio" checked <c:if test="${pd.is_original==1}">checked="checked"</c:if>    value = "1" class="ace" id="is_original1" />
																					<span class="lbl">原创</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="is_original" type="radio"  <c:if test="${pd.is_original==2}">checked="checked"</c:if>   value = "2"  class="ace" id="is_original2" />
																					<span class="lbl">非原创</span>
																				</label>
											                                </div>
											                            </div>
											                                 <div class="row"  style="padding:5px">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"><lable style="color:red;">*</lable>添加标签：</label>
											                                <div class="col-sm-9">
																				<label style="float:left;padding-left: 8px;padding-top:7px;">
																					<input  name="related_team" type="radio"  checked <c:if test="${pd.related_team==1}">checked="checked"</c:if>   value = "1" class="ace" id="related_team_1" />
																					<span class="lbl">主队</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="related_team" type="radio"  <c:if test="${pd.related_team==2}">checked="checked"</c:if>    value = "2" class="ace" id="related_team_2" />
																					<span class="lbl">客队</span>
																				</label>
											                                </div>
											                            </div>
															                <div class="row"  style="padding:5px">
																				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"><lable style="color:red;">*</lable>默认标签：</label>
												                                <div class="col-sm-9">
																					<label style="float:left;padding-left: 8px;padding-top:7px;">
																						<input  name="label_defaults" type="radio"   checked <c:if test="${pd.label_defaults==1}">checked="checked"</c:if>   value = "1" class="ace" id="label_defaults_1" />
																						<span class="lbl">主帅</span>
																					</label>
																					<label style="float:left;padding-left: 5px;padding-top:7px;">
																						<input name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==2}">checked="checked"</c:if>   value = "2" class="ace" id="label_defaults_2" />
																						<span class="lbl">球员</span>
																					</label>
																					<label style="float:left;padding-left: 8px;padding-top:7px;">
																						<input  name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==3}">checked="checked"</c:if>   value = "3" class="ace" id="label_defaults_3" />
																						<span class="lbl">球迷</span>
																					</label>
																					<label style="float:left;padding-left: 5px;padding-top:7px;">
																						<input name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==4}">checked="checked"</c:if>   value = "4" class="ace" id="label_defaults_4" />
																						<span class="lbl">阵容</span>
																					</label>
																		
																				<label style="float:left;padding-left: 8px;padding-top:7px;">
																					<input  name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==5}">checked="checked"</c:if>   value = "5" class="ace" id="label_defaults_5" />
																					<span class="lbl">战意</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==6}">checked="checked"</c:if>   value = "6" class="ace" id="label_defaults_6" />
																					<span class="lbl">拱手</span>
																				</label>
																				<label style="float:left;padding-left: 8px;padding-top:7px;">
																					<input  name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==7}">checked="checked"</c:if>   value = "7" class="ace" id="label_defaults_7" />
																					<span class="lbl">交锋</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==8}">checked="checked"</c:if>   value = "8" class="ace" id="label_defaults_8" />
																					<span class="lbl">停赛</span>
																				</label>
																				
																				<label style="float:left;padding-left: 8px;padding-top:7px;">
																					<input  name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==9}">checked="checked"</c:if>   value = "9" class="ace" id="label_defaults_9" />
																					<span class="lbl">伤病</span>
																				</label>
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==10}">checked="checked"</c:if>   value = "10" class="ace" id="label_defaults_10" />
																					<span class="lbl">状态</span>
																				</label>
											                                </div>
											                                </div>
											                                <div class="row"  style="padding:5px">
																				<label class="col-sm-3 control-label no-padding-right" for="form-field-1">文章内容：</label>
												                                <div class="col-sm-9"> </div>
											                            </div>
											                                <div class="row"  style="padding:5px">
												                                	<div class="ueQ313596790Que"></div>
												                                   <script id="editor"  type="text/plain"></script>
												                                    <div class="ueQ313596790Que"></div>
											                            </div>
											                            <div >
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"></label>
											                                <div class="col-sm-9">
											                                   <a class="btn btn-mini btn-primary" onclick="save(1)" >发表</a>
											                                   <a class="btn btn-mini btn-danger" onclick="save('2')">保存草稿</a>
											                                   <a class="btn btn-mini btn-warming" onclick="toPreShow()">预览</a>
<!-- 											                                   <a class="btn btn-mini btn-warming" onclick="preLook()">预览</a> -->
											                                </div>
											                            </div>
											                            </div>
											                        <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
											                        </form>
											                    </div>
											                </div>
											            </div>
													</div>
												</div>
											</div>
										</div>
									</div><!-- /.col -->
						        </div><!-- row -->
						    </div>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 表单构建组建 -->
    <script src="plugins/fhform/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="plugins/fhform/js/content.min.js?v=1.0.0"></script>
    <script src="plugins/fhform/js/ajaxfileupload.js"></script>
    <script src="plugins/fhform/js/jquery-ui-1.10.4.min.js"></script>
    <script src="plugins/fhform/js/beautifyhtml/beautifyhtml.js"></script>
	<!-- 百度富文本编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.parse.js"></script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
	<!-- 百度富文本编辑框-->
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<script src="static/ace/js/bootbox.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 上传控件 -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
	$(top.hangge());
	function save(status){
		if($("#title").val()==""){
			$("#title").tips({
				side:3,
	            msg:'请输入标题',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#title").focus();
		return false;
		}
		if($("#author").val()==""){
			$("#author").tips({
				side:3,
	            msg:'请输入作者',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#author").focus();
		return false;
		}
		var a=0;
		var flag = false;
		$('input[id^=article_thumb]').each(function(){  
			 if(!$(this).val()==""){
				 a++;
			 }	 
		});  
		if( $("input[name='list_style']:checked").val()== 1  ){
			if(a==0){
				$("#showOnePhoto").tips({
					side:3,
		            msg:'请上传一张图片',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#showOnePhoto").focus();
			return false;
			}
		}else if( $("input[name='list_style']:checked").val()== 3){
			if(a<3){
				$("#showThirdPhoto").tips({
					side:3,
		            msg:'请上传三张图片',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#showThirdPhoto").focus();
			return false;
			}
		}
		
		var editor = UE.getEditor('editor');
		var content = UE.getEditor('editor').getContent();
		    $("#content").val(content);
		    $("#status").val(status);
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	}
	$(function(){
	  if($("input[name='list_style']:checked").val()== '1') {
			$(".slt2").hide();
 			$(".slt1").show();
 			$(".slt3").hide();
      }else if($("input[name='list_style']:checked").val()== '3') {
      		$(".slt1").hide();
 			$(".slt2").show();
 			$(".slt3").hide();
      }else if($("input[name='list_style']:checked").val()=='0') {
      		$(".slt2").hide();
 			$(".slt1").hide();
 			$(".slt3").show();
		}else if($("input[name='list_style']:checked").val()== '4'){
			$(".slt2").hide();
 			$(".slt1").show();
 			$(".slt3").hide();
		}
	});
// 	不能创建editor之后马上使用ue.setContent('文本内容')，要等到创建完成之后才可以使用
	UE.getEditor('editor').ready(function() {  
	  UE.getEditor('editor').setContent('${pd.content}');
	});  

	var clickNum=0;//设置一个全局的变量；
	function uploadThreePhoto(){
	$('#fileUpload').trigger('click');
	clickNum+=1;//第单击一次clickNum的值加1;
		if(clickNum>3){
			clickNum=1;
		}
	}
	
		$(top.hangge());
		$(document).ready(function() {
		    $('input[type=radio][name=list_style]').change(function() {
		        if (this.value == '1') {
					$(".slt2").hide();
		   			$(".slt1").show();
		   			$(".slt3").hide();
		        }else if (this.value == '3') {
		        	$(".slt1").hide();
		   			$(".slt2").show();
		   			$(".slt3").hide();
		        }else if (this.value == '0') {
		        	$(".slt2").hide();
		   			$(".slt1").hide();
		   			$(".slt3").show();
		        }else if (this.value == '4') {
		        	$(".slt2").hide();
		   			$(".slt3").hide();
		   			$(".slt1").show();
	   			}
		    });
		});
		 var basePath = "<%=basePath%>"; 
		//上传图片
		function ajaxFileUpload(fileObj,fileId){
		    $.ajaxFileUpload({
		        url:'<%=basePath%>pictures/fileUpload.do',
		        secureuri:false,                           //是否启用安全提交,默认为false
		        fileElementId:fileId,               		//文件选择框的id属性
		        dataType:'json',                           //服务器返回的格式,可以是json或xml等
		        success:function(data, status){            //服务器响应成功时的处理函数
		            if(data.result){
		            	if($('input[type=radio][name=list_style]:checked').val() == '1'  || $('input[type=radio][name=list_style]:checked').val() == '4'  ){
			            	$("#photoShow").attr("src", data.IMG_SHOW_PATH).attr("width","100px").attr("hight","50px");
			            	$("#article_thumb1").val(data.PATH);
		            	}else if( $('input[type=radio][name=list_style]:checked').val() == '3' ){
			            	$("#photoShow"+clickNum).attr("src", data.IMG_SHOW_PATH).attr("width","100px").attr("hight","50px");
			            	$("#article_thumb"+clickNum).val(data.PATH);
		            	}
		            } 
		        }
		    });
		  }
		
		
		//百度富文本
		setTimeout("ueditor()",500);
		function ueditor(){
			UE.getEditor('editor');
		}
	</script>
	
	<script>
	function saveInnerHtml(status){
		var editor = UE.getEditor('editor');
		var content = UE.getEditor('editor').getContent();
		var article_id = $("#article_id").val();
		var title = $("#title").val();
		var author = $("#author").val();
		var video_url = $("#video_url").val();
		var extend_cat = $("input[name='extend_cat']:checked").val();
		var is_original = $("input[name='is_original']:checked").val();
		var related_team = $("input[name='add_label1']:checked").val();
		var label_defaults = $("input[name='add_label2']:checked").val();
		var photosNum = $("input[name='photosNum']:checked").val();
		var article_thumb1 =$("#article_thumb1").val();
		var article_thumb2 =$("#article_thumb2").val();
		var article_thumb3 =$("#article_thumb3").val();
 			$.ajax({
		   	 		type: "POST",
    		       	url: '<%=basePath%>articlecontroller/saveOrUpdate.do',
		        	data: {
		        					article_id:article_id,
				        			content:content,
					        		title:title,
					        	    author:author,
					        	   	video_url:video_url,
					        	   	extend_cat:extend_cat,
					        	   	is_original:is_original,
					        	   	related_team:related_team,
					        	   	label_defaults:label_defaults,
					        	   	status:status,
					        	   	photosNum:photosNum,
					        	   	article_thumb1:article_thumb1,
					        	   	article_thumb2:article_thumb2,
					        	   	article_thumb3:article_thumb3
			        	   		},
				    dataType:'json',
				    cache: false,
				    success: function(data){
				       if(data.result){
				    	   	top.Dialog.close();
							top.jzts();
							$("#Form").submit();
				       }
				    },error:function(e){
				    	alert(JSON.stringify(e))
				    }
			    });
			}
	
	//过滤ueditor
	var ueditorHtml = "";
	function getUeditorFormHtml(html,msg,isgx){
		var arryUe = html.split('<div class="ueQ313596790Que"></div>');
		if(arryUe.length == 3){
			var uejscode = "<script id=\"editor\" type=\"text/plain\" style=\"width:96%;height:200px;\"><\/script>";
			if(msg == '1'){
				if(isgx == '2'){
					ueditorHtml = arryUe[1];
					return arryUe[0] + '<div class="ueQ313596790Que"></div>' + uejscode + '<div class="ueQ313596790Que"></div>' + arryUe[2];
				}else{
					return arryUe[0] + '<div class="ueQ313596790Que"></div>' + ueditorHtml + '<div class="ueQ313596790Que"></div>' + arryUe[2];
				}
			}else{
				return arryUe[0] + uejscode + arryUe[2];
			}
		}else{
			return html;
		}
	}
	
	//过滤下拉框
	var selectHtml = "";
	function getSelectFormHtml(html,msg,isgx){
		var arrySe = html.split('<div class="selQ313596790Qsel"></div>');
		if(arrySe.length == 3){
			var selectcode ='<select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择">'+
								'<option value=""></option>'+
								'<option value="">选项一</option>'+
								'<option value="">选项二</option>'+
								'<option value="">选项三</option>'+
						  	'</select>';
			selectcode = selectHtml == ''?selectcode:selectHtml;
			if(msg == '1'){
				if(isgx == '2'){
					return arrySe[0] + '<div class="selQ313596790Qsel"></div>' + selectcode + '<div class="selQ313596790Qsel"></div>' + arrySe[2];
				}else{
					selectHtml = arrySe[1];
					return html;
				}
			}else{
				return arrySe[0] + selectcode + arrySe[2];
			}
		}else{
			return html;
		}
	}
	
	//过滤file上传控件
	var fileHtml = "";
	function getFileFormHtml(html,msg,isgx){
		var arryFile = html.split('<div class="fileQ313596790Qfile"></div>');
		if(arryFile.length == 3){
			var filecode = "<input type=\"file\" id=\"tp\" name=\"tp\" />";
			if(msg == '1'){
				if(isgx == '2'){
					fileHtml = arryFile[1];
					return arryFile[0] + '<div class="fileQ313596790Qfile"></div>' + filecode + '<div class="fileQ313596790Qfile"></div>' + arryFile[2];
				}else{
					return arryFile[0] + '<div class="fileQ313596790Qfile"></div>' + fileHtml + '<div class="fileQ313596790Qfile"></div>' + arryFile[2];
				};
			}else{
				return arryFile[0] + filecode + arryFile[2];
			}
		}else{
			return html;
		};
	}
	
    $(document).ready(function() {
    setup_draggable();
    $("#n-columns").on("change",
    function() {
        var v = $(this).val();
        if (v === "1") {
            var $col = $(".form-body .col-md-12").toggle(true);
            $(".form-body .col-md-6 .draggable").each(function(i, el) {
                $(this).remove().appendTo($col);
            });
            $(".form-body .col-md-6").toggle(false);
        } else {
            var $col = $(".form-body .col-md-6").toggle(true);
            $(".form-body .col-md-12 .draggable").each(function(i, el) {
                $(this).remove().appendTo(i % 2 ? $col[1] : $col[0]);
            });
            $(".form-body .col-md-12").toggle(false);
        };
    });
    $("#copy-to-clipboard").on("click",
	    function() {
	        var $copy = $(".form-body").clone().appendTo(document.body);
	        $copy.find(".tools, :hidden").remove();
	        $.each(["draggable", "droppable", "sortable", "dropped", "ui-sortable", "ui-draggable", "ui-droppable", "form-body"],
	        function(i, c) {
	            $copy.find("." + c).removeClass(c).removeAttr("style");
	        });
	        var html = html_beautify($copy.html());
	        html = getUeditorFormHtml(html,'2','2');
	        html = getSelectFormHtml(html,'2','2');
	        html = getFileFormHtml(html,'2','2');
	        $copy.remove();
	        $modal = get_modal(html).modal("show");
	        $modal.find(".btn").remove();
	        $('#myHtml').val(html);
	        $modal.find("#myBtn").html("<button type=\"submit\" class=\"btn btn-primary\" data-clipboard-text=\"testing\" onclick=\"downloadCode($('#myHtml').val())\">下载代码</button>");
	        $modal.find(".modal-title").html("生成的HTML代码");
	        $modal.find(":input:first").select().focus();
	        return false;
	    });
	});
	var setup_draggable = function() {
	    $(".draggable").draggable({
	        appendTo: "body",
	        helper: "clone"
	    });
	    $(".droppable").droppable({
	        accept: ".draggable",
	        helper: "clone",
	        hoverClass: "droppable-active",
	        drop: function(event, ui) {
	            $(".empty-form").remove();
	            var $orig = $(ui.draggable);
	            if (!$(ui.draggable).hasClass("dropped")) {
	                var $el = $orig.clone().addClass("dropped").css({
	                    "position": "static",
	                    "left": null,
	                    "right": null
	                }).appendTo(this);
	                var id = $orig.find(":input").attr("id");
	                if (id) {
	                    id = id.split("-").slice(0, -1).join("-") + "-" + (parseInt(id.split("-").slice( - 1)[0]) + 1);
	                    $orig.find(":input").attr("id", id);
	                    $orig.find("label").attr("for", id);
	                }
	                $('<p class="tools col-sm-12 col-sm-offset-3">						<a class="edit-link">编辑HTML<a> | 						<a class="remove-link">移除</a></p>').appendTo($el);
	            } else {
	                if ($(this)[0] != $orig.parent()[0]) {
	                    var $el = $orig.clone().css({
	                        "position": "static",
	                        "left": null,
	                        "right": null
	                    }).appendTo(this);
	                    $orig.remove();
	                }
	            }
	        }
	    }).sortable();
	};
	var get_modal = function(content) {
	    var modal = $('<div class="modal" style="overflow: auto;" tabindex="-1">	<div class="modal-dialog"><div class="modal-content"><div class="modal-header"><a type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</a><h4 class="modal-title">编辑HTML</h4></div><div class="modal-body ui-front">	<textarea id="myHtml" class="form-control" 	style="min-height: 200px; margin-bottom: 10px;font-family: Monaco, Fixed">' + content + '</textarea><div id="myBtn"><button class="btn btn-success">更新HTML</button></div></div>				</div></div></div>').appendTo(document.body);
	    return modal;
	};
	$(document).on("click", ".edit-link",
	function(ev) {
	    var $el = $(this).parent().parent();
	    var $el_copy = $el.clone();
	    var $edit_btn = $el_copy.find(".edit-link").parent().remove();
	    var memberHtml = html_beautify($el_copy.html());
	    var editHtml = getUeditorFormHtml(memberHtml,'1','2');
	    	editHtml = getSelectFormHtml(editHtml,'1','2');
	    	editHtml = getFileFormHtml(editHtml,'1','2');
	    var $modal = get_modal(editHtml).modal("show");
	    $modal.find(":input:first").focus();
	    $modal.find(".btn-success").click(function(ev2) {
	        var html = $modal.find("textarea").val();
	        html = getUeditorFormHtml(html,'1','1');
	        getSelectFormHtml(html,'1','1');
	        html = getFileFormHtml(html,'1','1');
	        if (!html) {
	            $el.remove();
	        } else {
	            $el.html(html);
	            $edit_btn.appendTo($el);
	        }
	        $modal.modal("hide");
	        return false;
	    });
	});
	$(document).on("click", ".remove-link",
	function(ev) {
	    $(this).parent().parent().remove();
	});
    
	 
	
// 	function preLook(){
//   	var content = UE.getEditor('editor').getContent();
// 		localStorage.setItem('content',content);
//         window.open('http://localhost:8080/lotteryManager/main/test.html','','height=800,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')  
// 	}
	
	
	//预览
	function toPreShow(){
		var toPreShowContent = UE.getEditor('editor').getContent();
		var str = '<style type="text/css">#mydiv{margin:0 auto;  text-align:center; width:375px; height:667px; border:1px solid red; overflow:hidden; } #mydiv img{ max-width:375px; max-height:200px;overflow:hidden; }</style>';
		var toPreShowContentValue= str+"<div id='mydiv'  style='OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>" +  toPreShowContent +"</div>";
		bootbox.confirm(toPreShowContentValue, function(result) {
			if(result) {
			}
		});
	}
	
    </script>
</body>
</html>