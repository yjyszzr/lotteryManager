<%@page import="com.fh.util.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- <script src="http://code.jquery.com/jquery-1.4.1.js"></script> -->
<!-- 日期框 -->
<script src="static/ace/js/My97DatePicker/WdatePicker.js"></script>
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
							
						<!-- 检索  -->
						<form action="customer/total.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;border-collapse:separate; border-spacing:10px;">
							<tr style="margin:2px ">
								<td>
									<div class="nav-search">
										<span class="input-icon" style="width:80px;text-align:right;">
											手机号:
										</span>
										<span class="input-icon">
											<input type="text" placeholder="手机号" style="width:154px;" class="nav-search-input" id="mobile" autocomplete="off" name="mobile" value="${pd.mobile}"   onkeyup="value=value.replace(/[^\d]/g,'')"  />
										</span>
										<span class="input-icon" style="width:80px;text-align:right;">
											销售员:
										</span>
										<span class="input-icon">
											<input type="text" placeholder="销售员" style="width:154px;" class="nav-search-input" id="last_add_seller_name" autocomplete="off" name="last_add_seller_name" value="${pd.last_add_seller_name}"  />
										</span>
									</div>
								</td>
								<td>
									<div class="nav-search">
										<span class="input-icon" style="width:80px;text-align:right;">
											用户类型:
										</span>
										<select  name="user_state" id="user_state" data-placeholder="请选择" value="${pd.user_state}" style="width:154px;border-radius:5px !important"  >
											<option value="" selected="selected">全部</option>
											<option value="1" <c:if test="${pd.user_state == 1}">selected</c:if>>新用户</option>
											<option value="2" <c:if test="${pd.user_state == 2}">selected</c:if>>老用户</option>
										</select>
								  	</div>
								</td>	
							</tr>
							<tr style="margin:2px ">
								<td>
									<span class="input-icon" style="width:80px;text-align:right;">
										录入时间:
									</span>
									<span>
										<input name="start_last_add_time" id="start_last_add_time"  value="${pd.start_last_add_time }" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="开始时间" title="开始时间"/>
										<input name="end_last_add_time" id="end_last_add_time"  value="${pd.end_last_add_time }" type="text"  onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="readonly" style="width:74px;border-radius:5px !important" placeholder="结束时间" title="结束时间"/>
									</span>
								</td>	
									
								<td>
									<div class="nav-search">
										<span class="input-icon" style="width:80px;text-align:right;">
											购彩状态:
										</span>
										<select  name="pay_state" id="pay_state" data-placeholder="请选择" value="${pd.pay_state}" style="width:154px;border-radius:5px !important"  >
											<option value="" selected="selected">全部</option>
											<option value="1" <c:if test="${pd.pay_state == 1}">selected</c:if>>已购彩</option>
											<option value="0" <c:if test="${pd.pay_state == 0}">selected</c:if>>未购彩</option>
										</select>
								  	</div>
								</td>
							</tr>
							<tr style="margin:2px ">		  	
								<td >
									<span class="input-icon" style="width:80px;"> </span>
									<span>
											<a class="btn btn-light btn-xs blue" onclick="tosearch(1);"  title="搜索"  style="border-radius:5px;color:blue !important; width:100px">搜索</a>
									</span>
									<span class="input-icon" style="width:44px;"> </span>
									<span>
											<a class="btn btn-light btn-xs blue" onclick="tosearch(0);"  title="清空"  style="border-radius:5px;color:blue !important; width:100px">清空</a>
									</span>
									<span class="input-icon" style="width:44px;"> </span>
									<span>
											<a class="btn btn-light btn-xs blue" onclick="toExcel();"  title="导出到EXCEL"  style="border-radius:5px;color:blue !important; width:100px">全部导出</a>
									</span>
								</td>
								<td >
									<span class="input-icon" style="width:80px;"> </span>
									<span>
											<a class="btn btn-light btn-xs blue" onclick="uploadExcel();"  title="EXCEL批量导入"  style="border-radius:5px;color:blue !important; width:100px">EXCEL批量导入</a>
									</span>
									<span class="input-icon" style="width:37px;"> </span>
									<span>
											<a class="btn btn-light btn-xs blue" onclick="toAssign('确定要指派选中的数据吗?');"  title="一键指派"  style="border-radius:5px;color:blue !important; width:100px">一键指派</a>
									</span>
								</td>
														  	
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									 <th class="center" style="width:35px;">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">手机号</th>
									<th class="center">用户名</th>
									<th class="center">用户类型</th>
									<th class="center">用户来源</th>
									<th class="center">是否已购彩</th>
									<th class="center">所属销售员</th>
									<th class="center">录入时间</th>
									<th class="center">购彩时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											 <td class='center'>
												<label class="pos-rel">
<%-- 												<c:choose> --%>
<%-- 												<c:when test="${ empty var.last_add_seller_id}"> --%>
													 <input type='checkbox' name='ids' value="${var.id}" class="ace" />
