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
<!--  质检查询列表 -->
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
						<form action="inboundcheckdetail/listForQuery.do" method="post" name="Form" id="Form">
						<input type="hidden" name="inbound_notice_stock_batch_id" value="${pd.inbound_notice_stock_batch_id }" />
						<input type="hidden" name="status" value="${pd.status }" />
						<input type="hidden" name="quantity" value="${pd.quantity }" />
						<table class="pgt-query-form-table">
							<tr>
							<%-- <td class="pgt-query-form-td">
								业务单号：<input type="text" name="purchase_code" id="purchase_code"  value="${pd.purchase_code}"  placeholder="业务编号"  title="业务编号"/>
							</td> --%>
							<td class="pgt-query-form-td">
								入库通知单号：<input type="text" name="inbound_notice_code" id="inbound_notice_code"  value="${pd.inbound_notice_code}"  placeholder="入库通知单编号"  title="入库通知单编号"/>&nbsp;
							</td>
							<td class="pgt-query-form-td">
							状态：
								 	<select name="status" id="status" data-placeholder="状态" value="${pd.status }" style="width: 120px;">
								 	<option>
									<option value="0" ${0==pd.status and pd.status!="" ?"selected":""}>未提交</option>
									<option value="1" ${1==pd.status?"selected":"" }>已提交</option>
									<option value="2"  ${2==pd.status?"selected":"" }>已入库</option>
								  	</select>
								</td>
							    
							<td class="pgt-query-form-td">	
							质检时间：
								<input class="span10 date-picker" name="create_time_begin" id="create_time_begin" value="${pd.create_time_begin}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开始时间	" title="开始时间"/>
							</td>
								
						<td class="pgt-query-form-td">	
								<input class="span10 date-picker" name="create_time_end_real" id="create_time_end_real" value="${pd.create_time_end_real}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束时间" title="结束时间"/>
						</td>
								<c:if test="${QX.cha == 1 }">
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="查询">查询</a></td>
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="doResetForm();"  title="清空">清空</a></td>
								</c:if>
							</tr>
							
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<!-- <th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th> -->
									<th class="center" style="width:50px;">序号</th>
									<!-- <th class="center">id</th> -->
									<th class="center">入库通知单</th>
									<!-- <th class="center">入库通知单详情ID</th> -->
								<!-- 	<th class="center">业务编码</th>
									<th class="center">业务详情ID</th> -->
									<!-- <th class="center">入库批次ID</th> -->
									<th class="center">入库批次</th>
									<!-- <th class="center">sku_id</th> -->
									<th class="center">商品名称</th>
									<th class="center">商品编码</th>
									<th class="center">质检总数</th>
									<th class="center">良品数</th>
									<th class="center">不良品数</th>
									<th class="center">报损数</th>
									<th class="center">处理类型</th>
									<th class="center">规格</th>
									<th class="center">单位名称</th>
									<th class="center">状态</th>
									<th class="center">质检时间</th>
									
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
											<%-- <td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.pgt_inbound_check_detail_id}" class="ace" /><span class="lbl"></span></label>
											</td> --%>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<%-- <td class='center'>${var.pgt_inbound_check_detail_id}</td> --%>
											<td class='center'>${var.inbound_notice_code}</td>
										<%-- 	<td class='center'>${var.notice_detail_id}</td>
											<td class='center'>${var.purchase_code}</td>
											<td class='center'>${var.purchase_detail_id}</td>
											<td class='center'>${var.inbound_notice_stock_batch_id}</td> --%>
											<td class='center'>${var.batch_code}</td>
											<%-- <td class='center'>${var.sku_id}</td> --%>
											<td class='center'>${var.sku_name}</td>
											<td class='center'>${var.sku_encode}</td>
											<td class='center'>${var.total_quantity}</td>
											<td class='center'>${var.good_quantity}</td>
											<td class='center'>${var.bad_quantity}</td>
											<td class='center'>${var.scrap_quantity}</td>
											<td class='center'>
												<c:if test="${var.bad_deal_type==0}">
													退回供应商
												</c:if>
												<c:if test="${var.bad_deal_type==1}">
													入不良品库
												</c:if>
											</td>
											<td class='center'>${var.spec}</td>
											<td class='center'>${var.unit_name}</td>
											<td class='center'>
<%-- 											${var.status==0?"未提交":"已提交"}
 --%>												<c:if test="${var.status==0}">
												未提交
												</c:if>
												<c:if test="${var.status==1}">
												已提交
												</c:if>
												<c:if test="${var.status==2}">
												已入库
												</c:if>
											
											</td>
											<td class='center'><fmt:formatDate value="${var.create_time}"  pattern="yyyy-MM-dd HH:mm:ss"  /></td>
											<td class="center">
												
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<a class="btn btn-xs btn-success"  title="详情"   onclick="goDetail('${var.pgt_inbound_check_detail_id}');">
														<i class="ace-icon fa bigger-120" title="详情">详情</i>
													</a>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													
												<%--	 <a class="btn btn-xs btn-success" title="提交" ${ var.status>0?"disabled='disabled'":"" } onclick="submitCheck('${var.pgt_inbound_check_detail_id}',${var.total_quantity});">
														<i class="ace-icon fa bigger-120" title="提交">提交</i>
													</a>
													</c:if>
													
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" ${ var.status==0?"":"disabled='disabled'" } onclick="del('${var.pgt_inbound_check_detail_id}');">
														<i class="ace-icon fa bigger-120" title="删除">删除</i>
													</a> --%>
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
																<a style="cursor:pointer;" onclick="edit('${var.pgt_inbound_check_detail_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.pgt_inbound_check_detail_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															
														</ul>
													</div>
												</div>
											</td>
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
								<c:if test="${pd.status==1 }"><!-- 完成状态不可编辑 -->
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add('${pd.inbound_notice_stock_batch_id}');">新增</a>
									</c:if>
									<%-- <c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if> --%>
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
		
		
		//修改
		function goDetail(pgt_inbound_check_detail_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="质检详情";
			 diag.URL = '<%=basePath%>inboundcheckdetail/goDetail.do?pgt_inbound_check_detail_id='+pgt_inbound_check_detail_id;
			 diag.Width = 450;
			 diag.Height = 655;
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
		
		
	</script>


</body>
</html>