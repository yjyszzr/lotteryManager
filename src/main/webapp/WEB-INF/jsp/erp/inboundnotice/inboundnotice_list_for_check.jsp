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
						<form action="inboundnotice/listForCheck.do" method="post" name="Form" id="Form">
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
									入库通知单：<input type="text" placeholder="入库通知单" class="nav-search-input" id="nav-search-input" autocomplete="off" name="sh_direct_sales_code" id="sh_direct_sales_code" value="${pd.sh_direct_sales_code}" placeholder="输入到货单编码"/>
								</td>
								
								<td class="pgt-query-form-td">
									业务单据编码：<input type="text" placeholder="业务单据编码" class="nav-search-input"  autocomplete="off" name="sh_purchase_code" value="${pd.sh_purchase_code}" placeholder="业务单据编码"/>
								</td>
								
								<%-- <td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="仓库" class="nav-search-input"  autocomplete="off" name="store_sn_name" id="store_sn_name" value="${pd.store_sn_name}" placeholder="仓库"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td> 
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="sh_type" id="sh_type" data-placeholder="单据类型" style="vertical-align:top;width: 120px;">
									<option value="99" selected='true' >全部类型</option>
									<option value="1" ${pd.sh_type=="1"?'selected':''} >采购入库</option>
									<option value="2" ${pd.sh_type=="2"?'selected':''}>生产入库</option>
									<option value="3" ${pd.sh_type=="3"?'selected':''}>调拨入库</option>
									<option value="4" ${pd.sh_type=="4"?'selected':''}>退货入库</option>
									<option value="5" ${pd.sh_type=="5"?'selected':''}>退料入库</option>
									<option value="6" ${pd.sh_type=="6"?'selected':''}>拒收入库</option>
									<option value="7" ${pd.sh_type=="7"?'selected':''}>其他入库</option>
								  	</select>
								</td>--%>
								<td class="pgt-query-form-td">
								 	状态：<select name="sh_status" id="sh_status" data-placeholder="状态" value="${pd.sh_status}" style="width: 120px;">
									
									<c:if test="${pd.sh_status == '0'}">
										<option value="" >全部状态</option>										
										<option value="0" selected = "true">物流中</option>
										<option value="1">已到货</option>
										<option value="9">已完成</option>
									</c:if>
									<c:if test="${pd.sh_status == '1'}">
										<option value="" >全部状态</option>										
										<option value="0">物流中</option>
										<option value="1" selected = "true">已到货</option>
										<option value="9">已完成</option>
									</c:if>
									<c:if test="${pd.sh_status == '9'}">		
										<option value="" >全部状态</option>								
										<option value="0">物流中</option>
										<option value="1">已到货</option>
										<option value="9" selected = "true">已完成</option>
									</c:if>
									<c:if test="${pd.sh_status != '0'&&pd.sh_status != '1'&&pd.sh_status != '9'}">
										<option selected = "true" value="" >全部状态</option>
										<option value="0">物流中</option>
										<option value="1">已到货</option>
										<option value="9">已完成</option>
									</c:if>
								  	</select>
								  	<td class="pgt-query-form-td">
									 	质检仓库：<select name="stores" id="stores" data-placeholder="仓库列表" style="width: 150px;">
											<option value="">请选择仓库</option>
											<c:if test="${not empty storeList}">
												<c:forEach items="${storeList}" var="var" varStatus="vs">
										         	<c:choose>
														<c:when test="${pd.store_sn==var.store_sn}">
														    <option selected="selected" value="${var.store_sn}">${var.store_name}</option>
														</c:when>
													</c:choose>
													<option value="${var.store_sn}">${var.store_name}</option>
												</c:forEach>
											</c:if>
									  	</select>
								    </td>
								</td>
								<c:if test="${QX.cha == 1}">
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
									<th class="center">到货单编码</th>
									<th class="center">单据类型</th>
									<th class="center">业务单据编码</th>
									<!-- <th class="center">业务单据明细ID</th> -->
								<!-- 	<th class="center">业务单据明细ID</th> -->
									<th class="center">预计到货日期</th>
									<th class="center">实际到货日期</th>
									<th class="center">仓库编码</th>
									<th class="center">仓库名称</th>
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
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.inbound_notice_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.inbound_notice_code}</td>
											<td class='center'>
												<c:if test="${var.bill_type=='1'}">采购入库</c:if>
												<c:if test="${var.bill_type=='2'}">生产入库</c:if>
												<c:if test="${var.bill_type=='3'}">调拨入库</c:if>
												<c:if test="${var.bill_type=='4'}">退货入库</c:if>
												<c:if test="${var.bill_type=='5'}">退料入库</c:if>
												<c:if test="${var.bill_type=='6'}">拒收入库</c:if>
												<c:if test="${var.bill_type=='7'}">其他入库</c:if>
											</td>
											<td class='center'>${var.purchase_code}</td>
											<%-- <td class='center'>${var.purchase_detail_id}</td> --%>
											<td class='center'>
												<fmt:formatDate value="${var.pre_arrival_time}" pattern="yyyy-MM-dd"/>
											</td>
											<td class='center'>
												<fmt:formatDate value="${var.arrival_time}" pattern="yyyy-MM-dd"/>
											</td>
											<td class='center'>${var.store_sn}</td>
											<td class='center'>${var.store_name}</td>
											<td class='center'>
											<c:if test="${var.status=='0'}">物流中</c:if>
											<c:if test="${var.status=='1'}">已到货</c:if>
											<c:if test="${var.status=='9'}">已完成</c:if>
											</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
												<c:if test="${QX.edit == 1 }">
													
														<%-- 	<c:if test="${var.bill_type == '1' || var.bill_type == '2'}">
																<a class="btn btn-xs btn-success" title="质检" onclick="quality('${var.inbound_notice_code}','${var.bill_type}','1');">质检</a>
															</c:if> --%>
															
															<a class="btn btn-xs btn-success" title="质检" onclick="toBatchList('${var.inbound_notice_code}','${var.bill_type}','4');">质检</a>
														<%-- 	<a class="btn btn-xs btn-success" title="入库" onclick="quality('${var.inbound_notice_code}','${var.bill_type}','2');">入库</a>
															<a class="btn btn-xs btn-success" title="完成" onclick="update('${var.inbound_notice_id}','${var.inbound_notice_code}');">完成</a>
															<a class="btn btn-xs btn-success" title="明细" onclick="quality('${var.inbound_notice_code}','${var.bill_type}','3');">明细</a>
															<a class="btn btn-xs btn-success" title="打印" onclick="quality('${var.inbound_notice_code}','${var.bill_type}','5');">打印</a> --%>
													
														
												</c:if>
													<c:if test="${QX.del == 1 }">
													<%-- <a class="btn btn-xs btn-danger" onclick="del('${var.inbound_notice_id}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a> --%>
													</c:if>
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
									<c:if test="${QX.add == 1 }">
									<!-- <a class="btn btn-mini btn-success" onclick="add();">新增</a> -->
									</c:if>
									<c:if test="${QX.del == 1 }">
									<!-- <a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a> -->
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
			 diag.URL = '<%=basePath%>inboundnotice/goAdd.do';
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
					var url = "<%=basePath%>inboundnotice/delete.do?inbound_notice_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//到货操作
		function arrived(Id,inbound_notice_code){
			var str = "确定已经收到货了吗?";
			bootbox.confirm(str, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>inboundnotice/arrived.do?inbound_notice_id="+Id+"&inbound_notice_code="+inbound_notice_code+"&tm="+new Date().getTime();
					$.get(url,function(data){
					top.hangge();
						if(data.msg=="fail"){
								alert(data.info);
							}
						tosearch();
					});
				}
			});
		}
		//更新到货通知单状态
		function update(Id,inbound_code){
			var str = "此通知单已完成!";
			bootbox.confirm(str, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>inboundnotice/update.do?inbound_notice_id="+Id+"&inbound_code="+inbound_code+"&tm="+new Date().getTime();
					$.get(url,function(data){
						top.hangge();
						if(data.msg=="fail"){
							alert(data.info);
						}
						tosearch();
					});
				}
			});
		}
		function toBatchList(code){
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="批次明细";
			 diag.URL = '<%=basePath%>inboundnoticestockbatch/listForCheck.do?inbound_notice_code='+code+'&page=1';
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
		//page:1、质检	2、入库	3、明细	4、批次	5、打印
		function quality(code,type,page){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="批次明细";
			 diag.URL = '<%=basePath%>inboundnoticedetail/list.do?inbound_notice_code='+code+'&bill_type='+type+'&page='+page;
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

			 //inboundnoticestockbatch

		// 明细
		function edit(Id,BillType){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="到货明细";
			 diag.URL = '<%=basePath%>inboundnoticedetail/list.do?inbound_code='+Id+'&bill_type='+BillType;
			 diag.Width = 850;
			 diag.Height = 555;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 
				} */
				tosearch();
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
								url: '<%=basePath%>inboundnotice/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>inboundnotice/excel.do';
		}
	</script>


</body>
</html>