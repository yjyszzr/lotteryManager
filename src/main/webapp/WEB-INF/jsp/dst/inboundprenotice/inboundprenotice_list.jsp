<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.fh.util.DateUtil"%>
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
						<form action="inboundprenotice/list.do" method="post" name="Form" id="Form">
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
									发货通知单编码：<input type="text" placeholder="发货通知单编码" class="nav-search-input" id="nav-search-input" autocomplete="off" name="inbound_pre_notice_code" value="${pd.inbound_pre_notice_code }" />
								</td>
								<td class="pgt-query-form-td">
								 	状态：<select name="status" id="status" data-placeholder="状态" value="${pd.status }" style="width: 120px;">
								 	<option value="">全部状态</option>
									<option value="0" ${0==pd.status  and pd.status!=""  ?"selected":""}>待审批</option>
									<option value="1" ${1==pd.status  ?"selected":""}>已批准</option>
									<option value="2" ${2==pd.status  ?"selected":""}>已驳回</option>
									<option value="9" ${9==pd.status  ?"selected":""}>完成</option>
								  	</select>
								</td>
								<td class="pgt-query-form-td">
								 	采购组：<select name="purchase_group_id" id="purchase_group_id" data-placeholder="选择采购组" style="width: 120px;">
								 	<option value="">全部采购组</option>
								 	<c:forEach items="${gpList}" var="gp" varStatus="vs">
								 			<option value="${gp.purchase_group_id}" ${gp.purchase_group_id==pd.purchase_group_id?"selected":""}>${gp.purchase_group_name}</option>
								 	</c:forEach>
									</select>
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
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">发货通知单编码</th>
									<th class="center">采购订单编码</th>
<!-- 									<th class="center">实际到货日期</th>
 -->									<th class="center">仓库编码</th>
									<th class="center">仓库名称</th>
									<th class="center">预计到货日期</th>
									<th class="center">状态</th>
									<th class="center">提交人</th>
									<th class="center">提交时间</th>
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.inbound_pre_notice_code}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.inbound_pre_notice_code}</td>
											<%-- <td class='center'>${var.inbound_notice_code}</td> --%>
										<%-- 	<td class='center'>${var.bill_type}</td> --%>
											<td class='center'>${var.purchase_code}</td>
											<%-- <td class='center'>${var.arrival_time}</td> --%>
											<td class='center'>${var.store_sn}</td>
											<td class='center'>${var.store_name}</td>
											<td class='center'>${var.pre_arrival_time==null?"":DateUtil.toDateStr(var.pre_arrival_time)}</td>
											<td class='center'>
												<c:if test="${var.status==0 }">
													待审批
												</c:if>
												<c:if test="${var.status==1 }">
													已批准
												</c:if>
												<c:if test="${var.status==2 }">
													已驳回
												</c:if>
												<c:if test="${var.status==9 }">
													完成
												</c:if>
											
											</td>
											<td class='center'>${var.createby}</td>
											<td class='center'>${var.create_time==null?"":DateUtil.toSdfTimeStr(var.create_time)}</td>
											<%-- <td class='center'>${var.updateby}</td>
											<td class='center'>${var.update_time}</td> --%>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<%--  只有在待审核状态下才可以审批 --%>
													<a class="btn btn-xs btn-success"   onclick="toDetailList('${var.inbound_pre_notice_code}')">
														明细
														</a>
														<a class="btn btn-xs btn-success"  ${var.status==0?"":"disabled='disabled'" }  onclick="updateStaus('${var.inbound_pre_notice_code}',1)">
														批准
														</a>
														<a class="btn btn-xs btn-success"  ${var.status==0?"":"disabled='disabled'" }   onclick="updateStaus('${var.inbound_pre_notice_code}',2)">
														驳回
														</a>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.inbound_pre_notice_code}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.inbound_pre_notice_code}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
								<%-- <td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if>
								</td> --%>
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
			 diag.URL = '<%=basePath%>inboundprenotice/goAdd.do';
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
					var url = "<%=basePath%>inboundprenotice/delete.do?inbound_pre_notice_code="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		//修改
		function toDetailList(inbound_pre_notice_code){
			
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="到货单详情";
		     diag.URL = '<%=basePath%>pgtinboundprenoticedetail/list.do?inbound_pre_notice_code='+inbound_pre_notice_code;
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
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>inboundprenotice/goEdit.do?inbound_pre_notice_code='+Id;
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
		//更新状态
		function updateStaus(Id,status,purchase_code){
			var promptMess = "";
			var isCommit = "";//如果是提交，后台需判断是否有明细
			if(status =="2"){
				promptMess = "确定要驳回该到货通知单吗？";
			}else 	if(status =="1"){
				promptMess = "确定要审核通过该到货通知单吗？？";
			}else if(status =="${PurchaseConstants.BUSINESS_STATUS_COMPLETE}"){
				promptMess = "确定要标记完成该${typeText}单吗？";
			}
			
			bootbox.confirm(promptMess, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>inboundprenotice/edit.do?isUpdateStaus=1&inbound_pre_notice_code="+Id+"&tm="+new Date().getTime()+"&status="+status+"&isCommit="+isCommit;
					if(purchase_code){
						url += "&purchase_code="+purchase_code;
					}
					$.ajax({ 
				           type: "POST", 
				           url: url, 
				           dataType:"text", 
				           success: function(data){ 
				        	   		top.hangge();
								if(1==data){
										tosearch();
								}else{
									bootbox.alert(decodeURI(data));
								}
				           }, 
				           error : function(XMLHttpRequest, textStatus, errorThrown){ 
				               alert(textStatus); 
				           } 
				        }); 
					
					/* $.get(url,function(data){
						top.hangge();
						if(1==data){
							//alert(data);
							
								tosearch();
						}else{
							bootbox.alert(data);
						}
						//alert(data);
					}); */
				}
			});
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
								url: '<%=basePath%>inboundprenotice/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>inboundprenotice/excel.do';
		}
	</script>


</body>
</html>