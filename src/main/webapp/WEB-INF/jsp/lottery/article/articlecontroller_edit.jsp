<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
											                        <form action="articlecontroller/${msg }.do" name="Form" id="Form" method="post"  target="_self" class="form-horizontal m-t">
									                        			<div id="zhongxin" style="padding-top: 13px;">
											                        	<div class="row" style="padding:5px">
											                                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"><lable style="color:red;">*</lable>文章标题：</label>
											                                <div class="col-sm-9">
											                                <input type="hidden"  id="article_id"   name ="article_id" value="${pd.article_id}"/>
											                                <input type="hidden"  id="match_id"   name ="match_id" value="${pd.match_id}"/>
											                                <input type="hidden"  id="content"  name="content"   value=""/>
											                                <input type="hidden"  id="status"  name="status"   value=""/>
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
																					<span class="lbl">世界杯</span>
																				</label>																				
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="extend_cat" type="radio" <c:if test="${pd.extend_cat==5}">checked="checked"</c:if>  value = "5" class="ace" id="extend_cat5" />
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
																				</div>
																				</div>
																				<div class="row"  style="padding:5px">
																				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> </label>
												                                <div class="col-sm-9">
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==6}">checked="checked"</c:if>   value = "6" class="ace" id="label_defaults_6" />
																					<span class="lbl">攻守</span>
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
																				<label style="float:left;padding-left: 5px;padding-top:7px;">
																					<input name="label_defaults" type="radio"   <c:if test="${pd.label_defaults==11}">checked="checked"</c:if>   value = "11" class="ace" id="label_defaults_11" />
																					<span class="lbl">世界杯</span>
																				</label>
											                                </div>
											                                </div>
											                                <div class="row"  style="padding:5px">
																				<label class="col-sm-3 control-label no-padding-right" for="form-field-1">文章内容：</label>
												                                <div class="col-sm-9"></div>
											                               </div>
											                               <div  id="editor"></div>
											                            <div >
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"></label>
											                                <div class="col-sm-9">
											                                   <a class="btn btn-mini btn-primary" onclick="save(1)" >发表</a>
											                                   <a class="btn btn-mini btn-danger" onclick="save('2')">保存草稿</a>
											                                   <a class="btn btn-mini btn-warming" onclick="toPreShow()">预览</a>
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
		var content = editor.txt.html();
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
	
    </script>
</body>
</html>