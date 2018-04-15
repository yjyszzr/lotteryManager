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
						<input type="text" class="nav-code-input" id="nav-code-input" autocomplete="off" name="inbound_code"  style="display:none;"/>
						<input type="text"  name="in_code" id="in_code" autocomplete="off"   style="display:none;"/>
						
						<input type="text"  name="inbound_notice_code" id="inbound_notice_code" value="${pd.inbound_notice_code}" autocomplete="off"   style="display:none;"/>
						<input type="hidden" name="bill_type" id="bill_type" value="${pd.bill_type}"/>
						<input type="hidden" name="page" id="page" value="${pd.page}"/>
						<input type="hidden" name="noticeStatus" id="noticeStatus" value="${pd.noticeStatus}"/>
						<%-- <table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<input type="text" class="nav-code-input" id="nav-code-input" autocomplete="off" name="inbound_code"  style="display:none;"/>
											
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
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<c:if test="${pd.page=='2'}">
										<th class="center" style="width:35px;">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
										</th>
									</c:if>
									<th class="center" style="width:50px;">序号</th>
									<!-- <th class="center">业务单据明细ID</th>
									<th class="center">商品ID</th> -->
									<th class="center">商品名称</th>
									<th class="center">商品编码</th>
									<th class='center'>商品条形码</th>
									<th class="center">单位</th>
									<th class="center">是否批次管理</th>
									<th class="center">规格</th>
									<th class="center">预计到货数量</th>
						<!-- 			<th class="center">良品数量</th> -->
<!-- 									<th class="center">不良品数量</th>
									<th class="center">不良品处理类型</th>
									<th class="center">质检状态</th>
									<th class="center">质检时间</th> -->
									<!-- <th class="center">质检ID</th> -->
									<th class="center">状态</th>
									<th class="center">操作</th>

								</tr>
							</thead>
													
											<% boolean isShow=false; %>
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<c:if test="${pd.page=='2'}">
												<td class='center'>
												<c:if test="${var.status == '3'|| empty var.status}">
													<%  isShow = true; %>
													<label class="pos-rel"><input type='checkbox' name='ids' onclick="inboundone(this,'${var.notice_detail_id}');" value="${var.notice_detail_id}" class="ace" /><span class="lbl"></span></label>
												</c:if>
												</td>
											</c:if>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<%-- 	<td class='center'>${var.inbound_code}</td>
											<td class='center'>${var.purchase_code}</td>
											<td class='center'>${var.purchase_detail_id}</td>
											<td class='center'>${var.sku_id}</td> --%>
											<td class='center'>${var.sku_name}</td>
											<td class='center'>${var.sku_encode}</td>
											<td class='center'>${var.sku_barcode}</td>
											<td class='center'>${var.unit}</td>
											<td class='center'>
											<c:if test="${var.isopen_batch=='1'}">是</c:if>
											<c:if test="${var.isopen_batch!='1'}">否</c:if>
											</td>
											<td class='center'>${var.spec}</td>
											<td class='center'>${var.pre_arrival_quantity}</td>
