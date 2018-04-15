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
						<form action="erporder/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
							   <%-- <td style="vertical-align:middle;padding-left:2px;">
								 	<select class="chosen-select form-control" name="shop_id" id="shop_id" data-placeholder="请选择" style="vertical-align:middle;width: 180px;">
									<option value=""></option>
									<c:if test="${not empty varListShop}">
									<c:forEach items="${varListShop}" var="var" varStatus="vs">
									    <c:if test="${var.shop_id!=pd.shop_id}">
										<option value="${var.shop_id}">${var.shop_name}</option>
										</c:if>
										<c:if test="${var.shop_id==pd.shop_id}">
									    <option value="${var.shop_id}" selected>${var.shop_name}</option>
									    </c:if>
									</c:forEach>
									 </c:if>
								  	</select>
								  	<input type="hidden" name="shop_name" id="shop_name"  value="${pd.shop_name}" />
								</td> --%>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="store_id" id="store_id" data-placeholder="仓库列表" style="vertical-align:top;width: 150px;">
										<c:if test="${not empty storeList}">
											<c:forEach items="${storeList}" var="var" varStatus="vs">
									         	<c:choose>
													<c:when test="${pd.store_id==var.store_id}">
													    <option selected="selected" value="${var.store_id}">${var.store_name}</option>
													</c:when>
													<c:otherwise>
														<option value="${var.store_id}">${var.store_name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
								  	</select>
								  	<input type="hidden" name="store_name" id="store_name"  value="${pd.store_name}" />
								  	<input type="hidden" name="store_sn" id="store_sn"  value="${pd.store_sn}" />
							    </td>
							
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="订单编号" class="nav-search-input" id="order_sn" autocomplete="off" name="order_sn" value="${pd.order_sn }" />
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="${pd.lastEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="shipping_status" id="shipping_status" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<!-- <option value="-1">全部</option> -->
									<!-- <option value="0">未处理</option> -->
									<option value="1">已出库</option>
									<!-- <option value="2">已收件</option> -->
									<option value="3">运输中</option>
									<!-- <option value="4">已收件</option>
									<option value="5">已派件</option>
									<option value="9">已签收</option> -->
								  	</select>
								</td>
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									
									<th class="center" style="width:50px;">序号</th>
									<th class="center">订单编号</th>
									<!-- <th class="center">订单物品</th> -->
									<th class="center">收货人姓名</th>
									<th class="center">电话</th>
									<th class="center">地址</th>
									<th class="center">配送组织</th>
									<th class="center">订单生成时间</th>
									<th class="center">订单ERP发货状态</th>
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
											
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.order_sn}</td>
											<%-- <td class='center'>${var.order_goods_name}</td> --%>
											<td class='center'>${var.consignee}</td>
											<td class='center'>${var.tel}</td>
											<td class='center'>${var.address}</td>
											<td class='center'>${var.delivery_org_name}</td>
											<td class='center'>${var.add_time_show}</td>
											<td class='center'>${var.shipping_status_show}</td>
											
											<td class="center">
												<c:if test="${var.shipping_status == 1 }">
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="配送调度" onclick="selectDeliveryOrgregion('${var.order_sn}');">
													配送调度
													</a>
													</c:if>
													<c:if test="${not empty var.delivery_org_name}">
													<a class="btn btn-xs btn-success" title="配送发货" onclick="edit('${var.order_sn}','${var.shipping_status}');">
													配送发货
													</a>
													</c:if>
												</div>
												
											    </c:if>
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
			var store_name=$("#store_id").find("option:selected").text();
			if(store_name=="undefine"){
				store_name="";
			}
			$("#store_name").val(store_name);
			if($("#store_name").val()==""){
				$("#store_id").tips({
					side:3,
		            msg:'请选择仓库',
		            bg:'#AE81FF',
		            time:2
		            
		        });
				$("#store_id").focus();
			    return false;
			}

			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			var shipping_status='${pd.shipping_status}';
		    //alert(search_type);
		    if(shipping_status!=''){
		       $("#shipping_status").val(shipping_status);
		    }
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
			 diag.URL = '<%=basePath%>erporder/goAdd.do';
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
					var url = "<%=basePath%>erporder/delete.do?pgt_order_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		var selectDeliveryOrgregionDiag1 = null;
		var select_Order_sn = null;
		//选择配送组织
		function selectDeliveryOrgregion(order_sn){
			 select_Order_sn = order_sn;
			 //top.jzts();
			 selectDeliveryOrgregionDiag1 = new top.Dialog();
			 selectDeliveryOrgregionDiag1.Drag=true;
			 selectDeliveryOrgregionDiag1.Title ="选择配送组织";
			 selectDeliveryOrgregionDiag1.URL = '<%=basePath%>deliveryorg/listSelectForOrder.do';
			 selectDeliveryOrgregionDiag1.Width = 1250;
			 selectDeliveryOrgregionDiag1.Height = 755;
			 selectDeliveryOrgregionDiag1.Modal = true;				//有无遮罩窗口
			 selectDeliveryOrgregionDiag1. ShowMaxButton = true;	//最大化按钮
			 selectDeliveryOrgregionDiag1.ShowMinButton = true;		//最小化按钮
			 selectDeliveryOrgregionDiag1.CancelEvent = function(){ //关闭事件
				 select_Order_sn = null;
				 selectDeliveryOrgregionDiag1.close();
			 };
			 selectDeliveryOrgregionDiag1.show();
			 
			 //关闭窗口
			 top.closeSelectOrgDialog =function(id, name){
				 closeSelectOrg1(id, name);
			}
		}
		
		function closeSelectOrg1(id,name){
			var posturl = "<%=basePath%>erporder/updateErpOrderDeliveryOrg.do?order_sn="+select_Order_sn+"&delivery_org_id="+id;
			$.ajax({
				  type: 'POST',
				  url: posturl,
				  dataType:'json',
                cache: false,
				  success: function(data){
					  if(data.msg=="success")
						{
						  tosearch();
						}
					else
						{
						  alert("配送组织更新失败！");
						  top.hangge();
						}
				  }
				});
			select_Order_sn=null;
			selectDeliveryOrgregionDiag1.close();
		}
		//修改
		function edit(order_sn,shipping_status){
			if(shipping_status!="1"){
				alert("只有已出库订单才可更改状态");
				return;
			}
			bootbox.confirm("确定要变更物料状态为运输中吗?", function(result) {
				if(result) {
					top.jzts();
					 var posturl = "<%=basePath%>erporder/updateErpOrder.do?order_sn="+order_sn;
					 <%-- $.get(url,function(data){
						tosearch();
					}); --%>
					
					$.ajax({
						  type: 'POST',
						  url: posturl,
						  dataType:'json',
                          cache: false,
						  success: function(data){
							  if(data.msg=="success")
								{
								  tosearch();
								}
							else
								{
								  alert("状态更新失败！");
								  top.hangge();
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
								url: '<%=basePath%>erporder/deleteAll.do?tm='+new Date().getTime(),
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
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>erporder/excel.do';
		}
	</script>


</body>
</html>