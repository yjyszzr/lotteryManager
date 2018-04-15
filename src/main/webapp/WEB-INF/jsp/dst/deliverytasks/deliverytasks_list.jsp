﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="com.fh.util.DateUtil"%>
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
						<form action="deliverytasks/list.do" method="post" name="Form" id="Form">
						<input type="hidden" name="operType" value="${pd.operType}">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入配送任务编号" class="nav-search-input" id="nav-search-input" autocomplete="off" name="delivery_tasks_code" value="${pd.delivery_tasks_code }" placeholder="这里输入配送任务编号"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="delivery_org_id" id="delivery_org_id" data-placeholder="选择配送组织" style="vertical-align:top;width: 120px;">
								 	
								 	<c:forEach items="${deliveryOrgs}" var="org" varStatus="vs">
								 			<option value="${org.delivery_org_id}" ${org.delivery_org_id==pd.delivery_org_id?"selected":""}>${org.delivery_org_name}</option>
								 	</c:forEach>
									</select>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="work_day" id="work_day"  value="${pd.work_day}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="工作日" title="工作日"/></td>
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
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
									<!-- <th class="center">ID</th> -->
									<th class="center">配送编码</th>
									<!-- <th class="center">配送组织ID</th> -->
									<th class="center">工作日</th>
									<!-- <th class="center">车辆工作ID</th> -->
									<th class="center">配送车辆</th>
									<th class="center">车辆联系人</th>
									<th class="center">车辆电话</th>
									<th class="center">配送组织</th> 
									<!-- <th class="center">userid</th> -->
									<th class="center">配送员</th>
									<th class="center">配送员电话</th>
									<th class="center">类型</th>
									<th class="center">状态</th>
									<th class="center">创建人</th>
									<th class="center">创建时间</th>
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.delivery_tasks_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<%-- <td class='center'>${var.delivery_tasks_id}</td> --%>
											<td class='center'>${var.delivery_tasks_code}</td>
											<%-- <td class='center'>${var.delivery_org_id}</td> --%>
											<td class='center'>${var.work_day}</td>
											<%-- <td class='center'>${var.delivery_vehicle_work_id}</td> --%>
											<td class='center'>${var.vehicle_number}</td>
											<td class='center'>${var.vehicle_contact}</td>
											<td class='center'>${var.vehicle_tel}</td>
											<td class='center'>${var.delivery_org_name}</td>
											<%-- <td class='center'>${var.user_id}</td> --%>
											<td class='center'>${var.user_name}</td>
											<td class='center'>${var.user_tel}</td>
											<td class='center'>${var.type==0?"自营":"第三方"}</td>
											<td class='center'>${var.status==0?"未上岗":(var.status==1?"配送中":"已完成")}</td>
											<td class='center'>${var.createby}</td>
											<td class='center'>${DateUtil.toSdfTimeStr(var.create_time)}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
												<c:if test="${pd.operType==0 }">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" ${ var.status!=0?"disabled":"" } title="编辑" onclick="edit('${var.delivery_tasks_id}');">
														<i class="ace-icon fa bigger-120" title="编辑">编辑</i>
													</a>
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="订单" onclick="toOrderList('${var.delivery_tasks_code}','${var.delivery_org_id}','${var.status}');">
														<i class="ace-icon fa bigger-120" title="订单">订单</i>
													</a>
													</c:if>
													<a class="btn btn-xs btn-success" title=提交  ${ var.status!=0?"disabled":"" } onclick="updateStaus('${var.delivery_tasks_id}','1','${var.delivery_tasks_code }');">
														<i class="ace-icon fa bigger-120" title="提交">提交</i>
													</a>
													<a class="btn btn-xs btn-success" title="完成"  ${ var.status!=1?"disabled":"" } onclick="updateStaus('${var.delivery_tasks_id}','2','${var.delivery_tasks_code }');">
														<i class="ace-icon fa bigger-120" title="完成">完成</i>
													</a>
													</c:if>
													
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger"  ${ var.status!=0?"disabled":"" } onclick="del('${var.delivery_tasks_id}');">
														<i class="ace-icon fa bigger-120" title="删除">删除</i>
													</a>
													</c:if>
												</c:if>
												<c:if test="${pd.operType==1 }"><%-- 选则任务 --%>
												
												<a class="btn btn-xs btn-success" title="选择任务" onclick="selectTask('${var.delivery_tasks_code}');">
														<i class="ace-icon fa bigger-120" title="选择任务">关联</i>
													</a>
												</c:if>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.delivery_tasks_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.delivery_tasks_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
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
								<c:if test="${pd.operType==0 || pd.operType==1 }"><%--常规列表 --%>
									<c:if test="${QX.add == 1 }">
										<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									</c:if>
									</c:if>
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
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>deliverytasks/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 595;
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
					var url = "<%=basePath%>deliverytasks/delete.do?delivery_tasks_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>deliverytasks/goEdit.do?delivery_tasks_id='+Id;
			 diag.Width = 450;
			 diag.Height = 595;
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
								url: '<%=basePath%>deliverytasks/deleteAll.do?tm='+new Date().getTime(),
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
		//修改
		function toOrderList(delivery_tasks_code,delivery_org_id,status){
			
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="配送单["+delivery_tasks_code+"]的订单";
		     diag.URL = '<%=basePath%>/pgtorder/list.do?operType=1&delivery_tasks_code='+delivery_tasks_code+"&delivery_org_id="+delivery_org_id+"&status="+status;
 			 diag.Width = $(window).width();
			 diag.Height = $(window).height();
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
				diag.close();
			 };
			 diag.show();
		}
		function selectTask(delivery_tasks_code){
			top.closeSelectTaskListDiag(delivery_tasks_code);
			//top.frames[top.frames.length-2].closeSelectTaskListDiag();
		}
		
		
		function updateStaus(Id,status,delivery_tasks_code){
			var promptMess = "";
			var isCommit = "";//如果是提交，后台需判断是否有明细
			
			if(status =="1"){
				promptMess = "确定要提交该此配送单吗？";
				isCommit = "1";
			}else 	if(status =="2"){
				promptMess = "确定要完成此配送单吗？";
			}
			
			bootbox.confirm(promptMess, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>deliverytasks/upState.do?delivery_tasks_id="+Id+"&tm="+new Date().getTime()+"&status="+status+"&delivery_tasks_code="+delivery_tasks_code;
				
					$.get(url,function(data){
						//alert(data);
						top.hangge();
						if("2"==data){
							bootbox.alert("请先在任务中添加订单再提交",function(){
							});
						}
						else if("1"!=data){
							bootbox.alert("更新状态失败",function(){
							});
						}else{
							tosearch();
						}
						
						
						
					});
				}
			});
		}
	</script>


</body>
</html>