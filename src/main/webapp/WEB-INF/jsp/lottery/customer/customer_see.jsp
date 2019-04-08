<%@page import="com.fh.util.DateUtil"%>
<%@page import="com.fh.util.StringUtil"%>
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
							<thead>
								<tr>&nbsp;</tr>
								<tr>&nbsp;</tr>
								<tr><strong>用户会员详情</strong></tr>
								 
							</thead>
							<tbody>
									<tr>
										<td>
											 手机号：${customer.mobile}
										</td>
										<td>
										&nbsp;
										</td>
										<td>
											姓名： ${customer.user_name}
										</td>
									</tr>
									<tr>		
										<td>
										 	用户类型： <c:if test="${customer.user_state == 1}">
													新用户
												</c:if>
												<c:if test="${customer.user_state == 2}">
													老用户
												</c:if>
										</td>	
										<td>
										&nbsp;
										</td>	 
										<td>
											用户来源： <c:if test="${customer.user_source == 1}">
													 公司资源
												</c:if>
												<c:if test="${customer.user_source == 2}">
													 微信群
												</c:if>
												<c:if test="${customer.user_source == 3}">
													 QQ群
												</c:if>
												<c:if test="${customer.user_source == 4}">
													 好友推荐
												</c:if>
												<c:if test="${customer.user_source == 5}">
													 电话访问
												</c:if>
												<c:if test="${customer.user_source == 6}">
													 其它
												</c:if>
												<c:if test="${customer.user_source == 7}">
													 维护资源
												</c:if>
										</td>
									</tr>
									</tbody>
								</table>
						<!-- 检索  -->
	 
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>&nbsp;</tr>
								<tr>&nbsp;</tr>
								<tr><strong>销售记录</strong></tr>
								<tr>
								    <!--  
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									-->
									<th class="center">序号</th>
									<th class="center">订单编号</th>
									<th class="center">投注金额</th>
									<th class="center">使用代金卷金额</th>
									<th class="center">投注时间</th>
									
								</tr>
							</thead>
							<tbody>
									<c:forEach items="${ordes}" var="var" varStatus="vs">
										<tr>
											<!-- 
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.user_id_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											 -->
											<td class='center'>${vs.index+1}</td>
											<td class='center'>
<%-- 												${var.order_sn} --%>
												${StringUtil.strReplace(var.order_sn, 8, 14, "XXXXXX")}
											</td>
											<td class='center'>${var.ticket_amount}</td>
											<td class='center'>${var.bonus}</td>
											
											<td class='center'>
												<c:choose>
													<c:when test="${empty var.add_time }"></c:when>
													<c:otherwise>${DateUtil.toSDFTime(var.add_time*1000)}</c:otherwise>
												</c:choose>
											</td>
				 						</tr>
									</c:forEach>	
									
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