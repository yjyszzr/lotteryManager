<%@page import="com.fh.common.OrderShippingStatusEnum"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="com.fh.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
							<form action="pgtorder/list.do" method="post" name="Form"
								id="Form">
								<input type="hidden" name="operType" value="${pd.operType}">
								<c:if test="${pd.operType==1}">
									<input type="hidden" name="delivery_tasks_code"
									value="${pd.delivery_tasks_code}">
									
								</c:if>
								<table
									style="margin-top:5px;display:${pd.operType==1||pd.operType==2?'none':''}">
									<%--0:正常订单列表，1:某配送任务的订单 2:为某配送任务选择订单 --%>
									<tr>
										<td>
											<div class="nav-search">
												<span class="input-icon"> <input type="text"
													placeholder="这里输入订单编号" class="nav-search-input"
													id="nav-search-input" autocomplete="off" name="order_sn"
													value="${pd.order_sn }" placeholder="这里输入订单编号" /> <i
													class="ace-icon fa fa-search nav-search-icon"></i>
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
										<%-- <td style="padding-left: 2px;"><input
											class="span10 date-picker" name="lastStart" id="lastStart"
											value="" type="text" data-date-format="yyyy-mm-dd"
											readonly="readonly" style="width: 88px;" placeholder="开始日期"
											title="开始日期" /></td>
										<td style="padding-left: 2px;"><input
											class="span10 date-picker" name="lastEnd" name="lastEnd"
											value="" type="text" data-date-format="yyyy-mm-dd"
											readonly="readonly" style="width: 88px;" placeholder="结束日期"
											title="结束日期" /></td>
										<td style="vertical-align: top; padding-left: 2px;"><select
											class="chosen-select form-control" name="name" id="id"
											data-placeholder="请选择"
											style="vertical-align: top; width: 120px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="">1</option>
												<option value="">2</option>
										</select></td>
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: top; padding-left: 2px"><a
												class="btn btn-light btn-xs" onclick="tosearch();"
												title="检索"><i id="nav-search-icon"
													class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
										</c:if>
										<c:if test="${QX.toExcel == 1 }">
											<td style="vertical-align: top; padding-left: 2px;"><a
												class="btn btn-light btn-xs" onclick="toExcel();"
												title="导出到EXCEL"><i id="nav-search-icon"
													class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td>
										</c:if> --%>
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: top; padding-left: 2px"><a
												class="btn btn-light btn-xs" onclick="tosearch();"
												title="检索"><i id="nav-search-icon"
													class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
										</c:if>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center"
												style="width:35px;display:${pd.operType==1?'none':''}">
												<label class="pos-rel"><input type="checkbox"
													class="ace" id="zcheckbox" onchange='uncheckBounded(this);' /><span
													class="lbl"></span></label>
											</th>
											<th class="center" style="width: 50px;">序号</th>
											<!-- <th class="center">备注1</th>
									<th class="center">备注2</th> -->
											<th class="center">订单编号</th>
											<!-- <th class="center">用户ID</th> -->
											<!-- <th class="center">状态</th> -->
											<!-- <th class="center">店铺ID</th> -->
											<!-- <th class="center">站点ID</th> -->
											<!-- <th class="center">备注8</th> -->
											<th class="center">收货人</th>
											<th class="center">电话</th>
											<th class="center">地址</th>
											<!-- <th class="center">添加时间</th> -->
											<th class="center">物流状态</th>
											<th class="center">配送任务</th>
											<c:if test="${pd.operType==1 ||pd.operType==0 }">
												<th class="center">操作</th>
											</c:if>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
															<td class='center'
																style="display:${pd.operType==1?'none':''}"><label
																class="pos-rel"><input type='checkbox'
																	name='ids' value="${var.pgt_order_id}" class="ace"
																	${ (var.delivery_tasks_code==null|| var.delivery_tasks_code=='')?'':'disabled=true'} /><span
																	class="lbl"></span></label></td>
															<td class='center' style="width: 30px;">${vs.index+1}</td>
															<%-- 	<td class='center'>${var.pgt_order_id}</td>
											<td class='center'>${var.order_id}</td> --%>
															<td class='center'>${var.order_sn}</td>
															<%-- <td class='center'>${var.user_id}</td> --%>
															<%-- <td class='center'>${var.order_status}</td> --%>
															<%-- <td class='center'>${var.shop_id}</td> --%>
															<%-- <td class='center'>${var.site_id}</td> --%>
														<%-- 	<td class='center'>${var.store_id}</td> --%>
															<td class='center'>${var.consignee}</td>
															<td class='center'>${var.tel}</td>
															<td class='center'>${var.address}</td>
