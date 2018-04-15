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
						<form action="outboundnotice/list.do" method="post" name="Form" id="Form">
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
									出库通知单：<input type="text" placeholder="出库通知单" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="出库通知单" style="width: 120px;"/>
								</td>
								
								<td class="pgt-query-form-td">
									业务单据：<input type="text" placeholder="业务单据" class="nav-search-input"  autocomplete="off" name="purchase_code"  placeholder="业务单据" value="${pd.purchase_code }" style="width: 120px;"/>
								</td>
								
								<%-- <td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="仓库" class="nav-search-input"  autocomplete="off" name="warename"  placeholder="仓库" value="${pd.warename }"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td> --%>
								
							<!-- 	<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								 -->
								
								<td class="pgt-query-form-td">
								 	出库类型：<select name="name" id="id" data-placeholder="请选择" style="width: 120px;">
									
									<option value="">全部类型</option>
									<option value="0" ${pd.bill_type=="0"?'selected':''} >领料出库 </option>
									<option value="1" ${pd.bill_type=="1"?'selected':''} >调拨出库</option>
									<option value="2" ${pd.bill_type=="2"?'selected':''} >采购退货出库</option>
									<option value="8" ${pd.bill_type=="8"?'selected':''} >生产退货出库</option>
									<option value="3" ${pd.bill_type=="3"?'selected':''} >报损出库</option>
									<option value="4" ${pd.bill_type=="4"?'selected':''} >内销出库</option>
									<option value="5" ${pd.bill_type=="5"?'selected':''} >销售出库</option>
									<option value="7" ${pd.bill_type=="7"?'selected':''} >不良品出库</option>
									<option value="6" ${pd.bill_type=="6"?'selected':''} >其他出库</option>
									
								  	</select>
								</td>
								
								<td  class="pgt-query-form-td">
								 	状态：<select name="status"  data-placeholder="请选择" style="width: 120px;">
									<option value="">全部状态</option>
									<option value="0"  ${pd.status=="0"?'selected':''} >已创建</option>
									<option value="10"  ${pd.status=="10"?'selected':''} >已出库</option>
								  	</select>
								</td>
								<td  class="pgt-query-form-td">
									出库仓库：<select name="stores" id="stores" data-placeholder="仓库列表" style="width: 150px;">
										<option value="">请选择仓库</option>
										<c:if test="${not empty storeList}">
											<c:forEach items="${storeList}" var="var" varStatus="vs">
									         	<c:choose>
													<c:when test="${pd.store_sn==var.store_sn}">
													    <option selected="selected" value="${var.store_sn}">${var.store_name}</option>
													</c:when>
													<c:otherwise>
														<option value="${var.store_sn}">${var.store_name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
								  	</select>
							    </td>
								
								<c:if test="${QX.cha == 1 }">
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="查询">查询</a></td>
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="doResetForm();"  title="清空">清空</a></td>
								</c:if>
								<%-- <c:if test="${QX.toExcel == 1 }">
								<td style="vertical-align:top;padding-left:2px;">
								<a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL">
								<i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue">
								</i>
								</a>
								</td>
								</c:if> --%>
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
									<th class="center">出库通知单</th>
									<th class="center">出库类型</th>
									<th class="center">业务单据</th>
									<th class="center">仓库编号</th>
									<th class="center">仓库名称</th>
									<th class="center">状态</th>
									<th class="center">预计出库时间</th>
									<th class="center">实际出库时间</th>
									<th class="center">拣货次数</th>
									
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
												<label class="pos-rel"><input type='checkbox' name='ids' attr_notice_id="${var.outbound_notice_id }" value="${var.purchase_code}" class="ace" /><span class="lbl"></span></label>
											</td> 
											
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.outbound_notice_code}</td>
											<td class='center'>
											
											<c:if test="${var.bill_type==0 }">
											   领料出库
											</c:if>
											
											<c:if test="${var.bill_type==1 }">
											   调拨出库
											</c:if>
											<c:if test="${var.bill_type==2 }">
											   采购退货出库
											</c:if>
											<c:if test="${var.bill_type==8 }">
											  生产退货出库
											</c:if>
											<c:if test="${var.bill_type==3 }">
											   报损出库
											</c:if>
											<c:if test="${var.bill_type==4 }">
											   内销出库
											</c:if>
											
											<c:if test="${var.bill_type==5 }">
											   销售出库
											</c:if>
											
											<c:if test="${var.bill_type==7 }">
											  不良品出库
											</c:if>
											
											<c:if test="${var.bill_type==6 }">
											   其他出库
											</c:if>
											
											</td>
											<td class='center'>${var.purchase_code}</td>
											<td class='center'>${var.store_sn}</td>
											<td class='center'>${var.store_name}</td>											
											<td class='center'>
										    
											<c:if test="${var.status==0 }">
											   已创建
											</c:if>
											
											<c:if test="${var.status==10 }">
											   已出库
											</c:if>
											<c:if test="${var.status==1 }">
											   已取消
											</c:if>											
											</td>
											<td class='center'><fmt:formatDate value="${var.pre_send_time}" pattern="yyyy-MM-dd"/></td>
											<td class='center'><fmt:formatDate value="${var.send_time}" pattern="yyyy-MM-dd"/></td>

											<td class='center'>${var.print_count}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<%-- <c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.outbound_notice_id}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" onclick="del('${var.outbound_notice_id}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
													</c:if> --%>
													
												
													
													<a class="btn btn-mini btn-success" onclick="viewDetail('${var.outbound_notice_code}');">详情</a>
													&nbsp;&nbsp;
													<c:if test="${var.bill_type == 6 }">
													
													</c:if>
													<c:choose>
														<c:when test="${var.bill_type == 3 }">
															<a class="btn btn-mini btn-success" title="报损出库" onclick="executeOutbound('${var.outbound_notice_id}','${var.outbound_notice_code}','${var.status}', '${var.bill_type }');">
																出库
															</a>
														</c:when>
														<c:when test="${var.bill_type == 7 }">
															<a class="btn btn-mini btn-success" title="不良品出库" onclick="executeOutbound('${var.outbound_notice_id}','${var.outbound_notice_code}','${var.status}', '${var.bill_type }');">
																出库
															</a>
														</c:when>
														<c:otherwise>
															<a class="btn btn-mini btn-success" title="发货出库" onclick="operateoutbound('${var.outbound_notice_id}','${var.outbound_notice_code}','${var.status}', '${var.bill_type }');">
																出库
															</a>
														</c:otherwise>
													</c:choose>
													
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.outbound_notice_id}','${var.outbound_notice_code}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.outbound_notice_id}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
								
								 <a class="btn btn-mini btn-success" onclick="printsendform();">打印面单</a>
								 <a class="btn btn-mini btn-success" onclick="printOutboundNotices();">打印拣货单</a>
				<!-- 				
								  &nbsp;&nbsp;
								 <a class="btn btn-mini btn-success" onclick="add();">打印拣货单</a> -->
									<%-- <c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if> --%>
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
		
	
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>outboundnotice/delete.do?outbound_notice_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//修改
		function edit(Id,code){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>outboundnotice/goEdit.do?outbound_notice_id='+Id+'&outbound_notice_code='+code;
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
		


		//修改
				function operateoutbound(Id,code,status,bill_type){
		        	if(status=="10")
				    {
				        alert("发货单已出库 ");
		        		return;
				    }
					 top.jzts();
					 var diag = new top.Dialog();
					 diag.Drag=true;
					 diag.Title ="出库";
					 
					 diag.URL = '<%=basePath%>outboundnoticedetail/sendlist.do?outbound_notice_id='+Id+'&outbound_notice_code='+code+'&bill_type='+bill_type;
					 diag.Width =900;
					 diag.Height = 500;
					 diag.Modal = true;				//有无遮罩窗口
					 diag. ShowMaxButton = true;	//最大化按钮
				     diag.ShowMinButton = true;		//最小化按钮 
					 diag.CancelEvent = function(){ //关闭事件
						/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none')
						 { */
							 tosearch();
						 //} 
						diag.close();
					 };
					 diag.show();
				}
		
		
				//修改
				function viewDetail(Id){
					 top.jzts();
					 var diag = new top.Dialog();
					 diag.Drag=true;
					 diag.Title ="详情 ";
					 
					 diag.URL = '<%=basePath%>outboundnoticedetail/list.do?outbound_notice_code='+Id;
					 diag.Width =900;
					 diag.Height = 500;
					 diag.Modal = true;				//有无遮罩窗口
					 diag. ShowMaxButton = true;	//最大化按钮
				     diag.ShowMinButton = true;		//最小化按钮 
					 diag.CancelEvent = function(){ //关闭事件
						/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none')
						 {
							 tosearch();
						 }  */
						diag.close();
					 };
					 diag.show();
				}

		
		function printsendform()
		{
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				 if(document.getElementsByName('ids')[i].checked)
					 {
					 if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					 }
			}
			if(str=='')
				{
				   alert(" 还未选择任何内容 ");
				    return;
				}
			
			var url = "<%=basePath%>outboundnotice/printsendform.do?sn="+str;
			window.open(url, "", 'left=250,top=150,width=850,height=700,toolbar=no,menubar=no,status=no,scrollbars=yes,resizable=yes');
		}
		function printOutboundNotices()
		{
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				 if(document.getElementsByName('ids')[i].checked)
					 {
					 if(str=='') str += document.getElementsByName('ids')[i].getAttribute('attr_notice_id');
					  	else str += ',' + document.getElementsByName('ids')[i].getAttribute('attr_notice_id');
					 }
			}
			if(str=='')
				{
				   alert(" 还未选择任何内容 ");
				    return;
				}
			
			var url = "<%=basePath%>outboundnotice/printOutboundNotices.do?sn="+str;
			window.open(url, "", 'left=250,top=150,width=850,height=700,toolbar=no,menubar=no,status=no,scrollbars=yes,resizable=yes');
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
								url: '<%=basePath%>outboundnotice/deleteAll.do?tm='+new Date().getTime(),
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
		
		function executeOutbound(Id,code,status,bill_type){
			console.info(status);
        	if(status=="10")
		    {
		        alert("发货单已出库 ");
        		return;
		    }
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="出库";
			 
			 diag.URL = '<%=basePath%>outboundnoticestockbatch/list.do?outbound_notice_id='+Id+'&outbound_notice_code='+code;
			 diag.Width =900;
			 diag.Height = 500;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none')
				 { */
					 tosearch();
				 //} 
				diag.close();
			 };
			 diag.show();
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>outboundnotice/excel.do';
		}
	</script>


</body>
</html>