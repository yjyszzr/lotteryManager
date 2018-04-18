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
					
					<form action="articlecontroller/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="article_id" id="article_id" value="${pd.article_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">文章标题:</td>
								<td><input type="text" name="title" id="title" value="${pd.title}" maxlength="100" placeholder="这里输入文章标题" title="文章标题" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注3:</td>
								<td><input type="number" name="cat_id" id="cat_id" value="${pd.cat_id}" maxlength="32" placeholder="这里输入备注3" title="备注3" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">文章内容:</td>
								<td><input type="text" name="content" id="content" value="${pd.content}" maxlength="715827882" placeholder="这里输入文章内容" title="文章内容" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注5:</td>
								<td><input type="text" name="keywords" id="keywords" value="${pd.keywords}" maxlength="255" placeholder="这里输入备注5" title="备注5" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注6:</td>
								<td><input type="text" name="jurisdiction" id="jurisdiction" value="${pd.jurisdiction}" maxlength="255" placeholder="这里输入备注6" title="备注6" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注7:</td>
								<td><input type="text" name="article_thumb" id="article_thumb" value="${pd.article_thumb}" maxlength="255" placeholder="这里输入备注7" title="备注7" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注8:</td>
								<td><input type="number" name="add_time" id="add_time" value="${pd.add_time}" maxlength="32" placeholder="这里输入备注8" title="备注8" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注9:</td>
								<td><input type="number" name="is_comment" id="is_comment" value="${pd.is_comment}" maxlength="32" placeholder="这里输入备注9" title="备注9" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注10:</td>
								<td><input type="number" name="click_number" id="click_number" value="${pd.click_number}" maxlength="32" placeholder="这里输入备注10" title="备注10" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注11:</td>
								<td><input type="number" name="is_show" id="is_show" value="${pd.is_show}" maxlength="32" placeholder="这里输入备注11" title="备注11" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注12:</td>
								<td><input type="number" name="user_id" id="user_id" value="${pd.user_id}" maxlength="32" placeholder="这里输入备注12" title="备注12" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注13:</td>
								<td><input type="number" name="status" id="status" value="${pd.status}" maxlength="32" placeholder="这里输入备注13" title="备注13" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注14:</td>
								<td><input type="text" name="link" id="link" value="${pd.link}" maxlength="255" placeholder="这里输入备注14" title="备注14" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注15:</td>
								<td><input type="text" name="source" id="source" value="${pd.source}" maxlength="255" placeholder="这里输入备注15" title="备注15" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注16:</td>
								<td><input type="text" name="summary" id="summary" value="${pd.summary}" maxlength="255" placeholder="这里输入备注16" title="备注16" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注17:</td>
								<td><input type="text" name="extend_cat" id="extend_cat" value="${pd.extend_cat}" maxlength="255" placeholder="这里输入备注17" title="备注17" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注18:</td>
								<td><input type="number" name="is_recommend" id="is_recommend" value="${pd.is_recommend}" maxlength="32" placeholder="这里输入备注18" title="备注18" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注19:</td>
								<td><input type="text" name="author" id="author" value="${pd.author}" maxlength="45" placeholder="这里输入备注19" title="备注19" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注20:</td>
								<td><input type="number" name="match_id" id="match_id" value="${pd.match_id}" maxlength="32" placeholder="这里输入备注20" title="备注20" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注21:</td>
								<td><input type="text" name="related_team" id="related_team" value="${pd.related_team}" maxlength="45" placeholder="这里输入备注21" title="备注21" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注22:</td>
								<td><input type="number" name="list_style" id="list_style" value="${pd.list_style}" maxlength="32" placeholder="这里输入备注22" title="备注22" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注23:</td>
								<td><input type="number" name="is_original" id="is_original" value="${pd.is_original}" maxlength="32" placeholder="这里输入备注23" title="备注23" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注24:</td>
								<td><input type="number" name="is_delete" id="is_delete" value="${pd.is_delete}" maxlength="32" placeholder="这里输入备注24" title="备注24" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注25:</td>
								<td><input type="number" name="is_stick" id="is_stick" value="${pd.is_stick}" maxlength="32" placeholder="这里输入备注25" title="备注25" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注26:</td>
								<td><input type="number" name="stick_time" id="stick_time" value="${pd.stick_time}" maxlength="32" placeholder="这里输入备注26" title="备注26" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注27:</td>
								<td><input type="text" name="price" id="price" value="${pd.price}" maxlength="12" placeholder="这里输入备注27" title="备注27" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注28:</td>
								<td><input type="number" name="level" id="level" value="${pd.level}" maxlength="32" placeholder="这里输入备注28" title="备注28" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注29:</td>
								<td><input type="number" name="article_pv" id="article_pv" value="${pd.article_pv}" maxlength="32" placeholder="这里输入备注29" title="备注29" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注30:</td>
								<td><input type="number" name="article_uv" id="article_uv" value="${pd.article_uv}" maxlength="32" placeholder="这里输入备注30" title="备注30" style="width:98%;"/></td>
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
			if($("#title").val()==""){
				$("#title").tips({
					side:3,
		            msg:'请输入文章标题',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#title").focus();
			return false;
			}
			if($("#cat_id").val()==""){
				$("#cat_id").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#cat_id").focus();
			return false;
			}
			if($("#content").val()==""){
				$("#content").tips({
					side:3,
		            msg:'请输入文章内容',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#content").focus();
			return false;
			}
			if($("#keywords").val()==""){
				$("#keywords").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#keywords").focus();
			return false;
			}
			if($("#jurisdiction").val()==""){
				$("#jurisdiction").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#jurisdiction").focus();
			return false;
			}
			if($("#article_thumb").val()==""){
				$("#article_thumb").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#article_thumb").focus();
			return false;
			}
			if($("#add_time").val()==""){
				$("#add_time").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#add_time").focus();
			return false;
			}
			if($("#is_comment").val()==""){
				$("#is_comment").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_comment").focus();
			return false;
			}
			if($("#click_number").val()==""){
				$("#click_number").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#click_number").focus();
			return false;
			}
			if($("#is_show").val()==""){
				$("#is_show").tips({
					side:3,
		            msg:'请输入备注11',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_show").focus();
			return false;
			}
			if($("#user_id").val()==""){
				$("#user_id").tips({
					side:3,
		            msg:'请输入备注12',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_id").focus();
			return false;
			}
			if($("#status").val()==""){
				$("#status").tips({
					side:3,
		            msg:'请输入备注13',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#status").focus();
			return false;
			}
			if($("#link").val()==""){
				$("#link").tips({
					side:3,
		            msg:'请输入备注14',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#link").focus();
			return false;
			}
			if($("#source").val()==""){
				$("#source").tips({
					side:3,
		            msg:'请输入备注15',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#source").focus();
			return false;
			}
			if($("#summary").val()==""){
				$("#summary").tips({
					side:3,
		            msg:'请输入备注16',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#summary").focus();
			return false;
			}
			if($("#extend_cat").val()==""){
				$("#extend_cat").tips({
					side:3,
		            msg:'请输入备注17',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#extend_cat").focus();
			return false;
			}
			if($("#is_recommend").val()==""){
				$("#is_recommend").tips({
					side:3,
		            msg:'请输入备注18',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_recommend").focus();
			return false;
			}
			if($("#author").val()==""){
				$("#author").tips({
					side:3,
		            msg:'请输入备注19',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#author").focus();
			return false;
			}
			if($("#match_id").val()==""){
				$("#match_id").tips({
					side:3,
		            msg:'请输入备注20',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#match_id").focus();
			return false;
			}
			if($("#related_team").val()==""){
				$("#related_team").tips({
					side:3,
		            msg:'请输入备注21',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#related_team").focus();
			return false;
			}
			if($("#list_style").val()==""){
				$("#list_style").tips({
					side:3,
		            msg:'请输入备注22',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#list_style").focus();
			return false;
			}
			if($("#is_original").val()==""){
				$("#is_original").tips({
					side:3,
		            msg:'请输入备注23',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_original").focus();
			return false;
			}
			if($("#is_delete").val()==""){
				$("#is_delete").tips({
					side:3,
		            msg:'请输入备注24',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_delete").focus();
			return false;
			}
			if($("#is_stick").val()==""){
				$("#is_stick").tips({
					side:3,
		            msg:'请输入备注25',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_stick").focus();
			return false;
			}
			if($("#stick_time").val()==""){
				$("#stick_time").tips({
					side:3,
		            msg:'请输入备注26',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#stick_time").focus();
			return false;
			}
			if($("#price").val()==""){
				$("#price").tips({
					side:3,
		            msg:'请输入备注27',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#price").focus();
			return false;
			}
			if($("#level").val()==""){
				$("#level").tips({
					side:3,
		            msg:'请输入备注28',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#level").focus();
			return false;
			}
			if($("#article_pv").val()==""){
				$("#article_pv").tips({
					side:3,
		            msg:'请输入备注29',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#article_pv").focus();
			return false;
			}
			if($("#article_uv").val()==""){
				$("#article_uv").tips({
					side:3,
		            msg:'请输入备注30',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#article_uv").focus();
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