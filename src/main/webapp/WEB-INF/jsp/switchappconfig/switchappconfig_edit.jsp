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
	<%@ include file="../system/index/top.jsp"%>
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
					
					<form action="switchappconfig/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">app版本号</td>
								<td><input type="text" name="version" id="version" value="${pd.version}" maxlength="10" placeholder="这里输入app版本号" title="app版本号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">平台</td>
								<td>
								<!-- <input type="text" name="platform" id="platform" value="${pd.platform}" maxlength="1" title="平台" style="width:98%;"/> -->
									<select class="chosen-select form-control" name="platform" id="platform" value="${pd.platform}"  data-placeholder="请选择平台" style="vertical-align:top;width: 120px;">
										<option value="0">ios</option>
										<option value="1">android</option>
								  	</select
								
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">业务版本</td>
								<td>
								<!--  <input type="text" name="business_type" id="business_type" value="${pd.business_type}" maxlength="255" placeholder="这里输入业务版本:1-交易版 2-资讯版" title="业务版本" style="width:98%;"/>-->
									<select class="chosen-select form-control" name="business_type" id="business_type" value="${pd.business_type}"  data-placeholder="请选择业务版本" style="vertical-align:top;width: 120px;">
										<option value="1">交易版 </option>
								  	</select
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">渠道</td>
								<td>
								 	<select class="chosen-select form-control" name="channel" id="channel" value="${pd.channel}"  data-placeholder="请选择渠道" style="vertical-align:top;width: 120px;">
										<option value="c16010">appstore</option>
										<option value="c10010">google</option>
										<option value="c10020">vivo</option>
										<option value="c10021">oppo</option>
										<option value="c10022">华为</option>
										<option value="c10023">阿里应用分发</option>
										<option value="c10024">魅族</option>
										<option value="c10025">金立</option>
										<option value="c10026">三星</option>
										<option value="c10027">小米</option>
										<option value="c10028">百度</option>
										<option value="c10029">360</option>
										<option value="c10030">锤子</option>
										<option value="c10031">联想</option>
										<option value="c10032">搜狗</option>
										<option value="c10033">机锋</option>
										<option value="c10034">应用汇</option>
										<option value="c10035">乐视</option>	
										<option value="c10036">应用宝</option>
										<option value="c10037">木蚂蚁</option>
										<option value="c10038">安智</option>	
										
										<option value="c26010">appstoreMJ</option>
										<option value="c20010">googleMJ</option>
										<option value="c20020">vivoMJ</option>
										<option value="c20021">oppoMJ</option>
										<option value="c20022">华为MJ</option>
										<option value="c20023">阿里应用分发MJ</option>
										<option value="c20024">魅族MJ</option>
										<option value="c20025">金立MJ</option>
										<option value="c20026">三星MJ</option>
										<option value="c20027">小米MJ</option>
										<option value="c20028">百度MJ</option>
										<option value="c20029">360MJ</option>
										<option value="c20030">锤子MJ</option>
										<option value="c20031">联想MJ</option>
										<option value="c20032">搜狗MJ</option>
										<option value="c20033">机锋MJ</option>
										<option value="c20034">应用汇MJ</option>
										<option value="c20035">乐视MJ</option>	
										<option value="c20036">应用宝MJ</option>
										<option value="c20037">木蚂蚁MJ</option>
										<option value="c20038">安智MJ</option>																				
								  	</select>
								</td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开关</td>
								<td><input type="text" name="turn_on" id="turn_on" value="${pd.turn_on}" maxlength="1" placeholder="这里输入0- 关 1- 开" title="开关" style="width:98%;"/></td>
								<!--  <td> 
									<label>
										<input name="turn_on" id="turn_on" value="${pd.turn_on}" checked="checked" class="ace ace-switch ace-switch-3" type="checkbox" />
										<span class="lbl"></span>
									</label>
								
								</td> -->
								<!--  <td><input type="text" name="turn_on" id="turn_on" value="${pd.turn_on}" maxlength="1" placeholder="这里输入0- 关 1- 开" title="开关" style="width:98%;"/></td>-->
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
	<%@ include file="../system/index/foot.jsp"%>
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
			if($("#version").val()==""){
				$("#version").tips({
					side:3,
		            msg:'请输入app版本号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#version").focus();
			return false;
			}
			if($("#platform").val()==""){
				$("#platform").tips({
					side:3,
		            msg:'请输入平台:0-ios 1-android',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#platform").focus();
			return false;
			}
			if($("#business_type").val()==""){
				$("#business_type").tips({
					side:3,
		            msg:'请输入业务版本:1-交易版 2-资讯版',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#business_type").focus();
			return false;
			}
			
			if($("#chanel").val()==""){
				$("#chanel").tips({
					side:3,
		            msg:'请选择渠道',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#chanel").focus();
			return false;
			}
			
			if($("#turn_on").val()==""){
				$("#turn_on").tips({
					side:3,
		            msg:'请填写开关',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#turn_on").focus();
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