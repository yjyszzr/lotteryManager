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
						<form action="inboundcheckdetail/listAll.do" method="post" name="Form" id="Form">
						<input type="hidden" name="inbound_notice_stock_batch_id" value="${pd.inbound_notice_stock_batch_id }" />
						<input type="hidden" name="status" value="${pd.status }" />
						<input type="hidden" name="quantity" value="${pd.quantity }" />
						<table style="margin-top:5px;">
							<tr>
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
									<!--<th class="center">入库通知单</th> -->
									<!-- <th class="center">入库通知单详情ID</th> -->
								<!-- 	<th class="center">业务编码</th>
									<th class="center">业务详情ID</th> -->
									<!-- <th class="center">入库批次ID</th> -->
									<th class="center">入库批次</th>
									<!-- <th class="center">sku_id</th> 
									<th class="center">物料编码</th>-->
									<th class="center">物料名称</th>
									<th class="center">质检总数</th>
									<th class="center">良品数</th>
									<th class="center">不良品数</th>
									<th class="center">报损数</th>
									<th class="center">处理类型</th>
									<th class="center">规格</th>
									<th class="center">单位</th>
									<th class="center">状态</th>
									<th class="center">质检时间</th>
									<c:if test="${pd.status==1 }"><!-- 完成状态不可编辑 -->
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
											<%-- <td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.pgt_inbound_check_detail_id}" class="ace" /><span class="lbl"></span></label>
											</td> --%>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<%-- <td class='center'>${var.pgt_inbound_check_detail_id}</td> --%>
											<%--<td class='center'>${var.inbound_notice_code}</td>--%>
										<%-- 	<td class='center'>${var.notice_detail_id}</td>
											<td class='center'>${var.purchase_code}</td>
											<td class='center'>${var.purchase_detail_id}</td>
											<td class='center'>${var.inbound_notice_stock_batch_id}</td> --%>
											<td class='center'>${var.batch_code}</td>
											<%-- <td class='center'>${var.sku_id}</td> 
											<td class='center'>${var.sku_encode}</td>--%>
											<td class='center'>${var.sku_name}</td>
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
											<td class='center'>${var.create_time}</td>
											<c:if test="${pd.status==1 }"><!-- 完成状态不可编辑 -->
											<td class="center">
												
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" style="display:none" title="编辑" ${ var.status>0?"disabled='disabled'":"" }  onclick="edit('${var.pgt_inbound_check_detail_id}');">
														编辑
													</a>
													<a class="btn btn-xs btn-success" title="提交" ${ var.status>0?"disabled='disabled'":"" } onclick="submitCheck('${var.pgt_inbound_check_detail_id}',${var.total_quantity});">
														提交
													</a>
													</c:if>
													
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" ${ var.status==0?"":"disabled='disabled'" } onclick="del('${var.pgt_inbound_check_detail_id}');">
														删除
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
															</c:if>
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
		
		//新增
		function add(inbound_notice_stock_batch_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>inboundcheckdetail/goAdd.do?inbound_notice_stock_batch_id='+inbound_notice_stock_batch_id;
			 diag.Width = 450;
			 diag.Height = 555;
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
		function submitCheck(pgt_inbound_check_detail_id,curCount){
			//校验当前记录提交后是否质检数已大于当前批次的总数
			var mess = "确定要提交吗?";
			
			if((${pd.checkTotal}+curCount)>${pd.quantity}){
				mess = "提交后质检总数量"+(${pd.checkTotal}+curCount)+"将大于本批次数量"+parseInt(${pd.quantity})+",不允许提交";
				//大于时不允许提交，20171009修改
				bootbox.alert(mess);
				return false;
			}
			bootbox.confirm(mess, function(result) {
				if(result) {
					$.ajax({
						type: "POST",
						url: '<%=basePath%>inboundcheckdetail/ajaxEdit.do?tm='+new Date().getTime()+'&status=1&pgt_inbound_check_detail_id='+pgt_inbound_check_detail_id+"&isSubmit=1",
				   
						dataType:'text',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							if(data=="success"){
								bootbox.alert( "提交成功.",function(){
									//top.Dialog.close();
									tosearch();
								});
							//	window.close();
							}else{
								bootbox.alert("更新失败.");
							}

						},
						error:function(data){
							if(data=="success"){
								bootbox.alert( "更新成功",function(){
								//	top.Dialog.close();
									tosearch();
								});
							//	Window.close();
							}else{
								bootbox.alert("更新失败");
							}
						}
					});
				}
			});
			
		}
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>inboundcheckdetail/delete.do?pgt_inbound_check_detail_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>inboundcheckdetail/goEdit.do?pgt_inbound_check_detail_id='+Id;
			 diag.Width = 450;
			 diag.Height = 455;
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
								url: '<%=basePath%>inboundcheckdetail/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>inboundcheckdetail/excel.do';
		}
	</script>


</body>
</html>