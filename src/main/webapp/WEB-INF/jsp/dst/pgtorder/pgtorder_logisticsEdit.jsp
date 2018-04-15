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
					
					<form action="pgtorder/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="pgt_order_id" id="pgt_order_id" value="${pd.pgt_order_id}"/>
						<input type="hidden" name="is_selfship"  value="1">
						<input type="hidden" name="is_selfship_commit"  value="1">
						<input type="hidden" name="shipping_status"  value="5">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物流公司:</td>
								<td><input type="text" name="logistics_company_name" onclick="toSelectLogisticsList()" id="logistics_company_name" value="${pd.logistics_company_name}" maxlength="20" placeholder="这里输入物流公司" title="物流公司"style="width:98%;"/></td>
							</tr>
							<tr style="display:none">
								<td style="width:75px;text-align: right;padding-top: 13px;">物流公司ID:</td>
								<td><input type="text" name="logistics_company_id" id="logistics_company_id" value="${pd.logistics_company_id}" maxlength="20" placeholder="这里输入物流公司" title="物流公司"style="width:98%;"/></td>
							</tr>
							<tr style="">
								<td style="width:75px;text-align: right;padding-top: 13px;">快递号:</td>
								<td><input type="text" name="logistics_code" id="logistics_code" value="${pd.logistics_code}" maxlength="20" placeholder="这里输入快递号" title="快递号"style="width:98%;"/></td>
							</tr>
							<tr style="">
								<td style="width:75px;text-align: right;padding-top: 13px;">运费:</td>
								<td><input type="text" name="freight" id="freight" value="${pd.freight}" maxlength="20" placeholder="这里输入运费" title="运费"style="width:98%;"/></td>
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
			if($("#pgt_order_id").val()==""){
				$("#pgt_order_id").tips({
					side:3,
		            msg:'请输入备注1',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pgt_order_id").focus();
			return false;
			}
			if($("#logistics_company_name").val()==""){
				$("#logistics_company_name").tips({
					side:3,
		            msg:'请输入公司名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#logistics_company_name").focus();
			return false;
			}
			if($("#logistics_code").val()==""){
				$("#logistics_code").tips({
					side:3,
		            msg:'请输入快递号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#logistics_code").focus();
			return false;
			}
			if($("#freight").val()==""){
				$("#freight").tips({
					side:3,
		            msg:'请输入运费',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#freight").focus();
			return false;
			}else if(!($("#freight").val()>0)){
				$("#freight").tips({
					side:3,
		            msg:'请输入正确的运费',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#freight").focus();
			return false;
			}
			
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		//------------------
		//选择物流公司
function toSelectLogisticsList(){
	
	 top.jzts();
	 selectTaskListDiag = new top.Dialog();
	 selectTaskListDiag.Drag=true;
	 selectTaskListDiag.Title ="选择物流公司";
	 selectTaskListDiag.URL = '<%=basePath%>/logisticscompany/toSelectLogisticsList.do';
		 selectTaskListDiag.Width = $(window).width();
		 selectTaskListDiag.Height = $(window).height();
		 selectTaskListDiag.Modal = true;				//有无遮罩窗口
		 selectTaskListDiag. ShowMaxButton = true;	//最大化按钮
		 selectTaskListDiag.ShowMinButton = true;		//最小化按钮 
		 selectTaskListDiag.CancelEvent = function(){ //关闭事件
		/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 tosearch();
		} */
		selectTaskListDiag.close();
	 };
	 selectTaskListDiag.show();
	 //关闭窗口
	 top.closeSelectLogisticsListDiag =function(logistics_company_id,logistics_company_name){
		selectTaskListDiag.close();
		$('#logistics_company_id').val(logistics_company_id);
		$('#logistics_company_name').val(logistics_company_name);
		
		}
}
		</script>
</body>
</html>