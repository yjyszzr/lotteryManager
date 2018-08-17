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
					
					<form action="phonechannel/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<input type="hidden"  id="classify_ids"   name="article_classify_ids"  />
						<input type="hidden"  id="sorts"   name="sorts"  />
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道编号:</td>
								<td><input type="text" name="channel" id="channel" value="${pd.channel}" maxlength="45" placeholder="这里输入渠道编号" title="渠道编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道名称:</td>
								<td><input type="text" name="channel_name" id="channel_name" value="${pd.channel_name}" maxlength="60" placeholder="这里输入渠道名称" title="渠道名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">app编码:</td>
								<td><input type="number" name="app_code_name" id="app_code_name" value="${pd.app_code_name}" maxlength="32" placeholder="这里输入app编码" title="app编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">资讯类型:</td>
								<td>
									<c:forEach var="articleclassify" items="${articleclassifyList}" varStatus="row">	 	
									<div style="width:60%;">
										<label >
								 			 <input type="checkbox" value="${articleclassify.id}"  <c:if test="${articleclassify.isCheck ==1}"> checked="checked" </c:if>  name ="article_classify" id ="article_classify" >${articleclassify.classify_name} 
								 		 </label>
								 		 <input type="number"  value="${articleclassify.sort}" name ="sort" id ="sort" maxlength="2" placeholder="排序"  style="width:40%;float:right"/>
								 			</div>
								 			 <hr>
<%-- 								 		 <c:if test="${(row.index+1) % 4 ==0 }"> --%>
<%-- 								 		 </c:if> --%>
									 </c:forEach>
								</td>
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
			if($("#channel").val()==""){
				$("#channel").tips({
					side:3,
		            msg:'请输入渠道编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel").focus();
			return false;
			}
			if($("#channel_name").val()==""){
				$("#channel_name").tips({
					side:3,
		            msg:'请输入渠道名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#channel_name").focus();
			return false;
			}
			if($("#app_code_name").val()==""){
				$("#app_code_name").tips({
					side:3,
		            msg:'请输入app编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#app_code_name").focus();
			return false;
			}
			
			   var obj = document.getElementsByName("article_classify"); 
			   var objSort = document.getElementsByName("sort"); 
			      var ids=''; 
			      var sorts=''; 
			      for(var i=0;i<obj.length;i++){
			         if(obj[i].checked){
			        	 ids+=obj[i].value+',';  
			        	 sorts+=obj[i].value+':'+objSort[i].value+',';  
			         }
			      }
			      $("#classify_ids").val(ids);
			      $("#sorts").val(sorts);
			if($("#classify_ids").val()==""){
				$("#article_classify").tips({
					side:3,
		            msg:'请选择资讯类型',
		            bg:'#AE81FF',
		            time:2
		        });
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
		</script>
</body>
</html>