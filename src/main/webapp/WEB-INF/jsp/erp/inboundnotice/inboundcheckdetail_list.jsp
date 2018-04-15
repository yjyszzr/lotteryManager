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
						<form action="inboundnoticedetail/list.do" method="post" name="Form" id="Form">
						<input type="hidden" name="inbound_notice_code" id="inbound_notice_code" value="${pd.inbound_notice_code}"/>
						<input type="hidden" name="bill_type" id="bill_type" value="${pd.bill_type}"/>
						<input type="hidden" name="page" id="page" value="${pd.page}"/>
						<input type="hidden" name="store_sn" id="store_sn" value="${pd.store_sn}"/>
						<input type="hidden" name="bill_type" id="bill_type" value="${pd.bill_type}"/>
						<input type="text"  name="in_code" id="in_code" autocomplete="off"   style="display:none;"/>
						<table style="margin-top:5px;">
							<tr>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" name="ids" /><span class="lbl"></span></label>
									<%-- <label class="pos-rel"><input type='checkbox' name='ids' onclick="inboundone(this,'${var.pgt_inbound_check_detail_id}');" value="${var.pgt_inbound_check_detail_id}" class="ace" /><span class="lbl"></span></label> --%>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<!-- <th class="center">id</th> -->
									<th class="center">入库通知单编号</th>
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
									<!-- <th class="center">操作</th> -->
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
											    <c:if test="${var.status == 1}">
													<label class="pos-rel"><input type='checkbox' name='ids' value="${var.pgt_inbound_check_detail_id}" class="ace" /><span class="lbl"></span></label>
											    </c:if>
											    <c:if test="${var.status == 2}">
													<!-- <label class="pos-rel"><input type='checkbox' disabled="disabled" class="ace" /><span class="lbl"></span></label> -->
											    </c:if>
											</td>
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
											    <c:if test="${var.bad_deal_type == 0}">退回</c:if>
											    <c:if test="${var.bad_deal_type == 1}">入不良品</c:if>
											</td>
											<td class='center'>${var.spec}</td>
											<td class='center'>${var.unit_name}</td>
											<%-- <td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<a class="btn btn-mini btn-success" onclick="inbound('${var.inbound_notice_code}',
													    '${var.batch_code}','${var.sku_name}','${var.sku_encode}','${var.total_quantity}',
													    '${var.good_quantity}','${var.bad_quantity}','${var.scrap_quantity}','${var.bad_deal_type}',
													    '${var.spec}','${var.unit_name}','${var.notice_detail_id}','${var.purchase_code}',
													    '${var.purchase_detail_id}','${var.inbound_notice_stock_batch_id}','${var.sku_id}'
													);">入库</a>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<li>
																<a class="btn btn-mini btn-success" onclick="inbound('${var.inbound_notice_code}',
																    '${var.batch_code}','${var.sku_name}','${var.sku_encode}','${var.total_quantity}',
																    '${var.good_quantity}','${var.bad_quantity}','${var.scrap_quantity}','${var.bad_deal_type}',
																    '${var.spec}','${var.unit_name}','${var.notice_detail_id}','${var.purchase_code}',
																    '${var.purchase_detail_id}','${var.inbound_notice_stock_batch_id}','${var.sku_id}'
																);">入库</a>
															</li>
														</ul>
													</div>
												</div>
											</td> --%>
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
									<%-- <c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if> --%>
									<a class="btn btn-mini btn-success" onclick="inbound();">入库</a>
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
		
		function inbound(){
			var list=document.getElementsByName("ids"); 
			var namelist="";
			for(i=0;i<list.length;i++){
			   if(list[i].checked){ 
				  if(list[i].value != 'on'){
			      	namelist += list[i].value+",";
				  }
			   }
			}
			var in_code = "";
			if(namelist.length > 0 && namelist.substring(0,namelist.length-1).length > 0){
				in_code = namelist.substring(0,namelist.length-1);
			}else{
				alert("请选中要入库的商品！");
				return;
			}
			bootbox.confirm("确定要入库吗?", function(result) {
				if(result) {
					var data = "&store_sn="+$("#store_sn").val()+"&bill_type="+$("#bill_type").val(); 
					top.jzts();
					var url = "<%=basePath%>inboundnoticedetail/inbound.do?inbound_notice_code="+$("#inbound_notice_code").val()+data+"&in_code="+in_code+"&tm="+new Date().getTime();
					$.get(url,function(data,status){
					top.hangge();
						if(data.msg=="success"){
								alert("入库成功");
							}else{
								alert(data.info);
							}
						tosearch();
					});
				}
			});
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>inboundcheckdetail/goAdd.do';
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