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
					
					<form action="otheroutstorage/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="other_outstorage_id" id="other_outstorage_id" value="${pd.other_outstorage_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">出库单ID:</td>
								<td><input type="number" name="other_outstorage_id" id="other_outstorage_id" value="${pd.other_outstorage_id}" maxlength="32" placeholder="这里输入入库单ID" title="入库单ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">出库单编码:</td>
								<td><input type="text" name="other_outstorage_code" id="other_outstorage_code" value="${pd.other_outstorage_code}" maxlength="50" placeholder="这里输入入库单编码" title="入库单编码" style="width:98%;"/></td>
							</tr>
							<tr  style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库编码:</td>
								<td><input type="text" name="store_sn" id="store_sn" value="${pd.store_sn}" maxlength="20" placeholder="这里输入仓库编码" title="仓库编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
								<td><input type="text" name="store_name"   onclick="selectStore(storeForOther)" readonly="readonly" id="store_name" value="${pd.store_name}" maxlength="50" placeholder="这里输入仓库名称" title="仓库名称" style="width:98%;"/></td>
							</tr>
						<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"> 预计出库时间:</td>
								<td><input class="span10 date-picker" name="pre_send_time" id="pre_send_time" value="${pd.pre_send_time!=null?DateUtil.toDateStr(pd.pre_send_time):''}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="预计出库时间" title="预计出库时间" style="width:98%;" /></td>
							
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="remark" id="remark" value="${pd.remark}" maxlength="500" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
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
	<%@ include file="../common/selectStore.jsp"%>
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
			
			if($("#other_outstorage_code").val()==""){
				$("#other_outstorage_code").tips({
					side:3,
		            msg:'请输入入库单编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#other_outstorage_code").focus();
			return false;
			}
			if($("#store_sn").val()==""){
				$("#store_sn").tips({
					side:3,
		            msg:'请输入仓库编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_sn").focus();
			return false;
			}
			if($("#store_name").val()==""){
				$("#store_name").tips({
					side:3,
		            msg:'请输入仓库名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_name").focus();
			return false;
			}
			if($("#remark").val()==""){
				$("#remark").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#remark").focus();
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