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
						<form action="allocationdetail/list.do?allocation_code=${pd.allocation_code}" method="post" name="Form" id="Form">
						<input type="hidden" name="from_store_sn" id="from_store_sn" value="${pd.from_store_sn}"/>
						
						<table class="table table-striped table-bordered table-hover" style="margin-top:5px;">
							<tr>
								<th class="center">调拨单号:</th>
								<th class="center">${pd.allocation_code}</th>
								<th class="center">状态：</th>
								<th class="center">
											    <c:if test="${pd.status == 0}">待提交 </c:if>
											    <c:if test="${pd.status == 1}">待审核 </c:if>
											    <c:if test="${pd.status == 2}">通过 </c:if>
											    <c:if test="${pd.status == 3}">驳回 </c:if>
											    <c:if test="${pd.status == 4}">已发货 </c:if>
											    <c:if test="${pd.status == 9}">已完成 </c:if>
								</th>
								<th class="center"></th>
								<th class="center"></th>
								<th class="center"></th>
								<th class="center"></th>
							</tr>
							<tr>
								<th class="center">调出仓库编号:</th>
								<th class="center">${pd.from_store_sn}</th>
								<th class="center">调出仓库名称:</th>
								<th class="center">${pd.from_store_name}</th>
								<th class="center">调入仓库编号:</th>
								<th class="center">${pd.to_store_sn}</th>
								<th class="center">调入仓库名称:</th>
								<th class="center">${pd.to_store_name}</th>
							</tr>
							<tr>
								<th class="center">预计发货时间:</th>
								<th class="center"><fmt:formatDate value="${pd.pre_send_time}" pattern="yyyy-MM-dd"/></th>
								<th class="center">实际发货时间:</th>
								<th class="center"><fmt:formatDate value="${pd.send_time}" pattern="yyyy-MM-dd"/></th>
								<th class="center">预计到货时间:</th>
								<th class="center"><fmt:formatDate value="${pd.pre_arrival_time}" pattern="yyyy-MM-dd"/></th>
								<th class="center">实际到货时间:</th>
								<th class="center"><fmt:formatDate value="${pd.arrival_time}" pattern="yyyy-MM-dd"/></th>
							</tr>
							<tr>
								<th class="center">提交人:</th>
								<th class="center">${pd.commitby}</th>
								<th class="center">提交时间:</th>
								<th class="center"><fmt:formatDate value="${pd.commit_time}" pattern="yyyy-MM-dd"/></th>
								<th class="center">审批人:</th>
								<th class="center">${pd.auditby}</th>
								<th class="center">审批时间:</th>
								<th class="center"><fmt:formatDate value="${pd.audit_time}" pattern="yyyy-MM-dd"/></th>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">调拨单编码</th>
									<th class="center">商品编号</th>
									<th class="center">商品名称</th>
									<th class="center">单位</th>
									<th class="center">规格</th>
									<th class="center">是否日期批次管理</th>
									<th class="center">属性分类</th>
									<th class="center">预计调拨数量</th>
									<th class="center">实际调拨数量</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${page.currentResult+vs.index+1}</td>
											<td class='center'>${var.allocation_code}</td>
											<td class="center">${var.sku_encode}</td>
											<td class='center'>${var.sku_name}</td>
											<td class="center">${var.unit_name}</td>
											<td class="center">${var.spec}</td>
											<td class="center">
											    <c:if test="${var.isopen_batch == 0}">不开启</c:if>
											    <c:if test="${var.isopen_batch == 1}">开启</c:if>
											</td>
											<td class="center">
											    <c:if test="${var.attr_cate == 0}">原材料</c:if>
											    <c:if test="${var.attr_cate == 1}">包材</c:if>
											    <c:if test="${var.attr_cate == 2}">成品</c:if>
											</td>
											<td class='center'>${var.pre_quantity}</td>
											<td class='center'>
											    ${var.quantity}
											</td>
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
		function add(sn,existSku_encodes){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>allocationdetail/goAdd.do?allocation_code='+sn+"&from_store_sn="+$("#from_store_sn").val()+"&existSku_encodes="+existSku_encodes;
			 diag.Width = 460;
			 diag.Height = 255;
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
					var url = "<%=basePath%>allocationdetail/delete.do?allocation_detail_id="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>allocationdetail/goEdit.do?allocation_detail_id='+Id;
			 diag.Width = 450;
			 diag.Height = 125;
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
								url: '<%=basePath%>allocationdetail/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>allocationdetail/excel.do';
		}
	</script>


</body>
</html>