<%-- 												</c:when> --%>
<%-- 												</c:choose> --%>
												
												<span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.mobile}</td>
											<td class='center'>${var.user_name}</td>
											<td class='center'>
												<c:if test="${var.user_state == 1}">
													新用户
												</c:if>
												<c:if test="${var.user_state == 2}">
													老用户
												</c:if>
											</td>
											<td class='center'>
											 	<c:if test="${var.user_source == 1}">
													 公司资源
												</c:if>
												<c:if test="${var.user_source == 2}">
													 微信群
												</c:if>
												<c:if test="${var.user_source == 3}">
													 QQ群
												</c:if>
												<c:if test="${var.user_source == 4}">
													 好友推荐
												</c:if>
												<c:if test="${var.user_source == 5}">
													 电话访问
												</c:if>
												<c:if test="${var.user_source == 6}">
													 其它
												</c:if>
												<c:if test="${var.user_source == 7}">
													维护资源
												</c:if>
											</td>
											<td class='center'>
												<c:if test="${var.pay_state == 1}">
													已购彩
												</c:if>
												<c:if test="${var.pay_state == 0}">
													 未购彩
												</c:if>
												 
											</td>
											<td class='center'>
												
												
												${var.last_add_seller_name}
											</td>
											<td class='center'>${DateUtil.toSDFTime(var.last_add_time*1000)}</td>
											<td class='center'>
													<c:choose>
														<c:when test="${null != var.first_pay_time && var.first_pay_time != ''}">
														
															${DateUtil.toSDFTime(var.first_pay_time*1000)}
														
														</c:when>
													</c:choose>
											</td>
											<td class="center">
												<div class="hidden-sm hidden-xs btn-group">
													 <a class="btn btn-xs btn-success" title="查看"   onclick="see('id=' + '${var.id}' + '');"	  >
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="查看">查看</i>
													</a>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<li>
															</li>
															<li>
															</li>
															<li>
															</li>
														</ul>
													</div>
												</div>
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
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
<%-- 									<c:if test="${QX.add == 1 }"> --%>
<!-- 									<a class="btn btn-mini btn-success" onclick="add();">新增</a> -->
<%-- 									</c:if> --%>
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
		
	
		//打开上传excel页面
		function uploadExcel(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="EXCEL导入到数据库";
			 diag.URL = '<%=basePath%>customer/goUploadExcel.do';
			 diag.Width = 300;
			 diag.Height = 150;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}			
	
		//打开指派页面
		function toAssign(msg){
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
						 top.jzts();
						 var diag = new top.Dialog();
						 diag.Drag=true;
						 diag.Title ="指派页面";
						 diag.URL = '<%=basePath%>customer/toAssign.do?ids='+str;
						 diag.Width = 300;
						 diag.Height = 100;
						 diag.CancelEvent = function(){ //关闭事件
							 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
								 if('${page.currentPage}' == '0'){
									 top.jzts();
									 setTimeout("self.location.reload()",100);
								 }else{
									 nextPage(${page.currentPage});
								 }
							}
							diag.close();
						 };
						 diag.show();
					}			
			
			
			
		}
		
		
		function tosearch(status){
			if(status==0){
				$("#last_add_seller_name").val("");
				$("#user_state").val("");
				$("#start_last_add_time").val("");
				$("#end_last_add_time").val("");
				$("#pay_state").val("");
			}
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
		
		
		
		function see(str){
			 
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="";
			 diag.URL = '<%=basePath%>customer/seeTotal.do?' + str;
			 /**
			 diag.Width = 450;
			 diag.Height = 355;
			 **/
			 diag.Width = 950;
			 diag.Height = 700;
			 
			 diag.Modal = true;				//有无遮罩窗口
			 diag.ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}		
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>customer/goAdd.do';
			 diag.Width = 450;
			 //diag.Height = 355;
			 diag.Height = 220;
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
					var url = "<%=basePath%>customer/delete.do?_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		function reset(_var) {
  
			var str = "置回后无法在列表中查询到该用户的任何信息确定要置回吗？";
			if (window.confirm(str)) {
				
				var url = "<%=basePath%>customer/reset.do?" + _var +"&tm="+new Date().getTime();  
				$.get(url,function(data){
					$("#Form").submit();
				    return true;
				});

				
			 }else{
			    //alert("取消");
			    return false;
			}
			 
		}
		
		//修改
		function edit(str){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>customer/goEdit.do?' + str;
			 diag.Width = 450;
			 diag.Height = 250;
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
								url: '<%=basePath%>customer/deleteAll.do?tm='+new Date().getTime(),
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
			
			var last_add_seller_name = $("#last_add_seller_name").val();
			var user_state = $("#user_state").val();
			var start_last_add_time = $("#start_last_add_time").val();
			var end_last_add_time = $("#end_last_add_time").val();
			var pay_state = $("#pay_state").val();
			
			var url = '<%=basePath%>customer/excel.do?t=' + new Date().getTime() 
			+ "&last_add_seller_name=" + last_add_seller_name 
			+ "&user_state=" + user_state		
			+ "&start_last_add_time=" + start_last_add_time
			+ "&end_last_add_time=" + end_last_add_time
			+ "&pay_state=" + pay_state
			;
		
// 			alert("url=" + url);
		
			window.location.href= url;
		
		}
	</script>


</body>
</html>