<%-- 											<td class='center'>${var.good_quantity}</td> --%>
											<%-- <td class='center'>${var.bad_quantity}</td>
											<td class='center'>
												<c:if test="${var.bad_deal_type == '0'}">退回</c:if>
												<c:if test="${var.bad_deal_type == '1'}">入不良品</c:if>
											</td>
											<td class='center'>
												<c:if test="${var.quality_status == '0'}">未质检</c:if>
												<c:if test="${var.quality_status == '1'}">已质检</c:if>
											</td>
											<td class='center'>
												<fmt:formatDate value="${var.quality_time}" pattern="yyyy-MM-dd"/>
											</td> --%>
											<%-- <td class='center'>${var.quality_id}</td> --%>
											<td class='center'>
												<c:if test="${var.status == '0'|| empty var.status}">未入库</c:if>
												<c:if test="${var.status == '1'}">已到货</c:if>
												<c:if test="${var.status == '2'}">已批次</c:if>
												<c:if test="${var.status == '3'}">已质检</c:if>
												<c:if test="${var.status == '9'}">已入库</c:if>
											</td>
											
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<c:if test="${QX.edit == 1 }">
													<c:if test="${pd.bill_type == '1' || pd.bill_type == '2'}">
														<c:if test="${pd.page == '1'}">
															<c:if test="${var.quality_status == '0' || empty var.quality_status}">
																<a class="btn btn-xs btn-success" onclick="edit('${var.notice_detail_id}')">质检</a>
															</c:if>
															<c:if test="${var.quality_status == '1'}">
																<a class="btn btn-xs btn-success" disabled = "ture" onclick="edit('${var.notice_detail_id}')">已质检</a>
															</c:if>
														</c:if>
													</c:if>

													<c:if test="${pd.page == '4'}">
														<c:if test="${var.isopen_batch=='1'}">
																<a class="btn btn-xs btn-success" onclick="batchdetail('${pd.inbound_notice_code}',
																'${var.notice_detail_id}','${var.sku_id}','${var.sku_name}',
																'${var.sku_encode}','${var.sku_barcode}','${var.unit}',
																'${var.spec}','${var.good_quantity}','${var.status}','${pd.bill_type}','${var.pre_arrival_quantity}'
																)">批次</a>
														</c:if>	
														<c:if test="${var.isopen_batch!='1'}">
																<a disabled = "ture" class="btn btn-xs btn-success" onclick="batchdetail('${pd.inbound_notice_code}',
																'${var.notice_detail_id}','${var.sku_id}','${var.sku_name}',
																'${var.sku_encode}','${var.sku_barcode}','${var.unit}',
																'${var.spec}','${var.good_quantity}','${var.status}','${pd.bill_type}','${var.pre_arrival_quantity}'
																)">批次</a>
														</c:if>	
													</c:if>	
													<c:if test="${pd.page == '5'}">
														<c:if test="${var.isopen_batch=='1'}">
																<a class="btn btn-xs btn-success" onclick="batchdetailprint('${pd.inbound_notice_code}',
																'${var.notice_detail_id}','${var.sku_id}','${var.sku_name}',
																'${var.sku_encode}','${var.sku_barcode}','${var.unit}',
																'${var.spec}','${var.good_quantity}','${var.status}','${pd.bill_type}','${pd.page}'
																)">打印批次</a>
														</c:if>	
														<c:if test="${var.isopen_batch!='1'}">
																<a disabled = "ture" class="btn btn-xs btn-success" onclick="batchdetailprint('${pd.inbound_notice_code}',
																'${var.notice_detail_id}','${var.sku_id}','${var.sku_name}',
																'${var.sku_encode}','${var.sku_barcode}','${var.unit}',
																'${var.spec}','${var.good_quantity}','${var.status}','${pd.bill_type}','${pd.page}'
																)">打印批次</a>
														</c:if>	
													</c:if>	
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
								<c:if test="${pd.page=='2'}">
									<%  if(isShow){ %>
										<a class="btn btn-xs btn-success" title="入库" onclick="inboundAll();">入库</a>	
									<%}else{ %>
										<a class="btn btn-xs btn-success" title="入库" disabled = "ture" onclick="inboundAll();">入库</a>
									<%}%>
								</c:if>
								
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
					if(th_checked){
						$(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
						//$(row).addClass(active_class).find('input[type=checkbox]').eq(0).trigger('click');
						
					}
					else {
						$(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
						//$(row).removeClass(active_class).find('input[type=checkbox]').eq(0).trigger('click');
					}
				});
				var temp=$("#in_code").val();
				//alert(temp);
				//alert(th_checked);
				for(var i=0;i < document.getElementsByName('ids').length;i++){
					  var Id=document.getElementsByName('ids')[i].value;
					  //alert(Id);
					  var temprow=Id+"__";
					  if(th_checked){
							if(temp.indexOf(temprow)<0 ) {
								temp=temp+temprow;
							}
						}
						else{
							if(temp.indexOf(temprow)>=0 ) {
								temp=temp.replace(temprow, "");
							}
						}
					//alert(temp);
				}
				
				$("#in_code").val(temp);
			});
		});
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>inboundnoticedetail/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 tosearch();
					 }else{
						 tosearch();
					 }
				} */
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>inboundnoticedetail/delete.do?notice_detail_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		function inboundone(obj,Id){
			var temp=$("#in_code").val();
			var temprow=Id+"__";
			if(obj.checked){
				if(temp.indexOf(temprow)<0 ) {
					temp=temp+temprow;
				}
			}
			else{
				if(temp.indexOf(temprow)>=0 ) {
					temp=temp.replace(temprow, "");
				}
			}
			//alert(temp);
			$("#in_code").val(temp);
			
		}

		//入库
		function inboundAll(){
			var in_code=$("#in_code").val();
			var inbound_notice_code=$("#inbound_notice_code").val();
			bootbox.confirm("确定要入库吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>inboundnoticedetail/inboundall.do?in_code="+in_code+"&inbound_notice_code="+inbound_notice_code+"&tm="+new Date().getTime();
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
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>inboundnoticedetail/goEdit.do?notice_detail_id='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
				 tosearch();
				diag.close();
			 };
			 diag.show();
		}
		//修改
		function batchdetail(inbound_notice_code, notice_detail_id, sku_id, sku_name, sku_encode, sku_barcode, unit, spec, quantity, status, bill_type, pre_arrival_quantity){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="批次列表";
			 diag.URL = '<%=basePath%>inboundnoticestockbatch/list.do?notice_detail_id='+notice_detail_id
					 +'&inbound_notice_code='+inbound_notice_code
					 +'&sku_id='+sku_id+'&sku_name='+sku_name+'&sku_encode='+sku_encode+'&sku_barcode='+sku_barcode
					 +'&unit_name='+unit
					 +'&spec='+spec
					 /* +'&status='+status */
					 +'&bill_type='+bill_type
					 +'&quantity='+quantity
					 +'&pre_arrival_quantity='+pre_arrival_quantity
					 +'&noticeStatus='+$("#noticeStatus").val();
			 diag.Width = 1250;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
				 tosearch();
				diag.close();
			 };
			 diag.show();
		}//修改
		function batchdetailprint(inbound_notice_code, notice_detail_id, sku_id, sku_name, sku_encode, sku_barcode, unit, spec, quantity, status, bill_type, page){
		
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="批次列表";
			 diag.URL = '<%=basePath%>inboundnoticestockbatch/list.do?notice_detail_id='+notice_detail_id
					 +'&inbound_notice_code='+inbound_notice_code
					 +'&sku_id='+sku_id+'&sku_name='+sku_name+'&sku_encode='+sku_encode+'&sku_barcode='+sku_barcode
					 +'&unit_name='+unit
					 +'&spec='+spec
					 +'&status='+status
					 +'&bill_type='+bill_type
					 +'&page='+page
					 +'&quantity='+quantity;
			 diag.Width = 1250;
			 diag.Height = 755;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
				 tosearch();
				diag.close();
			 };
			 diag.show();
		}
		//修改
		function editbatch(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑批次 ";
			 diag.URL = '<%=basePath%>inboundnoticedetail/goBatch.do?notice_detail_id='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				} */
				 tosearch();
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		function makeAll(msg){
			alert(msg);
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
								url: '<%=basePath%>inboundnoticedetail/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>inboundnoticedetail/excel.do';
		}
	</script>


</body>
</html>