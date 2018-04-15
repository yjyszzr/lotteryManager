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
					
					<form action="purchasereturn/returnGoods.do" name="Form" id="Form" method="post">
						<input type="hidden" name="return_id" id="return_id" value="${pd.return_id}"/>
						<input type="hidden" name="returnGoodsData" id="returnGoodsData" value="${pd.returnGoodsData}"/>
						<input type="hidden" name="menu_type" id="menu_type" value="${pd.menu_type}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库名称:</td>
								<td>
								    <input type="text" name="store_name" id="store_name" value="${pd.store_name}" readonly="readonly" maxlength="32" placeholder="这里输入仓库名称" title="仓库名称" style="width:82%;"/>
								    <input type="hidden" name="store_sn" id="store_sn" value="${pd.store_sn}" />
									<a class="btn btn-mini btn-primary" onclick="selectFromStormSn();">选择仓库</a>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预计发货时间:</td>
								<td>
								    <input class="span10 date-picker" name="pre_send_time" id="pre_send_time" value="${pd.pre_send_time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:98%;" placeholder="预计发货时间" title="预计发货时间"/>
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
			if($("#pre_send_time").val()==""){
				$("#pre_send_time").tips({
					side:3,
		            msg:'请输入预计发货时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pre_send_time").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		var selectStoreDiag = null;
		
		//选择调出仓库
		function selectFromStormSn(){
			 var types = "1";
			 top.jzts();
			 selectFromStormSnDiag = new top.Dialog();
			 selectFromStormSnDiag.Drag=true;
			 selectFromStormSnDiag.Title ="选择仓库";
			 selectFromStormSnDiag.URL = '<%=basePath%>szystore/listTo.do?types='+types;
			 selectFromStormSnDiag.Width = 1250;
			 selectFromStormSnDiag.Height = 755;
			 selectFromStormSnDiag.Modal = true;				//有无遮罩窗口
			 selectFromStormSnDiag. ShowMaxButton = true;	//最大化按钮
			 selectFromStormSnDiag.ShowMinButton = true;		//最小化按钮
			 selectFromStormSnDiag.CancelEvent = function(){ //关闭事件
				 selectFromStormSnDiag.close();
			 };
			 selectFromStormSnDiag.show();
		}
		
		//关闭选择调出仓库页面
		function closeToStormSnDiag(sn,name){
			$("#store_sn").val(sn);
			$("#store_name").val(name);
			selectFromStormSnDiag.close();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		})
	</script>
</body>
</html>