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
						<form action="inbound/list.do" method="post" name="Form" id="Form">
						<table class="pgt-query-form-table">
							<tr>
								<td class="pgt-query-form-td">
									入库通知单：<input type="text" placeholder="入库通知单" class="nav-search-input" id="inbound_notice_code" autocomplete="off" name="inbound_notice_code" value="${pd.inbound_notice_code}" placeholder="这里输入关键词"/>
								</td>
								<td class="pgt-query-form-td">
									业务单号：<input type="text" placeholder="业务单号" class="nav-search-input" id="purchase_code" autocomplete="off" name="purchase_code" value="${pd.purchase_code}" placeholder="这里输入关键词"/>
								</td>

								<td class="pgt-query-form-td">
								 	仓库：<select name="stores" id="stores" data-placeholder="仓库列表" style="width: 150px;">
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
							    <td class="pgt-query-form-td">
								 	入库类型：<select name="inbound_type" id="inbound_type" data-placeholder="请选择" style="width: 160px;">
									<option value="">全部类型</option>
									<option value="0" ${pd.inbound_type=="0"?'selected':''} >采购待检入库</option>
									<option value="1" ${pd.inbound_type=="1"?'selected':''} >采购总仓入库</option>
									<option value="2" ${pd.inbound_type=="2"?'selected':''} >采购正品入库</option>
									<option value="3" ${pd.inbound_type=="3"?'selected':''}>采购不良品入库</option>
									<option value="4" ${pd.inbound_type=="4"?'selected':''}>生产待检入库</option>
									<option value="5" ${pd.inbound_type=="5"?'selected':''}>生产总仓入库</option>
									<option value="6" ${pd.inbound_type=="6"?'selected':''}>调拨入库</option>
									<option value="7" ${pd.inbound_type=="7"?'selected':''} >调拨物流入库</option>
									<option value="8" ${pd.inbound_type=="8"?'selected':''} >调拨待检入库</option>
									<option value="9" ${pd.inbound_type=="9"?'selected':''} >销售物流入库</option>
									<option value="10" ${pd.inbound_type=="10"?'selected':''} >报损入库</option>
									<option value="11" ${pd.inbound_type=="11"?'selected':''}>其他入库</option>
									<option value="12" ${pd.inbound_type=="12"?'selected':''}>生产正品入库</option>
									<option value="13" ${pd.inbound_type=="13"?'selected':''}>生产不良品入库</option>
									<option value="14" ${pd.inbound_type=="14"?'selected':''}>客户退货入库</option>
									<option value="15" ${pd.inbound_type=="15"?'selected':''} >生产退料入库</option>
									<option value="16" ${pd.inbound_type=="16"?'selected':''} >客户拒收入库</option>
									<option value="20" ${pd.inbound_type=="20"?'selected':''} >俱乐部采购入库</option>
									<option value="21" ${pd.inbound_type=="21"?'selected':''} >拒收待检入库</option>
									<option value="22" ${pd.inbound_type=="22"?'selected':''} >拒收入库</option>
									<option value="23" ${pd.inbound_type=="23"?'selected':''} >不良品入库</option>
									<option value="30" ${pd.inbound_type=="30"?'selected':''} >销售退货入库</option>
									<option value="31" ${pd.inbound_type=="31"?'selected':''} >销售采购入库</option>
								  	</select>
								  	
								</td>
								<td style="padding-left:10px;">
								            业务时间：
								    <input class="span10 date-picker" name="start_date" id="start_date" value="${pd.start_date}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开始时间" title="开始时间"/>
								</td>
								<td style="padding-left:3px;">
								    <input class="span10 date-picker" name="end_date" id="end_date" value="${pd.end_date}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束时间" title="结束时间"/>
								</td>
								<c:if test="${QX.cha == 1 }">
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="查询">查询</a></td>
								<td class="pgt-query-form-td"><a class="btn btn-light btn-xs" onclick="doResetFormWithoutSelect();"  title="清空">清空</a></td>
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
									
									<th class="center">入库单号</th>
									<th class="center">入库类型</th>
									<th class="center">仓库编号</th>
									<th class="center">仓库名称</th>
									<th class="center">业务单号</th>
									<th class="center">入库通知单号</th>
									<th class="center">业务时间</th>
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.inbound_id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											
											<td class='center'>${var.inbound_code}</td>
											<td class='center'>${var.inbound_type}</td>
											<td class='center'>${var.store_sn}</td>
											<td class='center'>${var.store_name}</td>
											<td class='center'>${var.purchase_code}</td>
											<td class='center'>${var.inbound_notice_code}</td>
											<td class='center'>${var.business_time}</td>
											
											<td class="center">
												
												<div class="hidden-sm hidden-xs btn-group">
													<a class="btn btn-xs btn-success" title="编辑" onclick="opendetail('${var.inbound_code}');">
														查看明细
													</a>
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
								<td style="vertical-align:top;">
								</td>
								<td style="vertical-align:top;"><a class="btn btn-mini btn-success" onclick="printInboundNotices();">打印入库单</a><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
		function doResetFormWithoutSelect(){
			$("#inbound_notice_code").val('');
			$("#purchase_code").val('');
			$("#inbound_type").val('');
			$("#start_date").val('');
			$("#end_date").val('');
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
			 diag.URL = '<%=basePath%>inbound/goAdd.do';
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
					var url = "<%=basePath%>inbound/delete.do?inbound_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		function opendetail(inbound_code){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="入库单详情";
		     diag.URL = '<%=basePath%>inbounddetail/list.do?inbound_code='+inbound_code;
			 diag.Width = $(window).width();
			 diag.Height = $(window).height();
			 diag.Modal = true;				//有无遮罩窗口
			 diag.ShowMaxButton = true;	//最大化按钮
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
			 diag.URL = '<%=basePath%>inbound/goEdit.do?inbound_id='+Id;
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
		
		//打印入库单
		function printInboundNotices()
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
			
			var url = "<%=basePath%>inboundnotice/printInboundNotices.do?sn="+str;
			window.open(url, "", 'left=250,top=150,width=1000,height=700,toolbar=no,menubar=no,status=no,scrollbars=yes,resizable=yes');
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
								url: '<%=basePath%>inbound/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>inbound/excel.do';
		}
	</script>


</body>
</html>