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
			<div class="main-content-inner"
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							
						<!-- 检索  -->
						<form action="inboundnoticestockbatch/listForCheck.do" method="post" name="Form" id="Form">
						<%-- <table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="">1</option>
									<option value="">2</option>
								  	</select>
								</td>
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if>
							</tr>
						</table> --%>
						<!-- 检索  -->
						<input type="hidden" name="inbound_notice_code" id="inbound_notice_code" value="${pd.inbound_notice_code}"/>
						<input type="hidden" name="notice_detail_id" id="notice_detail_id" value="${pd.notice_detail_id}"/>
						<input type="hidden" name="sku_id" id="sku_id" value="${pd.sku_id}"/>
						<input type="hidden" name="sku_name" id="sku_name" value="${pd.sku_name}"/>
						<input type="hidden" name="sku_encode" id="sku_encode" value="${pd.sku_encode}"/>
						<input type="hidden" name=quantity id="quantity" value="${pd.quantity}"/>
						<input type="hidden" name="spec" id="spec" value="${pd.spec}"/>
						<input type="hidden" name="unit_name" id="unit_name" value="${pd.unit_name}"/>
						<input type="hidden" name="sku_barcode" id="sku_barcode" value="${pd.sku_barcode}"/>
						<input type="hidden" name="status" id="status" value="${pd.status}"/>
						<input type="hidden" name="bill_type" id="bill_type" value="${pd.bill_type}"/>
						<input type="hidden" name="sku_barcode" id="sku_barcode" value="${pd.sku_barcode}"/>
						<input type="hidden" name="page" id="page" value="${pd.page}"/>

						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<!-- <th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th> -->
									<th class="center" style="width:50px;">序号</th>
									<!-- <th class="center">到货通知单编码</th>
									<th class="center">明细ID</th>
									<th class="center">商品ID</th> -->
									<th class="center">商品名称</th>
									<th class="center">商品编码</th>
									
									<th class="center">批次</th>
									<th class="center">数量</th>
									<th class="center">规格</th>
									<th class="center">单位</th>
									<th class="center">状态</th>
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.inbound_notice_stock_batch_id}" class="ace" /><span class="lbl"></span></label>
											</td> --%>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<%-- <td class='center'>${var.inbound_notice_code}</td>
											<td class='center'>${var.notice_detail_id}</td>
											<td class='center'>${var.sku_id}</td> --%>
											<td class='center'>${var.sku_name}</td>
											<td class='center'>${var.sku_encode}</td>
											<td class='center'>${var.batch_code}</td>
											<td class='center'>${var.quantity}</td>
											<td class='center'>${var.spec}</td>
											<td class='center'>${var.unit_name}</td>
											<td class='center'>
											<c:if test="${var.status == 1}">
												待质检
											</c:if>
											<c:if test="${var.status == 2}">
												质检完成
											</c:if>
											</td>
											
																				<td class="center">
											
													<a class="btn btn-xs btn-success" title="质检"onclick="toCheckList('${var.inbound_notice_stock_batch_id}','${var.status}','${var.quantity}');">质检</a>
													<a class="btn btn-xs btn-success" title="完成" ${var.status==1?"":"disabled='true'"} onclick="makeComplete('${var.inbound_notice_stock_batch_id}','${var.quantity}');">完成</a>
												
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
													
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.inbound_notice_stock_batch_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.inbound_notice_stock_batch_id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.inbound_notice_stock_batch_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
						<c:if test = "${pd.page != 5}">
						<c:if test="${pd.status != 1 && (pd.bill_type==1 || pd.bill_type==2) }">
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add('${pd.inbound_notice_code}',
										'${pd.notice_detail_id}','${pd.sku_id}','${pd.sku_name}',
										'${pd.sku_encode}','${pd.sku_barcode}','${pd.unit_name}',
										'${pd.spec}','${pd.quantity}','${pd.bill_type}','${pd.status}'
										);">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if>
								</td>
								<%-- <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td> --%>
							</tr>
						</table>
						</div>
						</c:if>
						</c:if>
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
		function toCheckList(inbound_notice_stock_batch_id,status,quantity){
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="质检表";
			 diag.URL = '<%=basePath%>inboundcheckdetail/listAll.do?inbound_notice_stock_batch_id='+inbound_notice_stock_batch_id+"&status="+status+"&quantity="+quantity;
			 diag.Width = 1250;
			 diag.Height = 755;
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
		//新增
		function add(inbound_notice_code, notice_detail_id, sku_id, sku_name, sku_encode, sku_barcode,
				unit, spec, quantity, bill_type, status){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>inboundnoticestockbatch/goAdd.do?notice_detail_id='+notice_detail_id
				 +'&inbound_notice_code='+inbound_notice_code
				 +'&sku_id='+sku_id+'&sku_name='+sku_name+'&sku_encode='+sku_encode+'&sku_barcode='+sku_barcode
				 +'&unit_name='+unit
				 +'&spec='+spec
				 +'&status='+status
				 +'&bill_type='+bill_type
				 +'&quantity='+quantity;
			 diag.Width = 450;
			 diag.Height = 450;
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
					var url = "<%=basePath%>inboundnoticestockbatch/delete.do?inbound_notice_stock_batch_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//修改
		function printcode(Id, inbound_notice_code, notice_detail_id, sku_id, sku_name, sku_encode, sku_barcode, 
				unit, spec, quantity, bill_type, status){
			
				if(parseInt(status) != 1){
					alert("未入库不能打印!");
					return false;
				}
			
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="打印二维码";
			 diag.URL = '<%=basePath%>inboundnoticestockbatch/goPrint.do?inbound_notice_stock_batch_id='+Id;
			 	 +'notice_detail_id='+notice_detail_id
				 +'&inbound_notice_code='+inbound_notice_code
				 +'&sku_id='+sku_id+'&sku_name='+sku_name+'&sku_encode='+sku_encode+'&sku_barcode='+sku_barcode
				 +'&unit_name='+unit
				 +'&spec='+spec
				 +'&status='+status
				 +'&bill_type='+bill_type
				 +'&quantity='+quantity;
			 diag.Width = 450;
			 diag.Height = 450;
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
		function edit(Id, inbound_notice_code, notice_detail_id, sku_id, sku_name, sku_encode, sku_barcode, 
				unit, spec, quantity, bill_type, status){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>inboundnoticestockbatch/goEdit.do?inbound_notice_stock_batch_id='+Id;
			 	 +'notice_detail_id='+notice_detail_id
				 +'&inbound_notice_code='+inbound_notice_code
				 +'&sku_id='+sku_id+'&sku_name='+sku_name+'&sku_encode='+sku_encode+'&sku_barcode='+sku_barcode
				 +'&unit_name='+unit
				 +'&spec='+spec
				 +'&status='+status
				 +'&bill_type='+bill_type
				 +'&quantity='+quantity;
			 diag.Width = 450;
			 diag.Height = 450;
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
		/**
			“完成”操作
		*/
		function makeComplete(inbound_notice_stock_batch_id,quantity){
			bootbox.confirm("确定要标记完成吗?", function(result) {
				if(result) {
					$.ajax({
						type: "POST",
						url: '<%=basePath%>inboundnoticestockbatch/completeCheck.do?tm='+new Date().getTime()+'&inbound_notice_stock_batch_id='+inbound_notice_stock_batch_id+"&quantity="+quantity,
				   
						dataType:'text',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							if(data=="success"){
								bootbox.alert( "质检完成.",function(){
									//top.Dialog.close();
									tosearch();
								});
							//	window.close();
							}else if(data=="200" || data=="201"){
								bootbox.alert("操作失败,有未提交的质检单或还未质检.");
							}else if(data=="202"){
								bootbox.alert("操作失败,已质检数量和批次的商品数量不符");
							}
							else{
								bootbox.alert(mess);
							}

						},
						error:function(data){
							if(data=="success"){
								bootbox.alert( "质检完成",function(){
								//	top.Dialog.close();
									tosearch();
								});
							//	Window.close();
							}else if(data=="200" || data=="201"){
								bootbox.alert("操作失败,有未提交的质检单或还未质检.");
							}else if(data=="202"){
								bootbox.alert("操作失败,已质检数量和批次的商品数量不符");
							}
							else{
								bootbox.alert(mess);
							}

						}
					});

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
								url: '<%=basePath%>inboundnoticestockbatch/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>inboundnoticestockbatch/excel.do';
		}
	</script>


</body>
</html>