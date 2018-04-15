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
    <div id="mask" class="mask"></div>
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="allocation/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="allocation_id" id="allocation_id" value="${pd.allocation_id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<c:if test="${msg == 'save'}">
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">调拨编号:</td>
									<td><input type="text" name="allocation_code" id="allocation_code" value="${pd.allocation_code}" readonly="readonly" maxlength="50" placeholder="这里输入调拨编号" title="调拨编号" style="width:98%;"/></td>
								</tr>
							</c:if>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">调出仓库编号:</td>
								<td><input type="text" name="from_store_sn" id="from_store_sn" value="${pd.from_store_sn}" readonly="readonly" maxlength="20" placeholder="这里输入调出仓库编号" title="调出仓库编号" style="width:70%;"/>
								    <a ${msg=='save'?"":"disabled='disabled'" } class="btn btn-mini btn-primary" onclick="selectFromStormSn();" style="width:20%;">选择调出仓库</a>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">调出仓库名称:</td>
								<td><input type="text" name="from_store_name" id="from_store_name" value="${pd.from_store_name}" readonly="readonly" maxlength="50" placeholder="这里输入调出仓库名称" title="调出仓库名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">调入仓库编号:</td>
								<td><input type="text" name="to_store_sn" id="to_store_sn" value="${pd.to_store_sn}" readonly="readonly" maxlength="20" placeholder="这里输入调入仓库编号" title="调入仓库编号" style="width:70%;"/>
								    <a ${msg=='save'?"":"disabled='disabled'" } class="btn btn-mini btn-primary" onclick="selectToStormSn();">选择调入仓库</a>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">调入仓库名称:</td>
								<td><input type="text" name="to_store_name" id="to_store_name" value="${pd.to_store_name}" readonly="readonly" maxlength="50" placeholder="这里输入调入仓库名称" title="调入仓库名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预计发货时间:</td>
								<td>
								    <input class="span10 date-picker" name="pre_send_time" id="pre_send_time" value="${pd.pre_send_time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:98%;" placeholder="预计发货时间" title="预计发货时间"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预计到货时间:</td>
								<td>
								    <input class="span10 date-picker" name="pre_arrival_time" id="pre_arrival_time" value="${pd.pre_arrival_time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:98%;" placeholder="预计到货时间" title="预计到货时间"/>
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
			if($("#allocation_code").val()==""){
				$("#allocation_code").tips({
					side:3,
		            msg:'请输入调拨编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#allocation_code").focus();
			return false;
			}
			if($("#from_store_sn").val()==""){
				$("#from_store_sn").tips({
					side:3,
		            msg:'请输入调出仓库编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#from_store_sn").focus();
			return false;
			}
			if($("#to_store_sn").val()==""){
				$("#to_store_sn").tips({
					side:3,
		            msg:'请输入调入仓库编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#to_store_sn").focus();
			return false;
			}
			if($("#from_store_name").val()==""){
				$("#from_store_name").tips({
					side:3,
		            msg:'请输入调出仓库名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#from_store_name").focus();
			return false;
			}
			if($("#to_store_name").val()==""){
				$("#to_store_name").tips({
					side:3,
		            msg:'请输入调入仓库名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#to_store_name").focus();
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
			if($("#pre_arrival_time").val()==""){
				$("#pre_arrival_time").tips({
					side:3,
		            msg:'请输入预计到货时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pre_arrival_time").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		function isStoreTypeSame(fromStoreSn,toStoreSn){
			$.ajax({
			    type: "POST",
			    url: '<%=basePath%>allocationdetail/isStoreTypeSame.do',
			        data: {from_store_sn:fromStoreSn,to_store_sn:toStoreSn},
			    dataType:'json',
			    cache: false,
			    success: function(data){
			       if("success" != data.result){
				       return false;
			       }else{
			    	   return true;
			       }
			    }
		    });
		}
		
		//兼容火狐、IE8   
		//显示遮罩层    
		function showMask(){     
			$("#mask").css("height",top.window.innerHeight);     
			$("#mask").css("width",top.window.innerWidth);     
			$("#mask").show();    
		}

		//隐藏遮罩层  
		function hideMask(){     
			$("#mask").hide();     
		}
		
		var selectFromStormSnDiag = null;
		var selectToStormSnDiag = null;
		
		//选择调出仓库
		function selectFromStormSn(){
			 var types = "0,1,3,5";
			 top.jzts();
			 selectFromStormSnDiag = new top.Dialog();
			 selectFromStormSnDiag.Drag=true;
			 selectFromStormSnDiag.Title ="选择调出仓库";
			 selectFromStormSnDiag.URL = '<%=basePath%>szystore/list.do?types='+types;
			 selectFromStormSnDiag.Width = 1250;
			 selectFromStormSnDiag.Height = 755;
			 selectFromStormSnDiag.Modal = false;				//有无遮罩窗口
			 selectFromStormSnDiag. ShowMaxButton = true;	//最大化按钮
			 selectFromStormSnDiag.ShowMinButton = true;		//最小化按钮
			 selectFromStormSnDiag.CancelEvent = function(){ //关闭事件
				 selectFromStormSnDiag.close();
			 };
			 selectFromStormSnDiag.show();
		}
		
		//关闭选择调出仓库页面
		function closeFromStormSnDiag(sn,name){
			$("#from_store_sn").val(sn);
			$("#from_store_name").val(name);
			selectFromStormSnDiag.close();
			var fromStoreSn = $.trim($("#from_store_sn").val());
			var toStoreSn = $.trim($("#to_store_sn").val());
			if((fromStoreSn != null && fromStoreSn.length != 0) && (toStoreSn != null && toStoreSn.length != 0)){
				if(fromStoreSn == toStoreSn){
				   $("#from_store_sn").tips({
		                 side:3,
		                 msg:'调出仓库和调入仓库不能是同一个仓库，不能调拨！',
		                 bg:'#AE81FF',
		                 time:3
		           });
				   $("#from_store_sn").val("");
				   $("#from_store_name").val("");
				}else{
					$.ajax({
					    type: "POST",
					    url: '<%=basePath%>allocationdetail/isStoreTypeSame.do',
					        data: {from_store_sn:fromStoreSn,to_store_sn:toStoreSn},
					    dataType:'json',
					    cache: false,
					    success: function(data){
					       if("success" != data.result){
					    	   $("#from_store_sn").tips({
					                 side:3,
					                 msg:'调出仓库和调入仓库类型不一致，不能调拨！',
					                 bg:'#AE81FF',
					                 time:3
					           });
							   $("#from_store_sn").val("");
							   $("#from_store_name").val("");
					       }
					    }
				    });
				}
			}
		}
		
		//选择调入仓库
		function selectToStormSn(){
			 var types = "0,1,3,5";
			 top.jzts();
			 selectToStormSnDiag = new top.Dialog();
			 selectToStormSnDiag.Drag=true;
			 selectToStormSnDiag.Title ="选择调入仓库";
			 selectToStormSnDiag.URL = '<%=basePath%>szystore/listTo.do?types='+types;
			 selectToStormSnDiag.Width = 1250;
			 selectToStormSnDiag.Height = 755;
			 selectToStormSnDiag.Modal = false;				//有无遮罩窗口
			 selectToStormSnDiag. ShowMaxButton = true;	//最大化按钮
			 selectToStormSnDiag.ShowMinButton = true;		//最小化按钮
			 selectToStormSnDiag.CancelEvent = function(){ //关闭事件
				 selectToStormSnDiag.close();
			 };
			 selectToStormSnDiag.show();
		}
		
		//关闭选择调入仓库页面
		function closeToStormSnDiag(sn,name){
			$("#to_store_sn").val(sn);
			$("#to_store_name").val(name);
			selectToStormSnDiag.close();
			var fromStoreSn = $.trim($("#from_store_sn").val());
			var toStoreSn = $.trim($("#to_store_sn").val());
			if((fromStoreSn != null && fromStoreSn.length != 0) && (toStoreSn != null && toStoreSn.length != 0)){
				if(fromStoreSn == toStoreSn){
				   $("#to_store_sn").tips({
		                 side:3,
		                 msg:'调出仓库和调入仓库不能是同一个仓库，不能调拨！',
		                 bg:'#AE81FF',
		                 time:3
		           });
				   $("#to_store_sn").val("");
				   $("#to_store_name").val("");
				}else{
					$.ajax({
					    type: "POST",
					    url: '<%=basePath%>allocationdetail/isStoreTypeSame.do',
					        data: {from_store_sn:fromStoreSn,to_store_sn:toStoreSn},
					    dataType:'json',
					    cache: false,
					    success: function(data){
					       if("success" != data.result){
					    	   $("#to_store_sn").tips({
					                 side:3,
					                 msg:'调出仓库和调入仓库类型不一致，不能调拨！',
					                 bg:'#AE81FF',
					                 time:3
					           });
							   $("#to_store_sn").val("");
							   $("#to_store_name").val("");
					       }
					    }
				    });
				}
			}
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>