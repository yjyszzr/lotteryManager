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
					
					<form action="baskballmatch/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="match_id" id="match_id" value="${pd.match_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联赛名称:</td>
								<td><input type="text" name="league_name" id="league_name" value="${pd.league_name}" maxlength="32" placeholder="这里输入联赛名称" title="联赛名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联赛简称:</td>
								<td><input type="text" name="league_abbr" id="league_abbr" value="${pd.league_abbr}" maxlength="20" placeholder="这里输入联赛简称" title="联赛简称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">场次:</td>
								<td><input type="text" name="changci" id="changci" value="${pd.changci}" maxlength="20" placeholder="这里输入场次" title="场次" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">主队名称:</td>
								<td><input type="text" name="home_team_name" id="home_team_name" value="${pd.home_team_name}" maxlength="32" placeholder="这里输入主队名称" title="主队名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">主队简称:</td>
								<td><input type="text" name="home_team_abbr" id="home_team_abbr" value="${pd.home_team_abbr}" maxlength="20" placeholder="这里输入主队简称" title="主队简称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">主队排名:</td>
								<td><input type="text" name="home_team_rank" id="home_team_rank" value="${pd.home_team_rank}" maxlength="20" placeholder="这里输入主队排名" title="主队排名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">客队名称:</td>
								<td><input type="text" name="visiting_team_name" id="visiting_team_name" value="${pd.visiting_team_name}" maxlength="32" placeholder="这里输入客队名称" title="客队名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">客队简称:</td>
								<td><input type="text" name="visiting_team_abbr" id="visiting_team_abbr" value="${pd.visiting_team_abbr}" maxlength="20" placeholder="这里输入客队简称" title="客队简称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">客队排名:</td>
								<td><input type="text" name="visiting_team_rank" id="visiting_team_rank" value="${pd.visiting_team_rank}" maxlength="20" placeholder="这里输入客队排名" title="客队排名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">比赛时间:</td>
								<td><input type="text" name="match_time" id="match_time" value="${pd.match_time}" maxlength="19" placeholder="这里输入比赛时间" title="比赛时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">展示时间:</td>
								<td><input type="text" name="show_time" id="show_time" value="${pd.show_time}" maxlength="19" placeholder="这里输入展示时间" title="展示时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input type="text" name="create_time" id="create_time" value="${pd.create_time}" maxlength="20" placeholder="这里输入创建时间" title="创建时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否展示:</td>
								<td><input type="number" name="is_show" id="is_show" value="${pd.is_show}" maxlength="32" placeholder="这里输入是否展示" title="是否展示" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否删除:</td>
								<td><input type="number" name="is_del" id="is_del" value="${pd.is_del}" maxlength="32" placeholder="这里输入是否删除" title="是否删除" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">比赛编号:</td>
								<td><input type="text" name="match_sn" id="match_sn" value="${pd.match_sn}" maxlength="45" placeholder="这里输入比赛编号" title="比赛编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">全场:</td>
								<td><input type="text" name="whole" id="whole" value="${pd.whole}" maxlength="45" placeholder="这里输入全场" title="全场" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="text" name="status" id="status" value="${pd.status}" maxlength="2" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否热门:</td>
								<td><input type="number" name="is_hot" id="is_hot" value="${pd.is_hot}" maxlength="32" placeholder="这里输入是否热门" title="是否热门" style="width:98%;"/></td>
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
			if($("#league_name").val()==""){
				$("#league_name").tips({
					side:3,
		            msg:'请输入联赛名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#league_name").focus();
			return false;
			}
			if($("#league_abbr").val()==""){
				$("#league_abbr").tips({
					side:3,
		            msg:'请输入联赛简称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#league_abbr").focus();
			return false;
			}
			if($("#changci").val()==""){
				$("#changci").tips({
					side:3,
		            msg:'请输入场次',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#changci").focus();
			return false;
			}
			if($("#home_team_name").val()==""){
				$("#home_team_name").tips({
					side:3,
		            msg:'请输入主队名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#home_team_name").focus();
			return false;
			}
			if($("#home_team_abbr").val()==""){
				$("#home_team_abbr").tips({
					side:3,
		            msg:'请输入主队简称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#home_team_abbr").focus();
			return false;
			}
			if($("#home_team_rank").val()==""){
				$("#home_team_rank").tips({
					side:3,
		            msg:'请输入主队排名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#home_team_rank").focus();
			return false;
			}
			if($("#visiting_team_name").val()==""){
				$("#visiting_team_name").tips({
					side:3,
		            msg:'请输入客队名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#visiting_team_name").focus();
			return false;
			}
			if($("#visiting_team_abbr").val()==""){
				$("#visiting_team_abbr").tips({
					side:3,
		            msg:'请输入客队简称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#visiting_team_abbr").focus();
			return false;
			}
			if($("#visiting_team_rank").val()==""){
				$("#visiting_team_rank").tips({
					side:3,
		            msg:'请输入客队排名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#visiting_team_rank").focus();
			return false;
			}
			if($("#match_time").val()==""){
				$("#match_time").tips({
					side:3,
		            msg:'请输入比赛时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#match_time").focus();
			return false;
			}
			if($("#show_time").val()==""){
				$("#show_time").tips({
					side:3,
		            msg:'请输入展示时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#show_time").focus();
			return false;
			}
			if($("#create_time").val()==""){
				$("#create_time").tips({
					side:3,
		            msg:'请输入创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#create_time").focus();
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
			if($("#is_del").val()==""){
				$("#is_del").tips({
					side:3,
		            msg:'请输入是否删除',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_del").focus();
			return false;
			}
			if($("#match_sn").val()==""){
				$("#match_sn").tips({
					side:3,
		            msg:'请输入比赛编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#match_sn").focus();
			return false;
			}
			if($("#whole").val()==""){
				$("#whole").tips({
					side:3,
		            msg:'请输入全场',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#whole").focus();
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
			if($("#is_hot").val()==""){
				$("#is_hot").tips({
					side:3,
		            msg:'请输入是否热门',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#is_hot").focus();
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