﻿<%@page import="com.fh.util.DateUtil"%>
<%@page import="com.fh.util.MoneyUtil" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
							
						<form action="usermanagercontroller/list.do" method="post" name="Form" id="Form">
					<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
									<td style="text-align: right;width:200px">
	                                	<label class=" no-padding-right" for="form-field-1">账户总金额：</label>
                                	</td>
                                	<td style="text-align: left;width:200px"  >
	                                	<label class=" no-padding-right" for="form-field-1">${pd.allAmount}</label>
	                                </td>
                               		<td style="text-align: right;width:200px" >
	                                	<label class=" no-padding-right" for="form-field-1">可提现金额：</label>
                           		  	</td>
                           		  	<td style="text-align: left;width:200px" >	
	                                	<label class=" no-padding-right" for="form-field-1">${pd.user_money }</label>
                           		  	</td>
							</tr>
							<tr>
									<td style="text-align: right;width:200px">
	                                	<label class=" no-padding-right" for="form-field-1">可使用红包金额：</label>
                                	</td>
                                	<td style="text-align: left;width:200px"  >
	                                	<label class=" no-padding-right" for="form-field-1">${MoneyUtil.formatDouble(pd.unUseBonus)}</label>
	                                </td>
                           		  	<td style="text-align: right;width:200px" >
	                                	<label class=" no-padding-right" for="form-field-1">充值总金额：</label>
                           		  	</td>
                           		  	<td style="text-align: left;width:200px" >	
	                                	<label class=" no-padding-right" for="form-field-1">${MoneyUtil.formatDouble(pd.rechargeAllAmount) }</label>
                           		  	</td>
							</tr>
							<tr>
									<td style="text-align: right;width:200px">
	                                	<label class=" no-padding-right" for="form-field-1">奖金总金额：</label>
                                	</td>
                                	<td style="text-align: left;width:200px"  >
	                                	<label class=" no-padding-right" for="form-field-1">${MoneyUtil.formatDouble(pd.rewardAllAmount)}</label>
	                                </td>
									<td style="text-align: right;width:200px">
	                                	<label class=" no-padding-right" for="form-field-1">购彩总金额：</label>
                                	</td>
                                	<td style="text-align: left;width:200px"  >
	                                	<label class=" no-padding-right" for="form-field-1">${MoneyUtil.formatDouble(-pd.buyTicketAllAmount)}</label>
	                                </td>
							</tr>
						</table>
					
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">交易类型</th>
									<th class="center">金额</th>
									<th class="center">账户余额</th>
									<th class="center">可提现金额</th>
									<th class="center">状态</th>
									<th class="center">交易时间</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty pd.userAccountList}">
<%-- 							${pd.userAccountList} --%>
<%-- 									<c:if test="${QX.cha == 1 }"> --%>
									<c:forEach items="${pd.userAccountList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
													<c:choose>
														<c:when test="${var.process_type == 1}">奖金</c:when>
														<c:when test="${var.process_type == 2}">充值</c:when>
														<c:when test="${var.process_type == 3}">购彩</c:when>
														<c:when test="${var.process_type == 4}">提现</c:when>
														<c:when test="${var.process_type == 5}">红包</c:when>
														<c:when test="${var.process_type == 6}">账户回滚</c:when>
														<c:when test="${var.process_type == 7}">购券</c:when>
													</c:choose>
										 	</td>
											<td class='center'>
												<c:choose>
													<c:when test="${var.amount < 0}">${-var.amount}</c:when>
													<c:otherwise>${var.amount}</c:otherwise>
												</c:choose> 
											</td>
											<td class="center">${var.cur_balance}</td>
											<td class="center">${var.user_surplus}</td>
											<td class="center"> 
												<c:choose>
													<c:when test="${var.status == 0}">未完成</c:when>
													<c:when test="${var.status == 1}">成功</c:when>
													<c:when test="${var.status == 2}">失败</c:when>
												</c:choose>
											</td>
											<td class='center'>${DateUtil.toSDFTime(var.last_time*1000)}</td>
										</tr>
									
									</c:forEach>
<%-- 									</c:if> --%>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
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

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
		
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
			
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		function toConsumeDetail(consumeId){
			window.location.href='<%=basePath%>usermanagercontroller/toConsumeDetail.do?user_id='+userId;
		}
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>usermanagercontroller/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 tosearch();
					 }else{
						 tosearch();
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>usermanagercontroller/delete.do?user_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>usermanagercontroller/goEdit.do?user_id='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>usermanagercontroller/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											tosearch();
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>usermanagercontroller/excel.do';
		}
		
		//用户详情页
		function toDetail(userId){
			window.location.href='<%=basePath%>usermanagercontroller/toDetail.do?user_id='+userId;
		}
		
	</script>


</body>
</html>