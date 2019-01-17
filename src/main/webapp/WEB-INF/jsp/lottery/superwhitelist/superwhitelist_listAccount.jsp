<%@page import="com.fh.util.DateUtil"%>
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
						<div id="zhongxin" style="padding-top: 13px;">
						
						<!-- 检索  -->
						<form action="superwhitelist/listAccount.do" method="post" name="Form" id="Form">
						<input type="hidden" name="user_id" id="user_id" value="${pd.user_id}"/>
						<input type="hidden" name="store_id" id="store_id" value="${pd.store_id}"/>
						<table style="margin-top: 5px;">
									<tr>
										<td>
											<div class="nav-search">
												<span class="input-icon">
													<input name="start_add_time" id="start_add_time" value="${pd.start_add_time}" type="text"
													onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
													readonly="readonly" style="width: 150px; border-radius: 5px !important"
													placeholder="开始时间" title="开始时间" /> 
													<input name="end_add_time" id="end_add_time" value="${pd.end_add_time}" type="text"
													onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
													readonly="readonly" style="width: 150px; border-radius: 5px !important"
													placeholder="结束时间" title="结束时间" />
												</span> 
											</div>
										</td>
										<td>
											<div class="nav-search">
												<span class="input-icon">
													<select title="扣款原因" class="chosen-select form-control" name="process_type" id="process_type" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
													<option value="">全部</option>
													<option value="2" <c:if test="${pd.process_type==2}">selected</c:if>>充值</option>
													<option value="4" <c:if test="${pd.process_type==4}">selected</c:if>>提现</option>
													<option value="8" <c:if test="${pd.process_type==8}">selected</c:if>>退款</option>
													<option value="9" <c:if test="${pd.process_type==9}">selected</c:if>>输入错误</option>
												  	</select>	
												</span> 
											</div>
										</td>	
										<td>
										<c:if test="${QX.cha == 1 }">
													<span class="input-icon" style="width: 10px;"> </span>
													<span> <a class="btn btn-light btn-xs blue" onclick="tosearch(1);" title="搜索"
														style="border-radius: 5px; color: blue !important; width: 50px">搜索</a>
													</span>
													<span> <a class="btn btn-light btn-xs blue" onclick="tosearch(0);" title="清空"
														style="border-radius: 5px; color: blue !important; width: 50px">清空</a>
													</span>
													<span>
														 <a class="btn btn-light btn-xs blue" onclick="toExcel();" title="导出到EXCEL"
														style="border-radius: 5px; color: blue !important; width: 50px">EXCEL</a>
													</span>
												</c:if>
										</td>		 
												 												
													 
												
												 
											</div>
										</td>
									</tr>
								</table>
						<!-- 检索  -->
						
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
								    <!--  
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									-->
									<th class="center">用户ID</th>
									<th class="center">手机号</th>
									<th class="center">帐户余额</th>
									<th class="center">充值金额</th>
									<th class="center">大礼包金额</th>
									<th class="center">充值原因</th>
									<th class="center">扣款原因</th>
									<th class="center">充值时间</th>
									<th class="center">店铺</th>
									<th class="center">充值状态</th>
									<th class="center">操作人</th>
								</tr>
							</thead>
							<tbody>
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<!-- 
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.user_id_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											 -->
											<td class='center'>${var.user_id}</td>
											<td class='center'>${var.mobile}</td>
											<td class='center'>${var.cur_balance}</td>
											<td class='center'>${var.amount}</td>
											<td class='center'>${var.recharge_card_real_value}</td>
											<td class='center'>
											<c:choose>
												<c:when test="${var.process_type == 1}">奖金</c:when>
												<c:when test="${var.process_type == 2}">充值</c:when>
												<c:when test="${var.process_type == 3}">购彩</c:when>
												<c:when test="${var.process_type == 5}">红包</c:when>
												<c:when test="${var.process_type == 6}">账户回滚</c:when>
												<c:when test="${var.process_type == 7}">购券</c:when>
												<c:when test="${var.process_type == 8}">退款</c:when>
											</c:choose>
											</td>
											<td class='center'>
											<c:choose>
												<c:when test="${var.process_type == 4}">提现</c:when>
												
												<c:when test="${var.process_type == 9}">输入错误</c:when>
											</c:choose>
											</td>
											<td class='center'>${DateUtil.toSDFTime(var.add_time*1000)}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>
											<c:choose>
												<c:when test="${var.status == 0}">未完成</c:when>
												<c:when test="${var.status == 1}">成功</c:when>
												<c:when test="${var.status == 2}">失败</c:when>
											</c:choose>
											</td>
											<td class='center'>${var.admin_user}</td>
										</tr>
									</c:forEach>	
									</c:if>
									
									<!-- 
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
									 -->
							</tbody>
						</table>
						
						<div class="page-header position-relative">
							<table style="width: 100%;">

								<tr>
									<td style="vertical-align: top;"><div class="pagination"
											style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
					   </form>			
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						</div>
					</div>
					
				</div>
			</div>
		</div>

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
	<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(status){
			if(status==0){
				$("#start_add_time").val("");
				$("#end_add_time").val("");
				$("#process_type").val("");
			}
			top.jzts();
			$("#Form").submit();
		}
		
		$(function() {
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
									url: '<%=basePath%>superwhitelist/deleteAll.do?tm='+new Date().getTime(),
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
				var user_id =  $("input:hidden[name='user_id']").val();;
				var store_id =  $("input:hidden[name='store_id']").val();;
				var start_add_time = $("#start_add_time").val();
				var end_add_time = $("#end_add_time").val();
				var process_type = $("#process_type").val();
				var url = '<%=basePath%>superwhitelist/excelAccount.do?tm=' + new Date().getTime() 
					+ "&user_id=" + user_id 
					+ "&store_id=" + store_id		
					+ "&start_add_time=" + start_add_time
					+ "&end_add_time=" + end_add_time
					+ "&process_type=" + process_type
					;
				
// 				alert("url=" + url);
				
				window.location.href= url;
			}	
	</script>
</body>
</html>