<%-- 															<td class='center'>${DateUtil.toDateStr(var.add_time*1000)}</td>
 --%>															<td class='center'>${OrderShippingStatusEnum.getName(var.shipping_status)}</td>
															<td class='center'>${var.delivery_tasks_code}<input
																type='hidden' name='delivery_tasks_codes'
																value='${var.delivery_tasks_code}'></td>
															<c:if test="${pd.operType==1 || pd.operType==0}">
																<td class="center"><c:if
																		test="${QX.edit != 1 && QX.del != 1 }">
																		<span
																			class="label label-large label-grey arrowed-in-right arrowed-in"><i
																			class="ace-icon fa fa-lock" title="无权限"></i></span>
																	</c:if>
																	<div class="hidden-sm hidden-xs btn-group">
																		<c:if test="${pd.operType==0}">
																			<a class="btn btn-xs btn-success" title="确认到货"  ${var.shipping_status==OrderShippingStatusEnum.TYPE3_TRANSPORT.code?"":"disabled" } onclick="updateState('${var.pgt_order_id}')">
																				确认收件
																			</a>
																			<a class="btn btn-xs btn-success" title="第三方派件"  ${var.shipping_status==OrderShippingStatusEnum.TYPE4_INDELIVERY.code?"":"disabled" } onclick="goLogisticsEdit('${var.pgt_order_id}','${shipping_status}')">
																				第三方派件
																			</a>
																			
																		</c:if>
																		<c:if test="${pd.operType==1 }">
																			<%--operType： 1-任务已关联的订单列表页面 --%>
																			<a class="btn btn-xs btn-danger" ${pd.status==0?"":"disabled" }
																				onclick="delTaskOrder('${var.pgt_order_id}');">
																				<i class="ace-icon fa bigger-120" title="删除">删除</i>
																			</a>
																		</c:if>
																	</div>
																	<div class="hidden-md hidden-lg">
																		<div class="inline pos-rel">
																			<button
																				class="btn btn-minier btn-primary dropdown-toggle"
																				data-toggle="dropdown" data-position="auto">
																				<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																			</button>

																			<ul
																				class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																				<c:if test="${QX.edit == 1 }">
																					<li><a style="cursor: pointer;"
																						onclick="edit('${var.pgt_order_id}');"
																						class="tooltip-success" data-rel="tooltip"
																						title="修改"> <span class="green"> <i
																								class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																						</span>
																					</a></li>
																				</c:if>
																				<c:if test="${QX.del == 1 }">
																					<li><a style="cursor: pointer;"
																						onclick="del('${var.pgt_order_id}');"
																						class="tooltip-error" data-rel="tooltip"
																						title="删除"> <span class="red"> <i
																								class="ace-icon fa fa-trash-o bigger-120"></i>
																						</span>
																					</a></li>
																				</c:if>
																			</ul>
																		</div>
																	</div></td>
															</c:if>
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
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: top;"><c:if
													test="${pd.operType==1  and pd.status==0}">
													<%--当前页面是某任务的订单列表，弹出订单选择页面 --%>
													<a class="btn btn-mini btn-success"
														onclick="toSelectOrderList();">新增</a>
												</c:if> <c:if test="${pd.operType==2 }">
													<%--当前页面是为某任务选择订单，选中的订单直接关联到配送任务 --%>
													<a class="btn btn-mini btn-success"
														onclick="selectOrdersToTask();">加入配送任务</a>
												</c:if> <c:if test="${pd.operType==0 }">
													<%--当前页为常规的订单列表页，弹出配送任务选择列表 --%>
													<!-- 先不启用<a class="btn btn-mini btn-success"
														onclick="toSelectTaskList();">关联配送任务</a> -->
												</c:if> <%-- 	<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if> --%></td>
											<td style="vertical-align: top;"><div class="pagination"
													style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
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
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
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
			 diag.URL = '<%=basePath%>pgtorder/goAdd.do';
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
					var url = "<%=basePath%>pgtorder/delete.do?pgt_order_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		//删除
		function delTaskOrder(pgt_order_id){
			bootbox.confirm("确定要把此订单从配送任务中移除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>pgtorder/delTaskOrder.do?delivery_tasks_code=${pd.delivery_tasks_code}&pgt_order_id="+pgt_order_id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>pgtorder/goEdit.do?pgt_order_id='+Id;
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
								url: '<%=basePath%>pgtorder/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>pgtorder/excel.do';
		}
		<%-- >>>>operType=0 functions，订单普通列表页面--%>
		//修改
		function toSelectTaskList(){
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
			  if(document.getElementsByName('ids')[i].checked){
			  	if(str=='') str += document.getElementsByName('ids')[i].value;
			  	else str += ',' + document.getElementsByName('ids')[i].value;
			  }
			}
			if(str==''){
				bootbox.alert('请选择要加入配送任务的订单');
				return ;
			}
			 top.jzts();
			 selectTaskListDiag = new top.Dialog();
			 selectTaskListDiag.Drag=true;
			 selectTaskListDiag.Title ="关联配送任务";
			 selectTaskListDiag.URL = '<%=basePath%>/deliverytasks/list.do?operType=1&delivery_org_id=${pd.delivery_org_id}';
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
			 top.closeSelectTaskListDiag =function(delivery_tasks_code){
				selectTaskListDiag.close();
				boundOrdersToTask(str,delivery_tasks_code);
					
				}
		}
	
		function updateState(  pgt_order_id){
			bootbox.confirm("确认已到货", function(result) {
				if(result){
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>pgtorder/updateState.do?tm='+ new Date().getTime(),
						data : {
							pgt_order_id : pgt_order_id,
							shipping_status : ${OrderShippingStatusEnum.TYPE4_INDELIVERY.code}
						},
						dataType : 'text',
						//beforeSend: validateData,
						cache : false,
						success : function(data) {
							tosearch();
							if (data != '1') {
								bootbox.alert("确认到货失败");
							}

						}
					});

				}
			}
		);
		}
		//修改
		function goLogisticsEdit(Id,shipping_status){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑1";
			 diag.URL = '<%=basePath%>pgtorder/goLogisticsEdit.do?pgt_order_id='+Id+'&shipping_status='+shipping_status;
			 diag.Width = 450;
			 diag.Height = 550;
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
		
		<%--<<<< operType=0 functions  end--%>
		
		<%-- >>>>operType=1 functions，任务订单明细页面--%>
		/**
		打开选择要加入任务的订单列表页
		*/
		function  toSelectOrderList(){
			 top.jzts();
			 selectOrderdiag = new top.Dialog();
			 selectOrderdiag.Drag=true;
			 selectOrderdiag.Title ="选择订单";
			 //选状态
		     selectOrderdiag.URL = '<%=basePath%>/pgtorder/list.do?operType=2&shipping_status=4&delivery_org_id=${pd.delivery_org_id}';
			 selectOrderdiag.Width = $(window).width();
			 selectOrderdiag.Height = $(window).height();
			 selectOrderdiag.Modal = true;				//有无遮罩窗口
			 selectOrderdiag. ShowMaxButton = true;	//最大化按钮
		     selectOrderdiag.ShowMinButton = true;		//最小化按钮 
			 selectOrderdiag.CancelEvent = function(){ //关闭事件
				/*  if(selectOrderdiag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
				selectOrderdiag.close();
			 };
			 selectOrderdiag.show();
			 top.closeSelectOrderDiag =function(orders){
				    boundOrdersToTask(orders,'${pd.delivery_tasks_code}');
					selectOrderdiag.close();
			}
		}
		
		<%--<<<< operType=1 functions  end--%>
		
		<%-- >>>>operType=2 functions,订单选择页面--%>
		/**
		将选中的订单添加到任务
		*/
		function selectOrdersToTask( ){
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
			  if(document.getElementsByName('ids')[i].checked){
			  	if(str=='') str += document.getElementsByName('ids')[i].value;
			  	else str += ',' + document.getElementsByName('ids')[i].value;
			  }
			}
			if(str==''){
				bootbox.alert('请选择要加入配送任务的订单');
				return ;
			}
			
			top.closeSelectOrderDiag(str)
			
		}
		
		<%-- >>>>operType=2 functions--%>
		
		
		<%--common--%>
		/**
			将订单添加到配送任务当中
		*/
		function  boundOrdersToTask(orders,delivery_tasks_code){
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>pgtorder/boundOrdersToTask.do?tm='+ new Date().getTime(),
				data : {
					orders : orders,
					delivery_tasks_code : delivery_tasks_code
				},
				dataType : 'text',
				//beforeSend: validateData,
				cache : false,
				success : function(data) {
					tosearch();
					if (data != '1') {
						bootbox.alert("加入配送任务失败");
					}

				}
			});
		}

		function uncheckBounded(box) {
			if (box.checked) {
				var ids = document.getElementsByName('ids');
				var delivery_tasks_codes = document
						.getElementsByName('delivery_tasks_codes');

				for (var i = 0; i < delivery_tasks_codes.length; i++) {
					if (delivery_tasks_codes[i].value) {
						ids[i].checked = false;
					}
				}
			}
		}
	</script>


</body>
</html>