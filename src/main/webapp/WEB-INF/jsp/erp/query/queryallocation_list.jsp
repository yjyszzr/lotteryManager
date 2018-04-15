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
						<form action="queryallocation/list.do" method="post" name="Form" id="Form">
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
									调拨单号：<input type="text" placeholder="调拨单号" id="nav-search-input" autocomplete="off" name="search_allocation_code" value="${pd.search_allocation_code }" placeholder="这里输入调拨编号"/>
								</td>
								<td class="pgt-query-form-td">
									调出仓库：
								 	<select name="stores_out" id="stores_out" data-placeholder="调出仓库" style="width: 150px;">
										<option value="">请选择调出仓库</option>
										<c:if test="${not empty storeList_out}">
											<c:forEach items="${storeList_out}" var="var" varStatus="vs">
									         	<c:choose>
													<c:when test="${pd.store_sn_out==var.store_sn}">
													    <option selected="selected" value="${var.store_sn}">${var.store_name}</option>
													</c:when>
													<c:otherwise>
														<option value="${var.store_sn}">${var.store_name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
								  	</select>
							    </td>
								<td class="pgt-query-form-td">
									调入仓库：
								 	<select name="stores_in" id="stores_in" data-placeholder="调入仓库" style="width: 150px;">
								 		<option value="">请选择调入仓库</option>
										<c:if test="${not empty storeList_in}">
											<c:forEach items="${storeList_in}" var="var" varStatus="vs">
									         	<c:choose>
													<c:when test="${pd.store_sn_in==var.store_sn}">
													    <option selected="selected" value="${var.store_sn}">${var.store_name}</option>
													</c:when>
													<c:otherwise>
														<option value="${var.store_sn}">${var.store_name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
								  	</select>
							    </td>
								<!-- <td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td> -->
								<td class="pgt-query-form-td">
									状态：
								 	<select name="status" id="id" data-placeholder="请选择" style="width: 120px;">
									<option value="">请选择状态</option>
									<option value="0">待提交</option>
									<option value="1">待审核</option>
									<option value="2">通过</option>
									<option value="3">驳回</option>
									<option value="4">已发货</option>
									<option value="9">已完成</option>
								  	</select>
								</td>
								<c:if test="${QX.cha == 1 }">
																<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="查询">查询</a></td>
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="doResetForm();"  title="清空">清空</a></td>
								</c:if>
								<%-- <c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if> --%>
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
									<th class="center">调拨编号</th>
									<th class="center">调出仓库编号</th>
									<th class="center">调出仓库名称</th>
									<th class="center">调入仓库编号</th>
									<th class="center">调入仓库名称</th>
									<th class="center">预计发货</th>
									<th class="center">实际发货</th>
									<th class="center">预计到货</th>
									<th class="center">实际到货</th>
									<th class="center">状态</th>
									<th class="center">提交人</th>
									<th class="center">提交时间</th>
									<th class="center">审批人</th>
									<th class="center">审批时间</th>
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.allocation_code}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${page.currentResult+vs.index+1}</td>
											<td class='center'>${var.allocation_code}</td>
											<td class='center'>${var.from_store_sn}</td>
											<td class='center'>${var.from_store_name}</td>
											<td class='center'>${var.to_store_sn}</td>
											<td class='center'>${var.to_store_name}</td>
											<td class='center'>${var.pre_send_time}</td>
											<td class='center'>${var.send_time}</td>
											<td class='center'>${var.pre_arrival_time}</td>
											<td class='center'>${var.arrival_time}</td>
											<td class='center'>
											    <c:if test="${var.status == 0}">待提交 </c:if>
											    <c:if test="${var.status == 1}">待审核 </c:if>
											    <c:if test="${var.status == 2}">通过 </c:if>
											    <c:if test="${var.status == 3}">驳回 </c:if>
											    <c:if test="${var.status == 4}">已发货 </c:if>
											    <c:if test="${var.status == 9}">已完成 </c:if>
											</td>
											<td class='center'>${var.commitby}</td>
											<td class='center'><fmt:formatDate value="${var.commit_time}" pattern="yyyy-MM-dd"/></td>
											<td class='center'>${var.auditby}</td>
											<td class='center'><fmt:formatDate value="${var.audit_time}" pattern="yyyy-MM-dd"/></td>
											
											<td class="center">
												<a class="btn btn-xs btn-success" onclick="viewDetail('${var.allocation_code}','${var.status}');">明细详情</a>
												<a class="btn btn-xs btn-success" onclick="bound('${var.allocation_code}');">仓库记录</a>
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
								    <c:if test="${pd.flag == 'list'}">
										<c:if test="${QX.add == 1 }">
											<a class="btn btn-mini btn-success" onclick="add();">新增</a>
										</c:if>
									</c:if>
									<%-- <c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if> --%>
								</td>
								<td style="vertical-align:top;"><a class="btn btn-mini btn-success" onclick="printallcoation();">打印调拨单</a></td>
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
		
		//打印调拨单
		function printallcoation(){
			
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				 if(document.getElementsByName('ids')[i].checked)
					 {
					 if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					 }
			}
			if(str=='')
				{
				   alert(" 还未选择任何内容 ");
				    return;
				}
			
			var url = "<%=basePath%>queryallocation/printallocation.do?sn="+str;
			window.open(url, "", 'left=250,top=150,width=1000,height=700,toolbar=no,menubar=no,status=no,scrollbars=yes,resizable=yes');
		}
		
		function viewDetail(sn,status){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查看明细";
			 diag.URL = '<%=basePath%>queryallocation/viewlist.do?allocation_code='+sn+'&status='+status;
			 diag.Width = 1250;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		function outbound(sn){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="出库记录";
			 diag.URL = '<%=basePath%>queryallocation/outbounddetail.do?purchase_code='+sn;
			 diag.Width = 1250;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		function inbound(sn){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="入库记录";
			 diag.URL = '<%=basePath%>queryallocation/inbounddetail.do?purchase_code='+sn;
			 diag.Width = 1250;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		function bound(sn){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="出入库记录";
			 diag.URL = '<%=basePath%>queryallocation/bound.do?querytype=allocation&purchase_code='+sn;
			 diag.Width = 1250;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
	</script>
</body>
</html>