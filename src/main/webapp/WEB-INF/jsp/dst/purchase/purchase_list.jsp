<%@page import="com.fh.common.PurchaseConstants"%>
<%@page import="com.fh.util.DateUtil"%>
<%@page import="com.fh.common.TextConfig"%>
<%@page import="javax.wsdl.Import"%>
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
							<div style="display:none">
								${typeText=TextConfig.getPurchaseBsTypeText(pd.business_type)}
							</div>
							
							
						<!-- 检索  -->
						<form action="purchase/list.do" method="post" name="Form" id="Form">
						<!-- 操作类型采购、审批等 -->
						<input type="hidden" name="bsType" value="${pd.bsType}">
						<%--采购单类型 标准和生产 --%>
						<input type="hidden" name="business_type" value="${pd.business_type}">
						
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
									${typeText}单编码：<input type="text" placeholder="${typeText}单编码" class="nav-search-input" id="nav-search-input" autocomplete="off" name="purchase_code" value="${pd.purchase_code }" />
								</td>
								<td class="pgt-query-form-td">
									供应商名称：<input type="text" placeholder="供应商名称" class="nav-search-input" id="nav-search-input" autocomplete="off" name="supplier_name" value="${pd.supplier_name }" />
								</td>
								
								<c:if test="${pd.bsType!=1 && pd.bsType!=2}"><!-- 审核订单状态为待审 -->
								<td  class="pgt-query-form-td">
								 	订单状态：<select name="status" id="status" data-placeholder="${typeText}单状态" value="${pd.status }" style="width: 120px;">
								 	<option value="">全部状态</option>
									<option value="${PurchaseConstants.BUSINESS_STATUS_NO_COMMIT}" ${PurchaseConstants.BUSINESS_STATUS_NO_COMMIT==pd.status and pd.status!="" ?"selected":""}>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_NO_COMMIT)}</option>
									<option value="${PurchaseConstants.BUSINESS_STATUS_CREATE}" ${PurchaseConstants.BUSINESS_STATUS_CREATE==pd.status?"selected":"" }>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_CREATE)}</option>
									
									<option value="${PurchaseConstants.BUSINESS_STATUS_AGREE}"  ${PurchaseConstants.BUSINESS_STATUS_AGREE==pd.status?"selected":"" }>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_AGREE)}</option>
									<option value="${PurchaseConstants.BUSINESS_STATUS_REJECT}"  ${PurchaseConstants.BUSINESS_STATUS_REJECT==pd.status?"selected":"" }>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_REJECT)}</option>
									<option value="${PurchaseConstants.BUSINESS_STATUS_COMPLETE}" ${PurchaseConstants.BUSINESS_STATUS_COMPLETE==pd.status?"selected":"" }>${TextConfig.getBusinessStatus(PurchaseConstants.BUSINESS_STATUS_COMPLETE)}</option>
								  	</select>
								</td>
								</c:if>
								<td  class="pgt-query-form-td">
								 	采购组：<select name="purchase_group_id" id="purchase_group_id" data-placeholder="选择采购组" style="width: 120px;">
								 	<option value="">全部采购组
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
									<th class="center">${typeText}编码</th>
									<th class="center">供应商</th>
									<th class="center">采购组</th>
									<!--  <th class="center">票据类型</th>
									<th class="center">业务类型</th>-->
									<th class="center">${typeText}日期</th>
									<!-- <th class="center">采购部门</th>
									<th class="center">采购组织</th> -->
									<th class="center">状态</th>
									<th class="center">提交人</th>
									<th class="center">提交时间</th>
									<!-- <th class="center">更新人</th>
									<th class="center">更新时间</th> -->
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.purchase_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.purchase_code}</td>
											<td class='center'>${var.supplier_name}</td>
											<td class='center'>${var.purchase_group_name}</td>
											<%-- <td class='center'>${var.bill_type}</td>
											<td class='center'>${var.business_type}</td> --%>
											<td class='center'>${DateUtil.toDateStr(var.purchase_date)}</td>
											<%-- <td class='center'>${var.purchase_dep}</td>
											<td class='center'>${var.purchase_org}</td> --%>
											<td class='center'>${TextConfig.getBusinessStatus(var.status)}</td>
											<td class='center'>${var.commitby}</td>
											<td class='center'>${var.commit_time==null?"":DateUtil.toSdfTimeStr(var.commit_time)}</td>
											<%-- <td class='center'>${var.updateby}</td>
											<td class='center'>${DateUtil.toSdfTimeStr(var.update_time)}</td> --%>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
												<c:if test="${pd.bsType==null || pd.bsType==''}"> 
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" ${var.status==PurchaseConstants.BUSINESS_STATUS_NO_COMMIT?"":"disabled='disabled'"}  onclick="edit('${var.purchase_id}');">
														编辑
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-success" ${var.status==PurchaseConstants.BUSINESS_STATUS_NO_COMMIT?"":"disabled='disabled'"} onclick="del('${var.purchase_id}');">
														删除
													</a>
													</c:if>
														<a class="btn btn-xs btn-success"  ${var.status==PurchaseConstants.BUSINESS_STATUS_NO_COMMIT?"":"disabled='disabled'"} onclick="updateStaus('${var.purchase_id}',${PurchaseConstants.BUSINESS_STATUS_CREATE},'${var.purchase_code}')">
														提交
													</a>
													<%--待审状态显示批准核驳回按钮，如果是批准状态显示完成按钮 --%>
													
														<a class="btn btn-xs btn-success"  ${var.status==PurchaseConstants.BUSINESS_STATUS_AGREE?"":"disabled='disabled'"}  onclick="updateStaus('${var.purchase_id}',${ PurchaseConstants.BUSINESS_STATUS_COMPLETE})">
														完成
													</a>
														
													
													
													<a class="btn btn-xs btn-success"    ${var.status==PurchaseConstants.BUSINESS_STATUS_AGREE?"":"disabled='disabled'"} onclick="toUpdateSendQuantity('${var.purchase_code}');">
														可发货数量
													</a>
													<c:if test="${pd.business_type==PurchaseConstants.BUSINESS_TYPE_PRODUCT || pd.business_type==PurchaseConstants.BUSINESS_TYPE_NORM}">
													<a class="btn btn-xs btn-success"    ${( var.status==PurchaseConstants.BUSINESS_STATUS_AGREE)?"":"disabled='disabled'"} onclick="toPurchaseReturn('${var.purchase_code}','${var.supplier_sn}');">
														退货
													</a>
													</c:if>
												</c:if>
												<!-- 审批 -->
												<c:if test="${pd.bsType==1}">
														<%--  只有在待审核状态下才可以审批 --%>
														<a class="btn btn-xs btn-success"  ${var.status==PurchaseConstants.BUSINESS_STATUS_CREATE?"":"disabled='disabled'" } onclick="updateStaus('${var.purchase_id}',${ PurchaseConstants.BUSINESS_STATUS_AGREE})">
														批准
														</a>
														<a class="btn btn-xs btn-success"  ${var.status==PurchaseConstants.BUSINESS_STATUS_CREATE?"":"disabled='disabled'" } onclick="updateStaus('${var.purchase_id}',${ PurchaseConstants.BUSINESS_STATUS_REJECT})">
														驳回
													</a>
												</c:if>
												<!--  创建到货单 -->
												<c:if test="${pd.bsType==2}">
													<a class="btn btn-xs btn-success" ${var.status==PurchaseConstants.BUSINESS_STATUS_AGREE?"":"disabled='disabled'"} onclick="toCreateInboundNotice('${var.purchase_code}','${var.purchase_group_id}');">
														创建到货单
													</a>
												</c:if>
												<a class="btn btn-xs btn-success" onclick="toDetailList('${var.purchase_code}','${var.status }','${var.supplier_sn}');">
														明细
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
																<a style="cursor:pointer;" onclick="edit('${var.purchase_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.purchase_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
								<c:if test="${pd.bsType==null || pd.bsType==''}">
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
			 diag.URL = '<%=basePath%>purchase/goAdd.do?business_type=${pd.business_type}';
			 diag.Width = 450;
			 diag.Height = 350;
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
					var url = "<%=basePath%>purchase/delete.do?purchase_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		//更新状态
		function updateStaus(Id,status,purchase_code){
			var promptMess = "";
			var isCommit = "";//如果是提交，后台需判断是否有明细
			if(status =="${PurchaseConstants.BUSINESS_STATUS_CREATE}"){
				promptMess = "确定要提交该${typeText}单吗？";
				isCommit = "1";
			}else 	if(status =="${PurchaseConstants.BUSINESS_STATUS_REJECT}"){
				promptMess = "确定要驳回该${typeText}单吗？";
			}else 	if(status =="${PurchaseConstants.BUSINESS_STATUS_AGREE}"){
				promptMess = "确定要审核通过该${typeText}单吗？？";
			}else if(status =="${PurchaseConstants.BUSINESS_STATUS_COMPLETE}"){
				promptMess = "确定要标记完成该${typeText}单吗？";
			}
			
			bootbox.confirm(promptMess, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>purchase/edit.do?isUpdateStaus=1&purchase_id="+Id+"&tm="+new Date().getTime()+"&status="+status+"&isCommit="+isCommit;
					if(purchase_code){
						url += "&purchase_code="+purchase_code;
					}
					$.get(url,function(data){
						//alert(data);
						top.hangge();
						if("200"==data){
							bootbox.alert("请先添加订单明细再提交",function(){
							});
						}else{
							tosearch();
						}
						
						
						
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
			 diag.URL = '<%=basePath%>purchase/goEdit.do?purchase_id='+Id;
			 diag.Width = 450;
			 diag.Height = 350;
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
		
		//修改
		function toDetailList(purchase_code,status,supplier_sn){
			
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="${typeText}单详情";
		    diag.URL = '<%=basePath%>purchasedetail/list.do?purchase_code='+purchase_code+'&status='+status+'&supplier_sn='+supplier_sn;
		   if(${pd.bsType!=null}){
		    	diag.URL = diag.URL+"&bsType="+'${pd.bsType}';
		   }
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
		function toCreateInboundNotice(purchase_code,purchase_group_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="创建到货单";
		    diag.URL = '<%=basePath%>purchasedetail/listForInbound.do?purchase_code='+purchase_code+"&business_type=${pd.business_type}"+"&purchase_group_id="+purchase_group_id;
		    if(${pd.bsType!=null}){
		    	diag.URL = diag.URL+"&bsType="+'${pd.bsType}';
		   }
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
		function toUpdateSendQuantity(purchase_code){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="调整可发货数量";
		     diag.URL = '<%=basePath%>purchasedetail/listForSend.do?purchase_code='+purchase_code;
		     if(${pd.bsType!=null}){
			    	diag.URL = diag.URL+"&bsType="+'${pd.bsType}';
			   }
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
				// 退货
				function toPurchaseReturn(purchase_code,supplier_sn){
					 top.jzts();
					 var diag = new top.Dialog();
					 diag.Drag=true;
					 diag.Title ="退货"+purchase_code;
				     diag.URL = '<%=basePath%>purchasedetail/listPurchaseReturn.do?purchase_code='+purchase_code+"&supplier_sn="+supplier_sn+"&business_type=${pd.business_type}";
				     if(${pd.bsType!=null}){
					    	diag.URL = diag.URL+"&bsType="+'${pd.bsType}';
					   }
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
								url: '<%=basePath%>purchase/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>purchase/excel.do';
		}
	</script>
	

</body>
</html>